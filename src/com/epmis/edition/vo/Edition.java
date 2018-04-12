package com.epmis.edition.vo;
/**
 * 版本上传
 * @author Administrator
 *
 */
public class Edition {
	private int id;
	private String android;
	private String ios;
	private String androidcontent;
	private String ioscontent;
	private String androidURL;
	private String iosURL;
	private String isdel;
	private String androname;
	private String iosname;
	
	public String getAndroname() {
		return androname;
	}
	public void setAndroname(String androname) {
		this.androname = androname;
	}
	public String getIosname() {
		return iosname;
	}
	public void setIosname(String iosname) {
		this.iosname = iosname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAndroid() {
		return android;
	}
	public void setAndroid(String android) {
		this.android = android;
	}
	public String getIos() {
		return ios;
	}
	public void setIos(String ios) {
		this.ios = ios;
	}
	public String getAndroidcontent() {
		return androidcontent;
	}
	public void setAndroidcontent(String androidcontent) {
		this.androidcontent = androidcontent;
	}
	public String getIoscontent() {
		return ioscontent;
	}
	public void setIoscontent(String ioscontent) {
		this.ioscontent = ioscontent;
	}
	public String getAndroidURL() {
		return androidURL;
	}
	public void setAndroidURL(String androidURL) {
		this.androidURL = androidURL;
	}
	public String getIosURL() {
		return iosURL;
	}
	public void setIosURL(String iosURL) {
		this.iosURL = iosURL;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	
}
