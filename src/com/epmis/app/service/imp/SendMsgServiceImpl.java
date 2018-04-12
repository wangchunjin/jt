package com.epmis.app.service.imp;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.app.service.BaiduPushService;
import com.epmis.app.service.SendMsgService;
import com.epmis.app.vo.SysAppMsg;
import com.epmis.km.service.KmDocService;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
 
@Service("sendMsgService")
public class SendMsgServiceImpl implements SendMsgService{

	  @Autowired
	  @Qualifier("baseJdbcDao")
	  private BaseJdbcDao baseJdbcDao;
     
	  @Autowired
      private KmDocService kmDocService;
	  
	  @Autowired
      private BaiduPushService baiduPushService;
	  
	  @Override
	public String SendMsg(SysAppMsg sysAppMsg) {
		  String res = "success";
		try{
		  String sql = "INSERT INTO SYS_APP_MSG(WID,ID,TITLE,CONTENT,FLAG,MSG_TYPE,TO_USERS,  CALENDAR_TIME, REMIND_TIME,CREATED_BY, CREATED_TIME,PROJ_ID,REMARK,LINK_ID)" +
				"VALUES('"+Guid.getGuid()+"','"+sysAppMsg.getId()+"','"+DataTypeUtil.formatDbColumn(sysAppMsg.getTitle())+"','"+DataTypeUtil.formatDbColumn(sysAppMsg.getContent())+"','"+sysAppMsg.getFlag()+"','"+sysAppMsg.getMsgType()+"','"+DataTypeUtil.formatDbColumn(sysAppMsg.getToUsers())+"',"+(DataTypeUtil.validate(sysAppMsg.getCalendarTime()) ? "'"+  sysAppMsg.getCalendarTime()+"'" :null )+","+(DataTypeUtil.validate(sysAppMsg.getRemindTime()) ? "'"+  sysAppMsg.getRemindTime()+"'" :null )+",'"+sysAppMsg.getCreatedBy()+"',now(),'"+DataTypeUtil.formatDbColumn(sysAppMsg.getProjId())+"','"+DataTypeUtil.formatDbColumn(sysAppMsg.getRemark())+"','"+DataTypeUtil.formatDbColumn(sysAppMsg.getLinkId())+"')";
		baseJdbcDao.insert(sql);
		String   toUsers = DataTypeUtil.formatDbColumn(sysAppMsg.getToUsers());
		String   title = DataTypeUtil.formatDbColumn(sysAppMsg.getTitle());
		if(DataTypeUtil.validate(toUsers)||sysAppMsg.getFlag().equals("1")){
			if(sysAppMsg.getFlag().equals("1")){
			     sql  = "SELECT CHANNEL_ID,PLATFORM,USER_ID,BEGIN_TIME,END_TIME,TIME_SET_ENABLED FROM SYS_APP_MSG_SET WHERE CHANNEL_ID !='-1'";				
			}else{
			     sql  = "SELECT CHANNEL_ID,PLATFORM,USER_ID,BEGIN_TIME,END_TIME,TIME_SET_ENABLED FROM SYS_APP_MSG_SET WHERE LOCATE(USER_ID,'"+toUsers+"')>0 AND CHANNEL_ID !='-1'";
			}
			List<Map<String,Object>> listMap = baseJdbcDao.queryListMap(sql);
			if(DataTypeUtil.validate(listMap)){
				String type ="";//1:沟通 2:新闻 3:公告 4:关注点 5:收发文 6:日历
				if( sysAppMsg.getMsgType().equals("1")){
					type="沟通消息";
				}else if( sysAppMsg.getMsgType().equals("2")){
					type="新闻消息";
				}else if( sysAppMsg.getMsgType().equals("3")){
					type="公告消息";
				}else if( sysAppMsg.getMsgType().equals("4")){
					type="关注消息";
				}else if( sysAppMsg.getMsgType().equals("5")){
					type="收文消息";
				}
				String channel_id = "";
				String platform="";
				String begin_time="";
				String end_time="";
				String time_set_enabled="";
				String sound="default";
				int i = 0;
				
				String  Channels="";
				for(Map<String,Object> map:listMap){
					platform = DataTypeUtil.formatDbColumn(map.get("PLATFORM"));
					channel_id = DataTypeUtil.formatDbColumn(map.get("CHANNEL_ID"));
				    begin_time = DataTypeUtil.formatDbColumn(map.get("BEGIN_TIME"));
				    end_time = DataTypeUtil.formatDbColumn(map.get("END_TIME"));
				    time_set_enabled = DataTypeUtil.formatDbColumn(map.get("TIME_SET_ENABLED"));
					if(platform.equals("1")){ //平台 1:android 2:iphone
						Channels = Channels.equals("")? channel_id : Channels+","+channel_id;
					}else if(platform.equals("2")){						
						if(time_set_enabled.equals("1")&&DataTypeUtil.validate(begin_time)&&DataTypeUtil.validate(end_time)){
				 					
							   Calendar calendar1 = Calendar.getInstance();  
							   calendar1.setTime(new Date()); 
							   calendar1.set(Calendar.HOUR_OF_DAY, Integer.valueOf(begin_time.split(":")[0]));
							   calendar1.set(Calendar.MINUTE,Integer.valueOf(begin_time.split(":")[1]));
							  
							   Calendar calendar2 = Calendar.getInstance();  
							   calendar2.setTime(new Date()); 
							   calendar2.set(Calendar.HOUR_OF_DAY, Integer.valueOf(end_time.split(":")[0]));
							   calendar2.set(Calendar.MINUTE,Integer.valueOf(end_time.split(":")[1]));
							   
							   if(calendar2.before(calendar1)){
								   calendar2.add(Calendar.DATE, 1);
							   }
							   
							   Calendar calendar3 = Calendar.getInstance();  
							   calendar3.setTime(new Date()); 
							   
							 
							 if(calendar3.after(calendar1)&&calendar3.before(calendar2)){
								 sound="";
							 }
						}						
						baiduPushService.IosPushMsg(type+":"+title, channel_id,  sysAppMsg.getMsgType(), sysAppMsg.getLinkId(), sound);
					}										
				}
				if(!Channels.equals("")){
					String[] AndroidChannels = Channels.split(",");
					baiduPushService.AndroidPushMsg(type, title, AndroidChannels, sysAppMsg.getMsgType(), sysAppMsg.getLinkId());
				}
				
			}
		}
		}catch(Exception e){
			res = e.getMessage();
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<Map<String,Object>> GetMajorListByDate(String yesterday,String userId) {
		String sql="SELECT  * FROM ("+
				   "SELECT   K.PROJ_ID,(SELECT PROJ_SHORT_NAME FROM CM_PROJ WHERE PROJ_ID =K.PROJ_ID ) PROJ_SHORT_NAME,(SELECT PROJ_NAME FROM CM_PROJ WHERE PROJ_ID =K.PROJ_ID ) PROJ_NAME,BASE_MASTER_KEY PLAN_ID ,D.PLAN_SHORT_NAME,D.PLAN_NAME , K.DOC_ID,DOC_NUMBER ,TITLE FROM KM_DOC K INNER JOIN DS_PLAN D INNER JOIN SM_SWBS W   ON K.BASE_MASTER_KEY = D.PLAN_ID  AND D.SWBS_ID = W.SWBS_ID  WHERE (BASE_MASTER_KEY IS NOT NULL AND BASE_MASTER_KEY !='') AND DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')='"+yesterday+"' AND DELETED_FLAG ='0' AND (D.IS_MAJOR = '1' OR W.IS_MAJOR = '1') "+
                   "UNION "+
                   "SELECT   K.PROJ_ID,(SELECT PROJ_SHORT_NAME FROM CM_PROJ WHERE PROJ_ID =K.PROJ_ID ) PROJ_SHORT_NAME,(SELECT PROJ_NAME FROM CM_PROJ WHERE PROJ_ID =K.PROJ_ID ) PROJ_NAME,BASE_MASTER_KEY PLAN_ID ,S.PLAN_SHORT_NAME,S.PLAN_NAME , K.DOC_ID,DOC_NUMBER ,TITLE FROM KM_DOC K INNER JOIN SM_PLAN S INNER JOIN SM_SWBS W   ON K.BASE_MASTER_KEY = S.PLAN_ID  AND S.SWBS_ID = W.SWBS_ID WHERE (BASE_MASTER_KEY IS NOT NULL AND BASE_MASTER_KEY !='') AND DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')='"+yesterday+"' AND DELETED_FLAG ='0' AND (S.IS_MAJOR = '1' OR W.IS_MAJOR = '1')"+
                   "UNION "+
                   "SELECT   K.PROJ_ID,(SELECT PROJ_SHORT_NAME FROM CM_PROJ WHERE PROJ_ID =K.PROJ_ID ) PROJ_SHORT_NAME,(SELECT PROJ_NAME FROM CM_PROJ WHERE PROJ_ID =K.PROJ_ID ) PROJ_NAME,BASE_MASTER_KEY PLAN_ID ,T.PLAN_SHORT_NAME,T.PLAN_NAME , K.DOC_ID,DOC_NUMBER ,TITLE FROM KM_DOC K INNER JOIN SM_TEST T INNER JOIN SM_SWBS W   ON K.BASE_MASTER_KEY = T.PLAN_ID  AND T.SWBS_ID = W.SWBS_ID  WHERE (BASE_MASTER_KEY IS NOT NULL AND BASE_MASTER_KEY !='') AND DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')='"+yesterday+"' AND DELETED_FLAG ='0' AND (T.IS_MAJOR = '1' OR W.IS_MAJOR = '1') "+
                   "UNION "+
                   "SELECT   K.PROJ_ID,(SELECT PROJ_SHORT_NAME FROM CM_PROJ WHERE PROJ_ID =K.PROJ_ID ) PROJ_SHORT_NAME,(SELECT PROJ_NAME FROM CM_PROJ WHERE PROJ_ID =K.PROJ_ID ) PROJ_NAME,BASE_MASTER_KEY PLAN_ID ,P.PLAN_SHORT_NAME,P.PLAN_NAME , K.DOC_ID,DOC_NUMBER ,TITLE FROM KM_DOC K INNER JOIN SYS_PLAN P INNER JOIN SM_SWBS W   ON K.BASE_MASTER_KEY = P.PLAN_ID  AND P.SWBS_ID = W.SWBS_ID  WHERE (BASE_MASTER_KEY IS NOT NULL AND BASE_MASTER_KEY !='') AND DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')='"+yesterday+"' AND DELETED_FLAG ='0' AND (P.IS_MAJOR = '1' OR W.IS_MAJOR = '1') "+
                   "ORDER BY PROJ_NAME  ,PLAN_SHORT_NAME,DOC_NUMBER"
                   + ") TT WHERE TT.PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "'))  ORDER BY PROJ_NAME ,PLAN_SHORT_NAME ASC , DOC_NUMBER ASC";
		List<Map<String,Object>>  list = baseJdbcDao.queryListMap(sql);
		if(DataTypeUtil.validate(list)){
			for(Map<String,Object> map:list){
				String docId = DataTypeUtil.formatDbColumn(map.get("DOC_ID"));
           	    String url =kmDocService.GetUrlById(map.get("DOC_ID").toString(), "");
				map.put("URL",url);  
		 			File file = new File( AppSetting.PROJECT_PATH.substring(0, AppSetting.PROJECT_PATH.lastIndexOf(AppSetting.PROJECT_NAME.substring(1)))+url);
				if(file.exists()&&file.isFile()){
					map.put("FILE_SIZE",file.length()/1024+"KB"); 
				} 
			}
		}
		return list;
	}
	    
	
	
}
