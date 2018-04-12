package com.epmis.sys.web;

import com.epmis.sys.service.SwbsService;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.sys.vo.SmSwbs;
import com.opensymphony.xwork2.ActionSupport;

import java.io.File;
import java.io.IOException;
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
public class SwbsAction extends ActionSupport
  implements ServletRequestAware
{


@Autowired
  private SwbsService swbsService;
  private String parentId;
  private String level;
  private File uploadfile;
  private SmSwbs smSwbs;
  HttpServletRequest request;
  HttpServletResponse response = ServletActionContext.getResponse();
  private String swbs_type_id;
  private String module_name;

  public String getParentId()
  {
    return this.parentId;
  }
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getLevel()
  {
    return this.level;
  }
  public void setLevel(String level) {
    this.level = level;
  }

  public File getUploadfile()
  {
    return this.uploadfile;
  }
  public void setUploadfile(File uploadfile) {
    this.uploadfile = uploadfile;
  }

  public SmSwbs getSmSwbs()
  {
    return this.smSwbs;
  }
  public void setSmSwbs(SmSwbs smSwbs) {
    this.smSwbs = smSwbs;
  }

  public void setServletRequest(HttpServletRequest request)
  {
    this.request = request;
  }

  public String getSwbs_type_id()
  {
    return this.swbs_type_id;
  }
  public void setSwbs_type_id(String swbsTypeId) {
    this.swbs_type_id = swbsTypeId;
  }

  public String getModule_name()
  {
    return this.module_name;
  }
  public void setModule_name(String moduleName) {
    this.module_name = moduleName;
  }

  public void ShowSwbsTree() {
    List list = this.swbsService.ShowSwbsTree(this.parentId, this.swbs_type_id);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public void ShowSwbsMoveTree() {
    String orginId = this.request.getParameter("orginId");
    List list = this.swbsService.ShowSwbsMoveTree(this.parentId, this.swbs_type_id, orginId);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public void ShowSwbsTypeTable() {
    List list = this.swbsService.ShowSwbsTypeTable(this.module_name);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public String GetLabel() {
    this.smSwbs = new SmSwbs();
    String swbsId = this.request.getParameter("swbs_id");
    this.smSwbs.setSwbsId(swbsId);
    this.smSwbs = this.swbsService.GetSwbs(this.smSwbs);
    this.request.setAttribute("smSwbs", this.smSwbs);
    return "success";
  }

  public String UpdateSwbs() {
    String type = this.request.getParameter("type");
    String resutl = this.swbsService.UpdateSwbs(this.smSwbs, type);
    return resutl;
  }
  public void DelSwbsType() {
    String swbs_type_id = this.request.getParameter("swbs_type_id");
    String result = this.swbsService.DelSwbsType(swbs_type_id);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void impSwbsModule() {
    String swbsTypeId = this.request.getParameter("swbsTypeId");
    String mode = this.request.getParameter("mode");
    UserInfo userInfo = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    String result = this.swbsService.impSwbsModule(swbsTypeId, mode, this.uploadfile, userInfo);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void PlanRefDataTable() {
    String doc_id = this.request.getParameter("doc_id");
    String swbs_id = this.request.getParameter("swbs_id");
    String swbs_type_id = this.request.getParameter("swbs_type_id");
    List list = this.swbsService.ShowPlanRefDataTable(swbs_id, doc_id, swbs_type_id);
    WriterJsonArray.writerJSONArray(list, this.response);
  }
  public void AddDocLink() {
    String base_item_type = this.request.getParameter("base_item_type");
    String swbs_type_id = this.request.getParameter("swbs_type_id");
    String base_master_key = this.request.getParameter("base_master_key");
    String docIds = this.request.getParameter("docIds");
    UserInfo userInfo = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    String result = this.swbsService.AddDocLink(base_item_type, swbs_type_id, base_master_key, docIds, userInfo);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void delDocLink() {
    String Wids = this.request.getParameter("Wids");
    String result = this.swbsService.delDocLink(Wids);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void PlanStructTable() {
    String swbs_id = this.request.getParameter("swbs_id");
    String result = this.swbsService.PlanStructTable(swbs_id);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void AddSwbs() {
    String swbs_id = this.request.getParameter("swbs_id");
    String result = this.swbsService.AddSwbs(this.smSwbs);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void delSwbs() {
    String swbs_id = this.request.getParameter("swbsId");
    String result = this.swbsService.delSwbs(swbs_id);
    WriterJsonArray.writerJSONArray(result, this.response);
  }

  public void moduleSave() throws IOException {
    UserInfo userInfo = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    String tableName = this.request.getParameter("tableName");
    String module_name = this.request.getParameter("module_name");
    String remark = this.request.getParameter("remark");
    String swbs_type_name = this.request.getParameter("swbs_type_name");
    String resutl = this.swbsService.moduleSave(tableName, module_name, remark, swbs_type_name, userInfo.getProjId());
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
  public void MoveSwbs() {
    String parentId = this.request.getParameter("parentId");
    String orginId = this.request.getParameter("orginId");
    String swbs_type_id = this.request.getParameter("swbs_type_id");
    String result = this.swbsService.MoveSwbs(parentId, orginId, swbs_type_id);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void CopySwbs() {
    String parentId = this.request.getParameter("parentId");
    String orginId = this.request.getParameter("orginId");
    String swbs_type_id = this.request.getParameter("swbs_type_id");
    String result = this.swbsService.CopySwbs(parentId, orginId, swbs_type_id);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
}