package com.epmis.sys.service.imp;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.BaseJdbcService;
import com.epmis.sys.service.RoleService;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.SqlUtil;
import com.epmis.sys.vo.CmRole;

//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("roleService")
public class RoleServiceImpl
  implements RoleService
{

  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;

  @Autowired
  private BaseJdbcService baseJdbcService;

  public String GetRoleNameByUserId(String userId)
  {
    String sql = "SELECT A.RLUSER_ID,B.ROLE_NAME  FROM CM_RLUSER A left JOIN CM_ROLE B ON A.ROLE_ID=B.ROLE_ID  WHERE A.USER_ID='" + userId + "'";
    String roleName = "";
    List<Map<String,Object>> list = this.baseJdbcDao.queryListMap(sql);
    if (DataTypeUtil.validate(list)) {
      for (Map<String,Object> map : list) {
        roleName = DataTypeUtil.validate(roleName) ? roleName + "、" + DataTypeUtil.formatDbColumn(map.get("ROLE_NAME")) : DataTypeUtil.formatDbColumn(map.get("ROLE_NAME"));
      }
    }
    return roleName;
  }

  public List<Map<String,Object>> SysRoleTree(String parentId)
  {
    List<Map<String,Object>> items = findRoleTreeBySql(parentId);
    for (Map<String,Object> item : items) {
      if (findRoleTreeBySql(item.get("ROLE_ID").toString()).size() > 0) {
        item.put("state", "closed");
      }
      if (item.get("ROLE_TYPE").toString().equals("1"))
        item.put("iconCls", "icon-role");
      else {
        item.put("iconCls", "icon-obs");
      }
      String sql = " SELECT USER_NAME,ACTUAL_NAME FROM CM_USERS WHERE USER_ID IN( SELECT USER_ID FROM CM_RLUSER WHERE ROLE_ID='" + item.get("ROLE_ID").toString() + "')";
      String actualName = "";
      List<Map<String,Object>> list = this.baseJdbcDao.queryListMap(sql);
      if (DataTypeUtil.validate(list)) {
        for (Map<String,Object> map : list) {
          actualName = DataTypeUtil.validate(actualName) ? actualName + "、" + DataTypeUtil.formatDbColumn(map.get("ACTUAL_NAME")) : DataTypeUtil.formatDbColumn(map.get("ACTUAL_NAME"));
        }
      }
      item.put("ACTUAL_NAME", actualName);
    }
    return items;
  }

  public List<Map<String, Object>> findRoleTreeBySql(String parentId) {
    List<Map<String, Object>> result = null;

    String sql = " SELECT PARENT_ROLE_ID,ROLE_ID, ROLE_NAME,REMARK,ROLE_TYPE FROM CM_ROLE C WHERE 1=1  AND PARENT_ROLE_ID = '" + parentId + "'  ORDER BY SEQ_NUM";
    result = this.baseJdbcDao.queryListMap(sql);
    return result;
  }

  public Map<String, Object> GetRoleInfoById(String roleId) {
    String sql = "select * from cm_role where role_id = '" + roleId + "'";
    return this.baseJdbcDao.queryMap(sql);
  }

  public String UpdateRole(CmRole cmRole) {
    String sql = "update cm_role set role_name='" + cmRole.getRoleName() + "',seq_num=" + cmRole.getSeqNum() + ",remark='" + cmRole.getRemark() + "' where role_id = '" + cmRole.getRoleId() + "'";
    this.baseJdbcDao.update(sql);
    return "success";
  }

  public List<Map<String, Object>> RoleUserTable(String roleId) {
    String sql = "select RLUSER_ID , U.USER_ID,USER_NAME,ACTUAL_NAME from CM_RLUSER R left join CM_USERS U on R.USER_ID = U.User_ID WHERE ROLE_ID='" + roleId + "'";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public List<Map<String, Object>> AddUserTable(String roleId) {
    String sql = " SELECT USER_ID,USER_NAME,ACTUAL_NAME  FROM CM_USERS C  WHERE USER_ID NOT IN(SELECT USER_ID FROM CM_RLUSER WHERE ROLE_ID='" + 
      roleId + "') AND USER_ID NOT IN (SELECT B.USER_ID FROM CM_USERS B WHERE B.DISABLE_FLAG = 1)" + 
      " ORDER BY USER_NAME";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public String AddRoleUser(String roleId, String userIds)
  {
    String sql = "";
    String[] userIdList = userIds.split(",");
    if (userIdList.length > 0) {
      for (int i = 0; i < userIdList.length; i++) {
        if (this.baseJdbcDao.getCount("select count(wid) from cm_rluser where role_id='" + roleId + "' and user_id ='" + userIdList[i] + "'  ") == 0) {
          sql = "insert into cm_rluser(wid,rluser_id,user_id,role_id ,ar_flag)values('" + Guid.getGuid() + "','" + Guid.getGuid() + "','" + userIdList[i] + "','" + roleId + "','N')";
          this.baseJdbcDao.insert(sql);
        }
      }
    }
    return "success";
  }

  public List<Map<String, Object>> ModuleLicenseRoleTable(String roleId)
  {
//    List ModuleList = new ArrayList();
    String sql = "SELECT  A.MODULE_CODE,A.MODULE_NAME,A.SEQ_NUM ,(select count(B.wid) from CM_USERMDL B where  B.MODULE_CODE = A.MODULE_CODE AND B.ROLE_ID  = '" + roleId + "'  ) AS ROLE_FLAG  FROM CM_MODULE A  WHERE A.MODULE_FLAG = '0' AND A.ENABLED = '1'   AND A.PARENT_MODULE_CODE ='0' ORDER BY A.SEQ_NUM ASC";
    List<Map<String, Object>> items = this.baseJdbcDao.queryListMap(sql);
//    String ModuleCode = this.baseJdbcService.getStringByLicense("ModuleCode");
//    String[] ModuleCodeList = ModuleCode.split(",");
//    Arrays.sort(ModuleCodeList);
//    if (DataTypeUtil.validate(items)) {
//      for (Map item : items) {
//        if (Arrays.binarySearch(ModuleCodeList, item.get("MODULE_CODE").toString()) >= 0) {
//          ModuleList.add(item);
//        }
//      }
//    }
    return items;
  }

  public String UpdateModuleLicenseRole(String roleId, String moduleCodes)
  {
    String sql = "delete from cm_usermdl where role_id = '" + roleId + "' and  MODULE_CODE in (select    A.MODULE_CODE from   CM_MODULE A  WHERE A.MODULE_FLAG = '0' AND A.ENABLED = '1'   AND A.PARENT_MODULE_CODE ='0')";
    this.baseJdbcDao.delete(sql);
    sql = "insert into cm_usermdl(wid,usermdl_id,user_id,role_id,module_code,user_flag,parent_module_code) (select UUID(),UUID(),'0','" + roleId + "',module_code,'0',parent_module_code from cm_module where module_code in ('" + moduleCodes.replaceAll(",", "','") + "') and MODULE_FLAG = '0' AND ENABLED = '1'   AND  PARENT_MODULE_CODE ='0')";
    this.baseJdbcDao.insert(sql);
    return "success";
  }

  public String UpdateModuleAssignRole(String roleId, String moduleCodes)
  {
    String sql = "delete from cm_usermdl where role_Id = '" + roleId + "' and  MODULE_CODE not in (select    A.MODULE_CODE from   CM_MODULE A  WHERE A.MODULE_FLAG = '0' AND A.ENABLED = '1'   AND A.PARENT_MODULE_CODE ='0') AND MODULE_CODE IN (select MODULE_CODE from   CM_MODULE) AND PARENT_MODULE_CODE IN (select MODULE_CODE from   CM_MODULE)";
    this.baseJdbcDao.delete(sql);
    sql = "insert into cm_usermdl(wid,usermdl_id,user_id,role_id,module_code,user_flag,parent_module_code) (select UUID(),UUID(),'0','" + roleId + "',module_code,'0',parent_module_code from cm_module where module_code in ('" + moduleCodes.replaceAll(",", "','") + "') and MODULE_FLAG = '1' AND ENABLED = '1')";
    this.baseJdbcDao.insert(sql);
    return "success";
  }

  public List<Map<String,Object>> ModuleAssignRoleTree(String parentId, String roleId)
  {
    List<Map<String,Object>> items = findModuleAssignTreeBySql(parentId, roleId);
    for (Map<String,Object> item : items) {
      item.put("state", "closed");
      item.put("iconCls", "icon-module");
      String ROLE_FLAG = "1";
      List<Map<String,Object>> childerItems = findModuleAssignTreeBySql(item.get("MODULE_CODE").toString(), roleId);
      for (Map<String,Object> childerItem : childerItems) {
        childerItem.put("iconCls", "icon-list");
        childerItem.put("NODE_TYPE", "list");
        childerItem.put("PARENT", item.get("MODULE_CODE").toString());
        if (childerItem.get("ROLE_FLAG").toString().equals("0")) {
          ROLE_FLAG = "0";
        }
      }
      item.put("PARENT", item.get("MODULE_CODE").toString() + "_PARENT");
      item.put("ROLE_FLAG", ROLE_FLAG);
      item.put("NODE_TYPE", "module");
      item.put("children", childerItems);
    }

    return items;
  }

  public List<Map<String, Object>> findModuleAssignTreeBySql(String parentId, String roleId) {
    List<Map<String, Object>> result = null;
    String sql = "";
    if (parentId.equals("0"))
      sql = " SELECT MODULE_CODE,MODULE_NAME FROM CM_MODULE A WHERE A.PARENT_MODULE_CODE='0' AND A.ENABLED='1' AND A.MODULE_CODE IN(SELECT B.MODULE_CODE FROM  CM_USERMDL B WHERE B.ROLE_ID='" + roleId + "') ORDER BY A.SEQ_NUM";
    else {
      sql = " SELECT MODULE_CODE,MODULE_NAME,(SELECT COUNT(B.WID) FROM CM_USERMDL B WHERE  B.MODULE_CODE = A.MODULE_CODE AND B.ROLE_ID  = '" + roleId + "' ) AS ROLE_FLAG FROM CM_MODULE A WHERE A.PARENT_MODULE_CODE IN (SELECT MODULE_CODE FROM  CM_MODULE WHERE PARENT_MODULE_CODE = '" + parentId + "'  ) AND A.ENABLED='1' ORDER BY A.SEQ_NUM";
    }
    result = this.baseJdbcDao.queryListMap(sql);
    return result;
  }

  public List<Map<String,Object>> ProjRoleTree(String parentId, String roleId)
  {
    return findProjRoleTreeBySql(parentId, roleId);
  }

  public List<Map<String, Object>> findProjRoleTreeBySql(String parentId, String roleId) {
    String sql = "SELECT PROJ_ID,PARENT_PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,IS_ZJLR_FLAG,PROJ_CMPT_PCT,PROJ_NODE_FLAG,STATUS,SEQ_NUM,(SELECT COUNT(PROJ_ID) FROM CM_PROJ WHERE PARENT_PROJ_ID=A.PROJ_ID) AS CHILD_COUNT,(select count(B.wid) from CM_USERPROJ B where  B.PROJ_ID = A.PROJ_ID AND B.ROLE_ID = '" + 
      roleId + "' AND B.USER_FLAG ='0'   ) AS ROLE_FLAG " + 
      "FROM CM_PROJ A " + 
      "WHERE A.PARENT_PROJ_ID = '" + parentId + "' ORDER BY A.PROJ_NODE_FLAG DESC, A.SEQ_NUM";
    List<Map<String,Object>> result = this.baseJdbcDao.queryListMap(sql);
    if (DataTypeUtil.validate(result)) {
      for (Map<String,Object> Item : result) {
        if (!Item.get("CHILD_COUNT").toString().equals("0")) {
          Item.put("state", "closed");
          Item.put("children", findProjRoleTreeBySql(Item.get("PROJ_ID").toString(), roleId));
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

  public String InsertProjRole(String roleId, String projIds)
  {
    if (DataTypeUtil.validate(projIds)) {
      projIds = "'" + projIds.replaceAll(",", "','") + "'";
      String sqlin = SqlUtil.InSql(projIds, "proj_id in");
      String sql = "  INSERT INTO CM_USERPROJ(WID,USERPROJ_ID,USER_ID,PROJ_ID,USER_FLAG,ROLE_ID) (select UUID(),UUID(),'0',proj_id,'0', '" + roleId + "'  from cm_proj where  (1=2 " + sqlin + "))";
      this.baseJdbcDao.insert(sql);
    }
    return "success";
  }

  public String DeleteProjRole(String roleId)
  {
    String sql = "DELETE FROM CM_USERPROJ WHERE ROLE_ID = '" + roleId + "' and USER_FLAG ='0'";
    this.baseJdbcDao.delete(sql);
    return "success";
  }

  public String AddRole(CmRole cmRole)
  {
    String sql = " INSERT INTO CM_ROLE(ROLE_ID,WID,ROLE_NAME,ROLE_TYPE,REMARK,SEQ_NUM,PARENT_ROLE_ID) VALUES ('" + Guid.getGuid() + "','" + Guid.getGuid() + "','" + cmRole.getRoleName() + "','" + cmRole.getRoleType() + "','" + cmRole.getRemark() + "'," + cmRole.getSeqNum() + ",'" + cmRole.getParentRoleId() + "')";
    this.baseJdbcDao.insert(sql);
    return "success";
  }

  public String DelRole(String roleId, String roleType)
  {
    if (this.baseJdbcDao.getCount(" SELECT COUNT(WID)  FROM CM_ROLE WHERE PARENT_ROLE_ID='" + roleId + "'") > 0) {
      return "存在子节点，禁止删除";
    }
    if (roleType.equals("0")) {
      String sql = "DELETE FROM CM_ROLE WHERE ROLE_ID ='" + roleId + "'";
      this.baseJdbcDao.delete(sql);
    }
    else
    {
//      String sql = "DELETE FROM SM_PLANUSER WHERE ROLE_ID ='" + roleId + "'";
//      this.baseJdbcDao.delete(sql);
      String sql = "DELETE FROM CM_RLUSER WHERE ROLE_ID ='" + roleId + "'";
      this.baseJdbcDao.delete(sql);
      sql = "DELETE FROM CM_USERMDL WHERE ROLE_ID ='" + roleId + "'";
      this.baseJdbcDao.delete(sql);
      sql = " DELETE FROM CM_USERPROJ WHERE ROLE_ID ='" + roleId + "'";
      this.baseJdbcDao.delete(sql);
      sql = "DELETE FROM CM_ROLE WHERE ROLE_ID ='" + roleId + "'";
      this.baseJdbcDao.delete(sql);
      sql = "DELETE FROM CM_USERPROF WHERE PROJ_ID IN(SELECT PROJ_ID FROM CM_USERPROJ WHERE ROLE_ID='" + roleId + "' AND PROJ_ID NOT IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID IN (SELECT USER_ID FROM CM_RLUSER WHERE ROLE_ID='" + roleId + "')) AND NOT EXISTS(SELECT PROJ_ID FROM CM_USERPROJ WHERE ROLE_ID IN(SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID IN(SELECT USER_ID FROM CM_RLUSER WHERE ROLE_ID='" + roleId + "') AND ROLE_ID!='" + roleId + "')))";
    }

    return "success";
  }
}