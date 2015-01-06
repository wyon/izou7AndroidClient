package com.izouqi.client.toolkit;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Random;

import com.android.common.BackgroundExecutor;
import com.android.common.ZipUtils;
import com.izouqi.client.IzouqiApplication;
import com.izouqi.client.constant.Constant;
import com.izouqi.client.server.webservice.ServerImpl;
import com.izouqi.client.server.webservice.dto.ResponseWebUpgrade;

public final class Utils {
	private Utils() {
	}

	public static IzouqiApplication getApp() {
		return IzouqiApplication.getApplication();
	}

	/**
	 * 检测并升级html
	 */
	public static void checkWebUpgrade() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				ResponseWebUpgrade response = ServerImpl.checkWebUpgrade();
				if (response != null
						&& response.getVersion() > ConfigPreference
								.getWebVersion()) {
					File zipFile = downLoadFile(response.getUrl());
					if (zipFile != null) {
						File tmpDir = createTempDir();

						try {
							ZipUtils.upZipFile(zipFile, tmpDir);
							if (tmpDir.renameTo(new File(
									getApp().getFilesDir(),
									Constant.WEB_ROOT_DIR_UPDATE))) {
								ConfigPreference.setWebVersion(response
										.getVersion());
							}
						} catch (IOException e) {
							e.printStackTrace();
						}

						zipFile.delete();
					}
				}
			}

			private File createTempDir() {
				Random tempFileRandom = new Random();
				File baseDir = getApp().getFilesDir();
				File tmp;

				do {
					tmp = new File(baseDir, "www" + tempFileRandom.nextInt());
				} while (!(tmp.mkdir() && tmp.isDirectory()));
				return tmp;
			}

			private File downLoadFile(String httpUrl) {
				OutputStream ops = null;
				InputStream is = null;

				try {
					URL url = new URL(httpUrl);
					is = url.openStream();

					File tmpFile = File.createTempFile("hupgrade", ".zip");
					ops = new BufferedOutputStream(
							new FileOutputStream(tmpFile));

					byte[] buffer = new byte[10 * 1024];
					int len = -1;

					while ((len = is.read(buffer, 0, buffer.length)) > 0) {
						ops.write(buffer, 0, len);
					}

					return tmpFile;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (ops != null) {
						try {
							ops.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				return null;
			}
		};
		BackgroundExecutor.execute(runnable);
	}

	/**
	 * 
	 */
	public static void checkAppUpgrade() {

		Runnable runnable = new Runnable() {

			@Override
			public void run() {

			}
		};
		BackgroundExecutor.execute(runnable);
	}
}
