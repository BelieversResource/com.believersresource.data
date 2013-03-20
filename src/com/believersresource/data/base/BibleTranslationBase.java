package com.believersresource.data.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Date;

import com.believersresource.data.Utils;
import com.believersresource.data.BibleTranslation;
import com.believersresource.data.BibleTranslations;

public abstract class BibleTranslationBase {
	protected int id;
	protected String name;
	protected String abbreviation;

	protected boolean idIsNull = true;
	protected boolean nameIsNull = true;
	protected boolean abbreviationIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public String getName() { return name; };
	public void setName(String value) { name = value; nameIsNull = false; };
	public String getAbbreviation() { return abbreviation; };
	public void setAbbreviation(String value) { abbreviation = value; abbreviationIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getNameIsNull() { return nameIsNull; };
	public void setNameIsNull(boolean value) { nameIsNull = value; };
	public boolean getAbbreviationIsNull() { return abbreviationIsNull; };
	public void setAbbreviationIsNull(boolean value) { abbreviationIsNull = value; };


	public BibleTranslationBase() {}

	public BibleTranslationBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.name = rs.getString("name");
			if (!rs.wasNull()) this.nameIsNull = false;

			this.abbreviation = rs.getString("abbreviation");
			if (!rs.wasNull()) this.abbreviationIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call bible_translation_save(?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.nameIsNull) s.setNull("name", java.sql.Types.VARCHAR); else s.setString("name", this.name);
			if (this.abbreviationIsNull) s.setNull("abbreviation", java.sql.Types.VARCHAR); else s.setString("abbreviation", this.abbreviation);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static BibleTranslation load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		BibleTranslations result = new BibleTranslations("{call bible_translation_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call bible_translation_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
