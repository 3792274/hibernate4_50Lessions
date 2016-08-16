package single_one_to_many.test;

import java.sql.SQLException;
import java.util.Iterator;

import javax.sql.rowset.serial.SerialException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import single_one_to_many.pojo.Grade;
import single_one_to_many.pojo.Student;

 
/**
 * 单向一对多：Grade(1)   ->   Student(n)
 * 数据表结构为：Student表中有一个外键列greadeId,引用自greade表greadeID
 * 
 */
public class TestSingle_one_to_many {

	@Test//创建表结构
	public void testCreateDB(){
		Configuration cfg = new Configuration().configure("single_one_to_many/resources/hibernate.cfg.xml");
		SchemaExport se = new SchemaExport(cfg);
		//第一个参数 是否生成ddl脚本  第二个参数  是否执行到数据库中
		se.create(true, true);
	}

	
	
	
	@Test//先保存grade后，再保存student,会先执行插入grade,student,然后会再更新student,多出2条sql
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
			
			Student stu1 = new Student();
			stu1.setName("老王");
			stu1.setAge(23);
		
			//关联
			grade.getStudents().add(stu);
			grade.getStudents().add(stu1);
			
			//保存数据的顺序 是根据外键的配置来决定的
			//如果外键不能为null,那么先保存一的一端
			//如果外键可以为null,则可以随意保存
			session.save(grade);
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

	
	
	
	
	
	
	
	@Test//查询1的一端grade，默认不会级联查询多的一端student
	public void testGet(){
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			//取数据
			Grade grade = (Grade)session.get(Grade.class, 1);
			System.out.println("gradeName="+grade.getName());
			
			System.out.println("grade所对应的多的一端的数据-----------------------------");
			Iterator<Student> iter = grade.getStudents().iterator();
			for(;iter.hasNext();){
				Student temp = iter.next();
				System.out.println("name="+temp.getName()+"\tage="+temp.getAge());
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
