package com.epmis.jt.agree.web;


import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epmis.jt.agree.service.AgreeService;
import com.epmis.jt.agree.vo.Agree;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AgreeAction extends ActionSupport implements ServletRequestAware{
	
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	@Autowired
	private AgreeService agreeService;
	private Agree agree;
	

	public Agree getAgree() {
		return agree;
	}

	public void setAgree(Agree agree) {
		this.agree = agree;
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
	 * 根据id查询各协议信息
	 * @return
	 */
	public String findById(){
		String ids = request.getParameter("id");
		String loan = request.getParameter("loan");
		List<Map<String, Object>> result = this.agreeService.findById(ids);
		request.setAttribute("agree", result);	
		if("1".equalsIgnoreCase(loan)){
			return "loan";
		}else if("2".equalsIgnoreCase(loan)){
			return "insurance";
		}else if("3".equalsIgnoreCase(loan)){
			return "lender";
		}else{
			return "success";
		}
		
	}
	
	/**
	 * 修改注册协议
	 * @throws Exception 
	 */
	public void updateReg() {
		String result = this.agreeService.updateReg(agree);
		WriterJsonArray.writerJSONArray(result, response);
		
	}
	
	/**
	 * 修改借款协议
	 * @throws Exception 
	 */
	public void updateLoan() {
		String result = this.agreeService.updateLoan(agree);
		WriterJsonArray.writerJSONArray(result, response);
		
	}
	
	
	/**
	 * 修改保险协议
	 * @throws Exception 
	 */
	public void updateInsurance() {
		String result = this.agreeService.updateInsurance(agree);
		WriterJsonArray.writerJSONArray(result, response);
		
	}
	
	/**
	 * 修改出借人
	 * @throws Exception 
	 */
	public void updatelender() {
		String result = this.agreeService.updatelender(agree);
		WriterJsonArray.writerJSONArray(result, response);
		
	}

	
	
	
	
	
	

}
