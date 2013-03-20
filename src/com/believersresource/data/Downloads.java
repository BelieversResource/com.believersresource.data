package com.believersresource.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import com.believersresource.data.Download;
import com.believersresource.data.base.DownloadsBase;

public class Downloads extends DownloadsBase {

	
	public static Downloads loadPopular(int count)
	{
		return new Downloads("select * from downloads order by votes desc limit " + String.valueOf(count));
	}
	
	public Downloads() { super(); }
	public Downloads(String sql) { super(sql); }
	public Downloads(String sql, Hashtable<String, Object> params) { super(sql, params); }
	
	
	
}
