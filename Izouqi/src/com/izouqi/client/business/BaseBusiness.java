package com.izouqi.client.business;

import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.net.Uri;

public class BaseBusiness {

	public static class PluginTest extends CordovaPlugin {

		@Override
		public void initialize(CordovaInterface cordova, CordovaWebView webView) {
			// TODO Auto-generated method stub
			super.initialize(cordova, webView);
		}

		@Override
		public boolean execute(String action, String rawArgs,
				CallbackContext callbackContext) throws JSONException {
			// TODO Auto-generated method stub
			return super.execute(action, rawArgs, callbackContext);
		}

		@Override
		public boolean execute(String action, JSONArray args,
				CallbackContext callbackContext) throws JSONException {
			// TODO Auto-generated method stub
			return super.execute(action, args, callbackContext);
		}

		@Override
		public boolean execute(String action, CordovaArgs args,
				CallbackContext callbackContext) throws JSONException {
			// TODO Auto-generated method stub
			return super.execute(action, args, callbackContext);
		}

		@Override
		public void onPause(boolean multitasking) {
			// TODO Auto-generated method stub
			super.onPause(multitasking);
		}

		@Override
		public void onResume(boolean multitasking) {
			// TODO Auto-generated method stub
			super.onResume(multitasking);
		}

		@Override
		public void onNewIntent(Intent intent) {
			// TODO Auto-generated method stub
			super.onNewIntent(intent);
		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
		}

		@Override
		public Object onMessage(String id, Object data) {
			// TODO Auto-generated method stub
			return super.onMessage(id, data);
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent intent) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, intent);
		}

		@Override
		public boolean onOverrideUrlLoading(String url) {
			// TODO Auto-generated method stub
			return super.onOverrideUrlLoading(url);
		}

		@Override
		public Uri remapUri(Uri uri) {
			// TODO Auto-generated method stub
			return super.remapUri(uri);
		}

		@Override
		public void onReset() {
			// TODO Auto-generated method stub
			super.onReset();
		}

	}
}
