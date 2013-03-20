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
import com.believersresource.data.BibleVerseContent;
import com.believersresource.data.BibleVerseContents;

public abstract class BibleVerseContentsBase extends ArrayList<BibleVerseContent> {

	public BibleVerseContentsBase() {}

	public BibleVerseContentsBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new BibleVerseContent(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public BibleVerseContentsBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new BibleVerseContent(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public BibleVerseContent getById(int id)
	{
		for (BibleVerseContent bibleVerseContent : this) { if (bibleVerseContent.id == id) return bibleVerseContent; }
		return null;
	}

	public static BibleVerseContents loadAll()
	{
		return new BibleVerseContents("{call bible_verse_contents_load_all}");
	}

	public static BibleVerseContents loadByTranslationId(int translationId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("translation_id", translationId);
		return new BibleVerseContents("{call bible_verse_contents_load_by_translation_id(:translation_id)}", params);
	}

	public static BibleVerseContents loadByVerseId(int verseId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("verse_id", verseId);
		return new BibleVerseContents("{call bible_verse_contents_load_by_verse_id(:verse_id)}", params);
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<BibleVerseContent>() { @Override public int compare(BibleVerseContent o1, BibleVerseContent o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="verseid") Collections.sort(this, new Comparator<BibleVerseContent>() { @Override public int compare(BibleVerseContent o1, BibleVerseContent o2) { return o1.getVerseId() - o2.getVerseId(); } });
		else if (propertyName=="translationid") Collections.sort(this, new Comparator<BibleVerseContent>() { @Override public int compare(BibleVerseContent o1, BibleVerseContent o2) { return o1.getTranslationId() - o2.getTranslationId(); } });
		else if (propertyName=="body") Collections.sort(this, new Comparator<BibleVerseContent>() { @Override public int compare(BibleVerseContent o1, BibleVerseContent o2) { return o1.getBody().toLowerCase().compareTo(o2.getBody().toLowerCase()); } });
	}

	public void reverse()
	{
		BibleVerseContents copy = (BibleVerseContents)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
