package openSessionInView.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import openSessionInView.dao.UserDao;

public class UserServlet extends HttpServlet {
	private UserDao userDao = new UserDao();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		req.setAttribute("list", userDao.getAll());
		req.getRequestDispatcher("list.jsp").forward(req, resp);
	}
}
