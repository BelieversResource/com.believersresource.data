package com.believersresource.data;

import java.sql.ResultSet;
import com.believersresource.data.base.BibleTranslationBase;

public class BibleTranslation extends BibleTranslationBase {

	public BibleTranslation() { super(); }
	public BibleTranslation(ResultSet rs) { super(rs); }

}