package annotations.double_many_to_one;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

public class TestSingle_many_to_one {


	@Test
	public void testCreateDB(){                            
		Configuration cfg = new Configuration().configure("annotations/double_many_to_one/hibernate.cfg.xml");
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
		
		Category category = new Category();
		category.setName("文学");
		book.setCategory(category);
		
		Transaction tx = session.beginTransaction();
		session.save(book);
		tx.commit();
		
		HibernateUtil.closeSession();
	}
	
	
	
	@Test
	public void testGet(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
	
		Book book = (Book)session.get(Book.class, 1);
		System.out.println(book.getName()+"---"+book.getCategory().getName());
		
		tx.commit();
		HibernateUtil.closeSession();
	}


}
