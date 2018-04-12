package com.epmis.sys.vo;

public class CmModule
{
  private String moduleId;
  private String wid;
  private String moduleCode;
  private String moduleName;
  private String parentModuleCode;
  private String moduleFlag;
  private String shareFlag;
  private String enabled;
  private String image;
  private Integer displaywidth;
  private Integer displayheight;
  private String urlto;
  private String lastopen;
  private String currentuser;
  private String uploadFlag;
  private Integer seqNum;
  private String remark;

  public CmModule()
  {
  }

  public CmModule(String moduleId, String moduleCode)
  {
    this.moduleId = moduleId;
    this.moduleCode = moduleCode;
  }

  public CmModule(String moduleId, String wid, String moduleCode, String moduleName, String parentModuleCode, String moduleFlag, String shareFlag, String enabled, String image, Integer displaywidth, Integer displayheight, String urlto, String lastopen, String currentuser, String uploadFlag, Integer seqNum, String remark)
  {
    this.moduleId = moduleId;
    this.wid = wid;
    this.moduleCode = moduleCode;
    this.moduleName = moduleName;
    this.parentModuleCode = parentModuleCode;
    this.moduleFlag = moduleFlag;
    this.shareFlag = shareFlag;
    this.enabled = enabled;
    this.image = image;
    this.displaywidth = displaywidth;
    this.displayheight = displayheight;
    this.urlto = urlto;
    this.lastopen = lastopen;
    this.currentuser = currentuser;
    this.uploadFlag = uploadFlag;
    this.seqNum = seqNum;
    this.remark = remark;
  }

  public String getModuleId()
  {
    return this.moduleId;
  }

  public void setModuleId(String moduleId) {
    this.moduleId = moduleId;
  }

  public String getWid() {
    return this.wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }

  public String getModuleCode() {
    return this.moduleCode;
  }

  public void setModuleCode(String moduleCode) {
    this.moduleCode = moduleCode;
  }

  public String getModuleName() {
    return this.moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public String getParentModuleCode() {
    return this.parentModuleCode;
  }

  public void setParentModuleCode(String parentModuleCode) {
    this.parentModuleCode = parentModuleCode;
  }

  public String getModuleFlag() {
    return this.moduleFlag;
  }

  public void setModuleFlag(String moduleFlag) {
    this.moduleFlag = moduleFlag;
  }

  public String getShareFlag() {
    return this.shareFlag;
  }

  public void setShareFlag(String shareFlag) {
    this.shareFlag = shareFlag;
  }

  public String getEnabled() {
    return this.enabled;
  }

  public void setEnabled(String enabled) {
    this.enabled = enabled;
  }

  public String getImage() {
    return this.image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Integer getDisplaywidth() {
    return this.displaywidth;
  }

  public void setDisplaywidth(Integer displaywidth) {
    this.displaywidth = displaywidth;
  }

  public Integer getDisplayheight() {
    return this.displayheight;
  }

  public void setDisplayheight(Integer displayheight) {
    this.displayheight = displayheight;
  }

  public String getUrlto() {
    return this.urlto;
  }

  public void setUrlto(String urlto) {
    this.urlto = urlto;
  }

  public String getLastopen() {
    return this.lastopen;
  }

  public void setLastopen(String lastopen) {
    this.lastopen = lastopen;
  }

  public String getCurrentuser() {
    return this.currentuser;
  }

  public void setCurrentuser(String currentuser) {
    this.currentuser = currentuser;
  }

  public String getUploadFlag() {
    return this.uploadFlag;
  }

  public void setUploadFlag(String uploadFlag) {
    this.uploadFlag = uploadFlag;
  }

  public Integer getSeqNum() {
    return this.seqNum;
  }

  public void setSeqNum(Integer seqNum) {
    this.seqNum = seqNum;
  }

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}