package struts2Hibernate.dao;

import java.util.List;

import struts2Hibernate.pojo.Book;

public interface BookDao {
	public List<Book> getList(String hql);
}
