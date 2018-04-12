package com.epmis.co.vo;

import java.sql.Timestamp;

/**
 * CoNew entity. @author MyEclipse Persistence Tools
 */

public class CoNotify implements java.io.Serializable {

	// Fields

	private String notifyId;
	private String wid;
	private String title;
	private Timestamp createdDate;
	private String createdBy;
	private String status;
	private Timestamp beginDate;
	private Timestamp endDate;
	private String publishDate;
	private String content;
	private String toUsers;
	private Integer clickCount;
	 

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	public String getToUsers() {
		return toUsers;
	}

	public void setToUsers(String toUsers) {
		this.toUsers = toUsers;
	}



 
	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public Timestamp getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Timestamp beginDate) {
		this.beginDate = beginDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getWid() {
		return this.wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
 
	public String getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}