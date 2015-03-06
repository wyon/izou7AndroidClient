package com.izouqi.client.server.webservice.dto;

import com.izouqi.client.server.webservice.RequestParam;

public class LogBodyRequest implements RequestParam {
	private String content;
	private String title;
	private int type; // 0为info，1为debug，2为warnning，3为error
	private String time;

	/**
	 * 
	 * @param content
	 * @param title
	 * @param type
	 *            0为info，1为debug，2为warnning，3为error
	 * @param time
	 */
	public LogBodyRequest(String content, String title, int type, String time) {
		this.content = content;
		this.title = title;
		this.type = type;
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
