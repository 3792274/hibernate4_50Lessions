package test.lifecycle;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import lifecycle.entity.User;
import lifecycle.utils.HibernateUtil;


/**
	瞬时状态session中没有，数据库没有
	持久状态：session中有，数据库有
	游离状态： Session中没有，数据库中有
	
	get/load:性格特定
		1.get,无声无息的快：立即发送sql,查不到数据返回NULL，不提示错误不抛异常
		2.load,慢悠悠事多：不立刻发送sql,当使用的时候发送SQL,查不到数据抛异常。
		
	
	
 */
public class TestLifecycle {
	
	
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
			user.setUsername("刘德华");
			user.setPassword("2222");
			//持久状态，user被session管理，并且id有值--oid
			session.save(user); //立刻发送SQL
			user.setUsername("学友"); 
			
			session.flush();  //执行脏数据检查,立刻发送SQL,脏数据检查(数据库中和缓存中不一致)
			tx.commit();//当提交事务，清理缓存时发现session中数据
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally{
			HibernateUtil.closeSession();
		}
		
		
		//另一个Session，之前的user:游离状态
		System.out.println("姓名："+user.getUsername());
		user.setUsername("朝伟");
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			session.update(user); //持久状态，不会立刻发送SQl，等到Commit时候发送
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
			
			//get方法会立即查询数据(session--数据库)
			user = (User)session.get(User.class, 1) ; //如果get查询的数据不存在，那么返回null,不抛异常
			System.out.println(user);
			
			//session.clear(); //会清除session缓存中所有对象
			//session.evict(user);//会清除指定对象
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally{
			HibernateUtil.closeSession();
		}
		 
	}

	
	
	
	
	
	@Test
	public void testLoad(){
		Session session=null;
		Transaction tx=null;
		User user=null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			//如果load查询的数据不存在，抛异常， No row with the given identifier exists:
			user = (User)session.load(User.class,6) ; 
			System.out.println("姓名："+user.getUsername());//使用数据的时候才发送SQL
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	
	
	
	
	
	
	
	
	@Test
	public void testDelete(){
		Session session=null;
		Transaction tx=null;
		User user=null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			//手动构建对象 并指定id可以从数据库删除
			//如果指定id不存在 将抛异常
			user = new User();
			user.setId(5);
			user.setUsername("不管谁");
			session.delete(user); //通过id删除,如果id不存在，则提交事务的时候会抛出异常
			
			//应该从数据库先加载后删除，好处如果id不存在就会抛出异常，可以先判断下,不抛异常，返回空对象
			User u2= (User)session.get(User.class, 1111);
			if(u2!=null)
				session.delete(u2);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}finally{
			//关闭session
			HibernateUtil.closeSession();
		}
	}
	
	

	
	
	
	
	@Test
	public void testUpdate(){
		Session session=null;
		Transaction tx=null;
		User user=null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			//手动创建对象 可以被更新，需要指定id 当指定id不存在时 会抛异常
			user = new User();
			user.setId(10);
			user.setUsername("paul");
			
			session.update(user);
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
