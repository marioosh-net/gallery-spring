package net.marioosh.gallery.model.helpers;

/**
 * 
 * @author marioosh
 *
 */
public enum Visibility {
	PUBLIC("public"),	// publiczne
	USER("private"),	// widoczne dla zalogowanego usera
	ADMIN("admin");		// widoczne dla administratora
	
	private String name;
	
	Visibility(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
