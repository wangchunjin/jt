package com.epmis.jt.repwill.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.repwill.service.RepwillService;
import com.epmis.jt.repwill.vo.Repwill;
import com.epmis.sys.dao.BaseJdbcDao;

@Transactional
@Service("RepwillsService")
public class RepwillServiceImpl implements RepwillService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllRepwill(String isdel,int page, int rows) {
		int start=(page-1)*rows;
		int end=rows;
		String sql="select id,name,isdel,DATE_FORMAT(createtime,'%Y-%m-%d')createtime from  rep_willingness  where 1=1 and isdel='0'  order by id desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int count() {
		String sql="select count(1) from  rep_willingness where isdel='0'";
		return this.baseJdbcDao.getCount(sql);
	}

	@Override
	public String delete(String id) {
		
		
		String sql="update rep_willingness set isdel='1' where id="+id;		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}

	@Override
	public String save(Repwill repwill) {
		if(this.baseJdbcDao.getCount("SELECT count(id) from rep_willingness where isdel='0' and name='"+repwill.getName()+"'") > 0){
			return "输入还款意愿名称已存在";
		}
		String sql="INSERT INTO `rep_willingness`(name) VALUES ("			
				+ "'"+repwill.getName()+"'"				
				+ ");";
		this.baseJdbcDao.insert(sql);
		return "success";
}

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from rep_willingness where id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
		 
	}

	@Override
	public String update(Repwill repwill) {
		if(this.baseJdbcDao.getCount("SELECT count(id) from rep_willingness where isdel='0' and name='"+repwill.getName()+"' AND  ID <>"+repwill.getId()) > 0){
			return "还款意愿名称重复";
		}
		String sql="update rep_willingness set "		
				+ "name='"+repwill.getName()+"' "
				 +" where id='"+repwill.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
		
}

}
