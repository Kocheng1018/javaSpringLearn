package com.kocheng.demo1.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {
	private String id;
	private String name;
	private int price;

	public Product() { }
	public Product(String id, String name, int price) {
		this.id = id;
		this.name = name;
		this.price = price;
	};

	public Product(String name, int price) {
		this.name = name;
		this.price = price;
	};
	public String getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public int getPrice() {
		return this.price;
	}
	public void setName(String s) {
		this.name = s;
	}
	public void setPrice(int p) {
		this.price = p;
	}
	public void setId(String id) {
		this.id = id;
	}
}
