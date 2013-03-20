package com.believersresource.data.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Date;

import com.believersresource.data.Utils;
import com.believersresource.data.Download;
import com.believersresource.data.Downloads;

public abstract class DownloadBase {
	protected int id;
	protected String title;
	protected String url;
	protected int categoryId;
	protected String description;
	protected int votes;
	protected String shortDescription;
	protected int userId;

	protected boolean idIsNull = true;
	protected boolean titleIsNull = true;
	protected boolean urlIsNull = true;
	protected boolean categoryIdIsNull = true;
	protected boolean descriptionIsNull = true;
	protected boolean votesIsNull = true;
	protected boolean shortDescriptionIsNull = true;
	protected boolean userIdIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public String getTitle() { return title; };
	public void setTitle(String value) { title = value; titleIsNull = false; };
	public String getUrl() { return url; };
	public void setUrl(String value) { url = value; urlIsNull = false; };
	public int getCategoryId() { return categoryId; };
	public void setCategoryId(int value) { categoryId = value; categoryIdIsNull = false; };
	public String getDescription() { return description; };
	public void setDescription(String value) { description = value; descriptionIsNull = false; };
	public int getVotes() { return votes; };
	public void setVotes(int value) { votes = value; votesIsNull = false; };
	public String getShortDescription() { return shortDescription; };
	public void setShortDescription(String value) { shortDescription = value; shortDescriptionIsNull = false; };
	public int getUserId() { return userId; };
	public void setUserId(int value) { userId = value; userIdIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getTitleIsNull() { return titleIsNull; };
	public void setTitleIsNull(boolean value) { titleIsNull = value; };
	public boolean getUrlIsNull() { return urlIsNull; };
	public void setUrlIsNull(boolean value) { urlIsNull = value; };
	public boolean getCategoryIdIsNull() { return categoryIdIsNull; };
	public void setCategoryIdIsNull(boolean value) { categoryIdIsNull = value; };
	public boolean getDescriptionIsNull() { return descriptionIsNull; };
	public void setDescriptionIsNull(boolean value) { descriptionIsNull = value; };
	public boolean getVotesIsNull() { return votesIsNull; };
	public void setVotesIsNull(boolean value) { votesIsNull = value; };
	public boolean getShortDescriptionIsNull() { return shortDescriptionIsNull; };
	public void setShortDescriptionIsNull(boolean value) { shortDescriptionIsNull = value; };
	public boolean getUserIdIsNull() { return userIdIsNull; };
	public void setUserIdIsNull(boolean value) { userIdIsNull = value; };


	public DownloadBase() {}

	public DownloadBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.title = rs.getString("title");
			if (!rs.wasNull()) this.titleIsNull = false;

			this.url = rs.getString("url");
			if (!rs.wasNull()) this.urlIsNull = false;

			this.categoryId = rs.getInt("category_id");
			if (!rs.wasNull()) this.categoryIdIsNull = false;

			this.description = rs.getString("description");
			if (!rs.wasNull()) this.descriptionIsNull = false;

			this.votes = rs.getInt("votes");
			if (!rs.wasNull()) this.votesIsNull = false;

			this.shortDescription = rs.getString("short_description");
			if (!rs.wasNull()) this.shortDescriptionIsNull = false;

			this.userId = rs.getInt("user_id");
			if (!rs.wasNull()) this.userIdIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call download_save(?, ?, ?, ?, ?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.titleIsNull) s.setNull("title", java.sql.Types.VARCHAR); else s.setString("title", this.title);
			if (this.urlIsNull) s.setNull("url", java.sql.Types.VARCHAR); else s.setString("url", this.url);
			if (this.categoryIdIsNull) s.setNull("category_id", java.sql.Types.INTEGER); else s.setInt("category_id", this.categoryId);
			if (this.descriptionIsNull) s.setNull("description", java.sql.Types.OTHER); else s.setString("description", this.description);
			if (this.votesIsNull) s.setNull("votes", java.sql.Types.INTEGER); else s.setInt("votes", this.votes);
			if (this.shortDescriptionIsNull) s.setNull("short_description", java.sql.Types.VARCHAR); else s.setString("short_description", this.shortDescription);
			if (this.userIdIsNull) s.setNull("user_id", java.sql.Types.INTEGER); else s.setInt("user_id", this.userId);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static Download load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		Downloads result = new Downloads("{call download_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call download_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
