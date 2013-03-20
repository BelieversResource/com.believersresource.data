package com.believersresource.data.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Date;

import com.believersresource.data.Utils;
import com.believersresource.data.BibleVerse;
import com.believersresource.data.BibleVerses;

public abstract class BibleVerseBase {
	protected int id;
	protected int bookId;
	protected int chapterNumber;
	protected int verseNumber;
	protected int contextPassageId;

	protected boolean idIsNull = true;
	protected boolean bookIdIsNull = true;
	protected boolean chapterNumberIsNull = true;
	protected boolean verseNumberIsNull = true;
	protected boolean contextPassageIdIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public int getBookId() { return bookId; };
	public void setBookId(int value) { bookId = value; bookIdIsNull = false; };
	public int getChapterNumber() { return chapterNumber; };
	public void setChapterNumber(int value) { chapterNumber = value; chapterNumberIsNull = false; };
	public int getVerseNumber() { return verseNumber; };
	public void setVerseNumber(int value) { verseNumber = value; verseNumberIsNull = false; };
	public int getContextPassageId() { return contextPassageId; };
	public void setContextPassageId(int value) { contextPassageId = value; contextPassageIdIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getBookIdIsNull() { return bookIdIsNull; };
	public void setBookIdIsNull(boolean value) { bookIdIsNull = value; };
	public boolean getChapterNumberIsNull() { return chapterNumberIsNull; };
	public void setChapterNumberIsNull(boolean value) { chapterNumberIsNull = value; };
	public boolean getVerseNumberIsNull() { return verseNumberIsNull; };
	public void setVerseNumberIsNull(boolean value) { verseNumberIsNull = value; };
	public boolean getContextPassageIdIsNull() { return contextPassageIdIsNull; };
	public void setContextPassageIdIsNull(boolean value) { contextPassageIdIsNull = value; };


	public BibleVerseBase() {}

	public BibleVerseBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.bookId = rs.getInt("book_id");
			if (!rs.wasNull()) this.bookIdIsNull = false;

			this.chapterNumber = rs.getInt("chapter_number");
			if (!rs.wasNull()) this.chapterNumberIsNull = false;

			this.verseNumber = rs.getInt("verse_number");
			if (!rs.wasNull()) this.verseNumberIsNull = false;

			this.contextPassageId = rs.getInt("context_passage_id");
			if (!rs.wasNull()) this.contextPassageIdIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call bible_verse_save(?, ?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.bookIdIsNull) s.setNull("book_id", java.sql.Types.INTEGER); else s.setInt("book_id", this.bookId);
			if (this.chapterNumberIsNull) s.setNull("chapter_number", java.sql.Types.INTEGER); else s.setInt("chapter_number", this.chapterNumber);
			if (this.verseNumberIsNull) s.setNull("verse_number", java.sql.Types.INTEGER); else s.setInt("verse_number", this.verseNumber);
			if (this.contextPassageIdIsNull) s.setNull("context_passage_id", java.sql.Types.INTEGER); else s.setInt("context_passage_id", this.contextPassageId);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static BibleVerse load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		BibleVerses result = new BibleVerses("{call bible_verse_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call bible_verse_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
