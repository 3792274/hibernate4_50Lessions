package lazy.test;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import lazy.pojo.Book;


public class TestLazy_1 {
	
	
	@Test
	public void testCreateDB() {
		Configuration cfg = new Configuration().configure("lazy/resources/hibernate.cfg.xml");
		SchemaExport se = new SchemaExport(cfg);
		se.create(true, true);
	}

	
	
	@Test
	public void testSave() {
		Session session = HibernateUtil.getSession();
		
		Book book = new Book();
		book.setName("丰乳肥臀");
		book.setPrice(60.5);
		book.setAuthor("莫言");
		book.setPubDate(new Date());

		Book book1 = new Book();
		book1.setName("傲慢与偏见");
		book1.setPrice(80);
		book1.setAuthor("简.奥斯汀");
		book1.setPubDate(new Date());

		Book book2 = new Book();
		book2.setName("中国历史");
		book2.setPrice(30);
		book2.setAuthor("人民");
		book2.setPubDate(new Date());

		Book book3 = new Book();
		book3.setName("飘渺之旅");
		book3.setPrice(70);
		book3.setAuthor("萧鼎");
		book3.setPubDate(new Date());
		Book book4 = new Book();
		book4.setName("蓝血人");
		book4.setPrice(60);
		book4.setAuthor("卫斯里");
		book4.setPubDate(new Date());
		Book book5 = new Book();
		book5.setName("我的大学");
		book5.setPrice(60.5);
		book5.setAuthor("高尔基");
		book5.setPubDate(new Date());

		Transaction tx = session.beginTransaction();
		session.save(book);
		session.save(book1);
		session.save(book2);
		session.save(book3);
		session.save(book4);
		session.save(book5);
		tx.commit();
		HibernateUtil.closeSession();
	}

	@Test //会将结果缓存到sesion中，立即查询
	public void testGet() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		Book book = (Book) session.get(Book.class, 1);
		System.out.println("书名：" + book.getName());
		
		tx.commit();
		HibernateUtil.closeSession();
		
		System.out.println("书名：" + book.getName());
	}

	
	@Test  //load默认使用懒加载，当使用的时候发送SQL
	public void testLoad() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Book book = (Book) session.load(Book.class, 1);
		tx.commit();
		HibernateUtil.closeSession();
		// open session in view
		System.out.println("书名：" + book.getName());
	}
}
