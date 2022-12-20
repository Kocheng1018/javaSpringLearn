package com.kocheng.demo1.parameter;

public class ProductQuery {
	private String key;
	private Integer priceFrom;
	private Integer priceTo;
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

	public Integer getPriceFrom() {
		return priceFrom;
	}

	public Integer getPriceTo() {
		return priceTo;
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
