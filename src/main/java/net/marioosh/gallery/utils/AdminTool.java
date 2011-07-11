package net.marioosh.gallery.utils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.marioosh.gallery.FileService;
import net.marioosh.gallery.Settings;
import net.marioosh.gallery.model.dao.AlbumDAO;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.model.helpers.AlbumBrowseParams;
import net.marioosh.gallery.model.helpers.PhotoBrowseParams;
import net.marioosh.gallery.model.helpers.Visibility;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * odpalanie win32
 * 		java -cp *; net.marioosh.gallery.utils.AdminTool
 * 		java -cp target\gallery\WEB-INF\lib\*; net.marioosh.gallery.utils.AdminTool
 * 		java -cp target\gallery\WEB-INF\lib\*;c:\jsf\tomcat-6.0.26\lib\*; net.marioosh.gallery.utils.AdminTool
 * 
 * odpalanie linux
 * 		java -cp target/gallery/WEB-INF/lib/*:/opt/tomcat7/lib/* net.marioosh.gallery.utils.AdminTool
 * 
 * @author marioosh
 *
 */
public class AdminTool {

	private Logger log = Logger.getLogger(getClass());
	
	private AlbumDAO albumDAO;
	private PhotoDAO photoDAO;
	private FileService fileService;
	
	@Autowired
	private Settings settings;
	
	/**
	 * application context
	 */
	// private static final FileSystemXmlApplicationContext ac = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/config/applicationContext.xml");
	private static final ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

	static public void main(String[] args) {
		if(args.length > 0) {
			new AdminTool(args);
		} else {
			new AdminTool().syntax();
		}
	}

	public void syntax() {
		String f = "";
		for(Method m : this.getClass().getMethods()) {
			if(m.getName().startsWith("_")) {
				f += m.getName().substring(1)+"; ";
			}
		}
		
		System.out.println("SYNTAX ERROR: wrong function name");
		System.out.println("Available functions: "+ f);
	}
	
	public AdminTool() {}
	
	public AdminTool(String[] args) {

		this.settings = (Settings)ac.getBean("settings");
		this.photoDAO = (PhotoDAO)ac.getBean("photoDAO");
		this.albumDAO = (AlbumDAO)ac.getBean("albumDAO");
		this.fileService = (FileService)ac.getBean("fileService");
		
		try {
			if(args[0].equals("scan") && args.length > 1) {
				String path = args[1];
				scan(path);
			} else {
				Method m = this.getClass().getMethod("_"+args[0]);
				long start = System.currentTimeMillis();
				log.info("START");
				m.invoke(this);
				long stop = System.currentTimeMillis();
				log.info("END "+(stop - start) + "ms");
			}
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			syntax();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void _unload() {
		fileService.unload();
	}
	
	public void _load() {
		int[] s = fileService.scan(null, false, false);
		for(Long id: albumDAO.listAllId()) {
			fileService.makePublic(id);
		}
		log.info("ALBUMS:"+s[0]+", PHOTOS NEW:"+s[1]+", PHOTOS REFRESHED:"+s[2]);
	}
	
	public void _clear() {
		albumDAO.deleteAll();
	}
	
	/**
	 * pelny scan nowych plikow
	 */
	public void _scan() {
		int[] s = fileService.scan(null, true, false);
		log.info("ALBUMS:"+s[0]+", PHOTOS NEW:"+s[1]+", PHOTOS REFRESHED:"+s[2]);
	}
	
	public void scan(String path) {
		String relPath = UndefinedUtils.relativePath(new File(settings.getRootPath()), new File(path));
		log.info(relPath);
		if(relPath == null) {
			log.info("PATH WRONG. SCAN stopped.");
		} else {
			int[] s = fileService.scan(relPath, true, false);
			log.info("ALBUMS:"+s[0]+", PHOTOS NEW:"+s[1]+", PHOTOS REFRESHED:"+s[2]);
		}
	}
	
	public void _scannew() {
		int[] s = fileService.scan(null, true, true);
		log.info("ALBUMS:"+s[0]+", PHOTOS NEW:"+s[1]+", PHOTOS REFRESHED:"+s[2]);		
	}
	
	public void _test() {
		log.info("test");
		AlbumBrowseParams bp = new AlbumBrowseParams();
		PhotoBrowseParams bp2 = new PhotoBrowseParams();
		bp.setVisibility(Visibility.ADMIN);
		bp2.setVisibility(Visibility.ADMIN);
		log.info("albums: " + albumDAO.countAll(bp));
		log.info("photos: " + photoDAO.countAll(bp2));
	}
	
	/**
	 * aktyalizuj hashe wg zawartosci pliku, nie sciezki jak bylo dotychczas
	 */
	public void _updateHashes() {
		photoDAO.updateHashes();
	}
	
	public void _relocatedry() {
		fileService.reloacateAlbums(true);
	}
	
	public void _relocate() {
		fileService.reloacateAlbums(false);
	}
}
