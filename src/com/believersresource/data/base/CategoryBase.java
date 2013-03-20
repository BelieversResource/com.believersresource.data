package com.believersresource.data.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Date;

import com.believersresource.data.Utils;
import com.believersresource.data.Category;
import com.believersresource.data.Categories;

public abstract class CategoryBase {
	protected int id;
	protected String categoryType;
	protected String name;
	protected String url;
	protected String description;
	protected int parentId;

	protected boolean idIsNull = true;
	protected boolean categoryTypeIsNull = true;
	protected boolean nameIsNull = true;
	protected boolean urlIsNull = true;
	protected boolean descriptionIsNull = true;
	protected boolean parentIdIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public String getCategoryType() { return categoryType; };
	public void setCategoryType(String value) { categoryType = value; categoryTypeIsNull = false; };
	public String getName() { return name; };
	public void setName(String value) { name = value; nameIsNull = false; };
	public String getUrl() { return url; };
	public void setUrl(String value) { url = value; urlIsNull = false; };
	public String getDescription() { return description; };
	public void setDescription(String value) { description = value; descriptionIsNull = false; };
	public int getParentId() { return parentId; };
	public void setParentId(int value) { parentId = value; parentIdIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getCategoryTypeIsNull() { return categoryTypeIsNull; };
	public void setCategoryTypeIsNull(boolean value) { categoryTypeIsNull = value; };
	public boolean getNameIsNull() { return nameIsNull; };
	public void setNameIsNull(boolean value) { nameIsNull = value; };
	public boolean getUrlIsNull() { return urlIsNull; };
	public void setUrlIsNull(boolean value) { urlIsNull = value; };
	public boolean getDescriptionIsNull() { return descriptionIsNull; };
	public void setDescriptionIsNull(boolean value) { descriptionIsNull = value; };
	public boolean getParentIdIsNull() { return parentIdIsNull; };
	public void setParentIdIsNull(boolean value) { parentIdIsNull = value; };


	public CategoryBase() {}

	public CategoryBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.categoryType = rs.getString("category_type");
			if (!rs.wasNull()) this.categoryTypeIsNull = false;

			this.name = rs.getString("name");
			if (!rs.wasNull()) this.nameIsNull = false;

			this.url = rs.getString("url");
			if (!rs.wasNull()) this.urlIsNull = false;

			this.description = rs.getString("description");
			if (!rs.wasNull()) this.descriptionIsNull = false;

			this.parentId = rs.getInt("parent_id");
			if (!rs.wasNull()) this.parentIdIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call category_save(?, ?, ?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.categoryTypeIsNull) s.setNull("category_type", java.sql.Types.VARCHAR); else s.setString("category_type", this.categoryType);
			if (this.nameIsNull) s.setNull("name", java.sql.Types.VARCHAR); else s.setString("name", this.name);
			if (this.urlIsNull) s.setNull("url", java.sql.Types.VARCHAR); else s.setString("url", this.url);
			if (this.descriptionIsNull) s.setNull("description", java.sql.Types.OTHER); else s.setString("description", this.description);
			if (this.parentIdIsNull) s.setNull("parent_id", java.sql.Types.INTEGER); else s.setInt("parent_id", this.parentId);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static Category load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		Categories result = new Categories("{call category_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call category_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
