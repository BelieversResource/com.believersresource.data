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
import com.believersresource.data.BiblePassage;
import com.believersresource.data.BiblePassages;

public abstract class BiblePassagesBase extends ArrayList<BiblePassage> {

	public BiblePassagesBase() {}

	public BiblePassagesBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new BiblePassage(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public BiblePassagesBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new BiblePassage(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public BiblePassage getById(int id)
	{
		for (BiblePassage biblePassage : this) { if (biblePassage.id == id) return biblePassage; }
		return null;
	}

	public static BiblePassages loadAll()
	{
		return new BiblePassages("{call bible_passages_load_all}");
	}

	public static BiblePassages loadByStartVerseId(int startVerseId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("start_verse_id", startVerseId);
		return new BiblePassages("{call bible_passages_load_by_start_verse_id(:start_verse_id)}", params);
	}

	public static BiblePassages loadByEndVerseId(int endVerseId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("end_verse_id", endVerseId);
		return new BiblePassages("{call bible_passages_load_by_end_verse_id(:end_verse_id)}", params);
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<BiblePassage>() { @Override public int compare(BiblePassage o1, BiblePassage o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="startverseid") Collections.sort(this, new Comparator<BiblePassage>() { @Override public int compare(BiblePassage o1, BiblePassage o2) { return o1.getStartVerseId() - o2.getStartVerseId(); } });
		else if (propertyName=="endverseid") Collections.sort(this, new Comparator<BiblePassage>() { @Override public int compare(BiblePassage o1, BiblePassage o2) { return o1.getEndVerseId() - o2.getEndVerseId(); } });
	}

	public void reverse()
	{
		BiblePassages copy = (BiblePassages)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
