package net.marioosh.gallery.model.impl;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.marioosh.gallery.model.dao.AlbumDAO;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.model.entities.Album;
import net.marioosh.gallery.model.entities.Photo;
import net.marioosh.gallery.model.helpers.BrowseParams;
import net.marioosh.gallery.model.helpers.PhotoRowMapper;
import net.marioosh.gallery.utils.WebUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("albumDAO")
public class AlbumDAOImpl implements AlbumDAO {

	private Logger log = Logger.getLogger(getClass());
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * @Autowired - autowired by Spring's dependency injection facilities
	 * dataSourca from matching bean in the Spring container
	 */
	@Autowired
	public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	public void add(Album album) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int countAll(BrowseParams browseParams) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Album> findAll(BrowseParams browseParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Album get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Album album) {
		// TODO Auto-generated method stub
		return 0;
	}

}
