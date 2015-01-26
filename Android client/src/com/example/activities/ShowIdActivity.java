package com.example.activities;

import com.example.constant.constant;
import com.example.exceptionhandler.R;
import com.example.utils.utilies;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ShowIdActivity extends ActionBarActivity {

	EditText RegIdTextView;
	
	Button shareButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_id);
		
		RegIdTextView = (EditText)findViewById(R.id.editText1);
		
		final String regId = getSharedPreferences(constant.APP_SETTINGS, MODE_PRIVATE).getString(constant.A_GCM_ID, "Not Registered");
		RegIdTextView.setText(regId);
		
		shareButton = (Button)findViewById(R.id.shareButton);
		shareButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				utilies.shareWith(regId, ShowIdActivity.this);
			}
		});
	}

	
}
