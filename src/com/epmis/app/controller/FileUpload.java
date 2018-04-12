package com.epmis.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.Base64Encrypt;
import com.epmis.sys.util.DateUtil;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;

@Controller
public class FileUpload {
	 /**
     * 客户端上传文件
     * @param request
     * @param response
     * @throws IOException
     */
    @ResponseBody
	@RequestMapping(value = "/api/fileUpload/upload.do")
	public void appupload(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	Map<String, Object> resObj = new HashMap<String, Object>();
    	//不允许的后缀名
    	String[] notAllowExtStr = new String[]{"jsp","class","js","html","htm","mht","mhtml","exe","bat","sql","reg","msi","vbs","cmd","scr"}; 
    	Arrays.sort(notAllowExtStr); 
    	ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
    	String fileIds = "";
		try {
			
			List<?> fileList = sfu.parseRequest(request);
			String sourceName = "";
			String extName = "";
			
			for (int i = 0; i < fileList.size(); i++) {
				FileItem fi = (FileItem) fileList.get(i);
				if (!fi.isFormField()) {
					sourceName = fi.getName();
					if (sourceName == null || "".equals(sourceName.trim())) {
						continue;
					}
					if (sourceName.lastIndexOf(".") >= 0) { 
						//扩展名
						//name = sourceName.substring(0,sourceName.lastIndexOf("."));  //文件名
						extName = sourceName.substring(sourceName.lastIndexOf(".")+ 1);  //扩展名
						int notAllow = Arrays.binarySearch(notAllowExtStr,extName.toLowerCase());
						if(notAllow >=0){
							System.out.println("-----------不允许上传----"+extName);
							response.getWriter().println("notallow");
							return;
						}
  
						String nfile_name = getTempFileName(extName);
 
						String basedir = AppSetting.PROJECT_PATH + "/KM/app/";
						File sourceFolder = new File(basedir);

						if (!sourceFolder.exists()) {
							//文件夹不存在则创建
							sourceFolder.mkdirs();
						}
						File saveSourceFile = new File(basedir +"/" + nfile_name);
						fi.write(saveSourceFile);
						fileIds = nfile_name;
					}
				}
			}
			//response.setContentType ("text/plain;charset=utf-8");
			//response.getWriter().append(fileIds);
			resObj.put("RESULT", "1");
			resObj.put("FILENAME", fileIds);
			resObj.put("FILEPATH", "/KM/app/"+fileIds);
		}catch(Exception e){
			e.printStackTrace();
			resObj.put("RESULT", "0");
		}
		
		WriterJsonArray.AppWriterJSONArray(resObj,response);
    }
    /**
     * 上传文件
     * @param request
     * @param response
     * @throws IOException
     */
    @ResponseBody
	@RequestMapping(value = "/file/fileUpload/upload.do")
	public void upload(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	//不允许的后缀名
    	String[] notAllowExtStr = new String[]{"jsp","class","js","html","htm","mht","mhtml","exe","bat","sql","reg","msi","vbs","cmd","scr"}; 
    	Arrays.sort(notAllowExtStr); 
    	ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
    	String fileIds = "";
		try {
			List<?> fileList = sfu.parseRequest(request);
			String sourceName = "";
			String extName = "";
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
			String userId = userInfo.getUserId();
			for (int i = 0; i < fileList.size(); i++) {
				FileItem fi = (FileItem) fileList.get(i);
				if (!fi.isFormField()) {
					sourceName = fi.getName();
					if (sourceName == null || "".equals(sourceName.trim())) {
						continue;
					}
					if (sourceName.lastIndexOf(".") >= 0) { 
						//扩展名
						//name = sourceName.substring(0,sourceName.lastIndexOf("."));  //文件名
						extName = sourceName.substring(sourceName.lastIndexOf(".")+ 1);  //扩展名
						int notAllow = Arrays.binarySearch(notAllowExtStr,extName.toLowerCase());
						if(notAllow >=0){
							System.out.println("-----------不允许上传----"+extName);
							response.getWriter().println("notallow");
							return;
						}
  
						String nfile_name = getTempFileName(extName);
 
						String basedir = AppSetting.PROJECT_PATH + "/KM/temp/"+userId;
						File sourceFolder = new File(basedir);

						if (!sourceFolder.exists()) {
							//文件夹不存在则创建
							sourceFolder.mkdirs();
						}
						File saveSourceFile = new File(basedir +"/" + nfile_name);
						fi.write(saveSourceFile);
						fileIds = nfile_name;
						//fileIds += DataTypeUtil.validate(fileIds)?","+nfile_name.substring(0,nfile_name.lastIndexOf(".")):nfile_name.substring(0,nfile_name.lastIndexOf("."));
					}
				}
			}
			response.setContentType ("text/plain;charset=utf-8");
			response.getWriter().append(fileIds);
			//String amwId = request.getParameter("amwId");
			//String dailyId = request.getParameter("dailyId");
		}catch(Exception e){
			e.printStackTrace();
		}
    }
    
	public static String getFileType(String fileName){
		String fileExtName = "";
		String fileType = "";
		if (!fileName.equals("") && fileName.indexOf(".") >= 0){
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
		else if (fileExtName.equalsIgnoreCase("swf"))
			fileType = "FLASH文件";
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
