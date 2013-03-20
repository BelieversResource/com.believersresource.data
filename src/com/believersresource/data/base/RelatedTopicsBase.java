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
import com.believersresource.data.RelatedTopic;
import com.believersresource.data.RelatedTopics;

public abstract class RelatedTopicsBase extends ArrayList<RelatedTopic> {

	public RelatedTopicsBase() {}

	public RelatedTopicsBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new RelatedTopic(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public RelatedTopicsBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new RelatedTopic(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public RelatedTopic getById(int id)
	{
		for (RelatedTopic relatedTopic : this) { if (relatedTopic.id == id) return relatedTopic; }
		return null;
	}

	public static RelatedTopics loadAll()
	{
		return new RelatedTopics("{call related_topics_load_all}");
	}

	public static RelatedTopics loadByTopicId(int topicId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("topic_id", topicId);
		return new RelatedTopics("{call related_topics_load_by_topic_id(:topic_id)}", params);
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<RelatedTopic>() { @Override public int compare(RelatedTopic o1, RelatedTopic o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="contenttype") Collections.sort(this, new Comparator<RelatedTopic>() { @Override public int compare(RelatedTopic o1, RelatedTopic o2) { return o1.getContentType().toLowerCase().compareTo(o2.getContentType().toLowerCase()); } });
		else if (propertyName=="contentid") Collections.sort(this, new Comparator<RelatedTopic>() { @Override public int compare(RelatedTopic o1, RelatedTopic o2) { return o1.getContentId() - o2.getContentId(); } });
		else if (propertyName=="topicid") Collections.sort(this, new Comparator<RelatedTopic>() { @Override public int compare(RelatedTopic o1, RelatedTopic o2) { return o1.getTopicId() - o2.getTopicId(); } });
		else if (propertyName=="votes") Collections.sort(this, new Comparator<RelatedTopic>() { @Override public int compare(RelatedTopic o1, RelatedTopic o2) { return o1.getVotes() - o2.getVotes(); } });
	}

	public void reverse()
	{
		RelatedTopics copy = (RelatedTopics)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
