package com.epmis.sys.service.imp;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.BaseJdbcService;
import com.epmis.sys.service.UserService;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.SqlUtil;
import com.epmis.sys.vo.CmUsers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("userService")
public class UserServiceImpl
  implements UserService
{

  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;

  @Autowired
  private BaseJdbcService baseJdbcService;

  public String GetUserNameByRoleId(String roleId)
  {
    String sql = " SELECT USER_ID,USER_NAME,ACTUAL_NAME FROM CM_USERS WHERE USER_ID IN( SELECT USER_ID FROM CM_RLUSER WHERE ROLE_ID='" + roleId + "')";
    String actualName = "";
    List<Map<String,Object>> list = this.baseJdbcDao.queryListMap(sql);
    if (DataTypeUtil.validate(list)) {
      for (Map<String,Object> map : list) {
        actualName = DataTypeUtil.validate(actualName) ? actualName + "、" + DataTypeUtil.formatDbColumn(map.get("ACTUAL_NAME")) : DataTypeUtil.formatDbColumn(map.get("ACTUAL_NAME"));
      }
    }
    return actualName;
  }

  public List SysUserTable(String where, String key,String cuid,String issub)
  {
	  String str="";
	if("0".equals(issub)){
		
	}else{
		str=str+" and subsidiary='"+cuid+"'";
	}
	
    if (DataTypeUtil.validate(where)) {
      where = " AND C.DISABLE_FLAG ='" + where + "'";
    }
    if (DataTypeUtil.validate(key)) {
      where = where + " AND (C.USER_NAME LIKE '%" + key + "%' OR C.ACTUAL_NAME LIKE '%" + key + "%')";
    }
    String sql = "select T1.*,T2.ROLE_NAME from ( select C.WID,C.USER_PIC, C.USER_ID, C.USER_NAME,C.ENSEMBLE, C.ACTUAL_NAME, C.DISABLED, C.PROFILE_ID,(SELECT PROFILE_NAME FROM CM_PROFILE WHERE PROFILE_ID =C.PROFILE_ID ) PROFILE_NAME from CM_USERS C WHERE 1=1 "+str + where + " ORDER BY USER_NAME ASC ) T1 left join (SELECT user_id,role_name FROM (" + 
      "SELECT T.USER_ID,GROUP_CONCAT(T.ROLE_NAME)  ROLE_NAME FROM " + 
      "(SELECT USER_ID,ROLE_NAME  FROM CM_RLUSER C LEFT JOIN CM_ROLE R  ON C.ROLE_ID = R.ROLE_ID ) T " + 
      " GROUP BY t.user_id) tt) T2 ON T1.user_id = T2.user_id ORDER BY USER_NAME ASC";
    List listMap = this.baseJdbcDao.queryListMap(sql);
    return listMap;
  }

  public List SysUserTableByRoleId(String roleId)
  {
    if (DataTypeUtil.validate(roleId)) {
      roleId = roleId + this.baseJdbcService.GetChildIdsByParentId("cm_role", "parent_role_id", "role_id", roleId);
      String sql = "select C.WID, C.USER_ID,Case C.DISABLE_FLAG when '1' then '√' else '' END AS DISABLE_FLAG ,C.USER_NAME, C.ACTUAL_NAME, C.DISABLED, C.PROFILE_ID,(SELECT PROFILE_NAME FROM CM_PROFILE WHERE PROFILE_ID =C.PROFILE_ID ) PROFILE_NAME from CM_USERS C WHERE C.USER_ID IN (SELECT USER_ID FROM cm_rluser WHERE ROLe_ID IN ('" + roleId.replaceAll(",", "','") + "'))  ORDER BY USER_NAME ASC";
      return this.baseJdbcDao.queryListMap(sql);
    }
    return null;
  }

  public Map<String, Object> GetUserInfoById(String UserId)
  {
    return this.baseJdbcDao.queryMap("select * from cm_users where user_id ='" + UserId + "'");
  }

  public String AddUser(CmUsers cmUsers, String roleId)
  {
    if ((DataTypeUtil.validate(cmUsers.getUserName())) && (DataTypeUtil.validate(cmUsers.getActualName()))) {
      if (this.baseJdbcDao.getCount("select count(wid) from cm_users where user_name ='" + cmUsers.getUserName() + "'") > 0) {
        return "该用户名已存在！";
      }
      String userId = cmUsers.getUserId();
      String sql = "SELECT PROFILE_id FROM CM_PROFILE where prof_type = '0' and isdefault='1'";
      String profile_id = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
      sql = " INSERT INTO CM_USERS(profile_id,WID,USER_NAME,ACTUAL_NAME,SEX,BIRTHDAY,TEL_NO_DEPT,FAX_NO_DEPT,ADD_HOME,POST_NO_HOME,TEL_NO_HOME,MOBIL_NO,EMAIL,OICQ_NO,LOW_FRACTION,HIGH_FRACTION,MSN,USER_ID) VALUES ('" + 
        profile_id + "','" + Guid.getGuid() + "','" + cmUsers.getUserName() + "','" + cmUsers.getActualName() + "','" + cmUsers.getSex() + "'," + (DataTypeUtil.validate(cmUsers.getBirthday()) ? "'" + cmUsers.getBirthday() + "'" : null) + "," + 
        "'" + cmUsers.getTelNoDept() + "','" + cmUsers.getFaxNoDept() + "','" + cmUsers.getAddHome() + "','" + cmUsers.getPostNoHome() + "','" + cmUsers.getTelNoHome() + "','" + cmUsers.getMobilNo() + "','" + cmUsers.getEmail() + "','" + cmUsers.getOicqNo() + "'," + cmUsers.getLowFraction() + "," + cmUsers.getHighFraction() + ",'" + cmUsers.getMsn() + "','" + userId + "')";
      this.baseJdbcDao.insert(sql);
      if (DataTypeUtil.validate(roleId)) {
        sql = "insert into cm_rluser(wid,rluser_id,user_id,role_id ,ar_flag)values('" + Guid.getGuid() + "','" + Guid.getGuid() + "','" + userId + "','" + roleId + "','N')";
        this.baseJdbcDao.insert(sql);
      }
      return "success";
    }
    return "用户名、姓名任一不能为空！";
  }

  public String DelUser(String UserId)
  {
    ArrayList localArrayList = new ArrayList();

//    localArrayList.add("DELETE FROM SM_PLANUSER WHERE USER_ID='" + UserId + "'");
    localArrayList.add("DELETE FROM CM_RLUSER WHERE USER_ID='" + UserId + "'");
    localArrayList.add("DELETE FROM CM_USERMDL WHERE USER_ID='" + UserId + "'");
    localArrayList.add("DELETE FROM CM_USERPROJ WHERE USER_ID='" + UserId + "'");
    localArrayList.add("DELETE FROM CM_USERPROF WHERE USER_ID='" + UserId + "'");
    localArrayList.add("DELETE FROM CM_USERS WHERE USER_ID='" + UserId + "'");
    for (int i = 0; i < localArrayList.size(); i++) {
      this.baseJdbcDao.delete((String)localArrayList.get(i));
    }
    return "success";
  }

  public String UpdateUser(CmUsers cmUsers)
  {
    if (this.baseJdbcDao.getCount("select count(wid) from cm_users where user_name ='" + cmUsers.getUserName() + "' and user_id !='" + cmUsers.getUserId() + "'") > 0) {
      return "该用户名已存在！";
    }
    String sql = "update cm_users set user_name='" + cmUsers.getUserName() + "', actual_name ='" + cmUsers.getActualName() + "',DISABLE_FLAG='" + (DataTypeUtil.validate(cmUsers.getDisableFlag()) ? cmUsers.getDisableFlag() : "0") + "', sex='" + cmUsers.getSex() + "'," + 
      " LOW_FRACTION=" + cmUsers.getLowFraction() + ",HIGH_FRACTION =" + cmUsers.getHighFraction() + ",birthday =" + (DataTypeUtil.validate(cmUsers.getBirthday()) ? "'" + cmUsers.getBirthday() + "'" : null) + ",password='" + cmUsers.getPassword() + "' where  user_id ='" + cmUsers.getUserId() + "'";
    this.baseJdbcDao.update(sql);
    return "success";
  }

  public String UpdateUserOther(CmUsers cmUsers) {
    String sql = "update cm_users set TEL_NO_DEPT='" + cmUsers.getTelNoDept() + "', TEL_NO_HOME ='" + cmUsers.getTelNoHome() + "',FAX_NO_DEPT='" + cmUsers.getFaxNoDept() + "', ADD_HOME='" + cmUsers.getAddHome() + "'," + 
      " MOBIL_NO='" + cmUsers.getMobilNo() + "',POST_NO_HOME ='" + cmUsers.getPostNoHome() + "',EMAIL ='" + cmUsers.getEmail() + "',OICQ_NO='" + cmUsers.getOicqNo() + "',EMAIL_PASSWORD='" + cmUsers.getEmailPassword() + "',MSN='" + cmUsers.getMsn() + "',RTX_NAME='" + cmUsers.getRtxName() + "' where  user_id ='" + cmUsers.getUserId() + "'";
    this.baseJdbcDao.update(sql);
    return "success";
  }

  public List RoleUserTable(String userId)
  {
    String sql = "  SELECT A.RLUSER_ID,B.ROLE_NAME  FROM CM_RLUSER A left JOIN CM_ROLE B ON A.ROLE_ID=B.ROLE_ID  WHERE A.USER_ID='" + userId + "'";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public List AddRoleUserTree(String parentId, String userId) {
    List<Map<String,Object>> items = findRoleTreeBySql(parentId, userId);
    for (Map<String,Object> item : items) {
      if (findRoleTreeBySql(item.get("ROLE_ID").toString(), userId).size() > 0) {
        item.put("state", "closed");
      }
      if (item.get("ROLE_TYPE").toString().equals("1"))
        item.put("iconCls", "icon-role");
      else {
        item.put("iconCls", "icon-obs");
      }
      if (Integer.valueOf(item.get("NUM").toString()).intValue() > 0)
        item.put("SELECT", "1");
      else {
        item.put("SELECT", "0");
      }
    }
    return items;
  }

  public List<Map<String, Object>> findRoleTreeBySql(String parentId, String userId) {
    List result = null;

    String sql = " SELECT PARENT_ROLE_ID,ROLE_ID,(SELECT COUNT(A.WID) FROM CM_RLUSER A WHERE A.ROLE_ID = C.ROLE_ID AND USER_ID ='" + userId + "' ) NUM, ROLE_NAME,REMARK,ROLE_TYPE FROM CM_ROLE C WHERE 1=1  AND PARENT_ROLE_ID = '" + parentId + "'  ORDER BY SEQ_NUM";
    result = this.baseJdbcDao.queryListMap(sql);
    return result;
  }

  public String AddRoleUser(String roleIds, String userId)
  {
    String sql = "";
    String[] roleIdList = roleIds.split(",");
    if (roleIdList.length > 0) {
      for (int i = 0; i < roleIdList.length; i++) {
        if (this.baseJdbcDao.getCount("select count(wid) from cm_rluser where user_id='" + userId + "' and role_id ='" + roleIdList[i] + "'  ") == 0) {
          sql = "insert into cm_rluser(wid,rluser_id,user_id,role_id ,ar_flag)values('" + Guid.getGuid() + "','" + Guid.getGuid() + "','" + userId + "','" + roleIdList[i] + "','N')";
          this.baseJdbcDao.insert(sql);
        }
      }
    }
    return "success";
  }

  public String DelRoleUser(String rluserIds)
  {
    String sql = "delete from cm_rluser where rluser_id in ('" + rluserIds.replaceAll(",", "','") + "')";
    this.baseJdbcDao.delete(sql);
    return "success";
  }

  public List ModuleLicenseUserTable(String userId)
  {
    List ModuleList = new ArrayList();
    String sql = "SELECT  A.MODULE_CODE,A.MODULE_NAME,A.SEQ_NUM,(select count(B.wid) from CM_USERMDL B where  B.MODULE_CODE = A.MODULE_CODE AND B.USER_ID = '" + userId + "'  ) AS USRE_FLAG,(select count(B.wid) from CM_USERMDL B where  B.MODULE_CODE = A.MODULE_CODE AND B.ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "')  ) AS ROLE_FLAG  FROM CM_MODULE A  WHERE A.MODULE_FLAG = '0' AND A.ENABLED = '1'   AND A.PARENT_MODULE_CODE ='0' ORDER BY A.SEQ_NUM ASC";
    List<Map<String,Object>> items = this.baseJdbcDao.queryListMap(sql);
    //String ModuleCode = this.baseJdbcService.getStringByLicense("ModuleCode");
   // String[] ModuleCodeList = ModuleCode.split(",");
    //Arrays.sort(ModuleCodeList);
//    if (DataTypeUtil.validate(items)) {
//      for (Map<String,Object> item : items) {
//        //if (Arrays.binarySearch(ModuleCodeList, item.get("MODULE_CODE").toString()) >= 0) {
//          ModuleList.add(item);
//        //}
//      }
//    }
    return items;
  }

  public String UpdateModuleLicenseUser(String userId, String moduleCodes)
  {
    String sql = "delete from cm_usermdl where user_id = '" + userId + "' and  MODULE_CODE in (select    A.MODULE_CODE from   CM_MODULE A  WHERE A.MODULE_FLAG = '0' AND A.ENABLED = '1'   AND A.PARENT_MODULE_CODE ='0')";
    this.baseJdbcDao.delete(sql);
    sql = "insert into cm_usermdl(wid,usermdl_id,user_id,role_id,module_code,user_flag,parent_module_code) (select UUID(),UUID(),'" + userId + "','0',module_code,'1',parent_module_code from cm_module where module_code in ('" + moduleCodes.replaceAll(",", "','") + "') and MODULE_FLAG = '0' AND ENABLED = '1'   AND  PARENT_MODULE_CODE ='0')";
    this.baseJdbcDao.insert(sql);
    return "success";
  }

  public String UpdateModuleAssignUser(String userId, String moduleCodes)
  {
    String sql = "delete from cm_usermdl where user_id = '" + userId + "' and  MODULE_CODE not in (select    A.MODULE_CODE from   CM_MODULE A  WHERE A.MODULE_FLAG = '0' AND A.ENABLED = '1'   AND A.PARENT_MODULE_CODE ='0')  AND MODULE_CODE IN (select MODULE_CODE from   CM_MODULE) AND PARENT_MODULE_CODE IN (select MODULE_CODE from   CM_MODULE)";
    this.baseJdbcDao.delete(sql);
    sql = "insert into cm_usermdl(wid,usermdl_id,user_id,role_id,module_code,user_flag,parent_module_code) (select UUID(),UUID(),'" + userId + "','0',module_code,'1',parent_module_code from cm_module where module_code in ('" + moduleCodes.replaceAll(",", "','") + "') and MODULE_FLAG = '1' AND ENABLED = '1')";
    this.baseJdbcDao.insert(sql);
    return "success";
  }

  public List ModuleAssignUserTree(String parentId, String userId)
  {
    List<Map<String,Object>> items = findModuleAssignTreeBySql(parentId, userId);
    for (Map<String,Object> item : items) {
      item.put("state", "closed");
      item.put("iconCls", "icon-module");
      String USRE_FLAG = "1";
      String ROLE_FLAG = "1";
      List<Map<String,Object>> childerItems = findModuleAssignTreeBySql(item.get("MODULE_CODE").toString(), userId);
      for (Map<String,Object> childerItem : childerItems) {
        childerItem.put("iconCls", "icon-list");
        childerItem.put("NODE_TYPE", "list");
        childerItem.put("PARENT", item.get("MODULE_CODE").toString());
        if (childerItem.get("ROLE_FLAG").toString().equals("0")) {
          ROLE_FLAG = "0";
        }
        if ((childerItem.get("USRE_FLAG").toString().equals("0")) && (childerItem.get("ROLE_FLAG").toString().equals("0"))) {
          USRE_FLAG = "0";
        }
      }
      item.put("PARENT", item.get("MODULE_CODE").toString() + "_PARENT");
      item.put("ROLE_FLAG", ROLE_FLAG);
      item.put("NODE_TYPE", "module");
      item.put("USRE_FLAG", USRE_FLAG);
      item.put("children", childerItems);
    }

    return items;
  }

  public List<Map<String, Object>> findModuleAssignTreeBySql(String parentId, String userId) {
    List result = null;
    String sql = "";
    if (parentId.equals("0"))
      sql = " SELECT MODULE_CODE,MODULE_NAME FROM CM_MODULE A WHERE A.PARENT_MODULE_CODE='0' AND A.ENABLED='1' AND A.MODULE_CODE IN(SELECT B.MODULE_CODE FROM  CM_USERMDL B WHERE B.USER_ID='" + userId + "' OR B.ROLE_ID IN (SELECT C.ROLE_ID FROM CM_RLUSER C WHERE C.USER_ID='" + userId + "')) ORDER BY A.SEQ_NUM";
    else {
      sql = " SELECT MODULE_CODE,MODULE_NAME,(SELECT COUNT(B.WID) FROM CM_USERMDL B WHERE  B.MODULE_CODE = A.MODULE_CODE AND B.USER_ID = '" + userId + "'  ) AS USRE_FLAG,(SELECT COUNT(B.WID) FROM CM_USERMDL B WHERE  B.MODULE_CODE = A.MODULE_CODE AND B.ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "')  ) AS ROLE_FLAG FROM CM_MODULE A WHERE A.PARENT_MODULE_CODE IN (SELECT MODULE_CODE FROM  CM_MODULE WHERE PARENT_MODULE_CODE = '" + parentId + "'  ) AND A.ENABLED='1' ORDER BY A.SEQ_NUM";
    }
    result = this.baseJdbcDao.queryListMap(sql);
    return result;
  }

  public List ProjUserTree(String parentId, String userId)
  {
    return findProjUserTreeBySql(parentId, userId);
  }

  public List<Map<String, Object>> findProjUserTreeBySql(String parentId, String userId) {
    String sql = "SELECT PROJ_ID,PARENT_PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,IS_ZJLR_FLAG,PROJ_CMPT_PCT,PROJ_NODE_FLAG,STATUS,SEQ_NUM,(SELECT PROFILE_ID FROM CM_USERPROF WHERE USER_ID='" + 
      userId + "' AND PROJ_ID = A.PROJ_ID) PROFILE_ID," + 
      "(SELECT PROFILE_NAME FROM CM_PROFILE WHERE PROFILE_ID =(SELECT PROFILE_ID FROM CM_USERPROF WHERE USER_ID='" + userId + "' AND PROJ_ID = A.PROJ_ID)) PROFILE_NAME," + 
      "(SELECT COUNT(PROJ_ID) FROM CM_PROJ WHERE PARENT_PROJ_ID=A.PROJ_ID) AS CHILD_COUNT," + 
      "(select count(B.wid) from CM_USERPROJ B where  B.PROJ_ID = A.PROJ_ID AND B.USER_ID = '" + userId + "' AND B.USER_FLAG ='1' ) AS USRE_FLAG," + 
      "(select count(B.wid) from CM_USERPROJ B where  B.PROJ_ID = A.PROJ_ID AND B.ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "') AND B.USER_FLAG ='0'   ) AS ROLE_FLAG " + 
      "FROM CM_PROJ A " + 
      "WHERE A.PARENT_PROJ_ID = '" + parentId + "' ORDER BY A.PROJ_NODE_FLAG DESC, A.SEQ_NUM";
    List<Map<String,Object>> result = this.baseJdbcDao.queryListMap(sql);
    if (DataTypeUtil.validate(result)) {
      for (Map<String,Object> Item : result) {
        if (!Item.get("CHILD_COUNT").toString().equals("0")) {
          Item.put("state", "closed");
          Item.put("children", findProjUserTreeBySql(Item.get("PROJ_ID").toString(), userId));
        }
        if (Item.get("PROJ_NODE_FLAG").toString().equals("Y"))
          Item.put("iconCls", "icon-proj");
        else {
          Item.put("iconCls", "icon-eps");
        }
      }
    }
    return result;
  }

  public String InsertProjUser(String userId, String projIds)
  {
    if (DataTypeUtil.validate(projIds)) {
      projIds = "'" + projIds.replaceAll(",", "','") + "'";
      String sqlin = SqlUtil.InSql(projIds, "proj_id in");
      String sql = "  INSERT INTO CM_USERPROJ(WID,USERPROJ_ID,USER_ID,PROJ_ID,USER_FLAG,ROLE_ID) (select UUID(),UUID(), '" + userId + "',proj_id,'1','0'  from cm_proj where  (1=2 " + sqlin + "))";
      this.baseJdbcDao.insert(sql);
    }
    return "success";
  }

  public String DeleteProjUser(String userId)
  {
    String sql = "DELETE FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' and USER_FLAG ='1'";
    this.baseJdbcDao.delete(sql);
    return "success";
  }

  public List SeeProFileTable(String profileId)
  {
    String sql = "SELECT * FROM ( SELECT A.WID, B.PROFILE_ID, A.DATACTR_TYPE, A.DATACTR_CODE, A.DATACTR_NAME, A.ENABLED, A.REMARK, A.SUBSYS_CODE, A.MODULE_CODE,  D.MODULE_NAME MNAME, C.MODULE_NAME AS SUB_NAME, D.SEQ_NUM, CASE WHEN B.PROFILE_ID IS NULL THEN '0' ELSE '1' END AS ENABLED_FLAG FROM CM_DATACTR A  LEFT OUTER JOIN CM_PROFCTR B ON B.DATACTR_CODE = A.DATACTR_CODE AND B.PROFILE_ID = '" + 
      profileId + "'" + 
      " LEFT OUTER JOIN CM_MODULE C ON C.MODULE_CODE = A.SUBSYS_CODE" + 
      " LEFT OUTER JOIN CM_MODULE D ON D.MODULE_CODE = A.MODULE_CODE" + 
      " LEFT OUTER JOIN CM_MODULE E ON D.PARENT_MODULE_CODE = E.MODULE_CODE" + 
      " WHERE A.ENABLED='1' AND C.ENABLED='1' AND D.ENABLED='1' AND E.ENABLED='1'" + 
      " ) TT " + 
      " WHERE DATACTR_TYPE='0' ORDER BY SUB_NAME,SEQ_NUM,DATACTR_NAME";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public String CheckUserNum(HttpServletRequest request) {
    String str2 = this.baseJdbcService.getStringByLicense("UserNum");
    int UserNum = this.baseJdbcDao.getCount("SELECT COUNT(WID) NUM FROM CM_USERS ");
    if ((DataTypeUtil.validate(str2)) && 
      (Integer.valueOf(str2).intValue() <= UserNum)) {
      return "超出许可允许的用户个数，请联系管理员！";
    }

    return null;
  }

  public String SetAppUser(String ensemble, String userId, String where) {
    if (ensemble.equals("N")) {
      if (DataTypeUtil.validate(userId)) {
        where = "WHERE USER_ID ='" + userId + "'";
      }
      else if (DataTypeUtil.validate(where)) {
        where = "WHERE DISABLE_FLAG ='" + where + "'";
      }

      String sql = "UPDATE CM_USERS SET ENSEMBLE ='N' " + where;
      this.baseJdbcDao.update(sql);
      return "success";
    }
    String numSql = "";
    String NumLicense = this.baseJdbcService.getStringByLicense("AppNum");
    if (DataTypeUtil.validate(userId)) {
      numSql = "SELECT COUNT(WID) NUM FROM CM_USERS WHERE  ENSEMBLE ='Y' ";
      int AppNum = this.baseJdbcDao.getCount(numSql);

      if ((DataTypeUtil.validate(NumLicense)) && (Integer.valueOf(NumLicense).intValue() <= AppNum)) {
        return "超出许可允许的客户端个数(共" + NumLicense + "个)，请联系管理员！";
      }
      String sql = " UPDATE CM_USERS SET ENSEMBLE ='Y' WHERE USER_ID ='" + userId + "'";
      this.baseJdbcDao.update(sql);
      return "success";
    }

    if (DataTypeUtil.validate(where))
    {
      if (where.equals("0"))
        numSql = "SELECT COUNT(WID) NUM FROM CM_USERS WHERE  DISABLE_FLAG ='0' OR (DISABLE_FLAG ='1' AND ENSEMBLE ='Y' )";
      else {
        numSql = "SELECT COUNT(WID) NUM FROM CM_USERS WHERE  DISABLE_FLAG ='1' OR (DISABLE_FLAG ='0' AND ENSEMBLE ='Y' )";
      }
      where = " WHERE  DISABLE_FLAG ='" + where + "'";
    } else {
      numSql = "SELECT COUNT(WID) NUM FROM CM_USERS";
    }
    int AppNum = this.baseJdbcDao.getCount(numSql);
    if ((DataTypeUtil.validate(NumLicense)) && (Integer.valueOf(NumLicense).intValue() < AppNum)) {
      return "超出许可允许的客户端个数(共" + NumLicense + "个)，请联系管理员！";
    }
    String sql = "UPDATE CM_USERS SET ENSEMBLE ='Y' " + where;
    this.baseJdbcDao.update(sql);
    return "success";
  }
}