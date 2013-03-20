package com.believersresource.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import com.believersresource.data.base.RelatedPassagesBase;

public class RelatedPassages extends RelatedPassagesBase {

	public RelatedPassages() { super(); }
	public RelatedPassages(String sql) { super(sql); }
	public RelatedPassages(String sql, Hashtable<String, Object> params) { super(sql, params); }

	public static void generateForNewPassage(int passageId)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call related_passages_generate(?)}");
			s.setInt("passage_id", passageId);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex) {}
	}
}