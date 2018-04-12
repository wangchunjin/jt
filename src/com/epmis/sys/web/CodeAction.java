package com.epmis.sys.web;

import com.epmis.sys.service.CodeService;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.sys.vo.CmCode;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
@SuppressWarnings("serial")
public class CodeAction extends ActionSupport
  implements ServletRequestAware
{


@Autowired
  private CodeService codeService;
  private CmCode cmCode;
  HttpServletRequest request;
  HttpServletResponse response = ServletActionContext.getResponse();

  public CmCode getCmCode()
  {
    return this.cmCode;
  }
  public void setCmCode(CmCode cmCode) {
    this.cmCode = cmCode;
  }

  public void setServletRequest(HttpServletRequest request) {
    this.request = request;
  }

  public void SysCodeTable()
  {
    String CodeType = this.request.getParameter("CodeType");
    String Belongto = this.request.getParameter("Belongto");
    List resutl = this.codeService.SysCodeTable(CodeType, Belongto);
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
  public String GetCodeInfo() {
    String wid = this.request.getParameter("WID");
    Map result = this.codeService.GetCodeInfoByWid(wid);
    this.request.setAttribute("CmCodeInfo", result);
    return "success";
  }
  public void AddCode() {
    String result = this.codeService.AddCode(this.cmCode);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void DelCode() {
    String wids = this.request.getParameter("wids");
    String result = this.codeService.DelCode(wids);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void UpdateCode() {
    String result = this.codeService.UpdateCode(this.cmCode);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void GetCodeJson() {
    String CodeType = this.request.getParameter("CodeType");
    String Belongto = this.request.getParameter("Belongto");
    String Value = this.request.getParameter("Value");
    String Test = this.request.getParameter("Test");
    String ShowValue = DataTypeUtil.formatDbColumn(this.request.getParameter("ShowValue"));
    String ShowTest = DataTypeUtil.formatDbColumn(this.request.getParameter("ShowTest"));
    List resutl = this.codeService.SysCodeTable(CodeType, Belongto, Value, Test, ShowValue, ShowTest);

    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
}