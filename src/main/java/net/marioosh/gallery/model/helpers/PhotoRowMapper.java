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
		Photo photo = new Photo(rs.getLong("id"), rs.getString("name"), Visibility.PUBLIC);
		photo.setDescription(rs.getString("description"));
		return photo;
	}

}
