package net.marioosh.gallery.model.helpers;


public class Range {
	private int start;
	private int max;
	
	public Range(int start, int max) {
		super();
		this.start = start;
		this.max = max;
	}

	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getMax() {
		return max;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
}
