package com.epmis.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;







import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epmis.app.service.UserHealthService;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.WriterJsonArray;

@Controller
public class UserHealth {
	    @Autowired
	    @Qualifier("baseJdbcDao")
	    private BaseJdbcDao baseJdbcDao;
		@Autowired
		@Qualifier("userHealthService")
		private UserHealthService userHealthService;
		
	    /**
	     * 获取token
	     * @param request
	     * @param response
	     * @throws IOException
	     */
	  
	    
	    @ResponseBody	
		@RequestMapping(value = "/api/user/queryHealth.do")
		public void queryHealth(HttpServletRequest request,HttpServletResponse response) throws IOException {

			Map<String, Object> resObj = new HashMap<String, Object>();
			
			try{

				String userid =  request.getParameter("userid");  
			



				Map<String, Object> HealthMap=  userHealthService.queryHealth(userid);

					if(DataTypeUtil.validate(HealthMap)){
						resObj.put("RESULT", "0");
						resObj.putAll(HealthMap);
						
				  	}else{
						resObj.put("RESULT", "没有添加记录");
					}
				} catch (Exception e) {
					resObj.put("RESULT", "10010");
					e.printStackTrace();
				}	

				WriterJsonArray.AppWriterJSONArray(resObj,response);
			}
				
	    
	    
	    
	    
	    @ResponseBody	
		@RequestMapping(value = "/api/user/addHealth.do")
		public void addHealth(HttpServletRequest request,HttpServletResponse response) throws IOException {

			Map<String, Object> resObj = new HashMap<String, Object>();
			
			try{
				String jsonString =  request.getParameter("jsonString");  
				JSONObject jsonObj = JSONObject.fromObject(jsonString);
				String userid =  jsonObj.getString("userid");
				String bloodpress =  jsonObj.getString("bloodpress");
				String bloodsugar  =  jsonObj.getString("bloodsugar");
				String bloodrt   =  jsonObj.getString("bloodrt");
				String temperature   =  jsonObj.getString("temperature");
				String weight  =  jsonObj.getString("weight");
				String height   =  jsonObj.getString("height");
			boolean flag	=	userHealthService.addHealth(userid, bloodpress, bloodsugar, bloodrt, temperature, weight, height);

					if(flag=true){
						resObj.put("RESULT", "添加记录成功");
							
				  	}else{
						resObj.put("RESULT", "没有添加记录");
					}
				} catch (Exception e) {
					resObj.put("RESULT", "10010");
					e.printStackTrace();
				}	

				WriterJsonArray.AppWriterJSONArray(resObj,response);
			}
	    

@ResponseBody	
@RequestMapping(value = "/api/user/showrelations.do")
public void showrelations(HttpServletRequest request,HttpServletResponse response) throws IOException {

	Map<String, Object> resObj = new HashMap<String, Object>();
	
	try{
		String userid =  request.getParameter("userid");  
	
	String sql="SELECT DISTINCT A.RELATIONTYPE, A.OTHERID, B.TELE,B.USERNAME,B.USERID,B.SEX,B.AGE  "
					+ "FROM  RELATIONS A LEFT JOIN USERS B ON   A.OWNID='"+userid+"'"
					+ "AND OTHERID=USERID";
	List<Map<String, Object>> 	RelationsList	=baseJdbcDao.queryListMap(sql);
			
				if( DataTypeUtil.validate(RelationsList)){
					resObj.put("RESULT", "0");
					resObj.put("RelationsList", RelationsList);
					
							}else{
					resObj.put("RESULT", "没有添加记录");
						}
			} catch (Exception e) {
				resObj.put("RESULT", "10010");
				e.printStackTrace();
			}	
			
			WriterJsonArray.AppWriterJSONArray(resObj,response);
		}

	    
}
		
		
	
