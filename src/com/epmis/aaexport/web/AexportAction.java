package com.epmis.aaexport.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.epmis.aaexport.service.AexportService;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AexportAction extends ActionSupport implements ServletRequestAware{
	@Autowired
	private AexportService aexportService;
	
	HttpServletRequest request;
	HttpServletResponse response= ServletActionContext.getResponse();
	

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
		
	}
	
	public void FWQKexport(){
		String cuid=request.getParameter("cuid");
		String lend_id=request.getParameter("lend_id");
		String lender_name=request.getParameter("lender_name");
		String batch_id=request.getParameter("batch_id");
//		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("UserInfo");
		aexportService.FWQKexport(response,cuid,lend_id,lender_name,batch_id);
	}
	
	public void Orderexport(){
		String cuid=request.getParameter("cuid");
		//获取还款日期
		String rel_time=request.getParameter("rel_time");
//		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("UserInfo");
		aexportService.Orderexport(response,rel_time,cuid);
//		WriterJsonArray.writerJSONArray("success", response);
	}

}
