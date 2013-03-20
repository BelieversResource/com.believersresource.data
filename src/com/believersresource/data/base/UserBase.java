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
import com.believersresource.data.User;
import com.believersresource.data.Users;

public abstract class UserBase {
	protected int id;
	protected String displayName;
	protected String email;
	protected String password;
	protected double facebookId;
	protected Date registerDate;
	protected Date lastLogin;
	protected String authCode;

	protected boolean idIsNull = true;
	protected boolean displayNameIsNull = true;
	protected boolean emailIsNull = true;
	protected boolean passwordIsNull = true;
	protected boolean facebookIdIsNull = true;
	protected boolean registerDateIsNull = true;
	protected boolean lastLoginIsNull = true;
	protected boolean authCodeIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public String getDisplayName() { return displayName; };
	public void setDisplayName(String value) { displayName = value; displayNameIsNull = false; };
	public String getEmail() { return email; };
	public void setEmail(String value) { email = value; emailIsNull = false; };
	public String getPassword() { return password; };
	public void setPassword(String value) { password = value; passwordIsNull = false; };
	public double getFacebookId() { return facebookId; };
	public void setFacebookId(double value) { facebookId = value; facebookIdIsNull = false; };
	public Date getRegisterDate() { return registerDate; };
	public void setRegisterDate(Date value) { registerDate = value; registerDateIsNull = false; };
	public Date getLastLogin() { return lastLogin; };
	public void setLastLogin(Date value) { lastLogin = value; lastLoginIsNull = false; };
	public String getAuthCode() { return authCode; };
	public void setAuthCode(String value) { authCode = value; authCodeIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getDisplayNameIsNull() { return displayNameIsNull; };
	public void setDisplayNameIsNull(boolean value) { displayNameIsNull = value; };
	public boolean getEmailIsNull() { return emailIsNull; };
	public void setEmailIsNull(boolean value) { emailIsNull = value; };
	public boolean getPasswordIsNull() { return passwordIsNull; };
	public void setPasswordIsNull(boolean value) { passwordIsNull = value; };
	public boolean getFacebookIdIsNull() { return facebookIdIsNull; };
	public void setFacebookIdIsNull(boolean value) { facebookIdIsNull = value; };
	public boolean getRegisterDateIsNull() { return registerDateIsNull; };
	public void setRegisterDateIsNull(boolean value) { registerDateIsNull = value; };
	public boolean getLastLoginIsNull() { return lastLoginIsNull; };
	public void setLastLoginIsNull(boolean value) { lastLoginIsNull = value; };
	public boolean getAuthCodeIsNull() { return authCodeIsNull; };
	public void setAuthCodeIsNull(boolean value) { authCodeIsNull = value; };


	public UserBase() {}

	public UserBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.displayName = rs.getString("display_name");
			if (!rs.wasNull()) this.displayNameIsNull = false;

			this.email = rs.getString("email");
			if (!rs.wasNull()) this.emailIsNull = false;

			this.password = rs.getString("password");
			if (!rs.wasNull()) this.passwordIsNull = false;

			this.facebookId = rs.getDouble("facebook_id");
			if (!rs.wasNull()) this.facebookIdIsNull = false;

			this.registerDate = rs.getDate("register_date");
			if (!rs.wasNull()) this.registerDateIsNull = false;

			this.lastLogin = rs.getDate("last_login");
			if (!rs.wasNull()) this.lastLoginIsNull = false;

			this.authCode = rs.getString("auth_code");
			if (!rs.wasNull()) this.authCodeIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call user_save(?, ?, ?, ?, ?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.displayNameIsNull) s.setNull("display_name", java.sql.Types.VARCHAR); else s.setString("display_name", this.displayName);
			if (this.emailIsNull) s.setNull("email", java.sql.Types.VARCHAR); else s.setString("email", this.email);
			if (this.passwordIsNull) s.setNull("password", java.sql.Types.VARCHAR); else s.setString("password", this.password);
			if (this.facebookIdIsNull) s.setNull("facebook_id", java.sql.Types.DOUBLE); else s.setDouble("facebook_id", this.facebookId);
			if (this.registerDateIsNull) s.setNull("register_date", java.sql.Types.TIMESTAMP); else s.setTimestamp("register_date", new java.sql.Timestamp(this.registerDate.getTime()));
			if (this.lastLoginIsNull) s.setNull("last_login", java.sql.Types.TIMESTAMP); else s.setTimestamp("last_login", new java.sql.Timestamp(this.lastLogin.getTime()));
			if (this.authCodeIsNull) s.setNull("auth_code", java.sql.Types.VARCHAR); else s.setString("auth_code", this.authCode);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static User load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		Users result = new Users("{call user_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call user_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
