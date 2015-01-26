package com.example.exceptionhandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.activities.RegisterActivity;
import com.example.activities.SettingsActitvity;
import com.example.activities.ShowIdActivity;
import com.example.constant.constant;
import com.example.db.DBHelper;
import com.example.utils.utilies;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;

public class MainActivity extends MyListActivity {

    private static final int INITIAL_DELAY_MILLIS = 500;
    private MyExpandableListItemAdapter mExpandableListItemAdapter;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        DBHelper db = new DBHelper(getApplicationContext());
        //db.dropTable();
        //db.createTable();
        
        /*
         * Checking if the device has registerd, if not do registeration
         */
        boolean isRegistered = getSharedPreferences(constant.APP_SETTINGS, MODE_PRIVATE).getBoolean(constant.A_ISREGISTERED, false);
        
        if (!isRegistered ){
        	firstTimeJobs();
        }
        else{
        	/*
        	 * Add something here. What if there is no internet connectivity and not registered? 
        	 * Redirect to some other activity
        	 */        	
        }
        
        setNavigationDrawer();
        mExpandableListItemAdapter = new MyExpandableListItemAdapter(this);

        
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mExpandableListItemAdapter);
        alphaInAnimationAdapter.setAbsListView(getListView());

        assert alphaInAnimationAdapter.getViewAnimator() != null;
        alphaInAnimationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);

        getListView().setAdapter(alphaInAnimationAdapter);
        
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        /*switch (item.getItemId()) {
            case R.id.menu_expandable_limit:
                mLimited = !mLimited;
                item.setChecked(mLimited);
                mExpandableListItemAdapter.setLimit(mLimited ? 2 : 0);
                return true;
        }*/
    	if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
    	
        return super.onOptionsItemSelected(item);
    }
    
    private void firstTimeJobs(){
    	/*
    	 * Setting get notification default value as false
    	 */
    	getSharedPreferences(constant.APP_SETTINGS, MODE_PRIVATE)
			.edit()
			.putBoolean(constant.getNotificationError, true)
			.putBoolean(constant.getNotificationInfo, true)
			.putBoolean(constant.getNotificationWarning, true)
			.commit();
    	
    	if(utilies.isConnectingToInternet(this)){
    		Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
    		startActivity(i);
    		finish();
    	}else{
    		Toast.makeText(getApplicationContext(), "Not registered : Unable to connect to internet. You will receive msgs only after registering.", Toast.LENGTH_LONG).show();
    	}
    }
    
	private void setNavigationDrawer(){
    	mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.list_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  // host Activity 
                mDrawerLayout,         // DrawerLayout object 
                R.drawable.ic_drawer,  // nav drawer image to replace 'Up' caret 
                R.string.drawer_open,  // "open drawer" description for accessibility 
                R.string.drawer_close  // "close drawer" description for accessibility 
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setItemChecked(0, true);

    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
    	            "mailto","palaniappan.feb22@gmail.com", null));
    		emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            switch(position){
            	case 1: 
            		startActivity(new Intent(getApplicationContext(), ShowIdActivity.class));
            		break;
            	case 2: 
            		startActivity(new Intent(getApplicationContext(), SettingsActitvity.class));
            		break;
            	case 3:
            		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hey! I would like to buy u cookies!!");
            		startActivity(Intent.createChooser(emailIntent, "Send email..."));
            		break;
            	case 4:
            		//utilies.sendMail("Feedback - Exception handler app", "body", getApplicationContext());
            		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback - Exception handler app");
            		startActivity(Intent.createChooser(emailIntent, "Send email..."));
            		break;
            }
            mDrawerLayout.closeDrawers();
        }
    }
    
    
  //Receiver called when the GCM msg arrives
    public class UpdateListReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			Log.e("mainActivity","GCM msg arrived");
			
			AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mExpandableListItemAdapter);
	        alphaInAnimationAdapter.setAbsListView(getListView());

	        assert alphaInAnimationAdapter.getViewAnimator() != null;
	        alphaInAnimationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);

	        getListView().setAdapter(alphaInAnimationAdapter);
		
		}
    }
}
