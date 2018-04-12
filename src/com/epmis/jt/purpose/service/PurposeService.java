package com.epmis.jt.purpose.service;

import java.util.List;
import java.util.Map;

import com.epmis.jt.purpose.vo.Purpose;
import com.epmis.jt.repwill.vo.Repwill;

public interface PurposeService {
	
	/**
	 * 查询所有借款用途的信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllPurpose(String isdel,int page,int rows);
	/**
	 * 计算借款用途的信息的总数
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
	public abstract String save(Purpose purpose);
	
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
	public abstract String update(Purpose purpose);

}
