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
	 * ע��
	 * 
	 * @param rp
	 *            register param {@link RequestRegister}
	 * @return statusCode: 200 success; BaseDto is null.
	 */
	@POST("/user/user")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<BaseDto> register(@Body RequestParam rp);

	/**
	 * ��¼
	 * 
	 * @param rp
	 *            login param {@link RequestLogin}
	 * @return login success return token.
	 */
	@POST("/user/login")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<ResponseLogin> login(@Body RequestParam rp);

	/**
	 * �ǳ�
	 * 
	 * @param token
	 * @return BaseDto is null.
	 */
	@POST("/user/logout")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<BaseDto> logout(@Header("token") String token);

	/**
	 * �޸�����
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
	 * ��ȡ��ǰ�û�
	 * 
	 * @param token
	 * @return
	 */
	@GET("/user/currentUser")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<BaseDto> getCurrentUser(@Header("token") String token);

	/**
	 * �޸��û���Ϣ
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
	 * ��ѯ��ǰ�û���Ϣ
	 * 
	 * @param token
	 * @return
	 */
	@GET("/user/userInfo")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<ResponseUserInfo> getUserInfo(@Header("token") String token);

	/**
	 * ��ȡ�ҵļ��������Ļ
	 * 
	 * @param token
	 * @param page
	 *            ҳ�룬Ĭ��Ϊ0
	 * @return null or ActivityInfoInListDto[]
	 */
	@GET("/activity/myComingActivities")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<ActivityInfoInListDto[]> getComingActivites(
			@Header("token") String token, @Query("page") int page);

	/**
	 * ��ȡ�Ҹ���Ȥ�Ļ
	 * @param token
	 * @param page
	 * @return null or ActivityInfoInListDto[]
	 */
	@GET("/activity/myInterestActivities")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<ActivityInfoInListDto[]> getInterestActivities(@Header("token") String token,
			@Query("page") int page);

	/**
	 * �����
	 * 
	 * Query ����Ϊnull ʱ��get��url������Ӹò���
	 * 
	 * @param text
	 *            �������ݣ�null��ʾ����ȫ��
	 * @param cityId
	 *            ��������id�� null ��ʾ����ȫ��
	 * @param searchDateMap
	 *            ��������ʼʱ��㣬 �ͻ�������Ϊ����ɶԳ��֣�����ʼʱ��<=����ʱ��
	 * @param page
	 *            ҳ�룬Ĭ��Ϊ0
	 * @return null or ActivityInfoInListDto[]
	 */
	@GET("/activity/activities")
	@Headers("Content-Type:application/json;charset=utf-8")
	ResponseData<ActivityInfoInListDto[]> searchActivities(
			@Query("text") String text, @Query("city") Integer cityId,
			@QueryMap Map<String, String> searchDateMap, @Query("page") int page);

}
