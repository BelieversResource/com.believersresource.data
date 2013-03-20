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
import com.believersresource.data.Topic;
import com.believersresource.data.Topics;

public abstract class TopicsBase extends ArrayList<Topic> {

	public TopicsBase() {}

	public TopicsBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new Topic(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public TopicsBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new Topic(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public Topic getById(int id)
	{
		for (Topic topic : this) { if (topic.id == id) return topic; }
		return null;
	}

	public static Topics loadAll()
	{
		return new Topics("{call topics_load_all}");
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<Topic>() { @Override public int compare(Topic o1, Topic o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="name") Collections.sort(this, new Comparator<Topic>() { @Override public int compare(Topic o1, Topic o2) { return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()); } });
		else if (propertyName=="url") Collections.sort(this, new Comparator<Topic>() { @Override public int compare(Topic o1, Topic o2) { return o1.getUrl().toLowerCase().compareTo(o2.getUrl().toLowerCase()); } });
		else if (propertyName=="baseword") Collections.sort(this, new Comparator<Topic>() { @Override public int compare(Topic o1, Topic o2) { return o1.getBaseWord().toLowerCase().compareTo(o2.getBaseWord().toLowerCase()); } });
		else if (propertyName=="openbible") Collections.sort(this, new Comparator<Topic>() { @Override public int compare(Topic o1, Topic o2) { int b1 = o1.getOpenBible()?1:0; int b2 = o2.getOpenBible()?1:0; return b1 - b2; } });
	}

	public void reverse()
	{
		Topics copy = (Topics)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
