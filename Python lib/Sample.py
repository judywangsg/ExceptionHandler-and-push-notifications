import sys
from ExceptionHandler import myExceptionHandler


if __name__ == '__main__':
    sys.excepthook = myExceptionHandler
    print 4/0