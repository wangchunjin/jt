package com.epmis.jt.waymoney.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epmis.jt.waymoney.service.WaymoneyService;
import com.epmis.jt.waymoney.vo.Waymoney;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class WaymoneyAction extends ActionSupport implements ServletRequestAware{
	
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	@Autowired
	private WaymoneyService waymoneyService;
	private Waymoney waymoney;
	

	public Waymoney getWaymoney() {
		return waymoney;
	}

	public void setWaymoney(Waymoney waymoney) {
		this.waymoney = waymoney;
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
	 * 根据id查询打款方式
	 * @return
	 */
	public String findById(){
		String ids = request.getParameter("id");
	
		List<Map<String, Object>> result = this.waymoneyService.findById(ids);
		request.setAttribute("waymoney", result);	
		
			return "success";
		
	}
	
	/**
	 * 修改注册协议
	 * @throws Exception 
	 */
	public void update() {
		String result = this.waymoneyService.update(waymoney);
		WriterJsonArray.writerJSONArray(result, response);
		
	}
	

	
	
	

	
	
	
	
	
	

}
