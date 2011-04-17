package net.marioosh.gallery;

import java.io.IOException;
import java.util.List;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
	private PhotoDAO photoDAO;

	/*
	@ModelAttribute("albums")
	public List<Album> getAlbums() {
		AlbumBrowseParams bp = new AlbumBrowseParams();		
		return albumDAO.findAll(bp);
	}
	*/
	
	@RequestMapping("/index.html")
	public String index(@RequestParam(value="a", required=false, defaultValue="0") Long albumId, @RequestParam(value="p",required=false, defaultValue="-1") int p, @RequestParam(value="pp",required=false, defaultValue="-1") int pp, Model model) {
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
			if(pp == -1) {
				pp = 1;
			}			
			bp1.setAlbumId(albumId);		
			bp1.setRange(new Range((pp-1)*19,19));
			int pcount = albumDAO.countAll(bp1);
			model.addAttribute("photos", photoDAO.findAllId(bp1));
			model.addAttribute("album", albumDAO.get(albumId));
			model.addAttribute("aid", albumId);
			model.addAttribute("pcount",pcount);
			int[][] ppages = pages(pcount, 19);
			model.addAttribute("ppages", ppages);
			model.addAttribute("ppagesCount", ppages.length);
			model.addAttribute("ppage", pp);
		} else {
			model.addAttribute("aid", 0);
			pp = 1;
			model.addAttribute("ppage", pp);
		}
		 
		if(p == -1) {
			p = 1;
		}		
		bp.setRange(new Range((p-1)*20,20));
		int acount = albumDAO.countAll(bp);
		model.addAttribute("albums", albumDAO.findAll(bp));
		model.addAttribute("acount",acount);
		int[][] apages = pages(acount, 20);
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
	@RequestMapping("/deletealbum.html")
	public String deleteAlbum(@RequestParam("id") Long id) {
		albumDAO.delete(id);
		return "redirect:/index.html";
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) throws IOException {
		return new ModelAndView("error", "message", ex.getMessage());
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
