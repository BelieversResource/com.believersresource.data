package com.believersresource.data.base;

import java.util.Collections;
import java.util.Comparator;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import com.believersresource.data.NamedParameterStatement;

import com.believersresource.data.Utils;
import com.believersresource.data.Category;
import com.believersresource.data.Categories;

public abstract class CategoriesBase extends ArrayList<Category> {

	public CategoriesBase() {}

	public CategoriesBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new Category(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public CategoriesBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new Category(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public Category getById(int id)
	{
		for (Category category : this) { if (category.id == id) return category; }
		return null;
	}

	public static Categories loadAll()
	{
		return new Categories("{call categories_load_all}");
	}

	public static Categories loadByParentId(int parentId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("parent_id", parentId);
		return new Categories("{call categories_load_by_parent_id(:parent_id)}", params);
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<Category>() { @Override public int compare(Category o1, Category o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="categorytype") Collections.sort(this, new Comparator<Category>() { @Override public int compare(Category o1, Category o2) { return o1.getCategoryType().toLowerCase().compareTo(o2.getCategoryType().toLowerCase()); } });
		else if (propertyName=="name") Collections.sort(this, new Comparator<Category>() { @Override public int compare(Category o1, Category o2) { return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()); } });
		else if (propertyName=="url") Collections.sort(this, new Comparator<Category>() { @Override public int compare(Category o1, Category o2) { return o1.getUrl().toLowerCase().compareTo(o2.getUrl().toLowerCase()); } });
		else if (propertyName=="description") Collections.sort(this, new Comparator<Category>() { @Override public int compare(Category o1, Category o2) { return o1.getDescription().toLowerCase().compareTo(o2.getDescription().toLowerCase()); } });
		else if (propertyName=="parentid") Collections.sort(this, new Comparator<Category>() { @Override public int compare(Category o1, Category o2) { return o1.getParentId() - o2.getParentId(); } });
	}

	public void reverse()
	{
		Categories copy = (Categories)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
