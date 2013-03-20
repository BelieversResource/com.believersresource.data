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
import com.believersresource.data.AudioBibleVerse;
import com.believersresource.data.AudioBibleVerses;

public abstract class AudioBibleVersesBase extends ArrayList<AudioBibleVerse> {

	public AudioBibleVersesBase() {}

	public AudioBibleVersesBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new AudioBibleVerse(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public AudioBibleVersesBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new AudioBibleVerse(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public AudioBibleVerse getById(int id)
	{
		for (AudioBibleVerse audioBibleVerse : this) { if (audioBibleVerse.id == id) return audioBibleVerse; }
		return null;
	}

	public static AudioBibleVerses loadAll()
	{
		return new AudioBibleVerses("{call audio_bible_verses_load_all}");
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<AudioBibleVerse>() { @Override public int compare(AudioBibleVerse o1, AudioBibleVerse o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="translationid") Collections.sort(this, new Comparator<AudioBibleVerse>() { @Override public int compare(AudioBibleVerse o1, AudioBibleVerse o2) { return o1.getTranslationId() - o2.getTranslationId(); } });
		else if (propertyName=="verseid") Collections.sort(this, new Comparator<AudioBibleVerse>() { @Override public int compare(AudioBibleVerse o1, AudioBibleVerse o2) { return o1.getVerseId() - o2.getVerseId(); } });
		else if (propertyName=="starttime") Collections.sort(this, new Comparator<AudioBibleVerse>() { @Override public int compare(AudioBibleVerse o1, AudioBibleVerse o2) { return (int)o1.getStartTime() - (int)o2.getStartTime(); } });
		else if (propertyName=="endtime") Collections.sort(this, new Comparator<AudioBibleVerse>() { @Override public int compare(AudioBibleVerse o1, AudioBibleVerse o2) { return (int)o1.getEndTime() - (int)o2.getEndTime(); } });
		else if (propertyName=="ipaddress") Collections.sort(this, new Comparator<AudioBibleVerse>() { @Override public int compare(AudioBibleVerse o1, AudioBibleVerse o2) { return o1.getIpAddress() - o2.getIpAddress(); } });
		else if (propertyName=="dateworked") Collections.sort(this, new Comparator<AudioBibleVerse>() { @Override public int compare(AudioBibleVerse o1, AudioBibleVerse o2) { return o1.getDateWorked().compareTo(o2.getDateWorked()); } });
		else if (propertyName=="approved") Collections.sort(this, new Comparator<AudioBibleVerse>() { @Override public int compare(AudioBibleVerse o1, AudioBibleVerse o2) { int b1 = o1.getApproved()?1:0; int b2 = o2.getApproved()?1:0; return b1 - b2; } });
	}

	public void reverse()
	{
		AudioBibleVerses copy = (AudioBibleVerses)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
