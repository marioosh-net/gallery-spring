package net.marioosh.gallery;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
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
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

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
		try {
			Process pr = Runtime.getRuntime().exec(new String[]{settings.getConvertPath(), path,  "-thumbnail", "100x100^", "-gravity", "center", "-extent", "100x100", "-"});
			return IOUtils.toByteArray(pr.getInputStream());
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
		try {
			Process pr = Runtime.getRuntime().exec(new String[]{settings.getConvertPath(), path, "-quality", "80", "-resize", "800x800", "-"});
			return IOUtils.toByteArray(pr.getInputStream());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;		
	}
	
	public boolean rotateInPlace(String path, boolean left) {
		try {
			int degrees = 270;
			if(left) {
				degrees = 90;
			}
			Process pr = Runtime.getRuntime().exec(new String[]{settings.getMogrifyPath(), "-rotate", ""+degrees, path});
			return true;
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return false;
	}

	public byte[] thumb2(String path) {
		int vSize = 100;
		int hSize = 100;
		
		try{
			byte[] picture = IOUtils.toByteArray(new FileInputStream(new File(path)));
			
			if(picture==null)return null;
			
			//wyznaczanie nowych wymiarów obrazka
			
			Point pout=new Point();
			
			double dy;
			double dx;
			
			//skalowanie
			byte[] buf=picture;
			ByteArrayInputStream bais=new ByteArrayInputStream(buf);
			BufferedImage bi;
			
			try {
				bi = ImageIO.read(bais);
			} catch (IOException e) {
				return null;
			}
		
            // NullPointerException !!!
			int orgWidth = bi.getWidth();
			int orgHeight = bi.getHeight();
			
			dx = (double) orgWidth / vSize;
			dy = (double) orgHeight / hSize;
			
			if ( dx < dy ) {
				pout.x=(int)Math.round((double)orgWidth/dx);
				pout.y=(int)Math.round((double)orgHeight/dx);
			}
			else{
				pout.x=(int)Math.round((double)orgWidth/dy);
				pout.y=(int)Math.round((double)orgHeight/dy);
			}
			
			//System.out.println("parametry"+vSize+"/"+hSize);
			//System.out.println("stare wymiary skalowania"+orgWidth+"/"+orgHeight);
			//System.out.println("nowe wymiary skalowania"+pout.x+"/"+pout.y);
			
			
			BufferedImage thumbImage = new BufferedImage(pout.x, pout.y, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			
			graphics2D.drawImage(bi, 0, 0, pout.x, pout.y, null);
			
			
			// obcinanie
			int widthStart =0;
			int heightStart=0;
			
			heightStart = (int) Math.round((double) ( pout.y - hSize ) / 2 -1);
			widthStart =  (int) Math.round((double) ( pout.x - vSize ) / 2 -1);
			
			
			//System.out.println("punkty startu"+widthStart+"/"+heightStart);


			if (heightStart < 0) heightStart = 0;
			if (widthStart 	< 0) widthStart	 = 0;
			
			if (pout.x < vSize) vSize = pout.x;
			if (pout.y < hSize) hSize = pout.y;
			
			//System.out.println("punkty końcowe"+vSize+"/"+hSize);
			

			BufferedImage thumbImageCrop = thumbImage.getSubimage(widthStart,heightStart,vSize,hSize);
	
			
			//System.out.println("docelowe wymiary"+ thumbImageCrop.getWidth()+"/"+thumbImageCrop.getHeight());			
			
			//kompresja
			ByteArrayOutputStream out = new ByteArrayOutputStream();
	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
	        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImageCrop);
	        int quality = 90;
	        quality = Math.max(0, Math.min(quality, 100));
	        param.setQuality((float)quality / 100.0f, false);
	        encoder.setJPEGEncodeParam(param);
	        try {
	        	    encoder.encode(thumbImageCrop);
			} catch (Exception e) {
				throw e;
			}       
	        
	        ByteArrayOutputStream pictureOut=new ByteArrayOutputStream(); 
	        try {
				ImageIO.write(thumbImageCrop, "jpg" , pictureOut);
		        pictureOut.close();
			} catch (IOException e) {
				throw e;
			}
			//zapis
			
			// PhotoUtils photoUtils = (PhotoUtils) Utils.findBean("makeNegative");
			
			return pictureOut.toByteArray();
			
			
		}catch (Exception e){
			log.error(e.getMessage(), e);
			return null; // nie bedzie miniaturki
			// throw new ModelException();
		}
	}

	public byte[] resized2(String path) throws IOException {
		FileInputStream in = new FileInputStream(new File(path));
		BufferedImage original = ImageIO.read(in);
		int xsize = 800;
		int ysize = 800;
		if (original != null) {

			int originalX = original.getWidth();
			int originalY = original.getHeight();
			double scale = 1;
			if (originalX > originalY) { // landscape image
				scale = (double) xsize / (double) originalX;
			} else { // portrait image
				scale = (double) ysize / (double) originalY;
			}
			AffineTransformOp scaleOp = new AffineTransformOp(AffineTransform.getScaleInstance(scale, scale), null);
			BufferedImage resized = scaleOp.filter(original, null);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( resized, "jpg", baos );
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			
			return imageInByte;
		}
		return null;
	}
	
	
	
	
}
