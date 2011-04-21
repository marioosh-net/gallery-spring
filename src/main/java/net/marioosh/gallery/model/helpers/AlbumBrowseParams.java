package net.marioosh.gallery.model.helpers;

public class AlbumBrowseParams implements BrowseParams {
	
	private Range range;
	private AlbumSortField sort;
	private Visibility visibility = Visibility.PUBLIC;
	private String search;
	
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
		this.sort = (AlbumSortField) sort;
	}

	
	public Visibility getVisibility() {
		return visibility;
	}
	
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}
	
	public String getSearch() {
		return search;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
}
