package com.epmis.app.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epmis.app.vo.Authcode;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.WriterJsonArray;

@Controller 
public class Goods {
	@Autowired
	 @Qualifier("baseJdbcDao")
	 private BaseJdbcDao baseJdbcDao;
	
	//搜索并添加历史数据
	@ResponseBody
	@RequestMapping(value = "/api/user/searchgoods.do")
	public  void searchgoods(HttpServletRequest request,HttpServletResponse response)throws IOException{
		Map<String, Object> resObj = new HashMap<String, Object>();
		try{
		/*String jsonString=  request.getParameter("jsonString");
		JSONObject jsonObj = JSONObject.fromObject(jsonString);*/
		String userid=request.getParameter("userid");
	    String commoditys= request.getParameter("commoditys");
	   String sql=" SELECT GOODSNAME FROM GOODS WHERE GOODSNAME LIKE '%"+commoditys+"%' LIMIT 5";
	   Map<String , Object> map =baseJdbcDao.queryMap(sql);
	   if(DataTypeUtil.validate(map)){
				String sql2="SELECT A.GOODSNAME,A.GOODSTYPE,A.GOODSPRICE , B.LINKADDRESS,B.PICTURETYPE,C.COMMENTTOTALSCORE "
						+ "FROM GOODS A,PICTURES B,COMMENTS C "
						+ "WHERE A.GOODSID=B.FID AND A.GOODSID=C.GOODSID AND "
						+ "A.GOODSNAME='"+commoditys+"'";
				Map <String ,Object> goodsMap=baseJdbcDao.queryMap(sql2);
				if(DataTypeUtil.validate(goodsMap)){
					String goodsname=(String)goodsMap.get("GOODSNAME");
					String sql3="INSERT INTO HISTORY (WID,SEARCHID,USERID,SEARCHCONTENT) VALUES ('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+userid+"','"+goodsname+"')";
					this.baseJdbcDao.insert(sql3);
					
				
				}else{
					resObj.put("RESULT", "2");//请输入关键字
					}
			
	   }else{
		   resObj.put("RESULT", "3");//请输入正确关键字
	   }
	   resObj.put("RESULT", "1");
	}catch (Exception e) {
		resObj.put("RESULT", "0");
		e.printStackTrace();
	}		
	WriterJsonArray.AppWriterJSONArray(resObj,response);
	
	}
	//历史搜索
	@ResponseBody
	@RequestMapping(value = "/api/user/HistorySearch.do")
	public void HistorySearch(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try{
		/*String jsonString=  request.getParameter("jsonString");
		JSONObject jsonObj = JSONObject.fromObject(jsonString);*/
		String userid=request.getParameter("userid");
		String historySearch=request.getParameter("historySearch");
		String showsearch="SELECT A.SEARCHCONTENT FROM HISTORY A,USERS B "
									+ "WHERE A.USERID=B.USERID AND A.USERID='"+userid+"' "
									+ "ORDER BY SEARCHTIME DESC LIMIT 2";
					List<Map<String,Object>> searchMap=baseJdbcDao.queryListMap(showsearch);
					if(DataTypeUtil.validate(searchMap)){
						resObj.put("MODULELIST", searchMap);
						
					}
		}catch(Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	//热门搜索
	@ResponseBody
	@RequestMapping(value = "/api/user/HotSearch.do")
	public void HotSearch(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try{
		/*String jsonString=  request.getParameter("jsonString");
		JSONObject jsonObj = JSONObject.fromObject(jsonString);*/
		String userid=request.getParameter("userid");
		String hotSearch=request.getParameter("hotSearch");
		String showHotdoor="SELECT A.SEARCHCONTENT FROM HISTORY A ORDER BY COUNT(SEARCHTIME) DESC LIMIT 2";
		List<Map<String,Object>> hotdoorMap=baseJdbcDao.queryListMap(showHotdoor);
			if(DataTypeUtil.validate(hotdoorMap)){
						resObj.put("MODULELIST", hotdoorMap);
						
			}
		}catch(Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	//根据类型搜索商品或服务
	@ResponseBody
	@RequestMapping(value = "/api/user/searchgood.do")
	public void registered(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try{
		/*String jsonString=  request.getParameter("jsonString");
		JSONObject jsonObj = JSONObject.fromObject(jsonString);*/
		String goodsType=request.getParameter("goodsType");
		String sqltype="SELECT GOODSTYPE FROM GOODS WHERE GOODSTYPE='"+goodsType+"' LIMIT 4";
		List<Map<String , Object>> typeMap=baseJdbcDao.queryListMap(sqltype);
		if(DataTypeUtil.validate(typeMap)){
		String sql="SELECT A.GOODSNAME,A.GOODSTYPE,A.GOODSPRICE , B.ADDRESS,B.LINKADDRESS,B.PICTURETYPE,C.COMMENTTOTALSCORE "
				+ "FROM GOODS A,PICTURES B,COMMENTS C "
				+ "WHERE A.GOODSID=B.FID AND A.GOODSID=C.GOODSID "
				+ "AND A.GOODSTYPE LIKE '%"+goodsType+"%'";
		List<Map<String , Object>> searchMap=baseJdbcDao.queryListMap(sql);
		if(DataTypeUtil.validate(searchMap)){
			resObj.put("MODULELIST", searchMap);
			
		}
		}
		}catch(Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	//广告
	@ResponseBody
	@RequestMapping(value = "/api/user/advert.do")
	public void advert(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try{
		/*String jsonString=  request.getParameter("jsonString");
		JSONObject jsonObj = JSONObject.fromObject(jsonString);*/
		String advertType=request.getParameter("advertType");
		String sql1="SELECT ADLOCATION FROM ADLOCATION WHERE ADLOCATION = '"+advertType+"' ";
		Map<String , Object> adType=baseJdbcDao.queryMap(sql1);
		if(DataTypeUtil.validate(adType)){
		String sql="SELECT LINKADDRESS,ADTITLE,ADLOCATION FROM ADLOCATION WHERE ADLOCATION = '"+advertType+"'";
		List<Map<String , Object>> modulelist=baseJdbcDao.queryListMap(sql);
		if(DataTypeUtil.validate(modulelist)){
			resObj.put("MODULELIST", modulelist);
			
		}
		}
		}catch(Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	//足迹
	@ResponseBody
	@RequestMapping(value = "/api/user/footprint.do")
	public void footprint(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try{
		/*String jsonString=  request.getParameter("jsonString");
		JSONObject jsonObj = JSONObject.fromObject(jsonString);*/
		String userid=request.getParameter("userid");
		String footprint=request.getParameter("footprint");
		String sql="SELECT A.SEARCHCONTENT FROM HISTORY A,USERS B "
				+ "WHERE"
				+ "A.USERID=B.USERID AND A.USERID='"+userid+"' ORDER BY SEARCHTIME DESC LIMIT 1";
		List<Map<String , Object>> modulelist=baseJdbcDao.queryListMap(sql);
		if(DataTypeUtil.validate(modulelist)){
			resObj.put("MODULELIST", modulelist);
			
		}
		}catch(Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}		
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		
	}
	//热卖商品
	@ResponseBody
	@RequestMapping(value = "/api/user/hotgoods.do")
	public void hotgoods(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		try{
			
			String hotgoods=request.getParameter("hotgoods");
			String goodssql="SELECT  A.GOODSNAME,A.GOODSTYPE,A.GOODSPRICE ,B.ADDRESS, B.LINKADDRESS,B.PICTURETYPE,C.COMMENTTOTALSCORE FROM GOODS A,PICTURES B,COMMENTS C WHERE A.GOODSID=B.FID AND A.GOODSID=C.GOODSID AND GOODSTYPE LIKE '%"+hotgoods+"%' AND A.GOODSID*RAND()";	
			List<Map<String , Object>> goodsMap=baseJdbcDao.queryListMap(goodssql);
			if(DataTypeUtil.validate(goodsMap)){
				
				String showGoods="SELECT  A.GOODSNAME,A.GOODSTYPE,A.GOODSPRICE ,B.ADDRESS, B.LINKADDRESS,B.PICTURETYPE,C.COMMENTTOTALSCORE "
						+ "FROM GOODS A,PICTURES B,COMMENTS C  WHERE A.GOODSID=B.FID AND A.GOODSID=C.GOODSID AND A.GOODSTYPE='"+hotgoods+"'";
				List<Map<String , Object>> showMap=baseJdbcDao.queryListMap(showGoods);
				if(DataTypeUtil.validate(showMap)){
					resObj.put("RESULT", "1");
					resObj.put("RESULT",showMap);
				}
			}
			
		}catch(Exception e) {
			resObj.put("RESULT", "0");
			e.printStackTrace();
		}
		WriterJsonArray.AppWriterJSONArray(resObj,response);
		}
	//热卖服务
		@ResponseBody
		@RequestMapping(value = "/api/user/hotService.do")
		public void hotService(HttpServletRequest request,HttpServletResponse response) throws IOException {
			Map<String, Object> resObj = new HashMap<String, Object>();
			try{
			/*	String jsonString=  request.getParameter("jsonString");
				JSONObject jsonObj = JSONObject.fromObject(jsonString);*/
				String hotServices=request.getParameter("hotServices");
				String serviceSql="SELECT  A.GOODSNAME,A.GOODSTYPE,A.GOODSPRICE ,B.ADDRESS, B.LINKADDRESS,B.PICTURETYPE,C.COMMENTTOTALSCORE FROM GOODS A,PICTURES B,COMMENTS C WHERE A.GOODSID=B.FID AND A.GOODSID=C.GOODSID AND GOODSTYPE LIKE '%1%' AND A.GOODSID*RAND() LIMIT 2";
					
						List<Map<String , Object>> serviceMap=baseJdbcDao.queryListMap(serviceSql);
				if(DataTypeUtil.validate(serviceMap)){
					
					String showGoods="SELECT  A.GOODSNAME,A.GOODSTYPE,A.GOODSPRICE ,B.ADDRESS, B.LINKADDRESS,B.PICTURETYPE,C.COMMENTTOTALSCORE "
							+ "FROM GOODS A,PICTURES B,COMMENTS C  WHERE A.GOODSID=B.FID AND A.GOODSID=C.GOODSID AND B.ADDRESS='"+hotServices+"'";
					Map<String , Object> showMap=baseJdbcDao.queryMap(showGoods);
					if(DataTypeUtil.validate(showMap)){
						resObj.put("RESULT", "1");
						resObj.putAll(showMap);
					}
				}
			}catch(Exception e) {
				resObj.put("RESULT", "0");
				e.printStackTrace();
			}
			WriterJsonArray.AppWriterJSONArray(resObj,response);
			}
		
		//猜你喜欢
		/*@ResponseBody
		@RequestMapping(value = "/api/user/guess.do")
		public void guess(HttpServletRequest request,HttpServletResponse response) throws IOException {
			Map<String, Object> resObj = new HashMap<String, Object>();
			String jsonString=  request.getParameter("jsonString");
			JSONObject jsonObj = JSONObject.fromObject(jsonString);
			
		}
	*/
}
