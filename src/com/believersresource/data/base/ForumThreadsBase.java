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
import com.believersresource.data.ForumThread;
import com.believersresource.data.ForumThreads;

public abstract class ForumThreadsBase extends ArrayList<ForumThread> {

	public ForumThreadsBase() {}

	public ForumThreadsBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new ForumThread(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public ForumThreadsBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new ForumThread(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public ForumThread getById(int id)
	{
		for (ForumThread forumThread : this) { if (forumThread.id == id) return forumThread; }
		return null;
	}

	public static ForumThreads loadAll()
	{
		return new ForumThreads("{call forum_threads_load_all}");
	}

	public static ForumThreads loadByCategoryId(int categoryId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("category_id", categoryId);
		return new ForumThreads("{call forum_threads_load_by_category_id(:category_id)}", params);
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<ForumThread>() { @Override public int compare(ForumThread o1, ForumThread o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="title") Collections.sort(this, new Comparator<ForumThread>() { @Override public int compare(ForumThread o1, ForumThread o2) { return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase()); } });
		else if (propertyName=="url") Collections.sort(this, new Comparator<ForumThread>() { @Override public int compare(ForumThread o1, ForumThread o2) { return o1.getUrl().toLowerCase().compareTo(o2.getUrl().toLowerCase()); } });
		else if (propertyName=="categoryid") Collections.sort(this, new Comparator<ForumThread>() { @Override public int compare(ForumThread o1, ForumThread o2) { return o1.getCategoryId() - o2.getCategoryId(); } });
	}

	public void reverse()
	{
		ForumThreads copy = (ForumThreads)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
