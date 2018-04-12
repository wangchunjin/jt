package com.epmis.cc.service;

import java.util.List;
import java.util.Map;

import com.epmis.cc.vo.SmTest;
import com.epmis.sys.util.UserInfo;

public abstract interface SmTestService
{

  public abstract List<Map<String, Object>>  ShowSmTestTree(String ParentId, String moduleCode, UserInfo userInfo);

public abstract String AddPlan(String tableName, SmTest smTest,
		UserInfo userInfo);

public abstract Map<String, Object> GetSmTestInfo(String planId);

public abstract String UpdateSmTestWbs(SmTest smTest, String type);

public abstract String UpdateAssGuides(SmTest smTest);

public abstract String cancleFinish(SmTest smTest);

public abstract String UpdateSmTestTask(SmTest smTest);

public abstract List ShowSmTestMoveTree(String parentId, String moduleCode,
		UserInfo userInfo);

public abstract String MoveSmTest(String parentId, String orginId, String projId);

public abstract Map<String, Object> queryUserLayout(UserInfo userInfo, String p_type);

public abstract String updateUserPos(UserInfo userInfo,String rows,String cols,String ids, String p_type);
public abstract String updateUserWidths(UserInfo userInfo,String selType,String p_widths, String p_type);


public abstract List<Map<String,Object>> portaletTable(String userId);
String updateUserPortalet(String userId, String pId,String checkFlag);

public abstract List<Map<String,Object>> portaletRoleTable(String roleId);
String updateRolePortalet(String roleId, String pId,String checkFlag);

String userClosePortalet(String userId,String pId);

public abstract List<Map<String,Object>> userPortalet(UserInfo userInfo, String p_type);

public abstract String userSelPortalet(UserInfo userInfo,String pIds,String p_type);

public abstract List<Map<String,Object>> dataTable();

}