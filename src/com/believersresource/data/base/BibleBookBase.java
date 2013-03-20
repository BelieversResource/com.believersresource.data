package com.believersresource.data.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Date;

import com.believersresource.data.Utils;
import com.believersresource.data.BibleBook;
import com.believersresource.data.BibleBooks;

public abstract class BibleBookBase {
	protected int id;
	protected String name;
	protected boolean newTestament;
	protected String abbreviations;

	protected boolean idIsNull = true;
	protected boolean nameIsNull = true;
	protected boolean newTestamentIsNull = true;
	protected boolean abbreviationsIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public String getName() { return name; };
	public void setName(String value) { name = value; nameIsNull = false; };
	public boolean getNewTestament() { return newTestament; };
	public void setNewTestament(boolean value) { newTestament = value; newTestamentIsNull = false; };
	public String getAbbreviations() { return abbreviations; };
	public void setAbbreviations(String value) { abbreviations = value; abbreviationsIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getNameIsNull() { return nameIsNull; };
	public void setNameIsNull(boolean value) { nameIsNull = value; };
	public boolean getNewTestamentIsNull() { return newTestamentIsNull; };
	public void setNewTestamentIsNull(boolean value) { newTestamentIsNull = value; };
	public boolean getAbbreviationsIsNull() { return abbreviationsIsNull; };
	public void setAbbreviationsIsNull(boolean value) { abbreviationsIsNull = value; };


	public BibleBookBase() {}

	public BibleBookBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.name = rs.getString("name");
			if (!rs.wasNull()) this.nameIsNull = false;

			this.newTestament = rs.getBoolean("new_testament");
			if (!rs.wasNull()) this.newTestamentIsNull = false;

			this.abbreviations = rs.getString("abbreviations");
			if (!rs.wasNull()) this.abbreviationsIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call bible_book_save(?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.nameIsNull) s.setNull("name", java.sql.Types.VARCHAR); else s.setString("name", this.name);
			if (this.newTestamentIsNull) s.setNull("new_testament", java.sql.Types.BIT); else s.setBoolean("new_testament", this.newTestament);
			if (this.abbreviationsIsNull) s.setNull("abbreviations", java.sql.Types.VARCHAR); else s.setString("abbreviations", this.abbreviations);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static BibleBook load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		BibleBooks result = new BibleBooks("{call bible_book_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call bible_book_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
