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

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.Base64Encrypt;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.WriterJsonArray;

@Controller
public class Vnmt {
	 @Autowired
	    @Qualifier("baseJdbcDao")
	    private BaseJdbcDao baseJdbcDao;
	 
	 
	 @ResponseBody
		@RequestMapping(value = "/api/GetVnmtList.do")
		public void GetVnmtListId(HttpServletRequest request,HttpServletResponse response) throws IOException {
			Map<String, Object> resObj = new HashMap<String, Object>();
			try {
			    String jsonString=  request.getParameter("jsonString");   
			    JSONObject jsonObj = JSONObject.fromObject(jsonString);
				String projId =  Base64Encrypt.decode(jsonObj.getString("projId")); 
				String start =  Base64Encrypt.decode(jsonObj.getString("start"));
				String pageSize =  Base64Encrypt.decode(jsonObj.getString("pageSize"));
				
           
             int begin = Integer.parseInt(start);  			
				begin = begin-1;
	         String sql="SELECT  COUNT(WID) NUM  FROM CM_PROJ_JOIN A WHERE PROJ_ID = '"+projId+"' ORDER BY COMPANY_NAME";	             
             int count = baseJdbcDao.getCount(sql);
 			int totalPage = count/Integer.parseInt(pageSize);
 			if(count%Integer.parseInt(pageSize)>0){
 				totalPage=totalPage+1;
 			}
 			int currentPage = Integer.parseInt(start)/Integer.parseInt(pageSize)+1;
             
 		   sql=" SELECT  VNMT_ID, COMPANY_NAME, ROLE_ID, DESCRIPTION, (SELECT COMPANY_NAME FROM CM_PROJ_JOIN B WHERE B.VNMT_ID = A.OTHER_VNMT_ID) OTHER_VNMT_ID FROM CM_PROJ_JOIN A WHERE PROJ_ID = '"+projId+"' ORDER BY COMPANY_NAME   LIMIT "+begin+","+pageSize;
          
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
		@RequestMapping(value = "/api/GetVnmtInfo.do")
		public void GetVnmtInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
			Map<String, Object> resObj = new HashMap<String, Object>();
			try {
			    String jsonString=  request.getParameter("jsonString");   
			    JSONObject jsonObj = JSONObject.fromObject(jsonString);
				String vnmtId =  Base64Encrypt.decode(jsonObj.getString("vnmtId")); 
				String projId =  Base64Encrypt.decode(jsonObj.getString("projId"));
				String sql =  "SELECT  VNMT_ID, COMPANY_ABBREV, COMPANY_NAME, ROLE_ID, DESCRIPTION, (SELECT COMPANY_NAME FROM CM_PROJ_JOIN B WHERE B.VNMT_ID = A.OTHER_VNMT_ID  AND B.PROJ_ID = '"+projId+"' ) OTHER_VNMT_ID , (SELECT NAME FROM CM_VNDT WHERE VNDT_ID = DEFAULT_LINKMAN) AS DEFAULT_LINKMAN FROM CM_PROJ_JOIN A WHERE VNMT_ID = '"+vnmtId+"' ORDER BY COMPANY_NAME ";
                resObj = baseJdbcDao.queryMap(sql);
                resObj.put("RESULT", "1");
			} catch (Exception e) {
				resObj.put("RESULT", "0");
				e.printStackTrace();
			}		   
			WriterJsonArray.AppWriterJSONArray(resObj,response);
			
		}
	    @ResponseBody
		@RequestMapping(value = "/api/GetVnmtOther.do")
		public void GetVnmtOther(HttpServletRequest request,HttpServletResponse response) throws IOException {
			Map<String, Object> resObj = new HashMap<String, Object>();
			try {
			    String jsonString=  request.getParameter("jsonString");   
			    JSONObject jsonObj = JSONObject.fromObject(jsonString);
				String vnmtId =  Base64Encrypt.decode(jsonObj.getString("vnmtId")); 
				String projId =  Base64Encrypt.decode(jsonObj.getString("projId"));
				String sql =  "SELECT  VNMT_ID,REGISTER_FUND, IFNULL((SELECT CODE_NAME FROM CM_CODE C WHERE C.CODE_TYPE='TRADE' AND C.ID=TRADE) ,'') TRADE,IFNULL((SELECT CODE_NAME FROM CM_CODE C WHERE C.CODE_TYPE='INTG_GRADE' AND C.ID=INTG_GRADE) ,'') INTG_GRADE,IFNULL((SELECT CODE_NAME FROM CM_CODE C WHERE C.CODE_TYPE='ESTM_GRADE' AND C.ID=ESTM_GRADE) ,'') ESTM_GRADE,OFFICE_PHONE,FAX FROM CM_VNMT A WHERE VNMT_ID = '"+vnmtId+"'  ";
                resObj = baseJdbcDao.queryMap(sql);
                resObj.put("RESULT", "1");
			} catch (Exception e) {
				resObj.put("RESULT", "0");
				e.printStackTrace();
			}		   
			WriterJsonArray.AppWriterJSONArray(resObj,response);
			
		}
	    @ResponseBody
		@RequestMapping(value = "/api/GetVndtList.do")
		public void GetVndtList(HttpServletRequest request,HttpServletResponse response) throws IOException {
			Map<String, Object> resObj = new HashMap<String, Object>();
			try {
			    String jsonString=  request.getParameter("jsonString");   
			    JSONObject jsonObj = JSONObject.fromObject(jsonString);
				String vnmtId =  Base64Encrypt.decode(jsonObj.getString("vnmtId")); 
				String projId =  Base64Encrypt.decode(jsonObj.getString("projId"));
				String sql =  " SELECT VNDT_ID,NAME,OFFICE_PHONE,MOBILE_PHONE,MAIL_ADDRESS FROM CM_VNDT WHERE VNMT_ID='"+vnmtId+"' order by NAME ASC ";
				List<Map<String,Object>> list   = baseJdbcDao.queryListMap(sql);
				resObj.put("LIST", list);
                resObj.put("RESULT", "1");
			} catch (Exception e) {
				resObj.put("RESULT", "0");
				e.printStackTrace();
			}		   
			WriterJsonArray.AppWriterJSONArray(resObj,response);
			
		}
	    
	    @ResponseBody
		@RequestMapping(value = "/api/GetVnmtCatgList.do")
		public void GetVnmtCatgList(HttpServletRequest request,HttpServletResponse response) throws IOException {
			Map<String, Object> resObj = new HashMap<String, Object>();
			try {
			    String jsonString=  request.getParameter("jsonString");   
			    JSONObject jsonObj = JSONObject.fromObject(jsonString);
				String vnmtId =  Base64Encrypt.decode(jsonObj.getString("vnmtId")); 
				String projId =  Base64Encrypt.decode(jsonObj.getString("projId"));
				String sql =  "SELECT A.CATG_ID,B.CATG_SHORT_NAME,B.CATG_NAME,C.CATG_TYPE FROM CM_CATBASE A LEFT JOIN CM_CATVAL B ON A.CATG_ID=B.CATG_ID LEFT JOIN CM_CATTYPE C ON B.CATG_TYPE_ID=C.CATG_TYPE_ID WHERE A.BASE_MASTER_KEY='"+vnmtId+"' ORDER BY CATG_TYPE,CATG_SHORT_NAME";
				List<Map<String,Object>> list   = baseJdbcDao.queryListMap(sql);
				resObj.put("LIST", list);
                resObj.put("RESULT", "1");
			} catch (Exception e) {
				resObj.put("RESULT", "0");
				e.printStackTrace();
			}		   
			WriterJsonArray.AppWriterJSONArray(resObj,response);
			
		}
}
