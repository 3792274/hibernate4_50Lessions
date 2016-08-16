package openSessionInView.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import openSessionInView.util.HibernateSessionFactory;

public class OpenSessionInView implements Filter{
	
public void destroy() {
	
}


public void init(FilterConfig arg0) throws ServletException {
	
}



public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
	
	    Session session=null;
		Transaction tx=null;
	
		try{
			session= HibernateSessionFactory.getSession();
			tx = session.beginTransaction();
			
			chain.doFilter(request, response);
			
			// servlet-->service-dao--getSession--servlet-->jsp
			tx.commit();   //无需再DAO中提交了
			
		}catch (Exception e) {
			if(tx!=null)tx.rollback();
		}
		finally{
			HibernateSessionFactory.closeSession();
		}
	}




}
