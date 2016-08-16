package inverse.test;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import inverse.pojo.Grade;
import inverse.pojo.Student;

 
/**
	inverse
 	   1.默认应该设置在 1的一端<set inverse="true">
 	   2.当inverse=”false”时，谁都可以管理关系谁维护。如果都管理了，那么都维护。
 	   3.当关系由多的一端来维护时，效率较高。当关系由一的一端来维护时。会多执行update语句。
	   4.Inverse=”true”时，关系由另一端管理。不管一的一端是否指明关系，一的一端都不会去维护关系。都由多的一端负责。建议inverse=”true”.
 	   
 */
public class TestInverse {

	@Test//创建表结构
	public void testCreateDB(){
		Configuration cfg = new Configuration().configure("inverse/resources/hibernate.cfg.xml");
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
			
			Student stu = new Student();
			stu.setName("张三疯");
			stu.setAge(22);
			stu.setGrade(grade);
			
			Student stu1 = new Student();
			stu1.setName("老王");
			stu1.setAge(23);
			stu1.setGrade(grade);
			
//			grade.getStudents().add(stu);
//			grade.getStudents().add(stu1);
			
//			session.save(grade);
			session.save(stu);
			session.save(stu1);
			
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
			session.delete(grade);
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
