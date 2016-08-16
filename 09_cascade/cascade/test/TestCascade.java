package cascade.test;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import cascade.pojo.Grade;
import cascade.pojo.Student;

 
/**
 * 双向一对多：Grade(1)   ->   Student(n)
 * 数据表结构为：Student表中有一个外键列greadeId,引用自greade表greadeID，并外键非空约束
 * 维护关联关系应在多的一方，既多的一方做的事情比较多
 * 
 * 级联操作，对任一一端的操作会传递到另一端
 * 
 * 
 * 
		1、cascade时级联操作，使得在操作一端数据时，可以级联操作被关联的另外一端的数据。
		2、	在多对一的关系中，多的一端不能操作级联为delete。一般在多的一端设为save-update.
		3、	在一对多的关系中，如果一的一端设置为delete时，多的一端不能指明外键为非空。

 */
public class TestCascade {

	@Test//创建表结构
	public void testCreateDB(){
		Configuration cfg = new Configuration().configure("cascade/resources/hibernate.cfg.xml");
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
		
			Grade grade = new Grade();
			grade.setName("基础");
			
			Student stu1 = new Student();
			stu1.setName("张三疯");
			stu1.setAge(22);
			stu1.setGrade(grade);
			
			
			Student stu2 = new Student();
			stu2.setName("老王");
			stu2.setAge(23);
			stu2.setGrade(grade);
			
//			grade.getStudents().add(stu1);  //一的一端增加级联删除，要指明关联关系
//			grade.getStudents().add(stu2);
//			session.save(grade);   //当1的一端设置级联all,会自动保存student,会多出两条更新多的一端student的语句
			
			session.save(stu1); //当多的一端设置cascade="save-update"，会级联保存一的一端grade,不会发送多余SQL
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
	public void testDelete(){
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			Grade grade = (Grade)session.get(Grade.class, 1);
			session.delete(grade);  //当1的一端设置级联all,会自动保存删除多的一端，首先将多的一端外键约束设置为null,再删除,多的一端不能指明not-null="true"
			tx.commit();
		}catch (HibernateException e) {
			if(tx!=null)
				tx.rollback();
			e.printStackTrace();
			//throw e;
		}finally{
			HibernateUtil.closeSession();
		}
	}

}
