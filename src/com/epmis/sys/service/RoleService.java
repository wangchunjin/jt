package com.epmis.sys.service;

import com.epmis.sys.vo.CmRole;

import java.util.List;
import java.util.Map;

public abstract interface RoleService
{
  public abstract List<Map<String,Object>> SysRoleTree(String paramString);

  public abstract Map<String, Object> GetRoleInfoById(String paramString);

  public abstract String UpdateRole(CmRole paramCmRole);

  public abstract List<Map<String, Object>> RoleUserTable(String paramString);

  public abstract List<Map<String, Object>> AddUserTable(String paramString);

  public abstract String GetRoleNameByUserId(String paramString);

  public abstract String AddRoleUser(String paramString1, String paramString2);

  public abstract String UpdateModuleLicenseRole(String paramString1, String paramString2);

  public abstract List<Map<String, Object>> ModuleAssignRoleTree(String paramString1, String paramString2);

  public abstract String UpdateModuleAssignRole(String paramString1, String paramString2);

  public abstract List<Map<String, Object>> ModuleLicenseRoleTable(String paramString);

  public abstract List<Map<String, Object>> ProjRoleTree(String paramString1, String paramString2);

  public abstract String InsertProjRole(String paramString1, String paramString2);

  public abstract String DeleteProjRole(String paramString);

  public abstract String AddRole(CmRole paramCmRole);

  public abstract String DelRole(String paramString1, String paramString2);
}