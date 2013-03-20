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
import com.believersresource.data.BibleTranslation;
import com.believersresource.data.BibleTranslations;

public abstract class BibleTranslationsBase extends ArrayList<BibleTranslation> {

	public BibleTranslationsBase() {}

	public BibleTranslationsBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new BibleTranslation(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public BibleTranslationsBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new BibleTranslation(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public BibleTranslation getById(int id)
	{
		for (BibleTranslation bibleTranslation : this) { if (bibleTranslation.id == id) return bibleTranslation; }
		return null;
	}

	public static BibleTranslations loadAll()
	{
		return new BibleTranslations("{call bible_translations_load_all}");
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<BibleTranslation>() { @Override public int compare(BibleTranslation o1, BibleTranslation o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="name") Collections.sort(this, new Comparator<BibleTranslation>() { @Override public int compare(BibleTranslation o1, BibleTranslation o2) { return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()); } });
		else if (propertyName=="abbreviation") Collections.sort(this, new Comparator<BibleTranslation>() { @Override public int compare(BibleTranslation o1, BibleTranslation o2) { return o1.getAbbreviation().toLowerCase().compareTo(o2.getAbbreviation().toLowerCase()); } });
	}

	public void reverse()
	{
		BibleTranslations copy = (BibleTranslations)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
