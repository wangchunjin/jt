package com.epmis.zs.service;

import java.util.List;
import java.util.Map;

public interface ZsService {
	/**
	 * 查询所有楼价走势信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllZs(String month,String pinyin,String quyu,int page,int rows);
	/**
	 * 计算楼价走势总数
	 * @return
	 */
	public abstract int count(String month,String pinyin,String quyu);
	
	
	/**
	 * 查询所有楼价走势信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllZstb(String month,String pinyin,String quyu,int page,int rows);
	/**
	 * 计算楼价走势总数
	 * @return
	 */
	public abstract int count1(String month,String pinyin,String quyu);
	

}
