package com.epmis.sys.vo;

public class CmLable
{
  private String lableId;
  private String wid;
  private String module;
  private String label;
  private String labelurl;
  private String isdisplay;
  private Short ordernum;
  private String filter;
  private String isdefault;
  private String currentuser;

  public CmLable()
  {
  }

  public CmLable(String lableId, String wid)
  {
    this.lableId = lableId;
    this.wid = wid;
  }

  public CmLable(String lableId, String wid, String module, String label, String labelurl, String isdisplay, Short ordernum, String filter, String isdefault, String currentuser)
  {
    this.lableId = lableId;
    this.wid = wid;
    this.module = module;
    this.label = label;
    this.labelurl = labelurl;
    this.isdisplay = isdisplay;
    this.ordernum = ordernum;
    this.filter = filter;
    this.isdefault = isdefault;
    this.currentuser = currentuser;
  }

  public String getLableId()
  {
    return this.lableId;
  }

  public void setLableId(String lableId) {
    this.lableId = lableId;
  }

  public String getWid() {
    return this.wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }

  public String getModule() {
    return this.module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getLabel() {
    return this.label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getLabelurl() {
    return this.labelurl;
  }

  public void setLabelurl(String labelurl) {
    this.labelurl = labelurl;
  }

  public String getIsdisplay() {
    return this.isdisplay;
  }

  public void setIsdisplay(String isdisplay) {
    this.isdisplay = isdisplay;
  }

  public Short getOrdernum() {
    return this.ordernum;
  }

  public void setOrdernum(Short ordernum) {
    this.ordernum = ordernum;
  }

  public String getFilter() {
    return this.filter;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  public String getIsdefault() {
    return this.isdefault;
  }

  public void setIsdefault(String isdefault) {
    this.isdefault = isdefault;
  }

  public String getCurrentuser() {
    return this.currentuser;
  }

  public void setCurrentuser(String currentuser) {
    this.currentuser = currentuser;
  }
}