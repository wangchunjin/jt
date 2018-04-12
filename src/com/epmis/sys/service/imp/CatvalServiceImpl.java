package com.epmis.sys.service.imp;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.CatvalService;
import com.epmis.sys.util.Guid;
import com.epmis.sys.vo.CmCatval;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("catvalService")
public class CatvalServiceImpl
  implements CatvalService
{

  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;

  public List CatvalTableOrderByKey(String baseMasterKey)
  {
    String sql = " SELECT A.WID,A.CATG_ID,B.CATG_SHORT_NAME,B.CATG_NAME,C.CATG_TYPE_ID,C.CATG_TYPE FROM CM_CATBASE A LEFT JOIN CM_CATVAL B ON A.CATG_ID=B.CATG_ID LEFT JOIN CM_CATTYPE C ON B.CATG_TYPE_ID=C.CATG_TYPE_ID WHERE A.BASE_MASTER_KEY='" + 
      baseMasterKey + "' ORDER BY CATG_TYPE,CATG_SHORT_NAME";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public List CatvalTreeByKey(String parentId, String catgTypeId)
  {
    List<Map<String, Object>> items = findCattyeTreeBySql(parentId, catgTypeId);
    for (Map<String,Object> item : items) {
      if (findCattyeTreeBySql(item.get("CATG_ID").toString(), catgTypeId).size() > 0) {
        item.put("state", "closed");
      }
      item.put("iconCls", "icon-cat");
    }
    return items;
  }

  public List<Map<String, Object>> findCattyeTreeBySql(String parentId, String catgTypeId) {
    List result = null;

    String sql = "select * from cm_catval b where  b.parent_catg_id ='" + parentId + "' and b.catg_type_id = '" + catgTypeId + "' order By b.catg_short_name asc";

    result = this.baseJdbcDao.queryListMap(sql);
    return result;
  }

  public String AddCatval(String catgTypeId, String baseMasterKey, String catgId)
  {
    String sql = "select count(wid) from CM_CATBASE where BASE_MASTER_KEY='" + baseMasterKey + "' and CATG_TYPE_ID='" + catgTypeId + "' and CATG_ID='" + catgId + "'";
    if (this.baseJdbcDao.getCount(sql) > 0) {
      return "success";
    }
    sql = " INSERT INTO CM_CATBASE(WID,CATBASE_ID,BASE_MASTER_KEY,CATG_TYPE_ID,CATG_ID) VALUES('" + Guid.getGuid() + "','" + Guid.getGuid() + "','" + baseMasterKey + "','" + catgTypeId + "','" + catgId + "')";
    this.baseJdbcDao.insert(sql);
    return "success";
  }

  public String DelCatval(String wids)
  {
    String sql = "delete from cm_catbase where wid in ('" + wids.replaceAll(",", "','") + "')";
    this.baseJdbcDao.delete(sql);
    return "success";
  }

  public List AddCatvalAllTree(String parentId, String whichCatvalType, String base_master_key)
  {
    List<Map<String, Object>> items = findCattyeAllTreeBySql(parentId, whichCatvalType);
    for (Map<String,Object> item : items) {
      String catg_id = item.get("CATG_ID").toString();
      if (findCattyeAllTreeBySql(catg_id, whichCatvalType).size() > 0) {
        item.put("state", "closed");
      }
      if (parentId.equals("0")) {
        item.put("iconCls", "icon-cattype");
      } else {
        if (this.baseJdbcDao.getCount("select count(wid) from cm_catbase where CATG_ID = '" + catg_id + "' AND BASE_MASTER_KEY='" + base_master_key + "' ") > 0)
          item.put("SELECT", "1");
        else {
          item.put("SELECT", "0");
        }
        item.put("iconCls", "icon-cat");
      }
    }
    return items;
  }

  public List<Map<String, Object>> findCattyeAllTreeBySql(String parentId, String whichCatvalType) {
    List result = null;
    String sql = "";
    if (parentId.equals("0")) {
      sql = "SELECT concat('type_',CATG_TYPE_ID) CATG_ID ,CATG_TYPE CATG_SHORT_NAME  FROM CM_CATTYPE WHERE  WHICH_CATVAL_TYPE='" + whichCatvalType + "' ORDER BY CATG_TYPE_ID DESC";
    }
    else if (parentId.startsWith("type"))
      sql = "select * from cm_catval b where  b.parent_catg_id ='0' and b.catg_type_id = '" + parentId.substring(5) + "' order By b.catg_short_name asc";
    else {
      sql = "select * from cm_catval b where  b.parent_catg_id ='" + parentId + "'order By b.catg_short_name asc";
    }

    result = this.baseJdbcDao.queryListMap(sql);
    return result;
  }

  public List ShowCatvalTypeTable(String WHICH_CATVAL_TYPE)
  {
    return this.baseJdbcDao.queryListMap(" SELECT WID, CATG_TYPE_ID, CATG_TYPE, WHICH_CATVAL_TYPE FROM CM_CATTYPE WHERE WHICH_CATVAL_TYPE='" + WHICH_CATVAL_TYPE + "'");
  }

  public String DelCatvalType(String CATG_TYPE_ID)
  {
    String sql = "DELETE FROM CM_CATVAL WHERE CATG_TYPE_ID ='" + CATG_TYPE_ID + "'";
    this.baseJdbcDao.delete(sql);
    sql = "DELETE FROM CM_CATTYPE WHERE CATG_TYPE_ID ='" + CATG_TYPE_ID + "'";
    this.baseJdbcDao.delete(sql);
    sql = "DELETE FROM CM_CATBASE WHERE CATG_TYPE_ID ='" + CATG_TYPE_ID + "' ";
    this.baseJdbcDao.delete(sql);
    return "success";
  }

  public String AddCatvalCode(CmCatval cmCatval)
  {
    String sql = "";
    String catvalId = Guid.getGuid();
    if (cmCatval.getParentCatgId().equals("0"))
      sql = " INSERT INTO CM_CATVAL(WID,CATG_ID,CATG_TYPE_ID,CATG_SHORT_NAME,CATG_NAME,PARENT_CATG_ID,SEQ_NUM,CATG_ID_PATH) VALUES('" + 
        Guid.getGuid() + "','" + catvalId + "','" + cmCatval.getCatgTypeId() + "','" + cmCatval.getCatgShortName() + "','" + cmCatval.getCatgName() + "','0','" + cmCatval.getSeqNum() + "','" + catvalId + ";')";
    else {
      sql = " INSERT INTO CM_CATVAL(WID,CATG_ID,CATG_TYPE_ID,CATG_SHORT_NAME,CATG_NAME,PARENT_CATG_ID,SEQ_NUM,CATG_ID_PATH)select '" + 
        Guid.getGuid() + "','" + catvalId + "','" + cmCatval.getCatgTypeId() + "','" + cmCatval.getCatgShortName() + "','" + cmCatval.getCatgName() + "',CATG_ID,'" + cmCatval.getSeqNum() + "',concat(CATG_ID_PATH,'" + catvalId + ";') from CM_CATVAL where  CATG_ID = '" + cmCatval.getParentCatgId() + "'";
    }
    this.baseJdbcDao.insert(sql);
    return "success";
  }

  public String DelCatvalCode(String catgId)
  {
    String sql = "delete from CM_CATVAL where instr(CATG_ID_PATH,'" + catgId + "')>0 ";
    this.baseJdbcDao.delete(sql);
    return "success";
  }

  public Map<String, Object> GetCatvalInfo(String catgId)
  {
    String sql = "select *  from CM_CATVAL where CATG_ID = '" + catgId + "' ";
    return this.baseJdbcDao.queryMap(sql);
  }

  public String UpdateCatvalCode(CmCatval cmCatval)
  {
    String sql = "UPDATE CM_CATVAL SET CATG_SHORT_NAME='" + cmCatval.getCatgShortName() + "',CATG_NAME= '" + cmCatval.getCatgName() + "',SEQ_NUM='" + cmCatval.getSeqNum() + "' where CATG_ID = '" + cmCatval.getCatgId() + "' ";
    this.baseJdbcDao.update(sql);
    return "success";
  }
}