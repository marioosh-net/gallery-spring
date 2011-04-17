package net.marioosh.gallery.model.dao;

import java.util.List;
import net.marioosh.gallery.model.entities.Album;
import net.marioosh.gallery.model.helpers.BrowseParams;

public interface AlbumDAO {
	public List<Album> findAll(BrowseParams browseParams);
	public int countAll(BrowseParams browseParams);
	public Album get(Long id);
	public void add(Album album);
	public void delete(Long id);
	public int update(Album album);
	public List<Long> listAllId();
	public Long getCover(Long albumId);
}
