package com.believersresource.data.base;

import java.util.Collections;
import java.util.Comparator;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import com.believersresource.data.NamedParameterStatement;

import com.believersresource.data.Utils;
import com.believersresource.data.RelatedPassage;
import com.believersresource.data.RelatedPassages;

public abstract class RelatedPassagesBase extends ArrayList<RelatedPassage> {

	public RelatedPassagesBase() {}

	public RelatedPassagesBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new RelatedPassage(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public RelatedPassagesBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new RelatedPassage(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public RelatedPassage getById(int id)
	{
		for (RelatedPassage relatedPassage : this) { if (relatedPassage.id == id) return relatedPassage; }
		return null;
	}

	public static RelatedPassages loadAll()
	{
		return new RelatedPassages("{call related_passages_load_all}");
	}

	public static RelatedPassages loadByPassageId(int passageId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("passage_id", passageId);
		return new RelatedPassages("{call related_passages_load_by_passage_id(:passage_id)}", params);
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<RelatedPassage>() { @Override public int compare(RelatedPassage o1, RelatedPassage o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="contenttype") Collections.sort(this, new Comparator<RelatedPassage>() { @Override public int compare(RelatedPassage o1, RelatedPassage o2) { return o1.getContentType().toLowerCase().compareTo(o2.getContentType().toLowerCase()); } });
		else if (propertyName=="contentid") Collections.sort(this, new Comparator<RelatedPassage>() { @Override public int compare(RelatedPassage o1, RelatedPassage o2) { return o1.getContentId() - o2.getContentId(); } });
		else if (propertyName=="passageid") Collections.sort(this, new Comparator<RelatedPassage>() { @Override public int compare(RelatedPassage o1, RelatedPassage o2) { return o1.getPassageId() - o2.getPassageId(); } });
		else if (propertyName=="votes") Collections.sort(this, new Comparator<RelatedPassage>() { @Override public int compare(RelatedPassage o1, RelatedPassage o2) { return o1.getVotes() - o2.getVotes(); } });
	}

	public void reverse()
	{
		RelatedPassages copy = (RelatedPassages)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
