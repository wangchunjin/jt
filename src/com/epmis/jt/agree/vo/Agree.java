package com.epmis.jt.agree.vo;
/**
 * 协议表
 * @author Administrator
 *
 */
public class Agree {
	private String id;
	private String register_content;
	private String createtime;
	private String loan_content;
	private String isdel;
	private String Insurance_content;
	private String lender_content;	
	public String getInsurance_content() {
		return Insurance_content;
	}
	public void setInsurance_content(String insurance_content) {
		Insurance_content = insurance_content;
	}
	public String getLender_content() {
		return lender_content;
	}
	public void setLender_content(String lender_content) {
		this.lender_content = lender_content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRegister_content() {
		return register_content;
	}
	public void setRegister_content(String register_content) {
		this.register_content = register_content;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getLoan_content() {
		return loan_content;
	}
	public void setLoan_content(String loan_content) {
		this.loan_content = loan_content;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	

}
