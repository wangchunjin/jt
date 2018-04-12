package com.epmis.sys.vo;

import java.io.Serializable;

public class SmSwbs
  implements Serializable
{
  private String swbsId;
  private String wid;
  private String swbsTypeId;
  private String parentSwbsId;
  private String swbsShortName;
  private String swbsName;
  private String manageDept;
  private String hazardLevel;
  private String identifyBy;
  private String measure;
  private String nodeType;
  private String udf1;
  private Integer seqNum;
  private String remark;
  private String info;
  private String docId;
  private String assGuides;
  private String isMajor;
  private Integer planScore;
  private String codeId;

  public String getCodeId()
  {
    return this.codeId;
  }

  public void setCodeId(String codeId) {
    this.codeId = codeId;
  }

  public SmSwbs()
  {
  }

  public SmSwbs(String swbsId, String wid, String swbsTypeId, String parentSwbsId, Integer seqNum)
  {
    this.swbsId = swbsId;
    this.wid = wid;
    this.swbsTypeId = swbsTypeId;
    this.parentSwbsId = parentSwbsId;
    this.seqNum = seqNum;
  }

  public SmSwbs(String swbsId, String wid, String swbsTypeId, String parentSwbsId, String swbsShortName, String swbsName, String manageDept, String hazardLevel, String identifyBy, String measure, String nodeType, String udf1, Integer seqNum, String remark, String info, String docId, String assGuides, String isMajor, Integer planScore)
  {
    this.swbsId = swbsId;
    this.wid = wid;
    this.swbsTypeId = swbsTypeId;
    this.parentSwbsId = parentSwbsId;
    this.swbsShortName = swbsShortName;
    this.swbsName = swbsName;
    this.manageDept = manageDept;
    this.hazardLevel = hazardLevel;
    this.identifyBy = identifyBy;
    this.measure = measure;
    this.nodeType = nodeType;
    this.udf1 = udf1;
    this.seqNum = seqNum;
    this.remark = remark;
    this.info = info;
    this.docId = docId;
    this.assGuides = assGuides;
    this.isMajor = isMajor;
    this.planScore = planScore;
  }

  public String getSwbsId()
  {
    return this.swbsId;
  }

  public void setSwbsId(String swbsId) {
    this.swbsId = swbsId;
  }

  public String getWid() {
    return this.wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }

  public String getSwbsTypeId() {
    return this.swbsTypeId;
  }

  public void setSwbsTypeId(String swbsTypeId) {
    this.swbsTypeId = swbsTypeId;
  }

  public String getParentSwbsId() {
    return this.parentSwbsId;
  }

  public void setParentSwbsId(String parentSwbsId) {
    this.parentSwbsId = parentSwbsId;
  }

  public String getSwbsShortName() {
    return this.swbsShortName;
  }

  public void setSwbsShortName(String swbsShortName) {
    this.swbsShortName = swbsShortName;
  }

  public String getSwbsName() {
    return this.swbsName;
  }

  public void setSwbsName(String swbsName) {
    this.swbsName = swbsName;
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

  public String getUdf1() {
    return this.udf1;
  }

  public void setUdf1(String udf1) {
    this.udf1 = udf1;
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

  public String getInfo() {
    return this.info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getDocId() {
    return this.docId;
  }

  public void setDocId(String docId) {
    this.docId = docId;
  }

  public String getAssGuides() {
    return this.assGuides;
  }

  public void setAssGuides(String assGuides) {
    this.assGuides = assGuides;
  }

  public String getIsMajor() {
    return this.isMajor;
  }

  public void setIsMajor(String isMajor) {
    this.isMajor = isMajor;
  }

  public Integer getPlanScore() {
    return this.planScore;
  }

  public void setPlanScore(Integer planScore) {
    this.planScore = planScore;
  }
}