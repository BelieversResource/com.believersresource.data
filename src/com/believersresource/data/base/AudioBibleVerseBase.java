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
import com.believersresource.data.AudioBibleVerse;
import com.believersresource.data.AudioBibleVerses;

public abstract class AudioBibleVerseBase {
	protected int id;
	protected int translationId;
	protected int verseId;
	protected double startTime;
	protected double endTime;
	protected int ipAddress;
	protected Date dateWorked;
	protected boolean approved;

	protected boolean idIsNull = true;
	protected boolean translationIdIsNull = true;
	protected boolean verseIdIsNull = true;
	protected boolean startTimeIsNull = true;
	protected boolean endTimeIsNull = true;
	protected boolean ipAddressIsNull = true;
	protected boolean dateWorkedIsNull = true;
	protected boolean approvedIsNull = true;


	public int getId() { return id; };
	public void setId(int value) { id = value; idIsNull = false; };
	public int getTranslationId() { return translationId; };
	public void setTranslationId(int value) { translationId = value; translationIdIsNull = false; };
	public int getVerseId() { return verseId; };
	public void setVerseId(int value) { verseId = value; verseIdIsNull = false; };
	public double getStartTime() { return startTime; };
	public void setStartTime(double value) { startTime = value; startTimeIsNull = false; };
	public double getEndTime() { return endTime; };
	public void setEndTime(double value) { endTime = value; endTimeIsNull = false; };
	public int getIpAddress() { return ipAddress; };
	public void setIpAddress(int value) { ipAddress = value; ipAddressIsNull = false; };
	public Date getDateWorked() { return dateWorked; };
	public void setDateWorked(Date value) { dateWorked = value; dateWorkedIsNull = false; };
	public boolean getApproved() { return approved; };
	public void setApproved(boolean value) { approved = value; approvedIsNull = false; };

	public boolean getIdIsNull() { return idIsNull; };
	public void setIdIsNull(boolean value) { idIsNull = value; };
	public boolean getTranslationIdIsNull() { return translationIdIsNull; };
	public void setTranslationIdIsNull(boolean value) { translationIdIsNull = value; };
	public boolean getVerseIdIsNull() { return verseIdIsNull; };
	public void setVerseIdIsNull(boolean value) { verseIdIsNull = value; };
	public boolean getStartTimeIsNull() { return startTimeIsNull; };
	public void setStartTimeIsNull(boolean value) { startTimeIsNull = value; };
	public boolean getEndTimeIsNull() { return endTimeIsNull; };
	public void setEndTimeIsNull(boolean value) { endTimeIsNull = value; };
	public boolean getIpAddressIsNull() { return ipAddressIsNull; };
	public void setIpAddressIsNull(boolean value) { ipAddressIsNull = value; };
	public boolean getDateWorkedIsNull() { return dateWorkedIsNull; };
	public void setDateWorkedIsNull(boolean value) { dateWorkedIsNull = value; };
	public boolean getApprovedIsNull() { return approvedIsNull; };
	public void setApprovedIsNull(boolean value) { approvedIsNull = value; };


	public AudioBibleVerseBase() {}

	public AudioBibleVerseBase(ResultSet rs) {
		try{
			this.id = rs.getInt("id");
			if (!rs.wasNull()) this.idIsNull = false;

			this.translationId = rs.getInt("translation_id");
			if (!rs.wasNull()) this.translationIdIsNull = false;

			this.verseId = rs.getInt("verse_id");
			if (!rs.wasNull()) this.verseIdIsNull = false;

			this.startTime = rs.getDouble("start_time");
			if (!rs.wasNull()) this.startTimeIsNull = false;

			this.endTime = rs.getDouble("end_time");
			if (!rs.wasNull()) this.endTimeIsNull = false;

			this.ipAddress = rs.getInt("ip_address");
			if (!rs.wasNull()) this.ipAddressIsNull = false;

			this.dateWorked = rs.getDate("date_worked");
			if (!rs.wasNull()) this.dateWorkedIsNull = false;

			this.approved = rs.getBoolean("approved");
			if (!rs.wasNull()) this.approvedIsNull = false;

		} catch (SQLException ex) {}
	}

	public void save()
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call audio_bible_verse_save(?, ?, ?, ?, ?, ?, ?, ?)}");

			if (this.idIsNull) s.setNull("id", java.sql.Types.INTEGER); else s.setInt("id", this.id);
			if (this.translationIdIsNull) s.setNull("translation_id", java.sql.Types.INTEGER); else s.setInt("translation_id", this.translationId);
			if (this.verseIdIsNull) s.setNull("verse_id", java.sql.Types.INTEGER); else s.setInt("verse_id", this.verseId);
			if (this.startTimeIsNull) s.setNull("start_time", java.sql.Types.DOUBLE); else s.setDouble("start_time", this.startTime);
			if (this.endTimeIsNull) s.setNull("end_time", java.sql.Types.DOUBLE); else s.setDouble("end_time", this.endTime);
			if (this.ipAddressIsNull) s.setNull("ip_address", java.sql.Types.INTEGER); else s.setInt("ip_address", this.ipAddress);
			if (this.dateWorkedIsNull) s.setNull("date_worked", java.sql.Types.TIMESTAMP); else s.setTimestamp("date_worked", new java.sql.Timestamp(this.dateWorked.getTime()));
			if (this.approvedIsNull) s.setNull("approved", java.sql.Types.BIT); else s.setBoolean("approved", this.approved);

			ResultSet rs = s.executeQuery();
			while (rs.next()) this.setId(rs.getInt(1));
			conn.close();
		} catch (SQLException ex) {}
	}

	public static AudioBibleVerse load(int id)
	{
		Hashtable<String, Object> params = new Hashtable<String,Object>();
		params.put("id", id);
		AudioBibleVerses result = new AudioBibleVerses("{call audio_bible_verse_load(:id)}", params);
		if (result.size() == 0) return null; else return result.get(0);
	}

	public static void delete(int id)
	{
		try{
			Connection conn=Utils.getConnection();
			CallableStatement s = conn.prepareCall("{call audio_bible_verse_delete(?)}");
			s.setInt("id", id);
			s.executeUpdate();
			conn.close();
		} catch (SQLException ex){}
	}

}
