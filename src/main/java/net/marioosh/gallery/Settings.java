package net.marioosh.gallery;

import org.springframework.stereotype.Service;

@Service("settings")
public class Settings {
	private String destPath;
	private String rootPath;
	private int photosPerPage = 24;
	private int albumsPerPage = 24;
	private String thumbCommand;
	private String resizedCommand;

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

	public int getAlbumsPerPage() {
		return albumsPerPage;
	}

	public void setAlbumsPerPage(int albumsPerPage) {
		this.albumsPerPage = albumsPerPage;
	}

	public int getPhotosPerPage() {
		return photosPerPage;
	}

	public void setPhotosPerPage(int photosPerPage) {
		this.photosPerPage = photosPerPage;
	}
	
	public String getThumbCommand() {
		return thumbCommand;
	}
	
	public void setThumbCommand(String thumbCommand) {
		this.thumbCommand = thumbCommand;
	}
	
	public String getResizedCommand() {
		return resizedCommand;
	}
	
	public void setResizedCommand(String resizedCommand) {
		this.resizedCommand = resizedCommand;
	}
}
