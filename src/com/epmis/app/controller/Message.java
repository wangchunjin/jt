package com.epmis.app.controller;

import java.io.File;
import java.io.IOException;
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

import com.epmis.app.service.SendMsgService;
import com.epmis.app.thread.SendMsgThread;
import com.epmis.app.vo.SysAppMsg;
import com.epmis.co.service.CoDispatchReceiveService;
import com.epmis.co.vo.CoDispatch;
import com.epmis.km.service.KmDocService;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.Base64Encrypt;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;

@Controller
public class Message {
    @Autowired
    @Qualifier("baseJdbcDao")
    private BaseJdbcDao baseJdbcDao;
    @ResponseBody
	@RequestMapping(value = "/api/GetMajorListByDate.do")
	public void GetMajorListByDate(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String projId =  Base64Encrypt.decode(jsonObj.getString("projId")); 
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String ParentId =  Base64Encrypt.decode(jsonObj.getString("parentId"));
			String start =  Base64Encrypt.decode(jsonObj.getString("start"));
			String pageSize =  Base64Encrypt.decode(jsonObj.getString("pageSize"));
			String DocGrade =  Base64Encrypt.decode(jsonObj.getString("type"));
			//Base64.generateDecoder();
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
    }
    @ResponseBody
	@RequestMapping(value = "/api/AddCalendar.do")
	public void AddCalendar(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String calendarTime =  Base64Encrypt.decode(jsonObj.getString("calendarTime")); 
			String id =  Base64Encrypt.decode(jsonObj.getString("id")); 
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String remindTime =  Base64Encrypt.decode(jsonObj.getString("remindTime"));
			String content =  Base64Encrypt.decode(jsonObj.getString("content"));
			String title =  Base64Encrypt.decode(jsonObj.getString("title"));
			String sql="SELECT COUNT(ID)NUM FROM SYS_APP_MSG WHERE ID ='"+id+"'";
			if(baseJdbcDao.getCount(sql)>0){
				resObj.put("RESULT", "0");
				resObj.put("MSG", "ID在服务器端已存在");
			}else{
		    sql = "INSERT INTO SYS_APP_MSG(WID,ID,TITLE,CONTENT,FLAG,MSG_TYPE,TO_USERS,CALENDAR_TIME,REMIND_TIME,CREATED_BY,CREATED_TIME)" +
					"VALUES('"+Guid.getGuid()+"','"+id+"','"+title+"','"+content+"','2','6','"+userId+"',"+(DataTypeUtil.validate(calendarTime) ? "str_to_date('"+calendarTime+"','%Y-%m-%d %H:%i:%s')": null)+","+(DataTypeUtil.validate(remindTime) ? "str_to_date('"+remindTime+"','%Y-%m-%d %H:%i:%s')": null)+",'"+userId+"',now())";
			baseJdbcDao.insert(sql);
			resObj.put("RESULT", "1");
			}
		 } catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
    }
    
    @ResponseBody
	@RequestMapping(value = "/api/DeleteCalendar.do")
	public void DeleteCalendar(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String id =  Base64Encrypt.decode(jsonObj.getString("id")); 
			String sql = "DELETE FROM  SYS_APP_MSG WHERE ID= '"+id+"'";
			baseJdbcDao.delete(sql);
			resObj.put("RESULT", "1");
		 } catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
    }
    
    @ResponseBody
  	@RequestMapping(value = "/api/UpdateCalendar.do")
  	public void UpdateCalendar(HttpServletRequest request,HttpServletResponse response) throws IOException {
  		Map<String, Object> resObj = new HashMap<String, Object>();
  		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
  		try {
  		    String jsonString=  request.getParameter("jsonString");   
  		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String calendarTime =  Base64Encrypt.decode(jsonObj.getString("calendarTime")); 
			String id =  Base64Encrypt.decode(jsonObj.getString("id")); 
			String newId =  Base64Encrypt.decode(jsonObj.getString("newId")); 
			String remindTime =  Base64Encrypt.decode(jsonObj.getString("remindTime"));
			String content =  Base64Encrypt.decode(jsonObj.getString("content"));
			String title =  Base64Encrypt.decode(jsonObj.getString("title"));
			
  			String sql = "UPDATE SYS_APP_MSG SET ID='"+newId+"',TITLE='"+title+"',CONTENT='"+content+"',CALENDAR_TIME="+(DataTypeUtil.validate(calendarTime) ? "str_to_date('"+calendarTime+"','%Y-%m-%d %H:%i:%s')": null)+" ,REMIND_TIME="+(DataTypeUtil.validate(remindTime) ? "str_to_date('"+remindTime+"','%Y-%m-%d %H:%i:%s')": null)+" WHERE ID = '"+id+"'";
  			baseJdbcDao.update(sql);
  			resObj.put("RESULT", "1");
  		 } catch (Exception e) {
  			resObj.put("RESULT", "0");
  			e.printStackTrace();
  		}		   
  		WriterJsonArray.AppWriterJSONArray(resObj,response);
      }
    
    @ResponseBody
	@RequestMapping(value = "/api/GetCalendarListByUserId.do")
	public void GetCalendarListByUserId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String start =  Base64Encrypt.decode(jsonObj.getString("start"));
			String pageSize =  Base64Encrypt.decode(jsonObj.getString("pageSize"));
			int begin = Integer.parseInt(start);  			
			begin = begin-1;
			
             String sql=" SELECT  COUNT(WID)   FROM SYS_APP_MSG K WHERE MSG_TYPE ='6' AND  LOCATE('"+userId+"',TO_USERS)>0 ORDER BY CREATED_TIME DESC";

            int count = baseJdbcDao.getCount(sql);
			int totalPage = count/Integer.parseInt(pageSize);
			if(count%Integer.parseInt(pageSize)>0){
				totalPage=totalPage+1;
			}
			int currentPage = Integer.parseInt(start)/Integer.parseInt(pageSize)+1;
            
		   sql=" SELECT  ID,TITLE,CONTENT ,DATE_FORMAT(CALENDAR_TIME,'%Y-%m-%d %H:%i:%s') CALENDAR_TIME,DATE_FORMAT(REMIND_TIME,'%Y-%m-%d %H:%i:%s') REMIND_TIME,DATE_FORMAT(CREATED_TIME,'%Y-%m-%d %H:%i:%s') CREATED_TIME   FROM SYS_APP_MSG K WHERE MSG_TYPE ='6' AND LOCATE('"+userId+"',TO_USERS)>0 ORDER BY CREATED_TIME DESC  LIMIT "+begin+","+pageSize;
            list  =  baseJdbcDao.queryListMap(sql);
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
	@RequestMapping(value = "/api/GetReceiveList.do")
	public void GetReceiveList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String start =  Base64Encrypt.decode(jsonObj.getString("start"));
			String pageSize =  Base64Encrypt.decode(jsonObj.getString("pageSize"));
			String beginDate = Base64Encrypt.decode(jsonObj.getString("beginDate")); 
			String endDate = Base64Encrypt.decode(jsonObj.getString("endDate"));
			String title =  Base64Encrypt.decode(jsonObj.getString("title"));
			String where ="";
			if(DataTypeUtil.validate(beginDate)){
				where =" AND ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  > '"+beginDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+beginDate+"') ";
			}
			if(DataTypeUtil.validate(endDate)){
				where =where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  < '"+endDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+endDate+"')  ";
			}
			if(DataTypeUtil.validate(title)){
				where =where + " AND TITLE  LIKE '%"+title+"%' ";
			}
			
			int begin = Integer.parseInt(start);  			
			begin = begin-1;
			
             String sql="SELECT COUNT(DISPATCH_ID) NUM FROM CO_DISPATCH WHERE DISPATCH_ID IN (SELECT DISPATCH_ID FROM CO_RECEIVE WHERE RECEIVE_USER ='"+userId+"' AND DELETE_FLAG = '0')  "+where ;

            int count = baseJdbcDao.getCount(sql);
			int totalPage = count/Integer.parseInt(pageSize);
			if(count%Integer.parseInt(pageSize)>0){
				totalPage=totalPage+1;
			}
			int currentPage = Integer.parseInt(start)/Integer.parseInt(pageSize)+1;
            
		   sql="SELECT DISPATCH_ID,TITLE,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d %H:%i:%s') CREATED_DATE, (SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  CREATED_BY)CREATED_NAME,CREATED_BY,(SELECT MAX(RECEIVE_ID) FROM CO_RECEIVE C WHERE C.DISPATCH_ID = CO_DISPATCH.DISPATCH_ID  AND RECEIVE_USER ='"+userId+"' ) RECEIVE_ID,(SELECT MAX(READ_FLAG) FROM CO_RECEIVE C WHERE C.DISPATCH_ID = CO_DISPATCH.DISPATCH_ID  AND RECEIVE_USER ='"+userId+"' ) READ_FLAG  FROM CO_DISPATCH WHERE DISPATCH_ID IN (SELECT DISPATCH_ID FROM CO_RECEIVE WHERE RECEIVE_USER ='"+userId+"' AND DELETE_FLAG = '0') "+where+"   ORDER BY CO_DISPATCH.CREATED_DATE DESC  LIMIT "+begin+","+pageSize;
            list  =  baseJdbcDao.queryListMap(sql);
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
    
	@Autowired
    private CoDispatchReceiveService coDispatchReceiveService;
	
    @ResponseBody
	@RequestMapping(value = "/api/GetReceiveInfo.do")
	public void GetReceiveInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>(); 
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String dispatchId =  Base64Encrypt.decode(jsonObj.getString("dispatchId")); 
			  resObj = coDispatchReceiveService.OpenDispatch(dispatchId);
              resObj.put("RESULT", "1");

		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
    }
    
    @ResponseBody
	@RequestMapping(value = "/api/AddDispatchInfo.do")
	public void AddDispatchInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>(); 
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
		    String userId =  Base64Encrypt.decode(jsonObj.getString("userId"));
		    String title =  Base64Encrypt.decode(jsonObj.getString("title"));
		    String receiveUser =  Base64Encrypt.decode(jsonObj.getString("receiveUser"));
		    String content =  Base64Encrypt.decode(jsonObj.getString("content"));
		    
		    CoDispatch coDispatch = new CoDispatch();
		    coDispatch.setContent(content);
		    coDispatch.setTitle(title);
		    coDispatch.setReceiveUser(receiveUser);
		    
		    UserInfo userInfo = new UserInfo();
		    userInfo.setUserId(userId);
			String res =  coDispatchReceiveService.AddDispatch(userInfo,coDispatch);
            if(res.equals("success")){ 
			   resObj.put("RESULT", "1");
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
	@RequestMapping(value = "/api/GetMessageList.do")
	public void GetMessageList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>(); 
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
		    String type =  Base64Encrypt.decode(jsonObj.getString("type"));
		    String userId = Base64Encrypt.decode(jsonObj.getString("userId"));
			String start =  Base64Encrypt.decode(jsonObj.getString("start"));
			String pageSize =  Base64Encrypt.decode(jsonObj.getString("pageSize"));
			
			String beginDate = Base64Encrypt.decode(jsonObj.getString("beginDate")); 
			String endDate = Base64Encrypt.decode(jsonObj.getString("endDate"));
			
            String where ="";
            
        	if(DataTypeUtil.validate(beginDate)){
				where =" AND ( DATE_FORMAT(CREATED_TIME,'%Y-%m-%d')  > '"+beginDate+"' OR   DATE_FORMAT(CREATED_TIME,'%Y-%m-%d')  = '"+beginDate+"') ";
			}
			if(DataTypeUtil.validate(endDate)){
				where =where + " AND  ( DATE_FORMAT(CREATED_TIME,'%Y-%m-%d')  < '"+endDate+"' OR   DATE_FORMAT(CREATED_TIME,'%Y-%m-%d')  = '"+endDate+"')  ";
			}
            
            if(type.equals("0")){
            	where =where + " AND  MSG_TYPE = '1' AND CREATED_BY = '"+userId+"' ";
            }else{
            	where =where + " AND  MSG_TYPE = '"+type+"' and (LOCATE('"+userId+"',TO_USERS)>0 OR FLAG='1') ";
            }			
			int begin = Integer.parseInt(start);  			
			begin = begin-1;
		    String sql ="SELECT  COUNT(WID) NUM   FROM SYS_APP_MSG  where 1=1 "+where;
		    int count = baseJdbcDao.getCount(sql);
			int totalPage = count/Integer.parseInt(pageSize);
			if(count%Integer.parseInt(pageSize)>0){
				totalPage=totalPage+1;
			}
			int currentPage = Integer.parseInt(start)/Integer.parseInt(pageSize)+1;
		    sql ="SELECT  ID,TITLE,CONTENT,(SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  CREATED_BY)CREATED_NAME,DATE_FORMAT(CREATED_TIME,'%Y-%m-%d %H:%i:%s') CREATED_TIME,iFNULL(PROJ_ID,'')PROJ_ID,iFNULL(REMARK,'')REMARK,iFNULL(LINK_ID,'')LINK_ID  FROM SYS_APP_MSG  where 1=1 "+where+"  ORDER BY CREATED_TIME DESC LIMIT "+begin+","+pageSize;	 
		    list  =  baseJdbcDao.queryListMap(sql);
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

    @Autowired
    private KmDocService kmDocService;
    
    @ResponseBody
   	@RequestMapping(value = "/api/GetGtInfo.do")
   	public void GetGtInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
   		Map<String, Object> resObj = new HashMap<String, Object>(); 
   		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
   		try {
   		    String jsonString=  request.getParameter("jsonString");   
   		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
   		    String id =  Base64Encrypt.decode(jsonObj.getString("id"));   		    
   		    String sql = "SELECT  ID,TITLE,TO_USERS,CONTENT,(SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  CREATED_BY)CREATED_NAME,DATE_FORMAT(CREATED_TIME,'%Y-%m-%d %H:%i:%s') CREATED_TIME,iFNULL(LINK_ID,'')LINK_ID  FROM SYS_APP_MSG WHERE ID = '"+id+"'";
   		    resObj  =  baseJdbcDao.queryMap(sql);
		    if(DataTypeUtil.validate(resObj)){
					String show_name="";
					String to_user = resObj.get("TO_USERS").toString();
					String userSql =  "SELECT ACTUAL_NAME FROM CM_USERS WHERE USER_ID IN ('"+to_user.replaceAll(",","','")+"')";
					List<Map<String ,Object>> listuser = baseJdbcDao.queryListMap(userSql);
					if(DataTypeUtil.validate(listuser)){
						for(Map<String ,Object>mapuser:listuser){
							show_name = DataTypeUtil.validate(show_name) ?  show_name +"、"+DataTypeUtil.formatDbColumn(mapuser.get("ACTUAL_NAME")) : DataTypeUtil.formatDbColumn(mapuser.get("ACTUAL_NAME"));						
						}
					}
					resObj.put("TO_USERS", show_name);	
					
					String link_id =  DataTypeUtil.formatDbColumn(resObj.get("LINK_ID"));
					if(DataTypeUtil.validate(link_id)){
						String[] docIds  = link_id.split(",");
						for(int i=0;i<docIds.length;i++){
							Map<String, Object> map = new HashMap<String, Object>(); 
							
							map.put("DOC_ID", docIds[i].toString());
							map.put("DOC_TITLE", DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select title from km_doc where doc_id ='"+docIds[i].toString()+"'")));
							map.put("DOC_NUMBER", DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue("select doc_number from km_doc where doc_id ='"+docIds[i].toString()+"'")));
							String url =kmDocService.GetUrlById(map.get("DOC_ID").toString(), "");
		 				    map.put("URL",url);
				 			File file = new File( AppSetting.PROJECT_PATH.substring(0, AppSetting.PROJECT_PATH.lastIndexOf(AppSetting.PROJECT_NAME.substring(1)))+url);
		 					if(file.exists()&&file.isFile()){
		 						map.put("FILE_SIZE",file.length()/1024+"KB"); 
		 					} 
		 					list.add(map);
						}
					}
		    }
		    resObj.remove("LINK_ID");
		    resObj.put("DOC_LIST", list);
		    resObj.put("RESULT", "1");
   		} catch (Exception e) {
   			resObj = new HashMap<String, Object>(); 
   			resObj.put("RESULT", "0");
   			e.printStackTrace();
   		}		   
   		WriterJsonArray.AppWriterJSONArray(resObj,response);
     }
 

    @Autowired
    private SendMsgService sendMsgService;
    
    @ResponseBody
	@RequestMapping(value = "/api/AddGtInfo.do")
	public void AddGtInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>(); 
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
		    String title =  Base64Encrypt.decode(jsonObj.getString("title"));
		    String content =  Base64Encrypt.decode(jsonObj.getString("content"));
		    String flag =  Base64Encrypt.decode(jsonObj.getString("flag"));
		    String toUsers =  Base64Encrypt.decode(jsonObj.getString("toUsers"));
		    String userId =  Base64Encrypt.decode(jsonObj.getString("userId"));
		    String id = Guid.getGuid();
		    
		    SysAppMsg sysAppMsg = new SysAppMsg();
		    sysAppMsg.setId(id);
		    sysAppMsg.setTitle(title); 
		    sysAppMsg.setContent(content);
		    sysAppMsg.setFlag(flag);
		    sysAppMsg.setMsgType("1");
		    sysAppMsg.setToUsers(toUsers);
		    sysAppMsg.setCreatedBy(userId);	
		    sysAppMsg.setLinkId(id);
		//	String res =  sendMsgService.SendMsg(sysAppMsg);	
		    SendMsgThread SendMsgThread  =  new SendMsgThread(sysAppMsg);
		    SendMsgThread.run();
		    String res = "success";
            if(res.equals("success")){ 
			   resObj.put("RESULT", "1");
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
	@RequestMapping(value = "/api/GetMajorInfo.do")
	public void GetMajorInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String,
				Object>(); 
		List<Map<String ,Object>> projlist = new ArrayList<Map<String ,Object>>();
		List<Map<String ,Object>> tasklist = new ArrayList<Map<String ,Object>>();
		List<Map<String ,Object>> doclist = new ArrayList<Map<String ,Object>>();
		Map<String ,Object> projmap = new HashMap<String ,Object>();
		Map<String ,Object> taskmap = new HashMap<String ,Object>();
		Map<String ,Object> docmap = new HashMap<String ,Object>();	
		String tempprojid="";
		String temptaskid="";
		int projnum=0;
		int tasknum=0;
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
		    String id =  Base64Encrypt.decode(jsonObj.getString("id"));
		    String userId =  Base64Encrypt.decode(jsonObj.getString("userId"));
            String sql = "select link_id from sys_app_msg where id ='"+id+"'";
            String yesterday = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
		    if(DataTypeUtil.validate(yesterday)){
		    	List<Map<String ,Object>> list =sendMsgService.GetMajorListByDate(yesterday,userId);
		    	if(DataTypeUtil.validate(list)){
	    		
		    		for(Map<String ,Object> map:list){
		    			String projid = DataTypeUtil.formatDbColumn(map.get("PROJ_ID"));
		    			if(!tempprojid.equals(projid)){
		    				projnum=0;
		    			    projmap = new HashMap<String ,Object>();
		    				projmap.put("PARENT_ID", "0");
		    				projmap.put("PROJ_ID", projid);
		    				projmap.put("PROJ_SHORT_NAME", DataTypeUtil.formatDbColumn(map.get("PROJ_SHORT_NAME")));
		    				projmap.put("PROJ_NAME", DataTypeUtil.formatDbColumn(map.get("PROJ_NAME")));
		    				projlist.add(projmap);
		    			} 
		    				projnum++;
		    				projmap.put("NUM", projnum);	
		    				tempprojid = projid;
		    				
		    					    			
		    			String taskid = DataTypeUtil.formatDbColumn(map.get("PLAN_ID"));
		    			if(!temptaskid.equals(taskid)){
		    				tasknum=0;
		    				taskmap = new HashMap<String ,Object>();
		    				taskmap.put("PARENT_ID", projid);
		    				taskmap.put("TASK_ID", taskid);
		    				taskmap.put("TASK_SHORT_NAME", DataTypeUtil.formatDbColumn(map.get("PLAN_SHORT_NAME")));
		    				taskmap.put("TASK_NAME", DataTypeUtil.formatDbColumn(map.get("PLAN_NAME")));		    		
		    				tasklist.add(taskmap);
		    			}
		    				tasknum++;
		    				taskmap.put("NUM", tasknum);	
		    				temptaskid =taskid;		
		    				
		    				docmap = new HashMap<String ,Object>();	
		    				docmap.put("PARENT_ID", taskid);
		    				docmap.put("DOC_ID", DataTypeUtil.formatDbColumn(map.get("DOC_ID")));
		    				docmap.put("DOC_NUMBER", DataTypeUtil.formatDbColumn(map.get("DOC_NUMBER")));
		    				docmap.put("TITLE", DataTypeUtil.formatDbColumn(map.get("TITLE")));
		    				docmap.put("URL", DataTypeUtil.formatDbColumn(map.get("URL")));
		    				docmap.put("FILE_SIZE", DataTypeUtil.formatDbColumn(map.get("FILE_SIZE")));
		    				doclist.add(docmap);
		    				
		    				
		    		}
		    		resObj.put("PROJ_LIST", projlist);
		    		resObj.put("TASK_LIST", tasklist);
		    		resObj.put("DOC_LIST", doclist);
		    	}		    	
		    	
		    	resObj.put("RESULT", "1");
		    }else{
               resObj.put("RESULT", "0");
            }
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
    }
}
