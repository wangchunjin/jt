package com.epmis.sysm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sysm.service.SysmService;
import com.epmis.sysm.vo.Sysm;

@Transactional
@Service("SysmService")
public class SysmServiceImpl implements SysmService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllSysmcar(String title, String isdel,
			int page, int rows) {
		String sqlwhere="";
		if(DataTypeUtil.validate(title)){
			sqlwhere=sqlwhere+" and title like '%"+title+"%'";
		}
		if(DataTypeUtil.validate(isdel)){
			sqlwhere=sqlwhere+" and isduqu="+isdel;
		}else{
			sqlwhere=sqlwhere+" and isduqu=0";
		}
		
		int start=(page-1)*rows;
		int end=rows;
		String sql="select 	id,(select title from t_car c where c.id=b.oid)oid,title,content,(select nickname from t_user u where u.id=b.uid)uid,status,type,DATE_FORMAT(c_time,'%Y-%m-%d %H:%i:%s')c_time,isduqu from t_systemmessage b " 
					
				
				+ "where 1=1 and type='1' and status='0'  "+sqlwhere+" order by id desc limit "+start+","+end;
		return this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int count(String title, String isdel) {
		String sqlwhere="";
		if(DataTypeUtil.validate(title)){
			sqlwhere=sqlwhere+" and title like '%"+title+"%'";
		}
		if(DataTypeUtil.validate(isdel)){
			sqlwhere=sqlwhere+" and isduqu="+isdel;
		}else{
			sqlwhere=sqlwhere+" and isduqu=0";
		}
		
		String sql="select count(1) from t_systemmessage where 1=1 and type='1' and status='0' "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}

	//新增有关车型的消息并推送
	@Override
	public String save(Sysm sysm) {
		
		

		String sql="insert into t_systemmessage(oid,title,content,uid,status,type,isquntui,isduqu) values("
				+ "'"+sysm.getOid()+"',"
				+ "'"+sysm.getTitle()+"',"
				+ "'"+sysm.getContent()+"',"
				+ "'"+sysm.getUid()+"',"
				+ "'"+sysm.getStatus()+"',"
				+ "'"+sysm.getType()+"',"
				+ "'1',"
				+ "'"+sysm.getIsduqu()+"')";
		this.baseJdbcDao.insert(sql);
		
		return "success";
	}

	@Override
	public String delete(String id) {
//		String sql="update t_systemmessage set isduqu='1' where id="+id;
//		this.baseJdbcDao.delete(sql);
//		return "success";
		return null;
	}

	@Override
	public List<Map<String, Object>> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Sysm sysm) {
		// TODO Auto-generated method stub
		return null;
	}

}
