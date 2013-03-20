package com.believersresource.data.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Date;

import com.believersresource.data.Utils;
import com.believersresource.data.BiblePassage;
import com.believersresource.data.BiblePassages;

public abstract class BiblePassageBase {
	protected int id;
	protected int startVerseId;
	protected int endVerseId;

	protected boolean idIsNull = true;
	protected boolean startVerseIdIsNull = true;
	protected boolean endVerseIdIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public int getStartVerseId() { return startVerseId; };
	public void setStartVerseId(int value) { startVerseId = value; startVerseIdIsNull = false; };
	public int getEndVerseId() { return endVerseId; };
	public void setEndVerseId(int value) { endVerseId = value; endVerseIdIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getStartVerseIdIsNull() { return startVerseIdIsNull; };
	public void setStartVerseIdIsNull(boolean value) { startVerseIdIsNull = value; };
	public boolean getEndVerseIdIsNull() { return endVerseIdIsNull; };
	public void setEndVerseIdIsNull(boolean value) { endVerseIdIsNull = value; };


	public BiblePassageBase() {}

	public BiblePassageBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.startVerseId = rs.getInt("start_verse_id");
			if (!rs.wasNull()) this.startVerseIdIsNull = false;

			this.endVerseId = rs.getInt("end_verse_id");
			if (!rs.wasNull()) this.endVerseIdIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call bible_passage_save(?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.startVerseIdIsNull) s.setNull("start_verse_id", java.sql.Types.INTEGER); else s.setInt("start_verse_id", this.startVerseId);
			if (this.endVerseIdIsNull) s.setNull("end_verse_id", java.sql.Types.INTEGER); else s.setInt("end_verse_id", this.endVerseId);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static BiblePassage load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		BiblePassages result = new BiblePassages("{call bible_passage_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call bible_passage_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
