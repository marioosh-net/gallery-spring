package net.marioosh.gallery.model.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.marioosh.gallery.UtilService;
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
import net.marioosh.gallery.model.helpers.Visibility;
import net.marioosh.gallery.utils.WebUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository("albumDAO")
public class AlbumDAOImpl implements AlbumDAO {

	private Logger log = Logger.getLogger(getClass());
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UtilService utilService;
	
	/**
	 * @Autowired - autowired by Spring's dependency injection facilities
	 * dataSourca from matching bean in the Spring container
	 */
	@Autowired
	public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	public Long add(final Album album) {
		Object[] params = {album.getName(), new Date(), album.getVisibility().ordinal(), album.getPath(), album.getHash(), album.getParentId()};
		int[] types = {Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.BIGINT};
		jdbcTemplate.update("insert into talbum (name, mod_date, visibility, path, hash, parent_id) values(?, ?, ?, ?, ?, ?)", params, types);
		
		return jdbcTemplate.queryForLong("select currval('main_seq')");
		// return null;
		
		/* baza pg 8.2 lub nowsza i jdbc driver 8.4-701 lub nowszy 
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement("insert into talbum (name, mod_date, visibility) values(?, ?, ?)", new String[] {"id"});
				ps.setString(1, album.getName());
				ps.setDate(2, new java.sql.Date(new Date().getTime()));
				ps.setInt(3, album.getVisibility().ordinal());
				return ps;
			}
			
		}, keyHolder);
		return (Long) keyHolder.getKey();
		*/
	}

	@Override
	public int countAll(BrowseParams browseParams1) {
		AlbumBrowseParams browseParams = (AlbumBrowseParams) browseParams1;

		String s = "";
		if(browseParams.getVisibility() != null) {
			s += "and visibility <= " + browseParams.getVisibility().ordinal(); 
		}
		if(browseParams.getSearch() != null && !browseParams.getSearch().isEmpty()) {
			s += " and upper(name) like upper('%"+browseParams.getSearch()+"%') ";
		}		
		if(browseParams.getParentId() != null) {
			s += " and parent_id = " + browseParams.getParentId();
		} else {
			if(!browseParams.isWithSubalbums()) {
				s += " and parent_id is null";
			}
		}
		
		String sql = "select count(id) from talbum where 1 = 1 "+s;
		log.debug(sql);
		
		return jdbcTemplate.queryForInt(sql);

	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update("delete from tphoto where album_id = "+id);
		jdbcTemplate.update("delete from talbum where id = "+id);
	}

	@Override
	public void clear(Long id) {
		jdbcTemplate.update("delete from tphoto where album_id = "+id);
	}
	
	@Override
	public List<Album> findAll(BrowseParams browseParams1) {
		AlbumBrowseParams browseParams = (AlbumBrowseParams) browseParams1;

		String sort = "id desc";
		if(browseParams.getSort() != null) {
			sort = browseParams.getSort().getField();
			if(browseParams.getSort().isDescending()) {
				sort += " desc";
			}
		}
		String limit = "";
		if(browseParams.getRange() != null) {
			 limit = "limit " + browseParams.getRange().getMax() + " offset " + browseParams.getRange().getStart(); 
		}
		String s = "";
		if(browseParams.getVisibility() != null) {
			s += "and visibility <= " + browseParams.getVisibility().ordinal() + " "; 
		}
		if(browseParams.getSearch() != null && !browseParams.getSearch().isEmpty()) {
			s += " and upper(name) like upper('%"+browseParams.getSearch()+"%') ";
		}
		if(browseParams.getParentId() != null) {
			s += " and parent_id = " + browseParams.getParentId();
		} else {
			if(!browseParams.isWithSubalbums()) {
				s += " and parent_id is null";
			}
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
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public int update(Album album) {
		Object[] params = {album.getName(), new Date(), album.getVisibility().ordinal(), album.getPath(), album.getHash(), album.getParentId(), album.getId()};
		int[] types = {Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.BIGINT, Types.BIGINT};
		int rows = jdbcTemplate.update("update talbum set name = ?, mod_date = ?, visibility = ?, path = ?, hash = ?, parent_id = ? where id = ?", params, types);
		return rows;
	}

	@Override
	public List<Long> listAllId() {
		String sql = "select id from talbum";
		return jdbcTemplate.queryForList(sql, Long.class); 
	}

	@Override
	public Long getCover(Long albumId) {
		String sql = "select id from tphoto where album_id = ? and visibility <= ? order by random() limit 1";
		try {
			return jdbcTemplate.queryForLong(sql, albumId, utilService.getCurrentVisibility().ordinal());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}
	
	@Override
	public boolean isAlbumExist(String name) {
		String sql = "select count(id) from talbum where name = ?";
		if(jdbcTemplate.queryForLong(sql, new Object[]{name}, new int[]{Types.VARCHAR}) > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public Album getByName(String name) {
		String sql = "select * from talbum where name = ?";
		try {
			Album a = jdbcTemplate.queryForObject(sql, new Object[]{name}, new AlbumRowMapper());
			return a;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public void deleteAll() {
		jdbcTemplate.update("delete from tphoto");
		jdbcTemplate.update("delete from talbum");		
	}
	
	@Override
	public Album getByHash(String hash) {
		String sql = "select * from talbum where hash = ?";
		try {
			Album a = jdbcTemplate.queryForObject(sql, new Object[]{hash}, new AlbumRowMapper());
			return a;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
