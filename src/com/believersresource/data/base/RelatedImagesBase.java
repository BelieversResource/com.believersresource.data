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
import com.believersresource.data.RelatedImage;
import com.believersresource.data.RelatedImages;

public abstract class RelatedImagesBase extends ArrayList<RelatedImage> {

	public RelatedImagesBase() {}

	public RelatedImagesBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new RelatedImage(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public RelatedImagesBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new RelatedImage(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public RelatedImage getById(int id)
	{
		for (RelatedImage relatedImage : this) { if (relatedImage.id == id) return relatedImage; }
		return null;
	}

	public static RelatedImages loadAll()
	{
		return new RelatedImages("{call related_images_load_all}");
	}

	public static RelatedImages loadByImageId(int imageId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("image_id", imageId);
		return new RelatedImages("{call related_images_load_by_image_id(:image_id)}", params);
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<RelatedImage>() { @Override public int compare(RelatedImage o1, RelatedImage o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="contenttype") Collections.sort(this, new Comparator<RelatedImage>() { @Override public int compare(RelatedImage o1, RelatedImage o2) { return o1.getContentType().toLowerCase().compareTo(o2.getContentType().toLowerCase()); } });
		else if (propertyName=="contentid") Collections.sort(this, new Comparator<RelatedImage>() { @Override public int compare(RelatedImage o1, RelatedImage o2) { return o1.getContentId() - o2.getContentId(); } });
		else if (propertyName=="imageid") Collections.sort(this, new Comparator<RelatedImage>() { @Override public int compare(RelatedImage o1, RelatedImage o2) { return o1.getImageId() - o2.getImageId(); } });
		else if (propertyName=="votes") Collections.sort(this, new Comparator<RelatedImage>() { @Override public int compare(RelatedImage o1, RelatedImage o2) { return o1.getVotes() - o2.getVotes(); } });
	}

	public void reverse()
	{
		RelatedImages copy = (RelatedImages)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
