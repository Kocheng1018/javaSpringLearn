package com.kocheng.demo1.Model;

public class Product {
	private String id;
	private String name;
	private int price;

	public Product() {
	};

	public Product(String id, String name, int price) {
		this.id = id;
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

	public class ProductQuery {
		private String key;
		private String order;
		private String sortRule;

		public String getKey() {
			return key;
		}

		public String getOrder() {
			return order;
		}

		public String getSortRule() {
			return sortRule;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public void setOrder(String order) {
			this.order = order;
		}

		public void setSortRule(String sortRule) {
			this.sortRule = sortRule;
		}
	}
}
