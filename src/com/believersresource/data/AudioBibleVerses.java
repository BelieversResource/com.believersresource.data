package com.believersresource.data;

import java.util.Hashtable;
import com.believersresource.data.base.AudioBibleVersesBase;

public class AudioBibleVerses extends AudioBibleVersesBase {

	public AudioBibleVerses() { super(); }
	public AudioBibleVerses(String sql) { super(sql); }
	public AudioBibleVerses(String sql, Hashtable<String, Object> params) { super(sql, params); }

}