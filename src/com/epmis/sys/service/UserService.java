package com.epmis.sys.service;

import com.epmis.sys.vo.CmUsers;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface UserService
{
  public abstract List SysUserTable(String paramString1, String paramString2,String paramString3,String paramString4);

  public abstract Map<String, Object> GetUserInfoById(String paramString);

  public abstract String AddUser(CmUsers paramCmUsers, String paramString);

  public abstract String DelUser(String paramString);

  public abstract String UpdateUser(CmUsers paramCmUsers);

  public abstract String UpdateUserOther(CmUsers paramCmUsers);

  public abstract List RoleUserTable(String paramString);

  public abstract List AddRoleUserTree(String paramString1, String paramString2);

  public abstract String AddRoleUser(String paramString1, String paramString2);

  public abstract String DelRoleUser(String paramString);

  public abstract List ModuleLicenseUserTable(String paramString);

  public abstract String UpdateModuleLicenseUser(String paramString1, String paramString2);

  public abstract List ModuleAssignUserTree(String paramString1, String paramString2);

  public abstract String UpdateModuleAssignUser(String paramString1, String paramString2);

  public abstract List ProjUserTree(String paramString1, String paramString2);

  public abstract String InsertProjUser(String paramString1, String paramString2);

  public abstract String DeleteProjUser(String paramString);

  public abstract List SeeProFileTable(String paramString);

  public abstract String GetUserNameByRoleId(String paramString);

  public abstract List SysUserTableByRoleId(String paramString);

  public abstract String CheckUserNum(HttpServletRequest paramHttpServletRequest);

  public abstract String SetAppUser(String paramString1, String paramString2, String paramString3);
}