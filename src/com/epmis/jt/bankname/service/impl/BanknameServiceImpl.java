package com.epmis.jt.bankname.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.bankname.service.BanknameService;
import com.epmis.jt.bankname.vo.Bankname;
import com.epmis.sys.dao.BaseJdbcDao;
@Transactional
@Service("BanknameService")
public class BanknameServiceImpl implements BanknameService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	

	@Override
	public List<Map<String, Object>> findAllBankname(String isdel, int page,
			int rows) {
		
		int start=(page-1)*rows;
		int end=rows;
		String sql="select id,code,name,code_zfb,isdel from t_bank_name where 1=1 and isdel='0'  order by id desc limit "+start+","+end;
	    return	this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int count() {
		String sql="select count(1) from  t_bank_name where isdel='0'";
		return this.baseJdbcDao.getCount(sql);
	}

	@Override
	public String delete(String id) {
		String sql="update t_bank_name set isdel='1' where id="+id;		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}

	@Override
	public String save(Bankname bankname) {
		String sql="INSERT INTO `t_bank_name`(code,name,code_zfb) VALUES ("		
				+ "'"+bankname.getCode()+"'"		
				+ ",'"+bankname.getName()+"'"
				+ ",'"+bankname.getCode_zfb()+"'"				
				+ ");";
		this.baseJdbcDao.insert(sql);
		return "success";
	}

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from t_bank_name where id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public String update(Bankname bankname) {
		String sql="update t_bank_name set "
				+ "name='"+bankname.getName()+"' "
				+ ",code='"+bankname.getCode()+"' "
				+ ",code_zfb='"+bankname.getCode_zfb()+"'"
				 +" where id='"+bankname.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
	}

}
