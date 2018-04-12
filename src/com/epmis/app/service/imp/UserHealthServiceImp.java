package com.epmis.app.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.epmis.app.service.UserHealthService;
import com.epmis.app.vo.User;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
@Service("userHealthService")
public class UserHealthServiceImp implements UserHealthService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public Map<String, Object> queryHealth(String userid) {
		String sql = " SELECT WID,HEALTHID,USERID,"
				+ " BLOODPRESS,BLOODSUGAR,BLOODRT,"
				+ " TEMPERATURE,WEIGHT,HEIGHT,"
				+ " CREATEBY  , DATE_FORMAT(CREATETIME,'%Y-%m-%d %H:%i:%s') CREATETIME,"
				+ " UPDATEBY ,DATE_FORMAT(UPDATETIME,'%Y-%m-%d %H:%i:%s') UPDATETIME,SORTNUMBER"
				+ " FROM  `health` WHERE USERID='"+userid+"'";
		Map<String, Object> map = baseJdbcDao.queryMap(sql);
		return map;
	}

	@Override
	public boolean addHealth(String userid,String bloodpress, 
			String bloodsugar,String bloodrt, String temperature,
			String weight, String heigt) {
		    
		String sql = "INSERT INTO  `HEALTH` (WID,HEALTHID,USERID,"
				+ " BLOODPRESS,BLOODSUGAR,BLOODRT,TEMPERATURE,WEIGHT,HEIGHT,"
				+ " CREATEBY,CREATETIME,UPDATEBY,UPDATETIME,SORTNUMBER)"
				+ " VALUES ('"+Guid.getGuid()+"','"+Guid.getGuid()+"','"+userid+"',"
				+ "'"+bloodpress+"','"+bloodsugar+"','"+bloodrt+"',"
				+ " '"+temperature+"','"+weight+"','"+heigt+"',"
				+ " '"+DataTypeUtil.formatDbColumn(null)+"',now(),"
				+ "'"+DataTypeUtil.formatDbColumn(null)+"',now(),'"+1+"')";
		
		
		 
		return 	baseJdbcDao.insert(sql);
		

	}

	
	
}



