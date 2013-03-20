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
import com.believersresource.data.User;
import com.believersresource.data.Users;

public abstract class UsersBase extends ArrayList<User> {

	public UsersBase() {}

	public UsersBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new User(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public UsersBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new User(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public User getById(int id)
	{
		for (User user : this) { if (user.id == id) return user; }
		return null;
	}

	public static Users loadAll()
	{
		return new Users("{call users_load_all}");
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<User>() { @Override public int compare(User o1, User o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="displayname") Collections.sort(this, new Comparator<User>() { @Override public int compare(User o1, User o2) { return o1.getDisplayName().toLowerCase().compareTo(o2.getDisplayName().toLowerCase()); } });
		else if (propertyName=="email") Collections.sort(this, new Comparator<User>() { @Override public int compare(User o1, User o2) { return o1.getEmail().toLowerCase().compareTo(o2.getEmail().toLowerCase()); } });
		else if (propertyName=="password") Collections.sort(this, new Comparator<User>() { @Override public int compare(User o1, User o2) { return o1.getPassword().toLowerCase().compareTo(o2.getPassword().toLowerCase()); } });
		else if (propertyName=="facebookid") Collections.sort(this, new Comparator<User>() { @Override public int compare(User o1, User o2) { return (int)o1.getFacebookId() - (int)o2.getFacebookId(); } });
		else if (propertyName=="registerdate") Collections.sort(this, new Comparator<User>() { @Override public int compare(User o1, User o2) { return o1.getRegisterDate().compareTo(o2.getRegisterDate()); } });
		else if (propertyName=="lastlogin") Collections.sort(this, new Comparator<User>() { @Override public int compare(User o1, User o2) { return o1.getLastLogin().compareTo(o2.getLastLogin()); } });
		else if (propertyName=="authcode") Collections.sort(this, new Comparator<User>() { @Override public int compare(User o1, User o2) { return o1.getAuthCode().toLowerCase().compareTo(o2.getAuthCode().toLowerCase()); } });
	}

	public void reverse()
	{
		Users copy = (Users)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
