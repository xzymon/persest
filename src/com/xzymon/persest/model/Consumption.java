package com.xzymon.persest.model;

import java.sql.Date;

public class Consumption {
	private Long id;
	private Long purchaseId;
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
	public Long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
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
