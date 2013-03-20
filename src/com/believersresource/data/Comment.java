package com.believersresource.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import com.believersresource.data.base.CommentBase;

public class Comment extends CommentBase {

	private Comments childComments;
	private String userName;
	
	
	public Comments getChildComments() { return childComments; }
	public void setChildComments(Comments childComments) { this.childComments = childComments; }
	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }
	
		
	public Comment() { super(); }

	public Comment(ResultSet rs) 
	{ 
		super(rs);
		Hashtable<String,String> ht = Utils.getColumnHash(rs);
		try{
			if (ht.containsKey("user_name")) this.userName = rs.getString("user_name");
		} catch (SQLException ex) {}
	}
	
	public static String determineContentUrl(String contentType, int contentId)
    {
        String result = "/";
        if (contentType.equals("forumthread") || contentType.equals("forumthreadstart"))
        {
        	result = "/forum/" + ForumThread.load(contentId).getUrl();
        } else if (contentType.equals("passage")) {
        	result = "/bible/" + BiblePassage.load(contentId).getUrl();
        } else if (contentType.equals("topic")) {
        	result = "/topic/" + Topic.load(contentId).getUrl();
        } else if (contentType.equals("download")) {
        	result = "/downloads/" + Download.load(contentId).getUrl();
        }
        return result;
    }

}