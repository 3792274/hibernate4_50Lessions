package struts2Hibernate.service;

import java.util.List;

import struts2Hibernate.pojo.Book;

public interface BookService {
	public List<Book> getList(Book book);
}
