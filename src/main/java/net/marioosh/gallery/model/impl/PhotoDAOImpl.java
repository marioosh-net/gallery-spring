package net.marioosh.gallery.model.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.marioosh.gallery.Settings;
import net.marioosh.gallery.UtilService;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.model.entities.Album;
import net.marioosh.gallery.model.entities.Photo;
import net.marioosh.gallery.model.helpers.AlbumRowMapper;
import net.marioosh.gallery.model.helpers.BrowseParams;
import net.marioosh.gallery.model.helpers.PhotoBrowseParams;
import net.marioosh.gallery.model.helpers.PhotoRowMapper;
import net.marioosh.gallery.model.helpers.PhotoRowMapperInputStream;
import net.marioosh.gallery.model.helpers.Visibility;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	@Autowired
	private Settings settings;
	
	@Autowired
	private UtilService utilService;

	public Photo get(Long id) {
		String sql = "select * from tphoto where id = ?";
		try {
			Photo photo = (Photo)jdbcTemplate.queryForObject(sql, new Object[] { id }, new PhotoRowMapper());
			return photo;
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			return null;
		}
	}	
	
	public Long add(Photo photo) {
		//jdbcTemplate.update("insert into tphoto (name, description, mod_date, album_id, img, thumb, visibility) values(?, ?, ?, ?, ?, ?, ?)", photo.getName(), photo.getDescription(), new Date(), photo.getAlbumId(), photo.getImg(), photo.getThumb(), photo.getVisibility().ordinal());
		Object[] params = {photo.getName(), photo.getDescription(), new Date(), photo.getAlbumId(), photo.getImg(), photo.getThumb(), photo.getVisibility().ordinal(), photo.getFilePath(), photo.getHash()};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BINARY, Types.BINARY, Types.INTEGER, Types.VARCHAR, Types.VARCHAR};
		jdbcTemplate.update("insert into tphoto (name, description, mod_date, album_id, img, thumb, visibility, file_path, hash) values(?, ?, ?, ?, ?, ?, ?, ?, ?)", params, types);
		// return jdbcTemplate.queryForLong("select currval('main_seq')");
		return null;
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
			if(browseParams.getSort().isDescending()) {
				sort += " desc";
			}			
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
			s += "and visibility <= " + browseParams.getVisibility().ordinal(); 
		}
		
		if(browseParams.getAlbumId() != null) {
			s += " and album_id = " + browseParams.getAlbumId();
		}
		
		String sql = "select id from tphoto where 1 = 1 "+s+" order by "+sort + " " + limit;
		log.debug("SQL: "+sql);
		
		// return jdbcTemplate.query(sql, new PhotoRowMapper());
		return jdbcTemplate.queryForList(sql, Long.class);
	}
	
	public List<Photo> findAll(BrowseParams browseParams1) {
		
		PhotoBrowseParams browseParams = (PhotoBrowseParams) browseParams1;

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
			s += "and visibility <= " + browseParams.getVisibility().ordinal(); 
		}
		
		if(browseParams.getAlbumId() != null) {
			s += " and album_id = " + browseParams.getAlbumId();
		}
		
		String sql = "select * from tphoto where 1 = 1 "+s+" order by "+sort + " " + limit;
		log.debug("SQL: "+sql);
		
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
			s += "and visibility <= " + browseParams.getVisibility().ordinal(); 
		}

		if(browseParams.getAlbumId() != null) {
			s += " and album_id = " + browseParams.getAlbumId();
		}
		
		String sql = "select count(*) from tphoto where 1 = 1 "+s;
		return jdbcTemplate.queryForInt(sql);
	}
	
	public int update(Photo photo) {
		Object[] params = {photo.getName(), photo.getDescription(), new Date(), photo.getAlbumId(), photo.getImg(), photo.getThumb(), photo.getVisibility().ordinal(), photo.getFilePath(), photo.getHash(), photo.getId()};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BINARY, Types.BINARY, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.BIGINT};
		int rows = jdbcTemplate.update("update tphoto set name = ?, description = ?, mod_date = ?, album_id = ?, img = ?, thumb = ?, visibility = ?, file_path = ?, hash = ? where id = ?", params, types);
		/*
		Object[] params = {photo.getName(), photo.getDescription(), photo.getModDate(), photo.getAlbumId(), photo.getId()};
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT};
		int rows = jdbcTemplate.update("update tphoto set name = ?, description = ?, mod_date = ?, album_id = ? where id = ?", params, types);
		*/
		log.debug("Updated "+rows +" rows.");
		return rows;
	}

	@Override
	public List<Long> listAllId() {
		String sql = "select id from tphoto";
		return jdbcTemplate.queryForList(sql, Long.class); 
	}
	
	@Override
	public InputStream getStream(Long id, int type) throws SQLException {
		
		/**
		 * zabezpieczenie przed nieuprawnionym dostepem do obrazkow niepublicznych
		 */
		String s = " and visibility <= " + Visibility.PUBLIC.ordinal() + " ";
		for(GrantedAuthority a: SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			if(a.getAuthority().equals("ROLE_ADMIN")) {
				s = " and visibility <= " + Visibility.ADMIN.ordinal() + " ";
			}
			if(a.getAuthority().equals("ROLE_USER")) {
				s = " and visibility <= " + Visibility.USER.ordinal() + " ";
			}
		}
		
		String sql = "select img from tphoto where id = ?" + s;
		if(type == 1) {
			sql = "select thumb from tphoto where id = ?" + s;
		}
		InputStream in = (InputStream)jdbcTemplate.queryForObject(sql, new Object[] { id }, new PhotoRowMapperInputStream());
		return in;
	}
	
	@Override
	public List<Map<String, Object>> findAll(BrowseParams browseParams1, String[] columns) {
		PhotoBrowseParams browseParams = (PhotoBrowseParams) browseParams1;

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
			s += "and visibility <= " + browseParams.getVisibility().ordinal(); 
		}
		
		if(browseParams.getAlbumId() != null) {
			s += " and album_id = " + browseParams.getAlbumId();
		}
		
		String c ="";
		int i = 0;
		for(String columnName: columns) {
			c += (i++ > 0 ? "," : " ") + columnName;
		}
		String sql = "select "+c+" from tphoto where 1 = 1 "+s+" order by "+sort + " " + limit;
		log.debug("SQL: "+sql);
		
		// return jdbcTemplate.query(sql, new PhotoRowMapper());
		return jdbcTemplate.queryForList(sql);// (sql, new PhotoRowMapper());
	}
	
	@Override
	public Visibility nextVisbility(Long id) {
    	Photo p = get(id);
    	int v = p.getVisibility().ordinal();
    	int count = Visibility.values().length;
    	int next = ++v%count;
    	if(p != null) {
    		p.setVisibility(Visibility.values()[next]);
    	}
    	try {
			update(p);
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
		return Visibility.values()[next];
    }
	
	@Override
	public void updateVisibility(Long id, Visibility visibility) {
		String sql = "update tphoto set visibility = ? where id = ?";
		jdbcTemplate.update(sql, visibility.ordinal(), id);
	}
	
	@Override
	public Map<String, Object> get(Long id, String[] columns) {
		String c ="";
		int i = 0;
		for(String columnName: columns) {
			c += (i++ > 0 ? "," : " ") + columnName;
		}
		String sql = "select "+c+" from tphoto where id = ?";
		return jdbcTemplate.queryForMap(sql, id);
	}
	
	@Override
	public Long getByHash(String hash) {
		String sql = "select id from tphoto where hash = ?";
		try {
			return jdbcTemplate.queryForLong(sql, hash);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public Long getByAlbumAndHash(String albumHash, String hash) {
		String sql = "select p.id from tphoto p, talbum a where p.hash = ? and p.album_id = a.id and a.hash = ?";
		try {
			return jdbcTemplate.queryForLong(sql, hash, albumHash);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}		
	}
	
	@Override
	public Long getByAlbumAndName(Long albumId, String photoName) {
		String sql = "select p.id from tphoto p, talbum a where p.name = ? and p.album_id = ? and p.album_id = a.id";
		try {
			return jdbcTemplate.queryForLong(sql, photoName, albumId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}		
	}
	
	public boolean rotate(Long id, boolean left) {
		try {
			if(utilService.rotateInPlace(getAbsolutePath(id), left)) {
				return synchronize(id);
			}
		} catch(Exception e) {
			log.error(e.getMessage());
		}
		return false;
	}
	
	public boolean synchronize(Long id) {
		try {
			String path = getAbsolutePath(id);
			
			FileInputStream in = new FileInputStream(new File(path));
			String hash = DigestUtils.md5Hex(in);
			in.close();
			
			if(path != null) {
				log.info("SYNCHRONIZE " + path + " [ID: " + id + "] ...");
				Object[] params = {hash, utilService.resized(path), utilService.thumb(path),  new Date(), id};
				int[] types = {Types.VARCHAR, Types.BINARY, Types.BINARY, Types.TIMESTAMP, Types.BIGINT};
				jdbcTemplate.update("update tphoto set hash = ?, img = ?, thumb = ?, mod_date = ? where id = ?", params, types);
				return true;
			}
		} catch(Exception e) {
			log.error(e.getMessage());
		}
		return false;
	}
	
	public String getAbsolutePath(Long id) {
		try {
			Map<String, Object> map = get(id, new String[]{"id", "file_path"});
			String p = (String) map.get("file_path");
			File f = new File(settings.getRootPath(), p);
			if(f.isFile()) {
				return f.getAbsolutePath();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
		return null;
	}
	
	@Override
	public void updateHashes() {
		PhotoBrowseParams bp = new PhotoBrowseParams();
		bp.setVisibility(Visibility.ADMIN);
		List<Map<String, Object>> l = findAll(bp, new String[]{"id","file_path"});
		for(Map<String, Object> m: l) {
			
			Long id = (Long)m.get("id");
			String path = getAbsolutePath(id);
			if(path != null) {
				try {
					FileInputStream in = new FileInputStream(new File(path));
					String hash = DigestUtils.md5Hex(in);
					in.close();
				
					log.info("Calculating md5 " + path + " [ID: " + id + "]");
					Object[] params = {hash,  new Date(), id};
					int[] types = {Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT};
					jdbcTemplate.update("update tphoto set hash = ?, mod_date = ? where id = ?", params, types);
					
				} catch (FileNotFoundException e) {
					log.error(e.getMessage());
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
		
	}
}
