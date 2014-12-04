package com.izouqi.client.server.webservice;

/**
 * Restful Response
 * 
 * @author wyon
 * @param <D>
 *            body.data
 */
public class ResponseData<D> {
	private int statusCode;
	private String message;
	private ResponseBody<D> body;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponseBody<D> getBody() {
		return body;
	}

	public void setBody(ResponseBody<D> body) {
		this.body = body;
	}

	public D getData(){
		if(this.body != null){
			return this.body.getData();
		}
		return null;
	}
	
	// Response Body
	public static class ResponseBody<D> {
		private D data;

		public D getData() {
			return data;
		}

		public void setData(D data) {
			this.data = data;
		}
	}
}
