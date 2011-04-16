package net.marioosh.gallery.model.helpers;

public class AlbumBrowseParams implements BrowseParams {
	
	private Range range;
	private AlbumSortField sort;

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
		
}
