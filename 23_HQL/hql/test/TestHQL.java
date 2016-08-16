package hql.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import hql.pojo.Book;
import hql.pojo.Category;

@SuppressWarnings("unchecked")
public class TestHQL {

	@Test
	public void testCreateDB() {
		Configuration cfg = new Configuration().configure("hql/resources/hibernate.cfg.xml");
		SchemaExport se = new SchemaExport(cfg);
		se.create(true, true);
	}

	@Test
	public void testSave() {
		Session session = HibernateUtil.getSession();

		Category category = new Category();
		category.setName("文学");
		Category category1 = new Category();
		category1.setName("历史");
		Category category2 = new Category();
		category2.setName("仙侠");
		Category category3 = new Category();
		category3.setName("科幻");
		Category category4 = new Category();
		category4.setName("恐怖");
		
		Book book = new Book();
		book.setName("丰乳肥臀");
		book.setPrice(60.5);
		book.setAuthor("莫言");
		book.setPubDate(new Date());
		book.setCategory(category);

		Book book1 = new Book();
		book1.setName("傲慢与偏见");
		book1.setPrice(80);
		book1.setAuthor("简.奥斯汀");
		book1.setPubDate(new Date());
		book1.setCategory(category1);

		Book book2 = new Book();
		book2.setName("中国历史");
		book2.setPrice(30);
		book2.setAuthor("人民");
		book2.setPubDate(new Date());
		book2.setCategory(category1);

		Book book3 = new Book();
		book3.setName("飘渺之旅");
		book3.setPrice(70);
		book3.setAuthor("萧鼎");
		book3.setPubDate(new Date());
		book3.setCategory(category2);
	
		Book book4 = new Book();
		book4.setName("蓝血人");
		book4.setPrice(60);
		book4.setAuthor("卫斯里");
		book4.setPubDate(new Date());
		book4.setCategory(category3);
		
		Book book5 = new Book();
		book5.setName("我的大学");
		book5.setPrice(60.5);
		book5.setAuthor("高尔基");
		book5.setPubDate(new Date());
		book5.setCategory(category);

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

	
	
	
	@Test
	public void testGet() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Book book = (Book) session.get(Book.class, 1);
		System.out.println(book.getName() + "---" + book.getCategory().getName());

		tx.commit();
		HibernateUtil.closeSession();
	}

	

	
	
	
	@Test  // 查询单个属性
	public void testQuery1() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		// Book 大写 表示的是 hql.pojo.Book类
		// name表示的 Book类中的属性名
		String hql = "select name from Book";  // 查询所有书名
		Query query = session.createQuery(hql);// 创建Query对象
		
		List<String> list = query.list();// list()方法返回查询结果,返回结果的类型 是根据查询的列决定的
		for (String bookname : list) {
			System.out.println(bookname);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	

	@Test // 查询多个属性
	public void testQuery2() {
		// 查询所有书 的名称和价格
		// 创建Query对象
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		// 查询多个列时 返回结果是数组集合 数组中元素的类型 是由查询列来决定
		List<Object[]> list = session.createQuery("select name,price from Book").list();// name表示的 Book类中的属性名
		for (Object[] objs : list) {
			System.out.println(objs[0] + "--" + objs[1]);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	
	
	@Test// 查询多个列时 将查询结果封装为对象集合
	public void testQuery3() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.createQuery("select new Book(name,price) from Book").list();
		for (Book b : list) {
			System.out.println(b);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	
	
	@Test // 别名的使用
	public void testQuery4() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.createQuery("select new Book(b.name,b.price) from Book as b").list();
		for (Book b : list) {
			System.out.println(b);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	
	@Test  // 查询所有列
	public void testQuery5() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.createQuery("from Book").list();
		for (Book b : list) {
			System.out.println(b);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	// 查询所有列2 不能使用* 需要使用别名
	@Test
	public void testQuery6() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.createQuery("select b from Book b").list();
		for (Book b : list) {
			System.out.println(b);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	// 条件查询 占位符 从0开始
	@Test
	public void testQuery7() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.createQuery("from Book b where id<?").setInteger(0, 4).list();
		for (Book b : list) {
			System.out.println(b);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}
	
	

	// setParameter不用理会参数类型
	@Test
	public void testQuery8() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.createQuery("from Book b where id<?").setParameter(0, 4).list();
		for (Book b : list) {
			System.out.println(b);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	// 条件查询 命名查询--设置条件参数的名称 以冒号开头后更名称 设置参数时 只需指定名
	@Test
	public void testQuery9() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.createQuery("from Book b where id<:id").setParameter("id", 4).list();
		for (Book b : list) {
			System.out.println(b);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	// 分页查询
	@Test
	public void testQuery10() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.createQuery("from Book b").setFirstResult(3) // 开始显示的记录下标(currentPage-1)*pageSize
				                 .setMaxResults(3)// 设置每页记录数pageSize
				                 .list();
		for (Book b : list) {
			System.out.println(b);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	
	// 聚合函数--统计查询,结果唯一
	@Test
	public void testQuery11() {
		// 查询图书总数
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		//Number,可以包括了 int,long，因为不确定返回结果
		Number count = (Number) session.createQuery("select max(b.price) from Book b").uniqueResult();
		System.out.println("总数：" + count.byteValue());
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	
	// 分组查询，对象导航
	@Test
	public void testQuery12() {
		// 查询图书总数
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();

		List<Object[]> list = session .createQuery("select b.category.name,count(b.id) from Book b group by b.category.name").list();
		for (Object[] objs : list) {
			System.out.println(objs[0] + "--" + objs[1]);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}


	
	// 排序
	@Test
	public void testQuery13() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.createQuery("from Book order by price desc").list();
		for (Book b : list) {
			System.out.println(b);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	// 对象导航--连接查询
	@Test
	public void testQuery14() {
		// 查询 "仙侠"的书籍信息
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "from Book b where b.category.name=:name";
		hql = "select b from Book b join b.category c where c.name=:name";
		hql = "select b from Book b inner join b.category c where c.name=:name";
		List<Book> list = session.createQuery(hql).setString("name", "仙侠").list();
		for (Book b : list) {
			System.out.println(b);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	
	// 左外链接
	@Test
	public void testQuery15() {
		// 查询 "仙侠"的书籍信息
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "select c.name,b.name from Category c left outer join c.books b";
		List<Object[]> list = session.createQuery(hql).list();
		for (Object[] objs : list) {
			System.out.println(objs[0] + "----" + objs[1]);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}
	
	

	/*
	 *  1、过滤器的使用--过滤查询---为查询加上某些条件
	 * 1、定义过滤器 2、使用：加条件 3、在查询时候 使得过滤器生效
	 * 2.需要配置hibernate.cfg 增加扫描的类映射
	 */
	@Test
	public void testQuery16() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		// 启用过滤器
		session.enableFilter("bookFilter").setParameter("id", 4); //可以配置hibernate.cfg使用注解，还可以使用book.hbm.xml配置的过滤器
		List<Book> list = session.createQuery("from Book").list();
		for (Book b : list) {
			System.out.println(b);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}
	
	

	// 命名查询NamedQuery
	@Test
	public void testQuery17() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.getNamedQuery("getByCategoryId").setInteger("id", 3).list();
		for (Book b : list) {
			System.out.println(b);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	
	// 本地查询 SQL查询，不区分大小写
	@Test
	public void testQuery18() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		String sql = "select Name,Price from BOOK";  
		List<Object[]> list = session.createSQLQuery(sql).list();
		for (Object[] b : list) {
			System.out.println(b[0] + "-" + b[1]);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	// 如果查询是所有列
	@Test
	public void testQuery19() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		String sql = "select * from BOOK";
		List<Book> list = session.createSQLQuery(sql).addEntity(Book.class).list();  //自动封装
		for (Book b : list) {
			System.out.println(b);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	//命名本地SQL查询
	@Test
	public void testQuery20() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.getNamedQuery("getAll").list();
		for (Book b : list) {
			System.out.println(b + "-" + b.getCategory().getId());
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	// 对象查询 Critera查询
	// 查询对象集合
	@Test
	public void testQuery21() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.createCriteria(Book.class).list();
		for (Book b : list) {
			System.out.println(b + "-" + b.getCategory().getId());
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	
	// 条件
	@Test
	public void testQuery22() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();

		List<Book> list = session.createCriteria(Book.class).add(Restrictions.eq("id", 1)).list();
		for (Book b : list) {
			System.out.println(b + "-" + b.getCategory().getId());
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	// in
	@Test
	public void testQuery23() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		List<Integer> inl = new ArrayList<Integer>();
		inl.add(1);
		inl.add(3);
		
		List<Book> list = session.createCriteria(Book.class).add(Restrictions.in("id", inl)).list();
		for (Book b : list) {
			System.out.println(b + "-" + b.getCategory().getId());
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	// like查询
	@Test
	public void testQuery24() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.createCriteria(Book.class)
				// .add(Restrictions.like("name", "中%"))
				// .add(Restrictions.like("name", "的", MatchMode.EXACT))
				// ilike ignoreCase like忽略大小写
				.add(Restrictions.ilike("name", "%的%")).list();
		for (Book b : list) {
			System.out.println(b + "-" + b.getCategory().getId());
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	
	// or
	@Test
	public void testQuery25() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.createCriteria(Book.class)
				.add(Restrictions.or(Restrictions.eq("id", 1), Restrictions.lt("price", 60.0))).list();
		for (Book b : list) {
			System.out.println(b + "-" + b.getCategory().getId());
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	// 排序
	@Test
	public void testQuery26() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Book> list = session.createCriteria(Book.class).addOrder(Order.asc("price")).list();
		for (Book b : list) {
			System.out.println(b + "-" + b.getCategory().getId());
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	// 投影--函数
	@Test
	public void testQuery27() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Object count = session.createCriteria(Book.class).setProjection(Projections.rowCount()).uniqueResult();
		System.out.println(count);
		tx.commit();
		HibernateUtil.closeSession();
	}

	//总价和均价
	@Test 
	public void testQuery28() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Object[]> objs = session.createCriteria(Book.class)
				.setProjection(Projections.projectionList().add(Projections.max("price")).add(Projections.avg("price")))
				.list();
		for (Object[] obj : objs) {
			System.out.println(obj[0] + "---" + obj[1]);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	
	// 分组
	@Test
	public void testQuery29() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Object[]> objs = session.createCriteria(Book.class, "b").createCriteria("b.category", "c").setProjection(
				Projections.projectionList().add(Projections.rowCount()).add(Projections.groupProperty("c.name")))
				.list();
		for (Object[] obj : objs) {
			System.out.println(obj[0] + "---" + obj[1]);
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

	
	
	// example查询
	@Test
	public void testQuery30() {
		Session session = HibernateUtil.getSession();
		Book book = new Book();
		book.setName("傲慢与偏见");
		book.setPrice(80);
		Transaction tx = session.beginTransaction();
		List<Book> list = session.createCriteria(Book.class).add(Example.create(book)).list();
		for (Book b : list) {
			System.out.println(b + "-" + b.getCategory().getId());
		}
		tx.commit();
		HibernateUtil.closeSession();
	}

}
