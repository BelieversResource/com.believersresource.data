package com.believersresource.data;

import java.util.Hashtable;
import com.believersresource.data.base.BibleTranslationsBase;

public class BibleTranslations extends BibleTranslationsBase {

	public BibleTranslations() { super(); }
	public BibleTranslations(String sql) { super(sql); }
	public BibleTranslations(String sql, Hashtable<String, Object> params) { super(sql, params); }

}