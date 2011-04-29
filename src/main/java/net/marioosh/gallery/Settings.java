package net.marioosh.gallery;

import org.springframework.stereotype.Service;

@Service("settings")
public class Settings {
	private String destPath;
	private String rootPath;
	private int photosPerPage = 24;
	private int albumsPerPage = 24;
	private String convertPath;
	private String mogrifyPath;
	private String exifToolPath;

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

	public String getConvertPath() {
		return convertPath;
	}
	
	public void setConvertPath(String convertPath) {
		this.convertPath = convertPath;
	}
	
	public String getMogrifyPath() {
		return mogrifyPath;
	}
	
	public void setMogrifyPath(String mogrifyPath) {
		this.mogrifyPath = mogrifyPath;
	}
	
	public String getExifToolPath() {
		return exifToolPath;
	}
	
	public void setExifToolPath(String exifToolPath) {
		this.exifToolPath = exifToolPath;
	}
}
