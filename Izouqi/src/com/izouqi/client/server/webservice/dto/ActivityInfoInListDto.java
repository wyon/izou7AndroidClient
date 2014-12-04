package com.izouqi.client.server.webservice.dto;

import java.util.List;

public class ActivityInfoInListDto {
	private int id;
	private String user;
	private String name;
	private String place;
	private long startTime;
	private long endTime;
	private float distance;
	private List<String> persons;
	private List<String> weMedia;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public List<String> getPersons() {
		return persons;
	}

	public void setPersons(List<String> persons) {
		this.persons = persons;
	}

	public List<String> getWeMedia() {
		return weMedia;
	}

	public void setWeMedia(List<String> weMedia) {
		this.weMedia = weMedia;
	}
}
