
package com.example.exceptionhandler;


import com.example.activities.SettingsActitvity;
import com.example.activities.ShowIdActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BaseActivity extends Activity {

	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    
    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/
        super.onCreate(savedInstanceState);
        //assert getActionBar() != null;
        //.getActionBar().setDisplayHomeAsUpEnabled(true);
        
        //setNavigationDrawer();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void setNavigationDrawer(){
    	mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.list_drawer_items_array);
        
        View drawerView = getLayoutInflater().inflate(R.layout.activity_mylist, null);
        mDrawerLayout = (DrawerLayout) drawerView.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) drawerView.findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);
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
            switch(position){
            	case 1: 
            		startActivity(new Intent(getApplicationContext(), ShowIdActivity.class));
            		break;
            	case 2: 
            		startActivity(new Intent(getApplicationContext(), SettingsActitvity.class));
            		break;
            	case 4:
            		//utilies.sendMail("Feedback - Exception handler app", "body", getApplicationContext());
            		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            	            "mailto","palaniappan.feb22@gmail.com", null));
            		emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback - Exception handler app");
            		startActivity(Intent.createChooser(emailIntent, "Send email..."));
            		break;
            }
            mDrawerLayout.closeDrawers();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_expandablelistitem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
}
