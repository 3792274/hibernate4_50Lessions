package list_single_one_to_many.pojo;

import java.util.ArrayList;
import java.util.List;

public class Grade {

	private int id;
	private String name;
	
	private List<Student> students = new ArrayList<Student>(0);
	
	
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
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
}
