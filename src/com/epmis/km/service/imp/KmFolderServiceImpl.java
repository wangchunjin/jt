package com.epmis.km.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.epmis.km.service.KmFolderService;
import com.epmis.km.vo.KmFolder;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.DateUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("kmFolderService")
public class KmFolderServiceImpl implements KmFolderService
{
	  @Autowired
	  @Qualifier("baseJdbcDao")
	  private BaseJdbcDao baseJdbcDao;
	  public  Map<String, Object>  getFolderInfoByFolderId(String folderId)
	  { 
	    String str = "SELECT * FROM KM_FOLDER WHERE FOLDER_ID='" + folderId + "'";
	    Map localMap = baseJdbcDao.queryMap(str);
	    return localMap;
	  }
	@Override
	public String getFullPathByDocId(String docId) {
		String sql = "select folder_id from km_doc where doc_id = '"+docId+"'";
		String folderId = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
		
		return getFullPathByFolderId( folderId,"");
	}
	public String getFullPathByFolderId(String folderId) {

		return getFullPathByFolderId( folderId,"");
	}
	public String getFullPathByFolderId(String folderId,String path){

	    String str = "SELECT * FROM KM_FOLDER WHERE FOLDER_ID='" + folderId + "'";
	    Map localMap = baseJdbcDao.queryMap(str);
        if (DataTypeUtil.validate(localMap))
        {
          String str1 = DataTypeUtil.formatDbColumn(localMap.get("PARENT_FOLDER_ID"));
          String str2 = DataTypeUtil.formatDbColumn(localMap.get("FOLDER_NAME"));
          path = "/"+str2+path;
          path = getFullPathByFolderId(str1, path);
        }
		return path;		
	}

	@Override
	public String getFullPathNameByFolderId(String folderId) {
		return getFullPathNameByFolderId( folderId,"");
	}
	public String getFullPathNameByFolderId(String folderId,String path){

	    String str = "SELECT * FROM KM_FOLDER WHERE FOLDER_ID='" + folderId + "'";
	    Map localMap = baseJdbcDao.queryMap(str);
        if (DataTypeUtil.validate(localMap))
        {
          String str1 = DataTypeUtil.formatDbColumn(localMap.get("PARENT_FOLDER_ID"));
          String str2 = DataTypeUtil.formatDbColumn(localMap.get("TITLE"));
          path = "/"+str2+path;
          path = getFullPathNameByFolderId(str1, path);
        }
		return path;		
	}
	@Override
	public List ShowFolderTree(String parentId, String docGrade,
			UserInfo userInfo) {
		 
		return findChildKmFolderByParentId(parentId,docGrade,userInfo);
	}
	public List<Map<String, Object>> findChildKmFolderByParentId(String parentId,String docGrade,UserInfo userInfo)
	  {
		  List<Map<String, Object>> ChildrenList = findTreeBySql(parentId,docGrade,userInfo);
	    List items = new ArrayList();
	    for (Map<String, Object> item : ChildrenList) {
	 
			 item.put("iconCls", "icon-folderClose"); 
			 if(Integer.valueOf( item.get("CHILDNUM").toString())>0){
				 item.put("state", "closed");
			 }
			  items.add(item);
	     }
	   
	    return items;
	  }
	  public List<Map<String, Object>> findTreeBySql(String ParentId,String docGrade,UserInfo userInfo){

		  List<Map<String, Object>> result = null;
		  if ((DataTypeUtil.validate(ParentId))){
			  String sql = "";
			  if(docGrade.equals("0")){
		       sql = "SELECT TITLE,(select count(wid) from km_folder where  PARENT_FOLDER_ID = K.FOLDER_ID) AS CHILDNUM, K.FOLDER_ID ,DEF_CODE,DEF_FRACTION,doc_grade FROM km_folder K where  doc_grade='0' and parent_folder_id ='"+ParentId+"' order by seq_num";
			  }else{
			       sql = "SELECT TITLE,(select count(wid) from km_folder where  PARENT_FOLDER_ID = K.FOLDER_ID) AS CHILDNUM, K.FOLDER_ID ,DEF_CODE,DEF_FRACTION,doc_grade FROM km_folder K where  doc_grade='2' and parent_folder_id ='"+ParentId+"' and proj_id = '"+userInfo.getProjId()+"' order by seq_num";				  
			  }
		      result =  baseJdbcDao.queryListMap(sql); 
		  }
		return result;
		}

	@Override
	public String AddFolder(UserInfo user, KmFolder kmFolder) { 
		if(DataTypeUtil.validate(kmFolder.getTitle())&&DataTypeUtil.validate(kmFolder.getDefCode())){
			String sql = "select FOLDER_ID_PATH from KM_FOLDER where FOLDER_ID = '"+kmFolder.getParentFolderId()+"'";
			String parentPath = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
			String folderId = Guid.getGuid();
			if(kmFolder.getDocGrade().equals("0")){ 
			  sql  = " INSERT INTO KM_FOLDER(WID,DEF_CODE,DESCRIPTION,SEQ_NUM,DOC_GRADE,DELETED_FLAG,FOLDER_ID_PATH,FOLDER_ID,DEF_FRACTION,PARENT_FOLDER_ID,NOTLESS_FLAG,FOLDER_NAME,CREATED_BY,CREATED_DATE,ORIGINAL_NODE,TITLE) VALUES" +
					"                            ('"+Guid.getGuid()+"','"+kmFolder.getDefCode()+"','"+kmFolder.getDescription()+"',"+kmFolder.getSeqNum()+",'"+kmFolder.getDocGrade()+"','0','"+parentPath+","+folderId+"','"+folderId+"',"+kmFolder.getDefFraction()+",'"+kmFolder.getParentFolderId()+"','0','"+getTempFileName()+"','"+user.getUserId()+"',now(),'0','"+kmFolder.getTitle()+"')";
			}else{
				  sql  = " INSERT INTO KM_FOLDER(WID,DEF_CODE,DESCRIPTION,SEQ_NUM,DOC_GRADE,DELETED_FLAG,FOLDER_ID_PATH,FOLDER_ID,DEF_FRACTION,PARENT_FOLDER_ID,NOTLESS_FLAG,FOLDER_NAME,CREATED_BY,CREATED_DATE,PROJ_ID,ORIGINAL_NODE,TITLE) VALUES" +
					"                            ('"+Guid.getGuid()+"','"+kmFolder.getDefCode()+"','"+kmFolder.getDescription()+"',"+kmFolder.getSeqNum()+",'"+kmFolder.getDocGrade()+"','0','"+parentPath+","+folderId+"','"+folderId+"',"+kmFolder.getDefFraction()+",'"+kmFolder.getParentFolderId()+"','0','"+getTempFileName()+"','"+user.getUserId()+"',now(),'"+user.getProjId()+"','0','"+kmFolder.getTitle()+"')";				
			}
			  baseJdbcDao.insert(sql);
			return "success";
		}else{
			return "目录名称、默认代码任一不能为空！";
		}
		
	}
	  public static String getTempFileName()
	  {
	    int i = (int)Math.floor(Math.random() * 1000.0D);
	    String str = DateUtil.getTodaytime().replace("-", "").replace(" ", "").replace(":", "") + i;
	    return str;
	  }

	@Override
	public Map<String, Object> GetFolder(String folderId) {
		
		return baseJdbcDao.queryMap("SELECT * FROM KM_FOLDER WHERE FOLDER_ID = '"+folderId+"'");
	}

	@Override
	public String UpdateFolder(KmFolder kmFolder) { 
		 String sql = "update KM_FOLDER set title='"+kmFolder.getTitle()+"',DEF_CODE = '"+kmFolder.getDefCode()+"', DEF_FRACTION = "+kmFolder.getDefFraction()+",SEQ_NUM="+kmFolder.getSeqNum()+",DESCRIPTION='"+kmFolder.getDescription()+"' where folder_id = '"+kmFolder.getFolderId()+"' ";
		 if(baseJdbcDao.update(sql)){
			 return "success";  
		 }else{
				return "保存失败";
		 }

	}

	@Override 
	public String DeleteFolder(String folderId, String docGrade, String projId) { 
		String sql ="";
		String delsql ="";
		if(docGrade.equals("0")){
		     sql = "select count(wid) from km_doc where folder_Id in (select folder_id from km_folder where instr(FOLDER_ID_PATH,'"+folderId+"')>0 and doc_grade='0' ) and doc_grade='0'"; 
				delsql = "delete from KM_FOLDER where instr(FOLDER_ID_PATH,'"+folderId+"')>0 ";
	    }else{
		     sql = "select count(wid) from km_doc where folder_Id in (select folder_id from km_folder where instr(FOLDER_ID_PATH,'"+folderId+"')>0 and doc_grade='2' and proj_id ='"+projId+"') and doc_grade='2' and proj_id ='"+projId+"'"; 
			delsql = "delete from KM_FOLDER where instr(FOLDER_ID_PATH,'"+folderId+"')>0 and doc_grade='2'  and proj_id ='"+projId+"' ";
		}
		if(baseJdbcDao.getCount(sql)>0){
			return "该节点或子节点下存在文档，禁止删除！";
		}else{			
			baseJdbcDao.delete(delsql);
			return "success";
		}
	}
	@Override
	public String UpdatePath(String docGrade) {
		if(docGrade.equals("0")){
			// 更新公共目录Path：
			 UpdateChileNode("569EB056E4C14392981C4E448E8CB8E8","569EB056E4C14392981C4E448E8CB8E8");
			
		}else{
		    // 更新项目目录Path：
			 UpdateChileNode("2B44E7C053F64B24877F6E79775F4AFD","2B44E7C053F64B24877F6E79775F4AFD");

		}
		return "success";
	}

	  public void UpdateChileNode(String parentId,String parentPath){ 
		  String sql = "select FOLDER_ID from km_folder where PARENT_FOLDER_ID = '"+parentId+"'";
		  List<Map<String, Object>> list = baseJdbcDao.queryListMap(sql);
		  for(Map<String, Object> map :list){
			  String FOLDER_ID = DataTypeUtil.formatDbColumn(map.get("FOLDER_ID"));
			  baseJdbcDao.update("update km_folder set FOLDER_ID_PATH='"+parentPath+";"+FOLDER_ID+"' where FOLDER_ID= '"+FOLDER_ID+"' ");
			  UpdateChileNode(FOLDER_ID, parentPath+","+FOLDER_ID);
		  }
		  
	  }

} 