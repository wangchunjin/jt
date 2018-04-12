package com.epmis.km.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import com.epmis.km.service.KmFolderService;
import com.epmis.km.vo.KmFolder;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

public class KmFolderAction extends ActionSupport implements ServletRequestAware{

	@Autowired
    private KmFolderService kmFolderService;	
	 private String parentId;
	private KmFolder kmFolder;

	public KmFolder getKmFolder() {
		return kmFolder; 
	}
	public void setKmFolder(KmFolder kmFolder) {
		this.kmFolder = kmFolder;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	private String docGrade;
	
 
  	public String getDocGrade() {
		return docGrade;
	}
	public void setDocGrade(String docGrade) {
		this.docGrade = docGrade;
	}
	HttpServletRequest request  ;
	 @Override
	 public void setServletRequest(HttpServletRequest request) {
	 	this.request=request;
	 	
	 }
	 HttpServletResponse response = ServletActionContext.getResponse();
    
	public void ShowFolderTree()
	  {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		List list = kmFolderService.ShowFolderTree(this.parentId,this.docGrade,userInfo);
		WriterJsonArray.writerJSONArray(list,response); 
	  }
	  public void UpdatePath()
	  {
 		String resutl = kmFolderService.UpdatePath(this.docGrade);
		WriterJsonArray.writerJSONArray(resutl,response); 
	  }
	public void AddFolder() throws IOException
	  {
		UserInfo user = (UserInfo) request.getSession().getAttribute("UserInfo");
		  String resutl = kmFolderService.AddFolder(user, kmFolder);
		  Map root = new HashMap();
		  root.put("result", resutl);
	      WriterJsonArray.writerJSONArray(root,response);
	  }
	
	 
		public String  GetLabel()
	  {
	     String folder_id = request.getParameter("FOLDER_ID");  
	     String doc_grade = request.getParameter("DOC_GRADE");
	     if(DataTypeUtil.validate(folder_id)){
	        Map<String,Object> FolderInfo= kmFolderService.GetFolder(folder_id);		     
	        request.setAttribute("FolderInfo", FolderInfo);
	     }
	     if(DataTypeUtil.validate(doc_grade)&&doc_grade.equals("0")){
		     return "pub";	    	  
	     }else{
		     return "proj";    	 
	     }

	  }
		
		public void  UpdateFolder()
	  {
		 String type = request.getParameter("type");
	     String result = kmFolderService.UpdateFolder(kmFolder);
		  Map root = new HashMap();
		  root.put("result", result);
	      WriterJsonArray.writerJSONArray(root,response);
	  }
		public void  DeleteFolder()
		  {
			UserInfo user = (UserInfo) request.getSession().getAttribute("UserInfo");
			String projId = user.getProjId();
			 String folderId = request.getParameter("folderId");
			 String docGrade = request.getParameter("docGrade");
		     String result = kmFolderService.DeleteFolder(folderId,docGrade,projId);
			  Map root = new HashMap(); 
			  root.put("result", result);
		      WriterJsonArray.writerJSONArray(root,response);
		  }
       public void getFullPathNameByFolderId(){
    	   String folderId = request.getParameter("folderId");
    	   String path = kmFolderService.getFullPathNameByFolderId(folderId);
    	   WriterJsonArray.writerJSONArray(path,response);
       }
}
