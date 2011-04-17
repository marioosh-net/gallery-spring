package net.marioosh.gallery.model.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


public class PhotoRowMapperInputStream implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		return rs.getBinaryStream(1);
	}

}
