package com.believersresource.data;

import java.sql.ResultSet;
import com.believersresource.data.base.AudioBibleVerseBase;

public class AudioBibleVerse extends AudioBibleVerseBase {

	public AudioBibleVerse() { super(); }
	public AudioBibleVerse(ResultSet rs) { super(rs); }

}