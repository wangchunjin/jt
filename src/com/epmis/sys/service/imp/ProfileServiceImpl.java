package com.epmis.sys.service.imp;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.ProfileService;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.vo.CmProfile;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("profileService")
public class ProfileServiceImpl
  implements ProfileService
{

  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;

  public List SysProfileTable(String profType)
  {
    String sql = "";
    if (profType.equals("1")) {
      sql = "SELECT * FROM ( SELECT  REMARK,PROF_TYPE,PROFILE_ID,PROFILE_NAME,(CASE ISDEFAULT WHEN '1' THEN '��' ELSE '' END) AS ISDEFAULT FROM CM_PROFILE ORDER BY PROFILE_NAME) Z WHERE profile_id!='1' and prof_type='1' ORDER BY PROFILE_NAME";
      return this.baseJdbcDao.queryListMap(sql);
    }
    sql = "SELECT * FROM ( SELECT REMARK,PROF_TYPE,PROFILE_ID,PROFILE_NAME,(CASE ISDEFAULT WHEN '1' THEN '��' ELSE '' END) AS ISDEFAULT FROM CM_PROFILE  ORDER BY PROFILE_NAME) Z WHERE profile_id!='1' and prof_type='0' ORDER BY PROFILE_NAME";

    List<Map<String, Object>> list = this.baseJdbcDao.queryListMap(sql);
    if (DataTypeUtil.validate(list)) {
      for (Map<String,Object> map : list) {
        String actual_name = "";
        String PROFILE_ID = DataTypeUtil.formatDbColumn(map.get("PROFILE_ID"));
        sql = " SELECT ACTUAL_NAME FROM CM_USERS A WHERE PROFILE_ID='" + PROFILE_ID + "'";
        List<Map<String, Object>> listuser = this.baseJdbcDao.queryListMap(sql);
        if (DataTypeUtil.validate(listuser)) {
          for (Map<String,Object> mapuser : listuser) {
            actual_name = DataTypeUtil.validate(actual_name) ? actual_name + "、" + DataTypeUtil.formatDbColumn(mapuser.get("ACTUAL_NAME")) : DataTypeUtil.formatDbColumn(mapuser.get("ACTUAL_NAME"));
          }
        }
        map.put("USER_NAME", actual_name);
      }
    }
    return list;
  }

  public Map<String, Object> GetProfileInfoById(String profileId)
  {
    return this.baseJdbcDao.queryMap("select * from cm_profile where profile_Id ='" + profileId + "'");
  }

  public String AddProfile(CmProfile cmProfile)
  {
    String sql = " INSERT INTO CM_PROFILE(WID,PROFILE_ID,PROFILE_NAME,PROF_TYPE,REMARK,ISDEFAULT) VALUES ('" + 
      Guid.getGuid() + "','" + Guid.getGuid() + "','" + cmProfile.getProfileName() + "','" + cmProfile.getProfType() + "','" + cmProfile.getRemark() + "','0')";
    this.baseJdbcDao.insert(sql);

    return "success";
  }

  public String DelProfile(String profileId, String profType)
  {
    if (profType.equals("0")) {
      String sql = "DELETE FROM CM_PROFCTR WHERE PROFILE_ID='" + profileId + "'";
      this.baseJdbcDao.delete(sql);
      sql = " UPDATE CM_USERS SET PROFILE_ID=NULL WHERE PROFILE_ID='" + profileId + "'";
      this.baseJdbcDao.update(sql);
      sql = "DELETE FROM CM_PROFILE WHERE PROF_TYPE='0' AND PROFILE_ID='" + profileId + "'";
      this.baseJdbcDao.delete(sql);
      return "success";
    }
    String sql = "DELETE FROM CM_PROFCTR WHERE PROFILE_ID='" + profileId + "'";
    this.baseJdbcDao.delete(sql);
    sql = "UPDATE CM_USERPROF SET PROFILE_ID=NULL WHERE PROFILE_ID='" + profileId + "'";
    this.baseJdbcDao.update(sql);
    sql = "DELETE FROM CM_PROFILE WHERE PROF_TYPE='1' AND PROFILE_ID='" + profileId + "'";
    this.baseJdbcDao.delete(sql);

    return "success";
  }

  public String UpdateProfile(CmProfile cmProfile)
  {
    String sql = "update cm_Profile set Profile_name='" + cmProfile.getProfileName() + "', remark='" + cmProfile.getRemark() + "'  where  Profile_id ='" + cmProfile.getProfileId() + "'";
    this.baseJdbcDao.update(sql);
    if ((DataTypeUtil.validate(cmProfile.getIsdefault())) && (cmProfile.getIsdefault().equals("1"))) {
      sql = "update cm_Profile set  Isdefault = '0' where prof_type = '" + cmProfile.getProfType() + "'";
      this.baseJdbcDao.update(sql);
      sql = "update cm_Profile set  Isdefault = '1' where Profile_id ='" + cmProfile.getProfileId() + "'";
      this.baseJdbcDao.update(sql);
    }
    return "success";
  }

  public List DatactrListTable(String pROFILEID, String ProF_type)
  {
    String sql = "SELECT * FROM ( SELECT * FROM ( SELECT A.WID, B.PROFILE_ID, A.DATACTR_TYPE, A.DATACTR_CODE, A.DATACTR_NAME,  A.ENABLED,   A.REMARK,  A.SUBSYS_CODE, A.MODULE_CODE, D.MODULE_NAME MNAME, C.MODULE_NAME AS SUB_NAME, D.SEQ_NUM, CASE WHEN B.PROFILE_ID IS NULL THEN '0' ELSE '1' END AS ENABLED_FLAG  FROM CM_DATACTR A   LEFT OUTER JOIN CM_PROFCTR B ON B.DATACTR_CODE = A.DATACTR_CODE AND B.PROFILE_ID = '" + 
      pROFILEID + "' " + 
      " LEFT OUTER JOIN CM_MODULE C ON C.MODULE_CODE = A.SUBSYS_CODE " + 
      " LEFT OUTER JOIN CM_MODULE D ON D.MODULE_CODE = A.MODULE_CODE " + 
      " LEFT OUTER JOIN CM_MODULE E ON D.PARENT_MODULE_CODE = E.MODULE_CODE " + 
      " WHERE A.ENABLED='1' AND C.ENABLED='1' AND D.ENABLED='1' AND E.ENABLED='1' " + 
      " ) TT  " + 
      "WHERE DATACTR_TYPE='" + ProF_type + "' ORDER BY SUB_NAME,SEQ_NUM,DATACTR_NAME) Z ORDER BY SUBSYS_CODE,SEQ_NUM";

    return this.baseJdbcDao.queryListMap(sql);
  }

  public String DeleteProfctr(String profileId)
  {
    String sql = "delete from CM_PROFCTR where PROFILE_ID = '" + profileId + "'";
    this.baseJdbcDao.delete(sql);
    return "success";
  }

  public String InsertProfctr(String profileId, String datactrCodes)
  {
    String sql = "INSERT INTO CM_PROFCTR(WID,PROFCTR_ID,PROFILE_ID,DATACTR_CODE) SELECT UUID(),UUID(),'" + profileId + "',A.DATACTR_CODE FROM (SELECT distinct DATACTR_CODE from cm_DATACTR where DATACTR_CODE in ('" + datactrCodes.replaceAll(",", "','") + "')) A";
    this.baseJdbcDao.insert(sql);
    return "success";
  }

  public List ProfileUserTable(String profileId)
  {
    String sql = "SELECT * FROM CM_USERS WHERE PROFILE_ID= '" + profileId + "'";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public List AddUserTable(String profileId)
  {
    String sql = "SELECT WID,USER_NAME,ACTUAL_NAME,USER_ID FROM CM_USERS WHERE  USER_ID NOT IN (SELECT B.USER_ID FROM CM_USERS B WHERE B.DISABLE_FLAG = 1) AND (PROFILE_ID!='" + profileId + "' OR PROFILE_ID IS NULL  OR PROFILE_ID ='' )   ORDER BY USER_NAME";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public String AddProfileUser(String userIds, String profileId)
  {
    String sql = "update cm_users set profile_id= '" + profileId + "' where user_id in ('" + userIds.replaceAll(",", "','") + "')";
    this.baseJdbcDao.update(sql);
    return "success";
  }

  public String DelProfileUser(String userIds)
  {
    String sql = "update cm_users set profile_id = NULL where user_id in ('" + userIds.replaceAll(",", "','") + "')";
    this.baseJdbcDao.update(sql);
    return "success";
  }

  public String GetUserNameByProfileId(String profileId)
  {
    String actual_name = "";
    String sql = " SELECT ACTUAL_NAME FROM CM_USERS A WHERE PROFILE_ID='" + profileId + "'";
    List<Map<String, Object>> listuser = this.baseJdbcDao.queryListMap(sql);
    if (DataTypeUtil.validate(listuser)) {
      for (Map<String,Object> mapuser : listuser) {
        actual_name = DataTypeUtil.validate(actual_name) ? actual_name + "、" + DataTypeUtil.formatDbColumn(mapuser.get("ACTUAL_NAME")) : DataTypeUtil.formatDbColumn(mapuser.get("ACTUAL_NAME"));
      }
    }
    return actual_name;
  }

  public String CopyProfile(String profileId, String profType)
  {
    String NewprofileId = Guid.getGuid();
    String sql = " INSERT INTO CM_PROFILE(WID,PROFILE_ID,PROFILE_NAME,PROF_TYPE) select '" + Guid.getGuid() + "','" + NewprofileId + "',concat(PROFILE_NAME,'(1)') ,PROF_TYPE from CM_PROFILE where profile_id = '" + profileId + "' ";
    this.baseJdbcDao.insert(sql);
    sql = "INSERT INTO CM_PROFCTR(WID,PROFCTR_ID,PROFILE_ID,DATACTR_CODE)select UUID(),UUID() ,'" + NewprofileId + "',DATACTR_CODE from CM_PROFCTR  where profile_id = '" + profileId + "' ";
    this.baseJdbcDao.insert(sql);
    return "success";
  }
}