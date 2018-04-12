package com.epmis.jt.contract.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.contract.service.ContractService;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
@Transactional
@Service("ContractService")
public class ContractServiceImpl implements ContractService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllContract(String client_id,String order_number,String createtime,int page, int rows) {
		int start=(page-1)*rows;
		int end=rows;
		String sqlwhere="";
		if(DataTypeUtil.validate(client_id)){
			sqlwhere=sqlwhere+" and  client_id='"+client_id+"'";
		}
		if(DataTypeUtil.validate(order_number)){
			sqlwhere=sqlwhere+" and  order_number like '%"+order_number+"%'";
		}
		if(DataTypeUtil.validate(createtime)){
			sqlwhere=sqlwhere+" and  DATE_FORMAT(createtime,'%Y-%m-%d')='"+createtime+"'";
		}
		String sql="select id,client_id,client_number,order_number,contract_number,download_url,viewpdf_url,DATE_FORMAT(createtime,'%Y-%m-%d')createtime from  t_contract  where 1=1 "+sqlwhere+"   order by id desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int count(String client_id,String order_number,String createtime) {
		String sqlwhere="";
		if(DataTypeUtil.validate(client_id)){
			sqlwhere=sqlwhere+" and  client_id='"+client_id+"'";
		}
		if(DataTypeUtil.validate(order_number)){
			sqlwhere=sqlwhere+" and  order_number like '%"+order_number+"%'";
		}
		if(DataTypeUtil.validate(createtime)){
			sqlwhere=sqlwhere+" and  DATE_FORMAT(createtime,'%Y-%m-%d')='"+createtime+"'";
		}
		String sql="select count(1) from  t_contract where 1=1 "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}

}
