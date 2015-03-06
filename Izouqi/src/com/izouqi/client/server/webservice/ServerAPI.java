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
import com.izouqi.client.server.webservice.dto.LogBodyRequest;
import com.izouqi.client.server.webservice.dto.ResponseAppUpgrade;
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

	/**
	 * 检测html更新
	 * 
	 * @return
	 */
	@Deprecated
	public static ResponseWebUpgrade checkWebUpgrade() {
		Context context = Utils.getApp();
		if (Tools.isNetworkConnected(context)
				&& !Tools.isMobileConnected(context)) {
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
	 * 发送log
	 * 
	 * @param request
	 * @return
	 */
	@Deprecated
	public static boolean reportLog(LogBodyRequest request) {
		if (request != null && Tools.isNetworkConnected(Utils.getApp())) {
			try {
				@SuppressWarnings("rawtypes")
				ResponseData response = getInstance().serverAPI.log(request);
				if (response != null
						&& response.getStatusCode() == ResponseStatusCode.SUCCESS) {
					return true;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 检测apk升级
	 * 
	 * @return
	 */
	@Deprecated
	public static ResponseAppUpgrade checkApkUpgrade() {
		Context context = Utils.getApp();
		if (Tools.isNetworkConnected(context)) {
			try {
				PackageInfo pi = context.getPackageManager().getPackageInfo(
						context.getPackageName(), 0);
				ResponseData<ResponseAppUpgrade> response = getInstance().serverAPI
						.checkAppUpgrade(String.valueOf(pi.versionCode));
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
	public static String register(String request) {
		return getInstance().serverAPI.register(request);
	}

	/**
	 * 登录
	 * 
	 * @param request
	 *            {"username":"zy","password":"654321","source":0}
	 * @return {<br>
	 *         &nbsp;&nbsp;"statusCode":200,<br>
	 *         &nbsp;&nbsp;"message":"success",<br>
	 *         &nbsp;&nbsp;"data": {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "data":{"token":"*****"} <br>
	 *         } <br>
	 *         }
	 */
	public static String login(String request) {
		return getInstance().serverAPI.login(request);
	}

	/**
	 * logout
	 * 
	 * @param token
	 *            token
	 * @return 
	 *         {"data":{},"message":"登出成功！","statusCode":200,"body":{},"header":{
	 *         }}
	 */
	public static String logout(String token) {
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
	public static String changePassword(String token, String request) {
		return getInstance().serverAPI.changePassword(token, request);
	}

	/**
	 * 获取当前用户
	 * 
	 * @param token
	 *            token
	 * @return 
	 *         {"data":{"data":{"source":0,"id":1,"username":"zy"}},"message":"获取成功！"
	 *         ,"statusCode":200,"body":{"data":{"source":0,"id":1,"username":
	 *         "zy"}},"header":{}}
	 */
	public static String getCurrentUser(String token) {
		return getInstance().serverAPI.getCurrentUser(token);
	}

	/**
	 * 用户信息修改 For example: request:{"qq":""}
	 * {"data":{},"message":"修改成功！","statusCode":200,"body":{},"header":{}}
	 * 
	 * @param token
	 * @param request
	 *            json
	 *            <p>
	 *            realName 用户真实姓名 <br>
	 *            phone 电话<br>
	 *            email 邮箱<br>
	 *            qq Qq号<br>
	 *            headPicture 头像二进制数据<br>
	 *            birthday 生日<br>
	 *            city 所在城市<br>
	 *            sex 性别<br>
	 *            introduction 自我介绍<br>
	 *            interestField 兴趣领域<br>
	 *            interestCareer 兴趣职业<br>
	 * 
	 * @return
	 */
	public static String modifyUserInfo(String token, String request) {
		return getInstance().serverAPI.modifyUserInfo(token, request);
	}

	/**
	 * 修改用户职业信息
	 * 
	 * @param token
	 * @param careerInfo
	 *            json <br>
	 *            careerInfo 职业信息 Json <br>
	 *            "[{"begintime":"","endtime":"", "company":"", "position":"
	 *            "},...]"
	 * @return 
	 *         {"data":{},"message":"修改成功！","statusCode":200,"body":{},"header":{
	 *         }}
	 */
	public static String modifyUserCareerInfo(String token, String careerInfo) {
		return getInstance().serverAPI.modifyUserCareerInfo(token, careerInfo);
	}

	/**
	 * 用户信息查询
	 * 
	 * @param token
	 * @return json <br>
	 *         {"statusCode": 200, "message": "获取成功！",<br>
	 *         "data": {"data": {<br>
	 *         "id": 7,<br>
	 *         "interestField": "",<br>
	 *         "birthday": "2014-11-03",<br>
	 *         "sex": true,<br>
	 *         "phone": "12345678901",<br>
	 *         "headPicture": "",<br>
	 *         "email": "110@qq.com",<br>
	 *         "realName": "张大颖",<br>
	 *         "interestCareer": "",<br>
	 *         "introduction": "1234567890",<br>
	 *         "qq": "110",<br>
	 *         "city": 51<br>
	 *         } }}
	 */
	public static String getUserInfo(String token) {
		return getInstance().serverAPI.getUserInfo(token);
	}

	/**
	 * 用户职业信息查询
	 * 
	 * @param token
	 * @return json
	 *         <p>
	 *         { <br>
	 *         "statusCode": 200, <br>
	 *         "message": "获取成功！",<br>
	 *         "data": { "data": {<br>
	 *         "id": 1,<br>
	 *         "careerInfo":
	 *         "[{\"begintime\":\"\",\"endtime\":\"\",\"company\":\"\",\"position\":\"\"}]"
	 *         <br>
	 *         } }, }
	 */
	public static String getUserCareerInfo(String token) {
		return getInstance().serverAPI.getUserCareerInfo(token);

	}

	/**
	 * 获取我的即将到来的活动
	 * 
	 * @param token
	 * @param page
	 * @return
	 */
	public static String getComingActivites(String token, int page) {
		return getInstance().serverAPI.getComingActivites(token, page);
	}

	/**
	 * 活动搜索
	 * 
	 * @param text
	 * @param cityId
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	@Deprecated
	public static String searchActivities(String text, Integer cityId,
			String startTime, String endTime, int page) {

		Map<String, String> map = new HashMap<String, String>(2);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		return getInstance().serverAPI
				.searchActivities(text, cityId, map, page);
	}

	/**
	 * 感兴趣的活动
	 * 
	 * @param token
	 * @param page
	 * @return
	 */
	public static String getInterestActivities(String token, int page) {
		return getInstance().serverAPI.getInterestActivities(token, page);
	}

	/**
	 * 附近的活动
	 * 
	 * @param token
	 * @param page
	 * @param x
	 * @param y
	 * @return
	 */
	public static String nearbyActivities(String token, int page, String x,
			String y) {
		return getInstance().serverAPI.nearbyActivities(token, page, x, y);
	}

	/**
	 * 获取活动详细信息（首页）
	 * 
	 * @param token
	 * @param id
	 *            活动id
	 * @return
	 */
	public static String activityInfo(String token, String id) {
		return getInstance().serverAPI.activityInfo(token, id);
	}

	/**
	 * 关注活动
	 * 
	 * @param token
	 * @param request
	 *            json <br>
	 *            id 活动ID
	 * @return
	 */
	public static String focusActivity(String token, String request) {
		return getInstance().serverAPI.focusActivity(token, request);
	}

	/**
	 * 报名出席活动
	 * 
	 * @param token
	 * @param request
	 *            json <br>
	 *            id 活动ID <br>
	 *            registration 报名信息<br>
	 * 
	 * @return
	 */
	public static String signUpActivity(String token, String request) {
		return getInstance().serverAPI.signUpActivity(token, request);
	}

	/**
	 * 获取活动所有参与者
	 * 
	 * @param id
	 *            活动ID
	 * @return
	 */
	public static String getActivityJoiners(String id) {
		return getInstance().serverAPI.getActivityJoiners(id);
	}

	/**
	 * 获取活动留言
	 * 
	 * @param id
	 *            活动ID
	 * @param page
	 * @return
	 */
	public static String getActivityMessages(String id, int page) {
		return getInstance().serverAPI.getActivityMessages(id, page);
	}

	/**
	 * 提交活动留言
	 * 
	 * @param token
	 * @param request
	 *            json<br>
	 *            id 活动ID
	 * @return
	 */
	public static String postActivityMessage(String token, String request) {
		return getInstance().serverAPI.postActivityMessage(token, request);
	}

	/**
	 * 获取我的个人信息页
	 * 
	 * @param token
	 * @return
	 */
	public static String getMyInfo(String token) {
		return getInstance().serverAPI.getMyInfo(token);
	}

	/**
	 * 评价别人
	 * 
	 * @param token
	 * @param request
	 *            json<br>
	 *            userId 用户ID <br>
	 *            type 评价类型 0为praise，1为bad
	 * 
	 * @return
	 */
	public static String commentUser(String token, String request) {
		return getInstance().serverAPI.commentUser(token, request);
	}

	/**
	 * 浏览别人
	 * 
	 * @param token
	 * @param request
	 *            json<br>
	 *            userId 用户ID
	 * @return
	 */
	public static String browseUser(String token, String request) {
		return getInstance().serverAPI.browseUser(token, request);
	}

	/**
	 * 获取浏览我的人
	 * 
	 * @param token
	 * @param page
	 * @return
	 */
	public static String getBrowseMe(String token, int page) {
		return getInstance().serverAPI.getBrowseMe(token, page);
	}

	/**
	 * 获取他人个人主页信息
	 * 
	 * @param token
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public static String getUserPage(String token, String userId) {
		return getInstance().serverAPI.getUserPage(token, userId);
	}

	/**
	 * 获取聚会列表
	 * 
	 * @param token
	 * @param page
	 * @return
	 */
	public static String getParties(String token, int page) {
		return getInstance().serverAPI.getParties(token, page);
	}

	/**
	 * 关注聚会
	 * 
	 * @param token
	 * @param request
	 *            json<br>
	 *            partyId 聚会ID
	 * @return
	 */
	public static String concernParty(String token, String request) {
		return getInstance().serverAPI.concernParty(token, request);
	}

	/**
	 * 报名聚会
	 * 
	 * @param token
	 * @param request
	 *            json<br>
	 *            partyId 聚会ID
	 * @return
	 */
	public static String registParty(String token, String request) {
		return getInstance().serverAPI.registParty(token, request);
	}

	/**
	 * 获取聚会报名者
	 * 
	 * @param token
	 * @param partyId
	 *            聚会ID
	 * @return
	 */
	public static String getParthRegistrators(String token, String partyId) {
		return getInstance().serverAPI.getParthRegistrators(token, partyId);
	}

	/**
	 * 通过报名聚会者
	 * 
	 * @param token
	 * @param request
	 *            json<br>
	 *            partyId 聚会ID<br>
	 *            userId 报名者ID
	 * 
	 * @return
	 */
	public static String conformParthRegistrator(String token, String request) {
		return getInstance().serverAPI.conformParthRegistrator(token, request);
	}

	/**
	 * 添加聚会
	 * 
	 * @param token
	 * @param request
	 *            json<br>
	 *            title 主题 <br>
	 *            time 时间 <br>
	 *            place 地点 <br>
	 *            personNumber 人数 0为2-3人 1为4-6人 2为7-12人 3为13人以上<br>
	 *            form 类型 0为我请客 1为AA 2为待定<br>
	 *            description 说明<br>
	 *            open 是否公开 1为公开，0为不公开<br>
	 *            city 城市 int<br>
	 * 
	 * @return
	 */
	public static String addParty(String token, String request) {
		return getInstance().serverAPI.addParty(token, request);
	}

	/**
	 * 我发起的聚会
	 * 
	 * @param token
	 * @param page
	 * @return
	 */
	public static String getCreateParties(String token, int page) {
		return getInstance().serverAPI.getCreateParties(token, page);
	}

	/**
	 * 我报名的聚会
	 * 
	 * @param token
	 * @param page
	 * @return
	 */
	public static String getRegistParties(String token, int page) {
		return getInstance().serverAPI.getRegistParties(token, page);
	}

	/**
	 * 我关注的聚会
	 * 
	 * @param token
	 * @param page
	 * @return
	 */
	public static String getConcernParties(String token, int page) {
		return getInstance().serverAPI.getConcernParties(token, page);
	}

	/**
	 * 意见反馈
	 * 
	 * @param token
	 * @param request
	 *            json<br>
	 *            feedback 反馈
	 * @return
	 */
	public static String feedback(String token, String request) {
		return getInstance().serverAPI.feedback(token, request);
	}

	/**
	 * 获取聚会留言
	 * 
	 * @param id
	 * @param page
	 * @return
	 */
	public static String getPartyMessages(String id, int page) {
		return getInstance().serverAPI.getPartyMessages(id, page);
	}

	/**
	 * 提交聚会留言
	 * 
	 * @param token
	 * @param request
	 *            json<br>
	 *            id 聚会ID<br>
	 *            message 留言内容 <br>
	 *            toUser 回复的Useid 为空则没有回复人
	 * 
	 * @return
	 */
	public static String addPartyMessage(String token, String request) {
		return getInstance().serverAPI.addPartyMessage(token, request);
	}

	/**
	 * 获取聚会所有参与者
	 * 
	 * @param partyId
	 *            聚会ID
	 * @return
	 */
	public static String getPartyConcerns(String partyId) {
		return getInstance().serverAPI.getPartyConcerns(partyId);
	}
}