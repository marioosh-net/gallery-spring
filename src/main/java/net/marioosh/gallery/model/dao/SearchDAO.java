package net.marioosh.gallery.model.dao;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import net.marioosh.gallery.model.entities.Photo;
import net.marioosh.gallery.model.entities.Search;
import net.marioosh.gallery.model.helpers.BrowseParams;
import net.marioosh.gallery.model.helpers.Visibility;

public interface SearchDAO {
	public List<Search> findTop(int max);
	public void add(Search search);
	public void trigger(String phrase);	
}
