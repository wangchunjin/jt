package com.epmis.jt.repwill.service;

import java.util.List;
import java.util.Map;

import com.epmis.jt.repwill.vo.Repwill;


public interface RepwillService {
	
	/**
	 * 查询所有还款意愿的信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllRepwill(String isdel,int page,int rows);
	/**
	 * 计算还款意愿的信息的总数
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
	public abstract String save(Repwill repwill);
	
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
	public abstract String update(Repwill repwill);

}
