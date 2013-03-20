package com.believersresource.data;

import java.sql.ResultSet;
import java.util.Hashtable;

import com.believersresource.data.base.RelatedPassageBase;

public class RelatedPassage extends RelatedPassageBase {

	public RelatedPassage() { super(); }
	public RelatedPassage(ResultSet rs) { super(rs); }

	public static RelatedPassage load(String contentType, int contentId, int passageId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("contentType", contentType);
		params.put("contentId", contentId);
		params.put("passageId", passageId);
		RelatedPassages result = new RelatedPassages("SELECT * FROM related_passages WHERE content_type=:contentType AND content_id=:contentId and passage_id=:passageId", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
}