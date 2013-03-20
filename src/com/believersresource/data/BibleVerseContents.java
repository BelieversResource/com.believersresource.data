package com.believersresource.data;

import java.util.Hashtable;
import com.believersresource.data.base.BibleVerseContentsBase;

public class BibleVerseContents extends BibleVerseContentsBase {

	public BibleVerseContents() { super(); }
	public BibleVerseContents(String sql) { super(sql); }
	public BibleVerseContents(String sql, Hashtable<String, Object> params) { super(sql, params); }

}