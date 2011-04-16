package net.marioosh.gallery.model.helpers;

public enum PhotoSortField implements SortField {
	ID("id", "enum.photo_sortfield.id", false),
	ID_DESC("id", "enum.photo_sortfield.id_desc", true),
	NAME("name", "enum.photo_sortfield.name", false),
	NAME_DESC("name", "enum.photo_sortfield.name_desc", false);	
	
	private String name;
	private String field;
	private boolean descending;
	
	PhotoSortField(String field, String name, boolean descending) {
		this.name = name;
		this.field = field;
		this.descending = descending;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getField() {
		return field;
	}
	
	@Override
	public boolean isDescending() {
		return descending;
	}

}
