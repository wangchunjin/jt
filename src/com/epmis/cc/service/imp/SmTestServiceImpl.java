package com.epmis.cc.service.imp;

import com.epmis.cc.service.SmTestService;
import com.epmis.cc.vo.SmTest;
import com.epmis.sm.vo.SmPlan;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.DateUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("smTestService")
public class SmTestServiceImpl implements SmTestService
{

  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;
  
  public List<Map<String, Object>> ShowSmTestTree(String parentId,String moduleCode,UserInfo userInfo)
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
	     String sql = "SELECT A.FOLDER_ID,A.PLAN_ID,A.PLAN_SHORT_NAME,A.PLAN_NAME,A.NODE_TYPE,A.CREATE_USER_ID,DOC_SWBS_ID,CASE A.PLAN_STATUS WHEN '0' THEN '未开始' WHEN '1' THEN '进行中' WHEN '2' THEN '已完成' END AS PLAN_STATUS ,(SELECT USER_NAME FROM CM_USERS WHERE USER_ID=A.CREATE_USER_ID)AS CREATE_USER, A.IS_MAJOR IS_MAJOR_TEMP,S.IS_MAJOR FROM SM_TEST A LEFT JOIN SM_SWBS S  ON A.SWBS_ID = S.SWBS_ID ";
				sql += " WHERE  PARENT_PLAN_ID = '" + ParentId + "'  AND PROJ_ID = '"+userInfo.getProjId()+"'";
				sql += " ORDER BY  A.SEQ_NUM";
			
		  if(ParentId.equals("0")){
				    String planSql = " select plan_id from sm_test where  plan_short_name =(select plan_short_name from SM_PLAN_MATCHUP where f_name = '"+moduleCode+"') AND PROJ_ID = '"+userInfo.getProjId()+"'";
			        String  id = baseJdbcDao.getFieldValue(planSql).toString();
			        if(DataTypeUtil.validate(id)){
			        	sql = "SELECT A.FOLDER_ID,A.PLAN_ID,A.PLAN_SHORT_NAME,A.PLAN_NAME,A.NODE_TYPE,A.CREATE_USER_ID,DOC_SWBS_ID,CASE A.PLAN_STATUS WHEN '0' THEN '未开始' WHEN '1' THEN '进行中' WHEN '2' THEN '已完成' END AS PLAN_STATUS ,(SELECT USER_NAME FROM CM_USERS WHERE USER_ID=A.CREATE_USER_ID)AS CREATE_USER, A.IS_MAJOR IS_MAJOR_TEMP,S.IS_MAJOR FROM SM_TEST A LEFT JOIN SM_SWBS S  ON A.SWBS_ID = S.SWBS_ID ";
						sql += " WHERE  PLAN_ID = '" + id + "'  AND PROJ_ID = '"+userInfo.getProjId()+"'";
						sql += " ORDER BY A.SEQ_NUM";
			        }
			  }
		  result =  baseJdbcDao.queryListMap(sql); 
	  }
	return result;
	}
 
	@Override
	public String AddPlan(String tableName, SmTest smTest, UserInfo userInfo) {
		String plan_id  = Guid.getGuid();
		String proj_id= userInfo.getProjId();
		String User_id = userInfo.getUserId();
		String sql= "";
		String parentPath = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select PLAN_ID_PATH from "+tableName+" where plan_id = '"+smTest.getParentPlanId()+"' and proj_id ='"+proj_id+"'"));
		parentPath  = parentPath+","+plan_id;
		if(smTest.getNodeType().equals("SWBS")){
		 sql= "INSERT INTO "+tableName+"(WID,GRANTT_ID,GRANTT_PARENT_ID,PLAN_ID,PROJ_ID,PARENT_PLAN_ID,PLAN_SHORT_NAME,PLAN_NAME,SEQ_NUM,CREATE_USER_ID,PLAN_ID_PATH,TARGET_START_DATE,TARGET_END_DATE,NODE_TYPE,FOLDER_ID)" +
				 " VALUES('"+Guid.getGuid()+"','0','0','"+plan_id+"','"+proj_id+"','"+smTest.getParentPlanId()+"','"+smTest.getPlanShortName()+"','"+smTest.getPlanName()+"',"+smTest.getSeqNum()+",'"+User_id+"','"+parentPath+"',now(),now(),'SWBS','"+plan_id+"')";
		 baseJdbcDao.insert(sql);
		 String title = smTest.getPlanShortName()+" "+smTest.getPlanName();
		  parentPath = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select FOLDER_ID_PATH from km_folder where folder_id = '"+smTest.getParentPlanId()+"' and proj_id ='"+proj_id+"'"));
		  if(DataTypeUtil.validate(parentPath)){
		  parentPath  = parentPath+","+plan_id;
		 sql =  "INSERT INTO KM_FOLDER(WID,FOLDER_ID,PARENT_FOLDER_ID,TITLE,DEF_CODE,PROJ_ID,FOLDER_NAME,CREATED_BY,FOLDER_ID_PATH,DOC_GRADE,SEQ_NUM) " +
		 		" VALUES('"+Guid.getGuid()+"','"+plan_id+"','"+smTest.getParentPlanId()+"','"+title+"',concat('"+smTest.getPlanShortName()+"','-'),'"+proj_id+"','"+getTempFileName()+"','"+User_id+"','"+parentPath+"','2',"+smTest.getSeqNum()+")";
		  }else{
			  String temp_folder="2B44E7C053F64B24877F6E79775F4AFD";
				String sql_km0="SELECT FOLDER_ID FROM KM_FOLDER  WHERE DOC_GRADE='2' AND DELETED_FLAG!='1' AND PROJ_ID='"+proj_id+"' AND PARENT_FOLDER_ID = '"+temp_folder+"'  ORDER BY SEQ_NUM ASC";
				String folder_id0=(String)baseJdbcDao.getFieldValue(sql_km0);
				String sql_km1=" SELECT FOLDER_ID FROM KM_FOLDER WHERE DOC_GRADE = '2' AND DELETED_FLAG != '1' AND PROJ_ID = '"+proj_id+"' AND PARENT_FOLDER_ID ='"+folder_id0+"' AND DEF_CODE='-HTZJ-' ORDER BY SEQ_NUM ASC";
				String patentId=(String)baseJdbcDao.getFieldValue(sql_km1);
				sql = "SELECT FOLDER_ID_PATH FROM KM_FOLDER WHERE PROJ_ID='" + proj_id + "' AND FOLDER_ID = '" + patentId + "'";
				 parentPath = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));// 目标节点路径	
				 parentPath = parentPath+","+plan_id;
				sql =  "INSERT INTO KM_FOLDER(WID,FOLDER_ID,PARENT_FOLDER_ID,TITLE,DEF_CODE,PROJ_ID,FOLDER_NAME,CREATED_BY,FOLDER_ID_PATH,DOC_GRADE,SEQ_NUM) " +
		 		" VALUES('"+Guid.getGuid()+"','"+plan_id+"','"+patentId+"','"+title+"',concat('"+smTest.getPlanShortName()+"','-'),'"+proj_id+"','"+getTempFileName()+"','"+User_id+"','"+parentPath+"','2',"+smTest.getSeqNum()+")";		  
		  }
		 baseJdbcDao.insert(sql); 
		 if(baseJdbcDao.queryListMap("SELECT  '"+Guid.getGuid()+"','"+Guid.getGuid()+"',USER_ID,PROJ_ID,'0','1' FROM SM_PLANUSER WHERE PLAN_ID = '"+smTest.getParentPlanId()+"'").size()>0){
 		  sql = "INSERT INTO SM_PLANUSER(WID ,PLANUSER_ID,USER_ID,PROJ_ID,PLAN_ID,ROLE_ID,USER_FLAG) (SELECT  UUID(),UUID(),USER_ID,PROJ_ID,'"+plan_id+"','0','1' FROM SM_PLANUSER WHERE PLAN_ID = '"+smTest.getParentPlanId()+"')";
 		 baseJdbcDao.insert(sql);
		 }
		}else{
  
			 sql= "INSERT INTO "+tableName+"(WID,PLAN_STATUS,GRANTT_ID,GRANTT_PARENT_ID,PLAN_ID,PROJ_ID,PARENT_PLAN_ID,PLAN_SHORT_NAME,PLAN_NAME,SEQ_NUM,CREATE_USER_ID,PLAN_ID_PATH,TARGET_START_DATE,TARGET_END_DATE,NODE_TYPE,FOLDER_ID)" +
			 " VALUES('"+Guid.getGuid()+"','0','0','0','"+plan_id+"','"+proj_id+"','"+smTest.getParentPlanId()+"','"+smTest.getPlanShortName()+"','"+smTest.getPlanName()+"',"+smTest.getSeqNum()+",'"+User_id+"','"+parentPath+"',now(),now(),'TASK','"+plan_id+"')";
				 baseJdbcDao.insert(sql);
				 String title = smTest.getPlanShortName()+" "+smTest.getPlanName();
				  parentPath = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select FOLDER_ID_PATH from km_folder where folder_id = '"+smTest.getParentPlanId()+"' and proj_id ='"+proj_id+"'"));
				  parentPath  = parentPath+","+plan_id;
				 sql =  "INSERT INTO KM_FOLDER(WID,FOLDER_ID,PARENT_FOLDER_ID,TITLE,DEF_CODE,PROJ_ID,FOLDER_NAME,CREATED_BY,FOLDER_ID_PATH,DOC_GRADE,SEQ_NUM) " +
				 		" VALUES('"+Guid.getGuid()+"','"+plan_id+"','"+smTest.getParentPlanId()+"','"+title+"',concat('"+smTest.getPlanShortName()+"','-'),'"+proj_id+"','"+getTempFileName()+"','"+User_id+"','"+parentPath+"','2',"+smTest.getSeqNum()+")";
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
	public Map<String, Object> GetSmTestInfo(String planId) {
		String sql ="select * from sm_test where plan_id = '"+planId+"'";
		return baseJdbcDao.queryMap(sql);
	}

	@Override
	public String UpdateSmTestWbs(SmTest smTest, String type) {
		  	String result = "";
		  	String sql=""; 
		  	if(type.equals("info")){
				sql="update km_folder set title='"+smTest.getPlanShortName()+smTest.getPlanName()+"',DEF_CODE='"+smTest.getPlanShortName()+"-',seq_num = "+smTest.getSeqNum()+"  where folder_id ='"+smTest.getPlanId()+"'";
				baseJdbcDao.update(sql); 
		        sql = "update sm_test set plan_short_name ='"+smTest.getPlanShortName()+"',Plan_name = '"+smTest.getPlanName()+"',seq_num = "+smTest.getSeqNum()+" where plan_id ='"+smTest.getPlanId()+"'";
		  	}else{
		        sql = "update sm_test set remark ='"+smTest.getRemark().replaceAll("'", "\'").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")+"'where  plan_id ='"+smTest.getPlanId()+"'";	
		  	}
		    if(baseJdbcDao.update(sql)){
		  		result = type;
		  	}
			return result;
	}
	@Override
	public String UpdateAssGuides(SmTest smTest) { 
		baseJdbcDao.update("update sm_test set Ass_Guides ='"+smTest.getAssGuides().replaceAll("'", "\'").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")+"'   where plan_id = '"+smTest.getPlanId()+"'");
		return "success"; 
	}
	@Override
	public String cancleFinish(SmTest smTest) {  
		baseJdbcDao.update("update sm_test set plan_status ='1',act_end_date= null   where plan_id = '"+smTest.getPlanId()+"'"); 
		return "success";
	}

	@Override
	public String UpdateSmTestTask(SmTest smTest) {
		String sql = "update sm_test set plan_status ='"+smTest.getPlanStatus()+"'  ,PLAN_SHORT_NAME='"+smTest.getPlanShortName()+"',CODE_ID='"+smTest.getCodeId()+"'," +
				"PLAN_NAME ='"+smTest.getPlanName()+"' ,ACT_START_DATE="+(DataTypeUtil.validate(smTest.getActStartDate()) ? "'"+  smTest.getActStartDate()+"'" :null )+" ,ACT_END_DATE="+(DataTypeUtil.validate(smTest.getActEndDate()) ? "'"+  smTest.getActEndDate()+"'" :null )+" ,SEQ_NUM="+(DataTypeUtil.validate(smTest.getSeqNum()) ?   smTest.getSeqNum() : 0 ) +"," +
						"OBS_USER_ID='"+DataTypeUtil.formatDbColumn(smTest.getObsUserId())+"',STATUS='"+DataTypeUtil.formatDbColumn(smTest.getStatus())+"' where plan_id = '"+smTest.getPlanId()+"'"; 
		baseJdbcDao.update(sql); 
		sql="update km_folder set title='"+smTest.getPlanShortName()+smTest.getPlanName()+"',DEF_CODE='"+smTest.getPlanShortName()+"-',seq_num = "+smTest.getSeqNum()+" where folder_id ='"+smTest.getPlanId()+"'";
		baseJdbcDao.update(sql); 
		return "success";
	}

	@Override
	public List ShowSmTestMoveTree(String parentId, String moduleCode,
			UserInfo userInfo) {
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
	     String sql = "SELECT A.FOLDER_ID,A.PLAN_ID,A.PLAN_SHORT_NAME,A.PLAN_NAME,A.NODE_TYPE,A.CREATE_USER_ID,DOC_SWBS_ID,CASE A.PLAN_STATUS WHEN '0' THEN '未开始' WHEN '1' THEN '进行中' WHEN '2' THEN '已完成' END AS PLAN_STATUS ,(SELECT USER_NAME FROM CM_USERS WHERE USER_ID=A.CREATE_USER_ID)AS CREATE_USER, A.IS_MAJOR IS_MAJOR_TEMP,S.IS_MAJOR FROM SM_TEST A LEFT JOIN SM_SWBS S  ON A.SWBS_ID = S.SWBS_ID ";
				sql += " WHERE  PARENT_PLAN_ID = '" + ParentId + "' AND A.NODE_TYPE !='TASK'  AND PROJ_ID = '"+userInfo.getProjId()+"'";
				sql += " ORDER BY  A.SEQ_NUM";
			
		  if(ParentId.equals("0")){
				    String planSql = " select plan_id from sm_test where  plan_short_name =(select plan_short_name from SM_PLAN_MATCHUP where f_name = '"+moduleCode+"') AND PROJ_ID = '"+userInfo.getProjId()+"'";
			        String  id = baseJdbcDao.getFieldValue(planSql).toString();
			        if(DataTypeUtil.validate(id)){
			        	sql = "SELECT A.FOLDER_ID,A.PLAN_ID,A.PLAN_SHORT_NAME,A.PLAN_NAME,A.NODE_TYPE,A.CREATE_USER_ID,DOC_SWBS_ID,CASE A.PLAN_STATUS WHEN '0' THEN '未开始' WHEN '1' THEN '进行中' WHEN '2' THEN '已完成' END AS PLAN_STATUS ,(SELECT USER_NAME FROM CM_USERS WHERE USER_ID=A.CREATE_USER_ID)AS CREATE_USER, A.IS_MAJOR IS_MAJOR_TEMP,S.IS_MAJOR FROM SM_TEST A LEFT JOIN SM_SWBS S  ON A.SWBS_ID = S.SWBS_ID ";
						sql += " WHERE  PLAN_ID = '" + id + "'  AND A.NODE_TYPE !='TASK' AND PROJ_ID = '"+userInfo.getProjId()+"'";
						sql += " ORDER BY A.SEQ_NUM";
			        }
			  }
		  result =  baseJdbcDao.queryListMap(sql); 
	  }
	return result;
	}

@Override
public String MoveSmTest(String parentId, String orginId, String projId) {
 	if (DataTypeUtil.validate(parentId) && DataTypeUtil.validate(orginId))
		{
            String sql = "SELECT PARENT_PLAN_ID FROM SM_TEST WHERE PROJ_ID='" + projId + "' AND PLAN_ID = '"+orginId+"'";
            String orgin_parentId  = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
            if(parentId.equals(orgin_parentId)){
            	return "success";
            }
		    sql = "SELECT PLAN_ID_PATH FROM SM_TEST WHERE PROJ_ID='" + projId + "' AND PLAN_ID = '"+orginId+"'";// 源节点路径
			String source_listmd_id_path = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
			sql = "SELECT PLAN_ID_PATH FROM SM_TEST WHERE PROJ_ID='" + projId + "' AND PLAN_ID = '" + parentId + "'";
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
				String sql_km1=" SELECT FOLDER_ID FROM KM_FOLDER WHERE DOC_GRADE = '2' AND DELETED_FLAG != '1' AND PROJ_ID = '"+projId+"' AND PARENT_FOLDER_ID ='"+folder_id0+"' AND DEF_CODE='-HTZJ-' ORDER BY SEQ_NUM ASC";
				folder_parentId=(String)baseJdbcDao.getFieldValue(sql_km1);
				sql = "SELECT FOLDER_ID_PATH FROM KM_FOLDER WHERE PROJ_ID='" + projId + "' AND FOLDER_ID = '" + folder_parentId + "'";
			    folder_aim_listmd_id_path = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));// 目标节点路径			
			}
			
			if (DataTypeUtil.validate(source_listmd_id_path) && DataTypeUtil.validate(aim_listmd_id_path))
			{
				if (aim_listmd_id_path.contains(orginId))
					return "移动的目标节点不能是源节点或源节点的子节点!";
				sql="SELECT COUNT(WID) NUM   FROM KM_DOC K WHERE   DELETED_FLAG='0' AND  BASE_MASTER_KEY IN (SELECT PLAN_ID FROM SM_TEST WHERE  INSTR(PLAN_ID_PATH,'"+orginId+"') > 0 AND PROJ_ID='" + projId + "' ) AND  PROJ_ID='" + projId + "' ";
				if(baseJdbcDao.getCount(sql)>0){
					return "移动的节点下存在文档，禁止移动!"; 
				}
				// 修改本节点和本节点的所有子节点的路径
				sql = "UPDATE SM_TEST SET PLAN_ID_PATH=REPLACE(PLAN_ID_PATH,'" + source_listmd_id_path + "','" + aim_listmd_id_path + ";" + orginId + "')" + //
						" WHERE INSTR(PLAN_ID_PATH,'" + source_listmd_id_path + "')>0 AND PROJ_ID='" + projId + "'";
				baseJdbcDao.update(sql); 			
				sql = "UPDATE SM_TEST SET PARENT_PLAN_ID='" + parentId + "' WHERE PLAN_ID = '"+orginId+"' AND PROJ_ID='" + projId + "'";//
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

	/**
	 * 获得用户设置的portal信息以及系统默认信息
	 */
	@Override
	public Map<String, Object> queryUserLayout(UserInfo userInfo ,String p_type) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		//用户设置的信息
		String sql ="SELECT * FROM PORTAL_LAYOUT WHERE USER_ID='"+userInfo.getUserId()+"' AND P_TYPE='"+p_type+"'";
		Map<String,Object> userMap =  baseJdbcDao.queryMap(sql);
		if(null != userMap && !"".equals(userMap)){
			map.put("P_TITLE", userMap.get("P_TITLE"));
			map.put("P_WIDTH", userMap.get("P_WIDTH"));
		}else{
			//默认
			map.put("P_TITLE", "三列");
			map.put("P_WIDTH", "40%,30%,30%");
		}
		
		//获取每个porlet的高度
		//sql ="SELECT * FROM PORTAL_LET ORDER BY SEQ_NUM ASC";
		sql = "SELECT AAA.*,IFNULL(COL1,(SELECT IFNULL(MAX(U2.`COL`),AAA.SEQ_NUM) FROM PORTAL_LET P2 LEFT JOIN "
				+ "PORTAL_USER_LET U2 ON U2.P_ID=P2.P_ID AND U2.U_ID='"+userInfo.getUserId()+"' AND U2.`ROW`=`ROW` WHERE  P2.P_TYPE='"+p_type+"' AND U2.P_TYPE='"+p_type+"')) COL "
				+ "FROM (SELECT P.*,IFNULL(U.`ROW`,(SELECT MAX(U1.`ROW`) FROM PORTAL_LET P1 LEFT JOIN "
				+ "PORTAL_USER_LET U1 ON U1.P_ID=P1.P_ID AND U1.U_ID='"+userInfo.getUserId()+"' WHERE  P1.P_TYPE='"+p_type+"' AND U1.P_TYPE='"+p_type+"' )) `ROW`,U.COL AS COL1 "
				+ "FROM PORTAL_LET P LEFT JOIN PORTAL_USER_LET U ON U.P_ID=P.P_ID AND "
				+ "U.U_ID='"+userInfo.getUserId()+"' WHERE  P.P_TYPE='"+p_type+"' AND "
				+ "(U.P_TYPE='"+p_type+"' OR  U.P_TYPE IS NULL )  AND U.IS_SHOW='0' AND "
				+ "P.P_ID IN (SELECT P_ID  FROM PORTAL_ROLE_USERS PRU WHERE  PRU.RU_ID='"+userInfo.getUserId()+"' "
				+ "AND PRU.PR_TYPE='USER' UNION ALL SELECT P_ID FROM PORTAL_ROLE_USERS PRU1 WHERE PRU1.PR_TYPE='ROLE' "
				+ "AND pru1.RU_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+userInfo.getUserId()+"'))) AAA  ORDER BY `ROW`,COL ASC";
		List<Map<String,Object>> portalLetList = this.baseJdbcDao.queryListMap(sql);
		map.put("portalLetList", portalLetList);
		map.put("portalLetCount", portalLetList.size());
		
		return map;
	}
	
	@Override
	public String updateUserPos(UserInfo userInfo,String rows,String cols,String ids,String p_type) { 
		String sql = "";
		
		//先删除用户之前拖放的位置信息
		sql = "DELETE FROM PORTAL_USER_LET WHERE U_ID = '"+userInfo.getUserId()+"' AND P_TYPE='"+p_type+"'";
		this.baseJdbcDao.delete(sql);
		
		if(DataTypeUtil.validate(rows) && DataTypeUtil.validate(cols) && DataTypeUtil.validate(ids)){
			String[] row = rows.split(",");
			String[] col = cols.split(",");
			String[] id = ids.split(",");
			for(int i=0;i<row.length;i++){
				sql = "INSERT INTO PORTAL_USER_LET (WID,P_ID,U_ID,ROW,COL,P_TYPE,IS_SHOW)VALUES('"+Guid.getGuid()+"','"+id[i]+"','"+userInfo.getUserId()+"',"
						+ "'"+row[i]+"','"+col[i]+"','"+p_type+"','0')";
				this.baseJdbcDao.insert(sql);
			}
		}
		
		return "success"; 
	}
	
	@Override
	public String updateUserWidths(UserInfo userInfo,String selType,String p_widths,String p_type) { 
		String sql = "";
		
		//先查询是否存在用户设置的信息
		sql ="SELECT COUNT(WID) FROM PORTAL_LAYOUT WHERE USER_ID='"+userInfo.getUserId()+"' AND P_TYPE='"+p_type+"'";
		if(this.baseJdbcDao.getCount(sql)<=0){
			sql = "INSERT INTO PORTAL_LAYOUT(WID,P_ID,P_TITLE,P_WIDTH,USER_ID,P_TYPE) VALUES ('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+selType+"','"+p_widths+"','"+userInfo.getUserId()+"','"+p_type+"')";
			this.baseJdbcDao.insert(sql);
		}else{
			sql = "UPDATE PORTAL_LAYOUT SET P_TITLE='"+selType+"',P_WIDTH='"+p_widths+"' WHERE USER_ID='"+userInfo.getUserId()+"' AND P_TYPE='"+p_type+"' ";
			this.baseJdbcDao.update(sql);
		}
		
		return "success"; 
		
	}
	
	@Override
	public List<Map<String,Object>> portaletTable(String userId){
		String sql = "SELECT P_ID,P_CODE,P_TITLE,(SELECT COUNT(*) FROM PORTAL_ROLE_USERS PRU WHERE "
				+ "PRU.P_ID=PL.P_ID AND PRU.RU_ID='"+userId+"' AND PR_TYPE='USER') USRE_FLAG,"
				+ "(SELECT COUNT(*) FROM PORTAL_ROLE_USERS PRU WHERE PRU.P_ID=PL.P_ID AND "
				+ "PR_TYPE='ROLE' AND PRU.RU_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+userId+"')) ROLE_FLAG "
				+ "FROM PORTAL_LET PL "
				+ "WHERE P_TYPE='PRO' ORDER BY SEQ_NUM ASC";
		List<Map<String,Object>> list = this.baseJdbcDao.queryListMap(sql);
		return list;
	}
	
	@Override
	public String updateUserPortalet(String userId, String pId,String checkFlag) {
		String sql = "";
		
		if(!DataTypeUtil.validate(userId) || !DataTypeUtil.validate(pId)){
			return "error";
		}
		
		//防止重复插入，先删除
		sql = "DELETE FROM PORTAL_ROLE_USERS WHERE P_ID='"+pId+"' AND RU_ID='"+userId+"' AND PR_TYPE='USER'";
		this.baseJdbcDao.delete(sql);
		
		if("true".equals(checkFlag)){
			sql = "INSERT INTO PORTAL_ROLE_USERS(WID,PR_ID,P_ID,RU_ID,PR_TYPE) VALUES "
					+ "('"+Guid.getGuid()+"','"+Guid.getGuid()+"',"
					+ "'"+pId+"','"+userId+"','USER')";
			this.baseJdbcDao.insert(sql);
		}
		return "success";
	}
	
	
	@Override
	public List<Map<String,Object>> portaletRoleTable(String roleId){
		String sql = "SELECT P_ID,P_CODE,P_TITLE,(SELECT COUNT(*) FROM PORTAL_ROLE_USERS PRU WHERE "
				+ "PRU.P_ID=PL.P_ID AND PRU.RU_ID='"+roleId+"' AND PR_TYPE='ROLE') ROLE_FLAG FROM PORTAL_LET PL "
				+ "WHERE P_TYPE='PRO' ORDER BY SEQ_NUM ASC";
		List<Map<String,Object>> list = this.baseJdbcDao.queryListMap(sql);
		return list;
	}
	
	@Override
	public String updateRolePortalet(String roleId, String pId,String checkFlag) {
		String sql = "";
		if(!DataTypeUtil.validate(roleId) || !DataTypeUtil.validate(pId)){
			return "error";
		}
		//防止重复插入，先删除
		sql = "DELETE FROM PORTAL_ROLE_USERS WHERE P_ID='"+pId+"' AND RU_ID='"+roleId+"' AND PR_TYPE='ROLE'";
		this.baseJdbcDao.delete(sql);
		
		if("true".equals(checkFlag)){
			sql = "INSERT INTO PORTAL_ROLE_USERS(WID,PR_ID,P_ID,RU_ID,PR_TYPE) VALUES "
					+ "('"+Guid.getGuid()+"','"+Guid.getGuid()+"',"
					+ "'"+pId+"','"+roleId+"','ROLE')";
			this.baseJdbcDao.insert(sql);
		}
		return "success";
	}
	
	@Override
	public String userClosePortalet(String userId,String pId) {
		String sql = "";
		//防止重复插入，先删除
		sql = "UPDATE  PORTAL_USER_LET SET IS_SHOW='1' WHERE P_ID='"+pId+"' AND U_ID='"+userId+"'";
		this.baseJdbcDao.update(sql);
		
		return "success";
	}
	
	/**
	 * 获得用户设置的portal信息以及系统默认信息
	 */
	@Override
	public List<Map<String,Object>> userPortalet(UserInfo userInfo ,String p_type) {
		//Map<String,Object> map = new HashMap<String,Object>();
		
		//用户设置的信息
		String sql ="";
		
		//获取每个porlet的高度
		//sql ="SELECT * FROM PORTAL_LET ORDER BY SEQ_NUM ASC";
		sql = "SELECT AAA.*,IFNULL(COL1,(SELECT IFNULL(MAX(U2.`COL`),AAA.SEQ_NUM) FROM PORTAL_LET P2 LEFT JOIN "
				+ "PORTAL_USER_LET U2 ON U2.P_ID=P2.P_ID AND U2.U_ID='"+userInfo.getUserId()+"' AND U2.`ROW`=`ROW` WHERE  P2.P_TYPE='"+p_type+"' AND U2.P_TYPE='"+p_type+"')) COL "
				+ "FROM (SELECT P.*,IFNULL(U.`ROW`,(SELECT MAX(U1.`ROW`) FROM PORTAL_LET P1 LEFT JOIN "
				+ "PORTAL_USER_LET U1 ON U1.P_ID=P1.P_ID "
				+ "AND U1.U_ID='"+userInfo.getUserId()+"' WHERE  P1.P_TYPE='"+p_type+"' AND U1.P_TYPE='"+p_type+"' )) `ROW`,"
				+ "U.COL AS COL1,U.IS_SHOW "
				+ "FROM PORTAL_LET P LEFT JOIN PORTAL_USER_LET U ON U.P_ID=P.P_ID AND "
				+ "U.U_ID='"+userInfo.getUserId()+"' WHERE  P.P_TYPE='"+p_type+"' AND "
				+ "(U.P_TYPE='"+p_type+"' OR  U.P_TYPE IS NULL ) AND "
				+ "P.P_ID IN (SELECT P_ID  FROM PORTAL_ROLE_USERS PRU WHERE  PRU.RU_ID='"+userInfo.getUserId()+"' "
				+ "AND PRU.PR_TYPE='USER' UNION ALL SELECT P_ID FROM PORTAL_ROLE_USERS PRU1 WHERE PRU1.PR_TYPE='ROLE' "
				+ "AND pru1.RU_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '"+userInfo.getUserId()+"'))) AAA  ORDER BY SEQ_NUM ASC";
		List<Map<String,Object>> portalLetList = this.baseJdbcDao.queryListMap(sql);
		
		return portalLetList;
	}
	
	
	@Override
	public String userSelPortalet(UserInfo userInfo,String pIds,String p_type) { 
		String sql = "";
		
		sql = "UPDATE PORTAL_USER_LET SET IS_SHOW='1' WHERE U_ID='"+userInfo.getUserId()+"' AND P_TYPE='"+p_type+"'";
		this.baseJdbcDao.update(sql);
		
		if(DataTypeUtil.validate(pIds)){
			String[] pIdsStr = pIds.split(",");
			for(String pId : pIdsStr){
				sql = "SELECT COUNT(*) FROM PORTAL_USER_LET WHERE U_ID='"+userInfo.getUserId()+"' AND P_ID='"+pId+"'";
				if(this.baseJdbcDao.getCount(sql)>0){
					sql = "UPDATE PORTAL_USER_LET SET IS_SHOW='0' WHERE U_ID='"+userInfo.getUserId()+"' AND P_ID='"+pId+"'";
					this.baseJdbcDao.update(sql);
				}else{
					sql = "INSERT INTO PORTAL_USER_LET (WID,P_ID,U_ID,ROW,COL,P_TYPE,IS_SHOW)VALUES('"+Guid.getGuid()+"','"+pId+"','"+userInfo.getUserId()+"',"
							+ "'0','0','"+p_type+"','0')";
					this.baseJdbcDao.insert(sql);
				}
				
			}
		}
		
		return "success"; 
		
	}
	
	@Override
	public List<Map<String,Object>> dataTable(){
		//查询所有表
		String sql = "SELECT TABLE_NAME,TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES "
				+ "WHERE TABLE_SCHEMA = 'AJT' and TABLE_NAME LIKE 'ajt_%' ORDER BY TABLE_NAME ASC";
		List<Map<String,Object>> list = this.baseJdbcDao.queryListMap(sql);
		if(null != list && list.size()>0){
			for(Map<String,Object> map:list){
				//查询表字段及注释
				sql = "SELECT COLUMN_NAME,COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS "
						+ "WHERE TABLE_SCHEMA ='AJT' AND TABLE_NAME = '"+map.get("TABLE_NAME")+"'";
				map.put("tableColumns", this.baseJdbcDao.queryListMap(sql));
			}
		}
		
		return list;
	}
} 