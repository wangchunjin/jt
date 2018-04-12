package com.epmis.jt.contract.web;

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

import com.epmis.jt.contract.service.ContractService;
import com.epmis.jt.contract.vo.Contract;
import com.epmis.jt.lender.service.LenderService;
import com.epmis.jt.lender.vo.Lender;
import com.epmis.sys.dao.BaseJdbcDao;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class ContractAction extends ActionSupport implements ServletRequestAware{@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	@Autowired
	private ContractService contractService;
	private Contract contract;
	

	

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
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
	
	
public void findAllContract(){
		
		String createtime=request.getParameter("createtime");
		String client_id=request.getParameter("client_id");
		String order_number=request.getParameter("order_number");
		
		List<Map<String, Object>> result = this.contractService.findAllContract(client_id,order_number,createtime,page,rows);		
		
		
		int count=this.contractService.count(client_id,order_number,createtime);
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
	}}
