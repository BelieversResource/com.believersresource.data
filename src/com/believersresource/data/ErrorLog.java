package com.believersresource.data;

import java.sql.ResultSet;
import com.believersresource.data.base.ErrorLogBase;

public class ErrorLog extends ErrorLogBase {

	public ErrorLog() { super(); }
	public ErrorLog(ResultSet rs) { super(rs); }

}