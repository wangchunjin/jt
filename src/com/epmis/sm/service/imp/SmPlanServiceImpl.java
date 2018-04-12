package com.epmis.sm.service.imp;

import com.epmis.sm.service.SmPlanService;
import com.epmis.sm.vo.SmPlan;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DESEncrypt;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.DateUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("smPlanService")
public class SmPlanServiceImpl implements SmPlanService
{



  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;
  
  public List<Map<String, Object>> ShowSmPlanTree(String parentId,String moduleCode,UserInfo userInfo)
  {
	   
    List items = new ArrayList();
/*     if(parentId.equals("0")){
    	 findChildEpsByParentId("0","")
     }else{
    	 
     }*/
    if(DataTypeUtil.validate(moduleCode)){
     items = findChildSmPlanByParentId(parentId,moduleCode,userInfo);
    }
    return items;
  }
  
  public List<Map<String, Object>> findChildSmPlanByParentId(String parentId,String moduleCode,UserInfo userInfo)
  {
	  List<Map<String, Object>> ChildrenList = findTreeBySql(parentId,moduleCode,userInfo);
    List items = new ArrayList();
    for (Map<String, Object> item : ChildrenList) {
      String node_type = item.get("NODE_TYPE").toString();
	  	if ("SWBS".equalsIgnoreCase(node_type) && DataTypeUtil.validate(node_type))
		{
		 
			 item.put("iconCls", "icon-wbs"); 
		} else if ("PROJ".equalsIgnoreCase(node_type) && DataTypeUtil.validate(node_type))
		{
			item.put("iconCls", "icon-proj");
			 
		} else
		{
			item.put("iconCls", "icon-task");
			 
		}
      if (findTreeBySql(item.get("PLAN_ID").toString(),moduleCode,userInfo).size() > 0) {
        item.put("state", "closed");
       
      }
    //  item.put("children", findTreeBySql(item.get("id").toString()));
      items.add(item);
    }
    return items;
  }
  public List<Map<String, Object>> findTreeBySql(String ParentId,String moduleCode,UserInfo userInfo){

	  List<Map<String, Object>> result = null;
	  if (DataTypeUtil.validate(ParentId)&&DataTypeUtil.validate(moduleCode)){
	     String sql = "SELECT A.FOLDER_ID,A.PLAN_ID,A.PLAN_SHORT_NAME,A.PLAN_NAME,A.NODE_TYPE,A.CREATE_USER_ID,DOC_SWBS_ID,CASE A.PLAN_STATUS WHEN '0' THEN '未开始' WHEN '1' THEN '进行中' WHEN '2' THEN '已完成' END AS PLAN_STATUS ,(SELECT USER_NAME FROM CM_USERS WHERE USER_ID=A.CREATE_USER_ID)AS CREATE_USER, A.IS_MAJOR IS_MAJOR_TEMP,S.IS_MAJOR FROM SM_PLAN A LEFT JOIN SM_SWBS S  ON A.SWBS_ID = S.SWBS_ID ";
				sql += " WHERE  PARENT_PLAN_ID = '" + ParentId + "'  AND PROJ_ID = '"+userInfo.getProjId()+"'";
				sql += " ORDER BY A.SEQ_NUM ASC";
			
		  if(ParentId.equals("0")){
				    String planSql = " select plan_id from sm_plan where  plan_short_name =(select plan_short_name from SM_PLAN_MATCHUP where f_name = '"+moduleCode+"') AND PROJ_ID = '"+userInfo.getProjId()+"'";
			        String  id = baseJdbcDao.getFieldValue(planSql).toString();
			        if(DataTypeUtil.validate(id)){
			        	sql = "SELECT A.FOLDER_ID,A.PLAN_ID,A.PLAN_SHORT_NAME,A.PLAN_NAME,A.NODE_TYPE,A.CREATE_USER_ID,DOC_SWBS_ID,CASE A.PLAN_STATUS WHEN '0' THEN '未开始' WHEN '1' THEN '进行中' WHEN '2' THEN '已完成' END AS PLAN_STATUS ,(SELECT USER_NAME FROM CM_USERS WHERE USER_ID=A.CREATE_USER_ID)AS CREATE_USER, A.IS_MAJOR IS_MAJOR_TEMP,S.IS_MAJOR FROM SM_PLAN A LEFT JOIN SM_SWBS S  ON A.SWBS_ID = S.SWBS_ID ";
						sql += " WHERE  PLAN_ID = '" + id + "'  AND PROJ_ID = '"+userInfo.getProjId()+"'";
						sql += " ORDER BY A.SEQ_NUM";
			        }
			  }
		  result =  baseJdbcDao.queryListMap(sql); 
	  }
	return result;
	}
  
  public List<Map<String, Object>> ShowSmPlanMoveTree(String parentId,String moduleCode,UserInfo userInfo)
  {
	   
	  List<Map<String, Object>> ChildrenList = findMoveTreeBySql(parentId,moduleCode,userInfo);
	    List items = new ArrayList();
	    for (Map<String, Object> item : ChildrenList) {
	      String node_type = item.get("NODE_TYPE").toString();
		  	if ("SWBS".equalsIgnoreCase(node_type) && DataTypeUtil.validate(node_type))
			{
			 
				 item.put("iconCls", "icon-wbs"); 
			} else if ("PROJ".equalsIgnoreCase(node_type) && DataTypeUtil.validate(node_type))
			{
				item.put("iconCls", "icon-proj");
				 
			} else
			{
				item.put("iconCls", "icon-task");
				 
			}
	      if (findMoveTreeBySql(item.get("PLAN_ID").toString(),moduleCode,userInfo).size() > 0) {
	        item.put("state", "closed");
	       
	      } 
	      items.add(item);
	    }
	    return items;
  }
   
  public List<Map<String, Object>> findMoveTreeBySql(String ParentId,String moduleCode,UserInfo userInfo){

	  List<Map<String, Object>> result = null;
	  if (DataTypeUtil.validate(ParentId)&&DataTypeUtil.validate(moduleCode)){
	     String sql = "SELECT A.FOLDER_ID,A.PLAN_ID,A.PLAN_SHORT_NAME,A.PLAN_NAME,A.NODE_TYPE,A.CREATE_USER_ID,DOC_SWBS_ID,CASE A.PLAN_STATUS WHEN '0' THEN '未开始' WHEN '1' THEN '进行中' WHEN '2' THEN '已完成' END AS PLAN_STATUS ,(SELECT USER_NAME FROM CM_USERS WHERE USER_ID=A.CREATE_USER_ID)AS CREATE_USER, A.IS_MAJOR IS_MAJOR_TEMP,S.IS_MAJOR FROM SM_PLAN A LEFT JOIN SM_SWBS S  ON A.SWBS_ID = S.SWBS_ID ";
				sql += " WHERE  PARENT_PLAN_ID = '" + ParentId + "'  AND PROJ_ID = '"+userInfo.getProjId()+"' AND A.NODE_TYPE !='TASK'";
				sql += " ORDER BY A.SEQ_NUM ASC";
			
		  if(ParentId.equals("0")){
				    String planSql = " select plan_id from sm_plan where  plan_short_name =(select plan_short_name from SM_PLAN_MATCHUP where f_name = '"+moduleCode+"') AND PROJ_ID = '"+userInfo.getProjId()+"'";
			        String  id = baseJdbcDao.getFieldValue(planSql).toString();
			        if(DataTypeUtil.validate(id)){
			        	sql = "SELECT A.FOLDER_ID,A.PLAN_ID,A.PLAN_SHORT_NAME,A.PLAN_NAME,A.NODE_TYPE,A.CREATE_USER_ID,DOC_SWBS_ID,CASE A.PLAN_STATUS WHEN '0' THEN '未开始' WHEN '1' THEN '进行中' WHEN '2' THEN '已完成' END AS PLAN_STATUS ,(SELECT USER_NAME FROM CM_USERS WHERE USER_ID=A.CREATE_USER_ID)AS CREATE_USER, A.IS_MAJOR IS_MAJOR_TEMP,S.IS_MAJOR FROM SM_PLAN A LEFT JOIN SM_SWBS S  ON A.SWBS_ID = S.SWBS_ID ";
						sql += " WHERE  PLAN_ID = '" + id + "'  AND PROJ_ID = '"+userInfo.getProjId()+"' AND A.NODE_TYPE !='TASK' ";
						sql += " ORDER BY A.SEQ_NUM";
			        }
			  }
		  result =  baseJdbcDao.queryListMap(sql); 
	  }
	return result;
	}
  
	@Override
	public List PlanDocTree(String base_master_key,String type, UserInfo userInfo) {
		List items = new ArrayList();
		if(DataTypeUtil.validate(base_master_key)){
		 List<Map<String, Object>> ChildrenList = findDocTreeBySql(base_master_key,type,userInfo);
		    
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
	public List<Map<String, Object>> findDocTreeBySql(String base_master_key,String type,UserInfo userInfo){

		  List<Map<String, Object>> result = null; 
		  if (DataTypeUtil.validate(base_master_key)){

			  String  sql="";
			  if(DataTypeUtil.validate(type)&&type.equals("doc")){
				  sql   ="SELECT DOC_ID,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')CREATED_DATE,(SELECT count(ATTCH_ID) FROM KM_ATTCH WHERE DOC_ID=K.DOC_ID) NUM, FOLDER_ID,DOC_NUMBER,TITLE,AUTHOR,DATE_LINKED,PRIVATE_LOC,FILE_NAME,CASE STATUS WHEN 'INIT' THEN '新建' WHEN 'PENDING' THEN '审批中' WHEN 'SUSPENDED' THEN '挂起' WHEN 'TERMINATED' THEN '终止' WHEN 'APPROVED' THEN '已批准' WHEN 'PENDING' THEN '审批中' END AS STATUS, EDITION,FRACTION,(SELECT ACTUAL_NAME FROM CM_USERS WHERE USER_ID=CREATED_BY) AS ACTUAL_NAME,(select officetpl_id  from struct_info t  WHERE t.doc_id = K.doc_id ) struct FROM KM_DOC K WHERE BASE_MASTER_KEY='"+base_master_key+"'  AND DELETED_FLAG='0' ORDER BY CREATED_DATE DESC";
 
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
	    if ((!paramString.equals("")) && (paramString.indexOf(".") >= 0))
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
public SmPlan GetSmPlan(SmPlan smPlan) {
  	String sql = "select * from sm_plan where plan_id = '"+smPlan.getPlanId()+"'";
    Map  map  = baseJdbcDao.queryMap(sql);
    if(DataTypeUtil.validate(map)){ 
    	smPlan.setPlanShortName(DataTypeUtil.formatDbColumn(map.get("PLAN_SHORT_NAME")));
    	smPlan.setPlanName(DataTypeUtil.formatDbColumn(map.get("PLAN_NAME")));
    	smPlan.setSeqNum( Integer.valueOf(DataTypeUtil.formatDbColumn(DataTypeUtil.validate(map.get("SEQ_NUM")) ? map.get("SEQ_NUM").toString() : "0")));
    	smPlan.setRemark(DataTypeUtil.formatDbColumn(map.get("REMARK")));
    }
	return smPlan;
}

@Override
public String UpdateSmPlan(SmPlan smPlan, String type) {
  	String result = "";
  	String sql=""; 
  	if(type.equals("info")){
		sql="update km_folder set title='"+smPlan.getPlanShortName()+smPlan.getPlanName()+"',DEF_CODE='"+smPlan.getPlanShortName()+"-',seq_num = "+smPlan.getSeqNum()+" where folder_id ='"+smPlan.getPlanId()+"'";
		baseJdbcDao.update(sql); 
        sql = "update sm_plan set plan_short_name ='"+smPlan.getPlanShortName()+"',Plan_name = '"+smPlan.getPlanName()+"',seq_num = "+smPlan.getSeqNum()+" where plan_id ='"+smPlan.getPlanId()+"'";
  	}else{
        sql = "update sm_plan set remark ='"+smPlan.getRemark().replaceAll("'", "\'").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")+"'where  plan_id ='"+smPlan.getPlanId()+"'";	
  	}
    if(baseJdbcDao.update(sql)){
  		result = type;
  	}
  	return result;
}

@Override
public List ShowPlanUserTable(String planId, String projId) { 
	
	if(DataTypeUtil.validate(planId)&&DataTypeUtil.validate(projId)){ 
		String sql ="  SELECT '' OTHER, PLANUSER_ID,PLAN_ID,A.USER_ID,(SELECT USER_NAME  FROM CM_USERS WHERE USER_ID = A.USER_ID)"+
	   "AS USER_NAME, (SELECT  ACTUAL_NAME FROM CM_USERS WHERE USER_ID = A.USER_ID) AS ACTUAL_NAME FROM SM_PLANUSER A WHERE PROJ_ID='"+projId+"' AND PLAN_ID='"+planId+"'";
		return baseJdbcDao.queryListMap(sql);
	}else{
		return null;	
	}
  }  
	public List AddPlanUserTable(String planId, String projId) { 
		
		if(DataTypeUtil.validate(planId)&&DataTypeUtil.validate(projId)){  
			String sql ="   SELECT WID,USER_ID,USER_NAME,ACTUAL_NAME FROM CM_USERS WHERE USER_ID NOT IN " +
					" (SELECT A.USER_ID FROM SM_PLANUSER A WHERE A.PROJ_ID = '"+projId+"' AND PLAN_ID = '"+planId+"') AND USER_ID NOT IN (SELECT B.USER_ID FROM CM_USERS B WHERE B.DISABLE_FLAG = 1) ORDER BY USER_NAME ";;
			return baseJdbcDao.queryListMap(sql); 
		}else{
			return null;	
		}

    }

	@Override
	public String AddPlanUser(String planId, String projId, String userIds,String tableName) {
		String sql = " SELECT PLAN_ID FROM "+tableName+" WHERE (NODE_TYPE='SWBS' or NODE_TYPE='PROJ') AND INSTR( PLAN_ID_PATH,'"+planId+"')>0 AND PROJ_ID = '"+projId+"'";
		List<Map<String, Object>> listMap = baseJdbcDao.queryListMap(sql); 
		String[] ids = userIds.split(",");
		if(DataTypeUtil.validate(listMap)){
			for(Map<String, Object> map:listMap){ 
				for(int i=0;i<ids.length;i++){
					 if(baseJdbcDao.getCount("select count(wid) num from SM_PLANUSER where  PROJ_ID = '"+projId+"' AND PLAN_ID = '"+map.get("PLAN_ID")+"' AND USER_ID = '"+ids[i]+"'" )==0){
						 sql = " INSERT INTO SM_PLANUSER(WID ,PLANUSER_ID,PLAN_ID,PROJ_ID,USER_ID,ROLE_ID,USER_FLAG) VALUES ('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+map.get("PLAN_ID")+"','"+projId+"','"+ids[i]+"',0,'1')";
						 baseJdbcDao.insert(sql);
					 }
				}
			}
		}
		return "success";
	}

	@Override
	public String delPlanUser(String planId, String UserIds, String tableName, String projId){
		String sql = " SELECT PLAN_ID FROM "+tableName+" WHERE (NODE_TYPE='SWBS' or NODE_TYPE='PROJ') AND INSTR( PLAN_ID_PATH,'"+planId+"')>0 AND PROJ_ID = '"+projId+"'";
		List<Map<String, Object>> listMap = baseJdbcDao.queryListMap(sql); 
		UserIds = UserIds.replaceAll(",", "','"); 
		if(DataTypeUtil.validate(listMap)){ 
			for(Map<String, Object> map:listMap){ 
                 sql  = "delete from SM_PLANUSER where user_id in ('"+UserIds+"') and PROJ_ID = '"+projId+"' AND PLAN_ID = '"+map.get("PLAN_ID")+"' " ;
                 baseJdbcDao.delete(sql);
			}
		}
		return "success";
	}

	@Override
	public Map<String, Object> getSmPlanInfo(String plan_id) {
		// TODO Auto-generated method stub
		return baseJdbcDao.queryMap("select * from sm_plan where plan_id = '"+plan_id+"'");
	}

	@Override
	public String UpdateAssGuides(SmPlan smPlan) { 
		baseJdbcDao.update("update sm_plan set Ass_Guides ='"+smPlan.getAssGuides().replaceAll("'", "\'").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")+"'   where plan_id = '"+smPlan.getPlanId()+"'");
		return "success"; 
	}

	@Override
	public String cancleFinish(SmPlan smPlan) {  
		baseJdbcDao.update("update sm_plan set plan_status ='1',act_end_date= null   where plan_id = '"+smPlan.getPlanId()+"'"); 
		return "success";
	}

	@Override
	public String UpdateSmPlanTask(SmPlan smPlan) {
		String sql = "update sm_plan set plan_status ='"+smPlan.getPlanStatus()+"'  ,PLAN_SHORT_NAME='"+smPlan.getPlanShortName()+"',CODE_ID='"+smPlan.getCodeId()+"'," +
				"PLAN_NAME ='"+smPlan.getPlanName()+"' ,ACT_START_DATE="+(DataTypeUtil.validate(smPlan.getActStartDate()) ? "'"+  smPlan.getActStartDate()+"'" :null )+" ,ACT_END_DATE="+(DataTypeUtil.validate(smPlan.getActEndDate()) ? "'"+  smPlan.getActEndDate()+"'" :null )+" ,SEQ_NUM="+(DataTypeUtil.validate(smPlan.getSeqNum()) ?   smPlan.getSeqNum() : 0 ) +"," +
						"OBS_USER_ID='"+DataTypeUtil.formatDbColumn(smPlan.getObsUserId())+"',STATUS='"+DataTypeUtil.formatDbColumn(smPlan.getStatus())+"' where plan_id = '"+smPlan.getPlanId()+"'"; 
		baseJdbcDao.update(sql); 
		sql="update km_folder set title='"+smPlan.getPlanShortName()+smPlan.getPlanName()+"',DEF_CODE='"+smPlan.getPlanShortName()+"-',seq_num = "+smPlan.getSeqNum()+" where folder_id ='"+smPlan.getPlanId()+"'";
		baseJdbcDao.update(sql); 
		return "success";
	}

	@Override
	public String UpdateSmPlanRemark(SmPlan smPlan) {
		String sql = "update sm_plan set  remark='"+DataTypeUtil.formatDbColumn(smPlan.getRemark()).replaceAll("'", "\'").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")+"' where plan_id = '"+smPlan.getPlanId()+"'"; 
		baseJdbcDao.update(sql); 
		return "success";
	}

	@Override
	public List ShowPlanRefDataTable(String planId, String docId, String projId) {
		String sql = " SELECT * FROM (SELECT '' RES, A.WID,A.BASE_MASTER_KEY AS PLAN_ID,B.DOC_NUMBER,B.TITLE,B.DOC_TYPE, B.DOC_ID,date_format(A.DATE_LINKED,'%Y-%m-%d')  DATE_LINKED  "+
	 " FROM CM_DOCLINK A LEFT OUTER JOIN KM_DOC B ON A.DOC_ID = B.DOC_ID "+
	 " WHERE A.BASE_ITEM_TYPE='SM_PLAN_DATA' AND A.PROJ_ID='"+projId+"') Z  WHERE PLAN_ID='"+planId+"' OR PLAN_ID='"+docId+"'";
		return baseJdbcDao.queryListMap(sql);  
	}

	@Override
	public List PlanLindDocTable(String planId, String projId) {
		String sql = "  SELECT * FROM (SELECT '' RES, A.WID,A.BASE_MASTER_KEY AS PLAN_ID,B.DOC_NUMBER,B.TITLE, B.DOC_ID,date_format(A.DATE_LINKED,'%Y-%m-%d')  DATE_LINKED   "+
		 " FROM CM_DOCLINK A LEFT OUTER JOIN KM_DOC B ON A.DOC_ID = B.DOC_ID AND  B.DELETED_FLAG!='1'"+
		 " WHERE A.BASE_ITEM_TYPE='SM_PLAN') Z WHERE PLAN_ID='"+planId+"' or PLAN_ID='"+planId+"' ORDER BY Z.DOC_NUMBER DESC ";
			return baseJdbcDao.queryListMap(sql); 
	}

	@Override
	public String AddPlan(String tableName, SmPlan smPlan, UserInfo userInfo) {
		String plan_id  = Guid.getGuid();
		String proj_id= userInfo.getProjId();
		String User_id = userInfo.getUserId();
		String sql= "";
		String parentPath = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select PLAN_ID_PATH from "+tableName+" where plan_id = '"+smPlan.getParentPlanId()+"' and proj_id ='"+proj_id+"'"));
		parentPath  = parentPath+","+plan_id;
		if(smPlan.getNodeType().equals("SWBS")){
		 sql= "INSERT INTO "+tableName+"(WID,GRANTT_ID,GRANTT_PARENT_ID,PLAN_ID,PROJ_ID,PARENT_PLAN_ID,PLAN_SHORT_NAME,PLAN_NAME,SEQ_NUM,CREATE_USER_ID,PLAN_ID_PATH,TARGET_START_DATE,TARGET_END_DATE,NODE_TYPE,FOLDER_ID)" +
				 " VALUES('"+Guid.getGuid()+"','0','0','"+plan_id+"','"+proj_id+"','"+smPlan.getParentPlanId()+"','"+smPlan.getPlanShortName()+"','"+smPlan.getPlanName()+"',"+smPlan.getSeqNum()+",'"+User_id+"','"+parentPath+"',now(),now(),'SWBS','"+plan_id+"')";
		 baseJdbcDao.insert(sql); 
		 String title = smPlan.getPlanShortName()+" "+smPlan.getPlanName();
		  parentPath = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select FOLDER_ID_PATH from km_folder where folder_id = '"+smPlan.getParentPlanId()+"' and proj_id ='"+proj_id+"'"));
		  if(DataTypeUtil.validate(parentPath)){
		  parentPath  = parentPath+","+plan_id;
		   sql =  "INSERT INTO KM_FOLDER(WID,FOLDER_ID,PARENT_FOLDER_ID,TITLE,DEF_CODE,PROJ_ID,FOLDER_NAME,CREATED_BY,FOLDER_ID_PATH,DOC_GRADE,SEQ_NUM) " +
		 		" VALUES('"+Guid.getGuid()+"','"+plan_id+"','"+smPlan.getParentPlanId()+"','"+title+"',concat('"+smPlan.getPlanShortName()+"','-'),'"+proj_id+"','"+getTempFileName()+"','"+User_id+"','"+parentPath+"','2',"+smPlan.getSeqNum()+")";
		  }else{
			    String temp_folder="2B44E7C053F64B24877F6E79775F4AFD";
				String sql_km0="SELECT FOLDER_ID FROM KM_FOLDER  WHERE DOC_GRADE='2' AND DELETED_FLAG!='1' AND PROJ_ID='"+proj_id+"' AND PARENT_FOLDER_ID = '"+temp_folder+"'  ORDER BY SEQ_NUM ASC";
				String folder_id0=(String)baseJdbcDao.getFieldValue(sql_km0);
				String sql_km1=" SELECT FOLDER_ID FROM KM_FOLDER WHERE DOC_GRADE = '2' AND DELETED_FLAG != '1' AND PROJ_ID = '"+proj_id+"' AND PARENT_FOLDER_ID ='"+folder_id0+"' AND DEF_CODE='-ZLAQ-' ORDER BY SEQ_NUM ASC";
				String patentId=(String)baseJdbcDao.getFieldValue(sql_km1);
				sql = "SELECT FOLDER_ID_PATH FROM KM_FOLDER WHERE PROJ_ID='" + proj_id + "' AND FOLDER_ID = '" + patentId + "'";
				 parentPath = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));// 目标节点路径	
				 parentPath = parentPath+","+plan_id;
				sql =  "INSERT INTO KM_FOLDER(WID,FOLDER_ID,PARENT_FOLDER_ID,TITLE,DEF_CODE,PROJ_ID,FOLDER_NAME,CREATED_BY,FOLDER_ID_PATH,DOC_GRADE,SEQ_NUM) " +
		 		" VALUES('"+Guid.getGuid()+"','"+plan_id+"','"+patentId+"','"+title+"',concat('"+smPlan.getPlanShortName()+"','-'),'"+proj_id+"','"+getTempFileName()+"','"+User_id+"','"+parentPath+"','2',"+smPlan.getSeqNum()+")";
		  }
		 baseJdbcDao.insert(sql); 
		 if(baseJdbcDao.queryListMap("SELECT  '"+Guid.getGuid()+"','"+Guid.getGuid()+"',USER_ID,PROJ_ID,'0','1' FROM SM_PLANUSER WHERE PLAN_ID = '"+smPlan.getParentPlanId()+"'").size()>0){
 		  sql = "INSERT INTO SM_PLANUSER(WID ,PLANUSER_ID,USER_ID,PROJ_ID,PLAN_ID,ROLE_ID,USER_FLAG) (SELECT  UUID(),UUID(),USER_ID,PROJ_ID,'"+plan_id+"','0','1' FROM SM_PLANUSER WHERE PLAN_ID = '"+smPlan.getParentPlanId()+"')";
 		 baseJdbcDao.insert(sql);
		 }
		}else{
  
			 sql= "INSERT INTO "+tableName+"(WID,PLAN_STATUS,GRANTT_ID,GRANTT_PARENT_ID,PLAN_ID,PROJ_ID,PARENT_PLAN_ID,PLAN_SHORT_NAME,PLAN_NAME,SEQ_NUM,CREATE_USER_ID,PLAN_ID_PATH,TARGET_START_DATE,TARGET_END_DATE,NODE_TYPE,FOLDER_ID)" +
			 " VALUES('"+Guid.getGuid()+"','0','0','0','"+plan_id+"','"+proj_id+"','"+smPlan.getParentPlanId()+"','"+smPlan.getPlanShortName()+"','"+smPlan.getPlanName()+"',"+smPlan.getSeqNum()+",'"+User_id+"','"+parentPath+"',now(),now(),'TASK','"+plan_id+"')";
				 baseJdbcDao.insert(sql);
				 String title = smPlan.getPlanShortName()+" "+smPlan.getPlanName();
				  parentPath = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select FOLDER_ID_PATH from km_folder where folder_id = '"+smPlan.getParentPlanId()+"' and proj_id ='"+proj_id+"'"));
				  parentPath  = parentPath+","+plan_id;
				 sql =  "INSERT INTO KM_FOLDER(WID,FOLDER_ID,PARENT_FOLDER_ID,TITLE,DEF_CODE,PROJ_ID,FOLDER_NAME,CREATED_BY,FOLDER_ID_PATH,DOC_GRADE,SEQ_NUM) " +
				 		" VALUES('"+Guid.getGuid()+"','"+plan_id+"','"+smPlan.getParentPlanId()+"','"+title+"',concat('"+smPlan.getPlanShortName()+"','-'),'"+proj_id+"','"+getTempFileName()+"','"+User_id+"','"+parentPath+"','2',"+smPlan.getSeqNum()+")";
				 baseJdbcDao.insert(sql); 
		   
		}
		return "success";
	}
	  public static String getTempFileName()
	  {
	    int i = (int)Math.floor(Math.random() * 1000.0D);
	    String str = DateUtil.getTodaytime().replace("-", "").replace(" ", "").replace(":", "") + i;
	    return str;
	  }

	@Override
	public String delPlan(String tableName, String planId, String nodeType ,UserInfo userInfo) {
		String projId= userInfo.getProjId();
		String sql="select  count(wid) sum from km_doc k where k.base_master_key in (SELECT PLAN_ID FROM "+tableName+" WHERE   instr( PLAN_ID_PATH ,'"+planId+"') > 0  and proj_id ='"+userInfo.getProjId()+"')  and k.deleted_flag = '0'    and k.proj_id ='"+userInfo.getProjId()+"' ";
         if(baseJdbcDao.getCount(sql)>0){
        	 return "存在文档,请去【项目文档】处删除对应该节点下（包含所有子节点里）的实体文档！";
         }else{
					sql = "DELETE FROM CM_DOCLINK WHERE BASE_MASTER_KEY IN(SELECT doc_swbs_id FROM "+tableName+" WHERE PLAN_ID_PATH LIKE '%" + planId + "%' and PROJ_ID='"+projId+"') and PROJ_ID='"+projId+"'";
					baseJdbcDao.delete(sql);
				 sql = "DELETE FROM "+tableName+"   WHERE PLAN_ID_PATH LIKE '%" + planId + "%' AND PROJ_ID='"+projId+"'";  // 删除安全计划记录
				 baseJdbcDao.delete(sql);
                 sql  = "delete from SM_PLANUSER where  PROJ_ID = '"+projId+"' AND PLAN_ID in (SELECT PLAN_ID FROM "+tableName+" WHERE   instr( PLAN_ID_PATH ,'"+planId+"') > 0  and proj_id ='"+userInfo.getProjId()+"') " ;
                 baseJdbcDao.delete(sql);				 
				   sql ="select count(WID) from km_doc  WHERE    PROJ_ID='"+projId
					+"' and FOLDER_ID IN(SELECT FOLDER_ID FROM KM_FOLDER  WHERE    PROJ_ID='"+projId
					+"' and FOLDER_ID_PATH LIKE '%"+planId+"%')";
					if(baseJdbcDao.getCount(sql)<=0&& !tableName.toUpperCase().equals("DS_GCGK"))
					{
						if(nodeType.equals("PROJ")){ 
							String sign="";
					         if(tableName.toUpperCase().equals("DS_PLAN")){
					        	 sign="JSCX";
					         }else if(tableName.toUpperCase().equals("SM_PLAN")){
					        	 sign="ZLAQ";
					         }else if(tableName.toUpperCase().equals("SM_TEST")){
					        	 sign="HTZJ";
					         }else if(tableName.toUpperCase().equals("SYS_PLAN")){
					        	 sign="JDKZ"; 
					         }
							String querySql="SELECT FOLDER_ID FROM KM_FOLDER F WHERE F.FOLDER_NAME='"+sign+"'  AND PROJ_ID = '"+projId+"' ";
							String folder_id=(String)baseJdbcDao.getFieldValue(querySql);
							sql= "DELETE FROM KM_FOLDER WHERE instr(FOLDER_ID_PATH,'"+folder_id+"' ) > 0 AND FOLDER_ID!='"+folder_id+"' and  PROJ_ID='"+projId+"'";
							baseJdbcDao.delete(sql);
						}else{
						 
						sql= "DELETE FROM KM_FOLDER WHERE FOLDER_ID_PATH LIKE '%" + planId + "%'  and  PROJ_ID='"+projId+"'"; 
						baseJdbcDao.delete(sql);
						} 
					}
					return "success";
         }
		 
	}

	@Override
	public String MoveSmPlan(String parentId, String orginId, String projId) {
		if (DataTypeUtil.validate(parentId) && DataTypeUtil.validate(orginId))
		{
            String sql = "SELECT PARENT_PLAN_ID FROM SM_PLAN WHERE PROJ_ID='" + projId + "' AND PLAN_ID = '"+orginId+"'";
            String orgin_parentId  = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
            if(parentId.equals(orgin_parentId)){
            	return "success";
            }
		    sql = "SELECT PLAN_ID_PATH FROM SM_PLAN WHERE PROJ_ID='" + projId + "' AND PLAN_ID = '"+orginId+"'";// 源节点路径
			String source_listmd_id_path = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
			sql = "SELECT PLAN_ID_PATH FROM SM_PLAN WHERE PROJ_ID='" + projId + "' AND PLAN_ID = '" + parentId + "'";
			String aim_listmd_id_path = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));// 目标节点路径
			
		    sql = "SELECT FOLDER_ID_PATH FROM KM_FOLDER WHERE PROJ_ID='" + projId + "' AND FOLDER_ID = '"+orginId+"'";// 源节点路径
			String folder_source_listmd_id_path = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
			sql = "SELECT FOLDER_ID_PATH FROM KM_FOLDER WHERE PROJ_ID='" + projId + "' AND FOLDER_ID = '" + parentId + "'";
			String folder_aim_listmd_id_path = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));// 目标节点路径			
			String folder_parentId =parentId;
			if(!DataTypeUtil.validate(folder_aim_listmd_id_path)){
				String temp_folder="2B44E7C053F64B24877F6E79775F4AFD";
				String sql_km0="SELECT FOLDER_ID FROM KM_FOLDER  WHERE DOC_GRADE='2' AND DELETED_FLAG!='1' AND PROJ_ID='"+projId+"' AND PARENT_FOLDER_ID = '"+temp_folder+"'  ORDER BY SEQ_NUM ASC";
				String folder_id0=(String)baseJdbcDao.getFieldValue(sql_km0);
				String sql_km1=" SELECT FOLDER_ID FROM KM_FOLDER WHERE DOC_GRADE = '2' AND DELETED_FLAG != '1' AND PROJ_ID = '"+projId+"' AND PARENT_FOLDER_ID ='"+folder_id0+"' AND DEF_CODE='-ZLAQ-' ORDER BY SEQ_NUM ASC";
				folder_parentId=(String)baseJdbcDao.getFieldValue(sql_km1);
				sql = "SELECT FOLDER_ID_PATH FROM KM_FOLDER WHERE PROJ_ID='" + projId + "' AND FOLDER_ID = '" + folder_parentId + "'";
			    folder_aim_listmd_id_path = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));// 目标节点路径			
			}
			
			if (DataTypeUtil.validate(source_listmd_id_path) && DataTypeUtil.validate(aim_listmd_id_path))
			{
				if (aim_listmd_id_path.contains(orginId))
					return "移动的目标节点不能是源节点或源节点的子节点!";
				sql="SELECT COUNT(WID) NUM   FROM KM_DOC K WHERE   DELETED_FLAG='0' AND  BASE_MASTER_KEY IN (SELECT PLAN_ID FROM SM_PLAN WHERE  INSTR(PLAN_ID_PATH,'"+orginId+"') > 0 AND PROJ_ID='" + projId + "' ) AND  PROJ_ID='" + projId + "' ";
				if(baseJdbcDao.getCount(sql)>0){
					return "移动的节点下存在文档，禁止移动!"; 
				}
				// 修改本节点和本节点的所有子节点的路径
				sql = "UPDATE SM_PLAN SET PLAN_ID_PATH=REPLACE(PLAN_ID_PATH,'" + source_listmd_id_path + "','" + aim_listmd_id_path + ";" + orginId + "')" + //
						" WHERE INSTR(PLAN_ID_PATH,'" + source_listmd_id_path + "')>0 AND PROJ_ID='" + projId + "'";
				baseJdbcDao.update(sql); 			
				sql = "UPDATE SM_PLAN SET PARENT_PLAN_ID='" + parentId + "' WHERE PLAN_ID = '"+orginId+"' AND PROJ_ID='" + projId + "'";//
				baseJdbcDao.update(sql);
				
				sql = "UPDATE KM_FOLDER SET FOLDER_ID_PATH=REPLACE(FOLDER_ID_PATH,'" + folder_source_listmd_id_path + "','" + folder_aim_listmd_id_path + ";" + orginId + "')" + //
				" WHERE INSTR(FOLDER_ID_PATH,'" + folder_source_listmd_id_path + "')>0 AND PROJ_ID='" + projId + "'";
		         baseJdbcDao.update(sql);	
				sql = "UPDATE KM_FOLDER SET PARENT_FOLDER_ID='" + folder_parentId + "' WHERE FOLDER_ID = '"+orginId+"' AND PROJ_ID='" + projId + "'";//
				baseJdbcDao.update(sql);
				 
			} 		
	    }
		return "success";
	}
} 