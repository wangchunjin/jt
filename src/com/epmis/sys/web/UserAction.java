package com.epmis.sys.web;

import com.epmis.sys.service.RoleService;
import com.epmis.sys.service.UserService;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.sys.vo.CmUsers;
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
public class UserAction extends ActionSupport
  implements ServletRequestAware
{

 

@Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;
  private CmUsers cmUsers;
  HttpServletRequest request;
  private String parentId;
  HttpServletResponse response = ServletActionContext.getResponse();

  public CmUsers getCmUsers()
  {
    return this.cmUsers;
  }
  public void setCmUsers(CmUsers cmUsers) {
    this.cmUsers = cmUsers;
  }

  public String getParentId() {
    return this.parentId;
  }
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public void setServletRequest(HttpServletRequest request) {
    this.request = request;
  }

  public void SysUserTable()
  {
	  //获取当前用户登录的user_id  hxl
	  String cuid = DataTypeUtil.formatDbColumn(this.request.getParameter("cuid"));
	  System.out.println("cuid:-----------"+cuid);
	  
	//获取当前用户登录的issub  hxl
	  String issub = DataTypeUtil.formatDbColumn(this.request.getParameter("issub"));
	  System.out.println("issub:-----------"+issub);
	  
	  
    String where = DataTypeUtil.formatDbColumn(this.request.getParameter("where"));
    System.out.println("where:-----------"+where);
    String key = DataTypeUtil.formatDbColumn(this.request.getParameter("key"));
    System.out.println("key:-----------"+key);
    List resutl = this.userService.SysUserTable(where, key,cuid,issub);
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
  public void SysUserTableByRoleId() {
    String roleId = this.request.getParameter("ROLE_ID");
    List resutl = this.userService.SysUserTableByRoleId(roleId);
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
  public String GetUserInfo() {
    String USER_ID = this.request.getParameter("USER_ID");
    Map result = this.userService.GetUserInfoById(USER_ID);
    this.request.setAttribute("CmUserInfo", result);
    return "success";
  }
  public void AddUser() {
    String roleId = this.request.getParameter("ROLE_ID");
    String userId = Guid.getGuid();
    this.cmUsers.setUserId(userId);
    String result = this.userService.AddUser(this.cmUsers, roleId);
    Map map = new HashMap();
    map.put("result", result);
    map.put("userid", userId);
    WriterJsonArray.writerJSONArray(map, this.response);
  }
  public void DelUser() {
    String userId = this.request.getParameter("USER_ID");
    String result="";
	String [] stringArr= userId.split(",");
	for(int i=0;i<stringArr.length;i++){
		result = this.userService.DelUser(stringArr[i]);
	}
//    String result = this.userService.DelUser(userId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void UpdateUser() {
    String result = this.userService.UpdateUser(this.cmUsers);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void UpdateUserOther() {
    String result = this.userService.UpdateUserOther(this.cmUsers);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void RoleUserTable() {
    String userId = this.request.getParameter("USER_ID");
    List result = this.userService.RoleUserTable(userId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void AddRoleUserTree() {
    String userId = this.request.getParameter("userId");
    List result = this.userService.AddRoleUserTree(this.parentId, userId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void AddRoleUser() {
    String roleIds = this.request.getParameter("datas");
    String userId = this.request.getParameter("userId");
    String result = this.userService.AddRoleUser(roleIds, userId);
    String roleName = this.roleService.GetRoleNameByUserId(userId);
    Map map = new HashMap();
    map.put("result", result);
    map.put("roleName", roleName);
    WriterJsonArray.writerJSONArray(map, this.response);
  }
  public void DelRoleUser() {
    String rluserIds = this.request.getParameter("rluserIds");
    String result = this.userService.DelRoleUser(rluserIds);
    String userId = this.request.getParameter("userId");
    String roleName = this.roleService.GetRoleNameByUserId(userId);
    Map map = new HashMap();
    map.put("result", result);
    map.put("roleName", roleName);
    WriterJsonArray.writerJSONArray(map, this.response);
  }
  public void ModuleLicenseUserTable() {
    String userId = this.request.getParameter("USER_ID");
    List result = this.userService.ModuleLicenseUserTable(userId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void UpdateModuleLicenseUser() {
    String userId = this.request.getParameter("USER_ID");
    String moduleCodes = this.request.getParameter("MODULE_CODE");
    String result = this.userService.UpdateModuleLicenseUser(userId, moduleCodes);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void ModuleAssignUserTree() {
    String userId = this.request.getParameter("USER_ID");
    List result = this.userService.ModuleAssignUserTree(this.parentId, userId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void UpdateModuleAssignUser() {
    String userId = this.request.getParameter("USER_ID");
    String moduleCodes = this.request.getParameter("MODULE_CODE");
    String result = this.userService.UpdateModuleAssignUser(userId, moduleCodes);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void ProjUserTree() {
    String userId = this.request.getParameter("USER_ID");
    if (DataTypeUtil.validate(userId)) {
      List result = this.userService.ProjUserTree(this.parentId, userId);
      WriterJsonArray.writerJSONArray(result, this.response);
    }
  }

  public void InsertProjUser() { String userId = this.request.getParameter("USER_ID");
    String projIds = this.request.getParameter("PROJ_IDS");
    String result = this.userService.InsertProjUser(userId, projIds);
    WriterJsonArray.writerJSONArray(result, this.response); }

  public void DeleteProjUser() {
    String userId = this.request.getParameter("USER_ID");
    String result = this.userService.DeleteProjUser(userId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void SeeProFileTable() {
    String profile_id = this.request.getParameter("PROFILE_ID");
    List result = this.userService.SeeProFileTable(profile_id);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void CheckUserNum() {
    String result = this.userService.CheckUserNum(this.request);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void SetAppUser() {
    String userId = this.request.getParameter("userId");
    String where = this.request.getParameter("where");
    String ensemble = this.request.getParameter("ensemble");
    String result = this.userService.SetAppUser(ensemble, userId, where);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
}