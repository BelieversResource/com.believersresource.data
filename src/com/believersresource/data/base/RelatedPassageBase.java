package com.believersresource.data.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Date;

import com.believersresource.data.Utils;
import com.believersresource.data.RelatedPassage;
import com.believersresource.data.RelatedPassages;

public abstract class RelatedPassageBase {
	protected int id;
	protected String contentType;
	protected int contentId;
	protected int passageId;
	protected int votes;

	protected boolean idIsNull = true;
	protected boolean contentTypeIsNull = true;
	protected boolean contentIdIsNull = true;
	protected boolean passageIdIsNull = true;
	protected boolean votesIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public String getContentType() { return contentType; };
	public void setContentType(String value) { contentType = value; contentTypeIsNull = false; };
	public int getContentId() { return contentId; };
	public void setContentId(int value) { contentId = value; contentIdIsNull = false; };
	public int getPassageId() { return passageId; };
	public void setPassageId(int value) { passageId = value; passageIdIsNull = false; };
	public int getVotes() { return votes; };
	public void setVotes(int value) { votes = value; votesIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getContentTypeIsNull() { return contentTypeIsNull; };
	public void setContentTypeIsNull(boolean value) { contentTypeIsNull = value; };
	public boolean getContentIdIsNull() { return contentIdIsNull; };
	public void setContentIdIsNull(boolean value) { contentIdIsNull = value; };
	public boolean getPassageIdIsNull() { return passageIdIsNull; };
	public void setPassageIdIsNull(boolean value) { passageIdIsNull = value; };
	public boolean getVotesIsNull() { return votesIsNull; };
	public void setVotesIsNull(boolean value) { votesIsNull = value; };


	public RelatedPassageBase() {}

	public RelatedPassageBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.contentType = rs.getString("content_type");
			if (!rs.wasNull()) this.contentTypeIsNull = false;

			this.contentId = rs.getInt("content_id");
			if (!rs.wasNull()) this.contentIdIsNull = false;

			this.passageId = rs.getInt("passage_id");
			if (!rs.wasNull()) this.passageIdIsNull = false;

			this.votes = rs.getInt("votes");
			if (!rs.wasNull()) this.votesIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call related_passage_save(?, ?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.contentTypeIsNull) s.setNull("content_type", java.sql.Types.VARCHAR); else s.setString("content_type", this.contentType);
			if (this.contentIdIsNull) s.setNull("content_id", java.sql.Types.INTEGER); else s.setInt("content_id", this.contentId);
			if (this.passageIdIsNull) s.setNull("passage_id", java.sql.Types.INTEGER); else s.setInt("passage_id", this.passageId);
			if (this.votesIsNull) s.setNull("votes", java.sql.Types.INTEGER); else s.setInt("votes", this.votes);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static RelatedPassage load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		RelatedPassages result = new RelatedPassages("{call related_passage_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call related_passage_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
