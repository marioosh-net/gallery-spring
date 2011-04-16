package net.marioosh.gallery;

import net.marioosh.gallery.model.dao.AlbumDAO;
import net.marioosh.gallery.model.dao.PhotoDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;

@Controller
public class PhotosController {

	private Logger log = Logger.getLogger(PhotosController.class);

	@Autowired
	private Validator validator;

	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private PhotoDAO photoDAO;
	
}
