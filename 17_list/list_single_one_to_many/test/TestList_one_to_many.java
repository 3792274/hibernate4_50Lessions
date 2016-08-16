package list_single_one_to_many.test;

import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import list_single_one_to_many.pojo.Grade;
import list_single_one_to_many.pojo.Student;

 
/**
 	 使用List,单向，一对多 ，1-greade  -> n-student
 	 1、保存数据，因为list有序的，保存后，需要更新序号， update Student set  grade_id=?, sort=?   where sid=?
 	 
 	 2、级联删除 Grade
		1.查询Grade
		2.查询Student,where student.grade_id = GradeId
		3.update  Student  set grade_id=null,  sort=null  where grade_id=?
		4.delete   from  Student   where  sid=?		
	3、不能级联删除Student,顺便把Grade删除，Student中其他Student还有外键引用自Grade的
		
 
 
 */
public class TestList_one_to_many {

	@Test//创建表结构
	public void testCreateDB(){
		Configuration cfg = new Configuration().configure("list_single_one_to_many/resources/hibernate.cfg.xml");
		SchemaExport se = new SchemaExport(cfg);
		//第一个参数 是否生成ddl脚本  第二个参数  是否执行到数据库中
		se.create(true, true);
	}

	
 
	
	
	@Test
	public void testSave() throws HibernateException, SerialException, SQLException{
		Configuration cfg = new Configuration().configure("list_single_one_to_many/resources/hibernate.cfg.xml");
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
			grade.getStudents().add(stu);
			grade.getStudents().add(stu1);
			grade.getStudents().add(stu2);
			
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
			
			List<Student> list = grade.getStudents();
			for(Student stu:list){
				System.out.println(stu.getName());
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
			
//			//删除grade,若配置级联，将会删除成功，不过SQL是：
//			Grade grade = (Grade)session.get(Grade.class, 1);
//			session.delete(grade); 
			
			//删除student,若配置级联，将会删除成功，不过SQL是：
			Student student = (Student)session.get(Student.class, 4);
			session.delete(student); 
			
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
