package com.epmis.km.service.imp;

import com.epmis.km.service.FdmdService;
import com.epmis.km.vo.KmFdmd;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.SwbsService;
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.FileUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.vo.SmSwbs;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("fdmdService")
public class FdmdServiceImpl implements FdmdService
{



  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;
	 
	@Override
	public String AddFdmd(KmFdmd kmFdmd) {
		String sql = " INSERT INTO KM_FDMD(WID,FOLDER_ID,TITLE,DEF_CODE,SEQ_NUM,DEF_FRACTION,NOTLESS_FLAG,DESCRIPTION,PARENT_FOLDER_ID,FDMD_TYPE_ID) " +
				"VALUES ('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+kmFdmd.getTitle()+"','"+kmFdmd.getDefCode()+"',"+kmFdmd.getSeqNum()+",'"+kmFdmd.getDefFraction()+"','N','"+kmFdmd.getDescription()+"','"+kmFdmd.getParentFolderId()+"','"+kmFdmd.getFdmdTypeId()+"')";
		baseJdbcDao.insert(sql);
		return  "success";
	}
	
	@Override
	public String DelFdmdType(String FdmdTypeId) {
		String result ="";
		String sql  =  "DELETE FROM KM_FDMD WHERE FDMD_TYPE_ID = '"+FdmdTypeId+"'";
		boolean res =  baseJdbcDao.delete(sql);
		sql = "DELETE FROM KM_FDMDTYPE WHERE FDMD_TYPE_ID = '"+FdmdTypeId+"'";
		res =  baseJdbcDao.delete(sql);
		if(res){
		   result = "success";
		}else{
		   result ="删除失败！";
		}
		return result;
	}
	
	@Override
	public Map<String, Object> GetFdmd(String FdmdId) {
		String sql = "SELECT * FROM KM_FDMD WHERE FOLDER_ID ='"+FdmdId+"'";
		return baseJdbcDao.queryMap(sql);
	}
	
	@Override
	public List<Map<String, Object>> ShowFdmdTree(String ParentId,String Fdmd_type_id) {	
			List<Map<String, Object>> items = findTreeBySql(ParentId,Fdmd_type_id);
		    for (Map<String, Object> item : items) { 
		 
				 item.put("iconCls", "icon-folderClose"); 
				 if(Integer.valueOf( item.get("COUNT").toString())>0){
					 item.put("state", "closed");
				 }
		     }
		   
		    return items;
	}
		 public List<Map<String, Object>> findTreeBySql(String ParentId,String Fdmd_type_id){

			  List<Map<String, Object>> result = null;
			  if ((DataTypeUtil.validate(ParentId))){
				  String sql = " SELECT FOLDER_ID,TITLE,DEF_CODE,DEF_FRACTION,NOTLESS_FLAG,(SELECT COUNT(B.FOLDER_ID) FROM KM_FDMD B WHERE B.PARENT_FOLDER_ID=A.FOLDER_ID AND FDMD_TYPE_ID='"+Fdmd_type_id+"') AS COUNT  FROM KM_FDMD A WHERE FDMD_TYPE_ID='"+Fdmd_type_id+"' AND PARENT_FOLDER_ID = '"+ParentId+"'  ORDER BY SEQ_NUM";				   
			      result =  baseJdbcDao.queryListMap(sql); 
			  }
			return result;
			}
	@Override
	public List ShowFdmdTypeTable() {
	  	  return baseJdbcDao.queryListMap("SELECT FDMD_TYPE_ID,FDMD_TYPE_NAME,DESCRIPTION FROM KM_FDMDTYPE ORDER BY FDMD_TYPE_NAME ");
	  	 
	}
	
	@Override
	public String UpdateFdmd(KmFdmd kmFdmd) {
		 String sql = "update KM_FDMD set title='"+kmFdmd.getTitle()+"',DEF_CODE = '"+kmFdmd.getDefCode()+"', DEF_FRACTION = "+kmFdmd.getDefFraction()+",SEQ_NUM="+kmFdmd.getSeqNum()+",DESCRIPTION='"+kmFdmd.getDescription()+"' where folder_id = '"+kmFdmd.getFolderId()+"' ";
		 if(baseJdbcDao.update(sql)){
			 return "success";  
		 }else{
				return "保存失败";
		 }
	}
	
	@Override
	public String delFdmd(String FdmdId) {
		String sql = "DELETE FROM KM_FDMD WHERE FOLDER_ID ='"+FdmdId+"'";
		baseJdbcDao.delete(sql);
		return  "success";
	}
 
 
} 