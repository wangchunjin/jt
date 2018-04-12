package com.epmis.sys.vo;

public class CmCode
{
  private String wid;
  private String id;
  private String codeShortName;
  private String codeName;
  private String codeType;
  private String codeBelongto;
  private String remark;
  private Integer seqNum;

  public CmCode()
  {
  }

  public CmCode(String wid)
  {
    this.wid = wid;
  }

  public CmCode(String wid, String id, String codeShortName, String codeName, String codeType, String codeBelongto, String remark, Integer seqNum)
  {
    this.wid = wid;
    this.id = id;
    this.codeShortName = codeShortName;
    this.codeName = codeName;
    this.codeType = codeType;
    this.codeBelongto = codeBelongto;
    this.remark = remark;
    this.seqNum = seqNum;
  }

  public String getWid()
  {
    return this.wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCodeShortName() {
    return this.codeShortName;
  }

  public void setCodeShortName(String codeShortName) {
    this.codeShortName = codeShortName;
  }

  public String getCodeName() {
    return this.codeName;
  }

  public void setCodeName(String codeName) {
    this.codeName = codeName;
  }

  public String getCodeType() {
    return this.codeType;
  }

  public void setCodeType(String codeType) {
    this.codeType = codeType;
  }

  public String getCodeBelongto() {
    return this.codeBelongto;
  }

  public void setCodeBelongto(String codeBelongto) {
    this.codeBelongto = codeBelongto;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Integer getSeqNum() {
    return this.seqNum;
  }

  public void setSeqNum(Integer seqNum) {
    this.seqNum = seqNum;
  }
}