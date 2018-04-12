package com.epmis.km.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.epmis.km.service.FdmdService;
import com.epmis.km.vo.KmFdmd;
import com.epmis.sys.service.SwbsService;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.sys.vo.SmSwbs;
import com.opensymphony.xwork2.ActionSupport;


public class KmFdmdAction extends ActionSupport implements ServletRequestAware{

	@Autowired
    private FdmdService fdmdService;	
	  private String parentId;
	
	  public String getParentId() {
		return parentId; 
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
 
	private KmFdmd kmFdmd;
	
	 
	 public KmFdmd getKmFdmd() {
		return kmFdmd;
	}
	public void setKmFdmd(KmFdmd kmFdmd) {
		this.kmFdmd = kmFdmd;
	}
	HttpServletRequest request  ;
	 @Override
	 public void setServletRequest(HttpServletRequest request) {
	 	this.request=request;
	 	
	 }
	 HttpServletResponse response = ServletActionContext.getResponse();
 
	private String fdmd_type_id;
   
	public String getFdmd_type_id() {
		return fdmd_type_id;
	}
	public void setFdmd_type_id(String fdmdTypeId) {
		fdmd_type_id = fdmdTypeId;
	}
	public void ShowFdmdTypeTable()
	  {
		List list = fdmdService.ShowFdmdTypeTable();
		WriterJsonArray.writerJSONArray(list,response);
	  }  
	public void DelFdmdType(){
		 String fdmd_type_id = request.getParameter("fdmd_type_id"); 
		 String result = fdmdService.DelFdmdType(fdmd_type_id);
		 Map root = new HashMap();
		 root.put("result", result);
		 WriterJsonArray.writerJSONArray(root,response);
	}
	
	public void ShowFdmdTree()
	  {
		String fdmd_type_id = request.getParameter("fdmd_type_id"); 
		List list = fdmdService.ShowFdmdTree(this.parentId,fdmd_type_id);
		WriterJsonArray.writerJSONArray(list,response);
	  }
	public  void AddFdmd(){ 
			String  result = fdmdService.AddFdmd(kmFdmd);
			WriterJsonArray.writerJSONArray(result,response);
	}
	public  void delFdmd(){ 
		 String fdmdId = request.getParameter("fdmdId");   
			String  result = fdmdService.delFdmd(fdmdId);
			WriterJsonArray.writerJSONArray(result,response);
	}
	public String  GetLabel()
	  {
		 kmFdmd = new KmFdmd();
	     String fdmdId = request.getParameter("fdmdId");
    	 Map<String,Object> result = fdmdService.GetFdmd(fdmdId);
    	 request.setAttribute("FdmdInfo", result);
	     return "success";
	  }
	public void  UpdateFdmd()
	  {
	     String result = fdmdService.UpdateFdmd(kmFdmd);
	     WriterJsonArray.writerJSONArray(result,response);
	  }
    
}
