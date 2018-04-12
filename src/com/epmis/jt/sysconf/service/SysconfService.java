package com.epmis.jt.sysconf.service;

import java.util.List;
import java.util.Map;

import com.epmis.jt.sysconf.vo.Sysconf;



public interface SysconfService {
	/**
	 * 查询所有系统配置的信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllSysconf(int page,int rows);
	/**
	 * 计算系统配置的信息的总数
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
	public abstract String save(Sysconf sysconf);
	
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
	public abstract String update(Sysconf sysconf);

}
