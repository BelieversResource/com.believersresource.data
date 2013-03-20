package com.believersresource.data.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Date;

import com.believersresource.data.Utils;
import com.believersresource.data.Topic;
import com.believersresource.data.Topics;

public abstract class TopicBase {
	protected int id;
	protected String name;
	protected String url;
	protected String baseWord;
	protected boolean openBible;

	protected boolean idIsNull = true;
	protected boolean nameIsNull = true;
	protected boolean urlIsNull = true;
	protected boolean baseWordIsNull = true;
	protected boolean openBibleIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public String getName() { return name; };
	public void setName(String value) { name = value; nameIsNull = false; };
	public String getUrl() { return url; };
	public void setUrl(String value) { url = value; urlIsNull = false; };
	public String getBaseWord() { return baseWord; };
	public void setBaseWord(String value) { baseWord = value; baseWordIsNull = false; };
	public boolean getOpenBible() { return openBible; };
	public void setOpenBible(boolean value) { openBible = value; openBibleIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getNameIsNull() { return nameIsNull; };
	public void setNameIsNull(boolean value) { nameIsNull = value; };
	public boolean getUrlIsNull() { return urlIsNull; };
	public void setUrlIsNull(boolean value) { urlIsNull = value; };
	public boolean getBaseWordIsNull() { return baseWordIsNull; };
	public void setBaseWordIsNull(boolean value) { baseWordIsNull = value; };
	public boolean getOpenBibleIsNull() { return openBibleIsNull; };
	public void setOpenBibleIsNull(boolean value) { openBibleIsNull = value; };


	public TopicBase() {}

	public TopicBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.name = rs.getString("name");
			if (!rs.wasNull()) this.nameIsNull = false;

			this.url = rs.getString("url");
			if (!rs.wasNull()) this.urlIsNull = false;

			this.baseWord = rs.getString("base_word");
			if (!rs.wasNull()) this.baseWordIsNull = false;

			this.openBible = rs.getBoolean("open_bible");
			if (!rs.wasNull()) this.openBibleIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call topic_save(?, ?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.nameIsNull) s.setNull("name", java.sql.Types.VARCHAR); else s.setString("name", this.name);
			if (this.urlIsNull) s.setNull("url", java.sql.Types.VARCHAR); else s.setString("url", this.url);
			if (this.baseWordIsNull) s.setNull("base_word", java.sql.Types.VARCHAR); else s.setString("base_word", this.baseWord);
			if (this.openBibleIsNull) s.setNull("open_bible", java.sql.Types.BIT); else s.setBoolean("open_bible", this.openBible);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static Topic load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		Topics result = new Topics("{call topic_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call topic_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
