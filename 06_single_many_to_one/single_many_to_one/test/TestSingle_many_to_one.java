package single_many_to_one.test;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import single_many_to_one.pojo.Grade;
import single_many_to_one.pojo.Student;

/**
 * 单向多对一：Student(n) -> Grade(1)
 * 数据表结构为：Student表中有一个外键列greadeId,引用自greade表greadeID
 */
public class TestSingle_many_to_one {

	@Test
	public void testSave() throws HibernateException, SerialException, SQLException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			Grade grade = new Grade();  //创建并保存1端——greade
			grade.setName("基础");
			session.save(grade);
		
			Student stu = new Student(); //创建n端并保存 ——student
			stu.setName("张三疯");
			stu.setAge(22);
			stu.setGrade(grade);
			session.save(stu);

			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
	}

	@Test
	public void testGet() {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
		
			// 取数据
			Student stu = (Student) session.get(Student.class, 1);//默认只会发送查询Student的SQL
			System.out.println("student——name:" + stu.getName() );
			System.out.println("--------------------------------");
			System.out.println("--grade-name:" + stu.getGrade().getName()); //默认当使用Grade的时候才发送SQL
		
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			HibernateUtil.closeSession();
		}
	}

}
