package com.epmis.sys.region.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.region.service.SysRegionService;
import com.epmis.sys.region.vo.Region;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class SysRegionAction extends ActionSupport implements ServletRequestAware{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	@Autowired
    private SysRegionService sysRegionService;
	private String parentId;
	private Region region;
	
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
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
    
	public void regionTree(){
		List<Map<String,Object>> list = sysRegionService.regionTree(this.parentId);
		
		
		for(Map<String, Object> map:list){
			
			map.put("ids", map.get("id"));
			if("0".equals(String.valueOf(map.get("around")))){
				map.put("aroundname", "--");
								
				
			}else{
				String wzsql="select AREA_NAME from CM_REGION where wid='"+String.valueOf(map.get("around"))+"'";
				List<Map<String, Object>> wzs=this.baseJdbcDao.queryListMap(wzsql);
				map.put("aroundname", wzs.get(0).get("AREA_NAME"));	
				
			}			
			
			
		
	}
		
		
		
		WriterJsonArray.writerJSONArray(list,response);
	}
	
	public void save(){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		String result = sysRegionService.save(region,userInfo);
		WriterJsonArray.writerJSONArray(result,response);
	}
	//常用标签
	public String getInfoById(){
		String wid = request.getParameter("WID");  
		Map<String,Object> result = sysRegionService.getInfoById(wid);		     
		request.setAttribute("region", result);
		return "success";
	}
	
	public String findById(){
		String ids = request.getParameter("wid");
		List<Map<String, Object>> result = this.sysRegionService.findById(ids);
		request.setAttribute("region", result);
		return "success";
		
	}
	
	
	public void update(){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("UserInfo");
		String result = sysRegionService.update(region,userInfo);
		WriterJsonArray.writerJSONArray(result,response);
	}
	
	public void updatezb(){
		
		String result = sysRegionService.updatezb(region);
		WriterJsonArray.writerJSONArray(result,response);
	}
	
	public void delete(){
		String wid = request.getParameter("wid");
		String result = sysRegionService.delete(wid);
		WriterJsonArray.writerJSONArray(result,response);
	}
	
	public void deletezb(){
		String wid = request.getParameter("wid");
		String result = sysRegionService.deletezb(wid);
		WriterJsonArray.writerJSONArray(result,response);
	}
}
