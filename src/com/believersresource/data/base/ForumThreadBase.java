package com.believersresource.data.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Date;

import com.believersresource.data.Utils;
import com.believersresource.data.ForumThread;
import com.believersresource.data.ForumThreads;

public abstract class ForumThreadBase {
	protected int id;
	protected String title;
	protected String url;
	protected int categoryId;

	protected boolean idIsNull = true;
	protected boolean titleIsNull = true;
	protected boolean urlIsNull = true;
	protected boolean categoryIdIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public String getTitle() { return title; };
	public void setTitle(String value) { title = value; titleIsNull = false; };
	public String getUrl() { return url; };
	public void setUrl(String value) { url = value; urlIsNull = false; };
	public int getCategoryId() { return categoryId; };
	public void setCategoryId(int value) { categoryId = value; categoryIdIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getTitleIsNull() { return titleIsNull; };
	public void setTitleIsNull(boolean value) { titleIsNull = value; };
	public boolean getUrlIsNull() { return urlIsNull; };
	public void setUrlIsNull(boolean value) { urlIsNull = value; };
	public boolean getCategoryIdIsNull() { return categoryIdIsNull; };
	public void setCategoryIdIsNull(boolean value) { categoryIdIsNull = value; };


	public ForumThreadBase() {}

	public ForumThreadBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.title = rs.getString("title");
			if (!rs.wasNull()) this.titleIsNull = false;

			this.url = rs.getString("url");
			if (!rs.wasNull()) this.urlIsNull = false;

			this.categoryId = rs.getInt("category_id");
			if (!rs.wasNull()) this.categoryIdIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call forum_thread_save(?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.titleIsNull) s.setNull("title", java.sql.Types.VARCHAR); else s.setString("title", this.title);
			if (this.urlIsNull) s.setNull("url", java.sql.Types.VARCHAR); else s.setString("url", this.url);
			if (this.categoryIdIsNull) s.setNull("category_id", java.sql.Types.INTEGER); else s.setInt("category_id", this.categoryId);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static ForumThread load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		ForumThreads result = new ForumThreads("{call forum_thread_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call forum_thread_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
