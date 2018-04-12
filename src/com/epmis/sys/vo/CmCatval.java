package com.epmis.sys.vo;

public class CmCatval
{
  private String catgId;
  private String wid;
  private String catgTypeId;
  private String catgShortName;
  private String catgName;
  private String parentCatgId;
  private String catgIdPath;
  private Integer seqNum;

  public CmCatval()
  {
  }

  public CmCatval(String catgId, String wid, String catgTypeId, String catgShortName, String catgName, String parentCatgId)
  {
    this.catgId = catgId;
    this.wid = wid;
    this.catgTypeId = catgTypeId;
    this.catgShortName = catgShortName;
    this.catgName = catgName;
    this.parentCatgId = parentCatgId;
  }

  public CmCatval(String catgId, String wid, String catgTypeId, String catgShortName, String catgName, String parentCatgId, String catgIdPath, Integer seqNum)
  {
    this.catgId = catgId;
    this.wid = wid;
    this.catgTypeId = catgTypeId;
    this.catgShortName = catgShortName;
    this.catgName = catgName;
    this.parentCatgId = parentCatgId;
    this.catgIdPath = catgIdPath;
    this.seqNum = seqNum;
  }

  public String getCatgId()
  {
    return this.catgId;
  }

  public void setCatgId(String catgId) {
    this.catgId = catgId;
  }

  public String getWid() {
    return this.wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }

  public String getCatgTypeId() {
    return this.catgTypeId;
  }

  public void setCatgTypeId(String catgTypeId) {
    this.catgTypeId = catgTypeId;
  }

  public String getCatgShortName() {
    return this.catgShortName;
  }

  public void setCatgShortName(String catgShortName) {
    this.catgShortName = catgShortName;
  }

  public String getCatgName() {
    return this.catgName;
  }

  public void setCatgName(String catgName) {
    this.catgName = catgName;
  }

  public String getParentCatgId() {
    return this.parentCatgId;
  }

  public void setParentCatgId(String parentCatgId) {
    this.parentCatgId = parentCatgId;
  }

  public String getCatgIdPath() {
    return this.catgIdPath;
  }

  public void setCatgIdPath(String catgIdPath) {
    this.catgIdPath = catgIdPath;
  }

  public Integer getSeqNum() {
    return this.seqNum;
  }

  public void setSeqNum(Integer seqNum) {
    this.seqNum = seqNum;
  }
}