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
import net.marioosh.gallery.model.helpers.AlbumBrowseParams;
import net.marioosh.gallery.model.helpers.AlbumRowMapper;
import net.marioosh.gallery.model.helpers.BrowseParams;
import net.marioosh.gallery.model.helpers.PhotoBrowseParams;
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
		Object[] params = {album.getName(), new Date(), album.getVisibility().ordinal()};
		int[] types = {Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER};
		jdbcTemplate.update("insert into talbum (name, mod_date, visibility) values(?, ?, ?)", params, types);
	}

	@Override
	public int countAll(BrowseParams browseParams) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update("delete from tphoto where album_id = "+id);
		jdbcTemplate.update("delete from talbum where id = "+id);
	}

	@Override
	public List<Album> findAll(BrowseParams browseParams1) {
		AlbumBrowseParams browseParams = (AlbumBrowseParams) browseParams1;

		String sort = "id desc";
		if(browseParams.getSort() != null) {
			sort = browseParams.getSort().getField();
		}
		String limit = "";
		if(browseParams.getRange() != null) {
			 limit = "limit " + browseParams.getRange().getMax() + " offset " + browseParams.getRange().getStart(); 
		}
		String s = "";
		if(browseParams.getVisibility() != null) {
			s += "and visibility <= " + browseParams.getVisibility().ordinal(); 
		}
		String sql = "select * from talbum where 1 = 1 "+s+" order by "+sort + " " + limit;
		log.debug(sql);
		
		return jdbcTemplate.query(sql, new AlbumRowMapper());

	}

	@Override
	public Album get(Long id) {
		String sql = "select * from talbum where id = ?";
		try {
			Album album = (Album)jdbcTemplate.queryForObject(sql, new Object[] { id }, new AlbumRowMapper());
			return album;
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public int update(Album album) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Long> listAllId() {
		String sql = "select id from talbum";
		return jdbcTemplate.queryForList(sql, Long.class); 
	}

	@Override
	public Long getCover(Long albumId) {
		String sql = "select id from tphoto where album_id = ? limit 1";
		return jdbcTemplate.queryForLong(sql, albumId);
	}
	
	@Override
	public boolean isAlbumExist(String name) {
		String sql = "select id from talbum where name = ?";
		if(jdbcTemplate.queryForLong(sql) > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public Album getByName(String name) {
		String sql = "select id from talbum where name = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{name}, new AlbumRowMapper());
	}
}
