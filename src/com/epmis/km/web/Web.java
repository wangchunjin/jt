package com.epmis.km.web;

import java.util.List;
import java.util.Map;

import com.epmis.km.service.KmDocService;
import com.epmis.sys.service.BaseJdbcService;
import com.epmis.sys.service.CodeService;
import com.epmis.sys.util.SpringContextHolder;

public class Web {
	
	public static List<Map<String, Object>> getDocType()
	{
		CodeService codeService = ((CodeService) SpringContextHolder 
				.getBean("codeService"));
		List<Map<String, Object>> dataList = codeService.SysCodeTable("DOC_TYPE", "");
		return dataList;
	} 
}
