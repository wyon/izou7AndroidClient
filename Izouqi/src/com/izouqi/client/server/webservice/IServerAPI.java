package com.izouqi.client.server.webservice;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import com.izouqi.client.server.webservice.dto.ResponseWebUpgrade;

public interface IServerAPI {

	/**
	 * 检测html更新
	 * 
	 * @param version
	 *            html version; latest 为最新
	 * @param apkVersion
	 *            当前apk版本
	 * @return
	 */
	@GET("/update/html")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<ResponseWebUpgrade> checkWebUpgrade(
			@Query("version") String version,
			@Query("apk_version") String apkVersion);

	@POST("/log/logs")
	@Headers("Content-Type:application/json;charset=utf-8")
	String log(@Body String request);
	
	/**
	 * 注册
	 * 
	 * @param rp
	 *            register param {@link RequestRegister}
	 * @return statusCode: 200 success; BaseDto is null.
	 */
	@POST("/user/user")
	@Headers("Content-Type:application/json;charset=utf-8")
	String register(@Body String request);

	/**
	 * 登录
	 * 
	 * @param rp
	 *            login param {@link RequestLogin}
	 * @return login success return token.
	 */
	@POST("/user/login")
	@Headers("Content-Type:application/json;charset=utf-8")
	String login(@Body String request);

	/**
	 * 登出
	 * 
	 * @param token
	 * @return BaseDto is null.
	 */
	@POST("/user/logout")
	@Headers("Content-Type:application/json;charset=utf-8")
	String logout(@Header("token") String token);

	/**
	 * 修改密码
	 * 
	 * @param token
	 * @param rp
	 *            {@link RequestChangePassword}
	 * @return BaseDto is null.
	 *         <p>
	 *         statusCode: 200 success;
	 */
	@PUT("/user/user")
	@Headers("Content-Type:application/json;charset=utf-8")
	String changePassword(@Header("token") String token, @Body String request);

	/**
	 * 获取当前用户
	 * 
	 * @param token
	 * @return
	 */
	@GET("/user/currentUser")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getCurrentUser(@Header("token") String token);

	/**
	 * 修改用户信息
	 * 
	 * @param token
	 * @param rp
	 * @return
	 */
	@PUT("/user/userInfo")
	@Headers("Content-Type:application/json;charset=utf-8")
	String modifyUserInfo(@Header("token") String token, @Body String request);

	/**
	 * 修改用户职业信息
	 * 
	 * @param token
	 * @param request
	 * @return
	 */
	@PUT("/user/userCareerInfo")
	@Headers("Content-Type:application/json;charset=utf-8")
	String modifyUserCareerInfo(@Header("token") String token,
			@Body String request);

	/**
	 * 查询当前用户信息
	 * 
	 * @param token
	 * @return
	 */
	@GET("/user/userInfo")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getUserInfo(@Header("token") String token);

	/**
	 * 查询用户职业信息
	 * 
	 * @param token
	 * @return
	 */
	@GET("/user/userCareerInfo")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getUserCareerInfo(@Header("token") String token);

	/**
	 * 获取我的即将到来的活动
	 * 
	 * @param token
	 * @param page
	 *            页码，默认为0
	 * @return null or ActivityInfoInListDto[]
	 */
	@GET("/activity/myComingActivities")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getComingActivites(@Header("token") String token,
			@Query("page") int page);

	/**
	 * 获取我感兴趣的活动
	 * 
	 * @param token
	 * @param page
	 * @return null or ActivityInfoInListDto[]
	 */
	@GET("/activity/myInterestActivities")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getInterestActivities(@Header("token") String token,
			@Query("page") int page);

	/**
	 * 搜索活动
	 * 
	 * Query 参数为null 时，get的url不会添加该参数
	 * 
	 * @param text
	 *            搜索内容，null表示搜索全部
	 * @param cityId
	 *            搜索城市id， null 表示搜索全部
	 * @param searchDateMap
	 *            搜索的起始时间点， 客户端限制为必须成对出现，并开始时间<=结束时间
	 * @param page
	 *            页码，默认为0
	 * @return null or ActivityInfoInListDto[]
	 */
	@GET("/activity/activities")
	@Headers("Content-Type:application/json;charset=utf-8")
	String searchActivities(@Query("text") String text,
			@Query("city") Integer cityId,
			@QueryMap Map<String, String> searchDateMap, @Query("page") int page);

	/**
	 * 附近的活动
	 * 
	 * @param token
	 * @param page
	 *            从0开始
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @return
	 */
	@GET("/activity/nearbyActivities")
	@Headers("Content-Type:application/json;charset=utf-8")
	String nearbyActivities(@Header("token") String token,
			@Query("page") int page, @Query("x") String x, @Query("y") String y);

	/**
	 * 获取活动详细信息
	 * 
	 * @param token
	 * @param id
	 *            活动id
	 * @return
	 */
	@GET("/activity/homePage/{id}")
	@Headers("Content-Type:application/json;charset=utf-8")
	String activityInfo(@Header("token") String token, @Path("id") String id);

	/**
	 * 关注活动
	 * 
	 * @param token
	 * @param request
	 *            {"id":""}
	 * @return
	 */
	@POST("/activity/joiner")
	@Headers("Content-Type:application/json;charset=utf-8")
	String focusActivity(@Header("token") String token, @Body String request);

	/**
	 * 报名出席活动
	 * 
	 * @param token
	 * @param request
	 *            {"id":"", "registration":""}
	 * @return
	 */
	@POST("/activity/registration")
	@Headers("Content-Type:application/json;charset=utf-8")
	String signUpActivity(@Header("token") String token, @Body String request);

	/**
	 * 获取活动所有参与者
	 * 
	 * @param id
	 * @return
	 */
	@GET("/activity/joiners")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getActivityJoiners(@Query("id") String id);

	/**
	 * 获取活动留言
	 * 
	 * @param id
	 * @param page
	 * @return
	 */
	@GET("/activity/messages")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getActivityMessages(@Query("id") String id, @Query("page") int page);

	/**
	 * 提交活动留言
	 * 
	 * @param token
	 * @param request
	 *            {"id":""}
	 * @return
	 */
	@POST("/activity/message")
	@Headers("Content-Type:application/json;charset=utf-8")
	String postActivityMessage(@Header("token") String token,
			@Body String request);

	/**
	 * 获取我的个人信息页
	 * 
	 * @param token
	 * @return
	 */
	@GET("/user/myInfomation")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getMyInfo(@Header("token") String token);

	/**
	 * 评价别人
	 * 
	 * @param token
	 * @param request
	 *            {"userId":"", "type":0} userId 用户ID type 评价类型 0为praise，1为bad
	 * 
	 * @return
	 */
	@POST("/user/comment")
	@Headers("Content-Type:application/json;charset=utf-8")
	String commentUser(@Header("token") String token, @Body String request);

	/**
	 * 浏览别人
	 * 
	 * @param token
	 * @param request
	 *            {"userId":""}
	 * @return
	 */
	@POST("/user/browse")
	@Headers("Content-Type:application/json;charset=utf-8")
	String browseUser(@Header("token") String token, @Body String request);

	/**
	 * 获取浏览我的人
	 * 
	 * @param token
	 * @param page
	 * @return
	 */
	@GET("/user/browseUsers")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getBrowseMe(@Header("token") String token, @Query("page") int page);

	/**
	 * 获取他人个人主页信息
	 * 
	 * @param token
	 * @param userId
	 * @return
	 */
	@GET("/user/userPage")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getUserPage(@Header("token") String token,
			@Query("userId") String userId);

	/**
	 * 获取聚会列表
	 * 
	 * @param token
	 * @param page
	 * @return
	 */
	@GET("/party/parties")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getParties(@Header("token") String token, @Query("page") int page);

	/**
	 * 关注聚会
	 * 
	 * @param token
	 * @param request
	 *            {"partyId":""}
	 * @return
	 */
	@POST("/party/concern")
	@Headers("Content-Type:application/json;charset=utf-8")
	String concernParty(@Header("token") String token, @Body String request);

	/**
	 * 报名聚会
	 * 
	 * @param token
	 * @param request
	 *            {"partyId":""}
	 * @return
	 */
	@POST("/party/registration")
	@Headers("Content-Type:application/json;charset=utf-8")
	String registParty(@Header("token") String token, @Body String request);

	/**
	 * 获取聚会报名者
	 * 
	 * @param token
	 * @param partyId
	 *            聚会ID
	 * @return
	 */
	@GET("/party/registrators")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getParthRegistrators(@Header("token") String token,
			@Query("partyId") String partyId);

	/**
	 * 
	 * @param token
	 * @param request
	 *            {"partyId":"", "userId":""}
	 * @return
	 */
	@POST("/party/registrator")
	@Headers("Content-Type:application/json;charset=utf-8")
	String conformParthRegistrator(@Header("token") String token,
			@Body String request);

	/**
	 * 添加聚会
	 * 
	 * @param token
	 * @param request
	 *            json 输入参数<br>
	 *            title 主题<br>
	 *            time 时间<br>
	 *            place 地点<br>
	 *            personNumber 人数 0为2-3人 1为4-6人 2为7-12人 3为13人以上<br>
	 *            form 类型 0为我请客 1为AA 2为待定<br>
	 *            description 说明<br>
	 *            open 是否公开 1为公开，0为不公开<br>
	 *            city 城市 int
	 * @return
	 */
	@POST("/party/party")
	@Headers("Content-Type:application/json;charset=utf-8")
	String addParty(@Header("token") String token, @Body String request);

	/**
	 * 我发起的聚会
	 * 
	 * @param token
	 * @param page
	 * @return
	 */
	@GET("/party/myCreateParties")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getCreateParties(@Header("token") String token,
			@Query("page") int page);

	/**
	 * 我报名的聚会
	 * 
	 * @param token
	 * @param page
	 * @return
	 */
	@GET("/party/myRegistParties")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getRegistParties(@Header("token") String token,
			@Query("page") int page);

	/**
	 * 我关注的聚会
	 * 
	 * @param token
	 * @param page
	 * @return
	 */
	@GET("/party/myConcernParties")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getConcernParties(@Header("token") String token,
			@Query("page") int page);

	/**
	 * 意见反馈
	 * 
	 * @param token
	 * @param request
	 *            {"feedback":""}
	 * @return
	 */
	@POST("/feedback/feedback")
	@Headers("Content-Type:application/json;charset=utf-8")
	String feedback(@Header("token") String token, @Body String request);

	/**
	 * 获取聚会留言
	 * 
	 * @param id
	 *            聚会id
	 * @param page
	 * @return
	 */
	@GET("/party/messages")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getPartyMessages(@Query("id") String id, @Query("page") int page);

	/**
	 * 提交聚会留言
	 * 
	 * @param token
	 * @param request
	 *            json 输入参数<br>
	 *            id 聚会ID<br>
	 *            message 留言内容<br>
	 *            toUser 回复的Useid 为空则没有回复人
	 * @return
	 */
	@POST("/party/message")
	@Headers("Content-Type:application/json;charset=utf-8")
	String addPartyMessage(@Header("token") String token, @Body String request);

	/**
	 * 获取聚会所有参与者
	 * 
	 * @param partyId
	 * @return
	 */
	@GET("/party/concerns")
	@Headers("Content-Type:application/json;charset=utf-8")
	String getPartyConcerns(@Query("partyId") String partyId);
}