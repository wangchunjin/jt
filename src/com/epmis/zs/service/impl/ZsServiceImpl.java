package com.epmis.zs.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.zs.service.ZsService;

@Transactional
@Service("ZsService")
public class ZsServiceImpl implements ZsService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllZs(String month,String pinyin,String quyu,int page, int rows) {
		String sqlwhere="";

		if(DataTypeUtil.validate(month)){
			sqlwhere=sqlwhere+" and mouth like '%"+month+"%'";
		}
		if(!"--请选择--".equals(pinyin)&&DataTypeUtil.validate(pinyin)){
			sqlwhere=sqlwhere+" and  cityname='"+pinyin+"'";
		}
		if(!"--请选择--".equals(quyu)&& DataTypeUtil.validate(pinyin)){
			sqlwhere=sqlwhere+" and  quyuname='"+quyu+"'";
		}
		int start=(page-1)*rows;
		int end=rows;
		String sql="select ids,fangxing,mouth,price,pricestr,huanbi,tongbi,cityname,quyuname,didian from  t_dftable where 1=1 "+sqlwhere+" order by ids desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
		
	}

	@Override
	public int count(String month,String pinyin,String quyu) {
		String sqlwhere="";
		
//		if(DataTypeUtil.validate(isdel)){
//			sqlwhere=sqlwhere+" and isdel="+isdel;
//		}else{
//			sqlwhere=sqlwhere+" and isdel=0";
//		}
		if(DataTypeUtil.validate(month)){
			sqlwhere=sqlwhere+" and mouth like '%"+month+"%'";
		}
		if(!"--请选择--".equals(pinyin)&&DataTypeUtil.validate(pinyin)){
			sqlwhere=sqlwhere+" and  cityname='"+pinyin+"'";
		}
		if(!"--请选择--".equals(quyu)&& DataTypeUtil.validate(pinyin)){
			sqlwhere=sqlwhere+" and  quyuname='"+quyu+"'";
		}
		String sql="select count(1) from t_dftable where 1=1 "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}
	
	
	
	

	@Override
	public List<Map<String, Object>> findAllZstb(String month, String pinyin,
			String quyu, int page, int rows) {
		String sqlwhere="";

		if(DataTypeUtil.validate(month)){
			sqlwhere=sqlwhere+" and yuefen like '%"+month+"%'";
		}
		if(!"--请选择--".equals(pinyin)&&DataTypeUtil.validate(pinyin)){
			sqlwhere=sqlwhere+" and  cityname='"+pinyin+"'";
		}
		if(!"--请选择--".equals(quyu)&& DataTypeUtil.validate(pinyin)){
			sqlwhere=sqlwhere+" and  quyuname='"+quyu+"'";
		}
		int start=(page-1)*rows;
		int end=rows;
		String sql="select ids,cityname,quyuname,yuefen,price,type from  t_dftubiao where 1=1 "+sqlwhere+" order by ids desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
		
	}

	@Override
	public int count1(String month, String pinyin, String quyu) {
		String sqlwhere="";
		
//		if(DataTypeUtil.validate(isdel)){
//			sqlwhere=sqlwhere+" and isdel="+isdel;
//		}else{
//			sqlwhere=sqlwhere+" and isdel=0";
//		}
		if(DataTypeUtil.validate(month)){
			sqlwhere=sqlwhere+" and yuefen like '%"+month+"%'";
		}
		if(!"--请选择--".equals(pinyin)&&DataTypeUtil.validate(pinyin)){
			sqlwhere=sqlwhere+" and  cityname='"+pinyin+"'";
		}
		if(!"--请选择--".equals(quyu)&& DataTypeUtil.validate(pinyin)){
			sqlwhere=sqlwhere+" and  quyuname='"+quyu+"'";
		}
		String sql="select count(1) from t_dftubiao where 1=1 "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}

}
