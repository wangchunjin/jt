package com.epmis.jt.lender.service;

import java.util.List;
import java.util.Map;

import com.epmis.jt.lender.vo.Lender;



public interface LenderService {
	
	/**
	 * 查询所有出借人的信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllLender(String isdel,int page,int rows);
	/**
	 * 计算出借人的信息的总数
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
	public abstract String save(Lender lender);
	
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
	public abstract String update(Lender lender);

}
