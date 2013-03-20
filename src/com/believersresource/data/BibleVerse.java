package com.believersresource.data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Hashtable;

import com.believersresource.data.base.BibleVerseBase;

public class BibleVerse extends BibleVerseBase {

	private String body;
	private String bookName;
	private int votes;
	private int relatedPassageId;
	
	public String getBody() { return body; }
	public void setBody(String body) { this.body = body; }
	public String getBookName() { return bookName; }
	public void setBookName(String bookName) { this.bookName = bookName; }
	public int getVotes() { return votes; }
	public void setVotes(int votes) { this.votes = votes; }
	public int getRelatedPassageId() { return relatedPassageId; }
	public void setRelatedPassageId(int relatedPassageId) { this.relatedPassageId = relatedPassageId; }
	
	public BibleVerse() { super(); }
	public BibleVerse(ResultSet rs) 
	{ 
		super(rs);
		Hashtable<String,String> ht = Utils.getColumnHash(rs);
		try{
			if (ht.containsKey("book_name")) this.bookName = rs.getString("book_name");
			if (ht.containsKey("body")) this.body = rs.getString("body");
			if (ht.containsKey("votes")) this.votes = rs.getInt("votes");
			if (ht.containsKey("related_passage_id")) this.relatedPassageId = rs.getInt("related_passage_id");
		} catch (SQLException ex) {}
	}

	public String getDisplayName()
	{
		return bookName + " " + String.valueOf(chapterNumber) + ":" + String.valueOf(verseNumber);
	}
	
	public String getUrl()
	{
		return bookName.toLowerCase().replace(" ", "_") + "-" + String.valueOf(chapterNumber) + "-" + String.valueOf(verseNumber) + ".html";
	}
	
	public static BibleVerse load(String bookName, int chapter, int verse)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("bookName", bookName);
		params.put("chapter", chapter);
		params.put("verse", verse);

		BibleVerses result = new BibleVerses("SELECT v.* from bible_verses v inner join bible_books b on b.id=v.book_id WHERE b.name=:bookName AND v.chapter_number=:chapter AND v.verse_number=:verse", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
	public static BibleVerse load(int bookId, int chapter, int verse)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("bookId", bookId);
		params.put("chapter", chapter);
		params.put("verse", verse);
		BibleVerses result = new BibleVerses("SELECT v.* from bible_verses v WHERE v.book_id=:bookId AND v.chapter_number=:chapter AND v.verse_number=:verse", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
}