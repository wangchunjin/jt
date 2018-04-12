package com.epmis.co.vo;

import java.sql.Timestamp;

/**
 * CoDispatch entity. @author MyEclipse Persistence Tools
 */

public class CoDispatch {

	// Fields

	private String wid;
	private String dispatchId;
	private String title;
	private String content;
	private String createdBy;
	private Timestamp createdDate;
	private String receiveUser;
	private String deleteFlag;

	// Constructors

	/** default constructor */
	public CoDispatch() {
	}

	/** minimal constructor */
	public CoDispatch(String wid) {
		this.wid = wid;
	}

	/** full constructor */
	public CoDispatch(String wid, String dispatchId, String title,
			String content, String createdBy, Timestamp createdDate,
			String receiveUser, String deleteFlag) {
		this.wid = wid;
		this.dispatchId = dispatchId;
		this.title = title;
		this.content = content;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.receiveUser = receiveUser;
		this.deleteFlag = deleteFlag;
	}

	// Property accessors

	public String getWid() {
		return this.wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getDispatchId() {
		return this.dispatchId;
	}

	public void setDispatchId(String dispatchId) {
		this.dispatchId = dispatchId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getReceiveUser() {
		return this.receiveUser;
	}

	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}

	public String getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}