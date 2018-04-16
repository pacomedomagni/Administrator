/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.Author;

/**
 * @author ppradhan
 *
 */
@Component
public class AuthorDAO extends BaseDAO<Author> implements ResultSetExtractor<List<Author>>{
	
	
	public void createAuthor(Author author) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("insert into tbl_author (authorName) values (?)", new Object[]{author.getAuthorName()});
	}

	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("update tbl_author set authorName =? where authorId = ?", new Object[]{author.getAuthorName(), author.getAuthorId()});
	}

	public void deleteAuthor(Integer authorId) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("delete from tbl_author where authorId = ?", new Object[]{authorId});
	}
	
	public List<Author> readAuthors() throws ClassNotFoundException, SQLException {
		return jdbcTemplate.query("select * from tbl_author", this);
	}
	
	public List<Author> searchAuthorsByName(String searchString) throws ClassNotFoundException, SQLException {
		searchString = "%"+searchString+"%";
		return jdbcTemplate.query("select * from tbl_author where authorName like ?", new Object[]{searchString}, this);
	}
	public Integer createAuthorWithId(final Author author) throws SQLException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String query = "INSERT INTO tbl_author(authorName) VALUES (?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, author.getAuthorName());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}
	public Integer getAuthorsCount() throws SQLException {
		return jdbcTemplate.queryForObject("select count(*) as COUNT from tbl_author", Integer.class);
	}
	public Author getAuthorByPk(Integer authorId) throws SQLException {
		List<Author> authors = (List<Author>) jdbcTemplate.query(
				"SELECT * FROM tbl_author WHERE authorId = ?",
				new Object[] { authorId }, this);
		if (authors != null && !authors.isEmpty()) {
			return authors.get(0);
		}
		return null;
	}
	public List<Author> getAuthorsWithBookid(int bookId) throws SQLException {
		return jdbcTemplate.query("SELECT * FROM tbl_author WHERE authorId IN (SELECT authorId FROM tbl_book_authors WHERE bookId=?)",
						new Object[] { bookId }, this);
	}
	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while(rs.next()){
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			authors.add(author);
		}
		return authors;
	}
}
