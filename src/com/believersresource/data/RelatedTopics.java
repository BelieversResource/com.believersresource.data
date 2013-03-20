package com.believersresource.data;

import java.util.Hashtable;
import com.believersresource.data.base.RelatedTopicsBase;

public class RelatedTopics extends RelatedTopicsBase {

	public RelatedTopics() { super(); }
	public RelatedTopics(String sql) { super(sql); }
	public RelatedTopics(String sql, Hashtable<String, Object> params) { super(sql, params); }

}