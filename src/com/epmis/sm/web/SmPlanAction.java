package com.epmis.sm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.sm.service.SmPlanService;
import com.epmis.sm.vo.SmPlan;
import com.opensymphony.xwork2.ActionSupport;


public class SmPlanAction extends ActionSupport implements ServletRequestAware{

	@Autowired
    private SmPlanService smPlanService;	
	  private String parentId;
	private SmPlan smPlan;
	  public SmPlan getSmPlan() {
		return smPlan;
	}
	public void setSmPlan(SmPlan smPlan) {
		this.smPlan = smPlan;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	private String moduleCode;
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
  	HttpServletRequest request  ;
	 @Override
	 public void setServletRequest(HttpServletRequest request) {
	 	this.request=request;
	 	
	 }
	 HttpServletResponse response = ServletActionContext.getResponse();
    
	public void ShowSmPlanTree()
	  {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		List list = smPlanService.ShowSmPlanTree(this.parentId,this.moduleCode,userInfo);
		WriterJsonArray.writerJSONArray(list,response);
	  }
		public void ShowSmPlanMoveTree()
	  {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		List list = smPlanService.ShowSmPlanMoveTree(this.parentId,this.moduleCode,userInfo);
		WriterJsonArray.writerJSONArray(list,response);
	  }
	public void PlanDocTree()
	  {
		String base_master_key = request.getParameter("base_master_key");  
		String type= request.getParameter("type");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		List list = smPlanService.PlanDocTree(base_master_key,type,userInfo);
		WriterJsonArray.writerJSONArray(list,response);
	  }
	
	public String  GetLabel()
	  {
		SmPlan smPlan = new SmPlan();
	     String plan_id = request.getParameter("PLAN_ID");
	     smPlan.setPlanId(plan_id);
	     smPlan = smPlanService.GetSmPlan(smPlan);		     
	     request.setAttribute("smPlan", smPlan);
	     return "success";
	  }
	public String  UpdateSmPlan()
	  {
		 String type = request.getParameter("type"); 
	     String resutl = smPlanService.UpdateSmPlan(smPlan,type);
	     return resutl;
	  }
	public String  label_assgin()
	  {
	     return "success";
	  }
	public String  label_task_linkdoc()
	  {
	     return "success";
	  }
	public String  label_task_doc()
	  {
	     return "success";
	  }
	public String  tabel_task_refdata()
	  {
	     return "success";
	  }
	
	public void ShowPlanUserTable()
	  {
		String plan_id = request.getParameter("plan_id");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		String projId= userInfo.getProjId();
		List list = smPlanService.ShowPlanUserTable(plan_id,projId);
		WriterJsonArray.writerJSONArray(list,response);
	  }
	public void PlanLindDocTable()
	  {
		String plan_id = request.getParameter("plan_id");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		String projId= userInfo.getProjId();
		List list = smPlanService.PlanLindDocTable(plan_id,projId);
		WriterJsonArray.writerJSONArray(list,response);
	  }
	public void MoveSmPlan(){
		 String parentId = request.getParameter("parentId");
		 String orginId = request.getParameter("orginId");
		 UserInfo user = (UserInfo) request.getSession().getAttribute("UserInfo");
		 String projId = user.getProjId();
		 String result = smPlanService.MoveSmPlan(parentId,orginId,projId);
		 Map root = new HashMap();
		 root.put("result", result);
		 WriterJsonArray.writerJSONArray(root,response);
	}
	public void addPlanUserTable()
	  {
		String plan_id = request.getParameter("plan_id"); 
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		String projId= userInfo.getProjId();
		List list = smPlanService.AddPlanUserTable(plan_id,projId);
		WriterJsonArray.writerJSONArray(list,response);
	  }
	public void addPlanUser()
	  {
		String plan_id = request.getParameter("plan_id"); 
		String UserIds = request.getParameter("UserId"); 
		String tableName = request.getParameter("tableName"); 
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		String projId= userInfo.getProjId();
		String result = smPlanService.AddPlanUser(plan_id,projId,UserIds,tableName);
		  Map root = new HashMap();
		  root.put("result", result);
	      WriterJsonArray.writerJSONArray(root,response);
	  }
	public void delPlanUser()
	  {
		String plan_id = request.getParameter("plan_id"); 
		String UserIds = request.getParameter("UserId"); 
		String tableName = request.getParameter("tableName");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		String projId= userInfo.getProjId();
		String result = smPlanService.delPlanUser(plan_id,UserIds,tableName,projId);
		  Map root = new HashMap();
		  root.put("result", result);
	      WriterJsonArray.writerJSONArray(root,response);
	  }
	public String  label_task_guid()
	  {
		 String plan_id = request.getParameter("PLAN_ID"); 
		 if(DataTypeUtil.validate(plan_id)){
	     Map<String, Object> list = smPlanService.getSmPlanInfo(plan_id);
		 request.setAttribute("SmPlanInfo", list);
		 }
	     return "success";
	  }
	
	public void UpdateAssGuides()
	  { 
		  String resutl = smPlanService.UpdateAssGuides(smPlan);
	      WriterJsonArray.writerJSONArray(resutl,response);
	  }
	public String  label_task()
	  {
		 String plan_id = request.getParameter("PLAN_ID");  
		 if(DataTypeUtil.validate(plan_id)){
	     Map<String, Object> list = smPlanService.getSmPlanInfo(plan_id); 
		 request.setAttribute("SmPlanInfo", list);
		 }
	     return "success";
	  }
	public void cancleFinish()
	  { 
		  String resutl = smPlanService.cancleFinish(smPlan);  
	      WriterJsonArray.writerJSONArray(resutl,response);
	  }
	public void UpdateSmPlanTask()
	  { 
		  String resutl = smPlanService.UpdateSmPlanTask(smPlan);   
	      WriterJsonArray.writerJSONArray(resutl,response);
	  }
	public void UpdateSmPlanRemark()
	  { 
		  String resutl = smPlanService.UpdateSmPlanRemark(smPlan);  
	      WriterJsonArray.writerJSONArray(resutl,response);
	  }
	
	public String label_task_remark(){
		 String plan_id = request.getParameter("PLAN_ID");  
		 if(DataTypeUtil.validate(plan_id)){
	     Map<String, Object> list = smPlanService.getSmPlanInfo(plan_id); 
		 request.setAttribute("SmPlanInfo", list);
		 }
	     return "success";
	}
	public void PlanRefDataTable(){
		String plan_id = request.getParameter("plan_id");
		String doc_id = request.getParameter("doc_id");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		String projId= userInfo.getProjId();
		List list = smPlanService.ShowPlanRefDataTable(plan_id,doc_id,projId);
		WriterJsonArray.writerJSONArray(list,response);
	}
	public void AddPlan(){ 
		String tableName = request.getParameter("tableName");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		String list = smPlanService.AddPlan(tableName,smPlan,userInfo);
		WriterJsonArray.writerJSONArray(list,response);
	}
	public void delPlan(){ 
		String tableName = request.getParameter("tableName");
		String planId =  request.getParameter("planId");
		String nodeType = request.getParameter("nodeType");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		String list = smPlanService.delPlan(tableName,planId,nodeType,userInfo);
		WriterJsonArray.writerJSONArray(list,response);
	}
	 
       
}
