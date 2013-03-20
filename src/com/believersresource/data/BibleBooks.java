package com.believersresource.data;

import java.util.ArrayList;
import java.util.Hashtable;

import com.believersresource.data.base.BibleBooksBase;

public class BibleBooks extends BibleBooksBase {

	public BibleBooks() { super(); }
	public BibleBooks(String sql) { super(sql); }
	public BibleBooks(String sql, Hashtable<String, Object> params) { super(sql, params); }

	public BibleBook getByName(String name)
	{
		for (BibleBook bibleBook : this) { if (bibleBook.getName().toLowerCase().equals(name.toLowerCase())) return bibleBook; }
		return null;
	}
	
	public String[] getNameArray()
    {
        ArrayList<String> result = new ArrayList<String>();
        for (BibleBook book : this)
        {
            result.add(book.getName().toLowerCase());
        }
        return result.toArray(new String[0]);
    }
	
}