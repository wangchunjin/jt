package com.epmis.app.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.CatvalService;
import com.epmis.sys.service.ProjService;
import com.epmis.sys.util.Base64Encrypt;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.DateUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.sys.vo.CmProj;
import com.epmis.sys.vo.CmProjSurvey;
@Controller
public class Project {
	  @Autowired
	  @Qualifier("baseJdbcDao")
	  private BaseJdbcDao baseJdbcDao;
		@Autowired
	    private ProjService projService;
		
	@ResponseBody
	@RequestMapping(value = "/api/getProjListByParentId.do")
	public void ProjListByParentId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String parentid =  Base64Encrypt.decode(jsonObj.getString("parentId"));
			String start =  Base64Encrypt.decode(jsonObj.getString("start"));
			String pageSize =  Base64Encrypt.decode(jsonObj.getString("pageSize"));
			String type =  Base64Encrypt.decode(jsonObj.getString("type"));
			int begin = Integer.parseInt(start);  			
			begin = begin-1;
			if(type.equals("NULL")){
				type = " (CONTRACT_TITLE='' OR CONTRACT_TITLE IS NULL) ";
			}else if(type.equals("NO")){
				type=" CONTRACT_TITLE='未完工' ";
			}else if(type.equals("DO")){
				type=" CONTRACT_TITLE='已完工' ";
			}else{
				type =" (CONTRACT_TITLE='未完工' OR CONTRACT_TITLE='已完工' OR CONTRACT_TITLE='' OR CONTRACT_TITLE IS NULL ) ";
			}
			String sql = "SELECT COUNT(PROJ_ID) "+ //
		    "FROM CM_PROJ A " + // 
		    "WHERE (PROJ_NODE_FLAG = 'N' OR ("+type+" and PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "'))   AND STATUS = 'JH' ))" + //
		    "AND A.PARENT_PROJ_ID = '" + parentid + "'"
    		+ " AND (SELECT COUNT(PROJ_ID) FROM CM_PROJ WHERE "+type+" AND PROJ_NODE_FLAG = 'Y' AND LOCATE(A.PROJ_ID,PARENT_PATH)>0 AND PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "')) ) >0"
    		+ " ORDER BY A.PROJ_NODE_FLAG DESC, A.SEQ_NUM";
			int count = baseJdbcDao.getCount(sql);
			int totalPage = count/Integer.parseInt(pageSize);
			if(count%Integer.parseInt(pageSize)>0){
				totalPage=totalPage+1;
			}
			int currentPage = Integer.parseInt(start)/Integer.parseInt(pageSize)+1;

			
			sql = "SELECT PROJ_ID,PARENT_PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,PROJ_CMPT_PCT,date_format(PROCEED_DATE,'%Y-%m-%d')PROCEED_DATE,date_format(COMPLETION_DATE,'%Y-%m-%d')COMPLETION_DATE,PROJ_NODE_FLAG,SEQ_NUM," + //
		    "ifnull((SELECT B.CODE_NAME FROM CM_CODE B WHERE B.CODE_TYPE = 'PROJ_STAGE' AND B.CODE_SHORT_NAME = A.PROJ_STAGE),'') AS PROJ_STAGE,ifnull(MARK,'') MARK,ifnull(RADIUS,'0') RADIUS " + //
		    "FROM CM_PROJ A " + // 
		    "WHERE (PROJ_NODE_FLAG = 'N' OR ("+type+" and PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "'))   AND STATUS = 'JH' ))" + //
		    "AND A.PARENT_PROJ_ID = '" + parentid + "'"
    		+ " AND (SELECT COUNT(PROJ_ID) FROM CM_PROJ WHERE "+type+" AND  PROJ_NODE_FLAG = 'Y' AND LOCATE(A.PROJ_ID,PARENT_PATH)>0 AND PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "')) ) >0"
		    + " ORDER BY A.PROJ_NODE_FLAG DESC, A.SEQ_NUM LIMIT "+begin+","+pageSize;		
			List<Map<String,Object>> list  =  baseJdbcDao.queryListMap(sql);
	      if(DataTypeUtil.validate(list)){
	          resObj.put("RESULT", "1");
	          resObj.put("LIST",list);
			  resObj.put("TOTAL_PAGE", totalPage);
			  resObj.put("CURRENT_PAGE", currentPage);
          }else{
        	  resObj.put("RESULT", "1");
  		      resObj.put("TOTAL_PAGE", totalPage);
			  resObj.put("CURRENT_PAGE", currentPage);
          }
      	//Base64.generateDecoder();
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/getProjListByKey.do")
	public void getProjListByKey(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId = Base64Encrypt.decode(jsonObj.getString("userId")); 
			String key = Base64Encrypt.decode(jsonObj.getString("key"));
			String type = Base64Encrypt.decode(jsonObj.getString("type"));
			String parentId = Base64Encrypt.decode(jsonObj.getString("parentId"));
			String start =  Base64Encrypt.decode(jsonObj.getString("start"));
			String pageSize =  Base64Encrypt.decode(jsonObj.getString("pageSize"));
			int begin = Integer.parseInt(start);  			
			begin = begin-1;
			if(type.equals("NULL")){
				type = " (CONTRACT_TITLE='' OR CONTRACT_TITLE IS NULL) ";
			}else if(type.equals("NO")){
				type=" CONTRACT_TITLE='未完工' ";
			}else if(type.equals("DO")){
				type=" CONTRACT_TITLE='已完工' ";
			}else{
				type =" (CONTRACT_TITLE='未完工' OR CONTRACT_TITLE='已完工' OR CONTRACT_TITLE='' OR CONTRACT_TITLE IS NULL ) ";
			}
			
			String sql = "SELECT COUNT(PROJ_ID) " + //
		    "FROM CM_PROJ A " + // 
		    "WHERE (PROJ_NODE_FLAG = 'N' OR ("+type+" and PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "'))   AND STATUS = 'JH' ))" + //
		    "AND (A.PROJ_SHORT_NAME LIKE '%" + key + "%' or A.PROJ_NAME LIKE '%" + key + "%') AND PROJ_ID IN (SELECT  PROJ_ID FROM CM_PROJ WHERE  LOCATE('"+parentId+"',PARENT_PATH)>0 )"
    		+ " AND (SELECT COUNT(PROJ_ID) FROM CM_PROJ WHERE "+type+" AND  PROJ_NODE_FLAG = 'Y' AND LOCATE(A.PROJ_ID,PARENT_PATH)>0 AND PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "')) ) >0"
		    + "  ORDER BY A.PROJ_NODE_FLAG DESC, A.SEQ_NUM";
			int count = baseJdbcDao.getCount(sql);
			int totalPage = count/Integer.parseInt(pageSize);
			if(count%Integer.parseInt(pageSize)>0){
				totalPage=totalPage+1;
			}
			int currentPage = Integer.parseInt(start)/Integer.parseInt(pageSize)+1;

			
		    sql = "SELECT PROJ_ID,PARENT_PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,PROJ_CMPT_PCT,date_format(PROCEED_DATE,'%Y-%m-%d')PROCEED_DATE,date_format(COMPLETION_DATE,'%Y-%m-%d')COMPLETION_DATE,PROJ_NODE_FLAG,SEQ_NUM," + //
		    "ifnull((SELECT B.CODE_NAME FROM CM_CODE B WHERE B.CODE_TYPE = 'PROJ_STAGE' AND B.CODE_SHORT_NAME = A.PROJ_STAGE),'') AS PROJ_STAGE,ifnull(MARK,'')MARK,ifnull(RADIUS,'0') RADIUS " + //
		    "FROM CM_PROJ A " + // 
		    "WHERE (PROJ_NODE_FLAG = 'N' OR ("+type+" and PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "'))   AND STATUS = 'JH' ))" + //
		    "AND (A.PROJ_SHORT_NAME LIKE '%" + key + "%' or A.PROJ_NAME LIKE '%" + key + "%') AND PROJ_ID IN (SELECT  PROJ_ID FROM CM_PROJ WHERE  LOCATE('"+parentId+"',PARENT_PATH)>0 )"
    		+ " AND (SELECT COUNT(PROJ_ID) FROM CM_PROJ WHERE "+type+" AND  PROJ_NODE_FLAG = 'Y' AND LOCATE(A.PROJ_ID,PARENT_PATH)>0 AND PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "')) ) >0"		    		
		    + "  ORDER BY A.PROJ_NODE_FLAG DESC, A.SEQ_NUM  LIMIT "+begin+","+pageSize;	
			List<Map<String,Object>> list  =  baseJdbcDao.queryListMap(sql);
         if(DataTypeUtil.validate(list)){
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
	@RequestMapping(value = "/api/OpenProj.do")
	public void OpenProj(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
	 		String projId = Base64Encrypt.decode(jsonObj.getString("projId")); 
	 		String userId = Base64Encrypt.decode(jsonObj.getString("userId")); 
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(userId);
	        projService.ChangeProj(projId,userInfo); 
	        
	  String sql  ="SELECT DATACTR_CODE FROM CM_PROFCTR A JOIN CM_PROFILE B ON A.PROFILE_ID = B.PROFILE_ID  WHERE ((A.PROFILE_ID = (SELECT PROFILE_ID FROM CM_USERS WHERE USER_ID='" + userId + "') AND B.PROF_TYPE='0')" + "OR (A.PROFILE_ID = " + "(SELECT PROFILE_ID FROM CM_USERPROF WHERE USER_ID = '" + userId + "' AND PROJ_ID = '" + projId + "') AND B.PROF_TYPE='1')) AND A.DATACTR_CODE IN ('SYS_PROJ.QYXM.edit','DS_GCGK.add_del','DS_GCGK.edit','DS_ADD.edit','DS_EDIT.edit','SM_ADD.edit','SM_EDIT.edit','CC_ADD.edit','CC_EDIT.edit','QC_ADD.edit','QC_EDIT.edit','CC_UPLOAD.edit','DS_UPLOAD.edit','QC_UPLOAD.edit','SM_UPLOAD.edit','DS_FX_PICTABLE.edit')";  
	  List<Map<String, Object>> Datactrlist =  baseJdbcDao.queryListMap(sql);
	        resObj.put("DATACTRLIST", Datactrlist);
	        resObj.put("RESULT", "1");
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
	@ResponseBody
	@RequestMapping(value = "/api/GetHistoryProjList.do")
	public void GetHistoryProjList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString = request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId = Base64Encrypt.decode(jsonObj.getString("userId"));   
			 String sql="SELECT A.PROJ_ID,PARENT_PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,PROJ_CMPT_PCT,date_format(PROCEED_DATE,'%Y-%m-%d')PROCEED_DATE,date_format(COMPLETION_DATE,'%Y-%m-%d')COMPLETION_DATE,PROJ_NODE_FLAG,SEQ_NUM," + //
					    "(SELECT B.CODE_NAME FROM CM_CODE B WHERE B.CODE_TYPE = 'PROJ_STAGE' AND B.CODE_SHORT_NAME = A.PROJ_STAGE) AS PROJ_STAGE " + //
					    "FROM CM_PROJ A " + // 
						"INNER JOIN CM_PROJRCNT B ON A.PROJ_ID=B.PROJ_ID WHERE B.USER_ID='" + userId + "' " + //
						"AND A.PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID='" + userId + "' " + //
						"OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID='" + userId + "')) ORDER BY B.ACCESS_DATE DESC limit 0,10";
			 List<Map<String,Object>> list  =  baseJdbcDao.queryListMap(sql);
			 if(DataTypeUtil.validate(list)){
		          resObj.put("RESULT", "1");
		          resObj.put("LIST",list);
	          }else{
	        	  resObj.put("RESULT", "1");
	          }
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
	@ResponseBody
	@RequestMapping(value = "/api/GetProjInfo.do")
	public void GetProjInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString = request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String projId = Base64Encrypt.decode(jsonObj.getString("projId"));   
			 String sql=" SELECT PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,SEQ_NUM,PROJ_CMPT_PCT,date_format(PROCEED_DATE,'%Y-%m-%d')PROCEED_DATE,date_format(COMPLETION_DATE,'%Y-%m-%d')COMPLETION_DATE,SITE_LOCATION,PROJ_STAGE,PROJ_STAGE1,CONTRACT_TITLE,VNMT_ID,SUPERVISOR,SUPERVISOR_PHONE,PROJ_PLACE,ifnull(MARK,'') MARK ,ifnull(RADIUS,'')RADIUS  FROM CM_PROJ A WHERE A.PROJ_ID = '"+projId+"'";
			  resObj  =  baseJdbcDao.queryMap(sql);
			 if(DataTypeUtil.validate(resObj)){
		          resObj.put("RESULT", "1");
	          }else{
	        	  resObj.put("RESULT", "0");
	          }
			 sql = "SELECT CODE_NAME ID,CODE_NAME,SEQ_NUM FROM CM_CODE WHERE CODE_TYPE='XX_JD' ORDER BY SEQ_NUM ";
			 List<Map<String,Object>> jdlist  =  baseJdbcDao.queryListMap(sql);
			 resObj.put("XXJD", jdlist);
			 
			 sql = " SELECT VNMT_ID ID ,COMPANY_NAME CODE_NAME FROM CM_VNMT WHERE VNMT_ID IN (SELECT VNMT_ID FROM CM_PROJ_JOIN WHERE PROJ_ID = '"+projId+"')  ORDER BY COMPANY_NAME ";
			 List<Map<String,Object>> vnmtlist  =  baseJdbcDao.queryListMap(sql);
			 resObj.put("JSDW", vnmtlist);
			 
			 sql = "SELECT '未完工' ID, '未完工' CODE_NAME  UNION SELECT '已完工'  ID, '已完工' CODE_NAME  ";
			 List<Map<String,Object>> statuslist  =  baseJdbcDao.queryListMap(sql);
			 resObj.put("XMZT", statuslist);
			 
			 sql = " SELECT ID,CODE_SHORT_NAME ,CODE_NAME ,CODE_TYPE,CODE_BELONGTO FROM CM_CODE WHERE CODE_TYPE ='PROJ_STAGE1' UNION  SELECT  ID, CODE_SHORT_NAME  ,CODE_NAME ,CODE_TYPE,CODE_BELONGTO  FROM CM_CODE WHERE CODE_TYPE ='PROJ_STAGE'  ";
			 List<Map<String,Object>> lblist  =  baseJdbcDao.queryListMap(sql);
			 resObj.put("XMLB", lblist);			 
			 
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
	@ResponseBody
	@RequestMapping(value = "/api/UpdateProjInfo.do")
	public void UpdateProjInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");                
		              
		try {
			String jsonString = request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String projId = Base64Encrypt.decode(jsonObj.getString("projId"));
			String projShortName = Base64Encrypt.decode(jsonObj.getString("projShortName"));
			String projName = Base64Encrypt.decode(jsonObj.getString("projName"));
			String projCmptPct = Base64Encrypt.decode(jsonObj.getString("projCmptPct"));
			String proceedDate = Base64Encrypt.decode(jsonObj.getString("proceedDate"));
			String completionDate = Base64Encrypt.decode(jsonObj.getString("completionDate"));
			String siteLocation = Base64Encrypt.decode(jsonObj.getString("siteLocation"));
			String projStage = Base64Encrypt.decode(jsonObj.getString("projStage"));
			String projStage1 = Base64Encrypt.decode(jsonObj.getString("projStage1"));
			String contractTitle = Base64Encrypt.decode(jsonObj.getString("contractTitle"));
			String vnmtId = Base64Encrypt.decode(jsonObj.getString("vnmtId"));
			String supervisor = Base64Encrypt.decode(jsonObj.getString("supervisor"));
			String supervisorPhone = Base64Encrypt.decode(jsonObj.getString("supervisorPhone"));
			String projPlace = Base64Encrypt.decode(jsonObj.getString("projPlace"));
			String mark = Base64Encrypt.decode(jsonObj.getString("mark"));
			String radius = Base64Encrypt.decode(jsonObj.getString("radius"));
			
			CmProj cmProj = new CmProj();
			cmProj.setProjId(projId);
		    cmProj.setProjShortName(projShortName);
		    cmProj.setProjName(projName); 
		    cmProj.setProjCmptPct(projCmptPct); 
		    if(DataTypeUtil.validate(proceedDate)){
		    	cmProj.setProceedDate(new java.sql.Date(sdf.parse(proceedDate).getTime()));
		    }else{
		    	cmProj.setProceedDate(null);
		    }
		    if(DataTypeUtil.validate(completionDate)){
		    	cmProj.setCompletionDate(new java.sql.Date(sdf.parse(completionDate).getTime()));
		    }else{
		    	cmProj.setCompletionDate(null);
		    }
		    cmProj.setSiteLocation(siteLocation);
		    cmProj.setProjStage(projStage);
		    cmProj.setProjStage1(projStage1);
		    cmProj.setContractTitle(contractTitle);
		    cmProj.setVnmtId(vnmtId);
		    cmProj.setSupervisor(supervisor);
		    cmProj.setSupervisorPhone(supervisorPhone);
		    cmProj.setProjPlace(projPlace);
		    cmProj.setMark(mark);
		    cmProj.setRadius(radius);
			String res = projService.UpdateProjInfo(cmProj);
			if(res.endsWith("success")){
				resObj.put("RESULT", "1");
			}else{
				resObj.put("RESULT", "0");
				resObj.put("MSG", res);
			}
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	@ResponseBody
	@RequestMapping(value = "/api/GetProjSurvey.do")
	public void GetProjSurvey(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		List<Map<String, Object>> ColumnList= new ArrayList<Map<String, Object>>();
		Map<String, Object> ObjMap = new HashMap<String, Object>();
		try {
		    String jsonString = request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String projId = Base64Encrypt.decode(jsonObj.getString("projId"));   
			List<Map<String, Object>> ColumnInfo= projService.GetProjSurvey(projId);
			if(DataTypeUtil.validate(ColumnInfo)){
				for(Map<String, Object> obj : ColumnInfo){	
					 ObjMap = new HashMap<String, Object>();
					ObjMap.put("COLUMN_ID",DataTypeUtil.formatDbColumn(obj.get("column_code")));
					ObjMap.put("COLUMN_NAME",DataTypeUtil.formatDbColumn(obj.get("column_name")));
					ObjMap.put("COLUMN_VALUE",DataTypeUtil.formatDbColumn(obj.get("COLUMN_VALUE")));
					ColumnList.add(ObjMap);
				}
			}
			 CmProjSurvey cmProjSurvey = new CmProjSurvey();
			 cmProjSurvey.setProjId(projId);
		     cmProjSurvey = projService.GetProjSurveyInfo(cmProjSurvey);
		        ObjMap = new HashMap<String, Object>();
		        ObjMap.put("COLUMN_NAME","工程结构");
				ObjMap.put("COLUMN_VALUE",DataTypeUtil.formatDbColumn(cmProjSurvey.getProjForm()));
				ColumnList.add(ObjMap);
				
				ObjMap = new HashMap<String, Object>();
				ObjMap.put("COLUMN_NAME","工程质量目标");
				ObjMap.put("COLUMN_VALUE",DataTypeUtil.formatDbColumn(cmProjSurvey.getProjQualityDest()));
				ColumnList.add(ObjMap);
				
				ObjMap = new HashMap<String, Object>();
				ObjMap.put("COLUMN_NAME","总投资（万元）");
				ObjMap.put("COLUMN_VALUE",DataTypeUtil.formatDbColumn(cmProjSurvey.getGrossInvest()));
				ColumnList.add(ObjMap);
				
				ObjMap = new HashMap<String, Object>();
				ObjMap.put("COLUMN_NAME","规模");
				ObjMap.put("COLUMN_VALUE",DataTypeUtil.formatDbColumn(cmProjSurvey.getScale()));
				ColumnList.add(ObjMap);
				
				ObjMap = new HashMap<String, Object>();
				ObjMap.put("COLUMN_NAME","监理费用（万元）");
				ObjMap.put("COLUMN_VALUE",DataTypeUtil.formatDbColumn(cmProjSurvey.getPartProjInvest()));
				ColumnList.add(ObjMap);
				
				ObjMap = new HashMap<String, Object>();
				ObjMap.put("COLUMN_NAME","安全文明工地目标");
				ObjMap.put("COLUMN_VALUE",DataTypeUtil.formatDbColumn(cmProjSurvey.getCultureSite()));
				ColumnList.add(ObjMap);
		     
		     resObj.put("SURVEY_LIST", ColumnList);
		     resObj.put("RESULT", "1");
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/GetProjVnmtList.do")
	public void GetProjVnmtList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
			String jsonString = request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String projId = Base64Encrypt.decode(jsonObj.getString("projId"));  
			 List<Map<String,Object>> list =  projService.ProjCompanyTable(projId);
			 if(DataTypeUtil.validate(list)){
					for(Map<String, Object> obj : list){	
						obj.remove("WID");
						obj.remove("PROJ_ID");
					}
			 }
		     resObj.put("VNMT_LIST", list);
		     resObj.put("RESULT", "1");
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/GetProjPeopleList.do")
	public void GetProjUserList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
			String jsonString = request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String projId = Base64Encrypt.decode(jsonObj.getString("projId"));  
			 List<Map<String,Object>> list =  projService.ProjPeopleTable(projId);
		     resObj.put("PEOPLE_LIST", list);
		     resObj.put("RESULT", "1");
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/GetProjUserList.do")
	public void GetProjPeopleList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
			String jsonString = request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String projId = Base64Encrypt.decode(jsonObj.getString("projId"));  
			 List<Map<String,Object>> list =  projService.ProjUserTable(projId);
			 if(DataTypeUtil.validate(list)){
					for(Map<String, Object> obj : list){	
						obj.remove("RES");
						obj.remove("BUG_FLAG");
						obj.remove("CON_FLAG");
						obj.remove("PO_FLAG");
						obj.remove("PROFILE_ID");
					}
			 }
		     resObj.put("USER_LIST", list);
		     resObj.put("RESULT", "1");
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
	
	@Autowired
    private CatvalService catvalService;	
	
	@ResponseBody
	@RequestMapping(value = "/api/GetCatvalList.do")
	public void GetCatvalList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
			String jsonString = request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String projId = Base64Encrypt.decode(jsonObj.getString("projId"));  
			 List<Map<String,Object>> list =  catvalService.CatvalTableOrderByKey(projId);
			 if(DataTypeUtil.validate(list)){
					for(Map<String, Object> obj : list){	
						obj.remove("WID");
						obj.remove("CATG_TYPE_ID");
					}
			 }
		     resObj.put("CATVAL_LIST", list);
		     resObj.put("RESULT", "1");
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
	
	private static double EARTH_RADIUS = 6378.137;
	private static double rad(double d)
	{
	   return d * Math.PI / 180.0;
	}

	/**
	 * 计算两个经纬度之间的距离
	 * @param args
	 */
	public static void main(String[] args) {
		double y1 = 29.490295;
		double x1 = 106.486654;
		double y2 = 29.615467;
		double x2 = 106.581515;
		
		double jieguo = GetDistance(y1, x1, y2, x2);
		 System.out.print(jieguo);
 
   
	}
	

	public static double GetDistance(double y1, double x1, double y2, double x2)
	{
	   double radLat1 = rad(y1);
	   double radLat2 = rad(y2);
	   double a = radLat1 - radLat2;
	   double b = rad(x1) - rad(x2);
	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
	   Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS;
	   return Math.round(s*1000);
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/api/SignIn.do")
	public void SignIn(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
			String jsonString = request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String projId = Base64Encrypt.decode(jsonObj.getString("projId"));  
			String userId = Base64Encrypt.decode(jsonObj.getString("userId"));
			String signType = Base64Encrypt.decode(jsonObj.getString("signType"));
			String msg= ""; 
			String signTypeName="";
			  if(signType.equals("0")){
 			      signTypeName= "上午签到";
 			      msg= "迟到，请填写理由！"; 
	  		  }else if(signType.equals("2")){	  		   
	  			  signTypeName= "上午签退";
	  			  msg= "早退，请填写理由！";  
	  		  }else if(signType.equals("3")){
	  			  signTypeName= "下午签到";
	  			  msg= "迟到，请填写理由！"; 
	  		  }else if(signType.equals("1")){
	  			  signTypeName= "下午签退";
	  			  msg= "早退，请填写理由！";  
	  		  }
				 //	String signDate = Base64Encrypt.decode(jsonObj.getString("signDate"));			     
				 //	String signTime = Base64Encrypt.decode(jsonObj.getString("signTime"));
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
			java.util.Date date = new java.util.Date(); 
		    String signDate = formatDate.format(date);
		    String signTime = formatTime.format(date);
			String signMark = Base64Encrypt.decode(jsonObj.getString("signMark"));
			String signLocation = Base64Encrypt.decode(jsonObj.getString("signLocation"));
			String result="0"; 
						
			 int flag =  baseJdbcDao.getCount("SELECT COUNT(WID) FROM SYS_APP_SIGN  WHERE SIGN_TYPE='"+signTypeName+"' AND USER_ID ='"+userId+"'   AND SIGN_DATE = str_to_date('"+signDate+"','%Y-%m-%d')  ");
             if(flag>0){             	 
        		 String flag1 = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("SELECT SIGN_TYPE FROM SYS_APP_SIGN  WHERE  USER_ID ='"+userId+"' AND  SIGN_TYPE !='外出签到'   AND SIGN_DATE = str_to_date('"+signDate+"','%Y-%m-%d')  ORDER BY SIGN_TIME DESC LIMIT 0,1 "));
        		 if(flag1.equals("上午签到")){
        			 result ="4";
        			 msg ="今天已经上午上班打卡,禁止重复操作！";
        		 }else if(flag1.equals("下午签退")){
            		 result ="5";  
            		 msg ="今天已经下午下班打卡,禁止重复操作！";
        		 }else if(flag1.equals("上午签退")){
            		 result ="6";  
            		 msg ="今天已经上午下班打卡,禁止重复操作！";
        		 }else if(flag1.equals("下午签到")){
            		 result ="7";  
            		 msg ="今天已经下午上班打卡,禁止重复操作！";
        		 }       		 
             }else{

            	 String standerdTime=""; 
     		    String sql = "SELECT  SETMORNING_START,SETMORNING_ENT,SETAFTERNOON_START,SETAFTERNOON_ENT,SIGN_COUNT FROM HR_TIMESET WHERE STATUS='1'";
     		    Map<String,Object> map = baseJdbcDao.queryMap(sql);
                 if(!DataTypeUtil.validate(map)||map.size()==0){
             	   standerdTime = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("SELECT STANDARD_TIME FROM SYS_APP_SIGN_SET WHERE SIGN_TYPE='"+signTypeName+"'"));
                 }else{
             	   if(DataTypeUtil.formatDbColumn(map.get("SIGN_COUNT")).equals("1")){
             		   if(signType.equals("0")){
             			   standerdTime = DataTypeUtil.formatDbColumn(map.get("SETMORNING_START"))+":00";
             		   }else if(signType.equals("1")){
             			   standerdTime = DataTypeUtil.formatDbColumn(map.get("SETAFTERNOON_ENT"))+":00";
             		   }
             	   }else{
             		   if(signType.equals("0")){
             			   standerdTime = DataTypeUtil.formatDbColumn(map.get("SETMORNING_START"))+":00";
             		   }else if(signType.equals("1")){
             			   standerdTime = DataTypeUtil.formatDbColumn(map.get("SETAFTERNOON_ENT"))+":00";
             		   }else if(signType.equals("2")){
             			   standerdTime = DataTypeUtil.formatDbColumn(map.get("SETMORNING_ENT"))+":00";
             		   }else if(signType.equals("3")){
             			   standerdTime = DataTypeUtil.formatDbColumn(map.get("SETAFTERNOON_START"))+":00";
             		   }
             	   }
                 }
					
					
					
					
					java.util.Date actTime =   DateUtil.parseTime(signDate+" "
							+ ""+ signTime);
					java.util.Date planTime =   DateUtil.parseTime(signDate+" "+ standerdTime);
					String standerdMark = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("SELECT IFNULL(MARK,'') MARK FROM CM_PROJ WHERE PROJ_ID = '"+projId+"'"));
					String standerRadius = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("SELECT IFNULL(RADIUS,'0') RADIUS FROM CM_PROJ WHERE PROJ_ID = '"+projId+"'"));
			       if(!DataTypeUtil.validate(standerRadius)){
			    	   standerRadius="0";
			       }
					if(signType.equals("0")){ 
						if(actTime.before(planTime)){
							result="1";
						}
					}else if(signType.equals("1")){ 
						if(actTime.after(planTime)){
							result="1";
						}
					}else if(signType.equals("2")){ 
						if(actTime.after(planTime)){
							result="1";
						}
					}else if(signType.equals("3")){ 
						if(actTime.before(planTime)){
							result="1";
						}
					}
					
		            if(!DataTypeUtil.validate(standerdMark)){
		            	msg = "该项目未设定坐标，请联系管理员";
		            	result = "2";
		            }else{
		            	double radius = GetDistance(Double.parseDouble(signMark.split(",")[1]),Double.parseDouble(signMark.split(",")[0]),Double.parseDouble(standerdMark.split(",")[1]),Double.parseDouble(standerdMark.split(",")[0]));
		                if(radius > Double.parseDouble(standerRadius)*1000){
		                	msg = "不在项目打卡范围内，请到项目所属地区打卡！";
		                	result = "3";
		                }
		            }
					
		            if(result.equals("1")){
		            	  sql = "insert into sys_app_sign (WID, ID, SIGN_TYPE, SIGN_DATE, SIGN_LOCATION, SIGN_TIME, SIGN_MARK, STANDARD_TIME, STANDARD_MARK, STANDARD_RADIUS, USER_ID, PROJ_ID, RESULT)" +
		            			" values('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+signTypeName+"',str_to_date('"+signDate+"','%Y-%m-%d'),'"+signLocation+"',str_to_date('"+signDate+" "+signTime+"','%Y-%m-%d %H:%i:%s'),'"+signMark+"'," +
		            					"str_to_date('"+signDate+" "+standerdTime+"','%Y-%m-%d %H:%i:%s'),'"+standerdMark+"','"+standerRadius+"','"+userId+"','"+projId+"','1');";
		            	baseJdbcDao.insert(sql);
		            	msg =  "打卡成功！";
		            }else{
		            	  sql = "SELECT ID,CODE_NAME FROM CM_CODE WHERE CODE_TYPE = 'REASON_TYPE'";
		    			List<Map<String, Object>> list =   baseJdbcDao.queryListMap(sql);
		    			resObj.put("REASON_TYPE", list);
		            }
		      }
             resObj.put("MSG", msg);
		     resObj.put("RESULT", result);
             System.out.println(result);
		} catch (Exception e) {
			 resObj.put("RESULT", "error");
			 resObj.put("MSG", "接口调用失败");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
	
	

	@ResponseBody
	@RequestMapping(value = "/api/SignDetail.do")
	public void SignDetail(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
			String jsonString = request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String projId = Base64Encrypt.decode(jsonObj.getString("projId"));  
			String userId = Base64Encrypt.decode(jsonObj.getString("userId"));
			String signType = Base64Encrypt.decode(jsonObj.getString("signType"));
			String signTypeName="";
			 if(signType.equals("0")){
  			   signTypeName= "上午签到";
	  		  }else if(signType.equals("2")){
	  			  signTypeName= "上午签退";
	  		  }else if(signType.equals("3")){
	  			  signTypeName= "下午签到";
	  		  }else if(signType.equals("1")){
	  			  signTypeName= "下午签退";
	  		  }
/*			String signDate = Base64Encrypt.decode(jsonObj.getString("signDate"));
			String signTime = Base64Encrypt.decode(jsonObj.getString("signTime"));*/
			
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
			java.util.Date date = new java.util.Date(); 
		    String signDate = formatDate.format(date);
		    String signTime = formatTime.format(date);
		    
			String signMark = Base64Encrypt.decode(jsonObj.getString("signMark"));
			String signLocation = Base64Encrypt.decode(jsonObj.getString("signLocation"));
			String reasonType = Base64Encrypt.decode(jsonObj.getString("reasonType"));
			String reasonDetail = Base64Encrypt.decode(jsonObj.getString("reasonDetail"));
			String result = Base64Encrypt.decode(jsonObj.getString("result"));
			String standerdTime=""; 
		    String sql = "SELECT  SETMORNING_START,SETMORNING_ENT,SETAFTERNOON_START,SETAFTERNOON_ENT,SIGN_COUNT FROM HR_TIMESET WHERE STATUS='1'";
		    Map<String,Object> map = baseJdbcDao.queryMap(sql);
            if(!DataTypeUtil.validate(map)||map.size()==0){
        	   standerdTime = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("SELECT STANDARD_TIME FROM SYS_APP_SIGN_SET WHERE SIGN_TYPE='"+signTypeName+"'"));
            }else{
        	   if(DataTypeUtil.formatDbColumn(map.get("SIGN_COUNT")).equals("1")){
        		   if(signType.equals("0")){
        			   standerdTime = DataTypeUtil.formatDbColumn(map.get("SETMORNING_START"))+":00";
        		   }else if(signType.equals("1")){
        			   standerdTime = DataTypeUtil.formatDbColumn(map.get("SETAFTERNOON_ENT"))+":00";
        		   }
        	   }else{
        		   if(signType.equals("0")){
        			   standerdTime = DataTypeUtil.formatDbColumn(map.get("SETMORNING_START"))+":00";
        		   }else if(signType.equals("1")){
        			   standerdTime = DataTypeUtil.formatDbColumn(map.get("SETAFTERNOON_ENT"))+":00";
        		   }else if(signType.equals("2")){
        			   standerdTime = DataTypeUtil.formatDbColumn(map.get("SETMORNING_ENT"))+":00";
        		   }else if(signType.equals("3")){
        			   standerdTime = DataTypeUtil.formatDbColumn(map.get("SETAFTERNOON_START"))+":00";
        		   }
        	   }
            }
	          
			String standerdMark = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("SELECT IFNULL(MARK,'') MARK FROM CM_PROJ WHERE PROJ_ID = '"+projId+"'"));
			String standerRadius = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("SELECT IFNULL(RADIUS,'0') RADIUS FROM CM_PROJ WHERE PROJ_ID = '"+projId+"'"));
	       if(!DataTypeUtil.validate(standerRadius)){
	    	   standerRadius="0";
	       }
			
			 sql = "insert into sys_app_sign (WID, ID, SIGN_TYPE, SIGN_DATE, SIGN_LOCATION, SIGN_TIME, SIGN_MARK, STANDARD_TIME, STANDARD_MARK, STANDARD_RADIUS, USER_ID, PROJ_ID, RESULT,REASON_TYPE,REASON_DETAIL)" +
			" values('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+signTypeName+"',str_to_date('"+signDate+"','%Y-%m-%d'),'"+signLocation+"',str_to_date('"+signDate+" "+signTime+"','%Y-%m-%d %H:%i:%s'),'"+signMark+"'," +
					"str_to_date('"+signDate+" "+standerdTime+"','%Y-%m-%d %H:%i:%s'),'"+standerdMark+"','"+standerRadius+"','"+userId+"','"+projId+"','"+result+"','"+reasonType+"','"+reasonDetail+"');";
	       baseJdbcDao.insert(sql);
	       
		     resObj.put("RESULT", "1");
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/SignMarker.do")
	public void SignMarker(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
			String jsonString = request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String projId = Base64Encrypt.decode(jsonObj.getString("projId"));  
			String userId = Base64Encrypt.decode(jsonObj.getString("userId")); 
/*			String signDate = Base64Encrypt.decode(jsonObj.getString("signDate"));
			String signTime = Base64Encrypt.decode(jsonObj.getString("signTime"));*/
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
			java.util.Date date = new java.util.Date(); 
		    String signDate = formatDate.format(date);
		    String signTime = formatTime.format(date);
		    
			String signMark = Base64Encrypt.decode(jsonObj.getString("signMark"));
			String signLocation = Base64Encrypt.decode(jsonObj.getString("signLocation")); 
			String reasonDetail = Base64Encrypt.decode(jsonObj.getString("reasonDetail")); 
        	
			String sql = "insert into sys_app_sign (WID, ID, SIGN_TYPE, SIGN_DATE, SIGN_LOCATION, SIGN_TIME, SIGN_MARK, USER_ID, PROJ_ID,REASON_DETAIL,RESULT)" +
			" values('"+Guid.getGuid()+"','"+Guid.getGuid()+"','外出签到',str_to_date('"+signDate+"','%Y-%m-%d'),'"+signLocation+"',str_to_date('"+signDate+" "+signTime+"','%Y-%m-%d %H:%i:%s'),'"+signMark+"'," +
					"'"+userId+"','"+projId+"','"+reasonDetail+"','1');";
	       baseJdbcDao.insert(sql);
	       
		     resObj.put("RESULT", "1");
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/api/GetReasonTypeList.do")
	public void GetReasonTypeList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
			String sql = "SELECT ID,CODE_NAME FROM CM_CODE WHERE CODE_TYPE = 'REASON_TYPE'";
			List<Map<String, Object>> list =   baseJdbcDao.queryListMap(sql);
	       
		     resObj.put("RESULT", "1");
		     resObj.put("LIST", list);
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
		
	@ResponseBody
	@RequestMapping(value = "/api/GetSignHistory.do")
	public void GetSignHistory(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
			String jsonString = request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String beginDate = Base64Encrypt.decode(jsonObj.getString("beginDate")); 
			String endDate = Base64Encrypt.decode(jsonObj.getString("endDate"));
			String start =  Base64Encrypt.decode(jsonObj.getString("start"));
			String pageSize =  Base64Encrypt.decode(jsonObj.getString("pageSize"));
			String userId = Base64Encrypt.decode(jsonObj.getString("userId"));
			String where ="";
			if(DataTypeUtil.validate(beginDate)){
				where =" AND SIGN_DATE >= STR_TO_DATE('"+beginDate+"','%Y-%m-%d') ";
			}
			if(DataTypeUtil.validate(endDate)){
				where = where + " AND STR_TO_DATE('"+endDate+"','%Y-%m-%d')>= SIGN_DATE ";
			}
			 int begin = Integer.parseInt(start);  			
			 begin = begin-1;
	         String sql="SELECT  COUNT(WID) NUM  FROM SYS_APP_SIGN T WHERE T.USER_ID = '"+userId+"'  "+where+" ";	             
          int count = baseJdbcDao.getCount(sql);
			int totalPage = count/Integer.parseInt(pageSize);
			if(count%Integer.parseInt(pageSize)>0){
				totalPage=totalPage+1;
			}
		  int currentPage = Integer.parseInt(start)/Integer.parseInt(pageSize)+1;
			
		  sql = "SELECT ID ,SIGN_TYPE  ,DATE_FORMAT(SIGN_TIME,'%Y-%m-%d %H:%i:%s') SIGN_TIME  ,ifNull((SELECT PROJ_NAME FROM CM_PROJ WHERE PROJ_ID = T.PROJ_ID),'') PROJ_NAME, RESULT,(SELECT CODE_NAME FROM CM_CODE WHERE CODE_TYPE = 'REASON_TYPE' AND ID = T.REASON_TYPE) REASON_TYPE ,REASON_DETAIL  FROM SYS_APP_SIGN T WHERE T.USER_ID = '"+userId+"'  "+where+" ORDER BY SIGN_TIME DESC   LIMIT "+begin+","+pageSize;
		  List<Map<String, Object>> list =   baseJdbcDao.queryListMap(sql);
          if(DataTypeUtil.validate(list)){
        	  for(Map<String, Object> map:list){
        		  if(DataTypeUtil.formatDbColumn(map.get("RESULT")).equals("0")){
        			  if(DataTypeUtil.formatDbColumn(map.get("SIGN_TYPE")).equals("上午签到")||DataTypeUtil.formatDbColumn(map.get("SIGN_TYPE")).equals("下午签到")){
        				  map.put("RESULT", "2");
        			  }else if(DataTypeUtil.formatDbColumn(map.get("SIGN_TYPE")).equals("上午签退")||DataTypeUtil.formatDbColumn(map.get("SIGN_TYPE")).equals("下午签退")){
        				  map.put("RESULT", "3");
        			  }        			 
        		  }else if(DataTypeUtil.formatDbColumn(map.get("RESULT")).equals("1")){
        			  map.put("RESULT", "1");
        		  }else if(DataTypeUtil.formatDbColumn(map.get("RESULT")).equals("2")){
        			  map.put("RESULT", "4");
        		  }else if(DataTypeUtil.formatDbColumn(map.get("RESULT")).equals("3")){
        			  map.put("RESULT", "4");
        		  }
        		  if(DataTypeUtil.formatDbColumn(map.get("SIGN_TYPE")).equals("上午签到")){
        			  map.put("SIGN_TYPE", "上午上班打卡");
        		  }else if(DataTypeUtil.formatDbColumn(map.get("SIGN_TYPE")).equals("上午签退")){
        			  map.put("SIGN_TYPE", "上午下班打卡");
        		  }else if(DataTypeUtil.formatDbColumn(map.get("SIGN_TYPE")).equals("下午签到")){
        			  map.put("SIGN_TYPE", "下午上班打卡");
        		  }else if(DataTypeUtil.formatDbColumn(map.get("SIGN_TYPE")).equals("下午签退")){
        			  map.put("SIGN_TYPE", "下午下班打卡");
        		  }
        		  
        	  }
          }
		  resObj.put("RESULT", "1");
          resObj.put("LIST",list);
		  resObj.put("TOTAL_PAGE", totalPage);
		  resObj.put("CURRENT_PAGE", currentPage);
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/GetUsersListByKey.do")
	public void GetUsersListByKey(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
			String jsonString = request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String key = Base64Encrypt.decode(jsonObj.getString("key")); 
			String start =  Base64Encrypt.decode(jsonObj.getString("start"));
			String pageSize =  Base64Encrypt.decode(jsonObj.getString("pageSize"));
			String where ="";
			if(DataTypeUtil.validate(key)){
				where =" AND USER_NAME LIKE '%"+key+"%' OR ACTUAL_NAME LIKE '%"+key+"%' ";
			}
			 int begin = Integer.parseInt(start);  			
			 begin = begin-1;
	         String sql="SELECT  COUNT(WID) NUM  FROM CM_USERS T WHERE 1=1  "+where;	             
          int count = baseJdbcDao.getCount(sql);
			int totalPage = count/Integer.parseInt(pageSize);
			if(count%Integer.parseInt(pageSize)>0){
				totalPage=totalPage+1;
			}
		  int currentPage = Integer.parseInt(start)/Integer.parseInt(pageSize)+1;
			
		  sql = "SELECT USER_ID ,USER_NAME,ACTUAL_NAME FROM CM_USERS WHERE 1=1  "+where+" ORDER BY USER_NAME ASC   LIMIT "+begin+","+pageSize;
		  List<Map<String, Object>> list =   baseJdbcDao.queryListMap(sql);
       
		  resObj.put("RESULT", "1");
          resObj.put("LIST",list);
		  resObj.put("TOTAL_PAGE", totalPage);
		  resObj.put("CURRENT_PAGE", currentPage);
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/UpdateMajor.do")
	public void UpdateMajor(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
			String jsonString = request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String tableName = Base64Encrypt.decode(jsonObj.getString("type")); 
			String planId =  Base64Encrypt.decode(jsonObj.getString("planId"));
			String isMajor =  Base64Encrypt.decode(jsonObj.getString("isMajor"));
			if(tableName.equals("1")){
				tableName = "DS_PLAN";
			}else if(tableName.equals("2")){
				tableName = "SM_PLAN";
			}else if(tableName.equals("3")){
				tableName = "SM_TEST";
			}else if(tableName.equals("4")){
				tableName = "SYS_PLAN";
			}
	       String sql = "UPDATE "+tableName+" SET IS_MAJOR = '"+isMajor+"' where PLAN_ID = '"+planId+"'";
	       baseJdbcDao.update(sql);  
	       resObj.put("RESULT", "1");
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
}
