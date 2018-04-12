package com.epmis.jt.clientinfo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.clientinfo.service.ClientinfoService;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;

@Transactional
@Service("ClientinfosService")
public class ClientinfoServiceImpl implements ClientinfoService{
	
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllClientinfo(String mobile,int page, int rows){
		String sqlwhere="";
		if(DataTypeUtil.validate(mobile)){
			sqlwhere=sqlwhere+" and  mobile like '%"+mobile+"%'";
		}
		int start=(page-1)*rows;
		int end=rows;
		String sql="select client_id,name,pic,mobile,idcard,gender,DATE_FORMAT(birth_date,'%Y-%m-%d')birth_date,province,city,town,home_addr,bank_phone,bank_card,company_name,company_addr,qq,email,contact1_name,contact1_relation,contact1_mobile,contact1_addr,contact2_name,contact2_relation,contact2_mobile,contact2_addr,occupation,income,vechile_status,education,social_scurity,marital_status,accomodation,check_status_basic,check_status_idcard,check_status_bankcard,chech_status_carrier,auth_status,isdel,DATE_FORMAT(createtime,'%Y-%m-%d')createtime from  client_info  where 1=1 and isdel='0' "+sqlwhere+"  order by client_id desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
	}
	@Override
	public int count(String mobile){
		String sqlwhere="";
		if(DataTypeUtil.validate(mobile)){
			sqlwhere=sqlwhere+" and  mobile like '%"+mobile+"%'";
		}
		String sql="select count(1) from client_info where isdel='0' "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}
	@Override
	public List<Map<String, Object>> findById(String id){
		String sql="select * from client_info where client_id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
	}

}
