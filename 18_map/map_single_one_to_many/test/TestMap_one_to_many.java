package map_single_one_to_many.test;

import java.sql.SQLException;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import map_single_one_to_many.pojo.Grade;
import map_single_one_to_many.pojo.Student;

 
/**
 	    使用Map,单向，一对多 ，1-greade  -> n-student
 
 
 */
public class TestMap_one_to_many {

	@Test//创建表结构
	public void testCreateDB(){
		Configuration cfg = new Configuration().configure("map_single_one_to_many/resources/hibernate.cfg.xml");
		SchemaExport se = new SchemaExport(cfg);
		//第一个参数 是否生成ddl脚本  第二个参数  是否执行到数据库中
		se.create(true, true);
	}

	
 
	
	@Test
	public void testSave() throws HibernateException, SerialException, SQLException{
		Configuration cfg = new Configuration().configure("map_single_one_to_many/resources/hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory(new StandardServiceRegistryBuilder()
		.applySettings(cfg.getProperties()).build());
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Grade grade = new Grade();
			grade.setName("基础");
			
			Student stu = new Student();
			stu.setName("张三疯");
			stu.setAge(22);
			
			Student stu1 = new Student();
			stu1.setName("老王");
			stu1.setAge(23);
			
			Student stu2 = new Student();
			stu2.setName("老李");
			stu2.setAge(23);
			
			//关联
			grade.getStudents().put(stu.getName(), stu);
			grade.getStudents().put(stu1.getName(), stu1);
			grade.getStudents().put(stu2.getName(), stu2);
			
			//保存数据的顺序 是根据外键的配置来决定的
			//如果外键不能为null,那么先保存一的一端
			//如果外键可以为null,则可以随意保存
			session.save(grade);
			
			session.save(stu);
			session.save(stu1);
			session.save(stu2);
			
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
			
			//取数据
			Grade grade = (Grade)session.get(Grade.class, 1);
			System.out.println("gradeName="+grade.getName());
			System.out.println("grade所对应的多的一端的数据");
		
			Map<String,Student> map = grade.getStudents();
			System.out.println(map.get("老王").getAge());  //通过学生名，取学生
			
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
