package net.marioosh.gallery;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.activation.MimetypesFileTypeMap;
import net.marioosh.gallery.model.dao.AlbumDAO;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.model.entities.Album;
import net.marioosh.gallery.model.entities.Photo;
import net.marioosh.gallery.model.helpers.AlbumBrowseParams;
import net.marioosh.gallery.model.helpers.PhotoBrowseParams;
import net.marioosh.gallery.model.helpers.Visibility;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("utilService")
public class UtilService implements Serializable, ApplicationContextAware {

	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger(getClass());

	private ApplicationContext appContext;

	@Autowired
	private AlbumDAO albumDAO;

	@Autowired
	private PhotoDAO photoDAO;

	@Autowired
	private Settings settings;

	@Override
	public void setApplicationContext(ApplicationContext appContext)
			throws BeansException {
		this.appContext = appContext;
	}

	public UtilService() {
		log.info(this);
	}

	public Visibility getCurrentVisibility() {
		for(GrantedAuthority a: SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			if(a.getAuthority().equals("ROLE_ADMIN")) {
				return Visibility.ADMIN;
			}
			if(a.getAuthority().equals("ROLE_USER")) {
				return Visibility.USER;
			}
		}
		return Visibility.PUBLIC;
	}
	
	/**
	 * zwraca miniaturke w postaci byte[]
	 * dla pliku podanego przez sciezke w systemie plikow
	 * 
	 * @param path sciezka absolutna w systemie plikow
	 * @return miniaturka w postaci byte[]
	 */
	public byte[] thumb(String path) {
		//String command = "c:\\moje\\progs\\ImageMagick-6.6.7-5\\convert.exe \""+ path + "\" -thumbnail x200 -resize '200x<' -resize 50% -gravity center -crop 100x100+0+0 +repage -format jpg -quality 91 -";
		String command = settings.getThumbCommand().replace("{INPUT}", "\""+path+"\"").replace("{OUTPUT}", "-");
		log.debug(command);
		Process pr;
		try {
			pr = Runtime.getRuntime().exec(command);
			return IOUtils.toByteArray(IOUtils.toBufferedInputStream(pr.getInputStream()));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;		
	}
	
	/**
	 * obrazek pomniejszony (web friendly)
	 * 
	 * @param path
	 * @return
	 */
	public byte[] resized(String path) {
		String command = settings.getResizedCommand().replace("{INPUT}", "\""+path+"\"").replace("{OUTPUT}", "-");
		log.debug(command);
		Process pr;
		try {
			pr = Runtime.getRuntime().exec(command);
			return IOUtils.toByteArray(IOUtils.toBufferedInputStream(pr.getInputStream()));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;		
	}
	
}
