package com.believersresource.data.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Date;
import java.sql.Timestamp;

import com.believersresource.data.Utils;
import com.believersresource.data.Comment;
import com.believersresource.data.Comments;

public abstract class CommentBase {
	protected int id;
	protected int parentId;
	protected String contentType;
	protected int contentId;
	protected int userId;
	protected int votes;
	protected boolean active;
	protected Date datePosted;
	protected String body;
	protected String originalBody;

	protected boolean idIsNull = true;
	protected boolean parentIdIsNull = true;
	protected boolean contentTypeIsNull = true;
	protected boolean contentIdIsNull = true;
	protected boolean userIdIsNull = true;
	protected boolean votesIsNull = true;
	protected boolean activeIsNull = true;
	protected boolean datePostedIsNull = true;
	protected boolean bodyIsNull = true;
	protected boolean originalBodyIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public int getParentId() { return parentId; };
	public void setParentId(int value) { parentId = value; parentIdIsNull = false; };
	public String getContentType() { return contentType; };
	public void setContentType(String value) { contentType = value; contentTypeIsNull = false; };
	public int getContentId() { return contentId; };
	public void setContentId(int value) { contentId = value; contentIdIsNull = false; };
	public int getUserId() { return userId; };
	public void setUserId(int value) { userId = value; userIdIsNull = false; };
	public int getVotes() { return votes; };
	public void setVotes(int value) { votes = value; votesIsNull = false; };
	public boolean getActive() { return active; };
	public void setActive(boolean value) { active = value; activeIsNull = false; };
	public Date getDatePosted() { return datePosted; };
	public void setDatePosted(Date value) { datePosted = value; datePostedIsNull = false; };
	public String getBody() { return body; };
	public void setBody(String value) { body = value; bodyIsNull = false; };
	public String getOriginalBody() { return originalBody; };
	public void setOriginalBody(String value) { originalBody = value; originalBodyIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getParentIdIsNull() { return parentIdIsNull; };
	public void setParentIdIsNull(boolean value) { parentIdIsNull = value; };
	public boolean getContentTypeIsNull() { return contentTypeIsNull; };
	public void setContentTypeIsNull(boolean value) { contentTypeIsNull = value; };
	public boolean getContentIdIsNull() { return contentIdIsNull; };
	public void setContentIdIsNull(boolean value) { contentIdIsNull = value; };
	public boolean getUserIdIsNull() { return userIdIsNull; };
	public void setUserIdIsNull(boolean value) { userIdIsNull = value; };
	public boolean getVotesIsNull() { return votesIsNull; };
	public void setVotesIsNull(boolean value) { votesIsNull = value; };
	public boolean getActiveIsNull() { return activeIsNull; };
	public void setActiveIsNull(boolean value) { activeIsNull = value; };
	public boolean getDatePostedIsNull() { return datePostedIsNull; };
	public void setDatePostedIsNull(boolean value) { datePostedIsNull = value; };
	public boolean getBodyIsNull() { return bodyIsNull; };
	public void setBodyIsNull(boolean value) { bodyIsNull = value; };
	public boolean getOriginalBodyIsNull() { return originalBodyIsNull; };
	public void setOriginalBodyIsNull(boolean value) { originalBodyIsNull = value; };


	public CommentBase() {}

	public CommentBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.parentId = rs.getInt("parent_id");
			if (!rs.wasNull()) this.parentIdIsNull = false;

			this.contentType = rs.getString("content_type");
			if (!rs.wasNull()) this.contentTypeIsNull = false;

			this.contentId = rs.getInt("content_id");
			if (!rs.wasNull()) this.contentIdIsNull = false;

			this.userId = rs.getInt("user_id");
			if (!rs.wasNull()) this.userIdIsNull = false;

			this.votes = rs.getInt("votes");
			if (!rs.wasNull()) this.votesIsNull = false;

			this.active = rs.getBoolean("active");
			if (!rs.wasNull()) this.activeIsNull = false;

			this.datePosted = rs.getDate("date_posted");
			if (!rs.wasNull()) this.datePostedIsNull = false;

			this.body = rs.getString("body");
			if (!rs.wasNull()) this.bodyIsNull = false;

			this.originalBody = rs.getString("original_body");
			if (!rs.wasNull()) this.originalBodyIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call comment_save(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.parentIdIsNull) s.setNull("parent_id", java.sql.Types.INTEGER); else s.setInt("parent_id", this.parentId);
			if (this.contentTypeIsNull) s.setNull("content_type", java.sql.Types.VARCHAR); else s.setString("content_type", this.contentType);
			if (this.contentIdIsNull) s.setNull("content_id", java.sql.Types.INTEGER); else s.setInt("content_id", this.contentId);
			if (this.userIdIsNull) s.setNull("user_id", java.sql.Types.INTEGER); else s.setInt("user_id", this.userId);
			if (this.votesIsNull) s.setNull("votes", java.sql.Types.INTEGER); else s.setInt("votes", this.votes);
			if (this.activeIsNull) s.setNull("active", java.sql.Types.BIT); else s.setBoolean("active", this.active);
			if (this.datePostedIsNull) s.setNull("date_posted", java.sql.Types.TIMESTAMP); else s.setTimestamp("date_posted", new java.sql.Timestamp(this.datePosted.getTime()));
			if (this.bodyIsNull) s.setNull("body", java.sql.Types.OTHER); else s.setString("body", this.body);
			if (this.originalBodyIsNull) s.setNull("original_body", java.sql.Types.OTHER); else s.setString("original_body", this.originalBody);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static Comment load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		Comments result = new Comments("{call comment_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call comment_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
