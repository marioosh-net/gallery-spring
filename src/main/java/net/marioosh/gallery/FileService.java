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
import java.sql.SQLException;
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
import net.marioosh.gallery.utils.UndefinedUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
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

	private ApplicationContext appContext;

	@Autowired
	private AlbumDAO albumDAO;

	@Autowired
	private PhotoDAO photoDAO;

	@Autowired
	private Settings settings;

	@Autowired
	private UtilService utilService;

	@Override
	public void setApplicationContext(ApplicationContext appContext)
			throws BeansException {
		this.appContext = appContext;
	}

	public FileService() {
		log.info(this);
	}

	private int photosCount;
	private int photosUpdated;
	private int albumsCount;

	/**
	 * skanuj ktalog w poszukiwaniu nowych albumow
	 * [utworz albumy w bazie na podstawie systemu plikow na dysku]
	 */
	public int[] scan(String path, boolean refresh) {
		log.info("SCAN start, path: "+path);
		photosUpdated = 0;
		photosCount = 0;
		albumsCount = 0;
		
		File f = null;
		if(path != null) {
			// f = getDir(settings.getRootPath() + File.separatorChar + path);
			f = new File(settings.getRootPath(), path);
			log.info(settings.getRootPath() + File.separatorChar + path);
			if(f == null) {
				log.error("PATH WRONG");
				log.info("SCAN done [photos added: " + photosCount + ", photos updated: " + photosUpdated + ", albums added: " + albumsCount + "]");
				return new int[] {albumsCount, photosCount, photosUpdated};				
			}
		}
		
		// loadOLD(f, refresh);
		long start = System.currentTimeMillis();
		log.info("load("+(f != null ? f.getAbsolutePath() : "null")+")");
		// File root = getDir(settings.getRootPath());
		File root = new File(settings.getRootPath());
		if (root != null) {
			if(f != null) {
				loadFiles(root, f, true, refresh);
			} else {
				loadFiles(root, root, true, refresh);
			}
		} else {
			log.error("ROOT PATH WRONG!");
		}
		long stop = System.currentTimeMillis();
		log.info((stop - start) + "ms");
		
		
		log.info("SCAN done [photos added: " + photosCount + ", photos updated: " + photosUpdated + ", albums added: " + albumsCount + "]");
		return new int[] {albumsCount, photosCount, photosUpdated};
	}
	
	public void unload() {
		long start = System.currentTimeMillis();
		log.info("unload()");
		// File root = getDir(settings.getDestPath());
		File root = new File(settings.getDestPath());
		if(root != null) {
			unloadFiles(root, true);
		} else {
			log.error("DEST PATH WRONG!");
		}
		long stop = System.currentTimeMillis();
		log.info((stop - start) + "ms");
	}

	/**
	 * przetworz pliki z podanego ktalogu - file
	 * w tej chwili nie moze byc wiecej niz jeden album o tej samej nazwie
	 * nazwa albumu jest nazwa katalogu, wiec dla drugiego katalogu o tej samej
	 * nazwie nie zostanie utworzony nowy album
	 * w albumie pliki rozpoznawane sa po hashu (nie moze byc wiecej niz 1 plik
	 * z tym samym hashem w albumie)
	 * 
	 * @param file
	 * @param root
	 * @param createEmptyAlbums
	 * @throws IOException
	 * @throws EntityVersionException
	 */
	private void loadFiles(File root, File file, boolean createEmptyAlbums, boolean reloadExisting) {
		log.info("SCAN \""+ file.getAbsolutePath() +"\", reloadExisting:"+reloadExisting);
		File[] files = file.listFiles();
		
		if(files == null) {
			log.info("PATH WRONG");
			return;
		}
		for (File f : files) {
			// jest katalog, nie ma albumu w bazie i sa pliki wewnatrz
			if (f.isDirectory()) {
				// if (!albumDAO.isAlbumExist(f.getName()) && (f.listFiles().length > 0 || createEmptyAlbums)) {
				if (albumDAO.getByHash(DigestUtils.md5Hex(UndefinedUtils.relativePath(root, f))) == null && (f.listFiles().length > 0 || createEmptyAlbums)) {
					Album a = new Album();
					a.setModDate(new Date());
					a.setName(f.getName());
					a.setPath(UndefinedUtils.relativePath(root, f));
					a.setVisibility(Visibility.USER);
					a.setHash(DigestUtils.md5Hex(a.getPath()));
					Long albumId = albumDAO.add(a);
					log.info("1 Album '" + f.getName() + "' [" + albumId + "] created.");
					albumsCount++;
				}
				// przerob podkatalogi
				loadFiles(root, f, createEmptyAlbums, reloadExisting);
			} else {
				// make fotki
				String contentType = new MimetypesFileTypeMap().getContentType(f);
				if (contentType.equals("image/jpeg") || contentType.equals("image/jpg")) {
					// Album a = albumDAO.getByName(file.getName());
					Album a = albumDAO.getByHash(DigestUtils.md5Hex(UndefinedUtils.relativePath(root, file)));
					Long albumId = 0L;
					String albumHash = null;
					if (a == null) {
						a = new Album();
						a.setModDate(new Date());
						a.setName(file.getName());
						a.setPath(UndefinedUtils.relativePath(root, file));
						a.setVisibility(Visibility.USER);
						a.setHash(DigestUtils.md5Hex(a.getPath()));
						albumId = albumDAO.add(a);
						albumHash = a.getHash();
						log.info("2 Album '" + file.getName() + "' [" + albumId + "] created.");
						albumsCount++;
					} else {
						albumId = a.getId();
						albumHash = a.getHash();
					}

					try {
						FileInputStream in = new FileInputStream(f);
						// hash liczony z pliku (dane pliku sie zmieniaja - hash sie zmienia)
						String hash = DigestUtils.md5Hex(in);
						in.close();
						// hash jest liczony z wzglednej (wg glownego ktalogu) sciezki do pliku
						// String hash = DigestUtils.md5Hex(UndefinedUtils.relativePath(root, f));
						// Long id = photoDAO.getByAlbumAndHash(albumHash, hash);
						
						Long id = photoDAO.getByAlbumAndName(albumId, f.getName());
						
						if (id == null) {
							// nie ma takiego pliku w albumie - tworze nowy
							
							// if (true) {
							Photo p = new Photo();
							p.setHash(hash);
							p.setAlbumId(albumId);
							p.setModDate(new Date());

							// !!!
							// p.setImg(IOUtils.toByteArray(new FileInputStream(f)));
							p.setImg(utilService.resized(f.getAbsolutePath()));
							p.setThumb(utilService.thumb(f.getAbsolutePath()));

							p.setFilePath(UndefinedUtils.relativePath(root, f));

							p.setVisibility(Visibility.USER);
							p.setName(f.getName());
							photoDAO.add(p);
							log.info("Photo '" + f.getName() + "' created in album '" + a.getName() + "' [" + a.getId() + "].");
							photosCount++;
						} else {
							// jest to przeladowuje jesli hash sie zmienil
							if(reloadExisting) {
								Map<String,Object> m = photoDAO.get(id, new String[]{"id","hash"});
								String oldHash = (String) m.get("hash");
								if(!oldHash.equals(hash)) {
									log.info("reload ID: "+ id);
									photoDAO.synchronize(id);
									photosUpdated++;
								} else {
									log.debug("photo \""+f.getName()+"\" doesn't changed ID: "+ id);
								}
							}
							/*
							log.debug("Photo ("+f.getName()+") with hash '"+ hash +"' exist.");
							Photo p = photoDAO.get(id);
							p.setAlbumId(a.getId());
							photoDAO.update(p);
							*/
						}
						
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
				}
			}
		}
	}

	private void unloadFiles(File dest, boolean createEmptyDirectories)
			{
		String basePath = dest.getAbsolutePath();
		AlbumBrowseParams browseParams = new AlbumBrowseParams();
		browseParams.setVisibility(Visibility.ADMIN);
		for (Album a : albumDAO.findAll(browseParams)) {
			File fa = new File(basePath, a.getPath());
			fa.mkdirs();
			log.debug("Create directory: '" + fa.getAbsolutePath() + "'");
			PhotoBrowseParams browseParams1 = new PhotoBrowseParams();
			browseParams1.setVisibility(Visibility.ADMIN);
			browseParams1.setAlbumId(a.getId());
			for (Map<String, Object> m : photoDAO.findAll(browseParams1, new String[] {"id", "name", "file_path"})) {
				File fp = new File(basePath, (String) m.get("file_path"));
				FileOutputStream out;
				try {
					out = new FileOutputStream(fp);
					IOUtils.copy(photoDAO.getStream((Long) m.get("id"), 0), out);
					out.close();
					log.debug("Create image: '" + fp.getAbsolutePath() + "'");
				} catch (FileNotFoundException e) {
					log.error(e.getMessage());
				} catch (IOException e) {
					log.error(e.getMessage());
				} catch (SQLException e) {
					log.error(e.getMessage());
				}
			}
		}
	}

	public void makePublic(Long albumId) {
		log.debug("MAKE PUBLIC");
		// File root = getDir(settings.getRootPath());
		File root = new File(settings.getRootPath());
		
		Album a = albumDAO.get(albumId);
		if (a != null) {
			File f = new File(new File(root, a.getPath()), "pubfiles");

			PhotoBrowseParams browseParams = new PhotoBrowseParams();
			browseParams.setVisibility(Visibility.ADMIN);
			browseParams.setAlbumId(albumId);
			List<Map<String, Object>> l = photoDAO.findAll(browseParams, new String[] {
			"name", "id"
			});

			try {
				FileInputStream fstream = new FileInputStream(f);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				while ((strLine = br.readLine()) != null) {
					for (Map<String, Object> m : l) {
						if (m.get("name") != null && m.get("name").equals(strLine)) {
							photoDAO.updateVisibility((Long) m.get("id"), Visibility.PUBLIC);
							a.setVisibility(Visibility.PUBLIC);
							albumDAO.update(a);
							break;
						}
					}
				}
				//Close the input stream
				in.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}

	/*
	public File getDir(String path) {
		try {
			Resource root = appContext.getResource("file:" + path);
			if (root.getFile() != null && root.getFile().isDirectory()) {
				return root.getFile();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
		return null;
	}
	*/
	
}
