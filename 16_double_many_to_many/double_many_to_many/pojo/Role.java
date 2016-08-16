package double_many_to_many.pojo;

import java.util.HashSet;
import java.util.Set;

import double_many_to_many.pojo.Function;

public class Role {
	
	
	private int id;
	private String name;
	
	private Set<Function> functions = new HashSet<Function>(0);
	
	
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
	public Set<Function> getFunctions() {
		return functions;
	}
	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}
	
}
