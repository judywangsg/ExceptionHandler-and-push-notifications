package com.cyn0.exception;

import java.util.ArrayList;

public class StackoverflowItems {
	private String title;
	
	private String link;
	
	private int score;
	
	private String creationDate;
	
	private int answerCount;
	
	private int viewCount;
	
	private Boolean isAnswered;
	
	private ArrayList<String> tags;
	
	public StackoverflowItems(){}
	
	public StackoverflowItems(String title, String link, int score,
			String creationDate, int answerCount, int viewCount,
			Boolean isAnswered, ArrayList<String> tags) {
		super();
		this.title = title;
		this.link = link;
		this.score = score;
		this.creationDate = creationDate;
		this.answerCount = answerCount;
		this.viewCount = viewCount;
		this.isAnswered = isAnswered;
		this.tags = tags;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public Boolean getIsAnswered() {
		return isAnswered;
	}

	public void setIsAnswered(Boolean isAnswered) {
		this.isAnswered = isAnswered;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
}
