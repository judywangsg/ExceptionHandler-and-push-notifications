package com.example.activities;

import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.constant.constant;
import com.example.exceptionhandler.MainActivity;
import com.example.exceptionhandler.R;
import com.example.utils.utilies;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class RegisterActivity extends Activity {

	Button btnGCMRegister;
	Button btnAppShare;
	GoogleCloudMessaging gcm;
	Context context;
	String regId;

	public static final String REG_ID = "regId";
	private static final String APP_VERSION = "appVersion";
	private Boolean registerationSuccess = false;
	static final String TAG = "Register Activity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		context = getApplicationContext();
	
		if (!TextUtils.isEmpty(regId)) {
			showNavigationDrawerToast();
			Intent i = new Intent(getApplicationContext(),MainActivity.class);
			startActivity(i);
			finish();
		}
		
		btnGCMRegister = (Button) findViewById(R.id.btnGCMRegister);
		
		btnGCMRegister.setOnClickListener( new View.OnClickListener() {
			
			public void onClick(View v) {
				if (TextUtils.isEmpty(regId)) {
					regId = registerGCM();
					Log.d("RegisterActivity", "GCM RegId: " + regId);
				} else {
					showNavigationDrawerToast();
					Intent i = new Intent(getApplicationContext(),MainActivity.class);
					startActivity(i);
					finish();
				}
				
			}
		});		
}
	
	public void sendMail(){
		setRegistrationId();
		Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "palaniappan.feb22@gmail.com"));
		intent.putExtra(Intent.EXTRA_SUBJECT, "REGISTERATION ID");
		intent.putExtra(Intent.EXTRA_TEXT, regId);
		startActivity(intent);
	}
	
	public String registerGCM() {

		gcm = GoogleCloudMessaging.getInstance(this);
		regId = getRegistrationId(context);
		
		if (TextUtils.isEmpty(regId)) {
			registerInBackground();
			Log.d("RegisterActivity",
					"registerGCM - successfully registered with GCM server - regId: "
							+ regId);
		}
		return regId;
	}

	private String getRegistrationId(Context context) {
		
		String registrationId = getSharedPreferences(constant.APP_SETTINGS, MODE_PRIVATE).getString(constant.A_GCM_ID, "");
		if (registrationId.equals("")) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		int registeredVersion = getSharedPreferences(constant.APP_SETTINGS, MODE_PRIVATE).getInt(APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
//		if (registeredVersion != currentVersion) {
//			Log.i(TAG, "App version changed.");
//			return "";
//		}
		return registrationId;
	}
	
	private void setRegistrationId() {
		if(registerationSuccess){
			Editor editor = getSharedPreferences(constant.APP_SETTINGS, MODE_PRIVATE).edit();
	    	editor.putBoolean(constant.A_ISREGISTERED, true);
	    	editor.putString(constant.A_GCM_ID, regId);
	    	editor.commit();
	    	Log.d("committed",getSharedPreferences(constant.APP_SETTINGS, MODE_PRIVATE).getString(constant.A_GCM_ID, "not committed"));
		}
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			Log.d("RegisterActivity",
					"I never expected this! Going down, going down!" + e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (!TextUtils.isEmpty(regId)) {
			showNavigationDrawerToast();
			Intent i = new Intent(getApplicationContext(),MainActivity.class);
			startActivity(i);
			finish();
		}
	}

	private void registerInBackground() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regId = gcm.register(constant.GOOGLE_PROJECT_ID);
					
					Log.d("RegisterActivity", "registerInBackground - regId: "
							+ regId);
					//msg = "Device registered, registration ID=" + regId;
					msg = "Device registered successfully";
					registerationSuccess = true;
				} catch (IOException ex) {
					msg = "Error !:" + ex.getMessage();
					Log.d("RegisterActivity", "Error: " + msg);
				}
				Log.d("RegisterActivity", "AsyncTask completed: " + msg);
				setRegistrationId();
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				Toast.makeText(getApplicationContext(),
						 msg, Toast.LENGTH_LONG)
						.show();
				if(registerationSuccess)
					utilies.shareWith(regId, RegisterActivity.this);
				else
					Toast.makeText(getApplicationContext(), "Something went wrong. Please try after some time", Toast.LENGTH_LONG).show();
			}

		}.execute(null, null, null);
	}
	
	

	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		if (!TextUtils.isEmpty(regId)) {
			showNavigationDrawerToast();
			Intent i = new Intent(getApplicationContext(),MainActivity.class);
			startActivity(i);
			finish();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!TextUtils.isEmpty(regId)) {
			showNavigationDrawerToast();
			Intent i = new Intent(getApplicationContext(),MainActivity.class);
			startActivity(i);
			finish();
		}
	}

	private void showNavigationDrawerToast(){
		Toast.makeText(getApplicationContext(), "Swipe open left navigation drawer for options", Toast.LENGTH_LONG).show();
	}
	
}
