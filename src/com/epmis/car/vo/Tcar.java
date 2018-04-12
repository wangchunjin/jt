package com.epmis.car.vo;

public class Tcar {
	
	private int id;//汽车ID
	private String title;//汽车标题
	private String pic;//汽车主图片
	private String ico;//汽车列表小图片
	private double naked_price;//裸车价格
	private String naked_price_unit;      //后补   裸车单位
	private double official_price;//官方指导价
	private String official_unit;//官方指导价单位
	private double monthly_payments;//月供
	private String monthly_unit;//月供单位
	private String month_ms;
	private double down_payment;//首付
	private String down_unit;//首付单位
	public String getNaked_price_unit() {
		return naked_price_unit;
	}
	public void setNaked_price_unit(String naked_price_unit) {
		this.naked_price_unit = naked_price_unit;
	}
	private String cash_deposit;//保证金
	private String purchase_tax;//购置税
	private String insurance;//保险
	private String car_size;//车身尺寸
	private String car_construction;//车结构
	private String engine;//发动机
	private int bid;//品牌ID
	private int jid;//级别ID
	private int gid;//官方指导价ID
	private int sid;//首付ID
	private int lid;//类型ID
	private int yid;//月供ID
	private int status;//1.热卖 2.新品
	private int isdel;//0:未删除 1.删除
	private String car_vedio;//汽车车型视频
	private String vedioPic;
	private String car_title;
	private int type;//是否显示（大屏）
	private String orderflag;//排序
	private String down_ms;//首付描述
	private String overall_view;//全景图片
	
	
	
	
	public String getOverall_view() {
		return overall_view;
	}
	public void setOverall_view(String overall_view) {
		this.overall_view = overall_view;
	}
	public String getMonth_ms() {
		return month_ms;
	}
	public void setMonth_ms(String month_ms) {
		this.month_ms = month_ms;
	}
	public String getDown_ms() {
		return down_ms;
	}
	public void setDown_ms(String down_ms) {
		this.down_ms = down_ms;
	}
	public String getOrderflag() {
		return orderflag;
	}
	public void setOrderflag(String orderflag) {
		this.orderflag = orderflag;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCar_title() {
		return car_title;
	}
	public void setCar_title(String car_title) {
		this.car_title = car_title;
	}
	private String buy_car_flow;//购车流程
	private int car_long;//车长
	private int car_wide;//车宽
	private int car_hight;//车高
	private int car_min_space;//最小离地距离
	private int wheel_base;//轴距
	private String warranty_policy;//保修政策
	private String displacement;//排量
	private String power;//功率
	private String Ministry_industry;//工信部
	private String official;//官方
	private String tallest;//最高
	private int xin;//星级
	private int carsysid;//车系ID
	private String sales_volume;
	private String stock;
	private double jiangjia;
	private String jiangjiaunit;
	private String service_charge;
	private String service_charge_unit;
	
	
	public String getVedioPic() {
		return vedioPic;
	}
	public void setVedioPic(String vedioPic) {
		this.vedioPic = vedioPic;
	}
	public String getService_charge() {
		return service_charge;
	}
	public void setService_charge(String service_charge) {
		this.service_charge = service_charge;
	}
	public String getService_charge_unit() {
		return service_charge_unit;
	}
	public void setService_charge_unit(String service_charge_unit) {
		this.service_charge_unit = service_charge_unit;
	}
	public String getSales_volume() {
		return sales_volume;
	}
	public void setSales_volume(String sales_volume) {
		this.sales_volume = sales_volume;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public double getJiangjia() {
		return jiangjia;
	}
	public void setJiangjia(double jiangjia) {
		this.jiangjia = jiangjia;
	}
	public String getJiangjiaunit() {
		return jiangjiaunit;
	}
	public void setJiangjiaunit(String jiangjiaunit) {
		this.jiangjiaunit = jiangjiaunit;
	}
	public int getCarsysid() {
		return carsysid;
	}
	public void setCarsysid(int carsysid) {
		this.carsysid = carsysid;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public double getNaked_price() {
		return naked_price;
	}
	public void setNaked_price(double naked_price) {
		this.naked_price = naked_price;
	}
	public double getOfficial_price() {
		return official_price;
	}
	public void setOfficial_price(double official_price) {
		this.official_price = official_price;
	}
	public String getOfficial_unit() {
		return official_unit;
	}
	public void setOfficial_unit(String official_unit) {
		this.official_unit = official_unit;
	}
	public double getMonthly_payments() {
		return monthly_payments;
	}
	public void setMonthly_payments(double monthly_payments) {
		this.monthly_payments = monthly_payments;
	}
	public String getMonthly_unit() {
		return monthly_unit;
	}
	public void setMonthly_unit(String monthly_unit) {
		this.monthly_unit = monthly_unit;
	}
	public double getDown_payment() {
		return down_payment;
	}
	public void setDown_payment(double down_payment) {
		this.down_payment = down_payment;
	}
	public String getDown_unit() {
		return down_unit;
	}
	public void setDown_unit(String down_unit) {
		this.down_unit = down_unit;
	}
	public String getCash_deposit() {
		return cash_deposit;
	}
	public void setCash_deposit(String cash_deposit) {
		this.cash_deposit = cash_deposit;
	}
	public String getPurchase_tax() {
		return purchase_tax;
	}
	public void setPurchase_tax(String purchase_tax) {
		this.purchase_tax = purchase_tax;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public String getCar_size() {
		return car_size;
	}
	public void setCar_size(String car_size) {
		this.car_size = car_size;
	}
	public String getCar_construction() {
		return car_construction;
	}
	public void setCar_construction(String car_construction) {
		this.car_construction = car_construction;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public int getJid() {
		return jid;
	}
	public void setJid(int jid) {
		this.jid = jid;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public int getYid() {
		return yid;
	}
	public void setYid(int yid) {
		this.yid = yid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getIsdel() {
		return isdel;
	}
	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}
	public String getCar_vedio() {
		return car_vedio;
	}
	public void setCar_vedio(String car_vedio) {
		this.car_vedio = car_vedio;
	}
	public String getBuy_car_flow() {
		return buy_car_flow;
	}
	public void setBuy_car_flow(String buy_car_flow) {
		this.buy_car_flow = buy_car_flow;
	}
	public int getCar_long() {
		return car_long;
	}
	public void setCar_long(int car_long) {
		this.car_long = car_long;
	}
	public int getCar_wide() {
		return car_wide;
	}
	public void setCar_wide(int car_wide) {
		this.car_wide = car_wide;
	}
	public int getCar_hight() {
		return car_hight;
	}
	public void setCar_hight(int car_hight) {
		this.car_hight = car_hight;
	}
	public int getCar_min_space() {
		return car_min_space;
	}
	public void setCar_min_space(int car_min_space) {
		this.car_min_space = car_min_space;
	}
	public int getWheel_base() {
		return wheel_base;
	}
	public void setWheel_base(int wheel_base) {
		this.wheel_base = wheel_base;
	}
	public String getWarranty_policy() {
		return warranty_policy;
	}
	public void setWarranty_policy(String warranty_policy) {
		this.warranty_policy = warranty_policy;
	}
	public String getDisplacement() {
		return displacement;
	}
	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getMinistry_industry() {
		return Ministry_industry;
	}
	public void setMinistry_industry(String ministry_industry) {
		Ministry_industry = ministry_industry;
	}
	public String getOfficial() {
		return official;
	}
	public void setOfficial(String official) {
		this.official = official;
	}
	public String getTallest() {
		return tallest;
	}
	public void setTallest(String tallest) {
		this.tallest = tallest;
	}
	public int getXin() {
		return xin;
	}
	public void setXin(int xin) {
		this.xin = xin;
	}
	
	
	
	
}
