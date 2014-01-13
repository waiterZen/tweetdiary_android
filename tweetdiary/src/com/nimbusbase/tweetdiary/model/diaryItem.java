package com.nimbusbase.tweetdiary.model;

public class diaryItem {
	private  String  id;
	private  String  text; 
	
	private  String  create_time;
	private  String  tags;
	private  String  gid;
	private  String  synced;
	private String  time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getSynced() {
		return synced;
	}
	public void setSynced(String synced) {
		this.synced = synced;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
	
}
