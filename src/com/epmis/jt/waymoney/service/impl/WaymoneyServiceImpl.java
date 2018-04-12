package com.epmis.jt.waymoney.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.waymoney.service.WaymoneyService;
import com.epmis.jt.waymoney.vo.Waymoney;
import com.epmis.sys.dao.BaseJdbcDao;

@Transactional
@Service("waymoneysService")
public class WaymoneyServiceImpl implements WaymoneyService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from way_money where id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public String update(Waymoney waymoney) {
		String sql="update way_money set "
				+ "type='"+waymoney.getType()+"' "										
				 +" where id='"+waymoney.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
	}

}
