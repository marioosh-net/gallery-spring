package net.marioosh.gallery;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import net.marioosh.gallery.model.dao.AlbumDAO;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.model.dao.SearchDAO;
import net.marioosh.gallery.model.entities.Album;
import net.marioosh.gallery.model.helpers.AlbumBrowseParams;
import net.marioosh.gallery.model.helpers.AlbumSortField;
import net.marioosh.gallery.model.helpers.PhotoBrowseParams;
import net.marioosh.gallery.model.helpers.PhotoSortField;
import net.marioosh.gallery.model.helpers.Range;
import net.marioosh.gallery.model.helpers.Visibility;
import net.marioosh.gallery.utils.UndefinedUtils;
import net.marioosh.gallery.utils.WebUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	private Logger log = Logger.getLogger(MainController.class);

	@Autowired
	private Validator validator;

	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private PhotoDAO photoDAO;

	@Autowired
	private SearchDAO searchDAO;
	
	@Autowired
	private Settings settings;

	@Autowired
	private UtilService utilService;
	
	/**
	 * metoda poleci przy obsludze kazdego requesta, bo @ModelAttribute
	 * dorzucam do niej inne modelAttributy poprzed parametr model 
	 * @param model
	 * @return
	 */
	@ModelAttribute("context")
	public String context(Model model) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		model.addAttribute("servername", request.getServerName());
		model.addAttribute("hash2", utilService.getHash2());
		return request.getContextPath();
	}

	@RequestMapping(value="/home")
	public String index(Model model) {
		return home(null, 1, model);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		WebUtils.logRequestInfo(request);	// access log
		return home(null, 1, model);
	}
	
	@RequestMapping(value="/loginfail")
	public String loginfail(Model model) {
		model.addAttribute("loginfail", 1);
		return home(null, 1, model);
	}

	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String home(@RequestParam(value="a", required=false, defaultValue="0") Long albumId, @RequestParam(value="p",required=false, defaultValue="1") int p, @RequestParam(value="pp",required=false, defaultValue="1") int pp, Model model) {
		return home(null, 1, model);
		// return "index";
	}
	
	@RequestMapping(value="/home/{albumid}", method=RequestMethod.GET)
	public String home(@PathVariable("albumid") Long albumId, Model model) {
		// model.addAttribute("aid",albumId);
		//return "index";
		return home(albumId, 1, model);
	}

	@RequestMapping(value="/home/{albumid}/{page}", method=RequestMethod.GET)
	public String home(@PathVariable("albumid") Long albumId, @PathVariable("page") int pp, Model model) {
		model.addAttribute("aid",albumId);
		model.addAttribute("ppage", pp);
		return "index";
	}
	
	@RequestMapping("/json/albums")
	public void jsonAlbums(HttpServletResponse response, Model model) throws JSONException, IOException {
		albums(1,"",model);
		// JSONArray jsonArray = new JSONArray(((List)model.asMap().get("albums")).toArray()[0]);
		// jsonArray.write(response.getWriter());
		JSONObject o = new JSONObject(((List)model.asMap().get("albums")).toArray()[0]);
		o.write(response.getWriter());
		/*
		JSONObject o = new JSONObject(model.asMap().get("albums"));
		o.write(response.getWriter());
		*/
	}
	
	@RequestMapping("/albums")
	public String albums(Model model) {
		return albums(1,"",model);
	}
	
	@RequestMapping("/albums/{page}")
	public String albums(@PathVariable("page") int p, Model model) {
		return albums(p,"",model);
	}
	
	@RequestMapping("/albums/{page}/{search}")
	public String albums(@PathVariable("page") int p, @PathVariable("search") String s, Model model) {
		AlbumBrowseParams bp = new AlbumBrowseParams();
		bp.setVisibility(utilService.getCurrentVisibility());

		if(p < 0) {
			p = 1;
		}		
		bp.setRange(new Range((p-1)*settings.getAlbumsPerPage(),settings.getAlbumsPerPage()));
		bp.setSort(AlbumSortField.NAME_DESC);
		if(!s.isEmpty()) {
			bp.setSearch(s);
		}
		int acount = albumDAO.countAll(bp);
		List<Album> albums = albumDAO.findAll(bp);
		model.addAttribute("albums", albums);
		int[] ac = new int[albums.size()];
		int i = 0;
		for(Album a: albums) {
			PhotoBrowseParams bp2 = new PhotoBrowseParams();
			bp2.setAlbumId(a.getId());
			bp2.setVisibility(utilService.getCurrentVisibility());
			ac[i++] = photoDAO.countAll(bp2);
		}
		model.addAttribute("ac",ac);
		model.addAttribute("acount",acount);
		int[][] apages = UndefinedUtils.pages(acount, settings.getAlbumsPerPage());
		model.addAttribute("apages", apages);
		model.addAttribute("apagesCount", apages.length);
		model.addAttribute("apage", p);
		model.addAttribute("search",s);
		return "albums";
	}
	
	@RequestMapping("/subalbums/{parentid}")
	public ModelAndView subalbums(@PathVariable("parentid") Long parentId, Model model) {
		AlbumBrowseParams bp = new AlbumBrowseParams();
		bp.setVisibility(utilService.getCurrentVisibility());
		bp.setParentId(parentId);
		bp.setSort(AlbumSortField.NAME_DESC);
		List<Album> subalbums = albumDAO.findAll(bp);
		int acount = albumDAO.countAll(bp);
		int[] ac = new int[subalbums.size()];
		int i = 0;
		for(Album a: subalbums) {
			PhotoBrowseParams bp2 = new PhotoBrowseParams();
			bp2.setAlbumId(a.getId());
			bp2.setVisibility(utilService.getCurrentVisibility());
			ac[i++] = photoDAO.countAll(bp2);
		}
		model.addAttribute("ac",ac);		
		model.addAttribute("acount", acount);		
		model.addAttribute("subalbums", subalbums);
		return new ModelAndView("subalbums", model.asMap());
	}

	@RequestMapping("/covers")
	public ModelAndView covers(Model model) {
		albums(model);
		return new ModelAndView("covers", model.asMap());		
	}
	
	@RequestMapping("/covers/{page}")
	public ModelAndView covers(@PathVariable("page") int p, Model model) {
		albums(p, model);
		return new ModelAndView("covers", model.asMap());		
	}
	
	@RequestMapping("/covers/{page}/{search}")
	public ModelAndView covers(@PathVariable("page") int p, @PathVariable("search") String s, Model model) {
		albums(p, s, model);
		return new ModelAndView("covers", model.asMap());
	}

	@RequestMapping("/photos")
	public String photos(Model model) {
		return photos(0L, 1, model);
	}
	
	@RequestMapping("/photos/{albumid}")
	public String photos(@PathVariable("albumid") Long albumId, Model model) {
		return photos(albumId, 1, model);
	}
	
	@RequestMapping(value="/photos/{albumid}/{page}")
	public String photos(@PathVariable("albumid") Long albumId, @PathVariable("page") int pp, Model model) {
		PhotoBrowseParams bp1 = new PhotoBrowseParams();
		bp1.setVisibility(utilService.getCurrentVisibility());
		
		if(albumId != 0) {
			if(pp < 0) {
				pp = 1;
			}			
			bp1.setAlbumId(albumId);		
			bp1.setRange(new Range((pp-1)*settings.getPhotosPerPage(),settings.getPhotosPerPage()));
			bp1.setSort(PhotoSortField.NAME);
			int pcount = photoDAO.countAll(bp1);
			List<Map<String, Object>> l = photoDAO.findAll(bp1, new String[]{"id","visibility","name","hash"});
			log.debug("LIST: "+l);
			// photoDAO.findAllId(bp1)
			model.addAttribute("photos", l);
			model.addAttribute("album", albumDAO.get(albumId));
			model.addAttribute("aid", albumId);
			model.addAttribute("pcount",pcount);
			int[][] ppages = UndefinedUtils.pages(pcount, settings.getPhotosPerPage());
			model.addAttribute("ppages", ppages);
			model.addAttribute("ppagesCount", ppages.length);
			model.addAttribute("ppage", pp);
			
			// getnij wszystkie z albumu
			if(l.size() > 0) {
			bp1.setRange(null);
			bp1.setSort(PhotoSortField.NAME);
			List<Map<String, Object>> l2 = photoDAO.findAll(bp1, new String[]{"id","visibility","name"});
			Iterator<Map<String, Object>> i = l2.iterator();
			List<Map<String, Object>> before = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> after = new ArrayList<Map<String, Object>>();
			boolean s = false;
			int j = 0;
			Map<String, Object> first = l.get(0);
			while(i.hasNext()) {
				Map<String, Object> str = i.next();
				if(first != null && str.get("id").equals(first.get("id"))) {
					// od tego momentu nalezy wywalic z remainingHashes getElemsPerPage() elementow
					s = true;
				}
				if(!s) {
					before.add(str);
				}
				if(s && ++j > settings.getPhotosPerPage()) {
					after.add(str);
				}
			}
			model.addAttribute("before", before);
			model.addAttribute("after", after);
			}
			
		} else {
			model.addAttribute("aid", 0);
			pp = 1;
			model.addAttribute("ppage", pp);
		}
		return "photos";
	}

	@RequestMapping("/searches")
	public ModelAndView searches() {
		return new ModelAndView("searches", "searches", searchDAO.findTop(20));
	}
	
	@ResponseBody
	@RequestMapping("/addsearch.html")
	public String addSearch(@RequestParam String phrase) {
		searchDAO.trigger(phrase);
		return "0";
	}
	
	@ResponseBody
	@RequestMapping("/savealbum.html")
	public String saveAlbum(@ModelAttribute Album album) {
		log.debug(album);
		try {
			album.setModDate(new Date());
			albumDAO.update(album);
		} catch(Exception e) {
			return "1";
		}
		return "0";
	}

	/*@ResponseBody*/
	@Secured("ROLE_ADMIN")
	@RequestMapping("/deletealbum.html")
	public String deleteAlbum(@RequestParam("id") Long id) {
		Album a = albumDAO.get(id);
		if(a != null) {
			String fullpath = UndefinedUtils.absolutePath(settings.getRootPath(), a.getPath());
			// usun katalog z systemu plikow
			File directory = new File(fullpath);
			try {
				FileUtils.deleteDirectory(directory);
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
		albumDAO.delete(id);
		return "redirect:/home";
	}
	
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@RequestMapping("/deletephoto.html")
	public String deletePhoto(@RequestParam("id") Long id) {
		try {
			photoDAO.delete(id);
		} catch (Exception e) {
			return "1";
		}
		return "0";
	}
	
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@RequestMapping("/rotate.html")
	public String rotate(@RequestParam("id") Long id, @RequestParam("left") int left) {
		try {
			return photoDAO.rotate(id, left == 1) == true ? "0" : "1";
		} catch (Exception e) {
			return "1";
		}
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/deletephoto2.html")
	public String deletePhoto2(@RequestParam("id") Long id) {
		photoDAO.delete(id);
		return "redirect:/home";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/cleardb.html")
	public String clearDB() {
		albumDAO.deleteAll();
		return "redirect:/home";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping("/makepublic.html")
	public String makePublic() {
		for(Long id: albumDAO.listAllId()) {
			fileService.makePublic(id);
		}
		return "redirect:/home";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/load.html")
	public String load() {
		fileService.scan(null, false);
		for(Long id: albumDAO.listAllId()) {
			fileService.makePublic(id);
		}
		return "redirect:/home";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/unload.html")
	public String unload() {
		fileService.unload();
		return "redirect:/home";
	}

	@ResponseBody
	@Secured("ROLE_ADMIN")
	@RequestMapping("/scan.html")
	public String scan(@RequestParam(defaultValue="-1", required=false, value="path") String path, @RequestParam(defaultValue="-1", required=false, value="refresh") int refresh) {
		log.info("scan.html: path = "+path);
		path = path.equals("-1") ? null : path;
		int[] s = fileService.scan(path, refresh != -1 ? true : false);
		// return "redirect:/home";
		return "ALBUMS:"+s[0]+", PHOTOS NEW:"+s[1]+", PHOTOS REFRESHED:"+s[2];
	}

	/**
	 * delete i reload (usuwa i przeloadowuje cala zawartosc katalogu)
	 * DODAJE NOWE
	 * @param id
	 * @return
	 */
	@ResponseBody
	@Secured("ROLE_ADMIN")
	@RequestMapping("/reload.html")
	public String reload(@RequestParam Long id) {
		Album a = albumDAO.get(id);
		if(a != null) {
			albumDAO.clear(id);
			String path = a.getPath();
			int[] s = fileService.scan(path, false);
			// return "redirect:/home";
			return "ALBUMS:"+s[0]+", PHOTOS:"+s[1];
		} else {
			return "ALBUMS:0, PHOTOS:0";
		}
	}

	/**
	 * tylko reload, nie usuwa zdjec
	 * NIE dodaje nowych
	 * @param id
	 * @return
	 */
	@ResponseBody
	@Secured("ROLE_ADMIN")
	@RequestMapping("/refresh.html")
	public String refresh(@RequestParam Long id) {
		try {
			Album a = albumDAO.get(id);		
			PhotoBrowseParams bp = new PhotoBrowseParams();
			bp.setVisibility(Visibility.ADMIN);
			bp.setAlbumId(id);
			for(Long idp: photoDAO.findAllId(bp)) {
				photoDAO.synchronize(idp);
			}
			return "0";
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "-1";
	}
	
	@ResponseBody
	@Secured("ROLE_ADMIN")
	@RequestMapping("/refreshone.html")
	public String refreshPhoto(@RequestParam Long id) {
		try {
			photoDAO.synchronize(id);
			return "0";
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "-1";
	}

	
	@Secured("ROLE_ADMIN")
	@ResponseBody
	@RequestMapping("/changevisibility.html")	
	public String visibility(@RequestParam("id") Long id) {
		try {
			Visibility v = photoDAO.nextVisbility(id);
			return v.getName();
		} catch(Exception e) {
			return "1";
		}
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/visibility.html")
	@ResponseBody
	public String publicAll(@RequestParam("id") Long albumId, @RequestParam("v") Visibility visibility, HttpServletRequest request) {
		try {
			PhotoBrowseParams bp = new PhotoBrowseParams();
			bp.setVisibility(Visibility.ADMIN);
			bp.setAlbumId(albumId);
			for(Long id: photoDAO.findAllId(bp)) {
				photoDAO.updateVisibility(id, visibility);
			}
			return "0";
		} catch (Exception e) {
			return "-1";
		}
		// return "redirect:/home";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/palette.html")
	public ModelAndView pallete(@RequestParam Long id) {
		return new ModelAndView("pallete", "id", id);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/relocate")	
	public void reloacte() {
		fileService.reloacateAlbums(false);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/relocatedry")	
	public void reloacteDry() {
		fileService.reloacateAlbums(true);
	}	
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) throws IOException {
		log.error(ex.getMessage(), ex);
		for(GrantedAuthority a: SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			if(a.getAuthority().equals("ROLE_ADMIN")) {
				return new ModelAndView("error", "message", ex.getMessage());
			}
		}
		return new ModelAndView("error");
	}	

}
