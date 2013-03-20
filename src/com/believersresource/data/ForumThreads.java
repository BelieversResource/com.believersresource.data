package com.believersresource.data;

import java.util.Hashtable;
import com.believersresource.data.base.ForumThreadsBase;

public class ForumThreads extends ForumThreadsBase {

	public ForumThreads() { super(); }
	public ForumThreads(String sql) { super(sql); }
	public ForumThreads(String sql, Hashtable<String, Object> params) { super(sql, params); }

	public static ForumThreads loadByExtendedByCategoryId(int categoryId, int minVotes)
	{
		String sql = "select ft.*,fc.Votes as Votes, fc.Id as first_comment_id, fcu.display_name as first_user_name, fc.date_posted as first_date_posted,lcu.display_name as last_user_name,lc.date_posted as last_date_posted, (select count(*) from comments where content_type='forumthread' and content_id=ft.id and Votes>=:minVotes)+1 as posts " +
	            "from forum_threads ft " +
	            "inner join comments fc on fc.content_type='forumthreadstart' and fc.content_id=ft.id and fc.Active=1 and fc.Votes>=:minVotes " +
	            "inner join Users fcu on fcu.id=fc.user_id " +
	            "inner join Comments lc on lc.Id=(select id from Comments where content_type in ('forumthreadstart','forumthread') and content_id=ft.Id and Active=1 and Votes>=:minVotes order by Id desc limit 1) " +
	            "inner join Users lcu on lcu.id=lc.user_id " +
	            "where ft.category_id=:categoryId order by lc.date_posted desc";
		
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("categoryId", categoryId);
		params.put("minVotes", minVotes);
		return new ForumThreads(sql, params);
	}
	
	public static ForumThreads loadForPassage(int passageId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("passageId", passageId);
		return new ForumThreads("select ft.Id,ft.Title,ft.Url,ft.category_id, sum(c.Votes*rt.Votes*rp.Votes) from forum_threads ft  inner join Comments c on c.content_type='forumthreadstart' and c.content_id=ft.Id  inner join related_topics rt on rt.content_type='forumthread' and rt.content_id=ft.Id inner join related_passages rp on rp.content_type='topic' and rp.content_id=rt.topic_id where rp.passage_id=:passageId group by ft.Id,ft.Title,ft.Url,ft.category_id order by sum(c.Votes*rt.Votes*rp.Votes) desc", params);
	}
	
	public static ForumThreads loadForTopic(int topicId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("topicId", topicId);
		return new ForumThreads("select ft.* from forum_threads ft inner join comments c on c.content_type='forumthreadstart' and c.content_id=ft.Id inner join related_topics rt on rt.content_type='forumthread' and rt.content_id=ft.Id where rt.topic_id=:topicId order by c.votes*rt.votes desc", params);
	}
	
}