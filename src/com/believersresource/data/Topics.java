package com.believersresource.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.believersresource.data.base.TopicsBase;

public class Topics extends TopicsBase {

	public Topics() { super(); }
	public Topics(String sql) { super(sql); }
	public Topics(String sql, Hashtable<String, Object> params) { super(sql, params); }

	public ArrayList<Object> toList()
    {
        ArrayList<Object> result = new ArrayList<Object>();
        for (Topic t : this) { result.add(t.toHash()); }
        return result;
    }
	
	
	public static Topics loadPopular(int count)
	{
		return new Topics("select t.* from temp_popular_topics tpt inner join topics t on t.id=tpt.topic_id order by tpt.votes desc limit " + String.valueOf(count));
	}
	
	public static Topics loadRelated(String contentType, int contentId, int count)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("content_type", contentType);
		params.put("content_id", contentId);
		params.put("count", count);
		return new Topics("{call related_topics_load_by_content(:content_type,:content_id,:count)}", params);
	}
	
	public static Topics loadForPassage(int passageId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("passageId", passageId);
		return new Topics("select t.*, rp.Id as related_passage_id, rp.votes from related_passages rp inner join topics t on t.Id=rp.content_id where rp.passage_id=:passageId and rp.content_type='topic' and rp.Votes>=0 order by rp.Votes desc", params);
	}
	
	public static Topics loadForImage(int imageId, int count)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("imageId", imageId);
		params.put("count", count);
		return new Topics("select t.*, ri.Id as related_image_id, ri.votes from related_images ri inner join Topics t on t.Id=ri.content_id where ri.image_id=:imageId and ri.content_type='topic' and ri.Votes>=0 order by ri.Votes desc limit :count", params);
	}
	
	public static void generateForPassage(int passageId, int startVerseId, int endVerseId)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call topics_generate_for_passage(?, ?, ?)}");
			s.setInt("passage_id", passageId);
			s.setInt("start_verse_id", startVerseId);
			s.setInt("end_verse_id", endVerseId);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex) {}
	}
	
}