package com.epmis.webService.web;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.soap.SoapConstants;
import org.codehaus.xfire.util.Base64;

 



import com.epmis.km.service.KmFolderService;
import com.epmis.sys.dao.BaseJdbcDao;  
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.SpringContextHolder;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.webService.service.IDocUpdateService;
import com.epmis.webService.service.ILicenseService;
import com.epmis.webService.util.DocObj;
import com.opensymphony.xwork2.ActionSupport;

public class UpdatePublicDoc extends ActionSupport implements ServletRequestAware
{
	HttpServletRequest request  ;
	@Override
	 public void setServletRequest(HttpServletRequest request) {
	 	this.request=request;
	 	
	 }
	 HttpServletResponse response = ServletActionContext.getResponse();
	 
	 public static IDocUpdateService service = (IDocUpdateService) DocObj.getServiceInstance();//获得服务器代理对象
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	BaseJdbcDao baseJdbcDao = ((BaseJdbcDao) SpringContextHolder
			.getBean("baseJdbcDao"));
	
	public  void TestConnect()
	{
		String flag = "N";
		try{
		flag = service.ConnectTest(getMACAddress(),AppSetting.PROJECT_NAME.substring(1)); 
		}catch(Exception e){
			e.printStackTrace();
		}
		WriterJsonArray.writerJSONArray(flag,response);
	}
	public  void UpdateLicense()
	{
		String flag = "N";
		try{
			flag = LicenseWeb.downLicense(getMACAddress()); 
		}catch(Exception e){
			e.printStackTrace();
		}
		WriterJsonArray.writerJSONArray(flag,response);
	}
	
	// 获得待更新文档
	public  String  CompareWithService() 
	{
 
		List<Map<String,Object>>  docverlist =null;
		String  datver="";
		try
		{
			
			    String clientMac = getMACAddress();
			String updateTime = service.getUpdateTime(clientMac,AppSetting.PROJECT_NAME.substring(1));//log表中的更新时间
			//如果是第一次更新，初始化km_doc数据（为了加标识符）
			//1.获得服务器端所有的文档信息listServerDocs
			List<Map<String, Object>> listServerDocs = service.getAllDoc();
			if(updateTime==null||"".equals(updateTime))
			{
				for(Iterator<Map<String,Object>> it = listServerDocs.iterator();it.hasNext();)
				{
					Map<String,Object> map = it.next();
					//服务端的文档名称
					String title_Server = (String)map.get("TITLE");
					//服务端的文档ID
					String docId_Server = (String)map.get("DOC_ID");
					//服务端的文件夹ID
					String folderId_Server = (String)map.get("FOLDER_ID");
					//服务端的文件夹title
					String folderTitle_Server = service.getFolderTitle(folderId_Server);
					//开始为客户端的km_doc表添加唯一标识符
					addIdentifier_KMDOC(listServerDocs,title_Server,docId_Server,folderId_Server,folderTitle_Server);
				}
				//2.获得服务器端所有的文件夹信息listServerFolders
				List<Map<String, Object>> listServerFolders = service.getAllFolder();
				for(Iterator<Map<String,Object>> it = listServerFolders.iterator();it.hasNext();)
				{
					Map<String,Object> map = it.next();
					//服务端的文件夹ID
					String folderId_Server = (String)map.get("FOLDER_ID");
					//服务端的文件夹title
					String folderTitle_Server = service.getFolderTitle(folderId_Server);
					//开始为客户端的km_folder表添加唯一标识符
					addIdentifier_KMFOLDER(folderId_Server,folderTitle_Server);
				}
			}
			//*****
				List<Map<String,Object>> listClientDocs = null; 
				//服务端需要更新到客户端的文档集合
				List<Map<String,Object>> listNeedUpateDocs = new ArrayList<Map<String,Object>>();
				//客户端与服务器端冲突的文档集合，用于显示在比对表的客户端区域
				List<Map<String,Object>> listConflictDocsClient = new ArrayList<Map<String,Object>>();
				//客户端与服务器端冲突的文档集合，用于显示在比对表的服务端区域
				List<Map<String,Object>> listConflictDocsServer = new ArrayList<Map<String,Object>>();
				for(Iterator<Map<String,Object>> it = listServerDocs.iterator();it.hasNext();)
				{
					Map<String,Object> map = it.next();
					//服务端文档的docId
					String docId_Server = (String) map.get("DOC_ID");
					//服务端文档的editeDate
					
				 	XMLGregorianCalendar  date_Server = (XMLGregorianCalendar )map.get("EDITED_DATE");
				 	Date editedDate_Server = date_Server.toGregorianCalendar().getTime();  
					//服务端文档的edition
					String edition_Server =  map.get("EDITION")+"";
					String sql = "select d.EDITED_DATE,d.EDITION ,(select title from km_folder where folder_id = d.folder_id) as folder_title from km_doc d where d.service_id='"+docId_Server+"'";
					listClientDocs = baseJdbcDao.queryListMap( sql);  
					//如果客户端和服务端存在唯一标识符匹配的数据，再进行版本信息的比对
					if(listClientDocs!=null && listClientDocs.size()>0)
					{
						//客户端文档的editedDate
						Date editedDate_Client = (Date) listClientDocs.get(0).get("EDITED_DATE");
					//	XMLGregorianCalendar date_Client = (XMLGregorianCalendar) listClientDocs.get(0).get("EDITED_DATE");
					//	Date editedDate_Client = date_Client.toGregorianCalendar().getTime();
					//	Date editedDate_Client = sdf.parse(date_Client);
						//客户端文档的edition
						String edition_Client = (String)listClientDocs.get(0).get("EDITION");
						String folder_title_client = (String)listClientDocs.get(0).get("FOLDER_TITLE");
						//如果版本号与服务器相同
						if(edition_Client.equals(edition_Server))
						{
							//如果客户端的文档编辑时间 早于 服务端，说明服务端的此文档需要更新到客户端
							if(editedDate_Client.before(editedDate_Server))
							{
								//获得待更新的集合
								listNeedUpateDocs.add(map);
							}
							//如果客户端的文档编辑时间 晚于 服务端，说明客户端的此文档与服务器端有冲突
							else if(editedDate_Client.after(editedDate_Server))
							{
								//获得客户端区域显示的有冲突的文档集合
								map.put("FOLDER_TITLE_CLIENT", folder_title_client);
								listConflictDocsClient.add(map);
								//获得服务端区域显示的有冲突的文档集合
								listConflictDocsServer.add(map);
							}
						}
						//如果版本号小于服务器端
						else if(Double.parseDouble(edition_Client) < Double.parseDouble(edition_Server) )
						{
							//获得待更新的集合
							listNeedUpateDocs.add(map);
						}
						//如果版本号大于服务器端
						else
						{
							//获得客户端区域显示的有冲突的文档集合
							map.put("FOLDER_TITLE_CLIENT", folder_title_client);
							listConflictDocsClient.add(map);
							//获得服务端区域显示的有冲突的文档集合
							listConflictDocsServer.add(map);
						}
					}
					//如果客户端和服务端不存在唯一标识符匹配的数据，就全部作为待更新文档
					else
					{
						//获得待更新的集合
						listNeedUpateDocs.add(map);
					}
				}
				//服务端需要更新到客户端的文档集合放入请求中
				request.setAttribute("listNeedUpateDocs", listNeedUpateDocs);
				//客户端区域显示的有冲突的文档集合放入请求中
				request.setAttribute("listConflictDocsClient", listConflictDocsClient);
				//服务端区域显示的有冲突的文档集合放入请求中
				request.setAttribute("listConflictDocsServer", listConflictDocsServer);
				//服务器端待更新文档总数
				int sumServer = listNeedUpateDocs.size() + listConflictDocsServer.size();
				request.setAttribute("sumServer", sumServer);
				request.setAttribute("overflag", "Y"); 
				if(updateTime==null||"".equals(updateTime))
				{
					//添加服务器端log表更新时间
				//	service.addLogInfo(clientMac);
				}else
				{
					//更新服务器端log表更新时间
				//	service.updateLogInfo(clientMac);
				}
				
		 
		}catch(Exception e)
		{ 
			
			e.printStackTrace();
		}
		return "success";  
    }
	
	// 获得待更新文档
	public  String  NewCompareWithService() 
	{
 
		try
		{
			
			    String clientMac = getMACAddress();
			String updateTime = service.getUpdateTime(clientMac,AppSetting.PROJECT_NAME.substring(1));//log表中的更新时间
			//如果是第一次更新，初始化km_doc数据（为了加标识符）
			//1.获得服务器端所有的文档信息listServerDocs
			List<Map<String, Object>> listServerDocs = service.getNewDoc(updateTime);
			if(updateTime==null||"".equals(updateTime))
			{
				for(Iterator<Map<String,Object>> it = listServerDocs.iterator();it.hasNext();)
				{
					Map<String,Object> map = it.next();
					//服务端的文档名称
					String title_Server = (String)map.get("TITLE");
					//服务端的文档ID
					String docId_Server = (String)map.get("DOC_ID");
					//服务端的文件夹ID
					String folderId_Server = (String)map.get("FOLDER_ID");
					//服务端的文件夹title
					String folderTitle_Server = service.getFolderTitle(folderId_Server);
					//开始为客户端的km_doc表添加唯一标识符
					addIdentifier_KMDOC(listServerDocs,title_Server,docId_Server,folderId_Server,folderTitle_Server);
				}
				//2.获得服务器端所有的文件夹信息listServerFolders
				List<Map<String, Object>> listServerFolders = service.getAllFolder();
				for(Iterator<Map<String,Object>> it = listServerFolders.iterator();it.hasNext();)
				{
					Map<String,Object> map = it.next();
					//服务端的文件夹ID
					String folderId_Server = (String)map.get("FOLDER_ID");
					//服务端的文件夹title
					String folderTitle_Server = service.getFolderTitle(folderId_Server);
					//开始为客户端的km_folder表添加唯一标识符
					addIdentifier_KMFOLDER(folderId_Server,folderTitle_Server);
				}
			}
			String sql="SELECT A.DOC_ID, A.DOC_NUMBER , A.TITLE,A.FOLDER_ID,A.EDITED_DATE,A.EDITION,D.TITLE FOLDER_TITLE FROM KM_DOC A LEFT OUTER JOIN CM_CODE B ON A.DOC_TYPE = B.CODE_NAME AND B.CODE_TYPE='DOC_TYPE' LEFT OUTER JOIN CM_USERS C ON A.CHECKOUT_BY = C.USER_ID LEFT OUTER JOIN KM_FOLDER D  ON A.FOLDER_ID = D.FOLDER_ID WHERE A.DELETED_FLAG!='1'  and A.folder_id in (select folder_id from km_folder where doc_grade='0')   and a.doc_grade='0' ORDER BY A.EDITED_DATE ASC";
			List<Map<String, Object>> listClientDocs = baseJdbcDao.queryListMap(sql);
			listServerDocs.removeAll(listClientDocs);
	 
				//服务端需要更新到客户端的文档集合放入请求中
				request.setAttribute("listNeedUpateDocs", listServerDocs);
 
				//服务器端待更新文档总数
				int sumServer = listServerDocs.size();
				request.setAttribute("sumServer", sumServer);
			 
				 
				
		 
		}catch(Exception e)
		{ 
			
			e.printStackTrace();
		}
		return "success";  
    }
	
	//给客户端km_doc表添加唯一标识
	public  void addIdentifier_KMDOC(List<Map<String,Object>> listServerDocs,String title_Server,String docId_Server,String folderId_Server,String folderTitle_Server) throws Exception
	{
		//查出客户端与服务端相同名称的文档信息
		String sql="SELECT D.TITLE,D.DOC_ID,F.TITLE FOLDERTITLE,F.FOLDER_ID FROM KM_FOLDER F,KM_DOC D WHERE F.FOLDER_ID=D.FOLDER_ID AND D.TITLE='"+title_Server+"' AND (D.SERVICE_ID ='' OR D.SERVICE_ID IS NULL)";
		List<Map<String,Object>> listClientDocs = baseJdbcDao.queryListMap( sql);
		if(listClientDocs.size()>1)//如果查出多条，就再根据id过滤
		{
			for(Iterator<Map<String,Object>> j = listClientDocs.iterator();j.hasNext();)
			{
				Map<String,Object> mapC = j.next();
				//客户端的docID
				String docId_Client = (String) mapC.get("DOC_ID");
				//客户端的文件夹title
				String folderTitle_Client = (String) mapC.get("FOLDERTITLE");
				//如果根据服务端的docID能在客户端找到对应数据，并且处于相同的目录下，就将此数据的servicId标识为docId_Server
				if(docId_Server.equals(docId_Client) && folderTitle_Server.equals(folderTitle_Client))
				{
					sql = "UPDATE KM_DOC SET SERVICE_ID='"+docId_Server+"' WHERE DOC_ID = '"+docId_Server+"'";
					baseJdbcDao.update( sql);
				}
			}
		}else if(listClientDocs.size() == 1)
		{
			//客户端的docID
			String docId_Client = (String) listClientDocs.get(0).get("DOC_ID");
			//客户端的文件夹title
			String folderTitle_Client = (String) listClientDocs.get(0).get("FOLDERTITLE");
			//如果根据服务端的docID能在客户端找到对应数据，并且处于相同的目录下，就将此数据的servicId标识为docId_Server
			if(docId_Server.equals(docId_Client) && folderTitle_Server.equals(folderTitle_Client))
			{
				sql = "UPDATE KM_DOC SET SERVICE_ID='"+docId_Server+"' WHERE doc_id='"+docId_Client+"'";
				baseJdbcDao.update( sql);
			}
		}
	}
	
	//给客户端km_folder表添加唯一标识
	public  void addIdentifier_KMFOLDER(String folderId_Server,String folderTitle_Server) throws Exception
	{
		String sql = "select * from km_folder where title='"+folderTitle_Server+"'";
		List<Map<String,Object>> listMap = baseJdbcDao.queryListMap( sql);
		if(listMap!=null)
		{
			if(listMap.size()>1)
			{
				sql = "update km_folder set service_id = '"+folderId_Server+"' where title='"+folderTitle_Server+"' and folder_id='"+folderId_Server+"' and doc_grade='0'";
				baseJdbcDao.update( sql);
			}else if(listMap.size() == 1)
			{
				sql = "update km_folder set service_id = '"+folderId_Server+"' where title='"+folderTitle_Server+"' and doc_grade='0'";
				baseJdbcDao.update( sql);
			}
		}
	}	
	public  void GetPercent(){
	     Map<String,Object> root = new HashMap<String,Object>();
	     System.out.println(AppSetting.totalSize);
	     root.put("hasSize", AppSetting.hasSize/1024+"KB");
	     root.put("totalSize", AppSetting.totalSize/1024+"KB");
	     WriterJsonArray.writerJSONArray(root,response); 
	}
	// 文档跟新
	 
	public  void downLoad() 
	{
		AppSetting.totalSize=0; 
		AppSetting.hasSize = 0;
		//设置sfire文件传递的属性为true
 		Client client =((XFireProxy)Proxy.getInvocationHandler(service)).getClient();
		client.setProperty(SoapConstants.MTOM_ENABLED, "true");
		String returnStr = "false";
		String id = "";
		String folderPath = "";
		ResourceBundle rb=ResourceBundle.getBundle("system");  
		String downFlag = rb.getString("CLOUD-DOC-DOWN"); 
		try 
		{
			
			
			String serviceId = DataTypeUtil.formatDbColumn(request.getParameter("serviceId"));
	        String type = DataTypeUtil.formatDbColumn(request.getParameter("type"));
				 id = serviceId;
				 //文件夹数据库的更新,得到服务端的当前数据的map集合
				 String folderId_Server = service.getFolderIdByDocId(id);
				 Map<String,Object> folderMap_Server = service.getFolderInfoByFolderId(folderId_Server);
				 folderMap_Server.put("SERVICE_ID", folderId_Server);
				 String sql = "select folder_id,folder_name,PARENT_FOLDER_ID from km_folder where service_id = '"+folderId_Server+"'";
				 Map<String,Object> folderMap_Client = (Map<String,Object>) baseJdbcDao.queryMap( sql);
				 if(folderMap_Client != null)
				 {
					 String folderId_Client = (String) folderMap_Client.get("FOLDER_ID");
					 String folderName_Client = (String) folderMap_Client.get("FOLDER_NAME");
					 String parent_folder_Client = (String) folderMap_Client.get("PARENT_FOLDER_ID");
					 if(!(folderId_Server.equals(folderId_Client)))
					 {
						 baseJdbcDao.delete("delete from km_folder where folder_id ='"+folderId_Client+"'");
						 folderMap_Server.remove("FOLDER_ID");
						 folderMap_Server.put("FOLDER_ID", folderId_Client);
						 folderMap_Server.remove("PARENT_FOLDER_ID");
						 folderMap_Server.put("PARENT_FOLDER_ID", parent_folder_Client);
						 folderMap_Server.remove("FOLDER_NAME");
						 folderMap_Server.put("FOLDER_NAME", folderName_Client);
						 Date date = ((XMLGregorianCalendar )folderMap_Server.get("CREATED_DATE")).toGregorianCalendar().getTime();  ;
						 String d = sdf.format(date);
						 folderMap_Server.put("CREATED_DATE",d);
						 baseJdbcDao.insertMapInfo(folderMap_Server, "KM_FOLDER");
					 }
					 
				 }else
				 {
					 String folderIdPath_Server = (String) folderMap_Server.get("FOLDER_ID_PATH");
					 String[] str = folderIdPath_Server.split(",");
					 for(int e = 0;e<str.length;e++)
					 {
						 sql = "select folder_id,folder_name from km_folder where service_id = '"+str[e]+"'";
						 Map<String,Object> folderMap_Client2 = (Map<String,Object>) baseJdbcDao.queryMap( sql);
						 if(folderMap_Client2==null)
						 {
							 Map<String,Object> folderMap_Server2 = service.getFolderInfoByFolderId(str[e]);
							 if(DataTypeUtil.validate(folderMap_Server2)){
								 String service_parent_folder_id = (String)folderMap_Server2.get("PARENT_FOLDER_ID");
								 String parent_folder_id =DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue( "select folder_Id from km_folder where service_id ='"+service_parent_folder_id+"'"));
							
							 String folder_id = (String)folderMap_Server2.get("FOLDER_ID");
							 folderMap_Server2.put("SERVICE_ID", folder_id);
							 
							 Date date = ((XMLGregorianCalendar )folderMap_Server2.get("CREATED_DATE")).toGregorianCalendar().getTime();  ;
							 String d = sdf.format(date);
						
							 folderMap_Server2.remove("CREATED_DATE");
							 folderMap_Server2.put("CREATED_DATE", d);
							 folderMap_Server2.remove("PARENT_FOLDER_ID");
							 folderMap_Server2.put("PARENT_FOLDER_ID", parent_folder_id);
							 baseJdbcDao.insertMapInfo(folderMap_Server2, "KM_FOLDER"); 
							 }
						 }
					 }
				 }
				 
				 //文档数据库的更新,得到服务端的当前数据的map集合
				 Map<String,Object> docMap = service.getDocInfoByDocId(id);
				 docMap.put("SERVICE_ID", id);
				 sql = "select doc_id from km_doc where service_id='"+id+"'";
				 String docId_Client = (String)baseJdbcDao.getFieldValue( sql);
				 sql = "select folder_id from km_doc where service_id='"+id+"'";
				 String docFolder_Client = (String)baseJdbcDao.getFieldValue( sql);
				 String guid = Guid.getGuid();
				//第一种情况：更新没有冲突的文档
				 if(docId_Client==null||"".equals(docId_Client))
				 {
					 //插入一条新数据到客户端
					 sql = "SELECT FOLDER_ID FROM KM_FOLDER WHERE SERVICE_ID='"+folderId_Server+"'";
					  docFolder_Client = (String)baseJdbcDao.getFieldValue( sql);
					 addToClientKmDoc(docMap,sdf,guid,docFolder_Client);
					 docId_Client = guid;
				 }
				 //第二种情况：更新冲突的文档
				 else
				 {
					 sql = "DELETE FROM KM_DOC WHERE SERVICE_ID='"+id+"'";
					 baseJdbcDao.delete( sql);
					 //插入一条新数据到客户端
					 addToClientKmDoc(docMap,sdf,docId_Client,docFolder_Client);
				 }
				 //不管哪种情况，都要更新版本信息与服务器端对应数据的版本信息一致
				 sql = "DELETE FROM KM_DOCVER WHERE DOC_ID = '"+docId_Client+"'";
				 baseJdbcDao.delete( sql);//删除旧的信息
				List<Map<String,Object>> mapDocVer_Servers = service.getDocverinfoByDocId(id);
				for(Map<String,Object> mapDocVer_Server :mapDocVer_Servers){
					String docver_id = (String)mapDocVer_Server.get("DOCVER_ID");
				 mapDocVer_Server.put("WID",Guid.getGuid());
				 mapDocVer_Server.remove("DOCVER_ID");
				 //mapDocVer_Server.remove("DOC_ID");
				 mapDocVer_Server.put("DOC_ID", docId_Client);
		
				 Date dateStr = ((XMLGregorianCalendar )mapDocVer_Server.get("EDITED_DATE")).toGregorianCalendar().getTime();  ;
				 String editedDate = sdf.format(dateStr);
				 mapDocVer_Server.put("DOCVER_ID",Guid.getGuid());
				 mapDocVer_Server.remove("EDITED_DATE");
				 mapDocVer_Server.put("EDITED_DATE", editedDate);
				 baseJdbcDao.insertMapInfo(mapDocVer_Server, "KM_DOCVER");
				 //实体文件的更新
				 if(DataTypeUtil.validate(downFlag)&&downFlag.equals("Y")){
					KmFolderService kmFolderService = ((KmFolderService) SpringContextHolder
							.getBean("kmFolderService")); 
				 folderPath = AppSetting.PROJECT_PATH+"/KM/DOC" + service.getPathByDocId(id);
					String folderpathClient=kmFolderService.getFullPathByDocId(docId_Client);
					folderpathClient =  AppSetting.PROJECT_PATH+"/KM/DOC"+folderpathClient;
				 Long start=System.currentTimeMillis();
				 String fileName = service.getFileNameByDocId(docver_id);
				 //文档的完整路径
				 if(DataTypeUtil.validate(fileName)){
				 File file_new=new File(folderpathClient,fileName);
				 File file_new_service=new File(folderPath,fileName);
				 System.out.println(folderpathClient + File.separator + fileName);
			    //File file=new File(folderPath + File.separator + fileName);
			     File file=new File(folderpathClient);
			     if(!file.exists())
			     {
			    	 file.mkdirs();
			     }
			    if(file_new.exists()){
			    	file_new.delete();
			    }
			    
			    if(AppSetting.totalSize>=0){
				     RandomAccessFile raf=new RandomAccessFile(file_new,"rw");
				     raf.seek(file_new.length());
				     boolean isend=false;
				     while (!isend)
				     { 
				    	 
				    	 String data=service.download(id, file_new.length());
				    	 if(null == data)
							{
								isend = true;
							}
							else
							{
							//	Logger.info("返回大小：" + data.length());
								byte[] bytes = Base64.decode(data);
								raf.write(bytes);
								raf.skipBytes(data.length());
								if (data.length() <= 0)
								{
									isend = true;
								}
								AppSetting.hasSize = file_new.length();
								AppSetting.totalSize=service.GetFileSize(id);
							}
				     }
				     raf.close();
			    }
			    if(type.equals("new")){
			    	service.setUpdateTime(getMACAddress(), id,AppSetting.PROJECT_NAME.substring(1));
			    }
			 //    Long end=System.currentTimeMillis();
			   //  System.out.println("用时："+(end-start));
				 
				}
				} 
				}
			returnStr = "true";
		 
		} catch (Exception e) 
		{
		 			 
			e.printStackTrace();
		}
		WriterJsonArray.writerJSONArray(returnStr,response);
	}
	public  void addToClientKmDoc(Map<String,Object> docMap,SimpleDateFormat sdf,String guid,String docFolder_Client) throws Exception
	{
		//插入一条新数据到客户端
		docMap.put("DOC_ID",guid);
		docMap.put("WID",Guid.getGuid());
		//对所有的日期类型字段做处理
		Date created_date = DataTypeUtil.validate(docMap.get("CREATED_DATE")) ?   ((XMLGregorianCalendar )docMap.get("CREATED_DATE")).toGregorianCalendar().getTime(): null;
		Date edited_date =DataTypeUtil.validate(docMap.get("EDITED_DATE")) ? ((XMLGregorianCalendar ) docMap.get("EDITED_DATE")).toGregorianCalendar().getTime() : null;
		Date effect_date =DataTypeUtil.validate(docMap.get("EFFECT_DATE")) ? ((XMLGregorianCalendar ) docMap.get("EFFECT_DATE")).toGregorianCalendar().getTime(): null;
		Date abate_date = DataTypeUtil.validate(docMap.get("ABATE_DATE")) ?((XMLGregorianCalendar ) docMap.get("ABATE_DATE")).toGregorianCalendar().getTime(): null;
		Date target_start_date = DataTypeUtil.validate(docMap.get("TARGET_START_DATE")) ?((XMLGregorianCalendar ) docMap.get("TARGET_START_DATE")).toGregorianCalendar().getTime(): null;
		Date target_end_date = DataTypeUtil.validate(docMap.get("TARGET_END_DATE")) ?((XMLGregorianCalendar ) docMap.get("TARGET_END_DATE")).toGregorianCalendar().getTime(): null;
		Date act_start_date = DataTypeUtil.validate(docMap.get("ACT_START_DATE")) ?((XMLGregorianCalendar ) docMap.get("ACT_START_DATE")).toGregorianCalendar().getTime(): null;
		Date act_end_date =DataTypeUtil.validate(docMap.get("ACT_END_DATE")) ? ((XMLGregorianCalendar ) docMap.get("ACT_END_DATE")).toGregorianCalendar().getTime(): null;
		Date move_date = DataTypeUtil.validate(docMap.get("MOVE_DATE")) ?((XMLGregorianCalendar ) docMap.get("MOVE_DATE")).toGregorianCalendar().getTime(): null;
		Date date_linked = DataTypeUtil.validate(docMap.get("DATE_LINKED")) ?((XMLGregorianCalendar ) docMap.get("DATE_LINKED")).toGregorianCalendar().getTime(): null;
		Date approved_date =DataTypeUtil.validate(docMap.get("APPROVED_DATE")) ? ((XMLGregorianCalendar ) docMap.get("APPROVED_DATE")).toGregorianCalendar().getTime(): null;
		if(DataTypeUtil.validate(docFolder_Client)){
			docMap.remove("FOLDER_ID");
			docMap.put("FOLDER_ID", docFolder_Client);
		}
		
		docMap.put("CREATED_DATE","NOW()");
		docMap.remove("EDITED_DATE");
		docMap.remove("EFFECT_DATE");
		docMap.remove("ABATE_DATE");
		docMap.remove("TARGET_START_DATE");
		docMap.remove("TARGET_END_DATE");
		docMap.remove("ACT_START_DATE");
		docMap.remove("ACT_END_DATE");
		docMap.remove("MOVE_DATE");
		docMap.remove("DATE_LINKED");
		docMap.remove("APPROVED_DATE");
		
		if(DataTypeUtil.validate(created_date))
		{
			docMap.put("CREATED_DATE",sdf.format(created_date));
		}
		if(DataTypeUtil.validate(edited_date))
		{
			docMap.put("EDITED_DATE", sdf.format(edited_date));
		}
		if(DataTypeUtil.validate(effect_date))
		{
			docMap.put("EFFECT_DATE", sdf.format(effect_date));
		}
		if(DataTypeUtil.validate(abate_date))
		{
			docMap.put("ABATE_DATE", sdf.format(abate_date));
		}
		if(DataTypeUtil.validate(target_start_date))
		{
			docMap.put("TARGET_START_DATE", sdf.format(target_start_date));
		}
		if(DataTypeUtil.validate(target_end_date))
		{
			docMap.put("TARGET_END_DATE", sdf.format(target_end_date));
		}
		if(DataTypeUtil.validate(act_start_date))
		{
			docMap.put("ACT_START_DATE", sdf.format(act_start_date));
		}
		if(DataTypeUtil.validate(act_end_date))
		{
			docMap.put("ACT_END_DATE", sdf.format(act_end_date));
		}
		if(DataTypeUtil.validate(move_date))
		{
			docMap.put("MOVE_DATE", sdf.format(move_date));
		}
		if(DataTypeUtil.validate(date_linked))
		{
			docMap.put("DATE_LINKED", sdf.format(date_linked));
		}
		if(DataTypeUtil.validate(approved_date))
		{
			docMap.put("APPROVED_DATE", sdf.format(approved_date));
		}
		
		baseJdbcDao.insertMapInfo(docMap, "KM_DOC");
	}
 
	public  String  GetModle() 
	{
		try
		{
			
		String modle_name = request.getParameter("module_name");
		List<Map<String,Object>> MapListService = service.getSwbsTypeByModle(modle_name);
		List<Map<String,Object>> MapListClient =  baseJdbcDao.queryListMap( "select t.swbs_type_name,t.description from sm_swbstype t where t.modlename='"+modle_name+"'");
		MapListService.removeAll(MapListClient);
		request.setAttribute("MapList", MapListService);
		}catch(Exception e)
		{
			try 
			{
				 
			} catch (Exception e1) 
			{
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		return "success";
	}
    public  String  GetStruct() 
	{	 
		List<Map<String,Object>> MapListService = service.GetStruct();
		List<Map<String,Object>> MapListClient =  baseJdbcDao.queryListMap( "SELECT OFFICETPL_ID, OFFICETPL_NAME , OFFICETPL_REMARK FROM STRUCT_OFFICETPL");
		MapListService.removeAll(MapListClient);
		request.setAttribute("MapList", MapListService);		
		return "success";
	}
  	public  void StructUpdate() {
		try 
		{
			AppSetting.totalSize=0; 
			AppSetting.hasSize = 0;
			//设置sfire文件传递的属性为true
	 		Client client =((XFireProxy)Proxy.getInvocationHandler(service)).getClient();
			client.setProperty(SoapConstants.MTOM_ENABLED, "true");
  		String serviceId = DataTypeUtil.formatDbColumn(request.getParameter("serviceId"));
  		String folderpathClient =  AppSetting.PROJECT_PATH+"/KM/STRUCT";
  		File folderpath = new File(folderpathClient);
  		if(! folderpath.exists()){
  			folderpath.mkdirs();
  		}
			 File file_new=new File(folderpathClient,serviceId+".doc");
			 
			if(AppSetting.totalSize>=0){
			     RandomAccessFile raf=new RandomAccessFile(file_new,"rw");
			     raf.seek(file_new.length());
			     boolean isend=false;
			     while (!isend)
			     { 
			    	 
			    	 String data=service.StructDownload(serviceId, file_new.length());
			    	 if(null == data)
						{
							isend = true;
						}
						else
						{
						//	Logger.info("返回大小：" + data.length());
							byte[] bytes = Base64.decode(data);
							raf.write(bytes);
							raf.skipBytes(data.length());
							if (data.length() <= 0)
							{
								isend = true;
							}
							AppSetting.hasSize = file_new.length();
							AppSetting.totalSize=service.GetFileSize(serviceId);
						}
			     }
			     raf.close();
			  		List<Map<String,Object>> MapListDoc = service.getStructById(serviceId);
			  		baseJdbcDao.insertListMapInfo(MapListDoc, "STRUCT_OFFICETPL");
                  	 MapListDoc = service.getStructKeyById(serviceId);
                  	baseJdbcDao.insertListMapInfo(MapListDoc, "STRUCT_OFFICETPL_KEY");
                      MapListDoc = service.getStructLinkById(serviceId);
                      List<Map<String,Object>> MapListLink = new ArrayList<Map<String,Object>>();
                      if(DataTypeUtil.validate(MapListDoc)){
                    	  for(Map<String,Object> map : MapListDoc){
                    		  String service_id = map.get("SWBS_ID").toString();
                    		  String sql = "SELECT SWBS_ID FROM SM_SWBS WHERE SERVICE_ID = '"+service_id+"'";
                    		  List<Map<String,Object>> MapListSwbsId =  baseJdbcDao.queryListMap(sql);
                    		  if(DataTypeUtil.validate(MapListSwbsId)&&MapListSwbsId.size()>0){
                    			  for(Map<String,Object> mapSwbsId : MapListSwbsId){
                    				  mapSwbsId.put("OFFICETPL_FLAG", map.get("OFFICETPL_FLAG"));
                    				  mapSwbsId.put("WID", Guid.getGuid());
                    				  mapSwbsId.put("LINK_ID", Guid.getGuid());
                    				  MapListLink.add(mapSwbsId);
                    			  }
                    		  }else{
                    			  MapListLink.add(map);
                    		  }
                    	  }
                      }
                      baseJdbcDao.insertListMapInfo(MapListLink, "STRUCT_OFFICETPL_SWBS");
		   }
		} catch (Exception e) 
		{
		 			 
			e.printStackTrace();
		}
  	}
	 List<Map<String,Object>> MapListNead = new ArrayList<Map<String,Object>>();
	 List<Map<String,Object>> MapListDocLink = new ArrayList<Map<String,Object>>();
	public  void SwbsUpdate() 
	{
		Connection conn = null;
		try
		{
		
		
		String serviceIds = DataTypeUtil.formatDbColumn(request.getParameter("serviceIds"));
        String modle_name = DataTypeUtil.formatDbColumn(request.getParameter("modle_name"));
		String[] servicelist = serviceIds.split("~");
		String swbs_type_name = "";
		for (int i = 0; i < servicelist.length; i++) 
		{
			swbs_type_name = servicelist[i].split("@")[0];
			String new_swbs_type_id = Guid.getGuid();
		    updateSwbsXml(conn,new_swbs_type_id,swbs_type_name,"0","0");
		    
		    baseJdbcDao.insert("insert into sm_swbstype(wid,swbs_type_id, swbs_type_name, description, modlename) values('"+Guid.getGuid()+"','"+new_swbs_type_id+"','"+swbs_type_name+"','"+servicelist[i].split("@")[1]+"','"+modle_name+"')");
		    baseJdbcDao.insertListMapInfo(MapListNead, "SM_SWBS");
		    baseJdbcDao.insertListMapInfo(MapListDocLink, "CM_DOCLINK");
		    MapListNead = new ArrayList<Map<String,Object>>();
		    MapListDocLink= new ArrayList<Map<String,Object>>();
		}
		}catch(Exception e)
		{
			try 
			{
			} catch (Exception e1) 
			{
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
	}
	
	public  void updateSwbsXml(Connection conn ,String new_swbs_type_id ,String swbs_type_name ,String new_parent_swbs_id ,String parent_swbs_id) throws SQLException{
		
		List<Map<String,Object>> MapListService = service.GetSwbsBy(swbs_type_name, parent_swbs_id);
		if(DataTypeUtil.validate(MapListService)){				
			for(Map<String,Object> map : MapListService ){
				String new_swbs_id = Guid.getGuid();
				String swbs_id = map.get("SWBS_ID").toString();
				map.put("SWBS_ID", new_swbs_id);
				map.put("WID",Guid.getGuid());
				map.put("SERVICE_ID",swbs_id);
				map.put("SWBS_TYPE_ID", new_swbs_type_id);
				map.put("PARENT_SWBS_ID", new_parent_swbs_id); 	 
 				String remark = (DataTypeUtil.formatDbColumn(map.get("REMARK"))).replaceAll("'", "\'").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>");
				map.put("REMARK", remark);
				String info = (DataTypeUtil.formatDbColumn(map.get("INFO"))).replaceAll("'", "\'").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>");				
				map.put("INFO", info);
				map.remove("PLAN_SCORE");
				MapListNead.add(map);
				List<Map<String,Object>> MapListdoc = service.GetSwbsDocLinkBy(swbs_id);
				if(DataTypeUtil.validate(MapListdoc)){
					for(Map<String,Object> maplist : MapListdoc ){
						String doc_id = (String) baseJdbcDao.getFieldValue( "select doc_id from km_doc where service_id = '"+maplist.get("DOC_ID").toString()+"'");
					    if(DataTypeUtil.validate(doc_id)){
					    	maplist.put("WID",Guid.getGuid());
					    	maplist.put("DOC_ID", doc_id);
					    	maplist.put("BASE_MASTER_KEY", new_swbs_id);
					    	maplist.put("SWBS_TYPE_ID",new_swbs_type_id);
					    	MapListDocLink.add(maplist);
					    }
					}
				}
				updateSwbsXml( conn ,new_swbs_type_id , swbs_type_name ,new_swbs_id, swbs_id);
			}
		}
 
	}
	 public  static String getMACAddress() throws Exception
	  {
	    InetAddress ia = InetAddress.getLocalHost();
	    byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

	    StringBuffer sb = new StringBuffer();

	    for (int i = 0; i < mac.length; i++) {
	      if (i != 0) {
	        sb.append("-");
	      }

	      String s = Integer.toHexString(mac[i] & 0xFF);
	      sb.append(s.length() == 1 ? 0 + s : s);
	    }

	    return sb.toString().toUpperCase();
	  }

		public  void LanguageUpdate() {
			String result ="同步成功";
			try 
			{
 
		 		Client client =((XFireProxy)Proxy.getInvocationHandler(service)).getClient();
				client.setProperty(SoapConstants.MTOM_ENABLED, "true"); 
				List<Map<String, Object>> serviceList=service.getLanguageList();
				String sql = "SELECT SERVICE_ID ID FROM STRUCT_LANGUAGE WHERE TYPE = 'TASK'";
				List<Map<String, Object>> clientList=baseJdbcDao.queryListMap(sql);
				serviceList.removeAll(clientList);
				if(DataTypeUtil.validate(serviceList)&&serviceList.size()>0){
					for(Map<String, Object> map:serviceList){
						String parentId = "0";
						String Path="";
						Map<String, Object> sermap  = service.getLanguageInfoByLanguageId(map.get("ID").toString());
						String servicePath =  DataTypeUtil.formatDbColumn(sermap.get("ID_PATH"));
						sql =" SELECT  (MAX(LOCATE(SERVICE_ID,'"+servicePath+"')))LEN  FROM STRUCT_LANGUAGE WHERE SERVICE_ID IS NOT NULL AND SERVICE_ID !=''";  
						String len = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
						if(DataTypeUtil.validate(len) && !len.equals("0")){
 							String temp = servicePath.substring(Integer.valueOf(len)-1);
 							String serviceParent = temp.substring(0, temp.indexOf(","));
 							servicePath  = temp.substring(temp.indexOf(",")+1);
 							sql ="SELECT ID ,ID_PATH FROM STRUCT_LANGUAGE WHERE SERVICE_ID = '"+serviceParent+"'";
 							Map<String, Object> clientmap  = baseJdbcDao.queryMap(sql);
 							parentId =  DataTypeUtil.formatDbColumn(clientmap.get("ID"));
 							Path=  DataTypeUtil.formatDbColumn(clientmap.get("ID_PATH")); 							
						}						
						 String[] str = servicePath.split(",");
						 for(int e = 0;e<str.length;e++)
						 {
							 Map<String,Object> Map_Server2 = service.getLanguageInfoByLanguageId(str[e]);
							 if(DataTypeUtil.validate(Map_Server2)){
	                         String id = Guid.getGuid();
						     String service_id = (String)Map_Server2.get("ID");
							 Map_Server2.put("SERVICE_ID", service_id);
							 Map_Server2.put("ID", id);
							 
							 if(parentId.equals("0")){
								 Path = id;								 
							 }else{								
								 Path = Path+","+id;
							 }
							 Map_Server2.put("ID_PATH", Path);
							 Date date = new Date();
							 String d = sdf.format(date);						
							 Map_Server2.put("CREATED_TIME", d);
							 Map_Server2.put("PARENT_ID", parentId);
							 baseJdbcDao.insertMapInfo(Map_Server2, "STRUCT_LANGUAGE");
							 parentId = id;
							 }						 
						 }
						 
					}
				}				
			} catch (Exception e) 
			{
				result = e.getMessage(); 
				e.printStackTrace();
				
			}finally{
				 WriterJsonArray.writerJSONArray(result,response);
			}
	  	}
}