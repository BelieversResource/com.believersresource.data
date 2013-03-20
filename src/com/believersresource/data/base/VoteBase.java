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
import com.believersresource.data.Vote;
import com.believersresource.data.Votes;

public abstract class VoteBase {
	protected int id;
	protected String contentType;
	protected int contentId;
	protected int userId;
	protected int ipAddress;
	protected int points;
	protected Date voteDate;

	protected boolean idIsNull = true;
	protected boolean contentTypeIsNull = true;
	protected boolean contentIdIsNull = true;
	protected boolean userIdIsNull = true;
	protected boolean ipAddressIsNull = true;
	protected boolean pointsIsNull = true;
	protected boolean voteDateIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public String getContentType() { return contentType; };
	public void setContentType(String value) { contentType = value; contentTypeIsNull = false; };
	public int getContentId() { return contentId; };
	public void setContentId(int value) { contentId = value; contentIdIsNull = false; };
	public int getUserId() { return userId; };
	public void setUserId(int value) { userId = value; userIdIsNull = false; };
	public int getIpAddress() { return ipAddress; };
	public void setIpAddress(int value) { ipAddress = value; ipAddressIsNull = false; };
	public int getPoints() { return points; };
	public void setPoints(int value) { points = value; pointsIsNull = false; };
	public Date getVoteDate() { return voteDate; };
	public void setVoteDate(Date value) { voteDate = value; voteDateIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getContentTypeIsNull() { return contentTypeIsNull; };
	public void setContentTypeIsNull(boolean value) { contentTypeIsNull = value; };
	public boolean getContentIdIsNull() { return contentIdIsNull; };
	public void setContentIdIsNull(boolean value) { contentIdIsNull = value; };
	public boolean getUserIdIsNull() { return userIdIsNull; };
	public void setUserIdIsNull(boolean value) { userIdIsNull = value; };
	public boolean getIpAddressIsNull() { return ipAddressIsNull; };
	public void setIpAddressIsNull(boolean value) { ipAddressIsNull = value; };
	public boolean getPointsIsNull() { return pointsIsNull; };
	public void setPointsIsNull(boolean value) { pointsIsNull = value; };
	public boolean getVoteDateIsNull() { return voteDateIsNull; };
	public void setVoteDateIsNull(boolean value) { voteDateIsNull = value; };


	public VoteBase() {}

	public VoteBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.contentType = rs.getString("content_type");
			if (!rs.wasNull()) this.contentTypeIsNull = false;

			this.contentId = rs.getInt("content_id");
			if (!rs.wasNull()) this.contentIdIsNull = false;

			this.userId = rs.getInt("user_id");
			if (!rs.wasNull()) this.userIdIsNull = false;

			this.ipAddress = rs.getInt("ip_address");
			if (!rs.wasNull()) this.ipAddressIsNull = false;

			this.points = rs.getInt("points");
			if (!rs.wasNull()) this.pointsIsNull = false;

			this.voteDate = rs.getDate("vote_date");
			if (!rs.wasNull()) this.voteDateIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call vote_save(?, ?, ?, ?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.contentTypeIsNull) s.setNull("content_type", java.sql.Types.VARCHAR); else s.setString("content_type", this.contentType);
			if (this.contentIdIsNull) s.setNull("content_id", java.sql.Types.INTEGER); else s.setInt("content_id", this.contentId);
			if (this.userIdIsNull) s.setNull("user_id", java.sql.Types.INTEGER); else s.setInt("user_id", this.userId);
			if (this.ipAddressIsNull) s.setNull("ip_address", java.sql.Types.INTEGER); else s.setInt("ip_address", this.ipAddress);
			if (this.pointsIsNull) s.setNull("points", java.sql.Types.INTEGER); else s.setInt("points", this.points);
			if (this.voteDateIsNull) s.setNull("vote_date", java.sql.Types.TIMESTAMP); else s.setTimestamp("vote_date", new java.sql.Timestamp(this.voteDate.getTime()));

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static Vote load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		Votes result = new Votes("{call vote_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call vote_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
