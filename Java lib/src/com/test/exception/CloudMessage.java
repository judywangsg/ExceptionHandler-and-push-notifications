package com.test.exception;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CloudMessage {

	final public static String TAG_ERROR = "ERROR";
	final public static String TAG_WARNING = "WARNING";
	final public static String TAG_INFO = "INFO";
	
	final private static String ServerAddress = "http://54.149.149.26:5000";
	final private static String URI = "/sendMsg";

	public static void sendCloudMessage(String title, String msg, String type, String cloudId) throws Exception {
		 
		final String url = ServerAddress + URI;
		
		URL obj = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
 
		connection.setRequestMethod("POST");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime= Calendar.getInstance().getTime();
		String time = format.format(currentTime);
		
		String urlParameters = "title=" + title +
							   "&msg=" + msg +
							   "&type=" + type +
							   "&time=" + time +
							   "&id=" + cloudId;
 
		// Send post request
		connection.setDoOutput(true);
		
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = connection.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
 
	}

}
