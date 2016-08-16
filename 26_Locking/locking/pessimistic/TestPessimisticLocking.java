package locking.pessimistic;

import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

/*
 * 悲观锁
 * 
 * */
public class TestPessimisticLocking {



	@Test
	public void testCreateDB() {
		Configuration cfg = new Configuration().configure("locking/pessimistic/hibernate.cfg.xml");
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
			
			//在持久状态下；脏数据检查：当提交事务，清理缓存时发现session中数据
			//和数据库中数据不一致时，将会把session中的数据更新到数据库中
			user.setName("学友");
			
			//在保存以后 再修改对象 那么将会产生2条sql语句，那么效率较低，建议在save前修改
			//session.flush(); //commit在提交之前自动执行flush(),
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally{
			HibernateUtil.closeSession();
		}
		
		
		
		
		//user:游离状态
		System.out.println("姓名："+user.getName());
		user.setName("朝伟");
		
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			//持久状态
			session.update(user);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	
	
	
	
	@Test
	public void testGet(){
		Session session=null;
		Transaction tx=null;
		User user=null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			user = (User)session.get(User.class, 1, LockOptions.UPGRADE); //悲观锁，查询对象时候会加锁
			System.out.println(user.getName());
			
			
			user.setName("张三"); //期间其他对象访问不了
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
