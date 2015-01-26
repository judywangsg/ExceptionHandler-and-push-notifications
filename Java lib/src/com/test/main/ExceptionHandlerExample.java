package com.test.main;

import com.cyn0.exception.CloudMessage;
import com.cyn0.exception.ExceptionHandler;


public class ExceptionHandlerExample {
	
	public static void main(String[] args) throws Exception {
	  
		ExceptionHandler handler = new ExceptionHandler(System.out);
		
		handler.showStackOverFlowQuestions(true);
		handler.openGoogleSearch(false);
		handler.sendCloudNotificationOnException(true);
		
		Thread.setDefaultUncaughtExceptionHandler(handler);
	
		//handler.showStackOverFlowQuestions(false);
		
		// CloudID is obtained from the android app.
		String cloudID = "";
		handler.setCloudID(cloudID);
		
		//Causing Run-time exception,
		//Array index out of bound exception
		int[] arr = new int[5];
		arr[500] = 6;
		
		/*
		 * When a situation occurs where you want to be notified by sending msg to your phone.
		 */
	
		Boolean whenDiCaprioWinsOscar = true;
		if(whenDiCaprioWinsOscar)
			handler.sendCloudMessage("Di Caprio wins Oscar!!", "Finally he made it :)", CloudMessage.TAG_INFO);
	}
}
