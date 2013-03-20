package com.believersresource.data;

import java.sql.ResultSet;
import java.util.Hashtable;

import com.believersresource.data.base.DownloadBase;


public class Download extends DownloadBase {
	
	public Download(ResultSet rs) {super(rs);}
	public Download() {super();}
	
	
	public static Download load(String url)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("url", url);
		Downloads result = new Downloads("SELECT * FROM downloads WHERE url = :url", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
}
