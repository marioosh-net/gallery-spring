package net.marioosh.gallery.model.dao;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import net.marioosh.gallery.model.entities.Photo;
import net.marioosh.gallery.model.helpers.BrowseParams;
import net.marioosh.gallery.model.helpers.Visibility;

public interface PhotoDAO {
	public List<Long> findAllId(BrowseParams browseParams);
	public List<Photo> findAll(BrowseParams browseParams);
	public List<Map<String, Object>> findAll(BrowseParams browseParams, String[] columns);
	public int countAll(BrowseParams browseParams);
	public Photo get(Long id);
	public Map<String, Object> get(Long id, String[] columns);
	public Long add(Photo Photo);
	public void delete(Long id);
	public int update(Photo Photo);
	public List<Long> listAllId();
	public InputStream getStream(Long id, int type) throws SQLException;
	public Visibility nextVisbility(Long id);
	public void updateVisibility(Long id, Visibility visibility);
	public Long getByHash(String hash);
	public Long getByAlbumAndHash(String albumHash, String hash);
	public boolean rotate(Long id, boolean left);
	public boolean reload(Long id);
	public String getAbsolutePath(Long id);
}
