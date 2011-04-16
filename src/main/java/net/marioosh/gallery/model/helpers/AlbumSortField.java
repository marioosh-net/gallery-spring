package net.marioosh.gallery.model.helpers;

public enum AlbumSortField implements SortField {
	ID("id", "enum.album_sortfield.id", false),
	ID_DESC("id", "enum.album_sortfield.id_desc", true),
	NAME("name", "enum.album_sortfield.name", false),
	NAME_DESC("name", "enum.album_sortfield.name_desc", false);	
	
	private String name;
	private String field;
	private boolean descending;
	
	AlbumSortField(String field, String name, boolean descending) {
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
