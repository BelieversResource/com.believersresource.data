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
import com.believersresource.data.Download;
import com.believersresource.data.Downloads;

public abstract class DownloadsBase extends ArrayList<Download> {

	public DownloadsBase() {}

	public DownloadsBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new Download(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public DownloadsBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new Download(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public Download getById(int id)
	{
		for (Download download : this) { if (download.id == id) return download; }
		return null;
	}

	public static Downloads loadAll()
	{
		return new Downloads("{call downloads_load_all}");
	}

	public static Downloads loadByCategoryId(int categoryId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("category_id", categoryId);
		return new Downloads("{call downloads_load_by_category_id(:category_id)}", params);
	}

	public static Downloads loadByUserId(int userId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("user_id", userId);
		return new Downloads("{call downloads_load_by_user_id(:user_id)}", params);
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<Download>() { @Override public int compare(Download o1, Download o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="title") Collections.sort(this, new Comparator<Download>() { @Override public int compare(Download o1, Download o2) { return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase()); } });
		else if (propertyName=="url") Collections.sort(this, new Comparator<Download>() { @Override public int compare(Download o1, Download o2) { return o1.getUrl().toLowerCase().compareTo(o2.getUrl().toLowerCase()); } });
		else if (propertyName=="categoryid") Collections.sort(this, new Comparator<Download>() { @Override public int compare(Download o1, Download o2) { return o1.getCategoryId() - o2.getCategoryId(); } });
		else if (propertyName=="description") Collections.sort(this, new Comparator<Download>() { @Override public int compare(Download o1, Download o2) { return o1.getDescription().toLowerCase().compareTo(o2.getDescription().toLowerCase()); } });
		else if (propertyName=="votes") Collections.sort(this, new Comparator<Download>() { @Override public int compare(Download o1, Download o2) { return o1.getVotes() - o2.getVotes(); } });
		else if (propertyName=="shortdescription") Collections.sort(this, new Comparator<Download>() { @Override public int compare(Download o1, Download o2) { return o1.getShortDescription().toLowerCase().compareTo(o2.getShortDescription().toLowerCase()); } });
		else if (propertyName=="userid") Collections.sort(this, new Comparator<Download>() { @Override public int compare(Download o1, Download o2) { return o1.getUserId() - o2.getUserId(); } });
	}

	public void reverse()
	{
		Downloads copy = (Downloads)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
