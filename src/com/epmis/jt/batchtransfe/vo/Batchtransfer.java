package com.epmis.jt.batchtransfe.vo;
/**
 * 批量转账表
 * @author Administrator
 *
 */
public class Batchtransfer {
	/**
	 * 批量转账表id
	 */
	private String batch_id;
	/**
	 * 操作员id
	 */
	private String operator_id;
	/**
	 * 批量导出时间
	 */
	private String down_time;
	/**
	 * 导出文件地址
	 */
	private String down_file;
	/**
	 * 导出订单数
	 */
	private String down_orders;
	/**
	 * 导出总金额
	 */
	private String down_amount;
	/**
	 * 出借人表id
	 */
	private String lender_id;
	/**
	 * 批量导入时间
	 */
	private String up_time;
	/**
	 * 导入文件地址
	 */
	private String up_file;
	/**
	 * 导入订单数
	 */
	private String up_orders;
	/**
	 * 导入总金额
	 */
	private String up_amount;
	/**
	 * 平帐订单数
	 */
	private String balance_orders;
	/**
	 * 批量操作状态:0.已导出文件 1.已导入文件且账目平衡 2.已导入文件但账目不平
	 */
	private String batch_status;
	/**
	 * 0未删除   1删除
	 */
	private String isdel;
	/**
	 * 创建时间
	 */
	private String createtime;
	private String bd;
	
	
	public String getBd() {
		return bd;
	}
	public void setBd(String bd) {
		this.bd = bd;
	}
	public String getUp_time() {
		return up_time;
	}
	public void setUp_time(String up_time) {
		this.up_time = up_time;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public String getDown_time() {
		return down_time;
	}
	public void setDown_time(String down_time) {
		this.down_time = down_time;
	}
	public String getDown_file() {
		return down_file;
	}
	public void setDown_file(String down_file) {
		this.down_file = down_file;
	}
	public String getDown_orders() {
		return down_orders;
	}
	public void setDown_orders(String down_orders) {
		this.down_orders = down_orders;
	}
	public String getDown_amount() {
		return down_amount;
	}
	public void setDown_amount(String down_amount) {
		this.down_amount = down_amount;
	}
	public String getLender_id() {
		return lender_id;
	}
	public void setLender_id(String lender_id) {
		this.lender_id = lender_id;
	}
	public String getUp_file() {
		return up_file;
	}
	public void setUp_file(String up_file) {
		this.up_file = up_file;
	}
	public String getUp_orders() {
		return up_orders;
	}
	public void setUp_orders(String up_orders) {
		this.up_orders = up_orders;
	}
	public String getUp_amount() {
		return up_amount;
	}
	public void setUp_amount(String up_amount) {
		this.up_amount = up_amount;
	}
	public String getBalance_orders() {
		return balance_orders;
	}
	public void setBalance_orders(String balance_orders) {
		this.balance_orders = balance_orders;
	}
	public String getBatch_status() {
		return batch_status;
	}
	public void setBatch_status(String batch_status) {
		this.batch_status = batch_status;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	

}
