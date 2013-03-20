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
import com.believersresource.data.ErrorLog;
import com.believersresource.data.ErrorLogs;

public abstract class ErrorLogsBase extends ArrayList<ErrorLog> {

	public ErrorLogsBase() {}

	public ErrorLogsBase(String sql) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) this.add(new ErrorLog(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public ErrorLogsBase(String sql, Hashtable<String, Object> params) {
		Connection conn = null;
		try{
			try{
				conn = Utils.getConnection();
				NamedParameterStatement s = new NamedParameterStatement(conn, sql);
				Utils.addParamsToStatement(s, params);
				ResultSet rs = s.executeQuery();
				while (rs.next()) this.add(new ErrorLog(rs));
			} finally { conn.close(); }
		} catch (SQLException ex) {}
	}

	public ErrorLog getById(int id)
	{
		for (ErrorLog errorLog : this) { if (errorLog.id == id) return errorLog; }
		return null;
	}

	public static ErrorLogs loadAll()
	{
		return new ErrorLogs("{call error_logs_load_all}");
	}


	public void sort(String propertyName)
	{
		propertyName = propertyName.toLowerCase();
		if (propertyName=="id") Collections.sort(this, new Comparator<ErrorLog>() { @Override public int compare(ErrorLog o1, ErrorLog o2) { return o1.getId() - o2.getId(); } });
		else if (propertyName=="errordate") Collections.sort(this, new Comparator<ErrorLog>() { @Override public int compare(ErrorLog o1, ErrorLog o2) { return o1.getErrorDate().compareTo(o2.getErrorDate()); } });
		else if (propertyName=="url") Collections.sort(this, new Comparator<ErrorLog>() { @Override public int compare(ErrorLog o1, ErrorLog o2) { return o1.getUrl().toLowerCase().compareTo(o2.getUrl().toLowerCase()); } });
		else if (propertyName=="errormessage") Collections.sort(this, new Comparator<ErrorLog>() { @Override public int compare(ErrorLog o1, ErrorLog o2) { return o1.getErrorMessage().toLowerCase().compareTo(o2.getErrorMessage().toLowerCase()); } });
		else if (propertyName=="additionalinfo") Collections.sort(this, new Comparator<ErrorLog>() { @Override public int compare(ErrorLog o1, ErrorLog o2) { return o1.getAdditionalInfo().toLowerCase().compareTo(o2.getAdditionalInfo().toLowerCase()); } });
	}

	public void reverse()
	{
		ErrorLogs copy = (ErrorLogs)this.clone();
		this.clear();
		for (int i=copy.size() -1 ; i>=0; i--) { this.add(copy.get(i)); }
	}

}
