package struts2Hibernate.service.impl;

import java.util.List;

import struts2Hibernate.dao.CategoryDao;
import struts2Hibernate.dao.impl.CategoryDaoImpl;
import struts2Hibernate.pojo.Category;
import struts2Hibernate.service.CategoryService;

public class CategoryServiceImpl implements CategoryService{
	private CategoryDao categoryDao = new CategoryDaoImpl();
	public List<Category> getList() {
		return categoryDao.getList();
	}

}
