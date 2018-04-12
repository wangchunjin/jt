package com.epmis.jt.lender.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import com.epmis.jt.lender.service.LenderService;
import com.epmis.jt.lender.vo.Lender;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class LenderAction extends ActionSupport implements ServletRequestAware{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	@Autowired
	private LenderService lenderService;
	private Lender lender;
	

	public Lender getLender() {
		return lender;
	}

	public void setLender(Lender lender) {
		this.lender = lender;
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
	
	
public void findAllLender(){
		
		String isdel=request.getParameter("isdel");
		
		List<Map<String, Object>> result = this.lenderService.findAllLender(isdel,page,rows);		
		
		
		int count=this.lenderService.count();
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
			result = this.lenderService.delete(stringArr[i]);
		}
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	
	
	
	
	/**
	 * 保存
	 * @throws Exception
	 */
	public void save(){		
		String result =lenderService.save(lender);		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 根据id查询沟通结果名称
	 * @return
	 */
	public String findById(){
		String ids = request.getParameter("id");
		String see=request.getParameter("see");
		String res="";
		List<Map<String, Object>> result = this.lenderService.findById(ids);
		request.setAttribute("lender", result);
		if(("1").equals(see)){
			res="look";
		}else{
			res="success";
		}
		
		return res;
	}
	
	/**
	 * 修改
	 * @throws Exception 
	 */
	public void update(){

		String result = this.lenderService.update(lender);
		WriterJsonArray.writerJSONArray(result, response);
		
	}
	}
