package com.epmis.sys.vo;

public class CmProfile
{
  private String profileId;
  private String wid;
  private String profileName;
  private String profType;
  private String isdefault;
  private String remark;

  public CmProfile()
  {
  }

  public CmProfile(String profileId, String wid)
  {
    this.profileId = profileId;
    this.wid = wid;
  }

  public CmProfile(String profileId, String wid, String profileName, String profType, String isdefault, String remark)
  {
    this.profileId = profileId;
    this.wid = wid;
    this.profileName = profileName;
    this.profType = profType;
    this.isdefault = isdefault;
    this.remark = remark;
  }

  public String getProfileId()
  {
    return this.profileId;
  }

  public void setProfileId(String profileId) {
    this.profileId = profileId;
  }

  public String getWid() {
    return this.wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }

  public String getProfileName() {
    return this.profileName;
  }

  public void setProfileName(String profileName) {
    this.profileName = profileName;
  }

  public String getProfType() {
    return this.profType;
  }

  public void setProfType(String profType) {
    this.profType = profType;
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
}