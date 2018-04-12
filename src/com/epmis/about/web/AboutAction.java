package com.epmis.about.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epmis.about.service.AboutService;
import com.epmis.about.vo.About;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AboutAction extends ActionSupport implements ServletRequestAware{
	
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	@Autowired
	private AboutService aboutService;
	private About about;
	public About getAbout() {
		return about;
	}

	public void setAbout(About about) {
		this.about = about;
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
	 * 根据id查询对房公司信息
	 * @return
	 */
	public String findById(){
		String ids = request.getParameter("id");
		List<Map<String, Object>> result = this.aboutService.findById(ids);
		request.setAttribute("about", result);		
		return "success";
		
	}
	
	/**
	 * 修改
	 * @throws Exception 
	 */
	public void update() throws Exception{
		about.setPic(this.execute(file));
		String result = this.aboutService.update(about);
		WriterJsonArray.writerJSONArray(result, response);
		
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
		        String src="/upload/"+fileFileName;
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
