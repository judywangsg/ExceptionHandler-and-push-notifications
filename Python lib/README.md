# ExceptionHandler - Python
This is py lib Exception handler.

#Features
- Send notification to Android phone (Requires installing Android)
- Search stackoverflow for error occured

#Usage
See Sample.py for usage

To catch all uncaught exceptions and automatically send notification
    handler = Handler()
    sys.excepthook = handler.myExceptionHandler

To send notification to your phone.
    Handler.sendCloudMessage("Someone pays for WinRAR!!", "Hooo Yaaaaayyyy!!!!", Handler.TAG_INFO)

To stop receiving notifications
    handler.sendNotificationOnException = False
