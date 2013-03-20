package com.believersresource.data;

import java.util.Hashtable;
import com.believersresource.data.base.VotesBase;

public class Votes extends VotesBase {

	public Votes() { super(); }
	public Votes(String sql) { super(sql); }
	public Votes(String sql, Hashtable<String, Object> params) { super(sql, params); }

}