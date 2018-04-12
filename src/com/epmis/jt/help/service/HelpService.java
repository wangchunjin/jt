package com.epmis.jt.help.service;

import java.util.List;
import java.util.Map;

import com.epmis.jt.help.vo.Help;



public interface HelpService {
	
	/**
	 * 查询所有帮助的信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllHelp(String isdel,int page,int rows);
	/**
	 * 计算帮助的信息的总数
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
	public abstract String save(Help help);
	
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
	public abstract String update(Help help);

}
