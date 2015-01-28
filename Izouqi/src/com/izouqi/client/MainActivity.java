package com.izouqi.client;

import java.io.File;

import org.apache.cordova.DroidGap;

import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends DroidGap {

	private Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// super.loadUrl("file:///android_asset/www/index.html");
		// super.loadUrl(String.format("file://%s/www/index.html",
		// getFilesDir()));

		final String path = "file:///" + getFilesDir() + File.separator
				+ "www/index.html";

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				loadUrl(path);
			}
		}, 2000);
		
		

		// final Runnable r = new Runnable() {
		//
		// @Override
		// public void run() {
		// try {
		// FileUtils.copyAssetDirToFiles(MainActivity.this, "www");
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		//
		// String path = "file:///" + getFilesDir() + File.separator +
		// "www/index.html";
		//
		// MainActivity.super.loadUrl(path);
		//
		// File file = new File(getFilesDir(), "www/index.html");
		// boolean e = file.exists();
		// System.out.println(e);
		//
		// Uri uri = Uri.parse("file:///www/index.html");
		// try {
		// ParcelFileDescriptor d = getContentResolver()
		// .openFileDescriptor(uri, "r");
		// System.out.println(d.getStatSize());
		// } catch (FileNotFoundException ex) {
		// ex.printStackTrace();
		// }
		//
		// uri = Uri.fromFile(file);
		// try {
		// ParcelFileDescriptor d = getContentResolver()
		// .openFileDescriptor(uri, "r");
		// System.out.println(d.getStatSize());
		// } catch (FileNotFoundException ex) {
		// ex.printStackTrace();
		// }
		//
		// }
		// };
		//
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// try {
		// Thread.sleep(3000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// runOnUiThread(r);
		// }
		// }).start();
	}
}