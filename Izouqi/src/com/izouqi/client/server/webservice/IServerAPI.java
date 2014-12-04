package com.izouqi.client.server.webservice;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import com.izouqi.client.server.webservice.dto.ActivityInfoInListDto;
import com.izouqi.client.server.webservice.dto.ResponseLogin;

public interface IServerAPI {

	/**
	 * 登录
	 * 
	 * @param rp
	 *            login param
	 * @return token
	 */
	@POST("/user/login")
	ResponseData<ResponseLogin> login(@Body RequestParam rp);

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
	ResponseData<ActivityInfoInListDto[]> searchActivities(
			@Query("text") String text, @Query("city") Integer cityId,
			@QueryMap Map<String, String> searchDateMap, @Query("page") int page);
}
