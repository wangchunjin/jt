package com.epmis.sys.vo;

import java.sql.Timestamp;

public class StructLanguage
{
  private String id;
  private String wid;
  private String parentId;
  private String idPath;
  private String name;
  private String type;
  private String projId;
  private String createdBy;
  private Timestamp createdTime;
  private String isPublic;
  private Integer seqNum;

  public Integer getSeqNum()
  {
    return this.seqNum;
  }

  public void setSeqNum(Integer seqNum) {
    this.seqNum = seqNum;
  }

  public StructLanguage()
  {
  }

  public StructLanguage(String id, String wid)
  {
    this.id = id;
    this.wid = wid;
  }

  public StructLanguage(String id, String wid, String parentId, String idPath, String name, String type, String projId, String createdBy, Timestamp createdTime, String isPublic)
  {
    this.id = id;
    this.wid = wid;
    this.parentId = parentId;
    this.idPath = idPath;
    this.name = name;
    this.type = type;
    this.projId = projId;
    this.createdBy = createdBy;
    this.createdTime = createdTime;
    this.isPublic = isPublic;
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getWid() {
    return this.wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }

  public String getParentId() {
    return this.parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getIdPath() {
    return this.idPath;
  }

  public void setIdPath(String idPath) {
    this.idPath = idPath;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getProjId() {
    return this.projId;
  }

  public void setProjId(String projId) {
    this.projId = projId;
  }

  public String getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Timestamp getCreatedTime() {
    return this.createdTime;
  }

  public void setCreatedTime(Timestamp createdTime) {
    this.createdTime = createdTime;
  }

  public String getIsPublic() {
    return this.isPublic;
  }

  public void setIsPublic(String isPublic) {
    this.isPublic = isPublic;
  }
}