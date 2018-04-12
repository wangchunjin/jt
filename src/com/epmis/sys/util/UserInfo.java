package com.epmis.sys.util;

public class UserInfo
{
  private String UserId;
  private String UserName;
  private String ActualName;
  private String ProjId;
  private String ProjShortName;
  private String ProjName;
  private String MobileNo;
  private String DatactrCode;

  public String getMobileNo() {
	return MobileNo;
}
public void setMobileNo(String mobileNo) {
	MobileNo = mobileNo;
}
public String getDatactrCode()
  {
    return this.DatactrCode;
  }
  public void setDatactrCode(String datactrCode) {
    this.DatactrCode = datactrCode;
  }
  public String getProjId() {
    return this.ProjId;
  }
  public void setProjId(String projId) {
    this.ProjId = projId;
  }
  public String getUserId() {
    return this.UserId;
  }
  public void setUserId(String userId) {
    this.UserId = userId;
  }
  public String getUserName() {
    return this.UserName;
  }
  public void setUserName(String userName) {
    this.UserName = userName;
  }
  public String getActualName() {
    return this.ActualName;
  }
  public void setActualName(String actualName) {
    this.ActualName = actualName;
  }
  public String getProjShortName() {
    return this.ProjShortName;
  }
  public void setProjShortName(String projShortName) {
    this.ProjShortName = projShortName;
  }
  public String getProjName() {
    return this.ProjName;
  }
  public void setProjName(String projName) {
    this.ProjName = projName;
  }
}