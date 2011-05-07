package net.marioosh.gallery.model.dao;

import java.util.List;
import net.marioosh.gallery.model.entities.Album;
import net.marioosh.gallery.model.helpers.BrowseParams;

public interface AlbumDAO {
	public List<Album> findAll(BrowseParams browseParams);
	public int countAll(BrowseParams browseParams);
	public Album get(Long id);
	public Long add(Album album);
	public void delete(Long id);
	public int update(Album album);
	public List<Long> listAllId();
	public Long getCover(Long albumId);
	public boolean isAlbumExist(String name);
	public Album getByName(String name);
	public Album getByHash(String hash);
	public void deleteAll();
	public void clear(Long id);
}
