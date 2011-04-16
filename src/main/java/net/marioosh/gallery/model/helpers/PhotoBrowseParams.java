package net.marioosh.gallery.model.helpers;

public class PhotoBrowseParams implements BrowseParams {
	
	private Range range;
	private PhotoSortField sort;
	private String name;
	private Visibility visibility = Visibility.PUBLIC;

	@Override
	public Range getRange() {
		return range;
	}

	@Override
	public SortField getSort() {
		return sort;
	}

	@Override
	public void setRange(Range range) {
		this.range = range;
	}

	@Override
	public void setSort(SortField sort) {
		this.sort = (PhotoSortField) sort;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Visibility getVisibility() {
		return visibility;
	}
	
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}
}
