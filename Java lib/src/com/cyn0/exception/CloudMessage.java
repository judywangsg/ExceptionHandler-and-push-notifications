package com.cyn0.exception;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/*
 * Wrapper class to send HTTP request to the server url.
 * This send cloud message to the server to be deliver to android phone.
 */
public class CloudMessage {

	final public static String TAG_ERROR = "ERROR";
	final public static String TAG_WARNING = "WARNING";
	final public static String TAG_INFO = "INFO";
	
	/*
	 * Method to send cloud message to Android phone.
	 * @param title
	 * @param msg
	 * @param type : type of the msg, TAG_ERROR, TAG_WARNING, or TAG_INFO
	 * @param serverUrl : url of the server that forwards cloud message
	 * @param CloudId : device id obtained from Android app
	 * 
	 *  @return where the message was successfully delivered
	 */
	public static Boolean sendCloudMessage(String title, String msg, String type,String serverUrl, String cloudId) throws Exception {
		 
		final String url = serverUrl;
		
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
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
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
		//System.out.println(response.toString());
		
		JSONObject res = (JSONObject) JSONValue.parse(response.toString());
		int success = ((Long)res.get("success")).intValue();
		if(success > 0)	return true;
		else return false;
	}

}
