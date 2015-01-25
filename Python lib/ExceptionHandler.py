import traceback
import requests
import httplib, urllib
from time import strftime
import json


class Handler(object):
	"""
	This class handles all the uncaught exceptions in the main program. Also provides means to send notification to your Android phone.

	"""

	reg_id = ""

	TAG_ERROR = "ERROR"
	TAG_INFO = "INFO"
	TAG_WARNING = "WARNING"


	cloudMsgServer = "http://54.149.149.26:5000/sendMsg"

	def __init__(self):
		self.sendNotificationOnException = True
		self.searchStackoverflowOnException = False
		
	def myExceptionHandler(self, type, value, tback):
		"""This method catches all the uncaught exceptions in the program. """
		print "*" * 50
		print str(type)
		print str(value)
		print str(tback)
		print ''.join(traceback.format_tb(tback))
		print "*" * 50
		
		if(self.sendNotificationOnException):
			Handler.sendCloudMessage(str(value), str(type) + ''.join(traceback.format_tb(tback)), Handler.TAG_ERROR)
		
		if(self.searchStackoverflowOnException):
			getStackoverflowQuestion(str(type))

	def getStackoverflowQuestion(query):
		"""Searches stackoverflow for the given query """
		print query
		requestURL = "https://api.stackexchange.com/2.2/search/advanced"
		data = {
			'key' : 'U4DMV*8nvpm3EOpvf69Rxw((',
			'site' : 'stackoverflow',
			'order' : 'desc',
			'sort' : 'relevance',
			'q' : query
			}
		r = requests.get(requestURL , data=data, allow_redirects=True)
		print r.content
	
	

	@staticmethod
	def sendCloudMessage(title, msg, type):
		""" Send notification to your Android phone. This requires installing app """
		if Handler.reg_id is "":
			print "Please set your cloud id which is obtained from android app to send notification to your phone"
			return False
		else:
			data = {	
				'title':title,
				'time': strftime("%Y-%m-%d %H:%M:%S"),
				'msg' : msg, 
				'type' : type, 
				'id' : Handler.reg_id
				}
	
			r = requests.post(Handler.cloudMsgServer , data=data, allow_redirects=True)
			#print r.content
			response = json.loads(r.content)

			if response["success"] > 0:
				print "Message delivered successfully"
				return True

			else:
				print "Fatal : Unable to send message"
				return False