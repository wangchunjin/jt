package com.epmis.jt.orderinfo.web;

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
import com.epmis.jt.orderinfo.service.OrderinfoService;
import com.epmis.jt.orderinfo.vo.Orderinfo;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class OrderinfoAction extends ActionSupport implements ServletRequestAware{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	@Autowired
	private ClientinfoService clientinfoService;
	
	
	@Autowired
	private OrderinfoService orderinfoService;
	private Orderinfo orderinfo;
	
	

	public Orderinfo getOrderinfo() {
		return orderinfo;
	}

	public void setOrderinfo(Orderinfo orderinfo) {
		this.orderinfo = orderinfo;
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
	 * 查询所有订单
	 */
public void findAllOrderinfo(){		
		String approve_status=request.getParameter("approve_status");
		String transfer_status=request.getParameter("transfer_status");
		String zgtime=request.getParameter("zgtime");
		String jgtime=request.getParameter("jgtime");
		String mobile=request.getParameter("mobile");
		String jg=request.getParameter("jg");
		String cy=request.getParameter("cy");
		
		//催收主管用
		String fp_zg=request.getParameter("fp_zg");
		//催收机构用
		String jg_name=request.getParameter("jg_name");
		String fp_jg=request.getParameter("fp_jg");
		List<Map<String, Object>> result = this.orderinfoService.findAllOrderinfo(approve_status,transfer_status,fp_zg,fp_jg,jg_name,zgtime,jgtime,mobile,jg,cy,page,rows);		
		
		int count=this.orderinfoService.count(approve_status,transfer_status,fp_zg,fp_jg,jg_name,zgtime,jgtime,mobile,jg,cy);
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
 * 查询所有订单
 */
public void findAllOrderinfo_yg(){		
	String approve_status=request.getParameter("approve_status");
	String transfer_status=request.getParameter("transfer_status");
	String zgtime=request.getParameter("zgtime");
	String jgtime=request.getParameter("jgtime");
	String mobile=request.getParameter("mobile");
	//催收主管用
	String fp_zg=request.getParameter("fp_zg");
	//催收机构用
	String yg_name=request.getParameter("yg_name");
	String fp_jg=request.getParameter("fp_jg");
	//催收员用
	String collection=request.getParameter("collection");
	List<Map<String, Object>> result = this.orderinfoService.findAllOrderinfo_yg(approve_status,transfer_status,fp_zg,fp_jg,yg_name,collection,zgtime,jgtime,mobile,page,rows);		
	
	int count=this.orderinfoService.count_yg(approve_status,transfer_status,fp_zg,fp_jg,yg_name,collection,zgtime,jgtime,mobile);
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
//	WriterJsonArray.writerJSONArray(result, response);
}

	
	


	/**
	 * 根据id查询单个订单列表
	 * @return
	 */
	public String findById(){
		String ids = request.getParameter("id");
		List<Map<String, Object>> result = this.orderinfoService.findById(ids);
		request.setAttribute("orderinfo", result);
		return "success";
	}
	
	
	/**
	 * 根据id查询单个订单列表
	 * @return
	 */
	public String findById_lookt(){
		String ids = request.getParameter("id");
		//获取用户表中的第一第二紧急联系人
		List<Map<String, Object>> result = this.clientinfoService.findById(ids);
		//获取用户通讯录的所有信息		
//		List<Map<String, Object>> result1=this.orderinfoService.findById_t(ids);
		request.setAttribute("clientinfo", result);
//		request.setAttribute("operator", result1);
		
		//获取用户的通讯录信息进行比较
		String sql_sum="select count(1) from (select id from t_operator where uid='"+ids+"' GROUP BY phone ORDER BY id desc)a";
		int sum=this.baseJdbcDao.getCount(sql_sum);
		
		String sql_q="select uid,id,phone,pname from t_operator where uid='"+ids+"' GROUP BY phone ORDER BY id desc limit 0,"+(sum+1)/2;
		List<Map<String, Object>> resultq=this.baseJdbcDao.queryListMap(sql_q);
		String sql_h="select uid,id,phone,pname from t_operator where uid='"+ids+"' GROUP BY phone ORDER BY id desc limit "+(sum+1)/2+","+sum;
		List<Map<String, Object>> resulth=this.baseJdbcDao.queryListMap(sql_h);
		request.setAttribute("operator1", resultq);
		request.setAttribute("operator2", resulth);
		return "success";
	}
	
	
	
	/**
	 * 催收主管分配逾期订单
	 * @throws Exception 
	 */
	public void fp(){
		String ids=request.getParameter("ids");
		String jg_name=request.getParameter("jg_name");
		String cuid=request.getParameter("cuid");
		String result = this.orderinfoService.fp(ids,jg_name,cuid);
		WriterJsonArray.writerJSONArray(result, response);
		
	}
	
	
	
	
	
	
	
	
	/**
	 * 催收主管收回选中订单
	 */
	public void sh(){
		String ids = request.getParameter("ids");
		String cuid=request.getParameter("cuid");
		
		String result = this.orderinfoService.sh(ids,cuid);	
		WriterJsonArray.writerJSONArray(result, response);
	}

	
	/**
	 * 催收机构分配逾期订单
	 * @throws Exception 
	 */
	public void fp_jg(){
		String ids=request.getParameter("ids");
		String yg_name=request.getParameter("jg_name");
		String cuid=request.getParameter("cuid");
		String result = this.orderinfoService.fp_jg(ids,yg_name,cuid);
		WriterJsonArray.writerJSONArray(result, response);
		
	}
	
	/**
	 * 催收机构收回选中订单
	 */
	public void sh_jg(){
		String ids = request.getParameter("ids");
		
		String cuid=request.getParameter("cuid");
		String result = this.orderinfoService.sh_jg(ids,cuid);
		
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 * 消减滞纳金
	 * @throws Exception 
	 */
	public void late(){
		String late_fee=request.getParameter("late_fee");
		String cuid=request.getParameter("cuid");
		String result = this.orderinfoService.late(orderinfo,late_fee,cuid);
		WriterJsonArray.writerJSONArray(result, response);
		
	}
	
	/**
	 *对状态为1（还款中）的订单进行平帐 
	 */
	public void flat_1(){
		String id = request.getParameter("id");
		String cuid=request.getParameter("cuid");
	
		String result = this.orderinfoService.flat_1(id,cuid);
		
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	/**
	 *对状态为3（逾期）的订单进行平帐 
	 */
	public void flat_2(){
		String id = request.getParameter("id");
		String cuid=request.getParameter("cuid");
		String result = this.orderinfoService.flat_2(id,cuid);
		
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	/**
	 *对状态为1或3（逾期）的订单进行平帐 
	 */
	public void flat_3(){
		String id = request.getParameter("order_id");
		String cuid=request.getParameter("cuid");
		String rel_time=request.getParameter("rel_time");
		String transfer_status=request.getParameter("transfer_status");
		String o_transfer_status=request.getParameter("o_transfer_status");
		String o_rel_time=request.getParameter("o_rel_time");
		String result = this.orderinfoService.flat_3(id,cuid,rel_time,transfer_status,o_transfer_status,o_rel_time);
		
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	/**
	 *催收主管    催收排行
	 */
	public void rank(){
		
		String cuid=request.getParameter("cuid");
		
		String result = this.orderinfoService.rank(cuid);
		
		
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	
	
	
	/**
	 * 机构排行
	 */
public void Jgrankings(){		
		
		String cuid=request.getParameter("cuid");
		String start_time=request.getParameter("start_time");
		String end_time=request.getParameter("end_time");
		
		List<Map<String, Object>> result = this.orderinfoService.Jgrankings(cuid,start_time,end_time,page,rows);		
		
		int count=this.orderinfoService.countJgrankings(cuid,start_time,end_time);
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
 * 机构排行
 */
public void Cyrankings(){		
	
	String cuid=request.getParameter("cuid");
	String start_time=request.getParameter("start_time");
	String end_time=request.getParameter("end_time");
	
	List<Map<String, Object>> result = this.orderinfoService.Cyrankings(cuid,start_time,end_time,page,rows);		
	
	int count=this.orderinfoService.countCyrankings(cuid,start_time,end_time);
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
//	WriterJsonArray.writerJSONArray(result, response);
}
	
	
	
	
	
	
	
	
	
	
	
	

}
