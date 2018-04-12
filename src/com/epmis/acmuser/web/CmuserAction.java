package com.epmis.acmuser.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;






import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.epmis.acmuser.service.CmuserService;
import com.epmis.acmuser.vo.Cmuser;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CmuserAction extends ActionSupport implements ServletRequestAware{
	@Autowired
	private CmuserService cmuserService;
	private Cmuser cmUsers;
	
	
	
	


	


	public Cmuser getCmUsers() {
		return cmUsers;
	}

	public void setCmUsers(Cmuser cmUsers) {
		this.cmUsers = cmUsers;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	private int page;
	private int rows;
	
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	HttpServletRequest request;
	HttpServletResponse response= ServletActionContext.getResponse();
	

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
		
	}
	/**
	 * 新增
	 * @throws Exception
	 */
	public void AddUser() throws Exception { 
	String roleId = this.request.getParameter("ROLE_ID");
    String userId = this.getGuid();
    this.cmUsers.setUserId(userId);
    this.cmUsers.setUserpic(this.execute(file));
    String result = this.cmuserService.AddUser(this.cmUsers, roleId);
    Map<String, Object> map = new HashMap();
    map.put("result", result);
    map.put("userid", userId);
    WriterJsonArray.writerJSONArray(map, this.response);
  }
	
	/**
	 * 修改
	 * @throws Exception 
	 */
	public void SaveUserInfo() throws Exception
	  {
		 this.cmUsers.setUserpic(this.execute(file));
	    String result = this.cmuserService.UpdateUser(this.cmUsers);
	    result = this.cmuserService.UpdateUserOther(this.cmUsers);
	    WriterJsonArray.writerJSONArray(result, this.response);
	  }
	
	
	public static String getGuid()
	  {
	    StringBuffer localStringBuffer = new StringBuffer();
	    UUID localUUID = UUID.randomUUID();
	    localStringBuffer.append(localUUID.toString().replaceAll("-", "").toUpperCase());
	    return localStringBuffer.toString();
	  }
	
	
	private File file;
	private String fileFileName;
	private String fileFileContentType;

	public String execute(File file) throws Exception{
			String root = ServletActionContext.getServletContext().getRealPath("/");
			String dirName ="upload";
			String dirPath = root+dirName;
			//拼接文件夹
	   	 	File dirFile = new File(dirPath);
				if(!dirFile.exists()){
					dirFile.mkdir();	//创建文件夹
				}
	        if(file!=null&&!"".equals(file)){
	        	InputStream is = new FileInputStream(file);
	        	 //中文乱码处理
				String uu = UUID.randomUUID().toString().replace("-", "");
				String lastName = fileFileName.substring(fileFileName.lastIndexOf("."));
				fileFileName = uu+""+lastName;
	        	String filePath = dirPath+"/"+fileFileName;
	 	        OutputStream os = new FileOutputStream(filePath);
		        String src="/"+dirName+"/"+fileFileName;
		        byte[] buffer = new byte[500];
		        int length = 0;
	        
	        while(-1 != (length = is.read(buffer, 0, buffer.length)))
	        {
	            os.write(buffer);
	        }
		        os.close();
		        is.close();
		        return src ;
	        }
	        return "";
	    }

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileFileContentType() {
		return fileFileContentType;
	}

	public void setFileFileContentType(String fileFileContentType) {
		this.fileFileContentType = fileFileContentType;
	}
	



}
