package com.epmis.car.vo;
/**
 * 数据导入的判定类（类型）
 * @author Administrator
 *
 */
public class Ecar {
	private int id;
	private String naked_price;
	private String official_price;
	private String down_payment;
	private String monthly_payments;
	private String service_charge;
	private String purchase_tax;
	private String insurance;
	private String jiangjia;
	private String subsidy;//此字段客户自己添加
	private String down_ms;
	private String month_ms;
	
	public String getDown_ms() {
		return down_ms;
	}

	public void setDown_ms(String down_ms) {
		this.down_ms = down_ms;
	}

	public String getMonth_ms() {
		return month_ms;
	}

	public void setMonth_ms(String month_ms) {
		this.month_ms = month_ms;
	}

	public String getSubsidy() {
		return subsidy;
	}

	public void setSubsidy(String subsidy) {
		this.subsidy = subsidy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaked_price() {
		return naked_price;
	}
	public void setNaked_price(String naked_price) {
		this.naked_price = naked_price;
	}
	public String getOfficial_price() {
		return official_price;
	}
	public void setOfficial_price(String official_price) {
		this.official_price = official_price;
	}
	public String getDown_payment() {
		return down_payment;
	}
	public void setDown_payment(String down_payment) {
		this.down_payment = down_payment;
	}
	public String getMonthly_payments() {
		return monthly_payments;
	}
	public void setMonthly_payments(String monthly_payments) {
		this.monthly_payments = monthly_payments;
	}
	public String getService_charge() {
		return service_charge;
	}
	public void setService_charge(String service_charge) {
		this.service_charge = service_charge;
	}
	public String getPurchase_tax() {
		return purchase_tax;
	}
	public void setPurchase_tax(String purchase_tax) {
		this.purchase_tax = purchase_tax;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public String getJiangjia() {
		return jiangjia;
	}
	public void setJiangjia(String jiangjia) {
		this.jiangjia = jiangjia;
	}
	
	
	


}
