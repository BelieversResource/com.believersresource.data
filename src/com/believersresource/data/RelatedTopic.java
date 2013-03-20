package com.believersresource.data;

import java.sql.ResultSet;
import java.util.Hashtable;

import com.believersresource.data.base.RelatedTopicBase;

public class RelatedTopic extends RelatedTopicBase {

	public RelatedTopic() { super(); }
	public RelatedTopic(ResultSet rs) { super(rs); }

	public static RelatedTopic load(String contentType, int contentId, int topicId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("contentType", contentType);
		params.put("contentId", contentId);
		params.put("topicId", topicId);
		RelatedTopics result = new RelatedTopics("SELECT * FROM related_topics WHERE content_type=:contentType AND content_id=:contentId and topic_id=:topicId", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
}