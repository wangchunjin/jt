package com.epmis.app.service.imp;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.app.service.HrExamService;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.Base64Encrypt;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;


@Transactional
@Service("hrExamService")
public class HrExamServiceImpl implements HrExamService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	/**
	 * 查询token
	 */
	public String queryNewToken(){
		String token = "";
		
		//先查询库中是否存在两个小时内的ks系统的token，不存在则重新增加一条
		String sql = "SELECT * FROM EXAM_TOKEN WHERE SYS_TYPE='KS' AND "
				+ "(UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(CREATE_TIME))<7200";
		Map<String,Object> map = this.baseJdbcDao.queryMap(sql);
		if(!DataTypeUtil.validate(map)){
			
			token = getRandomString(138);
			
			sql = "DELETE FROM EXAM_TOKEN WHERE SYS_TYPE='KS'";
			this.baseJdbcDao.delete(sql);
			//为考试系统生成138位的token字符串
			sql = "INSERT INTO EXAM_TOKEN(WID,TOKEN_ID,TOKEN_STR,SYS_TYPE,CREATE_TIME) VALUES ("
					+ "'"+Guid.getGuid()+"',"
					+ "'"+Guid.getGuid()+"',"
					+ "'"+token+"',"
					+ "'KS',now())";
			this.baseJdbcDao.insert(sql);
		}else{
			token = DataTypeUtil.formatDbColumn(map.get("TOKEN_STR"));
		}
		
		return token;
	}
	/**
	 * 查询app版本号
	 */
	public Map<String,Object> queryAppVersion(){
		
		String sql = "SELECT VERSION,DOWN_URL FROM EXAM_APP_VERSION";
		Map<String,Object> map = this.baseJdbcDao.queryMap(sql);
		return map;
	}
	/**
	 * 验证token是否有效
	 */
	public boolean valToken(String tokenStr){
		String oldToken = queryNewToken();//查看库里当前的token
		if(tokenStr.equals(oldToken)){
			return true;
		}
		
		return false;
	}
	
	public static String getRandomString(int length) { //length表示生成字符串的长度  
		String base = "aAbBcCdDe_EfFgGhHiIj-JkKlLmMn_NoOpPqQrRsStTuUvV-wWxXyYzZ01_23456789";
		Random random = new Random();     
		StringBuffer sb = new StringBuffer();     
		for (int i = 0; i < length; i++) {     
		int number = random.nextInt(base.length());     
		sb.append(base.charAt(number));
		}
		return sb.toString();     
	}
	
	
	public List<Map<String,Object>> queryTrainList(String userId){
		String sql = "SELECT TRAIN_ID,TRAIN_NUMBER,TRAIN_NAME,TRAIN_TYPE,DATE_FORMAT(PUBLISH_DATE,'%Y-%m-%d') PUBLISH_DATE,"
				+ "START_DATE,END_DATE,(SELECT ACTUAL_NAME FROM CM_USERS WHERE USER_ID=PUBLISH_BY) PUBLISH_NAME,"
				+ "PUBLISH_BY,READ_COUNT,FILE_URL,INTRODUCTION,DETAIL_DESC,VIDEO_JPG_URL "
				+ "FROM EXAM_TRAIN WHERE TRAIN_STATUS='APPROVED' AND (TRAIN_ID IN (SELECT TRAIN_ID "
				+ "FROM EXAM_TRAIN_ROLE  WHERE RU_ID IN (SELECT RU_ID FROM EXAM_TRAIN_ROLE WHERE "
				+ "RU_ID='"+userId+"' AND R_TYPE='0' "
				+ "UNION ALL SELECT ROLE_ID FROM CM_RLUSER WHERE "
				+ "USER_ID='"+userId+"')) OR TRAIN_ID NOT "
				+ "IN (SELECT TRAIN_ID FROM EXAM_TRAIN_ROLE)) AND START_DATE<=NOW() AND "
				+ "(END_DATE>=NOW() OR END_DATE IS NULL)  ORDER BY PUBLISH_DATE DESC,TRAIN_TYPE";
		List<Map<String,Object>> list = this.baseJdbcDao.queryListMap(sql);
		for(Map<String,Object>item:list){
			String filename = getExtensionName(DataTypeUtil.formatDbColumn(item.get("FILE_URL"))).toLowerCase();
			if(item.get("TRAIN_TYPE").equals("0")){//文本
				item.put("Icon", "txtImg.png");
			}else if(item.get("TRAIN_TYPE").equals("1")){//文档
				if(filename.equals("xls")||filename.equals("xlsx")){
					item.put("Icon", "xlsImg.png");
				}else if(filename.equals("ppt")||filename.equals("pptx")){
					item.put("Icon", "pptImg.png");
				}else if(filename.equals("doc")||filename.equals("docx")){
					item.put("Icon", "docImg.png");
				}else if(filename.equals("pdf")){
					item.put("Icon", "pdfImg.png");
				}
			}else if(item.get("TRAIN_TYPE").equals("2")){//视频
				if(filename.equals("mp4")){
					//item.put("Icon", "/img/mp4Img.png");
					item.put("Icon", item.get("VIDEO_JPG_URL"));
					
				}
			}else if(item.get("TRAIN_TYPE").equals("3")){//外部链接
				item.put("Icon", "waibuImg.png");
			}
		}
		return list;
	}
	
	public static String getExtensionName(String filename) {   
		if ((filename != null) && (filename.length() > 0)) {   
			int dot = filename.lastIndexOf('.');   
			if ((dot >-1) && (dot < (filename.length() - 1))) {   
				return filename.substring(dot + 1);   
			}   
		}   
		return filename;   
	}
	
	public Map<String,Object> queryTrainDetail(String trainId){
		String sql = "SELECT TRAIN_ID,TRAIN_NUMBER,TRAIN_NAME,"
				+ "TRAIN_TYPE,DATE_FORMAT(PUBLISH_DATE,'%Y-%m-%d') PUBLISH_DATE,"
				+ "START_DATE,END_DATE,(SELECT ACTUAL_NAME FROM CM_USERS WHERE USER_ID=PUBLISH_BY) PUBLISH_NAME,"
				+ "PUBLISH_BY,READ_COUNT,FILE_URL ,INTRODUCTION,DETAIL_DESC,VIDEO_JPG_URL FROM EXAM_TRAIN "
				+ "WHERE TRAIN_ID='"+trainId+"'";
		
		return this.baseJdbcDao.queryMap(sql);
	}
	
	/**
	 * 修改用户考试信息
	 */
	public String updateUserTest(String userId,String themeId,String testId,String paperid,
			String flag,JSONArray singleAnswerRows,JSONArray multiAnswerRows,
			JSONArray judgeAnswerRows,JSONArray lsAnswerRows,String endTime){
		
		String sql = "";
		
		//flag = Base64Encrypt.decode(flag);
		
		double SINGLEPER = 0;//答对单选题分数
		double MULTIPER = 0;//答对多选题分数
		double JUDGEPER = 0; // 答对判读题分数
		
		double MULTIANSWERONE = 0;//多选题答对一项得几分
		
		String result = "error";
		try{
			//得到试卷题型分数
			sql = "SELECT * FROM EXAM_TEST WHERE TESTID='"+testId+"' AND THEME_ID='"+themeId+"'";
			Map<String,Object> map = this.baseJdbcDao.queryMap(sql);
			SINGLEPER = Double.parseDouble(DataTypeUtil.formatDbColumn(map.get("SINGLEPER")));
			MULTIPER = Double.parseDouble(DataTypeUtil.formatDbColumn(map.get("MULTIPER")));
			JUDGEPER = Double.parseDouble(DataTypeUtil.formatDbColumn(map.get("JUDGEPER")));
			MULTIANSWERONE = Double.parseDouble(DataTypeUtil.formatDbColumn(map.get("MULTIANSWERONE")));
			
			//单选题
			if(null != singleAnswerRows && !"".equals(singleAnswerRows)){
				for(int i=0;i<singleAnswerRows.size();i++){
					JSONObject jsonObj = singleAnswerRows.getJSONObject(i);
					String logId = Base64Encrypt.decode(jsonObj.getString("logId"));//对应exam_log表中的id
					String userAnswer = Base64Encrypt.decode(jsonObj.getString("userAnswer"));//用户答案
					String answer =  Base64Encrypt.decode(jsonObj.getString("answer"));//标准答案
					//System.out.println("---------"+userAnswer);
					if(!userAnswer.equals(answer)){
						sql = "UPDATE EXAM_LOG EL SET EL.SCORE=0,ANSWER='"+userAnswer+"' WHERE EL.ID='"+logId+"' ";
					}else{
						sql = "UPDATE EXAM_LOG EL SET EL.SCORE="+SINGLEPER+",ANSWER='"+userAnswer+"' WHERE EL.ID='"+logId+"' ";
					}
					this.baseJdbcDao.update(sql);
				}
			}
			
			//多选题
			if(null != multiAnswerRows && !"".equals(multiAnswerRows)){
				for(int i=0;i<multiAnswerRows.size();i++){
					JSONObject jsonObj = multiAnswerRows.getJSONObject(i);
					String logId = Base64Encrypt.decode(jsonObj.getString("logId"));//对应exam_log表中的id
					String userAnswer = Base64Encrypt.decode(jsonObj.getString("userAnswer"));//用户答案
					String answer =  Base64Encrypt.decode(jsonObj.getString("answer"));//标准答案
					//System.out.println(logId+"----"+userAnswer+"-----"+answer);
					
					double score = 0;//记录该题得分
					if(DataTypeUtil.validate(userAnswer)){
						if(userAnswer.equals(answer)){//如果相等，则表示选项全对，得该题全分
							score = MULTIPER;
						}else{//未答全或错答
							String[] userAnswers = userAnswer.split(",");
							for(String a : userAnswers){
								if(answer.indexOf(a)>=0){
									score += MULTIANSWERONE;
								}else{
									//说明答错选项，该题得0分
									score = 0;
									break;
								}
							}
						}
					}
					
					sql = "UPDATE EXAM_LOG EL SET EL.SCORE="+score+",ANSWER='"+userAnswer+"' WHERE EL.ID='"+logId+"' ";
					this.baseJdbcDao.update(sql);
				}
			}
			
			//判断题
			if(null != judgeAnswerRows && !"".equals(judgeAnswerRows)){
				for(int i=0;i<judgeAnswerRows.size();i++){
					JSONObject jsonObj = judgeAnswerRows.getJSONObject(i);
					String logId = Base64Encrypt.decode(jsonObj.getString("logId"));//对应exam_log表中的id
					String userAnswer = Base64Encrypt.decode(jsonObj.getString("userAnswer"));//用户答案
					String answer =  Base64Encrypt.decode(jsonObj.getString("answer"));//标准答案
					if(!userAnswer.equals(answer)){
						sql = "UPDATE EXAM_LOG EL SET EL.SCORE=0,ANSWER='"+userAnswer+"' WHERE EL.ID='"+logId+"' ";
					}else{
						sql = "UPDATE EXAM_LOG EL SET EL.SCORE="+JUDGEPER+",ANSWER='"+userAnswer+"' WHERE EL.ID='"+logId+"' ";
					}
					this.baseJdbcDao.update(sql);
				}
			}
			
			//论述题
			if(null != lsAnswerRows && !"".equals(lsAnswerRows)){
				System.out.println(lsAnswerRows);
				for(int i=0;i<lsAnswerRows.size();i++){
					JSONObject jsonObj = lsAnswerRows.getJSONObject(i);
					String logId = Base64Encrypt.decode(jsonObj.getString("logId"));//对应exam_log表中的id
					String userAnswer = Base64Encrypt.decode(jsonObj.getString("userAnswer"));//用户答案
					//String answer =  Base64Encrypt.decode(jsonObj.getString("answer"));//标准答案
					//String str = new String(userAnswer.getBytes("ISO8859-1"),"UTF-8");
//					System.out.println("=============================="+userAnswer);
					sql = "UPDATE EXAM_LOG EL SET EL.SCORE=0,ANSWER='"+userAnswer+"' WHERE EL.ID='"+logId+"' ";
					this.baseJdbcDao.update(sql);
				}
			}
			
			String status = "0";
			
			//统计该试卷总得分
			sql = "SELECT IFNULL(SUM(IFNULL(SCORE,0)),0) TATALSCORE FROM EXAM_LOG WHERE TESTID='"+testId+"' AND PAPERID='"+paperid+"'";
			double TATALSCORE = Double.parseDouble(DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql)));
			
			flag = DataTypeUtil.validate(flag)?flag:"0";
			
			if(flag.equals("0")){
				status = "4";
			}else if(flag.equals("1")||flag.equals("3")){
				status = "5";
			}else if(flag.equals("4")){
				sql = "UPDATE EXAM_SCORE SCORE SET SCORE='"+TATALSCORE+"',DATELINE='"+endTime+"' WHERE SCORE.USERID='"+userId+"' AND SCORE.TESTID='"+testId+"'";
				baseJdbcDao.update(sql);
				status = "1";
	 		}else if(flag.equals("5")){
	 			sql = "UPDATE EXAM_SCORE SCORE SET SCORE2='"+TATALSCORE+"',DATELINE2='"+endTime+"' WHERE SCORE.USERID='"+userId+"' AND SCORE.TESTID='"+testId+"'";
	 			baseJdbcDao.update(sql);
	 			status = "2";
	 		}
			sql = "UPDATE EXAM_TESTUSER ET SET STATUS='"+status+"' WHERE ET.USERID='"+userId+"' AND ET.TESTID='"+testId+"'";
	 		baseJdbcDao.update(sql);
	 		result = "success";
		}catch(Exception e){
			e.printStackTrace();
			result = "error";
		}
		return result;
		
	}
	
	//根据用户ID查询预发布试卷
	@Override
	public List<Map<String, Object>> getAllPublicPaper(String userid) {
		String sql = "SELECT * FROM EXAM_PUBLIC WHERE THEME_NAME IN (SELECT T3.THEME_NAME FROM EXAM_TEST T2,EXAM_THEME T3 "
				+ "WHERE T2.TESTID IN(SELECT TESTID FROM EXAM_TESTUSER T1 WHERE T1.USERID='"+userid.trim()+"') AND T2.THEME_ID=T3.THEME_ID) AND DATELINE<NOW() ";
		List<Map<String, Object>> list = baseJdbcDao.queryListMap(sql);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		for (Map<String, Object> map : list) {
			map.put("DATELINE", sd.format(map.get("DATELINE")));
			map.put("ISSHOW", "1".equals(map.get("ISSHOW"))?"是":"否");
		}
		return list;
	}
	
	/**
	 * 查询所有的已发布试卷
	 */
	@Override
	public List<Map<String, Object>> getPublicedExam(String themeName) {
		String sql = "SELECT * FROM EXAM_DATABASE D WHERE D.SUBJECT IN (SELECT SUB.EXAM_SUBJECT FROM EXAM_SUBJECT SUB WHERE "
				+ "SUB.ID IN(SELECT ETS.SUBJECT_ID FROM EXAM_THEME_SUBJECT ETS WHERE ETS.THEME_ID=(SELECT THEME.THEME_ID "
				+ "FROM EXAM_THEME THEME WHERE THEME.THEME_NAME ='"+themeName+"')))";
		List<Map<String, Object>> list = baseJdbcDao.queryListMap(sql);
		for (Map<String, Object> map : list) {
			String fileurl = "";
			fileurl = map.get("QUESTIONFILE")+"";
			if (DataTypeUtil.validate(map)&&DataTypeUtil.validate(fileurl)) {
				map.put("QUESTIONTYPE", fileurl.substring(fileurl.lastIndexOf("_")+1, fileurl.lastIndexOf(".")));
			}else{
				map.put("QUESTIONTYPE", "-1");
			}
			fileurl = map.get("TEXT1FILE")+"";
			if (DataTypeUtil.validate(map)&&DataTypeUtil.validate(fileurl)) {
				map.put("TEXT1TYPE", fileurl.substring(fileurl.lastIndexOf("_")+1, fileurl.lastIndexOf(".")));
			}else{
				map.put("TEXT1TYPE", "-1");
			}
			fileurl = map.get("TEXT2FILE")+"";
			if (DataTypeUtil.validate(map)&&DataTypeUtil.validate(fileurl)) {
				map.put("TEXT2TYPE", fileurl.substring(fileurl.lastIndexOf("_")+1, fileurl.lastIndexOf(".")));
			}else{
				map.put("TEXT2TYPE", "-1");
			}
			fileurl = map.get("TEXT3FILE")+"";
			if (DataTypeUtil.validate(map)&&DataTypeUtil.validate(fileurl)) {
				map.put("TEXT3TYPE", fileurl.substring(fileurl.lastIndexOf("_")+1, fileurl.lastIndexOf(".")));
			}else{
				map.put("TEXT3TYPE", "-1");
			}
			fileurl = map.get("TEXT4FILE")+"";
			if (DataTypeUtil.validate(map)&&DataTypeUtil.validate(fileurl)) {
				map.put("TEXT4TYPE", fileurl.substring(fileurl.lastIndexOf("_")+1, fileurl.lastIndexOf(".")));
			}else{
				map.put("TEXT4TYPE", "-1");
			}
			fileurl = map.get("TEXT5FILE")+"";
			if (DataTypeUtil.validate(map)&&DataTypeUtil.validate(fileurl)) {
				map.put("TEXT5TYPE", fileurl.substring(fileurl.lastIndexOf("_")+1, fileurl.lastIndexOf(".")));
			}else{
				map.put("TEXT5TYPE", "-1");
			}
			fileurl = map.get("TEXT6FILE")+"";
			if (DataTypeUtil.validate(map)&&DataTypeUtil.validate(fileurl)) {
				map.put("TEXT6TYPE", fileurl.substring(fileurl.lastIndexOf("_")+1, fileurl.lastIndexOf(".")));
			}else{
				map.put("TEXT6TYPE", "-1");
			}
		}
		return list;
	}
	
	@Override
	public List<Map<String,Object>> getScoreInfo(String userid, String testid) {
		String sql;
		List<Map<String, Object>> list = null;
		if (DataTypeUtil.validate(testid)) {
			sql = "SELECT T4.*,IFNULL(T5.THEME_NAME,'<font style=\"color:red;\">该试卷已删除</font>') THEME_NAME "
					+ "FROM (SELECT * FROM EXAM_SCORE WHERE USERID='"+userid+"' AND TESTID='"+testid+"') T4 "
					+ "LEFT JOIN EXAM_THEME T5 ON T4.THEME_ID=T5.THEME_ID";
		} else {
			sql = "SELECT T4.*,IFNULL(T5.THEME_NAME,'<font style=\"color:red;\">该试卷已删除</font>') THEME_NAME "
					+ "FROM (SELECT * FROM EXAM_SCORE WHERE USERID='"+userid+"') T4 LEFT JOIN EXAM_THEME T5 ON "
					+ "T4.THEME_ID=T5.THEME_ID  ORDER BY T4.DATELINE DESC";
		}
		list = baseJdbcDao.queryListMap(sql);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		for (Map<String, Object> map : list) {
			map.put("DATELINE", DataTypeUtil.validate(map.get("DATELINE"))?formatter.format(map.get("DATELINE")):"");
			map.put("DATELINE2", DataTypeUtil.validate(map.get("DATELINE2"))?formatter.format(map.get("DATELINE2")):"");
			map.put("STARTTIME", DataTypeUtil.validate(map.get("STARTTIME"))?formatter.format(map.get("STARTTIME")):"");
			map.put("ENDTIME", DataTypeUtil.validate(map.get("ENDTIME"))?formatter.format(map.get("ENDTIME")):"");
		}
		return list;
	} 
}
