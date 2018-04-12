package com.epmis.jt.adv.vo;
/**
 * 借条公告表
 * @author Administrator
 *
 */
public class Adv {
	private int id;
	private String title;
	private String pic;
	private String content;
	private String type;
	private String url;
	private String orderflag;
	private String isdel;
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOrderflag() {
		return orderflag;
	}
	public void setOrderflag(String orderflag) {
		this.orderflag = orderflag;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	
	

}
