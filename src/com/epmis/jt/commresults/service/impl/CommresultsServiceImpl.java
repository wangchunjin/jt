package com.epmis.jt.commresults.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.commresults.service.CommresultService;
import com.epmis.jt.commresults.vo.Commresults;
import com.epmis.sys.dao.BaseJdbcDao;

@Transactional
@Service("CommresultsService")
public class CommresultsServiceImpl implements CommresultService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllCommresults(String isdel, int page,
			int rows) {
		int start=(page-1)*rows;
		int end=rows;
		String sql="select id,name,isdel,DATE_FORMAT(createtime,'%Y-%m-%d')createtime from  comm_results  where 1=1 and isdel='0'  order by id desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int count() {
		String sql="select count(1) from  comm_results where isdel='0'";
		return this.baseJdbcDao.getCount(sql);
	}

	@Override
	public String delete(String id) {
		String sql="update comm_results set isdel='1' where id="+id;		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}

	@Override
	public String save(Commresults commresults) {
		if(this.baseJdbcDao.getCount("SELECT count(id) from comm_results where isdel='0' and name='"+commresults.getName()+"'") > 0){
			return "输入沟通结果名称已存在";
		}
		String sql="INSERT INTO `comm_results`(name) VALUES ("			
				+ "'"+commresults.getName()+"'"				
				+ ");";
		this.baseJdbcDao.insert(sql);
		return "success";
	}

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from comm_results where id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public String update(Commresults commresults) {
		if(this.baseJdbcDao.getCount("SELECT count(id) from comm_results where isdel='0' and name='"+commresults.getName()+"' AND  ID <>"+commresults.getId()) > 0){
			return "沟通结果名称重复";
		}
		String sql="update comm_results set "		
				+ "name='"+commresults.getName()+"' "
				 +" where id='"+commresults.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
	}

}
