package net.marioosh.gallery;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import net.marioosh.gallery.model.dao.PhotoDAO;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifReader;

@Controller
public class ExifController {

	private Logger log = Logger.getLogger(ExifController.class);

	final String cmd = "exiftool -EXIF:MeteringMode -EXIF:Flash -EXIF:ExposureMode -EXIF:WhiteBalance -EXIF:LightSource -EXIF:Model -EXIF:Make -EXIF:ModifyDate -EXIF:ExposureTime -EXIF:FNumber -EXIF:ExposureProgram -EXIF:ISO -EXIF:DateTimeOriginal -EXIF:CreateDate -EXIF:ExposureCompensation -EXIF:FocalLength -EXIF:SubSecTime -EXIF:SubSecTimeOriginal -EXIF:SubSecTimeDigitized -EXIF:FocalLengthIn35mmFormat -XMP-dc:title -XMP-iptcExt:PersonInImage -XMP-iptcExt:Event -XMP-xmp:Rating -EXIF:GPSLatitudeRef -EXIF:GPSLatitude -EXIF:GPSLongitudeRef -EXIF:GPSLongitude -EXIF:GPSAltitudeRef -EXIF:GPSAltitude -File:FileSize -File:FileName -File:ImageWidth -File:ImageHeight -File:FileType -File:MIMEType -ICC_Profile:ProfileDescription -EXIF:ColorSpace -EXIF:InteropIndex -g -j -struct -c \"%s\" -fast";

	@Autowired
	private PhotoDAO photoDAO;
	
	@Autowired
	private Settings settings;

	@RequestMapping("/exif3/{id}") 
	public ModelAndView exif3(@PathVariable Long id) {
		return new ModelAndView("exif","exifdata",exifString(id));
	}
	
	@RequestMapping("/exif2/{id}/{full}")
	public ModelAndView exif2(@PathVariable Long id, @PathVariable boolean full, HttpServletRequest r) {
		return new ModelAndView("exif","exifdata",exif(id, full, r));
	}
	
	@ResponseBody
	@RequestMapping("/exif/{id}/{full}")
	public String exif(@PathVariable Long id, @PathVariable boolean full, HttpServletRequest request) {
		String path = photoDAO.get(id).getFilePath();
		StringBuilder sb = new StringBuilder();
		try {
			File fullpath = new File(new File(settings.getRootPath()), path);
			log.debug("FILE PATH: "+fullpath.getAbsolutePath());
			ExifReader reader = new ExifReader(fullpath);
			Metadata meta = reader.extract();
			Iterator<Directory> i = meta.getDirectoryIterator();
			int j = 0;
			while (i.hasNext()) {
				Directory d = i.next();
				Iterator tags = d.getTagIterator();
				while (tags.hasNext()) {
					Tag tag = (Tag) tags.next();
					log.debug(tag.getTagName() + ":"+ tag.getDescription());
					if(full || tag.getTagName().equals("Model") 
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
							|| tag.getTagName().equals("Focal Length")
							|| tag.getTagName().equals("Lens")
							|| tag.getTagName().equals("Max Aperture Value")) {
						
					
						if(j++ == 0) {
							sb.append("<div class=\"exif-header\">EXIF");
							if(!full) {
								sb.append("&#160;<a target=\"_blank\" href=\""+request.getContextPath()+"/app/exif2/"+id+"/1\">Full</a>");
							}
							sb.append("</div>");
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
			log.debug(e.getMessage());
			return "<div class=\"exif-header\">EXIF</div>No EXIF Data";
		} catch (MetadataException e) {
			log.debug(e.getMessage());
			return "<div class=\"exif-header\">EXIF</div>No EXIF Data";
		} catch (Exception e) {
			log.debug(e.getMessage());
			return "<div class=\"exif-header\">EXIF</div>No EXIF Data";
		}
		return sb.toString().isEmpty() ? "<div class=\"exif-header\">EXIF</div>No EXIF Data" : sb.toString();
	}

	/**
	 * wykorzystanie exiftool
	 * @param id
	 * @return
	 */
	private String exifString(Long id) {
		String path = photoDAO.get(id).getFilePath();
		try {
			File fullpath = new File(new File(settings.getRootPath()), path);
			Process pr = Runtime.getRuntime().exec(new String[]{settings.getExifToolPath(), fullpath.getAbsolutePath()});
			return "<pre>"+IOUtils.toString(pr.getInputStream())+"</pre>";
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return "<div class=\"exif-header\">EXIF</div>No EXIF Data";
	}
	
}
