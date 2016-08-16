package struts2Hibernate.dao.impl;

import java.util.List;

import org.hibernate.Session;

import struts2Hibernate.dao.CategoryDao;
import struts2Hibernate.pojo.Category;
import struts2Hibernate.util.HibernateSessionFactory;

public class CategoryDaoImpl implements CategoryDao{
	
	public List<Category> getList() {
		Session session = HibernateSessionFactory.getSession();
		return session.createCriteria(Category.class).list();
	}
}
