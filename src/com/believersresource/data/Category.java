package com.believersresource.data;

import java.sql.ResultSet;
import java.util.Hashtable;

import com.believersresource.data.base.CategoryBase;

public class Category extends CategoryBase {

	private Categories childCategories;
	
	public Categories getChildCategories() { return childCategories; }
	public void setChildCategories(Categories childCategories) { this.childCategories = childCategories;}
	
	public Category() { super(); }
	public Category(ResultSet rs) { super(rs); }

	public static Category load(String url)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("url", url);
		Categories result = new Categories("SELECT * FROM categories WHERE url = :url", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
}