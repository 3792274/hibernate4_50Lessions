package cascade.pojo;

import java.util.HashSet;
import java.util.Set;

public class Grade {
	
	private int id;
	private String name;
	private Set<Student> students = new HashSet<Student>(0);

	
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
	public Set<Student> getStudents() {
		return students;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
}
