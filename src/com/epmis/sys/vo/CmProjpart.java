package com.epmis.sys.vo;

import java.util.Date;

public class CmProjpart
{
  private String id;
  private String wid;
  private String userId;
  private String projId;
  private String actualName;
  private String sex;
  private String jobTitle;
  private String technicalTitle;
  private Date birthday;
  private String birthplace;
  private String idNumber;
  private String tel;
  private String study;
  private String deptNo;
  private Integer seqNum;

  public CmProjpart()
  {
  }

  public CmProjpart(String id, String wid)
  {
    this.id = id;
    this.wid = wid;
  }

  public CmProjpart(String id, String wid, String userId, String projId, String actualName, String sex, String jobTitle, String technicalTitle, Date birthday, String birthplace, String idNumber, String tel, String study, String deptNo, Integer seqNum)
  {
    this.id = id;
    this.wid = wid;
    this.userId = userId;
    this.projId = projId;
    this.actualName = actualName;
    this.sex = sex;
    this.jobTitle = jobTitle;
    this.technicalTitle = technicalTitle;
    this.birthday = birthday;
    this.birthplace = birthplace;
    this.idNumber = idNumber;
    this.tel = tel;
    this.study = study;
    this.deptNo = deptNo;
    this.seqNum = seqNum;
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

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getProjId() {
    return this.projId;
  }

  public void setProjId(String projId) {
    this.projId = projId;
  }

  public String getActualName() {
    return this.actualName;
  }

  public void setActualName(String actualName) {
    this.actualName = actualName;
  }

  public String getSex() {
    return this.sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getJobTitle() {
    return this.jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public String getTechnicalTitle() {
    return this.technicalTitle;
  }

  public void setTechnicalTitle(String technicalTitle) {
    this.technicalTitle = technicalTitle;
  }

  public Date getBirthday() {
    return this.birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getBirthplace() {
    return this.birthplace;
  }

  public void setBirthplace(String birthplace) {
    this.birthplace = birthplace;
  }

  public String getIdNumber() {
    return this.idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }

  public String getTel() {
    return this.tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public String getStudy() {
    return this.study;
  }

  public void setStudy(String study) {
    this.study = study;
  }

  public String getDeptNo() {
    return this.deptNo;
  }

  public void setDeptNo(String deptNo) {
    this.deptNo = deptNo;
  }

  public Integer getSeqNum() {
    return this.seqNum;
  }

  public void setSeqNum(Integer seqNum) {
    this.seqNum = seqNum;
  }
}