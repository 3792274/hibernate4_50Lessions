package extends_everyclass_table.test;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import extends_everyclass_table.pojo.Person;
import extends_everyclass_table.pojo.Teacher;
import extends_everyclass_table.pojo.Student;

 
/**
  		每个具体类一张表
  		1.共3张表，Person,Student,Teacher,相同的东西放在Person中，不同的放在具体每张表中
 */
public class TestExtends_everyclass_table {

	@Test//创建表结构
	public void testCreateDB(){
		Configuration cfg = new Configuration().configure("extends_everyclass_table/resources/hibernate.cfg.xml");
		SchemaExport se = new SchemaExport(cfg);
		//第一个参数 是否生成ddl脚本  第二个参数  是否执行到数据库中
		se.create(true, true);
	}

	
 
	@Test
	public void testSave() throws HibernateException, SerialException, SQLException{
		Configuration cfg = new Configuration().configure("extends_everyclass_table/resources/hibernate.cfg.xml");
		SessionFactory factory = cfg.buildSessionFactory(new StandardServiceRegistryBuilder()
		.applySettings(cfg.getProperties()).build());
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
		
			Teacher teacher = new Teacher();
			teacher.setName("siggy");
			teacher.setAge(26);
			teacher.setId(1);     //hbm.xml配置的自定义主键
			teacher.setSalary(5000);
		
			Student student = new Student();
			student.setId(1);
			student.setName("小明");
			student.setAge(22);
			student.setWork("hello world");
			
			Student student1 = new Student();
			student.setId(2);
			student1.setName("小强");
			student1.setAge(20);
			student1.setWork("struts2");
			
			session.save(student);
			session.save(student1);
			
			session.save(teacher);
			
			
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
			
			Person person = (Person)session.get(Person.class, 2);
			System.out.println(person.getName());
			
			if(person instanceof Student){ //通过get()查询数据，可以判断类型
				Student stu = (Student)person;
				System.out.println(stu.getWork());
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
	public void testLoad(){
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			Person person = (Person)session.load(Person.class, 2);
			System.out.println(person.getName());
			
			if(person instanceof Student){ //通过load()查询数据，不能判断对象类型，load出的是Person的代理类
				Student stu = (Student)person;
				System.out.println(stu.getWork());
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
	
}
