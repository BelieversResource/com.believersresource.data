package com.believersresource.data;

import java.util.Hashtable;
import com.believersresource.data.base.LinkClicksBase;

public class LinkClicks extends LinkClicksBase {

	public LinkClicks() { super(); }
	public LinkClicks(String sql) { super(sql); }
	public LinkClicks(String sql, Hashtable<String, Object> params) { super(sql, params); }

}