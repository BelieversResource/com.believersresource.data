package com.believersresource.data.bbCode;

import java.util.ArrayList;

public class HtmlPartSets extends ArrayList<HtmlPartSet> {

	public void add(HtmlPart openPart, HtmlPart closePart)
    {
        HtmlPartSet set = new HtmlPartSet();
        set.OpenPart = openPart;
        set.ClosePart = closePart;
        this.add(set);
    }

    public static HtmlPartSets getHtmlPartSets(HtmlParts parts)
    {
        HtmlPartSets result = new HtmlPartSets();
        int i = 0;
        for (HtmlPart part : parts)
        {
            i++;
            if (part.IsTag && !part.IsCloseTag)
            {
                if (part.Part.startsWith("</") || part.Part.endsWith("/>"))
                {
                    result.add(part, null);
                }
                else
                {
                    HtmlPart closePart = parts.findClosePart(i, part.Indent);
                    result.add(part, closePart);
                }
            }
        }
        return result;
    }
    
    
}
