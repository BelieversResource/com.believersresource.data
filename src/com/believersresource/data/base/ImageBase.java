package com.believersresource.data.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Date;

import com.believersresource.data.Utils;
import com.believersresource.data.Image;
import com.believersresource.data.Images;

public abstract class ImageBase {
	protected int id;
	protected String title;
	protected String description;
	protected String extension;
	protected int width;
	protected int height;
	protected String sourceUrl;
	protected String imageSourceUrl;
	protected String sourceDescription;

	protected boolean idIsNull = true;
	protected boolean titleIsNull = true;
	protected boolean descriptionIsNull = true;
	protected boolean extensionIsNull = true;
	protected boolean widthIsNull = true;
	protected boolean heightIsNull = true;
	protected boolean sourceUrlIsNull = true;
	protected boolean imageSourceUrlIsNull = true;
	protected boolean sourceDescriptionIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public String getTitle() { return title; };
	public void setTitle(String value) { title = value; titleIsNull = false; };
	public String getDescription() { return description; };
	public void setDescription(String value) { description = value; descriptionIsNull = false; };
	public String getExtension() { return extension; };
	public void setExtension(String value) { extension = value; extensionIsNull = false; };
	public int getWidth() { return width; };
	public void setWidth(int value) { width = value; widthIsNull = false; };
	public int getHeight() { return height; };
	public void setHeight(int value) { height = value; heightIsNull = false; };
	public String getSourceUrl() { return sourceUrl; };
	public void setSourceUrl(String value) { sourceUrl = value; sourceUrlIsNull = false; };
	public String getImageSourceUrl() { return imageSourceUrl; };
	public void setImageSourceUrl(String value) { imageSourceUrl = value; imageSourceUrlIsNull = false; };
	public String getSourceDescription() { return sourceDescription; };
	public void setSourceDescription(String value) { sourceDescription = value; sourceDescriptionIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getTitleIsNull() { return titleIsNull; };
	public void setTitleIsNull(boolean value) { titleIsNull = value; };
	public boolean getDescriptionIsNull() { return descriptionIsNull; };
	public void setDescriptionIsNull(boolean value) { descriptionIsNull = value; };
	public boolean getExtensionIsNull() { return extensionIsNull; };
	public void setExtensionIsNull(boolean value) { extensionIsNull = value; };
	public boolean getWidthIsNull() { return widthIsNull; };
	public void setWidthIsNull(boolean value) { widthIsNull = value; };
	public boolean getHeightIsNull() { return heightIsNull; };
	public void setHeightIsNull(boolean value) { heightIsNull = value; };
	public boolean getSourceUrlIsNull() { return sourceUrlIsNull; };
	public void setSourceUrlIsNull(boolean value) { sourceUrlIsNull = value; };
	public boolean getImageSourceUrlIsNull() { return imageSourceUrlIsNull; };
	public void setImageSourceUrlIsNull(boolean value) { imageSourceUrlIsNull = value; };
	public boolean getSourceDescriptionIsNull() { return sourceDescriptionIsNull; };
	public void setSourceDescriptionIsNull(boolean value) { sourceDescriptionIsNull = value; };


	public ImageBase() {}

	public ImageBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.title = rs.getString("title");
			if (!rs.wasNull()) this.titleIsNull = false;

			this.description = rs.getString("description");
			if (!rs.wasNull()) this.descriptionIsNull = false;

			this.extension = rs.getString("extension");
			if (!rs.wasNull()) this.extensionIsNull = false;

			this.width = rs.getInt("width");
			if (!rs.wasNull()) this.widthIsNull = false;

			this.height = rs.getInt("height");
			if (!rs.wasNull()) this.heightIsNull = false;

			this.sourceUrl = rs.getString("source_url");
			if (!rs.wasNull()) this.sourceUrlIsNull = false;

			this.imageSourceUrl = rs.getString("image_source_url");
			if (!rs.wasNull()) this.imageSourceUrlIsNull = false;

			this.sourceDescription = rs.getString("source_description");
			if (!rs.wasNull()) this.sourceDescriptionIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call image_save(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.titleIsNull) s.setNull("title", java.sql.Types.VARCHAR); else s.setString("title", this.title);
			if (this.descriptionIsNull) s.setNull("description", java.sql.Types.OTHER); else s.setString("description", this.description);
			if (this.extensionIsNull) s.setNull("extension", java.sql.Types.VARCHAR); else s.setString("extension", this.extension);
			if (this.widthIsNull) s.setNull("width", java.sql.Types.INTEGER); else s.setInt("width", this.width);
			if (this.heightIsNull) s.setNull("height", java.sql.Types.INTEGER); else s.setInt("height", this.height);
			if (this.sourceUrlIsNull) s.setNull("source_url", java.sql.Types.VARCHAR); else s.setString("source_url", this.sourceUrl);
			if (this.imageSourceUrlIsNull) s.setNull("image_source_url", java.sql.Types.VARCHAR); else s.setString("image_source_url", this.imageSourceUrl);
			if (this.sourceDescriptionIsNull) s.setNull("source_description", java.sql.Types.OTHER); else s.setString("source_description", this.sourceDescription);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static Image load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		Images result = new Images("{call image_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call image_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
