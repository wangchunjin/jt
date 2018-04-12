package com.epmis.jt.orderinfo.vo;
/**
 * 订单类
 * @author Administrator
 *
 */
public class Orderinfo {
	/**
	 * 订单id
	 */
	private String order_id;
	/**
	 * 订单编号
	 */
	private String order_number;
	/**
	 * 用户表外键
	 */
	private String client_id;
	/**
	 * 借款申请时间
	 */
	private String borrow_time;
	/**
	 * 出借人表外键
	 */
	private String lend_id;
	/**
	 * 借款金额
	 */
	private String amount;
	/**
	 * 借款周期
	 */
	private String period;
	/**
	 * 利息
	 */
	private String interest;
	/**
	 * 滞纳金(实际)
	 */
	private String overdue_payment_rel;
	/**
	 * 滞纳金（理论）
	 */
	private String overdue_payment_the;
	/**
	 * 借款用途  与用途表关联
	 */
	private String pur_id;
	/**
	 * 实际到账金额
	 */
	private String real_amt;
	/**
	 * 预收服务费
	 */
	private String commision;
	/**
	 * 订单审核状态 0.审核中 1.通过 2.失败
	 */
	private String approve_status;
	/**
	 * 发起审核时间
	 */
	private String approve_time;
	/**
	 * 逾期时间
	 */
	private String overdue_time;
	/**
	 * 订单打款状态 0.待打款 1.打款成功 2.已还款 3.逾期 4.异常订单 
	 */
	private String transfer_status;
	/**
	 * 放款时间
	 */
	private String transfer_time;
	/**
	 * 应还款日期
	 */
	private String repay_time;
	/**
	 * 应还款金额
	 */
	private String repay_money;
	/**
	 * 实际还款日期
	 */
	private String rel_time;
	/**
	 * 实际还款金额
	 */
	private String rel_money;
	/**
	 * 上次借款最大额度
	 */
	private String last_loan_amount;
	/**
	 * 打款渠道
	 */
	private String fun_change;
	/**
	 * 通讯录人次
	 */
	private String maile_num;
	/**
	 * 诚信报告 id(与诚信表关联）
	 */
	private String good_report;
	private String bank_name;
	private String bank_id;
	private String createtime;
	
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBank_id() {
		return bank_id;
	}
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getBorrow_time() {
		return borrow_time;
	}
	public void setBorrow_time(String borrow_time) {
		this.borrow_time = borrow_time;
	}
	public String getLend_id() {
		return lend_id;
	}
	public void setLend_id(String lend_id) {
		this.lend_id = lend_id;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getOverdue_payment_rel() {
		return overdue_payment_rel;
	}
	public void setOverdue_payment_rel(String overdue_payment_rel) {
		this.overdue_payment_rel = overdue_payment_rel;
	}
	public String getOverdue_payment_the() {
		return overdue_payment_the;
	}
	public void setOverdue_payment_the(String overdue_payment_the) {
		this.overdue_payment_the = overdue_payment_the;
	}
	public String getPur_id() {
		return pur_id;
	}
	public void setPur_id(String pur_id) {
		this.pur_id = pur_id;
	}
	public String getReal_amt() {
		return real_amt;
	}
	public void setReal_amt(String real_amt) {
		this.real_amt = real_amt;
	}
	public String getCommision() {
		return commision;
	}
	public void setCommision(String commision) {
		this.commision = commision;
	}
	public String getApprove_status() {
		return approve_status;
	}
	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
	}
	public String getApprove_time() {
		return approve_time;
	}
	public void setApprove_time(String approve_time) {
		this.approve_time = approve_time;
	}
	public String getOverdue_time() {
		return overdue_time;
	}
	public void setOverdue_time(String overdue_time) {
		this.overdue_time = overdue_time;
	}
	public String getTransfer_status() {
		return transfer_status;
	}
	public void setTransfer_status(String transfer_status) {
		this.transfer_status = transfer_status;
	}
	public String getTransfer_time() {
		return transfer_time;
	}
	public void setTransfer_time(String transfer_time) {
		this.transfer_time = transfer_time;
	}
	public String getRepay_time() {
		return repay_time;
	}
	public void setRepay_time(String repay_time) {
		this.repay_time = repay_time;
	}
	public String getRepay_money() {
		return repay_money;
	}
	public void setRepay_money(String repay_money) {
		this.repay_money = repay_money;
	}
	public String getRel_time() {
		return rel_time;
	}
	public void setRel_time(String rel_time) {
		this.rel_time = rel_time;
	}
	public String getRel_money() {
		return rel_money;
	}
	public void setRel_money(String rel_money) {
		this.rel_money = rel_money;
	}
	public String getLast_loan_amount() {
		return last_loan_amount;
	}
	public void setLast_loan_amount(String last_loan_amount) {
		this.last_loan_amount = last_loan_amount;
	}
	public String getFun_change() {
		return fun_change;
	}
	public void setFun_change(String fun_change) {
		this.fun_change = fun_change;
	}
	public String getMaile_num() {
		return maile_num;
	}
	public void setMaile_num(String maile_num) {
		this.maile_num = maile_num;
	}
	public String getGood_report() {
		return good_report;
	}
	public void setGood_report(String good_report) {
		this.good_report = good_report;
	}
	

}
