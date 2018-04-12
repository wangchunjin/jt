package com.epmis.jt.crecord.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.crecord.service.CrecordService;
import com.epmis.jt.crecord.vo.Crecord;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;

@Transactional
@Service("CrecordsService")
public class CrecordServiceImpl implements CrecordService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllCrecord(String isdel,String oid, int page,
			int rows) {
		String sqlwhere="";
		if(DataTypeUtil.validate(oid)){
			sqlwhere=sqlwhere+" and order_id="+oid;
		}
		
			int start=(page-1)*rows;
			int end=rows;
			String sql="select id,cuid,order_id,content,cus_beh,(select name from rep_willingness r where r.id=c.rep_willingness)rep_willingness,(select name from comm_results t where t.id=c.comm_results)comm_results,contact_book_type,emergency_contact,contact_other_contacts,book_situation,isdel,DATE_FORMAT(createtime,'%Y-%m-%d')createtime from  connect_record c where 1=1 and isdel='0' "+sqlwhere+"  order by id desc limit "+start+","+end;
		return	this.baseJdbcDao.queryListMap(sql);}

	@Override
	public int count(String oid) {
		String sqlwhere="";
		if(DataTypeUtil.validate(oid)){
			sqlwhere=sqlwhere+" and order_id="+oid;
		}
		String sql="select count(1) from  connect_record where isdel='0' "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}

	@Override
	public String delete(String id) {
		String sql="update connect_record set isdel='1' where id="+id;		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}

	@Override
	public String save(Crecord crecord) {
//		if(this.baseJdbcDao.getCount("SELECT count(id) from comm_results where isdel='0' and name='"+commresults.getName()+"'") > 0){
//			return "输入沟通结果名称已存在";
//		}
		String sql="INSERT INTO `connect_record`(cuid,order_id,content,cus_beh,rep_willingness,comm_results,contact_book_type,emergency_contact,contact_other_contacts,book_situation) VALUES ("			
				+ "'"+crecord.getCuid()+"'"		
				+ ",'"+crecord.getOrder_id()+"'"		
				+ ",'"+crecord.getContent()+"'"	
				+ ",'"+crecord.getCus_beh()+"'"	
				+ ",'"+crecord.getRep_willingness()+"'"
				+ ",'"+crecord.getComm_results()+"'"	
				+ ",'"+crecord.getContact_book_type()+"'"		
				+ ",'"+crecord.getEmergency_contact()+"'"	
				+ ",'"+crecord.getContact_other_contacts()+"'"	
				+ ",'"+crecord.getBook_situation()+"'"	
				+ ");";
		this.baseJdbcDao.insert(sql);
		
		//修改当天逾期订单  被催收
		String sqlu="update order_info set collection='1' where order_id="+crecord.getOrder_id();
		this.baseJdbcDao.update(sqlu);
		
		return "success";
		
		
		
	}

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from connect_record where id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public String update(Crecord crecord) {
//		if(this.baseJdbcDao.getCount("SELECT count(id) from comm_results where isdel='0' and name='"+commresults.getName()+"' AND  ID <>"+commresults.getId()) > 0){
//			return "沟通结果名称重复";
//		}
		String sql="update connect_record set "		
				+ "content='"+crecord.getContent()+"' "
				+ ",cus_beh='"+crecord.getCus_beh()+"' "
				+ ",rep_willingness='"+crecord.getRep_willingness()+"' "
				+ ",comm_results='"+crecord.getComm_results()+"' "
				+ ",contact_book_type='"+crecord.getContact_book_type()+"' "
				+ ",emergency_contact='"+crecord.getEmergency_contact()+"' "
				+ ",contact_other_contacts='"+crecord.getContact_other_contacts()+"' "
				+ ",book_situation='"+crecord.getBook_situation()+"' "				
				 +" where id='"+crecord.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
	}

}
