package com.izouqi.client.server.webservice;

import static com.izouqi.client.constant.Constant.isDebug;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.android.AndroidApacheClient;
import retrofit.client.Client;
import retrofit.client.UrlConnectionClient;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;

import com.android.common.Tools;
import com.izouqi.client.constant.Constant;
import com.izouqi.client.server.webservice.dto.ResponseWebUpgrade;
import com.izouqi.client.toolkit.Utils;

public class ServerAPI {
	private static ServerAPI instance;

	private IServerAPI serverAPI;

	// -alias cerzy : cerzy 为 bks中存储证书的别名
	// -file certest.cer : 为从服务器导出的证书
	// -keystore cerzy.bks : 将证书保存到cerzy.bks中，若文件不存在则新建，存在则新增一条记录
	// -storepass pwtest : bks的密码
	// keytool -importcert -v -trustcacerts -alias cerzy -file certest.cer -ke
	// ystore cerzy.bks -storetype BKS -providerclass
	// org.bouncycastle.jce.provider.Bou
	// ncyCastleProvider -storepass pwtest

	private ServerAPI() {
		// HttpsURLConnection.setDefaultSSLSocketFactory(CustomSSLSocketFactory
		// .getDefault(IzouqiApplication.getApplication(), R.raw.cerzy,
		// "pwtest"));
		// HttpsURLConnection
		// .setDefaultHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		// RestAdapter.Builder rb = new RestAdapter.Builder().setEndpoint(
		// "https://10.2.16.182:8443/user").setLogLevel(
		// isDebug ? LogLevel.FULL : LogLevel.NONE);
		// RestAdapter restAdapter = rb.build();
		// serverAPI = restAdapter.create(IServerAPI.class);
		final Client client;
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
			client = new AndroidApacheClient();
		} else {
			client = new UrlConnectionClient();
		}

		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Constant.DEFAULT_SERVER_URL)
				.setLogLevel(isDebug ? LogLevel.FULL : LogLevel.NONE)
				.setClient(client).setConverter(new StringJsonConverter())
				.build();
		serverAPI = restAdapter.create(IServerAPI.class);
	}

	private static ServerAPI getInstance() {
		if (instance == null) {
			instance = new ServerAPI();
		}
		return instance;
	}

	public static synchronized ResponseWebUpgrade checkWebUpgrade() {
		Context context = Utils.getApp();
		if (Tools.isNetworkConnected(context)
				&& !Tools.isMobileConnected(context)) {s
			try {
				PackageInfo pi = context.getPackageManager().getPackageInfo(
						context.getPackageName(), 0);

				ResponseData<ResponseWebUpgrade> response = getInstance().serverAPI
						.checkWebUpgrade("latest",
								String.valueOf(pi.versionCode));
				if (response != null) {
					return response.getData();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * register
	 * 
	 * @param request
	 *            {"username":"", "password":""}
	 * @return
	 */
	public static synchronized String register(String request) {
		return getInstance().serverAPI.register(request);
	}

	/**
	 * 登录
	 * 
	 * @param request
	 *            {"username":"zy","password":"654321","source":0}
	 * @return {"statusCode":200, "message":"success", "body":{"token":"*****"}}
	 */
	public static synchronized String login(String request) {
		return getInstance().serverAPI.login(request);
	}

	/**
	 * logout
	 * 
	 * @param token
	 *            token
	 * @return
	 */
	public static synchronized String logout(String token) {
		return getInstance().serverAPI.logout(token);
	}

	/**
	 * changePassword
	 * 
	 * @param token
	 *            token
	 * @param request
	 *            {"oldPassword":"", "newPassword":""}
	 * @return
	 */
	public static synchronized String changePassword(String token,
			String request) {
		return getInstance().serverAPI.changePassword(token, request);
	}

	/**
	 * @param token
	 *            token
	 * @return
	 */
	public static synchronized String getCurrentUser(String token) {
		return getInstance().serverAPI.getCurrentUser(token);
	}

	/**
	 * 
	 * @param token
	 * @param request
	 * @return
	 */
	public static synchronized String modifyUserInfo(String token,
			String request) {
		return getInstance().serverAPI.modifyUserInfo(token, request);
	}

	/**
	 * @param token
	 * @return
	 */
	public static synchronized String getUserInfo(String token) {
		return getInstance().serverAPI.getUserInfo(token);
	}

	public static synchronized String getComingActivites(String token, int page) {
		return getInstance().serverAPI.getComingActivites(token, page);
	}

	public static synchronized String getInterestActivities(String token,
			int page) {
		return getInstance().serverAPI.getInterestActivities(token, page);
	}

	public static synchronized String searchActivities(String text,
			Integer cityId, String startTime, String endTime, int page) {

		Map<String, String> map = new HashMap<String, String>(2);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		return getInstance().serverAPI
				.searchActivities(text, cityId, map, page);
	}
}