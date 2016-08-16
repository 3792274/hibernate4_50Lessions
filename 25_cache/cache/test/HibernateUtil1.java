package cache.test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil1 {
	
	private static Configuration configuration = null;
	private static SessionFactory sessionFactory = null;
	private static Session session	= null;
	
	
	static{
		configuration = new Configuration().configure("cache/resources/hibernate.cfg1.xml");
		sessionFactory = configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
	}
	
	
	public static Session getSession(){
		if(sessionFactory!=null)
			return session = sessionFactory.openSession();
		sessionFactory = configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
		return session = sessionFactory.openSession();
	}

	
	public static void closeSession(){
		if(session!=null&&session.isOpen())
			session.close();
//		if(sessionFactory!=null && !sessionFactory.isClosed())
//			sessionFactory.close();
	}
	
}
