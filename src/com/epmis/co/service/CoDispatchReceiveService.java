package com.epmis.co.service;

import java.util.List;
import java.util.Map;

import com.epmis.co.vo.CoDispatch;
import com.epmis.co.vo.CoNotify;
import com.epmis.sys.util.UserInfo;

public abstract interface CoDispatchReceiveService
{

	String AddDispatch(UserInfo userInfo, CoDispatch coDispatch);

	List<Map<String, Object>> CoDispatchTable(UserInfo userInfo, int start,
			int number, String title, String content, String received,
			String beginDate, String endDate, String deleteFlag);

	Object getCoDispatchCount(UserInfo userInfo, String title, String content,
			String received, String beginDate, String endDate, String deleteFlag);

	Map<String, Object> OpenDispatch(String dispatchId);

	List<Map<String, Object>> getReceiveInfoByDispatchId(String dispatchId);

	List<Map<String, Object>> CoReceiveTable(UserInfo userInfo, int start,
			int number, String title, String content, String created,
			String beginDate, String endDate, String deleteFlag);

	Object getCoReceiveCount(UserInfo userInfo, String title, String content,
			String created, String beginDate, String endDate, String deleteFlag);

	String PushMsg(String userId, String dispatchId);
	

 
   
}