package com.believersresource.data.base;

import java.util.Collections;
import java.util.Comparator;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import com.believersresource.data.NamedParameterStatement;

import com.believersresource.data.Utils;
import com.believersresource.data.BibleVerse;
import com.believersresource.data.BibleVerses;

public abstract class BibleVersesBase extends ArrayList<BibleVerse> {

	public BibleVersesBase() {}

	public BibleVersesBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new BibleVerse(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public BibleVersesBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new BibleVerse(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public BibleVerse getById(int id)
	{
		for (BibleVerse bibleVerse : this) { if (bibleVerse.id == id) return bibleVerse; }
		return null;
	}

	public static BibleVerses loadAll()
	{
		return new BibleVerses("{call bible_verses_load_all}");
	}

	public static BibleVerses loadByBookId(int bookId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("book_id", bookId);
		return new BibleVerses("{call bible_verses_load_by_book_id(:book_id)}", params);
	}

	public static BibleVerses loadByContextPassageId(int contextPassageId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("context_passage_id", contextPassageId);
		return new BibleVerses("{call bible_verses_load_by_context_passage_id(:context_passage_id)}", params);
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<BibleVerse>() { @Override public int compare(BibleVerse o1, BibleVerse o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="bookid") Collections.sort(this, new Comparator<BibleVerse>() { @Override public int compare(BibleVerse o1, BibleVerse o2) { return o1.getBookId() - o2.getBookId(); } });
		else if (propertyName=="chapternumber") Collections.sort(this, new Comparator<BibleVerse>() { @Override public int compare(BibleVerse o1, BibleVerse o2) { return o1.getChapterNumber() - o2.getChapterNumber(); } });
		else if (propertyName=="versenumber") Collections.sort(this, new Comparator<BibleVerse>() { @Override public int compare(BibleVerse o1, BibleVerse o2) { return o1.getVerseNumber() - o2.getVerseNumber(); } });
		else if (propertyName=="contextpassageid") Collections.sort(this, new Comparator<BibleVerse>() { @Override public int compare(BibleVerse o1, BibleVerse o2) { return o1.getContextPassageId() - o2.getContextPassageId(); } });
	}

	public void reverse()
	{
		BibleVerses copy = (BibleVerses)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
