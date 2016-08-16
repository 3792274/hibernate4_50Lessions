package struts2Hibernate.dao.impl;

import java.util.List;

import org.hibernate.Session;

import struts2Hibernate.dao.BookDao;
import struts2Hibernate.pojo.Book;
import struts2Hibernate.util.HibernateSessionFactory;

public class BookDaoImpl implements BookDao{
	public List<Book> getList(String hql) {
		Session session = HibernateSessionFactory.getSession();
		System.out.println(session);
		return session.createQuery(hql).list();
	}
}
