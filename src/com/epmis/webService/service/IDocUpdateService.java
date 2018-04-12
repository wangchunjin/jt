package com.epmis.webService.service;

import java.util.List;
import java.util.Map;


public interface IDocUpdateService {

	public String ConnectTest(String mac,String projName);
	/**
	 * 根据名字得到唯一标识ID(DOC_ID)
	 * @param name
	 * @return
	 */
	public String getServiceId(String name);
	/**
	 * 获取所有文档的信息
	 * @return
	 */
	public List<Map<String,Object>> getAllDoc();
	/**
	 * 获取所有更新文档的信息
	 * @param updateTime 
	 * @return
	 */
	public List<Map<String,Object>> getNewDoc(String updateTime);
	/**
	 * 更具MAC地址得到文档的更新时间
	 * @param clientMac
	 * @return
	 */
	public String getUpdateTime(String clientMac,String projName);
	
	/**
	 * 更具MAC地址DOdcId更新时间
	 * @param clientMac
	 * @return
	 */
	public void setUpdateTime(String clientMac,String docId,String projName);
	
	/**
	 * 获取所有文件夹信息
	 * @return
	 */
	public List<Map<String,Object>> getAllFolder();
	
	/**
	 * 获取上次上次更新时间后的（客户端待更新）文档
	 * @param updatetime
	 * @return
	 */
	public Map<String,Object> getDocByServiceId(String service_id);
	
	/**
	 * 获取文件信息
	 * @param doc_id
	 * @return
	 */
	public  Map<String,Object>  getDocInfo(String doc_id);
	
	
	/**
	 * 文件下载
	 * @param doc_id
	 * @param startpost
	 * @return
	 * @throws Exception
	 */
	 public String download(String doc_id,long startpost) throws Exception ;
	 
	 /**
	  * 获取文件夹名称
	  * @param folder_id
	  * @return
	  */
	 
	 public String StructDownload(String doc_id,long startpost) throws Exception ;
	 public String getFolderTitle(String folder_id) ;
	 
	 /**
	  * 添加日志信息
	  * @param clientMac
	  */
	 public void addLogInfo(String clientMac,String projName) ;
	 /**
	  * 更新日志信息
	  * @param clientMac
	  */
	 public void updateLogInfo(String clientMac,String projName) ;
	 
	 /**
	  * 根据文件ID得到文件路径
	  * @param doc_id
	  * @return
	  */
	 public String  getPathByDocId(String doc_id) ;
	 
	 /**
	  * 根据文件ID得到文件名称
	  * @param doc_id
	  * @return
	  */
	 public String  getFileNameByDocId(String doc_id) ;
	 
	 /**
	  * 根据doc_id得到文档详细信息
	  * @param doc_id
	  * @return
	  */
	 public Map<String, Object>  getDocInfoByDocId(String doc_id) ;
	 
	 
	 
	 /**
	  * 根据doc_id得到文件夹ID
	  * @param doc_id
	  * @return
	  */
	 public String  getFolderIdByDocId(String doc_id) ;
	 
	 
	 /**
	  * 根据folder_id得到文件夹信息
	  * @param doc_id
	  * @return
	  */
	 public Map<String, Object>  getFolderInfoByFolderId(String folder_id) ;
	 
	 
	 /**
	  * 根据doc_id得到版本信息
	  * @param doc_id
	  * @return
	  */
	 public List<Map<String, Object>>  getDocverinfoByDocId(String doc_id) ;

	 /**
	  * 根据模块类型得到要更新的模版
	  * @param doc_id
	  * @return
	  */
	 public List<Map<String,Object>>  getSwbsTypeByModle(String modle) ;
	 
	 
	 
	 public List<Map<String,Object>> GetSwbsBy(String swbs_type_name,String parent_swbs_id) ;
	 public List<Map<String,Object>> GetSwbsDocLinkBy(String swbs_id);
	 public long GetFileSize(String id);
	 public List<Map<String,Object>> GetStruct();
	public List<Map<String, Object>> getStructById(String id);
	public List<Map<String, Object>> getStructKeyById(String id);
	public List<Map<String, Object>> getStructLinkById(String id);
	public List<Map<String, Object>> getLanguageList();
	public Map<String,Object> getLanguageInfoByLanguageId(String id);
	public String GetFileUrlById(String docId,  String AttchFileName,String VersionFileName);
	public String DownFileUrlById(String docId, String  AttchFileName,String VersionFileName);
	public String GetFileSize(String docId,  String AttchFileName,String VersionFileName);
}
