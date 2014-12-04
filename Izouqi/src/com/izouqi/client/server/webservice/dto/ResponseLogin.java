package com.izouqi.client.server.webservice.dto;

//login response data
public class ResponseLogin extends BaseDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7031576421466676203L;

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}