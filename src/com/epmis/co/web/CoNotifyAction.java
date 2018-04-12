package com.epmis.co.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import net.sf.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.epmis.co.service.CoNewService;
import com.epmis.co.service.CoNotifyService;
import com.epmis.co.vo.CoNew;
import com.epmis.co.vo.CoNotify;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

public class CoNotifyAction extends ActionSupport implements
		ServletRequestAware {
	@Autowired
	private CoNotifyService coNotifyService;
	@Autowired
	private CoNewService coNewService;

	private CoNotify coNotify;

	public CoNotify getCoNotify() {
		return coNotify;
	}

	public void setCoNotify(CoNotify coNotify) {
		this.coNotify = coNotify;
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

	public void CoNotifyTable() {
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
		List<Map<String, Object>> result = coNotifyService.CoNotifyTable(
				userInfo, start, number, type, title, created, beginDate,
				endDate);
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", coNotifyService.getCoNotifyCount(userInfo, type,
				title, created, beginDate, endDate));// total键 存放总记录数，必须的
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

	public void AddNotify() {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"UserInfo");
		if (!DataTypeUtil.validate(userInfo)) {
			WriterJsonArray.writerJSONArray("用户已失效，请重新登录！", response);
			return;
		}
		String result = coNotifyService.AddNotify(userInfo, this.coNotify);
		WriterJsonArray.writerJSONArray(result, response);
	}

	public String GetLabel() {
		String wid = request.getParameter("WID");
		Map<String, Object> result = coNotifyService.GetCoNotifyInfo(wid);
		request.setAttribute("CoNotifyInfo", result);
		return "success";
	}

	public void SaveNotify() {
		String result = coNotifyService.SaveNotify(this.coNotify);
		WriterJsonArray.writerJSONArray(result, response);
	}

	public void RoleUserTree() {
		String wid = request.getParameter("wid");
		String parentId = request.getParameter("parentId");
		String roleType = request.getParameter("roleType");
		String key = request.getParameter("key");
		List result = coNotifyService.RoleUserTree(wid, parentId, roleType,key);
		WriterJsonArray.writerJSONArray(result, response);
	}

	public String findNotify() {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
		"UserInfo");
		if (!DataTypeUtil.validate(userInfo)) {
			return SUCCESS;
		}
		co_Notifylist = coNotifyService.FindCoNotify(userInfo,0, 15, null, null, null,
				null, null);//new SimpleDateFormat("yyyy-MM-dd").format(new Date())
		return SUCCESS;
	}

	public void findNotify_page() {
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
		List<Map<String, Object>> result = coNotifyService.FindCoNotify(userInfo,start,
				number, type, title, created, beginDate, endDate);
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", coNotifyService.FindCoNotifyCount(userInfo,type, title,
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

	public String showNotify() {
		if (!DataTypeUtil.validate(coNotify)) {
			return SUCCESS;
		}
		co_Notify = coNotifyService.showNotify(coNotify.getWid());
		if (DataTypeUtil.validate(co_Notify)) {
			String newStr = (co_Notify.get("CONTENT") + "").replaceAll("&lt;",
					"<");
			newStr = newStr.replaceAll("&gt;", ">");
			co_Notify.put("CONTENT", newStr);
		}
		coNotify.setNotifyId(co_Notify.get("NOTIFY_ID") + "");
		//co_Notifylist = coNotifyService.FindCoNotifyLink(co_Notify.get("NOTIFY_ID")+"",-1,-1);
		countpage = coNotifyService.FindCoNotifyLinkCount(co_Notify.get("NOTIFY_ID") + "");
		return "success";
	}

	// 点击数量
	public void addco_notityCount() {
		String result = coNotifyService.addco_newCount(coNotify);
		WriterJsonArray.writerJSONArray(result, response);
	}

	private int nowpage;
	private int sizepage = 10;
	private int countpage;

	public String findco_NotifyLink_page() {
		if (nowpage > countpage) {
			nowpage = countpage;
		}
		if (nowpage < 1) {
			nowpage = 1;
		}
		co_Notifylist = coNotifyService.FindCoNotifyLink(coNotify.getNotifyId(), (nowpage-1)*sizepage, sizepage);
		countpage = coNotifyService.FindCoNotifyLinkCount(coNotify.getNotifyId());
		countpage = countpage%sizepage==0?countpage/sizepage:countpage/sizepage+1;
		countpage = countpage==0?1:countpage;
		return SUCCESS;
	}
	
	private String links;
	//发表评论
	public void add_NotifyLink(){
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
		String result = coNewService.AddComment(userInfo.getUserId(), coNotify.getNotifyId(), links, "NOTIFY");
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


	
	private List<Map<String, Object>> co_Notifylist;
	private Map<String, Object> co_Notify;

	public CoNotifyService getCoNotifyService() {
		return coNotifyService;
	}

	public List<Map<String, Object>> getCo_Notifylist() {
		return co_Notifylist;
	}

	
	public Map<String, Object> getCo_Notify() {
		return co_Notify;
	}

	public String findco_notify() {
		return SUCCESS;
	}
	public void PushMsg(){
		String wid =  request.getParameter("WID");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"UserInfo");
		String userId = userInfo.getUserId();
		String result = coNotifyService.PushMsg(userId,wid);
		WriterJsonArray.writerJSONArray(result, response);
	
    }
}
