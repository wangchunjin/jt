package com.epmis.app.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.app.service.AuthcodeService;
import com.epmis.app.vo.Authcode;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
@Transactional
@Service("authcodeService")
public class AuthcodeServiceImpl implements AuthcodeService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public String AddCode(Authcode authcode) {
		if(DataTypeUtil.validate(authcode)){
			String userid= Guid.getGuid();
			
			String sql1 = "INSERT INTO authcode(WID,CODEID,"
					+ "USERID,TELE,CODENUMBER,CODETIME,"
					+ "CODETYPE,CREATEBY,CREATETIME,UPDATEBY,"
					+ "UPDATETIME,SORTNUMBER)"
					+ "VALUES ('"+Guid.getGuid()+"','"+Guid.getGuid()+"',"
							+ "'"+userid+"',"
							+ "'"+(DataTypeUtil.validate(authcode.getTele())?authcode.getTele():"")+"',"
							+ "'"+(DataTypeUtil.validate(authcode.getCodenumber())?authcode.getCodenumber():"")+"',"
							+ "now(),"
							+ "'"+(DataTypeUtil.validate(authcode.getCodetype())?authcode.getCodetype():"0")+"',"
							+ "'"+(DataTypeUtil.validate(authcode.getCreateby())?authcode.getCreateby():"")+"',"
							+ "now(),"
							+ "'"+(DataTypeUtil.validate(authcode.getUpdateby())?authcode.getUpdateby():"")+"',"
							+ "now(),"
							+ "'"+(DataTypeUtil.validate(authcode.getSortnumber())?authcode.getSortnumber():"0")+"')";
			this.baseJdbcDao.insert(sql1);
			return userid;
		}
		return null;
	}

}
