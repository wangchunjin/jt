package com.epmis.co.vo;

import java.sql.Timestamp;

/**
 * CoReceive entity. @author MyEclipse Persistence Tools
 */

public class CoReceive {

	// Fields

	private String wid;
	private String receiveId;
	private String receiveUser;
	private String readFlag;
	private Timestamp readDate;
	private String deleteFlag;

	// Constructors

	/** default constructor */
	public CoReceive() {
	}

	/** minimal constructor */
	public CoReceive(String wid) {
		this.wid = wid;
	}

	/** full constructor */
	public CoReceive(String wid, String receiveId, String receiveUser,
			String readFlag, Timestamp readDate, String deleteFlag) {
		this.wid = wid;
		this.receiveId = receiveId;
		this.receiveUser = receiveUser;
		this.readFlag = readFlag;
		this.readDate = readDate;
		this.deleteFlag = deleteFlag;
	}

	// Property accessors

	public String getWid() {
		return this.wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getReceiveId() {
		return this.receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	public String getReceiveUser() {
		return this.receiveUser;
	}

	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}

	public String getReadFlag() {
		return this.readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public Timestamp getReadDate() {
		return this.readDate;
	}

	public void setReadDate(Timestamp readDate) {
		this.readDate = readDate;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}