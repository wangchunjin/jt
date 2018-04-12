package com.epmis.sys.vo;

import java.util.Date;

public class CmUsers
{
  private String userId;
  private String wid;
  private String userName;
  private String actualName;
  private String password;
  private Long newdocDays;
  private String profileId;
  private String userPriv;
  private String sex;
  private String birthday;
  private String telNoDept;
  private String faxNoDept;
  private String addHome;
  private String postNoHome;
  private String telNoHome;
  private String mobilNo;
  private String bpNo;
  private String email;
  private String oicqNo;
  private String icqNo;
  private String msn;
  private Long dutyType;
  private Date lastVisitTime;
  private Long authorize;
  private Long disabled;
  private String ensemble;
  private Long lowFraction;
  private Long highFraction;
  private String defProj;
  private String signkey;
  private String rtxName;
  private String emailPassword;
  private String weatherId;
  private String disableFlag;

  public CmUsers()
  {
  }

  public CmUsers(String userId, String wid)
  {
    this.userId = userId;
    this.wid = wid;
  }

  public CmUsers(String userId, String wid, String userName, String actualName, String password, Long newdocDays, String profileId, String userPriv, String sex, String birthday, String telNoDept, String faxNoDept, String addHome, String postNoHome, String telNoHome, String mobilNo, String bpNo, String email, String oicqNo, String icqNo, String msn, Long dutyType, Date lastVisitTime, Long authorize, Long disabled, String ensemble, Long lowFraction, Long highFraction, String defProj, String signkey, String rtxName, String emailPassword, String weatherId, String disableFlag)
  {
    this.userId = userId;
    this.wid = wid;
    this.userName = userName;
    this.actualName = actualName;
    this.password = password;
    this.newdocDays = newdocDays;
    this.profileId = profileId;
    this.userPriv = userPriv;
    this.sex = sex;
    this.birthday = birthday;
    this.telNoDept = telNoDept;
    this.faxNoDept = faxNoDept;
    this.addHome = addHome;
    this.postNoHome = postNoHome;
    this.telNoHome = telNoHome;
    this.mobilNo = mobilNo;
    this.bpNo = bpNo;
    this.email = email;
    this.oicqNo = oicqNo;
    this.icqNo = icqNo;
    this.msn = msn;
    this.dutyType = dutyType;
    this.lastVisitTime = lastVisitTime;
    this.authorize = authorize;
    this.disabled = disabled;
    this.ensemble = ensemble;
    this.lowFraction = lowFraction;
    this.highFraction = highFraction;
    this.defProj = defProj;
    this.signkey = signkey;
    this.rtxName = rtxName;
    this.emailPassword = emailPassword;
    this.weatherId = weatherId;
    this.disableFlag = disableFlag;
  }

  public String getUserId()
  {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getWid() {
    return this.wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getActualName() {
    return this.actualName;
  }

  public void setActualName(String actualName) {
    this.actualName = actualName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getNewdocDays() {
    return this.newdocDays;
  }

  public void setNewdocDays(Long newdocDays) {
    this.newdocDays = newdocDays;
  }

  public String getProfileId() {
    return this.profileId;
  }

  public void setProfileId(String profileId) {
    this.profileId = profileId;
  }

  public String getUserPriv() {
    return this.userPriv;
  }

  public void setUserPriv(String userPriv) {
    this.userPriv = userPriv;
  }

  public String getSex() {
    return this.sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getBirthday() {
    return this.birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getTelNoDept() {
    return this.telNoDept;
  }

  public void setTelNoDept(String telNoDept) {
    this.telNoDept = telNoDept;
  }

  public String getFaxNoDept() {
    return this.faxNoDept;
  }

  public void setFaxNoDept(String faxNoDept) {
    this.faxNoDept = faxNoDept;
  }

  public String getAddHome() {
    return this.addHome;
  }

  public void setAddHome(String addHome) {
    this.addHome = addHome;
  }

  public String getPostNoHome() {
    return this.postNoHome;
  }

  public void setPostNoHome(String postNoHome) {
    this.postNoHome = postNoHome;
  }

  public String getTelNoHome() {
    return this.telNoHome;
  }

  public void setTelNoHome(String telNoHome) {
    this.telNoHome = telNoHome;
  }

  public String getMobilNo() {
    return this.mobilNo;
  }

  public void setMobilNo(String mobilNo) {
    this.mobilNo = mobilNo;
  }

  public String getBpNo() {
    return this.bpNo;
  }

  public void setBpNo(String bpNo) {
    this.bpNo = bpNo;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getOicqNo() {
    return this.oicqNo;
  }

  public void setOicqNo(String oicqNo) {
    this.oicqNo = oicqNo;
  }

  public String getIcqNo() {
    return this.icqNo;
  }

  public void setIcqNo(String icqNo) {
    this.icqNo = icqNo;
  }

  public String getMsn() {
    return this.msn;
  }

  public void setMsn(String msn) {
    this.msn = msn;
  }

  public Long getDutyType() {
    return this.dutyType;
  }

  public void setDutyType(Long dutyType) {
    this.dutyType = dutyType;
  }

  public Date getLastVisitTime() {
    return this.lastVisitTime;
  }

  public void setLastVisitTime(Date lastVisitTime) {
    this.lastVisitTime = lastVisitTime;
  }

  public Long getAuthorize() {
    return this.authorize;
  }

  public void setAuthorize(Long authorize) {
    this.authorize = authorize;
  }

  public Long getDisabled() {
    return this.disabled;
  }

  public void setDisabled(Long disabled) {
    this.disabled = disabled;
  }

  public String getEnsemble() {
    return this.ensemble;
  }

  public void setEnsemble(String ensemble) {
    this.ensemble = ensemble;
  }

  public Long getLowFraction() {
    return this.lowFraction;
  }

  public void setLowFraction(Long lowFraction) {
    this.lowFraction = lowFraction;
  }

  public Long getHighFraction() {
    return this.highFraction;
  }

  public void setHighFraction(Long highFraction) {
    this.highFraction = highFraction;
  }

  public String getDefProj() {
    return this.defProj;
  }

  public void setDefProj(String defProj) {
    this.defProj = defProj;
  }

  public String getSignkey() {
    return this.signkey;
  }

  public void setSignkey(String signkey) {
    this.signkey = signkey;
  }

  public String getRtxName() {
    return this.rtxName;
  }

  public void setRtxName(String rtxName) {
    this.rtxName = rtxName;
  }

  public String getEmailPassword() {
    return this.emailPassword;
  }

  public void setEmailPassword(String emailPassword) {
    this.emailPassword = emailPassword;
  }

  public String getWeatherId() {
    return this.weatherId;
  }

  public void setWeatherId(String weatherId) {
    this.weatherId = weatherId;
  }

  public String getDisableFlag() {
    return this.disableFlag;
  }

  public void setDisableFlag(String disableFlag) {
    this.disableFlag = disableFlag;
  }
}