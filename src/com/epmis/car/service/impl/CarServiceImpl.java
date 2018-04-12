package com.epmis.car.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.car.service.CarService;
import com.epmis.car.vo.Ecar;
import com.epmis.car.vo.Tcar;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
@Transactional
@Service("CarService")
public class CarServiceImpl implements CarService{
	
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	/**
	 * 分页查询
	 */
	@Override
	public List<Map<String, Object>> findAllCar(String title,String status,String isdel,String xin,String bid,String jid,String gid,String carsysid,String type,String csid,int page,int rows) {
		System.out.println(title);
		System.out.println(DataTypeUtil.validate(title));
		String sqlwhere="";
		if(DataTypeUtil.validate(title)){
			sqlwhere=sqlwhere+" and title like '%"+title+"%'";
		}
		if(DataTypeUtil.validate(csid)){
			sqlwhere=sqlwhere+" and  id='"+csid+"'";
		}
		if(DataTypeUtil.validate(status)){
			sqlwhere=sqlwhere+" and status="+status;
		}
		if(DataTypeUtil.validate(isdel)){
			sqlwhere=sqlwhere+" and isdel="+isdel;
		}else{
			sqlwhere=sqlwhere+" and isdel=0";
		}
		
		if(DataTypeUtil.validate(bid)){
			sqlwhere=sqlwhere+" and bid="+bid;
		}
		if(DataTypeUtil.validate(jid)){
			sqlwhere=sqlwhere+" and jid="+jid;
		}
		if(DataTypeUtil.validate(type)){
			sqlwhere=sqlwhere+" and type="+type;
		}else{
			sqlwhere=sqlwhere+" and type='0'";
		}
		if(DataTypeUtil.validate(gid)){
			String sql="select * from t_officialprice where id='"+gid+"'";
			List<Map<String, Object>> result = baseJdbcDao.queryListMap(sql);
			for(Map<String, Object> map:result){
				Object minprice=map.get("minprice");
				Object maxprice = map.get("maxprice");
				if(minprice==null && maxprice!=null){
					sqlwhere=sqlwhere+" and official_price<"+maxprice;
				}
				if(maxprice==null && minprice!=null){
					sqlwhere=sqlwhere+" and official_price>"+minprice;
				}
				if(minprice!=null && maxprice !=null){
					sqlwhere=sqlwhere+" and official_price BETWEEN "+minprice+" and "+maxprice;
				}
				
				
			}	
//			sqlwhere=sqlwhere+" and gid="+gid;
		}
		if(DataTypeUtil.validate(xin)){
			sqlwhere=sqlwhere+" and xing="+xin;
		}
		if(DataTypeUtil.validate(carsysid)){
			sqlwhere=sqlwhere+" and carsysid="+carsysid;
		}
		int start=(page-1)*rows;
		int end=rows;
		String sql="select id,title,isdel,down_ms,pic,ico,naked_price,naked_price_unit,monthly_payments,down_payment,purchase_tax,insurance,official_price,official_unit,sales_volume,stock,jiangjia,jiangjiaunit,service_charge,type,orderflag from  t_car where 1=1 "+sqlwhere+" order by id desc limit "+start+","+end;
	return	this.baseJdbcDao.queryListMap(sql);
		
	}
	/**
	 * 计算总数
	 */
	@Override
	public int count(String title,String status,String isdel,String xin,String bid,String jid,String gid,String carsysid,String type,String csid) {
		String sqlwhere="";
		if(DataTypeUtil.validate(title)){
			sqlwhere=sqlwhere+" and title like '%"+title+"%'";
		}
		if(DataTypeUtil.validate(csid)){
			sqlwhere=sqlwhere+" and  id='"+csid+"'";
		}
		if(DataTypeUtil.validate(status)){
			sqlwhere=sqlwhere+" and status="+status;
		}
		if(DataTypeUtil.validate(isdel)){
			sqlwhere=sqlwhere+" and isdel="+isdel;
		}else{
			sqlwhere=sqlwhere+" and isdel=0";
		}
		if(DataTypeUtil.validate(bid)){
			sqlwhere=sqlwhere+" and bid="+bid;
		}
		if(DataTypeUtil.validate(jid)){
			sqlwhere=sqlwhere+" and jid="+jid;
		}
//		if(DataTypeUtil.validate(gid)){
//			sqlwhere=sqlwhere+" and gid="+gid;
//		}
		if(DataTypeUtil.validate(xin)){
			sqlwhere=sqlwhere+" and xing="+xin;
		}
		if(DataTypeUtil.validate(carsysid)){
			sqlwhere=sqlwhere+" and carsysid="+carsysid;
		}
		if(DataTypeUtil.validate(type)){
			sqlwhere=sqlwhere+" and type="+type;
		}else{
			sqlwhere=sqlwhere+" and type='0'";
		}
		if(DataTypeUtil.validate(gid)){
			String sql="select * from t_officialprice where id='"+gid+"'";
			List<Map<String, Object>> result = baseJdbcDao.queryListMap(sql);
			for(Map<String, Object> map:result){
				Object minprice=map.get("minprice");
				Object maxprice = map.get("maxprice");
				if(minprice==null && maxprice!=null){
					sqlwhere=sqlwhere+" and official_price<"+maxprice;
				}
				if(maxprice==null && minprice!=null){
					sqlwhere=sqlwhere+" and official_price>"+minprice;
				}
				if(minprice!=null && maxprice !=null){
					sqlwhere=sqlwhere+" and official_price BETWEEN "+minprice+" and "+maxprice;
				}
				
				
			}	
//			sqlwhere=sqlwhere+" and gid="+gid;
		}
		String sql="select count(1) from t_car where 1=1 "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}
	/**
	 * 根据id删除
	 */
	@Override
	public String delete(String id) {
		String sql="update t_car set isdel='1' where id="+id;
		String sql1="update t_car_tj_rx set isdel='1' where cid="+id;//车型推荐和热销进行下架
		this.baseJdbcDao.delete(sql1);
		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}
	
	/**
	 * 新增
	 */
	@Override
	public String save(Tcar car,String fuzhucs) {
		if(this.baseJdbcDao.getCount("SELECT count(id) from t_car where isdel='0' and REPLACE(title,' ','')=REPLACE('"+car.getTitle()+"' ,' ','')") > 0){
			return "输入车型名称已存在";
		}
		String sqlwhere="";
		if(car.getPic()==null || car.getPic()==""){
			sqlwhere=" '/upload/jiazai4.png' , ";
		}else{
			sqlwhere="'"+car.getPic()+"', ";
		}
		String sqlwhere1="";
		if(car.getIco()==null || car.getIco()==""){
			sqlwhere1=" '/upload/jiazai4.png' , ";
			
		}else{
			sqlwhere1= "'"+car.getIco()+"', ";
		}
		//houjia
		String sqlwhere3="";
		if(car.getVedioPic()==null || car.getVedioPic()==""){
			sqlwhere3=" '/upload/jiazai4.png' , ";
		}else{
			sqlwhere3="'"+car.getPic()+"', ";
		}
		
		
		String sql="INSERT INTO `t_car`(title,pic,ico,vedioPic,naked_price,naked_price_unit,official_price,official_unit"
				+ ",monthly_payments,month_ms,monthly_unit,down_payment,down_unit,cash_deposit"
				+ ",purchase_tax,insurance,car_size,car_construction,engine,bid"
				+ ",jid,gid,sid,lid,yid,status,isdel,car_vedio,car_title,buy_car_flow,car_long"
				+ ",car_wide,car_hight,car_min_space,wheel_base,"
				+ "warranty_policy,displacement,power,Ministry_industry,official,"
				+ "tallest,xing,carsysid,sales_volume,stock,jiangjia,jiangjiaunit,service_charge,down_ms,service_charge_unit,overall_view) VALUES ("
				+ "'"+car.getTitle()+"', "
						+ sqlwhere
						+sqlwhere1
						+sqlwhere3
						+ "'"+car.getNaked_price()+"', "
						+ "'"+car.getNaked_price_unit()+"', "
						+ "'"+car.getOfficial_price()+"', "
						+ "'"+car.getOfficial_unit()+"', "
						+ "'"+car.getMonthly_payments()+"', "
						+ "'"+car.getMonth_ms()+"', "
						+ "'"+car.getMonthly_unit()+"', "
						+ "'"+car.getDown_payment()+"', "
						+ "'"+car.getDown_unit()+"', "
						+ "'"+car.getCash_deposit()+"', "
						+ "'"+car.getPurchase_tax()+"', "
						+ "'"+car.getInsurance()+"', "
						+ "'"+car.getCar_size()+"', "
						+ "'"+car.getCar_construction()+"', "
						+ "'"+car.getEngine()+"', "
						+ "'"+car.getBid()+"', "
						+ "'"+car.getJid()+"', "
						+ "'"+car.getGid()+"', "
						+ "'"+car.getSid()+"', "
						+ "'"+car.getLid()+"', "
						+ "'"+car.getYid()+"', "
						+ "'"+car.getStatus()+"', "
						+ "'"+car.getIsdel()+"', "
						+ "'"+car.getCar_vedio()+"', "
						+ "'"+car.getCar_title()+"', "
						+ "'"+car.getBuy_car_flow()+"', "
						+ "'"+car.getCar_long()+"', "
						+ "'"+car.getCar_wide()+"', "
						+ "'"+car.getCar_hight()+"', "
						+ "'"+car.getCar_min_space()+"', "
						+ "'"+car.getWheel_base()+"', "
						+ "'"+car.getWarranty_policy()+"', "
						+ "'"+car.getDisplacement()+"', "
						+ "'"+car.getPower()+"', "
						+ "'"+car.getMinistry_industry()+"', "
						+ "'"+car.getOfficial()+"', "
						+ "'"+car.getTallest()+"', "					
						+ "'"+car.getXin()+"', "
						+ "'"+car.getCarsysid()+"', "						
						+ "'"+car.getSales_volume()+"', "
						+ "'"+car.getStock()+"', "						
						+ "'"+car.getJiangjia()+"', "									
						+ "'"+car.getJiangjiaunit()+"', "
						+ "'"+car.getService_charge()+"', "	
						+ "'"+car.getDown_ms()+"', "
						+ "'"+car.getService_charge_unit()+"',"
						+ "'"+car.getOverall_view()+"'"
						+ " );";
		this.baseJdbcDao.insert(sql);
		
		//查询新插入的车型数据
		String sql1="select * from t_car where 1=1 order by id desc limit 0,1 ";
		Object cid=null;
		List<Map<String, Object>> result = baseJdbcDao.queryListMap(sql1);
		for(Map<String, Object> map:result){
			//车型的ID
			cid=map.get("id");
			//库存量
			Object num=map.get("stock");
			//把信息插入到库存表中
			String sql2="insert into t_kucun(cid,num,sumnum,isdel) VALUES("
					+ "'"+cid+"', "									
					+ "'"+num+"', "
					+ "'"+num+"', "									
					+ "'"+0+"'"					
					+ ");";
			this.baseJdbcDao.insert(sql2);
			
			//车型新增给默认进行排序值（将id赋值给orderflag）
			String sql3="update t_car set orderflag='"+cid+"' where id="+cid;			
			 this.baseJdbcDao.delete(sql3);
			
		}
		
		/**
		 * 是否复制
		 */
		System.out.println("复制准备进入"+fuzhucs);
		
		if("19491001".equals(fuzhucs)){
			System.out.println("进入复制·······");
			/**
			 * 座椅参数
			 */
			//复制座椅参数
			String sqlseat="insert into t_param_seat(cid,caizhi,yundongfengge,zuoyigaodi,yaobuzhicheng,jianbuzhicheng,jiashizuo,dierpaikaobei,dierpaizuoyi,houpaizuoyi,fujiashiweihoupai,diandongzuoyijiyi,jiare,tongfeng,anmo,twodulizuoyi,threezuoyi,fangdaofangshi,zhongyangfushou,houpaibeijia,relengbeijia,isdel) select cid,caizhi,yundongfengge,zuoyigaodi,yaobuzhicheng,jianbuzhicheng,jiashizuo,dierpaikaobei,dierpaizuoyi,houpaizuoyi,fujiashiweihoupai,diandongzuoyijiyi,jiare,tongfeng,anmo,twodulizuoyi,threezuoyi,fangdaofangshi,zhongyangfushou,houpaibeijia,relengbeijia,isdel from t_param_seat where id="+car.getId();
			this.baseJdbcDao.insert(sqlseat);
			//修改座椅参数车型id（cid）
			String sqlseate1="UPDATE t_param_seat SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_seat where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqlseate1);
			
			/**
			 * 主/被动安全装备
			 */
			//复制主/被动安全装备参数
			String sqlaqzb="insert into t_param_anquanzhuangbei(cid,zhufuqinang,qianhouqinang,qianhouqilian,xibuqinang,taiyajiance,lingtaiya,anquandai,ISOFIX,ABS,zhidongli,shachefuzhu,qianyinli,cheshenwending,bingxian,chedaopianli,zhudongshache,yeshi,pilao,isdel) select cid,zhufuqinang,qianhouqinang,qianhouqilian,xibuqinang,taiyajiance,lingtaiya,anquandai,ISOFIX,ABS,zhidongli,shachefuzhu,qianyinli,cheshenwending,bingxian,chedaopianli,zhudongshache,yeshi,pilao,isdel from t_param_anquanzhuangbei where id="+car.getId();
			this.baseJdbcDao.insert(sqlaqzb);
			//修改主/被动安全装备参数车型id（cid）
			String sqlaqzb1="UPDATE t_param_anquanzhuangbei SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_anquanzhuangbei where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqlaqzb1);
			
			/**
			 * 辅助/操控配置
			 */
			//复制辅助/操控配置参数
			String sqlckpz="insert into t_param_assist(cid,zhucheleida,dacheshiping,quanjingshexiang,dingsuxunhang,zishiyingxunhang,zidongbocheruwei,fadongjiqiting,shangpofuzhu,zidongzhuche,doupohuanjiang,kebianxuanjia,kongqixuanjia,dianciganying,kebianzhuanxiangbi,qianqiaoxianhua,zhongyang,houqianxianhua,zhengtizhudong,isdel) select cid,zhucheleida,dacheshiping,quanjingshexiang,dingsuxunhang,zishiyingxunhang,zidongbocheruwei,fadongjiqiting,shangpofuzhu,zidongzhuche,doupohuanjiang,kebianxuanjia,kongqixuanjia,dianciganying,kebianzhuanxiangbi,qianqiaoxianhua,zhongyang,houqianxianhua,zhengtizhudong,isdel from t_param_assist where id="+car.getId();
			this.baseJdbcDao.insert(sqlckpz);
			//修改辅助/操控配置参数车型id（cid）
			String sqlckpz1="UPDATE t_param_assist SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_assist where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqlckpz1);
			
			/**
			 * 变速箱
			 */
			//复制变速箱参数
			String sqlbsx="insert into t_param_biansuxiang(cid,jiancheng,dangweigeshu,biansuxiangleixing,isdel) select cid,jiancheng,dangweigeshu,biansuxiangleixing,isdel from t_param_biansuxiang where id="+car.getId();
			this.baseJdbcDao.insert(sqlbsx);
			//修改变速箱参数车型id（cid）
			String sqlbsx1="UPDATE t_param_biansuxiang SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_biansuxiang where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqlbsx1);
			
			/**
			 * 车轮制动
			 */
			//复制车轮制动参数
			String sqlclzd="insert into t_param_chelunzhidong(cid,qianzhidongqi,houzhidongqi,zhuchezhidong,qianluntai,houluntai,beitai,isdel) select cid,qianzhidongqi,houzhidongqi,zhuchezhidong,qianluntai,houluntai,beitai,isdel from t_param_chelunzhidong where id="+car.getId();
			this.baseJdbcDao.insert(sqlclzd);
			//修改车轮制动参数车型id（cid）
			String sqlclzd1="UPDATE t_param_chelunzhidong SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_chelunzhidong where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqlclzd1);
			
			/**
			 * 车身
			 */
			//复制车身参数
			String sqlcs="insert into t_param_cheshen(cid,changdu,kuandu,gaodu,zhouju,qianlunju,houlunju,zuixiaolidijanxi,zhengbeizhiliang,cheshenjiegou,chemenshu,zuoweishu,youxiangrongliang,xinglixiang,isdel) select cid,changdu,kuandu,gaodu,zhouju,qianlunju,houlunju,zuixiaolidijanxi,zhengbeizhiliang,cheshenjiegou,chemenshu,zuoweishu,youxiangrongliang,xinglixiang,isdel from t_param_cheshen where id="+car.getId();
			this.baseJdbcDao.insert(sqlcs);
			//修改车身参数车型id（cid）
			String sqlcs1="UPDATE t_param_cheshen SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_cheshen where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqlcs1);
			
			/**
			 * 空调/冰箱
			 */
			//复制空调/冰箱参数
			String sqlkb="insert into t_param_conditioner(cid,kongzhifangshi,dulikongtiao,houzuochufengkou,wendufenqu,cheneikongtiao,chezaikongqi,chezaibingxiang,waiguanyanse,neishiyanse,isdel) select cid,kongzhifangshi,dulikongtiao,houzuochufengkou,wendufenqu,cheneikongtiao,chezaikongqi,chezaibingxiang,waiguanyanse,neishiyanse,isdel from t_param_conditioner where id="+car.getId();
			this.baseJdbcDao.insert(sqlkb);
			//修改空调/冰箱参数车型id（cid）
			String sqlkb1="UPDATE t_param_conditioner SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_conditioner where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqlkb1);
			
			/**
			 * 底盘转向
			 */
			//复制底盘转向参数
			String sqldpzx="insert into t_param_dipanzhuanxiang(cid,qudongfangshi,qianxuanjia,houxuanjia,zhulileixing,chetijiegou,isdel) select cid,qudongfangshi,qianxuanjia,houxuanjia,zhulileixing,chetijiegou,isdel from t_param_dipanzhuanxiang where id="+car.getId();
			this.baseJdbcDao.insert(sqldpzx);
			//修改底盘转向参数车型id（cid）
			String sqldpzx1="UPDATE t_param_dipanzhuanxiang SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_dipanzhuanxiang where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqldpzx1);
			
			/**
			 * 发动机
			 */
			//复制发动机参数
			String sqlfdj="insert into t_param_fadongji(cid,fadongjixinghao,pailiang,pailiang2,jinqixingshi,qigangpailiexingshi,qigangshu,meigangqimenshu,yasuobi,peiqijiegou,gangjing,xingcheng,zuidamali,zuidagonglv,zuidagonglvzhuansu,zuidaniuju,zuidaniujuzhuansu,fadongjiteyoujishu,ranliaoxingshi,ranyoubiaohao,gongyoufangshi,ganggaicailiao,gangticailiao,huanbaobiaozhun,isdel) select cid,fadongjixinghao,pailiang,pailiang2,jinqixingshi,qigangpailiexingshi,qigangshu,meigangqimenshu,yasuobi,peiqijiegou,gangjing,xingcheng,zuidamali,zuidagonglv,zuidagonglvzhuansu,zuidaniuju,zuidaniujuzhuansu,fadongjiteyoujishu,ranliaoxingshi,ranyoubiaohao,gongyoufangshi,ganggaicailiao,gangticailiao,huanbaobiaozhun,isdel from t_param_fadongji where id="+car.getId();
			this.baseJdbcDao.insert(sqlfdj);
			//修改发动机参数车型id（cid）
			String sqlfdj1="UPDATE t_param_fadongji SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_fadongji where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqlfdj1);
			
			/**
			 * 外部/防盗配置
			 */
			//复制外部/防盗配置参数
			String sqlguard="insert into t_param_guard(cid,diandongtianchuang,quanjingtianchuang,yundongwaiguan,lvhejinlunquan,diandongxihemen,cehuomen,diandonghoubeixiang,ganyinghoubeixiang,chedingxinglijia,fadongjidianzifangdao,cheneizhongkongsuo,yaokongyaoshi,wuyaoshiqidongxitong,wuyaoshijinruxitong,yuancheqidong,isdel) select cid,diandongtianchuang,quanjingtianchuang,yundongwaiguan,lvhejinlunquan,diandongxihemen,cehuomen,diandonghoubeixiang,ganyinghoubeixiang,chedingxinglijia,fadongjidianzifangdao,cheneizhongkongsuo,yaokongyaoshi,wuyaoshiqidongxitong,wuyaoshijinruxitong,yuancheqidong,isdel from t_param_guard where id="+car.getId();
			this.baseJdbcDao.insert(sqlguard);
			//修改外部/防盗配置参数车型id（cid）
			String sqlguard1="UPDATE t_param_guard SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_guard where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqlguard1);
			
			/**
			 * 内部配置
			 */
			//复制内部配置参数
			String sqlnb="insert into t_param_interior(cid,zhenpifangxiangpan,fangxiangpantiaojie,diandongtiaojie,duogongnengfangxiang,huandang,jiare,jiyi,xingchediannao,quanyejing,HUDtaitou,neizhixingche,zhudongjiangzao,shoujicongdian,isdel) select cid,zhenpifangxiangpan,fangxiangpantiaojie,diandongtiaojie,duogongnengfangxiang,huandang,jiare,jiyi,xingchediannao,quanyejing,HUDtaitou,neizhixingche,zhudongjiangzao,shoujicongdian,isdel from t_param_interior where id="+car.getId();
			this.baseJdbcDao.insert(sqlnb);
			//修改内部配置参数车型id（cid）
			String sqlnb1="UPDATE t_param_interior SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_interior where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqlnb1);
			
			/**
			 * 基本参数
			 */
			//复制基本参数参数
			String sqljbcs="insert into t_param_jibencanshu(cid,shangshishijian,fadongji,biansuxiang,changkuangao,cheshenjiegou,zuigaochesu,guanfangjiasu,shicejiasu,shicezhidong,shiceyouhao,gongxinbuyouhao,shicelidijianxi,zhengchezhibao,isdel) select cid,shangshishijian,fadongji,biansuxiang,changkuangao,cheshenjiegou,zuigaochesu,guanfangjiasu,shicejiasu,shicezhidong,shiceyouhao,gongxinbuyouhao,shicelidijianxi,zhengchezhibao,isdel from t_param_jibencanshu where id="+car.getId();
			this.baseJdbcDao.insert(sqljbcs);
			//修改基本参数参数车型id（cid）
			String sqljbcs1="UPDATE t_param_jibencanshu SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_jibencanshu where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqljbcs1);
			
			/**
			 * 灯光配置
			 */
			//复制灯光配置参数
			String sqldg="insert into t_param_lamplight(cid,jinguangdeng,yuanguangdeng,LEDrijian,dingshiying,zidongtoudeng,zhuanxiangfuzhu,zhuanxiangtou,qianwu,dadenggaodu,dadengqingxi,cheneifenwei,isdel) select cid,jinguangdeng,yuanguangdeng,LEDrijian,dingshiying,zidongtoudeng,zhuanxiangfuzhu,zhuanxiangtou,qianwu,dadenggaodu,dadengqingxi,cheneifenwei,isdel from t_param_lamplight where id="+car.getId();
			this.baseJdbcDao.insert(sqldg);
			//修改灯光配置参数车型id（cid）
			String sqldg1="UPDATE t_param_lamplight SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_lamplight where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqldg1);
			
			/**
			 * 多媒体配置
			 */
			//复制多媒体配置参数
			String sqldmt="insert into t_param_multimedia(cid,GPSdaohang,dingweihudong,caisedaping,caisedapingchicun,yejingpingfenping,lanyachezai,shoujihulianyingshe,chelianwang,chezaidianshi,houpaiyejingping,dianyuan,waijieyinyuan,cddvd,yangshengqipinpai,yangshengqisuliang,isdel) select cid,GPSdaohang,dingweihudong,caisedaping,caisedapingchicun,yejingpingfenping,lanyachezai,shoujihulianyingshe,chelianwang,chezaidianshi,houpaiyejingping,dianyuan,waijieyinyuan,cddvd,yangshengqipinpai,yangshengqisuliang,isdel from t_param_multimedia where id="+car.getId();
			this.baseJdbcDao.insert(sqldmt);
			//修改多媒体配置参数车型id（cid）
			String sqldmt1="UPDATE t_param_multimedia SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_multimedia where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqldmt1);
			
			/**
			 * 玻璃/后视镜
			 */
			//复制玻璃/后视镜配置参数
			String sqlbl="insert into t_param_plastic(cid,diandongchechuang,yijianshengjiang,chechuangfangjia,fangziwaixian,diandongtiaojie,jiare,fangxuanmu,liumeiti,zhedie,jiyi,dangzheyanglian,houpaicezhe,ceyinsiboli,zheyangbanhuazhuang,houyushua,ganyingyushua,isdel) select cid,diandongchechuang,yijianshengjiang,chechuangfangjia,fangziwaixian,diandongtiaojie,jiare,fangxuanmu,liumeiti,zhedie,jiyi,dangzheyanglian,houpaicezhe,ceyinsiboli,zheyangbanhuazhuang,houyushua,ganyingyushua,isdel from t_param_plastic where id="+car.getId();
			this.baseJdbcDao.insert(sqlbl);
			//修改玻璃/后视镜配置参数车型id（cid）
			String sqlbl1="UPDATE t_param_plastic SET cid='"+cid+"'  where isdel='0' AND id = (SELECT a.id FROM(select id from t_param_plastic where  cid='"+car.getId()+"' AND isdel='0' ORDER BY id DESC LIMIT 0,1)a)";
			this.baseJdbcDao.update(sqlbl1);
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
		
		return "success";
	}
	
	
	/**
	 * 根据id查询
	 */
	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from t_car where id='"+id+"'";
		return this.baseJdbcDao.queryListMap(sql);
		 
	}
	/**
	 * 修改
	 */
	@Override
	public String update(Tcar car){
		if(this.baseJdbcDao.getCount("SELECT count(id) from t_car where isdel='0' and REPLACE(title,' ','')=REPLACE('"+car.getTitle()+"' ,' ','') AND  ID <>"+car.getId()) > 0){
			return "车型名称重复";
		}
		String sqlwhere="";
		if(car.getPic()==null || car.getPic()==""){
			sqlwhere="";
		}else{
			sqlwhere= " pic='"+car.getPic()+"', ";
		}
		String sqlwhere1="";
		if(car.getIco()==null || car.getIco()==""){
			sqlwhere1="";
		}else{
			sqlwhere1= " ico='"+car.getIco()+"', ";
		}
		String sqlwhere2="";
		if(car.getCar_vedio()==null || car.getCar_vedio()==""){
			sqlwhere2="";
		}else{
			sqlwhere2= " Car_vedio='"+car.getCar_vedio()+"', ";
		}
		//houjia
		String sqlwhere3="";
		if(car.getVedioPic()==null || car.getVedioPic()==""){
			sqlwhere3="";
		}else{
			sqlwhere3= " vedioPic='"+car.getVedioPic()+"', ";
		}
		
		String sql="update t_car set "
				+ "title='"+car.getTitle()+"', "				
						+ sqlwhere
						+ sqlwhere1
						+ "naked_price='"+car.getNaked_price()+"', "
						+ "naked_price_unit='"+car.getNaked_price_unit()+"', "
						+ "Official_price='"+car.getOfficial_price()+"', "
						+ "Official_unit='"+car.getOfficial_unit()+"', "
						+ "Monthly_payments='"+car.getMonthly_payments()+"', "
						+ "Month_ms='"+car.getMonth_ms()+"', "
						+ "Monthly_unit='"+car.getMonthly_unit()+"', "
						+ "Down_payment='"+car.getDown_payment()+"', "
						+ "Down_unit='"+car.getDown_unit()+"', "
						+ "Cash_deposit='"+car.getCash_deposit()+"', "
						+ "Purchase_tax='"+car.getPurchase_tax()+"', "
						+ "Insurance='"+car.getInsurance()+"', "
						+ "Car_size='"+car.getCar_size()+"', "
						+ "Car_construction='"+car.getCar_construction()+"', "
						+ "Engine='"+car.getEngine()+"', "
						+ "Bid='"+car.getBid()+"', "
						+ "Jid='"+car.getJid()+"', "
						+ "Gid='"+car.getGid()+"', "
						+ "Sid='"+car.getSid()+"', "
						+ "Lid='"+car.getLid()+"', "
						+ "Yid='"+car.getYid()+"', "
						+ "Status='"+car.getStatus()+"', "
						+ "Isdel='"+car.getIsdel()+"', "
						
						+ sqlwhere2
						+ sqlwhere3
						
						+ "Buy_car_flow='"+car.getBuy_car_flow()+"', "
						+ "Car_long='"+car.getCar_long()+"', "
						+ "car_title='"+car.getCar_title()+"', "
						+ "Car_wide='"+car.getCar_wide()+"', "
						+ "Car_hight='"+car.getCar_hight()+"', "
						+ "Car_min_space='"+car.getCar_min_space()+"', "
						+ "Wheel_base='"+car.getWheel_base()+"', "
						+ "Warranty_policy='"+car.getWarranty_policy()+"', "
						+ "Displacement='"+car.getDisplacement()+"', "
						+ "Power='"+car.getPower()+"', "
						+ "Ministry_industry='"+car.getMinistry_industry()+"', "
						+ "Official='"+car.getOfficial()+"', "
						+ "Tallest='"+car.getTallest()+"', "				
						+ "carsysid='"+car.getCarsysid()+"', "							
						+ "sales_volume='"+car.getSales_volume()+"', "
						+ "stock='"+car.getStock()+"', "
						+ "jiangjia='"+car.getJiangjia()+"', "
						+ "jiangjiaunit='"+car.getJiangjiaunit()+"', "							
						+ "service_charge='"+car.getService_charge()+"', "	
						+ "down_ms='"+car.getDown_ms()+"', "	
						+ "service_charge_unit='"+car.getService_charge_unit()+"', "	
						
						
						
						
						+ "Xing='"+car.getXin()+"',"
						+ "overall_view='"+car.getOverall_view()+"'"
				 +" where id='"+car.getId()+"' ";
		this.baseJdbcDao.update(sql);
		return "success";
		
	}
	@Override
	public String hf(String id) {
		String sql="update t_car set isdel='0' where id="+id;
		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}
	//显示--->隐藏
	@Override
	public String xs(String id) {
		String sql="update t_car set type='1' where id="+id;
		
		String sql1="update t_car_tj_rx set isdel='1' where cid="+id;//车型推荐和热销进行下架
		this.baseJdbcDao.delete(sql1);
		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}
	/**
	 * 隐藏--->显示
	 */
	@Override
	public String yc(String id) {
		String sql="update t_car set type='0' where id="+id;
		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}
	/**
	 * 排序位置
	 */
	@Override
	public String wz(Tcar car) {
		String sql="update t_car set orderflag='"+car.getOrderflag()+"' where id="+car.getId();
		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	}
	@Override
	public int count(Object cid) {
		System.out.println("进来了"+cid);
		return this.baseJdbcDao.getCount("SELECT count(id) from t_car where  id='"+cid+"'");
		 
	}
	
	/**
	 * 修改价格
	 */
	@Override
	public String updateExcel(Ecar car) {
		
		String sql="update t_car set "
				
						+ "naked_price='"+car.getNaked_price()+"', "						
						+ "Official_price='"+car.getOfficial_price()+"', "						
						+ "Monthly_payments='"+car.getMonthly_payments()+"', "						
						+ "Down_payment='"+car.getDown_payment()+"', "						
						+ "Purchase_tax='"+car.getPurchase_tax()+"', "
						+ "Insurance='"+car.getInsurance()+"', "
					
						
						
					
						
						
						+ "jiangjia='"+car.getJiangjia()+"', "
													
						+ "service_charge='"+car.getService_charge()+"', "	
						
					+ "subsidy='"+car.getSubsidy()+"', "
					+ "month_ms='"+car.getMonth_ms()+"', "
					+ "down_ms='"+car.getDown_ms()+"' "
						
						
						
						
				 +" where id='"+car.getId()+"' ";
		this.baseJdbcDao.update(sql);
		return "success";
		
	}
	@Override
	public String deletez(String id) {

		String sql="delete from t_car where id="+id;
		
		 this.baseJdbcDao.delete(sql);
		 return "success";
	
	}
	@Override
	public String upxl(Tcar car) {
		
		String sql="update t_car set sales_volume='"+car.getSales_volume()+"' where id='"+car.getId()+"'";
		this.baseJdbcDao.update(sql);
		return "success";
		
	}
	
	

	

}
