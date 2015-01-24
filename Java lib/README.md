# ExceptionHandler
A java library to search stackoverflow when a unhandled exception occurs in the program.

The results (stackoverflow search results with links to them) will be printed to the passed PrintStream.

#Usage
Add the following code to your program.

		//printStream to output results, logs etc
		  ExceptionHandler handler = new ExceptionHandler(System.out);
		  handler.showStackOverFlowQuestions(true);
	 	  handler.openGoogleSearch(false);
		  Thread.setDefaultUncaughtExceptionHandler(handler);
	
