package com.believersresource.data.bbCode;

public class Utils {
	


    public static String convertBBCodeToHtml(String bbCode, boolean doFollow)
    {
        if (bbCode == null) bbCode = "";
        String html = bbCode.replace("\n", "<br/>");
        html = html.replace("[BLOCKQUOTE]", "<blockquote>").replace("[/BLOCKQUOTE]", "</blockquote>");
        html = html.replace("[B]", "<b>").replace("[/B]", "</b>");
        html = html.replace("[I]", "<i>").replace("[/I]", "</i>");
        html = html.replace("[U]", "<u>").replace("[/U]", "</u>");
        html = html.replace("[H2]", "<h2>").replace("[/h2]", "</H2>");
        html = html.replace("[H3]", "<h3>").replace("[/h3]", "</H3>");
        html = html.replace("[TABLE]", "<table width=\"100%\">").replace("[/TABLE]", "</table>");
        html = html.replace("[TR]", "<tr>").replace("[/TR]", "</tr>");
        html = html.replace("[TD]", "<td>").replace("[/TD]", "</td>");
        html = html.replace("[UL]", "<ul>").replace("[/UL]", "</ul>");
        html = html.replace("[DEL]", "<del>").replace("[/DEL]", "</del>");

        html = html.replace("[LI]", "<li>").replace("[/LI]", "</li>");

        html = html.replace("[/URL]", "</a>");
        html = html.replace("[/IMG]", "</img>");


        int i = 0;
        while (html.indexOf("[URL") > -1 && i < 99)
        {
        	
        	
            try
            {
                i++;
                int startIdx = html.indexOf("[URL");
                int endIdx = html.indexOf("\"]", startIdx + 6);

                String url = html.substring(startIdx + 6, endIdx);

                //if internal, follow
                boolean doFollowLink = true;
                if (doFollowLink)
                {
                    html = html.replace(html.substring(startIdx, endIdx + 2), "<a href=\"" + url + "\" >");
                }
                else
                {
                    html = html.replace(html.substring(startIdx, endIdx + 2), "<a href=\"" + url + "\" target=\"_blank\" rel=\"nofollow\" class=\"external\">");
                }
                //                throw new Exception("**" + url + "**");
            }
            catch (Exception ex){ }
        }

        i = 0;
        while (html.indexOf("[IMG") > -1 && i < 99)
        {
            try
            {
                i++;
                int startIdx = html.indexOf("[IMG");
                int endIdx = html.indexOf("]", startIdx);
                String tag = html.substring(startIdx, endIdx - startIdx + 1);
                String src = Parser.getParam(tag, "SRC", tag);
                String alt = Parser.getParam(tag, "ALT", tag);
                String align = Parser.getParam(tag, "ALIGN", tag);
                String width = Parser.getParam(tag, "WIDTH", tag);

                String newTag = "<img src=\"" + src + "\" alt=\"" + alt + "\"";
                if (align != "") newTag += " align=\"" + align + "\"";
                if (width != "") newTag += " width=\"" + width + "\"";
                newTag += ">";

                html = html.replace(tag, newTag);
                //                throw new Exception("**" + url + "**");
            }
            catch (Exception ex) { }
        }

        i = 0;
        while (html.indexOf("[YOUTUBE") > -1 && i < 99)
        {
            i++;
            int startIdx = html.indexOf("[YOUTUBE");
            int endIdx = html.indexOf("]", startIdx);
            String tag = html.substring(startIdx, endIdx - startIdx + 1);
            String video = Parser.getParam(tag, "VIDEO", tag);

            html = html.replace(tag, "<object height=\"350\" width=\"425\"><param name=\"movie\" value=\"http://www.youtube.com/v/" + video + "\"></param><param name=\"wmode\" value=\"transparent\"></param></object><embed src=\"http://www.youtube.com/v/" + video + "\" type=\"application/x-shockwave-flash\" wmode=\"transparent\" height=\"350\" width=\"425\"></embed>");
        }

        return html;
    }
	
    
    public static String convertHtmlToBBCode(String html)
    {
        if (html == null) html = "";
        html = html.replace("</div><div><br></div>", "</div><br>").replace("</p><div><br></div>", "</p><br>");
        StringBuilder sb = new StringBuilder();
        for (HtmlPart part : Parser.convertHtmlToBBCode(html))
        {
            sb.append(part.Part);
        }
        String bbCode = sb.toString();
        bbCode = bbCode.replaceAll("<( )*([^>])*>", "");
        if (bbCode.contains("\n  [UL")) bbCode = bbCode.replace("\n  [UL", "[UL");
        if (bbCode.contains("\n  [BLOCKQUOTE")) bbCode = bbCode.replace("\n  [BLOCKQUOTE", "[BLOCKQUOTE");
        if (bbCode.contains("\n  [TABLE")) bbCode = bbCode.replace("\n  [TABLE", "[TABLE");
        if (bbCode.contains("\n  [H2")) bbCode = bbCode.replace("\n  [H2", "[H2");
        if (bbCode.contains("\n  [H3")) bbCode = bbCode.replace("\n  [H3", "[H3");
        bbCode = bbCode.trim();
        return bbCode;
    }

    public static String removeBBCode(String bbCode)
    {
        if (bbCode == null) bbCode = "";
        return bbCode.replaceAll("\\[[^\\]]*\\]", "");
    }
    
}
