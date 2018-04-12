package com.epmis.jt.contract.service;

import java.util.List;
import java.util.Map;

public interface ContractService {
	/**
	 * 查询所有法大大生成合同的信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllContract(String client_id,String order_number,String createtime,int page,int rows);
	/**
	 * 计算法大大生成合同的信息的总数
	 * @return
	 */
	public abstract int count(String client_id,String order_number,String createtime);
	
	
	

}
