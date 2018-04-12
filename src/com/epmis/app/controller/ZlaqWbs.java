package com.epmis.app.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
 



import com.epmis.sm.service.SmPlanService;
import com.epmis.sm.vo.SmPlan;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.ProjService;
import com.epmis.sys.util.Base64Encrypt;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;

@Controller
public class ZlaqWbs {
	
	private static String  tableName = "SM_PLAN";
	
    @Autowired
    @Qualifier("baseJdbcDao")
    private BaseJdbcDao baseJdbcDao;

    
	@Autowired
    private ProjService projService;
	
	@Autowired
    private SmPlanService 	smPlanService;	
 
	@ResponseBody
	@RequestMapping(value = "/api/GetZlaqListByParentId.do")
	public void GetZlaqListByParentId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String projId =  Base64Encrypt.decode(jsonObj.getString("projId")); 
			String parentId =  Base64Encrypt.decode(jsonObj.getString("parentId"));
		     String sql = "SELECT A.PLAN_ID,A.PLAN_SHORT_NAME,A.PLAN_NAME,A.NODE_TYPE,A.CREATE_USER_ID,ifnull(DOC_SWBS_ID,'0')DOC_SWBS_ID ,CASE A.PLAN_STATUS WHEN '0' THEN '未开始' WHEN '1' THEN '进行中' WHEN '2' THEN '已完成' ELSE '' END AS PLAN_STATUS ,(SELECT USER_NAME FROM CM_USERS WHERE USER_ID=A.CREATE_USER_ID)AS CREATE_USER, ifnull(A.IS_MAJOR,'0')IS_MAJOR, ifnull(S.IS_MAJOR,'0') ISFIX ,A.REMARK,A.ASS_GUIDES,A.OBS_USER_ID OBS,ifnull(DATE_FORMAT(A.ACT_START_DATE, '%Y-%m-%d'),'') ACT_START_DATE,ifnull(DATE_FORMAT(A.ACT_END_DATE, '%Y-%m-%d'),'') ACT_END_DATE,A.SEQ_NUM FROM "+tableName+" A LEFT JOIN SM_SWBS S  ON A.SWBS_ID = S.SWBS_ID  WHERE  PARENT_PLAN_ID = '"+parentId+"'  AND PROJ_ID = '"+projId+"' ORDER BY A.SEQ_NUM"; 		      
            List<Map<String,Object>> list =baseJdbcDao.queryListMap(sql);
            if(DataTypeUtil.validate(list)){
        	 for(Map<String,Object>map:list){
        		 if(DataTypeUtil.formatDbColumn(map.get("ISFIX")).equals("1")){
        			 map.put("IS_MAJOR", "1");
        		 }
        	 }
            }
	    	  resObj.put("RESULT", "1");
	          resObj.put("LIST",list);
    
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/api/GetZlaqListByKey.do")
	public void GetZlaqListByKey(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String projId =  Base64Encrypt.decode(jsonObj.getString("projId")); 
			String parentId =  Base64Encrypt.decode(jsonObj.getString("parentId"));
			String key = Base64Encrypt.decode(jsonObj.getString("key"));
			String start =  Base64Encrypt.decode(jsonObj.getString("start"));
			String pageSize =  Base64Encrypt.decode(jsonObj.getString("pageSize"));
			int begin = Integer.parseInt(start);  			
			begin = begin-1;
             String sql=" SELECT  COUNT(PLAN_ID) NUM FROM "+tableName+" A LEFT JOIN SM_SWBS S  ON A.SWBS_ID = S.SWBS_ID  WHERE  LOCATE('"+parentId+"',PLAN_ID_PATH)>0  AND (A.PLAN_SHORT_NAME LIKE '%" + key + "%' or A.PLAN_NAME LIKE '%" + key + "%')  AND PROJ_ID = '"+projId+"' ORDER BY A.SEQ_NUM";

            int count = baseJdbcDao.getCount(sql);
			int totalPage = count/Integer.parseInt(pageSize);
			if(count%Integer.parseInt(pageSize)>0){
				totalPage=totalPage+1;
			}
			int currentPage = Integer.parseInt(start)/Integer.parseInt(pageSize)+1;
            
		   sql = "SELECT A.PLAN_ID,A.PLAN_SHORT_NAME,A.PLAN_NAME,A.NODE_TYPE,A.CREATE_USER_ID,ifnull(DOC_SWBS_ID,'0')DOC_SWBS_ID ,CASE A.PLAN_STATUS WHEN '0' THEN '未开始' WHEN '1' THEN '进行中' WHEN '2' THEN '已完成' ELSE '' END AS PLAN_STATUS ,(SELECT USER_NAME FROM CM_USERS WHERE USER_ID=A.CREATE_USER_ID)AS CREATE_USER, ifnull(A.IS_MAJOR,'0')IS_MAJOR, ifnull(S.IS_MAJOR,'0') ISFIX ,A.REMARK,A.ASS_GUIDES,A.OBS_USER_ID OBS,ifnull(DATE_FORMAT(A.ACT_START_DATE, '%Y-%m-%d'),'') ACT_START_DATE,ifnull(DATE_FORMAT(A.ACT_END_DATE, '%Y-%m-%d'),'') ACT_END_DATE,A.SEQ_NUM FROM "+tableName+" A LEFT JOIN SM_SWBS S  ON A.SWBS_ID = S.SWBS_ID  WHERE  LOCATE('"+parentId+"',PLAN_ID_PATH)>0  AND (A.PLAN_SHORT_NAME LIKE '%" + key + "%' or A.PLAN_NAME LIKE '%" + key + "%')  AND PROJ_ID = '"+projId+"' ORDER BY A.SEQ_NUM  LIMIT "+begin+","+pageSize;    
		              
		     List<Map<String,Object>> list  =  baseJdbcDao.queryListMap(sql);
	            if(DataTypeUtil.validate(list)){
	           	 for(Map<String,Object>map:list){
	           		 if(DataTypeUtil.formatDbColumn(map.get("ISFIX")).equals("1")){
	           			 map.put("IS_MAJOR", "1");
	           		 }
	           	 }
		          resObj.put("RESULT", "1");
		          resObj.put("LIST",list);
				  resObj.put("TOTAL_PAGE", totalPage);
				  resObj.put("CURRENT_PAGE", currentPage);
	          }else{
	        	  resObj.put("RESULT", "1"); 
	  			  resObj.put("TOTAL_PAGE", totalPage);
				  resObj.put("CURRENT_PAGE", currentPage);
	          }
    
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/AddZlaq.do")
	public void AddZlaq(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String projId =  Base64Encrypt.decode(jsonObj.getString("projId")); 
			String parentPlanId =  Base64Encrypt.decode(jsonObj.getString("parentPlanId")); 
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String planShortName =  Base64Encrypt.decode(jsonObj.getString("planShortName"));
			String planName =  Base64Encrypt.decode(jsonObj.getString("planName"));
			String seqNum =  Base64Encrypt.decode(jsonObj.getString("seqNum"));
			String nodeType = Base64Encrypt.decode(jsonObj.getString("nodeType"));
			if(!parentPlanId.equals("0")){
				SmPlan smPlan = new SmPlan();
				smPlan.setPlanShortName(planShortName);
				smPlan.setPlanName(planName);
				smPlan.setSeqNum(Integer.valueOf(seqNum));
				smPlan.setNodeType(nodeType);
				smPlan.setParentPlanId(parentPlanId);
				UserInfo userInfo = new UserInfo();
				userInfo.setUserId(userId);
				userInfo.setProjId(projId);
				String res = smPlanService.AddPlan(tableName,smPlan,userInfo);
		    	if(res.equals("success")){
				   resObj.put("RESULT", "1");
		    	}else{
		    	   resObj.put("RESULT", "0");
		    	   resObj.put("MSG", res);
		    	}
			}else{
				 resObj.put("RESULT", "0");
			}
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/MoveZlaq.do")
	public void MoveZlaq(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String orginId =  Base64Encrypt.decode(jsonObj.getString("planId")); 
			String parentId =  Base64Encrypt.decode(jsonObj.getString("targetId")); 
			String projId =  Base64Encrypt.decode(jsonObj.getString("projId"));
			String result =  smPlanService.MoveSmPlan(parentId,orginId,projId);
			if(result.equals("success")){
			    resObj.put("RESULT", "1");	
			}else{
				resObj.put("RESULT", "0");
				resObj.put("MSG", result);	
			}
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/api/CopyZlaq.do")
	public void CopyZlaq(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String orginId =  Base64Encrypt.decode(jsonObj.getString("planId")); 
			String parentId =  Base64Encrypt.decode(jsonObj.getString("targetId")); 
			String projId =  Base64Encrypt.decode(jsonObj.getString("projId"));
			projService.CopyPlan(parentId,orginId,tableName,projId);
			resObj.put("RESULT", "1");			 
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/DeleteZlaq.do")
	public void DeleteZlaq(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String planId =  Base64Encrypt.decode(jsonObj.getString("planId")); 
			String nodeType =  Base64Encrypt.decode(jsonObj.getString("nodeType")); 
			String projId =  Base64Encrypt.decode(jsonObj.getString("projId"));
			UserInfo userInfo = new UserInfo();
			userInfo.setProjId(projId);
			String result = smPlanService.delPlan(tableName, planId, nodeType, userInfo);
			if(result.equals("success")){
			    resObj.put("RESULT", "1");	
			}else{
				resObj.put("RESULT", "0");
				resObj.put("MSG", result);	
			}		 
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/UpdateZlaqInfo.do")
	public void UpdateZlaqInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String planId =  Base64Encrypt.decode(jsonObj.getString("planId")); 
			String planShortName =  Base64Encrypt.decode(jsonObj.getString("planShortName"));
			String planName =  Base64Encrypt.decode(jsonObj.getString("planName"));
			String seqNum =  Base64Encrypt.decode(jsonObj.getString("seqNum"));
			String actStartDate =  Base64Encrypt.decode(jsonObj.getString("actStartDate"));
			String actEndDate =  Base64Encrypt.decode(jsonObj.getString("actEndDate"));
			String status =  Base64Encrypt.decode(jsonObj.getString("status"));
			String obsUserId =  Base64Encrypt.decode(jsonObj.getString("obsUserId"));
			SmPlan smPlan = new SmPlan();
			smPlan.setPlanId(planId);
			smPlan.setPlanShortName(planShortName);
			smPlan.setPlanName(planName);
			
		    if(DataTypeUtil.validate(actStartDate)){		          
		    	smPlan.setActStartDate(Timestamp.valueOf(actStartDate+" 00:00:00"));
		    }else{
		    	smPlan.setActStartDate(null);
		    }
			
		    if(DataTypeUtil.validate(actEndDate)){
		    	smPlan.setActEndDate(Timestamp.valueOf(actEndDate+" 00:00:00"));
		    }else{
		    	smPlan.setActEndDate(null);
		    }
		    String sql = "SELECT CODE_ID FROM SM_PLAN WHERE PLAN_ID = '"+planId+"'";		    
		    smPlan.setCodeId(DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql)));
			smPlan.setObsUserId(obsUserId);
			smPlan.setPlanStatus(status);
			smPlan.setSeqNum(Integer.valueOf(seqNum));			
			smPlanService.UpdateSmPlanTask(smPlan);
	    	  resObj.put("RESULT", "1");	    
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	@ResponseBody
	@RequestMapping(value = "/api/GetZlaqInfo.do")
	public void GetZlaqInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String planId =  Base64Encrypt.decode(jsonObj.getString("planId")); 
			String projId =  Base64Encrypt.decode(jsonObj.getString("projId"));
			String sql ="SELECT A.PLAN_ID,A.PLAN_SHORT_NAME,A.PLAN_NAME,PLAN_STATUS,A.REMARK,A.ASS_GUIDES,A.OBS_USER_ID OBS,IFNULL(DATE_FORMAT(A.ACT_START_DATE, '%Y-%m-%d'),'') ACT_START_DATE,IFNULL(DATE_FORMAT(A.ACT_END_DATE, '%Y-%m-%d'),'') ACT_END_DATE,A.SEQ_NUM  FROM "+tableName+" A where A.proj_id = '"+projId+"' and plan_id='"+planId+"'";
			resObj =  baseJdbcDao.queryMap(sql);
			resObj.put("RESULT", "1");	
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	
}
