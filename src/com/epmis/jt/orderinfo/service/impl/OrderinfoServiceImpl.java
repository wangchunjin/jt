package com.epmis.jt.orderinfo.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.jt.orderinfo.service.OrderinfoService;
import com.epmis.jt.orderinfo.vo.Orderinfo;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;

@Transactional
@Service("OrderinfosService")
public class OrderinfoServiceImpl implements OrderinfoService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllOrderinfo(String approve_status,String transfer_status,String fp_zg,String fp_jg,String jg_name,String zgtime,String jgtime,String mobile,String jg,String cy,int page, int rows) {
		String sqlwhere="";
		if(DataTypeUtil.validate(approve_status)){
			sqlwhere=sqlwhere+" and  approve_status='"+approve_status+"'";
		}
		if(DataTypeUtil.validate(transfer_status)){
			sqlwhere=sqlwhere+" and transfer_status="+transfer_status;
		}
		if(DataTypeUtil.validate(fp_zg)){
			sqlwhere=sqlwhere+" and  fp_zg='"+fp_zg+"'";
		}
		if(DataTypeUtil.validate(fp_jg)){
			sqlwhere=sqlwhere+" and  fp_jg='"+fp_jg+"'";
		}
		if(DataTypeUtil.validate(jg_name)){
			sqlwhere=sqlwhere+" and  jg_name='"+jg_name+"'";
		}
		if(DataTypeUtil.validate(zgtime)){
			sqlwhere=sqlwhere+" and  DATE_FORMAT(zg_time,'%Y-%m-%d')='"+zgtime+"'";
		}
		if(DataTypeUtil.validate(jgtime)){
			sqlwhere=sqlwhere+" and  DATE_FORMAT(jg_time,'%Y-%m-%d')='"+jgtime+"'";
		}
		if(DataTypeUtil.validate(mobile)){
			sqlwhere=sqlwhere+" and client_id in (select client_id from client_info where mobile like '%"+mobile+"%')";
		}
		if(DataTypeUtil.validate(jg)){
			sqlwhere=sqlwhere+" and  jg_name='"+jg+"'";
		}
		if(DataTypeUtil.validate(cy)){
			sqlwhere=sqlwhere+" and  yg_name='"+cy+"'";
		}
		int start=(page-1)*rows;
		int end=rows;
		String sql="select order_id orderid,order_number ordernum,DATE_FORMAT(jg_time,'%Y-%m-%d')jg_time,DATE_FORMAT(zg_time,'%Y-%m-%d')zg_time,order_id,fp_zg,(select mobile from client_info c where c.client_id=o.client_id )mobile,(select ACTUAL_NAME from cm_users c where c.USER_ID=o.jg_name)jg_name,fp_jg,(select ACTUAL_NAME from cm_users c where c.USER_ID=o.yg_name)yg_name,order_number,(select name from client_info c where c.client_id=o.client_id )client_id,borrow_time,(select name from lender l where l.lender_id=o.lend_id )lend_id,amount,period,interest,overdue_payment_rel,overdue_payment_the,(select name from t_purpose p where p.id=o.pur_id)pur_id,real_amt,commision,approve_status,approve_time,overdue_time,transfer_status,transfer_time,repay_time,repay_money,DATE_FORMAT(rel_time,'%Y-%m-%d')rel_time,rel_money,last_loan_amount,fun_change,maile_num,good_report,DATE_FORMAT(createtime,'%Y-%m-%d')createtime from  order_info o  where 1=1 "+sqlwhere+" and isdel='0'  order by order_id desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int count(String approve_status,String transfer_status,String fp_zg,String fp_jg,String jg_name,String zgtime,String jgtime,String mobile,String jg,String cy) {
		String sqlwhere="";
		if(DataTypeUtil.validate(approve_status)){
			sqlwhere=sqlwhere+" and  approve_status='"+approve_status+"'";
		}
		if(DataTypeUtil.validate(transfer_status)){
			sqlwhere=sqlwhere+" and transfer_status="+transfer_status;
		}
		
		if(DataTypeUtil.validate(fp_zg)){
			sqlwhere=sqlwhere+" and  fp_zg='"+fp_zg+"'";
		}
		if(DataTypeUtil.validate(fp_jg)){
			sqlwhere=sqlwhere+" and  fp_jg='"+fp_jg+"'";
		}
		if(DataTypeUtil.validate(jg_name)){
			sqlwhere=sqlwhere+" and  jg_name='"+jg_name+"'";
		}
		if(DataTypeUtil.validate(zgtime)){
			sqlwhere=sqlwhere+" and  DATE_FORMAT(zg_time,'%Y-%m-%d')='"+zgtime+"'";
		}
		if(DataTypeUtil.validate(jgtime)){
			sqlwhere=sqlwhere+" and  DATE_FORMAT(jg_time,'%Y-%m-%d')='"+jgtime+"'";
		}
		if(DataTypeUtil.validate(mobile)){
			sqlwhere=sqlwhere+" and client_id in (select client_id from client_info where mobile like '%"+mobile+"%')";
		}
		if(DataTypeUtil.validate(jg)){
			sqlwhere=sqlwhere+" and  jg_name='"+jg+"'";
		}
		if(DataTypeUtil.validate(cy)){
			sqlwhere=sqlwhere+" and  yg_name='"+cy+"'";
		}
		String sql="select count(1) from order_info where  isdel='0' "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}

	
	
	@Override
	public List<Map<String, Object>> findAllOrderinfo_yg(String approve_status,String transfer_status,String fp_zg,String fp_jg,String yg_name,String collection,String zgtime,String jgtime,String mobile,int page, int rows) {
		String sqlwhere="";
		if(DataTypeUtil.validate(approve_status)){
			sqlwhere=sqlwhere+" and  approve_status='"+approve_status+"'";
		}
		if(DataTypeUtil.validate(transfer_status)){
			sqlwhere=sqlwhere+" and transfer_status="+transfer_status;
		}
		if(DataTypeUtil.validate(fp_zg)){
			sqlwhere=sqlwhere+" and  fp_zg='"+fp_zg+"'";
		}
		if(DataTypeUtil.validate(fp_jg)){
			sqlwhere=sqlwhere+" and  fp_jg='"+fp_jg+"'";
		}
		if(DataTypeUtil.validate(yg_name)){
			sqlwhere=sqlwhere+" and  yg_name='"+yg_name+"'";
		}
		if(DataTypeUtil.validate(collection)){
			sqlwhere=sqlwhere+" and  collection='"+collection+"'";
		}
		if(DataTypeUtil.validate(zgtime)){
			sqlwhere=sqlwhere+" and  DATE_FORMAT(zg_time,'%Y-%m-%d')='"+zgtime+"'";
		}
		if(DataTypeUtil.validate(jgtime)){
			sqlwhere=sqlwhere+" and  DATE_FORMAT(jg_time,'%Y-%m-%d')='"+jgtime+"'";
		}
		if(DataTypeUtil.validate(mobile)){
			sqlwhere=sqlwhere+" and client_id in (select client_id from client_info where mobile like '%"+mobile+"%')";
		}
		int start=(page-1)*rows;
		int end=rows;
		String sql="select order_id orderid,order_number ordernum,DATE_FORMAT(jg_time,'%Y-%m-%d')jg_time,DATE_FORMAT(zg_time,'%Y-%m-%d')zg_time,order_id,client_id cid,collection,fp_zg,(select ACTUAL_NAME from cm_users c where c.USER_ID=o.jg_name)jg_name,fp_jg,(select ACTUAL_NAME from cm_users c where c.USER_ID=o.yg_name)yg_name,order_number,(select name from client_info c where c.client_id=o.client_id )client_id,(select mobile from client_info c where c.client_id=o.client_id )mobile,borrow_time,(select name from lender l where l.lender_id=o.lend_id )lend_id,amount,period,interest,overdue_payment_rel,overdue_payment_the,(select name from t_purpose p where p.id=o.pur_id)pur_id,real_amt,commision,approve_status,approve_time,overdue_time,transfer_status,transfer_time,repay_time,repay_money,DATE_FORMAT(rel_time,'%Y-%m-%d')rel_time,rel_money,last_loan_amount,fun_change,maile_num,good_report,DATE_FORMAT(createtime,'%Y-%m-%d')createtime from  order_info o  where 1=1 "+sqlwhere+" and isdel='0'  order by order_id desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int count_yg(String approve_status,String transfer_status,String fp_zg,String fp_jg,String yg_name,String collection,String zgtime,String jgtime,String mobile) {
		String sqlwhere="";
		if(DataTypeUtil.validate(approve_status)){
			sqlwhere=sqlwhere+" and  approve_status='"+approve_status+"'";
		}
		if(DataTypeUtil.validate(transfer_status)){
			sqlwhere=sqlwhere+" and transfer_status="+transfer_status;
		}
		
		if(DataTypeUtil.validate(fp_zg)){
			sqlwhere=sqlwhere+" and  fp_zg='"+fp_zg+"'";
		}
		if(DataTypeUtil.validate(fp_jg)){
			sqlwhere=sqlwhere+" and  fp_jg='"+fp_jg+"'";
		}
		if(DataTypeUtil.validate(yg_name)){
			sqlwhere=sqlwhere+" and  yg_name='"+yg_name+"'";
		}
		if(DataTypeUtil.validate(collection)){
			sqlwhere=sqlwhere+" and  collection='"+collection+"'";
		}
		if(DataTypeUtil.validate(zgtime)){
			sqlwhere=sqlwhere+" and  DATE_FORMAT(zg_time,'%Y-%m-%d')='"+zgtime+"'";
		}
		if(DataTypeUtil.validate(jgtime)){
			sqlwhere=sqlwhere+" and  DATE_FORMAT(jg_time,'%Y-%m-%d')='"+jgtime+"'";
		}
		if(DataTypeUtil.validate(mobile)){
			sqlwhere=sqlwhere+" and client_id in (select client_id from client_info where mobile like '%"+mobile+"%')";
		}
		String sql="select count(1) from order_info where  isdel='0' "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select order_id,order_number,(select name from client_info c where c.client_id=o.client_id )client_id,borrow_time,(select name from lender l where l.lender_id=o.lend_id )lend_id,amount,period,interest,overdue_payment_rel,overdue_payment_the,(select name from t_purpose p where p.id=o.pur_id)pur_id,real_amt,commision,approve_status,approve_time,overdue_time,transfer_status,transfer_time,repay_time,repay_money,rel_time,rel_money,last_loan_amount,fun_change,maile_num,good_report,createtime from order_info o where order_id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public String update(Orderinfo orderinfo,String bd) {
		String sql="update order_info set transfer_status='"+orderinfo.getTransfer_status()+"' where bank_id='"+orderinfo.getBank_id()+"' and bd='"+bd+"'";
		this.baseJdbcDao.update(sql);
		return "success";
	}

	/**
	 * 催收主管分配逾期订单
	 */
	@Override
	public String fp(String ids, String jg_name,String cuid) {
		Date day=new Date();    
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String sql="update order_info set fp_zg='1' , jg_name='"+jg_name+"',zg_time='"+df.format(day)+"' where order_id in ("+ids+")";
		this.baseJdbcDao.update(sql);
		
		String result="";
		String [] stringArr= ids.split(",");
		//对催收主管分配行为记录
		for(int i=0;i<stringArr.length;i++){
			String sql_fp="INSERT INTO `rankings`(order_id,assignor,assigned,status,type) VALUES ("			
					+ "'"+stringArr[i]+"'"			
					+ ",'"+cuid+"'"
					+ ",'"+jg_name+"'"
					+ ",'0'"
					+ ",'0'"
					+ ");";
			this.baseJdbcDao.insert(sql_fp);
		}
		
		
		
		return "success";
	}
	/**
	 * 催收主管收回逾期订单
	 */
	@Override
	public String sh(String ids,String cuid) {
		Date day=new Date();	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String sql="update order_info set fp_zg='0',collection='0', jg_name=null,fp_jg='0',yg_name=null,zg_time=null,jg_time=null where order_id in ("+ids+")";
		this.baseJdbcDao.update(sql);
		String result="";
		String [] stringArr= ids.split(",");
		//对催收主管收回行为记录
		for(int i=0;i<stringArr.length;i++){
			String sql_sh="update rankings set type='2',t_time='"+df.format(day)+"' where order_id='"+stringArr[i]+"' and type='0'";
			this.baseJdbcDao.insert(sql_sh);
		}
		return "success";
	}
	/**
	 * 催收机构分配逾期订单
	 */
	@Override
	public String fp_jg(String ids, String yg_name,String cuid) {
		Date day=new Date();    
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String sql="update order_info set fp_jg='1' , yg_name='"+yg_name+"',jg_time='"+df.format(day)+"' where order_id in ("+ids+")";
		this.baseJdbcDao.update(sql);
		
		
		String result="";
		String [] stringArr= ids.split(",");
		//对催收机构分配行为记录
		for(int i=0;i<stringArr.length;i++){
			String sql_fp="INSERT INTO `rankings`(order_id,assignor,assigned,status,type) VALUES ("			
					+ "'"+stringArr[i]+"'"			
					+ ",'"+cuid+"'"
					+ ",'"+yg_name+"'"
					+ ",'1'"
					+ ",'0'"
					+ ");";
			this.baseJdbcDao.insert(sql_fp);
		}
		
		
		
		return "success";
	}
	/**
	 * 催收机构收回逾期订单
	 */
	@Override
	public String sh_jg(String ids,String cuid) {
		Date day=new Date();    
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String sql="update order_info set fp_jg='0',collection='0',yg_name=null,jg_time=null where order_id in ("+ids+")";
		this.baseJdbcDao.update(sql);
		String result="";
		String [] stringArr= ids.split(",");
		//对催收机构收回行为记录
		for(int i=0;i<stringArr.length;i++){
			String sql_sh="update rankings set type='2',t_time='"+df.format(day)+"' where order_id='"+stringArr[i]+"' and status='1' and type='0'";
			this.baseJdbcDao.insert(sql_sh);
		}
		return "success";
	}

	/**
	 * 获取用户的通讯录信息
	 */
	@Override
	public List<Map<String, Object>> findById_t(String id) {
		
		
		String sql="select id,phone,pname from t_operator where uid='"+id+"' GROUP BY phone ORDER BY id desc";
		return this.baseJdbcDao.queryListMap(sql);
	}

	/**
	 * 消减滞纳金
	 */
	@Override
	public String late(Orderinfo orderinfo,String late_fee,String cuid) {
		BigDecimal b1 = new BigDecimal(orderinfo.getAmount());
		BigDecimal b2 = new BigDecimal(orderinfo.getOverdue_payment_rel());
		BigDecimal b3 = new BigDecimal(orderinfo.getInterest());
		String sum=b1.add(b2).add(b3).toString();		
		String sql="update order_info set overdue_payment_rel='"+orderinfo.getOverdue_payment_rel()+"',rel_money='"+sum+"' where order_id="+orderinfo.getOrder_id();
		this.baseJdbcDao.update(sql);
		
		String sql_history="INSERT INTO `history_record`(cuid,content,action_type,order_id,late_fee) VALUES ("			
				+ "'"+cuid+"'"	
				+ ",'对订单("+orderinfo.getOrder_id()+")进行消减滞纳金。'"
				+ ",'4'"
				+ ",'"+orderinfo.getOrder_id()+"'"
				+ ",'"+late_fee+"'"
				+ ");";
		this.baseJdbcDao.insert(sql_history);
		
		
		return "success";
	}
	/**
	 *对状态为1（还款中）的订单进行平帐 
	 */
	@Override
	public String flat_1(String id,String cuid) {
		Date day=new Date();    
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String sql="update order_info set transfer_status='2',rel_time='"+df.format(day)+"' where order_id in("+id+")";
		this.baseJdbcDao.update(sql);
		String sql_history="INSERT INTO `history_record`(cuid,content) VALUES ("			
				+ "'"+cuid+"'"				
				+ ",'对订单("+id+")进行平帐。'"	
				+ ");";
		this.baseJdbcDao.insert(sql_history);
		return "success";
	}
	/**
	 *对状态为3（逾期）的订单进行平帐 
	 */
	@Override
	public String flat_2(String id,String cuid) {
		Date day=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String sql="update order_info set transfer_status='4',rel_time='"+df.format(day)+"' where order_id in("+id+")";
		this.baseJdbcDao.update(sql);
		String sql_history="INSERT INTO `history_record`(cuid,content) VALUES ("			
				+ "'"+cuid+"'"				
				+ ",'对订单("+id+")进行平帐。'"	
				+ ");";
		this.baseJdbcDao.insert(sql_history);
		return "success";
	}
	
	
	/**
	 *对状态为1或3（逾期）的订单进行平帐 
	 */
	@Override
	public String flat_3(String id,String cuid,String rel_time,String transfer_status,String o_transfer_status,String o_rel_time) {
		Date day=new Date();    
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		String sql="update order_info set transfer_status='"+transfer_status+"',rel_time='"+rel_time+"' where order_id in("+id+")";
		this.baseJdbcDao.update(sql);
		String sql_history="INSERT INTO `history_record`(cuid,content,action_type,rel_time,transfer_status,order_id) VALUES ("			
				+ "'"+cuid+"'"		
				+ ",'对订单("+id+")进行线下还款。'"
				+ ",'3'"
				+ ",'"+o_rel_time+"'"
				+ ",'"+o_transfer_status+"'"				
				+ ",'"+id+"'"
				+ ");";
		this.baseJdbcDao.insert(sql_history);
		
		//对逾期订单 中 催收情况的记录
		String sql_rank="update rankings set type='1' ,t_time='"+df.format(day)+"' where order_id='"+id+"' and type='0' ";		
		this.baseJdbcDao.insert(sql_rank);
		return "success";
	}
	
	/**
	 * 催收主管  催收排行
	 */
	@Override
	public String rank(String cuid) {
		
		String sql_rank="";		
		this.baseJdbcDao.insert(sql_rank);
		return "success";
	}

	@Override
	public List<Map<String, Object>> Jgrankings(String cuid, String start_time,
			String end_time, int page, int rows) {
		String sqlwhere="";
		String starttime="";
		String endtime="";
		if(DataTypeUtil.validate(cuid)){
			sqlwhere=sqlwhere+" and a.subsidiary='"+cuid+"'";
		}
		if(DataTypeUtil.validate(start_time) && DataTypeUtil.validate(end_time)){
			starttime=start_time;
		}else{
			starttime="2018-01-01";
		}
		
		if(DataTypeUtil.validate(start_time) && DataTypeUtil.validate(end_time)){
			endtime=end_time;
		}else{
			endtime="2018-03-27";
		}
		
		
		int start=(page-1)*rows;
		int end=rows;
		String sql="select user_id,actual_name name,snum sum,snumc daisum,c success,d fail,percent,msum money from("
				+ "select user_id,actual_name ,"
				+ "(select count(1) from rankings b where 1=1 AND b.assigned=a.user_id and b.status='0'AND b.t_time BETWEEN '"+starttime+"' and '"+endtime+"') snum ,"
				+ "(select count(1) from rankings b where 1=1 AND b.assigned=a.user_id and b.status='0'AND b.type='0' and b.t_time BETWEEN '"+starttime+"' and '"+endtime+"') snumc,"
				+ "(select count(1) from rankings b where 1=1 AND b.assigned=a.user_id and b.status='0'AND b.type='1' and b.t_time BETWEEN '"+starttime+"' and '"+endtime+"') c,"
				+ "(select count(1) from rankings b where 1=1 AND b.assigned=a.user_id and b.status='0'AND b.type='2' and b.t_time BETWEEN '"+starttime+"' and '"+endtime+"') d,"
				+ "concat(cast(coalesce(round(((select count(1) from rankings b where 1=1 and b.assigned=a.user_id and b.status='0'AND b.type='1' and b.t_time BETWEEN '"+starttime+"' and '"+endtime+"')/(select count(1) from rankings b where 1=1 and b.assigned=a.user_id and b.status='0'AND b.t_time BETWEEN '"+starttime+"' and '"+endtime+"')),2),0)*100 as char),'%') as percent,"
				+ "(select sum(rel_money) ddd from order_info d where 1=1 and d.order_id in(select order_id from rankings b where 1=1 and b.assigned=a.user_id and b.status='0' and type='1' AND b.t_time BETWEEN '"+starttime+"' and '"+endtime+"'))msum"
				+ " from cm_users a  where 1=1 "+sqlwhere+") cc ORDER BY percent desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int countJgrankings(String cuid, String start_time, String end_time) {
		String sqlwhere="";		
		if(DataTypeUtil.validate(cuid)){
			sqlwhere=sqlwhere+" and subsidiary='"+cuid+"'";
		}
		
		
		String sql="select count(1) from cm_users where 1=1 "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}

	@Override
	public List<Map<String, Object>> Cyrankings(String cuid, String start_time,
			String end_time, int page, int rows) {
		String sqlwhere="";
		String starttime="";
		String endtime="";
		if(DataTypeUtil.validate(cuid)){
			sqlwhere=sqlwhere+" and a.subsidiary='"+cuid+"'";
		}
		if(DataTypeUtil.validate(start_time) && DataTypeUtil.validate(end_time)){
			starttime=start_time;
		}else{
			starttime="2018-01-01";
		}
		
		if(DataTypeUtil.validate(start_time) && DataTypeUtil.validate(end_time)){
			endtime=end_time;
		}else{
			endtime="2018-03-27";
		}
		
		
		int start=(page-1)*rows;
		int end=rows;
		String sql="select user_id,actual_name name,snum sum,snumc daisum,c success,d fail,percent,msum money from("
				+ "select user_id,actual_name ,"
				+ "(select count(1) from rankings b where 1=1 AND b.assigned=a.user_id and b.status='1'AND b.t_time BETWEEN '"+starttime+"' and '"+endtime+"') snum ,"
				+ "(select count(1) from rankings b where 1=1 AND b.assigned=a.user_id and b.status='1'AND b.type='0' and b.t_time BETWEEN '"+starttime+"' and '"+endtime+"') snumc,"
				+ "(select count(1) from rankings b where 1=1 AND b.assigned=a.user_id and b.status='1'AND b.type='1' and b.t_time BETWEEN '"+starttime+"' and '"+endtime+"') c,"
				+ "(select count(1) from rankings b where 1=1 AND b.assigned=a.user_id and b.status='1'AND b.type='2' and b.t_time BETWEEN '"+starttime+"' and '"+endtime+"') d,"
				+ "concat(cast(coalesce(round(((select count(1) from rankings b where 1=1 and b.assigned=a.user_id and b.status='1'AND b.type='1' and b.t_time BETWEEN '"+starttime+"' and '"+endtime+"')/(select count(1) from rankings b where 1=1 and b.assigned=a.user_id and b.status='1'AND b.t_time BETWEEN '"+starttime+"' and '"+endtime+"')),2),0)*100 as char),'%') as percent,"
				+ "(select sum(rel_money) ddd from order_info d where 1=1 and d.order_id in(select order_id from rankings b where 1=1 and b.assigned=a.user_id and b.status='1' and type='1' AND b.t_time BETWEEN '"+starttime+"' and '"+endtime+"'))msum"
				+ " from cm_users a  where 1=1 "+sqlwhere+") cc ORDER BY percent desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int countCyrankings(String cuid, String start_time, String end_time) {
		String sqlwhere="";		
		if(DataTypeUtil.validate(cuid)){
			sqlwhere=sqlwhere+" and subsidiary='"+cuid+"'";
		}
		
		
		String sql="select count(1) from cm_users where 1=1 "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}
	

}
