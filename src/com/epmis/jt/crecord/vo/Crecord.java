package com.epmis.jt.crecord.vo;
/**
 * 催收记录
 * @author Administrator
 *
 */
public class Crecord {
	/**
	 * 催收记录id
	 */
	private String id;
	/**
	 * 催收员id
	 */
	private String cuid;
	/**
	 * 订单id
	 */
	private String order_id;
	/**
	 * 备注
	 */
	private String content;
	/**
	 * 客户行为
	 */
	private String cus_beh;
	/**
	 * 还款意愿
	 */
	private String rep_willingness;
	/**
	 * 沟通结果
	 */
	private String comm_results;
	/**
	 * 是否联系通讯录（0是  1否）
	 */
	private String contact_book_type;
	/**
	 * 是否为紧急联系人（0是  1否）
	 */
	private String emergency_contact;
	/**
	 * 是否联系其他联系人（0是  1否）
	 */
	private String contact_other_contacts;
	/**
	 * 通讯录联系情况
	 */
	private String book_situation;
	/**
	 * 创建时间
	 */
	private String createtime;
	/**
	 * 0未删除  1删除
	 */
	private String isdel;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCuid() {
		return cuid;
	}
	public void setCuid(String cuid) {
		this.cuid = cuid;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCus_beh() {
		return cus_beh;
	}
	public void setCus_beh(String cus_beh) {
		this.cus_beh = cus_beh;
	}
	public String getRep_willingness() {
		return rep_willingness;
	}
	public void setRep_willingness(String rep_willingness) {
		this.rep_willingness = rep_willingness;
	}
	public String getComm_results() {
		return comm_results;
	}
	public void setComm_results(String comm_results) {
		this.comm_results = comm_results;
	}
	public String getContact_book_type() {
		return contact_book_type;
	}
	public void setContact_book_type(String contact_book_type) {
		this.contact_book_type = contact_book_type;
	}
	public String getEmergency_contact() {
		return emergency_contact;
	}
	public void setEmergency_contact(String emergency_contact) {
		this.emergency_contact = emergency_contact;
	}
	public String getContact_other_contacts() {
		return contact_other_contacts;
	}
	public void setContact_other_contacts(String contact_other_contacts) {
		this.contact_other_contacts = contact_other_contacts;
	}
	public String getBook_situation() {
		return book_situation;
	}
	public void setBook_situation(String book_situation) {
		this.book_situation = book_situation;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	

}
