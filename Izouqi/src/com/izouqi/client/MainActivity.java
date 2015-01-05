package com.izouqi.client;

import org.apache.cordova.DroidGap;

import android.os.Bundle;

public class MainActivity extends DroidGap {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// super.loadUrl("file:///android_asset/www/index.html");
//		 super.loadUrl(String.format("file://%s/www/index.html", getFilesDir()));

		super.loadUrl("file:///www/index.html");
		
//		final Runnable r = new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//					FileUtils.copyAssetDirToFiles(MainActivity.this, "www");
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//
//				File file = new File(getFilesDir(), "www/index.html");
//				boolean e = file.exists();
//				System.out.println(e);
//
//				Uri uri = Uri.parse("file:///www/index.html");
//				try {
//					ParcelFileDescriptor d = getContentResolver()
//							.openFileDescriptor(uri, "r");
//					System.out.println(d.getStatSize());
//				} catch (FileNotFoundException ex) {
//					ex.printStackTrace();
//				}
//				
//				uri = Uri.fromFile(file);
//				try {
//					ParcelFileDescriptor d = getContentResolver()
//							.openFileDescriptor(uri, "r");
//					System.out.println(d.getStatSize());
//				} catch (FileNotFoundException ex) {
//					ex.printStackTrace();
//				}
//				
//			}
//		};
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				runOnUiThread(r);
//			}
//		}).start();
	}
}