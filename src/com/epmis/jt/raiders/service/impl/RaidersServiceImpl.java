package com.epmis.jt.raiders.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.raiders.service.RaidersService;
import com.epmis.jt.raiders.vo.Raiders;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;

@Transactional
@Service("RaidersService")
public class RaidersServiceImpl implements RaidersService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllRaiders(String isdel, int page,
			int rows) {
		String sqlwhere="";
		if(DataTypeUtil.validate(isdel)){
			sqlwhere=sqlwhere+" and isdel="+isdel;
		}else{
			sqlwhere=sqlwhere+" and isdel=0";
		}
		
		int start=(page-1)*rows;
		int end=rows;
		String sql="select id,pic,isdel,content,type,url,orderflag,DATE_FORMAT(createtime,'%Y-%m-%d')createtime from  t_raiders where 1=1 "+sqlwhere+" order by orderflag asc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
		
	}

	@Override
	public int count(String isdel) {
		String sqlwhere="";
		
		if(DataTypeUtil.validate(isdel)){
			sqlwhere=sqlwhere+" and isdel="+isdel;
		}else{
			sqlwhere=sqlwhere+" and isdel=0";
		}
		
		String sql="select count(1) from t_raiders where 1=1 "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}

	@Override
	public String delete(String id) {
		String sql="update t_raiders set isdel='1' where id="+id;
		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}
	//处理文本编辑器斜杠，拼接处理代码
			public static String replaceAll(String str,String in,String out){
				int index = 0;
				int bakindex = -1;
				while((index = str.indexOf(in,index)) != -1 && index > bakindex){
					bakindex = index;
					index++;
					str = str.substring(0,index - 1) + out + str.substring(index+in.length() - 1,str.length());
				}
				return str;
			}
	@Override
	public String save(Raiders raiders) {
//		if(this.baseJdbcDao.getCount("SELECT count(id) from t_car where isdel='0' and REPLACE(title,' ','')=REPLACE('"+car.getTitle()+"' ,' ','')") > 0){
//		return "输入车型名称已存在";
//	}
	Random ra=new Random();
	int sum=this.baseJdbcDao.getCount("SELECT count(id) from t_raiders where isdel='0'");
	if(sum==0){		
		int a=ra.nextInt(100);
		raiders.setOrderflag(Integer.toString(a));			
	}else{
		
		int countt=this.baseJdbcDao.getCount("select max(orderflag)+1 flag from t_raiders where isdel='0'");
		raiders.setOrderflag(Integer.toString(countt));
		
		
//		double i = Double.MAX_VALUE;
//		while(i == i + 1){
//			int b=ra.nextInt(100);
//			int countt=this.baseJdbcDao.getCount("SELECT count(id) from t_adv where isdel='0' and orderflag='"+b+"'");
//			if(countt==0){
//				adv.setOrderflag(Integer.toString(b));
//				break;
//			}
//				
//		}
		
		
		
	}
	String sqlwhere="";
	if(raiders.getPic()==null || raiders.getPic()==""){
		sqlwhere="'',";
	}else{
		sqlwhere="'"+raiders.getPic()+"', ";
	}
	String result2 = replaceAll(raiders.getContent(),"src=\\'", "src=\\'http://106.14.163.15:8088");
	
	String sql="INSERT INTO `t_raiders`(type,pic,content,orderflag,url) VALUES ("
			+ "'"+raiders.getType()+"', "
					+ sqlwhere
					+ "'"+result2+"',"
					+ "'"+raiders.getOrderflag()+"', "					
					+ "'"+raiders.getUrl()+"'"
					+ " );";
	this.baseJdbcDao.insert(sql);
	return "success";
}

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from t_raiders where id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
		 
	}

	@Override
	public String update(Raiders raiders) {
		String sqlwhere="";
		if(raiders.getPic()==null || raiders.getPic()==""){
			sqlwhere="";
		}else{
			sqlwhere= " pic='"+raiders.getPic()+"', ";
		}		
		String result2 = replaceAll(raiders.getContent(),"src=\\'", "src=\\'http://106.14.163.15:8088");
		String sql="update t_raiders set "
				+ "type='"+raiders.getType()+"', "
				
						+ sqlwhere
						+ " content='"+result2+"', "
						+ "url='"+raiders.getUrl()+"' "
				 +" where id='"+raiders.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
		
	}

	@Override
	public String wz(Raiders raiders) {
		String sql="update t_adv set orderflag='"+raiders.getOrderflag()+"' where id="+raiders.getId();		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}

}
