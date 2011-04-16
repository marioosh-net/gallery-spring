package net.marioosh.gallery;

import java.io.IOException;
import java.util.List;
import net.marioosh.gallery.model.dao.AlbumDAO;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.model.entities.Album;
import net.marioosh.gallery.model.helpers.AlbumBrowseParams;
import net.marioosh.gallery.model.helpers.PhotoBrowseParams;
import net.marioosh.gallery.model.helpers.Visibility;
import net.marioosh.gallery.utils.UndefinedUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
	public String index(@RequestParam(value="a", required=false) Long albumId, Model model) {
		if(albumId != null) {
			PhotoBrowseParams bp1 = new PhotoBrowseParams();
			bp1.setAlbumId(albumId);
			model.addAttribute("photos", photoDAO.findAll(bp1));
		}
		AlbumBrowseParams bp = new AlbumBrowseParams(); 
		model.addAttribute("albums", albumDAO.findAll(bp));		
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

}
