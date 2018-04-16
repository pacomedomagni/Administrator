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

import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.Branch;

/**
 * @Branch ppradhan
 *
 */
@Component
public class BranchDAO extends BaseDAO<Branch> implements ResultSetExtractor<List<Branch>>{
	
	
	public void createBranch(Branch branch) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("INSERT INTO tbl_library_branch (branchName,branchAddress) VALUES (?,?)",
				new Object[] { branch.getBranchName(), branch.getBranchAddress() });
	}
	
	public void populateBranchWithBooks(Branch branch) throws ClassNotFoundException, SQLException {
		for (BookCopies bookCopie : branch.getBookCopies()) {
			jdbcTemplate.update("INSERT INTO tbl_book_copies VALUES (?,?,?)",
					new Object[] { bookCopie.getBookId(), branch.getBranchId(), bookCopie.getNoOfCopies() });
		}
	}

	public void updateBranch(Branch branch) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ?",
		new Object[] { branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId() });
	}

	public void deleteBranch(Integer branchId) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("DELETE FROM tbl_library_Branch WHERE branchId = ?", new Object[] { branchId });
	}
	public Integer getBranchesCount() throws SQLException {
		return jdbcTemplate.queryForObject("select count(*) as COUNT from tbl_library_branch", Integer.class);
	}
	public Branch getBranchByPk(Integer branchId) throws SQLException {
		List<Branch> branchs = jdbcTemplate.query(
				"SELECT * FROM tbl_library_branch WHERE branchId = ?",
				new Object[] { branchId }, this);
		if (branchs != null && !branchs.isEmpty()) {
			return branchs.get(0);
		}
		return null;
	}
	
	public List<Branch> readBranchs() throws ClassNotFoundException, SQLException {
		return jdbcTemplate.query("SELECT * FROM tbl_library_branch", this);
	}

	public List<Branch> SearchBranchByName(String branchName) throws SQLException, ClassNotFoundException {
		if (branchName != null && !branchName.isEmpty()) {
			branchName = "%" + branchName + "%";
			return jdbcTemplate.query("SELECT * FROM tbl_library_branch WHERE branchName like ?", new Object[] { branchName },this);
		} else {
			return jdbcTemplate.query("SELECT * FROM tbl_library_branch", this);
		}
	}
	
	@Override
	public List<Branch> extractData(ResultSet rs) throws SQLException {
		List<Branch> branchs = new ArrayList<>();
		while (rs.next()) {
			Branch branch = new Branch();
			branch.setBranchId(rs.getInt("branchId"));
			branch.setBranchName(rs.getString("branchName"));
			branch.setBranchAddress(rs.getString("branchAddress"));
			branchs.add(branch);
		}

		return branchs;
	}
	
}
