package net.marioosh.gallery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;
import net.marioosh.gallery.model.dao.AlbumDAO;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.model.entities.Album;
import net.marioosh.gallery.model.entities.Photo;
import net.marioosh.gallery.model.helpers.AlbumBrowseParams;
import net.marioosh.gallery.model.helpers.BrowseParams;
import net.marioosh.gallery.model.helpers.Visibility;
import net.marioosh.gallery.utils.UndefinedUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController implements ResourceLoaderAware {

	private Logger log = Logger.getLogger(TestController.class);

	private ResourceLoader resourceLoader;
	
	@Autowired
	private Validator validator;

	@Autowired
	private AlbumDAO albumDAO;
	
	@Autowired
	private PhotoDAO photoDAO;

	public void newAlbum() {
		Album a = new Album();
		a.setModDate(new Date());
		a.setName(UndefinedUtils.randomWords(4));
		//a.setName(UndefinedUtils.nextRandomString());
		albumDAO.add(a);
	}
	
	public Album randomAlbum() {
		List<Album> l = albumDAO.findAll(new AlbumBrowseParams());
		if (l.size() > 0) {
			Album a = l.get((int) (Math.random() * l.size()));
			// log.debug("Random album: "+ (a != null ? a.getName() : "NULL"));
			return a;
		}
		return null;
	}
	
	public boolean newPhoto() {
		Album a = randomAlbum();
		log.debug(a);
		if(a == null) {
			newAlbum();
			a = randomAlbum();
		}
		if(a != null) {
			Photo p = new Photo();
			p.setAlbumId(a.getId());
			p.setModDate(new Date());
			p.setImg(getBytes("images/no_image.jpg"));
			p.setName(UndefinedUtils.nextRandomString());
			photoDAO.add(p);
			return true;
		}
		return false;
	}
	
	public boolean newPhoto(byte[] img, String name) {
		Album a = randomAlbum();
		if(a == null) {
			newAlbum();
			a = randomAlbum();
		}
		if(a != null) {
			Photo p = new Photo();
			Set<Album> s = new HashSet<Album>();
			s.add(a);
			p.setAlbumId(a.getId());
			p.setModDate(new Date());
			p.setImg(img);
			p.setVisibility(Visibility.PUBLIC);
			p.setName(name != null ? name : UndefinedUtils.nextRandomString());
			photoDAO.add(p);
			return true;
		}		
		return false;
	}
	
	public void newPhotosOld(int count) {
		for(int i=0; i< count; i++) {
			newPhoto();
		}
	}
	
	@RequestMapping("/makephotos.html")
	public void newPhotos(@RequestParam(value="count", defaultValue="0", required=false) Integer count) {
		try {
			makePhotos("http://www.impawards.com/2009/std.html", count != 0 ? count : null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}	
	
	/**
	 * wypluj bajty dla lokalnego resourca (katalog biezacy . = webapp) 
	 * @param path
	 * @return
	 */
	public byte[] getBytes(String path) {
		try {
			Resource resource = resourceLoader.getResource(path);
			return IOUtils.toByteArray(resource.getInputStream());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@RequestMapping("/testalbum.html")
	public String testalbum() {
		Album a = new Album(UndefinedUtils.randomWord() + " " + UndefinedUtils.randomWord() + " "+ UndefinedUtils.randomWord());
		a.setVisibility(Visibility.PUBLIC);
		albumDAO.add(a);
		return "redirect:/index.html";
	}

	/**
	 * pomieszaj fotki miedzy albumami
	 * @throws EntityVersionException 
	 */
	@ResponseBody
	@RequestMapping("/shuffle.html")
	public String shuffle() {
		long start = System.currentTimeMillis();
		log.debug("shuffle()...");
		int x = 0;
		for(Long id: photoDAO.listAllId()) {
			Photo p = photoDAO.get(id);
			Album a = randomAlbum();
			p.setAlbumId(a.getId());
			photoDAO.update(p);
			x++;
		}
		log.debug("shuffled "+x+" files.");
		long stop = System.currentTimeMillis();
		log.debug((stop - start) + "ms");
		return "0";
	}
	
	public void clearDB() {
		for(Long id: photoDAO.listAllId()) {
			photoDAO.delete(id);
		}
		for(Long id: albumDAO.listAllId()) {
			albumDAO.delete(id);
		}
	}
	
	public void makePhotos(String address, Integer count) throws Exception {

		URL url = new URL(address);
		URLConnection uc = url.openConnection();
		InputStream is = uc.getInputStream();
		String prefix = address.substring(0, address.lastIndexOf('/') + 1);

		// jericho
		Source source = new Source(is);
		List<Element> divs = source.getAllElements(HTMLElementName.DIV);
		Element content = null;
		for (Element div : divs) {
			String styleClass = div.getAttributeValue("class");
			if (styleClass != null && styleClass.equals("content")) {
				content = div;
				break;
			}
		}

		if (content != null) {
			List<Element> trs = content.getAllElements(HTMLElementName.TR);
			log.debug(trs.size() + " nodes found.");
			Integer i = 0;
			Collections.shuffle(trs); // pomieszaj :)
			for (Element tr : trs) {
				List<Element> list = tr.getAllElements(HTMLElementName.IMG);
				Element tdWithTitle = tr.getFirstElement(HTMLElementName.TD);
				Element font = tdWithTitle.getFirstElement();
				String name = new TextExtractor(font.getContent()).toString();
				// dodaj obrazki do produktu
				for (Element img : list) {
					String imageUrl = prefix + img.getAttributeValue("src");
					imageUrl = imageUrl.replaceFirst("thumbs/imp_", "posters/");
					
					String filename = "";
					URL url1 = new URL(imageUrl);
					int l = url1.getPath().lastIndexOf('/');
					if(l != -1) {
						filename = url1.getPath().substring(l+1); 
					}
					if(filename.equals("")) {
						filename = UndefinedUtils.nextRandomString();
					}
					
					byte[] data = urlToBytes(imageUrl);
					if(newPhoto(data, filename)) {
						log.debug("Image " + imageUrl + " (name: " + filename + ") added. / "+i++);
					}
					break; // tylko jeden z danego filmu
				}
				if (count != null && i >= count) { break;	}
			}
			log.debug(i + " products added.");
		}
		is.close();
		log.debug("END");
	}

	private byte[] urlToBytes(String stringUrl) {
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		InputStream is = null;

		try {
			URL url = new URL(stringUrl);
			is = url.openStream();
			byte[] byteChunk = new byte[4096];
			int n;

			while ((n = is.read(byteChunk)) > 0) {
				bais.write(byteChunk, 0, n);
			}
			is.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bais.toByteArray();
	}

	public void render() {
		log.debug("RENDER");
	}
	
	public long getTime() {
		return new Date().getTime();
	}	
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) throws IOException {
		return new ModelAndView("error", "message", ex.getMessage());
	}	

}
