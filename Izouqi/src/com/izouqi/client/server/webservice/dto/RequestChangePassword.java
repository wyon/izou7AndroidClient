package com.izouqi.client.server.webservice.dto;

import com.izouqi.client.server.webservice.RequestParam;

public class RequestChangePassword implements RequestParam {
	private String oldPassword;
	private String newPassword;

	public RequestChangePassword(String oldPassword, String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}
}
