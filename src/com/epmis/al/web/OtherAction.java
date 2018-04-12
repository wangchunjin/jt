package com.epmis.al.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.epmis.al.service.OtherService;
import com.epmis.al.vo.Other;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")
public class OtherAction extends ActionSupport implements ServletRequestAware{

	@Autowired
	private OtherService otherService;
	private Other other;
	HttpServletRequest request;
	private int page;
	private int rows;

	private int hid;
	private int rid;
	private int sid;
	
	
	
	public int getSid() {
		return sid;
	}



	public void setSid(int sid) {
		this.sid = sid;
	}



	public int getRid() {
		return rid;
	}



	public void setRid(int rid) {
		this.rid = rid;
	}



	public int getHid() {
		return hid;
	}



	public void setHid(int hid) {
		this.hid = hid;
	}



	public Other getOther() {
		return other;
	}



	public void setOther(Other other) {
		this.other = other;
	}



	public HttpServletRequest getRequest() {
		return request;
	}



	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}



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



	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request  = request;
	}
	
	HttpServletResponse response = ServletActionContext.getResponse();
	
	private String hyly;
	private String rzpc;
	private String szdq;


	
	
	public String getHyly() {
		return hyly;
	}



	public void setHyly(String hyly) {
		this.hyly = hyly;
	}



	public String getRzpc() {
		return rzpc;
	}



	public void setRzpc(String rzpc) {
		this.rzpc = rzpc;
	}



	public String getSzdq() {
		return szdq;
	}



	public void setSzdq(String szdq) {
		this.szdq = szdq;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}

	private String creattime;
	
	
	public String getCreattime() {
		return creattime;
	}



	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}

	private String content;
	
	


	public void showOther(){
		List<Map<String, Object>> result = otherService.showOther(page, rows, creattime,hyly,rzpc,szdq,hid,rid,sid);
		int count = otherService.getOtherCount();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("rows", result);
		jsonMap.put("total",count);
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
	
	public void addOther() throws IOException{
		other.setImg(this.execute(file1, file1FileName));
		String result = otherService.addOther(other,content);
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	public void delOther(){
		String id = request.getParameter("id");
		String result = otherService.delOther(id);
		WriterJsonArray.writerJSONArray(result, response);
	}
	public void updateOther() throws IOException{
		other.setImg(this.execute(file1, file1FileName));
		String result = otherService.updateOther(other,content);
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	public void SelOther(){
		String result = otherService.updateOther(other,content);
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	public String OtherById(){
		String id =request.getParameter("id");
		Map<String,Object> mapResult = otherService.OtherById(id);
		request.setAttribute("other", mapResult);
		return "success";
	}
	
	private String img;
	
	
	
	
	public String getImg() {
		return img;
	}



	public void setImg(String img) {
		this.img = img;
	}
	
	private File file1;
	private String file1ContentType;
	private String file1FileName;
	
	


	public File getFile1() {
		return file1;
	}



	public void setFile1(File file1) {
		this.file1 = file1;
	}



	public String getFile1ContentType() {
		return file1ContentType;
	}



	public void setFile1ContentType(String file1ContentType) {
		this.file1ContentType = file1ContentType;
	}



	public String getFile1FileName() {
		return file1FileName;
	}



	public void setFile1FileName(String file1FileName) {
		this.file1FileName = file1FileName;
	}

	public String execute(File file,String fileFileName ) throws IOException
    {
		String root = ServletActionContext.getServletContext().getRealPath("/");
		String dirName ="upload";
		String dirPath = root+dirName;
		//拼接文件夹
   	 	File dirFile = new File(dirPath);
			if(!dirFile.exists()){
				dirFile.mkdir();	//创建文件夹
			}
        if(file!=null&&!"".equals(file)){
	    	//中文乱码处理
			String uu = UUID.randomUUID().toString().replace("-", "");
			String lastName = fileFileName.substring(fileFileName.lastIndexOf("."));
			fileFileName = uu+""+lastName;
	        InputStream is = new FileInputStream(file);
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
	

	
	
	public String showHylyName(){
		List<Map<String, Object>> list = otherService.showHylyName();
		List<Map<String, Object>> list1 = otherService.showRzPcName();
		List<Map<String, Object>> list2 = otherService.showSzDqName();
		request.setAttribute("list", list);
		request.setAttribute("list1", list1);
		request.setAttribute("list2", list2);
		return "success";
	}
}
