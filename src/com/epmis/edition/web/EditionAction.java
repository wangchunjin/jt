package com.epmis.edition.web;

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




import com.epmis.edition.service.EditionService;
import com.epmis.edition.vo.Edition;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class EditionAction extends ActionSupport implements ServletRequestAware{
	@Autowired
	private EditionService editionService;
	private Edition edition;
	
	

	public Edition getEdition() {
		return edition;
	}

	public void setEdition(Edition edition) {
		this.edition = edition;
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
	
	public void findAllEdition(){

		String isdel = request.getParameter("isdel");
		
		List<Map<String, Object>> result = this.editionService.findAlledition(isdel,page,rows);
		
		
		
		int count=this.editionService.count(isdel);
		Map<String, Object> jsonMap=new HashMap<String, Object>();
		jsonMap.put("rows", result);
		jsonMap.put("total", count);
		JSONObject json = JSONObject.fromObject(jsonMap);
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Charset","utf-8");
		response.setHeader("Cache-Control", "no-cache");
		
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 删除数据
	 */
	public void delete(){
		String id = request.getParameter("id");
		System.out.println(id);
		String result="";
		String [] stringArr= id.split(",");
		for(int i=0;i<stringArr.length;i++){
			result = this.editionService.delete(stringArr[i]);
		}
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	
	
	/**
	 * 保存
	 * @throws Exception
	 */
	public void save() throws Exception{
		
		edition.setAndroidURL(this.execute(file));
		edition.setIosURL(this.execute1(file1));
		
		String result =editionService.save(edition);
		
		
		
		
		
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 根据id查询汽车列表
	 * @return
	 */
	public String findById(){
		String ids = request.getParameter("id");
		
		List<Map<String, Object>> result = this.editionService.findById(ids);
		
		request.setAttribute("edition", result);
		return "success";
		
	}
	
	/**
	 * 修改
	 * @throws Exception 
	 */
	public void update() throws Exception{
//		System.out.println("jinglaile~~~~~~");
//		System.out.println(fileFileName);
//		System.out.println(file1FileName);
//		edition.setAndroidURL(this.execute(file));
//	
//		edition.setIosURL(this.execute1(file1));
		
		
		
		String result = this.editionService.update(edition);
		WriterJsonArray.writerJSONArray(result, response);
		
	}

	private File file;
	private String fileFileName;
	private String fileFileContentType;

	public String execute(File file) throws Exception{
			String root = ServletActionContext.getServletContext().getRealPath("/");
			String dirName ="update";
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
	
	
	private File file1;
	private String file1FileName;
	private String fileFileContentType1;

	

	public String execute1(File file) throws Exception{
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
				String lastName = file1FileName.substring(file1FileName.lastIndexOf("."));
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

	public File getFile1() {
		return file1;
	}

	public void setFile1(File file1) {
		this.file1 = file1;
	}

	public String getFile1FileName() {
		return file1FileName;
	}

	public void setFile1FileName(String file1FileName) {
		this.file1FileName = file1FileName;
	}

	public String getFileFileContentType1() {
		return fileFileContentType1;
	}

	public void setFileFileContentType1(String fileFileContentType1) {
		this.fileFileContentType1 = fileFileContentType1;
	}

	
	
	
	
	
	
	
	

}
