package com.epmis.sys.web;

import com.epmis.sys.service.CatvalService;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.sys.vo.CmCatval;
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
public class CatvalAction extends ActionSupport
  implements ServletRequestAware
{

  /**
	 * 
	 */
	
@Autowired
  private CatvalService catvalService;
  private String parentId;
  private CmCatval cmCatval;
  HttpServletRequest request;
  HttpServletResponse response = ServletActionContext.getResponse();

  public String getParentId()
  {
    return this.parentId;
  }
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public CmCatval getCmCatval() {
    return this.cmCatval;
  }
  public void setCmCatval(CmCatval cmCatval) {
    this.cmCatval = cmCatval;
  }

  public void setServletRequest(HttpServletRequest request)
  {
    this.request = request;
  }

  public void CatvalTable()
  {
    String base_master_key = this.request.getParameter("base_master_key");
    String which_catval_type = this.request.getParameter("which_catval_type");
    List resutl = this.catvalService.CatvalTableOrderByKey(base_master_key);
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
  public void AddCatvalTree() {
    String catg_type_id = this.request.getParameter("catg_type_id");
    List resutl = this.catvalService.CatvalTreeByKey(this.parentId, catg_type_id);
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
  public void AddCatval() {
    String catg_type_id = this.request.getParameter("catg_type_id");
    String base_master_key = this.request.getParameter("base_master_key");
    String catg_id = this.request.getParameter("catg_id");
    String result = this.catvalService.AddCatval(catg_type_id, base_master_key, catg_id);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void DelCatval() {
    String Wids = this.request.getParameter("Wids");

    String result = this.catvalService.DelCatval(Wids);
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void AddCatvalAllTree() {
    String which_catval_type = this.request.getParameter("which_catval_type");
    String base_master_key = this.request.getParameter("base_master_key");
    List resutl = this.catvalService.AddCatvalAllTree(this.parentId, which_catval_type, base_master_key);
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
  public void AddCatvalAll() {
    String datas = this.request.getParameter("datas");
    String base_master_key = this.request.getParameter("base_master_key");

    String result = "";
    String[] das = datas.split(";");
    for (int i = 0; i < das.length; i++) {
      result = this.catvalService.AddCatval(das[i].split(",")[1], base_master_key, das[i].split(",")[0]);
    }
    Map root = new HashMap();
    root.put("result", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }
  public void ShowCatvalTypeTable() {
    String WHICH_CATVAL_TYPE = this.request.getParameter("WHICH_CATVAL_TYPE");
    List resutl = this.catvalService.ShowCatvalTypeTable(WHICH_CATVAL_TYPE);
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
  public void DelCatvalType() {
    String CATG_TYPE_ID = this.request.getParameter("CATG_TYPE_ID");

    String result = this.catvalService.DelCatvalType(CATG_TYPE_ID);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void AddCatvalCode() {
    String result = this.catvalService.AddCatvalCode(this.cmCatval);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void UpdateCatvalCode() {
    String result = this.catvalService.UpdateCatvalCode(this.cmCatval);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void DelCatvalCode() {
    String catgId = this.request.getParameter("catgId");
    String result = this.catvalService.DelCatvalCode(catgId);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public String GetCatvalInfo() {
    String catgId = this.request.getParameter("CATG_ID");
    Map result = this.catvalService.GetCatvalInfo(catgId);
    this.request.setAttribute("CmCatvalInfo", result);
    return "success";
  }
}