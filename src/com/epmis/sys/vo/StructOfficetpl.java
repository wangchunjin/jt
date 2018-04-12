package com.epmis.sys.vo;

import java.sql.Date;

public class StructOfficetpl
{
  private String officetplId;
  private String wid;
  private String officetplName;
  private String officetplUrl;
  private String officetplRemark;
  private Integer seqNum;
  private String officetplFlag;
  private Date createdDate;

  public StructOfficetpl()
  {
  }

  public StructOfficetpl(String officetplId, String wid)
  {
    this.officetplId = officetplId;
    this.wid = wid;
  }

  public StructOfficetpl(String officetplId, String wid, String officetplName, String officetplUrl, String officetplRemark, Integer seqNum, String officetplFlag, Date createdDate)
  {
    this.officetplId = officetplId;
    this.wid = wid;
    this.officetplName = officetplName;
    this.officetplUrl = officetplUrl;
    this.officetplRemark = officetplRemark;
    this.seqNum = seqNum;
    this.officetplFlag = officetplFlag;
    this.createdDate = createdDate;
  }

  public String getOfficetplId()
  {
    return this.officetplId;
  }

  public void setOfficetplId(String officetplId) {
    this.officetplId = officetplId;
  }

  public String getWid() {
    return this.wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }

  public String getOfficetplName() {
    return this.officetplName;
  }

  public void setOfficetplName(String officetplName) {
    this.officetplName = officetplName;
  }

  public String getOfficetplUrl() {
    return this.officetplUrl;
  }

  public void setOfficetplUrl(String officetplUrl) {
    this.officetplUrl = officetplUrl;
  }

  public String getOfficetplRemark() {
    return this.officetplRemark;
  }

  public void setOfficetplRemark(String officetplRemark) {
    this.officetplRemark = officetplRemark;
  }

  public Integer getSeqNum() {
    return this.seqNum;
  }

  public void setSeqNum(Integer seqNum) {
    this.seqNum = seqNum;
  }

  public String getOfficetplFlag() {
    return this.officetplFlag;
  }

  public void setOfficetplFlag(String officetplFlag) {
    this.officetplFlag = officetplFlag;
  }

  public Date getCreatedDate() {
    return this.createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
}