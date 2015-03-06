package com.izouqi.client.business;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONException;

public class TestPlugin extends CordovaPlugin {
	@Override
	public boolean execute(String action, String rawArgs,
			CallbackContext callbackContext) throws JSONException {

		try {
			if (action.equals("sayHello")) {
				callbackContext.success("Hello World!你好，科尔多瓦！");
				return true;
			}
		} catch (Exception e) {
			callbackContext.error("Oh shit!");
			return false;
		}

		return true;
	}
}
