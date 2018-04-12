package com.epmis.jt.waymoney.service;

import java.util.List;
import java.util.Map;

import com.epmis.jt.waymoney.vo.Waymoney;



public interface WaymoneyService {
	/**
	 * 根据id查询打款方式
	 * @param id
	 * @return
	 */
	public abstract List<Map<String, Object>> findById(String id);
	
	/**
	 * 修改打款方式
	 * @param brand
	 * @return
	 */
	public abstract String update(Waymoney waymoney);
	
	

}
