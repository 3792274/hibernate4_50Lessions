package double_many_to_many.test;

import java.sql.SQLException;
import java.util.Iterator;

import javax.sql.rowset.serial.SerialException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import double_many_to_many.pojo.Function;
import double_many_to_many.pojo.Role;
 
/**
 	  双向多对多
 	  Role中，级联删除只能设置  cascade="save-update" ，不能将role删除，导致中间表和，
 	      中间表对应的function也删除，因为删除function表将导致中间表其他引用function表的数据出现引用外键错误
 */
public class TestDouble_many_to_many {

	@Test//创建表结构
	public void testCreateDB(){
		Configuration cfg = new Configuration().configure("double_many_to_many/resources/hibernate.cfg.xml");
		SchemaExport se = new SchemaExport(cfg);
		//第一个参数 是否生成ddl脚本  第二个参数  是否执行到数据库中
		se.create(true, true);
	}

	
	
	@Test
	public void testSave() throws HibernateException, SerialException, SQLException{
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			Function f1 = new Function("用户管理","user_mag","userAction");
			Function f2 = new Function("角色管理","role_mag","roleAction");
			Function f3 = new Function("系统管理","sys_mag","sysAction");
			Function f4 = new Function("权限管理","prev_mag","prevAction");
		
			Role r1 = new Role();
			r1.setName("admin");
			r1.getFunctions().add(f1);
			r1.getFunctions().add(f2);
			r1.getFunctions().add(f3);
			r1.getFunctions().add(f4);
		
			
			Role r2 = new Role();
			r2.setName("vip");
			r2.getFunctions().add(f1);
			r2.getFunctions().add(f2);
		
			session.save(r1);
			session.save(r2);
			
			tx.commit();
			
		}catch (HibernateException e) {
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	
	
	
	@Test
	public void testGet(){
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			Role role = (Role)session.get(Role.class, 1);
			System.out.println("角色名："+role.getName());
			System.out.println("该角色所对应的权限：");
			
			for(Iterator<Function> iter = role.getFunctions().iterator();
					iter.hasNext();){
				Function func = iter.next();
				System.out.println(func.getName()+"---"+func.getCode());
			}
			
			System.out.println("============================");
			
			Function func = (Function)session.get(Function.class, 4);
			System.out.println("功能名称："+func.getName());
			System.out.println("该功能所对应的角色：");
			
			for(Iterator<Role> iter = func.getRoles().iterator();iter.hasNext();){
				System.out.println(iter.next().getName());
			}

			tx.commit();
		}catch (HibernateException e) {
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			HibernateUtil.closeSession();
		}
	}
	@Test
	public void testDelete(){
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			Role role = (Role)session.get(Role.class, 2);
			session.delete(role);
			
			tx.commit();
		}catch (HibernateException e) {
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	
}
