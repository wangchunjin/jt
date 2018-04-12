package com.epmis.sys.vo;

import java.sql.Timestamp;

public class CmVnmt
{
  private String vnmtId;
  private String wid;
  private String companyName;
  private String companyAbbrev;
  private String roleId;
  private String defaultLinkman;
  private String taxIdNumber;
  private String defaultLocation;
  private String intgGrade;
  private String scale;
  private String registerFund;
  private String trade;
  private Integer point;
  private String estmGrade;
  private String officePhone;
  private String fax;
  private String mailAddress;
  private String bank;
  private String accountsName;
  private String accountsNo;
  private Timestamp createdDate;
  private String createdBy;
  private String isdefault;
  private String remark;
  private String otherVnmtId;
  private String projId;

  public CmVnmt()
  {
  }

  public CmVnmt(String vnmtId, String wid, String companyName, String companyAbbrev)
  {
    this.vnmtId = vnmtId;
    this.wid = wid;
    this.companyName = companyName;
    this.companyAbbrev = companyAbbrev;
  }

  public CmVnmt(String vnmtId, String wid, String companyName, String companyAbbrev, String roleId, String defaultLinkman, String taxIdNumber, String defaultLocation, String intgGrade, String scale, String registerFund, String trade, Integer point, String estmGrade, String officePhone, String fax, String mailAddress, String bank, String accountsName, String accountsNo, Timestamp createdDate, String createdBy, String isdefault, String remark, String otherVnmtId, String projId)
  {
    this.vnmtId = vnmtId;
    this.wid = wid;
    this.companyName = companyName;
    this.companyAbbrev = companyAbbrev;
    this.roleId = roleId;
    this.defaultLinkman = defaultLinkman;
    this.taxIdNumber = taxIdNumber;
    this.defaultLocation = defaultLocation;
    this.intgGrade = intgGrade;
    this.scale = scale;
    this.registerFund = registerFund;
    this.trade = trade;
    this.point = point;
    this.estmGrade = estmGrade;
    this.officePhone = officePhone;
    this.fax = fax;
    this.mailAddress = mailAddress;
    this.bank = bank;
    this.accountsName = accountsName;
    this.accountsNo = accountsNo;
    this.createdDate = createdDate;
    this.createdBy = createdBy;
    this.isdefault = isdefault;
    this.remark = remark;
    this.otherVnmtId = otherVnmtId;
    this.projId = projId;
  }

  public String getVnmtId()
  {
    return this.vnmtId;
  }

  public void setVnmtId(String vnmtId) {
    this.vnmtId = vnmtId;
  }

  public String getWid() {
    return this.wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }

  public String getCompanyName() {
    return this.companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCompanyAbbrev() {
    return this.companyAbbrev;
  }

  public void setCompanyAbbrev(String companyAbbrev) {
    this.companyAbbrev = companyAbbrev;
  }

  public String getRoleId() {
    return this.roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getDefaultLinkman() {
    return this.defaultLinkman;
  }

  public void setDefaultLinkman(String defaultLinkman) {
    this.defaultLinkman = defaultLinkman;
  }

  public String getTaxIdNumber() {
    return this.taxIdNumber;
  }

  public void setTaxIdNumber(String taxIdNumber) {
    this.taxIdNumber = taxIdNumber;
  }

  public String getDefaultLocation() {
    return this.defaultLocation;
  }

  public void setDefaultLocation(String defaultLocation) {
    this.defaultLocation = defaultLocation;
  }

  public String getIntgGrade() {
    return this.intgGrade;
  }

  public void setIntgGrade(String intgGrade) {
    this.intgGrade = intgGrade;
  }

  public String getScale() {
    return this.scale;
  }

  public void setScale(String scale) {
    this.scale = scale;
  }

  public String getRegisterFund() {
    return this.registerFund;
  }

  public void setRegisterFund(String registerFund) {
    this.registerFund = registerFund;
  }

  public String getTrade() {
    return this.trade;
  }

  public void setTrade(String trade) {
    this.trade = trade;
  }

  public Integer getPoint() {
    return this.point;
  }

  public void setPoint(Integer point) {
    this.point = point;
  }

  public String getEstmGrade() {
    return this.estmGrade;
  }

  public void setEstmGrade(String estmGrade) {
    this.estmGrade = estmGrade;
  }

  public String getOfficePhone() {
    return this.officePhone;
  }

  public void setOfficePhone(String officePhone) {
    this.officePhone = officePhone;
  }

  public String getFax() {
    return this.fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getMailAddress() {
    return this.mailAddress;
  }

  public void setMailAddress(String mailAddress) {
    this.mailAddress = mailAddress;
  }

  public String getBank() {
    return this.bank;
  }

  public void setBank(String bank) {
    this.bank = bank;
  }

  public String getAccountsName() {
    return this.accountsName;
  }

  public void setAccountsName(String accountsName) {
    this.accountsName = accountsName;
  }

  public String getAccountsNo() {
    return this.accountsNo;
  }

  public void setAccountsNo(String accountsNo) {
    this.accountsNo = accountsNo;
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

  public String getIsdefault() {
    return this.isdefault;
  }

  public void setIsdefault(String isdefault) {
    this.isdefault = isdefault;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getOtherVnmtId() {
    return this.otherVnmtId;
  }

  public void setOtherVnmtId(String otherVnmtId) {
    this.otherVnmtId = otherVnmtId;
  }

  public String getProjId() {
    return this.projId;
  }

  public void setProjId(String projId) {
    this.projId = projId;
  }
}