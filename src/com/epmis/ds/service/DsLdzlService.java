package com.epmis.ds.service;

import java.util.List;
import java.util.Map;

import com.epmis.sys.util.UserInfo;
//import com.epmis.sys.vo.CmProj;
//import com.epmis.sys.vo.CmProjSurvey;
//import com.epmis.sys.vo.CmProjpart;

public abstract interface DsLdzlService
{
	public abstract String DsLdzlTable(String moduleCode, UserInfo userInfo);

	public abstract String DsLdzlIndex(String dsXmjzHzSelectId, String dsJscxHzSelectId,
			String dsWxyHzSelectId, String dsXmsbHzSelectId,
			String dsDailyHzSelectId, String dsZdsgHzSelectId, UserInfo userInfo,String groupId);
	public abstract List<Map<String, Object>>  LdzlModuleByUserId(String UserId);
	public abstract List<Map<String, Object>>  ZjlrModuleByUserId(String UserId);
	public abstract String CheckUser(String userId);

	public abstract List<Map<String, Object>> ProjPlanTaskCodeId(String userId,
			String parentId, String startdate, String enddate, String codeId,
			String key, String nodeType, int start, int number);

	public abstract Object getProjPlanTaskCodeIdCount(String userId,String parentId,
			String startdate, String enddate, String codeId, String key,
			String nodeType);

	public abstract List<Map<String, Object>> ShowDocDetail(String id,
			String startdate, String enddate, String codeId, String nodeType);
}