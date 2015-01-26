package com.example.exceptionhandler;

import java.util.Date;

import com.example.utils.utilies;

public class Item {
	private String title;
	
	private String msg;
	
	private Date timeInDate;

	private String timeInString;
	
	private String id;
	
	private String msgtype;
	
	
	public Item(String id, String title, String msg, String msgtype, String time) {
		super();
		this.title = title;
		this.msg = msg;
		
		Date date = utilies.convertDate(time);
		this.timeInDate = date;
		
		this.timeInString = time;
		this.id = id;
		this.msgtype = msgtype;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTimeInString() {
		return timeInString;
	}

	public void setTimeInString(String timeInString) {
		this.timeInString = timeInString;
	}

	public Date getTime() {
		return timeInDate;
	}

	public void setTime(Date time) {
		this.timeInDate = time;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
