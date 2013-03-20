package com.believersresource.data;

import java.util.Hashtable;
import com.believersresource.data.base.CommentsBase;

public class Comments extends CommentsBase {

	public Comments() { super(); }
	public Comments(String sql) { super(sql); }
	public Comments(String sql, Hashtable<String, Object> params) { super(sql, params); }

	public static Comments load(String contentType, int contentId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("contentType", contentType);
		params.put("contentId", contentId);
		return new Comments("SELECT c.*, u.display_name as user_name FROM Comments c INNER JOIN Users u on u.Id=c.user_id WHERE content_type=:contentType AND content_id=:contentId order by c.Votes desc, c.date_posted", params);
	}
	
	public Comments buildTree(int parentId)
    {
        Comments result = getByParentId(parentId);
        for (Comment comment : result)
        {
            Comments children = buildTree(comment.getId());
            if (children.size() > 0) comment.setChildComments(children);
        }
        return result;
    }
	
	public Comments getByParentId(int parentId)
    {
        Comments result = new Comments();
        for (Comment comment : this)
        {
            if (comment.getParentId() == parentId) result.add(comment);
        }
        return result;
    }
	
	public static Comments loadByParentId(int parentId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("parent_id", parentId);
		return new Comments("{call comments_load_by_parent_id(:parent_id)}", params);
	}
	
}