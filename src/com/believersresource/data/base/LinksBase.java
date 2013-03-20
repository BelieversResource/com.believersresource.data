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
import com.believersresource.data.Link;
import com.believersresource.data.Links;

public abstract class LinksBase extends ArrayList<Link> {

	public LinksBase() {}

	public LinksBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new Link(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public LinksBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new Link(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public Link getById(int id)
	{
		for (Link link : this) { if (link.id == id) return link; }
		return null;
	}

	public static Links loadAll()
	{
		return new Links("{call links_load_all}");
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<Link>() { @Override public int compare(Link o1, Link o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="contenttype") Collections.sort(this, new Comparator<Link>() { @Override public int compare(Link o1, Link o2) { return o1.getContentType().toLowerCase().compareTo(o2.getContentType().toLowerCase()); } });
		else if (propertyName=="contentid") Collections.sort(this, new Comparator<Link>() { @Override public int compare(Link o1, Link o2) { return o1.getContentId() - o2.getContentId(); } });
		else if (propertyName=="linktype") Collections.sort(this, new Comparator<Link>() { @Override public int compare(Link o1, Link o2) { return o1.getLinkType().toLowerCase().compareTo(o2.getLinkType().toLowerCase()); } });
		else if (propertyName=="name") Collections.sort(this, new Comparator<Link>() { @Override public int compare(Link o1, Link o2) { return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()); } });
		else if (propertyName=="url") Collections.sort(this, new Comparator<Link>() { @Override public int compare(Link o1, Link o2) { return o1.getUrl().toLowerCase().compareTo(o2.getUrl().toLowerCase()); } });
		else if (propertyName=="votes") Collections.sort(this, new Comparator<Link>() { @Override public int compare(Link o1, Link o2) { return o1.getVotes() - o2.getVotes(); } });
	}

	public void reverse()
	{
		Links copy = (Links)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
