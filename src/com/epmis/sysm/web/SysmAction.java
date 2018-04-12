package com.epmis.sysm.web;

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


import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.sysm.service.SysmService;
import com.epmis.sysm.vo.Sysm;
import com.jiguang.JiguangPush;
import com.jiguang.jiguangAll;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class SysmAction extends ActionSupport implements ServletRequestAware{
	@Autowired
	private SysmService sysmService;
	
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	private Sysm sysm;
	

	public Sysm getSysm() {
		return sysm;
	}

	public void setSysm(Sysm sysm) {
		this.sysm = sysm;
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
	/**
	 * 分页查询推送消息
	 */
	public void findAllSysmcar(){
		String title = request.getParameter("title");
		
		String isdel = request.getParameter("isdel");
		
		System.out.println(1111+"  "+title);
		List<Map<String, Object>> result = this.sysmService.findAllSysmcar(title,isdel,page,rows);
		
		
		int count=this.sysmService.count(title,isdel);
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
	}
	/**
	 * 新增有关车型的推送消息并推送
	 * @throws Exception 
	 */
	public void save() {
		//新增有关车型的推送消息
		String result=this.sysmService.save(sysm);
			//全推
			jiguangAll jg=new jiguangAll();
			jg.jiguangAll("", ""+sysm.getContent()+"", "1,0,"+sysm.getOid()+"", "2"); //将系统消息表里的type status  和主键ID  三个放这里
		
		
		WriterJsonArray.writerJSONArray(result, response);
		
		
	}
	
	
	
	
	/**
	 * 删除
	 */
	public void delete(){
		String id=request.getParameter("id");
		
		String result="";
		String [] stringArr= id.split(",");
		for(int i=0;i<stringArr.length;i++){
			result = this.sysmService.delete(stringArr[i]);
		}
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 根据id查询
	 */
	public String findById(){
		String ids = request.getParameter("id");
		List<Map<String, Object>> result = this.sysmService.findById(ids);
		request.setAttribute("sysm", result);
		
		return "success";
		
		
		
		
	}
	/**
	 * 修改
	 * @throws Exception 
	 */
	public void update() {
		
		String result = this.sysmService.update(sysm);
		WriterJsonArray.writerJSONArray(result, response);
	}
	
}
