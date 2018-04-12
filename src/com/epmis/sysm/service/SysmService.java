package com.epmis.sysm.service;

import java.util.List;
import java.util.Map;


import com.epmis.sysm.vo.Sysm;

public interface SysmService {
	/**
	 * 查询所有车型推送的信息
	 * @param title
	 * @param page
	 * @param rows
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllSysmcar(String title,String isdel,int page,int rows);
	
	/**
	 * 查询车型推送的信息总页数
	 * @param title
	 * @return
	 */
	public abstract int count(String title,String isdel);
	/**
	 * 保存
	 * @param brand
	 */
	public abstract String save(Sysm sysm);
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
	public abstract String update(Sysm sysm);
	
	







}
