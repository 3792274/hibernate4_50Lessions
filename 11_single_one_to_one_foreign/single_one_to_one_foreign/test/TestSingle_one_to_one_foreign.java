package single_one_to_one_foreign.test;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import single_one_to_one_foreign.pojo.IdCard;
import single_one_to_one_foreign.pojo.Person;

 
/**
 	   基于外键的单向一对一关联
 	 1. 和多对一基本一致，2个表，一个Persion表外键引用另一个表IDCard主键
     2.只是在多对一的基础上添加<many-to-one unique="true">,会添加一个唯一约束
 */
public class TestSingle_one_to_one_foreign {

	@Test//创建表结构
	public void testCreateDB(){
		Configuration cfg = new Configuration().configure("single_one_to_one_foreign/resources/hibernate.cfg.xml");
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
			
			IdCard id1 = new IdCard();
			id1.setCode("320123231212112");
			
			IdCard id2 = new IdCard();
			id2.setCode("110231432423432");
			
			Person person1 = new Person();  //关联关系定义在Persion中 ->IDCard
			person1.setName("贾宝玉");
			person1.setAge(23);
			person1.setIdCard(id1);
			
			Person person2 = new Person();
			person2.setName("林黛玉");
			person2.setAge(22);
			person2.setIdCard(id2);
			
			Person person3 = new Person();
			person3.setName("薛宝钗");
			person3.setAge(21);
			person3.setIdCard(id2); //与Persion2同一个ID,将违反唯一约束，既在Persion.hbm.xml中定义的<many-to-one unique="true">
			
			session.save(person1);
			session.save(person2);
			//session.save(person3);  //当保存person3出错,外键列重复
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
	public void testDelete(){
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			//取数据
			Person person  =(Person) session.get(Person.class, 4);  //默认不会级联查出IDcard
			session.delete(person);//当persion.hbm.xml中设置 级联删除cascade="all"  将会级联删除
			
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
