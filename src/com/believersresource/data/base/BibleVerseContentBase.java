package com.believersresource.data.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Date;

import com.believersresource.data.Utils;
import com.believersresource.data.BibleVerseContent;
import com.believersresource.data.BibleVerseContents;

public abstract class BibleVerseContentBase {
	protected int id;
	protected int verseId;
	protected int translationId;
	protected String body;

	protected boolean idIsNull = true;
	protected boolean verseIdIsNull = true;
	protected boolean translationIdIsNull = true;
	protected boolean bodyIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public int getVerseId() { return verseId; };
	public void setVerseId(int value) { verseId = value; verseIdIsNull = false; };
	public int getTranslationId() { return translationId; };
	public void setTranslationId(int value) { translationId = value; translationIdIsNull = false; };
	public String getBody() { return body; };
	public void setBody(String value) { body = value; bodyIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getVerseIdIsNull() { return verseIdIsNull; };
	public void setVerseIdIsNull(boolean value) { verseIdIsNull = value; };
	public boolean getTranslationIdIsNull() { return translationIdIsNull; };
	public void setTranslationIdIsNull(boolean value) { translationIdIsNull = value; };
	public boolean getBodyIsNull() { return bodyIsNull; };
	public void setBodyIsNull(boolean value) { bodyIsNull = value; };


	public BibleVerseContentBase() {}

	public BibleVerseContentBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.verseId = rs.getInt("verse_id");
			if (!rs.wasNull()) this.verseIdIsNull = false;

			this.translationId = rs.getInt("translation_id");
			if (!rs.wasNull()) this.translationIdIsNull = false;

			this.body = rs.getString("body");
			if (!rs.wasNull()) this.bodyIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call bible_verse_content_save(?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.verseIdIsNull) s.setNull("verse_id", java.sql.Types.INTEGER); else s.setInt("verse_id", this.verseId);
			if (this.translationIdIsNull) s.setNull("translation_id", java.sql.Types.INTEGER); else s.setInt("translation_id", this.translationId);
			if (this.bodyIsNull) s.setNull("body", java.sql.Types.OTHER); else s.setString("body", this.body);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static BibleVerseContent load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		BibleVerseContents result = new BibleVerseContents("{call bible_verse_content_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call bible_verse_content_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
