package com.epmis.app.service;

import java.util.List;
import java.util.Map;

import com.epmis.app.vo.SysAppMsg;

public abstract interface SendMsgService {
	public abstract String SendMsg(SysAppMsg sysAppMsg);
	public abstract List<Map<String,Object>>  GetMajorListByDate(String yesterday,String userId);
}
