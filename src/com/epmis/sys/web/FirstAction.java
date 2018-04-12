package com.epmis.sys.web;

import com.epmis.sys.service.FirstService;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
@SuppressWarnings("serial")
public class FirstAction extends ActionSupport
  implements ServletRequestAware
{

  
@Autowired
  private FirstService firstService;
  HttpServletRequest request;
  HttpServletResponse response = ServletActionContext.getResponse();

  public void setServletRequest(HttpServletRequest request) {
    this.request = request;
  }

  public void GetMajor() {
    String Startdate = this.request.getParameter("Startdate");
    String Enddate = this.request.getParameter("Enddate");
    UserInfo userInfo = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    List resutl = this.firstService.MajorTable(userInfo, Startdate, Enddate);
    WriterJsonArray.writerJSONArray(resutl, this.response);
  }
  public String GetProjectPic() {
    UserInfo userInfo = (UserInfo)this.request.getSession().getAttribute("UserInfo");
    String projId = userInfo.getProjId();
    List PhotoList = this.firstService.ProjectPic(projId);
    this.request.setAttribute("PhotoList", PhotoList);
    return "success";
  }
}