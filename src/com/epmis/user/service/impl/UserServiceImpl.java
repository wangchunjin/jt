package com.epmis.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.user.service.UserService;
import com.epmis.user.vo.User;

@Transactional
@Service("UserService")
public class UserServiceImpl implements UserService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;

	@Override
	public List<Map<String, Object>> findAllUser(String title, String isdel,String uid,String type,String neibu,String uuid,String isrenzhen,String rz,String realname,
			int page, int rows) {
		String sqlwhere="";
		if(DataTypeUtil.validate(realname)){
			sqlwhere=sqlwhere+" and realName like '%"+realname+"%'";
		}
		if(DataTypeUtil.validate(title)){
			sqlwhere=sqlwhere+" and telephone like '%"+title+"%'";
		}
		if(DataTypeUtil.validate(isdel)){
			sqlwhere=sqlwhere+" and isdel="+isdel;
		}else{
			sqlwhere=sqlwhere+" and isdel=0";
		}
		if(DataTypeUtil.validate(uid)){
			sqlwhere=sqlwhere+" and id='"+uid+"'";
		}
		if(DataTypeUtil.validate(uuid)){
			sqlwhere=sqlwhere+" and uid='"+uuid+"'";
		}else{
			if("0".equals(type)){
				sqlwhere=sqlwhere+" and type<>'1'";
			}else{
				sqlwhere=sqlwhere+" and type='1'";
			}
		}
		
		
		if(DataTypeUtil.validate(neibu)){
			sqlwhere=sqlwhere+" and neibu='"+neibu+"'";
		}
		if("19940119".equals(rz)){
			if(DataTypeUtil.validate(isrenzhen)){
				sqlwhere=sqlwhere+" and isrenzhen='"+isrenzhen+"'";
			}else{
				sqlwhere=sqlwhere+" and isrenzhen='1'";
			}
		}
		
		int start=(page-1)*rows;
		int end=rows;
//		String sql="select id,ico,nickname,neibu,type,weiyibiaoshi,token,continuity_sign,total,uid,telephone,sex,brithdate,password,wxOpenId,qqOpenId,alipayOpenId,sinaOpenId,huanxinAccount,huanxinPassword,invitationCode,IdAuthentified,NaAuthentified,realName,IDNo,bankcardNo,createtime,isdel,(select signin from t_sign_in s where s.uid=u.id and s.isdel=0)signcount,(select sum(if(type=0,num,-1*num)) from t_score a where a.uid=u.id and a.isdel=0)scorecount from t_user u where 1=1 "+sqlwhere+" order by u.id desc limit "+start+","+end;
		String sql="select id,id uuid,(select jnum from t_jnum j where j.id=u.job_num)job_name,job_num,ico,nickname,neibu,type,weiyibiaoshi,token,continuity_sign,total,uid,telephone,sex,brithdate,password,wxOpenId,qqOpenId,alipayOpenId,sinaOpenId,huanxinAccount,huanxinPassword,invitationCode,IdAuthentified,NaAuthentified,realName,isrenzhen,IDNo,bankcardNo,DATE_FORMAT(createtime,'%Y-%m-%d')createtime,isdel,(select sum(if(type=0,-1*num,num)) from t_score a where a.uid=u.id and a.isdel=0)scorecount from t_user u where 1=1 "+sqlwhere+" order by u.id desc limit "+start+","+end;
		return this.baseJdbcDao.queryListMap(sql);
	}

	@Override
	public int count(String title, String isdel,String uid,String type,String neibu,String uuid,String isrenzhen,String rz,String realname) {
		String sqlwhere="";
		if(DataTypeUtil.validate(title)){
			sqlwhere=sqlwhere+" and telephone like '%"+title+"%'";
		}
		if(DataTypeUtil.validate(realname)){
			sqlwhere=sqlwhere+" and realName like '%"+realname+"%'";
		}
		if(DataTypeUtil.validate(isdel)){
			sqlwhere=sqlwhere+" and isdel="+isdel;
		}else{
			sqlwhere=sqlwhere+" and isdel=0";
		}
		if(DataTypeUtil.validate(uid)){
			sqlwhere=sqlwhere+" and id='"+uid+"'";
		}
//		if("0".equals(type)){
//			sqlwhere=sqlwhere+" and type<>'1'";
//		}else{
//			sqlwhere=sqlwhere+" and type='1'";
//		}
		if(DataTypeUtil.validate(uuid)){
			sqlwhere=sqlwhere+" and uid='"+uuid+"'";
		}else{
			if("0".equals(type)){
				sqlwhere=sqlwhere+" and type<>'1'";
			}else{
				sqlwhere=sqlwhere+" and type='1'";
			}
		}
		
		
			
		
		if(DataTypeUtil.validate(neibu)){
			sqlwhere=sqlwhere+" and neibu='"+neibu+"'";
		}		
		if("19940119".equals(rz)){
			if(DataTypeUtil.validate(isrenzhen)){
				sqlwhere=sqlwhere+" and isrenzhen='"+isrenzhen+"'";
			}else{
				sqlwhere=sqlwhere+" and isrenzhen='1'";
			}
		}
		
		String sql="select count(1) from t_user where 1=1 "+sqlwhere;
		return this.baseJdbcDao.getCount(sql);
	}

//	@Override
//	public String save(Source source) {		
//		String sql="insert into t_user(title,isdel) values("
//				
//				+ "'"+source.getTitle()+"',"	
//				+ "'"+source.getIsdel()+"')";
//		this.baseJdbcDao.insert(sql);
//		
//		return "success";}

	@Override
	public String delete(String id) {		
		String sql="update t_user set isdel='1' where id="+id;
		this.baseJdbcDao.delete(sql);
		return "success";
	}

	@Override
	public List<Map<String, Object>> findById(String id) {
		String sql="select * from t_user where id='"+id+"'";
		List<Map<String, Object>> result = baseJdbcDao.queryListMap(sql);
		return result;
	}
	/**
	 * 计算今天有几人注册
	 */
	@Override
	public int count() {
		String sql="select count(*) from t_user where date_format(createtime, '%Y%m%d')=date_format(NOW(), '%Y%m%d')";
		return this.baseJdbcDao.getCount(sql);
	}
	/**
	 * 计算有多少人注册
	 */
	@Override
	public int counts() {
		String sql="select count(*) from t_user where isdel='0'";
		return this.baseJdbcDao.getCount(sql);
	}
	/**
	 * 姓名认证
	 */
	@Override
	public String updateName(User user) {
		String sql="update t_user set NaAuthentified='"+user.getNaAuthentified()+"',IdAuthentified='"+user.getIdAuthentified()+"' where id="+user.getId();
		this.baseJdbcDao.delete(sql);
		return "success";
	}

	@Override
	public String updateYhkh(User user) {
		String sql="update t_user set BankAuthentified='"+user.getBankAuthentified()+"' where id="+user.getId();
		this.baseJdbcDao.delete(sql);
		return "success";
	}

	
	@Override
	public String gw(User user,String job_yname) {	
		//判断输入工单是否正确
		if(!(this.baseJdbcDao.getCount("SELECT count(id) from t_jnum where  jnum='"+user.getJob_num()+"'") > 0)){
			return "输入工单格式不正确！";
		}
		//判断输入工单和工单类别是否匹配
		if(!(this.baseJdbcDao.getCount("SELECT count(id) from t_jnum where status='"+user.getNeibu()+"' and jnum='"+user.getJob_num()+"'") > 0)){
			return "输入工单与类别不匹配！";
		}
		
		if(!job_yname.equals(user.getJob_num())){
			
			if(!(this.baseJdbcDao.getCount("SELECT count(id) from t_jnum where type='0' and jnum='"+user.getJob_num()+"'") > 0)){
				return "输入工单已被使用！";
			}
			
		}else{
			return "success";
		}
		
		//新
		String sql="update t_jnum set type='1' where jnum='"+user.getJob_num()+"'";
		//旧
		String sql2="update t_jnum set type='0' where jnum='"+job_yname+"'";
		//改变
		String sql3="update t_user set neibu='"+user.getNeibu()+"' , job_num=(select id from t_jnum where jnum='"+user.getJob_num()+"' ) where id='"+user.getId()+"'";
		this.baseJdbcDao.update(sql);
		this.baseJdbcDao.update(sql2);
		this.baseJdbcDao.update(sql3);
		
		return "success";
	}

	@Override
	public String save(User user) {
		//判断认证是否通过，通过赋予顾问工号和确认顾问的性质；除了通过的其他状态，不赋予顾问的工号和性质为
		if("3".equals(user.getIsrenzhen())){
			if("0".equals(user.getNeibu())){
				user.setJob_num("( select id from t_jnum where status='0' and type='0'  LIMIT 1 )");
			}
			if("1".equals(user.getNeibu())){
				user.setJob_num("( select id from t_jnum where status='1' and type='0'  LIMIT 1 )");
			}		
			String sql2="update t_jnum set type='1' where id=(select job_num from t_user where type='1' and neibu='"+user.getNeibu()+"' and sex='"+user.getSex()+"' and telephone='"+user.getTelephone()+"' and isdel='0' order by id desc limit 1)";
			String sql="insert into t_user(job_num,isrenzhen,IdPiczheng,IdPicfan,shouchiId,neibu,type,sex,brithdate,IdNo,telephone,realName) value("+user.getJob_num()+",'"+user.getIsrenzhen()+"','"+user.getIdPiczheng()+"','"+user.getIdPicfan()+"','"+user.getShouchiId()+"','"+user.getNeibu()+"','1','"+user.getSex()+"','"+user.getBirthdate()+"','"+user.getIdNo()+"','"+user.getTelephone()+"','"+user.getRealName()+"')";
			this.baseJdbcDao.insert(sql);
			this.baseJdbcDao.update(sql2);
		}else{
			String sql="insert into t_user(isrenzhen,IdPiczheng,IdPicfan,shouchiId,type,sex,brithdate,IdNo,telephone,realName) value('"+user.getIsrenzhen()+"','"+user.getIdPiczheng()+"','"+user.getIdPicfan()+"','"+user.getShouchiId()+"','1','"+user.getSex()+"','"+user.getBirthdate()+"','"+user.getIdNo()+"','"+user.getTelephone()+"','"+user.getRealName()+"')";
			this.baseJdbcDao.insert(sql);
			
		}
		
		return "success";
	}

	@Override
	public List<Map<String, Object>> findByIdrz(String id) {
		String sql="select * from t_user where id='"+id+"'";
		List<Map<String, Object>> result = baseJdbcDao.queryListMap(sql);
		return result;
	}


	@Override
	public String updaterz(User user) {
		if("2".equals(user.getIsrenzhen())){
			String sql="update t_user set  isrenzhen='"+user.getIsrenzhen()+"' where id="+user.getId();
			this.baseJdbcDao.delete(sql);
		}
		if("3".equals(user.getIsrenzhen())){			
			String sql="update t_user set neibu='"+user.getNeibu()+"', isrenzhen='"+user.getIsrenzhen()+"' ,job_num=(select id from t_jnum where type='0' and status='"+user.getNeibu()+"' limit 1 ) where id="+user.getId();
			this.baseJdbcDao.delete(sql);
			//首先占领工号表中的工号
			String sqlzl="update t_jnum set type='1' where id=(select job_num from t_user where id='"+user.getId()+"')";
			this.baseJdbcDao.delete(sqlzl);
		}
		return "success";
	}
	
	/**
	 * 查询今天注册的用户
	 */
	@Override
	public List<Map<String, Object>> selectNewUsers(int page, int pageSize,
			String phone, String name) {
		int start = (page-1)*pageSize;
		int end = pageSize;
		StringBuffer sb = new StringBuffer();
		sb.append(" select u.id,u.realName,u.telephone,u.neibu,u.type,u.sex,u.nickname,u.isrenzhen,u.IdNo,u.brithdate,u.createtime,(SELECT COUNT(1) FROM t_subscrit S WHERE s.phone=u.telephone) as issub from t_user u where TO_DAYS(u.createtime)=TO_DAYS(NOW()) and u.type=0 ");
		if(!"".equals(phone)&&null!=phone){
			sb.append(" and u.telephone like '"+phone+"%'");
		}
		if(!"".equals(name)&&null!=name){
			sb.append(" and u.realName like '%"+name+"%'");
		}
		sb.append(" order by u.createtime desc limit "+start+","+end);
		return this.baseJdbcDao.queryListMap(sb.toString());
	}

	@Override
	public int getNewUsersCount(String phone, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(1) from t_user u where TO_DAYS(u.createtime)=TO_DAYS(NOW()) and u.type=0");
		if(!"".equals(phone)&&null!=phone){
			sb.append(" and u.telephone like '"+phone+"%'");
		}
		if(!"".equals(name)&&null!=name){
			sb.append(" and u.realName like '%"+name+"%'");
		}
		return this.baseJdbcDao.getCount(sb.toString());
	}

}
