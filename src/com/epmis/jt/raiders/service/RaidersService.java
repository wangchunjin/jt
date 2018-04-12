package com.epmis.jt.raiders.service;

import java.util.List;
import java.util.Map;

import com.epmis.jt.raiders.vo.Raiders;



public interface RaidersService {

	/**
	 * 查询所有产品攻略信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllRaiders(String isdel,int page,int rows);
	/**
	 * 计算产品攻略总数
	 * @return
	 */
	public abstract int count(String isdel);
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
	public abstract String save(Raiders raiders);
	
	
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
	public abstract String update(Raiders raiders);

	
	
	/**
	 * 位置
	 * @param cartjrx
	 * @return
	 */
	public abstract String wz(Raiders raiders);
	
	
	
	
	
	


	

}
