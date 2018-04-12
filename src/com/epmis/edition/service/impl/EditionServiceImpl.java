package com.epmis.edition.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.edition.service.EditionService;
import com.epmis.edition.vo.Edition;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;

@Transactional
@Service("EditionService")
public class EditionServiceImpl implements EditionService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from t_edition where id='"+id+"'";
		List<Map<String, Object>> result = baseJdbcDao.queryListMap(sql);
		return result;
	}

	@Override
	public String update(Edition edition) {
		String sqlwhere="";
		if(edition.getAndroidURL()==null || edition.getAndroidURL()==""){
			sqlwhere="";
		}else{
			sqlwhere= " androidURL='"+edition.getAndroidURL()+"', ";
		}
//		String sqlwhere1="";
//		if(edition.getIosURL()==null || edition.getIosURL()==""){
//			sqlwhere1="";
//		}else{
//			sqlwhere1= " iosURL='"+edition.getIosURL()+"', ";
//		}
		String sql="update t_edition set "
				+ "android='"+edition.getAndroid()+"', "				
						+ sqlwhere
						+ "iosURL='"+edition.getIosURL()+"', "
						+ "ios='"+edition.getIos()+"', "
						+ "androname='"+edition.getAndroname()+"', "
						+ "iosname='"+edition.getIosname()+"', "
						+ "androidcontent='"+edition.getAndroidcontent()+"', "						
						+ "ioscontent='"+edition.getIoscontent()+"'"
				 +" where id='"+edition.getId()+"' ";
		this.baseJdbcDao.update(sql);
		return "success";
	}

	@Override
	public List<Map<String, Object>> findAlledition(String isdel,
			int page, int rows) {
		String sqlwhere="";
		
		if(DataTypeUtil.validate(isdel)){
			sqlwhere=sqlwhere+" and isdel="+isdel;
		}else{
			sqlwhere=sqlwhere+" and isdel=0";
		}
		
		int start=(page-1)*rows;
		int end=rows;
		String sql="select id,android,ios,androidcontent,androname,iosname,ioscontent,androidURL,iosURL,isdel,DATE_FORMAT(createtime,'%Y-%m-%d %H:%i:%s')createtime from t_edition where 1=1 "+sqlwhere+" order by id desc limit "+start+","+end;
		return this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int count(String isdel) {
		String sqlwhere="";
		
		if(DataTypeUtil.validate(isdel)){
			sqlwhere=sqlwhere+" and isdel="+isdel;
		}else{
			sqlwhere=sqlwhere+" and isdel=0";
		}
		
		String sql="select count(1) from t_edition where 1=1 "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}

	@Override
	public String save(Edition edition) {
		String sql="insert into t_edition(android,ios,androname,iosname,androidcontent,ioscontent,androidURL,iosURL) values("				
				+ "'"+edition.getAndroid()+"',"
				+ "'"+edition.getIos()+"',"
				+ "'"+edition.getAndroname()+"',"
				+ "'"+edition.getIosname()+"',"
				+ "'"+edition.getAndroidcontent()+"',"
				+ "'"+edition.getIoscontent()+"',"
				+ "'"+edition.getAndroidURL()+"',"
				+ "'"+edition.getIosURL()+"')";
		this.baseJdbcDao.insert(sql);
		
		return "success";
	}

	@Override
	public String delete(String id) {
		String sql="update t_edition set isdel='1' where id="+id;
		this.baseJdbcDao.delete(sql);
		return "success";
	}

}
