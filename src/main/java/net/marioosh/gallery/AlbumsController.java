package net.marioosh.gallery;

import java.io.IOException;
import java.util.List;
import net.marioosh.gallery.model.dao.AlbumDAO;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.model.entities.Album;
import net.marioosh.gallery.model.helpers.AlbumBrowseParams;
import net.marioosh.gallery.model.helpers.Visibility;
import net.marioosh.gallery.utils.UndefinedUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AlbumsController {

	private Logger log = Logger.getLogger(AlbumsController.class);

	@Autowired
	private Validator validator;

	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private PhotoDAO photoDAO;
		
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) throws IOException {
		return new ModelAndView("error", "message", ex.getMessage());
	}	
		
}
