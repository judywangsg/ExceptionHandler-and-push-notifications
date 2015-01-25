# ExceptionHandler
A java library for Exception Handler

#Features
- Send notification to Android phone (Requires installing Android app)
- Search stackoverflow for error occured

#Usage
Go through ExceptionHandlerExample for usage.
The results(stackoverflow search results with links to them), logs will be printed to the passed PrintStream.

```
	ExceptionHandler handler = new ExceptionHandler(System.out);
	handler.showStackOverFlowQuestions(true);
	handler.openGoogleSearch(false);
	handler.sendCloudNotificationOnException(true);
	handler.showStackOverFlowQuestions(false);	

	Thread.setDefaultUncaughtExceptionHandler(handler);
	
```
Send Notification to phone.
```
	String cloudID = "your id obtained from androidapp goes here";
	handler.setCloudID(cloudID);
		
	Boolean whenDiCaprioWinsOscar = true;
	if(whenDiCaprioWinsOscar)
		handler.sendCloudMessage("Di Caprio wins Oscar!!", "Finally he made it :)", CloudMessage.TAG_INFO);
```



