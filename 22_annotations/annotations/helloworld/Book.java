package annotations.helloworld;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;





@Entity    //entity表示需要持久化的实体类
@Table(name = "t_book")// 实体类 所对应的表
public class Book {
	@Id  // id主键
	@GeneratedValue(strategy = GenerationType.AUTO)  // 指定 主键生成策略
	private int id;

	private String name;
	
	private double price;
	
	private String author;
	
	private Date pubDate;
	
	

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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
}
