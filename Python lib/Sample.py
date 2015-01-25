import sys
from ExceptionHandler import Handler
import ExceptionHandler

""" This is a test program to demonstrate how to use Exception Handle module"""

if __name__ == '__main__':
    handler = Handler()
    sys.excepthook = handler.myExceptionHandler
    
    #Registration id obtained from android app goes here..
    Handler.reg_id = "My registration id"
    
    whenSomeonePaysForWinRAR = True
    if(whenSomeonePaysForWinRAR):
	    Handler.sendCloudMessage("Someone pays for WinRAR!!", "Hooo Yaaaaayyyy!!!!", Handler.TAG_INFO)
    
    #causing runtime exception
    print 4/0
    