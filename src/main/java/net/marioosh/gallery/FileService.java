package net.marioosh.gallery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service("fileService")
public class FileService implements Serializable, ApplicationContextAware {
	
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger(getClass());

	/**
	 * zrodlo obrazkow wczytywanych do bazy
	 */
	private String rootPath;
	
	/**
	 * docelowy katalog dla zrzutu obrazkow z bazy
	 */
	private String destPath;

	private ApplicationContext appContext;
	
	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private PhotoDAO photoDAO;
		
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	
	public void setDestPath(String destPath) {
		this.destPath = destPath;
	}

	@Override
	public void setApplicationContext(ApplicationContext appContext)
			throws BeansException {
		this.appContext = appContext;
	}

	public FileService() {
		log.info(this);		
	}

	/**
	 * utworz albumy w bazie na podstawie systemu plikow na dysku
	 * 
	 * @param rootPath
	 */
	public void load() {
		long start = System.currentTimeMillis();
		log.info("load()");
		Resource root = appContext.getResource("file:"+rootPath);
		try {
			if(root.getFile() != null && root.getFile().isDirectory()) {
				loadFiles(root.getFile(), true);
			} else {
				log.error("ROOT PATH WRONG!");
			}
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		long stop = System.currentTimeMillis();
		log.info((stop - start) + "ms");		
	}
	
	public void unload() {
		log.info("unload()");
		Resource root = appContext.getResource("file:" + destPath);
		try {
			if(root.getFile() != null && root.getFile().isDirectory()) {
				unloadFiles(root.getFile(), true);
			} else {
				log.error("DEST PATH WRONG!");
			}
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}

	/**
	 * przetworz pliki z podanego ktalogu
	 * w tej chwili nie moze byc wiecej niz jeden album o tej samej nazwie
	 * nazwa albumu jest nazwa katalogu, wiec dla drugiego katalogu o tej samej nazwie nie zostanie utworzony nowy album
	 * w albumie pliki rozpoznawane sa po hashu (nie moze byc wiecej niz 1 plik z tym samym hashem w albumie)
	 * 
	 * @param file
	 * @param createEmptyAlbums
	 * @throws IOException
	 * @throws EntityVersionException 
	 */
	private void loadFiles(File file, boolean createEmptyAlbums) throws IOException {
		File[] files = file.listFiles();
		for(File f: files) {
			// jest katalog, nie ma albumu w bazie i sa pliki wewnatrz
			if(f.isDirectory()) {
				if(!albumDAO.isAlbumExist(f.getName()) && (f.listFiles().length > 0 || createEmptyAlbums)) {
					Album a = new Album();
					a.setModDate(new Date());
					a.setName(f.getName());		
					a.setPath(f.getAbsolutePath());
					a.setVisibility(Visibility.ADMIN);
					albumDAO.add(a);
					log.info("1 Album '" + f.getName() + "' created.");
				}
				// przerob podkatalogi
				loadFiles(f, createEmptyAlbums);
			} else {
				// make fotki
				String contentType = new MimetypesFileTypeMap().getContentType(f);
				if(contentType.equals("image/jpeg") || contentType.equals("image/jpg")) {
					Album a = albumDAO.getByName(file.getName());
					if(a == null) {
						a = new Album();
						a.setModDate(new Date());
						a.setName(file.getName());		
						a.setPath(file.getAbsolutePath());
						a.setVisibility(Visibility.ADMIN);
						albumDAO.add(a);
						log.info("2 Album " + file.getName() + " created.");					
					}
					FileInputStream in = new FileInputStream(f);
					String hash = DigestUtils.md5Hex(in);
					// Photo p1 = photoDAO.getByHash(hash);
					//if(p1 == null) {
					if(true) {
						Photo p = new Photo();
						// p.setHash(hash);
						p.setAlbumId(a.getId());
						p.setModDate(new Date());

						// !!!
						p.setImg(f);
						p.setFilePath(f);
						
						p.setVisibility(Visibility.ADMIN);
						p.setName(f.getName());						
						photoDAO.add(p);				
						log.info("Photo '" + f.getName() + "' created in album '" + a.getName() + "'.");
					/*} else {
						log.debug("Photo ("+f.getName()+") with hash '"+ hash +"' exist.");
						p1.setAlbumId(a.getId());
						photoDAO.update(p1);*/
					}
					in.close();
				}
			}
		}		
	}

	private void unloadFiles(File file, boolean createEmptyDirectories) throws IOException {
		String basePath = file.getAbsolutePath();
		AlbumBrowseParams browseParams = new AlbumBrowseParams();
		browseParams.setVisibility(Visibility.ADMIN);
		for(Album a: albumDAO.findAll(browseParams)) {
			File fa = new File(basePath, a.getName());
			fa.mkdirs();
			log.debug("Create directory: '" + fa.getAbsolutePath() + "'");
			PhotoBrowseParams browseParams1 = new PhotoBrowseParams();
			browseParams1.setAlbumId(a.getId());
			for(Photo p: photoDAO.findAll(browseParams1)) {
				File fp = new File(new File(basePath, a.getName()).getPath(), p.getName());
				FileOutputStream out = new FileOutputStream(fp);
				out.write(p.getImg());
				out.close();
				log.debug("Create image: '" + fp.getAbsolutePath() + "'");
			}
		}
	}
	
}
