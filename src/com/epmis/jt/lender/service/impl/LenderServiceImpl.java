package com.epmis.jt.lender.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.lender.service.LenderService;
import com.epmis.jt.lender.vo.Lender;
import com.epmis.sys.dao.BaseJdbcDao;

@Transactional
@Service("LendersService")
public class LenderServiceImpl implements LenderService{
	
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllLender(String isdel, int page,
			int rows) {
		int start=(page-1)*rows;
		int end=rows;
		String sql="select lender_id,todey_money,name,money,idcard,mobile,type,DATE_FORMAT(createtime,'%Y-%m-%d')createtime from  lender  where 1=1 and isdel='0'  order by lender_id desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int count() {
		String sql="select count(1) from  lender where isdel='0'";
		return this.baseJdbcDao.getCount(sql);
	}

	@Override
	public String delete(String id) {
		String sql="update lender set isdel='1' where lender_id="+id;		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}

	@Override
	public String save(Lender lender) {
		if(this.baseJdbcDao.getCount("SELECT count(lender_id) from lender where isdel='0' and name='"+lender.getName()+"'") > 0){
			return "新增出借人姓名已存在";
		}
		String sql="INSERT INTO `lender`(name,idcard,mobile,money) VALUES ("		
				+ "'"+lender.getName()+"'"			
				+ ",'"+lender.getIdcard()+"'"
				+ ",'"+lender.getMobile()+"'"
				+ ",'"+lender.getMoney()+"'"
				+ ");";
		this.baseJdbcDao.insert(sql);
		return "success";
	}

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from lender where lender_id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public String update(Lender lender) {
		if(this.baseJdbcDao.getCount("SELECT count(lender_id) from lender where isdel='0' and name='"+lender.getName()+"' AND  lender_id <>"+lender.getLender_id()) > 0){
			return "出借人姓名重复";
		}
		String sql="update lender set "		
				+ "name='"+lender.getName()+"' "
				+ ",idcard='"+lender.getIdcard()+"' "
				+ ",mobile='"+lender.getMobile()+"' "
				+ ",money='"+lender.getMoney()+"' "
				 +" where lender_id='"+lender.getLender_id()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
	}

}
