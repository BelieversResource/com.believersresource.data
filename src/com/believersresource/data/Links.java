package com.believersresource.data;

import java.util.Hashtable;
import com.believersresource.data.base.LinksBase;

public class Links extends LinksBase {

	public Links() { super(); }
	public Links(String sql) { super(sql); }
	public Links(String sql, Hashtable<String, Object> params) { super(sql, params); }

	public static Links load(String contentType, int contentId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("contentType", contentType);
		params.put("contentId", contentId);
		return new Links("SELECT * FROM links WHERE content_type=:contentType AND content_id=:contentId ORDER BY link_type,name", params);
	}
	
}