package com.epmis.sys.web;

import com.epmis.sys.service.ProfileService;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.sys.vo.CmProfile;
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
public class ProfileAction extends ActionSupport
  implements ServletRequestAware
{


@Autowired
  private ProfileService profileService;
  private CmProfile cmProfile;
  HttpServletRequest request;
  HttpServletResponse response = ServletActionContext.getResponse();

  public CmProfile getCmProfile()
  {
    return this.cmProfile;
  }
  public void setCmProfile(CmProfile cmProfile) {
    this.cmProfile = cmProfile;
  }

  public void setServletRequest(HttpServletRequest request) {
    this.request = request;
  }

  public void SysProfileTable()
  {
    String profType = this.request.getParameter("PROF_TYPE");
    List resutl = this.profileService.SysProfileTable(profType);
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
  public String GetProfileInfo() {
    String PROFILE_ID = this.request.getParameter("PROFILE_ID");
    Map result = this.profileService.GetProfileInfoById(PROFILE_ID);
    this.request.setAttribute("CmProfileInfo", result);
    return "success";
  }
  public void AddProfile() {
    String result = this.profileService.AddProfile(this.cmProfile);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void DelProfile() {
    String profile_id = this.request.getParameter("PROFILE_ID");
    String prof_type = this.request.getParameter("PROF_TYPE");
    String result = this.profileService.DelProfile(profile_id, prof_type);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void CopyProfile() {
    String profile_id = this.request.getParameter("PROFILE_ID");
    String prof_type = this.request.getParameter("PROF_TYPE");
    String result = this.profileService.CopyProfile(profile_id, prof_type);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void UpdateProfile() {
    String result = this.profileService.UpdateProfile(this.cmProfile);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void DatactrListTable() {
    String PROFILE_ID = this.request.getParameter("PROFILE_ID");
    String prof_type = this.request.getParameter("PROF_TYPE");
    List result = this.profileService.DatactrListTable(PROFILE_ID, prof_type);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void DeleteProfctr() {
    String profile_id = this.request.getParameter("PROFILE_ID");
    String result = this.profileService.DeleteProfctr(profile_id);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void InsertProfctr() {
    String profile_id = this.request.getParameter("PROFILE_ID");
    String datactr_codes = this.request.getParameter("DATACTR_CODES");
    String result = this.profileService.InsertProfctr(profile_id, datactr_codes);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void ProfileUserTable() {
    String profile_id = this.request.getParameter("PROFILE_ID");
    List result = this.profileService.ProfileUserTable(profile_id);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void AddUserTable() {
    String profile_id = this.request.getParameter("PROFILE_ID");
    List result = this.profileService.AddUserTable(profile_id);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void AddProfileUser() {
    String userIds = this.request.getParameter("USER_IDS");
    String profile_id = this.request.getParameter("PROFILE_ID");
    String result = this.profileService.AddProfileUser(userIds, profile_id);
    String actualName = this.profileService.GetUserNameByProfileId(profile_id);
    Map map = new HashMap();
    map.put("result", result);
    map.put("USER_NAME", actualName);
    WriterJsonArray.writerJSONArray(map, this.response);
  }

  public void DelProfileUser() {
    String userIds = this.request.getParameter("USER_IDS");
    String profile_id = this.request.getParameter("PROFILE_ID");
    String result = this.profileService.DelProfileUser(userIds);
    String actualName = this.profileService.GetUserNameByProfileId(profile_id);
    Map map = new HashMap();
    map.put("result", result);
    map.put("USER_NAME", actualName);
    WriterJsonArray.writerJSONArray(map, this.response);
  }
}