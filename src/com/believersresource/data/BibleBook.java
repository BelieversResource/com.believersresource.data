package com.believersresource.data;

import java.sql.ResultSet;
import com.believersresource.data.base.BibleBookBase;

public class BibleBook extends BibleBookBase {

	public BibleBook() { super(); }
	public BibleBook(ResultSet rs) { super(rs); }

}