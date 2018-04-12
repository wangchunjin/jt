package com.epmis.km.vo;

import java.sql.Timestamp;

/**
 * KmDoc entity. @author MyEclipse Persistence Tools
 */

public class KmDoc implements java.io.Serializable {

	// Fields

	private String docId;
	private String wid;
	private String folderId;
	private String docNumber; 
	private String title;
	private String docType;
	private String status;
	private String priority;
	private String keyWords;
	private String summary;
	private String author;
	private String edition;
	private Integer editionSeq;
	private Timestamp editedDate;
	private String issueDept;
	private Timestamp effectDate;
	private Timestamp abateDate;
	private String delivFlag;
	private String docGrade;
	private String checkoutFlag;
	private String checkoutBy;
	private Integer fraction;
	private Timestamp targetStartDate;
	private Timestamp targetEndDate;
	private Timestamp actStartDate;
	private Timestamp actEndDate;
	private String createdBy;
	private String createdByName;
	private String moduleCode;
	
	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	private Timestamp createdDate;
	private Timestamp moveDate;
	private String assignedTo;
	private String p3ProjId;
	private String p3WbsId;
	private String projId;
	private String wbsId;
	private String baseItemType;
	private String baseMasterKey;
	private Timestamp dateLinked;
	private String privateLoc;
	private String publicLoc;
	private String fileName;
	private String templateFlag;
	private String docNote;
	private String deletedFlag;
	private String absId;
	private String volumeId;
	private String certifiedType;
	private String docNumber1;
	private Integer isDocrec;
	private String docrecUserId;
	private String docrecStatus;
	private String tzFlag;
	private Integer docCopys;
	private Integer docPageno;
	private String tzType;
	private String tzUnion;
	private String tzMajor;
	private String tzDesco;
	private String editionDes;
	private String approvedBy;
	private Timestamp approvedDate;
	private String effectiveFlag;
	private String uploadFlag;
	private String serviceId;
	private String baseLinkId;
	private String htId;
	// Constructors

	/** default constructor */
	public KmDoc() {
	}

	/** minimal constructor */
	public KmDoc(String docId, String wid, String folderId, String docNumber,
			String delivFlag, String docGrade, String checkoutFlag,
			Timestamp createdDate, String templateFlag, String deletedFlag,
			String certifiedType) {
		this.docId = docId;
		this.wid = wid;
		this.folderId = folderId;
		this.docNumber = docNumber;
		this.delivFlag = delivFlag;
		this.docGrade = docGrade;
		this.checkoutFlag = checkoutFlag;
		this.createdDate = createdDate;
		this.templateFlag = templateFlag;
		this.deletedFlag = deletedFlag;
		this.certifiedType = certifiedType;
	}

	/** full constructor */
	public KmDoc(String docId, String wid, String folderId, String docNumber,
			String title, String docType, String status, String priority,
			String keyWords, String summary, String author, String edition,
			Integer editionSeq, Timestamp editedDate, String issueDept,
			Timestamp effectDate, Timestamp abateDate, String delivFlag,
			String docGrade, String checkoutFlag, String checkoutBy,
			Integer fraction, Timestamp targetStartDate,
			Timestamp targetEndDate, Timestamp actStartDate,
			Timestamp actEndDate, String createdBy, Timestamp createdDate,
			Timestamp moveDate, String assignedTo, String p3ProjId,
			String p3WbsId, String projId, String wbsId, String baseItemType,
			String baseMasterKey, Timestamp dateLinked, String privateLoc,
			String publicLoc, String fileName, String templateFlag,
			String docNote, String deletedFlag, String absId, String volumeId,
			String certifiedType, String docNumber1, Integer isDocrec,
			String docrecUserId, String docrecStatus, String tzFlag,
			Integer docCopys, Integer docPageno, String tzType, String tzUnion,
			String tzMajor, String tzDesco, String editionDes,
			String approvedBy, Timestamp approvedDate, String effectiveFlag,
			String uploadFlag, String serviceId, String baseLinkId,String htId) {
		this.docId = docId;
		this.wid = wid;
		this.folderId = folderId;
		this.docNumber = docNumber;
		this.title = title;
		this.docType = docType;
		this.status = status;
		this.priority = priority;
		this.keyWords = keyWords;
		this.summary = summary;
		this.author = author;
		this.edition = edition;
		this.editionSeq = editionSeq;
		this.editedDate = editedDate;
		this.issueDept = issueDept;
		this.effectDate = effectDate;
		this.abateDate = abateDate;
		this.delivFlag = delivFlag;
		this.docGrade = docGrade;
		this.checkoutFlag = checkoutFlag;
		this.checkoutBy = checkoutBy;
		this.fraction = fraction;
		this.targetStartDate = targetStartDate;
		this.targetEndDate = targetEndDate;
		this.actStartDate = actStartDate;
		this.actEndDate = actEndDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.moveDate = moveDate;
		this.assignedTo = assignedTo;
		this.p3ProjId = p3ProjId;
		this.p3WbsId = p3WbsId;
		this.projId = projId;
		this.wbsId = wbsId;
		this.baseItemType = baseItemType;
		this.baseMasterKey = baseMasterKey;
		this.dateLinked = dateLinked;
		this.privateLoc = privateLoc;
		this.publicLoc = publicLoc;
		this.fileName = fileName;
		this.templateFlag = templateFlag;
		this.docNote = docNote;
		this.deletedFlag = deletedFlag;
		this.absId = absId;
		this.volumeId = volumeId;
		this.certifiedType = certifiedType;
		this.docNumber1 = docNumber1;
		this.isDocrec = isDocrec;
		this.docrecUserId = docrecUserId;
		this.docrecStatus = docrecStatus;
		this.tzFlag = tzFlag;
		this.docCopys = docCopys;
		this.docPageno = docPageno;
		this.tzType = tzType;
		this.tzUnion = tzUnion;
		this.tzMajor = tzMajor;
		this.tzDesco = tzDesco;
		this.editionDes = editionDes;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.effectiveFlag = effectiveFlag;
		this.uploadFlag = uploadFlag;
		this.serviceId = serviceId;
		this.baseLinkId = baseLinkId;
		this.htId = htId;
	}

	// Property accessors

	public String getDocId() {
		return this.docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getWid() {
		return this.wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getFolderId() {
		return this.folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getDocNumber() {
		return this.docNumber;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDocType() {
		return this.docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getKeyWords() {
		return this.keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEdition() {
		return this.edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public Integer getEditionSeq() {
		return this.editionSeq;
	}

	public void setEditionSeq(Integer editionSeq) {
		this.editionSeq = editionSeq;
	}

	public Timestamp getEditedDate() {
		return this.editedDate;
	}

	public void setEditedDate(Timestamp editedDate) {
		this.editedDate = editedDate;
	}

	public String getIssueDept() {
		return this.issueDept;
	}

	public void setIssueDept(String issueDept) {
		this.issueDept = issueDept;
	}

	public Timestamp getEffectDate() {
		return this.effectDate;
	}

	public void setEffectDate(Timestamp effectDate) {
		this.effectDate = effectDate;
	}

	public Timestamp getAbateDate() {
		return this.abateDate;
	}

	public void setAbateDate(Timestamp abateDate) {
		this.abateDate = abateDate;
	}

	public String getDelivFlag() {
		return this.delivFlag;
	}

	public void setDelivFlag(String delivFlag) {
		this.delivFlag = delivFlag;
	}

	public String getDocGrade() {
		return this.docGrade;
	}

	public void setDocGrade(String docGrade) {
		this.docGrade = docGrade;
	}

	public String getCheckoutFlag() {
		return this.checkoutFlag;
	}

	public void setCheckoutFlag(String checkoutFlag) {
		this.checkoutFlag = checkoutFlag;
	}

	public String getCheckoutBy() {
		return this.checkoutBy;
	}

	public void setCheckoutBy(String checkoutBy) {
		this.checkoutBy = checkoutBy;
	}

	public Integer getFraction() {
		return this.fraction;
	}

	public void setFraction(Integer fraction) {
		this.fraction = fraction;
	}

	public Timestamp getTargetStartDate() {
		return this.targetStartDate;
	}

	public void setTargetStartDate(Timestamp targetStartDate) {
		this.targetStartDate = targetStartDate;
	}

	public Timestamp getTargetEndDate() {
		return this.targetEndDate;
	}

	public void setTargetEndDate(Timestamp targetEndDate) {
		this.targetEndDate = targetEndDate;
	}

	public Timestamp getActStartDate() {
		return this.actStartDate;
	}

	public void setActStartDate(Timestamp actStartDate) {
		this.actStartDate = actStartDate;
	}

	public Timestamp getActEndDate() {
		return this.actEndDate;
	}

	public void setActEndDate(Timestamp actEndDate) {
		this.actEndDate = actEndDate;
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

	public Timestamp getMoveDate() {
		return this.moveDate;
	}

	public void setMoveDate(Timestamp moveDate) {
		this.moveDate = moveDate;
	}

	public String getAssignedTo() {
		return this.assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getP3ProjId() {
		return this.p3ProjId;
	}

	public void setP3ProjId(String p3ProjId) {
		this.p3ProjId = p3ProjId;
	}

	public String getP3WbsId() {
		return this.p3WbsId;
	}

	public void setP3WbsId(String p3WbsId) {
		this.p3WbsId = p3WbsId;
	}

	public String getProjId() {
		return this.projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getWbsId() {
		return this.wbsId;
	}

	public void setWbsId(String wbsId) {
		this.wbsId = wbsId;
	}

	public String getBaseItemType() {
		return this.baseItemType;
	}

	public void setBaseItemType(String baseItemType) {
		this.baseItemType = baseItemType;
	}

	public String getBaseMasterKey() {
		return this.baseMasterKey;
	}

	public void setBaseMasterKey(String baseMasterKey) {
		this.baseMasterKey = baseMasterKey;
	}

	public Timestamp getDateLinked() {
		return this.dateLinked;
	}

	public void setDateLinked(Timestamp dateLinked) {
		this.dateLinked = dateLinked;
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

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTemplateFlag() {
		return this.templateFlag;
	}

	public void setTemplateFlag(String templateFlag) {
		this.templateFlag = templateFlag;
	}

	public String getDocNote() {
		return this.docNote;
	}

	public void setDocNote(String docNote) {
		this.docNote = docNote;
	}

	public String getDeletedFlag() {
		return this.deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getAbsId() {
		return this.absId;
	}

	public void setAbsId(String absId) {
		this.absId = absId;
	}

	public String getVolumeId() {
		return this.volumeId;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	public String getCertifiedType() {
		return this.certifiedType;
	}

	public void setCertifiedType(String certifiedType) {
		this.certifiedType = certifiedType;
	}

	public String getDocNumber1() {
		return this.docNumber1;
	}

	public void setDocNumber1(String docNumber1) {
		this.docNumber1 = docNumber1;
	}

	public Integer getIsDocrec() {
		return this.isDocrec;
	}

	public void setIsDocrec(Integer isDocrec) {
		this.isDocrec = isDocrec;
	}

	public String getDocrecUserId() {
		return this.docrecUserId;
	}

	public void setDocrecUserId(String docrecUserId) {
		this.docrecUserId = docrecUserId;
	}

	public String getDocrecStatus() {
		return this.docrecStatus;
	}

	public void setDocrecStatus(String docrecStatus) {
		this.docrecStatus = docrecStatus;
	}

	public String getTzFlag() {
		return this.tzFlag;
	}

	public void setTzFlag(String tzFlag) {
		this.tzFlag = tzFlag;
	}

	public Integer getDocCopys() {
		return this.docCopys;
	}

	public void setDocCopys(Integer docCopys) {
		this.docCopys = docCopys;
	}

	public Integer getDocPageno() {
		return this.docPageno;
	}

	public void setDocPageno(Integer docPageno) {
		this.docPageno = docPageno;
	}

	public String getTzType() {
		return this.tzType;
	}

	public void setTzType(String tzType) {
		this.tzType = tzType;
	}

	public String getTzUnion() {
		return this.tzUnion;
	}

	public void setTzUnion(String tzUnion) {
		this.tzUnion = tzUnion;
	}

	public String getTzMajor() {
		return this.tzMajor;
	}

	public void setTzMajor(String tzMajor) {
		this.tzMajor = tzMajor;
	}

	public String getTzDesco() {
		return this.tzDesco;
	}

	public void setTzDesco(String tzDesco) {
		this.tzDesco = tzDesco;
	}

	public String getEditionDes() {
		return this.editionDes;
	}

	public void setEditionDes(String editionDes) {
		this.editionDes = editionDes;
	}

	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Timestamp getApprovedDate() {
		return this.approvedDate;
	}

	public void setApprovedDate(Timestamp approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getEffectiveFlag() {
		return this.effectiveFlag;
	}

	public void setEffectiveFlag(String effectiveFlag) {
		this.effectiveFlag = effectiveFlag;
	}

	public String getUploadFlag() {
		return this.uploadFlag;
	}

	public void setUploadFlag(String uploadFlag) {
		this.uploadFlag = uploadFlag;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getBaseLinkId() {
		return this.baseLinkId;
	}

	public void setBaseLinkId(String baseLinkId) {
		this.baseLinkId = baseLinkId;
	}

	public String getHtId() {
		return htId;
	}

	public void setHtId(String htId) {
		this.htId = htId;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	

}