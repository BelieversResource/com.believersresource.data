package com.believersresource.data;

import java.sql.ResultSet;
import java.util.Hashtable;

import com.believersresource.data.base.UserBase;

public class User extends UserBase {

	public User() { super(); }
	public User(ResultSet rs) { super(rs); }

	public static User load(String email, String password)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("email", email);
		params.put("password", password);
		Users result = new Users("SELECT * FROM users WHERE lower(email)=:email AND password=:password", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
	public static User load(String email)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("email", email);
		Users result = new Users("SELECT * FROM users WHERE lower(email)=:email", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
	public static User loadByDisplayName(String displayName)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("displayName", displayName);
		Users result = new Users("SELECT * FROM users WHERE lower(display_name)=:displayName", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
	public static User loadByAuthCode(String authCode)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("authCode", authCode);
		Users result = new Users("SELECT * FROM users WHERE auth_code=:authCode", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
	public static User loadByFacebookId(double facebookId)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("facebookId", facebookId);
		Users result = new Users("SELECT * FROM users WHERE facebook_id=:facebookId", params);
		if (result.size() == 0) return null; else return result.get(0);
	}
	
}