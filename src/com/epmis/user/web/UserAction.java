package com.epmis.user.web;

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

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DateUtil;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.user.service.UserService;
import com.epmis.user.vo.User;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class UserAction extends ActionSupport implements ServletRequestAware{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	
	@Autowired
	private UserService userService;
	
	private User user;
	
	

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	 * 分页查询促销
	 */
	public void findAllUser(){
		String title = request.getParameter("title");		
		String isdel = request.getParameter("isdel");	
		String uid=request.getParameter("uid");
		String type=request.getParameter("type");
		String neibu=request.getParameter("neibu");
		String uuid=request.getParameter("uuid");
		String isrenzhen=request.getParameter("isrenzhen");
		String rz=request.getParameter("rz");
		String realname=request.getParameter("realname");
		
		
		List<Map<String, Object>> result = this.userService.findAllUser(title,isdel,uid,type,neibu,uuid,isrenzhen,rz,realname,page,rows);
		int count=this.userService.count(title,isdel,uid,type,neibu,uuid,isrenzhen,rz,realname);
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
			result = this.userService.delete(stringArr[i]);
		}
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 根据id查询
	 */
	public String findById(){
		String ids = request.getParameter("id");
		List<Map<String, Object>> result = this.userService.findById(ids);
		request.setAttribute("user", result);	
		return "success";
		
	}
	
	
	/**
	 * 获取单个用户姓名认证信息
	 */
	public String findByIdName(){
		String ids = request.getParameter("id");
		List<Map<String, Object>> result = this.userService.findById(ids);
		request.setAttribute("user", result);	
		return "success";
		
	}
	
	
	/**
	 * 获取单个用户身份证号信息
	 */
	public String findByIdYhkh(){
		String ids = request.getParameter("id");
		List<Map<String, Object>> result = this.userService.findById(ids);
		request.setAttribute("user", result);
		return "success";
		
	}
	
	
	
	/**
	 * 计算今天注册的人数
	 * @return
	 */
	public String findCount(){
		int count=this.userService.count();
		int counts=this.userService.counts();
		request.setAttribute("count", count);
		request.setAttribute("counts", counts);
		return "success";
	}
	
	
	/**
	 * 姓名认证
	 */
	public void updateName(){
		
		String result = this.userService.updateName(user);
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 银行卡号认证
	 */
	public void updateYhkh(){
		
		String result = this.userService.updateYhkh(user);
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 设置金牌顾问的内外部及工单
	 */
	public void gw(){
		String job_yname=request.getParameter("job_yname");
		String result = this.userService.gw(user,job_yname);
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	public void save() throws Exception{		
		user.setIdPiczheng(this.execute(file));
		user.setIdPicfan(this.executexiao(file1));
		user.setShouchiId(this.executedi(file3));
		
		String result=this.userService.save(user);		
		//获取新增数据的id
		String sql="select * from t_user where telephone='"+user.getTelephone()+"'";
		List<Map<String, Object>> result2 =this.baseJdbcDao.queryListMap(sql);
		Object o=result2.get(0).get("id");
		//获取时间毫秒数
		int ran = (int) Math.floor(Math.random() * 1000);
		String fileName = DateUtil.getTodaytime().replace("-", "").replace(" ", "").replace(":", "") + ran;
		//对该新增的金牌顾问token属性定义
		String sqlu="update t_user set token='"+o+"|"+fileName+"' where id='"+o+"'";
		
		this.baseJdbcDao.update(sqlu);		
		WriterJsonArray.writerJSONArray(result, response);
		
	}
	
	
	/**
	 * 获取金牌顾问的认证信息
	 */
	public String findByIdrz(){
		String ids = request.getParameter("id");
		List<Map<String, Object>> result = this.userService.findByIdrz(ids);
		request.setAttribute("user", result);
		return "success";
		
	}
	
	/**
	 * 金牌顾问认证
	 */
	public void updaterz(){		
		String result = this.userService.updaterz(user);
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	
	private String phone;
	private String name;
	public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	public void showNewUsers(){
			List<Map<String,Object>> result = this.userService.selectNewUsers(page, rows, phone, name);
			int count = this.userService.getNewUsersCount(phone, name);
			Map<String,Object> jsonMap = new HashMap<String,Object>();
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
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//视频第一幁图片
		private File file3;
		private String file3FileName;
		private String fileFileContentType3;
		


		public File getFile3() {
			return file3;
		}

		public void setFile3(File file3) {
			this.file3 = file3;
		}

		

		public String getFile3FileName() {
			return file3FileName;
		}

		public void setFile3FileName(String file3FileName) {
			this.file3FileName = file3FileName;
		}

		public String getFileFileContentType3() {
			return fileFileContentType3;
		}

		public void setFileFileContentType3(String fileFileContentType3) {
			this.fileFileContentType3 = fileFileContentType3;
		}
		
		//视频上传
		private File file2;
		private String fileFileName2;
		private String fileFileContentType2;
		
		
	public File getFile2() {
			return file2;
		}

		public void setFile2(File file2) {
			this.file2 = file2;
		}

		public String getFileFileName2() {
			return fileFileName2;
		}

		public void setFileFileName2(String fileFileName2) {
			this.fileFileName2 = fileFileName2;
		}

		public String getFileFileContentType2() {
			return fileFileContentType2;
		}

		public void setFileFileContentType2(String fileFileContentType2) {
			this.fileFileContentType2 = fileFileContentType2;
		}

	private File file;
	private File file1;
	private String fileFileName1;
	private String fileFileContentType1;

	public String getFileFileName1() {
		return fileFileName1;
	}

	public void setFileFileName1(String fileFileName1) {
		this.fileFileName1 = fileFileName1;
	}

	public String getFileFileContentType1() {
		return fileFileContentType1;
	}

	public void setFileFileContentType1(String fileFileContentType1) {
		this.fileFileContentType1 = fileFileContentType1;
	}

	private String fileFileName;
	private String file1FileName;
	private String file2FileName;
	private String fileFileContentType;


	public String getFile1FileName() {
		return file1FileName;
	}

	public void setFile1FileName(String file1FileName) {
		this.file1FileName = file1FileName;
	}

	public String getFile2FileName() {
		return file2FileName;
	}

	public void setFile2FileName(String file2FileName) {
		this.file2FileName = file2FileName;
	}

	//图片上传
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
				System.out.println("fileFileName="+fileFileName);
				System.out.println("fileFileName1="+fileFileName1);
				System.out.println("file1FileName="+file1FileName);
				System.out.println("fileFileName3="+file3FileName);
				String lastName = fileFileName.substring(fileFileName.lastIndexOf("."));
				System.out.println("lastName="+lastName);
				fileFileName = uu+""+lastName;
				System.out.println("fileFileName="+fileFileName);
	        	String filePath = dirPath+"/"+fileFileName;
	        	System.out.println("filePath="+filePath);
	 	        OutputStream os = new FileOutputStream(filePath);
	 	        System.out.println("os="+os);
		        String src="/"+dirName+"/"+fileFileName;
		        System.out.println("src"+src);
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

	public String executexiao(File file) throws Exception{
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
			System.out.println("lastName="+lastName);
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


	public String executedi(File file) throws Exception{
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
			
			String lastName = file3FileName.substring(file3FileName.lastIndexOf("."));
			System.out.println("lastName="+lastName);
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



	//视频上传
	public String executeTwo(File file) throws Exception{
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
			String lastName = file2FileName.substring(file2FileName.lastIndexOf("."));
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
