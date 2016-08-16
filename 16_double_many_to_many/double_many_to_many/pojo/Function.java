package double_many_to_many.pojo;

import java.util.HashSet;
import java.util.Set;

import double_many_to_many.pojo.Role;

public class Function {
	
	private int id;
	private String name;
	private String code;
	private String url;
	
	
	private Set<Role> roles = new HashSet<Role>();

	public Function() {
	}

	public Function(String name, String code, String url) {
		super();
		this.name = name;
		this.code = code;
		this.url = url;
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
