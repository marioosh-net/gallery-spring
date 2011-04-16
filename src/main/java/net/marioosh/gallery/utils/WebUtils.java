package net.marioosh.gallery.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

public class WebUtils {
	
	private static Logger log = Logger.getLogger(WebUtils.class);
	
	public static Map<String, String> pageInfo(String u) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			u = u.startsWith("http://") || u.startsWith("https://") ? u : "http://" + u;
			// jericho
			Source source = new Source(new URL(u));
			List<Element> titles = source.getAllElements(HTMLElementName.TITLE);
			if (!titles.isEmpty()) {
				Element title = titles.get(0);
				// return new String(title.getContent().getTextExtractor().toString().getBytes(), "ISO-8859-2");
				map.put("title", title.getTextExtractor().toString());
			}

			List<Element> meta = source.getAllElements(HTMLElementName.META);
			if (!meta.isEmpty()) {
				for (Element e : meta) {
					if (e.getAttributeValue("name") != null) {
						if (e.getAttributeValue("name").equalsIgnoreCase("description")) {
							map.put("description", e.getAttributeValue("content"));
						}
					}
				}
			}

		} catch (IOException e) {
			return map;
		} catch (Exception e) {
			return map;
		}
		return map;
	}
	
	public static void logRequestInfo(HttpServletRequest request) {
		log.info("HOST / IP: "+request.getRemoteHost() + " / " + request.getRemoteAddr());		
		log.info("---- HEADERS ----");
		Enumeration<String> e = request.getHeaderNames();
		while(e.hasMoreElements()) {
			String headerName = e.nextElement();
			log.info(headerName + " : " + request.getHeader(headerName));
		}
		log.info("---- HEADERS END ----");
	}
}
