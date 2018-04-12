package com.epmis.dynamic.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import com.df.jiguang.JiguangPush_yonghu;

import com.epmis.dynamic.service.DynamicService;
import com.epmis.dynamic.vo.Dynamic;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class DynamicAction extends ActionSupport implements ServletRequestAware{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	@Autowired
	private DynamicService dynamicService;
	private Dynamic dynamic;
	
	public Dynamic getDynamic() {
		return dynamic;
	}

	public void setDynamic(Dynamic dynamic) {
		this.dynamic = dynamic;
	}

	private int page;
	private int rows;
	
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	HttpServletRequest request;
	HttpServletResponse response= ServletActionContext.getResponse();
	

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
		
	}
	
	
public void findAllProdt(){
		
		String isdel = request.getParameter("isdel");
		String lid=request.getParameter("lid");
		
		
		List<Map<String, Object>> result = this.dynamicService.findAllProdt(isdel,lid,page,rows);		
		
		
		int count=this.dynamicService.count(isdel,lid);
		Map<String, Object> jsonMap=new HashMap<String, Object>();
		jsonMap.put("rows", result);
		jsonMap.put("total", count);
		JSONObject json = JSONObject.fromObject(jsonMap);
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Charset","utf-8");
		response.setHeader("Cache-Control", "no-cache");
		
		try {
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 删除数据
	 */
	public void delete(){
		String id = request.getParameter("id");
		System.out.println(id);
		String result="";
		String [] stringArr= id.split(",");
		for(int i=0;i<stringArr.length;i++){
			result = this.dynamicService.delete(stringArr[i]);
		}
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	
	
	
	
	/**
	 * 保存
	 * @throws Exception
	 */
	public void save(){		
		String result =dynamicService.save(dynamic);
		
		String sqlsum = "select count(1) from t_user istuisong='1' and where isdel='0' and type <>'1' and  id in(select uid from t_dingyue where status='1' and oid='"+dynamic.getL_id()+"')";
		int countsum = this.baseJdbcDao.getCount(sqlsum);// 计算推送人的数量
		int avg = countsum / 20;
		
		int start = 0;
		for (int i = 1; i <= avg + 1; i++) {
			int len = 20;
		
		String sql="select telephone from t_user where istuisong='1' and isdel='0' and type <>'1' and  id in(select uid from t_dingyue where status='1' and oid='"+dynamic.getL_id()+"') limit "+ start + ", " + len;
		List<Map<String,Object>> results = this.baseJdbcDao.queryListMap(sql);
		List<String> phoneList = new ArrayList<String>();
		for (Map<String, Object> map : results) {
			phoneList.add(map.get("telephone").toString());
		}		
		if("success".equals(result)){
			//极光部分推送
			if(!(phoneList.size()==0)){
				 Map<String,String> map=new Hashtable();
		    	 map.put("clickid", dynamic.getL_id());
		    	 map.put("type", "3");
		    	 map.put("status", "2");
//				JiguangPush_yonghu.sendAlias("您关注的楼盘有新的动态！", map,phoneList);	
			}
			
		}
		start = i * len;
		}
		
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 根据id查询汽车列表
	 * @return
	 */
	public String findById(){
		String ids = request.getParameter("id");	
//		String lid = request.getParameter("lid");
		List<Map<String, Object>> result = this.dynamicService.findById(ids);
		request.setAttribute("dynamic", result);
		
		return "success";
	}
	
	/**
	 * 修改
	 * @throws Exception 
	 */
	public void update(){
//		String result="";
//		if(this.baseJdbcDao.getCount("SELECT count(id) from t_pro_dynamic where isdel='0' and l_id='"+dynamic.getL_id()+"' ") > 0){
//			result = this.dynamicService.update(dynamic);
//		}else{
//			result =dynamicService.save(dynamic);
//		}
		
		String result = this.dynamicService.update(dynamic);
		
		WriterJsonArray.writerJSONArray(result, response);
		
	}
	}
