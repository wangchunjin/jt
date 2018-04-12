package com.epmis.jt.clientinfo.vo;
/**
 * 借款用户类
 * @author Administrator
 *
 */
public class Clientinfo {
	/**
	 * 用户id
	 */
	private int client_id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 头像
	 */
	private String pic;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 身份证号码
	 */
	private String idcard;
	/**
	 * 性别 0.女 1.男
	 */
	private String gender;
	/**
	 * 出生日期
	 */
	private String birth_date;
	/**
	 * 省份:两位数字 参见area_code表
	 */
	private String province;
	/**
	 * 城市:四位数字 参见area_code表
	 */
	private String city;
	/**
	 * 区县:六位数字 参见area_code表
	 */
	private String town;
	/**
	 * 家庭住址
	 */
	private String home_addr;
	/**
	 * 银行卡预留手机号
	 */
	private String bank_phone;
	/**
	 * 银行卡号
	 */
	private String bank_card;
	/**
	 * 公司名称
	 */
	private String company_name;
	/**
	 * 公司地址
	 */
	private String company_addr;
	/**
	 * qq号
	 */
	private String qq;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 第一联系人姓名
	 */
	private String contact1_name;
	/**
	 * 第一联系人关系 "0":配偶，"1":父母，"2":兄弟姐妹,"3":子女,"4":同事,"5": 同学,"6": 朋友
	 */
	private String contact1_relation;
	/**
	 * 第一联系人电话
	 */
	private String contact1_mobile;
	/**
	 * 第一联系人地址
	 */
	private String contact1_addr;
	/**
	 * 第二联系人姓名
	 */
	private String contact2_name;
	/**
	 * 第二联系人关系 "0":配偶，"1":父母，"2":兄弟姐妹,"3":子女,"4":同事,"5": 同学,"6": 朋友
	 */
	private String contact2_relation;
	/**
	 * 第二联系人电话
	 */
	private String contact2_mobile;
	/**
	 * 第二联系人地址
	 */
	private String contact2_addr;
	/**
	 * 职业: 0.学生/待业 1.餐饮/服务业 2.快递物流行业 3.生产制造业 4.建筑业 5.农林渔牧业 6.事业单位/公务员 7.企业职员 8.个体户/私营企业主 9.其他
	 */
	private String occupation;
	/**
	 * 月收入状况:0.2500及以下 1.2500-5000 2.5000-7500 3.7500-10000 4.10000及以上
	 */
	private String income;
	/**
	 * 车辆状况:0.无车 1.有车
	 */
	private String vechile_status;
	/**
	 * 教育状况:0.初中及以下 1.高中/中专 2.大专 3.本科 4.研究生及以上
	 */
	private String education;
	/**
	 * 社保状况:0.未交社保 1.已交社保
	 */
	private String social_scurity;
	/**
	 * 婚姻状况:0.未婚 1.已婚未育 2.已婚已育 3.离异
	 */
	private String marital_status;
	/**
	 * 住房状况:0.租房/宿舍 1.自有住房无贷款 2.自有住房有贷款 3.与父母同住
	 */
	private String accomodation;
	/**
	 * 基本信息填写: 0.未完成 1.已完成
	 */
	private String check_status_basic;
	/**
	 * 实名认证状态: 0.不通过 1.通过
	 */
	private String check_status_idcard;
	/**
	 * 银行卡验证状态: 0.不通过 1.通过
	 */
	private String check_status_bankcard;
	/**
	 * 运营商验证状态:0.不通过 1.通过
	 */
	private String chech_status_carrier;
	/**
	 * 授信审批状态:0.不通过 1.通过
	 */
	private String auth_status;
	/**
	 * 创建时间
	 */
	private String createtime;
	/**
	 * 是否删除  0.未删除    1.删除
	 */
	private String isdel;
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getHome_addr() {
		return home_addr;
	}
	public void setHome_addr(String home_addr) {
		this.home_addr = home_addr;
	}
	public String getBank_phone() {
		return bank_phone;
	}
	public void setBank_phone(String bank_phone) {
		this.bank_phone = bank_phone;
	}
	public String getBank_card() {
		return bank_card;
	}
	public void setBank_card(String bank_card) {
		this.bank_card = bank_card;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_addr() {
		return company_addr;
	}
	public void setCompany_addr(String company_addr) {
		this.company_addr = company_addr;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact1_name() {
		return contact1_name;
	}
	public void setContact1_name(String contact1_name) {
		this.contact1_name = contact1_name;
	}
	public String getContact1_relation() {
		return contact1_relation;
	}
	public void setContact1_relation(String contact1_relation) {
		this.contact1_relation = contact1_relation;
	}
	public String getContact1_mobile() {
		return contact1_mobile;
	}
	public void setContact1_mobile(String contact1_mobile) {
		this.contact1_mobile = contact1_mobile;
	}
	public String getContact1_addr() {
		return contact1_addr;
	}
	public void setContact1_addr(String contact1_addr) {
		this.contact1_addr = contact1_addr;
	}
	public String getContact2_name() {
		return contact2_name;
	}
	public void setContact2_name(String contact2_name) {
		this.contact2_name = contact2_name;
	}
	public String getContact2_relation() {
		return contact2_relation;
	}
	public void setContact2_relation(String contact2_relation) {
		this.contact2_relation = contact2_relation;
	}
	public String getContact2_mobile() {
		return contact2_mobile;
	}
	public void setContact2_mobile(String contact2_mobile) {
		this.contact2_mobile = contact2_mobile;
	}
	public String getContact2_addr() {
		return contact2_addr;
	}
	public void setContact2_addr(String contact2_addr) {
		this.contact2_addr = contact2_addr;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getVechile_status() {
		return vechile_status;
	}
	public void setVechile_status(String vechile_status) {
		this.vechile_status = vechile_status;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getSocial_scurity() {
		return social_scurity;
	}
	public void setSocial_scurity(String social_scurity) {
		this.social_scurity = social_scurity;
	}
	public String getMarital_status() {
		return marital_status;
	}
	public void setMarital_status(String marital_status) {
		this.marital_status = marital_status;
	}
	public String getAccomodation() {
		return accomodation;
	}
	public void setAccomodation(String accomodation) {
		this.accomodation = accomodation;
	}
	public String getCheck_status_basic() {
		return check_status_basic;
	}
	public void setCheck_status_basic(String check_status_basic) {
		this.check_status_basic = check_status_basic;
	}
	public String getCheck_status_idcard() {
		return check_status_idcard;
	}
	public void setCheck_status_idcard(String check_status_idcard) {
		this.check_status_idcard = check_status_idcard;
	}
	public String getCheck_status_bankcard() {
		return check_status_bankcard;
	}
	public void setCheck_status_bankcard(String check_status_bankcard) {
		this.check_status_bankcard = check_status_bankcard;
	}
	public String getChech_status_carrier() {
		return chech_status_carrier;
	}
	public void setChech_status_carrier(String chech_status_carrier) {
		this.chech_status_carrier = chech_status_carrier;
	}
	public String getAuth_status() {
		return auth_status;
	}
	public void setAuth_status(String auth_status) {
		this.auth_status = auth_status;
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
