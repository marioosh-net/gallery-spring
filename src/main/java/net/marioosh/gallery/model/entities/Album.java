package net.marioosh.gallery.model.entities;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import net.marioosh.gallery.model.helpers.Visibility;
import org.hibernate.validator.constraints.NotEmpty;

public class Album extends AbstractEntity {

	private Long id;

	@NotEmpty
	@Size(max = 255)
	private String name;

	@NotNull
	private Date modDate;

	@NotNull
	private String path;

	private Visibility visibility = Visibility.ADMIN;
	
	private String hash;
	
	private Long parentId;

	public Album(String name) {
		this.name = name;
	}
	
	public Album() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getShortName() {
		if(name != null && name.length() > 40) {
			return name.substring(0, 39)+"...";
		}
		return name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getModDate() {
		return modDate;
	}
	
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public String getHash() {
		return hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}

	public Long getParentId() {
		return parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
