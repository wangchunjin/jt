package com.epmis.sm.vo;

import java.sql.Timestamp;

/**
 * SmPlan entity. @author MyEclipse Persistence Tools
 */

public class SmPlan implements java.io.Serializable {

	// Fields

	private String planId;
	private String wid;
	private String projId;
	private String parentPlanId;
	private String planShortName;
	private String planName;
	private Integer bottomValue;
	private String tendCompany;
	private String manageDept;
	private String hazardLevel;
	private String identifyBy;
	private String measure;
	private String nodeType;
	private String planStatus;
	private String relateFlag;
	private String relateType;
	private String p3ProjId;
	private String p3ProjShortName;
	private String p3WbsId;
	private String p3WbsShortName;
	private String p3WbsName;
	private String p3TaskId;
	private String p3TaskCode;
	private String p3TaskName;
	private Timestamp p3StartDate;
	private Timestamp p3EndDate;
	private Timestamp targetStartDate;
	private Timestamp targetEndDate;
	private Timestamp actStartDate;
	private Timestamp actEndDate;
	private String isApproved;
	private String certifiedType;
	private String obsUserId;
	private String planIdPath;
	private String createUserId;
	private Integer granttId;
	private Integer granttParentId;
	private Timestamp startDate;
	private Timestamp endDate;
	private Integer seqNum;
	private String remark;
	private String isselected;
	private String priority;
	private String fromDept;
	private String fromLinkman;
	private Timestamp fromDate;
	private String auditDept;
	private String auditLinkman;
	private String result;
	private Integer fraction;
	private String status;
	private String chkNumber;
	private String checkUserName;
	private String closedFlag;
	private String assGuides;
	private String docSwbsId;
	private String folderId;
	private String swbsId;
	private String isMajor;
	private String codeId;
	// Constructors

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	// Constructors

	/** default constructor */
	public SmPlan() {
	}

	/** minimal constructor */
	public SmPlan(String planId, String projId, String parentPlanId,
			Integer seqNum) {
		this.planId = planId;
		this.projId = projId;
		this.parentPlanId = parentPlanId;
		this.seqNum = seqNum;
	}

	/** full constructor */
	public SmPlan(String planId, String wid, String projId,
			String parentPlanId, String planShortName, String planName,
			Integer bottomValue, String tendCompany, String manageDept,
			String hazardLevel, String identifyBy, String measure,
			String nodeType, String planStatus, String relateFlag,
			String relateType, String p3ProjId, String p3ProjShortName,
			String p3WbsId, String p3WbsShortName, String p3WbsName,
			String p3TaskId, String p3TaskCode, String p3TaskName,
			Timestamp p3StartDate, Timestamp p3EndDate,
			Timestamp targetStartDate, Timestamp targetEndDate,
			Timestamp actStartDate, Timestamp actEndDate, String isApproved,
			String certifiedType, String obsUserId, String planIdPath,
			String createUserId, Integer granttId, Integer granttParentId,
			Timestamp startDate, Timestamp endDate, Integer seqNum,
			String remark, String isselected, String priority, String fromDept,
			String fromLinkman, Timestamp fromDate, String auditDept,
			String auditLinkman, String result, Integer fraction,
			String status, String chkNumber, String checkUserName,
			String closedFlag, String assGuides, String docSwbsId,
			String folderId, String swbsId, String isMajor) {
		this.planId = planId;
		this.wid = wid;
		this.projId = projId;
		this.parentPlanId = parentPlanId;
		this.planShortName = planShortName;
		this.planName = planName;
		this.bottomValue = bottomValue;
		this.tendCompany = tendCompany;
		this.manageDept = manageDept;
		this.hazardLevel = hazardLevel;
		this.identifyBy = identifyBy;
		this.measure = measure;
		this.nodeType = nodeType;
		this.planStatus = planStatus;
		this.relateFlag = relateFlag;
		this.relateType = relateType;
		this.p3ProjId = p3ProjId;
		this.p3ProjShortName = p3ProjShortName;
		this.p3WbsId = p3WbsId;
		this.p3WbsShortName = p3WbsShortName;
		this.p3WbsName = p3WbsName;
		this.p3TaskId = p3TaskId;
		this.p3TaskCode = p3TaskCode;
		this.p3TaskName = p3TaskName;
		this.p3StartDate = p3StartDate;
		this.p3EndDate = p3EndDate;
		this.targetStartDate = targetStartDate;
		this.targetEndDate = targetEndDate;
		this.actStartDate = actStartDate;
		this.actEndDate = actEndDate;
		this.isApproved = isApproved;
		this.certifiedType = certifiedType;
		this.obsUserId = obsUserId;
		this.planIdPath = planIdPath;
		this.createUserId = createUserId;
		this.granttId = granttId;
		this.granttParentId = granttParentId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.seqNum = seqNum;
		this.remark = remark;
		this.isselected = isselected;
		this.priority = priority;
		this.fromDept = fromDept;
		this.fromLinkman = fromLinkman;
		this.fromDate = fromDate;
		this.auditDept = auditDept;
		this.auditLinkman = auditLinkman;
		this.result = result;
		this.fraction = fraction;
		this.status = status;
		this.chkNumber = chkNumber;
		this.checkUserName = checkUserName;
		this.closedFlag = closedFlag;
		this.assGuides = assGuides;
		this.docSwbsId = docSwbsId;
		this.folderId = folderId;
		this.swbsId = swbsId;
		this.isMajor = isMajor;
	}

	// Property accessors

	public String getPlanId() {
		return this.planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
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

	public String getParentPlanId() {
		return this.parentPlanId;
	}

	public void setParentPlanId(String parentPlanId) {
		this.parentPlanId = parentPlanId;
	}

	public String getPlanShortName() {
		return this.planShortName;
	}

	public void setPlanShortName(String planShortName) {
		this.planShortName = planShortName;
	}

	public String getPlanName() {
		return this.planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getBottomValue() {
		return this.bottomValue;
	}

	public void setBottomValue(Integer bottomValue) {
		this.bottomValue = bottomValue;
	}

	public String getTendCompany() {
		return this.tendCompany;
	}

	public void setTendCompany(String tendCompany) {
		this.tendCompany = tendCompany;
	}

	public String getManageDept() {
		return this.manageDept;
	}

	public void setManageDept(String manageDept) {
		this.manageDept = manageDept;
	}

	public String getHazardLevel() {
		return this.hazardLevel;
	}

	public void setHazardLevel(String hazardLevel) {
		this.hazardLevel = hazardLevel;
	}

	public String getIdentifyBy() {
		return this.identifyBy;
	}

	public void setIdentifyBy(String identifyBy) {
		this.identifyBy = identifyBy;
	}

	public String getMeasure() {
		return this.measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public String getNodeType() {
		return this.nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getPlanStatus() {
		return this.planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public String getRelateFlag() {
		return this.relateFlag;
	}

	public void setRelateFlag(String relateFlag) {
		this.relateFlag = relateFlag;
	}

	public String getRelateType() {
		return this.relateType;
	}

	public void setRelateType(String relateType) {
		this.relateType = relateType;
	}

	public String getP3ProjId() {
		return this.p3ProjId;
	}

	public void setP3ProjId(String p3ProjId) {
		this.p3ProjId = p3ProjId;
	}

	public String getP3ProjShortName() {
		return this.p3ProjShortName;
	}

	public void setP3ProjShortName(String p3ProjShortName) {
		this.p3ProjShortName = p3ProjShortName;
	}

	public String getP3WbsId() {
		return this.p3WbsId;
	}

	public void setP3WbsId(String p3WbsId) {
		this.p3WbsId = p3WbsId;
	}

	public String getP3WbsShortName() {
		return this.p3WbsShortName;
	}

	public void setP3WbsShortName(String p3WbsShortName) {
		this.p3WbsShortName = p3WbsShortName;
	}

	public String getP3WbsName() {
		return this.p3WbsName;
	}

	public void setP3WbsName(String p3WbsName) {
		this.p3WbsName = p3WbsName;
	}

	public String getP3TaskId() {
		return this.p3TaskId;
	}

	public void setP3TaskId(String p3TaskId) {
		this.p3TaskId = p3TaskId;
	}

	public String getP3TaskCode() {
		return this.p3TaskCode;
	}

	public void setP3TaskCode(String p3TaskCode) {
		this.p3TaskCode = p3TaskCode;
	}

	public String getP3TaskName() {
		return this.p3TaskName;
	}

	public void setP3TaskName(String p3TaskName) {
		this.p3TaskName = p3TaskName;
	}

	public Timestamp getP3StartDate() {
		return this.p3StartDate;
	}

	public void setP3StartDate(Timestamp p3StartDate) {
		this.p3StartDate = p3StartDate;
	}

	public Timestamp getP3EndDate() {
		return this.p3EndDate;
	}

	public void setP3EndDate(Timestamp p3EndDate) {
		this.p3EndDate = p3EndDate;
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

	public String getIsApproved() {
		return this.isApproved;
	}

	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}

	public String getCertifiedType() {
		return this.certifiedType;
	}

	public void setCertifiedType(String certifiedType) {
		this.certifiedType = certifiedType;
	}

	public String getObsUserId() {
		return this.obsUserId;
	}

	public void setObsUserId(String obsUserId) {
		this.obsUserId = obsUserId;
	}

	public String getPlanIdPath() {
		return this.planIdPath;
	}

	public void setPlanIdPath(String planIdPath) {
		this.planIdPath = planIdPath;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getGranttId() {
		return this.granttId;
	}

	public void setGranttId(Integer granttId) {
		this.granttId = granttId;
	}

	public Integer getGranttParentId() {
		return this.granttParentId;
	}

	public void setGranttParentId(Integer granttParentId) {
		this.granttParentId = granttParentId;
	}

	public Timestamp getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Integer getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsselected() {
		return this.isselected;
	}

	public void setIsselected(String isselected) {
		this.isselected = isselected;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getFromDept() {
		return this.fromDept;
	}

	public void setFromDept(String fromDept) {
		this.fromDept = fromDept;
	}

	public String getFromLinkman() {
		return this.fromLinkman;
	}

	public void setFromLinkman(String fromLinkman) {
		this.fromLinkman = fromLinkman;
	}

	public Timestamp getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public String getAuditDept() {
		return this.auditDept;
	}

	public void setAuditDept(String auditDept) {
		this.auditDept = auditDept;
	}

	public String getAuditLinkman() {
		return this.auditLinkman;
	}

	public void setAuditLinkman(String auditLinkman) {
		this.auditLinkman = auditLinkman;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getFraction() {
		return this.fraction;
	}

	public void setFraction(Integer fraction) {
		this.fraction = fraction;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChkNumber() {
		return this.chkNumber;
	}

	public void setChkNumber(String chkNumber) {
		this.chkNumber = chkNumber;
	}

	public String getCheckUserName() {
		return this.checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public String getClosedFlag() {
		return this.closedFlag;
	}

	public void setClosedFlag(String closedFlag) {
		this.closedFlag = closedFlag;
	}

	public String getAssGuides() {
		return this.assGuides;
	}

	public void setAssGuides(String assGuides) {
		this.assGuides = assGuides;
	}

	public String getDocSwbsId() {
		return this.docSwbsId;
	}

	public void setDocSwbsId(String docSwbsId) {
		this.docSwbsId = docSwbsId;
	}

	public String getFolderId() {
		return this.folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getSwbsId() {
		return this.swbsId;
	}

	public void setSwbsId(String swbsId) {
		this.swbsId = swbsId;
	}

	public String getIsMajor() {
		return this.isMajor;
	}

	public void setIsMajor(String isMajor) {
		this.isMajor = isMajor;
	}

}