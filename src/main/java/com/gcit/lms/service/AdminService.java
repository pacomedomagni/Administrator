package com.gcit.lms.service;

import java.net.URI;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Publisher;

//@CrossOrigin(origins="http://localhost:3000")
@RestController
public class AdminService extends BaseController {

	@Autowired
	AuthorDAO adao;
	@Autowired
	BookDAO bdao;
	@Autowired
	BorrowerDAO brdao;
	@Autowired
	BranchDAO bradao;
	@Autowired
	GenreDAO gdao;
	@Autowired
	PublisherDAO pdao;

	@RequestMapping(value="initAuthor", method=RequestMethod.GET, produces="application/json" )
	public Author initAuthor() throws SQLException {
		return new Author();
	}
	@RequestMapping(value="initBook", method=RequestMethod.GET, produces="application/json" )
	public Book initBook() throws SQLException {
		return new Book();
	}
	@Transactional
	@RequestMapping(value = "Borrower/{borrwerId}", method = RequestMethod.DELETE, consumes = "application/json")
	public ResponseEntity<Object> deleteBorrower(@PathVariable Integer borrowerId) {
		try {
			brdao.deleteBorrower(borrowerId);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Borrower/"+borrowerId));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// update borrower information input the CardNo and update name phone and
	// address
	@Transactional
	@RequestMapping(value = "Borrower", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<Object> updateBorrowerInfo(@RequestBody Borrower borrower) {
		try {
			brdao.updateBorrower(borrower);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Borrower"));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	@RequestMapping(value = "Borrowers/{search}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> searchBorrowerByName(@PathVariable String search) {
		List<Borrower> borrowers = new ArrayList<>();
		try {
			borrowers = brdao.searchBorrowerByName(search);
			return new ResponseEntity<Object>(borrowers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// create new borrower account input name, address, phone
	@Transactional
	@RequestMapping(value = "Borrower", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> createBorrowerNewAccount(@RequestBody Borrower borrower) {

		try {
			brdao.createBorrowerAccount(borrower);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Borrower"));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// get the list of all borrowers no input
	@Transactional
	@RequestMapping(value = "Borrowers", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAllBorrowers() {
		List<Borrower> borrowers = new ArrayList<>();

		try {
			borrowers = brdao.readBorrowers();
			return new ResponseEntity<Object>(borrowers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// Everything about Publisher follow
	@RequestMapping(value = "Publisher/{searchString}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> searchPublisherByName(@PathVariable("searchString") String searchString) {
		List<Publisher> publishers = new ArrayList<>();
		try {
			publishers = pdao.searchPublisherByName(searchString);
			return new ResponseEntity<Object>(publishers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "Publisher/{publisherId}", method = RequestMethod.DELETE, consumes = "application/json")
	public ResponseEntity<Object> deletePublisher(@RequestBody Integer publisherId) {
		try {
			pdao.deletePublisher(publisherId);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Publisher/"+publisherId));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "Publisher", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<Object> updatePublisher(@RequestBody Publisher publisher) {

		try {
			pdao.updatePublisher(publisher);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Publisher"));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "Publisher", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Object> createNewPublisher(@RequestBody Publisher publisher) {
		try {
			pdao.createPublisher(publisher);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Publisher"));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	@RequestMapping(value = "Publishers", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> listOfAllPublisher() {
		List<Publisher> publishers = new ArrayList<>();
		try {
			publishers = pdao.readPublishers();
			return new ResponseEntity<Object>(publishers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "Author", method = RequestMethod.POST, consumes = "application/json")
	@Transactional
	public ResponseEntity<Object> createAuthor(@RequestBody Author author) {
		try {
			adao.createAuthor(author);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Author"));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "Author", method = RequestMethod.PUT, consumes = "application/json")
	@Transactional
	public ResponseEntity<Object>  updateAuthor(@RequestBody Author author) {
		try {
			adao.updateAuthor(author);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Author"));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "Author/{authorId}", method = RequestMethod.DELETE, consumes = "application/json")
	@Transactional
	public ResponseEntity<Object>  deleteAuthor(@RequestBody Integer authorId) {
		try {
			adao.deleteAuthor(authorId);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Author/"+authorId));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Transactional
	@RequestMapping(value ="Authors", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object>  readAllAuthors() {
		List<Author> authors = new ArrayList<>();
		try {
			authors = adao.readAuthors();
			for (Author a : authors) {
				a.setBooks(bdao.searchBookByAuthorId(a));
			}
			return new ResponseEntity<Object>(authors,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "Authors/{searchString}", method = RequestMethod.GET, produces = "application/json")
	@Transactional
	public ResponseEntity<Object> searchAuthorsByName(@PathVariable("searchString") String searchString) {
		List<Author> authors = new ArrayList<>();
		try {
			authors = adao.searchAuthorsByName(searchString);
			for (Author a : authors) {
				a.setBooks(bdao.searchBookByAuthorId(a));
			}
			return new ResponseEntity<Object>(authors,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "Author/{authorId}", method = RequestMethod.GET, produces = "application/json")
	@Transactional
	public ResponseEntity<Object> getAuthorByPk(@PathVariable ("authorId")Integer authorId) {
		Author author =null;
		
		try {
			author= adao.getAuthorByPk(authorId);
			return new ResponseEntity<Object>(author,HttpStatus.OK);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "Book/{bookId}", method = RequestMethod.DELETE, consumes = "application/json")
	@Transactional
	public ResponseEntity<Object> deleteBook(@PathVariable("bookId") Integer bookId) {
		try {
			bdao.deleteBook(bookId);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Book/"+bookId));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "Book", method = RequestMethod.POST, consumes = "application/json")
	@Transactional
	public ResponseEntity<Object> createBook(@RequestBody Book book) {
		try {
			bdao.createBook(book);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Book"));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "Book", method = RequestMethod.PUT, consumes = "application/json")
	@Transactional
	public ResponseEntity<Object> updateBook(@RequestBody Book book) {
		try {
			bdao.updateBook(book);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("Book"));
			return new ResponseEntity<Object>(headers,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "Book/{bookId}", method = RequestMethod.GET, produces = "application/json")
	@Transactional
	public ResponseEntity<Object> getBookByPk(@PathVariable Integer bookId) {
		Book book=null;
		try {
			book=bdao.getBookByPK(bookId);
			return new ResponseEntity<Object>(book,HttpStatus.OK);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value ="Books", method = RequestMethod.GET, produces = "application/json")
	@Transactional
	public ResponseEntity<Object>  listOfBooks(){
		List<Book>books = new ArrayList<>();
		
		try {
			books= bdao.readBooks();
			for(Book b:books) {
				b.setAuthors(adao.getAuthorsWithBookid(b.getBookId()));	
				b.setGenres(gdao.getGenresWithBookid(b.getBookId()));
				//b.setPublisher(pdao.getPublisherByBook(b.getBookId()));
			}
			return new ResponseEntity<Object>(books,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "Books/{searchString}", method = RequestMethod.GET, produces = "application/json")
	@Transactional
	public ResponseEntity<Object>  searchBookByTitle(@PathVariable ("searchString")String searchString){
		List<Book>books = new ArrayList<>();
		
		try {
			books= bdao.searchBookByTitle(searchString);
			return new ResponseEntity<Object>(books,HttpStatus.OK);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*// everything about branches follow below
	@Transactional
	@RequestMapping(value = "createNewBranch", method = RequestMethod.POST, consumes = "application/json")
	public void createNewBranch(@RequestBody Branch branch) {

		try {
			bradao.createBranch(branch);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "listBranchs", method = RequestMethod.GET, produces = "application/json")
	public List<Branch> getAllBranchs() {
		List<Branch> branchs = new ArrayList<>();
		try {
			branchs = bradao.readBranchs();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return branchs;
	}

	@RequestMapping(value = "updateBranchInfo", method = RequestMethod.POST, consumes = "application/json")
	@Transactional
	public void updateBranchInfo(@RequestBody Branch branch) {
		try {
			bradao.updateBranch(branch);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "readBranchByName/{search}", method = RequestMethod.GET, produces = "application/json")
	public List<Branch> searchBranchByName(@PathVariable("search") String search) {
		List<Branch> branchs = new ArrayList<>();
		try {
			branchs = bradao.SearchBranchByName(search);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return branchs;
	}

	@Transactional
	@RequestMapping(value = "deleteBranch", method = RequestMethod.POST, consumes = "application/json")
	public void deleteBranch(@RequestBody Branch branch) {

		try {
			bradao.deleteBranch(branch);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional
	@RequestMapping(value = "totalBranchs", method = RequestMethod.GET, produces = "application/json")
	public Integer totalNumberOfBranchs() {

		Integer total = 0;
		try {
			total = bradao.getBranchesCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}

	@Transactional
	@RequestMapping(value = "listOfBooksByBranchId/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public List<Book> listOfBooksByBranchId(@PathVariable("branchId") int branchId) {
		List<Book> books = new ArrayList<>();
		try {
			books = bdao.getBooksFromBranchId(branchId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return books;
	}*/

	/*@Transactional
	@RequestMapping(value = "getAvailableBookCopie/{branchId}/{bookId}", method = RequestMethod.GET, produces = "application/json")
	public Integer getAvailableBookCopie(@PathVariable int branchId, @PathVariable int bookId) {
		Integer totals = 0;
		try {
			totals = bcdao.countBookCopies(branchId, bookId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return totals;
	}*/

	/*// needed to be improved
	@Transactional
	@RequestMapping(value = "addBookCopies", method = RequestMethod.POST, consumes = "application/json")
	public void addBookCopies(@RequestBody BookCopies bookCopie) {
		try {
			bcdao.updateBookCopies(bookCopie);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Override due date input bookId, branchId and cardNo
	@RequestMapping(value = "overideDueDate", method = RequestMethod.POST, consumes = "application/json")
	@Transactional
	public void overrideDueDate(@RequestBody BookLoans bookLoan) {

		try {
			bldao.overrideDueDate(bookLoan);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	/*@RequestMapping(value = "updateAuthor", method = RequestMethod.POST, consumes = "application/json")
	@Transactional
	public void updateAutho(@RequestBody Author author) throws SQLException {
		try {
			if (author.getAuthorId() != null && author.getAuthorName() != null) {
				adao.updateAuthor(author);
			} else if (author.getAuthorId() == null && author.getAuthorName() != null) {
				adao.createAuthor(author);
			} else {
				adao.deleteAuthor(author);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}*/	

	/*@RequestMapping(value ="listOBooks", method = RequestMethod.GET, produces = "application/json")
	@Transactional
	public List<Book>listOBooks(){
		List<Book>books = new ArrayList<>();
		
		try {
			books= bdao.readBooks();
			for(Book b:books) {
				b.setAuthors(adao.getAuthorsWithBookid(b.getBookId()));	
			}
			for(Book b:books) {
				b.setGenres(gdao.getGenresWithBookid(b.getBookId()));	
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return books;
	}*/
	
	/*@RequestMapping(value = "updateBook", method = RequestMethod.POST, consumes = "application/json")
	@Transactional
	public void updateBook(@RequestBody Book book) throws SQLException {

		try {

			if (book.getBookId() != null && book.getTitle() != null) {
				bdao.updateBook(book);
				bdao.deleteBookAuthor(book);
				if (book.getAuthors() != null) {
					bdao.updateBookAuthor(book);
				}
				// call update book authors
				if(book.getGenres()!=null) {
					bdao.updateBookGenres(book);
				}
				// caa update book genres
			} else if (book.getBookId() == null && book.getTitle() != null) {
				Integer bookId = bdao.createBookWithPK(book);
				book.setBookId(bookId);
				if (book.getAuthors() != null) {
					bdao.updateBookAuthor(book);
				}
				// call update book authors
				if(book.getGenres()!=null) {
					bdao.updateBookGenres(book);
				}
				
			} else {
				bdao.deleteBook(book);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

		}
	}*/
	
}
