package test.helloHibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import helloHibernate.entity.User;

//helloworld测试 ，user
public class TestUser_HelloWorld { 
	
	
public static void main(String[] args) {
		
		//1.新建Configuration对象，单例
		//Configuration cfg = new Configuration()，默认读取src下的hibernate.cfg.xml ，不推荐
		 //Configuration cfg = new Configuration().configure();
		//如果hibernate的核心配置文件 不叫 hibernate.cfg.xml ，.configure();读取指定的hibernate核心配置文件
		Configuration cfg = new Configuration().configure("helloHibernate/resources/hibernate.cfg.xml");
		//可编程式  可以不使用配置文件，不推荐
		//cfg.addProperties();
		//cfg.addResource() // xml映射文件
		
		
		
		
		//2.通过Configuration创建SessionFactory对象,进程级，线程安全
			//在hibernate3.x中是这种写法
			//SessionFactory sf = cfg.buildSessionFactory();
		//hibernate4.3之前~hibernate4.0
 		//ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		//hibernate4.3
		ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
		
		SessionFactory sf = cfg.buildSessionFactory(registry);
		
		
		
		
		//3.通过SessionFactory得到Session，相当于JDBC Connection，线程不安全，短暂使用
		Session session = sf.openSession();
		
		
		//4.通过session对象 得到Transaction对象
		//开启事务
		//第一种写法
		Transaction tx1 = session.beginTransaction();
		//第二种写法,不可以开启2个事务，nested transactions not supported
		//Transaction tx2 = session.getTransaction();
		//tx2.begin();
		
		
		//5.保存数据,如果数据库乱码，检查mysql.ini编码配置
		User user = new User();
		user.setUsername("小风");
		user.setPassword("1111");
		//session.save(user);
		
		//获取对象
		User u  = (User)session.get(User.class, 2);
		System.out.println("name="+u.getUsername());
		
		
		//6.提交事务,查询操作也建议使用事务
		tx1.commit();
		
		
		//7.关闭session
		session.close();
		
		//关闭重量级sessionfactory
		sf.close();
	}
}
