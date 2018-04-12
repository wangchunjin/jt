package com.epmis.co.service;

import java.util.List;
import java.util.Map;

import com.epmis.co.vo.CoNotify;
import com.epmis.sys.util.UserInfo;

public abstract interface CoNotifyService
{

  public abstract List<Map<String, Object>> CoNotifyTable(UserInfo userInfo,int start, int number, String type, String title, String created, String beginDate, String endDate);

public abstract Object getCoNotifyCount(UserInfo userInfo,String type, String title, String created,
		String beginDate, String endDate);

public abstract String AddNotify(UserInfo userInfo, CoNotify coNotify);

public abstract Map<String, Object> GetCoNotifyInfo(String wid);

public abstract String SaveNotify(CoNotify coNotify);

public abstract List RoleUserTree(String wid, String parentId, String roleType,String key);
 
public abstract List<Map<String, Object>> FindCoNotify(UserInfo userInfo,int start, int number, String type, String title, String created, String beginDate, String endDate);

public abstract int FindCoNotifyCount(UserInfo userInfo,String type, String title, String created,
		String beginDate, String endDate);

public String addco_newCount(CoNotify coNotify);
//查询公告
public abstract Map<String, Object> showNotify(String wid);
//查询回复
public abstract List<Map<String, Object>> FindCoNotifyLink(String notityid,int start, int number);
//查询回复数量
public abstract int FindCoNotifyLinkCount(String notityid);

public abstract String PushMsg(String userId, String wid);
}