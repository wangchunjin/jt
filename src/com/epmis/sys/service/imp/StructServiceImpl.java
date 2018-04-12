package com.epmis.sys.service.imp;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.StructService;
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.FileUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.vo.StructLanguage;
import com.epmis.sys.vo.StructOfficetpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("structService")
public class StructServiceImpl
  implements StructService
{

  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;

  public List<Map<String, Object>> ShowStructTable()
  {
    String sql = "SELECT '' IMG,OFFICETPL_ID, OFFICETPL_NAME, OFFICETPL_URL, OFFICETPL_REMARK, SEQ_NUM, OFFICETPL_FLAG  FROM STRUCT_OFFICETPL T  WHERE NOT EXISTS (SELECT * FROM STRUCT_OFFICETPL S WHERE S.OFFICETPL_FLAG = T.OFFICETPL_FLAG AND S.CREATED_DATE > T.CREATED_DATE) ORDER BY SEQ_NUM";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public String AddStruct(StructOfficetpl StructOfficetpl, File uploadfile)
  {
    String tempname = Guid.getGuid();
    if (!DataTypeUtil.validate(StructOfficetpl.getOfficetplName())) {
      return "名称不能为空!";
    }
    String path = AppSetting.PROJECT_PATH + "/KM/STRUCT/";
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    FileUtil.uploadFile(uploadfile, path + "/" + tempname + ".doc");
    File excelfile = new File(path + "/" + tempname + ".doc");
    if (!excelfile.exists())
      return "模板上传失败!";
    StructOfficetpl.setOfficetplUrl("/KM/STRUCT/" + tempname + ".doc");
    String sql = "INSERT INTO STRUCT_OFFICETPL( WID, OFFICETPL_ID, OFFICETPL_NAME, OFFICETPL_URL, OFFICETPL_REMARK, SEQ_NUM, OFFICETPL_FLAG, CREATED_DATE)VALUES('" + 
      Guid.getGuid() + "','" + tempname + "','" + StructOfficetpl.getOfficetplName() + "','" + StructOfficetpl.getOfficetplUrl() + "','" + StructOfficetpl.getOfficetplRemark() + "'," + StructOfficetpl.getSeqNum() + ",'" + (DataTypeUtil.validate(StructOfficetpl.getOfficetplFlag()) ? StructOfficetpl.getOfficetplFlag() : Guid.getGuid()) + "',now())";
    this.baseJdbcDao.insert(sql);
    return "success";
  }

  public Map<String, Object> GetStruct(String id)
  {
    String sql = "SELECT OFFICETPL_ID, OFFICETPL_NAME, OFFICETPL_URL, OFFICETPL_REMARK, SEQ_NUM  FROM STRUCT_OFFICETPL WHERE OFFICETPL_ID = '" + id + "'";
    return this.baseJdbcDao.queryMap(sql);
  }

  public List<Map<String, Object>> ShowStructVersionTable(String structOfficetpl, String officetplFlag)
  {
    String sql = "SELECT '' IMG, OFFICETPL_ID, OFFICETPL_NAME, OFFICETPL_URL, OFFICETPL_REMARK, SEQ_NUM ,OFFICETPL_FLAG  FROM STRUCT_OFFICETPL WHERE OFFICETPL_FLAG = '" + officetplFlag + "' AND OFFICETPL_ID != '" + structOfficetpl + "'";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public List LinkStructTable(String swbsId)
  {
    String sql = "select '' IMG, OFFICETPL_ID, OFFICETPL_NAME, OFFICETPL_URL, OFFICETPL_REMARK, SEQ_NUM ,OFFICETPL_FLAG from struct_officetpl t  where not exists (select *  from struct_officetpl where officetpl_flag = t.officetpl_flag and created_date >t.created_date) and officetpl_flag in (select officetpl_flag from STRUCT_OFFICETPL_SWBS where swbs_id='" + swbsId + "') order by seq_num ";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public List AddLinkStructTable(String swbsId)
  {
    String sql = "select '' IMG, OFFICETPL_ID, OFFICETPL_NAME, OFFICETPL_URL, OFFICETPL_REMARK, SEQ_NUM ,OFFICETPL_FLAG from struct_officetpl t  where not exists (select *  from struct_officetpl where officetpl_flag = t.officetpl_flag and created_date >t.created_date) and officetpl_flag not in (select officetpl_flag from STRUCT_OFFICETPL_SWBS where swbs_id='" + swbsId + "') order by seq_num ";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public String AddLinkStruct(String officetplFlags, String swbsId)
  {
    String[] Ids = officetplFlags.split(",");
    String sql = "";
    for (int i = 0; i < Ids.length; i++) {
      sql = "INSERT INTO STRUCT_OFFICETPL_SWBS(WID, LINK_ID, OFFICETPL_FLAG, SWBS_ID ) VALUES ('" + Guid.getGuid() + "','" + Guid.getGuid() + "','" + Ids[i] + "','" + swbsId + "')";
      this.baseJdbcDao.insert(sql);
    }
    return "success";
  }

  public String DelLinkStruct(String officetplFlags, String swbsId)
  {
    String sql = "DELETE FROM STRUCT_OFFICETPL_SWBS WHERE SWBS_ID ='" + swbsId + "' AND OFFICETPL_FLAG IN ('" + officetplFlags.replaceAll(",", "','") + "')";
    this.baseJdbcDao.delete(sql);
    return "success";
  }

  public String delStruct(String id) {
    String path = AppSetting.PROJECT_PATH + "/KM/STRUCT/";
    String[] ids = id.split(",");
    for (int i = 0; i < ids.length; i++) {
      if (DataTypeUtil.validate(ids[i].trim())) {
        File file = new File(path + ids[i] + ".doc");
        if (file.exists()) {
          file.delete();
        }
      }
    }
    String sql = "DELETE FROM STRUCT_OFFICETPL WHERE OFFICETPL_ID IN ('" + id.replaceAll(",", "','") + "')";
    this.baseJdbcDao.delete(sql);
    return "success";
  }

  public String UpdateStruct(StructOfficetpl structOfficetpl)
  {
    String sql = "UPDATE STRUCT_OFFICETPL set OFFICETPL_NAME='" + structOfficetpl.getOfficetplName() + "' , OFFICETPL_REMARK = '" + structOfficetpl.getOfficetplRemark() + "', SEQ_NUM =" + structOfficetpl.getSeqNum() + "  where OFFICETPL_ID ='" + structOfficetpl.getOfficetplId() + "'";
    this.baseJdbcDao.update(sql);
    return "success";
  }

  public String saveDoc(File uploadfile, String type, String id)
  {
    String path = AppSetting.PROJECT_PATH + "/KM/STRUCT/";
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    FileUtil.uploadFile(uploadfile, path + "/" + id + ".doc");
    return "success";
  }

  public String CreateDoc(File uploadfile, String path, String name)
  {
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    FileUtil.uploadFile(uploadfile, path + "/" + name);
    return "success";
  }

  public List OfficetplKeyInfo(String id)
  {
    String sql = " SELECT WID, ID, KEY_SHORT_NAME, KEY_NAME, OFFICETPL_ID, KEY_TYPE, KEY_DISPLAY_NAME FROM STRUCT_OFFICETPL_KEY WHERE OFFICETPL_ID = '" + id + "' ORDER BY KEY_SHORT_NAME ASC";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public String addLanguage(StructLanguage structLanguage)
  {
    String sql = "SELECT ID_PATH FROM STRUCT_LANGUAGE WHERE ID = '" + structLanguage.getParentId() + "'";
    String parentPath = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
    String id = Guid.getGuid();
    String path = "";
    if (!DataTypeUtil.validate(parentPath))
      path = id;
    else {
      path = parentPath + "," + id;
    }
    sql = "INSERT INTO STRUCT_LANGUAGE(WID,ID,PARENT_ID,ID_PATH,NAME,TYPE,PROJ_ID,CREATED_BY,CREATED_TIME,IS_PUBLIC,SEQ_NUM)VALUES('" + 
      Guid.getGuid() + "','" + id + "','" + structLanguage.getParentId() + "','" + path + "','" + structLanguage.getName() + "','" + structLanguage.getType() + "','','" + structLanguage.getCreatedBy() + "',now(),'" + structLanguage.getIsPublic() + "'," + structLanguage.getSeqNum() + ")";
    this.baseJdbcDao.insert(sql);
    return "success";
  }

  public List<Map<String, Object>> showLanguageTree(String parentId)
  {
    List<Map<String, Object>> ChildrenList = findTreeBySql(parentId);
    List items = new ArrayList();
    for (Map<String, Object> item : ChildrenList) {
      if (DataTypeUtil.formatDbColumn(item.get("TYPE")).equals("WBS"))
        item.put("iconCls", "icon-wbs");
      else {
        item.put("iconCls", "icon-task");
      }
      if (Integer.valueOf(item.get("CHILDNUM").toString()).intValue() > 0) {
        item.put("state", "closed");
      }
      items.add(item);
    }
    return items;
  }

  public List<Map<String, Object>> findTreeBySql(String parentId) {
    List result = null;
    if (DataTypeUtil.validate(parentId)) {
      String sql = "SELECT ID,NAME,ID_PATH,TYPE,IS_PUBLIC,SEQ_NUM,(SELECT PROJ_NAME FROM CM_PROJ C WHERE C.PROJ_ID = S.PROJ_ID) PROJ_NAME,PROJ_ID,(SELECT ACTUAL_NAME FROM CM_USERS U WHERE U.USER_ID = S.CREATED_BY) CREATED_NAME,(SELECT COUNT(WID) FROM STRUCT_LANGUAGE WHERE  PARENT_ID = S.ID) AS CHILDNUM  FROM STRUCT_LANGUAGE S WHERE PARENT_ID = '" + parentId + "' ORDER BY SEQ_NUM ASC";
      result = this.baseJdbcDao.queryListMap(sql);
    }
    return result;
  }

  public String deleteLanguage(String id)
  {
    String sql = "DELETE from STRUCT_LANGUAGE where  LOCATE('" + id + "',ID_PATH)>0";
    this.baseJdbcDao.delete(sql);
    return "success";
  }

  public Map<String, Object> lableLanguage(String id)
  {
    String sql = "SELECT ID,NAME,ID_PATH,TYPE,IS_PUBLIC,SEQ_NUM,(SELECT PROJ_NAME FROM CM_PROJ C WHERE C.PROJ_ID = S.PROJ_ID) PROJ_NAME,PROJ_ID,(SELECT ACTUAL_NAME FROM CM_USERS U WHERE U.USER_ID = S.CREATED_BY) CREATED_NAME,DATE_FORMAT(CREATED_TIME,'%Y-%m-%d %H:%i:%s')CREATED_TIME   FROM STRUCT_LANGUAGE S WHERE ID = '" + id + "' ";
    return this.baseJdbcDao.queryMap(sql);
  }

  public String updateLanguage(StructLanguage structLanguage)
  {
    String sql = "UPDATE STRUCT_LANGUAGE SET IS_PUBLIC = '" + (DataTypeUtil.validate(structLanguage.getIsPublic()) ? Character.valueOf('Y') : "N") + "' ,NAME='" + structLanguage.getName() + "' , SEQ_NUM ='" + structLanguage.getSeqNum() + "' WHERE  ID = '" + structLanguage.getId() + "'";
    this.baseJdbcDao.update(sql);
    return "success";
  }

  public List<Map<String, Object>> showOwnLanguageTree(String parentId, String userId)
  {
    List<Map<String, Object>> ChildrenList = findOwnTreeBySql(parentId, userId);
    List items = new ArrayList();
    for (Map<String, Object> item : ChildrenList) {
      if (DataTypeUtil.formatDbColumn(item.get("TYPE")).equals("WBS"))
        item.put("iconCls", "icon-wbs");
      else {
        item.put("iconCls", "icon-task");
      }
      if (Integer.valueOf(item.get("CHILDNUM").toString()).intValue() > 0) {
        item.put("state", "closed");
      }
      items.add(item);
    }
    return items;
  }

  public List<Map<String, Object>> findOwnTreeBySql(String parentId, String userId) {
    List result = null;
    if (DataTypeUtil.validate(parentId)) {
      String sql = "SELECT ID,NAME,ID_PATH,TYPE,IS_PUBLIC,SEQ_NUM,(SELECT PROJ_NAME FROM CM_PROJ C WHERE C.PROJ_ID = S.PROJ_ID) PROJ_NAME,PROJ_ID,(SELECT ACTUAL_NAME FROM CM_USERS U WHERE U.USER_ID = S.CREATED_BY) CREATED_NAME,(SELECT COUNT(WID) FROM STRUCT_LANGUAGE WHERE  PARENT_ID = S.ID) AS CHILDNUM  FROM STRUCT_LANGUAGE S WHERE PARENT_ID = '" + 
        parentId + "' " + 
        " AND (SELECT COUNT(WID) FROM STRUCT_LANGUAGE WHERE LOCATE(S.ID,ID_PATH)>0 AND (TYPE='TASK' OR CREATED_BY='" + userId + "' ) AND (CREATED_BY='" + userId + "' OR IS_PUBLIC='Y') )>0 ORDER BY SEQ_NUM ASC";
      result = this.baseJdbcDao.queryListMap(sql);
    }
    return result;
  }

  public String checkNode(String id)
  {
    String sql = "SELECT COUNT(WID) NUM FROM STRUCT_LANGUAGE WHERE LOCATE('" + id + "',ID_PATH)>0 AND  TYPE='TASK'  AND  IS_PUBLIC='Y' ";
    int num = this.baseJdbcDao.getCount(sql);
    if (num > 0) {
      return "该节点下存在已共享生效的语句，禁止删除！";
    }
    return "success";
  }
}