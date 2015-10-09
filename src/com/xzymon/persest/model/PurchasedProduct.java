package com.xzymon.persest.model;

import java.sql.Date;

public class PurchasedProduct {
	private Long id;
	private Long productId;
	private Long storeId;
	private Integer intPrice;
	private Short centPrice;
	private String comment;
	private Date date;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	public Integer getIntPrice() {
		return intPrice;
	}
	public void setIntPrice(Integer intPrice) {
		this.intPrice = intPrice;
	}
	public Short getCentPrice() {
		return centPrice;
	}
	public void setCentPrice(Short centPrice) {
		this.centPrice = centPrice;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
