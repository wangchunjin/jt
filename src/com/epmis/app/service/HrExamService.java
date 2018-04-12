package com.epmis.app.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public abstract interface HrExamService {
	public abstract boolean valToken(String tokenStr);
	public abstract String queryNewToken();
	public abstract Map<String,Object> queryAppVersion();
	public abstract List<Map<String,Object>> queryTrainList(String userId);
	public abstract Map<String,Object> queryTrainDetail(String trainId);
	
	public abstract String updateUserTest(String userId,String themeId,String testId,String paperid,
			String flag,JSONArray singleAnswerRows,JSONArray multiAnswerRows,
			JSONArray judgeAnswerRows,JSONArray lsAnswerRows,String endTime);
	
	public List<Map<String, Object>> getAllPublicPaper(String userid);
	
	public List<Map<String, Object>> getPublicedExam(String themeName);
	
	public List<Map<String,Object>> getScoreInfo(String userid, String testid);
}
