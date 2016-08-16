package annotations.helloworld;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;
 

/**
 * 1.hibernate4之前使用注解需要单独加Jar.hibernate-commons-annotation/hibernate-jpa
 */
public class TestHelloWorld {

	@Test //生成数据表
	public void testCreateDB(){
		
		//3.0以前使用
		//Configuration cfg = new AnnotationConfiguration().configure("annotations/helloworld/hibernate.cfg.xml");
		
		//4.0
		Configuration cfg = new Configuration().configure("annotations/helloworld/hibernate.cfg.xml");
		SchemaExport se = new SchemaExport(cfg);
		se.create(true, true);
	}

	
	
	
	@Test
	public void testSave(){
		Session session = HibernateUtil.getSession();
		
		Book book = new Book();
		book.setName("丰乳肥臀");
		book.setPrice(60.5);
		book.setAuthor("莫言");
		book.setPubDate(new Date());
		
		Transaction tx = session.beginTransaction();
		session.save(book);
		tx.commit();
		
		HibernateUtil.closeSession();
	}
}
