/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.Publisher;

/**
 * @Publisher ppradhan
 *
 */
@Component
public class PublisherDAO extends BaseDAO<Publisher> implements ResultSetExtractor<List<Publisher>>{
	
	public void createPublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values (?,?,?)",
				new Object[]{publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhone()});
	}

	public void updatePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("update tbl_publisher set publisherName =?, publisherAddress=?, publisherPhone=? where publisherId = ?", 
				new Object[]{publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhone(), publisher.getPublisherId()});
	}

	public void deletePublisher(Integer publisherId) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("delete from tbl_publisher where publisherId = ?", new Object[]{publisherId});
	}
	
	public List<Publisher> readPublishers() throws ClassNotFoundException, SQLException {
		return jdbcTemplate.query("select * from tbl_publisher", this);
	}
	public Publisher getPublisherByBook(Integer bookId) throws ClassNotFoundException, SQLException {
		List<Publisher>publishers = new ArrayList<>();
		publishers= jdbcTemplate.query("SELECT * FROM tbl_publisher WHERE publisherId IN (SELECT pubId FROM tbl_book WHERE bookId=?)",new Object[]{bookId}, this);
		return publishers.get(0);
	}

	public List<Publisher> searchPublisherByName(String searchString) throws ClassNotFoundException, SQLException {
		searchString = "%"+searchString+"%";
		return jdbcTemplate.query("select * from tbl_publisher where publisherName like ?", new Object[]{searchString}, this);
	}
	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while(rs.next()){
			Publisher publisher = new Publisher();
			publisher.setPublisherId(rs.getInt("publisherId"));
			publisher.setPublisherName(rs.getString("publisherName"));
			publisher.setPublisherAddress(rs.getString("publisherAddress"));
			publisher.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(publisher);
		}
		return publishers;
	}

}
