package com.epmis.sys.web;

import com.epmis.sys.service.RoleService;
import com.epmis.sys.service.UserService;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.sys.vo.CmRole;
import com.opensymphony.xwork2.ActionSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
@SuppressWarnings("serial")
public class RoleAction extends ActionSupport
  implements ServletRequestAware
{



@Autowired
  private RoleService roleService;

  @Autowired
  private UserService userService;
  private CmRole cmRole;
  private String parentId;
  HttpServletRequest request;
  HttpServletResponse response = ServletActionContext.getResponse();

  public String getParentId()
  {
    return this.parentId;
  }
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public CmRole getCmRole() {
    return this.cmRole;
  }
  public void setCmRole(CmRole cmRole) {
    this.cmRole = cmRole;
  }

  public void setServletRequest(HttpServletRequest request) {
    this.request = request;
  }

  public void SysRoleTree()
  {
    List<Map<String, Object>> result = this.roleService.SysRoleTree(this.parentId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public String GetRoleInfo() {
    String role_id = this.request.getParameter("ROLE_ID");
    Map<String, Object> result = this.roleService.GetRoleInfoById(role_id);
    this.request.setAttribute("CmRoleInfo", result);
    return "success";
  }
  public void AddRole() {
    String result = this.roleService.AddRole(this.cmRole);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void DelRole() {
    String roleId = this.request.getParameter("ROLE_ID");
    String roleType = this.request.getParameter("ROLE_TYPE");
    String result = this.roleService.DelRole(roleId, roleType);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void UpdateRole() {
    String result = this.roleService.UpdateRole(this.cmRole);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void RoleUserTable() {
    String roleId = this.request.getParameter("ROLE_ID");
    System.out.println("roleuser:______~~~~~："+roleId);
    List<Map<String, Object>> result = this.roleService.RoleUserTable(roleId);
    WriterJsonArray.writerJSONArray(result, response);
  }
  
  //后加的权限中用户
  public String label_user(){
	  System.out.println("进入权限用户！");
	  String ROLE_ID=request.getParameter("ROLE_ID");
	  System.out.println(ROLE_ID);
	  request.setAttribute("roleid", ROLE_ID);
	  return "success";
  }
  
  
  
  
  
  public void AddUserTable() {
    String roleId = this.request.getParameter("ROLE_ID");
    List<Map<String, Object>> result = this.roleService.AddUserTable(roleId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void AddRoleUser() {
    String userIds = this.request.getParameter("USER_IDS");
    String roleId = this.request.getParameter("ROLE_ID");
    String result = this.roleService.AddRoleUser(roleId, userIds);
    String actualName = this.userService.GetUserNameByRoleId(roleId);
    Map<String, Object> map = new HashMap<>();
    map.put("result", result);
    map.put("actualName", actualName);
    WriterJsonArray.writerJSONArray(map, this.response);
  }

  public void DelRoleUser() {
    String rluserIds = this.request.getParameter("rluserIds");
    String result = this.userService.DelRoleUser(rluserIds);
    String roleId = this.request.getParameter("roleId");
    String actualName = this.userService.GetUserNameByRoleId(roleId);
    Map<String, Object> map = new HashMap<>();
    map.put("result", result);
    map.put("actualName", actualName);
    WriterJsonArray.writerJSONArray(map, this.response);
  }
  public void ModuleLicenseRoleTable() {
    String roleId = this.request.getParameter("ROLE_ID");
    List<Map<String, Object>> result = this.roleService.ModuleLicenseRoleTable(roleId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void UpdateModuleLicenseRole() {
    String roleId = this.request.getParameter("ROLE_ID");
    String moduleCodes = this.request.getParameter("MODULE_CODE");
    String result = this.roleService.UpdateModuleLicenseRole(roleId, moduleCodes);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void ModuleAssignRoleTree() {
    String roleId = this.request.getParameter("ROLE_ID");
    List<Map<String, Object>> result = this.roleService.ModuleAssignRoleTree(this.parentId, roleId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void UpdateModuleAssignRole() {
    String roleId = this.request.getParameter("ROLE_ID");
    String moduleCodes = this.request.getParameter("MODULE_CODE");
    String result = this.roleService.UpdateModuleAssignRole(roleId, moduleCodes);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void ProjRoleTree() {
    String roleId = this.request.getParameter("ROLE_ID");
    if (DataTypeUtil.validate(roleId)) {
      List<Map<String, Object>> result = this.roleService.ProjRoleTree(this.parentId, roleId);
      WriterJsonArray.writerJSONArray(result, this.response);
    }
  }

  public void InsertProjRole() { String roleId = this.request.getParameter("ROLE_ID");
    String projIds = this.request.getParameter("PROJ_IDS");
    String result = this.roleService.InsertProjRole(roleId, projIds);
    WriterJsonArray.writerJSONArray(result, this.response); }

  public void DeleteProjRole() {
    String roleId = this.request.getParameter("ROLE_ID");
    String result = this.roleService.DeleteProjRole(roleId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
}