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
import com.believersresource.data.ErrorLog;
import com.believersresource.data.ErrorLogs;

public abstract class ErrorLogBase {
	protected int id;
	protected Date errorDate;
	protected String url;
	protected String errorMessage;
	protected String additionalInfo;

	protected boolean idIsNull = true;
	protected boolean errorDateIsNull = true;
	protected boolean urlIsNull = true;
	protected boolean errorMessageIsNull = true;
	protected boolean additionalInfoIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public Date getErrorDate() { return errorDate; };
	public void setErrorDate(Date value) { errorDate = value; errorDateIsNull = false; };
	public String getUrl() { return url; };
	public void setUrl(String value) { url = value; urlIsNull = false; };
	public String getErrorMessage() { return errorMessage; };
	public void setErrorMessage(String value) { errorMessage = value; errorMessageIsNull = false; };
	public String getAdditionalInfo() { return additionalInfo; };
	public void setAdditionalInfo(String value) { additionalInfo = value; additionalInfoIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getErrorDateIsNull() { return errorDateIsNull; };
	public void setErrorDateIsNull(boolean value) { errorDateIsNull = value; };
	public boolean getUrlIsNull() { return urlIsNull; };
	public void setUrlIsNull(boolean value) { urlIsNull = value; };
	public boolean getErrorMessageIsNull() { return errorMessageIsNull; };
	public void setErrorMessageIsNull(boolean value) { errorMessageIsNull = value; };
	public boolean getAdditionalInfoIsNull() { return additionalInfoIsNull; };
	public void setAdditionalInfoIsNull(boolean value) { additionalInfoIsNull = value; };


	public ErrorLogBase() {}

	public ErrorLogBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.errorDate = rs.getDate("error_date");
			if (!rs.wasNull()) this.errorDateIsNull = false;

			this.url = rs.getString("url");
			if (!rs.wasNull()) this.urlIsNull = false;

			this.errorMessage = rs.getString("error_message");
			if (!rs.wasNull()) this.errorMessageIsNull = false;

			this.additionalInfo = rs.getString("additional_info");
			if (!rs.wasNull()) this.additionalInfoIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call error_log_save(?, ?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.errorDateIsNull) s.setNull("error_date", java.sql.Types.TIMESTAMP); else s.setTimestamp("error_date", new java.sql.Timestamp(this.errorDate.getTime()));
			if (this.urlIsNull) s.setNull("url", java.sql.Types.VARCHAR); else s.setString("url", this.url);
			if (this.errorMessageIsNull) s.setNull("error_message", java.sql.Types.OTHER); else s.setString("error_message", this.errorMessage);
			if (this.additionalInfoIsNull) s.setNull("additional_info", java.sql.Types.OTHER); else s.setString("additional_info", this.additionalInfo);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static ErrorLog load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		ErrorLogs result = new ErrorLogs("{call error_log_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call error_log_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
