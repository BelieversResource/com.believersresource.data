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
import com.believersresource.data.LinkClick;
import com.believersresource.data.LinkClicks;

public abstract class LinkClicksBase extends ArrayList<LinkClick> {

	public LinkClicksBase() {}

	public LinkClicksBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new LinkClick(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public LinkClicksBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new LinkClick(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public LinkClick getById(int id)
	{
		for (LinkClick linkClick : this) { if (linkClick.id == id) return linkClick; }
		return null;
	}

	public static LinkClicks loadAll()
	{
		return new LinkClicks("{call link_clicks_load_all}");
	}

	public static LinkClicks loadByLinkId(int linkId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("link_id", linkId);
		return new LinkClicks("{call link_clicks_load_by_link_id(:link_id)}", params);
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<LinkClick>() { @Override public int compare(LinkClick o1, LinkClick o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="linkid") Collections.sort(this, new Comparator<LinkClick>() { @Override public int compare(LinkClick o1, LinkClick o2) { return o1.getLinkId() - o2.getLinkId(); } });
		else if (propertyName=="ipaddress") Collections.sort(this, new Comparator<LinkClick>() { @Override public int compare(LinkClick o1, LinkClick o2) { return o1.getIpAddress() - o2.getIpAddress(); } });
		else if (propertyName=="clickdate") Collections.sort(this, new Comparator<LinkClick>() { @Override public int compare(LinkClick o1, LinkClick o2) { return o1.getClickDate().compareTo(o2.getClickDate()); } });
	}

	public void reverse()
	{
		LinkClicks copy = (LinkClicks)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
