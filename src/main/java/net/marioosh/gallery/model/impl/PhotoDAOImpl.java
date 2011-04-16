package net.marioosh.gallery.model.impl;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.model.entities.Photo;
import net.marioosh.gallery.model.helpers.BrowseParams;
import net.marioosh.gallery.model.helpers.PhotoBrowseParams;
import net.marioosh.gallery.model.helpers.PhotoRowMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("photoDAO")
public class PhotoDAOImpl implements PhotoDAO {

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

	public Photo get(Long id) {
		String sql = "select * from tphoto where id = ?";
		try {
			Photo photo = (Photo)jdbcTemplate.queryForObject(sql, new Object[] { id }, new PhotoRowMapper());
			return photo;
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}
	}	
	
	public void add(Photo photo) {
		jdbcTemplate.update("insert into tphoto (name, description, mod_date, album_id, img, thumb, visibility) values(?, ?, ?, ?, ?, ?, ?)", photo.getName(), photo.getDescription(), new Date(), photo.getAlbumId(), photo.getImg(), photo.getThumb(), photo.getVisibility().ordinal());
	}
	
	public void delete(Long id) {
		jdbcTemplate.update("delete from tphoto where id = "+id);
	}

	@Override
	public List<Long> findAllId(BrowseParams browseParams1) {
		PhotoBrowseParams browseParams = (PhotoBrowseParams) browseParams1;

		String sort = "id desc";
		if(browseParams.getSort() != null) {
			sort = browseParams.getSort().getField();
		}
		String limit = "";
		if(browseParams.getRange() != null) {
			 limit = "limit " + browseParams.getRange().getMax() + " offset " + browseParams.getRange().getStart(); 
		}
		
		String s = "";
		if(browseParams.getName() != null) {
			String q = browseParams.getName();
			q = q.replaceAll("[ ]{2,}", " ");
			s = "and ";
			int i = 0;
			for(String p: q.split(" ")) {
				if(i == 0) {
					s += "upper(name) like upper('%" + p + "%') ";
				} else {
					s += "or upper(name) like upper('%" + p + "%') ";
				}
				i++;
			}
		}		
		
		if(browseParams.getVisibility() != null) {
			s += "and visibility = " + browseParams.getVisibility().ordinal(); 
		}
		
		if(browseParams.getAlbumId() != null) {
			s += " and album_id = " + browseParams.getAlbumId();
		}
		
		String sql = "select id from tphoto where 1 = 1 "+s+" order by "+sort + " " + limit;
		
		// return jdbcTemplate.query(sql, new PhotoRowMapper());
		return jdbcTemplate.queryForList(sql, Long.class);
	}
	
	public List<Photo> findAll(BrowseParams browseParams1) {
		
		PhotoBrowseParams browseParams = (PhotoBrowseParams) browseParams1;

		String sort = "id desc";
		if(browseParams.getSort() != null) {
			sort = browseParams.getSort().getField();
		}
		String limit = "";
		if(browseParams.getRange() != null) {
			 limit = "limit " + browseParams.getRange().getMax() + " offset " + browseParams.getRange().getStart(); 
		}
		
		String s = "";
		if(browseParams.getName() != null) {
			String q = browseParams.getName();
			q = q.replaceAll("[ ]{2,}", " ");
			s = "and ";
			int i = 0;
			for(String p: q.split(" ")) {
				if(i == 0) {
					s += "upper(name) like upper('%" + p + "%') ";
				} else {
					s += "or upper(name) like upper('%" + p + "%') ";
				}
				i++;
			}
		}		
		
		if(browseParams.getVisibility() != null) {
			s += "and visibility = " + browseParams.getVisibility().ordinal(); 
		}
		
		if(browseParams.getAlbumId() != null) {
			s += " and album_id = " + browseParams.getAlbumId();
		}
		
		String sql = "select * from tphoto where 1 = 1 "+s+" order by "+sort + " " + limit;
		
		// return jdbcTemplate.query(sql, new PhotoRowMapper());
		return jdbcTemplate.query(sql, new PhotoRowMapper());
		
	}

	public int countAll(BrowseParams browseParams1) {
		
		PhotoBrowseParams browseParams = (PhotoBrowseParams) browseParams1;
		
		String s = "";
		if(browseParams.getName() != null) {
			String q = browseParams.getName();
			q = q.replaceAll("[ ]{2,}", " ");
			s = "and ";
			int i = 0;
			for(String p: q.split(" ")) {
				if(i == 0) {
					s += "upper(name) like upper('%" + p + "%') ";
				} else {
					s += "or upper(name) like upper('%" + p + "%') ";
				}
				i++;
			}
		}		
		// String s = browseParams.getSearch() != null ? "and (upper(address) like upper('%"+browseParams.getSearch()+"%') or upper(name) like upper('%"+browseParams.getSearch()+"%'))" : "";
		
		if(browseParams.getVisibility() != null) {
			s += "and visibility = " + browseParams.getVisibility().ordinal(); 
		}

		if(browseParams.getAlbumId() != null) {
			s += " and album_id = " + browseParams.getAlbumId();
		}
		
		String sql = "select count(*) from tphoto where 1 = 1 "+s;
		return jdbcTemplate.queryForInt(sql);
	}
	
	public int update(Photo photo) {
		Object[] params = {photo.getName(), photo.getDescription(), photo.getModDate(), photo.getId(), photo.getAlbumId()};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT};
		int rows = jdbcTemplate.update("update tphoto set name = ?, description = ?, mod_date = ?, album_id = ? where id = ?", params, types);
		log.debug("Updated "+rows +" rows.");
		return rows;
	}

	@Override
	public List<Long> listAllId() {
		String sql = "select id from tphoto";
		return jdbcTemplate.queryForList(sql, Long.class); 
	}
	
}
