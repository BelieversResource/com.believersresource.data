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
import com.believersresource.data.BibleBook;
import com.believersresource.data.BibleBooks;

public abstract class BibleBooksBase extends ArrayList<BibleBook> {

	public BibleBooksBase() {}

	public BibleBooksBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new BibleBook(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public BibleBooksBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new BibleBook(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public BibleBook getById(int id)
	{
		for (BibleBook bibleBook : this) { if (bibleBook.id == id) return bibleBook; }
		return null;
	}

	public static BibleBooks loadAll()
	{
		return new BibleBooks("{call bible_books_load_all}");
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<BibleBook>() { @Override public int compare(BibleBook o1, BibleBook o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="name") Collections.sort(this, new Comparator<BibleBook>() { @Override public int compare(BibleBook o1, BibleBook o2) { return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()); } });
		else if (propertyName=="newtestament") Collections.sort(this, new Comparator<BibleBook>() { @Override public int compare(BibleBook o1, BibleBook o2) { int b1 = o1.getNewTestament()?1:0; int b2 = o2.getNewTestament()?1:0; return b1 - b2; } });
		else if (propertyName=="abbreviations") Collections.sort(this, new Comparator<BibleBook>() { @Override public int compare(BibleBook o1, BibleBook o2) { return o1.getAbbreviations().toLowerCase().compareTo(o2.getAbbreviations().toLowerCase()); } });
	}

	public void reverse()
	{
		BibleBooks copy = (BibleBooks)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
