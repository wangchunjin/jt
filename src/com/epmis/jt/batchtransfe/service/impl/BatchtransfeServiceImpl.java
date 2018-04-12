package com.epmis.jt.batchtransfe.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.batchtransfe.service.BatchtransfeService;
import com.epmis.jt.batchtransfe.vo.Batchtransfer;
import com.epmis.sys.dao.BaseJdbcDao;

@Transactional
@Service("BatchtransfersService")
public class BatchtransfeServiceImpl  implements BatchtransfeService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllBatch(String isdel, int page,
			int rows) {
		int start=(page-1)*rows;
		int end=rows;
		String sql="select batch_id,operator_id,down_time,down_file,down_orders,down_amount,lender_id,up_time,up_file,up_orders,up_amount,balance_ordersbatch_status,isdel,DATE_FORMAT(createtime,'%Y-%m-%d')createtime from  batch_transfer  where 1=1 and isdel='0'  order by batch_id desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int count() {
		String sql="select count(1) from  batch_transfer where isdel='0'";
		return this.baseJdbcDao.getCount(sql);
	}

	@Override
	public String delete(String id) {
		String sql="update batch_transfer set isdel='1' where batch_id="+id;		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}

	@Override
	public String save(Batchtransfer batchtransfer) {

		String sql="INSERT INTO `batch_transfer`(operator_id,down_time,down_file,down_orders,down_amount,lender_id,batch_status,bd) VALUES ("			
				+ "'"+batchtransfer.getOperator_id()+"'"		
				+ ",'"+batchtransfer.getDown_time()+"'"		
				+ ",'"+batchtransfer.getDown_file()+"'"	
				+ ",'"+batchtransfer.getDown_orders()+"'"		
				+ ",'"+batchtransfer.getDown_amount()+"'"	
				+ ",'"+batchtransfer.getLender_id()+"'"		
				+ ",'"+batchtransfer.getBatch_status()+"'"	
				+ ",'"+batchtransfer.getBd()+"'"	
				+ ");";
		this.baseJdbcDao.insert(sql);
		return "success";

	}

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from batch_transfer where batch_id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public String update(Batchtransfer batchtransfer) {
		
		String sql="update batch_transfer set "
				+ "up_time='"+batchtransfer.getUp_time()+"' "
				+ ",up_file='"+batchtransfer.getUp_file()+"' "
				+ ",up_orders='"+batchtransfer.getUp_orders()+"' "
				+ ",up_amount='"+batchtransfer.getUp_amount()+"' "
				+ ",balance_orders='"+batchtransfer.getBalance_orders()+"' "
				+ ",batch_status='"+batchtransfer.getBatch_status()+"' "
				 +" where batch_id='"+batchtransfer.getBatch_id()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
	}


}
