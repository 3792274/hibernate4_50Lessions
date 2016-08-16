package lazy.pojo2;

import java.util.Date;


public class Book {


	private int id;
	private String name;
	private double price;
	private String author;
	private Date pubDate;
	
	
	private Category category;
	
	
	
	
	public Book(String name, double price, String author, Date pubDate) {
		super();
		this.name = name;
		this.price = price;
		this.author = author;
		this.pubDate = pubDate;
	}
	
	
	
	public Book() {
	}
	public Book(String name, double price) {
		super();
		this.name = name;
		this.price = price;
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
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", price=" + price
				+ ", author=" + author + ", pubDate=" + pubDate;
	}
	
}
