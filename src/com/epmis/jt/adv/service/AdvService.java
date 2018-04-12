package com.epmis.jt.adv.service;

import java.util.List;
import java.util.Map;

import com.epmis.jt.adv.vo.Adv;



public interface AdvService {

	/**
	 * 查询所有公告信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllAdv(String isdel,int page,int rows);
	/**
	 * 计算公告总数
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
	public abstract String save(Adv adv);
	
	
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
	public abstract String update(Adv adv);

	
	
	/**
	 * 位置
	 * @param cartjrx
	 * @return
	 */
	public abstract String wz(Adv adv);
	
	
	
	
	
	


	

}
