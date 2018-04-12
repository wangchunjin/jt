package com.epmis.co.vo;

import java.sql.Timestamp;

/**
 * CoNew entity. @author MyEclipse Persistence Tools
 */

public class CoNew implements java.io.Serializable {

	// Fields

	private String newId;
	private String wid;
	private String title;
	private Timestamp createdDate;
	private String createdBy;
	private String status;
	private String type;
	private String publishDate;
	private String content;
	private Integer clickCount;
	// Constructors

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	/** default constructor */
	public CoNew() {
	}

	/** minimal constructor */
	public CoNew(String newId, String wid) {
		this.newId = newId;
		this.wid = wid;
	}

	/** full constructor */
	public CoNew(String newId, String wid, String title, Timestamp createdDate,
			String createdBy, String status, String type, String publishDate,
			String content) {
		this.newId = newId;
		this.wid = wid;
		this.title = title;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.status = status;
		this.type = type;
		this.publishDate = publishDate;
		this.content = content;
	}

	// Property accessors

	public String getNewId() {
		return this.newId;
	}

	public void setNewId(String newId) {
		this.newId = newId;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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