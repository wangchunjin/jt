package com.epmis.km.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.epmis.km.service.KmDocService;
import com.epmis.km.vo.KmDoc;
import com.epmis.km.vo.KmFolder;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray; 
import com.epmis.webService.service.IDocUpdateService;
import com.epmis.webService.util.DocObj;
import com.epmis.webService.util.LicenseObj;
import com.epmis.webService.web.LicenseWeb;
import com.epmis.webService.web.UpdatePublicDoc;
import com.opensymphony.xwork2.ActionSupport;


public class KmDocAction extends ActionSupport implements ServletRequestAware{

	@Autowired
    private KmDocService kmDocService;	
	private String parentId; 
	private KmFolder kmFolder;
	private String folderId;
	private String IsAll;
	private String docId;
	private KmDoc kmDoc;
	private String fileName;
	private String fileType;
	
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public KmDoc getKmDoc() {
		return kmDoc;
	}
	public void setKmDoc(KmDoc kmDoc) {
		this.kmDoc = kmDoc;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getIsAll() {
		return IsAll;
	}
	public void setIsAll(String isAll) {
		IsAll = isAll;
	}
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
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
	 private File uploadfile;
	 
	public File getUploadfile() {
		return uploadfile;
	}
	public void setUploadfile(File uploadfile) {
		this.uploadfile = uploadfile;
	}
	HttpServletRequest request  ;
	 @Override
	 public void setServletRequest(HttpServletRequest request) {
	 	this.request=request;
	 	
	 }
	 HttpServletResponse response = ServletActionContext.getResponse();
    
	public void ShowDocTree()
	  {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		List list = kmDocService.ShowDocTree(this.parentId,this.docGrade,userInfo);
		WriterJsonArray.writerJSONArray(list,response);
	  }
		public void ShowNewDocTree()
	  {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo"); 
		List list = kmDocService.ShowNewDocTree(this.parentId,userInfo);
		WriterJsonArray.writerJSONArray(list,response);
	  }
	public void ShowDocTable()
	  {
		String keyWord = request.getParameter("keyWord");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		List list = new ArrayList();
		if(DataTypeUtil.validate(keyWord)){
			  list = kmDocService.ShowDocTable(this.folderId,this.docGrade,"1",keyWord,userInfo);
		}else{
			  list = kmDocService.ShowDocTable(this.folderId,this.docGrade,this.IsAll,userInfo);
		}

		WriterJsonArray.writerJSONArray(list,response);
	  }
		public void ShowNewDocTable()
	  {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		List list = kmDocService.ShowNewDocTable(this.folderId,userInfo);
		WriterJsonArray.writerJSONArray(list,response);
	  }
		
	  @Autowired
	  private BaseJdbcDao baseJdbcDao;
	  
	  public String viewDoc()
	  {
		String type = request.getParameter("type");
		String url = kmDocService.GetUrlById(this.docId,type);
		ResourceBundle rb=ResourceBundle.getBundle("system"); 
		String docSee = DataTypeUtil.formatDbColumn(rb.getString("DOC-SEE")); 
		if(url.equals("error")){
			try{ 
	    		if(LicenseWeb.TestConnect(UpdatePublicDoc.getMACAddress()).equals("Y")&&docSee.equals("Y")){  
	    			String serviceId ="";
	    			String public_loc="";
	    			if(!DataTypeUtil.validate(type)){
	    		    	 serviceId = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select service_id from km_doc where doc_id = '"+this.docId+"' and doc_grade = '0' "));
	    			}else{
	    				 serviceId = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select service_id from km_doc where doc_id = '"+type+"' and doc_grade = '0' "));
			    		 public_loc = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select max(public_loc) public_loc from km_attch where doc_id = '"+type+"' and attch_id = '"+this.docId+"' "));
	    			}	
	    				    			 
	    			if(DataTypeUtil.validate(serviceId)){		    		
		    			if(DataTypeUtil.validate(type)){
		    				if(DataTypeUtil.validate(public_loc)){
		    		 		  url =    UpdatePublicDoc.service.GetFileUrlById(serviceId, public_loc, "");
		    				}
		    			}else{
		    				url =    UpdatePublicDoc.service.GetFileUrlById(serviceId,"","");
		    			}
		    			if(!url.equals("error")){
			    		 	url= url.substring(url.indexOf("/KM"));		    			 
			    			String address = rb.getString("CLOUD-SERVICES"); 
			    			url = address+url;
		    			}
	    			}else{
	    				return "error";
	    			}
	    		}else{
			    	return "error";
	    		}
			}catch(Exception e){
				e.printStackTrace();
				return "error";
			}
		}			
		if(url.equals("error")){			 
			return "error";
		}else if(url.toLowerCase().endsWith(".doc") || url.toLowerCase().endsWith(".docx") || url.toLowerCase().endsWith(".xls") || url.toLowerCase().endsWith(".xlsx")|| url.toLowerCase().endsWith(".mpp")|| url.toLowerCase().endsWith(".ppt")|| url.toLowerCase().endsWith(".pptx")|| url.toLowerCase().endsWith(".vsd")){
			request.setAttribute("url",url);
			return "office";
		}else if(url.toLowerCase().endsWith(".png") || url.toLowerCase().endsWith(".gif") || url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".bmp")){
			request.setAttribute("url",url);
			return "pic";
		}else if(url.toLowerCase().endsWith(".tif") || url.toLowerCase().endsWith(".pdf")){
			request.setAttribute("url",url);
			return "pdf";
		}else{
			request.setAttribute("url",url);
			return "other";
		}
		
	  }
	  public  void saveDoc(){ 
	     String url = request.getParameter("url");
		 String  result = kmDocService.saveDoc(this.uploadfile,url);
		 WriterJsonArray.writerJSONArray(result,response);
	  }
	  public void downDoc(){
		  String type = request.getParameter("type");
		  UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		  String userId = userInfo.getUserId();
		  String url = kmDocService.GetActualNameUrlById(this.docId,type,userId); 
		  ResourceBundle rb=ResourceBundle.getBundle("system");  
		  String docSee = DataTypeUtil.formatDbColumn(rb.getString("DOC-SEE")); 
		  if(url.equals("error")){
			  try{ 
		    		if(LicenseWeb.TestConnect(UpdatePublicDoc.getMACAddress()).equals("Y")&&docSee.equals("Y")){  
		    			String serviceId ="";
		    			String public_loc="";
		    			if(!DataTypeUtil.validate(type)){
		    		    	 serviceId = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select service_id from km_doc where doc_id = '"+this.docId+"' and doc_grade = '0' "));
		    			}else{
		    				 serviceId = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select service_id from km_doc where doc_id = '"+type+"' and doc_grade = '0' "));
				    		 public_loc = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select max(public_loc) public_loc from km_attch where doc_id = '"+type+"' and attch_id = '"+this.docId+"' "));
		    			}		    			
		    			if(DataTypeUtil.validate(serviceId)){  //&&DataTypeUtil.validate(public_loc)
			    			if(DataTypeUtil.validate(type)){
			    				if(DataTypeUtil.validate(public_loc)){
			    		 		  url =    UpdatePublicDoc.service.DownFileUrlById(serviceId, public_loc, "");
			    				}
			    			}else{
			    				url =    UpdatePublicDoc.service.DownFileUrlById(serviceId,"","");
			    			}
			    			 if(!url.equals("error")){
				    		 	url= url.substring(url.indexOf("/KM"));
				    			String address = rb.getString("CLOUD-SERVICES"); 
				    			url = address+url;
			    			 }
		    			}else{
		    				url =  "error";
		    			}
		    		}else{
		    			url =  "error";
		    		}
				}catch(Exception e){
					e.printStackTrace();
					url =  "error";
				}
		  }
		  WriterJsonArray.writerJSONArray(url,response);		  
	  }
	  public void publicDoc(){
		  UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		  String userId = userInfo.getUserId();
		  String docIds = request.getParameter("docIds");
		  String status = request.getParameter("status");
		  String url = kmDocService.publicDoc(docIds,status,userId); 
		  WriterJsonArray.writerJSONArray(url,response);
		  
	  }
	  public void downDocVersion(){
		  String docver_id = request.getParameter("docver_id");
		  String url = kmDocService.GetUrlByVersionId(this.docId,docver_id);
		  ResourceBundle rb=ResourceBundle.getBundle("system");  
		  String docSee = DataTypeUtil.formatDbColumn(rb.getString("DOC-SEE")); 
		  if(url.equals("error")){
			  try{ 
		    		if(LicenseWeb.TestConnect(UpdatePublicDoc.getMACAddress()).equals("Y")&&docSee.equals("Y")){  
		    			String serviceId = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select service_id from km_doc where doc_id = '"+this.docId+"' and doc_grade = '0' "));
		    			String file_name = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select max(file_name) file_name from km_docver where doc_id = '"+this.docId+"' and docver_id = '"+docver_id+"' "));
		    			if(DataTypeUtil.validate(serviceId)&&DataTypeUtil.validate(file_name)){
			    	     	url =    UpdatePublicDoc.service.DownFileUrlById(serviceId, "", file_name);
			    	     	if(!url.equals("error")){
				    		 	url= url.substring(url.indexOf("/KM"));
				    			String address = rb.getString("CLOUD-SERVICES"); 
				    			url = address+url;
			    	     	}
		    			}else{
		    				url =  "error";
		    			}
		    		}else{
		    			url =  "error";
		    		}
				}catch(Exception e){
					e.printStackTrace();
					url =  "error";
				}
		  }
		  WriterJsonArray.writerJSONArray(url,response);
		  
	  }
		public void delDoc(){
			 String type = request.getParameter("type");
		    String result = kmDocService.delDoc(this.docId,type);
		    WriterJsonArray.writerJSONArray(result,response);
		}
		public void MoveDoc(){
			 String docIds = request.getParameter("docIds");
			 String folderId = request.getParameter("folderId");
			 String docGrade = request.getParameter("docGrade");
			  UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
			  String projId = userInfo.getProjId();
		    String result = kmDocService.MoveDoc(docIds,folderId,docGrade,projId);
		    WriterJsonArray.writerJSONArray(result,response);
		}
		public String GetDocInfo(){
			String doc_Id = request.getParameter("DOC_ID") ;
			Map<String, Object> result = kmDocService.getDocInfoByDocId(doc_Id);
		    request.setAttribute("DocInfo", result);  
		    return "success"; 
		}
		public void UpdateDocNormal(){
		    String result = kmDocService.UpdateDocNormal(this.kmDoc);
		    WriterJsonArray.writerJSONArray(result,response);
		}
		public void UpdateDocOther(){
		    String result = kmDocService.UpdateDocOther(this.kmDoc);
		    WriterJsonArray.writerJSONArray(result,response);
		}	
		public void DocVersionTable(){
		    List result = kmDocService.DocVersionTable(this.docId);
		    WriterJsonArray.writerJSONArray(result,response);
		}      
		public void DocLogTable(){
		    List result = kmDocService.DocLogTable(this.docId);
		    WriterJsonArray.writerJSONArray(result,response);
		}      
		public void DocAttchTable(){
		    List result = kmDocService.DocAttchTable(this.docId);
		    WriterJsonArray.writerJSONArray(result,response);
		}   
		public void PlanDocTree()
		  {
			String id = request.getParameter("id");
			String base_link_id = request.getParameter("base_link_id");
			String table_name = request.getParameter("table_name");
			String type= request.getParameter("type");
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
			List list = kmDocService.PlanDocTree(id,table_name,base_link_id,userInfo.getUserId(),userInfo.getProjId(),type);
			WriterJsonArray.writerJSONArray(list,response);
		  }
			public void ShowRecycleTable()
		  {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
			String doc_grade = request.getParameter("DOC_GRADE");
			List list = kmDocService.ShowRecycleTable(doc_grade,userInfo);
			WriterJsonArray.writerJSONArray(list,response);
		  }
			public void ClearDocs(){
				String doc_grade = request.getParameter("DOC_GRADE");
				String doc_ids = request.getParameter("DOC_IDS");
				String result = kmDocService.ClearDocs(doc_ids,doc_grade);
			    WriterJsonArray.writerJSONArray(result,response);
			}
			public void RevertDocs(){
				String doc_grade = request.getParameter("DOC_GRADE");
				String doc_ids = request.getParameter("DOC_IDS");
				String result = kmDocService.RevertDocs(doc_ids,doc_grade);
			    WriterJsonArray.writerJSONArray(result,response);
			}
			public void ShowSearchTable(){
				String keywords =  request.getParameter("keywords");
				String created_date1 = request.getParameter("created_date1");
				String created_date2 = request.getParameter("created_date2");
				String edited_date1 = request.getParameter("edited_date1");
				String edited_date2 = request.getParameter("edited_date2");
				String created_by = request.getParameter("created_by");
				String assigned_to = request.getParameter("assigned_to");
				String approved_by = request.getParameter("approved_by");
				String edited_by = request.getParameter("edited_by");
				String author = request.getParameter("author");
				String keywordsType = request.getParameter("keywordsType");
				String doc_type = request.getParameter("doc_type");
				String proj_id = request.getParameter("proj_id");             
				UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
				String user_id = userInfo.getUserId();
				List result = kmDocService.ShowSearchTable(user_id,keywords,created_date1,created_date2,edited_date1,edited_date2,created_by,assigned_to,approved_by,edited_by,
						author,keywordsType,doc_type,proj_id);
			    WriterJsonArray.writerJSONArray(result,response);
			}
			public void downPackageDoc(){
				UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
				String url = kmDocService.downPackageDoc(this.docGrade,this.folderId,userInfo);
				Map map  = new HashMap();
				map.put("result", "success");
				map.put("url", url);
				WriterJsonArray.writerJSONArray(map,response);
			}
			public void PlugDown(){
				List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>(); 
					Map<String, Object> mapInfo = new HashMap<String, Object>(); 
					mapInfo.put("WID", Guid.getGuid());
					mapInfo.put("NAME", "NTKO Office文档控件(chrome)");
					mapInfo.put("SIZE", "136KB");
					mapInfo.put("REMARK", "文档控件");
					mapInfo.put("LINK", "<a href='"+AppSetting.PROJECT_NAME+"/webResources/ntko/ntkoplugins.crx' style=' border-bottom:1px blue solid;'>点击这里或鼠标右键另存下载");
					listMap.add(mapInfo);
					
					Map<String, Object> mapInfo4 = new HashMap<String, Object>();
					mapInfo4.put("WID", Guid.getGuid());
					mapInfo4.put("NAME", "NTKO Office文档控件(ie)");
					mapInfo4.put("SIZE", "1.19 MB");
					mapInfo4.put("REMARK", "文档控件");
					mapInfo4.put("LINK", "<a href='"+AppSetting.PROJECT_NAME+"/webResources/ntko/ofctnewclsid.cab' style=' border-bottom:1px blue solid;'>点击这里或鼠标右键另存下载");
					listMap.add(mapInfo4);
					
					Map<String, Object> mapInfo5 = new HashMap<String, Object>();
					mapInfo5.put("WID", Guid.getGuid());
					mapInfo5.put("NAME", "NTKO Office文档控件(firefox)");
					mapInfo5.put("SIZE", "135 KB");
					mapInfo5.put("REMARK", "文档控件");
					mapInfo5.put("LINK", "<a href='"+AppSetting.PROJECT_NAME+"/webResources/ntko/ntkoplugins.xpi' style=' border-bottom:1px blue solid;'>点击这里或鼠标右键另存下载");
					listMap.add(mapInfo5);
					
					Map<String, Object> mapInfo2 = new HashMap<String, Object>();
					mapInfo2.put("WID", Guid.getGuid());
					mapInfo2.put("NAME", "chrome浏览器");
					mapInfo2.put("SIZE", "49.3 MB");
					mapInfo2.put("REMARK", "浏览器");
					mapInfo2.put("LINK", "<a href='http://www.gpmis.com:12345/plugin/chrome.rar' style=' border-bottom:1px blue solid;'>点击这里或鼠标右键另存下载");
					listMap.add(mapInfo2);
					
					Map<String, Object> mapInfo3 = new HashMap<String, Object>();
					mapInfo3.put("WID", Guid.getGuid());
					mapInfo3.put("NAME", "NTKO Pdf文档控件");
					mapInfo3.put("SIZE", "4.28 MB");
					mapInfo3.put("REMARK", "Pdf控件");
					mapInfo3.put("LINK", "<a href='"+AppSetting.PROJECT_NAME+"/webResources/ntko/ntkooledocall.cab' style=' border-bottom:1px blue solid;'>点击这里或鼠标右键另存下载");
					listMap.add(mapInfo3);

					Map<String, Object> mapInfo6 = new HashMap<String, Object>();
					mapInfo6.put("WID", Guid.getGuid());
					mapInfo6.put("NAME", "NTKO控件手动安装工具");
					mapInfo6.put("SIZE", "110 KB");
					mapInfo6.put("REMARK", "手动安装工具");
					mapInfo6.put("LINK", "<a href='"+AppSetting.PROJECT_NAME+"/webResources/ntko/Register and uninstal.exe' style=' border-bottom:1px blue solid;'>点击这里或鼠标右键另存下载");
					listMap.add(mapInfo6);
					
					Map<String, Object> mapInfo7 = new HashMap<String, Object>();
					mapInfo7.put("WID", Guid.getGuid());
					mapInfo7.put("NAME", "Chrome 视频查看插件");
					mapInfo7.put("SIZE", "116 KB");
					mapInfo7.put("REMARK", "视频控件");
					mapInfo7.put("LINK", "<a href='"+AppSetting.PROJECT_NAME+"/webResources/windowsMediaPlayer/wmpChrome.crx' style=' border-bottom:1px blue solid;'>点击这里或鼠标右键另存下载");
					listMap.add(mapInfo7);
					
					Map<String, Object> mapInfo8 = new HashMap<String, Object>();
					mapInfo8.put("WID", Guid.getGuid());
					mapInfo8.put("NAME", "视频解码");
					mapInfo8.put("SIZE", "116 KB");
					mapInfo8.put("REMARK", "完美解码");
					mapInfo8.put("LINK", "<a href='http://www.gpmis.com:12345/plugin/Decoding.exe' style=' border-bottom:1px blue solid;'>点击这里或鼠标右键另存下载");
					listMap.add(mapInfo8);
					
				WriterJsonArray.writerJSONArray(listMap,response);
			}
			 private File file;
			    private String fileFileName;
			    private String FileContentType;
			    
	//文本编辑器文件上传
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
				public String getFileContentType() {
					return FileContentType;
				}
				public void setFileContentType(String fileContentType) {
					FileContentType = fileContentType;
				}
				//文本编辑器文件上传
	public void UploadPlusFile(){
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
			if(!DataTypeUtil.validate(userInfo)){
				userInfo = (UserInfo) request.getSession().getAttribute("UserInfo_Ent");
			}
			Map map  = new HashMap();
			//不允许的后缀名
			String[] notAllowExtStr = new String[]{"jsp","class","js","html","htm","mht","mhtml","exe","bat","sql","reg","msi","vbs","cmd","scr"}; 
			Arrays.sort(notAllowExtStr); 
			
			String id = Guid.getGuid();
			String userId = userInfo.getUserId();
//			request.setCharacterEncoding("UTF-8");
//			String fileType = request.getParameter("fileType");
//			String fileName = request.getParameter("fileName");
			String extName = fileName.substring(fileName.lastIndexOf(".")+ 1);  //扩展名
			int notAllow = Arrays.binarySearch(notAllowExtStr,extName.toLowerCase());
			String uu = UUID.randomUUID().toString().replace("-", "");
			String lastName = fileName.substring(fileName.lastIndexOf("."));
			fileName = uu+""+lastName;
			if(notAllow >=0){
				System.out.println("-----------不允许上传----"+extName);
				map.put("resultCode", "notAllow");
				map.put("result", "文件格式不允许上传！");
				WriterJsonArray.writerJSONArray(map,response); 
				return;
			}
			String proj_name = AppSetting.PROJECT_NAME.substring(1); 
			String root = ServletActionContext.getServletContext().getRealPath("/");
			String dirName ="upload";
			String dirPath = root+dirName;
			
			//拼接文件夹
//	   	 	File dirFile = new File(dirPath+"/KM/PLUS/"+id);
//				if(!dirFile.exists()){
//					dirFile.mkdir();	//创建文件夹
//				}
			File fileDir = new File(AppSetting.PROJECT_PATH+"/KM/PLUS/"+id);
			if(!fileDir.exists()){
				fileDir.mkdirs();
			} 
			String  result = kmDocService.saveDoc(this.uploadfile,proj_name+"/KM/PLUS/"+id+"/"+fileName);
			
			if(!result.equals("success")){
			   map.put("resultCode", "failed");
			   map.put("result", "文件插入失败");
			}else{
			   map.put("resultCode", "success");
			   map.put("fileName", fileName);
			   map.put("fileUrl", AppSetting.PROJECT_NAME+"/KM/PLUS/"+id+"/"+fileName);
			   kmDocService.AddUserFile(fileName,fileType,"/KM/PLUS/"+id+"/"+fileName,userId,id);
			}
			map.put("fileType", fileType);
			WriterJsonArray.writerJSONArray(map,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

  	}
			
			
			
			
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
			public void PlusFileTable(){ 
				  UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
					if(!DataTypeUtil.validate(userInfo)){
						userInfo = (UserInfo) request.getSession().getAttribute("UserInfo_Ent");
					}
				  
				  int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);    
			      //每页显示条数  
			      int number = Integer.parseInt((rows == null || rows == "0") ? "20":rows);  
			      //每页的开始记录  第一页为1  第二页为number +1   
			      int start = (intPage-1)*number;  
					List<Map<String, Object>> result = kmDocService.PlusFileTable(userInfo,start,number);
					Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map  
			      jsonMap.put("total",kmDocService.getPlusFileTableCount(userInfo));//total键 存放总记录数，必须的  
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
			public void DeletePlusFile(){  
				String userfile_id = request.getParameter("userfile_id");
				String resule = kmDocService.DeletePlusFile(userfile_id);
				WriterJsonArray.writerJSONArray(resule,response); 
			}
			public void getUrlListByDocIds(){
				String a = request.getRemoteAddr();
				List list  = new ArrayList();
				System.out.print(a); 
				String docIds = request.getParameter("docIds"); 
				String fileNames = request.getParameter("fileNames");
				String[] names = fileNames.split(",");
				String[] ids = docIds.split(",");
				String url="";
				for(int i=0;i<ids.length;i++){
					 url = kmDocService.GetUrlById(ids[i],"");
					 Map<String, Object> map = new HashMap<String, Object>();//定义map
					 map.put("url",  url);
					 map.put("fileName",  names[i]);
			 		 map.put("icon", "WORDICON.EXE");
					 list.add(map);
				}
				WriterJsonArray.writerJSONArray(list,response);
			}
			public void copyDoc(){
				UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
				String userId = userInfo.getUserId();
				String type = request.getParameter("type"); 
				String docId = request.getParameter("docId");
				String result = kmDocService.copyDoc(docId,type,userInfo);
				WriterJsonArray.writerJSONArray(result,response); 
			}
}
