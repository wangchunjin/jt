package com.epmis.app.controller;

import java.io.IOException;
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

import com.epmis.co.service.CoNewService;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.ProjService;
import com.epmis.sys.util.Base64Encrypt;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;

@Controller
public class NewsNotify {
	@Autowired
    @Qualifier("baseJdbcDao")
    private BaseJdbcDao baseJdbcDao;
	
	@Autowired
    private CoNewService coNewService;
	
	@ResponseBody
	@RequestMapping(value = "/api/GetNewsList.do")
	public void GetNewsList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String start =  Base64Encrypt.decode(jsonObj.getString("start"));
			String pageSize =  Base64Encrypt.decode(jsonObj.getString("pageSize"));
			int begin = Integer.parseInt(start);  			
			begin = begin-1;

			String sql = " SELECT COUNT(WID) NUM FROM CO_NEW WHERE  STATUS = 'APPROVED' ";
			int count = baseJdbcDao.getCount(sql);
			int totalPage = count/Integer.parseInt(pageSize);
			if(count%Integer.parseInt(pageSize)>0){
				totalPage=totalPage+1;
			}
			int currentPage = Integer.parseInt(start)/Integer.parseInt(pageSize)+1;

			
			sql = "SELECT  NEW_ID,TITLE,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d %H:%i:%s') CREATED_DATE, (SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  CREATED_BY)CREATED_BY, (SELECT CODE_NAME FROM CM_CODE D WHERE D.ID = TYPE) TYPE FROM CO_NEW WHERE  STATUS = 'APPROVED'  ORDER BY CO_NEW.CREATED_DATE DESC  LIMIT "+begin+","+pageSize;		
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
	@RequestMapping(value = "/api/ShowNewsDetail.do")
	public void ShowNewsDetail(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String newId =  Base64Encrypt.decode(jsonObj.getString("newId"));
			String isFirst = Base64Encrypt.decode(jsonObj.getString("isFirst"));
			String sql="";
		    if(isFirst.equals("1")){
				sql = " UPDATE CO_NEW SET CLICK_COUNT = CLICK_COUNT+1 WHERE NEW_ID = '"+newId+"'";
				baseJdbcDao.update(sql);
		    }
		    sql = "SELECT NEW_ID,TITLE,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d %H:%i:%s') CREATED_DATE, (SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  CREATED_BY)CREATED_BY, (SELECT CODE_NAME FROM CM_CODE D WHERE D.ID = TYPE) TYPE,CONTENT,CLICK_COUNT FROM CO_NEW WHERE NEW_ID = '"+newId+"'";			
			resObj =  baseJdbcDao.queryMap(sql);
			resObj.put("RESULT", "1");	
		}catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/GetNewsCommentList.do")
	public void GetNewsCommentLis(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
		    String linkId =  Base64Encrypt.decode(jsonObj.getString("newId"));
			String start =  Base64Encrypt.decode(jsonObj.getString("start"));
			String pageSize =  Base64Encrypt.decode(jsonObj.getString("pageSize"));
			int begin = Integer.parseInt(start);  			
			begin = begin-1;

			String sql = " SELECT COUNT(WID) NUM FROM CO_COMMENT WHERE  LINK_ID='"+linkId+"' AND  TYPE = 'NEWS' ";
			int count = baseJdbcDao.getCount(sql);
			int totalPage = count/Integer.parseInt(pageSize);
			if(count%Integer.parseInt(pageSize)>0){
				totalPage=totalPage+1;
			}
			int currentPage = Integer.parseInt(start)/Integer.parseInt(pageSize)+1;			
			sql = "SELECT COMMENT_ID ,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d %H:%i:%s') CREATED_DATE, (SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  O.USER_ID)CREATED_BY  ,O.USER_ID,CONTENT FROM CO_COMMENT O WHERE LINK_ID = '"+linkId+"' AND TYPE='NEWS' ORDER BY CREATED_DATE DESC    LIMIT "+begin+","+pageSize;		
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
	@RequestMapping(value = "/api/AddNewsComment.do")
	public void AddNewsComment(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String linkId =  Base64Encrypt.decode(jsonObj.getString("newId")); 
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String content =  Base64Encrypt.decode(jsonObj.getString("content")); 			
			coNewService.AddComment(userId, linkId, content, "NEWS");
			resObj.put("RESULT", "1");	
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/DeleteNewsComment.do")
	public void DeleteNewsComment(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String commentId =  Base64Encrypt.decode(jsonObj.getString("commentId")); 		
			coNewService.DeleteComment(commentId, "NEWS");
			resObj.put("RESULT", "1");	
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
 
	@ResponseBody
	@RequestMapping(value = "/api/GetNotifyList.do")
	public void GetNotifyList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String start =  Base64Encrypt.decode(jsonObj.getString("start"));
			String pageSize =  Base64Encrypt.decode(jsonObj.getString("pageSize"));
			int begin = Integer.parseInt(start);  			
			begin = begin-1;

			String sql = " SELECT COUNT(WID)NUM  FROM CO_NOTIFY WHERE STATUS = 'APPROVED'  AND  (LOCATE('"+userId+"', TO_USERS)>0 OR TO_USERS IS NULL OR TO_USERS='') AND  DATE_FORMAT(END_DATE,'%Y-%m-%d') >= DATE_FORMAT(now(),'%Y-%m-%d') ORDER BY CO_NOTIFY.CREATED_DATE DESC ";
			int count = baseJdbcDao.getCount(sql);
			int totalPage = count/Integer.parseInt(pageSize);
			if(count%Integer.parseInt(pageSize)>0){
				totalPage=totalPage+1;
			}
			int currentPage = Integer.parseInt(start)/Integer.parseInt(pageSize)+1;

			
			sql = "SELECT   NOTIFY_ID, TITLE, DATE_FORMAT(CREATED_DATE, '%Y-%m-%d %H:%i:%s') CREATED_DATE, DATE_FORMAT(BEGIN_DATE, '%Y-%m-%d') BEGIN_DATE, DATE_FORMAT(END_DATE, '%Y-%m-%d') END_DATE, DATE_FORMAT(END_DATE, '%Y-%m-%d') < DATE_FORMAT(NOW(), '%Y-%m-%d') IFCLOSE, (SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID = CREATED_BY) CREATED_BY  FROM CO_NOTIFY WHERE STATUS = 'APPROVED'  AND  (LOCATE('"+userId+"', TO_USERS)>0 OR TO_USERS IS NULL OR TO_USERS='') AND  DATE_FORMAT(END_DATE,'%Y-%m-%d') >= DATE_FORMAT(now(),'%Y-%m-%d') ORDER BY CO_NOTIFY.CREATED_DATE DESC   LIMIT "+begin+","+pageSize;		
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
	@RequestMapping(value = "/api/ShowNotifyDetail.do")
	public void ShowNotifyDetail(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String notifyId =  Base64Encrypt.decode(jsonObj.getString("notifyId")); 
			String isFirst = Base64Encrypt.decode(jsonObj.getString("isFirst"));
			String sql="";
		    if(isFirst.equals("1")){
		    	sql = " UPDATE CO_NOTIFY SET CLICK_COUNT = CLICK_COUNT+1 WHERE NOTIFY_ID = '"+notifyId+"'";
				baseJdbcDao.update(sql);
		    }
		    sql = "SELECT   NOTIFY_ID, TITLE, DATE_FORMAT(CREATED_DATE, '%Y-%m-%d %H:%i:%s') CREATED_DATE, DATE_FORMAT(BEGIN_DATE, '%Y-%m-%d') BEGIN_DATE, DATE_FORMAT(END_DATE, '%Y-%m-%d') END_DATE, DATE_FORMAT(END_DATE, '%Y-%m-%d') < DATE_FORMAT(NOW(), '%Y-%m-%d') IFCLOSE, (SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID = CREATED_BY) CREATED_BY,CLICK_COUNT,CONTENT  FROM CO_NOTIFY WHERE NOTIFY_ID = '"+notifyId+"'";
			resObj =  baseJdbcDao.queryMap(sql);
			resObj.put("RESULT", "1");	
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/GetNotifyCommentList.do")
	public void GetNotifyCommentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
		    String linkId =  Base64Encrypt.decode(jsonObj.getString("notifyId"));
			String start =  Base64Encrypt.decode(jsonObj.getString("start"));
			String pageSize =  Base64Encrypt.decode(jsonObj.getString("pageSize"));
			int begin = Integer.parseInt(start);  			
			begin = begin-1;

			String sql = " SELECT COUNT(WID) NUM FROM CO_COMMENT WHERE  LINK_ID='"+linkId+"' AND  TYPE = 'NOTIFY' ";
			int count = baseJdbcDao.getCount(sql);
			int totalPage = count/Integer.parseInt(pageSize);
			if(count%Integer.parseInt(pageSize)>0){
				totalPage=totalPage+1;
			}
			int currentPage = Integer.parseInt(start)/Integer.parseInt(pageSize)+1;			
			sql = "SELECT COMMENT_ID ,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d %H:%i:%s') CREATED_DATE, (SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  O.USER_ID)CREATED_BY  ,O.USER_ID,CONTENT FROM CO_COMMENT O WHERE LINK_ID = '"+linkId+"' AND TYPE='NOTIFY' ORDER BY CREATED_DATE DESC    LIMIT "+begin+","+pageSize;		
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
	@RequestMapping(value = "/api/AddNotifyComment.do")
	public void AddNotifyComment(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String linkId =  Base64Encrypt.decode(jsonObj.getString("notifyId")); 
			String userId =  Base64Encrypt.decode(jsonObj.getString("userId")); 
			String content =  Base64Encrypt.decode(jsonObj.getString("content")); 			
			coNewService.AddComment(userId, linkId, content, "NOTIFY");
			resObj.put("RESULT", "1");	
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/DeleteNotifyComment.do")
	public void DeleteNotifyComment(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try {
		    String jsonString=  request.getParameter("jsonString");   
		    JSONObject jsonObj = JSONObject.fromObject(jsonString);
			String commentId =  Base64Encrypt.decode(jsonObj.getString("commentId")); 		
			coNewService.DeleteComment(commentId, "NOTIFY");
			resObj.put("RESULT", "1");	
		} catch (Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		   
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
 
}
