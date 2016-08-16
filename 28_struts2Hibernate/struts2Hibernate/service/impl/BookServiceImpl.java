package struts2Hibernate.service.impl;

import java.util.List;

import struts2Hibernate.dao.BookDao;
import struts2Hibernate.dao.impl.BookDaoImpl;
import struts2Hibernate.pojo.Book;
import struts2Hibernate.service.BookService;

public class BookServiceImpl implements BookService{
	
	private BookDao bookDao = new BookDaoImpl();
	
	public List<Book> getList(Book book) {
		String hql="from Book b where 1=1";
	
		if(book!=null){
			if(book.getName()!=null&&!"".equals(book.getName().trim()))
				hql+="and b.name like '%"+book.getName()+"%'";
			if(book.getCategory().getId()>0){
				hql+="and b.category.id="+book.getCategory().getId();
			}
		}
		return bookDao.getList(hql);
	}
}
