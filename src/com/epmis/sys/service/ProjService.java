package com.epmis.sys.service;

import com.epmis.sys.util.UserInfo;
import com.epmis.sys.vo.CmProj;
import com.epmis.sys.vo.CmProjSurvey;
import com.epmis.sys.vo.CmProjpart;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ProjService
{
  public abstract String AddEps(UserInfo paramUserInfo, CmProj paramCmProj);

  public abstract String DelEps(String paramString);

  public abstract CmProj GetEps(CmProj paramCmProj);

  public abstract String UpdateEps(CmProj paramCmProj);

  public abstract List<Map<String, Object>> ShowEpsTree(String paramString);

  public abstract List ShowProjTree(String paramString1, UserInfo paramUserInfo, String paramString2, String paramString3);

  public abstract void ChangeProj(String paramString, UserInfo paramUserInfo);

  public abstract String AddProj(UserInfo paramUserInfo, CmProj paramCmProj);

  public abstract List<Map<String, Object>> ShowUserTable(String paramString);

  public abstract String AddUser(String paramString1, String paramString2);

  public abstract String ModuleImport(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract CmProj GetProj(CmProj paramCmProj);

  public abstract String UpdateProjInfo(CmProj paramCmProj);

  public abstract List<Map<String, Object>> GetProjSurvey(String paramString);

  public abstract CmProjSurvey GetProjSurveyInfo(CmProjSurvey paramCmProjSurvey);

  public abstract String UpdateProjSurveyInfo(String paramString1, String paramString2);

  public abstract List ProjCompanyTable(String paramString);

  public abstract List ProjTable(UserInfo paramUserInfo);

  public abstract List ProjTreeProjectCattye(String paramString, UserInfo paramUserInfo);

  public abstract List ProjTreeSupervisionCattye(String paramString, UserInfo paramUserInfo);

  public abstract List ProjTreeComprojCattye(String paramString, UserInfo paramUserInfo);

  public abstract List ProjTreeCattye(String paramString1, String paramString2, UserInfo paramUserInfo);

  public abstract List AddVnmtTable(String paramString);

  public abstract String AddVnmt(String paramString1, String paramString2);

  public abstract String delVnmt(String paramString);

  public abstract List ProjPeopleTable(String paramString);

  public abstract String AddPeople(CmProjpart paramCmProjpart);

  public abstract String delPeople(String paramString);

  public abstract Map<String, Object> GetProjPart(String paramString);

  public abstract String SavePeople(CmProjpart paramCmProjpart);

  public abstract List ProjUserTable(String paramString);

  public abstract List GetProfileType(String paramString);

  public abstract String SetProfileType(String paramString1, String paramString2, String paramString3);

  public abstract List AddUserTable(String paramString);

  public abstract String delUser(String paramString1, String paramString2);

  public abstract String SetAllUser(String paramString1, String paramString2, String paramString3);

  public abstract List seeAssignTable(String paramString1, String paramString2);

  public abstract String DelProj(String paramString);

  public abstract List ShowProjColumn(String paramString);

  public abstract String CheckProjNum(HttpServletRequest paramHttpServletRequest);

  public abstract String MoveEps(String paramString1, String paramString2, String paramString3);

  public abstract String MoveProj(String paramString1, String paramString2, String paramString3);

  public abstract String CopyPlan(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract List ShowProjByMap(UserInfo paramUserInfo);
}