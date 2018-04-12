package com.epmis.km.service;

import java.util.List;
import java.util.Map;

import com.epmis.km.vo.KmFolder;
import com.epmis.sys.util.UserInfo;

public abstract interface KmFolderService
{
	public  abstract Map<String, Object>  getFolderInfoByFolderId(String folderId);
	public  abstract String  getFullPathByFolderId(String folderId);
	public  abstract String  getFullPathByDocId(String docId);
	public abstract List ShowFolderTree(String parentId, String docGrade,
			UserInfo userInfo);
	public abstract String AddFolder(UserInfo user, KmFolder kmFolder);
	public abstract Map<String, Object> GetFolder(String folderId);
	public abstract String UpdateFolder(KmFolder kmFolder);
	public abstract String DeleteFolder(String folderId, String docGrade, String projId);
	public  abstract String  getFullPathNameByFolderId(String folderId);
	public abstract String UpdatePath(String docGrade); 
}