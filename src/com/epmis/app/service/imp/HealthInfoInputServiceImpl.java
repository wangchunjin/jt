package com.epmis.app.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.app.service.HealthInfoInputService;
import com.epmis.app.vo.HealthInfo;
import com.epmis.app.vo.User;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.Guid;

@Transactional
@Service("healthInfoInputService")
public class HealthInfoInputServiceImpl implements HealthInfoInputService{

	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	@Override
	public boolean addHealthInfo(HealthInfo healthInfo) {
		for (Map<String, Object> item : baseJdbcDao.queryListMap("SELECT USERNAME usna FROM AJT_USERS WHERE USERID='"+healthInfo.getUSERID()+"'")) {
			String temp = String.valueOf(item.get("usna"));
			healthInfo.setCREATEBY(temp);
			healthInfo.setUPDATEBY(temp);
			break;
		}
		String sql = String.format("INSERT INTO AJT_HEALTH VALUES('%s','%s','%s',%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,'%s','%s','%s',%s,%d)", 
					Guid.getGuid(),Guid.getGuid(),healthInfo.getUSERID(),healthInfo.getBLOODPRESS(),healthInfo.getBLOODSUGAR(),healthInfo.getBLOODRT(),
					healthInfo.getTEMPERATURE(),healthInfo.getWEIGHT(),healthInfo.getHEIGHT(),healthInfo.getCREATEBY(),healthInfo.getCREATETIME(),healthInfo.getUPDATEBY(),"now()",1);
		return baseJdbcDao.insert(sql);
	}

	@Override
	public List<Map<String, Object>> findfamily(HealthInfo healthInfo) {
		List<Map<String, Object>> userlist = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> item : baseJdbcDao.queryListMap(String.format("SELECT OTHERID FROM AJT_RELATIONS WHERE OWNID = '%s'", healthInfo.getUSERID()))) {
			String findsql = String.format("SELECT USERID,USERPIC,USERNAME,SEX,AGE,TELE FROM AJT_USERS WHERE USERID='%s'",String.valueOf(item.get("OTHERID")));
			for (Map<String,Object> temp : baseJdbcDao.queryListMap(findsql)) {
				Map<String, Object> maplist = new HashMap<String, Object>();
				maplist.put("userid", temp.get("USERID"));
				maplist.put("userpic", temp.get("USERPIC"));
				maplist.put("username", temp.get("USERNAME"));
				maplist.put("nickname", temp.get("NICKNAME"));
				maplist.put("sex", temp.get("SEX"));
				maplist.put("age", temp.get("AGE"));
				maplist.put("tele", temp.get("TELE"));
				userlist.add(maplist);
			}
		}
		return userlist;
	}

}