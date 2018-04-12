package com.epmis.sys.web;

import com.epmis.ds.vo.DsCmVnmtSupp;
import com.epmis.sys.service.VnmtService;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.sys.vo.CmVndt;
import com.epmis.sys.vo.CmVnmt;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
@SuppressWarnings("serial")
public class VnmtAction extends ActionSupport
  implements ServletRequestAware
{


@Autowired
  private VnmtService vnmtService;
  private CmVnmt cmVnmt;
  private CmVndt cmVndt;
  private DsCmVnmtSupp dsCmVnmtSupp;
  HttpServletRequest request;
  private String parentId;
  HttpServletResponse response = ServletActionContext.getResponse();

  public CmVndt getCmVndt()
  {
    return this.cmVndt;
  }
  public void setCmVndt(CmVndt cmVndt) {
    this.cmVndt = cmVndt;
  }

  public CmVnmt getCmVnmt() {
    return this.cmVnmt;
  }
  public void setCmVnmt(CmVnmt cmVnmt) {
    this.cmVnmt = cmVnmt;
  }

  public String getParentId()
  {
    return this.parentId;
  }
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public DsCmVnmtSupp getDsCmVnmtSupp() {
    return this.dsCmVnmtSupp;
  }
  public void setDsCmVnmtSupp(DsCmVnmtSupp dsCmVnmtSupp) {
    this.dsCmVnmtSupp = dsCmVnmtSupp;
  }

  public void setServletRequest(HttpServletRequest request) {
    this.request = request;
  }

  public void SysVnmtTable()
  {
    String key = this.request.getParameter("KEY");
    String projId = this.request.getParameter("PROJ_ID");
    List resutl = this.vnmtService.SysVnmtTable(key, projId);
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
  public void SysVnmtTree() {
    String catg_type_id = this.request.getParameter("catg_type_id");
    List resutl = this.vnmtService.SysVnmtTree(this.parentId, catg_type_id);
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
  public String GetVnmtInfo() {
    String vnmt_id = this.request.getParameter("VNMT_ID");
    Map result = this.vnmtService.GetVnmtInfoById(vnmt_id);
    this.request.setAttribute("CmVnmtInfo", result);
    return "success";
  }
  public void AddVnmt() {
    String result = this.vnmtService.AddVnmt(this.cmVnmt, this.dsCmVnmtSupp);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void DelVnmt() {
    String vnmtIds = this.request.getParameter("vnmtIds");
    String result = this.vnmtService.DelVnmt(vnmtIds);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void UpdateVnmt() {
    String result = this.vnmtService.UpdateVnmt(this.cmVnmt, this.dsCmVnmtSupp);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void UpdateVnmtOther() {
    String result = this.vnmtService.UpdateVnmtOther(this.cmVnmt);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void VndtTable() {
    String vnmtId = this.request.getParameter("vnmtId");
    List resutl = this.vnmtService.VndtTable(vnmtId);
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
  public void AddVndt() {
    String vnmtId = this.request.getParameter("vnmtId");
    String resutl = this.vnmtService.AddVndt(this.cmVndt);
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
  public String EditVndt() {
    String vndtId = this.request.getParameter("vndtId");
    Map result = this.vnmtService.GetVndtInfoById(vndtId);
    this.request.setAttribute("CmVndtInfo", result);
    return "success";
  }
  public void UpdateVndt() {
    String result = this.vnmtService.UpdateVndt(this.cmVndt);
    WriterJsonArray.writerJSONArray(result, this.response);
  }

  public void DelVndt() {
    String vndtIds = this.request.getParameter("vndtIds");
    String result = this.vnmtService.DelVndt(vndtIds);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
}