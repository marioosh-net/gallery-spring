package net.marioosh.gallery;

import org.springframework.stereotype.Service;

@Service("settings")
public class Settings {
	private String destPath;
	private String rootPath;

	public String getDestPath() {
		return destPath;
	}

	public void setDestPath(String destPath) {
		this.destPath = destPath;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

}
