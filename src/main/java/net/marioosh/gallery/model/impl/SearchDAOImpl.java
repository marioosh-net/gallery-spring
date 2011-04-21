package net.marioosh.gallery.model.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import net.marioosh.gallery.model.dao.AlbumDAO;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.model.dao.PhotoDAO;
import net.marioosh.gallery.model.dao.SearchDAO;
import net.marioosh.gallery.model.entities.Album;
import net.marioosh.gallery.model.entities.Photo;
import net.marioosh.gallery.model.entities.Search;
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

@Repository("searchDAO")
public class SearchDAOImpl implements SearchDAO {

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
	public List<Search> findTop(int max) {
		String sql = "select * from tsearch order by counter desc limit "+max;
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Search>(Search.class));
	}
	
	@Override
	public void add(Search search) {
		jdbcTemplate.update("insert into tsearch (phrase, counter, mod_date) values(?, ?, ?)", search.getPhrase(), search.getCounter(), search.getModDate());
	}	
	
	@Override
	public void trigger(String phrase) {
		if(phrase != null && !phrase.trim().isEmpty()) {
			String sql = "select * from tsearch where phrase = ?";
			try {
				Search search = (Search)jdbcTemplate.queryForObject(sql, new Object[] { phrase }, new BeanPropertyRowMapper<Search>(Search.class));
				jdbcTemplate.update("update tsearch set mod_date = ?, counter = counter + 1 where id = " + search.getId(), new Date());
			} catch (org.springframework.dao.EmptyResultDataAccessException e) {
				Search s = new Search();
				s.setCounter(1);
				s.setModDate(new Date());
				s.setPhrase(phrase);
				add(s);
			}		
		}
	}

}
