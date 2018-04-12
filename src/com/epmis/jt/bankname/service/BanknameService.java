package com.epmis.jt.bankname.service;

import java.util.List;
import java.util.Map;

import com.epmis.jt.bankname.vo.Bankname;



public interface BanknameService {
	
	/**
	 * 查询所有银行名称的信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllBankname(String isdel,int page,int rows);
	/**
	 * 计算银行名称的信息的总数
	 * @return
	 */
	public abstract int count();
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	public abstract String delete(String id);
	
	
	/**
	 * 新增
	 * @param car
	 * @return
	 */
	public abstract String save(Bankname bankname);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public abstract List<Map<String, Object>> findById(String id);
	
	/**
	 * 修改
	 * @param brand
	 * @return
	 */
	public abstract String update(Bankname bankname);

}
