package com.epmis.jt.agree.service;

import java.util.List;
import java.util.Map;

import com.epmis.jt.agree.vo.Agree;



public interface AgreeService {
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public abstract List<Map<String, Object>> findById(String id);
	
	/**
	 * 修改注册协议
	 * @param brand
	 * @return
	 */
	public abstract String updateReg(Agree agree);
	
	/**
	 * 修改借款协议
	 * @param brand
	 * @return
	 */
	public abstract String updateLoan(Agree agree);
	/**
	 * 修改保险协议
	 * @param brand
	 * @return
	 */
	public abstract String updateInsurance(Agree agree);
	/**
	 * 修改出借人协议
	 * @param brand
	 * @return
	 */
	public abstract String updatelender(Agree agree);

}
