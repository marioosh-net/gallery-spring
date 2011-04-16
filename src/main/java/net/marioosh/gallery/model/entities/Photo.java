package net.marioosh.gallery.model.entities;

import java.util.Date;
import net.marioosh.gallery.model.helpers.Visibility;
import org.hibernate.validator.constraints.NotEmpty;

public class Photo {

	private Long id;

	private String name;

	@NotEmpty
	private String albumId;

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

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
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
}
