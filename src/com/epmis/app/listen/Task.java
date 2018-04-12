package com.epmis.app.listen;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import javax.servlet.ServletContext;
import com.epmis.app.service.SendMsgService;
import com.epmis.app.vo.SysAppMsg;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.SpringContextHolder;

public class Task extends TimerTask
{

  private static boolean isRunning = false;
  private ServletContext context = null; 
  static BaseJdbcDao baseJdbcDao =((BaseJdbcDao)SpringContextHolder.getBean("baseJdbcDao"));
  static SendMsgService sendMsgService =((SendMsgService)SpringContextHolder.getBean("sendMsgService"));
  
  public Task(ServletContext context)
  {
      this.context = context; 
  }
 
  public void run()
  {

    if(!isRunning)
    {

        isRunning = true;
        context.log("开始执行关注消息推送任务");
       try {
    	   Date dBefore = new Date();
    	   Calendar calendar = Calendar.getInstance();  
		   calendar.setTime(new Date());
		   calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		   dBefore = calendar.getTime();   //得到前一天的时间
		   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		   String yesterday = sdf.format(dBefore);    //格式化前一天
		   
	 	   String sql = "SELECT USER_ID FROM CM_USERPROJ WHERE  USER_FLAG = '1' AND PROJ_ID IN "
	 	   		+ "(SELECT K.PROJ_ID  FROM (SELECT BASE_MASTER_KEY,PROJ_ID,DELETED_FLAG,CREATED_DATE FROM KM_DOC WHERE DATE_FORMAT(CREATED_DATE, '%Y-%m-%d') = '"+yesterday+"' ) K LEFT JOIN DS_PLAN D ON K.BASE_MASTER_KEY = D.PLAN_ID LEFT JOIN SM_SWBS W     ON D.SWBS_ID = W.SWBS_ID  WHERE (BASE_MASTER_KEY IS NOT NULL AND BASE_MASTER_KEY !='') AND DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')='"+yesterday+"' AND DELETED_FLAG ='0' AND (D.IS_MAJOR = '1' OR W.IS_MAJOR = '1') "+
	 			        "UNION "+
	 	   		        "SELECT K.PROJ_ID  FROM (SELECT BASE_MASTER_KEY,PROJ_ID,DELETED_FLAG,CREATED_DATE FROM KM_DOC WHERE DATE_FORMAT(CREATED_DATE, '%Y-%m-%d') = '"+yesterday+"' ) K LEFT JOIN SM_PLAN D ON K.BASE_MASTER_KEY = D.PLAN_ID LEFT JOIN SM_SWBS W     ON D.SWBS_ID = W.SWBS_ID  WHERE (BASE_MASTER_KEY IS NOT NULL AND BASE_MASTER_KEY !='') AND DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')='"+yesterday+"' AND DELETED_FLAG ='0' AND (D.IS_MAJOR = '1' OR W.IS_MAJOR = '1') "+
	 	                "UNION "+
			                "SELECT K.PROJ_ID  FROM (SELECT BASE_MASTER_KEY,PROJ_ID,DELETED_FLAG,CREATED_DATE FROM KM_DOC WHERE DATE_FORMAT(CREATED_DATE, '%Y-%m-%d') = '"+yesterday+"' ) K LEFT JOIN SYS_PLAN D ON K.BASE_MASTER_KEY = D.PLAN_ID LEFT JOIN SM_SWBS W     ON D.SWBS_ID = W.SWBS_ID  WHERE (BASE_MASTER_KEY IS NOT NULL AND BASE_MASTER_KEY !='') AND DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')='"+yesterday+"' AND DELETED_FLAG ='0' AND (D.IS_MAJOR = '1' OR W.IS_MAJOR = '1') "+
	 	                "UNION "+
			                "SELECT K.PROJ_ID  FROM (SELECT BASE_MASTER_KEY,PROJ_ID,DELETED_FLAG,CREATED_DATE FROM KM_DOC WHERE DATE_FORMAT(CREATED_DATE, '%Y-%m-%d') = '"+yesterday+"' ) K LEFT JOIN SM_TEST D ON K.BASE_MASTER_KEY = D.PLAN_ID LEFT JOIN SM_SWBS W     ON D.SWBS_ID = W.SWBS_ID  WHERE (BASE_MASTER_KEY IS NOT NULL AND BASE_MASTER_KEY !='') AND DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')='"+yesterday+"' AND DELETED_FLAG ='0'  AND (D.IS_MAJOR = '1' OR W.IS_MAJOR = '1') )"
			          + " UNION "
			            +"SELECT USER_ID FROM CM_RLUSER WHERE ROLE_ID IN (SELECT ROLE_ID FROM CM_USERPROJ WHERE USER_FLAG = '0' AND  PROJ_ID IN "    
			          + "(SELECT K.PROJ_ID  FROM (SELECT BASE_MASTER_KEY,PROJ_ID,DELETED_FLAG,CREATED_DATE FROM KM_DOC WHERE DATE_FORMAT(CREATED_DATE, '%Y-%m-%d') = '"+yesterday+"' ) K LEFT JOIN DS_PLAN D ON K.BASE_MASTER_KEY = D.PLAN_ID LEFT JOIN SM_SWBS W     ON D.SWBS_ID = W.SWBS_ID  WHERE (BASE_MASTER_KEY IS NOT NULL AND BASE_MASTER_KEY !='') AND DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')='"+yesterday+"' AND DELETED_FLAG ='0' AND (D.IS_MAJOR = '1' OR W.IS_MAJOR = '1') "+
	 			        "UNION "+
	 	   		        "SELECT K.PROJ_ID  FROM (SELECT BASE_MASTER_KEY,PROJ_ID,DELETED_FLAG,CREATED_DATE FROM KM_DOC WHERE DATE_FORMAT(CREATED_DATE, '%Y-%m-%d') = '"+yesterday+"' ) K LEFT JOIN SM_PLAN D ON K.BASE_MASTER_KEY = D.PLAN_ID LEFT JOIN SM_SWBS W     ON D.SWBS_ID = W.SWBS_ID  WHERE (BASE_MASTER_KEY IS NOT NULL AND BASE_MASTER_KEY !='') AND DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')='"+yesterday+"' AND DELETED_FLAG ='0' AND (D.IS_MAJOR = '1' OR W.IS_MAJOR = '1') "+
	 	                "UNION "+
			                "SELECT K.PROJ_ID  FROM (SELECT BASE_MASTER_KEY,PROJ_ID,DELETED_FLAG,CREATED_DATE FROM KM_DOC WHERE DATE_FORMAT(CREATED_DATE, '%Y-%m-%d') = '"+yesterday+"' ) K LEFT JOIN SYS_PLAN D ON K.BASE_MASTER_KEY = D.PLAN_ID LEFT JOIN SM_SWBS W     ON D.SWBS_ID = W.SWBS_ID  WHERE (BASE_MASTER_KEY IS NOT NULL AND BASE_MASTER_KEY !='') AND DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')='"+yesterday+"' AND DELETED_FLAG ='0' AND (D.IS_MAJOR = '1' OR W.IS_MAJOR = '1') "+
	 	                "UNION "+
			                "SELECT K.PROJ_ID  FROM (SELECT BASE_MASTER_KEY,PROJ_ID,DELETED_FLAG,CREATED_DATE FROM KM_DOC WHERE DATE_FORMAT(CREATED_DATE, '%Y-%m-%d') = '"+yesterday+"' ) K LEFT JOIN SM_TEST D ON K.BASE_MASTER_KEY = D.PLAN_ID LEFT JOIN SM_SWBS W     ON D.SWBS_ID = W.SWBS_ID  WHERE (BASE_MASTER_KEY IS NOT NULL AND BASE_MASTER_KEY !='') AND DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')='"+yesterday+"'AND DELETED_FLAG ='0'  AND (D.IS_MAJOR = '1' OR W.IS_MAJOR = '1') ))";
         String  ToUsers ="";
	 	 List<Map<String,Object>>  list = baseJdbcDao.queryListMap(sql);
	 	 if(DataTypeUtil.validate(list)){
			for(Map<String,Object> map:list){
				ToUsers = ToUsers.equals("") ? DataTypeUtil.formatDbColumn(map.get("USER_ID")) : ToUsers+","+DataTypeUtil.formatDbColumn(map.get("USER_ID"));
			}
	 	 }
	 	if(DataTypeUtil.validate(ToUsers)){
	 	  SysAppMsg sysAppMsg = new SysAppMsg();
		    sysAppMsg.setId(Guid.getGuid());
		    sysAppMsg.setTitle(yesterday+"有更新"); 
		    sysAppMsg.setContent("");
		    sysAppMsg.setFlag("2");
		    sysAppMsg.setMsgType("4");
		    sysAppMsg.setToUsers(ToUsers);
		    sysAppMsg.setCreatedBy("7A999C1758674BC3A6A5A1EA70E1CF9F");	
		    sysAppMsg.setLinkId(yesterday);
	        String res =  sendMsgService.SendMsg(sysAppMsg);
	 	 }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        isRunning = false;
        context.log("关注消息推送任务执行结束");
    }
  }
   
}
