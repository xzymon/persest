package com.xzymon.persest.model.comparator;

import java.util.Comparator;

import com.xzymon.persest.model.Category;

public class CategoryByNameComparator implements Comparator<Category> {

	@Override
	public int compare(Category cat1, Category cat2) {
		return cat1.getName().compareTo(cat2.getName());
	}

}
