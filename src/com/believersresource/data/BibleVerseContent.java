package com.believersresource.data;

import java.sql.ResultSet;
import com.believersresource.data.base.BibleVerseContentBase;

public class BibleVerseContent extends BibleVerseContentBase {

	public BibleVerseContent() { super(); }
	public BibleVerseContent(ResultSet rs) { super(rs); }

}