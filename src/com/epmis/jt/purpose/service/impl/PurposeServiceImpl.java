package com.epmis.jt.purpose.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.purpose.service.PurposeService;
import com.epmis.jt.purpose.vo.Purpose;
import com.epmis.sys.dao.BaseJdbcDao;

@Transactional
@Service("PurposesService")
public class PurposeServiceImpl implements PurposeService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllPurpose(String isdel, int page,
			int rows) {
		int start=(page-1)*rows;
		int end=rows;
		String sql="select id,name,isdel,DATE_FORMAT(createtime,'%Y-%m-%d')createtime from  t_purpose  where 1=1 and isdel='0'  order by id desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int count() {
		String sql="select count(1) from  t_purpose where isdel='0'";
		return this.baseJdbcDao.getCount(sql);
	}

	@Override
	public String delete(String id) {
		
		
		String sql="update t_purpose set isdel='1' where id="+id;		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}

	@Override
	public String save(Purpose purpose) {
		if(this.baseJdbcDao.getCount("SELECT count(id) from t_purpose where isdel='0' and name='"+purpose.getName()+"'") > 0){
			return "输入借款用途名称已存在";
		}
		String sql="INSERT INTO `t_purpose`(name) VALUES ("			
				+ "'"+purpose.getName()+"'"				
				+ ");";
		this.baseJdbcDao.insert(sql);
		return "success";
}

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from t_purpose where id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
		 
	}

	@Override
	public String update(Purpose purpose) {
		if(this.baseJdbcDao.getCount("SELECT count(id) from t_purpose where isdel='0' and name='"+purpose.getName()+"' AND  ID <>"+purpose.getId()) > 0){
			return "借款用途名称重复";
		}
		String sql="update t_purpose set "		
				+ "name='"+purpose.getName()+"' "
				 +" where id='"+purpose.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
		
}

}
