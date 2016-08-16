package struts2Hibernate.action;

import java.util.List;

import struts2Hibernate.pojo.Book;
import struts2Hibernate.pojo.Category;
import struts2Hibernate.service.BookService;
import struts2Hibernate.service.CategoryService;
import struts2Hibernate.service.impl.BookServiceImpl;
import struts2Hibernate.service.impl.CategoryServiceImpl;

public class BookAction {
	private List<Book> bookList;
	private Book book;
	private List<Category> categoryList;
	private BookService bookService = new BookServiceImpl();
	private CategoryService categoryService = new CategoryServiceImpl();
	
	public String list(){
		bookList = bookService.getList(book);
		categoryList =categoryService.getList(); 
		return "success";
	}
	
	
	
	public List<Book> getBookList() {
		return bookList;
	}
	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public List<Category> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	public BookService getBookService() {
		return bookService;
	}
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	public CategoryService getCategoryService() {
		return categoryService;
	}
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
}
