package com.izouqi.client;

import com.izouqi.client.server.webservice.ServerImpl;

import android.app.Application;

public class IzouqiApplication extends Application {

	private static IzouqiApplication instance;

	public static IzouqiApplication getApplication() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		test();
	}

	private void test() {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);

					ServerImpl.searchActivities();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
}