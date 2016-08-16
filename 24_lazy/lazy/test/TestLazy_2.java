package lazy.test;

import java.util.Date;
import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import lazy.pojo2.Book;
import lazy.pojo2.Category;


public class TestLazy_2 {
	
	
	@Test
	public void testCreateDB() {
		Configuration cfg = new Configuration().configure("lazy/resources/hibernate.cfg2.xml");
		SchemaExport se = new SchemaExport(cfg);
		se.create(true, true);
	}

	

	
	@Test
	public void testSave(){
		Session session = HibernateUtil2.getSession();
		
		Category category = new Category("文学");
		Category category1 = new Category("历史");
		Category category2 = new Category("仙侠");
		Category category3 = new Category("科幻");
		Category category4 = new Category("恐怖");
		
		Book book = new Book("丰乳肥臀", 60.5, "莫言", new Date());
		book.setCategory(category);
		
		Book book1 = new Book("丰傲慢与偏见肥臀", 80, "简.奥斯汀", new Date());
		book1.setCategory(category1);
		
		Book book2 = new Book("中国历史", 30, "人民", new Date());
		book2.setCategory(category1);
		
		Book book3 = new Book("飘渺之旅",70, "萧鼎", new Date());
		book3.setCategory(category2);
		
		Book book4 = new Book("蓝血人", 60, "卫斯里", new Date());
		book4.setCategory(category3);
		
		Book book5 = new Book("我的大学", 60.5, "高尔基", new Date());
		book5.setCategory(category);
		
		
		
		Transaction tx = session.beginTransaction();
		session.save(book);
		session.save(book1);
		session.save(book2);
		session.save(book3);
		session.save(book4);
		session.save(book5);
		session.save(category4);  //保存一个空类别
		
		
		tx.commit();
		HibernateUtil2.closeSession();
	}
	@Test
	public void testGet(){
		Session session = HibernateUtil2.getSession();
		Transaction tx = session.beginTransaction();
		Category category = (Category)session.get(Category.class, 1);
		System.out.println("分类名:"+category.getName());
		for(Iterator<Book> iter=category.getBooks().iterator();iter.hasNext();){
			System.out.println(iter.next().getName());
		}
		
		tx.commit();
		HibernateUtil2.closeSession();
	}
	@Test
	public void testLoad(){
		Session session = HibernateUtil2.getSession();
		Transaction tx = session.beginTransaction();
		Category category = (Category)session.load(Category.class, 1);
		System.out.println("分类名:"+category.getName());
		System.out.println("对应的书大小："+category.getBooks().size());
		for(Iterator<Book> iter=category.getBooks().iterator();iter.hasNext();){
			System.out.println(iter.next().getName());
		}
		
		tx.commit();
		HibernateUtil2.closeSession();
	}
	@Test
	public void testLoad1(){
		Session session = HibernateUtil2.getSession();
		Transaction tx = session.beginTransaction();
		Book book = (Book)session.get(Book.class, 1);
		System.out.println(book.getName());
		System.out.println(book.getCategory().getName());
		tx.commit();
		HibernateUtil2.closeSession();
	}
}
