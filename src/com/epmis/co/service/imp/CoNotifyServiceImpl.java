package com.epmis.co.service.imp;


import com.epmis.app.service.SendMsgService;
import com.epmis.app.thread.SendMsgThread;
import com.epmis.app.vo.SysAppMsg;
import com.epmis.co.service.CoNotifyService;
import com.epmis.co.vo.CoNotify;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("coNotifyService")
public class CoNotifyServiceImpl implements CoNotifyService
{

	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public Object getCoNotifyCount(UserInfo userInfo,String type, String title, String created,
			String beginDate, String endDate) {
		String where = "";

		if(DataTypeUtil.validate(title)){
			where = where + " AND TITLE  LIKE '%"+title+"%'";
		}
		if(DataTypeUtil.validate(created)){
			where = where + " AND CREATED_BY IN (SELECT USER_ID FROM CM_USERS WHERE ACTUAL_NAME LIKE '%"+created+"%')";
		}
		if(DataTypeUtil.validate(beginDate)){
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  > '"+beginDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+beginDate+"')  ";
		}
		if(DataTypeUtil.validate(endDate)){
			where = where +  " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  < '"+endDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+endDate+"')  ";		
		}
		String sql = "SELECT COUNT(WID) NUM FROM CO_NOTIFY WHERE (STATUS = 'APPROVED' OR CREATED_BY = '"+userInfo.getUserId()+"') "+where+"";
		return baseJdbcDao.getCount(sql);
	}

	@Override
	public List<Map<String, Object>> CoNotifyTable(UserInfo userInfo,int start, int number, String type,
			String title, String created, String beginDate, String endDate) {
		String where = "";
		if(DataTypeUtil.validate(title)){
			where = where + " AND TITLE  LIKE '%"+title+"%'";
		}
		if(DataTypeUtil.validate(created)){
			where = where + " AND CREATED_BY IN (SELECT USER_ID FROM CM_USERS WHERE ACTUAL_NAME LIKE '%"+created+"%')";
		}
		if(DataTypeUtil.validate(beginDate)){
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  > '"+beginDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+beginDate+"')  ";
		}
		if(DataTypeUtil.validate(endDate)){
			where = where +  " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  < '"+endDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+endDate+"')  ";		
		}
		String sql ="SELECT WID,NOTIFY_ID,TITLE,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d') CREATED_DATE,DATE_FORMAT(PUBLISH_DATE,'%Y-%m-%d') PUBLISH_DATE,DATE_FORMAT(BEGIN_DATE,'%Y-%m-%d') BEGIN_DATE,DATE_FORMAT(END_DATE,'%Y-%m-%d')  END_DATE,DATE_FORMAT(END_DATE,'%Y-%m-%d') < DATE_FORMAT(now(),'%Y-%m-%d')  IFCLOSE,(SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  CREATED_BY)CREATED_BY,STATUS FROM CO_NOTIFY WHERE (STATUS = 'APPROVED' OR CREATED_BY = '"+userInfo.getUserId()+"') "+where+" ORDER BY CO_NOTIFY.CREATED_DATE DESC LIMIT "+start+" ,"+number;
		return baseJdbcDao.queryListMap(sql);
	}

	@Override
	public String AddNotify(UserInfo userInfo, CoNotify coNotify) {  
		String sql = "INSERT INTO CO_NOTIFY(WID,NOTIFY_ID,TITLE,CREATED_DATE,CREATED_BY,STATUS,TO_USERS,CONTENT,BEGIN_DATE,END_DATE)VALUES(" +
				"'"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+coNotify.getTitle()+"',now(),'"+userInfo.getUserId()+"','INIT','"+coNotify.getToUsers()+"','"+coNotify.getContent().replaceAll("'", "\'").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")+"',"+(DataTypeUtil.validate(coNotify.getBeginDate())? "'"+coNotify.getBeginDate()+"'" : null )+","+(DataTypeUtil.validate(coNotify.getEndDate())? "'"+coNotify.getEndDate()+"'" : null )+")";
		baseJdbcDao.insert(sql);
		return "success";
	}

	@Override
	public Map<String, Object> GetCoNotifyInfo(String wid) {
		String sql = "SELECT WID,NOTIFY_ID,TITLE,CREATED_DATE,CREATED_BY,STATUS,DATE_FORMAT(BEGIN_DATE,'%Y-%m-%d') BEGIN_DATE,DATE_FORMAT(END_DATE,'%Y-%m-%d')  END_DATE,CONTENT,TO_USERS FROM CO_NOTIFY WHERE WID = '"+wid+"'";
		Map<String, Object> map =  baseJdbcDao.queryMap(sql);
		if(DataTypeUtil.validate(map)){
			String to_users =DataTypeUtil.formatDbColumn(map.get("TO_USERS"));
			sql = " SELECT USER_NAME,ACTUAL_NAME FROM CM_USERS WHERE USER_ID IN( '"+to_users.replaceAll(",", "','")+"')";
			String actualName="";
			List<Map<String ,Object>> list = baseJdbcDao.queryListMap(sql);
			if(DataTypeUtil.validate(list)){
				for(Map<String ,Object>mapuser:list){
					actualName = DataTypeUtil.validate(actualName) ?  actualName +"、"+DataTypeUtil.formatDbColumn(mapuser.get("ACTUAL_NAME")) : DataTypeUtil.formatDbColumn(mapuser.get("ACTUAL_NAME"));
				}
			}
			map.put("SHOW_USERS", actualName);
		}
		return map;
	}

	@Override
	public String SaveNotify(CoNotify coNotify) {
		String sql = "UPDATE CO_NOTIFY SET TITLE='"+coNotify.getTitle()+"',BEGIN_DATE= "+(DataTypeUtil.validate(coNotify.getBeginDate())? "'"+coNotify.getBeginDate()+"'" : null )+",END_DATE= "+(DataTypeUtil.validate(coNotify.getEndDate())? "'"+coNotify.getEndDate()+"'" : null )+",TO_USERS='"+coNotify.getToUsers()+"',CONTENT='"+coNotify.getContent().replaceAll("'", "\'").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>")+"' WHERE WID = '"+coNotify.getWid()+"'";
		baseJdbcDao.update(sql);
		return "success";
	}

	@Override
	public List RoleUserTree(String wid,String parentId,String roleType,String key) { 
		List<Map<String, Object>>  items = new ArrayList<Map<String, Object>>();
		if(DataTypeUtil.validate(key)){
			String sql = "SELECT '1' PARENT_ROLE_ID,USER_ID ID ,ACTUAL_NAME NAME,'2' ROLE_TYPE  FROM CM_USERS C WHERE  ACTUAL_NAME LIKE '"+key+"' AND  DISABLE_FLAG !='1' ";
			items = this.baseJdbcDao.queryListMap(sql);
			for (Map<String, Object> item : items) {
				item.put("iconCls", "icon-user");
			}
		}else{
			items = findRoleTreeBySql(wid,parentId,roleType,key);  
			for (Map<String, Object> item : items) {
				if(item.get("ROLE_TYPE").toString().equals("0")||item.get("ROLE_TYPE").toString().equals("1")){
					List<Map<String, Object>>  childerItems = findRoleTreeBySql(wid,item.get("ID").toString(),item.get("ROLE_TYPE").toString(),key);
					if (childerItems.size() > 0){
						item.put("state", "closed");	
						if(item.get("ROLE_TYPE").toString().equals("0")){
							item.put("children", RoleUserTree(wid,item.get("ID").toString(),item.get("ROLE_TYPE").toString(),key));
						}
					}
				}
				if(item.get("ROLE_TYPE").toString().equals("0")){
					item.put("iconCls", "icon-obs"); 

				}else if(item.get("ROLE_TYPE").toString().equals("1")){
					item.put("iconCls", "icon-role");
				}else{
					item.put("iconCls", "icon-user");
				}
				/*	  	String sql = " SELECT USER_NAME,ACTUAL_NAME FROM CM_USERS WHERE USER_ID IN( SELECT USER_ID FROM CM_RLUSER WHERE ROLE_ID='"+item.get("ROLE_ID").toString()+"')";
		String actualName="";
		List<Map<String ,Object>> list = baseJdbcDao.queryListMap(sql);
		if(DataTypeUtil.validate(list)){
			for(Map<String ,Object>map:list){
				actualName = DataTypeUtil.validate(actualName) ?  actualName +"、"+DataTypeUtil.formatDbColumn(map.get("ACTUAL_NAME")) : DataTypeUtil.formatDbColumn(map.get("ACTUAL_NAME"));
			}
		}*/
			}
		}
		return items;
	}
	public List<Map<String, Object>> findRoleTreeBySql(String wid ,String parentId,String userFlag,String key){

		List<Map<String, Object>> result = null;     
		String sql ="";
		if(userFlag.equals("0")){
			sql=" SELECT PARENT_ROLE_ID,ROLE_ID ID, ROLE_NAME NAME,REMARK,ROLE_TYPE FROM CM_ROLE C WHERE 1=1  AND PARENT_ROLE_ID = '"+parentId+"'  ORDER BY SEQ_NUM";		   
		}else{
			sql=" SELECT '"+parentId+"' PARENT_ROLE_ID,USER_ID ID ,ACTUAL_NAME NAME,'2' ROLE_TYPE,(select count(wid) from co_notify where LOCATE(C.USER_ID ,to_users )>0 AND WID='"+wid+"')  NUM FROM CM_USERS C WHERE USER_ID IN( SELECT USER_ID FROM CM_RLUSER WHERE ROLE_ID='"+parentId+"')AND  DISABLE_FLAG !='1' ORDER BY CONVERT( ACTUAL_NAME USING gbk ) COLLATE GBK_CHINESE_CI ASC";		       	   
		}
		result =  baseJdbcDao.queryListMap(sql); 
		return result;
	}

	@Override
	public List<Map<String, Object>> FindCoNotify(UserInfo userInfo,int start, int number, String type, String title, String created, String beginDate, String endDate) {
		List<Map<String, Object>> list = null;
		String where = "";
		if(DataTypeUtil.validate(title)){
			where = where + " AND TITLE  LIKE '%"+title+"%'";
		}
		if(DataTypeUtil.validate(created)){
			where = where + " AND CREATED_BY IN (SELECT USER_ID FROM CM_USERS WHERE ACTUAL_NAME LIKE '%"+created+"%')";
		}
		if(DataTypeUtil.validate(beginDate)){
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  > '"+beginDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+beginDate+"')  ";
		}
		if(DataTypeUtil.validate(endDate)){
			where = where +  " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  < '"+endDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+endDate+"')  ";		
		}
		String sql ="SELECT WID,NOTIFY_ID,TITLE,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d') CREATED_DATE,DATE_FORMAT(PUBLISH_DATE,'%Y-%m-%d') PUBLISH_DATE,DATE_FORMAT(BEGIN_DATE,'%Y-%m-%d') BEGIN_DATE,DATE_FORMAT(END_DATE,'%Y-%m-%d')  END_DATE,DATE_FORMAT(END_DATE,'%Y-%m-%d') < DATE_FORMAT(now(),'%Y-%m-%d')  IFCLOSE,(SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  CREATED_BY)CREATED_BY,STATUS FROM CO_NOTIFY WHERE STATUS = 'APPROVED' AND (LOCATE('"+userInfo.getUserId()+"',TO_USERS)>0 OR TO_USERS IS NULL OR TO_USERS='')  AND  DATE_FORMAT(END_DATE,'%Y-%m-%d') >= DATE_FORMAT(now(),'%Y-%m-%d') "+where+" ORDER BY CO_NOTIFY.CREATED_DATE DESC LIMIT "+start+" ,"+number;
		list = baseJdbcDao.queryListMap(sql);
		return list;
	}

	@Override
	public int FindCoNotifyCount(UserInfo userInfo,String type, String title, String created,
			String beginDate, String endDate) {
		String where = "";

		if(DataTypeUtil.validate(title)){
			where = where + " AND TITLE  LIKE '%"+title+"%'";
		}
		if(DataTypeUtil.validate(created)){
			where = where + " AND CREATED_BY IN (SELECT USER_ID FROM CM_USERS WHERE ACTUAL_NAME LIKE '%"+created+"%')";
		}
		if(DataTypeUtil.validate(beginDate)){
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  > '"+beginDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+beginDate+"')  ";
		}
		if(DataTypeUtil.validate(endDate)){
			where = where +  " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  < '"+endDate+"' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"+endDate+"')  ";		
		}
		String sql = "SELECT COUNT(WID) NUM FROM CO_NOTIFY WHERE STATUS = 'APPROVED' AND LOCATE('"+userInfo.getUserId()+"',TO_USERS)>0 "+where;
		return baseJdbcDao.getCount(sql);
	}

	@Override
	public String addco_newCount(CoNotify coNotify) {
		if (!(DataTypeUtil.validate(coNotify)&&DataTypeUtil.validate(coNotify.getWid()))) {
			return "操作对象不存在";
		}
		String sql = "select CLICK_COUNT FROM CO_NOTIFY WHERE WID = '"+coNotify.getWid()+"'";
		int count = Integer.parseInt(DataTypeUtil.formatDbColumn(baseJdbcDao.getFieldValue(sql)));
		sql = "UPDATE CO_NOTIFY SET CLICK_COUNT='"+(count+1)+"' WHERE WID = '"+coNotify.getWid()+"'";
		String result = "浏览数量加一失败";
		if (baseJdbcDao.update(sql)) {
			result = "success";
		}
		return result;
	}

	@Override
	public Map<String, Object> showNotify(String wid) {
		String sql = "select WID,NOTIFY_ID,TITLE,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d') CREATED_DATE,DATE_FORMAT(PUBLISH_DATE,'%Y-%m-%d') PUBLISH_DATE,DATE_FORMAT(BEGIN_DATE,'%Y-%m-%d') BEGIN_DATE,DATE_FORMAT(END_DATE,'%Y-%m-%d')  END_DATE,DATE_FORMAT(END_DATE,'%Y-%m-%d') < DATE_FORMAT(now(),'%Y-%m-%d')  IFCLOSE,(SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  CREATED_BY)CREATED_BY,STATUS,CLICK_COUNT,CONTENT from co_notify where wid='"+wid+"'";
		return baseJdbcDao.queryMap(sql);
	}

	@Override
	public int FindCoNotifyLinkCount(String notityid) {
		String sql = "select count(WID) from co_comment where LINK_ID='"+notityid+"' and type='NOTIFY'";
		return baseJdbcDao.getCount(sql);
	}

	@Override
	public List<Map<String, Object>> FindCoNotifyLink(String notityid, int start,
			int number) {
		String sql = null;
		if (number < 1) {
			sql = "select WID,COMMENT_ID,LINK_ID,TYPE,(SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  cm.USER_ID)CREATED_BY,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d') CREATED_DATE,CONTENT  from co_comment cm where LINK_ID='"+notityid+"' and type='NOTIFY' ORDER BY cm.CREATED_DATE DESC";
		}else{
			sql = "select WID,COMMENT_ID,LINK_ID,TYPE,(SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  cm.USER_ID)CREATED_BY,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d') CREATED_DATE,CONTENT  from co_comment cm where LINK_ID='"+notityid+"' and type='NOTIFY' ORDER BY cm.CREATED_DATE DESC limit "
					+ start + "," + number;
		}
		return baseJdbcDao.queryListMap(sql);
	}

	@Autowired
	private SendMsgService sendMsgService;
@Override
public String PushMsg(String userId, String wid) {
	
	 String sql = "SELECT NOTIFY_ID ,TITLE ,TO_USERS FROM CO_NOTIFY WHERE WID = '"+wid+"'";
	 Map<String ,Object> map =  baseJdbcDao.queryMap(sql);
	 String id = Guid.getGuid();
	 SysAppMsg sysAppMsg = new SysAppMsg();
	    sysAppMsg.setId(id);
	    sysAppMsg.setTitle(map.get("TITLE").toString()); 
	    sysAppMsg.setContent("");	   
	    sysAppMsg.setMsgType("3");
	    sysAppMsg.setToUsers(map.get("TO_USERS").toString());
	    if(DataTypeUtil.validate(map.get("TO_USERS").toString())){
	    	 sysAppMsg.setFlag("2");
	    }else{
	    	 sysAppMsg.setFlag("1");
	    }
	    sysAppMsg.setCreatedBy(userId);	
	    sysAppMsg.setLinkId(map.get("NOTIFY_ID").toString());
	    SendMsgThread SendMsgThread  =  new SendMsgThread(sysAppMsg);
	    SendMsgThread.run();
	//	String res =  sendMsgService.SendMsg(sysAppMsg);
		return "success";
	}
} 