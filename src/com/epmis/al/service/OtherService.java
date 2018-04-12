package com.epmis.al.service;

import java.util.List;
import java.util.Map;

import com.epmis.al.vo.Other;


public abstract interface OtherService {
		
	public abstract List<Map<String,Object>> showOther(int page,int rows,String creattime,String hyly,String rzpc,String szdq, int hid,int rid,int sid);
	
	public abstract String addOther(Other other,String content);
	
	public abstract String delOther(String id);
	
	public abstract String updateOther(Other other,String content);
	
	public abstract String SelOther(Other other,String content);
	
	public abstract Map<String,Object> OtherById(String id);
	
	public abstract int getOtherCount();
	
	public abstract List<Map<String, Object>> showHylyName();
	
	public abstract List<Map<String, Object>> showRzPcName();
	
	public abstract List<Map<String, Object>> showSzDqName();
}
