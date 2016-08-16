package openSessionInView.dao;

import java.util.List;

import org.hibernate.Session;

import openSessionInView.pojo.User;
import openSessionInView.util.HibernateSessionFactory;

public class UserDao {
	@SuppressWarnings("unchecked")
	public List<User> getAll(){
		Session session=HibernateSessionFactory.getSession();
		return session.createQuery("from User").list();
	}
}
