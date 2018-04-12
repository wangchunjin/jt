package com.epmis.about.vo;
/**
 * 关于我们
 * @author Administrator
 *
 */
public class About {
	private int id;
	private String pic;
	private String content;
	
	private String xy_content;
	private String url;
	private String isdel;
	private String servicephone;
	private String weixinname;
	
	public String getServicephone() {
		return servicephone;
	}
	public void setServicephone(String servicephone) {
		this.servicephone = servicephone;
	}
	public String getWeixinname() {
		return weixinname;
	}
	public void setWeixinname(String weixinname) {
		this.weixinname = weixinname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getXy_content() {
		return xy_content;
	}
	public void setXy_content(String xy_content) {
		this.xy_content = xy_content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	
	
}
