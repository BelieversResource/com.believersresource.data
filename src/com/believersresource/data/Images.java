package com.believersresource.data;

import java.util.Hashtable;
import com.believersresource.data.base.ImagesBase;

public class Images extends ImagesBase {

	public Images() { super(); }
	public Images(String sql) { super(sql); }
	public Images(String sql, Hashtable<String, Object> params) { super(sql, params); }

	public static Images loadRelated(String contentType, int contentId, int count)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("contentType", contentType);
		params.put("contentId", contentId);
		params.put("count", count);
		return new Images("select i.*,ri.Votes,ri.Id as related_image_id from related_images ri inner join Images i on i.Id=ri.image_id where ri.content_type=:contentType and ri.content_id=:contentId ORDER BY ri.Votes desc limit :count", params);
	}
	
}