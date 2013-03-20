package com.believersresource.data;

import java.util.Hashtable;
import com.believersresource.data.base.PagesBase;

public class Pages extends PagesBase {

	public Pages() { super(); }
	public Pages(String sql) { super(sql); }
	public Pages(String sql, Hashtable<String, Object> params) { super(sql, params); }

}