package com.izouqi.client.server.webservice.dto;

import com.izouqi.client.server.webservice.RequestParam;

public class RequestLogin implements RequestParam {
	private String username;
	private String password;
	private byte source; // source ������ʾ�Ǵӱ�ϵͳ����qq��¼

	public RequestLogin(String username, String password) {
		this.username = username;
		this.password = password;
		source = 0;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public byte getSource() {
		return source;
	}
}
