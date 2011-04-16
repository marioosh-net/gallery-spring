package net.marioosh.gallery.model.dao;

import java.util.List;
import net.marioosh.gallery.model.entities.Photo;
import net.marioosh.gallery.model.helpers.BrowseParams;

public interface PhotoDAO {
	public List<Photo> findAll(BrowseParams browseParams);
	public int countAll(BrowseParams browseParams);
	public Photo get(Long id);
	public void add(Photo Photo);
	public void delete(Long id);
	public int update(Photo Photo);
	public List<Long> listAllId();
}
