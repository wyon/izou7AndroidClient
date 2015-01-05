package com.izouqi.client;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.common.BackgroundExecutor;
import com.android.common.FileUtils;
import com.izouqi.client.constant.Constant;
import com.izouqi.client.toolkit.ConfigPreference;

/**
 * welcome page 更新web页面，向服务器查询最新的
 * 
 * @author wyon
 */
public class StartupActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);

		checkLocalUpdate();
	}

	// 首次启动时或files/www不存在，copy asset 到 files/www下；
	// files/www_new存在时，copy new 到 files/www下，并删除new
	private void checkLocalUpdate() {
		Runnable run = null;

		final File webRootFile = new File(getFilesDir(), Constant.WEB_ROOT_DIR);
		if (ConfigPreference.isFirstStartup(true) || !webRootFile.exists()) {
			// 首次启动
			run = new Runnable() {

				@Override
				public void run() {
					if (webRootFile.exists()) {
						File[] childFiles = webRootFile.listFiles();
						if (childFiles != null && childFiles.length > 0) {
							for (File tmp : childFiles) {
								FileUtils.deleteFileRecur(tmp);
							}
						}
					}

					// copy asset www to files
					try {
						FileUtils.copyAssetDirToFiles(StartupActivity.this,
								Constant.WEB_ROOT_DIR);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					runOnUiThread(new Runnable() {
						public void run() {
							startMainActivity();	
						}
					});
				}
			};
		} else {
			final File webUpdate = new File(getFilesDir(),
					Constant.WEB_ROOT_DIR_UPDATE);
			if (webUpdate.exists()) {
				run = new Runnable() {

					@Override
					public void run() {
						FileUtils.deleteFileRecur(webRootFile);
						
						try {
							FileUtils.copyDir(webUpdate, webRootFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						File filetmp = new File(webUpdate.getParentFile(), webUpdate.getName() + "_tmp");
						webUpdate.renameTo(filetmp);
						FileUtils.deleteFileRecur(webUpdate);
						FileUtils.deleteFileRecur(filetmp);
						
						runOnUiThread(new Runnable() {
							public void run() {
								startMainActivity();	
							}
						});
					}
				};
			}
		}

		if (run != null) {
			BackgroundExecutor.execute(run);
		}else{
			startMainActivity();
		}
	}
	
	private void startMainActivity(){
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void finish() {
		ConfigPreference.setFirstStartup(false);
		super.finish();
	}
	
}
