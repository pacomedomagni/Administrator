/**
 * 
 */
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

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Genre;

/**
 * @book ppradhan
 *
 */
@Component
public class BookDAO extends BaseDAO<Book> implements ResultSetExtractor<List<Book>>{

	public void createBook(Book book) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("insert into tbl_book (title) values (?)",
				new Object[] { book.getTitle()});
	}

	public Integer createBookWithPK(Book book) throws ClassNotFoundException, SQLException {
		String inserSql= "insert into tbl_book (title) values (?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		String id_column ="ID";
		jdbcTemplate.update(con->{ 
			PreparedStatement ps = con.prepareStatement(inserSql, new String[] {id_column});
			ps.setString(1, book.getTitle());
			return ps;
		}
		, keyHolder);
		BigDecimal id = (BigDecimal) keyHolder.getKeys().get(id_column);
		return id.intValue();
	}
	
	public Book getBookByPk(Integer bookId) throws SQLException {
		List<Book> books = (List<Book>) jdbcTemplate.query(
				"SELECT * FROM tbl_book WHERE bookId = ?",
				new Object[] { bookId }, this);
		if (books != null && !books.isEmpty()) {
			return books.get(0);
		}
		return null;
	}

	public void updateBook(Book book) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("update tbl_book set title =? where bookId = ?", new Object[] { book.getTitle(), book.getBookId() });
	}

	public void deleteBook(Integer bookId) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("delete from tbl_book where bookId = ?", new Object[] { bookId });
	}
	public Integer getBooksCount() throws SQLException {
		return jdbcTemplate.queryForObject("select count(*) as COUNT from tbl_book", Integer.class);
	}
	public Book getBookByPK(Integer bookId) throws SQLException {
		List<Book> books = (List<Book>) jdbcTemplate.query(
				"SELECT * FROM tbl_book WHERE bookId = ?",
				new Object[] { bookId }, this);
		if (books != null && !books.isEmpty()) {
			return books.get(0);
		}
		return null;
	}


	public List<Book> readBooks() throws ClassNotFoundException, SQLException {
		return jdbcTemplate.query("select * from tbl_book", this);
	}
	public List<Book> searchBookByTitle(String bookTitle) throws ClassNotFoundException, SQLException {
		bookTitle= "%"+bookTitle+"%";
		return jdbcTemplate.query("select * from tbl_book where title like ?",new Object[] {bookTitle}, this);
	}

	public List<Book> getBooksWithPublisher(int pubId) throws SQLException {
		return jdbcTemplate.query("SELECT * FROM tbl_book WHERE pubId =?",
				new Object[] { pubId }, this);
	}
	public List<Book> searchBookByAuthorId(Author author) throws ClassNotFoundException, SQLException {
		return jdbcTemplate.query("select * from tbl_book where bookId IN "
				+ "(select bookId from tbl_book_authors where authorId= ?)",new Object[] {author.getAuthorId()}, this);
	}
	public List<Book> getBooksFromAuthorId(int authorId) throws SQLException {
		return jdbcTemplate
				.query("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_authors WHERE authorId=?)",
						new Object[] { authorId }, this);
	}
	
	public void updateBookAuthor(Book book) throws ClassNotFoundException, SQLException {
		for (Author author : book.getAuthors()) {
			jdbcTemplate.update("INSERT INTO tbl_book_authors VALUES (?, ?)",
					new Object[] { book.getBookId(), author.getAuthorId() });
		}
	}
	public void deleteBookAuthor(Book book) throws SQLException, ClassNotFoundException {
		jdbcTemplate.update("DELETE FROM tbl_book_authors WHERE bookId = ?", new Object[] { book.getBookId() });
	}

	public void updateBookGenres(Book book) throws ClassNotFoundException, SQLException {
		for (Genre genre : book.getGenres()) {
			jdbcTemplate.update("INSERT INTO tbl_book_genres VALUES (?, ?)",
					new Object[] { book.getBookId(), genre.getGenreId() });
		}
	}
	public List<Book> getBooksWithGenre(int genreId) throws SQLException {
		return jdbcTemplate
				.query("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_genres WHERE genre_Id=?)",
						new Object[] { genreId }, this);
	}

	public void deleteBookGenres(Book book) throws SQLException, ClassNotFoundException {
		jdbcTemplate.update("DELETE FROM tbl_book_genres WHERE bookId = ?", new Object[] { book.getBookId() });
	}

	public List<Book> ListOfBooksByBranch(Branch branch) throws SQLException, ClassNotFoundException {

		return jdbcTemplate.query("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_copies WHERE branchId = ?)",
				new Object[] { branch.getBranchId() },this);
	}
	public List<Book> getBooksFromBranchId(int branchId) throws SQLException {
		return jdbcTemplate.query("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_copies WHERE branchId=?)",
						new Object[] { branchId }, this);
	}

	public List<Book> ListOfBooksBorrowed(int branchId, int cardNo)
			throws SQLException, ClassNotFoundException {
		return jdbcTemplate.query(
				"SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_loans WHERE branchId = ? AND cardNo = ? AND dateIn IS null)",
				new Object[] { branchId, cardNo },this);
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			books.add(book);
		}
		return books;
	}
}
