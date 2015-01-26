package com.cyn0.exception;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
		
	/*
	 * Printstream to print the results, logs etc
	 */
	private static PrintStream printStream;
	
	
	/*
	 * Should default browser should google search with the occured exception
	 */
	Boolean openGoogleSearch = false;
	
	/*
	 * Should search for exception in stackoverflow site
	 */
	Boolean showStackOverFlowQuestions = true;

	
	
	/*
	 * Minimum score(upvotes) of stackoverflow question that should appear in result
	 */
	int minimumScore = 0;
	
	//Title for cloud notification
	private String title = "Error : ";
	
	/*
	 * Should send notification to Android mobile
	 */
	Boolean sendCloudNotification = true;

	/*
	 * CloudID that is obtained from the android app.
	 * This ID is used to get notification to android app...
	 */
	private String cloudID = "";
	
	private String serverURL = "http://54.149.149.26:5000/sendMsg";
	
	
	public ExceptionHandler(PrintStream printstream){
		if(printstream != null)
			printStream = printstream;
		else
			printStream = System.out;
		
	}
	
	
	public void uncaughtException(Thread t, Throwable e) {
		
		try{
		    
			printStream.println("Throwable: " + e.getMessage());
		    printStream.println(e.getClass().getName());
		    e.printStackTrace(printStream);
		    StackTraceElement[] stackTraceElements = e.getStackTrace();
		    StackTraceElement main = stackTraceElements[stackTraceElements.length - 1];
		    String mainClass = main.getClassName ();
		    
		    title += mainClass;

		    String q ="";
		    q = e.getClass().getName() + " " + e.getMessage() + " ";
		    
		    for(StackTraceElement element: stackTraceElements){
		    	q +=element.toString() + " ";
		    }
		    
		    printStream.println("**************************");
		    printStream.println(q);
		    printStream.println("**************************");
		    
		    if(showStackOverFlowQuestions){
			    
					ArrayList<StackoverflowItems> response = StackOverFlow.getStackOverFlowSuggestions(q);
					for(StackoverflowItems item : response){
						printStream.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						if(item.getScore() >= minimumScore){
							printStream.println();
							printStream.println(item.getTitle());
							printStream.println(item.getLink());
							printStream.println();
						}
						printStream.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					}
				
		    }
		    
		    //openGoogleSearch = true;
		    if(openGoogleSearch){
				openGoogleSearch(q);
		    }
		    if(sendCloudNotification){
		    	if(!cloudID.equals("")){
		    		Boolean success = CloudMessage.sendCloudMessage(title, q, CloudMessage.TAG_ERROR,serverURL, cloudID);
		    		if(success)
		    			printStream.println("Message sent successfully");
		    		else
		    			printStream.println("Fatal : Message not delivered");
		    	}
		    	else
		    		printStream.println("Please set cloudID to send notification to your phone");
		    }
	  }catch(Exception excp){
		  printStream.println("OH!! The irony.. Something went wrong in the exception handler :(");
		  excp.printStackTrace(printStream);
	  }
	}
	
	/*
	 * Send notification to the android phone.
	 * 
	 * @param title
	 * @param message 
	 * @param msgTag which is anyone of the values - CloudMessage.TAG_ERROR, CloudMessage.TAG_WARNING or CloudMessage.TAG_INFO 
	 */
			
	public void sendCloudMessage(String title, String message, String msgTag) throws Exception{
		printStream.println("--->" + cloudID);
		if(cloudID.equals("") || cloudID.equals("<Your cloudID goes here>"))
			printStream.println("Please set cloudID to send notification to your phone");
		else{
    		Boolean success = CloudMessage.sendCloudMessage(title, message, msgTag, serverURL, cloudID);
    		if(success)
    			printStream.println("Message sent successfully");
    		else
    			printStream.println("Fatal : Message not delivered");
		}
	}
	
	
  /*
   * method to open browser with google search for the given query
   * @Param Query
   */
	public static void openGoogleSearch(String query){
		String url="";
		try {
			url = "http://www.google.com?q=" + URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	      if(Desktop.isDesktopSupported()){
	          Desktop desktop = Desktop.getDesktop();
	          try {
	              desktop.browse(new URI(url));
	          } catch (IOException | URISyntaxException e) {
	              e.printStackTrace(printStream);
	          }
	      }else{
	          Runtime runtime = Runtime.getRuntime();
	          try {
	              runtime.exec("xdg-open " + url);
	          } catch (IOException e) {
	              e.printStackTrace(printStream);
	          }
	      }
  
	}
	

	public Boolean openGoogleSearch() {
		return openGoogleSearch;
	}


	public void openGoogleSearch(Boolean enable) {
		this.openGoogleSearch = enable;
	}


	public Boolean showStackOverFlowQuestions() {
		return showStackOverFlowQuestions;
	}


	public void showStackOverFlowQuestions(Boolean enable) {
		this.showStackOverFlowQuestions = enable;
	}

	public Boolean sendCloudNotificationOnException() {
		return sendCloudNotification;
	}


	public void sendCloudNotificationOnException(Boolean enable) {
		this.sendCloudNotification = enable;
	}
	
	public String getCloudID() {
		return cloudID;
	}

	public void setCloudID(String cloudID) {
		this.cloudID = cloudID;
	}
	
	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

}
