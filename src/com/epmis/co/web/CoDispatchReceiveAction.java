package com.epmis.co.web;

import java.io.IOException;
import java.util.HashMap;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.epmis.co.service.CoDispatchReceiveService;
import com.epmis.co.service.CoNotifyService;
import com.epmis.co.vo.CoDispatch;
import com.epmis.co.vo.CoNotify;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

public class CoDispatchReceiveAction extends ActionSupport implements ServletRequestAware{
	@Autowired
    private CoDispatchReceiveService coDispatchReceiveService;		 
 
	  
	private CoDispatch coDispatch;
	  

	 
  	public CoDispatch getCoDispatch() {
		return coDispatch;
	}

	public void setCoDispatch(CoDispatch coDispatch) {
		this.coDispatch = coDispatch;
	}
	HttpServletRequest request  ;
	 @Override
	 public void setServletRequest(HttpServletRequest request) {
	 	this.request=request;
	 	
	 }
	 HttpServletResponse response = ServletActionContext.getResponse();
	   private String rows;//每页显示的记录数  
       
	    private String page;//当前第几页  
	  
	    public String getRows() {
			return rows;
		}

		public void setRows(String rows) {
			this.rows = rows;
		}

		public String getPage() {
			return page;
		}

		public void setPage(String page) {
			this.page = page;
		}
	    private String deleteFlag;
        public String getDeleteFlag() {
			return deleteFlag;
		}

		public void setDeleteFlag(String deleteFlag) {
			this.deleteFlag = deleteFlag;
		}

public void CoDispatchTable(){ 
	  UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo_Ent");
	  String content = request.getParameter("content"); 
	  String title = request.getParameter("title");
	  String received = request.getParameter("received");
	  String beginDate = request.getParameter("beginDate");
	  String endDate = request.getParameter("endDate");
	  
	  int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);    
      //每页显示条数  
      int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
      //每页的开始记录  第一页为1  第二页为number +1   
      int start = (intPage-1)*number;  
		List<Map<String, Object>> result = coDispatchReceiveService.CoDispatchTable(userInfo,start,number,title,content,received,beginDate,endDate,this.deleteFlag);
		Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map  
      jsonMap.put("total",coDispatchReceiveService.getCoDispatchCount(userInfo,title,content,received,beginDate,endDate,this.deleteFlag));//total键 存放总记录数，必须的  
      jsonMap.put("rows", result);//rows键 存放每页记录 list  
  	 JSONObject json = JSONObject.fromObject(jsonMap);
	      response.setContentType("application/json;charset=UTF-8");
	      response.setCharacterEncoding("utf-8");
	      response.setHeader("Charset", "utf-8");
	      response.setHeader("Cache-Control", "no-cache");
	      try {
			response.getWriter().println(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
  }
	 public void AddDispatch(){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo_Ent"); 
	 	String result = coDispatchReceiveService.AddDispatch(userInfo,this.coDispatch);
		WriterJsonArray.writerJSONArray(result,response);
	}
	 public String  OpenDispatch(){
		  String dispatch_id = request.getParameter("DISPATCH_ID"); 
		 	Map<String,Object> result = coDispatchReceiveService.OpenDispatch(dispatch_id);
			List<Map<String,Object>> listMap = coDispatchReceiveService.getReceiveInfoByDispatchId(dispatch_id);
		 	request.setAttribute("DispatchInfo", result);
		 	request.setAttribute("ReceiveInfo", listMap); 
		 	return "success";
		}
	 public String  OpenReceive(){
		  String dispatch_id = request.getParameter("DISPATCH_ID"); 
		 	Map<String,Object> result = coDispatchReceiveService.OpenDispatch(dispatch_id);
 		 	request.setAttribute("DispatchInfo", result); 
		 	return "success";
		}	 
	 public void CoReceiveTable(){ 
		  UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo_Ent");
		  String content = request.getParameter("content"); 
		  String title = request.getParameter("title");
		  String created = request.getParameter("created");
		  String beginDate = request.getParameter("beginDate");
		  String endDate = request.getParameter("endDate");
		  
		  int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);    
	      //每页显示条数  
	      int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
	      //每页的开始记录  第一页为1  第二页为number +1   
	      int start = (intPage-1)*number;  
			List<Map<String, Object>> result = coDispatchReceiveService.CoReceiveTable(userInfo,start,number,title,content,created,beginDate,endDate,this.deleteFlag);
			Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map  
	      jsonMap.put("total",coDispatchReceiveService.getCoReceiveCount(userInfo,title,content,created,beginDate,endDate,this.deleteFlag));//total键 存放总记录数，必须的  
	      jsonMap.put("rows", result);//rows键 存放每页记录 list  
	  	 JSONObject json = JSONObject.fromObject(jsonMap);
		      response.setContentType("application/json;charset=UTF-8");
		      response.setCharacterEncoding("utf-8");
		      response.setHeader("Charset", "utf-8");
		      response.setHeader("Cache-Control", "no-cache");
		      try {
				response.getWriter().println(json.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	  }
		public void PushMsg(){
			String dispatchId =  request.getParameter("dispatchId");
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
					"UserInfo_Ent");
			String userId = userInfo.getUserId();
			String result = coDispatchReceiveService.PushMsg(userId,dispatchId);
			WriterJsonArray.writerJSONArray(result, response);
		
	    }
	 
	  
}
