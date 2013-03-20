package com.believersresource.data.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Date;

import com.believersresource.data.Utils;
import com.believersresource.data.Page;
import com.believersresource.data.Pages;

public abstract class PageBase {
	protected int id;
	protected String title;
	protected String header;
	protected String url;
	protected String body;

	protected boolean idIsNull = true;
	protected boolean titleIsNull = true;
	protected boolean headerIsNull = true;
	protected boolean urlIsNull = true;
	protected boolean bodyIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public String getTitle() { return title; };
	public void setTitle(String value) { title = value; titleIsNull = false; };
	public String getHeader() { return header; };
	public void setHeader(String value) { header = value; headerIsNull = false; };
	public String getUrl() { return url; };
	public void setUrl(String value) { url = value; urlIsNull = false; };
	public String getBody() { return body; };
	public void setBody(String value) { body = value; bodyIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getTitleIsNull() { return titleIsNull; };
	public void setTitleIsNull(boolean value) { titleIsNull = value; };
	public boolean getHeaderIsNull() { return headerIsNull; };
	public void setHeaderIsNull(boolean value) { headerIsNull = value; };
	public boolean getUrlIsNull() { return urlIsNull; };
	public void setUrlIsNull(boolean value) { urlIsNull = value; };
	public boolean getBodyIsNull() { return bodyIsNull; };
	public void setBodyIsNull(boolean value) { bodyIsNull = value; };


	public PageBase() {}

	public PageBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.title = rs.getString("title");
			if (!rs.wasNull()) this.titleIsNull = false;

			this.header = rs.getString("header");
			if (!rs.wasNull()) this.headerIsNull = false;

			this.url = rs.getString("url");
			if (!rs.wasNull()) this.urlIsNull = false;

			this.body = rs.getString("body");
			if (!rs.wasNull()) this.bodyIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call page_save(?, ?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.titleIsNull) s.setNull("title", java.sql.Types.VARCHAR); else s.setString("title", this.title);
			if (this.headerIsNull) s.setNull("header", java.sql.Types.VARCHAR); else s.setString("header", this.header);
			if (this.urlIsNull) s.setNull("url", java.sql.Types.VARCHAR); else s.setString("url", this.url);
			if (this.bodyIsNull) s.setNull("body", java.sql.Types.OTHER); else s.setString("body", this.body);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static Page load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		Pages result = new Pages("{call page_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call page_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
