package com.epmis.banner.service;

import java.util.List;
import java.util.Map;

import com.epmis.banner.vo.Banner;




public interface BannerService {

	/**
	 * 查询所有banner信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllBanner(String isdel,int page,int rows);
	/**
	 * 计算banner总数
	 * @return
	 */
	public abstract int count(String isdel);
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
	public abstract String save(Banner banner);
	
	
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
	public abstract String update(Banner banner);

	
	
	/**
	 * 位置
	 * @param cartjrx
	 * @return
	 */
	public abstract String wz(Banner banner);
	
	
	
	
	
	


	

}
