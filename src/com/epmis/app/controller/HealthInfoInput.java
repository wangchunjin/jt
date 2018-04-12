package com.epmis.app.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epmis.app.service.HealthInfoInputService;
import com.epmis.app.vo.HealthInfo;
import com.epmis.sys.util.WriterJsonArray;

@Controller
public class HealthInfoInput {
	@Autowired
	@Qualifier("healthInfoInputService")
	private HealthInfoInputService healthInfoInputService;

	/**
	 * 录入一个新的健康信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/api/health/addhealth.do")
	public void addhealth(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		HealthInfo healthInfo = new HealthInfo();
		healthInfo.setUSERID(request.getParameter("userid"));
		healthInfo.setBLOODPRESS(Double.parseDouble(request.getParameter("bloodpress")));
		healthInfo.setBLOODSUGAR(Double.parseDouble(request.getParameter("bloodsugar")));
		healthInfo.setBLOODRT(Double.parseDouble(request.getParameter("bloodrt")));
		healthInfo.setTEMPERATURE(Double.parseDouble(request.getParameter("temperature")));
		healthInfo.setWEIGHT(Double.parseDouble(request.getParameter("weight")));
		healthInfo.setHEIGHT(Double.parseDouble(request.getParameter("height")));
		healthInfo.setCREATETIME(Timestamp.valueOf(request.getParameter("createtime") + " 0:0:0"));
		System.out.println("健康录入接口被调用\n"+healthInfo);
		if(healthInfoInputService.addHealthInfo(healthInfo)){
			resObj.put("RESULT", "1");
		}else{
			resObj.put("RESULT", "0");
		}
		WriterJsonArray.AppWriterJSONArray(resObj, response);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/api/health/findfamily.do")
	public void selecthealth(HttpServletRequest request,HttpServletResponse response) throws IOException {
		Map<String, Object> resObj = new HashMap<String, Object>();
		HealthInfo healthInfo = new HealthInfo();
		healthInfo.setUSERID(request.getParameter("userid"));
		System.out.println("查询亲属接口被调用\n");
		if((resObj.put("list", healthInfoInputService.findfamily(healthInfo))) != null){
			resObj.put("RESULT", "1");
		}else{
			resObj.put("RESULT", "0");
		}
		WriterJsonArray.AppWriterJSONArray(resObj, response);
	}
}
