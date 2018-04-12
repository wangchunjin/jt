package com.epmis.acmuser.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epmis.acmuser.service.CmuserService;
import com.epmis.acmuser.vo.Cmuser;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.BaseJdbcService;
import com.epmis.sys.util.DataTypeUtil;

import java.util.UUID;

@Transactional
@Service("CmuserService")
public class CmuserServiceImpl implements CmuserService{
	@Autowired
	@Qualifier("baseJdbcDao")
	private BaseJdbcDao baseJdbcDao;
	@Autowired
	  private BaseJdbcService baseJdbcService;
	
	public String AddUser(Cmuser cmUsers, String roleId)
	  {
			
	    if ((DataTypeUtil.validate(cmUsers.getUserName())) && (DataTypeUtil.validate(cmUsers.getActualName()))) {
	      if (this.baseJdbcDao.getCount("select count(wid) from cm_users where user_name ='" + cmUsers.getUserName() + "'") > 0) {
	        return "该用户名已存在！";
	      }
	      
	      String sqlwhere1="";
			if(cmUsers.getUserpic()==null || cmUsers.getUserpic()==""){
				sqlwhere1=" '/upload/jiazai4.png' , ";
				
			}else{
				sqlwhere1= "'"+cmUsers.getUserpic()+"', ";
			}
	      
	      String userId = cmUsers.getUserId();
	      String sql = "SELECT PROFILE_id FROM CM_PROFILE where prof_type = '0' and isdefault='1'";
	      String profile_id = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
	      sql = " INSERT INTO CM_USERS(profile_id,WID,USER_NAME,USER_PIC,ACTUAL_NAME,SEX,BIRTHDAY,TEL_NO_DEPT,FAX_NO_DEPT,ADD_HOME,POST_NO_HOME,TEL_NO_HOME,MOBIL_NO,EMAIL,OICQ_NO,LOW_FRACTION,HIGH_FRACTION,MSN,USER_ID,subsidiary) VALUES ('" + 
	        profile_id + "','" + this.getGuid() + "','" + cmUsers.getUserName() + "',"+sqlwhere1+"'" + cmUsers.getActualName() + "','" + cmUsers.getSex() + "'," + (DataTypeUtil.validate(cmUsers.getBirthday()) ? "'" + cmUsers.getBirthday() + "'" : null) + "," + 
	        "'" + cmUsers.getTelNoDept() + "','" + cmUsers.getFaxNoDept() + "','" + cmUsers.getAddHome() + "','" + cmUsers.getPostNoHome() + "','" + cmUsers.getTelNoHome() + "','" + cmUsers.getMobilNo() + "','" + cmUsers.getEmail() + "','" + cmUsers.getOicqNo() + "'," + cmUsers.getLowFraction() + "," + cmUsers.getHighFraction() + ",'" + cmUsers.getMsn() + "','" + userId + "','"+cmUsers.getSubsidiary()+"')";
	      this.baseJdbcDao.insert(sql);
	      if (DataTypeUtil.validate(roleId)) {
	        sql = "insert into cm_rluser(wid,rluser_id,user_id,role_id ,ar_flag)values('" + this.getGuid() + "','" + this.getGuid() + "','" + userId + "','" + roleId + "','N')";
	        this.baseJdbcDao.insert(sql);
	      }
	      return "success";
	    }
	    return "用户名、姓名任一不能为空！";
	  }
	
	
	
	public String UpdateUser(Cmuser cmUsers)
	  {
	    if (this.baseJdbcDao.getCount("select count(wid) from cm_users where user_name ='" + cmUsers.getUserName() + "' and user_id !='" + cmUsers.getUserId() + "'") > 0) {
	      return "该用户名已存在！";
	    }
	    String sqlwhere="";
		if(cmUsers.getUserpic()==null||cmUsers.getUserpic()==""){
			sqlwhere="";
		}else{
			sqlwhere=" USER_PIC='"+cmUsers.getUserpic()+"' , ";
		}
	    
	    
	    String sql = "update cm_users set user_name='" + cmUsers.getUserName() + "', actual_name ='" + cmUsers.getActualName() + "',DISABLE_FLAG='" + (DataTypeUtil.validate(cmUsers.getDisableFlag()) ? cmUsers.getDisableFlag() : "0") + "', sex='" + cmUsers.getSex() + "'," + 
	      " LOW_FRACTION=" + cmUsers.getLowFraction() + ", "+sqlwhere+" HIGH_FRACTION =" + cmUsers.getHighFraction() + ",birthday =" + (DataTypeUtil.validate(cmUsers.getBirthday()) ? "'" + cmUsers.getBirthday() + "'" : null) + ",password='" + cmUsers.getPassword() + "' where  user_id ='" + cmUsers.getUserId() + "'";
	    this.baseJdbcDao.update(sql);
	    return "success";
	  }
	  
	  public String UpdateUserOther(Cmuser cmUsers) {
	    String sql = "update cm_users set TEL_NO_DEPT='" + cmUsers.getTelNoDept() + "', TEL_NO_HOME ='" + cmUsers.getTelNoHome() + "',FAX_NO_DEPT='" + cmUsers.getFaxNoDept() + "', ADD_HOME='" + cmUsers.getAddHome() + "'," + 
	      " MOBIL_NO='" + cmUsers.getMobilNo() + "',POST_NO_HOME ='" + cmUsers.getPostNoHome() + "',EMAIL ='" + cmUsers.getEmail() + "',OICQ_NO='" + cmUsers.getOicqNo() + "',EMAIL_PASSWORD='" + cmUsers.getEmailPassword() + "',MSN='" + cmUsers.getMsn() + "',RTX_NAME='" + cmUsers.getRtxName() + "' where  user_id ='" + cmUsers.getUserId() + "'";
	    this.baseJdbcDao.update(sql);
	    return "success";
	  }
	
	
	
	
	
	
	public static String getGuid()
	  {
	    StringBuffer localStringBuffer = new StringBuffer();
	    UUID localUUID = UUID.randomUUID();
	    localStringBuffer.append(localUUID.toString().replaceAll("-", "").toUpperCase());
	    return localStringBuffer.toString();
	  }

}
