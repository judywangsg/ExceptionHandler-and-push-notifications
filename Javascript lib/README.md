# Exception Handler and push notifications
A Javascript library for Exception Handler

Currently supports only sending push notification to phone. Exception handling not supported.

#Features
- Send notification to Android phone (Requires installing Android app)

#Usage
Download the <a href="https://github.com/cyn0/ExceptionHandler/releases/download/0.1/ExceptionHandler.apk">Android client</a>

Send Notification to phone.
```
	var pushnotification = require('./PushNotification');	
	pushnotification.sendPushNotification('This is the title', 'this is the msg', pushnotification.TAG_ERROR, id);
```

