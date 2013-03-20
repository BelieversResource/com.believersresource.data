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
import com.believersresource.data.Vote;
import com.believersresource.data.Votes;

public abstract class VotesBase extends ArrayList<Vote> {

	public VotesBase() {}

	public VotesBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new Vote(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public VotesBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new Vote(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public Vote getById(int id)
	{
		for (Vote vote : this) { if (vote.id == id) return vote; }
		return null;
	}

	public static Votes loadAll()
	{
		return new Votes("{call votes_load_all}");
	}

	public static Votes loadByUserId(int userId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("user_id", userId);
		return new Votes("{call votes_load_by_user_id(:user_id)}", params);
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<Vote>() { @Override public int compare(Vote o1, Vote o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="contenttype") Collections.sort(this, new Comparator<Vote>() { @Override public int compare(Vote o1, Vote o2) { return o1.getContentType().toLowerCase().compareTo(o2.getContentType().toLowerCase()); } });
		else if (propertyName=="contentid") Collections.sort(this, new Comparator<Vote>() { @Override public int compare(Vote o1, Vote o2) { return o1.getContentId() - o2.getContentId(); } });
		else if (propertyName=="userid") Collections.sort(this, new Comparator<Vote>() { @Override public int compare(Vote o1, Vote o2) { return o1.getUserId() - o2.getUserId(); } });
		else if (propertyName=="ipaddress") Collections.sort(this, new Comparator<Vote>() { @Override public int compare(Vote o1, Vote o2) { return o1.getIpAddress() - o2.getIpAddress(); } });
		else if (propertyName=="points") Collections.sort(this, new Comparator<Vote>() { @Override public int compare(Vote o1, Vote o2) { return o1.getPoints() - o2.getPoints(); } });
		else if (propertyName=="votedate") Collections.sort(this, new Comparator<Vote>() { @Override public int compare(Vote o1, Vote o2) { return o1.getVoteDate().compareTo(o2.getVoteDate()); } });
	}

	public void reverse()
	{
		Votes copy = (Votes)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
