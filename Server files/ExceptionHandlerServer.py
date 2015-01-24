from flask import Flask,request
import json
import urllib2
import datetime
from time import strftime

app = Flask(__name__)

#reg_ids = ['APA91bFQlavXTnIFW9TRB0RNszYfkFFg-D4ePT6T0dV4nJfWHwubGP5K4oGPZnK8hMkBRQx15VK0QTlPONylKaS3Vdp0Jhf0B0SP0iomf0Ty8EjAHMbkfa03-3XjhmM3irzrTsBOd1iT20AK2JAwGgONPC9VWcrytuKsihlqJMN0Y5TXgbZ1m2E']

reg_ids = []

@app.route('/sendMsg', methods = ['POST'])
def sendMsg():
	title = request.form['title']
	msg = request.form['msg']
	type = request.form['type']
	time = request.form['time']
	reg_ids[:] = []
	reg_ids.append(request.form['id'])
	sendGCMMsg(title, msg, type, time)
	return "ok"

def sendGCMMsg(title, msg, type, time):
	    timestamp = strftime("%Y-%m-%d %H:%M:%S")
            json_data = {"collapse_key" : "msg", 
                         "data" : { "title": title,
				"type":type,
				"msg":msg,
				"time":time
                                   }, 
                    "registration_ids": reg_ids,
            }
	    url = 'https://android.googleapis.com/gcm/send'
            myKey = "AIzaSyAnDazk65j2QGi-hgRQxTZFxSJX9c1FXd8" 
            data = json.dumps(json_data)
            headers = {'Content-Type': 'application/json', 'Authorization': 'key=%s' % myKey}
            req = urllib2.Request(url, data, headers)
            f = urllib2.urlopen(req)
            response = json.loads(f.read())

	    print response
	    return response 


if __name__ == '__main__':
    app.run(host='0.0.0.0',debug=True)
