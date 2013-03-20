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
import com.believersresource.data.Page;
import com.believersresource.data.Pages;

public abstract class PagesBase extends ArrayList<Page> {

	public PagesBase() {}

	public PagesBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new Page(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public PagesBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new Page(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public Page getById(int id)
	{
		for (Page page : this) { if (page.id == id) return page; }
		return null;
	}

	public static Pages loadAll()
	{
		return new Pages("{call pages_load_all}");
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<Page>() { @Override public int compare(Page o1, Page o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="title") Collections.sort(this, new Comparator<Page>() { @Override public int compare(Page o1, Page o2) { return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase()); } });
		else if (propertyName=="header") Collections.sort(this, new Comparator<Page>() { @Override public int compare(Page o1, Page o2) { return o1.getHeader().toLowerCase().compareTo(o2.getHeader().toLowerCase()); } });
		else if (propertyName=="url") Collections.sort(this, new Comparator<Page>() { @Override public int compare(Page o1, Page o2) { return o1.getUrl().toLowerCase().compareTo(o2.getUrl().toLowerCase()); } });
		else if (propertyName=="body") Collections.sort(this, new Comparator<Page>() { @Override public int compare(Page o1, Page o2) { return o1.getBody().toLowerCase().compareTo(o2.getBody().toLowerCase()); } });
	}

	public void reverse()
	{
		Pages copy = (Pages)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
