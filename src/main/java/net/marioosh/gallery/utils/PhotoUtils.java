package net.marioosh.gallery.utils;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class PhotoUtils {
	
	static private Logger log = Logger.getLogger(PhotoUtils.class);
	
	/**
	 * Tworzy  miniaturkę dla skanu
	 * Obsługuje formaty jpg, gif, bmp, tif, png - sprawdzone, może jeszcze jakieś
	 * @param byte[] obrazek, szerokość int, wysokość int
	 * 
	 * @return Boolean czy się powidło
	 * na podstawie metody Przemka
	 * 
	 * 
	 */
	public static byte[] makeThumbCroop(byte[] picture, int vSize, int hSize) {
		try{
			
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

	public static byte[] makeResized(FileInputStream in) throws IOException {
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
