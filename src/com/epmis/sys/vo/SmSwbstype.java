package com.epmis.sys.vo;

import java.io.Serializable;

public class SmSwbstype
  implements Serializable
{
  private String swbsTypeId;
  private String wid;
  private String swbsTypeName;
  private String description;
  private String modlename;

  public SmSwbstype()
  {
  }

  public SmSwbstype(String swbsTypeId, String wid)
  {
    this.swbsTypeId = swbsTypeId;
    this.wid = wid;
  }

  public SmSwbstype(String swbsTypeId, String wid, String swbsTypeName, String description, String modlename)
  {
    this.swbsTypeId = swbsTypeId;
    this.wid = wid;
    this.swbsTypeName = swbsTypeName;
    this.description = description;
    this.modlename = modlename;
  }

  public String getSwbsTypeId()
  {
    return this.swbsTypeId;
  }

  public void setSwbsTypeId(String swbsTypeId) {
    this.swbsTypeId = swbsTypeId;
  }

  public String getWid() {
    return this.wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }

  public String getSwbsTypeName() {
    return this.swbsTypeName;
  }

  public void setSwbsTypeName(String swbsTypeName) {
    this.swbsTypeName = swbsTypeName;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getModlename() {
    return this.modlename;
  }

  public void setModlename(String modlename) {
    this.modlename = modlename;
  }
}