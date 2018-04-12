package com.epmis.edition.service;

import java.util.List;
import java.util.Map;

import com.epmis.edition.vo.Edition;




public interface EditionService {
	
	/**
	 * 查询所有版本
	 * @param title
	 * @param page
	 * @param rows
	 * @return
	 */
	public abstract List<Map<String, Object>> findAlledition(String isdel,int page,int rows);
	
	/**
	 * 查询版本总数
	 * @param title
	 * @return
	 */
	public abstract int count(String isdel);
	/**
	 * 保存
	 * @param brand
	 */
	public abstract String save(Edition edition);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public abstract String delete(String id);

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
	public abstract String update(Edition edition);



}
