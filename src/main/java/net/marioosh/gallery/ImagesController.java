package net.marioosh.gallery;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import net.marioosh.gallery.model.dao.AlbumDAO;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.model.entities.Album;
import net.marioosh.gallery.model.entities.Photo;
import net.marioosh.gallery.model.helpers.AlbumBrowseParams;
import net.marioosh.gallery.model.helpers.PhotoBrowseParams;
import net.marioosh.gallery.model.helpers.Visibility;
import net.marioosh.gallery.utils.UndefinedUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

/**
 * wypluwa obrazki
 * @author marioosh
 *
 */
@Controller
public class ImagesController implements ServletContextAware {

	private Logger log = Logger.getLogger(ImagesController.class);

	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private PhotoDAO photoDAO;

	private ServletContext servletContext;

	//@ResponseBody
	@RequestMapping("p.html")
	public void photo(@RequestParam("id") Long id, @RequestParam("type") int type, HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		try {
		if(type == 0) {
			// photo
			IOUtils.copy(photoDAO.getStream(id, type), response.getOutputStream());
		}
		if(type == 1) {
			// thumb
			IOUtils.copy(photoDAO.getStream(id, type), response.getOutputStream());
		}
		if(type == 2) {
			// cover
			IOUtils.copy(photoDAO.getStream(albumDAO.getCover(id), 1), response.getOutputStream());
		}
		} catch (Exception e) {
			InputStream in = servletContext.getResourceAsStream("/images/no_image.jpg");
			IOUtils.copy(in, response.getOutputStream());
		}
	}

	/*
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) throws IOException {
		return new ModelAndView("error", "message", ex.getMessage());
	}
	*/

	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
	}	
	
}
