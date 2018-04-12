package com.epmis.km.service.imp;

import com.epmis.km.service.KmDocService;
import com.epmis.km.service.KmFolderService;
import com.epmis.km.vo.KmAttch;
import com.epmis.km.vo.KmDoc;
import com.epmis.sm.service.SmPlanService;
import com.epmis.sm.vo.SmPlan;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.UserService;
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.DESEncrypt;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.DateUtil;
import com.epmis.sys.util.FileUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.Logger;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("kmDocService")
public class KmDocServiceImpl implements KmDocService
{



  @Autowired
  @Qualifier("baseJdbcDao") 
  private BaseJdbcDao baseJdbcDao;
  
  @Autowired
  private KmFolderService kmFolderService;	
  
  public List<Map<String, Object>> ShowDocTree(String parentId,String docGrade,UserInfo userInfo)
  {
	   
    List items = new ArrayList();
    items = findChildKmDocByParentId(parentId,docGrade,userInfo);
    //	baseJdbcDao.queryListMap(sql)
/*    if(DataTypeUtil.validate(parentId)){
     
    }*/
    return items;
  }
 
  public List<Map<String, Object>> findChildKmDocByParentId(String parentId,String moduleCode,UserInfo userInfo)
  {
	  List<Map<String, Object>> ChildrenList = findTreeBySql(parentId,moduleCode,userInfo);
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
  public List<Map<String, Object>> findTreeBySql(String ParentId,String DocGrade,UserInfo userInfo){

	  List<Map<String, Object>> result = null;
	  String folder_id = "";
	  String sql = "";
	   if(DocGrade.equals("0")){
		   sql = "SELECT TITLE,(select count(wid) from km_folder where  PARENT_FOLDER_ID = k.FOLDER_ID) AS CHILDNUM,"+
       
       " k.FOLDER_ID,"+
       " k.PARENT_FOLDER_ID"+
       " FROM KM_FOLDER k"+
       " WHERE k.DOC_GRADE = '0'"+
       " AND k.DELETED_FLAG != '1'"+
       " AND k.PARENT_FOLDER_ID = '"+ParentId+"'"+
       " ORDER BY SEQ_NUM ASC, TITLE ASC";	
		   result = baseJdbcDao.queryListMap(sql);
		   if(DataTypeUtil.validate(result)){
			   for(Map<String, Object> map :result){
				   folder_id = DataTypeUtil.formatDbColumn(map.get("FOLDER_ID"));
				   sql = "  SELECT count(A.DOC_ID)"+
			       " FROM KM_DOC A"+
			       " WHERE A.FOLDER_ID IN"+
			       " (SELECT FOLDER_ID"+
			       " FROM KM_FOLDER"+
			       " WHERE  INSTR(FOLDER_ID_PATH, '"+folder_id+"') > 0 AND DOC_GRADE = '0')"+
			       " and A.DELETED_FLAG != '1' AND A.DOC_GRADE = '0'"+
			       "  AND (A.CREATED_BY = '"+userInfo.getUserId()+"' OR"+
			       " A.ASSIGNED_TO = '"+userInfo.getUserId()+"' OR"+
			       " A.APPROVED_BY = '"+userInfo.getUserId()+"' OR"+
			       " ((A.FRACTION >="+
			       " (SELECT LOW_FRACTION"+
			       " FROM CM_USERS"+
			       " WHERE USER_ID = '"+userInfo.getUserId()+"') AND"+
			       " A.FRACTION <="+
			       " (SELECT HIGH_FRACTION"+
			       "  FROM CM_USERS"+
			       "  WHERE USER_ID = '"+userInfo.getUserId()+"')) AND"+
			       " A.STATUS = 'APPROVED')) ";
				   map.put("NUM", baseJdbcDao.getCount(sql));
			   }
		   }
	   }else{
		sql="SELECT TITLE,(select count(wid) from km_folder where  PARENT_FOLDER_ID = k.FOLDER_ID and proj_id = '"+userInfo.getProjId()+"') AS CHILDNUM,"+
		" (SELECT count(A.DOC_ID)"+
		" FROM KM_DOC A"+
		" WHERE A.FOLDER_ID IN"+
		" (SELECT FOLDER_ID"+
		" FROM KM_FOLDER"+
		"  WHERE proj_id = '"+userInfo.getProjId()+"'"+
		" and instr(FOLDER_ID_PATH,"+
		"  '"+ParentId+"') > 0"+
		" and INSTR(FOLDER_ID_PATH, k.folder_id) > 0 AND PROJ_ID = '"+userInfo.getProjId()+"')"+
		" and A.DELETED_FLAG != '1' AND A.PROJ_ID = '"+userInfo.getProjId()+"'"+
		" AND (A.CREATED_BY = '"+userInfo.getUserId()+"' OR"+
		" A.ASSIGNED_TO = '"+userInfo.getUserId()+"' OR"+
		"  A.APPROVED_BY = '"+userInfo.getUserId()+"' OR"+
		" ((A.FRACTION >="+
		" (SELECT LOW_FRACTION"+
		" FROM CM_USERS"+
		" WHERE USER_ID = '"+userInfo.getUserId()+"') AND"+
		"  A.FRACTION <="+
		"  (SELECT HIGH_FRACTION"+
		"      FROM CM_USERS"+
		"    WHERE USER_ID = '"+userInfo.getUserId()+"')) AND"+
		"   A.STATUS = 'APPROVED'))) as NUM,"+
		" k.FOLDER_ID,"+
		"  k.PARENT_FOLDER_ID"+
		" FROM KM_FOLDER k"+
		" WHERE k.DOC_GRADE = '2'"+
		" AND k.DELETED_FLAG != '1'"+
		" AND k.PROJ_ID IN (SELECT PROJ_ID FROM CM_PROJ)"+
		" AND k.PROJ_ID = '"+userInfo.getProjId()+"'"+
		"  AND k.PARENT_FOLDER_ID = '"+ParentId+"'"+
		" ORDER BY SEQ_NUM ASC, TITLE ASC";   
		result = baseJdbcDao.queryListMap(sql);
	   }
	   
	return result;
	}
	@Override
	public List ShowNewDocTree(String parentId, UserInfo userInfo) {
		  List<Map<String, Object>> items = findNewTreeBySql(parentId,userInfo);
		    for (Map<String, Object> item : items) {		
		    	if(DataTypeUtil.formatDbColumn(item.get("PROJ_NODE_FLAG")).equals("Y")){
		    		item.put("iconCls", "icon-proj"); 
		    	}else{
				 item.put("iconCls", "icon-folderClose"); 
		    	}
				 if(Integer.valueOf( item.get("CHILDNUM").toString())>0){
					 item.put("state", "closed");
				 }
		     }		   
		    return items;
	}
  private List<Map<String, Object>> findNewTreeBySql(String parentId,
			UserInfo userInfo) { 
	  List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		if(parentId.equals("0")){
			Map<String, Object> map =new  HashMap<String, Object>();
			map.put("TITLE", "公共文档");
			map.put("NUM", baseJdbcDao.queryListMap(getNewDocSql("ggwd", userInfo.getUserId())).size());
			map.put("FOLDER_ID", "ggwd");
			map.put("CHILDNUM", "0");
			items.add(map);
			map =new  HashMap<String, Object>();
			map.put("TITLE", "项目文档");
			map.put("NUM",  baseJdbcDao.queryListMap(getNewDocSql("xmwd", userInfo.getUserId())).size());
			map.put("FOLDER_ID", "xmwd");
			map.put("CHILDNUM", "1");
			items.add(map);
		}else{
			if(parentId.equals("xmwd")){
				parentId ="0";
			}
			String sql = " SELECT PROJ_ID FOLDER_ID,PROJ_SHORT_NAME ,PROJ_NAME TITLE,PROJ_CMPT_PCT,PROJ_NODE_FLAG,STATUS,SEQ_NUM,(SELECT B.CODE_NAME FROM CM_CODE B WHERE B.CODE_TYPE = 'PROJ_STAGE' AND B.CODE_SHORT_NAME = A.PROJ_STAGE) AS PROJ_STAGE,(SELECT COUNT(PROJ_ID) FROM CM_PROJ WHERE PROJ_NODE_FLAG = 'N' AND PARENT_PROJ_ID=A.PROJ_ID) AS EPS_COUNT,(SELECT COUNT(PROJ_ID) FROM CM_PROJ WHERE PROJ_NODE_FLAG = 'Y' AND PARENT_PROJ_ID = A.PROJ_ID AND PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '"+userInfo.getUserId()+"' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+userInfo.getUserId()+"')) ) AS RS_COUNT FROM CM_PROJ A WHERE  (PROJ_NODE_FLAG = 'N' OR (PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '"+userInfo.getUserId()+"' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+userInfo.getUserId()+"')) ))AND A.PARENT_PROJ_ID = '"+parentId+"' ORDER BY A.PROJ_NODE_FLAG DESC, A.SEQ_NUM";
			items =  baseJdbcDao.queryListMap(sql);
			if(DataTypeUtil.validate(items)){
				for (Map<String, Object> item : items) {		 
					 item.put("iconCls", "icon-folderClose"); 
					 if(Integer.valueOf( item.get("EPS_COUNT").toString())>0||Integer.valueOf( item.get("RS_COUNT").toString())>0){
						 item.put("CHILDNUM", "1");
					 }else{
						 item.put("CHILDNUM", "0");
					 }
					 item.put("NUM", baseJdbcDao.queryListMap(getNewDocSql(item.get("FOLDER_ID").toString(), userInfo.getUserId())).size());
			     }
			}
		}
		return items;
	}
	@Autowired
    private UserService userService;

	
  public String getNewDocSql(String proj_id,String user_Id)
	{
		int newDocDays = 30;
		String startDate = "";
		String sql = "";
		 
			 
			String low_fraction = "";
			String high_fraction = "";
			String newdoc_days = "";
			Map<String,Object> userMap = userService.GetUserInfoById(user_Id);
			if (DataTypeUtil.validate(userMap))
			{
				low_fraction = DataTypeUtil.formatDbColumn(userMap.get("LOW_FRACTION"));
				high_fraction = DataTypeUtil.formatDbColumn(userMap.get("HIGH_FRACTION"));
				newdoc_days = DataTypeUtil.formatDbColumn(userMap.get("NEWDOC_DAYS"));
			}
			if (DataTypeUtil.validate(newdoc_days))
				newDocDays = Integer.valueOf(DataTypeUtil.formatDbColumn(newdoc_days));
			// 添加最新打开文档的天数后的日期
			startDate = DateUtil.addDate(DateUtil.parseDate(DateUtil.getTodaydate()), newDocDays * -1, Calendar.DAY_OF_MONTH, "yyyy-MM-dd");

			if (proj_id.equals("ggwd"))
			{
				sql = "SELECT A.*,date_format(A.CREATED_DATE,'%Y-%m-%d')CREATED_DATE,date_format(A.EDITED_DATE,'%Y-%m-%d')EDITED_DATE ,(SELECT ACTUAL_NAME FROM CM_USERS E WHERE A.CREATED_BY=E.USER_ID) AS ACTUAL_NAME,"//
						+ "B.REMARK,CASE A.CREATED_BY WHEN '" + user_Id + "' THEN 'Y' ELSE 'N' END AS CREATED_FLAG,"//
						+ "CASE A.ASSIGNED_TO " + "WHEN '" + user_Id + "' THEN 'Y' ELSE 'N' END AS ASSIGNED_FLAG," //
						+ "CASE WHEN A.FRACTION>='" + low_fraction + "' AND A.FRACTION<='" + high_fraction + "' " //
						+ "THEN 'Y' ELSE 'N' END AS FRACTION_FLAG,D.TITLE AS FOLDER_NAME " //
						+ "FROM (SELECT PROJ_ID,DOC_ID,DOC_TYPE,FOLDER_ID,DELETED_FLAG,CREATED_BY,ASSIGNED_TO,FRACTION,DOC_GRADE,EDITED_DATE,STATUS,CREATED_DATE,DOC_NUMBER,TITLE FROM km_doc  WHERE  DATE_FORMAT(EDITED_DATE, '%Y-%m-%d') > '"+startDate+"' AND DOC_GRADE='0' AND DELETED_FLAG!='1' ) A LEFT OUTER JOIN CM_CODE B ON A.DOC_TYPE=B.CODE_NAME " //
						+ "AND B.CODE_TYPE='DOC_TYPE' LEFT OUTER JOIN KM_FOLDER D ON A.FOLDER_ID=D.FOLDER_ID " //
						+ "WHERE  A.DOC_GRADE='0' AND A.DELETED_FLAG!='1' AND date_format(A.EDITED_DATE,'%Y-%m-%d')  >  '" + startDate + "' " //
						+ "AND (A.CREATED_BY='" + user_Id + "' " + "OR A.ASSIGNED_TO='" + user_Id + "' OR ((A.FRACTION>='" + low_fraction + "' " //
						+ "AND A.FRACTION<='" + high_fraction + "') )) AND A.STATUS='APPROVED' ORDER BY A.EDITED_DATE DESC";//
			} else if(proj_id.equals("xmwd"))
			{
				sql = "SELECT A.*,(SELECT ACTUAL_NAME FROM CM_USERS E WHERE A.CREATED_BY=E.USER_ID) AS ACTUAL_NAME,"//
					+ "C.PROJ_SHORT_NAME,B.REMARK,CASE A.CREATED_BY WHEN '" + user_Id + "' THEN 'Y' ELSE 'N' END AS CREATED_FLAG," //
					+ "CASE A.ASSIGNED_TO WHEN '" + user_Id + "' THEN 'Y' ELSE 'N' " + "END AS ASSIGNED_FLAG,"//
					+ "CASE WHEN A.FRACTION>='" + low_fraction + "' " + "AND A.FRACTION<='" + high_fraction + "' "//
					+ "THEN 'Y' ELSE 'N' END AS FRACTION_FLAG,D.TITLE AS FOLDER_NAME "//
					+ "FROM (SELECT PROJ_ID,DOC_TYPE,DOC_ID,FOLDER_ID,DELETED_FLAG,CREATED_BY,ASSIGNED_TO,FRACTION,DOC_GRADE,EDITED_DATE,STATUS,CREATED_DATE,DOC_NUMBER,TITLE FROM km_doc  WHERE  DATE_FORMAT(EDITED_DATE, '%Y-%m-%d') > '"+startDate+"' AND DOC_GRADE='2' AND DELETED_FLAG!='1') A LEFT OUTER JOIN CM_CODE B ON A.DOC_TYPE=B.CODE_NAME " //
					+ "AND B.CODE_TYPE='DOC_TYPE' LEFT OUTER JOIN CM_PROJ C ON A.PROJ_ID = C.PROJ_ID "//
					+ "LEFT OUTER JOIN KM_FOLDER D ON A.FOLDER_ID=D.FOLDER_ID WHERE A.DOC_GRADE='2' " //
					+ "AND A.DELETED_FLAG!='1' AND date_format(A.EDITED_DATE,'%Y-%m-%d')  >  '" + startDate + "'  " //
					+ "AND C.PROJ_ID  in (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID='" + user_Id + "' " + 
					"OR ROLE_ID IN(SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID='" + user_Id + "') " 
					+ ") AND (A.CREATED_BY='" + user_Id + "' OR A.ASSIGNED_TO='" + user_Id + "' "//
					+ "OR ((A.FRACTION>='" + low_fraction + "' " + "AND A.FRACTION<='" + high_fraction + "') )) AND A.STATUS='APPROVED' " //
					+ "ORDER BY C.PROJ_ID,A.EDITED_DATE DESC";
			}else if(proj_id.startsWith("proj_"))
			{
				sql = "SELECT A.*,date_format(A.CREATED_DATE,'%Y-%m-%d')CREATED_DATE,date_format(A.EDITED_DATE,'%Y-%m-%d')EDITED_DATE ,(SELECT ACTUAL_NAME FROM CM_USERS E WHERE A.CREATED_BY=E.USER_ID) AS ACTUAL_NAME,"//
					+ "C.PROJ_SHORT_NAME,B.REMARK,CASE A.CREATED_BY WHEN '" + user_Id + "' THEN 'Y' ELSE 'N' END AS CREATED_FLAG," //
					+ "CASE A.ASSIGNED_TO WHEN '" + user_Id + "' THEN 'Y' ELSE 'N' " + "END AS ASSIGNED_FLAG,"//
					+ "CASE WHEN A.FRACTION>='" + low_fraction + "' " + "AND A.FRACTION<='" + high_fraction + "' "//
					+ "THEN 'Y' ELSE 'N' END AS FRACTION_FLAG,D.TITLE AS FOLDER_NAME "//
					+ "FROM (SELECT PROJ_ID,DOC_TYPE,FOLDER_ID,DOC_ID,DELETED_FLAG,CREATED_BY,ASSIGNED_TO,FRACTION,DOC_GRADE,EDITED_DATE,STATUS,CREATED_DATE,DOC_NUMBER,TITLE FROM km_doc  WHERE  DATE_FORMAT(EDITED_DATE, '%Y-%m-%d') > '"+startDate+"' AND DOC_GRADE='2' AND DELETED_FLAG!='1') A LEFT OUTER JOIN CM_CODE B ON A.DOC_TYPE=B.CODE_NAME " //
					+ "AND B.CODE_TYPE='DOC_TYPE' LEFT OUTER JOIN CM_PROJ C ON A.PROJ_ID = C.PROJ_ID "//
					+ "LEFT OUTER JOIN KM_FOLDER D ON A.FOLDER_ID=D.FOLDER_ID WHERE A.DOC_GRADE='2' " //
					+ "AND A.DELETED_FLAG!='1' AND date_format(A.EDITED_DATE,'%Y-%m-%d')  >  '" + startDate + "' " //
					+ "AND C.PROJ_ID IN(" + 
						"SELECT PROJ_ID FROM CM_PROJ A WHERE (PROJ_NODE_FLAG = 'N' OR  (PROJ_ID IN   (SELECT PROJ_ID  FROM CM_USERPROJ  " +
						"WHERE USER_ID = '" + user_Id + "'  OR ROLE_ID IN  (SELECT ROLE_ID  FROM CM_RLUSER  WHERE " +
						"USER_ID = '" + user_Id + "'))))" +"AND  PROJ_ID ='"+proj_id.replace("proj_", "")+"'    "
						+ ")  AND (A.CREATED_BY='" + user_Id + "' OR A.ASSIGNED_TO='" + user_Id + "' "//
					+ "OR ((A.FRACTION>='" + low_fraction + "' " + "AND A.FRACTION<='" + high_fraction + "') )) AND A.STATUS='APPROVED' " //
					+ "ORDER BY C.PROJ_ID,A.EDITED_DATE DESC";
			}
			else
			{
				sql = "SELECT A.*,(SELECT ACTUAL_NAME FROM CM_USERS E WHERE A.CREATED_BY=E.USER_ID) AS ACTUAL_NAME,"//
						+ "C.PROJ_SHORT_NAME,B.REMARK,CASE A.CREATED_BY WHEN '" + user_Id + "' THEN 'Y' ELSE 'N' END AS CREATED_FLAG," //
						+ "CASE A.ASSIGNED_TO WHEN '" + user_Id + "' THEN 'Y' ELSE 'N' " + "END AS ASSIGNED_FLAG,"//
						+ "CASE WHEN A.FRACTION>='" + low_fraction + "' " + "AND A.FRACTION<='" + high_fraction + "' "//
						+ "THEN 'Y' ELSE 'N' END AS FRACTION_FLAG,D.TITLE AS FOLDER_NAME "//
						+ "FROM (SELECT PROJ_ID,DOC_TYPE,DOC_ID,FOLDER_ID,DELETED_FLAG,CREATED_BY,ASSIGNED_TO,FRACTION,DOC_GRADE,EDITED_DATE,STATUS,CREATED_DATE,DOC_NUMBER,TITLE FROM km_doc  WHERE  DATE_FORMAT(EDITED_DATE, '%Y-%m-%d') > '"+startDate+"') A LEFT OUTER JOIN CM_CODE B ON A.DOC_TYPE=B.CODE_NAME " //
						+ "AND B.CODE_TYPE='DOC_TYPE' LEFT OUTER JOIN CM_PROJ C ON A.PROJ_ID = C.PROJ_ID "//
						+ "LEFT OUTER JOIN KM_FOLDER D ON A.FOLDER_ID=D.FOLDER_ID WHERE A.DOC_GRADE='2' " //
						+ "AND A.DELETED_FLAG!='1' AND date_format(A.EDITED_DATE,'%Y-%m-%d')  >  '" + startDate + "' " //
						+ "AND C.PROJ_ID IN(" + 
						"SELECT PROJ_ID FROM CM_PROJ A WHERE (PROJ_NODE_FLAG = 'N' OR  (PROJ_ID IN   (SELECT PROJ_ID  FROM CM_USERPROJ  " +
						"WHERE USER_ID = '" + user_Id + "'  OR ROLE_ID IN  (SELECT ROLE_ID  FROM CM_RLUSER  WHERE " +
						"USER_ID = '" + user_Id + "'))))" +"AND INSTR(A.PARENT_PATH , '"+proj_id+"')>0   "
						+ ") AND (A.CREATED_BY='" + user_Id + "' OR A.ASSIGNED_TO='" + user_Id + "' "//
						+ "OR ((A.FRACTION>='" + low_fraction + "' " + "AND A.FRACTION<='" + high_fraction + "') )) AND A.STATUS='APPROVED' " //
						+ "ORDER BY C.PROJ_ID,A.EDITED_DATE DESC";
			}
		   
		return sql;
	}
	@Override
	public List ShowNewDocTable(String folderId, UserInfo userInfo) {
		if(folderId.equals("ggwd")){
		  return	baseJdbcDao.queryListMap(getNewDocSql("ggwd", userInfo.getUserId()));
		}else{
		  return	baseJdbcDao.queryListMap(getNewDocSql("proj_"+folderId, userInfo.getUserId()));
		}
	}
@Override
  public List ShowDocTable(String folderId, String docGrade, String isAll,
  		UserInfo userInfo) {
	  String sql ="";
  	if(docGrade.equals("0")){
  		 String FoldString = "'"+folderId+"'";
  		 if(DataTypeUtil.validate(isAll)&&isAll.equals("1")){
  			FoldString = "SELECT FOLDER_ID  FROM KM_FOLDER  WHERE  INSTR(FOLDER_ID_PATH, '"+folderId+"') > 0 ";
  		 }
  		 sql ="SELECT '' AS IMG,A.CERTIFIED_TYPE,(SELECT ACTUAL_NAME FROM CM_USERS E WHERE A.CREATED_BY=E.USER_ID) AS ACTUAL_NAME,"+
			" A.WID,A.FILE_NAME,A.DOC_ID,A.DOC_NUMBER,A.TITLE,A.EDITION,A.STATUS,"+
			" A.FRACTION,A.AUTHOR,date_format(A.CREATED_DATE,'%Y-%m-%d') CREATED_DATE ,date_format(A.EDITED_DATE,'%Y-%m-%d') EDITED_DATE,B.REMARK,A.PROJ_ID,"+
			" (SELECT COUNT(B.ATTCH_ID) FROM KM_ATTCH B WHERE B.DOC_ID=A.DOC_ID) AS ATTCH_NUM,"+
			" CASE A.CHECKOUT_FLAG WHEN 'Y' THEN '' ELSE 'NONE' END AS FLAG,"+
			" A.CHECKOUT_FLAG,"+
			" CASE A.CHECKOUT_BY WHEN '"+userInfo.getUserId()+"' THEN 'Y' ELSE 'N' END AS USER_FLAG,"+
			" CASE A.CREATED_BY WHEN '"+userInfo.getUserId()+"' THEN 'Y' ELSE 'N' END AS CREATED_FLAG,"+
			" CASE A.ASSIGNED_TO WHEN '"+userInfo.getUserId()+"' THEN 'Y' ELSE 'N' END AS ASSIGNED_FLAG,"+
			" CASE A.APPROVED_BY WHEN '"+userInfo.getUserId()+"' THEN 'Y' ELSE 'N' END AS APPROVED_FLAG,"+
			" D.TITLE AS FOLDER_NAME FROM KM_DOC A LEFT OUTER JOIN CM_CODE B ON A.DOC_TYPE = B.CODE_NAME "+
			" AND B.CODE_TYPE='DOC_TYPE' LEFT OUTER JOIN CM_USERS C ON A.CHECKOUT_BY = C.USER_ID "+
			" LEFT OUTER JOIN KM_FOLDER D  ON A.FOLDER_ID = D.FOLDER_ID "+
			" WHERE A.DELETED_FLAG!='1' AND A.FOLDER_ID IN("+FoldString+") AND (A.CREATED_BY='"+userInfo.getUserId()+"' OR A.ASSIGNED_TO='"+userInfo.getUserId()+"'"+ 
			" OR A.APPROVED_BY='"+userInfo.getUserId()+"' OR  ((A.FRACTION>=(SELECT LOW_FRACTION FROM CM_USERS WHERE USER_ID='"+userInfo.getUserId()+"') "+
			" AND A.FRACTION<=(SELECT HIGH_FRACTION FROM CM_USERS WHERE USER_ID='"+userInfo.getUserId()+"')) "+
			" AND A.STATUS='APPROVED')) ORDER BY A.DOC_NUMBER DESC";
  	}else{
 		 String FoldString = "'"+folderId+"'";
  		 if(DataTypeUtil.validate(isAll)&&isAll.equals("1")){
  			FoldString = "SELECT FOLDER_ID  FROM KM_FOLDER  WHERE  INSTR(FOLDER_ID_PATH, '"+folderId+"') > 0 AND PROJ_ID = '"+userInfo.getProjId()+"'";
  		 }
  		sql="SELECT '' AS IMG,A.CERTIFIED_TYPE,A.FILE_NAME,A.DOC_ID,A.DOC_NUMBER,A.TITLE,A.EDITION,A.STATUS,"+
  			" A.FRACTION,A.AUTHOR,date_format(A.CREATED_DATE,'%Y-%m-%d') CREATED_DATE ,date_format(A.EDITED_DATE,'%Y-%m-%d') EDITED_DATE,A.DOC_GRADE,B.REMARK,A.PROJ_ID,A.FOLDER_ID,"+
  			" (SELECT COUNT(B.ATTCH_ID) FROM KM_ATTCH B WHERE B.DOC_ID=A.DOC_ID) AS ATTCH_NUM,"+
  			" CASE A.CHECKOUT_FLAG WHEN 'Y' THEN '' ELSE 'NONE' END AS FLAG,A.CHECKOUT_FLAG,"+
  			" CASE A.CHECKOUT_BY WHEN '"+userInfo.getUserId()+"' THEN 'Y' ELSE 'N' END AS USER_FLAG,"+
  			" CASE A.CREATED_BY WHEN '"+userInfo.getUserId()+"' THEN 'Y' ELSE 'N' END AS CREATED_FLAG,"+
  			" CASE A.ASSIGNED_TO WHEN '"+userInfo.getUserId()+"' THEN 'Y' ELSE 'N' END AS ASSIGNED_FLAG,"+
  			" (SELECT ACTUAL_NAME FROM CM_USERS WHERE USER_ID='"+userInfo.getUserId()+"')AS ACTUAL_NAME,"+
  			" (SELECT CATG_SHORT_NAME FROM CM_CATVAL WHERE CATG_ID=(SELECT CATG_ID FROM CM_CATBASE WHERE BASE_MASTER_KEY=A.DOC_ID "+
  			" AND CATG_TYPE_ID=(SELECT CATG_TYPE_ID FROM CM_CATTYPE WHERE CATG_TYPE='项目单元'))) AS TZ_UNION,"+
  			" (SELECT CATG_SHORT_NAME FROM CM_CATVAL WHERE CATG_ID=(SELECT CATG_ID FROM CM_CATBASE WHERE BASE_MASTER_KEY=A.DOC_ID "+
  			" AND CATG_TYPE_ID=(SELECT CATG_TYPE_ID FROM CM_CATTYPE WHERE CATG_TYPE='图纸类别'))) AS TZ_TYPE,"+
  			" (SELECT CATG_SHORT_NAME FROM CM_CATVAL WHERE CATG_ID=(SELECT CATG_ID FROM CM_CATBASE WHERE BASE_MASTER_KEY=A.DOC_ID "+
  			" AND CATG_TYPE_ID=(SELECT CATG_TYPE_ID FROM CM_CATTYPE WHERE CATG_TYPE='图纸设计单位'))) AS TZ_DESCO,"+
  			" D.TITLE AS FOLDER_NAME,A.DOC_COPYS,A.DOC_PAGENO FROM KM_DOC A LEFT OUTER JOIN CM_CODE B ON A.DOC_TYPE = B.CODE_NAME "+
  			" AND B.CODE_TYPE='DOC_TYPE' LEFT OUTER JOIN CM_USERS C ON A.CHECKOUT_BY = C.USER_ID LEFT OUTER JOIN KM_FOLDER D  "+
  			" ON A.FOLDER_ID = D.FOLDER_ID WHERE A.DELETED_FLAG!='1' AND A.FOLDER_ID IN("+FoldString+") "+
  			" AND A.PROJ_ID='"+userInfo.getProjId()+"'  AND (A.CREATED_BY='"+userInfo.getUserId()+"' "+
  			" OR A.ASSIGNED_TO='"+userInfo.getUserId()+"' OR A.APPROVED_BY='"+userInfo.getUserId()+"' "+
  			" OR ((A.FRACTION>=(SELECT LOW_FRACTION FROM CM_USERS WHERE USER_ID='"+userInfo.getUserId()+"') "+
  			" AND A.FRACTION<=(SELECT HIGH_FRACTION FROM CM_USERS WHERE USER_ID='"+userInfo.getUserId()+"')) AND A.STATUS='APPROVED'))  "+
  			" ORDER BY A.DOC_NUMBER DESC";
  	}
	return  baseJdbcDao.queryListMap(sql);
  }
  public  String initAddDocNum(String folder_id, String proj_id, String user_id, String doc_grade)
	{
		String def_codes = getAllDefCode(folder_id, "");
		def_codes = def_codes.replaceAll(" ", "");
		String dataStr = DateUtil.getFormatDate(new Date(),"YYYYMMDD");
		def_codes = def_codes +dataStr;
		String sql = "SELECT  MAX(SUBSTR(DOC_NUMBER, CHAR_LENGTH('"+def_codes+"')+1, 3))   AS NUM"+
		  " FROM KM_DOC"+
		  " WHERE EFFECTIVE_FLAG = '1'"+
		  " AND FOLDER_ID = '"+folder_id+"'"+
		  " and  CHAR_LENGTH(DOC_NUMBER) =  CHAR_LENGTH('"+def_codes+"')+3 and DOC_NUMBER like '"+def_codes+"%' and  isNum(SUBSTR(DOC_NUMBER, CHAR_LENGTH('"+def_codes+"')+1, 3)) = 1";
		String num = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
		if(DataTypeUtil.validate(num)){
			num =  String.valueOf(Integer.valueOf(num)+1);
			if(num.length()==1){
				num = "00"+num;
			}else if(num.length()==2){
				num = "0"+num;
			}
		}else{
			num  = "001";
		}
	//	doc_num1 = def_codes + year;
					   
		return def_codes+num;
	}

	public  String getAllDefCode(String folder_id, String def_codes)
	{
	 
		Map<String, Object> folderMap = kmFolderService.getFolderInfoByFolderId(folder_id);
		if (DataTypeUtil.validate(folderMap))
		{
			String parent_folder_id = DataTypeUtil.formatDbColumn(folderMap.get("PARENT_FOLDER_ID"));
			String def_code = DataTypeUtil.formatDbColumn(folderMap.get("DEF_CODE"));
			def_codes = def_code + def_codes;
			def_codes = getAllDefCode(parent_folder_id, def_codes);
		} 
		return def_codes;
	}

	@Override
	public boolean InsertDoc(KmDoc kmDoc) {
		String sql  ="";
	    String doc_number = initAddDocNum( kmDoc.getFolderId(),  kmDoc.getProjId(),  kmDoc.getCreatedBy(),  kmDoc.getDocGrade());
	    System.out.println("-------------------"+doc_number);
	    boolean result=false;
	    

	    String approveStatus = "APPROVED";//默认为不需要审批
	    String module_code = kmDoc.getModuleCode();
	    if(DataTypeUtil.validate(module_code)){
	    	//查询文档是否需要审批 0：不需要审批  大于一：需要审批
		    sql = "SELECT COUNT(*) MODULECOUNT FROM SYS_DOC_STATUS WHERE MOUDLE_CODE='"+module_code+"'";
		    if(this.baseJdbcDao.getCount(sql)>0){
		    	approveStatus = "INIT";
		    }
	    }
	    
	    
		if( kmDoc.getDocGrade().equals("0")){
		  sql = "INSERT INTO KM_DOC "+
		   "(WID,DOC_ID,FOLDER_ID,DOC_NUMBER,TITLE,DOC_TYPE,STATUS,PRIORITY,KEY_WORDS,SUMMARY,AUTHOR, "+
		   "EDITION,EDITION_SEQ,EDITED_DATE,ISSUE_DEPT,EFFECT_DATE,ABATE_DATE,DELIV_FLAG,CERTIFIED_TYPE,DOC_GRADE,CHECKOUT_FLAG,FRACTION, "+
		   "CREATED_BY,CREATED_DATE,ASSIGNED_TO,PROJ_ID,DATE_LINKED,TEMPLATE_FLAG,DOC_NOTE,DELETED_FLAG,DOCREC_STATUS,BASE_MASTER_KEY,BASE_ITEM_TYPE, "+
		   "DOC_NUMBER1,APPROVED_DATE,APPROVED_BY,FILE_NAME,PUBLIC_LOC,PRIVATE_LOC,HT_ID) "+
		"VALUES "+
		  "('"+Guid.getGuid()+"','"+ kmDoc.getDocId()+"','"+kmDoc.getFolderId()+"','"+doc_number+"', '"+kmDoc.getTitle()+"','"+kmDoc.getDocType()+"','"+approveStatus+"','0','','','', "+
		   "'1.0','1',now(),'',now(),now(),'0','0','0','N','10', "+
		   "'"+kmDoc.getCreatedBy()+"',now(),'"+kmDoc.getCreatedBy()+"','',now(),'0','','0','未分发','','', "+
		   "'', now(),'"+kmDoc.getCreatedBy()+"','"+kmDoc.getFileName()+"','"+kmDoc.getPublicLoc()+"','"+kmDoc.getPrivateLoc()+"','"+kmDoc.getHtId()+"')";
		}else{
			  sql = "INSERT INTO KM_DOC "+
			   "(WID,DOC_ID,FOLDER_ID,DOC_NUMBER,TITLE,DOC_TYPE,STATUS,PRIORITY,KEY_WORDS,SUMMARY,AUTHOR, "+
			   "EDITION,EDITION_SEQ,EDITED_DATE,ISSUE_DEPT,EFFECT_DATE,ABATE_DATE,DELIV_FLAG,CERTIFIED_TYPE,DOC_GRADE,CHECKOUT_FLAG,FRACTION, "+
			   "CREATED_BY,CREATED_DATE,ASSIGNED_TO,PROJ_ID,DATE_LINKED,TEMPLATE_FLAG,DOC_NOTE,DELETED_FLAG,DOCREC_STATUS,BASE_MASTER_KEY,BASE_ITEM_TYPE, "+
			   "DOC_NUMBER1,APPROVED_DATE,APPROVED_BY,FILE_NAME,PUBLIC_LOC,PRIVATE_LOC,BASE_LINK_ID,HT_ID) "+
			"VALUES "+
			  "('"+Guid.getGuid()+"','"+ kmDoc.getDocId()+"','"+kmDoc.getFolderId()+"','"+doc_number+"', '"+kmDoc.getTitle()+"','"+kmDoc.getDocType()+"','"+approveStatus+"','0','','','', "+
			   "'1.0','1',now(),'',now(),now(),'0','0','2','N','10', "+
			   "'"+kmDoc.getCreatedBy()+"',now(),'"+kmDoc.getCreatedBy()+"','"+kmDoc.getProjId()+"',now(),'0','','0','未分发','"+kmDoc.getBaseMasterKey()+"','', "+
			   "'', now(),'"+kmDoc.getCreatedBy()+"','"+kmDoc.getFileName()+"','"+kmDoc.getPublicLoc()+"','"+kmDoc.getPrivateLoc()+"','"+kmDoc.getBaseLinkId()+"','"+kmDoc.getHtId()+"')";
			
		}
		result = baseJdbcDao.insert(sql);  
//		sql="INSERT INTO KM_DOCVER(WID,DOCVER_ID,DOC_ID,EDITION,EDITION_SEQ,STATUS,EDITED_BY,EDITED_BY_NAME,EDITED_DATE,FILE_NAME,EDITION_DES)  VALUES('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+ kmDoc.getDocId()+"','1.0','1','APPROVED','"+ kmDoc.getCreatedBy()+"','"+kmDoc.getCreatedByName()+"',now(),'"+kmDoc.getFileName()+"','')";
//		result = baseJdbcDao.insert(sql);
		
		if("APPROVED".equals(approveStatus)){
			
			sql="INSERT INTO KM_DOCVER(WID,DOCVER_ID,DOC_ID,EDITION,EDITION_SEQ,STATUS,EDITED_BY,EDITED_BY_NAME,EDITED_DATE,FILE_NAME,EDITION_DES)  VALUES('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+ kmDoc.getDocId()+"','1.0','1','APPROVED','"+ kmDoc.getCreatedBy()+"','"+kmDoc.getCreatedByName()+"',now(),'"+kmDoc.getFileName()+"','')";
			result = baseJdbcDao.insert(sql);
			
			sql="INSERT INTO KM_DOCLOG(WID,DOCLOG_ID,DOC_ID,ACCESSED_DATE,ACCESSED_BY,ACCESSED_BY_NAME,ACCESS_DESCRIBE,VERSION_NAME)  VALUES('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+ kmDoc.getDocId()+"',now(),'"+kmDoc.getCreatedBy()+"','"+kmDoc.getCreatedByName()+"','已批准','1.0')";
		}else{
			sql="INSERT INTO KM_DOCVER(WID,DOCVER_ID,DOC_ID,EDITION,EDITION_SEQ,STATUS,EDITED_BY,EDITED_BY_NAME,EDITED_DATE,FILE_NAME,EDITION_DES)  VALUES('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+ kmDoc.getDocId()+"','1.0','1','INIT','"+ kmDoc.getCreatedBy()+"','"+kmDoc.getCreatedByName()+"',now(),'"+kmDoc.getFileName()+"','')";
			result = baseJdbcDao.insert(sql);
			
			sql="INSERT INTO KM_DOCLOG(WID,DOCLOG_ID,DOC_ID,ACCESSED_DATE,ACCESSED_BY,ACCESSED_BY_NAME,ACCESS_DESCRIBE,VERSION_NAME)  VALUES('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+ kmDoc.getDocId()+"',now(),'"+kmDoc.getCreatedBy()+"','"+kmDoc.getCreatedByName()+"','新建','1.0')";
		}
		
		result = baseJdbcDao.insert(sql);
		String BaseMasterKey = kmDoc.getBaseMasterKey();
		if(DataTypeUtil.validate(BaseMasterKey)){
			sql = "UPDATE  DS_PLAN SET PLAN_STATUS ='1'  ,ACT_START_DATE= CURDATE() where plan_id = '"+BaseMasterKey+"' and plan_status='0' and proj_id = '"+kmDoc.getProjId()+"'";
			baseJdbcDao.update(sql);
			sql = "UPDATE  SM_PLAN SET PLAN_STATUS ='1'  ,ACT_START_DATE= CURDATE() where plan_id = '"+BaseMasterKey+"' and plan_status='0' and proj_id = '"+kmDoc.getProjId()+"'";
			baseJdbcDao.update(sql);
			sql = "UPDATE  SM_TEST SET PLAN_STATUS ='1'  ,ACT_START_DATE= CURDATE() where plan_id = '"+BaseMasterKey+"' and plan_status='0' and proj_id = '"+kmDoc.getProjId()+"'";
			baseJdbcDao.update(sql);
			sql = "UPDATE  SYS_PLAN SET PLAN_STATUS ='1'  ,ACT_START_DATE= CURDATE() where plan_id = '"+BaseMasterKey+"' and plan_status='0' and proj_id = '"+kmDoc.getProjId()+"'";
			baseJdbcDao.update(sql);
		}
		return result;
	}

	@Override
	public String GetUrlById(String docId,String type) {
		if(DataTypeUtil.validate(type)){
			String file_name ="";
			Map<String, Object> AttchMap  = getAttchInfoByAttchId(docId);
			if(DataTypeUtil.validate(AttchMap)){
				file_name = DataTypeUtil.formatDbColumn(AttchMap.get("PUBLIC_LOC"));
				Map<String, Object> docMap = getDocInfoByDocId(type);
				if(DataTypeUtil.validate(docMap)){
					String folder_id = DataTypeUtil.formatDbColumn(docMap.get("FOLDER_ID"));
					String folderPath = kmFolderService.getFullPathByFolderId(folder_id);
				    String baseDir =  AppSetting.PROJECT_NAME+"/KM/DOC";
				    String OrgbaseDir =  AppSetting.PROJECT_PATH+"/KM/DOC";
					String fulldir = baseDir+folderPath + "/" + file_name;
					if(new File(OrgbaseDir+folderPath + "/" + file_name).exists()){
						return fulldir;
					}else{
						return "error";
					}
					
				}else{
					return "error";
				}
			}else{
				return "error";
			}
		}else{
		    Map<String, Object> docMap = getDocInfoByDocId(docId);
			if(DataTypeUtil.validate(docMap)){
				String file_name = DataTypeUtil.formatDbColumn(docMap.get("FILE_NAME"));
				String folder_id = DataTypeUtil.formatDbColumn(docMap.get("FOLDER_ID"));
				String folderPath = kmFolderService.getFullPathByFolderId(folder_id);
				String baseDir =  AppSetting.PROJECT_NAME+"/KM/DOC";
			    String OrgbaseDir =  AppSetting.PROJECT_PATH+"/KM/DOC";
				String fulldir = baseDir+folderPath + "/" + file_name;
				if(new File(OrgbaseDir+folderPath + "/" + file_name).exists()){
					return fulldir;
				}else{
					return "error";
				}
				
			}else{
				return "error";
			}
		}
	}
	@Override
	public String GetActualNameUrlById(String docId,String type,String userId) {
		 Pattern pattern = Pattern.compile("[/////:/\\\\/*/?/</>/#/|]");
		if(DataTypeUtil.validate(type)){
			String file_name ="";
			String actual_file_name = "";
			Map<String, Object> AttchMap  = getAttchInfoByAttchId(docId);
			if(DataTypeUtil.validate(AttchMap)){
				file_name = DataTypeUtil.formatDbColumn(AttchMap.get("PUBLIC_LOC"));
				actual_file_name = DataTypeUtil.formatDbColumn(AttchMap.get("PRIVATE_LOC"));
				 Matcher matcher  = pattern.matcher(actual_file_name);
				 if(matcher.find()){
					 actual_file_name = matcher.replaceAll("_");
				 }
				Map<String, Object> docMap = getDocInfoByDocId(type);
				if(DataTypeUtil.validate(docMap)){
					String folder_id = DataTypeUtil.formatDbColumn(docMap.get("FOLDER_ID"));
					String folderPath = kmFolderService.getFullPathByFolderId(folder_id);
				    String baseDir =  AppSetting.PROJECT_NAME+"/KM/temp/"+userId;
				    String OrgbaseDir =  AppSetting.PROJECT_PATH+"/KM/DOC";
				    String ActualbaseDir =  AppSetting.PROJECT_PATH+"/KM/temp/"+userId;
				    if(!new File(ActualbaseDir).exists()){
				    	new File(ActualbaseDir).mkdirs();
				    }
					String fulldir = baseDir + "/" + actual_file_name;
					File file = new File(OrgbaseDir+folderPath + "/" + file_name);
					if(file.exists()){
						FileUtil.copyFile(OrgbaseDir+folderPath + "/" + file_name, ActualbaseDir + "/" + actual_file_name);
						return fulldir;
					}else{
						return "error";
					}
					
				}else{
					return "error";
				}
			}else{
				return "error";
			}
		}else{
		    Map<String, Object> docMap = getDocInfoByDocId(docId);
			if(DataTypeUtil.validate(docMap)){
				String file_name = DataTypeUtil.formatDbColumn(docMap.get("FILE_NAME"));
				String folder_id = DataTypeUtil.formatDbColumn(docMap.get("FOLDER_ID"));
				String actual_file_name = DataTypeUtil.formatDbColumn(docMap.get("TITLE"));
				 Matcher matcher  = pattern.matcher(actual_file_name);
				 if(matcher.find()){
					 actual_file_name = matcher.replaceAll("_");
				 }
				String doc_number = DataTypeUtil.formatDbColumn(docMap.get("DOC_NUMBER"));
				 Matcher matcher2  = pattern.matcher(doc_number);
				 if(matcher2.find()){
					 doc_number = matcher2.replaceAll("_");
				 }
				String folderPath = kmFolderService.getFullPathByFolderId(folder_id);
				String baseDir =  AppSetting.PROJECT_NAME+"/KM/temp/"+userId;
			    String OrgbaseDir =  AppSetting.PROJECT_PATH+"/KM/DOC";
			    String ActualbaseDir =  AppSetting.PROJECT_PATH+"/KM/temp/"+userId;
			    if(!new File(ActualbaseDir).exists()){
			    	new File(ActualbaseDir).mkdirs();
			    }
			    String fulldir = baseDir + "/" + doc_number+"_"+actual_file_name;
				if(new File(OrgbaseDir+folderPath + "/" + file_name).exists()){
					String filetype=file_name.substring(file_name.lastIndexOf("."));
					FileUtil.copyFile(OrgbaseDir+folderPath + "/" + file_name, ActualbaseDir + "/" + doc_number+"_"+actual_file_name+filetype);
					return fulldir+filetype;
				}else{
					return "error";
				}
				
			}else{
				return "error";
			}
		}
	}

	@Override
	public Map<String, Object> getDocInfoByDocId(String docId) {
		return  baseJdbcDao.queryMap("select * from km_doc where doc_id = '"+docId+"'");
	}

	@Override
	public String delDoc(String docId,String type) { 
		if(DataTypeUtil.validate(type)){
			String file_name ="";
			Map<String, Object> AttchMap  = getAttchInfoByAttchId(docId);
			if(DataTypeUtil.validate(AttchMap)){
				file_name = DataTypeUtil.formatDbColumn(AttchMap.get("PUBLIC_LOC"));
				Map<String, Object> docMap = getDocInfoByDocId(type);
				if(DataTypeUtil.validate(docMap)){
					String folder_id = DataTypeUtil.formatDbColumn(docMap.get("FOLDER_ID"));
					String folderPath = kmFolderService.getFullPathByFolderId(folder_id);
				    String OrgbaseDir =  AppSetting.PROJECT_PATH+"/KM/DOC";
					if(new File(OrgbaseDir+folderPath + "/" + file_name).exists()){
						new File(OrgbaseDir+folderPath + "/" + file_name).delete();
					}
				}
			}
			String sql = "delete from KM_ATTCH where Attch_Id = '"+docId+"'";
			baseJdbcDao.delete(sql);
		}else{
			String sql =" UPDATE KM_DOC SET DELETED_FLAG='1',DOC_NUMBER=concat(DOC_NUMBER,'(删)') WHERE DOC_ID ='"+docId+"'";
			baseJdbcDao.update(sql);

		}
		return "success";
	}
	@Override
	public String publicDoc(String docIds, String status, String userId) {
		String[] ids = docIds.split(",");
		String sql = "SELECT COUNT(WID) FROM KM_DOC A WHERE A.CREATED_BY = '"+userId+"' OR A.ASSIGNED_TO = '"+userId+"' OR  A.APPROVED_BY = '"+userId+"' AND DOC_ID IN ('"+docIds.replaceAll(",", "','")+"') ";
        int num  = baseJdbcDao.getCount(sql);
        if(ids.length>num){
        	return "选择的记录中含有你无权限操作记录，请重新选择";
        }else{
        	sql = "UPDATE KM_DOC SET STATUS ='"+status+"'  WHERE DOC_ID IN ('"+docIds.replaceAll(",", "','")+"')";
        	baseJdbcDao.update(sql);
        	return "success";
        }
	}
	@Override
	public boolean InsertAttch(KmAttch kmAttch) {
		String sql="INSERT INTO KM_ATTCH(WID,ATTCH_ID,DOC_ID,DATE_LINKED,SUBJECT,PRIVATE_LOC,PUBLIC_LOC,AUTHOR_NAME)  VALUES('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+ kmAttch.getDocId()+"',now(),'"+ kmAttch.getSubject()+"','"+kmAttch.getPrivateLoc()+"','"+kmAttch.getPublicLoc()+"','"+kmAttch.getAuthorName()+"')";
		return baseJdbcDao.insert(sql);
	}	 
	@Override
	public Map<String, Object> getAttchInfoByAttchId(String AttchId) {
		return  baseJdbcDao.queryMap("select * from km_attch where Attch_Id = '"+AttchId+"'");
	}

	@Override
	public String UpdateDocNormal(KmDoc kmDoc) {
		String  sql ="UPDATE  KM_DOC Set DOC_NUMBER ='"+kmDoc.getDocNumber()+"',TITLE='"+kmDoc.getTitle()+"',PRIORITY='"+kmDoc.getPriority()+"',KEY_WORDS='"+kmDoc.getKeyWords()+"',DELIV_FLAG='"+(DataTypeUtil.validate(kmDoc.getDelivFlag()) ? kmDoc.getDelivFlag() : "0")+"',TEMPLATE_FLAG='"+(DataTypeUtil.validate(kmDoc.getTemplateFlag()) ? kmDoc.getTemplateFlag() : "0")+"',FRACTION='"+kmDoc.getFraction()+"',SUMMARY='"+kmDoc.getSummary()+"' where Doc_id = '"+kmDoc.getDocId()+"'";
		baseJdbcDao.update(sql);
		return "success";
	}
	@Override
	public String UpdateDocOther(KmDoc kmDoc) {
		String  sql ="UPDATE  KM_DOC Set AUTHOR ='"+kmDoc.getAuthor()+"',ISSUE_DEPT='"+kmDoc.getIssueDept()+"',EFFECT_DATE="+(DataTypeUtil.validate(kmDoc.getEffectDate()) ? "'"+kmDoc.getEffectDate()+"'" : null)+",ABATE_DATE="+(DataTypeUtil.validate(kmDoc.getAbateDate()) ? "'"+kmDoc.getAbateDate()+"'" : null)+",IS_DOCREC='"+(DataTypeUtil.validate(kmDoc.getIsDocrec()) ? kmDoc.getIsDocrec() : "0")+"',DOC_NOTE='"+kmDoc.getDocNote()+"',DOC_NUMBER1='"+kmDoc.getDocNumber1()+"' where Doc_id = '"+kmDoc.getDocId()+"'";
		baseJdbcDao.update(sql);
		return "success";
	}

	@Override
	public List DocVersionTable(String docId) {
		return  baseJdbcDao.queryListMap(" SELECT WID,EDITION_SEQ,EDITION,EDITION_DES,EDITED_BY_NAME,date_format(EDITED_DATE,'%Y-%m-%d') EDITED_DATE,DOCVER_ID,DOC_ID FROM KM_DOCVER WHERE DOC_ID='"+docId+"' ORDER BY EDITION_SEQ ");
	}

	@Override
	public String GetUrlByVersionId(String docId, String docverId) {
		String file_name ="";
		Map<String, Object> DocverMap  = getVersionInfoByVersionId(docverId);
		if(DataTypeUtil.validate(DocverMap)){
			file_name = DataTypeUtil.formatDbColumn(DocverMap.get("FILE_NAME"));
			Map<String, Object> docMap = getDocInfoByDocId(docId);
			if(DataTypeUtil.validate(docMap)){
				String folder_id = DataTypeUtil.formatDbColumn(docMap.get("FOLDER_ID"));
				String folderPath = kmFolderService.getFullPathByFolderId(folder_id);
				String baseDir =  AppSetting.PROJECT_NAME+"/KM/DOC";
			    String OrgbaseDir =  AppSetting.PROJECT_PATH+"/KM/DOC";
				String fulldir = baseDir+folderPath + "/" + file_name;
				if(new File(OrgbaseDir+folderPath + "/" + file_name).exists()){
					return fulldir;
				}else{
					return "error";
				}
				
			}else{
				return "error";
			}
		}else{
			return "error";
		}
	}

	@Override
	public Map<String, Object> getVersionInfoByVersionId(String VersionId) {
		return  baseJdbcDao.queryMap("select * from KM_DOCVER where DOCVER_ID = '"+VersionId+"'");
	}

	@Override
	public List DocLogTable(String docId) {
		return  baseJdbcDao.queryListMap("  SELECT WID,date_format(ACCESSED_DATE,'%Y-%m-%d') ACCESSED_DATE,ACCESS_DESCRIBE,ACCESSED_BY_NAME,VERSION_NAME FROM KM_DOCLOG WHERE DOC_ID='"+docId+"' ORDER BY  ACCESSED_DATE desc   ");

	}

	@Override
	public List DocAttchTable(String docId) {
		return  baseJdbcDao.queryListMap("  SELECT WID,ATTCH_ID,date_format(DATE_LINKED,'%Y-%m-%d')  DATE_LINKED,SUBJECT,AUTHOR_NAME,DOC_ID FROM KM_ATTCH WHERE DOC_ID='"+docId+"' ORDER BY  DATE_LINKED desc   ");
	}

	@Override
	public List PlanDocTree(String id, String tableName,String baseLinkId, String userId, String projId,String type) {
		String base_master_key ="";
		if(!DataTypeUtil.validate(id)){
			return null;
		}
		if (DataTypeUtil.validate(type)&&type.equals("doc")){
		  base_master_key = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select "+id+" from "+tableName+" where "+tableName.replace("DS_", "")+"_ID  = '"+baseLinkId+"' "));
	    }else{
	     base_master_key =id;
	    }
		List items = new ArrayList();
		if(DataTypeUtil.validate(base_master_key)){
		 List<Map<String, Object>> ChildrenList = findDocTreeBySql(base_master_key,baseLinkId,userId,projId,type);
		    
		    for (Map<String, Object> item : ChildrenList) {
		      
			  	if (DataTypeUtil.validate(type)&&type.equals("doc"))
				{
				 
			  		String num = item.get("NUM").toString();
				      if (!num.equals("0")) {
					        item.put("state", "closed");
					       
					      }
					String  columnValue = DataTypeUtil.formatDbColumn(item.get("STRUCT"));
					if(DataTypeUtil.validate(columnValue)){
						item.put("STRUCT", "是");
						item.put("OFFICETPL_ID", columnValue);
					}else{
						item.put("STRUCT", "否");
					}
					String private_loc = DataTypeUtil.formatDbColumn(item.get("PRIVATE_LOC"));
					
					if (DataTypeUtil.validate(private_loc))
					{
						item.put("iconCls", getFileImage(private_loc));
					} else
						item.put("iconCls", ".icon-others");
					
					item.put("DOC_FLAG", "Y");
				} else
				{
					item.put("iconCls", "icon-attch");
					String idValue = DataTypeUtil.formatDbColumn(item.get("ATTCH_ID"));	 
					item.put("DOC_ID", idValue);
					String columnValue = DataTypeUtil.formatDbColumn(item.get("SUBJECT"));
					item.put("DOC_NUMBER", "主题:" + columnValue);
					columnValue = DataTypeUtil.formatDbColumn(item.get("AUTHOR_NAME"));
					item.put("STATUS", "作者:" + columnValue);
					columnValue = DataTypeUtil.formatDbColumn(item.get("DATE_LINKED"));
					item.put("TITLE", "挂接日期:" + columnValue.substring(0, 10));
					 
					item.put("DOC_FLAG", "N");
					item.put("LINK_DOC_ID",base_master_key );
				}   
				 

 		      items.add(item);
		    }
		   
		}
		 return items;
	}
	public List<Map<String, Object>> findDocTreeBySql(String base_master_key,String baseLinkId, String userId, String projId,String type){

		  List<Map<String, Object>> result = null; 
		  if (DataTypeUtil.validate(base_master_key)){

			  String  sql="";
			  if(DataTypeUtil.validate(type)&&type.equals("doc")){
				  sql   ="SELECT DOC_ID,(SELECT count(ATTCH_ID) FROM KM_ATTCH WHERE DOC_ID=K.DOC_ID) NUM, FOLDER_ID,DOC_NUMBER,TITLE,AUTHOR,DATE_LINKED,PRIVATE_LOC,FILE_NAME,CASE STATUS WHEN 'INIT' THEN '新建' WHEN 'PENDING' THEN '审批中' WHEN 'SUSPENDED' THEN '挂起' WHEN 'TERMINATED' THEN '终止' WHEN 'APPROVED' THEN '已批准' END AS STATUS, EDITION,FRACTION,(SELECT ACTUAL_NAME FROM CM_USERS WHERE USER_ID=CREATED_BY) AS ACTUAL_NAME,(select officetpl_id  from struct_info t  WHERE t.doc_id = K.doc_id ) struct FROM KM_DOC K WHERE BASE_MASTER_KEY='"+base_master_key+"'   AND (BASE_LINK_ID = '"+baseLinkId+"' OR BASE_LINK_ID IS NULL OR BASE_LINK_ID ='')  AND DELETED_FLAG='0' ORDER BY CREATED_DATE DESC";
 
			 }else{
				  sql   =" SELECT ATTCH_ID,SUBJECT,AUTHOR_NAME,DATE_LINKED FROM KM_ATTCH WHERE DOC_ID='"+base_master_key+"' ORDER BY DATE_LINKED ASC";
					  
			 }
			  result =  baseJdbcDao.queryListMap(sql); 
		  }
		return result;
		}
	 public static String getFileImage(String paramString)
	  {
	    String str1 = "";
	    String str2 = "icon-others";
	    if ( DataTypeUtil.validate(paramString) && (paramString.indexOf(".") >= 0))
	    {
	      str1 = paramString.substring(paramString.lastIndexOf(".") + 1);
	      if ((str1.equalsIgnoreCase("doc")) || (str1.equalsIgnoreCase("docx")))
	        str2 = "icon-word";
	      else if (str1.equalsIgnoreCase("xls") || (str1.equalsIgnoreCase("xlsx")))
	        str2 = "icon-excel";
	      else if (str1.equalsIgnoreCase("ppt"))
	        str2 = "icon-ppt";
	      else if (str1.equalsIgnoreCase("txt"))
	        str2 = "icon-txt";
	      else if (str1.equalsIgnoreCase("dwg"))
	        str2 = "icon-dwg";
	      else if ((str1.equalsIgnoreCase("gif")) || (str1.equalsIgnoreCase("jpg")) || (str1.equalsIgnoreCase("png")) || (str1.equalsIgnoreCase("bmp")))
	        str2 = "icon-gif";
	      else if (str1.equalsIgnoreCase("pdf"))
	        str2 = "icon-pdf";
	      else if ((str1.equalsIgnoreCase("zip")) || (str1.equalsIgnoreCase("rar")))
	        str2 = "icon-zip";
	    }
	    return str2;
	  }

	@Override
	public List ShowRecycleTable(String docGrade, UserInfo userInfo) {
		String sql ="";
		String userId = userInfo.getUserId();
		String projId = userInfo.getProjId();
		
		if(docGrade.equals("0")){
			sql = " SELECT A.FOLDER_ID,A.STATUS,A.DOC_ID AS WID,A.DOC_ID,A.FILE_NAME,A.DOC_NUMBER,A.TITLE,A.EDITION,A.AUTHOR,date_format(A.CREATED_DATE,'%Y-%m-%d')  CREATED_DATE,date_format(A.EDITED_DATE,'%Y-%m-%d')  EDITED_DATE,"+
					" A.FRACTION,B.REMARK,C.ACTUAL_NAME FROM KM_DOC A "+
					" LEFT OUTER JOIN CM_CODE B ON A.DOC_TYPE = B.CODE_NAME AND B.CODE_TYPE = 'DOC_TYPE' "+
					" LEFT JOIN CM_USERS C ON C.USER_ID=A.CREATED_BY"+
					" WHERE A.DELETED_FLAG='1' AND A.DOC_GRADE ='0'  AND (A.CREATED_BY='"+userId+"' OR A.ASSIGNED_TO='"+userId+"' "+
					" OR (A.FRACTION>=(SELECT LOW_FRACTION FROM CM_USERS WHERE USER_ID='"+userId+"') AND A.FRACTION<=(SELECT LOW_FRACTION FROM CM_USERS WHERE USER_ID='"+userId+"')))";

		}else{
			sql =" SELECT A.FOLDER_ID,A.STATUS,A.DOC_ID AS WID,A.DOC_ID,A.DOC_NUMBER,A.FILE_NAME,A.TITLE,A.EDITION,A.AUTHOR,date_format(A.CREATED_DATE,'%Y-%m-%d')  CREATED_DATE,date_format(A.EDITED_DATE,'%Y-%m-%d')  EDITED_DATE,"+
			" A.FRACTION,B.REMARK,C.ACTUAL_NAME FROM KM_DOC A "+
			" LEFT OUTER JOIN CM_CODE B ON A.DOC_TYPE = B.CODE_NAME AND B.CODE_TYPE = 'DOC_TYPE' "+
			" LEFT JOIN CM_USERS C ON C.USER_ID=A.CREATED_BY "+
			" WHERE A.DELETED_FLAG='1' AND A.PROJ_ID='"+projId+"' AND (A.DOC_GRADE ='2' OR A.DOC_GRADE ='3')  AND (A.CREATED_BY='"+userId+"' OR A.ASSIGNED_TO='"+userId+"' "+
			" OR (A.FRACTION>=(SELECT LOW_FRACTION FROM CM_USERS WHERE USER_ID='"+userId+"') AND A.FRACTION<=(SELECT LOW_FRACTION FROM CM_USERS WHERE USER_ID='"+userId+"'))) ";
		}
		List<Map<String, Object>> items =   baseJdbcDao.queryListMap(sql); 
		if(DataTypeUtil.validate(items)){
			for(Map<String, Object>item:items){
				item.put("IMG", "<span class='tree-icon tree-file "+getFileImage(DataTypeUtil.formatDbColumn(item.get("FILE_NAME")))+"'></span>");
				item.put("PATH",kmFolderService.getFullPathNameByFolderId(DataTypeUtil.formatDbColumn(item.get("FOLDER_ID"))));
			}
		}
		return items;
	}

	@Override
	public String ClearDocs(String docIds, String docGrade) {
		String[] ids = docIds.split(",");
		for(String  docId : ids)
		{
			// 删除文档的时候同时删除附件和版本，包括表信息的清除
			ClearDoc(docId, docGrade);
		}
		return "success";
		
	}
		public void ClearDoc(String docId, String docGrade){
			String sql ="";
			String sql2 = "";
			String sql3 = "";
			//获取附件文档名称
			String sqlSel = "select a.public_loc from KM_ATTCH a where a.doc_id='"+docId+"'";
			List<Map<String, Object>> listAttch = baseJdbcDao.queryListMap(sqlSel);
			//获取版本文档名称
			sqlSel = "select file_name from KM_DOCVER where doc_id='"+docId+"'";
			List<Map<String, Object>> listDocVer =  baseJdbcDao.queryListMap(sqlSel);
			// 删除文档表信息
		     String folder_id = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select folder_id from KM_DOC where doc_id ='"+docId+"'"));
		     String basedir = AppSetting.PROJECT_PATH + "/KM/DOC";
		     String docDir = kmFolderService.getFullPathByFolderId(folder_id);    
		     String folderPath = basedir+docDir; 
			//删除附件文档
			for(Map<String,Object> m:listAttch)
			{
				String attchName  = (String) m.get("PUBLIC_LOC");
				String attchPath = folderPath + "\\" + attchName;
				if(DataTypeUtil.validate(attchName)){
					File attchfile = new File(attchPath);
					if(attchfile.exists()&&attchfile.isFile())
						attchfile.delete();//删除附件
				}
			}
			//删除版本文档
			for(Map<String,Object> m: listDocVer)
			{
				String ver_Name = (String)m.get("FILE_NAME");
				String ver_path = folderPath + "\\" + ver_Name;//获得版本的绝对路径；
				if(DataTypeUtil.validate(ver_Name)){
					File verfile = new File(ver_path);
					if(verfile.exists()&&verfile.isFile())
						verfile.delete();//删除版本文档 
				}
			}
			sql = "DELETE FROM KM_DOC WHERE DOC_ID='" + docId+ "'";
			baseJdbcDao.delete(sql);
			// 删除附件表信息
			sql2="DELETE FROM KM_ATTCH WHERE doc_id = '"+docId+"'";
			baseJdbcDao.delete(sql2);
			// 删除版本表信息
			sql3="delete from km_docver where doc_id = '"+docId+"'";
			baseJdbcDao.delete(sql3);
			//删除结构化数据
		    sql3 =" delete from  STRUCT_INFO where doc_id = '"+docId+"'";
		    baseJdbcDao.delete(sql);
			 
		}
	@Override
	public String RevertDocs(String docIds, String docGrade) {
		baseJdbcDao.update("update KM_DOC set  DELETED_FLAG ='0' where doc_id in ('"+docIds.replaceAll(",","','")+"')");
		return "success";
	}

	@Override
	public List ShowSearchTable(String userId, String keywords,
		String createdDate1, String createdDate2, String editedDate1,
		String editedDate2, String createdBy, String assignedTo,
		String approvedBy, String editedBy, String author,
		String keywordsType, String docType, String projId) {
		String sel_keywords = "";
		String sel_docType = "";
		String sel_created_date = "";
		String sel_edited_date = "";
		String sel_users = "";
		String sel_order = "";
		if (DataTypeUtil.validate(keywordsType))
		{
			String[] keywordsTypeArray = keywordsType.split(";");
			for (int i = 0; i < keywordsTypeArray.length; i++)
			{
				if (!DataTypeUtil.validate(sel_keywords))
				{
					if ("subject".equals(keywordsTypeArray[i]))
					{
						sel_keywords = " K.DOC_ID IN(SELECT DOC_ID FROM KM_ATTCH WHERE KM_ATTCH." + keywordsTypeArray[i] + " LIKE '%" + keywords + "%')";
					} else
					{
						sel_keywords = " K." + keywordsTypeArray[i] + " LIKE '%" + keywords + "%' ";
					}
				} else
				{
					if ("subject".equals(keywordsTypeArray[i]))
					{
						sel_keywords = sel_keywords + " OR  K.DOC_ID IN(SELECT DOC_ID FROM KM_ATTCH WHERE KM_ATTCH." + keywordsTypeArray[i] + " LIKE '%" + keywords + "%')";
					} else
					{
						sel_keywords = sel_keywords + " OR K." + keywordsTypeArray[i] + " LIKE '%" + keywords + "%'";
					}
				}
			}
		}
		if (DataTypeUtil.validate(sel_keywords))
		{
		    sel_keywords = " and (" + sel_keywords +")";
		}
	/*	if (DataTypeUtil.validate(sel_keywords))
		{
			sel_keywords = " and (" + sel_keywords + lucense_words +")";
		}else{
			if(DataTypeUtil.validate(keywords)&&DataTypeUtil.validate(lucense_words)){
				sel_keywords = " and ( 1=2 " + lucense_words +")";
			}
		}*/
		// 文档类型
		if (DataTypeUtil.validate(docType))
		{
			String[] docTypeArray = docType.split(";");
			for (int i = 0; i < docTypeArray.length; i++)
			{
				if (!DataTypeUtil.validate(sel_docType))
				{
					sel_docType = "K.DOC_TYPE = '" + docTypeArray[i] + "'";
				} else
				{
					sel_docType = sel_docType + " OR K.DOC_TYPE = '" + docTypeArray[i] + "'";
				}
			}
			if (DataTypeUtil.validate(sel_docType))
			{
				sel_docType = " AND (" + sel_docType + ")";
			}
		}
		// 创建日期
		if (DataTypeUtil.validate(createdDate1) && DataTypeUtil.validate(createdDate2))
		{
			sel_created_date = "K.CREATED_DATE > STR_TO_DATE('" + createdDate1 + "','%Y-%m-%d') AND K.CREATED_DATE < STR_TO_DATE('" + createdDate2 + "','%Y-%m-%d')";
		} else if (DataTypeUtil.validate(createdDate1) && !DataTypeUtil.validate(createdDate2))
		{
			sel_created_date = "K.CREATED_DATE > STR_TO_DATE('" + createdDate1 + "','%Y-%m-%d')";
		} else if (DataTypeUtil.validate(createdDate1) && DataTypeUtil.validate(createdDate2))
		{
			sel_created_date = "K.CREATED_DATE < STR_TO_DATE('" + createdDate2 + "','%Y-%m-%d')";
		}
		if (DataTypeUtil.validate(sel_created_date))
		{
			sel_created_date = " AND " + sel_created_date;
		}
		// 最后修改日期
		if (DataTypeUtil.validate(editedDate1) && DataTypeUtil.validate(editedDate2))
		{
			sel_edited_date = "K.EDITED_DATE > STR_TO_DATE('" + editedDate1 + "','%Y-%m-%d') AND K.EDITED_DATE < STR_TO_DATE('" + editedDate2 + "','%Y-%m-%d')";
		} else if (DataTypeUtil.validate(editedDate1) && !DataTypeUtil.validate(editedDate2))
		{
			sel_edited_date = "K.EDITED_DATE > STR_TO_DATE('" + editedDate1 + "','%Y-%m-%d')";
		} else if (!DataTypeUtil.validate(editedDate1) && DataTypeUtil.validate(editedDate2))
		{
			sel_edited_date = "K.EDITED_DATE < STR_TO_DATE('" + editedDate2 + "','%Y-%m-%d')";
		}
		if (DataTypeUtil.validate(sel_edited_date))
		{
			sel_edited_date = " AND " + sel_edited_date;
		}
		// 文档相关用户
		if (DataTypeUtil.validate(createdBy))
		{
			if (!DataTypeUtil.validate(sel_users))
			{
				sel_users = "K.CREATED_BY IN (SELECT USER_ID FROM CM_USERS WHERE CM_USERS.ACTUAL_NAME LIKE '%" + createdBy + "%')";
			}
		}
		if (DataTypeUtil.validate(assignedTo))
		{
			if (!DataTypeUtil.validate(sel_users))
			{
				sel_users = "K.ASSIGNED_TO IN (SELECT USER_ID FROM CM_USERS WHERE CM_USERS.ACTUAL_NAME LIKE '%" + assignedTo + "%')";

			} else
			{
				sel_users = sel_users + " OR K.ASSIGNED_TO IN (SELECT USER_ID FROM CM_USERS WHERE CM_USERS.ACTUAL_NAME LIKE '%" + assignedTo + "%')";
			}
		}
		if (DataTypeUtil.validate(approvedBy))
		{
			if (!DataTypeUtil.validate(sel_users))
			{
				sel_users = "K.APPROVED_BY IN (SELECT USER_ID FROM CM_USERS WHERE CM_USERS.ACTUAL_NAME LIKE '%" + approvedBy + "%')";

			} else
			{
				sel_users = sel_users + " OR K.APPROVED_BY IN (SELECT USER_ID FROM CM_USERS WHERE CM_USERS.ACTUAL_NAME LIKE '%" + approvedBy + "%')";
			}
		}
		if (DataTypeUtil.validate(editedBy))
		{
			if (!DataTypeUtil.validate(sel_users))
			{
				sel_users = "K.DOC_ID IN(SELECT DOC_ID FROM KM_DOCVER WHERE EDITED_BY IN(SELECT USER_ID FROM CM_USERS WHERE CM_USERS.ACTUAL_NAME LIKE '%" + editedBy + "%'))";

			} else
			{
				sel_users = sel_users + " OR K.DOC_ID IN (SELECT DOC_ID FROM KM_DOCVER WHERE EDITED_BY IN (SELECT USER_ID FROM CM_USERS WHERE CM_USERS.ACTUAL_NAME LIKE '%" + editedBy + "%'))";
			}
		}
		if (DataTypeUtil.validate(author))
		{
			if (!DataTypeUtil.validate(sel_users))
			{
				sel_users = " K.AUTHOR LIKE '%" + author + "%'";
			} else
			{
				sel_users = sel_users + " OR K.AUTHOR LIKE '%" + author + "%'";
			}
		}
		if (DataTypeUtil.validate(sel_users))
		{
			sel_users = " AND (" + sel_users + ")";
		}
		String projIds = "";
		if (DataTypeUtil.validate( projId)){
			projIds = " and K.PROJ_ID IN ('" + projId.replaceAll(",", "','") + "') and  K.DOC_GRADE='2' ";
		}else{
			projIds=" and  K.DOC_GRADE='0' ";
		} 
		String sql = sel_keywords  + sel_docType + sel_created_date + sel_edited_date + sel_users + projIds + sel_order ;
        if(DataTypeUtil.validate(sel_keywords)){
	   sql = "SELECT '' IMG,K.DOC_ID, K.DOC_GRADE,K.PROJ_ID,K.FOLDER_ID,K.FILE_NAME , (select proj_name from cm_proj where proj_id = K.PROJ_ID )PROJ_NAME ,K.DOC_NUMBER, K.TITLE, K.EDITION,K.FRACTION,"+
		"		K.STATUS,K.AUTHOR,date_format(K.CREATED_DATE,'%Y-%m-%d')   CREATED_DATE,date_format(K.EDITED_DATE,'%Y-%m-%d')  EDITED_DATE, CASE K.CREATED_BY WHEN '"+userId+"' "+
		"			 THEN 'Y' ELSE 'N' END AS CREATED_FLAG,CASE K.ASSIGNED_TO WHEN '"+userId+"' "+
		"			 THEN 'Y' ELSE 'N' END AS ASSIGNED_FLAG,CASE WHEN "+
		"			 K.FRACTION>=(SELECT LOW_FRACTION FROM CM_USERS WHERE USER_ID='"+userId+"') "+
		"			AND K.FRACTION<=(SELECT HIGH_FRACTION FROM CM_USERS WHERE USER_ID='"+userId+"') "+
		"			THEN 'Y' ELSE 'N' END AS FRACTION_FLAG,"+
		"			D.TITLE AS FOLDER_NAME FROM KM_DOC K  LEFT  JOIN KM_FOLDER D ON K.FOLDER_ID=D.FOLDER_ID WHERE 1=1  "+
		"			AND (K.CREATED_BY='"+userId+"' OR K.ASSIGNED_TO='"+userId+"' OR K.APPROVED_BY='"+userId+"' OR ((K.FRACTION>=(SELECT LOW_FRACTION "+
		"			FROM CM_USERS WHERE USER_ID='"+userId+"') AND K.FRACTION<=(SELECT HIGH_FRACTION "+
		"			FROM CM_USERS WHERE USER_ID='"+userId+"')) AND K.STATUS='APPROVED')) "+sql +" ORDER BY K.DOC_GRADE ASC ,K.PROJ_ID ,K.DOC_NUMBER ASC";
       List<Map<String,Object>> list = baseJdbcDao.queryListMap(sql);
        if(DataTypeUtil.validate(list)){
    	   for(Map<String,Object> map:list){
    		//   String fold_path = kmFolderService.getFullPathNameByFolderId(map.get("FOLDER_ID").toString());
    		//   map.put("PATH", fold_path);
    		   String img = getFileImage(DataTypeUtil.formatDbColumn(map.get("FILE_NAME")));
    		   map.put("IMG", "<span style='width:17px' class='"+img+"'>&nbsp;&nbsp;&nbsp;&nbsp;</span>");
    	   }
       } 
       return  list;
        }else{
        	return null;
        }
	}

	@Override
	public String getTempFileName(String fileExtName) {
		int ran = (int) Math.floor(Math.random() * 1000);
		String fileName = "";
		if (fileExtName.equals(""))
			fileName = DateUtil.getTodaytime().replace("-", "").replace(" ", "").replace(":", "") + ran;
		else
			fileName = DateUtil.getTodaytime().replace("-", "").replace(" ", "").replace(":", "") + ran + "." + fileExtName;
		return fileName;
	}

	@Override
	public String saveDoc(File uploadfile, String url) {		
		String proj_name = AppSetting.PROJECT_NAME.substring(1); 
 		 String path = AppSetting.PROJECT_PATH.substring(0, AppSetting.PROJECT_PATH.lastIndexOf(proj_name));		 
		 File file = new File(path);
		 if(!file.exists()){
			 file.mkdirs();
		 }
		 FileUtil.uploadFile(uploadfile,path+url) ;
		 return "success";
	}

	@Override
	public String downPackageDoc(String docGrade, String folderId, UserInfo userInfo) {
		String UserId = userInfo.getUserId();
		String ProjId = userInfo.getProjId();
		String fulldir = "";
		String title ="";
		String originPath = "";
		String newpath="";
		Map<String, Object> folderMap = kmFolderService.getFolderInfoByFolderId(folderId);
		if(DataTypeUtil.validate(folderMap)){
		 title = DataTypeUtil.formatDbColumn(folderMap.get("TITLE"));
		 Pattern pattern = Pattern.compile("[/////:/\\\\/*/?/</>/#/|]");
		 Matcher matcher  = pattern.matcher(title);
		 if(matcher.find()){
			 title = matcher.replaceAll("_");
		 }
		originPath = kmFolderService.getFullPathByFolderId(folderId);
    	String kmrealpath=AppSetting.PROJECT_PATH+"/KM";
		originPath=kmrealpath+"/DOC"+originPath;
		newpath=kmrealpath+"/temp/"+UserId;
		File file=new File(newpath);
		   if(file.exists()){
			   FileUtil.delFolder(newpath);
		   }
		DownAllDoc(docGrade,folderId,ProjId,UserId,kmrealpath,originPath,newpath+"/"+title);
		FileUtil.zip(newpath+"/"+title, newpath + "/" + title + ".zip");
		FileUtil.delFolder(newpath+"/"+title);
		fulldir =AppSetting.PROJECT_NAME+"/KM/temp/"+UserId+"/"+ title + ".zip";
		}
		return fulldir;
	}
	public  void DownAllDoc(String doc_grade,String folder_id,String proj_id,String user_id,String kmrealpath,String originPath,String newpath){
		 
			    String sql = "";
 
				File filepath=new File(newpath);
				if(!filepath.exists()){
				   filepath.mkdirs();
				}
				if(doc_grade.equals("2")){
					sql="SELECT A.DOC_ID," +
							" A.FILE_NAME," +
							"A.DOC_NUMBER," +
							"A.TITLE," +
							" D.TITLE AS FOLDER_NAME " +
							"FROM KM_DOC A " +
							"LEFT OUTER JOIN CM_CODE B ON A.DOC_TYPE = B.CODE_NAME AND B.CODE_TYPE='DOC_TYPE' " +
							"LEFT OUTER JOIN CM_USERS C ON A.CHECKOUT_BY = C.USER_ID" +
							" LEFT OUTER JOIN KM_FOLDER D  ON A.FOLDER_ID = D.FOLDER_ID" +
							" WHERE A.DELETED_FLAG!='1' " +
							"AND A.FOLDER_ID IN('"+folder_id+"') " +
							"AND A.PROJ_ID='"+proj_id+"' " +
							"AND (A.CREATED_BY='"+user_id+"' " +
							"OR A.ASSIGNED_TO='"+user_id+"' " +
							"OR A.APPROVED_BY='"+user_id+"' " +
							"OR ((A.FRACTION>=(SELECT LOW_FRACTION FROM CM_USERS WHERE USER_ID='"+user_id+"') " +
							"AND A.FRACTION<=(SELECT HIGH_FRACTION FROM CM_USERS WHERE USER_ID='"+user_id+"')) " +
							"AND A.STATUS='APPROVED'))  ORDER BY A.EDITED_DATE DESC";
				}else{
					sql="SELECT A.DOC_ID ," +
							" A.FILE_NAME," +
							"A.DOC_NUMBER," +
							"A.TITLE," +
							" D.TITLE AS FOLDER_NAME " +
							"FROM KM_DOC A " +
							"LEFT OUTER JOIN CM_CODE B ON A.DOC_TYPE = B.CODE_NAME AND B.CODE_TYPE='DOC_TYPE' " +
							"LEFT OUTER JOIN CM_USERS C ON A.CHECKOUT_BY = C.USER_ID LEFT " +
							"OUTER JOIN KM_FOLDER D  ON A.FOLDER_ID = D.FOLDER_ID " +
							"WHERE A.DELETED_FLAG!='1' " +
							"AND A.FOLDER_ID IN('"+folder_id+"') AND " +
							"(A.CREATED_BY='"+user_id+"' " +
							"OR A.ASSIGNED_TO='"+user_id+"' " +
							"OR A.APPROVED_BY='"+user_id+"' " +
							"OR  ((A.FRACTION>=(SELECT LOW_FRACTION FROM CM_USERS WHERE USER_ID='"+user_id+"') " +
							"AND A.FRACTION<=(SELECT HIGH_FRACTION FROM CM_USERS WHERE USER_ID='"+user_id+"')) " +
							"AND A.STATUS='APPROVED')) ORDER BY A.DOC_NUMBER DESC";
				}
				List<Map<String, Object>> doclist =baseJdbcDao.queryListMap(sql);
				if(DataTypeUtil.validate(doclist)){
					for(Map<String, Object> docmap :doclist){
					String doc_name = DataTypeUtil.formatDbColumn(docmap.get("FILE_NAME"));
					String doc_number = DataTypeUtil.formatDbColumn(docmap.get("DOC_NUMBER"));
					String doc_title = DataTypeUtil.formatDbColumn(docmap.get("TITLE"));
					 Pattern pattern = Pattern.compile("[/////:/\\\\/*/?/</>/#/|]");
					 Matcher matcher  = pattern.matcher(doc_title);
					 if(matcher.find()){
						 doc_title = matcher.replaceAll("_");
					 }
					 Matcher matcher2  = pattern.matcher(doc_number);
					 if(matcher2.find()){
						 doc_number = matcher2.replaceAll("_");
					 }					 
					if(DataTypeUtil.validate(doc_name)){
					String filetype=doc_name.substring(doc_name.lastIndexOf("."));
					File originFile = new File(originPath+"/"+doc_name);
					if(originFile.exists()&&originFile.isFile()){
						try {
							FileUtils.copyFile(new File(originPath+"/"+doc_name), new File(newpath+"/"+doc_number+"_"+doc_title+filetype));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					}
					}
				}
				List<Map<String, Object>> folderlist = baseJdbcDao.queryListMap("select title,folder_id from km_folder where parent_folder_id = '"+folder_id+"'");
	            if(DataTypeUtil.validate(folderlist)){
	            	for(Map<String, Object> foldermap :folderlist){
	    			String son_folder_id = DataTypeUtil.formatDbColumn(foldermap.get("FOLDER_ID")); 		
	    			String  title = DataTypeUtil.formatDbColumn(foldermap.get("TITLE"));
	    			String SonoriginPath = kmFolderService.getFullPathByFolderId(son_folder_id);
	     			SonoriginPath = kmrealpath+"/DOC"+SonoriginPath;
	    			DownAllDoc(doc_grade,son_folder_id,proj_id,user_id,kmrealpath,SonoriginPath,newpath+"/"+title);
	            	}
	            }
 
				    	
		}

	@Override
	public String MoveDoc(String docIds, String folderId, String docGrade,
			String projId) { 
		if(DataTypeUtil.validate(docIds)&&DataTypeUtil.validate(folderId)){
		String[] docIdList = docIds.split(",");
		String sql  = "SELECT FOLDER_ID ,TITLE FROM KM_FOLDER WHERE FOLDER_ID IN (SELECT FOLDER_ID FROM KM_DOC WHERE DOC_ID = '"+docIdList[0]+"')";
		Map<String,Object> map  = baseJdbcDao.queryMap(sql);
		String SourceFolderId = map.get("FOLDER_ID").toString();
		if(map.get("FOLDER_ID").toString().equals(folderId)){
			return "success";
		}
		if(map.get("TITLE").toString().equals("1.5 项目进展图片")){
			return "禁止移动'项目进展图片'目录下的文件！";
		}
		String sourcePath = kmFolderService.getFullPathByFolderId(SourceFolderId);
		if(DataTypeUtil.validate(sourcePath)){
			sourcePath = AppSetting.PROJECT_PATH+"/KM/DOC"+sourcePath;
		}
		String targetPath = kmFolderService.getFullPathByFolderId(folderId);
		String public_log = targetPath;
		if(DataTypeUtil.validate(targetPath)){
			targetPath = AppSetting.PROJECT_PATH+"/KM/DOC"+targetPath;
		}
		File targetDir = new File(targetPath);
		if(!targetDir.exists()){
			targetDir.mkdirs();
		}
		for(int i=0;i<docIdList.length;i++){
			sql = "select file_name from km_doc where doc_id = '"+docIdList[i]+"'";
		    String fileName = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
		    File file = new File(sourcePath,fileName);
		    if(file.exists()){
	    	    sql = "update km_doc set public_loc = '"+public_log+"',folder_id = '"+folderId+"' where doc_id = '"+docIdList[i]+"'";
	    	    baseJdbcDao.update(sql);
		    	if(FileUtil.copyFile(sourcePath+"/"+fileName, targetPath+"/"+fileName)){
			    	file.delete();		    		
                    String Dvrsql = "SELECT FILE_NAME FROM  KM_DOCVER WHERE DOC_ID ='"+docIdList[i]+"' UNION SELECT PUBLIC_LOC FROM  KM_ATTCH WHERE DOC_ID ='"+docIdList[i]+"'";
                    List<Map<String, Object>> docList = baseJdbcDao.queryListMap(Dvrsql);
                    if(DataTypeUtil.validate(docList)){
                    	for (Map<String, Object> docMap : docList)
    					{
                    		String AttchName = docMap.get("FILE_NAME").toString();
                    		File Attchfile = new File(sourcePath,AttchName);
                    		if(Attchfile.exists()){
	                    		if(FileUtil.copyFile(sourcePath+"/"+AttchName, targetPath+"/"+AttchName)){
	                    			Attchfile.delete();	
	                    		}
                    		}
    					}
                    }
		    	}		    	
		    }
		}
		
	    	return "success";
		}else{
			return "移动的文档至少一个，移动的目标目录必须选择！";
		}
	}

	@Override
	public void AddUserFile(String fileName, String fileType, String filePath,
			String userId, String id) {
		String sql = "INSERT INTO CM_USERFILE(WID, USERFILE_ID, USER_ID, FILEPATH,FILENAME, TYPE, CURRENTDATE)" +
				"VALUES('"+Guid.getGuid()+"','"+id+"','"+userId+"','"+filePath+"','"+fileName+"','"+fileType+"',now())";
		baseJdbcDao.insert(sql);
	}

	@Override
	public List<Map<String, Object>> PlusFileTable(UserInfo userInfo,
			int start, int number) {
		String sql = "SELECT FILENAME ,FILEPATH,USERFILE_ID ,TYPE FROM CM_USERFILE WHERE USER_ID = '"+userInfo.getUserId()+"' ORDER BY CURRENTDATE DESC LIMIT "+start+" ,"+number;
		return baseJdbcDao.queryListMap(sql);
	}

	@Override
	public Object getPlusFileTableCount(UserInfo userInfo) {
		String sql = "SELECT COUNT(WID) FROM CM_USERFILE WHERE USER_ID = '"+userInfo.getUserId()+"'";
		return baseJdbcDao.getCount(sql);
	}

	@Override
	public String DeletePlusFile(String userfileId) {
		if(DataTypeUtil.validate(userfileId)){
			String sql = "DELETE FROM CM_USERFILE WHERE USERFILE_ID = '"+userfileId+"'";
			String Path = AppSetting.PROJECT_PATH+"/KM/PLUS/"+userfileId;
			File file = new File(Path);	
			//这是物理删除（可以把服务器上的数据或本地的数据连文件一起删除
//			if(file.exists()){
//		       FileUtil.delFolder(Path);
//			}
			baseJdbcDao.delete(sql);
			return "success";
		}else{
			return "删除出错!";
		}
		
	}

	@Override
	public String copyDoc(String docId, String type, UserInfo userInfo) {
		String result="";
		try{
			String sql = "select * from km_doc where doc_id = '"+docId+"'";
			Map<String, Object> docMap = baseJdbcDao.queryMap(sql);
			String file_name = DataTypeUtil.formatDbColumn(docMap.get("FILE_NAME"));
			String folder_id = DataTypeUtil.formatDbColumn(docMap.get("FOLDER_ID"));		
			String nfile_name = getTempFileName(file_name.substring(file_name.indexOf(".")+1));
			String folderPath = kmFolderService.getFullPathByFolderId(folder_id);
		    String newId = Guid.getGuid();
		     KmDoc kmDoc = new KmDoc();
		     kmDoc.setFolderId(folder_id);
		     kmDoc.setDocId(newId);
		     kmDoc.setProjId(DataTypeUtil.formatDbColumn(docMap.get("PROJ_ID")));
		     kmDoc.setCreatedBy(userInfo.getUserId());
		     kmDoc.setDocGrade(DataTypeUtil.formatDbColumn(docMap.get("DOC_GRADE")));
		     kmDoc.setTitle("(复制)"+DataTypeUtil.formatDbColumn(docMap.get("TITLE")));
		     kmDoc.setFileName(nfile_name);
		     kmDoc.setPublicLoc(folderPath);
		     kmDoc.setPrivateLoc(DataTypeUtil.formatDbColumn(docMap.get("PRIVATE_LOC")));
		     kmDoc.setDocType(DataTypeUtil.formatDbColumn(docMap.get("DOC_TYPE")));
		     kmDoc.setCreatedByName(userInfo.getActualName());
		     kmDoc.setBaseMasterKey(DataTypeUtil.formatDbColumn(docMap.get("BASE_MASTER_KEY"))); 
		     kmDoc.setBaseLinkId(DataTypeUtil.formatDbColumn(docMap.get("BASE_LINK_ID")));
		     InsertDoc(kmDoc);	
		     String OrgbaseDir =  AppSetting.PROJECT_PATH+"/KM/DOC";
		     
		     if(type.equals("是")){
		    	 sql ="insert into struct_info(wid,INFO_ID,RELATEDATE,DOC_ID,PROJ_ID,OFFICETPL_ID)SELECT '"+Guid.getGuid()+"','"+Guid.getGuid()+"',RELATEDATE,'"+newId+"',PROJ_ID,OFFICETPL_ID FROM struct_info WHERE DOC_ID = '"+docId+"'";
		    	 baseJdbcDao.insert(sql);
		     }
		     
			 if(new File(OrgbaseDir+folderPath + "/" + file_name).exists()){
				FileUtil.copyFile(OrgbaseDir+folderPath + "/" + file_name, OrgbaseDir+folderPath + "/" + nfile_name);
			}
			 result = "success";
		}catch(Exception e){
			result=e.getMessage();
			e.printStackTrace();
		}finally{
			return result ;
		}
		
	}

	@Override
	public List ShowDocTable(String folderId, String docGrade, String isAll,
			String keyWord, UserInfo userInfo) {
		String sql="";
		if(docGrade.equals("0")){
	  		 String FoldString = "'"+folderId+"'";
	  		 if(DataTypeUtil.validate(isAll)&&isAll.equals("1")){
	  			FoldString = "SELECT FOLDER_ID  FROM KM_FOLDER  WHERE  INSTR(FOLDER_ID_PATH, '"+folderId+"') > 0 ";
	  		 }
	  		 sql ="SELECT '' AS IMG,A.CERTIFIED_TYPE,(SELECT ACTUAL_NAME FROM CM_USERS E WHERE A.CREATED_BY=E.USER_ID) AS ACTUAL_NAME,"+
				" A.WID,A.FILE_NAME,A.DOC_ID,A.DOC_NUMBER,A.TITLE,A.EDITION,A.STATUS,"+
				" A.FRACTION,A.AUTHOR,date_format(A.CREATED_DATE,'%Y-%m-%d') CREATED_DATE ,date_format(A.EDITED_DATE,'%Y-%m-%d') EDITED_DATE,B.REMARK,A.PROJ_ID,"+
				" (SELECT COUNT(B.ATTCH_ID) FROM KM_ATTCH B WHERE B.DOC_ID=A.DOC_ID) AS ATTCH_NUM,"+
				" CASE A.CHECKOUT_FLAG WHEN 'Y' THEN '' ELSE 'NONE' END AS FLAG,"+
				" A.CHECKOUT_FLAG,"+
				" CASE A.CHECKOUT_BY WHEN '"+userInfo.getUserId()+"' THEN 'Y' ELSE 'N' END AS USER_FLAG,"+
				" CASE A.CREATED_BY WHEN '"+userInfo.getUserId()+"' THEN 'Y' ELSE 'N' END AS CREATED_FLAG,"+
				" CASE A.ASSIGNED_TO WHEN '"+userInfo.getUserId()+"' THEN 'Y' ELSE 'N' END AS ASSIGNED_FLAG,"+
				" CASE A.APPROVED_BY WHEN '"+userInfo.getUserId()+"' THEN 'Y' ELSE 'N' END AS APPROVED_FLAG,"+
				" D.TITLE AS FOLDER_NAME FROM KM_DOC A LEFT OUTER JOIN CM_CODE B ON A.DOC_TYPE = B.CODE_NAME "+
				" AND B.CODE_TYPE='DOC_TYPE' LEFT OUTER JOIN CM_USERS C ON A.CHECKOUT_BY = C.USER_ID "+
				" LEFT OUTER JOIN KM_FOLDER D  ON A.FOLDER_ID = D.FOLDER_ID "+
				" WHERE A.DELETED_FLAG!='1' AND (A.DOC_NUMBER LIKE '%"+keyWord+"%' OR A.TITLE LIKE '%"+keyWord+"%' ) AND A.FOLDER_ID IN("+FoldString+") AND (A.CREATED_BY='"+userInfo.getUserId()+"' OR A.ASSIGNED_TO='"+userInfo.getUserId()+"'"+ 
				" OR A.APPROVED_BY='"+userInfo.getUserId()+"' OR  ((A.FRACTION>=(SELECT LOW_FRACTION FROM CM_USERS WHERE USER_ID='"+userInfo.getUserId()+"') "+
				" AND A.FRACTION<=(SELECT HIGH_FRACTION FROM CM_USERS WHERE USER_ID='"+userInfo.getUserId()+"')) "+
				" AND A.STATUS='APPROVED')) ORDER BY A.DOC_NUMBER DESC";
	  	}else{
	 		 String FoldString = "'"+folderId+"'";
	  		 if(DataTypeUtil.validate(isAll)&&isAll.equals("1")){
	  			FoldString = "SELECT FOLDER_ID  FROM KM_FOLDER  WHERE  INSTR(FOLDER_ID_PATH, '"+folderId+"') > 0 AND PROJ_ID = '"+userInfo.getProjId()+"'";
	  		 }
	  		sql="SELECT '' AS IMG,A.CERTIFIED_TYPE,A.FILE_NAME,A.DOC_ID,A.DOC_NUMBER,A.TITLE,A.EDITION,A.STATUS,"+
	  			" A.FRACTION,A.AUTHOR,date_format(A.CREATED_DATE,'%Y-%m-%d') CREATED_DATE ,date_format(A.EDITED_DATE,'%Y-%m-%d') EDITED_DATE,A.DOC_GRADE,B.REMARK,A.PROJ_ID,A.FOLDER_ID,"+
	  			" (SELECT COUNT(B.ATTCH_ID) FROM KM_ATTCH B WHERE B.DOC_ID=A.DOC_ID) AS ATTCH_NUM,"+
	  			" CASE A.CHECKOUT_FLAG WHEN 'Y' THEN '' ELSE 'NONE' END AS FLAG,A.CHECKOUT_FLAG,"+
	  			" CASE A.CHECKOUT_BY WHEN '"+userInfo.getUserId()+"' THEN 'Y' ELSE 'N' END AS USER_FLAG,"+
	  			" CASE A.CREATED_BY WHEN '"+userInfo.getUserId()+"' THEN 'Y' ELSE 'N' END AS CREATED_FLAG,"+
	  			" CASE A.ASSIGNED_TO WHEN '"+userInfo.getUserId()+"' THEN 'Y' ELSE 'N' END AS ASSIGNED_FLAG,"+
	  			" (SELECT ACTUAL_NAME FROM CM_USERS WHERE USER_ID='"+userInfo.getUserId()+"')AS ACTUAL_NAME,"+
	  			" (SELECT CATG_SHORT_NAME FROM CM_CATVAL WHERE CATG_ID=(SELECT CATG_ID FROM CM_CATBASE WHERE BASE_MASTER_KEY=A.DOC_ID "+
	  			" AND CATG_TYPE_ID=(SELECT CATG_TYPE_ID FROM CM_CATTYPE WHERE CATG_TYPE='项目单元'))) AS TZ_UNION,"+
	  			" (SELECT CATG_SHORT_NAME FROM CM_CATVAL WHERE CATG_ID=(SELECT CATG_ID FROM CM_CATBASE WHERE BASE_MASTER_KEY=A.DOC_ID "+
	  			" AND CATG_TYPE_ID=(SELECT CATG_TYPE_ID FROM CM_CATTYPE WHERE CATG_TYPE='图纸类别'))) AS TZ_TYPE,"+
	  			" (SELECT CATG_SHORT_NAME FROM CM_CATVAL WHERE CATG_ID=(SELECT CATG_ID FROM CM_CATBASE WHERE BASE_MASTER_KEY=A.DOC_ID "+
	  			" AND CATG_TYPE_ID=(SELECT CATG_TYPE_ID FROM CM_CATTYPE WHERE CATG_TYPE='图纸设计单位'))) AS TZ_DESCO,"+
	  			" D.TITLE AS FOLDER_NAME,A.DOC_COPYS,A.DOC_PAGENO FROM KM_DOC A LEFT OUTER JOIN CM_CODE B ON A.DOC_TYPE = B.CODE_NAME "+
	  			" AND B.CODE_TYPE='DOC_TYPE' LEFT OUTER JOIN CM_USERS C ON A.CHECKOUT_BY = C.USER_ID LEFT OUTER JOIN KM_FOLDER D  "+
	  			" ON A.FOLDER_ID = D.FOLDER_ID WHERE A.DELETED_FLAG!='1'  AND (A.DOC_NUMBER LIKE '%"+keyWord+"%' OR A.TITLE LIKE '%"+keyWord+"%' )  AND A.FOLDER_ID IN("+FoldString+") "+
	  			" AND A.PROJ_ID='"+userInfo.getProjId()+"'  AND (A.CREATED_BY='"+userInfo.getUserId()+"' "+
	  			" OR A.ASSIGNED_TO='"+userInfo.getUserId()+"' OR A.APPROVED_BY='"+userInfo.getUserId()+"' "+
	  			" OR ((A.FRACTION>=(SELECT LOW_FRACTION FROM CM_USERS WHERE USER_ID='"+userInfo.getUserId()+"') "+
	  			" AND A.FRACTION<=(SELECT HIGH_FRACTION FROM CM_USERS WHERE USER_ID='"+userInfo.getUserId()+"')) AND A.STATUS='APPROVED'))  "+
	  			" ORDER BY A.DOC_NUMBER DESC";
	  	}
		return  baseJdbcDao.queryListMap(sql);
	}

} 