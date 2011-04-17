package net.marioosh.gallery;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import javax.imageio.ImageIO;
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
import org.springframework.web.servlet.ModelAndView;

/**
 * wypluwa obrazki
 * @author marioosh
 *
 */
@Controller
public class ImagesController {

	private Logger log = Logger.getLogger(ImagesController.class);

	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private PhotoDAO photoDAO;

	//@ResponseBody
	@RequestMapping("p.html")
	public void photo(@RequestParam("id") Long id, @RequestParam("type") int type, HttpServletResponse response) throws IOException, SQLException {
		if(type == 0) {
			// photo
			//outImage(response, photoDAO.getStream(id, type));
			IOUtils.copy(photoDAO.getStream(id, type), response.getOutputStream());
		}
		if(type == 1) {
			// thumb
			//outImage(response, photoDAO.getStream(id, type));
			IOUtils.copy(photoDAO.getStream(id, type), response.getOutputStream());
		}
		if(type == 2) {
			// cover
			Long idp = albumDAO.getCover(id);
			// outImage(response, photoDAO.getStream(idp, type));			
			IOUtils.copy(photoDAO.getStream(idp, 1), response.getOutputStream());
		}
	}

	private void outImage(HttpServletResponse response, InputStream in) throws IOException {
		OutputStream out = response.getOutputStream();
		response.setContentType("image/jpeg");
		byte[] b = new byte[8024];
		int length = 0;
		while ((length = in.read(b)) > 0) {
			out.write(b, 0, length);
		}
		out.close();
		response.getOutputStream().flush();		
	}
	
}
