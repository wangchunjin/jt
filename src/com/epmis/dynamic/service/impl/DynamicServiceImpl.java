package com.epmis.dynamic.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.dynamic.service.DynamicService;
import com.epmis.dynamic.vo.Dynamic;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;

@Transactional
@Service("ProdynamicService")
public class DynamicServiceImpl implements DynamicService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	
	
	
	@Override
	public List<Map<String, Object>> findAllProdt(String isdel,String lid, int page,
			int rows) {
		String sqlwhere="";
		if(DataTypeUtil.validate(isdel)){
			sqlwhere=sqlwhere+" and isdel="+isdel;
		}else{
			sqlwhere=sqlwhere+" and isdel=0";
		}
		if(DataTypeUtil.validate(lid)){
			sqlwhere=sqlwhere+" and l_id="+lid;
		}
		
		
		int start=(page-1)*rows;
		int end=rows;
		String sql="select id,l_id,content,isdel,DATE_FORMAT(createtime,'%Y-%m-%d')createtime from  t_pro_dynamic  where 1=1 "+sqlwhere+" order by id desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
		
	}

	@Override
	public int count(String isdel,String lid) {
		String sqlwhere="";
		
		if(DataTypeUtil.validate(isdel)){
			sqlwhere=sqlwhere+" and isdel="+isdel;
		}else{
			sqlwhere=sqlwhere+" and isdel=0";
		}		
		if(DataTypeUtil.validate(lid)){
			sqlwhere=sqlwhere+" and l_id="+lid;
		}		
		String sql="select count(1) from t_pro_dynamic where 1=1 "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}

	@Override
	public String delete(String id) {
		String sql="update t_pro_dynamic set isdel='1' where id="+id;		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}
	

	@Override
	public String save(Dynamic dynamic) {
	
	String sql="INSERT INTO `t_pro_dynamic`(l_id,content) VALUES ("			
			+ "'"+dynamic.getL_id()+"'"		
			+ ",'"+dynamic.getContent()+"');";
	this.baseJdbcDao.insert(sql);
	return "success";
}

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from t_pro_dynamic where id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
		 
	}

	@Override
	public String update(Dynamic dynamic) {
	String sql="update t_pro_dynamic set "
			+ "l_id='"+dynamic.getL_id()+"',"
			+ "content='"+dynamic.getContent()+"' "
			 +" where id='"+dynamic.getId()+"'";
	this.baseJdbcDao.update(sql);
	return "success";
	
}

	
	

}
