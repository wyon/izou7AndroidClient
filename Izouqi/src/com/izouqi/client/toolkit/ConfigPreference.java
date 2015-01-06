package com.izouqi.client.toolkit;

import com.izouqi.client.constant.Constant;

import android.content.Context;
import android.content.SharedPreferences;

public final class ConfigPreference {
	private static final String SHARED_PREFERENCE_NAME = "config";

	private ConfigPreference() {
	}

	private static SharedPreferences getPre() {
		return Utils.getApp().getSharedPreferences(SHARED_PREFERENCE_NAME,
				Context.MODE_PRIVATE);
	}

	/**
	 * set first startup
	 * @param value
	 */
	public static void setFirstStartup(boolean value) {
		getPre().edit().putBoolean("first_startup", value).apply();
	}

	/**
	 * is first startup
	 * @param defValue
	 * @return
	 */
	public static boolean isFirstStartup(boolean defValue) {
		return getPre().getBoolean("first_startup", defValue);
	}
	
	/**
	 * 获取当前web版本号
	 * @return
	 */
	public static int getWebVersion(){
		return getPre().getInt("web_html_version", Constant.DEF_WEB_VERSION);
	}
	
	/**
	 * 设置web版本
	 * @param webVersion
	 */
	public static void setWebVersion(int webVersion){
		getPre().edit().putInt("web_html_version", webVersion).apply();
	}
}
