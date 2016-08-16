package component.test;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import component.pojo.Address;
import component.pojo.Teacher;

/**
 *  组件，Teacher中含有1个Address,将Address映射成为组件。
 */
public class TestComponent {

	@Test
	public void testSave() throws HibernateException, SerialException, SQLException {
		Session session = null;
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			Teacher t = new Teacher();
			t.setName("老裴");
			t.setSex("男");
		
			Address address = new Address();
			address.setAddr1("西三旗");
			address.setAddr2("西直门");
			address.setAddr3("南六环");
			t.setAddress(address);

			session.save(t);
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
