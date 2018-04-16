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

import com.gcit.lms.entity.Borrower;

/**
 * @Borrower ppradhan
 *
 */
@Component
public class BorrowerDAO extends BaseDAO<Borrower> implements ResultSetExtractor<List<Borrower>>{
	
	
	public void createBorrowerAccount(Borrower borrower) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("INSERT INTO tbl_borrower (name,address,phone) VALUES (?,?,?)", 
				new Object[] { borrower.getName() , borrower.getAddress(), borrower.getPhone()});
	}

	public void updateBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("UPDATE tbl_borrower SET name = ?, address = ?, phone = ? WHERE cardNo = ?",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo() });
	}

	public void deleteBorrower(Integer borrowerId) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] { borrowerId });
	}
	
	public List<Borrower> readBorrowers() throws ClassNotFoundException, SQLException {
		return jdbcTemplate.query("select * from tbl_Borrower", this);
	}
	public List<Borrower> getBorrowerById(Borrower borrower) throws ClassNotFoundException, SQLException {
		return jdbcTemplate.query("select * from tbl_Borrower where cardNo=? ", new Object[] { borrower.getCardNo() } ,this);
	}
	public boolean validateCard(int cardNo) throws SQLException, ClassNotFoundException {
		List<Borrower> temp = jdbcTemplate.query("SELECT * FROM tbl_borrower WHERE cardNo = ?", new Object[]{cardNo},this);
		if(!temp.isEmpty())
			return true;
		return false;
	}
	public List<Borrower> searchBorrowerByName(String name) throws ClassNotFoundException, SQLException {
		name= "%+name+%";
		return jdbcTemplate.query("select * from tbl_borrower where name like ?",new Object[] {name}, this);
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while(rs.next()){
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			borrowers.add(borrower);
		}
		
		return borrowers;
		
	}
	

}
