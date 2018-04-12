package com.epmis.sys.region.vo;

public class Region {

	private String wid;
	private String areaName;
	private String areaEn;
	private String areaType;
	private String parentId;
	private String seqNum;
	private String creator;
	private String createDate;
	private String around;//设置周边城市
	
	public String getAround() {
		return around;
	}
	public void setAround(String around) {
		this.around = around;
	}
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaEn() {
		return areaEn;
	}
	public void setAreaEn(String areaEn) {
		this.areaEn = areaEn;
	}
	public String getAreaType() {
		return areaType;
	}
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}
