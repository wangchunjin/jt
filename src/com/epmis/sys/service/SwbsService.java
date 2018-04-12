package com.epmis.sys.service;

import com.epmis.sys.util.UserInfo;
import com.epmis.sys.vo.SmSwbs;
import java.io.File;
import java.util.List;
import java.util.Map;

public abstract interface SwbsService
{
  public abstract List<Map<String, Object>> ShowSwbsTree(String paramString1, String paramString2);

  public abstract SmSwbs GetSwbs(SmSwbs paramSmSwbs);

  public abstract String UpdateSwbs(SmSwbs paramSmSwbs, String paramString);

  public abstract List ShowSwbsTypeTable(String paramString);

  public abstract String DelSwbsType(String paramString);

  public abstract String impSwbsModule(String paramString1, String paramString2, File paramFile, UserInfo paramUserInfo);

  public abstract List ShowPlanRefDataTable(String paramString1, String paramString2, String paramString3);

  public abstract String AddDocLink(String paramString1, String paramString2, String paramString3, String paramString4, UserInfo paramUserInfo);

  public abstract String delDocLink(String paramString);

  public abstract String PlanStructTable(String paramString);

  public abstract String AddSwbs(SmSwbs paramSmSwbs);

  public abstract String delSwbs(String paramString);

  public abstract String moduleSave(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract List ShowSwbsMoveTree(String paramString1, String paramString2, String paramString3);

  public abstract String MoveSwbs(String paramString1, String paramString2, String paramString3);

  public abstract String CopySwbs(String paramString1, String paramString2, String paramString3);
}