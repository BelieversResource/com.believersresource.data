package com.believersresource.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import com.believersresource.data.base.ImageBase;

public class Image extends ImageBase {

	private int votes;
	private int relatedImageId;

	public int getVotes() { return votes; }
	public void setVotes(int votes) { this.votes = votes; }
	public int getRelatedImageId() { return relatedImageId; }
	public void setRelatedImageId(int relatedImageId) { this.relatedImageId = relatedImageId; }
	
	public Image() { super(); }
	
	public Image(ResultSet rs) 
	{ 
		super(rs);
		Hashtable<String,String> ht = Utils.getColumnHash(rs);
		try{
			if (ht.containsKey("votes")) this.votes = rs.getInt("votes");
			if (ht.containsKey("related_image_id")) this.relatedImageId = rs.getInt("related_image_id");
		} catch (SQLException ex) {}
	}

	public static Image loadRelated(String contentType, int contentId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("contentType", contentType);
		params.put("contentId", contentId);
		Images images = new Images("select * from Images where id = (select ri.image_id from related_images ri where ri.content_type=:contentType and ri.content_id=:contentId order by ri.Votes desc limit 1)", params);
		if (images.size() > 0) return images.get(0); else return null;
	}
	
	public static Image loadForPassage(int passageId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("passageId", passageId);
		Images images = new Images("select * from Images where id = (select ri.image_id from  related_passages rp inner join related_images ri on ri.content_type='topic' and ri.content_id=rp.content_id where rp.passage_id=:passageId and rp.content_type='topic' group by ri.image_id order by SUM(rp.Votes*ri.Votes) desc limit 1)", params);
		if (images.size() > 0) return images.get(0); else return null;
	}
	
}