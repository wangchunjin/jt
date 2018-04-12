package com.epmis.jt.help.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.help.service.HelpService;
import com.epmis.jt.help.vo.Help;
import com.epmis.sys.dao.BaseJdbcDao;

@Transactional
@Service("HelpsService")
public class HelpServiceImpl implements HelpService{
	
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllHelp(String isdel, int page,
			int rows) {
		int start=(page-1)*rows;
		int end=rows;
		String sql="select id,title,orderflag,content,isdel,DATE_FORMAT(createtime,'%Y-%m-%d')createtime from  t_help  where 1=1 and isdel='0'  order by orderflag  limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int count() {
		String sql="select count(1) from  t_help where isdel='0'";
		return this.baseJdbcDao.getCount(sql);
	}

	@Override
	public String delete(String id) {
		String sql="update t_help set isdel='1' where id="+id;		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}

	@Override
	public String save(Help help) {
		if(this.baseJdbcDao.getCount("SELECT count(id) from t_help where isdel='0' and title='"+help.getTitle()+"'") > 0){
			return "输入帮助标题已存在";
		}
		String sql="INSERT INTO `t_help`(title,content,orderflag) VALUES ("			
				+ "'"+help.getTitle()+"'"			
				+ ",'"+help.getContent()+"'"
				+ ",'"+help.getOrderflag()+"'"
				+ ");";
		this.baseJdbcDao.insert(sql);
		return "success";
	}

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from t_help where id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public String update(Help help) {
		if(this.baseJdbcDao.getCount("SELECT count(id) from t_help where isdel='0' and title='"+help.getTitle()+"' AND  ID <>"+help.getId()) > 0){
			return "帮助标题重复";
		}
		String sql="update t_help set "		
				+ "title='"+help.getTitle()+"' "
				+ ",content='"+help.getContent()+"' "
				+ ",orderflag='"+help.getOrderflag()+"' "
				 +" where id='"+help.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
	}

}
