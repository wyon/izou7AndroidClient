package com.izouqi.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringWriter;

import android.app.Application;

import com.izouqi.client.common.android.CrashHandler;
import com.izouqi.client.server.webservice.ServerImpl;

public class IzouqiApplication extends Application {

	private static IzouqiApplication instance;

	public static IzouqiApplication getApplication() {
		return instance;
	}

	@Override
	public void onCreate() {
		instance = this;
		super.onCreate();

		CrashHandler.getInstance().init(this);
		reportCrashLogs();
		
		// test();
	}

	private void test() {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);

					ServerImpl.getComingActivites();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	
	private void reportCrashLogs() {/*
		new Thread("reportCrashLogs_thread") {
			public void run() {
				try {
					File[] crashLog = CrashHandler.getCrashFiles();
					if (crashLog != null && crashLog.length > 0) {
						StringWriter sw = null;
						Reader reader = null;
						char[] buffer = new char[1024];
						int len;

						for (File file : crashLog) {
							try {
								// reader to string
								sw = new StringWriter();
								reader = new BufferedReader(
										new FileReader(file));

								while ((len = reader.read(buffer)) > 0) {
									sw.write(buffer, 0, len);
								}

								reader.close();
								reader = null;

								// send
								String filename = file.getName();
								boolean res = DoctorServer.reportLog(filename,
										sw.toString());

								// delete
								if (res) {
									try {
										file.delete();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								if (reader != null) {
									try {
										reader.close();
									} catch (Exception e2) {
										e2.printStackTrace();
									}
								}
								reader = null;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	*/}
}