package com.izouqi.client.server.webservice;

import static com.izouqi.client.constant.Constant.isDebug;

import java.util.HashMap;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

import com.izouqi.client.constant.Constant;
import com.izouqi.client.server.webservice.dto.ActivityInfoInListDto;
import com.izouqi.client.server.webservice.dto.RequestLogin;
import com.izouqi.client.server.webservice.dto.ResponseLogin;

public class ServerImpl {
	private static ServerImpl instance;

	private IServerAPI serverAPI;

	// -alias cerzy : cerzy 为 bks中存储证书的别名
	// -file certest.cer : 为从服务器导出的证书
	// -keystore cerzy.bks : 将证书保存到cerzy.bks中，若文件不存在则新建，存在则新增一条记录
	// -storepass pwtest : bks的密码
	// keytool -importcert -v -trustcacerts -alias cerzy -file certest.cer -ke
	// ystore cerzy.bks -storetype BKS -providerclass
	// org.bouncycastle.jce.provider.Bou
	// ncyCastleProvider -storepass pwtest

	private ServerImpl() {
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

		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Constant.DEFAULT_SERVER_URL)
				.setLogLevel(isDebug ? LogLevel.FULL : LogLevel.NONE)
				.setRequestInterceptor(new RequestInterceptor() {

					@Override
					public void intercept(RequestFacade arg0) {
						arg0.addHeader("Content-Type",
								"application/json;charset=utf-8");
					}
				}).build();
		serverAPI = restAdapter.create(IServerAPI.class);
	}

	private static ServerImpl getInstance() {
		if (instance == null) {
			instance = new ServerImpl();
		}
		return instance;
	}

	public static synchronized void login() {
		RequestParam rp = new RequestLogin("zy", "123456");

		ResponseData<ResponseLogin> response = getInstance().serverAPI
				.login(rp);

		ResponseLogin rl = response.getData();

		System.out.println(response);
		System.out.println(rl);
	}

	public static synchronized void searchActivities() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("startTime", "");
		map.put("endTime", "");

		// ResponseData<ActivityInfoInListDto[]> r =
		// getInstance().serverAPI.searchActivities(1, map);
		// ActivityInfoInListDto[] a = r.getData();

		// System.out.println(a);
	}
}
