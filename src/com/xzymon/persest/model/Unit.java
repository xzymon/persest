package com.xzymon.persest.model;

public class Unit {
	private Long id;
	private String name;
	private String code;
	private Short quantityDenominator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name.length() <= 45) {
			this.name = name;
		} else {
			throw new IllegalStateException("The name parameter too long: "
					+ name.length());
		}
	}
	
	public Short getQuantityDenominator() {
		return quantityDenominator;
	}

	public void setQuantityDenominator(Short quantityDenominator) {
		this.quantityDenominator = quantityDenominator;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		if (code.length() <= 5) {
			this.code = code;
		} else {
			throw new IllegalStateException("The code parameter too long: "
					+ code.length());
		}
	}

}
