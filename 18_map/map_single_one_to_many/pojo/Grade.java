package map_single_one_to_many.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grade {
	
	private int id;
	private String name;
	
	private Map<String,Student> students = new HashMap<String,Student>(0);
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Student> getStudents() {
		return students;
	}
	public void setStudents(Map<String, Student> students) {
		this.students = students;
	}
}
