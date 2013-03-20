package com.believersresource.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import com.believersresource.data.base.BiblePassageBase;

public class BiblePassage extends BiblePassageBase {
	
	private int votes;
	private int relatedPassageId;
	private int relatedTopicId;
	private BibleVerses verses;
	private boolean isChapter = false;
	private boolean modified = false;
	
	public int getVotes() { return votes; }
	public void setVotes(int votes) { this.votes = votes; }
	public int getRelatedPassageId() { return relatedPassageId; }
	public void setRelatedPassageId(int relatedPassageId) { this.relatedPassageId = relatedPassageId; }
	public int getRelatedTopicId() { return relatedTopicId; }
	public void setRelatedTopicId(int relatedTopicId) { this.relatedTopicId = relatedTopicId; }
	public BibleVerses getVerses() { return verses; }
	public void setVerses(BibleVerses verses) { this.verses = verses; }
	public boolean isChapter() { return isChapter; }
	public void setChapter(boolean isChapter) { this.isChapter = isChapter; }
	public boolean getModified() { return modified; }
	public void setModified(boolean modified) { this.modified = modified; }
	
	public BiblePassage() { super(); }
	
	public BiblePassage(ResultSet rs) 
	{ 
		super(rs);
		Hashtable<String,String> ht = Utils.getColumnHash(rs);
		try{
			if (ht.containsKey("votes")) this.votes = rs.getInt("votes");
			if (ht.containsKey("related_passage_id")) this.relatedPassageId = rs.getInt("related_passage_id");
			if (ht.containsKey("related_topic_id")) this.relatedTopicId = rs.getInt("related_topic_id");
		} catch (SQLException ex) {  }
	}

	public Hashtable<String, Object> toHash()
    {
        Hashtable<String, Object> result = new Hashtable<String, Object>();
        result.put("startVerseId", this.getStartVerseId());
        result.put("endVerseId", this.getEndVerseId());
        result.put("displayName", this.getDisplayName());
        result.put("votes", this.getVotes());
        result.put("body", this.getBody());
        result.put("id", this.getId());
        if (relatedPassageId>0) result.put("relatedPassageId", this.relatedPassageId);
        if (relatedTopicId > 0) result.put("relatedTopicId", this.relatedTopicId);
        return result;
    }
	
	
	public String getDisplayName()
	{
		if (verses.size()==0) return "";
		BibleVerse startVerse = verses.get(0);
		BibleVerse endVerse = verses.get(verses.size() - 1);
		String result = startVerse.getDisplayName();
		if (verses.size() == 1) return result;
		result += " - ";
		if (!endVerse.getBookName().equals(startVerse.getBookName())) result += endVerse.getBookName();
		if (endVerse.getChapterNumber() != startVerse.getChapterNumber()) result += String.valueOf(endVerse.getChapterNumber()) + ":";
		result += String.valueOf(endVerse.getVerseNumber());
		return result;
	}
	
	public String getUrl()
	{
		if (verses.size()==0) return "";
		if (verses.size()==1) return verses.get(0).getUrl();
		
		BibleVerse startVerse = verses.get(0);
		BibleVerse endVerse = verses.get(verses.size() - 1);
		String result = startVerse.getBookName().toLowerCase().replace(" ", "_") + "-" + String.valueOf(startVerse.getChapterNumber());
		if (!isChapter)
		{
			result += "-" + String.valueOf(startVerse.getVerseNumber()) + "_";
			if (startVerse.getChapterNumber() != endVerse.getChapterNumber()) result += String.valueOf(endVerse.getChapterNumber()) + "-";
			result += String.valueOf(endVerse.getVerseNumber());
		}
		
		return result + ".html";
	}
	
	public String getBody()
	{
		StringBuilder sb = new StringBuilder();
		for (BibleVerse verse : verses)
		{
			sb.append(verse.getBody() + " ");
		}
		return sb.toString().trim();
	}
	
	public void populateVerses(int translationId)
	{
		verses = BibleVerses.loadRanges(new int[]{this.startVerseId}, new int[]{this.endVerseId}, translationId );
	}
	
	public static BiblePassage load(String bookName, int startChapter, int startVerse, int endChapter, int endVerse)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("book", bookName);
		params.put("startChapter", startChapter);
		params.put("startVerse", startVerse);
		params.put("endChapter", endChapter);
		params.put("endVerse", endVerse);
		BiblePassages result = new BiblePassages("SELECT * FROM bible_passages WHERE start_verse_id=(SELECT v.ID from bible_verses v inner join bible_books b on b.Id=v.book_id where b.Name=:book and v.chapter_number=:startChapter and v.verse_number=:startVerse) AND end_verse_id=(SELECT v.ID from bible_verses v inner join bible_books b on b.Id=v.book_id where b.Name=:book and v.chapter_number=:endChapter and v.verse_number=:endVerse)", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
	public static BiblePassage loadContext(int verseId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("verse_id", verseId);
		BiblePassages result = new BiblePassages("{call bible_verse_load_context(:verse_id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
	public static BiblePassage load(int startVerseId, int endVerseId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("startVerseId", startVerseId);
		params.put("endVerseId", endVerseId);
		BiblePassages result = new BiblePassages("SELECT * FROM bible_passages WHERE start_verse_id=:startVerseId AND end_verse_id=:endVerseId", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
	
	public static BiblePassage parse(String text, BibleBooks books, boolean populateVerses)
    {
        text = text.trim();
        String rangeText = "";
        
        int startChapter=0;
        int startVerse=0;
        int endChapter = 0;
        int endVerse = 0;
        String[] parts = text.split("-");
        boolean isChapter = false;
        
        //Detect if this is a range and split that into a separate component
        if (parts.length > 1)
        {
            text = parts[0].trim();
            rangeText = parts[1].trim();
            String[] subParts = rangeText.split(":");
            if (subParts.length > 1)
            {
                try
                {
                    endChapter = Integer.parseInt(subParts[0].trim());
                    endVerse = Integer.parseInt(subParts[1].trim());
                }
                catch (Exception ex){ }
            }
            else
            {
                try{
                    endVerse = Integer.parseInt(rangeText);
                } catch (Exception ex) {}
            }
        }


        //Detect if there is a verse number
        parts = text.split(":");
        if (parts.length > 1)
        {
            text = parts[0].trim();
            try
            {
                startVerse = Integer.parseInt(parts[1].trim());
            }
            catch (Exception ex) { }
        }
        else
        {
            startVerse = 1;
            isChapter = true;
        }

        //Detect if there's a chapter number
        parts = text.split(" ");
        if (parts.length > 1)
        {
            String lastPart = parts[parts.length - 1].trim();
            try
            {
                startChapter = Integer.parseInt(lastPart);
                text = "";
                for (int i=0; i<parts.length-1; i++)
                {
                    text += parts[i] + " ";
                }
                text = text.trim();
                if (endVerse > 0 && endChapter == 0) endChapter = startChapter;
            }
            catch (Exception ex){ }
        }


        if (startChapter == 0) return null;

        String bookName = text.toLowerCase().replace("first", "1").replace("second", "2").replace("third", "3");
        bookName = bookName.replace("1st", "1").replace("2nd", "2").replace("3rd", "3");
        bookName = bookName.replace("songs", "soloman");
        bookName = Utils.getClosestMatch(bookName, books.getNameArray(), 0);

        BibleBook book=books.getByName(bookName);
        
        
        BibleVerse verse = BibleVerse.load(book.getId(), startChapter, startVerse);
        if (verse == null) return null;

        BiblePassage passage = new BiblePassage();
        passage.setStartVerseId(verse.getId());
        passage.setEndVerseId(verse.getId());

        if (isChapter)
        {
            BibleVerses verses = BibleVerses.loadByBookIdChapterNumber(book.getId(), startChapter);
            endVerse = verses.get(verses.size() - 1).getVerseNumber();
            endChapter = verses.get(verses.size() - 1).getChapterNumber();
        }

        if (endVerse > 0 && (endVerse!=startVerse || endChapter!=startChapter))
        {
            verse = BibleVerse.load(book.getId(), endChapter, endVerse);
            if (verse!=null) passage.setEndVerseId(verse.getId());
        }
        if (populateVerses) passage.populateVerses(1);

        if (isChapter) passage.isChapter = true;
        return passage;

    }
	
	public static BiblePassage loadByUrl(String url)
	{
		url = url.replace(".html", "");
		String versePart = url;
		String passagePart = "";
		
		int indexOfDash = url.indexOf("-");
		int indexOfUnderscore = url.lastIndexOf("_");
		if (indexOfDash>0 && indexOfUnderscore>indexOfDash)
		{
			versePart = url.substring(0,indexOfUnderscore);
			passagePart = url.substring(indexOfUnderscore + 1, url.length());
		}
		
		String[] result = new String[5];
		
		String[] parts = versePart.split("-");
		result[0] = parts[0].replace("_", " "); //book name
		
		if (parts.length > 1) result[1] = parts[1]; else result[1] = "0";
		if (parts.length > 2) result[2] = parts[2]; else result[2] = "0";
		
		if (passagePart.equals(""))
		{
			result[3] = result[1]; //end chapter
			result[4] = result[2]; //end verse
		} else {
			parts = passagePart.split("-");
			if (parts.length > 1)
			{
				result[3] = parts[0];  //end chapter
				result[4] = parts[1];  //end verse
			} else {
				result[3] = result[1]; //end chapter
				result[4] = parts[0];  //end verse
			}
		}
		
		BiblePassage passage = BiblePassage.load(result[0], Integer.parseInt(result[1]) , Integer.parseInt(result[2]), Integer.parseInt(result[3]), Integer.parseInt(result[4]));
		if (passage==null)
		{
			BibleVerse startVerse = BibleVerse.load(result[0], Integer.parseInt(result[1]), Integer.parseInt(result[2]));
			BibleVerse endVerse = BibleVerse.load(result[0], Integer.parseInt(result[1]), Integer.parseInt(result[2]));
			passage = new BiblePassage();
			passage.setStartVerseId(startVerse.getId());
			passage.setEndVerseId(endVerse.getId());
			passage.save();
			Topics.generateForPassage(passage.getId(), passage.getStartVerseId(), passage.getEndVerseId());
		}
		
		return  passage;
	}
	
	
	
}