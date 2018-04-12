package com.epmis.cc.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.epmis.cc.service.SmTestService;
import com.epmis.cc.vo.SmTest;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;
@SuppressWarnings("serial")
public class SmTestAction extends ActionSupport implements ServletRequestAware{
	/**
	 * 
	 */
	
	@Autowired
    private SmTestService smTestService;	
	  private String parentId;
	private SmTest smTest;
	 
	public String getParentId() {
		return parentId;
	}
 
	public SmTest getSmTest() {
		return smTest;
	}

	public void setSmTest(SmTest smTest) {
		this.smTest = smTest;
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
    
	public void ShowSmTestTree()
	  {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		List list = smTestService.ShowSmTestTree(this.parentId,this.moduleCode,userInfo);
		WriterJsonArray.writerJSONArray(list,response);
	  }
		public void ShowSmTestMoveTree()
	  {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		List list = smTestService.ShowSmTestMoveTree(this.parentId,this.moduleCode,userInfo);
		WriterJsonArray.writerJSONArray(list,response);
	  }
	public void AddPlan(){ 
		String tableName = request.getParameter("tableName");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		String list = smTestService.AddPlan(tableName,smTest,userInfo);
		WriterJsonArray.writerJSONArray(list,response);
	}
	public String  GetLabel()
	  {
	     String plan_id = request.getParameter("PLAN_ID");  
	     Map<String,Object> result = smTestService.GetSmTestInfo(plan_id);		     
	     request.setAttribute("SmTestInfo", result);
	     return "success";
	  }
	public String  UpdateSmTestWbs()
	  {
		 String type = request.getParameter("type"); 
	     String result = smTestService.UpdateSmTestWbs(smTest,type);
	     Map<String,Object> SmTestInfo = smTestService.GetSmTestInfo(smTest.getPlanId());		     
	     request.setAttribute("SmTestInfo", SmTestInfo);
	     return result;
	  }
	public void UpdateAssGuides()
	  { 
		  String resutl = smTestService.UpdateAssGuides(smTest);
	      WriterJsonArray.writerJSONArray(resutl,response);
	  }
	public void cancleFinish()
	  { 
		  String result = smTestService.cancleFinish(smTest);  
	      WriterJsonArray.writerJSONArray(result,response);
	  }
	public void UpdateSmTestTask()
	  { 
		  String result = smTestService.UpdateSmTestTask(smTest);
	      WriterJsonArray.writerJSONArray(result,response);
	  }
	public void MoveSmTest(){
		 String parentId = request.getParameter("parentId");
		 String orginId = request.getParameter("orginId");
		 UserInfo user = (UserInfo) request.getSession().getAttribute("UserInfo");
		 String projId = user.getProjId();
		 String result = smTestService.MoveSmTest(parentId,orginId,projId);
		 Map root = new HashMap();
		 root.put("result", result);
		 WriterJsonArray.writerJSONArray(root,response);
	}
	
	public void queryUserLayout(){
		String p_type = request.getParameter("p_type");
		String user_id = request.getParameter("user_id");
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(user_id);
		Map<String,Object> list = smTestService.queryUserLayout(userInfo,p_type);
		WriterJsonArray.writerJSONArray(list,response);
	}
	public void updateUserPos(){ 
		String user_id = request.getParameter("user_id");
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(user_id);
		String rows = request.getParameter("rows");
		String cols = request.getParameter("cols");
		String ids = request.getParameter("ids");
		String p_type = request.getParameter("p_type");
		String resutl = smTestService.updateUserPos(userInfo,rows,cols,ids,p_type);
		WriterJsonArray.writerJSONArray(resutl,response);
	}
	public void updateUserWidths(){ 
		String user_id = request.getParameter("user_id");
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(user_id);
		String selType = request.getParameter("selType"); 
		String p_widths = request.getParameter("p_widths");
		String p_type = request.getParameter("p_type");
		String resutl = smTestService.updateUserWidths(userInfo,selType,p_widths,p_type);
		WriterJsonArray.writerJSONArray(resutl,response);
	}
	
	public void portaletTable(){
    	String userId = request.getParameter("USER_ID");  
    	List<Map<String,Object>> result = smTestService.portaletTable(userId); 
    	WriterJsonArray.writerJSONArray(result,response);
    }
    
    public void updateUserPortalet(){
    	String userId =  request.getParameter("USER_ID"); 
    	String pId = request.getParameter("pId");
    	String checkFlag = request.getParameter("checkFlag");
    	String result = smTestService.updateUserPortalet(userId,pId,checkFlag); 
    	WriterJsonArray.writerJSONArray(result,response);
    }
    
    public void portaletRoleTable(){
    	String roleId = request.getParameter("ROLE_ID");  
    	List<Map<String,Object>> result = smTestService.portaletRoleTable(roleId); 
    	WriterJsonArray.writerJSONArray(result,response);
    }
    
    
    public String  dataTable(){
    	List<Map<String,Object>> result = smTestService.dataTable(); 	     
	    request.setAttribute("tableData", result);
	    return "success";
	}
    
    public void updateRolePortalet(){
    	String roleId =  request.getParameter("ROLE_ID"); 
    	String pId = request.getParameter("pId");
    	String checkFlag = request.getParameter("checkFlag");
    	String result = smTestService.updateRolePortalet(roleId,pId,checkFlag); 
    	WriterJsonArray.writerJSONArray(result,response);
    }
    
    public void userClosePortalet(){
    	String userId =  request.getParameter("USER_ID"); 
    	String pId = request.getParameter("pId");
    	String result = smTestService.userClosePortalet(userId,pId); 
    	WriterJsonArray.writerJSONArray(result,response);
    }
    
    public void userPortalet(){
		String p_type = request.getParameter("p_type");
		String user_id = request.getParameter("USER_ID");
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(user_id);
		List<Map<String,Object>> list = smTestService.userPortalet(userInfo,p_type);
		WriterJsonArray.writerJSONArray(list,response);
	}
    
    public void userSelPortalet(){ 
		String user_id = request.getParameter("USER_ID");
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(user_id);
		String pIds = request.getParameter("pIds"); 
		String p_type = request.getParameter("p_type");
		String resutl = smTestService.userSelPortalet(userInfo,pIds,p_type);
		WriterJsonArray.writerJSONArray(resutl,response);
	}
}
