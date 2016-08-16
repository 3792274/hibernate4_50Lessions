package test.helloHibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import helloHibernate.entity.User;

/**
	SessionFactory.openSession() 方法可能出现异常，需要处理,完善的方法是使用try catch
	查询也推荐使用事务
*/
public class TestException {
	
	
	public static void main(String[] args) {
		
		Configuration configuration = null;
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction transaction = null;
	
		try{
			configuration = new Configuration().configure("helloHibernate/resources/hibernate.cfg.xml");
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
			  					.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			
			
			User u  = (User)session.get(User.class, 2);
			System.out.println("name="+u.getUsername());
			
			//6.提交事务
			transaction.commit();
			
			
		}catch (Exception e) {
			e.printStackTrace();
			//回滚事务
			transaction.rollback();
			throw new HibernateException(e.getCause());
		}finally{
			//7.关闭session
			if(session!=null&&session.isOpen())
			session.close();
			if(sessionFactory!=null&&!sessionFactory.isClosed())
				sessionFactory.close();
		}
	}
}
