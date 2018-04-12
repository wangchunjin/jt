package com.epmis.sys.service.imp;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.BaseJdbcService;
import com.epmis.sys.service.ProjService;
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.DateUtil;
import com.epmis.sys.util.FileUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.vo.CmProj;
import com.epmis.sys.vo.CmProjSurvey;
import com.epmis.sys.vo.CmProjpart;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("projService")
public class ProjServiceImpl
  implements ProjService
{

  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;

  @Autowired
  @Qualifier("baseJdbcService")
  private BaseJdbcService baseJdbcService;
  private String newStringIds = "";

  public List<Map<String, Object>> ShowEpsTree(String parentId)
  {
    List items = new ArrayList();

    items = findChildEpsByParentId(parentId);
    return items;
  }

  public List<Map<String, Object>> findChildEpsByParentId(String parentId)
  {
    List<Map<String, Object>> ChildrenList = findTreeBySql(parentId);
    List items = new ArrayList();
    for (Map<String,Object> item : ChildrenList)
    {
      if (findTreeBySql(item.get("id").toString()).size() > 0) {
        item.put("state", "closed");
        item.put("iconCls", "icon-eps");
      } else {
        item.put("state", "open");
        item.put("iconCls", "icon-eps");
      }

      items.add(item);
    }
    return items;
  }

  public List<Map<String, Object>> findTreeBySql(String ParentId) {
    List result = null;
    if (DataTypeUtil.validate(ParentId)) {
      String sql = "SELECT proj_id id ,proj_short_name,proj_name,seq_num  from cm_proj where proj_node_flag = 'N' and parent_proj_id = '" + ParentId + "' order by seq_num ";
      result = this.baseJdbcDao.queryListMap(sql);
    }
    return result;
  }

  public String AddEps(UserInfo user, CmProj cmProj) {
    String result = "";
    if ((DataTypeUtil.validate(cmProj.getProjName())) && (DataTypeUtil.validate(cmProj.getProjShortName())) && (DataTypeUtil.validate(cmProj.getParentProjId())))
    {
      String wid = Guid.getGuid();
      String projId = Guid.getGuid();
      String sql = "";

      if (cmProj.getParentProjId().equals("0")) {
        String seq_numSql = "select (ifnull(max(seq_num),0)+100) as seq_num from cm_proj where parent_proj_id = '" + cmProj.getParentProjId() + "'";
        String seq_num = this.baseJdbcDao.getFieldValue(seq_numSql).toString();
        sql = "INSERT INTO CM_PROJ(WID,PROJ_SHORT_NAME,PROJ_NAME,PROJ_NODE_FLAG,PARENT_PROJ_ID,PROJ_ID,SEQ_NUM,CREATED_BY,PARENT_PATH) values('" + 
          wid + "','" + cmProj.getProjShortName() + "','" + cmProj.getProjName() + "','N'," + 
          "'" + cmProj.getParentProjId() + "'," + 
          "'" + projId + "'," + seq_num + ",'" + user.getUserId() + "', '" + projId + "')";
      } else {
        sql = "INSERT INTO CM_PROJ(WID,PROJ_SHORT_NAME,PROJ_NAME,PROJ_NODE_FLAG,PARENT_PROJ_ID,PROJ_ID,SEQ_NUM,CREATED_BY,PARENT_PATH)  select '" + 
          wid + "','" + cmProj.getProjShortName() + "','" + cmProj.getProjName() + "','N'," + 
          "'" + cmProj.getParentProjId() + "'," + 
          "'" + projId + "',(select ifnull(max(seq_num),0)+10 from cm_proj where parent_proj_id = '" + cmProj.getParentProjId() + "'),'" + user.getUserId() + "', concat(PARENT_PATH,';','" + projId + "') from cm_Proj where proj_id = '" + cmProj.getParentProjId() + "' ";
      }
      if (this.baseJdbcDao.update(sql))
        result = "success";
      else {
        result = "增加失败！";
      }
    }
    else
    {
      result = "代码、名称任一不能为空！";
    }
    return result;
  }

  public String DelEps(String projId) {
    String result = "";
    String numSql = "  SELECT count(PROJ_ID) FROM CM_PROJ WHERE PARENT_PROJ_ID='" + projId + "'";
    if (this.baseJdbcDao.getCount(numSql) > 0) {
      result = "存在子节点，禁止删除！";
      return result;
    }
    String sql = "delete from cm_proj where proj_id = '" + projId + "'";
    if (this.baseJdbcDao.delete(sql))
      result = "success";
    else {
      result = "增加失败";
    }
    return result;
  }

  public CmProj GetEps(CmProj cmProj) {
    String sql = "select * from cm_proj where proj_id = '" + cmProj.getProjId() + "'";
    Map map = this.baseJdbcDao.queryMap(sql);
    if (DataTypeUtil.validate(map)) {
      cmProj.setProjShortName(map.get("PROJ_SHORT_NAME").toString());
      cmProj.setProjName(map.get("PROJ_NAME").toString());
      String str = DataTypeUtil.validate(map.get("SEQ_NUM")) ? map.get("SEQ_NUM").toString() : "0";
      cmProj.setSeqNum(Integer.valueOf(DataTypeUtil.formatDbColumn(str)));
    }
    return cmProj;
  }

  public String UpdateEps(CmProj cmProj) {
    String result = "";
    String sql = "update CM_PROJ set proj_short_name ='" + cmProj.getProjShortName() + "',proj_name = '" + cmProj.getProjName() + "',seq_num=" + cmProj.getSeqNum() + " where proj_id ='" + cmProj.getProjId() + "'";
    if (this.baseJdbcDao.update(sql)) {
      result = "success";
    }
    return result;
  }

  public List ShowProjTree(String parentId, UserInfo userInfo, String type, String key)
  {
    List result = new ArrayList();
    String userId = userInfo.getUserId();
    String ProjId = userInfo.getProjId();
    if (type.equals("NULL"))
      type = " (CONTRACT_TITLE='' OR CONTRACT_TITLE IS NULL) ";
    else if (type.equals("NOT"))
      type = " CONTRACT_TITLE='未完工' ";
    else if (type.equals("DO"))
      type = " CONTRACT_TITLE='已完工' ";
    else {
      type = " (CONTRACT_TITLE='未完工' OR CONTRACT_TITLE='已完工') ";
    }
    if (DataTypeUtil.validate(key)) {
      key = " AND (PROJ_SHORT_NAME LIKE '%" + key + "%' OR PROJ_NAME LIKE '%" + key + "%') ";
    }
    List<Map<String, Object>> items = findProjTreeBySql(parentId, userId, type, key);
    for (Map<String, Object> item : items)
    {
      int rs_count = Integer.valueOf(DataTypeUtil.formatDbColumn(item.get("RS_COUNT"))).intValue();
      int eps_count = Integer.valueOf(DataTypeUtil.formatDbColumn(item.get("EPS_COUNT"))).intValue();
      String proj_node_flag = DataTypeUtil.formatDbColumn(item.get("PROJ_NODE_FLAG"));
      String is_zjlr_flag = DataTypeUtil.formatDbColumn(item.get("IS_ZJLR_FLAG"));
      if (proj_node_flag.equals("N")) {
        if ((rs_count > 0) || (eps_count > 0)) {
          item.put("state", "closed");
        }
        item.put("iconCls", "icon-eps");

        result.add(item);
      }
      else if (proj_node_flag.equals("Y")) {
        if ((DataTypeUtil.validate(ProjId)) && (ProjId.equals(item.get("PROJ_ID").toString())))
          item.put("iconCls", "icon-openproj");
        else {
          item.put("iconCls", "icon-proj");
        }
        result.add(item);
      }

    }

    return result;
  }

  public List<Map<String, Object>> findProjTreeBySql(String parentId, String userId, String type, String key) {
    List result = null;
    if (DataTypeUtil.validate(parentId)) {
      String sql = "SELECT PROJ_ID,PARENT_PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,IS_ZJLR_FLAG,PROJ_CMPT_PCT,date_format(PROCEED_DATE,'%Y-%m-%d')PROCEED_DATE,date_format(COMPLETION_DATE,'%Y-%m-%d')COMPLETION_DATE,PROJ_NODE_FLAG,STATUS,SEQ_NUM,(SELECT B.CODE_NAME FROM CM_CODE B WHERE B.CODE_TYPE = 'PROJ_STAGE' AND B.CODE_SHORT_NAME = A.PROJ_STAGE) AS PROJ_STAGE,(SELECT COUNT(PROJ_ID) FROM CM_PROJ WHERE PROJ_NODE_FLAG = 'N' AND PARENT_PROJ_ID=A.PROJ_ID) AS EPS_COUNT,(SELECT COUNT(PROJ_ID) FROM CM_PROJ WHERE PROJ_NODE_FLAG = 'Y' AND LOCATE(A.PROJ_ID,PARENT_PATH)>0 AND " + 
        type + key + 
        "AND PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "'))  AND STATUS = 'JH' ) AS RS_COUNT " + 
        "FROM CM_PROJ A " + 
        "WHERE (PROJ_NODE_FLAG = 'N' OR (" + type + key + " and PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "'))   AND STATUS = 'JH' ))" + 
        "AND A.PARENT_PROJ_ID = '" + parentId + "' ORDER BY A.PROJ_NODE_FLAG DESC, A.SEQ_NUM";
      result = this.baseJdbcDao.queryListMap(sql);
    }
    return result;
  }

  public List ProjTreeProjectCattye(String parentId, UserInfo userInfo) {
    String userId = userInfo.getUserId();
    String ProjId = userInfo.getProjId();

    List<Map<String, Object>> items = findProjectCattyeTreeBySql(parentId, userId);
    for (Map<String,Object> item : items)
    {
      String type = DataTypeUtil.formatDbColumn(item.get("TYPE"));
      if ((type.equals("CAT")) && 
        (findProjectCattyeTreeBySql(item.get("PROJ_ID").toString(), userId).size() > 0)) {
        item.put("state", "closed");
      }

      if (type.equals("CAT"))
        item.put("iconCls", "icon-cat");
      else {
        item.put("iconCls", "icon-proj");
      }
    }

    return items;
  }

  public List<Map<String, Object>> findProjectCattyeTreeBySql(String parentId, String userId) {
    List result = null;

    String sql = "";
    if (parentId.equals("0")) {
      sql = "SELECT CONCAT('FIRST_',ID) PROJ_ID,CODE_SHORT_NAME PROJ_SHORT_NAME,CODE_NAME PROJ_NAME ,SEQ_NUM,'CAT' TYPE FROM CM_CODE WHERE CODE_TYPE='PROJ_STAGE' UNION SELECT 'NOT' ,'δ����','δ����',100000 ,'CAT' TYPE ORDER BY SEQ_NUM";
      result = this.baseJdbcDao.queryListMap(sql);
      return result;
    }if (parentId.equals("NOT")) {
      sql = "SELECT PROJ_ID,PARENT_PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,IS_ZJLR_FLAG,PROJ_CMPT_PCT,date_format(PROCEED_DATE,'%Y-%m-%d')PROCEED_DATE,date_format(COMPLETION_DATE,'%Y-%m-%d')COMPLETION_DATE,PROJ_NODE_FLAG,STATUS,SEQ_NUM,(SELECT B.CODE_NAME FROM CM_CODE B WHERE B.CODE_TYPE = 'PROJ_STAGE' AND B.CODE_SHORT_NAME = A.PROJ_STAGE) AS PROJ_STAGE,'PROJ' TYPE ,CONTRACT_TITLE FROM CM_PROJ A WHERE PROJ_NODE_FLAG = 'Y' AND  PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + 
        userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "'))   AND STATUS = 'JH' " + 
        "AND   (A.PROJ_STAGE IS NULL OR A.PROJ_STAGE='' ) and (A.PROJ_STAGE1 IS NULL OR A.PROJ_STAGE1='' )  ORDER BY A.PROJ_SHORT_NAME ASC, A.SEQ_NUM";
      result = this.baseJdbcDao.queryListMap(sql);
      return result;
    }if (parentId.startsWith("FIRST_")) {
      sql = "SELECT PROJ_ID,PARENT_PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,IS_ZJLR_FLAG,PROJ_CMPT_PCT,date_format(PROCEED_DATE,'%Y-%m-%d')PROCEED_DATE,date_format(COMPLETION_DATE,'%Y-%m-%d')COMPLETION_DATE,PROJ_NODE_FLAG,STATUS,SEQ_NUM,(SELECT B.CODE_NAME FROM CM_CODE B WHERE B.CODE_TYPE = 'PROJ_STAGE' AND B.CODE_SHORT_NAME = A.PROJ_STAGE) AS PROJ_STAGE,'PROJ' TYPE  ,CONTRACT_TITLE FROM CM_PROJ A WHERE PROJ_NODE_FLAG = 'Y' AND  PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + 
        userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "'))   AND STATUS = 'JH' " + 
        "AND   A.PROJ_STAGE = (SELECT CODE_SHORT_NAME FROM CM_CODE WHERE ID=SUBSTR('" + parentId + "',7)) and (A.PROJ_STAGE1 IS NULL OR A.PROJ_STAGE1='' )  ORDER BY A.PROJ_SHORT_NAME ASC, A.SEQ_NUM";
      result = this.baseJdbcDao.queryListMap(sql);
      sql = "SELECT ID PROJ_ID,CODE_SHORT_NAME PROJ_SHORT_NAME,CODE_NAME PROJ_NAME ,SEQ_NUM,'CAT' TYPE FROM CM_CODE WHERE CODE_TYPE='PROJ_STAGE1' AND code_belongto = SUBSTR('" + parentId + "',7) ORDER BY SEQ_NUM";
      List catList = this.baseJdbcDao.queryListMap(sql);
      if (DataTypeUtil.validate(catList)) {
        catList.addAll(result);
        return catList;
      }
      return result;
    }

    sql = "SELECT PROJ_ID,PARENT_PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,IS_ZJLR_FLAG,PROJ_CMPT_PCT,date_format(PROCEED_DATE,'%Y-%m-%d')PROCEED_DATE,date_format(COMPLETION_DATE,'%Y-%m-%d')COMPLETION_DATE,PROJ_NODE_FLAG,STATUS,SEQ_NUM,(SELECT B.CODE_NAME FROM CM_CODE B WHERE B.CODE_TYPE = 'PROJ_STAGE' AND B.CODE_SHORT_NAME = A.PROJ_STAGE) AS PROJ_STAGE,'PROJ' TYPE  ,CONTRACT_TITLE FROM CM_PROJ A WHERE PROJ_NODE_FLAG = 'Y' AND  PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + 
      userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "'))   AND STATUS = 'JH' " + 
      "AND   A.PROJ_STAGE1 = (SELECT CODE_SHORT_NAME FROM CM_CODE WHERE ID='" + parentId + "')   ORDER BY A.PROJ_SHORT_NAME ASC, A.SEQ_NUM";
    result = this.baseJdbcDao.queryListMap(sql);
    return result;
  }

  public List ProjTreeSupervisionCattye(String parentId, UserInfo userInfo)
  {
    String userId = userInfo.getUserId();
    String ProjId = userInfo.getProjId();

    List<Map<String, Object>> items = findSupervisionCattyeTreeBySql(parentId, userId);
    for (Map<String,Object> item : items)
    {
      String type = DataTypeUtil.formatDbColumn(item.get("TYPE"));
      if ((type.equals("CAT")) && 
        (findSupervisionCattyeTreeBySql(item.get("PROJ_ID").toString(), userId).size() > 0)) {
        item.put("state", "closed");
      }

      if (type.equals("CAT"))
        item.put("iconCls", "icon-cat");
      else {
        item.put("iconCls", "icon-proj");
      }
    }

    return items;
  }

  public List<Map<String, Object>> findSupervisionCattyeTreeBySql(String parentId, String userId) {
    List result = null;

    String sql = "";
    if (parentId.equals("0")) {
      sql = "SELECT CODE_SHORT_NAME PROJ_ID,CODE_SHORT_NAME PROJ_SHORT_NAME,CODE_NAME PROJ_NAME ,SEQ_NUM,'CAT' TYPE FROM CM_CODE WHERE CODE_TYPE='SUPERVISION_CATTYE' UNION SELECT 'NOT' ,'δ����','δ����',100000 ,'CAT' TYPE  ORDER BY SEQ_NUM";
      result = this.baseJdbcDao.queryListMap(sql);
      return result;
    }
    String where = "";
    if (parentId.equals("NOT")) {
      where = " AND  (B.PART_PROJ_INVEST is null or B.PART_PROJ_INVEST ='')";
    } else if (parentId.length() > 4)
    {
      String[] arr = parentId.split("-");
      where = " AND  B.PART_PROJ_INVEST>=" + Double.parseDouble(arr[0]) + " AND B.PART_PROJ_INVEST<" + Double.parseDouble(arr[1]);
    } else if (parentId.length() == 4) {
      where = " AND  B.PART_PROJ_INVEST>=" + Double.parseDouble(parentId);
    } else if (parentId.length() < 4) {
      where = " AND  B.PART_PROJ_INVEST<" + Double.parseDouble(parentId) + "AND  (B.PART_PROJ_INVEST is not null and B.PART_PROJ_INVEST !='')";
    }sql = "SELECT A.PROJ_ID,PARENT_PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,IS_ZJLR_FLAG,PROJ_CMPT_PCT,date_format(PROCEED_DATE,'%Y-%m-%d')PROCEED_DATE,date_format(COMPLETION_DATE,'%Y-%m-%d')COMPLETION_DATE,PROJ_NODE_FLAG,STATUS,SEQ_NUM,(SELECT B.CODE_NAME FROM CM_CODE B WHERE B.CODE_TYPE = 'PROJ_STAGE' AND B.CODE_SHORT_NAME = A.PROJ_STAGE) AS PROJ_STAGE,'PROJ' TYPE ,CONTRACT_TITLE FROM CM_PROJ A LEFT JOIN CM_PROJ_SURVEY B ON  A.PROJ_ID=B.PROJ_ID   WHERE  PROJ_NODE_FLAG = 'Y' " + 
      where + " AND  A.PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "'))   AND STATUS = 'JH' " + 
      "  ORDER BY A.PROJ_SHORT_NAME ASC, A.SEQ_NUM";
    result = this.baseJdbcDao.queryListMap(sql);
    return result;
  }

  public List ProjTreeComprojCattye(String parentId, UserInfo userInfo)
  {
    String userId = userInfo.getUserId();
    String ProjId = userInfo.getProjId();

    List<Map<String, Object>> items = findComprojCattyeTreeBySql(parentId, userId);
    for (Map<String,Object> item : items)
    {
      String type = DataTypeUtil.formatDbColumn(item.get("TYPE"));
      if ((type.equals("CAT")) && 
        (findComprojCattyeTreeBySql(item.get("PROJ_ID").toString(), userId).size() > 0)) {
        item.put("state", "closed");
      }

      if (type.equals("CAT"))
        item.put("iconCls", "icon-cat");
      else {
        item.put("iconCls", "icon-proj");
      }
    }

    return items;
  }

  public List<Map<String, Object>> findComprojCattyeTreeBySql(String parentId, String userId) {
    List result = null;

    String sql = "";
    if (parentId.equals("0")) {
      sql = "SELECT CODE_SHORT_NAME PROJ_ID,CODE_SHORT_NAME PROJ_SHORT_NAME,CODE_NAME PROJ_NAME ,SEQ_NUM,'CAT' TYPE FROM CM_CODE WHERE CODE_TYPE='COMPROJ_CATTYE' UNION SELECT 'NOT' ,'δ����','δ����',100000 ,'CAT' TYPE  ORDER BY SEQ_NUM";
      result = this.baseJdbcDao.queryListMap(sql);
      return result;
    }
    String where = "";
    if (parentId.equals("NOT")) {
      where = " AND  (B.GROSS_INVEST is null or B.GROSS_INVEST ='')";
    } else if ("100000".equals(parentId)) {
      where = " AND  B.GROSS_INVEST>=" + Double.parseDouble(parentId);
    } else if ("50000-100000".equals(parentId)) {
      String[] arr = parentId.split("-");
      where = " AND  B.GROSS_INVEST>=" + Double.parseDouble(arr[0]) + " AND B.GROSS_INVEST<" + Double.parseDouble(arr[1]);
    }
    else if ("10000-50000".equals(parentId)) {
      String[] arr = parentId.split("-");
      where = " AND  B.GROSS_INVEST>=" + Double.parseDouble(arr[0]) + " AND B.GROSS_INVEST<" + Double.parseDouble(arr[1]);
    }
    else if ("10000".equals(parentId)) {
      where = " AND  B.GROSS_INVEST<" + Double.parseDouble(parentId) + " AND  (B.GROSS_INVEST is not null AND B.GROSS_INVEST !='')";
    }sql = "SELECT A.PROJ_ID,PARENT_PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,IS_ZJLR_FLAG,PROJ_CMPT_PCT,date_format(PROCEED_DATE,'%Y-%m-%d')PROCEED_DATE,date_format(COMPLETION_DATE,'%Y-%m-%d')COMPLETION_DATE,PROJ_NODE_FLAG,STATUS,SEQ_NUM,(SELECT B.CODE_NAME FROM CM_CODE B WHERE B.CODE_TYPE = 'PROJ_STAGE' AND B.CODE_SHORT_NAME = A.PROJ_STAGE) AS PROJ_STAGE,'PROJ' TYPE ,CONTRACT_TITLE FROM CM_PROJ A LEFT JOIN CM_PROJ_SURVEY B ON  A.PROJ_ID=B.PROJ_ID   WHERE  PROJ_NODE_FLAG = 'Y' " + 
      where + " AND  A.PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "'))   AND STATUS = 'JH' " + 
      "  ORDER BY A.PROJ_SHORT_NAME ASC, A.SEQ_NUM";
    result = this.baseJdbcDao.queryListMap(sql);
    return result;
  }

  public List ProjTreeCattye(String parentId, String catg_type_id, UserInfo userInfo)
  {
    String userId = userInfo.getUserId();
    String ProjId = userInfo.getProjId();

    List<Map<String, Object>> items = findCattyeTreeBySql(parentId, catg_type_id, userId);
    for (Map<String,Object> item : items)
    {
      String type = DataTypeUtil.formatDbColumn(item.get("TYPE"));
      if ((type.equals("CAT")) && 
        (findCattyeTreeBySql(item.get("PROJ_ID").toString(), catg_type_id, userId).size() > 0)) {
        item.put("state", "closed");
      }

      if (type.equals("CAT"))
        item.put("iconCls", "icon-cat");
      else {
        item.put("iconCls", "icon-proj");
      }
    }

    return items;
  }

  public List<Map<String, Object>> findCattyeTreeBySql(String parentId, String catg_type_id, String userId) {
    List result = null;

    String sql = "";
    if (parentId.equals("0")) {
      sql = " SELECT CATG_ID PROJ_ID,CATG_SHORT_NAME PROJ_SHORT_NAME,CATG_NAME PROJ_NAME ,SEQ_NUM,'CAT' TYPE FROM CM_CATVAL WHERE PARENT_CATG_ID='0' AND CATG_TYPE_ID='" + catg_type_id + "' UNION SELECT 'NOT' ,'δ����','δ����',100000 ,'CAT' TYPE  ORDER BY SEQ_NUM";
      result = this.baseJdbcDao.queryListMap(sql);
      return result;
    }
    String where = "";
    if (parentId.equals("NOT"))
      where = " AND A.PROJ_ID NOT IN (SELECT BASE_MASTER_KEY FROM CM_CATBASE WHERE CATG_TYPE_ID='" + catg_type_id + "') ";
    else {
      where = " AND A.PROJ_ID IN (SELECT BASE_MASTER_KEY FROM CM_CATBASE WHERE CATG_ID='" + parentId + "') ";
    }

    sql = "SELECT A.PROJ_ID,PARENT_PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,IS_ZJLR_FLAG,PROJ_CMPT_PCT,date_format(PROCEED_DATE,'%Y-%m-%d')PROCEED_DATE,date_format(COMPLETION_DATE,'%Y-%m-%d')COMPLETION_DATE,PROJ_NODE_FLAG,STATUS,SEQ_NUM,(SELECT B.CODE_NAME FROM CM_CODE B WHERE B.CODE_TYPE = 'PROJ_STAGE' AND B.CODE_SHORT_NAME = A.PROJ_STAGE) AS PROJ_STAGE,'PROJ' TYPE ,CONTRACT_TITLE FROM CM_PROJ A LEFT JOIN CM_PROJ_SURVEY B ON  A.PROJ_ID=B.PROJ_ID   WHERE  PROJ_NODE_FLAG = 'Y' " + 
      where + " AND  A.PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userId + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userId + "'))   AND STATUS = 'JH' " + 
      "  ORDER BY A.PROJ_SHORT_NAME ASC, A.SEQ_NUM";
    result = this.baseJdbcDao.queryListMap(sql);
    sql = "SELECT CATG_ID PROJ_ID,CATG_SHORT_NAME PROJ_SHORT_NAME,CATG_NAME PROJ_NAME ,SEQ_NUM,'CAT' TYPE FROM CM_CATVAL WHERE PARENT_CATG_ID='" + parentId + "' AND CATG_TYPE_ID='" + catg_type_id + "' ORDER BY SEQ_NUM ";
    List catList = this.baseJdbcDao.queryListMap(sql);
    if (DataTypeUtil.validate(catList)) {
      catList.addAll(result);
      return catList;
    }
    return result;
  }

  public void ChangeProj(String projId, UserInfo userInfo)
  {
    String sql = "SELECT PROJ_ID FROM CM_PROJRCNT WHERE USER_ID='" + userInfo.getUserId() + "' AND PROJ_ID='" + projId + "'";
    Map map = this.baseJdbcDao.queryMap(sql);
    if (DataTypeUtil.validate(map))
    {
      sql = "UPDATE CM_PROJRCNT SET ACCESS_DATE=now() WHERE USER_ID='" + userInfo.getUserId() + "' AND PROJ_ID='" + projId + "'";
      this.baseJdbcDao.update(sql);
    }
    else {
      sql = "INSERT INTO CM_PROJRCNT (WID,USER_ID,PROJ_ID,ACCESS_DATE) VALUES ('" + Guid.getGuid() + "','" + userInfo.getUserId() + "','" + projId + "',now())";
      this.baseJdbcDao.insert(sql);
    }
    sql = "SELECT PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME  FROM  CM_PROJ WHERE PROJ_ID='" + projId + "'";
    Map projMap = this.baseJdbcDao.queryMap(sql);
    if (DataTypeUtil.validate(projMap)) {
      userInfo.setProjId(projMap.get("PROJ_ID").toString());
      userInfo.setProjShortName(projMap.get("PROJ_SHORT_NAME").toString());
      userInfo.setProjName(projMap.get("PROJ_NAME").toString());
      sql = "SELECT DATACTR_CODE FROM CM_PROFCTR A JOIN CM_PROFILE B ON A.PROFILE_ID = B.PROFILE_ID  WHERE (A.PROFILE_ID = (SELECT PROFILE_ID FROM CM_USERS WHERE USER_ID='" + userInfo.getUserId() + "') AND B.PROF_TYPE='0')" + "OR (A.PROFILE_ID = " + "(SELECT PROFILE_ID FROM CM_USERPROF WHERE USER_ID = '" + userInfo.getUserId() + "' AND PROJ_ID = '" + projMap.get("PROJ_ID").toString() + "') AND B.PROF_TYPE='1')";
      String DatactrCode = "";
      List DatactrCodeMap = this.baseJdbcDao.queryListMap(sql);
      if (DataTypeUtil.validate(DatactrCodeMap)) {
        Iterator localIterator = DatactrCodeMap.iterator();
        while (localIterator.hasNext())
        {
          Map localMap = (Map)localIterator.next();
          DatactrCode = DatactrCode + (DataTypeUtil.validate(DatactrCode) ? ";" + DataTypeUtil.formatDbColumn(localMap.get("DATACTR_CODE")) : DataTypeUtil.formatDbColumn(localMap.get("DATACTR_CODE")));
        }
      }
      userInfo.setDatactrCode(DatactrCode);
    }
  }

  public String AddProj(UserInfo user, CmProj cmProj)
  {
    String result = "";

    if ((DataTypeUtil.validate(cmProj.getProjName())) && (DataTypeUtil.validate(cmProj.getProjShortName())) && (DataTypeUtil.validate(cmProj.getParentProjId())))
    {
      String wid = Guid.getGuid();
      String sql = "";
      sql = "select count(wid) from cm_proj where proj_short_name='" + cmProj.getProjShortName() + "' and proj_node_flag='Y'";
      if (this.baseJdbcDao.getCount(sql) > 0) {
        result = "项目代码已存在！";
        return result;
      }
      sql = "INSERT INTO CM_PROJ (WID,PARENT_PATH, DESCRIPTION, PROJ_SHORT_NAME, SEQ_NUM, STATUS, PARENT_PROJ_ID, PROCEED_DATE, COMPLETION_DATE, CREATED_BY, CURR_ID, PROJECT_NUMBER, PROJ_NAME, VNMT_ID, PROJ_NODE_FLAG, PROJ_ID, SITE_LOCATION, CONTRACT_TITLE) select '" + 
        wid + "',concat(PARENT_PATH,';','" + cmProj.getProjId() + "')," + 
        " '" + cmProj.getDescription() + "'," + 
        " '" + cmProj.getProjShortName() + "'," + 
        " (select ifnull(max(seq_num),0)+10 from cm_proj where parent_proj_id = '" + cmProj.getParentProjId() + "')," + 
        " 'JH'," + 
        " '" + cmProj.getParentProjId() + "'," + (
        DataTypeUtil.validate(cmProj.getProceedDate()) ? "'" + cmProj.getProceedDate() + "'" : null) + "," + (
        DataTypeUtil.validate(cmProj.getCompletionDate()) ? "'" + cmProj.getCompletionDate() + "'" : null) + "," + 
        " '" + user.getUserId() + "'," + 
        " '2E3A5297E15A44439F8FA4DE9DC644DC'," + 
        " ''," + 
        " '" + cmProj.getProjName() + "'," + 
        " ''," + 
        " 'Y'," + 
        " '" + cmProj.getProjId() + "'," + 
        " '" + cmProj.getSiteLocation() + "'," + 
        " '未完工' from cm_Proj where proj_id = '" + cmProj.getParentProjId() + "' ";
      this.baseJdbcDao.insert(sql);
      sql = "INSERT INTO CM_PROJRCNT (WID,USER_ID,PROJ_ID,ACCESS_DATE) VALUES ('" + Guid.getGuid() + "','" + user.getUserId() + "','" + cmProj.getProjId() + "',now())";
      this.baseJdbcDao.insert(sql);

      sql = "SELECT T.PROFILE_ID FROM CM_PROFILE T WHERE T.PROF_TYPE='1' AND T.ISDEFAULT='1'";
      String profile_id = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));

      String rolesql = "select distinct role_id from cm_userproj t  where proj_id = '" + cmProj.getParentProjId() + "' and  user_flag ='0'";
      List<Map<String, Object>> listrole = this.baseJdbcDao.queryListMap(rolesql);
      if (DataTypeUtil.validate(listrole)) {
        for (Map<String,Object> maprole : listrole) {
          rolesql = " INSERT INTO CM_USERPROJ(WID,USERPROJ_ID,USER_ID,PROJ_ID,USER_FLAG,ROLE_ID)  VALUES('" + 
            Guid.getGuid() + "','" + Guid.getGuid() + "','0','" + cmProj.getProjId() + "','0','" + DataTypeUtil.formatDbColumn(maprole.get("ROLE_ID")) + "')";
          this.baseJdbcDao.insert(rolesql);
        }
      }
      String roleusersql = "select distinct user_id from cm_userproj    where proj_id = '" + cmProj.getParentProjId() + "' and user_flag ='1'";
      List<Map<String, Object>> listroleuser = this.baseJdbcDao.queryListMap(roleusersql);
      if (DataTypeUtil.validate(listroleuser)) {
        for (Map<String,Object> roleuser : listroleuser) {
          roleusersql = " INSERT INTO CM_USERPROJ(WID,USERPROJ_ID,USER_ID,PROJ_ID,USER_FLAG,ROLE_ID) VALUES('" + 
            Guid.getGuid() + "','" + Guid.getGuid() + "','" + DataTypeUtil.formatDbColumn(roleuser.get("USER_ID")) + "','" + cmProj.getProjId() + "','1','0')";
          this.baseJdbcDao.insert(roleusersql);
        }
      }

      String usersql2 = " select count(distinct user_id) num from ( select user_id from cm_rluser where role_id in(  select  role_id from cm_userproj t  where proj_id = '" + 
        cmProj.getParentProjId() + "' and user_flag ='0')" + 
        " union select  user_id from cm_userproj    where proj_id = '" + cmProj.getParentProjId() + "'  and user_flag ='1') AA" + 
        " where user_id ='" + user.getUserId() + "'";
      if (this.baseJdbcDao.getCount(usersql2) == 0) {
        sql = "INSERT INTO CM_USERPROJ(WID,USERPROJ_ID,USER_ID,PROJ_ID) VALUES('" + Guid.getGuid() + "','" + Guid.getGuid() + "','" + user.getUserId() + "','" + cmProj.getProjId() + "')";
        this.baseJdbcDao.insert(sql);
      }
      sql = " select distinct user_id   from ( select user_id from cm_rluser where role_id in(  select  role_id from cm_userproj t  where proj_id = '" + 
        cmProj.getParentProjId() + "' and user_flag ='0')" + 
        " union select  user_id from cm_userproj    where proj_id = '" + cmProj.getParentProjId() + "'  and user_flag ='1'" + 
        "union select '" + user.getUserId() + "') AA";

      List<Map<String, Object>> listuser = this.baseJdbcDao.queryListMap(sql);
      if (DataTypeUtil.validate(listuser)) {
        for (Map<String,Object> mapuser : listuser) {
          sql = "INSERT INTO CM_USERPROF(WID,USER_ID,PROJ_ID,PROFILE_ID)values('" + Guid.getGuid() + "','" + DataTypeUtil.formatDbColumn(mapuser.get("USER_ID")) + "','" + cmProj.getProjId() + "','" + profile_id + "')";
          this.baseJdbcDao.insert(sql);
        }
      }
      result = "success";
    }
    else {
      result = "代码、名称任一不能为空！";
    }
    return result;
  }

  public List<Map<String, Object>> ShowUserTable(String projId)
  {
    String sql = "SELECT USER_ID  ,USER_NAME,ACTUAL_NAME,PROFILE_ID FROM CM_USERS WHERE USER_ID NOT IN(SELECT USER_ID FROM CM_USERPROJ WHERE USER_FLAG='1' AND PROJ_ID='" + 
      projId + "') AND USER_ID NOT IN (SELECT B.USER_ID FROM CM_USERS B WHERE B.DISABLE_FLAG = 1)        and USER_ID NOT IN (SELECT USER_ID from cm_rluser where role_id in (select role_id  FROM CM_USERPROJ  WHERE USER_FLAG = '0' AND PROJ_ID = '" + projId + "'))" + 
      " ORDER BY USER_NAME";
    List listrole = this.baseJdbcDao.queryListMap(sql);
    return listrole;
  }

  public String AddUser(String userIds, String projId)
  {
    String sql = "SELECT T.PROFILE_ID FROM CM_PROFILE T WHERE T.PROF_TYPE='1' AND T.ISDEFAULT='1'";
    String profile_id = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
    String[] usersIds = userIds.split(";");
    if (usersIds.length > 0) {
      for (String user_id : usersIds) {
        sql = "INSERT INTO CM_USERPROJ(WID,USERPROJ_ID,USER_ID,PROJ_ID) VALUES('" + Guid.getGuid() + "','" + Guid.getGuid() + "','" + user_id + "','" + projId + "')";
        this.baseJdbcDao.insert(sql);
        sql = "INSERT INTO CM_USERPROF(WID,USER_ID,PROJ_ID,PROFILE_ID)values('" + Guid.getGuid() + "','" + user_id + "','" + projId + "','" + profile_id + "')";
        this.baseJdbcDao.insert(sql);
      }
    }

    return "success";
  }

  public String ModuleImport(String userId, String projId, String swbsTypeId, String tableName)
  {
    String sql = "SELECT COUNT(WID) NUM FROM " + tableName + " WHERE PROJ_ID = '" + projId + "'";
    if (this.baseJdbcDao.getCount(sql) > 0) {
      return "已存在模板，请删除后再导入";
    }
    sql = "select * from cm_proj where proj_id = '" + projId + "' ";
    Map map = this.baseJdbcDao.queryMap(sql);
    String proj_short_name = DataTypeUtil.formatDbColumn(map.get("PROJ_SHORT_NAME"));
    String proj_name = DataTypeUtil.formatDbColumn(map.get("PROJ_NAME"));
    String planId = Guid.getGuid();
    String plan_id_path = "";
    plan_id_path = planId;
    sql = "INSERT INTO " + tableName + "(WID,PLAN_ID,PROJ_ID,PARENT_PLAN_ID,PLAN_SHORT_NAME,PLAN_NAME,SEQ_NUM,CREATE_USER_ID,NODE_TYPE,PLAN_ID_PATH) " + 
      "VALUES('" + Guid.getGuid() + "','" + planId + "','" + projId + "','0','" + proj_short_name + "','" + proj_name + "','100','" + userId + "','PROJ','" + plan_id_path + "')";
    this.baseJdbcDao.insert(sql);
    sql = " INSERT INTO SM_PLANUSER(WID,PLANUSER_ID,USER_ID,PROJ_ID,PLAN_ID,ROLE_ID,USER_FLAG) VALUES('" + Guid.getGuid() + "','" + Guid.getGuid() + "','" + userId + "','" + projId + "','" + planId + "','0','1')";
    this.baseJdbcDao.insert(sql);
    if (tableName.toLowerCase().equals("ds_gcgk")) {
      importPlanGk(projId, userId, "0", swbsTypeId, planId, plan_id_path);
    } else {
      importPlan(projId, userId, "0", swbsTypeId, planId, plan_id_path, tableName);
      CreateBaseFolder(projId, userId);
      CreateModuleFolder(projId, userId, tableName);
    }

    return "success";
  }

  private void importPlanGk(String projId, String userId, String parentSwbsId, String swbsTypeId, String ParentplanId, String ParentplanIdPath)
  {
    List<Map<String, Object>> Listmap = this.baseJdbcDao.queryListMap("Select *  from SM_Swbs  where  PARENT_SWBS_ID='" + parentSwbsId + "'  and swbs_type_id = '" + swbsTypeId + "'");
    if (DataTypeUtil.validate(Listmap))
      for (Map<String,Object> map : Listmap) {
        String nodeType = DataTypeUtil.formatDbColumn(map.get("NODE_TYPE"));
        String plan_short_name = DataTypeUtil.formatDbColumn(map.get("SWBS_SHORT_NAME"));
        String plan_name = DataTypeUtil.formatDbColumn(map.get("SWBS_NAME"));
        String remark = DataTypeUtil.formatDbColumn(map.get("REMARK"));
        String seq_num = DataTypeUtil.formatDbColumn(map.get("SEQ_NUM"));
        String doc_swbs_id = DataTypeUtil.formatDbColumn(map.get("DOC_ID"));
        String swbs_id = DataTypeUtil.formatDbColumn(map.get("SWBS_ID"));
        String planId = Guid.getGuid();
        if (nodeType.equals("SWBS")) {
          String sql = "INSERT INTO DS_GCGK(WID,PLAN_ID,PROJ_ID,PARENT_PLAN_ID,PLAN_ID_PATH,PLAN_SHORT_NAME,PLAN_NAME,CREATE_USER_ID,SEQ_NUM,DOC_SWBS_ID,NODE_TYPE,REMARK) VALUES ('" + 
            Guid.getGuid() + "','" + planId + "','" + projId + "','" + ParentplanId + "','" + ParentplanIdPath + ";" + planId + "','" + plan_short_name + "','" + plan_name + "','" + userId + "'," + seq_num + ",'" + doc_swbs_id + "','SWBS','" + remark + "')";
          this.baseJdbcDao.insert(sql);
          sql = " INSERT INTO SM_PLANUSER(WID,PLANUSER_ID,USER_ID,PROJ_ID,PLAN_ID,ROLE_ID,USER_FLAG) VALUES('" + Guid.getGuid() + "','" + Guid.getGuid() + "','" + userId + "','" + projId + "','" + planId + "','0','1')";
          this.baseJdbcDao.insert(sql);
          importPlanGk(projId, userId, swbs_id, swbsTypeId, planId, ParentplanIdPath + ";" + planId);
        }
      }
  }

  private void importPlan(String projId, String userId, String parentSwbsId, String swbsTypeId, String ParentplanId, String ParentplanIdPath, String tableName)
  {
    List<Map<String, Object>> Listmap = this.baseJdbcDao.queryListMap("Select *  from SM_Swbs  where  PARENT_SWBS_ID='" + parentSwbsId + "'  and swbs_type_id = '" + swbsTypeId + "'");
    if (DataTypeUtil.validate(Listmap))
      for (Map<String,Object> map : Listmap) {
        String nodeType = DataTypeUtil.formatDbColumn(map.get("NODE_TYPE"));
        String plan_short_name = DataTypeUtil.formatDbColumn(map.get("SWBS_SHORT_NAME"));
        String plan_name = DataTypeUtil.formatDbColumn(map.get("SWBS_NAME"));
        String remark = DataTypeUtil.formatDbColumn(map.get("REMARK"));
        if (DataTypeUtil.validate(remark)) {
          remark = remark.replaceAll("'", "\\\\'").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>");
        }
        String seq_num = DataTypeUtil.formatDbColumn(map.get("SEQ_NUM"));
        String doc_swbs_id = DataTypeUtil.formatDbColumn(map.get("DOC_ID"));
        String swbs_id = DataTypeUtil.formatDbColumn(map.get("SWBS_ID"));
        String is_major = DataTypeUtil.formatDbColumn(map.get("IS_MAJOR"));
        String info = DataTypeUtil.formatDbColumn(map.get("INFO"));
        String codeId = DataTypeUtil.formatDbColumn(map.get("CODE_ID"));
        if (DataTypeUtil.validate(info)) {
          info = info.replaceAll("'", "\\\\'").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>");
        }
        String planId = Guid.getGuid();
        if (nodeType.equals("SWBS")) {
          String sql = "INSERT INTO " + tableName + "(WID,PLAN_ID,PROJ_ID,PARENT_PLAN_ID,PLAN_ID_PATH,PLAN_SHORT_NAME,PLAN_NAME,CREATE_USER_ID,SEQ_NUM,DOC_SWBS_ID,NODE_TYPE,REMARK,FOLDER_ID,TARGET_START_DATE,TARGET_END_DATE,SWBS_ID)" + 
            " VALUES ('" + Guid.getGuid() + "','" + planId + "','" + projId + "','" + ParentplanId + "','" + ParentplanIdPath + ";" + planId + "','" + plan_short_name + "','" + plan_name + "','" + userId + "'," + seq_num + ",'" + doc_swbs_id + "','SWBS','" + info + "','" + planId + "',now(),now(),'" + swbs_id + "')";
          this.baseJdbcDao.insert(sql);
          sql = " INSERT INTO SM_PLANUSER(WID,PLANUSER_ID,USER_ID,PROJ_ID,PLAN_ID,ROLE_ID,USER_FLAG) VALUES('" + Guid.getGuid() + "','" + Guid.getGuid() + "','" + userId + "','" + projId + "','" + planId + "','0','1')";
          this.baseJdbcDao.insert(sql);
          importPlan(projId, userId, swbs_id, swbsTypeId, planId, ParentplanIdPath + ";" + planId, tableName);
        } else {
          String sql = " INSERT INTO " + tableName + "(WID,PLAN_ID,PROJ_ID,PARENT_PLAN_ID,PLAN_ID_PATH,PLAN_SHORT_NAME,PLAN_NAME,CREATE_USER_ID,SEQ_NUM,DOC_SWBS_ID,NODE_TYPE,REMARK,FOLDER_ID,TARGET_START_DATE,TARGET_END_DATE,SWBS_ID,IS_MAJOR,PLAN_STATUS,ASS_GUIDES,CODE_ID) " + 
            " VALUES ('" + Guid.getGuid() + "','" + planId + "','" + projId + "','" + ParentplanId + "','" + ParentplanIdPath + ";" + planId + "','" + plan_short_name + "','" + plan_name + "','" + userId + "'," + seq_num + ",'" + doc_swbs_id + "','TASK','" + remark + "','" + planId + "',now(),now(),'" + swbs_id + "','" + is_major + "','0','" + info + "','" + codeId + "')";
          this.baseJdbcDao.insert(sql);
          sql = " INSERT INTO SM_PLANUSER(WID,PLANUSER_ID,USER_ID,PROJ_ID,PLAN_ID,ROLE_ID,USER_FLAG) VALUES('" + Guid.getGuid() + "','" + Guid.getGuid() + "','" + userId + "','" + projId + "','" + planId + "','0','1')";
          this.baseJdbcDao.insert(sql);
          if (DataTypeUtil.validate(doc_swbs_id))
          {
            sql = "SELECT DOC_ID,BASE_ITEM_TYPE FROM CM_DOCLINK WHERE BASE_MASTER_KEY ='" + doc_swbs_id + "' AND SWBS_TYPE_ID='" + swbsTypeId + "'";
            List<Map<String, Object>> Listmap2 = this.baseJdbcDao.queryListMap(sql);
            if (DataTypeUtil.validate(Listmap2))
              for (Map<String,Object> map2 : Listmap2) {
                sql = "INSERT INTO CM_DOCLINK(WID,PROJ_ID,DOC_ID,BASE_MASTER_KEY,BASE_ITEM_TYPE,DATE_LINKED) VALUES('" + Guid.getGuid() + "','" + projId + "','" + DataTypeUtil.formatDbColumn(map2.get("DOC_ID")) + "','" + doc_swbs_id + "','" + DataTypeUtil.formatDbColumn(map2.get("BASE_ITEM_TYPE")) + "',now())";
                this.baseJdbcDao.insert(sql);
              }
          }
        }
      }
  }

  public String CreateBaseFolder(String projId, String userId)
  {
    String pGuid = Guid.getGuid();
    String sGuid1 = Guid.getGuid();
    String sGuid2 = Guid.getGuid();
    String sGuid3 = Guid.getGuid();
    String sGuid4 = Guid.getGuid();
    String sGuid5 = Guid.getGuid();
    List listMap = this.baseJdbcDao.queryListMap("select * from cm_proj where proj_id = '" + projId + "'");

    String projShortName = DataTypeUtil.formatDbColumn(((Map)listMap.get(0)).get("PROJ_SHORT_NAME"));
    String projName = DataTypeUtil.formatDbColumn(((Map)listMap.get(0)).get("PROJ_NAME"));

    List count = this.baseJdbcDao.queryListMap("SELECT wid as nums from KM_FOLDER  where PROJ_ID='" + projId + "'");
    if (count.size() <= 6)
    {
      this.baseJdbcDao.delete("delete from  KM_FOLDER  where PROJ_ID='" + projId + "'");
      String[] sql = new String[6];

      sql[0] = 
        ("INSERT INTO KM_FOLDER(Wid,CREATED_DATE,FOLDER_ID,PROJ_ID,TITLE,FOLDER_NAME,DEF_CODE,PARENT_FOLDER_ID,CREATED_BY,SEQ_NUM,DOC_GRADE,FOLDER_ID_PATH)VALUES('" + 
        Guid.getGuid() + "',now(),'" + pGuid + "','" + projId + "','" + projName + "','" + projId + "','" + projShortName + "','2B44E7C053F64B24877F6E79775F4AFD','" + userId + "'," + 
        "1," + "2," + "'2B44E7C053F64B24877F6E79775F4AFD," + pGuid + "')");

      sql[1] = 
        ("INSERT INTO KM_FOLDER(Wid,CREATED_DATE,FOLDER_ID,PROJ_ID,TITLE,FOLDER_NAME,DEF_CODE,PARENT_FOLDER_ID,CREATED_BY,SEQ_NUM,DOC_GRADE,FOLDER_ID_PATH)VALUES('" + 
        Guid.getGuid() + "',now(),'" + sGuid1 + "','" + projId + "','1.1 项目信息','JSCX','-JSCX-','" + pGuid + "','" + userId + "'," + 
        "1," + "2," + "'2B44E7C053F64B24877F6E79775F4AFD," + pGuid + "," + sGuid1 + "')");

      sql[2] = 
        ("INSERT INTO KM_FOLDER(Wid,CREATED_DATE,FOLDER_ID,PROJ_ID,TITLE,FOLDER_NAME,DEF_CODE,PARENT_FOLDER_ID,CREATED_BY,SEQ_NUM,DOC_GRADE,FOLDER_ID_PATH)VALUES('" + 
        Guid.getGuid() + "',now(),'" + sGuid2 + "','" + projId + "','1.2 质量安全文档','ZLAQ','-ZLAQ-','" + pGuid + "','" + userId + "'," + 
        "2," + "2," + "'2B44E7C053F64B24877F6E79775F4AFD," + pGuid + "," + sGuid2 + "')");

      sql[3] = 
        ("INSERT INTO KM_FOLDER(Wid,CREATED_DATE,FOLDER_ID,PROJ_ID,TITLE,FOLDER_NAME,DEF_CODE,PARENT_FOLDER_ID,CREATED_BY,SEQ_NUM,DOC_GRADE,FOLDER_ID_PATH)VALUES('" + 
        Guid.getGuid() + "',now(),'" + sGuid3 + "','" + projId + "','1.3 合同造价文档','HTZJ','-HTZJ-','" + pGuid + "','" + userId + "'," + 
        "3," + "2," + "'2B44E7C053F64B24877F6E79775F4AFD," + pGuid + "," + sGuid3 + "')");

      sql[4] = 
        ("INSERT INTO KM_FOLDER(Wid,CREATED_DATE,FOLDER_ID,PROJ_ID,TITLE,FOLDER_NAME,DEF_CODE,PARENT_FOLDER_ID,CREATED_BY,SEQ_NUM,DOC_GRADE,FOLDER_ID_PATH)VALUES('" + 
        Guid.getGuid() + "',now(),'" + sGuid4 + "','" + projId + "','1.4 进度控制文档','JDKZ','-JDKZ-','" + pGuid + "','" + userId + "'," + 
        "4," + "2," + "'2B44E7C053F64B24877F6E79775F4AFD," + pGuid + "," + sGuid4 + "')");

      sql[5] = 
        ("INSERT INTO KM_FOLDER(Wid,CREATED_DATE,FOLDER_ID,PROJ_ID,TITLE,FOLDER_NAME,DEF_CODE,PARENT_FOLDER_ID,CREATED_BY,SEQ_NUM,DOC_GRADE,FOLDER_ID_PATH)VALUES('" + 
        Guid.getGuid() + "',now(),'" + sGuid5 + "','" + projId + "','1.5 项目进展图片','XMJZ','-XMJZ-','" + pGuid + "','" + userId + "'," + 
        "5," + "2," + "'2B44E7C053F64B24877F6E79775F4AFD," + pGuid + "," + sGuid5 + "')");

      for (String sqls : sql)
      {
        this.baseJdbcDao.insert(sqls);
      }

      return "ok";
    }

    return "�Ѵ��ڣ�";
  }

  public String CreateModuleFolder(String projId, String user_id, String tableName)
  {
    List<Map<String, Object>> listmap = new ArrayList();
    String sign = "";
    if (tableName.toUpperCase().equals("DS_PLAN"))
      sign = "-JSCX-";
    else if (tableName.toUpperCase().equals("SM_PLAN"))
      sign = "-ZLAQ-";
    else if (tableName.toUpperCase().equals("SM_TEST"))
      sign = "-HTZJ-";
    else if (tableName.toUpperCase().equals("SYS_PLAN")) {
      sign = "-JDKZ-";
    }

    if (DataTypeUtil.validate(projId))
    {
      String temp_folder = "2B44E7C053F64B24877F6E79775F4AFD";
      String sql_km0 = "SELECT FOLDER_ID FROM KM_FOLDER  WHERE DOC_GRADE='2' AND DELETED_FLAG!='1' AND PROJ_ID='" + projId + "' AND PARENT_FOLDER_ID = '" + temp_folder + "'  ORDER BY SEQ_NUM ASC";
      String folder_id0 = (String)this.baseJdbcDao.getFieldValue(sql_km0);
      String sql_km1 = " SELECT FOLDER_ID FROM KM_FOLDER WHERE DOC_GRADE = '2' AND DELETED_FLAG != '1' AND PROJ_ID = '" + projId + "' AND PARENT_FOLDER_ID ='" + folder_id0 + "' AND DEF_CODE='" + sign + "' ORDER BY SEQ_NUM ASC";
      String folder_id1 = (String)this.baseJdbcDao.getFieldValue(sql_km1);

      String sql0 = "SELECT PLAN_ID FROM " + tableName + "  WHERE PARENT_PLAN_ID = '0'  AND PROJ_ID='" + projId + "' ORDER BY NODE_TYPE DESC,SEQ_NUM";
      String plan_id0 = (String)this.baseJdbcDao.getFieldValue(sql0);
      String sql1 = "SELECT A.PLAN_ID,CONCAT(A.PLAN_SHORT_NAME,A.PLAN_NAME) TITLE,A.PLAN_SHORT_NAME DEF_CODE,A.NODE_TYPE,A.CREATE_USER_ID,DOC_SWBS_ID,A.SEQ_NUM FROM " + tableName + " A  WHERE PARENT_PLAN_ID = '" + plan_id0 + "'  AND PROJ_ID='" + projId + "' ORDER BY NODE_TYPE DESC,SEQ_NUM";
      listmap = this.baseJdbcDao.queryListMap(sql1);
      String seqNum1;
      for (Map<String,Object> map : listmap)
      {
        seqNum1 = "";
        seqNum1 = DataTypeUtil.formatDbColumn(map.get("SEQ_NUM"));
        String plan_id = DataTypeUtil.formatDbColumn(map.get("PLAN_ID"));
        String title = DataTypeUtil.formatDbColumn(map.get("TITLE"));
        String def_code = DataTypeUtil.formatDbColumn(map.get("DEF_CODE") + "-");
        String folderName = getTempFileName();
        String folder_id_path = temp_folder + "," + folder_id0 + "," + folder_id1 + "," + plan_id;
        String insertSql = "INSERT INTO KM_FOLDER(Wid,CREATED_DATE,FOLDER_ID,PARENT_FOLDER_ID,TITLE,DEF_CODE,PROJ_ID,FOLDER_NAME,CREATED_BY,FOLDER_ID_PATH,DOC_GRADE,SEQ_NUM) values('" + Guid.getGuid() + "',now(),'" + plan_id + "','" + folder_id1 + "','" + title + "','" + def_code + "','" + projId + "','" + folderName + "','" + user_id + "','" + folder_id_path + "','2','" + seqNum1 + "')";
        this.baseJdbcDao.insert(insertSql);
      }
      listmap.clear();
      String sql = "SELECT E.PLAN_ID,E.PARENT_PLAN_ID,CONCAT(E.PLAN_SHORT_NAME,E.PLAN_NAME) TITLE,E.PLAN_SHORT_NAME DEF_CODE,REPLACE(E.PLAN_ID_PATH,'" + plan_id0 + "','" + folder_id1 + "') PLAN_ID_PATH ,E.SEQ_NUM FROM " + tableName + " D," + tableName + " E WHERE D.PLAN_ID=E.PARENT_PLAN_ID AND D.PARENT_PLAN_ID!='0' AND D.PROJ_ID = '" + projId + "' ORDER BY D.NODE_TYPE DESC, D.SEQ_NUM";
      listmap = this.baseJdbcDao.queryListMap(sql);
      for (Map<String,Object> map : listmap)
      {
        String seqNum11 = DataTypeUtil.formatDbColumn(map.get("SEQ_NUM"));//修改seqNum1
        String plan_id = DataTypeUtil.formatDbColumn(map.get("PLAN_ID"));
        String parent_plan_id = DataTypeUtil.formatDbColumn(map.get("PARENT_PLAN_ID"));
        String title = DataTypeUtil.formatDbColumn(map.get("TITLE"));
        String def_code = DataTypeUtil.formatDbColumn(map.get("DEF_CODE") + "-");
        String folderName = getTempFileName();
        String folder_id_path = temp_folder + "," + folder_id0 + "," + DataTypeUtil.formatDbColumn(map.get("PLAN_ID_PATH"));
        String insertSql = "INSERT INTO KM_FOLDER(Wid,CREATED_DATE,FOLDER_ID,PARENT_FOLDER_ID,TITLE,DEF_CODE,PROJ_ID,FOLDER_NAME,CREATED_BY,FOLDER_ID_PATH,DOC_GRADE,SEQ_NUM) VALUES('" + Guid.getGuid() + "',now(),'" + plan_id + "','" + parent_plan_id + "','" + title + "','" + def_code + "','" + projId + "','" + folderName + "','" + user_id + "','" + folder_id_path + "','2','" + seqNum11 + "')";
        this.baseJdbcDao.insert(insertSql);
      }
    }

    return "加载成功！";
  }

  public static String getTempFileName() {
    int i = (int)Math.floor(Math.random() * 1000.0D);
    String str = DateUtil.getTodaytime().replace("-", "").replace(" ", "").replace(":", "") + i;
    return str;
  }

  public CmProj GetProj(CmProj cmProj) {
    String sql = "select * from cm_proj where proj_id = '" + cmProj.getProjId() + "'";
    Map map = this.baseJdbcDao.queryMap(sql);
    if (DataTypeUtil.validate(map)) {
      cmProj.setProjShortName(map.get("PROJ_SHORT_NAME").toString());
      cmProj.setProjName(map.get("PROJ_NAME").toString());
      cmProj.setSeqNum(Integer.valueOf(DataTypeUtil.formatDbColumn(map.get("SEQ_NUM"))));
      cmProj.setProjCmptPct(DataTypeUtil.formatDbColumn(map.get("PROJ_CMPT_PCT")));
      cmProj.setProceedDate((Date)map.get("PROCEED_DATE"));
      cmProj.setCompletionDate((Date)map.get("COMPLETION_DATE"));
      cmProj.setSiteLocation(DataTypeUtil.formatDbColumn(map.get("SITE_LOCATION")));
      cmProj.setProjStage(DataTypeUtil.formatDbColumn(map.get("PROJ_STAGE")));
      cmProj.setProjStage1(DataTypeUtil.formatDbColumn(map.get("PROJ_STAGE1")));
      cmProj.setContractTitle(DataTypeUtil.formatDbColumn(map.get("CONTRACT_TITLE")));
      cmProj.setVnmtId(DataTypeUtil.formatDbColumn(map.get("VNMT_ID")));
      cmProj.setSupervisor(DataTypeUtil.formatDbColumn(map.get("SUPERVISOR")));
      cmProj.setSupervisorPhone(DataTypeUtil.formatDbColumn(map.get("SUPERVISOR_PHONE")));
      cmProj.setProjPlace(DataTypeUtil.formatDbColumn(map.get("PROJ_PLACE")));
      cmProj.setMark(DataTypeUtil.formatDbColumn(map.get("MARK")));
      cmProj.setRadius(DataTypeUtil.formatDbColumn(map.get("RADIUS")));
    }
    return cmProj;
  }

  public String UpdateProjInfo(CmProj cmProj)
  {
    String result = "";
    if ((!DataTypeUtil.validate(cmProj.getProjShortName())) || (!DataTypeUtil.validate(cmProj.getProjName()))) {
      return "项目代码、项目名称任一不能为空！";
    }
    String sql = "select count(wid) from cm_proj where proj_short_name='" + cmProj.getProjShortName() + "' and proj_id !='" + cmProj.getProjId() + "' and proj_node_flag='Y'";
    if (this.baseJdbcDao.getCount(sql) > 0) {
      return "项目代码已存在！";
    }
    if (DataTypeUtil.validate(cmProj.getProjPlace())) {
      String projplace = cmProj.getProjPlace();
      if (!projplace.startsWith("\\'")) {
        cmProj.setProjPlace(projplace.replaceAll("'", "\\\\'"));
      }
    }
    sql = "UPDATE CM_PROJ SET PROJ_SHORT_NAME ='" + cmProj.getProjShortName() + "',PROJ_NAME = '" + cmProj.getProjName() + "',PROJ_CMPT_PCT='" + cmProj.getProjCmptPct() + "'," + 
      " PROCEED_DATE = " + (DataTypeUtil.validate(cmProj.getProceedDate()) ? "'" + cmProj.getProceedDate() + "'" : null) + "," + 
      " COMPLETION_DATE = " + (DataTypeUtil.validate(cmProj.getCompletionDate()) ? "'" + cmProj.getCompletionDate() + "'" : null) + "," + 
      " SITE_LOCATION='" + cmProj.getSiteLocation() + "',PROJ_STAGE='" + cmProj.getProjStage() + "' ,PROJ_STAGE1='" + cmProj.getProjStage1() + "'," + 
      " CONTRACT_TITLE='" + cmProj.getContractTitle() + "',VNMT_ID='" + cmProj.getVnmtId() + "',SUPERVISOR='" + cmProj.getSupervisor() + "',MARK = '" + cmProj.getMark() + "',RADIUS='" + cmProj.getRadius() + "'," + 
      " SUPERVISOR_PHONE='" + cmProj.getSupervisorPhone() + "',PROJ_PLACE='" + cmProj.getProjPlace() + "'" + 
      " WHERE PROJ_ID ='" + cmProj.getProjId() + "'";
    if (this.baseJdbcDao.update(sql)) {
      sql = " update ds_gcgk set PLAN_SHORT_NAME ='" + cmProj.getProjShortName() + "',PLAN_NAME='" + cmProj.getProjName() + "'  where parent_plan_id = '0' and proj_id = '" + cmProj.getProjId() + "'";
      this.baseJdbcDao.update(sql);
      sql = " update ds_plan set PLAN_SHORT_NAME ='" + cmProj.getProjShortName() + "',PLAN_NAME='" + cmProj.getProjName() + "'  where parent_plan_id = '0' and proj_id = '" + cmProj.getProjId() + "'";
      this.baseJdbcDao.update(sql);
      sql = " update sm_plan set PLAN_SHORT_NAME ='" + cmProj.getProjShortName() + "',PLAN_NAME='" + cmProj.getProjName() + "'  where parent_plan_id = '0' and proj_id = '" + cmProj.getProjId() + "'";
      this.baseJdbcDao.update(sql);
      sql = " update sm_test set PLAN_SHORT_NAME ='" + cmProj.getProjShortName() + "',PLAN_NAME='" + cmProj.getProjName() + "'  where parent_plan_id = '0' and proj_id = '" + cmProj.getProjId() + "'";
      this.baseJdbcDao.update(sql);
      sql = " update sys_plan set PLAN_SHORT_NAME ='" + cmProj.getProjShortName() + "',PLAN_NAME='" + cmProj.getProjName() + "'  where parent_plan_id = '0' and proj_id = '" + cmProj.getProjId() + "'";
      this.baseJdbcDao.update(sql);
      sql = " update km_folder set def_code ='" + cmProj.getProjShortName() + "',title='" + cmProj.getProjName() + "'  where parent_folder_id = '2B44E7C053F64B24877F6E79775F4AFD'  and proj_id = '" + cmProj.getProjId() + "'";
      this.baseJdbcDao.update(sql);
      result = "success";
    }
    return result;
  }

  public List<Map<String, Object>> GetProjSurvey(String projId)
  {
    String sql = "select column_code ,column_name from cm_column where proj_stage =( select proj_stage from cm_proj where proj_id ='" + projId + "') and enabled ='1' order by seq_num";
    List<Map<String, Object>> mapInfo = this.baseJdbcDao.queryListMap(sql);
    if (DataTypeUtil.validate(mapInfo))
    {
      for (Map<String, Object> map : mapInfo) {
        String column_code = map.get("COLUMN_CODE").toString();
        String value = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue("select " + column_code + " from CM_PROJ_SURVEY where proj_id ='" + projId + "'"));
        map.put("COLUMN_VALUE", value);
      }
    }

    return mapInfo;
  }

  public CmProjSurvey GetProjSurveyInfo(CmProjSurvey cmProjSurvey)
  {
    Map mapInfo = this.baseJdbcDao.queryMap("select * from CM_PROJ_SURVEY where proj_id ='" + cmProjSurvey.getProjId() + "'");
    if (DataTypeUtil.validate(mapInfo))
    {
      String PROJ_FORM = DataTypeUtil.formatDbColumn(mapInfo.get("PROJ_FORM"));
      String PROJ_QUALITY_DEST = DataTypeUtil.formatDbColumn(mapInfo.get("PROJ_QUALITY_DEST"));
      String GROSS_INVEST = DataTypeUtil.formatDbColumn(mapInfo.get("GROSS_INVEST"));
      String SCALE = DataTypeUtil.formatDbColumn(mapInfo.get("SCALE"));
      String PART_PROJ_INVEST = DataTypeUtil.formatDbColumn(mapInfo.get("PART_PROJ_INVEST"));
      String CULTURE_SITE = DataTypeUtil.formatDbColumn(mapInfo.get("CULTURE_SITE"));
      cmProjSurvey.setProjForm(PROJ_FORM);
      cmProjSurvey.setProjQualityDest(PROJ_QUALITY_DEST);
      cmProjSurvey.setGrossInvest(GROSS_INVEST);
      cmProjSurvey.setScale(SCALE);
      cmProjSurvey.setPartProjInvest(PART_PROJ_INVEST);
      cmProjSurvey.setCultureSite(CULTURE_SITE);
    }
    return cmProjSurvey;
  }

  public String UpdateProjSurveyInfo(String prames, String proj_id)
  {
    String[] strs = prames.split("@@@");
    String sqlSet = "";
    for (int i = 0; i < strs.length; i++) {
      sqlSet = sqlSet + "," + strs[i].split("=")[0] + "='" + (strs[i].split("=").length > 1 ? strs[i].split("=")[1] : "") + "'";
    }

    if (this.baseJdbcDao.getCount("select count(wid) from CM_PROJ_SURVEY WHERE PROJ_ID='" + proj_id + "'") == 0) {
      this.baseJdbcDao.insert("insert into CM_PROJ_SURVEY(wid,proj_survey_id,proj_id)values('" + Guid.getGuid() + "','" + Guid.getGuid() + "','" + proj_id + "')");
    }
    String sql = "update CM_PROJ_SURVEY set " + sqlSet + " WHERE PROJ_ID='" + proj_id + "'";
    if (this.baseJdbcDao.update(sql)) {
      return "success";
    }
    return "error";
  }

  public List ProjCompanyTable(String projId)
  {
    String sql = "  SELECT * FROM ( SELECT A.WID,A.PROJ_ID,B.VNMT_ID,B.COMPANY_ABBREV,B.COMPANY_NAME,(SELECT CODE_NAME FROM CM_CODE WHERE CODE_TYPE='PROJ_ROLE' AND CODE_SHORT_NAME = B.ROLE_ID) AS ROLE_ID FROM CM_PROJ_JOIN A LEFT OUTER JOIN CM_VNMT B ON A.VNMT_ID =B.VNMT_ID ORDER BY COMPANY_ABBREV) Z WHERE PROJ_ID='" + 
      projId + "'";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public List AddVnmtTable(String projId) {
    String sql = "  SELECT WID, VNMT_ID, ROLE_ID, COMPANY_NAME, COMPANY_ABBREV  FROM CM_VNMT WHERE VNMT_ID NOT IN (SELECT VNMT_ID   FROM CM_PROJ_JOIN   WHERE PROJ_ID = '" + 
      projId + "')" + 
      " ORDER BY ROLE_ID, COMPANY_ABBREV";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public String AddVnmt(String projId, String vnmtIds)
  {
    String[] datas = vnmtIds.split(",");
    String roleId = "";
    String vnmtId = "";
    for (String data : datas)
    {
      vnmtId = data.split(",")[0];
      String sql = " INSERT INTO CM_PROJ_JOIN (WID, PROJ_ID, VNMT_ID, ROLE_ID, DESCRIPTION, COMPANY_NAME, COMPANY_ABBREV, DEFAULT_LINKMAN, OTHER_VNMT_ID, ISDEFAULT)  select UUID(),'" + 
        projId + "', vnmt_id, role_id, remark, company_name, company_abbrev, default_linkman, '', isdefault from cm_vnmt where vnmt_id = '" + vnmtId + "'";
      this.baseJdbcDao.insert(sql);
    }

    return "success";
  }

  public String delVnmt(String wids)
  {
    String sql = "delete from CM_PROJ_JOIN where wid in ('" + wids.replaceAll(",", "','") + "')";
    this.baseJdbcDao.delete(sql);

    return "success";
  }

  public List ProjTable(UserInfo user) {
    String sql = " SELECT * from (SELECT  A.WID, A.PROJ_SHORT_NAME, A.PROJ_NAME,A.PROJ_CMPT_PCT,A.CONTRACT_TITLE, date_format(A.PROCEED_DATE,'%Y-%m-%d')PROCEED_DATE, date_format(A.COMPLETION_DATE,'%Y-%m-%d') COMPLETION_DATE, A.PROJ_ID,(SELECT E.PROJ_NAME FROM CM_PROJ E WHERE E.PROJ_ID=A.PARENT_PROJ_ID) as DEPT_NAME,D.CODE_NAME AS STAGE_NAME  FROM CM_PROJ A LEFT OUTER JOIN CM_CODE D on  D.CODE_TYPE='PROJ_STAGE' AND D.CODE_SHORT_NAME=A.PROJ_STAGE where A.PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID='" + 
      user.getUserId() + "' " + 
      " OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID='" + user.getUserId() + "')) AND A.PROJ_NODE_FLAG='Y' ) AA ORDER BY DEPT_NAME";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public List ProjPeopleTable(String projId)
  {
    String sql = " SELECT ID,ACTUAL_NAME,CASE SEX WHEN '1' THEN 'Ů' ELSE '��' END AS SEX, (SELECT CODE_NAME FROM CM_CODE WHERE CODE_TYPE='JOB_TITLE' AND ID =T.JOB_TITLE) JOB_TITLE,TEL,(SELECT DEPT_NAME FROM CM_DEPART WHERE PARENT_DEPT_NO='0' AND DEPT_TYPE='SYS_DEPART' AND DEPT_NO =T.DEPT_NO) DEPT_NO FROM CM_PROJPART T WHERE proj_id='" + projId + "' ORDER BY ACTUAL_NAME ASC ";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public String AddPeople(CmProjpart cmProjpart)
  {
    String sql = " INSERT INTO CM_PROJPART(WID,ID,ACTUAL_NAME,SEX,TEL,DEPT_NO,JOB_TITLE,PROJ_ID,SEQ_NUM) VALUES ('" + Guid.getGuid() + "','" + Guid.getGuid() + "','" + cmProjpart.getActualName() + "','" + cmProjpart.getSex() + "','" + cmProjpart.getTel() + "','" + cmProjpart.getDeptNo() + "','" + cmProjpart.getJobTitle() + "','" + cmProjpart.getProjId() + "'," + cmProjpart.getSeqNum() + ")";
    this.baseJdbcDao.insert(sql);
    return "success";
  }

  public String delPeople(String ids)
  {
    String sql = "delete from CM_PROJPART where id in ('" + ids.replaceAll(",", "','") + "')";
    this.baseJdbcDao.delete(sql);

    return "success";
  }

  public Map<String, Object> GetProjPart(String id)
  {
    String sql = "select ID,ACTUAL_NAME,SEX,TEL,DEPT_NO,JOB_TITLE,PROJ_ID,SEQ_NUM from CM_PROJPART where id ='" + id + "'";
    return this.baseJdbcDao.queryMap(sql);
  }

  public String SavePeople(CmProjpart cmProjpart)
  {
    String sql = "update CM_PROJPART set ACTUAL_NAME='" + cmProjpart.getActualName() + "',SEX='" + cmProjpart.getSex() + "',TEL='" + cmProjpart.getTel() + "',DEPT_NO='" + cmProjpart.getDeptNo() + "',JOB_TITLE='" + cmProjpart.getJobTitle() + "',SEQ_NUM=" + cmProjpart.getSeqNum() + " where id = '" + cmProjpart.getId() + "'";
    this.baseJdbcDao.update(sql);
    return "success";
  }

  public List ProjUserTable(String projId)
  {
    String sql = " SELECT '' RES,A.USER_ID , A.USER_NAME, A.ACTUAL_NAME, OF_ROLE,D.PROFILE_ID,(SELECT PROFILE_NAME FROM CM_PROFILE WHERE PROFILE_ID = D.PROFILE_ID) AS PROFILE_NAME FROM(SELECT USER_ID, USER_NAME, ACTUAL_NAME, '��' AS OF_ROLE  FROM CM_USERS WHERE USER_ID IN(SELECT USER_ID FROM CM_RLUSER WHERE ROLE_ID IN (SELECT ROLE_ID FROM CM_USERPROJ WHERE USER_FLAG = '0' AND PROJ_ID = '" + 
      projId + "')) " + 
      " UNION SELECT USER_ID, USER_NAME, ACTUAL_NAME, '' AS OF_ROLE  " + 
      " FROM CM_USERS WHERE USER_ID NOT IN(SELECT USER_ID FROM CM_RLUSER WHERE ROLE_ID IN (SELECT ROLE_ID FROM CM_USERPROJ WHERE USER_FLAG = '0' AND PROJ_ID = '" + projId + "')) AND USER_ID IN(SELECT USER_ID FROM CM_USERPROJ WHERE USER_FLAG = '1'" + 
      " AND PROJ_ID = '" + projId + "')" + 
      " )" + 
      " A  LEFT JOIN CM_USERPROF D ON D.USER_ID = A.USER_ID AND D.PROJ_ID = '" + projId + "' ORDER BY USER_NAME";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public List GetProfileType(String value)
  {
    String sql = "SELECT * FROM (SELECT REMARK, PROF_TYPE, PROFILE_ID, PROFILE_NAME, (CASE ISDEFAULT WHEN '1' THEN '��' ELSE '' END) AS ISDEF FROM CM_PROFILE ORDER BY PROFILE_NAME) Z WHERE profile_id != '1' and prof_type = '1'";

    List<Map<String, Object>> res = this.baseJdbcDao.queryListMap(sql);
    for (Map<String, Object> map : res) {
      if ((DataTypeUtil.validate(value)) && (map.get("PROFILE_NAME").toString().equals(value))) {
        map.put("selected", Boolean.valueOf(true));
      }
    }
    return res;
  }

  public String SetProfileType(String id, String projId, String user_id)
  {
    if (this.baseJdbcDao.getCount("SELECT COUNT(WID)  FROM CM_USERPROF WHERE USER_ID='" + user_id + "' AND PROJ_ID='" + projId + "' ") > 0)
      this.baseJdbcDao.update(" UPDATE CM_USERPROF SET PROFILE_ID='" + id + "' WHERE USER_ID='" + user_id + "' AND PROJ_ID='" + projId + "' ");
    else {
      this.baseJdbcDao.insert(" INSERT INTO CM_USERPROF(WID,USER_ID,PROJ_ID,PROFILE_ID) VALUES('" + Guid.getGuid() + "','" + user_id + "','" + projId + "','" + id + "')");
    }
    return "success";
  }

  public List AddUserTable(String projId)
  {
    String sql = " SELECT USER_ID,USER_NAME,ACTUAL_NAME,PROFILE_ID,(SELECT PROFILE_NAME FROM CM_PROFILE WHERE PROFILE_ID =C.PROFILE_ID ) PROFILE_NAME  FROM CM_USERS C  WHERE USER_ID NOT IN(SELECT USER_ID FROM CM_USERPROJ WHERE USER_FLAG='1' AND PROJ_ID='" + 
      projId + "') AND USER_ID NOT IN (SELECT B.USER_ID FROM CM_USERS B WHERE B.DISABLE_FLAG = 1)        and USER_ID NOT IN (SELECT USER_ID from cm_rluser where role_id in (select role_id  FROM CM_USERPROJ  WHERE USER_FLAG = '0' AND PROJ_ID = '" + projId + "'))" + 
      " ORDER BY USER_NAME";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public String delUser(String userIds, String projId)
  {
    String sql = " DELETE FROM CM_USERPROF WHERE USER_ID IN('" + userIds.replaceAll(",", "','") + "') AND PROJ_ID IN('" + projId + "')";
    this.baseJdbcDao.delete(sql);
    sql = "  DELETE FROM CM_USERPROJ WHERE USER_ID IN('" + userIds.replaceAll(",", "','") + "') AND PROJ_ID IN('" + projId + "')";
    this.baseJdbcDao.delete(sql);

    sql = "   DELETE FROM SM_PLANUSER WHERE USER_ID IN('" + userIds.replaceAll(",", "','") + "') AND PROJ_ID IN('" + projId + "')";
    this.baseJdbcDao.delete(sql);

    return "success";
  }

  public String SetAllUser(String id, String projId, String userIds)
  {
    String[] ids = userIds.split(",");
    for (int i = 0; i < ids.length; i++) {
      String user_id = ids[i];
      if (this.baseJdbcDao.getCount("SELECT COUNT(WID)  FROM CM_USERPROF WHERE USER_ID='" + user_id + "' AND PROJ_ID='" + projId + "' ") > 0)
        this.baseJdbcDao.update(" UPDATE CM_USERPROF SET PROFILE_ID='" + id + "' WHERE USER_ID='" + user_id + "' AND PROJ_ID='" + projId + "' ");
      else {
        this.baseJdbcDao.insert(" INSERT INTO CM_USERPROF(WID,USER_ID,PROJ_ID,PROFILE_ID) VALUES('" + Guid.getGuid() + "','" + user_id + "','" + projId + "','" + id + "')");
      }

    }

    return "success";
  }

  public List seeAssignTable(String profileId, String where)
  {
    where = "AND SUBSYS_CODE IN('" + where.replace(",", "','") + "')";
    String sql = "SELECT * FROM ( SELECT A.WID,B.PROFILE_ID, A.DATACTR_TYPE, A.DATACTR_CODE, A.SUBSYS_CODE, A.DATACTR_NAME, (SELECT MODULE_NAME FROM CM_MODULE WHERE MODULE_CODE = A.MODULE_CODE) AS MNAME, (SELECT MODULE_NAME FROM CM_MODULE WHERE MODULE_CODE = A.SUBSYS_CODE) AS SUB_NAME, A.REMARK, A.SEQ_NUM, CASE WHEN B.PROFILE_ID IS NULL THEN '0' ELSE '1' END AS ENABLED_FLAG FROM CM_DATACTR A  RIGHT JOIN CM_PROFCTR B ON B.DATACTR_CODE = A.DATACTR_CODE AND B.PROFILE_ID = '" + 
      profileId + "') Z WHERE DATACTR_TYPE='1' " + where + " ORDER BY SUB_NAME";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public String DelProj(String projId)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("DELETE FROM CM_CATBASE WHERE  BASE_MASTER_KEY IN (SELECT PROJ_ID FROM CM_PROJ WHERE PROJ_ID = '" + projId + "')");
    localArrayList.add("delete from pic_table where proj_id ='" + projId + "'");
    localArrayList.add("DELETE FROM CM_PROJ_JOIN WHERE PROJ_ID='" + projId + "'");
    localArrayList.add("DELETE FROM CM_PROJRCNT WHERE PROJ_ID='" + projId + "'");
    localArrayList.add("DELETE FROM CM_USERPROJ WHERE PROJ_ID='" + projId + "'");
    localArrayList.add("DELETE FROM CM_USERPROF WHERE PROJ_ID='" + projId + "'");
    localArrayList.add("DELETE FROM CM_DOCLINK WHERE PROJ_ID='" + projId + "'");
    localArrayList.add("DELETE FROM KM_DOCVER WHERE DOC_ID IN (SELECT DOC_ID FROM KM_DOC WHERE PROJ_ID ='" + projId + "')");
    localArrayList.add("DELETE FROM KM_DOCLOG WHERE DOC_ID IN (SELECT DOC_ID FROM KM_DOC WHERE PROJ_ID ='" + projId + "')");
    localArrayList.add("DELETE FROM KM_ATTCH WHERE DOC_ID IN (SELECT DOC_ID FROM KM_DOC WHERE PROJ_ID ='" + projId + "')");
    localArrayList.add("DELETE FROM KM_FOLDER WHERE PROJ_ID='" + projId + "'");
    localArrayList.add("DELETE FROM KM_DOC WHERE PROJ_ID='" + projId + "'");
    localArrayList.add("DELETE FROM SM_PLAN WHERE PROJ_ID='" + projId + "'");
    localArrayList.add("DELETE FROM DS_PLAN WHERE PROJ_ID='" + projId + "'");
    localArrayList.add("DELETE FROM DS_GCGK WHERE PROJ_ID='" + projId + "'");
    localArrayList.add("DELETE FROM SM_TEST WHERE PROJ_ID='" + projId + "'");
    localArrayList.add("DELETE FROM SYS_PLAN WHERE PROJ_ID='" + projId + "'");
    localArrayList.add("DELETE FROM SM_PLANUSER WHERE PROJ_ID='" + projId + "'");
    localArrayList.add("DELETE FROM CM_PROJ WHERE PROJ_ID='" + projId + "'");

    for (int i = 0; i < localArrayList.size(); i++) {
      this.baseJdbcDao.delete((String)localArrayList.get(i));
    }

    String sql = "select IS_SPECIAL_PRO  from cm_proj where proj_id= '" + projId + "'";
    String a = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
    if (a.equals("1")) {
      sql = "select proj_name  from cm_proj where proj_id= '" + projId + "'";

      String name = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
      sql = "delete from CM_MODULE where module_name = '" + name + "'";
      this.baseJdbcDao.delete(sql);
    }

    String path = AppSetting.PROJECT_PATH;
    if (DataTypeUtil.validate(projId)) {
      String imgPath = path + "/KM/DOC/project/" + projId;
      if (DataTypeUtil.validate(projId)) {
        FileUtil.delFolder(imgPath);
      }
    }
    return "success";
  }

  public List ShowProjColumn(String projStage)
  {
    String sql = "select * from CM_COLUMN t where t.proj_stage ='" + projStage + "' order by seq_num asc";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public String CheckProjNum(HttpServletRequest request)
  {
    String str2 = this.baseJdbcService.getStringByLicense("ProjNum");
    int projNum = this.baseJdbcDao.getCount("select count(wid) num from cm_proj where proj_node_flag = 'Y'");
    if ((DataTypeUtil.validate(str2)) && 
      (Integer.valueOf(str2).intValue() <= projNum)) {
      return "超出许可允许的项目个数，请联系管理员！";
    }

    return null;
  }

  public String MoveEps(String parentId, String orginId, String userId)
  {
    if ((DataTypeUtil.validate(parentId)) && (DataTypeUtil.validate(orginId)))
    {
      String sql = "SELECT PARENT_PROJ_ID FROM CM_PROJ WHERE PROJ_ID='" + orginId + "'";
      String orgin_parentId = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
      if (parentId.equals(orgin_parentId)) {
        return "success";
      }
      sql = "SELECT PARENT_PATH FROM CM_PROJ WHERE PROJ_ID='" + orginId + "'";
      String source_listmd_id_path = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
      sql = "SELECT PARENT_PATH FROM CM_PROJ WHERE PROJ_ID='" + parentId + "'";
      String aim_listmd_id_path = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
      if ((DataTypeUtil.validate(source_listmd_id_path)) && (DataTypeUtil.validate(aim_listmd_id_path)))
      {
        if (aim_listmd_id_path.contains(orginId))
          return "移动的目标节点不能是源节点或源节点的子节点!";
        sql = "SELECT COUNT(WID) NUM FROM CM_PROJ WHERE INSTR(PARENT_PATH,'" + orginId + "') > 0 AND PROJ_NODE_FLAG = 'Y'";
        if (this.baseJdbcDao.getCount(sql) > 0) {
          return "移动的节点下存在项目，禁止移动!";
        }

        sql = "UPDATE CM_PROJ SET PARENT_PATH=REPLACE(PARENT_PATH,'" + source_listmd_id_path + "','" + aim_listmd_id_path + ";" + orginId + "')" + 
          " WHERE INSTR(PARENT_PATH,'" + source_listmd_id_path + "')>0";
        this.baseJdbcDao.update(sql);

        this.baseJdbcDao.delete("delete  from cm_userproj where proj_id in (SELECT proj_id NUM FROM CM_PROJ WHERE INSTR(PARENT_PATH,'" + orginId + "') > 0 ) ");
        this.baseJdbcDao.delete("delete from CM_USERPROF where proj_id='" + orginId + "'");
        sql = "UPDATE CM_PROJ SET PARENT_PROJ_ID='" + parentId + "' WHERE PROJ_ID='" + orginId + "'";
        this.baseJdbcDao.update(sql);

        String rolesql = "select distinct role_id from cm_userproj t  where PROJ_ID = '" + parentId + "'  and user_flag ='0'";
        List<Map<String, Object>> listrole = this.baseJdbcDao.queryListMap(rolesql);
        if (DataTypeUtil.validate(listrole)) {
          for (Map<String, Object> maprole : listrole) {
            maprole.put("WID", Guid.getGuid());
            maprole.put("USERPROJ_ID", Guid.getGuid());
            maprole.put("USER_ID", "0");
            maprole.put("PROJ_ID", orginId);
            maprole.put("USER_FLAG", "0");
            try {
              this.baseJdbcDao.insertMapInfo(maprole, "CM_USERPROJ");
            }
            catch (SQLException e) {
              e.printStackTrace();
            }
          }
        }
        String roleusersql = "SELECT DISTINCT USER_ID FROM CM_USERPROJ    WHERE PROJ_ID = '" + parentId + "'  AND USER_FLAG ='1'";
        List<Map<String, Object>> listroleuser = this.baseJdbcDao.queryListMap(roleusersql);
        if (DataTypeUtil.validate(listroleuser)) {
          for (Map<String, Object> roleuser : listroleuser) {
            roleuser.put("WID", Guid.getGuid());
            roleuser.put("USERPROJ_ID", Guid.getGuid());
            roleuser.put("ROLE_ID", "0");
            roleuser.put("PROJ_ID", orginId);
            roleuser.put("USER_FLAG", "1");
            try {
              this.baseJdbcDao.insertMapInfo(roleuser, "CM_USERPROJ");
            }
            catch (SQLException e) {
              e.printStackTrace();
            }
          }
        }

      }
      else if (parentId.equals("0")) {
        sql = "UPDATE CM_PROJ SET PARENT_PATH=REPLACE(PARENT_PATH,'" + source_listmd_id_path + "','" + orginId + "')" + 
          " WHERE INSTR(PARENT_PATH,'" + source_listmd_id_path + "')>0";
        this.baseJdbcDao.update(sql);

        this.baseJdbcDao.delete("delete  from cm_userproj where proj_id in (SELECT proj_id NUM FROM CM_PROJ WHERE INSTR(PARENT_PATH,'" + orginId + "') > 0 ) ");
        this.baseJdbcDao.delete("delete from CM_USERPROF where proj_id='" + orginId + "'");
        sql = "UPDATE CM_PROJ SET PARENT_PROJ_ID='" + parentId + "' WHERE PROJ_ID='" + orginId + "'";
        this.baseJdbcDao.update(sql);
      }
    }

    return "success";
  }

  public String MoveProj(String parentId, String orginId, String userId)
  {
    if ((DataTypeUtil.validate(parentId)) && (DataTypeUtil.validate(orginId)))
    {
      String sql = "SELECT PARENT_PROJ_ID FROM CM_PROJ WHERE PROJ_ID='" + orginId + "'";
      String orgin_parentId = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
      if (parentId.equals(orgin_parentId)) {
        return "success";
      }
      sql = "SELECT PARENT_PATH FROM CM_PROJ WHERE PROJ_ID='" + orginId + "'";
      String source_listmd_id_path = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
      sql = "SELECT PARENT_PATH FROM CM_PROJ WHERE PROJ_ID='" + parentId + "'";
      String aim_listmd_id_path = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
      if ((DataTypeUtil.validate(source_listmd_id_path)) && (DataTypeUtil.validate(aim_listmd_id_path)))
      {
        sql = "UPDATE CM_PROJ SET PARENT_PATH=REPLACE(PARENT_PATH,'" + source_listmd_id_path + "','" + aim_listmd_id_path + ";" + orginId + "')" + 
          " WHERE INSTR(PARENT_PATH,'" + source_listmd_id_path + "')>0";
        this.baseJdbcDao.update(sql);

        sql = "UPDATE CM_PROJ SET PARENT_PROJ_ID='" + parentId + "' WHERE PROJ_ID='" + orginId + "'";
        this.baseJdbcDao.update(sql);
      }

    }

    return "success";
  }

  public String CopyPlan(String parentId, String orginId, String tableName, String projId)
  {
    this.newStringIds = "";
    if ((DataTypeUtil.validate(parentId)) && (DataTypeUtil.validate(orginId))) {
      String sql = "select plan_id_path from " + tableName + " where plan_id = '" + parentId + "' and proj_id = '" + projId + "'";
      String parentPath = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
      sql = "select folder_id_path from km_folder where folder_id = '" + parentId + "' and proj_id = '" + projId + "'";
      String parentFolderPath = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
      String parentFolderId = parentId;
      if (!DataTypeUtil.validate(parentFolderPath)) {
        String sign = "";
        if (tableName.toUpperCase().equals("DS_PLAN"))
          sign = "-JSCX-";
        else if (tableName.toUpperCase().equals("SM_PLAN"))
          sign = "-ZLAQ-";
        else if (tableName.toUpperCase().equals("SM_TEST"))
          sign = "-HTZJ-";
        else if (tableName.toUpperCase().equals("SYS_PLAN")) {
          sign = "-JDKZ-";
        }
        String temp_folder = "2B44E7C053F64B24877F6E79775F4AFD";
        String sql_km0 = "SELECT FOLDER_ID FROM KM_FOLDER  WHERE DOC_GRADE='2' AND DELETED_FLAG!='1' AND PROJ_ID='" + projId + "' AND PARENT_FOLDER_ID = '" + temp_folder + "'  ORDER BY SEQ_NUM ASC";
        String folder_id0 = (String)this.baseJdbcDao.getFieldValue(sql_km0);
        String sql_km1 = " SELECT FOLDER_ID FROM KM_FOLDER WHERE DOC_GRADE = '2' AND DELETED_FLAG != '1' AND PROJ_ID = '" + projId + "' AND PARENT_FOLDER_ID ='" + folder_id0 + "' AND DEF_CODE='" + sign + "' ORDER BY SEQ_NUM ASC";
        parentFolderId = (String)this.baseJdbcDao.getFieldValue(sql_km1);
        sql = "SELECT FOLDER_ID_PATH FROM KM_FOLDER WHERE PROJ_ID='" + projId + "' AND FOLDER_ID = '" + parentFolderId + "'";
        parentFolderPath = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
      }
      sql = "select NODE_TYPE from   " + tableName + " where plan_id = '" + orginId + "' and proj_id = '" + projId + "'";
      String node_type = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
      CopyInfo(parentId, parentPath, parentFolderId, parentFolderPath, orginId, tableName, projId, node_type);
    }
    return "success";
  }

  private void CopyInfo(String parentId, String parentPath, String parentFolderId, String parentFolderPath, String orginId, String tableName, String projId, String nodeType) {
    String newId = Guid.getGuid();
    String sql = "";
    if (!tableName.equals("DS_GCGK")) {
      sql = "INSERT INTO KM_FOLDER(WID,FOLDER_ID,PARENT_FOLDER_ID,TITLE,DEF_CODE,PROJ_ID,FOLDER_NAME,CREATED_BY,FOLDER_ID_PATH,DOC_GRADE,SEQ_NUM)   SELECT '" + 
        Guid.getGuid() + "','" + newId + "','" + parentFolderId + "',TITLE,DEF_CODE,PROJ_ID,'" + getTempFileName() + "',CREATED_BY,'" + parentFolderPath + "," + newId + "',DOC_GRADE,SEQ_NUM FROM KM_FOLDER where FOLDER_ID = '" + orginId + "' AND PROJ_ID='" + projId + "'  ";
      this.baseJdbcDao.insert(sql);

      if (nodeType.equals("SWBS")) {
        sql = "INSERT INTO " + tableName + "(WID,GRANTT_ID,GRANTT_PARENT_ID,PLAN_ID,PROJ_ID,PARENT_PLAN_ID,PLAN_SHORT_NAME,PLAN_NAME,SEQ_NUM,CREATE_USER_ID,PLAN_ID_PATH,TARGET_START_DATE,TARGET_END_DATE,NODE_TYPE,FOLDER_ID,REMARK)" + 
          " select '" + Guid.getGuid() + "',GRANTT_ID,GRANTT_PARENT_ID,'" + newId + "',PROJ_ID,'" + parentId + "',PLAN_SHORT_NAME,PLAN_NAME,SEQ_NUM,CREATE_USER_ID,'" + parentPath + "," + newId + "',TARGET_START_DATE,TARGET_END_DATE,NODE_TYPE,'" + newId + "',REMARK  FROM  " + tableName + " where plan_id = '" + orginId + "' and proj_id = '" + projId + "'";
      } else {
        sql = "INSERT INTO " + tableName + "(WID,PLAN_STATUS,GRANTT_ID,GRANTT_PARENT_ID,PLAN_ID,PROJ_ID,PARENT_PLAN_ID,PLAN_SHORT_NAME,PLAN_NAME,SEQ_NUM,CREATE_USER_ID,PLAN_ID_PATH,TARGET_START_DATE,TARGET_END_DATE,NODE_TYPE,FOLDER_ID,REMARK,ASS_GUIDES,CODE_ID)" + 
          " select '" + Guid.getGuid() + "','0',GRANTT_ID,GRANTT_PARENT_ID,'" + newId + "',PROJ_ID,'" + parentId + "',PLAN_SHORT_NAME,PLAN_NAME,SEQ_NUM,CREATE_USER_ID,'" + parentPath + "," + newId + "',TARGET_START_DATE,TARGET_END_DATE,NODE_TYPE,'" + newId + "' ,REMARK,ASS_GUIDES,CODE_ID FROM  " + tableName + " where plan_id = '" + orginId + "' and proj_id = '" + projId + "'";
        String linksql = "INSERT INTO CM_DOCLINK(wid, proj_id, doc_id, base_master_key, base_item_type, date_linked, created_by, swbs_type_id)SELECT uuid(),proj_id,doc_id,'" + 
          newId + "',base_item_type, date_linked,created_by, swbs_type_id from cm_doclink where base_master_key = '" + orginId + "' ";
        this.baseJdbcDao.insert(linksql);
      }
      this.baseJdbcDao.insert(sql);
    } else {
      sql = "INSERT INTO DS_GCGK(WID,PLAN_ID,PROJ_ID,PARENT_PLAN_ID,PLAN_ID_PATH,PLAN_SHORT_NAME,PLAN_NAME,CREATE_USER_ID,SEQ_NUM,DOC_SWBS_ID,NODE_TYPE,REMARK) SELECT  '" + 
        Guid.getGuid() + "','" + newId + "','" + projId + "','" + parentId + "','" + parentPath + ";" + newId + "',PLAN_SHORT_NAME,PLAN_NAME,CREATE_USER_ID,SEQ_NUM,DOC_SWBS_ID,NODE_TYPE,REMARK FROM   " + tableName + " where plan_id = '" + orginId + "' and proj_id = '" + projId + "'";
      this.baseJdbcDao.insert(sql);
    }
    this.newStringIds = (this.newStringIds + "," + newId);
    sql = "SELECT PLAN_ID,NODE_TYPE FROM  " + tableName + " where parent_plan_id = '" + orginId + "' and proj_id = '" + projId + "' and LOCATE(plan_id ,'" + this.newStringIds + "') =0 ";
    List<Map<String, Object>> listMap = this.baseJdbcDao.queryListMap(sql);
    if (DataTypeUtil.validate(listMap))
      for (Map<String, Object> map : listMap)
        CopyInfo(newId, parentPath + ";" + newId, newId, parentFolderPath + "," + newId, map.get("PLAN_ID").toString(), tableName, projId, map.get("NODE_TYPE").toString());
  }

  public List ShowProjByMap(UserInfo userInfo)
  {
    String sql = "SELECT PROJ_ID,MARK,PARENT_PROJ_ID,PROJ_SHORT_NAME,PROJ_NAME,IS_ZJLR_FLAG,PROJ_CMPT_PCT,date_format(PROCEED_DATE,'%Y-%m-%d')PROCEED_DATE,date_format(COMPLETION_DATE,'%Y-%m-%d')COMPLETION_DATE,PROJ_NODE_FLAG,STATUS,SEQ_NUM,(SELECT B.CODE_NAME FROM CM_CODE B WHERE B.CODE_TYPE = 'PROJ_STAGE' AND B.CODE_SHORT_NAME = A.PROJ_STAGE) AS PROJ_STAGE  FROM CM_PROJ A WHERE (PROJ_NODE_FLAG = 'N' OR ( CONTRACT_TITLE='δ�깤'  and PROJ_ID IN (SELECT PROJ_ID FROM CM_USERPROJ WHERE USER_ID = '" + userInfo.getUserId() + "' OR ROLE_ID IN (SELECT ROLE_ID FROM CM_RLUSER WHERE USER_ID = '" + userInfo.getUserId() + "'))   AND STATUS = 'JH' )) AND MARK !='' AND MARK is not null AND MARK !='null' ORDER BY A.PROJ_NODE_FLAG DESC, A.SEQ_NUM";
    return this.baseJdbcDao.queryListMap(sql);
  }
}