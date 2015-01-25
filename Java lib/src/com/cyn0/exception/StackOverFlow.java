package com.cyn0.exception;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
 
import java.net.URLEncoder;



//import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class StackOverFlow {
	public static ArrayList<StackoverflowItems> getStackOverFlowSuggestions(String query) throws Exception {
		String requestURL = "https://api.stackexchange.com/2.2/search/advanced?key=U4DMV*8nvpm3EOpvf69Rxw((&site=stackoverflow&order=desc&sort=relevance&q=" +  URLEncoder.encode(query, "UTF-8").replace("+", "%20");
		//System.out.println(requestURL);

		URL url = new URL(requestURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}
		
		InputStream zin = new GZIPInputStream(conn.getInputStream());
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
			(zin)));
 
		String output,result = "";
		while ((output = br.readLine()) != null) {
			result += output;
		}
		//System.out.println(result);
		JSONObject response = (JSONObject) JSONValue.parse(result);
		JSONArray items = (JSONArray)response.get("items");
		int itemLength = items.size();
		
		ArrayList<StackoverflowItems> stackoverflowResults = new ArrayList<>();
		for(int i =0; i < itemLength; i++){
			JSONObject item = (JSONObject) items.get(i);
			String title = (String) item.get("title");
			String link= (String) item.get("link");
			int score = Integer.parseInt(item.get("score").toString());
			int ans_count = Integer.parseInt(item.get("answer_count").toString());
			int view_count = Integer.parseInt(item.get("view_count").toString());
			Boolean isAnswered = Boolean.parseBoolean(item.get("is_answered").toString());
			String creationDate = item.get("creation_date").toString();
			JSONArray tags = (JSONArray) item.get("tags");
			ArrayList<String> tagsList = new ArrayList<String>();
			int tagLength = tags.size();
			
			for(int j=0; j<tagLength; j++){
				tagsList.add((String) tags.get(j));
			}
			
			
			stackoverflowResults.add(
					new StackoverflowItems(title, link, score, creationDate, ans_count, view_count, isAnswered, tagsList)
					);
		}
		conn.disconnect();
		return stackoverflowResults;
	}
	
	
	
	
}
