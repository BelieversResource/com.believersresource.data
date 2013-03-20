package com.believersresource.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import com.believersresource.data.base.ForumThreadBase;

public class ForumThread extends ForumThreadBase {

	private int votes;
	private int posts;
	private int firstCommentId;
	private String firstUserName;
	
	public int getVotes() { return votes; }
	public void setVotes(int votes) { this.votes = votes; }
	public int getPosts() { return posts; }
	public void setPosts(int posts) { this.posts = posts; }
	public int getFirstCommentId() { return firstCommentId; }
	public void setFirstCommentId(int firstCommentId) { this.firstCommentId = firstCommentId; }
	public String getFirstUserName() { return firstUserName; }
	public void setFirstUserName(String firstUserName) { this.firstUserName = firstUserName; }

	
	public ForumThread() { super(); }
	
	public ForumThread(ResultSet rs) 
	{ 
		super(rs);
		Hashtable<String,String> ht = Utils.getColumnHash(rs);
		try{
			if (ht.containsKey("votes")) this.votes = rs.getInt("votes");
			if (ht.containsKey("posts")) this.posts = rs.getInt("posts");
			if (ht.containsKey("first_comment_id")) this.firstCommentId = rs.getInt("first_comment_id");
			if (ht.containsKey("first_user_name")) this.firstUserName = rs.getString("first_user_name");
		} catch (SQLException ex) {}
	}

	public static ForumThread load(String url)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("url", url);
		ForumThreads result = new ForumThreads("SELECT * FROM forum_threads WHERE url = :url", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

}