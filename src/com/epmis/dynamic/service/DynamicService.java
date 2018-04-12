package com.epmis.dynamic.service;

import java.util.List;
import java.util.Map;

import com.epmis.dynamic.vo.Dynamic;


public interface DynamicService {
	
	/**
	 * 查询所有楼盘动态信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllProdt(String isdel,String lid,int page,int rows);
	/**
	 * 计算楼盘动态总数
	 * @return
	 */
	public abstract int count(String isdel,String lid);
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
	public abstract String save(Dynamic dynamic);
	
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
	public abstract String update(Dynamic dynamic);

}
