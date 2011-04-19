package net.marioosh.gallery.model.entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import net.marioosh.gallery.model.helpers.Visibility;
import net.marioosh.gallery.utils.PhotoUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.validator.constraints.NotEmpty;

public class Photo extends AbstractEntity {

	private Long id;

	private String name;

	@NotEmpty
	private Long albumId;

	private Date modDate;
	
	/**
	 * web-friendly
	 */
	private byte[] img;

	/**
	 * miniaturka
	 */
	private byte[] thumb;

	/**
	 * oryginal
	 */
	private String filePath;

	private Visibility visibility = Visibility.ADMIN;

	private String description;
	
	private String hash;

	public Photo() {	
	}
	
	public Photo(Long id, String name, Visibility visibility) {
		super();
		this.id = id;
		this.name = name;
		this.visibility = visibility;
	}

	
	public Long getId() {
		return id;
	}
	
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAlbumId() {
		return albumId;
	}
	
	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}
	
	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
		this.setThumb(PhotoUtils.makeThumbCroop(this.img, 85, 85));
	}
	
	public void setImg(File f) throws IOException {
		FileInputStream in = new FileInputStream(f);
		this.img = PhotoUtils.makeResized(in);
		in = new FileInputStream(f);
		this.setThumb(PhotoUtils.makeThumbCroop(this.img, 85, 85));		
	}

	public byte[] getThumb() {
		return thumb;
	}

	public void setThumb(byte[] thumb) {
		this.thumb = thumb;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void setFilePath(File file) {
		setFilePath(file.getAbsolutePath());
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getModDate() {
		return modDate;
	}
	
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	
	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
}
