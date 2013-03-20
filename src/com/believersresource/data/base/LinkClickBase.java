package com.believersresource.data.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Date;
import java.sql.Timestamp;

import com.believersresource.data.Utils;
import com.believersresource.data.LinkClick;
import com.believersresource.data.LinkClicks;

public abstract class LinkClickBase {
	protected int id;
	protected int linkId;
	protected int ipAddress;
	protected Date clickDate;

	protected boolean idIsNull = true;
	protected boolean linkIdIsNull = true;
	protected boolean ipAddressIsNull = true;
	protected boolean clickDateIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public int getLinkId() { return linkId; };
	public void setLinkId(int value) { linkId = value; linkIdIsNull = false; };
	public int getIpAddress() { return ipAddress; };
	public void setIpAddress(int value) { ipAddress = value; ipAddressIsNull = false; };
	public Date getClickDate() { return clickDate; };
	public void setClickDate(Date value) { clickDate = value; clickDateIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getLinkIdIsNull() { return linkIdIsNull; };
	public void setLinkIdIsNull(boolean value) { linkIdIsNull = value; };
	public boolean getIpAddressIsNull() { return ipAddressIsNull; };
	public void setIpAddressIsNull(boolean value) { ipAddressIsNull = value; };
	public boolean getClickDateIsNull() { return clickDateIsNull; };
	public void setClickDateIsNull(boolean value) { clickDateIsNull = value; };


	public LinkClickBase() {}

	public LinkClickBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.linkId = rs.getInt("link_id");
			if (!rs.wasNull()) this.linkIdIsNull = false;

			this.ipAddress = rs.getInt("ip_address");
			if (!rs.wasNull()) this.ipAddressIsNull = false;

			this.clickDate = rs.getDate("click_date");
			if (!rs.wasNull()) this.clickDateIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call link_click_save(?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.linkIdIsNull) s.setNull("link_id", java.sql.Types.INTEGER); else s.setInt("link_id", this.linkId);
			if (this.ipAddressIsNull) s.setNull("ip_address", java.sql.Types.INTEGER); else s.setInt("ip_address", this.ipAddress);
			if (this.clickDateIsNull) s.setNull("click_date", java.sql.Types.TIMESTAMP); else s.setTimestamp("click_date", new java.sql.Timestamp(this.clickDate.getTime()));

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static LinkClick load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		LinkClicks result = new LinkClicks("{call link_click_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call link_click_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
