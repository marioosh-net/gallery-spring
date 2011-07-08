package net.marioosh.gallery;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("logService")
public class LogService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LinkedList<String> q;

	private Logger log = Logger.getLogger(getClass());

	public LogService() {
		log.info(this);
		q = new LinkedList<String>();
	}

	public void log(String message) {
		q.add(message);
	}
	
	public String getMessage() {
		try {
			Iterator<String> i = q.iterator();
			String m = "";
			int j = 0;
			while(i.hasNext()) {
				m += (j != 0 ? "\n" : "") + q.remove();
				j++;
			}
			return m;
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
