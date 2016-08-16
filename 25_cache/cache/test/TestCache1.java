package cache.test;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import cache.pojo1.Book;


/**
 * Hibernate 1级缓存
 */
public class TestCache1 {
	@Test
	public void testCreateDB() {
		Configuration cfg = new Configuration().configure("cache/resources/hibernate.cfg1.xml");
		SchemaExport se = new SchemaExport(cfg);
		se.create(true, true);
	}

	@Test
	public void testSave() {
		Session session = HibernateUtil1.getSession();
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
		HibernateUtil1.closeSession();
	}

	
	/***
	 * flush()方法进行清理缓存的操作，执行一系列的SQL语句，但不会提交事务;
	 * commit()方法会先调用flush()方法，然后提交事务。提交事务意味着对数据库所做的更新被永久保存下来。
	 */
	@Test
	public void testSaveBatch() {
		Session session = HibernateUtil1.getSession();
		Transaction tx = session.beginTransaction();
		
		for (int i = 0; i < 10000; i++) {
			Book book = new Book();
			book.setName("小小" + i);
			book.setPrice(60.5);
			book.setAuthor("莫言");
			book.setPubDate(new Date());
			if (i % 10 == 0){
//				session.setFlushMode(FlushMode.MANUAL);
				session.flush();  //大量数据如不及时更新到数据库，session中对象会占用大量内存
			}
			session.save(book);
			
		}

		tx.commit();
		HibernateUtil1.closeSession();
	}

	@Test
	public void testEvict() {
		Session session = HibernateUtil1.getSession();
		Transaction tx = session.beginTransaction();
		Book book = (Book) session.get(Book.class, 1);
		// 发出sql语句取数据
		System.out.println(book.getName());
		session.clear();  //会清空session中对象
		// session.evict(book);
		System.out.println("---------");
		book = (Book) session.get(Book.class, 1);
		System.out.println(book.getName());
		tx.commit();
		HibernateUtil1.closeSession();
	}

	@Test
	public void testGet() {
		Session session = HibernateUtil1.getSession();
		Transaction tx = session.beginTransaction();
		Book book = (Book) session.get(Book.class, 1);
		// 发出sql语句取数据
		System.out.println(book.getName());
		System.out.println("---------");
		book = (Book) session.get(Book.class, 1);
		System.out.println(book.getName());
		tx.commit();
		HibernateUtil1.closeSession();
	}

	@Test
	public void testLoad() {
		Session session = HibernateUtil1.getSession();
		Transaction tx = session.beginTransaction();
		Book book = (Book) session.load(Book.class, 1);
		// 发出sql语句取数据
		System.out.println(book.getName());
		System.out.println("---------");
		book = (Book) session.load(Book.class, 1);
		System.out.println(book.getName());
		tx.commit();
		HibernateUtil1.closeSession();
	}

	@Test
	public void testGetLoad() {
		Session session = HibernateUtil1.getSession();
		Transaction tx = session.beginTransaction();
		Book book = (Book) session.get(Book.class, 1);
		// 发出sql语句取数据
		System.out.println(book.getName());
		session.close();
		session = HibernateUtil1.getSession();
		System.out.println("---------");
		book = (Book) session.load(Book.class, 1);
		System.out.println(book.getName());
		tx.commit();
		HibernateUtil1.closeSession();
	}

	@Test //list不会查询缓存
	public void testList() {
		Session session = HibernateUtil1.getSession();
		Transaction tx = session.beginTransaction();

		List<Book> list = session.createQuery("from Book").list();
		System.out.println("条数：" + list.size());
		System.out.println("----------------------");
		list = session.createQuery("from Book").list();
		System.out.println("条数：" + list.size());
		tx.commit();
		HibernateUtil1.closeSession();
	}

	@Test //list查询出的数据会放入缓存，供其他人调用
	public void testList1() {
		Session session = HibernateUtil1.getSession();
		Transaction tx = session.beginTransaction();

		List<Book> list = session.createQuery("from Book").list();
		System.out.println("条数：" + list.size());
		System.out.println("----------------------");
		Book book = (Book) session.get(Book.class, 2);
		System.out.println("书名:" + book.getName());
		tx.commit();
		HibernateUtil1.closeSession();
	}

	@Test//带参数的uniqueResult查询不会使用List缓存
	public void testList2() {
		Session session = HibernateUtil1.getSession();
		Transaction tx = session.beginTransaction();

		List<String> list = session.createQuery("select name from Book").list();
		System.out.println("条数：" + list.size());
		System.out.println("----------------------");
		Object bookName = session.createQuery("select name from Book where id=:id").setInteger("id", 2).uniqueResult();
		System.out.println("书名:" + bookName);
		tx.commit();
		HibernateUtil1.closeSession();
	}

	@Test //uniqueResult会将结果放入缓存中
	public void testUnique1() {
		Session session = HibernateUtil1.getSession();
		Transaction tx = session.beginTransaction();

		Book book = (Book) session.createQuery("from Book where id=:id").setInteger("id", 2).uniqueResult();
		System.out.println("书名:" + book.getName());
		System.out.println("---------------");
		book = (Book) session.get(Book.class, 2);
		System.out.println("书名:" + book.getName());
		tx.commit();
		HibernateUtil1.closeSession();
	}

	
	@Test //uniqueResult永远会向数据库查询
	public void testUnique2() {
		Session session = HibernateUtil1.getSession();
		Transaction tx = session.beginTransaction();

		Book book = (Book) session.createQuery("from Book where id=:id").setInteger("id", 2).uniqueResult();
		System.out.println("书名:" + book.getName());
		System.out.println("---------------");
		book = (Book) session.createQuery("from Book where id=:id").setInteger("id", 2).uniqueResult();
		System.out.println("书名:" + book.getName());
		tx.commit();
		HibernateUtil1.closeSession();
	}

	@Test //Iterator会使用List的缓存，只会查询所有ID
	public void testIterate() {
		Session session = HibernateUtil1.getSession();
		Transaction tx = session.beginTransaction();

		List<Book> list = session.createQuery("from Book").list();
		System.out.println("条数：" + list.size());
		System.out.println("----------------------");
		Iterator<Book> iter = session.createQuery("from Book").iterate();
		for (; iter.hasNext();) {
			System.out.println(iter.next().getName());
		}
		tx.commit();
		HibernateUtil1.closeSession();
	}

	@Test//iterator查询结果会放入缓存。
	public void testIterate1() {
		Session session = HibernateUtil1.getSession();
		Transaction tx = session.beginTransaction();

		Iterator<Book> iter = session.createQuery("from Book").iterate();
		for (; iter.hasNext();) {
			System.out.println(iter.next().getName());
		}
		System.out.println("----------------------");
		iter = session.createQuery("from Book").iterate();
		for (; iter.hasNext();) {
			System.out.println(iter.next().getName());
		}
		tx.commit();
		HibernateUtil1.closeSession();
	}

}
