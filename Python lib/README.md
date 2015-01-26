# ExceptionHandler - Python
This is python library for Exception handler.

#Features
- Send notification to Android phone (Requires installing Android app)
- Search stackoverflow for error occured

#Usage
Download <a ref="https://github.com/cyn0/ExceptionHandler/releases/download/0.1/ExceptionHandler-v0.1-beta.apk">Android client</a>. 
See Sample.py for usage

To catch all uncaught exceptions and automatically send notification
```       
        handler = Handler()
        sys.excepthook = handler.myExceptionHandler```
```
To send notification to your phone.
```
        whenSomeonePaysForWinRAR = True
        if(whenSomeonePaysForWinRAR):
            Handler.sendCloudMessage("Someone pays for WinRAR!!", "Hooo Yaaaaayyyy!!!!", Handler.TAG_INFO)
```
To stop receiving notifications
```
        handler.sendNotificationOnException = False
```
