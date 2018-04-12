package com.epmis.user.service;

import java.util.List;
import java.util.Map;


import com.epmis.user.vo.User;

public interface UserService {
	/**
	 * 查询所有用户
	 * @param title
	 * @param page
	 * @param rows
	 * @return
	 */
	public abstract List<Map<String, Object>> findAllUser(String title,String isdel,String uid,String type,String neibu,String uuid,String isrenzhen,String rz,String realname,int page,int rows);
	
	/**
	 * 查询所有用户总数
	 * @param title
	 * @return
	 */
	public abstract int count(String title,String isdel,String uid,String type,String neibu,String uuid,String isrenzhen,String rz,String realname);
	/**
	 * 保存
	 * @param brand
	 */
	public abstract String save(User user);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public abstract String delete(String id);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public abstract List<Map<String, Object>> findById(String id);
	
	/**
	 * 修改
	 * @param brand
	 * @return
	 */
//	public abstract String update(Source source);
	
	/**
	 * 计算今天注册的人数
	 * @return
	 */
	public abstract int count();
	/**
	 * 计算所有注册的人数
	 * @return
	 */
	public abstract int counts();
	/**
	 * 姓名认证
	 * @return
	 */
	public abstract String updateName(User user);
	
	/**
	 * 身份证号认证
	 * @return
	 */
	public abstract String updateYhkh(User user);
	
	
	public abstract String gw(User user,String job_yname);
	
	
	/**
	 * 查询要认证的金牌顾问
	 * @param id
	 * @return
	 */
	public abstract List<Map<String, Object>> findByIdrz(String id);
	

	/**
	 * 金牌顾问认证
	 * @return
	 */
	public abstract String updaterz(User user);

	//查询今天注册的用户
		public List<Map<String,Object>> selectNewUsers(int page,int pageSize,String phone,String name);
		public int getNewUsersCount(String phone,String name);

}
