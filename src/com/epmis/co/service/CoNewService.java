package com.epmis.co.service;

import java.util.List;
import java.util.Map;

import com.epmis.cc.vo.SmTest;
import com.epmis.co.vo.CoNew;
import com.epmis.sys.util.UserInfo;

public abstract interface CoNewService
{

  public abstract List<Map<String, Object>> CoNewTable(UserInfo userInfo,int start, int number, String type, String title, String created, String beginDate, String endDate);

public abstract Object getCoNewCount(UserInfo userInfo,String type, String title, String created,
		String beginDate, String endDate);

public abstract String AddNew(UserInfo userInfo, CoNew coNew);

public abstract Map<String, Object> GetCoNewInfo(String wid);

public abstract String SaveNew(CoNew coNew);
 
public abstract String AddComment(String userId,String linkId,String Content,String type);
  
public abstract String DeleteComment(String commentId,String type);
//查询新闻前15条
public abstract List<Map<String, Object>> findco_new(int nowpage,int sizepage, String type, String title, String created, String beginDate, String endDate);
//查询新闻总条数
public abstract int findco_newcount(String type, String title, String created, String beginDate, String endDate);

//count++
public abstract String addco_newCount(CoNew coNew);

//根据ID查询新闻
public abstract Map<String, Object> findco_newByid(CoNew coNew);
//根据ID查询新闻回复
public abstract List<Map<String, Object>> findco_newByidLink(String newid,int start, int number);
//根据ID查询新闻回复数量
public abstract int findco_newByidLinkCount(String newid);

public abstract String PushMsg(String userId, String wid);

}