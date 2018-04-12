package com.epmis.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.imp.BaseJdbcServiceImpl;
import com.epmis.sys.util.Base64Encrypt;
import com.epmis.sys.util.DESEncrypt;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.WriterJsonArray;

@Controller
public class SystemSet {
	  @Autowired
	  @Qualifier("baseJdbcDao")
	  private BaseJdbcDao baseJdbcDao;
	  
	@ResponseBody
	@RequestMapping(value = "/api/login.do")
	public void login(HttpServletRequest request,HttpServletResponse response) throws IOException {

		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");  
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String username =  jsonObj.getString("username"); 
			String password =  jsonObj.getString("password"); 
			String channelid =  Base64Encrypt.decode(jsonObj.getString("channelid")); 
			String platform = Base64Encrypt.decode(jsonObj.getString("platform"));
			String device_id = Base64Encrypt.decode(jsonObj.getString("device_id")); 
		  String sql = "SELECT A.USER_ID,A.USER_NAME,A.ACTUAL_NAME FROM CM_USERS A WHERE A.USER_NAME='" + Base64Encrypt.decode(username)+ "' AND DISABLE_FLAG = '0' AND ENSEMBLE ='Y' ";
          if(DataTypeUtil.validate(password)){
        	  if(platform.equals("2")){
              	 sql = sql + " AND A.PASSWORD='" + DESEncrypt.desEncode(Base64Encrypt.decode(password)) + "'";       	//ios密码加密	  
        	  }else{
              	 sql = sql + " AND A.PASSWORD='" + password + "'";
        	  }
          }else{
         	 sql = sql + "AND (A.PASSWORD='' OR A.PASSWORD IS NULL)";
          }
          Map<String, Object> userMap =  baseJdbcDao.queryMap(sql); 
          if(DataTypeUtil.validate(userMap)){
        	String UserId = userMap.get("USER_ID").toString();
        	//修正推送用户关联表
        	sql = "SELECT COUNT(WID) NUM FROM SYS_APP_MSG_SET WHERE DEVICE_ID = '"+device_id+"'";
        	if(baseJdbcDao.getCount(sql)>0){
        		sql = "UPDATE  SYS_APP_MSG_SET SET USER_ID = '"+UserId+"',CREATED_TIME = NOW(),CHANNEL_ID='"+channelid+"' , PLATFORM = '"+platform+"' WHERE DEVICE_ID = '"+device_id+"'   ";
        		baseJdbcDao.update(sql);
        	}else{
        		sql = "INSERT INTO SYS_APP_MSG_SET(WID,ID,USER_ID,CHANNEL_ID,PLATFORM,DEVICE_ID)VALUES('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+UserId+"','"+channelid+"','"+platform+"','"+device_id+"')";
        		baseJdbcDao.insert(sql);
        	}       	       	
      	    sql  ="SELECT PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,CURR_ID FROM (SELECT A.PROJ_ID,A.PROJ_SHORT_NAME,A.PROJ_NAME,A.CURR_ID FROM CM_PROJ A INNER JOIN CM_PROJRCNT B ON A.PROJ_ID=B.PROJ_ID WHERE B.USER_ID='"+UserId+"' AND A.PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID='"+UserId+"' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID='"+UserId+"')) ORDER BY ACCESS_DATE DESC) TT  limit 1 ";
    	    Map<String, Object> projMap =  baseJdbcDao.queryMap(sql);
    	    String projId="";  
    	    String projShortName="";
    	    String projName="";
    	    if(DataTypeUtil.validate(projMap)){
    	    	projId = DataTypeUtil.formatDbColumn(projMap.get("PROJ_ID"));
    	    	projShortName = DataTypeUtil.formatDbColumn(projMap.get("PROJ_SHORT_NAME"));
    	    	projName = DataTypeUtil.formatDbColumn(projMap.get("PROJ_NAME"));
        	    sql  ="SELECT DATACTR_CODE FROM CM_PROFCTR A JOIN CM_PROFILE B ON A.PROFILE_ID = B.PROFILE_ID  WHERE ((A.PROFILE_ID = (SELECT PROFILE_ID FROM CM_USERS WHERE USER_ID='" + UserId + "') AND B.PROF_TYPE='0')" + "OR (A.PROFILE_ID = " + "(SELECT PROFILE_ID FROM CM_USERPROF WHERE USER_ID = '" + UserId + "' AND PROJ_ID = '" + projMap.get("PROJ_ID").toString() + "') AND B.PROF_TYPE='1')) AND A.DATACTR_CODE IN ('SYS_PROJ.QYXM.edit','DS_GCGK.add_del','DS_GCGK.edit','DS_ADD.edit','DS_EDIT.edit','SM_ADD.edit','SM_EDIT.edit','CC_ADD.edit','CC_EDIT.edit','QC_ADD.edit','QC_EDIT.edit','CC_UPLOAD.edit','DS_UPLOAD.edit','QC_UPLOAD.edit','SM_UPLOAD.edit','DS_FX_PICTABLE.edit')";           
    	    }else{
    	    	sql = "SELECT DATACTR_CODE FROM CM_PROFCTR A JOIN CM_PROFILE B ON A.PROFILE_ID = B.PROFILE_ID  WHERE ((A.PROFILE_ID = (SELECT PROFILE_ID FROM CM_USERS WHERE USER_ID='" + UserId + "') AND B.PROF_TYPE='0')) AND A.DATACTR_CODE IN ('SYS_PROJ.QYXM.edit','DS_GCGK.add_del','DS_GCGK.edit','DS_ADD.edit','DS_EDIT.edit','SM_ADD.edit','SM_EDIT.edit','CC_ADD.edit','CC_EDIT.edit','QC_ADD.edit','QC_EDIT.edit','CC_UPLOAD.edit','DS_UPLOAD.edit','QC_UPLOAD.edit','SM_UPLOAD.edit','DS_FX_PICTABLE.edit')";
    	    }
    	    List<Map<String, Object>> Datactrlist =  baseJdbcDao.queryListMap(sql);
    	    resObj.put("DATACTRLIST", Datactrlist);
    	      userMap.put("PROJ_ID", projId);
    	      userMap.put("PROJ_SHORT_NAME",projShortName);
    	      userMap.put("PROJ_NAME", projName);
	          resObj.put("RESULT", "2");
	          resObj.putAll(userMap);
	          sql = "SELECT  SIGN_COUNT FROM HR_TIMESET WHERE STATUS='1'";
	          String ATTENDANCE = DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql));
	          if(!DataTypeUtil.validate(ATTENDANCE)){
	        	  ATTENDANCE="1";
	          }
	          resObj.put("ATTENDANCE", ATTENDANCE);
	          sql = "SELECT A.PARENT_MODULE_CODE , A.MODULE_CODE, A.MODULE_NAME, SEQ_NUM "+
				"FROM "+
				"CM_MODULE A  "+
				"WHERE "+
				"PARENT_MODULE_CODE = '0'   AND "+
				"ENABLED = '1'  "+
				"AND A.MODULE_CODE IN  "+
				"(SELECT  "+
				"B.MODULE_CODE  "+
				"FROM "+
				"CM_USERMDL B  "+
				"WHERE B.USER_ID = '"+UserId+"'  "+
				"OR B.ROLE_ID IN  "+
				"(SELECT  "+
				"C.ROLE_ID  "+
				"FROM "+
				"CM_RLUSER C  "+
				"WHERE C.USER_ID = '"+UserId+"'))  "+
				"AND  MODULE_CODE IN ('DS', 'SM', 'CC', 'QC', 'KM')  "+
				"UNION  "+
				"SELECT  PARENT_MODULE_CODE, MODULE_CODE, MODULE_NAME, SEQ_NUM   "+
				"FROM  CM_MODULE  WHERE MODULE_CODE IN (						 "+
				"SELECT  "+
				"A.PARENT_MODULE_CODE  "+
				"FROM "+
				"CM_MODULE A  "+
				"WHERE "+
				"ENABLED = '1'  "+
				"AND A.MODULE_CODE IN  "+
				"(SELECT  "+
				"B.MODULE_CODE  "+
				"FROM "+
				"CM_USERMDL B  "+
				"WHERE B.USER_ID = '"+UserId+"'  "+
				"OR B.ROLE_ID IN  "+
				"(SELECT  "+
				"C.ROLE_ID  "+
				"FROM "+
				"CM_RLUSER C  "+
				"WHERE C.USER_ID = '"+UserId+"'))  "+
				"AND  MODULE_CODE IN ('CC_CLAIM', 'DS_GCGK', 'DS_GTORDER', 'DS_GTWORKS', 'DS_FX_PICTABLE','CC_ABTEST','DS_QYSUMY','KM_PUBDOC','KM_PROJDOC','QC_QWBS','SM_SWBS') "+ 
				")   "+
				"UNION  "+
				"SELECT  A.PARENT_MODULE_CODE , A.MODULE_CODE, A.MODULE_NAME , SEQ_NUM "+
				"FROM "+
				"CM_MODULE A  "+
				"WHERE "+
				"ENABLED = '1'  "+
				"AND A.MODULE_CODE IN  "+
				"(SELECT  "+
				"B.MODULE_CODE  "+
				"FROM "+
				"CM_USERMDL B  "+
				"WHERE B.USER_ID = '"+UserId+"'  "+
				"OR B.ROLE_ID IN  "+
				"(SELECT  "+
				"C.ROLE_ID  "+
				"FROM "+
				"CM_RLUSER C  "+
				"WHERE C.USER_ID = '"+UserId+"'))  "+
				"AND  MODULE_CODE IN ('CC_CLAIM', 'DS_GCGK', 'DS_GTORDER', 'DS_GTWORKS', 'DS_FX_PICTABLE','CC_ABTEST','DS_QYSUMY','KM_PUBDOC','KM_PROJDOC','QC_QWBS','SM_SWBS') ";
				
               List<Map<String, Object>> modulelist =  baseJdbcDao.queryListMap(sql);
               resObj.put("MODULELIST", modulelist);
          }else{
        	  resObj.put("RESULT", "0");
          }
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		  System.out.println("接口结束"); 		
	}
	

	@ResponseBody
	@RequestMapping(value = "/api/SetChannelId.do")
	public void SetChannelId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String device_id = Base64Encrypt.decode(jsonObj.getString("device_id")); 
			String platform = Base64Encrypt.decode(jsonObj.getString("platform"));
			String channelid =  Base64Encrypt.decode(jsonObj.getString("channelId")); 
			
			String sql = "SELECT COUNT(WID) NUM FROM SYS_APP_MSG_SET WHERE DEVICE_ID = '"+device_id+"'";
        	if(baseJdbcDao.getCount(sql)>0){
        		sql = "UPDATE  SYS_APP_MSG_SET SET USER_ID = '"+userId+"',CREATED_TIME = NOW(),CHANNEL_ID='"+channelid+"' , PLATFORM = '"+platform+"' WHERE DEVICE_ID = '"+device_id+"'   ";
        		baseJdbcDao.update(sql);
        	}else{
        		sql = "INSERT INTO SYS_APP_MSG_SET(WID,ID,USER_ID,CHANNEL_ID,PLATFORM,DEVICE_ID)VALUES('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+userId+"','"+channelid+"','"+platform+"','"+device_id+"')";
        		baseJdbcDao.insert(sql);
        	} 
        	
	    	resObj.put("RESULT", "1");
    
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/DeleteChannelId.do")
	public void DeleteChannelId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String device_id = Base64Encrypt.decode(jsonObj.getString("device_id")); 
			String platform = Base64Encrypt.decode(jsonObj.getString("platform"));
			String channelid =  Base64Encrypt.decode(jsonObj.getString("channelId")); 
    		String sql = "DELETE FROM  SYS_APP_MSG_SET WHERE USER_ID ='"+userId+"' AND CHANNEL_ID ='"+channelid+"' AND  DEVICE_ID = '"+device_id+"' ";
    		baseJdbcDao.delete(sql);
    		sql = "DELETE FROM  SYS_APP_MSG_SET WHERE USER_ID ='"+userId+"' AND CHANNEL_ID ='"+channelid+"'";
    		baseJdbcDao.delete(sql);
	    	resObj.put("RESULT", "1");    
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/GetUserInfo.do")
	public void GetUserInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
            String sql=" SELECT  USER_ID ,USER_NAME,ACTUAL_NAME,SEX,IFNULL(MOBIL_NO,'')MOBIL_NO,IFNULL(ADD_HOME,'')ADD_HOME,IFNULL(PASSWORD,'')PASSWORD FROM CM_USERS WHERE USER_ID = '"+userId+"'";
            resObj  = baseJdbcDao.queryMap(sql);		  
	    	resObj.put("RESULT", "1");
    
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	@ResponseBody
	@RequestMapping(value = "/api/UpdateUserInfo.do")
	public void UpdateUserInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String columnId =  Base64Encrypt.decode(jsonObj.getString("columnId")); 
			String columnValue =  Base64Encrypt.decode(jsonObj.getString("columnValue")); 
			String platform = Base64Encrypt.decode(jsonObj.getString("platform"));
			  if(platform.equals("2")&&DataTypeUtil.validate(columnValue)&&columnId.equals("PASSWORD")){
				  columnValue = DESEncrypt.desEncode(columnValue);       	//ios密码加密	  
	       	  } 
			
            String sql=" UPDATE  CM_USERS SET "+columnId+" = '"+columnValue+"' WHERE USER_ID = '"+userId+"'";
            baseJdbcDao.update(sql);		  
	    	resObj.put("RESULT", "1");
    
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	@ResponseBody
	@RequestMapping(value = "/api/AddAppLog.do")
	public void AddAppLog(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String machine =  Base64Encrypt.decode(jsonObj.getString("machine")); 
			String content =  Base64Encrypt.decode(jsonObj.getString("content")); 
			String device_id = Base64Encrypt.decode(jsonObj.getString("device_id")); 
            String sql="INSERT INTO SYS_APP_LOG(WID,LOG_ID,USER_ID,MACHINE,CONTENT,DEVICE_ID,TYPE)VALUES('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+userId+"','"+machine+"','"+content+"','"+device_id+"','0')";
            baseJdbcDao.insert(sql);		  
	    	resObj.put("RESULT", "1");
    
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/AddAppErrorLog.do")
	public void AddAppErrorLog(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String machine =  Base64Encrypt.decode(jsonObj.getString("machine")); 
			String content =  Base64Encrypt.decode(jsonObj.getString("content")); 
			String device_id = Base64Encrypt.decode(jsonObj.getString("device_id")); 
            String sql="INSERT INTO SYS_APP_LOG(WID,LOG_ID,USER_ID,MACHINE,CONTENT,DEVICE_ID,TYPE)VALUES('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+userId+"','"+machine+"','"+content+"','"+device_id+"','1')";
            baseJdbcDao.insert(sql);		  
	    	resObj.put("RESULT", "1");
    
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/GetMsgSet.do")
	public void GetMsgSet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String channelId =  Base64Encrypt.decode(jsonObj.getString("channelId")); 
            String sql="SELECT ID ,BEGIN_TIME,END_TIME,TIME_SET_ENABLED FROM SYS_APP_MSG_SET WHERE USER_ID = '"+userId+"' AND CHANNEL_ID='"+channelId+"'";
            resObj = baseJdbcDao.queryMap(sql);		  
	    	resObj.put("RESULT", "1");
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/AddMsgSet.do")
	public void AddMsgSet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String channelId =  Base64Encrypt.decode(jsonObj.getString("channelId")); 
			String beginTime =  Base64Encrypt.decode(jsonObj.getString("beginTime")); 
			String endTime =  Base64Encrypt.decode(jsonObj.getString("endTime")); 
			if(baseJdbcDao.getCount("SELECT COUNT(ID) NUM FROM SYS_APP_MSG_SET WHERE USER_ID = '"+userId+"' AND CHANNEL_ID='"+channelId+"'")==0){
	            String sql="INSERT INTO SYS_APP_MSG_SET(WID,ID,USER_ID,CHANNEL_ID,BEGIN_TIME,END_TIME,TIME_SET_ENABLED)VALUES('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+userId+"','"+channelId+"','"+beginTime+"','"+endTime+"','1')";
	            baseJdbcDao.insert(sql);	
			}else{
				 String sql="UPDATE SYS_APP_MSG_SET SET TIME_SET_ENABLED='1',BEGIN_TIME='"+beginTime+"',END_TIME='"+endTime+"' WHERE USER_ID='"+userId+"' AND CHANNEL_ID = '"+channelId+"'";
				 baseJdbcDao.update(sql);		 
			}
            	  
	    	resObj.put("RESULT", "1");
    
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	 
	@ResponseBody
	@RequestMapping(value = "/api/DeleteMsgSet.do")
	public void DeleteMsgSet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String channelId =  Base64Encrypt.decode(jsonObj.getString("channelId")); 
            String sql="UPDATE SYS_APP_MSG_SET SET TIME_SET_ENABLED='0' WHERE USER_ID='"+userId+"' AND CHANNEL_ID = '"+channelId+"'";
            baseJdbcDao.update(sql);		  
	    	resObj.put("RESULT", "1");
    
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	@ResponseBody
	@RequestMapping(value = "/api/UpdateVersion.do")
	public void UpdateVersion(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String version =  Base64Encrypt.decode(jsonObj.getString("version")); 
			System.out.println(version);
			String platform = Base64Encrypt.decode(jsonObj.getString("platform"));
			
			String remotePath = "";
			if(platform.equals("1")){
				remotePath ="http://www.gpmis.com:12345/version/android.txt";
			}else{
				remotePath ="http://www.gpmis.com:12345/version/ios.txt";
			}
		 
			java.net.URL url = new java.net.URL(remotePath);
			java.net.URLConnection conn = url.openConnection();
			java.io.InputStream in = conn.getInputStream();
			 byte b[] = new byte[1024];   
		        int len = 0;   
		        int temp=0;          //所有读取的内容都使用temp接收   
			 while((temp=in.read())!=-1){    //当没有读取完时，继续读取   
		            b[len]=(byte)temp;   
		            len++;   
		        }   
		        in.close();   
		       String name = new String(b,0,len).trim();
		       if(!name.equals(version)){
		    	   System.out.println(name);
			       String FirstName = name.substring(0,name.lastIndexOf("."));
			       String inputName = version.substring(0,version.lastIndexOf("."));
			       if(FirstName.equals(inputName)){
			    	   String SecName = name.substring(name.lastIndexOf(".")+1);
				       String inputSecName = version.substring(version.lastIndexOf(".")+1);			    	   
				       if(Integer.parseInt(SecName)>Integer.parseInt(inputSecName)){				       
				    		resObj.put("RESULT", "2");
				    		resObj.put("URL", "http://www.gpmis.com:12345/version/GPMIS"+name+".apk");
				       }else{
				    	     resObj.put("RESULT", "1");
				       }
			       }else{ 
			    	   String[] inp = version.split("\\."); 
			    	   String[] service = name.split("\\.");	
			    	   if(Integer.parseInt(service[0])>Integer.parseInt(inp[0]) || (Integer.parseInt(service[0])==Integer.parseInt(inp[0]) && Integer.parseInt(service[1])>Integer.parseInt(inp[1]) )){
			    		   resObj.put("RESULT", "3");
				    		resObj.put("URL", "http://www.gpmis.com:12345/version/GPMIS"+name+".apk");
			    	   }else{
			    		   resObj.put("RESULT", "1");
			    	   }			    	   
			       }
		       }else{
		    	   resObj.put("RESULT", "1");
		       }
			
	    
    
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			resObj.put("MSG", e.getMessage());
			e.printStackTrace();
		
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	

	@ResponseBody
	@RequestMapping(value = "/api/GetServerTime.do")
	public void GetServerTime(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		     Date date = new Date();
		     String datetime = format.format(date);
		     resObj.put("SERVERTIME", datetime);
            resObj.put("RESULT", "1");
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
	}
}
