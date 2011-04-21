package net.marioosh.gallery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ServletContext;
import net.marioosh.gallery.model.dao.PhotoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

@Controller
public class ExifController implements ServletContextAware {

	final String cmd = "exiftool -EXIF:MeteringMode -EXIF:Flash -EXIF:ExposureMode -EXIF:WhiteBalance -EXIF:LightSource -EXIF:Model -EXIF:Make -EXIF:ModifyDate -EXIF:ExposureTime -EXIF:FNumber -EXIF:ExposureProgram -EXIF:ISO -EXIF:DateTimeOriginal -EXIF:CreateDate -EXIF:ExposureCompensation -EXIF:FocalLength -EXIF:SubSecTime -EXIF:SubSecTimeOriginal -EXIF:SubSecTimeDigitized -EXIF:FocalLengthIn35mmFormat -XMP-dc:title -XMP-iptcExt:PersonInImage -XMP-iptcExt:Event -XMP-xmp:Rating -EXIF:GPSLatitudeRef -EXIF:GPSLatitude -EXIF:GPSLongitudeRef -EXIF:GPSLongitude -EXIF:GPSAltitudeRef -EXIF:GPSAltitude -File:FileSize -File:FileName -File:ImageWidth -File:ImageHeight -File:FileType -File:MIMEType -ICC_Profile:ProfileDescription -EXIF:ColorSpace -EXIF:InteropIndex -g -j -struct -c \"%s\" -fast";
	
	@Autowired
	private PhotoDAO photoDAO;

	private ServletContext servletContext;

	@ResponseBody
	@RequestMapping("/exif.html")
	public String exif(@RequestParam Long id) {
		StringBuilder sb = new StringBuilder();
		try {
			String line;
			Process p = Runtime.getRuntime().exec(cmd + " \"" + photoDAO.get(id).getFilePath()+"\"");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				sb.append(line);
			}
			input.close();
		} catch (IOException e) {

		}
		return sb.toString();
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
	}

}
