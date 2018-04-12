package com.epmis.km.vo;

import java.sql.Timestamp;

/**
 * KmFolder entity. @author MyEclipse Persistence Tools
 */

public class KmFolder implements java.io.Serializable {

	// Fields

	private String folderId;
	private String wid;
	private String projId;
	private String title;
	private String parentFolderId;
	private String docGrade; 
	private String folderName;
	private String createdBy;
	private Timestamp createdDate;
	private String deletedFlag;
	private String originalNode;
	private Integer defFraction;
	private String notlessFlag;
	private String defCode;
	private String description;
	private String defTemplate;
	private String wkflCatvalueId;
	private String formCode;
	private Integer seqNum;
	private String folderIdPath;
	private Integer orderByFlag;
	private String serviceId;

	// Constructors

	/** default constructor */
	public KmFolder() {
	}

	/** minimal constructor */
	public KmFolder(String folderId, String wid, String parentFolderId,
			String deletedFlag, String originalNode, Integer defFraction,
			String notlessFlag) {
		this.folderId = folderId;
		this.wid = wid;
		this.parentFolderId = parentFolderId;
		this.deletedFlag = deletedFlag;
		this.originalNode = originalNode;
		this.defFraction = defFraction;
		this.notlessFlag = notlessFlag;
	}

	/** full constructor */
	public KmFolder(String folderId, String wid, String projId, String title,
			String parentFolderId, String docGrade, String folderName,
			String createdBy, Timestamp createdDate, String deletedFlag,
			String originalNode, Integer defFraction, String notlessFlag,
			String defCode, String description, String defTemplate,
			String wkflCatvalueId, String formCode, Integer seqNum,
			String folderIdPath, Integer orderByFlag, String serviceId) {
		this.folderId = folderId;
		this.wid = wid;
		this.projId = projId;
		this.title = title;
		this.parentFolderId = parentFolderId;
		this.docGrade = docGrade;
		this.folderName = folderName;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.deletedFlag = deletedFlag;
		this.originalNode = originalNode;
		this.defFraction = defFraction;
		this.notlessFlag = notlessFlag;
		this.defCode = defCode;
		this.description = description;
		this.defTemplate = defTemplate;
		this.wkflCatvalueId = wkflCatvalueId;
		this.formCode = formCode;
		this.seqNum = seqNum;
		this.folderIdPath = folderIdPath;
		this.orderByFlag = orderByFlag;
		this.serviceId = serviceId;
	}

	// Property accessors

	public String getFolderId() {
		return this.folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getWid() {
		return this.wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getProjId() {
		return this.projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getParentFolderId() {
		return this.parentFolderId;
	}

	public void setParentFolderId(String parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public String getDocGrade() {
		return this.docGrade;
	}

	public void setDocGrade(String docGrade) {
		this.docGrade = docGrade;
	}

	public String getFolderName() {
		return this.folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
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

	public String getDeletedFlag() {
		return this.deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getOriginalNode() {
		return this.originalNode;
	}

	public void setOriginalNode(String originalNode) {
		this.originalNode = originalNode;
	}

	public Integer getDefFraction() {
		return this.defFraction;
	}

	public void setDefFraction(Integer defFraction) {
		this.defFraction = defFraction;
	}

	public String getNotlessFlag() {
		return this.notlessFlag;
	}

	public void setNotlessFlag(String notlessFlag) {
		this.notlessFlag = notlessFlag;
	}

	public String getDefCode() {
		return this.defCode;
	}

	public void setDefCode(String defCode) {
		this.defCode = defCode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDefTemplate() {
		return this.defTemplate;
	}

	public void setDefTemplate(String defTemplate) {
		this.defTemplate = defTemplate;
	}

	public String getWkflCatvalueId() {
		return this.wkflCatvalueId;
	}

	public void setWkflCatvalueId(String wkflCatvalueId) {
		this.wkflCatvalueId = wkflCatvalueId;
	}

	public String getFormCode() {
		return this.formCode;
	}

	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}

	public Integer getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getFolderIdPath() {
		return this.folderIdPath;
	}

	public void setFolderIdPath(String folderIdPath) {
		this.folderIdPath = folderIdPath;
	}

	public Integer getOrderByFlag() {
		return this.orderByFlag;
	}

	public void setOrderByFlag(Integer orderByFlag) {
		this.orderByFlag = orderByFlag;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

}