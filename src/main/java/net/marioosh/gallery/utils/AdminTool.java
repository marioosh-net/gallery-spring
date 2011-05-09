package net.marioosh.gallery.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.marioosh.gallery.FileService;
import net.marioosh.gallery.model.dao.AlbumDAO;
import net.marioosh.gallery.model.dao.PhotoDAO;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;

public class AdminTool {

	private Logger log = Logger.getLogger(getClass());
	
	private AlbumDAO albumDAO;
	private PhotoDAO photoDAO;
	private FileService fileService;
	
	/**
	 * application context
	 */
	private static final FileSystemXmlApplicationContext ac = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/config/applicationContext.xml");
	// private static final ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

	static public void main(String[] args) {
		if(args.length > 0) {
			new AdminTool(args[0]);
		} else {
			System.out.println("SYNTAX ERROR: no func name");
		}
	}

	public AdminTool(String func) {
		log.info("START");
		this.photoDAO = (PhotoDAO)ac.getBean("photoDAO");
		this.albumDAO = (AlbumDAO)ac.getBean("albumDAO");
		this.fileService = (FileService)ac.getBean("fileService");
		
		try {
			Method m = this.getClass().getMethod(func, Void.class);
			m.invoke(this, null);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		// fileService.unload();
		/*
		albumDAO.deleteAll();
		fileService.load();
		for(Long id: albumDAO.listAllId()) {
			fileService.makePublic(id);
		}
		*/
		log.info("END");
	}
	
	public void unload() {
		fileService.unload();
	}
	
	public void load() {
		fileService.load(null, false);
		for(Long id: albumDAO.listAllId()) {
			fileService.makePublic(id);
		}
	}
	
	public void clear() {
		albumDAO.deleteAll();
	}
}
