package com.believersresource.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import com.believersresource.data.base.TopicBase;

public class Topic extends TopicBase {

	private int votes;
	private int relatedPassageId;
	private int relatedTopicId;
	private int relatedImageId;
	
	public int getVotes() { return votes; }
	public void setVotes(int votes) { this.votes = votes; }
	public int getRelatedPassageId() { return relatedPassageId; }
	public void setRelatedPassageId(int relatedPassageId) { this.relatedPassageId = relatedPassageId; }
	public int getRelatedTopicId() { return relatedTopicId; }
	public void setRelatedTopicId(int relatedTopicsId) { this.relatedTopicId = relatedTopicsId; }
	public int getRelatedImageId() { return relatedImageId; }
	public void setRelatedImageId(int relatedImageId) { this.relatedImageId = relatedImageId; }
	
	
	
	public Topic() { super(); }
	
	public Topic(ResultSet rs) 
	{ 
		super(rs);
		Hashtable<String,String> ht = Utils.getColumnHash(rs);
		try{
			if (ht.containsKey("votes")) this.votes = rs.getInt("votes");
			if (ht.containsKey("related_passage_id")) this.relatedPassageId = rs.getInt("related_passage_id");
			if (ht.containsKey("related_topic_id")) this.relatedTopicId = rs.getInt("related_topic_id");
			if (ht.containsKey("related_image_id")) this.relatedImageId = rs.getInt("related_image_id");
		} catch (SQLException ex) {}
	}
	
	public Hashtable<String, Object> toHash()
    {
        Hashtable<String, Object> result = new Hashtable<String, Object>();
        if (relatedTopicId > 0) result.put("relatedTopicId", this.relatedTopicId);
        result.put("id", this.getId());
        result.put("name", this.getName());
        result.put("votes", this.getVotes());
        return result;
    }
	
	public static Topic load(String url)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("url", url);
		Topics result = new Topics("SELECT * FROM topics WHERE url=:url", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
	public static Topic loadByBaseWord(String baseWord)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("baseWord", baseWord);
		Topics result = new Topics("SELECT * FROM topics WHERE base_word=:baseWord", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
	public static String getBaseWord(String word)
    {
        String result = word.toLowerCase().trim();
        if (result.endsWith("ies")) result = result.substring(0, result.length() - 3) + "y";
        else if (result.endsWith("ing")) result = result.substring(0, result.length() - 3);
        else if (result.endsWith("es"))
        {
            String previousLetter = result.substring(result.length() - 3, result.length()-2);
            if (previousLetter == "s" || previousLetter == "z" || previousLetter == "x" || previousLetter == "h") result = result.substring(0, result.length() - 2); else result = result.substring(0, result.length() - 1);
        }
        else if (result.endsWith("ed")) result = result.substring(0, result.length() - 2);
        else if (result.endsWith("s")) result = result.substring(0, result.length() - 1);
        return result;
    }
	

}