package double_one_to_many.test;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.sql.rowset.serial.SerialException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import double_one_to_many.pojo.Grade;
import double_one_to_many.pojo.Student;

 
/**
 * 双向一对多：Grade(1)   ->   Student(n)
 * 数据表结构为：Student表中有一个外键列greadeId,引用自greade表greadeID，并外键非空约束
 * 维护关联关系在多的一方，既多的一方做的事情比较多
 * 
 */
public class TestDouble_one_to_many {

	@Test//创建表结构
	public void testCreateDB(){
		Configuration cfg = new Configuration().configure("double_one_to_many/resources/hibernate.cfg.xml");
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
			
			//关联
			//保存数据的顺序 是根据外键的配置来决定的
			//如果外键不能为null,那么先保存一的一端
			//如果外键可以为null,则可以随意保存
			session.save(grade);  //会产生3条insert语句
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
		
			System.out.println("===========以下为从一的一端查询=============");
			Grade grade = (Grade)session.get(Grade.class, 1);
			System.out.println("gradeName="+grade.getName());
			
			
			System.out.println("--------------------------------------------");
			System.out.println("grade所对应的多的一端的数据");
			Iterator<Student> iter = grade.getStudents().iterator();//默认查询一的一端，不会自动级联查询多的一端
			for(;iter.hasNext();){
				Student temp = iter.next();
				System.out.println("name="+temp.getName()+"\tage="+temp.getAge());
			}
			
			
			System.out.println("===========以下为从多的一端查询=============");
			Student stu = (Student)session.get(Student.class, 1);
			System.out.println("studentname="+stu.getName());
			System.out.println("-------------------------------------------");
			System.out.println("gradeName="+stu.getGrade().getName());//默认查询多的一端，不会自动级联查询一的一端
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
