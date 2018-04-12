package com.epmis.sys.vo;

import java.sql.Date;
import java.sql.Timestamp;

public class CmProj
{
  private String projId;
  private String wid;
  private String projShortName;
  private String projName;
  private String projNodeFlag;
  private String contractTitle;
  private String jobNumber;
  private String projectNumber;
  private String siteLocation;
  private Double projectValue;
  private String status;
  private String parentProjId;
  private String description;
  private Date proceedDate;
  private Date completionDate;
  private Timestamp contractDate;
  private String projectCurrency;
  private String currId;
  private String vnmtId;
  private String msUserId;
  private String projCmptPct;
  private String projStage;
  private String parentPath;
  private String primProjId;
  private String kksTypeId;
  private String createdBy;
  private Timestamp createdDate;
  private String sumy;
  private Integer seqNum;
  private String isSpecialPro;
  private String projStage1;
  private String projPlace;
  private String supervisor;
  private String supervisorPhone;
  private String isZjlrFlag;
  private String obsId;
  private String radius;
  private String mark;

  public String getRadius()
  {
    return this.radius;
  }

  public void setRadius(String radius) {
    this.radius = radius;
  }

  public String getMark() {
    return this.mark;
  }

  public void setMark(String mark) {
    this.mark = mark;
  }

  public CmProj()
  {
  }

  public CmProj(String projId, String wid, String projShortName, String projName, String projNodeFlag, Timestamp createdDate)
  {
    this.projId = projId;
    this.wid = wid;
    this.projShortName = projShortName;
    this.projName = projName;
    this.projNodeFlag = projNodeFlag;
    this.createdDate = createdDate;
  }

  public CmProj(String projId, String wid, String projShortName, String projName, String projNodeFlag, String contractTitle, String jobNumber, String projectNumber, String siteLocation, Double projectValue, String status, String parentProjId, String description, Date proceedDate, Date completionDate, Timestamp contractDate, String projectCurrency, String currId, String vnmtId, String msUserId, String projCmptPct, String projStage, String parentPath, String primProjId, String kksTypeId, String createdBy, Timestamp createdDate, String sumy, Integer seqNum, String isSpecialPro, String projStage1, String projPlace, String supervisor, String supervisorPhone, String isZjlrFlag, String obsId)
  {
    this.projId = projId;
    this.wid = wid;
    this.projShortName = projShortName;
    this.projName = projName;
    this.projNodeFlag = projNodeFlag;
    this.contractTitle = contractTitle;
    this.jobNumber = jobNumber;
    this.projectNumber = projectNumber;
    this.siteLocation = siteLocation;
    this.projectValue = projectValue;
    this.status = status;
    this.parentProjId = parentProjId;
    this.description = description;
    this.proceedDate = proceedDate;
    this.completionDate = completionDate;
    this.contractDate = contractDate;
    this.projectCurrency = projectCurrency;
    this.currId = currId;
    this.vnmtId = vnmtId;
    this.msUserId = msUserId;
    this.projCmptPct = projCmptPct;
    this.projStage = projStage;
    this.parentPath = parentPath;
    this.primProjId = primProjId;
    this.kksTypeId = kksTypeId;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
    this.sumy = sumy;
    this.seqNum = seqNum;
    this.isSpecialPro = isSpecialPro;
    this.projStage1 = projStage1;
    this.projPlace = projPlace;
    this.supervisor = supervisor;
    this.supervisorPhone = supervisorPhone;
    this.isZjlrFlag = isZjlrFlag;
    this.obsId = obsId;
  }

  public String getProjId()
  {
    return this.projId;
  }

  public void setProjId(String projId) {
    this.projId = projId;
  }

  public String getWid() {
    return this.wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }

  public String getProjShortName() {
    return this.projShortName;
  }

  public void setProjShortName(String projShortName) {
    this.projShortName = projShortName;
  }

  public String getProjName() {
    return this.projName;
  }

  public void setProjName(String projName) {
    this.projName = projName;
  }

  public String getProjNodeFlag() {
    return this.projNodeFlag;
  }

  public void setProjNodeFlag(String projNodeFlag) {
    this.projNodeFlag = projNodeFlag;
  }

  public String getContractTitle() {
    return this.contractTitle;
  }

  public void setContractTitle(String contractTitle) {
    this.contractTitle = contractTitle;
  }

  public String getJobNumber() {
    return this.jobNumber;
  }

  public void setJobNumber(String jobNumber) {
    this.jobNumber = jobNumber;
  }

  public String getProjectNumber() {
    return this.projectNumber;
  }

  public void setProjectNumber(String projectNumber) {
    this.projectNumber = projectNumber;
  }

  public String getSiteLocation() {
    return this.siteLocation;
  }

  public void setSiteLocation(String siteLocation) {
    this.siteLocation = siteLocation;
  }

  public Double getProjectValue() {
    return this.projectValue;
  }

  public void setProjectValue(Double projectValue) {
    this.projectValue = projectValue;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getParentProjId() {
    return this.parentProjId;
  }

  public void setParentProjId(String parentProjId) {
    this.parentProjId = parentProjId;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getProceedDate() {
    return this.proceedDate;
  }

  public void setProceedDate(Date proceedDate) {
    this.proceedDate = proceedDate;
  }

  public Date getCompletionDate() {
    return this.completionDate;
  }

  public void setCompletionDate(Date completionDate) {
    this.completionDate = completionDate;
  }
  public Timestamp getContractDate() {
    return this.contractDate;
  }

  public void setContractDate(Timestamp contractDate) {
    this.contractDate = contractDate;
  }

  public String getProjectCurrency() {
    return this.projectCurrency;
  }

  public void setProjectCurrency(String projectCurrency) {
    this.projectCurrency = projectCurrency;
  }

  public String getCurrId() {
    return this.currId;
  }

  public void setCurrId(String currId) {
    this.currId = currId;
  }

  public String getVnmtId() {
    return this.vnmtId;
  }

  public void setVnmtId(String vnmtId) {
    this.vnmtId = vnmtId;
  }

  public String getMsUserId() {
    return this.msUserId;
  }

  public void setMsUserId(String msUserId) {
    this.msUserId = msUserId;
  }

  public String getProjCmptPct() {
    return this.projCmptPct;
  }

  public void setProjCmptPct(String projCmptPct) {
    this.projCmptPct = projCmptPct;
  }

  public String getProjStage() {
    return this.projStage;
  }

  public void setProjStage(String projStage) {
    this.projStage = projStage;
  }

  public String getParentPath() {
    return this.parentPath;
  }

  public void setParentPath(String parentPath) {
    this.parentPath = parentPath;
  }

  public String getPrimProjId() {
    return this.primProjId;
  }

  public void setPrimProjId(String primProjId) {
    this.primProjId = primProjId;
  }

  public String getKksTypeId() {
    return this.kksTypeId;
  }

  public void setKksTypeId(String kksTypeId) {
    this.kksTypeId = kksTypeId;
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

  public String getSumy() {
    return this.sumy;
  }

  public void setSumy(String sumy) {
    this.sumy = sumy;
  }

  public Integer getSeqNum() {
    return this.seqNum;
  }

  public void setSeqNum(Integer seqNum) {
    this.seqNum = seqNum;
  }

  public String getIsSpecialPro() {
    return this.isSpecialPro;
  }

  public void setIsSpecialPro(String isSpecialPro) {
    this.isSpecialPro = isSpecialPro;
  }

  public String getProjStage1() {
    return this.projStage1;
  }

  public void setProjStage1(String projStage1) {
    this.projStage1 = projStage1;
  }

  public String getProjPlace() {
    return this.projPlace;
  }

  public void setProjPlace(String projPlace) {
    this.projPlace = projPlace;
  }

  public String getSupervisor() {
    return this.supervisor;
  }

  public void setSupervisor(String supervisor) {
    this.supervisor = supervisor;
  }

  public String getSupervisorPhone() {
    return this.supervisorPhone;
  }

  public void setSupervisorPhone(String supervisorPhone) {
    this.supervisorPhone = supervisorPhone;
  }

  public String getIsZjlrFlag() {
    return this.isZjlrFlag;
  }

  public void setIsZjlrFlag(String isZjlrFlag) {
    this.isZjlrFlag = isZjlrFlag;
  }

  public String getObsId() {
    return this.obsId;
  }

  public void setObsId(String obsId) {
    this.obsId = obsId;
  }
}