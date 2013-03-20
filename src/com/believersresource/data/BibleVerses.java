package com.believersresource.data;

import java.util.ArrayList;
import java.util.Hashtable;

import com.believersresource.data.base.BibleVersesBase;

public class BibleVerses extends BibleVersesBase {

	public BibleVerses() { super(); }
	public BibleVerses(String sql) { super(sql); }
	public BibleVerses(String sql, Hashtable<String, Object> params) { super(sql, params); }

	public static BibleVerses loadRanges(int[] startIds, int[] endIds, int translationId)
	{
		ArrayList<String> whereClauses = new ArrayList<String>();
		for (int i=0; i<startIds.length; i++)
		{
			whereClauses.add("v.Id between " + String.valueOf(startIds[i]) + " AND " + String.valueOf(endIds[i]));
		}
		String whereClause = Utils.joinStrings(" OR ", whereClauses);
		String sql = "select v.*,b.Name as book_name, c.body as body from bible_verses v inner join bible_books b on b.Id=v.book_id inner join bible_verse_contents c on c.verse_id=v.Id and c.translation_id=" + String.valueOf(translationId) + " where " + whereClause + " order by v.Id";
		return new BibleVerses(sql);
	}
	
	public static BibleVerses loadByBookIdChapterNumber(int bookId, int chapterNumber)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("bookId", bookId);
		params.put("chapterNumber", chapterNumber);
		return new BibleVerses("SELECT * from bible_verses WHERE book_id=:bookId and chapter_number=:chapterNumber order by ID", params);
	}
	
	public static BibleVerses loadRange(int startVerseId, int endVerseId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("startVerseId", startVerseId);
		params.put("endVerseId", endVerseId);
		return new BibleVerses("SELECT * from bible_verses WHERE ID Between :startVerseId and :endVerseId order by ID", params);
	}
}