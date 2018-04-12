package com.epmis.app.service;

import java.util.List;
import java.util.Map;

import com.epmis.app.vo.HealthInfo;
import com.epmis.app.vo.User;

public interface HealthInfoInputService {
	
	public boolean addHealthInfo(HealthInfo healthInfo);
	
	public List<Map<String, Object>> findfamily(HealthInfo healthInfo);
	
}

