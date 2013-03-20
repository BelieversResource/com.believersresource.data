package com.believersresource.data.base;

import java.util.Collections;
import java.util.Comparator;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import com.believersresource.data.NamedParameterStatement;

import com.believersresource.data.Utils;
import com.believersresource.data.Image;
import com.believersresource.data.Images;

public abstract class ImagesBase extends ArrayList<Image> {

	public ImagesBase() {}

	public ImagesBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new Image(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public ImagesBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new Image(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public Image getById(int id)
	{
		for (Image image : this) { if (image.id == id) return image; }
		return null;
	}

	public static Images loadAll()
	{
		return new Images("{call images_load_all}");
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<Image>() { @Override public int compare(Image o1, Image o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="title") Collections.sort(this, new Comparator<Image>() { @Override public int compare(Image o1, Image o2) { return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase()); } });
		else if (propertyName=="description") Collections.sort(this, new Comparator<Image>() { @Override public int compare(Image o1, Image o2) { return o1.getDescription().toLowerCase().compareTo(o2.getDescription().toLowerCase()); } });
		else if (propertyName=="extension") Collections.sort(this, new Comparator<Image>() { @Override public int compare(Image o1, Image o2) { return o1.getExtension().toLowerCase().compareTo(o2.getExtension().toLowerCase()); } });
		else if (propertyName=="width") Collections.sort(this, new Comparator<Image>() { @Override public int compare(Image o1, Image o2) { return o1.getWidth() - o2.getWidth(); } });
		else if (propertyName=="height") Collections.sort(this, new Comparator<Image>() { @Override public int compare(Image o1, Image o2) { return o1.getHeight() - o2.getHeight(); } });
		else if (propertyName=="sourceurl") Collections.sort(this, new Comparator<Image>() { @Override public int compare(Image o1, Image o2) { return o1.getSourceUrl().toLowerCase().compareTo(o2.getSourceUrl().toLowerCase()); } });
		else if (propertyName=="imagesourceurl") Collections.sort(this, new Comparator<Image>() { @Override public int compare(Image o1, Image o2) { return o1.getImageSourceUrl().toLowerCase().compareTo(o2.getImageSourceUrl().toLowerCase()); } });
		else if (propertyName=="sourcedescription") Collections.sort(this, new Comparator<Image>() { @Override public int compare(Image o1, Image o2) { return o1.getSourceDescription().toLowerCase().compareTo(o2.getSourceDescription().toLowerCase()); } });
	}

	public void reverse()
	{
		Images copy = (Images)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
