package com.epmis.km.vo;

import java.sql.Timestamp;

/**
 * KmAttch entity. @author MyEclipse Persistence Tools
 */

public class KmAttch {

	// Fields

	private String attchId;
	private String wid;
	private String docId;
	private Timestamp dateLinked;
	private String subject;
	private String privateLoc;
	private String publicLoc;
	private String authorName; 
	private String remark;

	// Constructors

	/** default constructor */
	public KmAttch() {
	}

	/** minimal constructor */
	public KmAttch(String attchId, String wid, String docId) {
		this.attchId = attchId;
		this.wid = wid;
		this.docId = docId;
	}

	/** full constructor */
	public KmAttch(String attchId, String wid, String docId,
			Timestamp dateLinked, String subject, String privateLoc,
			String publicLoc, String authorName, String remark) {
		this.attchId = attchId;
		this.wid = wid;
		this.docId = docId;
		this.dateLinked = dateLinked;
		this.subject = subject;
		this.privateLoc = privateLoc;
		this.publicLoc = publicLoc;
		this.authorName = authorName;
		this.remark = remark;
	}

	// Property accessors

	public String getAttchId() {
		return this.attchId;
	}

	public void setAttchId(String attchId) {
		this.attchId = attchId;
	}

	public String getWid() {
		return this.wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getDocId() {
		return this.docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public Timestamp getDateLinked() {
		return this.dateLinked;
	}

	public void setDateLinked(Timestamp dateLinked) {
		this.dateLinked = dateLinked;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPrivateLoc() {
		return this.privateLoc;
	}

	public void setPrivateLoc(String privateLoc) {
		this.privateLoc = privateLoc;
	}

	public String getPublicLoc() {
		return this.publicLoc;
	}

	public void setPublicLoc(String publicLoc) {
		this.publicLoc = publicLoc;
	}

	public String getAuthorName() {
		return this.authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}