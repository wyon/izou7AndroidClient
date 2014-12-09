package com.izouqi.client.server.webservice;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import com.izouqi.client.server.webservice.dto.ActivityInfoInListDto;
import com.izouqi.client.server.webservice.dto.BaseDto;
import com.izouqi.client.server.webservice.dto.RequestChangePassword;
import com.izouqi.client.server.webservice.dto.RequestLogin;
import com.izouqi.client.server.webservice.dto.RequestRegister;
import com.izouqi.client.server.webservice.dto.ResponseLogin;
import com.izouqi.client.server.webservice.dto.ResponseUserInfo;

public interface IServerAPI {

	/**
	 * 注册
	 * 
	 * @param rp
	 *            register param {@link RequestRegister}
	 * @return statusCode: 200 success; BaseDto is null.
	 */
	@POST("/user/user")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<BaseDto> register(@Body RequestParam rp);

	/**
	 * 登录
	 * 
	 * @param rp
	 *            login param {@link RequestLogin}
	 * @return login success return token.
	 */
	@POST("/user/login")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<ResponseLogin> login(@Body RequestParam rp);

	/**
	 * 登出
	 * 
	 * @param token
	 * @return BaseDto is null.
	 */
	@POST("/user/logout")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<BaseDto> logout(@Header("token") String token);

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
	ResponseData<BaseDto> changePassword(@Header("token") String token,
			@Body RequestParam rp);

	/**
	 * 获取当前用户
	 * 
	 * @param token
	 * @return
	 */
	@GET("/user/currentUser")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<BaseDto> getCurrentUser(@Header("token") String token);

	/**
	 * 修改用户信息
	 * 
	 * @param token
	 * @param rp
	 * @return
	 */
	@PUT("/user/userInfo")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<BaseDto> modifyUserInfo(@Header("token") String token,
			@Body Map<String, Object> rp);

	/**
	 * 查询当前用户信息
	 * 
	 * @param token
	 * @return
	 */
	@GET("/user/userInfo")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<ResponseUserInfo> getUserInfo(@Header("token") String token);

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
	ResponseData<ActivityInfoInListDto[]> getComingActivites(
			@Header("token") String token, @Query("page") int page);

	/**
	 * 获取我感兴趣的活动
	 * @param token
	 * @param page
	 * @return null or ActivityInfoInListDto[]
	 */
	@GET("/activity/myInterestActivities")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<ActivityInfoInListDto[]> getInterestActivities(@Header("token") String token,
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
	ResponseData<ActivityInfoInListDto[]> searchActivities(
			@Query("text") String text, @Query("city") Integer cityId,
			@QueryMap Map<String, String> searchDateMap, @Query("page") int page);

}
