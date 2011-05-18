package net.marioosh.gallery.model.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.marioosh.gallery.model.entities.Album;
import net.marioosh.gallery.model.entities.Photo;
import org.springframework.jdbc.core.RowMapper;

/**
 * RowMapper - mapuje rekord w bazie danych na na obiekt
 * tutaj, obiektem jest Photo
 * 
 * @author marioosh
 *
 */
public class AlbumRowMapper implements RowMapper<Album> {

	@Override
	public Album mapRow(ResultSet rs, int rowNum) throws SQLException {
		Album album = new Album(rs.getString("name"));
		int vi = rs.getInt("visibility");
		for(Visibility v: Visibility.values()) {
			if(v.ordinal() == vi) {
				album.setVisibility(v);
			}
		}
		album.setPath(rs.getString("path"));
		album.setModDate(rs.getDate("mod_date"));
		album.setId(rs.getLong("id"));
		album.setHash(rs.getString("hash"));
		album.setParentId(rs.getLong("parent_id"));
		return album;
	}

	/*
	public Photo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Photo photo = new Photo(rs.getLong("id"), rs.getString("name"), Visibility.PUBLIC);
		photo.setDescription(rs.getString("description"));
		return photo;
	}
	*/

}
