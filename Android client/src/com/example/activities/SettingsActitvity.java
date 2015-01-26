package com.example.activities;

import com.example.constant.constant;
import com.example.exceptionhandler.R;
import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class SettingsActitvity extends ActionBarActivity {

	CheckBox errorCheckbox, warningCheckbox, infoCheckbox;
	
	SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		sharedPreferences = getSharedPreferences(constant.APP_SETTINGS, MODE_PRIVATE); 
		errorCheckbox = (CheckBox)findViewById(R.id.errorCheckBox);
		warningCheckbox = (CheckBox)findViewById(R.id.warningCheckBox);
		infoCheckbox = (CheckBox)findViewById(R.id.infoCheckBox3);
		
		errorCheckbox.setChecked(sharedPreferences.getBoolean(constant.getNotificationError, true));
		warningCheckbox.setChecked(sharedPreferences.getBoolean(constant.getNotificationWarning, true));
		infoCheckbox.setChecked(sharedPreferences.getBoolean(constant.getNotificationInfo, true));
		
		errorCheckbox.setOnClickListener(clickListener);
		warningCheckbox.setOnClickListener(clickListener);
		infoCheckbox.setOnClickListener(clickListener);
	}

	OnClickListener clickListener = new OnClickListener() {
		
		public void onClick(View v) {
			if(v.getId() == R.id.errorCheckBox){
				sharedPreferences
				.edit()
				.putBoolean(constant.getNotificationError, ((CheckBox)v).isChecked())
				.commit();
			}else if(v.getId() == R.id.warningCheckBox){
				sharedPreferences
				.edit()
				.putBoolean(constant.getNotificationWarning, ((CheckBox)v).isChecked())
				.commit();
			}if(v.getId() == R.id.infoCheckBox3){
				sharedPreferences
				.edit()
				.putBoolean(constant.getNotificationInfo, ((CheckBox)v).isChecked())
				.commit();
			}
			
			
		}
	};
	
}
