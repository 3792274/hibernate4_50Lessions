package compositeID.test;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import compositeID.pojo.Score;
import compositeID.pojo.ScoreId;

/**
 * 测试复合主键，Score中有一个SocreID主键，该主键是复合主键包括stuId/subjectId
 */
public class TestCompositeID {

	
	
	@Test //SchemaExport工具类可以自动生成数据表，先删除再创建
	public void testCreateDB(){
		Configuration cfg = new Configuration().configure("compositeID/resources/hibernate.cfg.xml");
		SchemaExport se = new SchemaExport(cfg);
		//第一个参数 是否生成ddl脚本  第二个参数  是否执行到数据库中
		se.create(true, true);
	}
	
	
	

	@Test
	public void testSave() throws HibernateException{
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			Score s = new Score();
			ScoreId sid = new ScoreId();
			sid.setStuId(2);
			sid.setSubjectId(6);
			s.setResult(89);
			s.setScoreId(sid);
			
			session.save(s);
			
			tx.commit();
			
		}catch (HibernateException e) {
			if(tx!=null)  //buildSessionFactory，opensession都会发生异常，tx有可能null,需要处理异常
				tx.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			HibernateUtil.closeSession();
		}
	}
	
	
	
}
