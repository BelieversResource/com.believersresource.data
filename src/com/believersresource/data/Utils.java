package com.believersresource.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import sun.misc.BASE64Encoder;

public class Utils {
	
	public static Connection getConnection()
	{
		try{
			java.lang.Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
			
		Connection conn = ConnectionPool.getConnection();
		return conn;
	

	}
	
	public static void addParamsToStatement(NamedParameterStatement statement, Hashtable<String, Object> params) throws SQLException
	{
		Enumeration<String> e = params.keys();
		while (e.hasMoreElements())
		{
			String keyName = e.nextElement();
			Object o = params.get(keyName);
			String className = o.getClass().getName();
			
			if (className.equals("java.lang.Integer")) { statement.setInt(keyName, Integer.parseInt(o.toString())); } 
			else { statement.setString(keyName, o.toString()); }
		}
	}
	
	public static void addParamsToStatement(CallableStatement statement, Hashtable<String, Object> params) throws SQLException
	{
		boolean namedParams=true;
		int idx=1;
		if (params.containsKey("1")) namedParams=false;
		
		Enumeration<String> e = params.keys();
		while (e.hasMoreElements())
		{
			String keyName = e.nextElement();
			Object o = params.get(keyName);
			String className = o.getClass().getName();
			
			if (namedParams)
			{
				if (className.equals("java.lang.Integer")) { statement.setInt(keyName, Integer.parseInt(o.toString())); } 
				else { statement.setString(keyName, o.toString()); }
			} else {
				if (className.equals("java.lang.Integer")) { statement.setInt(idx, Integer.parseInt(o.toString())); } 
				else { statement.setString(idx, o.toString()); }
				idx++;
			}
			
		}
	}
	
	public static String joinStrings(String delimiter, ArrayList<String> items) {
	     StringBuilder builder = new StringBuilder();
	     Iterator<String> iter = items.iterator();
	     while (iter.hasNext()) {
	         builder.append(iter.next());
	         if (iter.hasNext()) builder.append(delimiter);
	     }
	     return builder.toString();
	}
	
	public static Hashtable<String,String> getColumnHash(ResultSet rs)
	{
		Hashtable<String,String> ht = new Hashtable<String,String>();
		try{
			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount();
			for (int i = 1; i<columnCount + 1; i++) {
				ht.put(meta.getColumnLabel(i).toLowerCase(), "");
			}
		} catch (SQLException ex) {}
		return ht;
	}
	
	public static int getIntForIP(String ipAddress)
    {
        String[] parts = ipAddress.split(".");

        if (parts.length != 4) return 0;

        int[] numbers = new int[4];
        for (int i = 0; i < 3; i++) numbers[i] = Integer.parseInt(parts[i]);
        return ((numbers[0]-128) * 16777216) + (numbers[1] * 65536) + (numbers[2] * 256) + numbers[3];
    }
	
	public static String getClosestMatch(String word, String[] options, int minScore)
    {
        double bestScore = 0;
        String bestWord = "";

        for (String option : options)
        {
        	ArrayList<String> wordLetters = new ArrayList<String>();
        	ArrayList<String> optionLetters = new ArrayList<String>();
            
            for (char c : word.toCharArray()) wordLetters.add(String.valueOf(c));
            for (char c : option.toCharArray()) optionLetters.add(String.valueOf(c));
            
            int matches = 0;
            int nonMatches = 0;

            for (String wordLetter : wordLetters)
            {
                if (optionLetters.contains(wordLetter))
                {
                    optionLetters.remove(wordLetter);
                    matches++;
                }
                else
                {
                    nonMatches++;
                }
            }
            for (String optionLetter : optionLetters) nonMatches++;

            double score = (double)matches / (double)nonMatches;
            if (score > minScore && score > bestScore)
            {
                bestScore = score;
                bestWord = option;
            }
        }
        return bestWord;
    }
	
	public static String encrypt(String toEncrypt)
    {
        String myEncryptionKey = "YourEncryptionKeyGoesHere12";
        String result = "";
		try {
	        String myEncryptionScheme = "DESede";
	        byte[] keyAsBytes = myEncryptionKey.getBytes("UTF8");
	        KeySpec myKeySpec = new DESedeKeySpec(keyAsBytes);
	        SecretKeyFactory mySecretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
	        Cipher cipher = Cipher.getInstance(myEncryptionScheme);
	        SecretKey key = mySecretKeyFactory.generateSecret(myKeySpec);
	        
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = toEncrypt.getBytes("UTF8");
			byte[] encryptedText = cipher.doFinal(plainText);
			BASE64Encoder base64encoder = new BASE64Encoder();
			result = base64encoder.encode(encryptedText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }
	
	public static String getUrlContents(String url)
	{
		String result="";
		try {
			StringBuilder sb=new StringBuilder();
		    URL urlObj = new URL(url);
		    BufferedReader in = new BufferedReader(new InputStreamReader(urlObj.openStream()));
		    String str;
		    while ((str = in.readLine()) != null) {
		        sb.append(str + "\n");
		    }
		    in.close();
		    result = sb.toString();
		} catch (Exception ex) {}
		return result;
	}
	
	public static void sendEmail(String recipient, String subject, String body)
    {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "localhost"); 
		Session session = Session.getDefaultInstance(properties); 
		Message message = new MimeMessage(session);
		try{
			message.setFrom(new InternetAddress("noreply@believersresource.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }

	public static String getUrlName(String name)
    {
        String result = name.toLowerCase().trim();
        result = result.replaceAll("[^A-Za-z0-9 ]", "");
        result = result.replaceAll(" ", "-").replaceAll("---", "-");
        return result;
    }
	
	public static String getTitleCase(String input)
	{
		String result = "";
    	for (int i = 0; i < input.length(); i++){
    		String letter = input.substring(i, i + 1);
    		if (i == 0){
    			result += letter.toUpperCase();
    		} else {
    			String previous = input.substring(i - 1, i);
    			if (previous.equals(" ")) {
    				result += letter.toUpperCase();
    			} else {
    				result += letter.toLowerCase();
    			}
    		}
    	}
    	return result;
    }
	
	public static String removeHtml(String html, boolean keepLineBreaks)
    {
        if (keepLineBreaks) html = html.replace("\n", "`");
        String result = com.believersresource.data.bbCode.Utils.convertHtmlToBBCode(html);
        result= com.believersresource.data.bbCode.Utils.removeBBCode(result);
        if (keepLineBreaks) result = result.replace("`", "<br>");
        return result;
    }
	
}
