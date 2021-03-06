var request = require('request');
var serverUrl = 'http://54.149.149.26:5000/sendMsg';

var sendPushNotification = function(title, msg, type, id, callback){
	var date = new Date();
	var curTime = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " +  date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
	console.log('Sending push notification...');
	request.post(
		serverUrl,
		{ form: {
	  
			'title' : title,
			'msg': msg,
			'time': curTime,
			'id' : id,
			'type' : type
		  } 
		},
			function (error, response, body) {
				
				var result = JSON.parse(body);
				if(callback)
					callback(error, response.statusCode, result)
				else if(error){
					console.log("Fatal error: Sending notification failed");
					console.log(error);
				}else if (response.statusCode >= 500) {
					console.log('GCM service is unavailable (500)');
				}else if (response.statusCode === 401) {
					console.log('Unauthorized (401). Check that your API token is correct.');
				}else if(response.statusCode !== 200){
					console.log("Invalid request");
				}else{ // response.statusCode == 200
					if(result['success'] > 0)
						console.log("Notification successfully sent");
					else	
						console.log("Failed to send notification");
				}
				
			}
	);
}

exports.sendPushNotification = sendPushNotification