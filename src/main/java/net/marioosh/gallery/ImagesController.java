package net.marioosh.gallery;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	public void photo(@RequestParam("id") Long id, @RequestParam("type") int type, HttpServletResponse response) throws IOException {
		if(type == 0) {
			// photo
			Photo p = photoDAO.get(id);
			InputStream in = new ByteArrayInputStream(p.getImg());
			outImage(response, in);
			//return ImageIO.read(in);
		}
		if(type == 1) {
			// thumb
			Photo p = photoDAO.get(id);
			InputStream in = new ByteArrayInputStream(p.getThumb());
			outImage(response, in);
			// return ImageIO.read(in);
		}
		if(type == 2) {
			// cover
			Long idp = albumDAO.getCover(id);
			Photo p = photoDAO.get(idp);
			InputStream in = new ByteArrayInputStream(p.getThumb());
			outImage(response, in);			
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
