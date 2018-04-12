package com.epmis.sys.web;

import com.epmis.km.service.KmDocService;
import com.epmis.km.service.KmFolderService;
import com.epmis.km.vo.KmDoc;
import com.epmis.sys.service.StructService;
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.sys.vo.StructLanguage;
import com.epmis.sys.vo.StructOfficetpl;
import com.opensymphony.xwork2.ActionSupport;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
@SuppressWarnings("serial")
public class StructAction extends ActionSupport
  implements ServletRequestAware
{


@Autowired
  private StructService structService;

  @Autowired
  private KmDocService kmDocService;

  @Autowired
  private KmFolderService kmFolderService;
  private File uploadfile;
  private StructOfficetpl structOfficetpl;
  HttpServletRequest request;
  HttpServletResponse response = ServletActionContext.getResponse();
  private StructLanguage structLanguage;

  public File getUploadfile()
  {
    return this.uploadfile;
  }
  public void setUploadfile(File uploadfile) {
    this.uploadfile = uploadfile;
  }

  public StructOfficetpl getStructOfficetpl()
  {
    return this.structOfficetpl;
  }
  public void setStructOfficetpl(StructOfficetpl structOfficetpl) {
    this.structOfficetpl = structOfficetpl;
  }

  public void setServletRequest(HttpServletRequest request)
  {
    this.request = request;
  }

  public StructLanguage getStructLanguage()
  {
    return this.structLanguage;
  }
  public void setStructLanguage(StructLanguage structLanguage) {
    this.structLanguage = structLanguage;
  }

  public void StructTable() {
    List list = this.structService.ShowStructTable();
    WriterJsonArray.writerJSONArray(list, this.response);
  }
  public void AddStruct() {
    String result = this.structService.AddStruct(this.structOfficetpl, this.uploadfile);
    WriterJsonArray.writerJSONArray(result, this.response);
  }

  public void StructVersionTable()
  {
    String structOfficetpl = this.request.getParameter("ID");
    String officetplFlag = this.request.getParameter("OFFICETPL_FLAG");
    List list = this.structService.ShowStructVersionTable(structOfficetpl, officetplFlag);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public void LinkStructTable() {
    String swbs_id = this.request.getParameter("swbs_id");
    List list = this.structService.LinkStructTable(swbs_id);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public void AddLinkStructTable() {
    String swbs_id = this.request.getParameter("swbs_id");
    List list = this.structService.AddLinkStructTable(swbs_id);
    WriterJsonArray.writerJSONArray(list, this.response);
  }
  public void AddLinkStruct() {
    String swbs_id = this.request.getParameter("swbs_id");
    String officetpl_flags = this.request.getParameter("officetpl_flags");
    String result = this.structService.AddLinkStruct(officetpl_flags, swbs_id);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void DelLinkStruct() {
    String swbs_id = this.request.getParameter("swbs_id");
    String officetpl_flags = this.request.getParameter("officetpl_flags");
    String result = this.structService.DelLinkStruct(officetpl_flags, swbs_id);
    WriterJsonArray.writerJSONArray(result, this.response);
  }

  public String GetLabel() {
    String structOfficetpl = this.request.getParameter("ID");
    Map structInfo = this.structService.GetStruct(structOfficetpl);
    this.request.setAttribute("StructInfo", structInfo);
    return "success";
  }

  public void UpdateStruct() {
    String type = this.request.getParameter("type");
    String resutl = this.structService.UpdateStruct(this.structOfficetpl);
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }

  public void DelStruct() {
    String officetpl_ids = this.request.getParameter("OFFICETPL_IDS");
    String result = this.structService.delStruct(officetpl_ids);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void saveDoc() {
    String type = this.request.getParameter("type");
    String id = this.request.getParameter("id");
    String result = this.structService.saveDoc(this.uploadfile, type, id);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void CreateDoc() {
    String type = this.request.getParameter("type");
    String id = this.request.getParameter("id");
    String name = this.request.getParameter("officetplName");
    String base_master_key = this.request.getParameter("base_master_key");
    String base_link_id = this.request.getParameter("base_link_id");
    String folder_id = this.request.getParameter("folder_id");
    UserInfo user = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    String proj_id = user.getProjId();
    String user_id = user.getUserId();
    String actual_name = user.getActualName();
    String docDir = this.kmFolderService.getFullPathByFolderId(folder_id);
    String nfile_name = this.kmDocService.getTempFileName("doc");
    KmDoc kmDoc = new KmDoc();
    kmDoc.setFolderId(folder_id);
    kmDoc.setDocId(id);
    kmDoc.setProjId(proj_id);
    kmDoc.setCreatedBy(user_id);
    kmDoc.setDocGrade("2");
    kmDoc.setTitle(name);
    kmDoc.setFileName(nfile_name);
    kmDoc.setPublicLoc(docDir);
    kmDoc.setPrivateLoc(name + ".doc");
    kmDoc.setDocType("Word");
    kmDoc.setCreatedByName(actual_name);
    kmDoc.setBaseMasterKey(base_master_key);
    kmDoc.setBaseLinkId(base_link_id);
    this.kmDocService.InsertDoc(kmDoc);
    String basedir = AppSetting.PROJECT_PATH + "/KM/DOC";
    String result = this.structService.CreateDoc(this.uploadfile, basedir + docDir, nfile_name);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public String updateStructDoc() {
    String docId = this.request.getParameter("docId");
    String url = this.kmDocService.GetUrlById(docId, "");
    if (url.equals("error")) {
      return "error";
    }
    this.request.setAttribute("url", url);
    return "success";
  }

  public void OfficetplKeyInfo()
  {
    String id = this.request.getParameter("ID");
    List list = this.structService.OfficetplKeyInfo(id);
    WriterJsonArray.writerJSONArray(list, this.response);
  }

  public void addLanguage() {
    String result = this.structService.addLanguage(this.structLanguage);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void deleteLanguage() {
    String id = this.request.getParameter("id");
    String result = this.structService.deleteLanguage(id);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void deleteOwnLanguage() {
    String id = this.request.getParameter("id");
    String result = this.structService.checkNode(id);
    if (result.equals("success")) {
      result = this.structService.deleteLanguage(id);
    }
    WriterJsonArray.writerJSONArray(result, this.response);
  }

  public void updateLanguage() {
    System.out.println(this.structLanguage.getIsPublic());
    String result = this.structService.updateLanguage(this.structLanguage);
    WriterJsonArray.writerJSONArray(result, this.response);
  }

  public void showLanguageTree() {
    String parentId = this.request.getParameter("parentId");
    List result = this.structService.showLanguageTree(parentId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public String lableLanguage() {
    String id = this.request.getParameter("ID");
    Map languageInfo = this.structService.lableLanguage(id);
    this.request.setAttribute("languageInfo", languageInfo);
    return "success";
  }
  public void showOwnLanguageTree() {
    String parentId = this.request.getParameter("parentId");
    String userId = this.request.getParameter("userId");
    List result = this.structService.showOwnLanguageTree(parentId, userId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
}