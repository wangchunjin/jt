package com.epmis.jt.clientinfo.web;

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

import com.epmis.jt.clientinfo.service.ClientinfoService;
import com.epmis.jt.clientinfo.vo.Clientinfo;
import com.epmis.sys.dao.BaseJdbcDao;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ClientinfoAction extends ActionSupport implements ServletRequestAware{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	@Autowired
	private ClientinfoService clientinfoService;
	private Clientinfo clientinfo;
	
	

	public Clientinfo getClientinfo() {
		return clientinfo;
	}

	public void setClientinfo(Clientinfo clientinfo) {
		this.clientinfo = clientinfo;
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
	 * 查询所有借款用户
	 */
public void findAllClientinfo(){	
		String mobile=request.getParameter("mobile");
		List<Map<String, Object>> result = this.clientinfoService.findAllClientinfo(mobile,page,rows);		
		
		int count=this.clientinfoService.count(mobile);
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
	 * 根据id查询单个借款用户列表
	 * @return
	 */
	public String findById(){
		String ids = request.getParameter("id");
		List<Map<String, Object>> result = this.clientinfoService.findById(ids);
		request.setAttribute("clientinfo", result);
		return "success";
	}
	


}
