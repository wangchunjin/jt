package com.epmis.co.service.imp;

import com.epmis.app.service.SendMsgService;
import com.epmis.app.thread.SendMsgThread;
import com.epmis.app.vo.SysAppMsg;
import com.epmis.co.service.CoNewService;
import com.epmis.co.vo.CoNew;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("coNewService")
public class CoNewServiceImpl implements CoNewService {

	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public Object getCoNewCount(UserInfo userInfo, String type, String title,
			String created, String beginDate, String endDate) {
		String where = "";
		if (DataTypeUtil.validate(type)) {
			where = " AND TYPE='" + type + "'";
		}
		if (DataTypeUtil.validate(title)) {
			where = where + " AND TITLE  LIKE '%" + title + "%'";
		}
		if (DataTypeUtil.validate(created)) {
			where = where
					+ " AND CREATED_BY IN (SELECT USER_ID FROM CM_USERS WHERE ACTUAL_NAME LIKE '%"
					+ created + "%')";
		}
		if (DataTypeUtil.validate(beginDate)) {
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  > '"
					+ beginDate
					+ "' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"
					+ beginDate + "')  ";
		}
		if (DataTypeUtil.validate(endDate)) {
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  < '"
					+ endDate
					+ "' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"
					+ endDate + "')  ";
		}
		String sql = "SELECT COUNT(WID) NUM FROM CO_NEW WHERE (STATUS = 'APPROVED' OR CREATED_BY = '"
				+ userInfo.getUserId() + "') " + where + "";
		return baseJdbcDao.getCount(sql);
	}

	@Override
	public List<Map<String, Object>> CoNewTable(UserInfo userInfo, int start,
			int number, String type, String title, String created,
			String beginDate, String endDate) {
		String where = "";
		if (DataTypeUtil.validate(type)) {
			where = " AND TYPE='" + type + "'";
		}
		if (DataTypeUtil.validate(title)) {
			where = where + " AND TITLE  LIKE '%" + title + "%'";
		}
		if (DataTypeUtil.validate(created)) {
			where = where
					+ " AND CREATED_BY IN (SELECT USER_ID FROM CM_USERS WHERE ACTUAL_NAME LIKE '%"
					+ created + "%')";
		}
		if (DataTypeUtil.validate(beginDate)) {
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  > '"
					+ beginDate
					+ "' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"
					+ beginDate + "')  ";
		}
		if (DataTypeUtil.validate(endDate)) {
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  < '"
					+ endDate
					+ "' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"
					+ endDate + "')  ";
		}
		String sql = "SELECT WID,NEW_ID,TITLE,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d') CREATED_DATE,DATE_FORMAT(PUBLISH_DATE,'%Y-%m-%d') PUBLISH_DATE,(SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  CREATED_BY)CREATED_BY,STATUS,(SELECT CODE_NAME FROM CM_CODE D WHERE D.ID = TYPE) TYPE FROM CO_NEW WHERE (STATUS = 'APPROVED' OR CREATED_BY = '"
				+ userInfo.getUserId()
				+ "') "
				+ where
				+ " ORDER BY CO_NEW.CREATED_DATE DESC LIMIT "
				+ start
				+ " ,"
				+ number;
		return baseJdbcDao.queryListMap(sql);
	}

	@Override
	public String AddNew(UserInfo userInfo, CoNew coNew) {
		String sql = "insert into co_new(wid,new_id,title,created_date,created_by,status,type,content)values("
				+ "'"
				+ Guid.getGuid()
				+ "','"
				+ Guid.getGuid()
				+ "','"
				+ coNew.getTitle()
				+ "',now(),'"
				+ userInfo.getUserId()
				+ "','INIT','"
				+ coNew.getType()
				+ "','"
				+ coNew.getContent().replaceAll("'", "\'").replaceAll("\"",
						"&quot;").replaceAll("<", "&lt;").replaceAll(">",
						"&gt;").replaceAll("\n", "<br>") + "')";
		baseJdbcDao.insert(sql);
		return "success";
	}

	@Override
	public Map<String, Object> GetCoNewInfo(String wid) {
		String sql = "SELECT WID,NEW_ID,TITLE,CREATED_DATE,CREATED_BY,STATUS,TYPE,CONTENT FROM CO_NEW WHERE WID = '"
				+ wid + "'";
		return baseJdbcDao.queryMap(sql);
	}

	@Override
	public String SaveNew(CoNew coNew) {
		String sql = "UPDATE CO_NEW SET TITLE='"
				+ coNew.getTitle()
				+ "',TYPE='"
				+ coNew.getType()
				+ "',CONTENT='"
				+ coNew.getContent().replaceAll("'", "\'").replaceAll("\"",
						"&quot;").replaceAll("<", "&lt;").replaceAll(">",
						"&gt;").replaceAll("\n", "<br>") + "' WHERE WID = '"
				+ coNew.getWid() + "'";
		baseJdbcDao.update(sql);
		return "success";
	}

	@Override
	public String AddComment(String userId, String linkId, String Content,
			String type) {
		String sql = "INSERT INTO CO_COMMENT (WID,COMMENT_ID,LINK_ID,CREATED_DATE,USER_ID,TYPE,CONTENT)VALUES('"
				+ Guid.getGuid()
				+ "','"
				+ Guid.getGuid()
				+ "','"
				+ linkId
				+ "',now(),'"
				+ userId
				+ "','"
				+ type
				+ "','"
				+ Content.replaceAll("'", "\'").replaceAll("\"", "&quot;")
						.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
						.replaceAll("\n", "<br>") + "')";
		baseJdbcDao.insert(sql);
		return "success";
	}

	@Override
	public String DeleteComment(String commentId, String type) {
		String sql = "DELETE FROM  CO_COMMENT WHERE COMMENT_ID ='" + commentId
				+ "' AND TYPE='" + type + "'";
		baseJdbcDao.delete(sql);
		return "success";
	}

	@Override
	public List<Map<String, Object>> findco_new(int nowpage, int sizepage, String type, String title, String created, String beginDate, String endDate) {
		String where = "";
		if (DataTypeUtil.validate(type)) {
			where = " AND TYPE='" + type + "'";
		}
		if (DataTypeUtil.validate(title)) {
			where = where + " AND TITLE  LIKE '%" + title + "%'";
		}
		if (DataTypeUtil.validate(created)) {
			where = where
					+ " AND CREATED_BY IN (SELECT USER_ID FROM CM_USERS WHERE ACTUAL_NAME LIKE '%"
					+ created + "%')";
		}
		if (DataTypeUtil.validate(beginDate)) {
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  > '"
					+ beginDate
					+ "' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"
					+ beginDate + "')  ";
		}
		if (DataTypeUtil.validate(endDate)) {
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  < '"
					+ endDate
					+ "' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"
					+ endDate + "')  ";
		}
		String sql = "SELECT WID,NEW_ID,TITLE,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d') CREATED_DATE,(SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  CREATED_BY)CREATED_BY,(SELECT CODE_NAME FROM CM_CODE D WHERE D.ID = TYPE) TYPE FROM CO_NEW WHERE STATUS = 'APPROVED' "+where+" ORDER BY CO_NEW.CREATED_DATE DESC limit "
				+ (nowpage - 1) * sizepage + "," + sizepage;
		return baseJdbcDao.queryListMap(sql);
	}

	@Override
	public String addco_newCount(CoNew coNew) {
		if (!(DataTypeUtil.validate(coNew) && DataTypeUtil.validate(coNew
				.getNewId()))) {
			return "操作对象不存在";
		}
		String sql = "select CLICK_COUNT FROM CO_NEW WHERE NEW_ID = '"
				+ coNew.getNewId() + "'";
		int count = Integer.parseInt(DataTypeUtil.formatDbColumn(baseJdbcDao
				.getFieldValue(sql)));
		sql = "UPDATE CO_NEW SET CLICK_COUNT='" + (count + 1)
				+ "' WHERE NEW_ID = '" + coNew.getNewId() + "'";
		String result = "浏览数量加一失败";
		if (baseJdbcDao.update(sql)) {
			result = "success";
		}
		return result;
	}

	@Override
	public Map<String, Object> findco_newByid(CoNew coNew) {
		String sql = "select WID,NEW_ID,TITLE,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d') CREATED_DATE,(SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  CREATED_BY)CREATED_BY,(SELECT CODE_NAME FROM CM_CODE D WHERE D.ID = TYPE) TYPE,CLICK_COUNT,CONTENT,STATUS,DATE_FORMAT(PUBLISH_DATE,'%Y-%m-%d')PUBLISH_DATE FROM CO_NEW WHERE NEW_ID = '" + coNew.getNewId()
				+ "'";
		return baseJdbcDao.queryMap(sql);
	}

	@Override
	public int findco_newcount(String type, String title, String created, String beginDate, String endDate) {
		String where = "";
		if (DataTypeUtil.validate(type)) {
			where = " AND TYPE='" + type + "'";
		}
		if (DataTypeUtil.validate(title)) {
			where = where + " AND TITLE  LIKE '%" + title + "%'";
		}
		if (DataTypeUtil.validate(created)) {
			where = where
					+ " AND CREATED_BY IN (SELECT USER_ID FROM CM_USERS WHERE ACTUAL_NAME LIKE '%"
					+ created + "%')";
		}
		if (DataTypeUtil.validate(beginDate)) {
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  > '"
					+ beginDate
					+ "' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"
					+ beginDate + "')  ";
		}
		if (DataTypeUtil.validate(endDate)) {
			where = where + " AND  ( DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  < '"
					+ endDate
					+ "' OR   DATE_FORMAT(CREATED_DATE,'%Y-%m-%d')  = '"
					+ endDate + "')  ";
		}
		String sql = "SELECT count(WID) FROM CO_NEW WHERE STATUS = 'APPROVED' "+where;
		return baseJdbcDao.getCount(sql);
	}

	@Override
	public int findco_newByidLinkCount(String newid) {
		String sql = "select count(WID) from co_comment where LINK_ID='"
				+ newid + "' and type='NEWS'";
		return baseJdbcDao.getCount(sql);
	}

	@Override
	public List<Map<String, Object>> findco_newByidLink(String newid,
			int start, int number) {
		String sql = null;
		if (number < 1) {
			sql = "select WID,COMMENT_ID,LINK_ID,TYPE,(SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  cm.USER_ID)CREATED_BY,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d') CREATED_DATE,CONTENT  from co_comment cm where LINK_ID='"
					+ newid + "' and type='NEWS' ORDER BY cm.CREATED_DATE DESC";
		} else {
			sql = "select WID,COMMENT_ID,LINK_ID,TYPE,(SELECT ACTUAL_NAME FROM CM_USERS C WHERE C.USER_ID =  cm.USER_ID)CREATED_BY,DATE_FORMAT(CREATED_DATE,'%Y-%m-%d') CREATED_DATE,CONTENT  from co_comment cm where LINK_ID='"
					+ newid
					+ "' and type='NEWS' ORDER BY cm.CREATED_DATE DESC limit "
					+ start + "," + number;
		}
		return baseJdbcDao.queryListMap(sql);
	}
	  @Autowired
      private SendMsgService sendMsgService;
	@Override
	public String PushMsg(String userId, String wid) {
		
		 String sql = " SELECT NEW_ID ,TITLE FROM CO_NEW WHERE WID = '"+wid+"'";
		 Map<String ,Object> map =  baseJdbcDao.queryMap(sql);
		 String id = Guid.getGuid();
		 SysAppMsg sysAppMsg = new SysAppMsg();
		    sysAppMsg.setId(id);
		    sysAppMsg.setTitle(map.get("TITLE").toString()); 
		    sysAppMsg.setContent("");
		    sysAppMsg.setFlag("1");
		    sysAppMsg.setMsgType("2");
		    sysAppMsg.setToUsers("");
		    sysAppMsg.setCreatedBy(userId);	
		    sysAppMsg.setLinkId(map.get("NEW_ID").toString());
		    SendMsgThread SendMsgThread  =  new SendMsgThread(sysAppMsg);
		    SendMsgThread.run();
		//	String res =  sendMsgService.SendMsg(sysAppMsg);
			return "success";
	}

}