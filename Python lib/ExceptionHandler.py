import traceback
import requests
import httplib, urllib
from time import strftime
reg_id = "APA91bFQlavXTnIFW9TRB0RNszYfkFFg-D4ePT6T0dV4nJfWHwubGP5K4oGPZnK8hMkBRQx15VK0QTlPONylKaS3Vdp0Jhf0B0SP0iomf0Ty8EjAHMbkfa03-3XjhmM3irzrTsBOd1iT20AK2JAwGgONPC9VWcrytuKsihlqJMN0Y5TXgbZ1m2E"

cloudMsgServer = "http://54.149.149.26:5000/sendMsg"

def myExceptionHandler(type, value, tback):
	print "*" * 50

	print str(type)
	print str(value)
	print str(tback)
	print ''.join(traceback.format_tb(tback))
	print "*" * 50
	#sendCloudMessage(str(value), str(type) + ''.join(traceback.format_tb(tback)), "ERROR")
	getStackoverflowQuestion(str(type))

def getStackoverflowQuestion(query):
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
	
def sendCloudMessage(title, msg, type):
	
	data = {
		'title':title,
		'time': strftime("%Y-%m-%d %H:%M:%S"),
		'msg' : msg, 
		'type' : type, 
		'id' : reg_id
		}

	r = requests.post(cloudMsgServer , data=data, allow_redirects=True)
	print r.content
	