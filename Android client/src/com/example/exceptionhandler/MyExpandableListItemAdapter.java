
package com.example.exceptionhandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.constant.constant;
import com.example.db.DBHelper;
import com.example.utils.utilies;
import com.nhaarman.listviewanimations.itemmanipulation.expandablelistitem.ExpandableListItemAdapter;

public class MyExpandableListItemAdapter extends ExpandableListItemAdapter<Item> {

    private final Context mContext;
    
    ArrayList<Item> feeds;
    DBHelper db;
    
    Item curItem;
    /**
     * Creates a new ExpandableListItemAdapter with the specified list, or an empty list if
     * items == null.
     */
    public MyExpandableListItemAdapter(final Context context) {
        super(context, R.layout.activity_expandablelistitem_card, R.id.activity_expandablelistitem_card_title, R.id.activity_expandablelistitem_card_content);
        mContext = context;
        
        db = new DBHelper(context);
        Log.i("adaptr", "get all feeds");
        feeds = db.getAllFeeds();
        
        //If there are no feeds to display, adding a feed
        if(feeds.size() == 0){
        	Date today = Calendar.getInstance().getTime();
       		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a"); // 3-letter month name & 2-char day of month
       		String curTime = formatter.format(today);
        	
       		feeds.add(
        			new Item("9999", "No items to display", "This is the content message", constant.getNotificationInfo, curTime)
        			);
        }
        
        for(Item feed : feeds){
        	add(feed);
        }
        
        
    }

    @Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@NonNull
    @Override
    public View getTitleView(final int position, final View convertView, @NonNull final ViewGroup parent) {
    	curItem = feeds.get(position);
    	final String title = curItem.getTitle();
    	final String time = utilies.getFormattedDate(curItem.getTimeInString());
    	final String id = curItem.getId();
    	final String msg  = curItem.getMsg();
    	
    	LayoutInflater factory = LayoutInflater.from(mContext);
    	View myView = factory.inflate(R.layout.title_layout, null);
    	
    	TextView titleTextView = (TextView) myView.findViewById(R.id.title);
    	titleTextView.setText(title);
    	
    	TextView timeTextView = (TextView) myView.findViewById(R.id.time);
    	timeTextView.setText(time);
    	
    	ImageView options = (ImageView)myView.findViewById(R.id.options);
    	
    	options.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				AlertDialog.Builder builderSingle = new AlertDialog.Builder(mContext);
		        builderSingle.setTitle("Options");
		        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext,
		                android.R.layout.select_dialog_item);
		        
		        		arrayAdapter.add("Delete");
		        		arrayAdapter.add("Share");
		        builderSingle.setNegativeButton("cancel",
		                new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int which) {
		                        dialog.dismiss();
		                    }
		                });

		        builderSingle.setAdapter(arrayAdapter,
		                new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int which) {
		                        switch (which) {
								case 0:
									feeds.remove(position);
									notifyDataSetChanged();
									db.deleteFeed(id);
									Toast.makeText(mContext, "Deleted" , Toast.LENGTH_LONG).show();
									break;
								case 1:
									String shareText = "Title : " + title +
													"\nTime : " + curItem.getTimeInString() + 
													"\nMessage : " + msg;
									utilies.shareWith(shareText, mContext);
									break;
								default:
									break;
								}
		                        
		                    }
		                });
		        builderSingle.show();
   
			}
		});

    	
    	//timeTextView.setText("2015-23-4 34:23:45");
    	/*TextView tv = (TextView) convertView;
        if (tv == null) {
            tv = new TextView(mContext);
        }
        String title = feeds.get(position).getTitle();
        String time = utilies.getFormattedDate(feeds.get(position).getTimeInString());
        tv.setText(Html.fromHtml("<b>"+ title +"</b>" + "\t"+ time));
        return tv;
        */
    	return myView;
    }

    @NonNull
    @Override
    public View getContentView(final int position, final View convertView, @NonNull final ViewGroup parent) {
    	/*TextView tv = (TextView) convertView;
        if (tv == null) {
            tv = new TextView(mContext);
        }
        String msg = feeds.get(position).getMsg();
        tv.setText(msg);
        return tv;
    	 */
    	
    	LayoutInflater factory = LayoutInflater.from(mContext);
    	View myView = factory.inflate(R.layout.content_layout, null);
    	TextView msgTextView = (TextView) myView.findViewById(R.id.content);
    	String msg = feeds.get(position).getMsg();
    	msgTextView.setText(msg);
    	return myView;
    }


    @Override
    public int getCount() {
     return feeds.size();
    }

    @Override
    public Item getItem(int position) {
     return feeds.get(position);
    }

    @Override
    public long getItemId(int position) {
     return feeds.indexOf(getItem(position));
    }
    
    
    
}