package com.believersresource.data;

import java.util.ArrayList;
import java.util.Hashtable;

import com.believersresource.data.base.BiblePassagesBase;

public class BiblePassages extends BiblePassagesBase {

	public BiblePassages() { super(); }
	public BiblePassages(String sql) { super(sql); }
	public BiblePassages(String sql, Hashtable<String, Object> params) { super(sql, params); }

	public ArrayList<Object> toList()
    {
        ArrayList<Object> result = new ArrayList<Object>();
        for (BiblePassage rp : this) { result.add(rp.toHash()); }
        return result;
    }
	
	public static BiblePassages loadPopular(int count)
	{
		return new BiblePassages("select bp.* from temp_popular_passages tpp inner join bible_passages bp on bp.id=tpp.passage_id order by votes desc limit " + String.valueOf(count));
	}
	
	public static BiblePassages loadForTopic(int topicId, int maxCount)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("topicId", topicId);
		return new BiblePassages("select bp.*,0 as related_passage_id,rt.Id as related_topic_id, rt.Votes from related_topics rt inner join bible_passages bp on bp.id=rt.content_id where rt.content_type='passage' and rt.topic_id=:topicId and rt.votes>=0 order by votes desc limit " + String.valueOf(maxCount), params);
	}
	
	public static BiblePassages loadRelated(String contentType, int contentId, int maxCount)
	{
		if (contentType.equals("topic")) return loadForTopic(contentId, maxCount);
		
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("content_type", contentType);
		params.put("content_id", contentId);
		params.put("count", maxCount);
		return new BiblePassages("{call related_passages_load_by_content(:content_type, :content_id, :count)}", params);
	}
	
	public void populateVerses(int translationId)
	{
		if (this.size() == 0) return;
		int[] startIds = new int[this.size()];
		int[] endIds = new int[this.size()];
		
		for (int i=0; i<this.size(); i++)
		{
			startIds[i] = this.get(i).getStartVerseId();
			endIds[i] = this.get(i).getEndVerseId();
		}
		BibleVerses verses = BibleVerses.loadRanges(startIds, endIds, translationId);
		
		for (BiblePassage passage : this)
		{
			passage.setVerses(new BibleVerses());
			for (int i=passage.getStartVerseId(); i<=passage.getEndVerseId(); i++)
			{
				BibleVerse verse = verses.getById(i);
				if (verse != null) passage.getVerses().add(verse);
			}
		}
	}
	
	

    public BiblePassages consolidateAndUpdate()
    {
        BiblePassages result = this.consolidate().consolidate();
        ArrayList<String> clauses = new ArrayList<String>();

        for (BiblePassage passage : result)
        {
            if (passage.getModified()) clauses.add("start_verse_id=" + String.valueOf(passage.getStartVerseId()) + " AND end_verse_id=" + String.valueOf(passage.getEndVerseId()) );
        }

        if (clauses.size() > 0)
        {
            //Look up the proper ids for those passages
            BiblePassages tempPassages = new BiblePassages("SELECT * FROM bible_passages WHERE (" + Utils.joinStrings(") OR (", clauses) + ")");
            for (BiblePassage tempPassage : tempPassages)
            {
                for (BiblePassage passage : result)
                {
                    if (passage.getModified() && passage.getStartVerseId() == tempPassage.getStartVerseId() && passage.getEndVerseId() == tempPassage.getEndVerseId())
                    {
                        passage.setId(tempPassage.getId());
                        passage.setModified(false);
                    }
                }
            }

            for (int i = result.size() - 1; i >= 0; i--)
            {
                if (result.get(i).getModified()) result.remove(i);
            }
        }
        return result;
    }

    //Combines neighboring passages with similar votes together into a single passage.
    private BiblePassages consolidate()
    {
        BiblePassages result = new BiblePassages();
        for (BiblePassage passage : this)
        {
            boolean match = false;
            
            for (int i = result.size() - 1; i >= 0 && i>=result.size() - 10; i--)
            {
                BiblePassage existingPassage = result.get(i);
                int min=existingPassage.getStartVerseId()-1;
                int max=existingPassage.getEndVerseId()+1;
                if ((passage.getStartVerseId() >= min && passage.getStartVerseId() <= max) || (passage.getEndVerseId() >= min && passage.getEndVerseId() <= max))
                {
                	//System.out.println(String.valueOf(passage.getStartVerseId()) + " - " + String.valueOf(existingPassage.getStartVerseId()));
                    match = true;
                    if (passage.getStartVerseId() < existingPassage.getStartVerseId()) existingPassage.setStartVerseId(passage.getStartVerseId());
                    if (passage.getEndVerseId() > existingPassage.getEndVerseId()) existingPassage.setEndVerseId(passage.getEndVerseId());
                    existingPassage.setModified(true);
                    break;
                }
            }
            if (!match) result.add(passage);
        }
        return result;
    }
    
}