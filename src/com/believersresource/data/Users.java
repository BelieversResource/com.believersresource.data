package com.believersresource.data;

import java.util.Hashtable;
import com.believersresource.data.base.UsersBase;

public class Users extends UsersBase {

	public Users() { super(); }
	public Users(String sql) { super(sql); }
	public Users(String sql, Hashtable<String, Object> params) { super(sql, params); }

}