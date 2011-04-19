package net.marioosh.gallery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.marioosh.gallery.model.dao.AlbumDAO;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.model.entities.Album;
import net.marioosh.gallery.model.helpers.AlbumBrowseParams;
import net.marioosh.gallery.model.helpers.PhotoBrowseParams;
import net.marioosh.gallery.model.helpers.Range;
import net.marioosh.gallery.model.helpers.Visibility;
import net.marioosh.gallery.utils.UndefinedUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	private Settings settings;

	/*
	@ModelAttribute("albums")
	public List<Album> getAlbums() {
		AlbumBrowseParams bp = new AlbumBrowseParams();		
		return albumDAO.findAll(bp);
	}
	*/
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/index.html", method=RequestMethod.POST)
	public String saveAlbum(@ModelAttribute("album") Album album, HttpServletRequest request) {
		log.debug(album);
		album.setModDate(new Date());
		albumDAO.update(album);
		return "redirect:/index.html?"+request.getQueryString();
	}
	
	@RequestMapping(value="/index.html", method=RequestMethod.GET)
	public String index(@RequestParam(value="a", required=false, defaultValue="0") Long albumId, @RequestParam(value="p",required=false, defaultValue="1") int p, @RequestParam(value="pp",required=false, defaultValue="1") int pp, Model model) {
		PhotoBrowseParams bp1 = new PhotoBrowseParams();
		AlbumBrowseParams bp = new AlbumBrowseParams();

		bp1.setVisibility(Visibility.PUBLIC);
		bp.setVisibility(Visibility.PUBLIC);
		for(GrantedAuthority a: SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			if(a.getAuthority().equals("ROLE_ADMIN")) {
				bp1.setVisibility(Visibility.ADMIN);
				bp.setVisibility(Visibility.ADMIN);
			}
			if(a.getAuthority().equals("ROLE_USER")) {
				bp1.setVisibility(Visibility.USER);
				bp.setVisibility(Visibility.USER);
			}
		}
		
		if(albumId != 0) {
			if(pp < 0) {
				pp = 1;
			}			
			bp1.setAlbumId(albumId);		
			bp1.setRange(new Range((pp-1)*26,26));
			int pcount = photoDAO.countAll(bp1);
			List<Map<String, Object>> l = photoDAO.findAll(bp1, new String[]{"id","visibility","name"});
			log.debug("LIST: "+l);
			// photoDAO.findAllId(bp1)
			model.addAttribute("photos", l);
			model.addAttribute("album", albumDAO.get(albumId));
			model.addAttribute("aid", albumId);
			model.addAttribute("pcount",pcount);
			int[][] ppages = pages(pcount, 26);
			model.addAttribute("ppages", ppages);
			model.addAttribute("ppagesCount", ppages.length);
			model.addAttribute("ppage", pp);
			
			// getnij wszystkie z albumu
			if(l.size() > 0) {
			bp1.setRange(null);
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
				if(s && ++j > 26) {
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
		 
		if(p < 0) {
			p = 1;
		}		
		bp.setRange(new Range((p-1)*19,19));
		int acount = albumDAO.countAll(bp);
		model.addAttribute("albums", albumDAO.findAll(bp));
		model.addAttribute("acount",acount);
		int[][] apages = pages(acount, 19);
		model.addAttribute("apages", apages);
		model.addAttribute("apagesCount", apages.length);
		model.addAttribute("apage", p);
		return "index";
	}
	
	@RequestMapping("/albums.html")
	public ModelAndView albums() {
		AlbumBrowseParams bp = new AlbumBrowseParams(); 
		return new ModelAndView("albums", "albums", albumDAO.findAll(bp));
	}
	
	@RequestMapping("/covers.html")
	public ModelAndView covers() {
		AlbumBrowseParams bp = new AlbumBrowseParams(); 
		return new ModelAndView("covers", "albums", albumDAO.findAll(bp));
	}

	/*@ResponseBody*/
	@RequestMapping("/testalbum.html")
	public String testalbum() {
		Album a = new Album(UndefinedUtils.randomWord() + " " + UndefinedUtils.randomWord() + " "+ UndefinedUtils.randomWord());
		a.setVisibility(Visibility.PUBLIC);
		albumDAO.add(a);
		return "redirect:/index.html";
	}
	
	/*@ResponseBody*/
	@Secured("ROLE_ADMIN")
	@RequestMapping("/deletealbum.html")
	public String deleteAlbum(@RequestParam("id") Long id) {
		albumDAO.delete(id);
		return "redirect:/index.html";
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
	@RequestMapping("/deletephoto2.html")
	public String deletePhoto2(@RequestParam("id") Long id) {
		photoDAO.delete(id);
		return "redirect:/index.html";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/cleardb.html")
	public String clearDB() {
		albumDAO.deleteAll();
		return "redirect:/index.html";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/load.html")
	public String load() {
		fileService.load();
		return "redirect:/index.html";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/unload.html")
	public String unload() {
		fileService.unload();
		return "redirect:/index.html";
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
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) throws IOException {
		for(GrantedAuthority a: SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			if(a.getAuthority().equals("ROLE_ADMIN")) {
				return new ModelAndView("error", "message", ex.getMessage());
			}
		}
		return new ModelAndView("error");
	}	

	private int[][] pages(int count, int perPage) {
		int pages = count / perPage + (count % perPage == 0 ? 0 : 1);
		int[][] p = new int[pages][3];
		for(int i = 0; i < p.length; i++) {
			p[i][0] = i + 1; 
			p[i][1] = i * perPage; 
			p[i][2] = ((i+1) * perPage) - 1;
		}
		return p;
	}

}
