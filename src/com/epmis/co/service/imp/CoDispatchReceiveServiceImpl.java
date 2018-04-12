package com.epmis.co.service.imp;

import java.util.List;
import java.util.Map;

import com.epmis.app.service.SendMsgService;
import com.epmis.app.thread.SendMsgThread;
import com.epmis.app.vo.SysAppMsg;
import com.epmis.co.service.CoDispatchReceiveService;
import com.epmis.co.vo.CoDispatch;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("coDispatchReceiveService")
public class CoDispatchReceiveServiceImpl implements CoDispatchReceiveService
{

  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;

@Override
public String AddDispatch(UserInfo userInfo, CoDispatch coDispatch) {
	String dispatch_id = coDispatch.getDispatchId();
	if(!DataTypeUtil.validate(coDispatch.getDispatchId())){
		 dispatch_id = Guid.getGuid();
	}
	
	String sql = "INSERT INTO CO_DISPATCH(WID,DISPATCH_ID,TITLE,CREATED_DATE,CREATED_BY,RECEIVE_USER,CONTENT,DELETE_FLAG)VALUES(" +
    "'"+Guid.getGuid()+"','"+dispatch_id+"','"+coDispatch.getTitle()+"',now(),'"+userInfo.getUserId()+"','"+coDispatch.getReceiveUser()+"','"+coDispatch.getContent().replaceAll("'", "\'").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")+"','0')";
	baseJdbcDao.insert(sql);
	String receiveUsers = coDispatch.getReceiveUser();
	String[] users = coDispatch.getReceiveUser().split(",");
	for(int i=0; i<users.length;i++){
		sql = "INSERT INTO CO_RECEIVE(WID,RECEIVE_ID,RECEIVE_USER,READ_FLAG,DELETE_FLAG,DISPATCH_ID)VALUES(" +
				"'"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+users[i]+"','0','0','"+dispatch_id+"')";
		baseJdbcDao.insert(sql);
	}
	return "success";
}

@Override
public List<Map<String, Object>> CoDispatchTable(UserInfo userInfo, int start,
		int number, String title, String content, String received,
		String beginDate, String endDate, String deleteFlag) {
	  String where = "";
	    if(DataTypeUtil.validate(title)){
	    	where = where + " AND TITLE  LIKE '%"+title+"%'";
	    }
	    if(DataTypeUtil.validate(received)){
	    	where = where + " AND EXISTS (SELECT ACTUAL_NAME FROM CM_USERS WHERE LOCATE (USER_ID ,RECEIVE_USER)>0  AND LOCATE('"+received+"',ACTUAL_NAME)>0)";
	    }
	    if(DataTypeUtil.validate(content)){
	    	where = where + " AND  LOCATE('"+content+"',CONTENT)>0 ";
	    }
		if(DataTypeUtil.validate(beginDate)){
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  > '"+beginDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+beginDate+"')  ";
		}
		if(DataTypeUtil.validate(endDate)){
			where = where +  " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  < '"+endDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+endDate+"')  ";		
		}
		String sql ="SELECT DISPATCH_ID,TITLE,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d %H:%i:%s') CREATED_DATE, (SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  CREATED_BY)CREATED_BY,RECEIVE_USER FROM CO_DISPATCH WHERE CO_DISPATCH.CREATED_BY ='"+userInfo.getUserId()+"' AND DELETE_FLAG ='"+deleteFlag+"' "+where+" ORDER BY CO_DISPATCH.CREATED_DATE DESC LIMIT "+start+" ,"+number;	
		List<Map<String ,Object>> list = baseJdbcDao.queryListMap(sql);
		String userSql = "";
		if(DataTypeUtil.validate(list)){
			for(Map<String ,Object>map:list){
				String show_name="";
				String receive_user = map.get("RECEIVE_USER").toString();
				userSql =  "SELECT ACTUAL_NAME FROM CM_USERS WHERE USER_ID IN ('"+receive_user.replaceAll(",", "','")+"')";
				List<Map<String ,Object>> listuser = baseJdbcDao.queryListMap(userSql);
				if(DataTypeUtil.validate(listuser)){
					for(Map<String ,Object>mapuser:listuser){
						show_name = DataTypeUtil.validate(show_name) ?  show_name +"、"+DataTypeUtil.formatDbColumn(mapuser.get("ACTUAL_NAME")) : DataTypeUtil.formatDbColumn(mapuser.get("ACTUAL_NAME"));						
					}
				}
				map.put("RECEIVE_USER", show_name);
			}
		}
		return list;
}

@Override
public Object getCoDispatchCount(UserInfo userInfo, String title,
		String content, String received, String beginDate, String endDate, String deleteFlag) {
	String where = "";
    if(DataTypeUtil.validate(title)){
    	where = where + " AND TITLE  LIKE '%"+title+"%'";
    }
    if(DataTypeUtil.validate(received)){
    	where = where + " AND EXISTS (SELECT ACTUAL_NAME FROM CM_USERS WHERE LOCATE (USER_ID ,RECEIVE_USER)>0  AND LOCATE('"+received+"',ACTUAL_NAME)>0)";
    }
    if(DataTypeUtil.validate(content)){
    	where = where + " AND  LOCATE('"+content+"',CONTENT)>0 ";
    }
	if(DataTypeUtil.validate(beginDate)){
		where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  > '"+beginDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+beginDate+"')  ";
	}
	if(DataTypeUtil.validate(endDate)){
		where = where +  " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  < '"+endDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+endDate+"')  ";		
	}
	String sql ="SELECT COUNT(WID) NUM  FROM CO_DISPATCH WHERE CO_DISPATCH.CREATED_BY ='"+userInfo.getUserId()+"' AND DELETE_FLAG ='"+deleteFlag+"' "+where+" ORDER BY CO_DISPATCH.CREATED_DATE DESC";
return baseJdbcDao.getCount(sql);
}

@Override
public Map<String, Object> OpenDispatch(String dispatchId) {
	String sql = "select DISPATCH_ID,TITLE,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d %H:%i:%s') CREATED_DATE, (SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  CREATED_BY)CREATED_BY,CONTENT FROM CO_DISPATCH WHERE DISPATCH_ID='"+dispatchId+"'";
	Map<String,Object> map = baseJdbcDao.queryMap(sql);
	return map;
}

@Override
public List<Map<String, Object>> getReceiveInfoByDispatchId(String dispatchId) {
	String sql = "SELECT (SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  RECEIVE_USER)RECEIVE_USER,DATE_FORMAT(READ_DATE,'%Y-%m-%d %H:%i:%s') READ_DATE FROM CO_RECEIVE WHERE DISPATCH_ID = '"+dispatchId+"'";
	List<Map<String,Object>> listmap = baseJdbcDao.queryListMap(sql);
	if(DataTypeUtil.validate(listmap)){
		for(Map<String ,Object>map:listmap){
		  if(!DataTypeUtil.validate(map.get("READ_DATE"))){
			  map.put("READ_DATE", "未阅读");
		  }else{
			  map.put("READ_DATE", "在"+map.get("READ_DATE")+"阅读此文");
		  }
		}
	}
	return listmap;
}

@Override
public List<Map<String, Object>> CoReceiveTable(UserInfo userInfo, int start,
		int number, String title, String content, String created,
		String beginDate, String endDate, String deleteFlag) {
	   String where ="";
	   if(DataTypeUtil.validate(title)){
	    	where = where + " AND TITLE  LIKE '%"+title+"%'";
	    }
	    if(DataTypeUtil.validate(created)){
	    	where = where + "  AND CREATED_BY IN (SELECT USER_ID FROM CM_USERS WHERE ACTUAL_NAME LIKE '%"+created+"%')";
	    }
	    if(DataTypeUtil.validate(content)){
	    	where = where + " AND  LOCATE('"+content+"',CONTENT)>0 ";
	    }
		if(DataTypeUtil.validate(beginDate)){
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  > '"+beginDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+beginDate+"')  ";
		}
		if(DataTypeUtil.validate(endDate)){
			where = where +  " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  < '"+endDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+endDate+"')  ";		
		}
		String sql ="SELECT DISPATCH_ID,TITLE,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d %H:%i:%s') CREATED_DATE, (SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  CREATED_BY)CREATED_NAME,CREATED_BY,RECEIVE_USER,(SELECT MAX(RECEIVE_ID) FROM CO_RECEIVE C WHERE C.DISPATCH_ID = CO_DISPATCH.DISPATCH_ID  AND RECEIVE_USER ='"+userInfo.getUserId()+"' ) RECEIVE_ID,(SELECT MAX(READ_FLAG) FROM CO_RECEIVE C WHERE C.DISPATCH_ID = CO_DISPATCH.DISPATCH_ID  AND RECEIVE_USER ='"+userInfo.getUserId()+"' ) READ_FLAG  FROM CO_DISPATCH WHERE DISPATCH_ID IN (SELECT DISPATCH_ID FROM CO_RECEIVE WHERE RECEIVE_USER ='"+userInfo.getUserId()+"' AND DELETE_FLAG = '"+deleteFlag+"')   "+where+"   ORDER BY CO_DISPATCH.CREATED_DATE DESC LIMIT "+start+" ,"+number;	

	return baseJdbcDao.queryListMap(sql);
}

@Override
public Object getCoReceiveCount(UserInfo userInfo, String title,
		String content, String created, String beginDate, String endDate, String deleteFlag) {
	   String where ="";
	   if(DataTypeUtil.validate(title)){
	    	where = where + " AND TITLE  LIKE '%"+title+"%'";
	    }
	    if(DataTypeUtil.validate(created)){
	    	where = where + "  AND CREATED_BY IN (SELECT USER_ID FROM CM_USERS WHERE ACTUAL_NAME LIKE '%"+created+"%')";
	    }
	    if(DataTypeUtil.validate(content)){
	    	where = where + " AND  LOCATE('"+content+"',CONTENT)>0 ";
	    }
		if(DataTypeUtil.validate(beginDate)){
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  > '"+beginDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+beginDate+"')  ";
		}
		if(DataTypeUtil.validate(endDate)){
			where = where +  " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  < '"+endDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+endDate+"')  ";		
		}
		String sql ="SELECT COUNT(DISPATCH_ID) NUM FROM CO_DISPATCH WHERE DISPATCH_ID IN (SELECT DISPATCH_ID FROM CO_RECEIVE WHERE RECEIVE_USER ='"+userInfo.getUserId()+"' AND DELETE_FLAG = '"+deleteFlag+"')   "+where+"   ORDER BY CO_DISPATCH.CREATED_DATE DESC ";	
		return baseJdbcDao.getCount(sql);
}

@Autowired
private SendMsgService sendMsgService;
@Override
public String PushMsg(String userId, String dispatchId) {
	String sql = " SELECT DISPATCH_ID ,TITLE,RECEIVE_USER FROM CO_DISPATCH WHERE DISPATCH_ID = '"+dispatchId+"'";
	 Map<String ,Object> map =  baseJdbcDao.queryMap(sql);
	 String id = Guid.getGuid();
	 SysAppMsg sysAppMsg = new SysAppMsg();
	    sysAppMsg.setId(id);
	    sysAppMsg.setTitle(map.get("TITLE").toString()); 
	    sysAppMsg.setContent("");
	    sysAppMsg.setFlag("2");
	    sysAppMsg.setMsgType("5");
	    sysAppMsg.setToUsers(map.get("RECEIVE_USER").toString());
	    sysAppMsg.setCreatedBy(userId);	
	    sysAppMsg.setLinkId(map.get("DISPATCH_ID").toString());
	    SendMsgThread SendMsgThread  =  new SendMsgThread(sysAppMsg);
	    SendMsgThread.run();
	    //String res =  sendMsgService.SendMsg(sysAppMsg);
		return "success";
}

} 