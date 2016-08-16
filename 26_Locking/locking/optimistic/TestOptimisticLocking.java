package locking.optimistic;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

/*
 * 乐观锁，会增在数据库中增加一个Version列
 * 
 * */
public class TestOptimisticLocking {



	@Test
	public void testCreateDB() {
		Configuration cfg = new Configuration().configure("locking/optimistic/hibernate.cfg.xml");
		SchemaExport se = new SchemaExport(cfg);
		se.create(true, true);
	}

 

	
	
	@Test
	public void testSave(){
		Session session=null;
		Transaction tx=null;
		User user=null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			//构造对象--瞬时状态
			user = new User();
			user.setName("刘德华");
			user.setPwd("2222");
			//持久状态，user被session管理，并且id有值--oid
			session.save(user);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	
	
	
	@Test //Row was updated or deleted by another transaction 
	public void testGet(){
		Session session=null;
		Transaction tx=null;
		User user=null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
		
			user = (User)session.get(User.class, 1);  //在第一个Session中获得对象，并修改,version=1
			System.out.println(user.getName());
			user.setName("siggy");
			
			Session session1 = HibernateUtil.getSession();
			Transaction tx1 = session1.beginTransaction();
			User user1 = (User)session1.get(User.class, 1);
			System.out.println(user1.getName());
			user1.setName("李四");   //在第二个Session中获得同样的对象，version=1 ,并修改提交version=2
			session1.update(user1);
			tx1.commit();
			
			session.update(user);   //提交第一个Session中修改的对象，无法保存，应为version =2 !=1
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally{
			//关闭session
			HibernateUtil.closeSession();
		}
		
	}
	

}
