package com.believersresource.data.bbCode;

public class Parser {
	
	public static String getParam(String tag, String param, String html)
    {
        String result = "";
        int start = tag.toLowerCase().indexOf(param.toLowerCase());
        if (start > -1)
        {
            start = start + param.length() + 2;
            int end = tag.indexOf("\"", start);
            result = tag.substring(start, end - start);
            int htmlStart = html.toLowerCase().indexOf(result.toLowerCase());
            result = html.substring(htmlStart, result.length());
        }
        return result;
    }
	
	public static HtmlParts convertHtmlToBBCode(String html)
    {
        html = html.replace("\r", " ");
        html = html.replace("\n", " ");
        HtmlParts parts = convertToHtmlParts(html);
        HtmlPartSets sets = HtmlPartSets.getHtmlPartSets(parts);

        for (HtmlPartSet set : sets)
        {
            if (set.OpenPart.Part.startsWith("<A HREF"))
            {
                String url = set.OpenPart.Part.substring(9, set.OpenPart.Part.length() - 11);
                replaceTags(set, set.OpenPart.Part, "[URL=\"" + url + "\"]", "[/URL]");
            }

            replaceTags(set, "<BR", "\n", "");
            replaceTags(set, "<P", "", "\n\n");
            replaceTags(set, "<DIV", "", "\n");

            replaceTags(set, "<BLOCKQUOTE", "[BLOCKQUOTE]", "[/BLOCKQUOTE]");

            replaceTags(set, "<B", "[B]", "[/B]");
            replaceTags(set, "<STRONG", "[B]", "[/B]");
            replaceTags(set, "FONT-WEIGHT: BOLD", "[B]", "[/B]");

            replaceTags(set, "<I", "[I]", "[/I]");
            replaceTags(set, "<EM", "[I]", "[/I]");
            replaceTags(set, "FONT-STYLE: ITALIC", "[I]", "[/I]");

            replaceTags(set, "<UL", "[UL]", "[/UL]");
            replaceTags(set, "<LI", "[LI]", "[/LI]");

            replaceTags(set, "<U", "[U]", "[/U]");
            replaceTags(set, "TEXT-DECORATION: UNDERLINE", "[U]", "[/U]");

            replaceTags(set, "TEXT-DECORATION: LINE-THROUGH", "[DEL]", "[/DEL]");

            replaceTags(set, "<H2", "[H2]", "[/H2]");
            replaceTags(set, "<H3", "[H3]", "[/H3]");

            replaceTags(set, "<TD", "[TD]", "[/TD]");
            replaceTags(set, "<TR", "[TR]", "[/TR]");
            replaceTags(set, "<TABLE", "[TABLE]", "[/TABLE]");
        }
        return parts;

    }
	
	
	private static void replaceTags(HtmlPartSet set, String match, String openTag, String closeTag)
    {
        if (set.OpenPart.Part.indexOf(match) > -1)
        {
            set.OpenPart.Part = openTag;
            if (set.ClosePart != null) set.ClosePart.Part = closeTag;
        }
    }
	
	

    public static HtmlParts convertToHtmlParts(String input)
    {
        input = input.replace("<br>", "<br />");
        input = input.replace("<BR>", "<br />");

        int indent = 0;
        HtmlParts result = new HtmlParts();
        String[] parts = input.split("<");
        if (parts.length == 0) parts = new String[] { input };

        for (String part : parts)
        {
            String[] split = part.split(">");
            if (split.length < 2)
            {
            	if (part.contains(">")) part = "<" + part;
                result.add(part, false, false, indent);
            }
            else
            {
            	//System.out.println(split[0] + " - " + split[1]);
            	
                if (split[0].startsWith("/")) indent--;
                result.add(standardizeTag("<" + split[0] + ">"), true, split[0].startsWith("/"), indent);
                if (!split[0].startsWith("/") && !split[0].endsWith("/")) indent++;
                result.add(split[1], false, false, indent);
            }
        }
    //    for (HtmlPart part : result)
  //      {
//        	System.out.println(part.Part);
        //}
        return result;
    }
    
    private static String standardizeTag(String input)
    {
        if (input.startsWith("<a") || input.startsWith("<A"))
        {
            int startIdx = input.toUpperCase().indexOf("HREF");
            if (startIdx > -1)
            {
                startIdx += 6;
                int endIdx = input.indexOf("\"", startIdx + 1);
                if (endIdx > startIdx) return "<A HREF=\"" + input.substring(startIdx, endIdx - startIdx) + "\">";
            }
        }
        return input.toUpperCase();
    }
	
	
}
