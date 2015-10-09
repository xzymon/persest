package com.xzymon.persest.model;

import java.sql.Date;

public class MockConsumption {
	private Long id;
	private Long productId;
	private Integer intPrice;
	private Short centPrice;
	private Integer consumedDenominator; //mianownik
	private Integer consumedNumerator; //licznik
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
	public Integer getConsumedDenominator() {
		return consumedDenominator;
	}
	public void setConsumedDenominator(Integer consumedDenominator) {
		this.consumedDenominator = consumedDenominator;
	}
	public Integer getConsumedNumerator() {
		return consumedNumerator;
	}
	public void setConsumedNumerator(Integer consumedNumerator) {
		this.consumedNumerator = consumedNumerator;
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
