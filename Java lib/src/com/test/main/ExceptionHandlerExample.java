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
	
		handler.showStackOverFlowQuestions(false);
		//Causing Run-time exception,
		//Array index out of bound exception
		//int[] arr = new int[5];
		//arr[500] = 6;
  
		
		/*
		 * When a situation occurs where you want to be notified by sending msg to your phone.
		 * CloudID is obtained from the android app.
		 */
		String cloudID = "APA91bFQlavXTnIFW9TRB0RNszYfkFFg-D4ePT6T0dV4nJfWHwubGP5K4oGPZnK8hMkBRQx15VK0QTlPONylKaS3Vdp0Jhf0B0SP0iomf0Ty8EjAHMbkfa03-3XjhmM3irzrTsBOd1iT20AK2JAwGgONPC9VWcrytuKsihlqJMN0Y5TXgbZ1m2E";
		handler.setCloudID(cloudID);
		
		Boolean whenDiCaprioWinsOscar = true;
		if(whenDiCaprioWinsOscar)
			handler.sendCloudMessage("Di Caprio wins Oscar!!", "Finally he made it :)", CloudMessage.TAG_INFO);
	}
}