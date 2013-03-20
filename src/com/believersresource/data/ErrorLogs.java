package com.believersresource.data;

import java.util.Hashtable;
import com.believersresource.data.base.ErrorLogsBase;

public class ErrorLogs extends ErrorLogsBase {

	public ErrorLogs() { super(); }
	public ErrorLogs(String sql) { super(sql); }
	public ErrorLogs(String sql, Hashtable<String, Object> params) { super(sql, params); }

}