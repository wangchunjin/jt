package com.epmis.app.vo;

import java.sql.Timestamp;

public class HealthInfo {
	
	/**
	 * WID
	 */
	private String WID;
	/**
	 * 健康ID
	 */
	private String HEALTHID;
	/**
	 * 用户ID
	 */
	private String USERID;
	/**
	 * 血压
	 */
	private Double BLOODPRESS;
	/**
	 * 血糖
	 */
	private Double BLOODSUGAR;
	/**
	 * 血常规
	 */
	private Double BLOODRT;
	/**
	 * 体温
	 */
	private Double TEMPERATURE;
	/**
	 * 体重
	 */
	private Double WEIGHT;
	/**
	 * 身高
	 */
	private Double HEIGHT;
	/**
	 * 创建人
	 */
	private String CREATEBY;
	/**
	 * 创建时间
	 */
	private Timestamp CREATETIME;
	/**
	 * 修改人
	 */
	private String UPDATEBY;
	/**
	 * 修改时间
	 */
	private Timestamp UPDATETIME;
	/**
	 * 排序号
	 */
	private Integer SORTNUMBER;
	
	public String getWID() {
		return WID;
	}
	public void setWID(String wID) {
		WID = wID;
	}
	public String getHEALTHID() {
		return HEALTHID;
	}
	public void setHEALTHID(String hEALTHID) {
		HEALTHID = hEALTHID;
	}
	public String getUSERID() {
		return USERID;
	}
	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}
	public Double getBLOODPRESS() {
		return BLOODPRESS;
	}
	public void setBLOODPRESS(Double bLOODPRESS) {
		BLOODPRESS = bLOODPRESS;
	}
	public Double getBLOODSUGAR() {
		return BLOODSUGAR;
	}
	public void setBLOODSUGAR(Double bLOODSUGAR) {
		BLOODSUGAR = bLOODSUGAR;
	}
	public Double getBLOODRT() {
		return BLOODRT;
	}
	public void setBLOODRT(Double bLOODRT) {
		BLOODRT = bLOODRT;
	}
	public Double getTEMPERATURE() {
		return TEMPERATURE;
	}
	public void setTEMPERATURE(Double tEMPERATURE) {
		TEMPERATURE = tEMPERATURE;
	}
	public Double getWEIGHT() {
		return WEIGHT;
	}
	public void setWEIGHT(Double wEIGHT) {
		WEIGHT = wEIGHT;
	}
	public Double getHEIGHT() {
		return HEIGHT;
	}
	public void setHEIGHT(Double hEIGHT) {
		HEIGHT = hEIGHT;
	}
	public String getCREATEBY() {
		return CREATEBY;
	}
	public void setCREATEBY(String cREATEBY) {
		CREATEBY = cREATEBY;
	}
	public Timestamp getCREATETIME() {
		return CREATETIME;
	}
	public void setCREATETIME(Timestamp cREATETIME) {
		CREATETIME = cREATETIME;
	}
	public String getUPDATEBY() {
		return UPDATEBY;
	}
	public void setUPDATEBY(String uPDATEBY) {
		UPDATEBY = uPDATEBY;
	}
	public Timestamp getUPDATETIME() {
		return UPDATETIME;
	}
	public void setUPDATETIME(Timestamp uPDATETIME) {
		UPDATETIME = uPDATETIME;
	}
	public Integer getSORTNUMBER() {
		return SORTNUMBER;
	}
	public void setSORTNUMBER(Integer sORTNUMBER) {
		SORTNUMBER = sORTNUMBER;
	}
	@Override
	public String toString() {
		return "HealthInfo [WID=" + WID + ", HEALTHID=" + HEALTHID
				+ ", USERID=" + USERID + ", BLOODPRESS=" + BLOODPRESS
				+ ", BLOODSUGAR=" + BLOODSUGAR + ", BLOODRT=" + BLOODRT
				+ ", TEMPERATURE=" + TEMPERATURE + ", WEIGHT=" + WEIGHT
				+ ", HEIGHT=" + HEIGHT + ", CREATEBY=" + CREATEBY
				+ ", CREATETIME=" + CREATETIME + ", UPDATEBY=" + UPDATEBY
				+ ", UPDATETIME=" + UPDATETIME + ", SORTNUMBER=" + SORTNUMBER
				+ "]";
	}
	
}
