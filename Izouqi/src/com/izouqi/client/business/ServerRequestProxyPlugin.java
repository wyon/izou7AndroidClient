package com.izouqi.client.business;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.common.Tools;
import com.google.gson.Gson;

public class ServerRequestProxyPlugin extends CordovaPlugin {

	static final int CONNECT_TIMEOUT_MILLIS = 15 * 1000; // 15s
	static final int READ_TIMEOUT_MILLIS = 20 * 1000; // 20s

	static final String UTF8 = "UTF-8";

	private Gson gson;

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		gson = new Gson();
	}

	@Override
	public boolean execute(String action, String rawArgs,
			CallbackContext callbackContext) throws JSONException {
		// BackgroundExecutor.execute(new RequestRunnable(gson.fromJson(rawArgs,
		// Request.class), callbackContext));
		new RequestRunnable(gson.fromJson(rawArgs, Request.class),
				callbackContext).run();

		return true;
	}

	private boolean isNetworkEnable() {
		return Tools.isNetworkConnected(getContext());
	}

	private Context getContext() {
		return this.cordova.getActivity();
		// return Utils.getApp();
	}

	class RequestRunnable implements Runnable {

		private Request request;
		private CallbackContext callbackContext;

		RequestRunnable(Request request, CallbackContext callbackContext) {
			this.request = request;
			this.callbackContext = callbackContext;
		}

		@Override
		public void run() {
			if (!isNetworkEnable()) {
				PluginResult pluginResult = new PluginResult(
						PluginResult.Status.IO_EXCEPTION);
				callbackContext.sendPluginResult(pluginResult);
				return;
			}

			try {
				Response response = execute(request);
				String responseStr = gson.toJson(response);
				callbackContext.success(new JSONObject(responseStr));
			} catch (IOException e) {
				e.printStackTrace();
				PluginResult pluginResult = new PluginResult(
						PluginResult.Status.IO_EXCEPTION);
				callbackContext.sendPluginResult(pluginResult);
			} catch (JSONException e) {
				e.printStackTrace();
				PluginResult pluginResult = new PluginResult(
						PluginResult.Status.JSON_EXCEPTION);
				callbackContext.sendPluginResult(pluginResult);
			}
		}

		private Response execute(Request request) throws IOException {
			HttpURLConnection connection = openConnection(request);
			prepareRequest(connection, request);
			return readResponse(connection);
		}

		private HttpURLConnection openConnection(Request request)
				throws IOException {
			HttpURLConnection connection = (HttpURLConnection) new URL(
					request.getUrl()).openConnection();
			connection.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
			connection.setReadTimeout(READ_TIMEOUT_MILLIS);
			return connection;
		}

		private void prepareRequest(HttpURLConnection connection,
				Request request) throws IOException {
			connection.setRequestMethod(request.getMethod());
			connection.setDoInput(true);

			for (Entry<String, String> header : request.getHeaders()) {
				connection.addRequestProperty(header.getKey(),
						header.getValue());
			}

			String body = request.getBody();
			if (body != null && body.length() > 0) {
				connection.setDoOutput(true);
				byte[] bytes = body.getBytes(UTF8);

				connection.setFixedLengthStreamingMode(bytes.length);
				connection.addRequestProperty("Content-Length",
						String.valueOf(bytes.length));

				connection.getOutputStream().write(bytes);
			}
		}

		private Response readResponse(HttpURLConnection connection)
				throws IOException {
			int status = connection.getResponseCode();
			String reason = connection.getResponseMessage();
			if (reason == null)
				reason = ""; // HttpURLConnection treats empty reason as null.

			List<Entry<String, String>> headers = new ArrayList<Map.Entry<String, String>>();
			for (Entry<String, List<String>> field : connection
					.getHeaderFields().entrySet()) {
				String name = field.getKey();

				for (String value : field.getValue()) {
					headers.add(new SimpleEntry(name, value));
				}
			}

			InputStream stream;
			if (status >= 400) {
				stream = connection.getErrorStream();
			} else {
				stream = connection.getInputStream();
			}

			byte[] reBytes = streamToBytes(stream);

			Response response = new Response();
			response.setUrl(request.getUrl());
			response.setStatus(status);
			response.setMsg(reason);
			response.setHeaders(headers);
			response.setResponseBody(new String(reBytes, UTF8));

			return response;
		}

		byte[] streamToBytes(InputStream stream) throws IOException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (stream != null) {
				byte[] buf = new byte[0x1000];
				int r;
				while ((r = stream.read(buf)) != -1) {
					baos.write(buf, 0, r);
				}
			}
			return baos.toByteArray();
		}
	}

	class Response {
		private String url;
		private int status;
		private String msg;
		private List<Entry<String, String>> headers;
		private String responseBody;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public List<Entry<String, String>> getHeaders() {
			return headers;
		}

		public void setHeaders(List<Entry<String, String>> headers) {
			this.headers = headers;
		}

		public String getResponseBody() {
			return responseBody;
		}

		public void setResponseBody(String responseBody) {
			this.responseBody = responseBody;
		}
	}

	class Request {
		private String method;
		private String url;
		private List<Map.Entry<String, String>> headers;
		private String body;

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public List<Map.Entry<String, String>> getHeaders() {
			return headers;
		}

		public void setHeaders(List<Map.Entry<String, String>> headers) {
			this.headers = headers;
		}
	}
}
