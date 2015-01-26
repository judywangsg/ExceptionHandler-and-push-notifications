package com.example.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.ArrayAdapter;

import com.example.exceptionhandler.MainActivity;
import com.example.exceptionhandler.R;

public class utilies {

	public static void sendNotification(String title, String msg, Context context,int notifId) {
		
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				new Intent(context, MainActivity.class), 0);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.launcher)
				.setContentTitle(title)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg)
				.setSound(soundUri);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(notifId, mBuilder.build());
	}
	
	public static boolean isConnectingToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null) 
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null) 
                  for (int i = 0; i < info.length; i++) 
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
	
	public static void report(String subject, String content, Context context){
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_EMAIL, new String[]{"palaniappan.feb22@gmail.com"});          
		email.putExtra(Intent.EXTRA_SUBJECT, subject);
		email.putExtra(Intent.EXTRA_TEXT, content);
		email.setType("message/rfc822");
		context.startActivity(Intent.createChooser(email, "Choose an Email client :"));
	}
	
	public static Date convertDate(String date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date result = null;
		try {
			result = format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static String convertDate(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = null;
		try {
			result = format.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

public static String getFormattedDate(String date){
	try{
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date msgDate = format.parse(date);
	Calendar cal1 = Calendar.getInstance();
	Calendar cal2 = Calendar.getInstance();
	Date today = Calendar.getInstance().getTime();
	cal1.setTime(today);
	cal2.setTime(msgDate);
	boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
	                  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
	
	if(sameDay){
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a"); // 3-letter month name & 2-char day of month
		return formatter.format(cal2.getTime());
	}else{
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM"); // 3-letter month name & 2-char day of month
		return formatter.format(cal2.getTime());
	}
	}catch(Exception e){
		e.printStackTrace();
	}
	return date;
 }


public static void shareWith(final String shareText, final Context context) {
	AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
    builderSingle.setTitle("Share using");
    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
            context,
            android.R.layout.select_dialog_item);
    PackageManager pm = context.getPackageManager();
    final Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
    shareIntent.setType("text/plain");

	   List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
	     for (final ResolveInfo app : activityList) 
	     {
	         if ((app.activityInfo.name).contains("whatsapp")) 
	           arrayAdapter.add("WhatsApp");
	         else if ("com.twitter.android.PostActivity".equals(app.activityInfo.name))
	        	arrayAdapter.add("Twitter");
	         else if ((app.activityInfo.name).contains("plus"))
	        	arrayAdapter.add("Google Plus");
	         else if ((app.activityInfo.name).contains("facebook"))
	        	arrayAdapter.add("Facebook");
	        else if ((app.activityInfo.name).contains("gm"))
	        	arrayAdapter.add("Gmail");
	        else if ((app.activityInfo.name).contains("org.telegram.messenger"))
	        	arrayAdapter.add("Telegram");
	         
	     }
    builderSingle.setNegativeButton("cancel",
            new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

    builderSingle.setAdapter(arrayAdapter,
            new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    String strName = arrayAdapter.getItem(which);
                    shareIntent .putExtra(Intent.EXTRA_TEXT, shareText);
                    if(strName.equals("WhatsApp")){
                    	shareIntent.setPackage("com.whatsapp");
                    }else if(strName.equals("Twitter")){
                    	shareIntent.setPackage("com.twitter.android");
                    }else if(strName.equals("Google Plus")){
                    	shareIntent.setPackage("com.google.android.apps.plus");
                    }else if(strName.equals("Facebook")){
                    	shareIntent.setPackage("com.facebook.katana");
                    }else if(strName.equals("Gmail")){
                    	shareIntent.setPackage("com.google.android.gm");
                    }else if(strName.equals("Telegram")){
                    	shareIntent.setPackage("org.telegram.messenger");
                    }
                    
                    context.startActivity(Intent.createChooser(shareIntent  , "Sharing using.."));
                }
            });
    builderSingle.show();

	}


}