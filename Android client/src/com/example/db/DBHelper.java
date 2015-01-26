package com.example.db;

import java.util.ArrayList;

import com.example.exceptionhandler.Item;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "MyDB.db";
   public static final String TABLE_NAME = "MyTable";
   
   //unique ID that identifies the feed
   public static final String COLUMN_ID = "ID";
   
   public static final String COLUMN_TILE = "title";

   public static final String COLUMN_MSG = "content";
  
   //Whether the feed has been read by the user
   //values---> YES/NO
   public static final String COLUMN_READ = "read";
   
   
   //timestamp of received data
   public static final String COLUMN_TIME = "time";

   //try to add site 
   public static final String COLUMN_MSGTYPE = "msgtype";
   
   public DBHelper(Context context){
      super(context, DATABASE_NAME , null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      db.execSQL(
      "create table if not exists MyTable " +
      "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + //1
      "title text," + 
      "content text," + 
      "msgtype text," + 
      "read text," +
      "time text" +
      ")"
      );
   }

   public void createTable() {
	   	  SQLiteDatabase db = this.getWritableDatabase();
	      db.execSQL(
	      "create table if not exists MyTable " +
	      "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + //1
	      "title text," + //2
	      "content text," + //3
	      "msgtype text," + //4
	      "read text," + //6
	      "time text" + //7
	      ")"
	      );
   }
   
   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // TODO Auto-generated method stub
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
      onCreate(db);
   }

   
   public boolean insertFeed( String title, String msg,String msgtype, String time){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      
      contentValues.put(COLUMN_TILE, title);
      contentValues.put(COLUMN_MSG, msg);
      contentValues.put(COLUMN_READ, "NO");
      contentValues.put(COLUMN_MSGTYPE, msgtype);
      contentValues.put(COLUMN_TIME, time);
      
      db.insert(TABLE_NAME, null, contentValues);
      Log.d("db", "inserted values");
      db.close();
      return true;
   }
   
   
   public Boolean markAsRead(String id){
		  SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	      Log.d("DB helper- MArk as read", id);
	      contentValues.put(COLUMN_READ, "YES");
	      db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ? ", new String[] {id});
	      db.close();
	      return true;
		   
   }
   
   
   //Deletes the particular feed by passing the link
   public Integer deleteFeed (String id){
      SQLiteDatabase db = this.getWritableDatabase();
       db.delete(TABLE_NAME, 
      COLUMN_ID +" = ? ", 
      new String[] { id });
       db.close();
       return null;
   }
   
   
   public Integer deleteFeedBetweenDates(String start, String end){
	   
	   SQLiteDatabase db = this.getWritableDatabase();
	   //Cursor res = db.query(TABLE_NAME, null, COLUMN_TIME + " BETWEEN ? AND ?", new String[] {
         //      start, end }, null, null, null, null);
	   
	    db.delete(TABLE_NAME, COLUMN_TIME + " <= ?", new String[] {
		                end });
	    /*Cursor res = db.query(TABLE_NAME,null, COLUMN_TIME + " <= ?", new String[] {
                end }, null, null, null, null);
	   res.moveToFirst();
	      while(res.isAfterLast() == false){
	    	  
	    	  Log.d("Deleting", res.getString(res.getColumnIndex(COLUMN_TIME)) + "  " + res.getString(res.getColumnIndex(COLUMN_TILE))); 
	    	  
	    	  res.moveToNext();
	      }*/
	    db.close();
	   return 0;
   
   }
   
   //return all the feed in the table
   public ArrayList<Item> getAllFeeds(){
      ArrayList<Item> array_list = new ArrayList<Item>();
      
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_TIME + " DESC", null );
      //Cursor res =  db.query(TABLE_NAME, null, null, null, null, null, COLUMN_TIME);//rawQuery("SELECT * FROM " + TABLE_NAME + "WHERE "+ COLUMN_RATING_UPLOADED +" != 'DEL'", null );
      Log.i("db", "getting feeds sdf");
      //if(res.getCount() <= 0)
    	//  return array_list;
      res.moveToFirst();
      
      Item feed;
      Log.i("db", "getting feeds");
      while(res.isAfterLast() == false){
    	  Log.i("db", res.getString(res.getColumnIndex(COLUMN_ID)));
    	  feed = new Item(res.getString(res.getColumnIndex(COLUMN_ID)),
    			  res.getString(res.getColumnIndex(COLUMN_TILE)),
    			  res.getString(res.getColumnIndex(COLUMN_MSG)),
    			  res.getString(res.getColumnIndex(COLUMN_MSGTYPE)),
    			  res.getString(res.getColumnIndex(COLUMN_TIME))
    			  );
    	  
    	  //Log.d("feeds", feed.toString());
    	  
    	  array_list.add(feed);
    	  res.moveToNext();
      }
      db.close();
      return array_list;
   }
   
   
   public void dropTable(){
	   SQLiteDatabase db = this.getWritableDatabase();
	   db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	   db.close();
   }
  
   
}