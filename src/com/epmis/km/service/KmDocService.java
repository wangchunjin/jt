package com.epmis.km.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.epmis.km.vo.KmAttch;
import com.epmis.km.vo.KmDoc;
import com.epmis.sys.util.UserInfo;

public abstract interface KmDocService
{
  public abstract List<Map<String, Object>>  ShowDocTree(String ParentId,String docGrade , UserInfo userInfo);
  public abstract String initAddDocNum(String folder_id, String proj_id, String user_id, String doc_grade);
  public abstract boolean InsertDoc(KmDoc kmDoc);
  public abstract boolean InsertAttch(KmAttch kmAttch);
  public abstract List ShowDocTable(String folderId, String docGrade,
			String isAll, UserInfo userInfo);
  public abstract String GetUrlById(String docId, String type);
  public abstract Map<String, Object> getDocInfoByDocId(String docId);
  public abstract Map<String, Object> getAttchInfoByAttchId(String AttchId);
  public abstract Map<String, Object> getVersionInfoByVersionId(String VersionId);
  public abstract String delDoc(String docId, String type);
  public abstract String publicDoc(String docIds, String status, String userId);
  public abstract String UpdateDocNormal(KmDoc kmDoc);
public abstract String UpdateDocOther(KmDoc kmDoc);
public abstract List DocVersionTable(String docId);  
public abstract String GetUrlByVersionId(String docId, String docverId);
public abstract List DocLogTable(String docId);
public abstract List DocAttchTable(String docId);
public abstract List PlanDocTree(String id, String tableName,String baseLinkId, String userId,
		String projId,String type);
public abstract List ShowNewDocTree(String parentId, UserInfo userInfo);
public abstract List ShowNewDocTable(String folderId, UserInfo userInfo);
public abstract List ShowRecycleTable(String docGrade, UserInfo userInfo);
public abstract String ClearDocs(String docIds, String docGrade);
public abstract String RevertDocs(String docIds, String docGrade);
public abstract List ShowSearchTable(String userId, String keywords,
		String createdDate1, String createdDate2, String editedDate1,
		String editedDate2, String createdBy, String assignedTo,
		String approvedBy, String editedBy, String author, String keywordsType,
		String docType, String projId);
public abstract String getTempFileName(String fileExtName);
public abstract String saveDoc(File uploadfile, String url);
public abstract String downPackageDoc(String docGrade, String folderId,
		UserInfo userInfo);
public abstract String GetActualNameUrlById(String docId, String type, String userId);
public abstract String MoveDoc(String docIds, String folderId, String docGrade,
		String projId);
public abstract void AddUserFile(String fileName, String fileType,
		String filePath, String userId, String id);
public abstract List<Map<String, Object>> PlusFileTable(UserInfo userInfo,
		int start, int number);
public abstract Object getPlusFileTableCount(UserInfo userInfo);
public abstract String DeletePlusFile(String userfileId);
public abstract String  getNewDocSql(String proj_id,String user_Id);
public abstract String copyDoc(String docId, String type, UserInfo userInfo);
public abstract List ShowDocTable(String folderId, String docGrade,
		String isAll, String keyWord, UserInfo userInfo);
}