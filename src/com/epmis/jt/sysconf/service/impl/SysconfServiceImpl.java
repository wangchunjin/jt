package com.epmis.jt.sysconf.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.sysconf.service.SysconfService;
import com.epmis.jt.sysconf.vo.Sysconf;
import com.epmis.sys.dao.BaseJdbcDao;


@Transactional
@Service("SysconfsService")
public class SysconfServiceImpl implements SysconfService{
	
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllSysconf(int page, int rows) {
		
		int start=(page-1)*rows;
		int end=rows;
		String sql="select id,param,value,comment,DATE_FORMAT(createtime,'%Y-%m-%d')createtime from  sys_conf  where 1=1  order by id desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
		
	}

	@Override
	public int count() {		
		String sql="select count(1) from sys_conf ";
		return this.baseJdbcDao.getCount(sql);
	}

	@Override
	public String delete(String id) {
		String sql="delete from sys_conf where id="+id;		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}

	@Override
	public String save(Sysconf sysconf) {
		
		String sql="INSERT INTO `sys_conf`(param,value,comment) VALUES ("			
				+ "'"+sysconf.getParam()+"'"
				+ ",'"+sysconf.getValue()+"'"
				+ ",'"+sysconf.getComment()+"');";
		this.baseJdbcDao.insert(sql);
		return "success";
}

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from sys_conf where id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
		 
	}

	@Override
	public String update(Sysconf sysconf) {
		String sql="update sys_conf set "
				+ "param='"+sysconf.getParam()+"',"
				+ "value='"+sysconf.getValue()+"',"
				+ "comment='"+sysconf.getComment()+"' "
				 +" where id='"+sysconf.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
		
}

}
