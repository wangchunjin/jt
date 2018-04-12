package com.epmis.car.service;

import java.util.List;
import java.util.Map;

import com.epmis.car.vo.Ecar;
import com.epmis.car.vo.Tcar;




public interface CarService {
	/**
	 * 查询所有车辆信息
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllCar(String title,String status,String isdel,String xin,String bid,String jid,String gid,String carsysid,String type,String csid,int page,int rows);
	/**
	 * 计算总数
	 * @return
	 */
	public abstract int count(String title,String status,String isdel,String xin,String bid,String jid,String gid,String carsysid,String type,String csid);
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	public abstract String delete(String id);
	/**
	 * 根据id彻底删除数据（物理删除）
	 * @param id
	 * @return
	 */
	public abstract String deletez(String id);
	/**
	 * 根据id恢复数据
	 * @param id
	 * @return
	 */
	public abstract String hf(String id);
	
	/**
	 * 新增
	 * @param car
	 * @return
	 */
	public abstract String save(Tcar car,String fuzhics);
	
	
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
	public abstract String update(Tcar car);
	/**
	 * 显示--->隐藏
	 * @param id
	 * @return
	 */
	public abstract String xs(String id);
	/**
	 * 隐藏--->显示
	 * @param id
	 * @return
	 */
	public abstract String yc(String id);
	/**
	 * 排序位置
	 * @param id
	 * @return
	 */
	public abstract String wz(Tcar car);
	
	/**
	 * 计数
	 * @param brand
	 * @return
	 */
	public abstract int count(Object cid);
	
	/**
	 * 修改
	 * @param brand
	 * @return
	 */
	public abstract String updateExcel(Ecar car);
	
	/**
	 * 修改销量
	 * @param brand
	 * @return
	 */
	public abstract String upxl(Tcar car);
	
	
	
	

}
