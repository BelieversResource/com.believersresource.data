package com.believersresource.data.bbCode;

import java.util.ArrayList;

public class HtmlParts extends ArrayList<HtmlPart> {
	
	public void add(String part, boolean isTag, boolean isCloseTag, int indent)
    {
        HtmlPart p = new HtmlPart();
        p.Indent = indent;
        p.IsTag = isTag;
        p.IsCloseTag = isCloseTag;
        p.Part = part;
        this.add(p);
    }
	
	public HtmlPart findClosePart(int index, int indent)
    {
        for (int i = index + 1; i < this.size(); i++)
        {
            if (this.get(i).IsCloseTag && this.get(i).Indent == indent) return this.get(i);
        }
        return null;
    }
	
}
