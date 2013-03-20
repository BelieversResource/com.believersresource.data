package com.believersresource.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.believersresource.data.base.VoteBase;

public class Vote extends VoteBase {

	public Vote() { super(); }
	public Vote(ResultSet rs) { super(rs); }

	public static int cast(String contentType, int contentId, int ipAddress, int userId, boolean up)
    {
        Vote v = new Vote();
        v.setContentType(contentType);
        v.setContentId(contentId);
        v.setIpAddress(ipAddress);
        if (up) v.setPoints(1); else v.setPoints(-1);
        if (userId>0) v.setUserId(userId);
        v.setVoteDate(new java.sql.Timestamp(new java.util.Date().getTime()));
        System.out.println(new java.util.Date());
        return v.cast();
    }
	
	public int cast()
	{
		int result = 0;
		try{
			Connection conn=Utils.getConnection();
			//
			//NamedParameterStatement s = new NamedParameterStatement(conn, "{call vote_cast(:p_content_type, :p_content_id, :p_user_id, :p_ip_address, :p_points, :p_vote_date)}");
			CallableStatement s = conn.prepareCall("{call vote_cast(?, ?, ?, ?, ?, ?)}");
			
			s.setString("p_content_type", this.contentType);
			s.setInt("p_content_id", this.contentId);
			
			if (this.userIdIsNull){
				s.setNull("p_user_id", java.sql.Types.INTEGER);
			} else {
				s.setInt("p_user_id", this.userId);
			}
			
			s.setInt("p_ip_address", this.ipAddress);
			s.setInt("p_points", this.points);
			s.setTimestamp("p_vote_date", new java.sql.Timestamp(this.voteDate.getTime()));
			
			ResultSet rs = s.executeQuery();
			while (rs.next()) result = rs.getInt(1);
			conn.close();
		} catch (SQLException ex) { System.out.println(ex.toString());}
		return result;
	}
	
}