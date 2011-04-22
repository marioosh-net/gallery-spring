package net.marioosh.gallery;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import javax.servlet.ServletContext;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.MagickInfo;
import net.marioosh.gallery.model.dao.PhotoDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifReader;

@Controller
public class ExifController implements ServletContextAware {

	private Logger log = Logger.getLogger(MainController.class);

	final String cmd = "exiftool -EXIF:MeteringMode -EXIF:Flash -EXIF:ExposureMode -EXIF:WhiteBalance -EXIF:LightSource -EXIF:Model -EXIF:Make -EXIF:ModifyDate -EXIF:ExposureTime -EXIF:FNumber -EXIF:ExposureProgram -EXIF:ISO -EXIF:DateTimeOriginal -EXIF:CreateDate -EXIF:ExposureCompensation -EXIF:FocalLength -EXIF:SubSecTime -EXIF:SubSecTimeOriginal -EXIF:SubSecTimeDigitized -EXIF:FocalLengthIn35mmFormat -XMP-dc:title -XMP-iptcExt:PersonInImage -XMP-iptcExt:Event -XMP-xmp:Rating -EXIF:GPSLatitudeRef -EXIF:GPSLatitude -EXIF:GPSLongitudeRef -EXIF:GPSLongitude -EXIF:GPSAltitudeRef -EXIF:GPSAltitude -File:FileSize -File:FileName -File:ImageWidth -File:ImageHeight -File:FileType -File:MIMEType -ICC_Profile:ProfileDescription -EXIF:ColorSpace -EXIF:InteropIndex -g -j -struct -c \"%s\" -fast";

	@Autowired
	private PhotoDAO photoDAO;

	private ServletContext servletContext;

	@ResponseBody
	@RequestMapping("/exif.html")
	public String exif(@RequestParam("id") Long id) {
		String path = photoDAO.get(id).getFilePath();
		StringBuilder sb = new StringBuilder();
		try {
			ExifReader reader = new ExifReader(new File(path));
			Metadata meta = reader.extract();
			Iterator<Directory> i = meta.getDirectoryIterator();
			int j = 0;
			while (i.hasNext()) {
				Directory d = i.next();
				Iterator tags = d.getTagIterator();
				while (tags.hasNext()) {
					Tag tag = (Tag) tags.next();
					log.debug(tag.getTagName() + ":"+ tag.getDescription());
					if(tag.getTagName().equals("Model") 
							|| tag.getTagName().equals("Date/Time")
							|| tag.getTagName().equals("Make")
							|| tag.getTagName().equals("F-Number")
							|| tag.getTagName().equals("ISO Speed Ratings")
							|| tag.getTagName().equals("Shutter Speed Value")
							|| tag.getTagName().equals("Aperture Value")
							|| tag.getTagName().equals("Flash")
							|| tag.getTagName().equals("Exposure Time")
							|| tag.getTagName().equals("Exposure Program")
							|| tag.getTagName().equals("Metering Mode")
							|| tag.getTagName().equals("White Balance")
							|| tag.getTagName().equals("Date/Time")
							|| tag.getTagName().equals("Picture Mode")
							|| tag.getTagName().equals("Focus Mode")
							|| tag.getTagName().equals("Focal Length")) {
						
						
					
						if(j++ == 0) {
							sb.append("<div class=\"exif-header\">EXIF</div>");
						}
						sb.append("<div class=\"exif-name\">"+tag.getTagName() + "</div><div class=\"exif-value\">"+ tag.getDescription()+"</div><div class=\"clear\"></div>");
					}
					
				}
			}
			// ImageInfo info = new ImageInfo(path);

			/*
			String line;
			Process p = Runtime.getRuntime().exec(cmd + " \"" + photoDAO.get(id).getFilePath() + "\"");
			log.debug(p);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				log.debug(line);
				sb.append(line);
			}
			input.close();
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);*/
		} catch (JpegProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
	}

}
