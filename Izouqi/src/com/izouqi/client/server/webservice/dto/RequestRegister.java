package com.izouqi.client.server.webservice.dto;

import com.izouqi.client.server.webservice.RequestParam;

public class RequestRegister implements RequestParam {
	private String username;
	private String password;

	public RequestRegister(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
