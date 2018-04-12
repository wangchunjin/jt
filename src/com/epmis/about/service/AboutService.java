package com.epmis.about.service;

import java.util.List;
import java.util.Map;

import com.epmis.about.vo.About;


public interface AboutService {

	/**
	 * 查询相应楼盘所有楼栋信息
	 * @return
	 */
//	public abstract List<Map<String, Object>> findAllBan(String isdel,String lid,int page,int rows);
	/**
	 * 计算相应楼盘的楼栋总数
	 * @return
	 */
//	public abstract int count(String isdel,String lid);
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
//	public abstract String save(Ban ban);
	
	
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
	public abstract String update(About about);

	
	

	
	
	
	
	


	

}
