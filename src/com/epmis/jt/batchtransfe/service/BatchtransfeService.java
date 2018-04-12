package com.epmis.jt.batchtransfe.service;

import java.util.List;
import java.util.Map;

import com.epmis.jt.batchtransfe.vo.Batchtransfer;

public interface BatchtransfeService {
	
	/**
	 * 查询所有批量转账的信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllBatch(String isdel,int page,int rows);
	/**
	 * 计算批量转账的信息的总数
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
	public abstract String save(Batchtransfer batchtransfer);
	
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
	public abstract String update(Batchtransfer batchtransfer);

}