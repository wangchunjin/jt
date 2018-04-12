package com.epmis.sys.web;

import com.epmis.sys.service.ProjService;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.sys.vo.CmProj;
import com.epmis.sys.vo.CmProjSurvey;
import com.epmis.sys.vo.CmProjpart;
import com.opensymphony.xwork2.ActionSupport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
@SuppressWarnings("serial")
public class ProjAction extends ActionSupport
  implements ServletRequestAware
{

  
@Autowired
  private ProjService projService;
  private String parentId;
  private String projId;
  private CmProj cmProj;
  private CmProjSurvey cmProjSurvey;
  HttpServletRequest request;
  HttpServletResponse response = ServletActionContext.getResponse();
  private CmProjpart cmProjpart;

  public String getParentId()
  {
    return this.parentId;
  }
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getProjId()
  {
    return this.projId;
  }
  public void setProjId(String projId) {
    this.projId = projId;
  }

  public CmProj getCmProj()
  {
    return this.cmProj;
  }
  public void setCmProj(CmProj cmProj) {
    this.cmProj = cmProj;
  }

  public CmProjSurvey getCmProjSurvey()
  {
    return this.cmProjSurvey;
  }
  public void setCmProjSurvey(CmProjSurvey cmProjSurvey) {
    this.cmProjSurvey = cmProjSurvey;
  }

  public void setServletRequest(HttpServletRequest request)
  {
    this.request = request;
  }

  public void ShowEpsTree()
  {
    List list = this.projService.ShowEpsTree(this.parentId);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public void ShowProjTree() {
    String type = this.request.getParameter("type");
    String key = DataTypeUtil.formatDbColumn(this.request.getParameter("key"));
    UserInfo userInfo = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    List list = this.projService.ShowProjTree(this.parentId, userInfo, type, key);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public void ChangeProj() {
    UserInfo userInfo = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    Map root = new HashMap();
    if (DataTypeUtil.validate(this.projId)) {
      this.projService.ChangeProj(this.projId, userInfo);
      root.put("result", "success");
    } else {
      root.put("result", "error");
    }
    WriterJsonArray.writerJSONArray(root, this.response);
  }

  public void AddEps() throws IOException {
    UserInfo user = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    String resutl = this.projService.AddEps(user, this.cmProj);
    Map root = new HashMap();
    root.put("result", resutl);
    WriterJsonArray.writerJSONArray(root, this.response);
  }

  public void AddProj() throws IOException {
    UserInfo user = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    String projId = Guid.getGuid();
    this.cmProj.setProjId(projId);
    String resutl = this.projService.AddProj(user, this.cmProj);
    Map root = new HashMap();
    root.put("result", resutl);
    root.put("projId", projId);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void DelEps() {
    String projId = this.request.getParameter("proj_id");
    String result = this.projService.DelEps(projId);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void MoveEps() {
    String parentId = this.request.getParameter("parentId");
    String orginId = this.request.getParameter("orginId");
    UserInfo user = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    String userId = user.getUserId();
    String result = this.projService.MoveEps(parentId, orginId, userId);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void MoveProj() {
    String parentId = this.request.getParameter("parentId");
    String orginId = this.request.getParameter("orginId");
    UserInfo user = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    String userId = user.getUserId();
    String result = this.projService.MoveProj(parentId, orginId, userId);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }

  public String GetLabel() {
    this.cmProj = new CmProj();
    String proj_id = this.request.getParameter("PROJ_ID");
    this.cmProj.setProjId(proj_id);
    this.cmProj = this.projService.GetEps(this.cmProj);
    this.request.setAttribute("cmProj", this.cmProj);
    return "success";
  }

  public String labelInfo() {
    this.cmProj = new CmProj();
    String proj_id = this.request.getParameter("PROJ_ID");
    this.cmProj.setProjId(proj_id);
    this.cmProj = this.projService.GetProj(this.cmProj);
    this.request.setAttribute("cmProj", this.cmProj);
    return "success";
  }

  public String UpdateEps()
  {
    String resutl = this.projService.UpdateEps(this.cmProj);
    return resutl;
  }

  public void UpdateProjInfo() {
    String result = this.projService.UpdateProjInfo(this.cmProj);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void ShowUserTable() {
    String projId = this.request.getParameter("projId");
    List result = this.projService.ShowUserTable(projId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void AddUser() {
    String userIds = this.request.getParameter("userIds");
    String projId = this.request.getParameter("projId");
    String result = this.projService.AddUser(userIds, projId);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void ModuleImport() {
    UserInfo user = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    String userId = user.getUserId();
    String swbs_type_id = this.request.getParameter("swbs_type_id");
    String tableName = this.request.getParameter("tableName");
    String projId = this.request.getParameter("projId");
    String result = "";
    try {
      result = this.projService.ModuleImport(userId, projId, swbs_type_id, tableName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }

  public String labelInfoSuy() {
    String proj_id = this.request.getParameter("PROJ_ID");
    List ColumnInfo = this.projService.GetProjSurvey(proj_id);
    this.request.setAttribute("ColumnInfo", ColumnInfo);
    this.cmProjSurvey = new CmProjSurvey();
    this.cmProjSurvey.setProjId(proj_id);
    this.cmProjSurvey = this.projService.GetProjSurveyInfo(this.cmProjSurvey);
    this.request.setAttribute("cmProjSurvey", this.cmProjSurvey);
    return "success";
  }

  public void updateProjSuy() {
    String prames = this.request.getParameter("prames");
    String proj_id = this.request.getParameter("proj_id");
    String result = this.projService.UpdateProjSurveyInfo(prames, proj_id);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }

  public String ProjCompany()
  {
    return "success";
  }

  public void ProjCompanyTable()
  {
    String proj_id = this.request.getParameter("proj_id");
    List list = this.projService.ProjCompanyTable(proj_id);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public void AddVnmtTable()
  {
    String proj_id = this.request.getParameter("projId");
    List list = this.projService.AddVnmtTable(proj_id);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public void AddVnmt()
  {
    String proj_id = this.request.getParameter("projId");
    String VnmtIds = this.request.getParameter("VnmtIds");
    String result = this.projService.AddVnmt(proj_id, VnmtIds);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }

  public void delVnmt()
  {
    String Wids = this.request.getParameter("Wids");
    String result = this.projService.delVnmt(Wids);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }

  public void ProjTable()
  {
    UserInfo user = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    List list = this.projService.ProjTable(user);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public void ProjTreeProjectCattye()
  {
    UserInfo user = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    List list = this.projService.ProjTreeProjectCattye(this.parentId, user);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public void ProjTreeSupervisionCattye()
  {
    UserInfo user = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    List list = this.projService.ProjTreeSupervisionCattye(this.parentId, user);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public void ProjTreeComprojCattye()
  {
    UserInfo user = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    List list = this.projService.ProjTreeComprojCattye(this.parentId, user);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public void ProjTreeCattye()
  {
    String catg_type_id = this.request.getParameter("catg_type_id");
    UserInfo user = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    List list = this.projService.ProjTreeCattye(this.parentId, catg_type_id, user);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public String labelPeople() {
    return "success";
  }

  public void ProjPeopleTable() {
    String proj_id = this.request.getParameter("projId");
    UserInfo user = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    List list = this.projService.ProjPeopleTable(proj_id);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public CmProjpart getCmProjpart()
  {
    return this.cmProjpart;
  }
  public void setCmProjpart(CmProjpart cmProjpart) {
    this.cmProjpart = cmProjpart;
  }
  public void AddPeople() {
    String proj_id = this.request.getParameter("projId");
    String result = this.projService.AddPeople(this.cmProjpart);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void delPeople() {
    String ids = this.request.getParameter("ids");
    String result = this.projService.delPeople(ids);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public String EditPeople() {
    String id = this.request.getParameter("id");
    Map list = this.projService.GetProjPart(id);
    this.request.setAttribute("PeopleInfo", list);
    return "success";
  }
  public void SavePeople() {
    String result = this.projService.SavePeople(this.cmProjpart);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void ProjUserTable() {
    String projId = this.request.getParameter("projId");
    List result = this.projService.ProjUserTable(projId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void GetProfileType() {
    String value = this.request.getParameter("value");
    List result = this.projService.GetProfileType(value);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void SetProfileType() {
    String proj_id = this.request.getParameter("proj_id");
    String id = this.request.getParameter("id");
    String user_id = this.request.getParameter("user_id");
    String result = this.projService.SetProfileType(id, proj_id, user_id);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void AddUserTable() {
    String projId = this.request.getParameter("projId");
    List result = this.projService.AddUserTable(projId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void delUser() {
    String userIds = this.request.getParameter("userIds");
    String projId = this.request.getParameter("projId");
    String result = this.projService.delUser(userIds, projId);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void SetAllUser() {
    String profile_id = this.request.getParameter("profile_id");
    String projId = this.request.getParameter("projId");
    String userIds = this.request.getParameter("userIds");
    String result = this.projService.SetAllUser(profile_id, projId, userIds);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void seeAssignTable() {
    String profile_id = this.request.getParameter("profile_id");
    String where = this.request.getParameter("where");
    List result = this.projService.seeAssignTable(profile_id, where);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void DelProj() {
    String projId = this.request.getParameter("projId");
    String result = this.projService.DelProj(projId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void ShowProjColumn() {
    String proj_stage = this.request.getParameter("proj_stage");
    List result = this.projService.ShowProjColumn(proj_stage);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void CheckProjNum() {
    String result = this.projService.CheckProjNum(this.request);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void CopyPlan() {
    String tableName = this.request.getParameter("tableName");
    String parentId = this.request.getParameter("parentId");
    String orginId = this.request.getParameter("orginId");
    UserInfo user = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    String projId = user.getProjId();
    String result = this.projService.CopyPlan(parentId, orginId, tableName, projId);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void GetLocationByMark() {
    String location = this.request.getParameter("location");
    String outputType = this.request.getParameter("output");
    String key = this.request.getParameter("key");
    String strMessage = "";
    StringBuffer buffer = new StringBuffer();
    StringBuffer sendStr = new StringBuffer();

    BufferedReader reader = null;
    try {
      URL uploadServlet = new URL(
        "http://api.map.baidu.com/geocoder/v2/?ak=" + key + "&location=" + location + "&output=" + outputType + "&pois=0");

      HttpURLConnection servletConnection = (HttpURLConnection)uploadServlet
        .openConnection();

      servletConnection.setRequestMethod("POST");
      servletConnection.setDoOutput(true);
      servletConnection.setDoInput(true);
      servletConnection.setAllowUserInteraction(true);

      InputStream inputStream = servletConnection.getInputStream();
      reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
      while ((strMessage = reader.readLine()) != null) {
        buffer.append(strMessage);
      }

      System.out.println("接收返回值:" + buffer);
      this.response.getWriter().println(buffer);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void ShowProjEntByMap() { UserInfo userInfo = (UserInfo)this.request.getSession().getAttribute("UserInfo_Ent");
    List list = this.projService.ShowProjByMap(userInfo);
    WriterJsonArray.writerJSONArray(list, this.response); }

  public void ShowProjByMap() {
    UserInfo userInfo = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    List list = this.projService.ShowProjByMap(userInfo);
    WriterJsonArray.writerJSONArray(list, this.response);
  }
}