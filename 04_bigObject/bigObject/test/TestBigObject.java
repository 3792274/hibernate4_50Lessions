package bigObject.test;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import bigObject.pojo.Student;
import bigObject.test.HibernateUtil;

/**
 *  数据库插入2进制数据
 */
public class TestBigObject { 
	
	
 
	@Test
	public void testSave() throws HibernateException, SerialException, SQLException{
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			Student stu = new Student();
			stu.setName("尹志平");
			stu.setAge(23);
			
			Blob blob = new SerialBlob("ttt".getBytes());  //构建要插入的二进制数据，存放大数据 可以存放4G的内容
			Clob clob = new SerialClob("sss".toCharArray());
		
			stu.setImage(blob);
			stu.setIntroduce(clob);
			
			session.save(stu);
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
