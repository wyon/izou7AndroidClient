package com.izouqi.client.server.webservice.dto;

public class ResponseWebUpgrade extends BaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = -474145037567850468L;

	private int version;
	private String url;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}