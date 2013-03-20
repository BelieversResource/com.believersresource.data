package com.believersresource.data;

import java.sql.ResultSet;
import java.util.Hashtable;

import com.believersresource.data.base.PageBase;

public class Page extends PageBase {

	public Page() { super(); }
	public Page(ResultSet rs) { super(rs); }

	public static Page load(String url)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("url", url);
		Pages result = new Pages("SELECT * FROM pages WHERE url = :url", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
}