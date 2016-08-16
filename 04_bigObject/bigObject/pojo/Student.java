package bigObject.pojo;

import java.sql.Blob;
import java.sql.Clob;

import java.sql.Blob;
import java.sql.Clob;

/**
 * Student 学生类
 */
public class Student {

	private int id;
	private String name;
	private int age;
	
	
	// 存放大数据 可以存放4G的内容
	private Blob image;
	private Clob introduce;

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public Clob getIntroduce() {
		return introduce;
	}

	public void setIntroduce(Clob introduce) {
		this.introduce = introduce;
	}
}
