package net.marioosh.gallery.model.entities;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import net.marioosh.gallery.model.helpers.Visibility;
import org.hibernate.validator.constraints.NotEmpty;

public class Search extends AbstractEntity {

	private Long id;

	@NotEmpty
	private String phrase;

	private Date modDate = new Date();

	private int counter = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}
