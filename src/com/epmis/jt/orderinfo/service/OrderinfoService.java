package com.epmis.jt.orderinfo.service;

import java.util.List;
import java.util.Map;

import com.epmis.jt.orderinfo.vo.Orderinfo;

public interface OrderinfoService {
	/**
	 * 查询所有订单的信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllOrderinfo(String approve_status,String transfer_status,String fp_zg,String fp_jg,String jg_name,String zgtime,String jgtime,String mobile,String jg,String cy,int page,int rows);
	/**
	 * 计算所有订单的总数
	 * @return
	 */
	public abstract int count(String approve_status,String transfer_status,String fp_zg,String fp_jg,String  jg_name,String zgtime,String jgtime,String mobile,String jg,String cy);
	
	
	/**
	 * 查询分配给催收员的所有订单信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllOrderinfo_yg(String approve_status,String transfer_status,String fp_zg,String fp_jg,String yg_name,String collection,String zgtime,String jgtime,String mobile,int page,int rows);
	/**
	 * 计算分配给催收员所有订单的总数
	 * @return
	 */
	public abstract int count_yg(String approve_status,String transfer_status,String fp_zg,String fp_jg,String  yg_name,String collection,String zgtime,String jgtime,String mobile);
	
	
	
	
	
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
//	public abstract String delete(String id);
	
	
	/**
	 * 新增
	 * @param car
	 * @return
	 */
//	public abstract String save(Clientinfo clientinfo);
	
	/**
	 * 根据id查询订单
	 * @param id
	 * @return
	 */
	public abstract List<Map<String, Object>> findById(String id);
	
	/**
	 * 根据id查询通讯录
	 * @param id
	 * @return
	 */
	public abstract List<Map<String, Object>> findById_t(String id);
	
	/**
	 * 修改
	 * @param brand
	 * @return
	 */
	public abstract String update(Orderinfo orderinfo,String bd);
	/**
	 * 催收主管分配逾期订单
	 * @param brand
	 * @return
	 */
	public abstract String fp(String ids,String jg_name,String cuid);
	
	/**
	 * 催收主管收回逾期订单   等待重新分配催收机构
	 * @param id
	 * @return
	 */
	public abstract String sh(String ids,String cuid);
	/**
	 * 催收机构分配逾期订单
	 * @param brand
	 * @return
	 */
	public abstract String fp_jg(String ids,String yg_name,String cuid);
	
	/**
	 * 催收机构收回逾期订单   等待重新分配催收员
	 * @param id
	 * @return
	 */
	public abstract String sh_jg(String ids,String cuid);
	/**
	 * 消减滞纳金
	 * @return
	 */
	public abstract String late(Orderinfo orderinfo,String late_fee,String cuid);
	/**
	 *对状态为1（还款中）的订单进行平帐 
	 */
	public abstract String flat_1(String id,String cuid);
	/**
	 *对状态为3（逾期）的订单进行平帐 
	 */
	public abstract String flat_2(String id,String cuid);
	/**
	 *对状态为1或3（逾期）的订单进行平帐 
	 */
	public abstract String flat_3(String id,String cuid,String rel_time,String transfer_status,String o_transfer_status,String o_rel_time);

	
	/**
	 *催收主管  催收排行
	 */
	public abstract String rank(String cuid);
	
	/**
	 * 查询所有机构排行的信息
	 * @return
	 */
	public abstract List<Map<String, Object>> Jgrankings(String cuid,String start_time,String end_time,int page,int rows);
	/**
	 * 计算所有机构排行的总数
	 * @return
	 */
	public abstract int countJgrankings(String cuid,String start_time,String end_time);
	
	
	
	
	/**
	 * 查询所有机构成员排行的信息
	 * @return
	 */
	public abstract List<Map<String, Object>> Cyrankings(String cuid,String start_time,String end_time,int page,int rows);
	/**
	 * 计算所有机构成员排行的总数
	 * @return
	 */
	public abstract int countCyrankings(String cuid,String start_time,String end_time);
	
	
	
	
	

}
