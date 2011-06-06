package net.marioosh.gallery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.MagickInfo;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.utils.UndefinedUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.ResourceBundleEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.ModelAndView;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifReader;

/**
 * edytor messages'ow
 * @author marioosh
 *
 */
@Controller
public class BundlesEditorController {

	private Logger log = Logger.getLogger(BundlesEditorController.class);

	@Autowired
	private Settings settings;

	@Autowired
	private ServletContext servletContext;

	@ModelAttribute("context")
	public String context(Model model) throws IOException {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		ServletContextResource messagespl = new ServletContextResource(servletContext, "/WEB-INF/messages/messages_pl.properties");
		ServletContextResource messagesen = new ServletContextResource(servletContext, "/WEB-INF/messages/messages_en.properties");
		model.addAttribute("messagespl", UndefinedUtils.convertStreamToString(messagespl.getInputStream()));
		model.addAttribute("messagesen", UndefinedUtils.convertStreamToString(messagesen.getInputStream()));
		model.addAttribute("plpath", messagespl.getFile().getAbsolutePath());
		model.addAttribute("enpath", messagesen.getFile().getAbsolutePath());
		return request.getContextPath();
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/messages", method=RequestMethod.GET)
	public String messages_pl(Model model, HttpServletResponse response)
			throws IOException {
		return "messages";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/save-messages", method=RequestMethod.POST)
	public String save(@RequestParam("savedlang") String lang, @RequestParam String text, Model model) throws IOException {
		log.debug("save");
		log.debug(text);
		ServletContextResource messages = null;
		if(lang.equals("pl")) {
			messages = new ServletContextResource(servletContext, "/WEB-INF/messages/messages_pl.properties");
		}
		if(lang.equals("en")) {
			messages = new ServletContextResource(servletContext, "/WEB-INF/messages/messages_en.properties");
		}
		File f = messages.getFile();
		FileWriter wr = new FileWriter(f);
		wr.write(text);
		wr.close();

		if(lang.equals("pl")) {
			model.addAttribute("messagespl", UndefinedUtils.convertStreamToString(messages.getInputStream()));
		}
		if(lang.equals("en")) {
			model.addAttribute("messagesen", UndefinedUtils.convertStreamToString(messages.getInputStream()));
		}
		
		return "messages";
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) throws IOException {
		for (GrantedAuthority a : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			if (a.getAuthority().equals("ROLE_ADMIN")) {
				return new ModelAndView("error", "message", ex.getMessage());
			}
		}
		return new ModelAndView("error");
	}

}
