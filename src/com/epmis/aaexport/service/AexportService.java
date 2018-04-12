package com.epmis.aaexport.service;

import javax.servlet.http.HttpServletResponse;



public interface AexportService {
	
	void FWQKexport(HttpServletResponse response,String cuid,String lend_id,String lender_name,String batch_id);
	/**
	 * 对已还款的订单进行导出
	 * @param response
	 * @param rel_time
	 */
	void Orderexport(HttpServletResponse response,String rel_time,String cuid);

}
