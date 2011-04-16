package net.marioosh.gallery.model.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.marioosh.gallery.model.entities.Photo;
import org.springframework.jdbc.core.RowMapper;

/**
 * RowMapper - mapuje rekord w bazie danych na na obiekt
 * tutaj, obiektem jest Photo
 * 
 * @author marioosh
 *
 */
public class PhotoRowMapper implements RowMapper<Photo> {

	public Photo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Photo photo = new Photo();
		int vi = rs.getInt("visibility");
		for(Visibility v: Visibility.values()) {
			if(v.ordinal() == vi) {
				photo.setVisibility(v);
			}
		}
		photo.setName(rs.getString("name"));
		photo.setFilePath(rs.getString("file_path"));
		photo.setModDate(rs.getDate("mod_date"));
		photo.setId(rs.getLong("id"));
		photo.setImg(rs.getBytes("img"));
		photo.setThumb(rs.getBytes("thumb"));
		photo.setAlbumId(rs.getLong("album_id"));
		photo.setDescription(rs.getString("description"));
		return photo;
		
	}

}
