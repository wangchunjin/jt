package com.epmis.jt.clientinfo.service;

import java.util.List;
import java.util.Map;




public interface ClientinfoService {
	/**
	 * 查询所有借款用户的信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllClientinfo(String mobile,int page,int rows);
	/**
	 * 计算借款用户的总数
	 * @return
	 */
	public abstract int count(String mobile);
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
//	public abstract String delete(String id);
	
	
	/**
	 * 新增
	 * @param car
	 * @return
	 */
//	public abstract String save(Clientinfo clientinfo);
	
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
//	public abstract String update(Clientinfo clientinfo);

}
