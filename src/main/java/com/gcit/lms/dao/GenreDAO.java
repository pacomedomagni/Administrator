
package com.gcit.lms.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import com.gcit.lms.entity.Genre;

/**
 * @Genre ppradhan
 *
 */
@Component
public class GenreDAO extends BaseDAO<Genre> implements ResultSetExtractor<List<Genre>>{
	
	
	public void createGenre(Genre Genre) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("insert into tbl_Genre (genre_name) values (?)", new Object[]{Genre.getGenreName()});
	}
	
	public Integer createGenreWithPK(Genre genre) throws ClassNotFoundException, SQLException {

			String inserSql= "insert into tbl_Genre (genre_name) values (?)";
			GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
			String id_column ="ID";
			jdbcTemplate.update(con->{ 
				PreparedStatement ps = con.prepareStatement(inserSql, new String[] {id_column});
				ps.setString(1, genre.getGenreName());
				return ps;
			}
			, keyHolder);
			BigDecimal id = (BigDecimal) keyHolder.getKeys().get(id_column);
			return id.intValue();
	}

	public void updateGenre(Genre Genre) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("update tbl_Genre set title =? where GenreId = ?", new Object[]{Genre.getGenreName(), Genre.getGenreId()});
	}

	public void deleteGenre(Genre Genre) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("delete from tbl_Genre where GenreId = ?", new Object[]{Genre.getGenreId()});
	}
	
	public List<Genre> readGenres() throws ClassNotFoundException, SQLException {
		return jdbcTemplate.query("select * from tbl_Genre", this);
	}
	
	public List<Genre> getGenresWithBookid(int bookId) throws SQLException {
		return jdbcTemplate.query("SELECT * FROM tbl_genre WHERE genre_id IN (SELECT genre_id FROM tbl_book_genres WHERE bookId=?)",
						new Object[] { bookId },this);
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> Genres = new ArrayList<>();
		while(rs.next()){
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genre_id"));
			genre.setGenreName(rs.getString("genre_name"));
			Genres.add(genre);
		}
		return Genres;
	}
	
}
