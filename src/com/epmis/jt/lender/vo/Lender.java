package com.epmis.jt.lender.vo;
/**
 * 出借人
 * @author Administrator
 *
 */
public class Lender {
	private String lender_id;
	private String name;
	private String idcard;
	private String mobile;
	private String money;
	private String type;
	private String todey_money;
	
	
	public String getTodey_money() {
		return todey_money;
	}
	public void setTodey_money(String todey_money) {
		this.todey_money = todey_money;
	}
	private String createtime;
	
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getLender_id() {
		return lender_id;
	}
	public void setLender_id(String lender_id) {
		this.lender_id = lender_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	

}
