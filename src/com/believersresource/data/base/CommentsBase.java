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
import com.believersresource.data.Comment;
import com.believersresource.data.Comments;

public abstract class CommentsBase extends ArrayList<Comment> {

	public CommentsBase() {}

	public CommentsBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new Comment(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public CommentsBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new Comment(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public Comment getById(int id)
	{
		for (Comment comment : this) { if (comment.id == id) return comment; }
		return null;
	}

	public static Comments loadAll()
	{
		return new Comments("{call comments_load_all}");
	}

	public static Comments loadByUserId(int userId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("user_id", userId);
		return new Comments("{call comments_load_by_user_id(:user_id)}", params);
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<Comment>() { @Override public int compare(Comment o1, Comment o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="parentid") Collections.sort(this, new Comparator<Comment>() { @Override public int compare(Comment o1, Comment o2) { return o1.getParentId() - o2.getParentId(); } });
		else if (propertyName=="contenttype") Collections.sort(this, new Comparator<Comment>() { @Override public int compare(Comment o1, Comment o2) { return o1.getContentType().toLowerCase().compareTo(o2.getContentType().toLowerCase()); } });
		else if (propertyName=="contentid") Collections.sort(this, new Comparator<Comment>() { @Override public int compare(Comment o1, Comment o2) { return o1.getContentId() - o2.getContentId(); } });
		else if (propertyName=="userid") Collections.sort(this, new Comparator<Comment>() { @Override public int compare(Comment o1, Comment o2) { return o1.getUserId() - o2.getUserId(); } });
		else if (propertyName=="votes") Collections.sort(this, new Comparator<Comment>() { @Override public int compare(Comment o1, Comment o2) { return o1.getVotes() - o2.getVotes(); } });
		else if (propertyName=="active") Collections.sort(this, new Comparator<Comment>() { @Override public int compare(Comment o1, Comment o2) { int b1 = o1.getActive()?1:0; int b2 = o2.getActive()?1:0; return b1 - b2; } });
		else if (propertyName=="dateposted") Collections.sort(this, new Comparator<Comment>() { @Override public int compare(Comment o1, Comment o2) { return o1.getDatePosted().compareTo(o2.getDatePosted()); } });
		else if (propertyName=="body") Collections.sort(this, new Comparator<Comment>() { @Override public int compare(Comment o1, Comment o2) { return o1.getBody().toLowerCase().compareTo(o2.getBody().toLowerCase()); } });
		else if (propertyName=="originalbody") Collections.sort(this, new Comparator<Comment>() { @Override public int compare(Comment o1, Comment o2) { return o1.getOriginalBody().toLowerCase().compareTo(o2.getOriginalBody().toLowerCase()); } });
	}

	public void reverse()
	{
		Comments copy = (Comments)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
