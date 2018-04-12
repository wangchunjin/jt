package com.epmis.sm.service;

import java.util.List;
import java.util.Map;

import com.epmis.sm.vo.SmPlan;
import com.epmis.sys.util.UserInfo;

public abstract interface SmPlanService
{
  public abstract List<Map<String, Object>>  ShowSmPlanTree(String ParentId,String moduleCode , UserInfo userInfo);
public abstract SmPlan GetSmPlan(SmPlan smPlan);

public abstract String UpdateSmPlan(SmPlan smPlan, String type);

public abstract List ShowPlanUserTable(String planId, String projId);

public abstract List AddPlanUserTable(String planId, String projId);

public abstract String AddPlanUser(String planId, String projId, String userIds, String tableName);

public abstract String delPlanUser(String planId, String UserIds, String tableName, String projId);

public abstract Map<String, Object> getSmPlanInfo(String plan_id);

public abstract String UpdateAssGuides(SmPlan smPlan);

public abstract String cancleFinish(SmPlan smPlan);

public abstract String UpdateSmPlanTask(SmPlan smPlan);

public abstract String UpdateSmPlanRemark(SmPlan smPlan);

public abstract List PlanDocTree(String base_master_key,String type,
		UserInfo userInfo);

public abstract List ShowPlanRefDataTable(String planId, String docId,
		String projId);

public abstract List PlanLindDocTable(String planId, String projId);

public abstract String AddPlan(String tableName, SmPlan smPlan, UserInfo userInfo);

public abstract String delPlan(String tableName, String planId,
		String nodeType, UserInfo userInfo);
public abstract List ShowSmPlanMoveTree(String parentId, String moduleCode,
		UserInfo userInfo);
public abstract String MoveSmPlan(String parentId, String orginId, String projId);
}