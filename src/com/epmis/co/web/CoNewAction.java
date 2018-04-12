package com.epmis.co.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.epmis.app.service.SendMsgService;
import com.epmis.app.vo.SysAppMsg;
import com.epmis.cc.service.SmTestService;
import com.epmis.cc.vo.SmTest;
import com.epmis.co.service.CoNewService;
import com.epmis.co.vo.CoNew;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

public class CoNewAction extends ActionSupport implements ServletRequestAware {
	@Autowired
	private CoNewService coNewService;

	private CoNew coNew;

	public CoNew getCoNew() {
		return coNew;
	}

	public void setCoNew(CoNew coNew) {
		this.coNew = coNew;
	}

	HttpServletRequest request;

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	HttpServletResponse response = ServletActionContext.getResponse();
	private String rows;// 每页显示的记录数

	private String page;// 当前第几页

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void CoNewTable() {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"UserInfo");
		if (!DataTypeUtil.validate(userInfo)) {
			WriterJsonArray.writerJSONArray("用户已失效，请重新登录！", response);
			return;
		}
		String type = request.getParameter("type");
		String title = request.getParameter("title");
		String created = request.getParameter("created");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");

		int intPage = Integer.parseInt((page == null || page == "0") ? "1"
				: page);
		// 每页显示条数
		int number = Integer.parseInt((rows == null || rows == "0") ? "20"
				: rows);
		// 每页的开始记录 第一页为1 第二页为number +1
		int start = (intPage - 1) * number;
		List<Map<String, Object>> result = coNewService.CoNewTable(userInfo,
				start, number, type, title, created, beginDate, endDate);
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", coNewService.getCoNewCount(userInfo, type, title,
				created, beginDate, endDate));// total键 存放总记录数，必须的
		jsonMap.put("rows", result);// rows键 存放每页记录 list

		JSONObject json = JSONObject.fromObject(jsonMap);
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Charset", "utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			response.getWriter().println(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void AddNew() {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"UserInfo");
		if (!DataTypeUtil.validate(userInfo)) {
			WriterJsonArray.writerJSONArray("用户已失效，请重新登录！", response);
			return;
		}
		String result = coNewService.AddNew(userInfo, this.coNew);
		WriterJsonArray.writerJSONArray(result, response);
	}

	public String GetLabel() {
		String wid = request.getParameter("WID");
		Map<String, Object> result = coNewService.GetCoNewInfo(wid);
		request.setAttribute("CoNewInfo", result);
		return "success";
	}

	public void SaveNew() {
		String result = coNewService.SaveNew(this.coNew);
		WriterJsonArray.writerJSONArray(result, response);
	}

	List<Map<String, Object>> co_newlist;
	Map<String, Object> co_new;

	public String findco_new() {
		co_newlist = coNewService.findco_new(1, 15, null, null, null, null,
				null);
		return SUCCESS;
	}

	public void findco_new_page() {
		String type = request.getParameter("type");
		String title = request.getParameter("title");
		String created = request.getParameter("created");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		int intPage = Integer.parseInt((page == null || page == "0") ? "1"
				: page);
		// 每页显示条数
		int number = Integer.parseInt((rows == null || rows == "0") ? "20"
				: rows);
		// 每页的开始记录 第一页为1 第二页为number +1
		int start = (intPage - 1) * number;
		List<Map<String, Object>> result = coNewService.findco_new(intPage,
				number, type, title, created, beginDate, endDate);
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", coNewService.findco_newcount(type, title, created,
				beginDate, endDate));// total键 存放总记录数，必须的
		jsonMap.put("rows", result);// rows键 存放每页记录 list

		JSONObject json = JSONObject.fromObject(jsonMap);
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Charset", "utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			response.getWriter().println(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// if (nowpage < 1) {
		// nowpage = 1;
		// }
		// if (nowpage > countpage) {
		// nowpage = countpage;
		// }
		// co_newlist = coNewService.findco_new(nowpage, sizepage);
		// countpage = coNewService.findco_newcount();
		// countpage =
		// countpage%sizepage==0?countpage/sizepage:countpage/sizepage+1;
		// return SUCCESS;
	}

	public void addco_newCount() {
		String result = coNewService.addco_newCount(coNew);
		WriterJsonArray.writerJSONArray(result, response);
	}

	// private int nowpage;// 当前页数
	// private int sizepage=20;// 每页显示条数
	// private int countpage = 1;// 总页数

	public String findco_newByid() {
		co_new = coNewService.findco_newByid(coNew);
		if (DataTypeUtil.validate(co_new)) {
			coNew = new CoNew();
			coNew.setNewId(co_new.get("NEW_ID") + "");
			coNew.setTitle(co_new.get("TITLE") + "");
			coNew.setCreatedBy(co_new.get("CREATED_BY") + "");
			// coNew.setPublishDate(new SimpleDateFormat("yyyy-MM-dd")
			// .format(co_new.get("CREATED_DATE")));
			coNew.setPublishDate(co_new.get("CREATED_DATE") + "");
			coNew.setClickCount(Integer
					.parseInt(co_new.get("CLICK_COUNT") + ""));
			String newStr = (co_new.get("CONTENT") + "")
					.replaceAll("&lt;", "<");
			newStr = newStr.replaceAll("&gt;", ">");
			coNew.setContent(newStr);
		}
		countpage = coNewService.findco_newByidLinkCount(co_new.get("NEW_ID") + "");
		return SUCCESS;
	}

	private int nowpage;
	private int sizepage = 10;
	private int countpage;

	public String findco_newLink_page() {
		if (nowpage > countpage) {
			nowpage = countpage;
		}
		if (nowpage < 1) {
			nowpage = 1;
		}
		co_newlist = coNewService.findco_newByidLink(coNew.getNewId(), (nowpage-1)*sizepage, sizepage);
		countpage = coNewService.findco_newByidLinkCount(coNew.getNewId());
		countpage = countpage%sizepage==0?countpage/sizepage:countpage/sizepage+1;
		countpage = countpage==0?1:countpage;
		return SUCCESS;
	}
	
	private String links;
	//发表评论
	public void add_newLink(){
		if (!DataTypeUtil.validate(links)) {
			WriterJsonArray.writerJSONArray("发表内容不能为空", response);
			return;
		}
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
		"UserInfo");
		if (!DataTypeUtil.validate(userInfo)) {
			WriterJsonArray.writerJSONArray("用户已失效，请重新登录！", response);
			return;
		}
		String result = coNewService.AddComment(userInfo.getUserId(), coNew.getNewId(), links, "NEWS");
		WriterJsonArray.writerJSONArray(result, response);
	}
	
	
	public void setLinks(String links) {
		this.links = links;
	}

	public int getNowpage() {
		return nowpage;
	}

	public void setNowpage(int nowpage) {
		this.nowpage = nowpage;
	}

	public int getCountpage() {
		return countpage;
	}

	public void setCountpage(int countpage) {
		this.countpage = countpage;
	}

	public List<Map<String, Object>> getCo_newlist() {
		return co_newlist;
	}

	public Map<String, Object> getCo_new() {
		return co_new;
	}
	
 
	public void PushMsg(){
		String wid =  request.getParameter("WID");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"UserInfo");
		String userId = userInfo.getUserId();
		String result = coNewService.PushMsg(userId,wid);
		WriterJsonArray.writerJSONArray(result, response);
	
    }

}
