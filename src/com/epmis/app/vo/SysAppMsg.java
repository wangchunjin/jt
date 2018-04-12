package com.epmis.app.vo;

import java.sql.Timestamp;

/**
 * SysAppMsg entity. @author MyEclipse Persistence Tools
 */

public class SysAppMsg {

	// Fields

	private String id;
	private String wid;
	private String title;
	private String content;
	private String flag;
	private String msgType;
	private String toUsers;
	private Timestamp calendarTime;
	private Timestamp remindTime;
	private String createdBy;
	private Timestamp createdTime;
	private String projId;
	private String remark;
	private String linkId;

	// Constructors

	public Timestamp getCalendarTime() {
		return calendarTime;
	}

	public void setCalendarTime(Timestamp calendarTime) {
		this.calendarTime = calendarTime;
	}

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	/** default constructor */
	public SysAppMsg() {
	}

	/** minimal constructor */
	public SysAppMsg(String id, String wid) {
		this.id = id;
		this.wid = wid;
	}

	/** full constructor */
	public SysAppMsg(String id, String wid, String title, String content,
			String flag, String msgType, String toUsers, Timestamp remindTime,
			String createdBy, Timestamp createdTime, String projId,
			String remark) {
		this.id = id;
		this.wid = wid;
		this.title = title;
		this.content = content;
		this.flag = flag;
		this.msgType = msgType;
		this.toUsers = toUsers;
		this.remindTime = remindTime;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.projId = projId;
		this.remark = remark;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

 
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getToUsers() {
		return this.toUsers;
	}

	public void setToUsers(String toUsers) {
		this.toUsers = toUsers;
	}

	public Timestamp getRemindTime() {
		return this.remindTime;
	}

	public void setRemindTime(Timestamp remindTime) {
		this.remindTime = remindTime;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public String getProjId() {
		return this.projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}