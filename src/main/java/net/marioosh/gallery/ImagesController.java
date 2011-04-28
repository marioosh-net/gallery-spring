package net.marioosh.gallery;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

/**
 * wypluwa obrazki
 * @author marioosh
 * 
 * check duplicate hashes
 * select t1.id,t1.hash,t2.id,t2.hash from tphoto t1, tphoto t2 where t1.hash = t2.hash and t1.id <> t2.id;
 *
 */
@Controller
public class ImagesController implements ServletContextAware {

	private Logger log = Logger.getLogger(ImagesController.class);

	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private PhotoDAO photoDAO;

	@Autowired
	private UtilService utilService;
	
	@Autowired
	private Settings settings;
	
	private ServletContext servletContext;

	/**
	 * pociagnij obrazek by hash, BEZ sprawdzania zalogowanego usera
	 * 
	 * @param hash
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("p2.html")
	public void photo(@RequestParam(value="hash", required=false, defaultValue="-1") String hash, HttpServletResponse response) throws IOException {

		response.setContentType("image/jpeg");
		try {
			Long id = photoDAO.getByHash(hash.substring(0,32));
			if(id != null && hash.substring(32).equals(utilService.getHash2())) {
				// file system
				Map<String, Object> m = photoDAO.get(id, new String[]{"file_path"});
				log.debug(m.get("file_path"));
				
				if(m.get("file_path") != null) {
					File fullpath = new File(settings.getRootPath(), (String)m.get("file_path"));
					String normalFullPath = fullpath.getAbsolutePath();
					String bigFullPath = getBigPhotoPath(normalFullPath);
					log.debug("BIG:"+bigFullPath);
					
					FileSystemResource resource = new FileSystemResource(bigFullPath);
					IOUtils.copy(resource.getInputStream(), response.getOutputStream());			
				} else {
					throw new FileNotFoundException();
				}
				return;
			}
			throw new FileNotFoundException();
		} catch (Exception e) {
			InputStream in = servletContext.getResourceAsStream("/images/no_image.jpg");
			IOUtils.copy(in, response.getOutputStream());
		}
	}
	
	/**
	 * pociagnij obrazek by id, ZE sprawdzaniem zalogowanego usera
	 * 
	 * @param id
	 * @param type
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("p.html")
	public void photo(@RequestParam(value="id", required=false, defaultValue="-1") Long id, @RequestParam(value="type", required=false, defaultValue="-1") int type, HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		try {
			if(type == 0) {
				// photo
				IOUtils.copy(photoDAO.getStream(id, type), response.getOutputStream());
				return;
			}
			if(type == 1) {
				// thumb
				IOUtils.copy(photoDAO.getStream(id, type), response.getOutputStream());
				return;
			}
			if(type == 2) {
				// cover
				IOUtils.copy(photoDAO.getStream(albumDAO.getCover(id), 1), response.getOutputStream());
				return;
			}
			if(type == 3 || true) {
				// file system
				Map<String, Object> m = photoDAO.get(id, new String[]{"file_path", "visibility"});
				log.debug(m.get("file_path"));
				log.debug(m.get("visibility"));
				log.debug(utilService.getCurrentVisibility().ordinal());
				
				if(m.get("file_path") != null && m.get("visibility") != null && (Integer)m.get("visibility") <= utilService.getCurrentVisibility().ordinal()) {
					File fullpath = new File(settings.getRootPath(), (String)m.get("file_path"));
					String normalFullPath = fullpath.getAbsolutePath();
					String bigFullPath = getBigPhotoPath(normalFullPath);
					log.debug("BIG:"+bigFullPath);
					
					FileSystemResource resource = new FileSystemResource(bigFullPath);
					IOUtils.copy(resource.getInputStream(), response.getOutputStream());			
				} else {
					throw new FileNotFoundException();
				}
				return;
			}
			throw new FileNotFoundException();
		} catch (Exception e) {
			InputStream in = servletContext.getResourceAsStream("/images/no_image.jpg");
			IOUtils.copy(in, response.getOutputStream());
		}
	}

	/**
	 * pull
	 */
	@RequestMapping(value="/picnik2.html", method=RequestMethod.GET)
	public void picnik(@RequestParam Long id, @RequestParam("file") String file, HttpServletResponse response) throws IOException {
		log.info("PICNIK-GET: " + id);
		log.info("PICNIK-GET: " + file);
	
		URL url = new URL(file);
		URLConnection uc = url.openConnection();
		InputStream is = uc.getInputStream();
		
		IOUtils.copy(is, response.getOutputStream());
	}
	
	/**
	 * push
	 * @param id
	 * @param file
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/picnik.html", method=RequestMethod.POST)
	public void picnik(@RequestParam Long id, @RequestParam("file") byte[] file, HttpServletResponse response) throws IOException {
		log.info("PICNIK-GET: " + id);
		log.info("PICNIK-GET: " + file.length);
		response.setContentType("image/jpeg");
		response.getOutputStream().write(file); // wyswietla zmieniony obrazek
	}
	
	@ExceptionHandler(Exception.class)
	public void handleException(Exception ex, HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		InputStream in = servletContext.getResourceAsStream("/images/no_image.jpg");
		IOUtils.copy(in, response.getOutputStream());
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
	}	
	
	/**
	 * daj mi sciezke do lokalnego pliku duzego obrazka
	 * @param smallPhotoPath
	 * @return
	 */
	private String getBigPhotoPath(String smallPhotoPath) {
		// TODO
		// ...
		// return smallPhotoPath.replaceFirst("/photos_pub/", "/photos/").replaceFirst("_large", "");
		return smallPhotoPath;
		// return smallPhotoPath;
	}
	
	
	
}
