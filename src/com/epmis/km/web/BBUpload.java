package com.epmis.km.web;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.epmis.edition.service.EditionService;
import com.epmis.edition.vo.Edition;
import com.epmis.km.service.KmDocService;
import com.epmis.km.service.KmFolderService;
import com.epmis.km.vo.KmAttch;
import com.epmis.km.vo.KmDoc;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.BaseJdbcService;
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.DateUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;

import java.util.Map;
public class BBUpload extends HttpServlet{

	/**
	 * 文件上传
	 */
	private static final long serialVersionUID = 2384326745121073713L;
	private WebApplicationContext wac;
	
	public void init(){
		wac =WebApplicationContextUtils.getRequiredWebApplicationContext(
		this.getServletContext());
	}
	@Override 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { 
		doPost(request, response); 
	} 
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		EditionService editionService = (EditionService)wac.getBean("EditionService"); 
		KmFolderService kmFolderService = (KmFolderService)wac.getBean("kmFolderService");
		  System.out.println("-----------------UpLoadify-doPost");
		  
		  //不允许的后缀名
		String[] notAllowExtStr = new String[]{"jsp","class","js","html","htm","mht","mhtml","exe","bat","sql","reg","msi","vbs","cmd","scr"}; 
		Arrays.sort(notAllowExtStr); 
		  //int i = Arrays.binarySearch(extStr,"jsp");
		  
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8"); 
		String android = request.getParameter("android");
		String androname = request.getParameter("androname");
		String androidcontent = request.getParameter("androidcontent");
		String ios=request.getParameter("ios");
		String iosname=request.getParameter("iosname");
		String ioscontent=request.getParameter("ioscontent");
		String bbid=request.getParameter("bbid");
		String iosurl=request.getParameter("iosurl");

		System.out.println("android="+android);
		System.out.println("androname="+androname);
		
		
		
		
//		UserInfo userinfo = (UserInfo)request.getSession().getAttribute("UserInfo");
//		String proj_id =  userinfo.getProjId();
//		String user_id =  userinfo.getUserId();
//		String actual_name =  userinfo.getActualName();
	
		ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
	  sfu.setHeaderEncoding("UFT-8");
		try {
			List<?> fileList = sfu.parseRequest(request);
			String sourceName = "";
			String extName = "";
			String name = "";
			String successId="";
			for (int i = 0; i < fileList.size(); i++) {
				System.out.println("-------------------UpLoadify fileList[" + i + "]::::" + fileList.get(i));
				FileItem fi = (FileItem) fileList.get(i);
				if (!fi.isFormField()) {
					sourceName = fi.getName();
					System.out.println("-------------------UpLoadify name::::" + sourceName);
					if (sourceName == null || "".equals(sourceName.trim())) {
						continue;
					}
					if (sourceName.lastIndexOf(".") >= 0) {
						// 扩展名
						name = sourceName.substring(0,sourceName.lastIndexOf("."));  //文件名
						extName = sourceName.substring(sourceName.lastIndexOf(".")+ 1);  //扩展名
						int notAllow = Arrays.binarySearch(notAllowExtStr,extName.toLowerCase());
						if(notAllow >=0){
							System.out.println("-----------不允许上传----"+extName);
							response.getWriter().println("notallow");
							return;
						}					  
						String fileType = getFileType(sourceName);// 文档类型
						String nfile_name ="com_wxj_"+ getTempFileName(extName);
						System.out.println("-----------------UpLoadify extName::::" + extName);
					 
						String basedir = AppSetting.PROJECT_PATH + "/update";
						//String docDir = kmFolderService.getFullPathByFolderId(folder_id);
						String sourcePath = basedir;//+docDir;
						
						System.out.println(sourcePath+"============"+sourcePath+"===============");
						File sourceFolder = new File(sourcePath);
				
						if (!sourceFolder.exists()) {
							//文件夹不存在则创建
							System.out.println("-------------------UpLoadify::::" + "创建文件夹source");
							sourceFolder.mkdirs();
						}
						File saveSourceFile = new File(sourcePath +"/" + nfile_name);
						fi.write(saveSourceFile);
						System.out.println(saveSourceFile);
						String a=saveSourceFile.getAbsolutePath();
						
						String [] stringArr= a.split(".");
						//String b = stringArr[stringArr.length-1];
						System.out.println(stringArr.length);
						System.out.println("========"+a+"===b");
//							Carpic pic=new Carpic();
//							pic.setCid(Integer.parseInt(cid));
//							pic.setColorid(Integer.parseInt(color_id));
//							pic.setPic("/upload/"+nfile_name);
//							pic.setStatus(Integer.parseInt(status));
//							pic.setIsdel(Integer.parseInt(isdel));
							Edition edition=new Edition();
							edition.setAndroid(android);
							edition.setAndroname(androname);
							edition.setAndroidcontent(androidcontent);
							edition.setAndroidURL("/update/"+nfile_name);
							edition.setIos(ios);
							edition.setIosname(iosname);
							edition.setIoscontent(ioscontent);
							edition.setId(Integer.parseInt(bbid));
							edition.setIosURL(iosurl);
							
						
						//插入文档
							System.out.println("-------------------UpLoadify::::" + "插入KM_DOC开始");
							
							String result="";
								if("0".equals(bbid)){
									 result=editionService.save(edition);
								}else{
								      result=editionService.update(edition);
								}
								
//								JSONObject json = new JSONObject();
//								json.put("result", result);
								
								
	
								WriterJsonArray.writerJSONArray(result, response);
								
								System.out.println(result+"-----------------=======");
//								editionService.save(pic);
								//successId = DataTypeUtil.validate(successId)? successId+","+doc_id : doc_id;
							
							System.out.println("-------------------UpLoadify::::" + "插入KM_DOC完成");
						
						System.out.println("-------------------UpLoadify fileSourceName::::" + sourceName);
					}
				}
			}
			response.setContentType ("text/plain;charset=utf-8");
			
		} catch (FileUploadException e) {
			response.getWriter().println("0");
			e.printStackTrace();
		} catch (Exception e) {
			response.getWriter().println("0");
			e.printStackTrace();
		}
	}
	
	public static String getFileType(String fileName){
		String fileExtName = "";
		String fileType = "";
		if (!fileName.equals("") && fileName.indexOf(".") >= 0)
		{
			fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (fileExtName.equalsIgnoreCase("doc") || fileExtName.equalsIgnoreCase("docx"))
				fileType = "Word";
			else if (fileExtName.equalsIgnoreCase("xls"))
				fileType = "Excel";
			else if (fileExtName.equalsIgnoreCase("ppt"))
				fileType = "PowerPoint";
			else if (fileExtName.equalsIgnoreCase("txt"))
				fileType = "文本文件";
			else if (fileExtName.equalsIgnoreCase("dwg"))
				fileType = "AutoCAD";
			else if (fileExtName.equalsIgnoreCase("gif") || fileExtName.equalsIgnoreCase("jpg") || fileExtName.equalsIgnoreCase("png") || fileExtName.equalsIgnoreCase("bmp"))
				fileType = "图片";
			else if (fileExtName.equalsIgnoreCase("pdf"))
				fileType = "PDF文件";
			else if (fileExtName.equalsIgnoreCase("pdf"))
				fileType = "PDF文件";
			else if (fileExtName.equalsIgnoreCase("zip") || fileExtName.equalsIgnoreCase("rar"))
				fileType = "压缩包文件";
			else
				fileType = "其他";

		}
		return fileType;
	}
	
	public static String getTempFileName(String fileExtName){
		int ran = (int) Math.floor(Math.random() * 1000);
		String fileName = "";
		if (fileExtName.equals(""))
			fileName = DateUtil.getTodaytime().replace("-", "").replace(" ", "").replace(":", "") + ran;
		else
			fileName = DateUtil.getTodaytime().replace("-", "").replace(" ", "").replace(":", "") + ran + "." + fileExtName;
		return fileName;
	}
	 
}

