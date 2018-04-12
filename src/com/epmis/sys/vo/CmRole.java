package com.epmis.sys.vo;

public class CmRole
{
  private String roleId;
  private String wid;
  private String roleName;
  private String parentRoleId;
  private String roleType;
  private String remark;
  private Integer seqNum;

  public CmRole()
  {
  }

  public CmRole(String roleId, String wid, String roleName)
  {
    this.roleId = roleId;
    this.wid = wid;
    this.roleName = roleName;
  }

  public CmRole(String roleId, String wid, String roleName, String parentRoleId, String roleType, String remark, Integer seqNum)
  {
    this.roleId = roleId;
    this.wid = wid;
    this.roleName = roleName;
    this.parentRoleId = parentRoleId;
    this.roleType = roleType;
    this.remark = remark;
    this.seqNum = seqNum;
  }

  public String getRoleId()
  {
    return this.roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getWid() {
    return this.wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }

  public String getRoleName() {
    return this.roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getParentRoleId() {
    return this.parentRoleId;
  }

  public void setParentRoleId(String parentRoleId) {
    this.parentRoleId = parentRoleId;
  }

  public String getRoleType() {
    return this.roleType;
  }

  public void setRoleType(String roleType) {
    this.roleType = roleType;
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