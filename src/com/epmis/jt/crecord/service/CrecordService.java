package com.epmis.jt.crecord.service;

import java.util.List;
import java.util.Map;

import com.epmis.jt.crecord.vo.Crecord;



public interface CrecordService {
	
	/**
	 * 查询所有催收记录的信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllCrecord(String isdel,String oid,int page,int rows);
	/**
	 * 计算催收记录的信息的总数
	 * @return
	 */
	public abstract int count(String oid);
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
	public abstract String save(Crecord crecord);
	
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
	public abstract String update(Crecord crecord);

}
