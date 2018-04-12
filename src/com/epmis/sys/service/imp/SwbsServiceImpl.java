package com.epmis.sys.service.imp;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.SwbsService;
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.FileUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.vo.SmSwbs;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("swbsService")
public class SwbsServiceImpl
  implements SwbsService
{

  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;

  public List<Map<String, Object>> ShowSwbsTree(String parentId, String swbs_type_id)
  {
    List items = new ArrayList();

    if (DataTypeUtil.validate(swbs_type_id)) {
      items = findChildSwbsByParentId(parentId, swbs_type_id);
    }
    return items;
  }

  public List<Map<String, Object>> findChildSwbsByParentId(String parentId, String swbs_type_id)
  {
    List<Map<String, Object>> ChildrenList = findTreeBySql(parentId, swbs_type_id);
    List items = new ArrayList();
    for (Map<String, Object> item : ChildrenList) {
      String node_type = item.get("NODE_TYPE").toString();
      if (("SWBS".equalsIgnoreCase(node_type)) && (DataTypeUtil.validate(node_type)))
      {
        item.put("iconCls", "icon-wbs");
      }
      else {
        item.put("iconCls", "icon-task");
      }

      if (findTreeBySql(item.get("SWBS_ID").toString(), swbs_type_id).size() > 0) {
        item.put("state", "closed");
      }

      items.add(item);
    }
    return items;
  }

  public List<Map<String, Object>> findTreeBySql(String ParentId, String swbs_type_id) {
    List result = null;
    if (DataTypeUtil.validate(ParentId)) {
      String sql = "SELECT * FROM SM_SWBS WHERE PARENT_SWBS_ID = '" + ParentId + "' AND SWBS_TYPE_ID='" + swbs_type_id + "'  ORDER BY SEQ_NUM ASC ";
      result = this.baseJdbcDao.queryListMap(sql);
    }
    return result;
  }

  public List ShowSwbsMoveTree(String parentId, String swbsTypeId, String orginId)
  {
    List<Map<String, Object>> ChildrenList = findMoveTreeBySql(parentId, swbsTypeId, orginId);
    List items = new ArrayList();
    for (Map<String, Object> item : ChildrenList) {
      String node_type = item.get("NODE_TYPE").toString();
      if (("SWBS".equalsIgnoreCase(node_type)) && (DataTypeUtil.validate(node_type)))
      {
        item.put("iconCls", "icon-wbs");
      }
      else {
        item.put("iconCls", "icon-task");
      }

      if (findMoveTreeBySql(item.get("SWBS_ID").toString(), swbsTypeId, orginId).size() > 0) {
        item.put("state", "closed");
      }

      items.add(item);
    }
    return items;
  }

  public List<Map<String, Object>> findMoveTreeBySql(String ParentId, String swbs_type_id, String orginId)
  {
    List result = null;
    if (DataTypeUtil.validate(ParentId)) {
      String where = "";
      if (DataTypeUtil.validate(orginId)) {
        where = " AND SWBS_ID !='" + orginId + "'";
      }
      String sql = "SELECT * FROM SM_SWBS WHERE PARENT_SWBS_ID = '" + ParentId + "' " + where + " AND SWBS_TYPE_ID='" + swbs_type_id + "' AND NODE_TYPE = 'SWBS' ORDER BY SEQ_NUM ASC ";
      result = this.baseJdbcDao.queryListMap(sql);
    }
    return result;
  }

  public SmSwbs GetSwbs(SmSwbs smSwbs)
  {
    String sql = "select * from sm_swbs where swbs_id = '" + smSwbs.getSwbsId() + "'";
    Map map = this.baseJdbcDao.queryMap(sql);
    if (DataTypeUtil.validate(map)) {
      smSwbs.setSwbsShortName(DataTypeUtil.formatDbColumn(map.get("SWBS_SHORT_NAME")));
      smSwbs.setSwbsName(DataTypeUtil.formatDbColumn(map.get("SWBS_NAME")));
      smSwbs.setSeqNum(Integer.valueOf(DataTypeUtil.formatDbColumn(map.get("SEQ_NUM"))));
      smSwbs.setRemark(DataTypeUtil.formatDbColumn(map.get("REMARK")));
      smSwbs.setInfo(DataTypeUtil.formatDbColumn(map.get("INFO")));
      smSwbs.setCodeId(DataTypeUtil.formatDbColumn(map.get("CODE_ID")));
    }
    return smSwbs;
  }

  public String UpdateSwbs(SmSwbs smSwbs, String type) {
    String result = "";
    String sql = "";
    if (type.equals("info"))
      sql = "update sm_swbs set swbs_short_name ='" + smSwbs.getSwbsShortName() + "',swbs_name = '" + smSwbs.getSwbsName() + "',seq_num = " + smSwbs.getSeqNum() + ",code_id='" + smSwbs.getCodeId() + "' where swbs_id ='" + smSwbs.getSwbsId() + "'";
    else if (type.equals("taskremark"))
      sql = "update sm_swbs set remark ='" + smSwbs.getRemark().replaceAll("'", "'").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>") + "'where  swbs_id ='" + smSwbs.getSwbsId() + "'";
    else {
      sql = "update sm_swbs set info ='" + smSwbs.getInfo().replaceAll("'", "'").replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>") + "'where  swbs_id ='" + smSwbs.getSwbsId() + "'";
    }
    if (this.baseJdbcDao.update(sql)) {
      result = type;
    }
    return result;
  }

  public List ShowSwbsTypeTable(String moduleName) {
    return this.baseJdbcDao.queryListMap("SELECT SWBS_TYPE_ID  ,SWBS_TYPE_NAME   ,DESCRIPTION FROM SM_SWBSTYPE WHERE MODLENAME='" + moduleName + "' ORDER BY SWBS_TYPE_NAME");
  }

  public String DelSwbsType(String swbsTypeId)
  {
    String result = "";
    String sql = "DELETE FROM SM_SWBS WHERE SWBS_TYPE_ID = '" + swbsTypeId + "'";
    boolean res = this.baseJdbcDao.delete(sql);
    sql = "DELETE FROM SM_SWBSTYPE WHERE SWBS_TYPE_ID = '" + swbsTypeId + "'";
    res = this.baseJdbcDao.delete(sql);
    sql = "DELETE FROM CM_DOCLINK WHERE BASE_ITEM_TYPE='SM_PLAN_DATA' AND SWBS_TYPE_ID='" + swbsTypeId + "'";
    res = this.baseJdbcDao.delete(sql);
    return "success";
  }

  public String impSwbsModule(String swbsTypeId, String mode, File uploadfile, UserInfo userInfo)
  {
    String UserId = userInfo.getUserId();
    String tempname = Guid.getGuid();
    String path = AppSetting.PROJECT_PATH + "/KM/temp/" + UserId;
    File file = new File(path);
    if (!file.exists()) {
      file.mkdirs();
    }
    FileUtil.uploadFile(uploadfile, path + "/" + tempname + ".xls");
    File excelfile = new File(path + "/" + tempname + ".xls");
    if (!excelfile.exists())
      return "�ļ��ϴ�ʧ��!";
    if (mode.equals("tihuan")) {
      this.baseJdbcDao.delete("DELETE FROM SM_SWBS WHERE SWBS_TYPE_ID='" + swbsTypeId + "'");
    }
    List cols = new ArrayList();

    cols.add("WID");
    cols.add("SWBS_ID");
    cols.add("PARENT_SWBS_ID");
    cols.add("SWBS_TYPE_ID");
    cols.add("SWBS_SHORT_NAME");
    cols.add("SWBS_NAME");
    cols.add("NODE_TYPE");
    cols.add("REMARK");
    cols.add("INFO");
    cols.add("SEQ_NUM");
    InsertDB(excelfile, cols, "SM_SWBS", swbsTypeId);
    return "success";
  }

  public void InsertDB(File file, List<String> cols, String tableName, String SWBS_TYPE_ID)
  {
    Workbook workbook = null;
    try {
      workbook = Workbook.getWorkbook(file);
    }
    catch (BiffException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    List<List> rowsList = null;//改List
    List<String> coles = null;//改List
    for (int i = 0; i < workbook.getSheets().length; i++)
    {
      Sheet sheet = workbook.getSheet(i);
      String value;
      if (sheet.getRows() >= 2)
      {
        rowsList = new ArrayList();
        for (int r = 1; r < sheet.getRows(); r++)
        {
          coles = new ArrayList();
          System.out.println(r + "**");
          for (int c = 0; c < 5; c++)
          {
            Cell cell = sheet.getCell(c, r);
            value = cell.getContents();
            coles.add(value);
            System.out.print(c);
          }

          rowsList.add(coles);
        }
      }

      if ((rowsList != null) && (rowsList.size() > 0)) {
        int ind = 0;

        String colStr = "";

        for (String str : cols) {
          colStr = colStr + "," + str;
        }

        for (List<String> colslist : rowsList)
        {
          String type = "";
          String valuStr = "";
          int num = 1;
          for (String str : colslist) {
            if (num == 3) {
              type = str;
            }
            valuStr = valuStr + ",'" + str + "'";
            num++;
          }

          if ((!((String)colslist.get(0)).equals("")) && (colslist.get(0) != null))
          {
            String shortName = ((String)colslist.get(0)).substring(0, ((String)colslist.get(0)).lastIndexOf(".") == -1 ? ((String)colslist.get(0)).length() : ((String)colslist.get(0)).lastIndexOf("."));
            String parent_id = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue("select ifnull(SWBS_ID,'0') parent_id  from " + tableName + " where  SWBS_TYPE_ID='" + SWBS_TYPE_ID + "' and SWBS_SHORT_NAME ='" + shortName + "'"));
            if (!DataTypeUtil.validate(parent_id)) {
              parent_id = "0";
            }
            String seq_num = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue("SELECT ifnull(MAX(SEQ_NUM),0)+1  seq_num FROM " + tableName + " SM WHERE  SM.SWBS_TYPE_ID='" + SWBS_TYPE_ID + "' AND SM.PARENT_SWBS_ID=ifnull((select SWBS_ID from " + tableName + " where  SWBS_TYPE_ID='" + SWBS_TYPE_ID + "' and SWBS_SHORT_NAME ='" + shortName + "'),0)"));
            if (type.equals("TASK")) {
              String swbs_id = Guid.getGuid();
              String sql = "insert into " + tableName + "(" + colStr.substring(1) + ",DOC_ID)values('" + Guid.getGuid() + "','" + swbs_id + "', " + 
                "'" + parent_id + "'," + 
                "'" + SWBS_TYPE_ID + "'," + valuStr.substring(1) + "," + 
                seq_num + ",'" + swbs_id + "')";
              this.baseJdbcDao.insert(sql);
            } else if (type.equals("SWBS")) {
              String swbs_id = Guid.getGuid();
              String sql = "insert into " + tableName + "(WID,SWBS_ID,PARENT_SWBS_ID,SWBS_TYPE_ID,SWBS_SHORT_NAME,SWBS_NAME,NODE_TYPE,INFO,REMARK,SEQ_NUM,DOC_ID)values('" + Guid.getGuid() + "','" + swbs_id + "', " + 
                "'" + parent_id + "'," + 
                "'" + SWBS_TYPE_ID + "'," + valuStr.substring(1) + "," + 
                seq_num + ",'" + swbs_id + "')";
              this.baseJdbcDao.insert(sql);
            }
          }
        }
        rowsList = null;
        ind = 0;
      }
    }
  }

  public List ShowPlanRefDataTable(String swbsId, String docId, String swbs_type_id)
  {
    String sql = " SELECT * FROM (SELECT A.WID,A.BASE_MASTER_KEY AS PLAN_ID,B.DOC_NUMBER,B.DOC_TYPE,B.TITLE, B.EDITION,B.DOC_ID,date_format(A.DATE_LINKED,'%Y-%m-%d')  DATE_LINKED     FROM CM_DOCLINK A LEFT OUTER JOIN KM_DOC B ON A.DOC_ID = B.DOC_ID  WHERE A.BASE_ITEM_TYPE='SM_PLAN_DATA' AND A.SWBS_TYPE_ID='" + 
      swbs_type_id + "') Z WHERE PLAN_ID='" + swbsId + "' or PLAN_ID='" + docId + "'";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public String AddDocLink(String base_item_type, String swbs_type_id, String base_master_key, String docIds, UserInfo userInfo)
  {
    String sql = "";
    String projId = userInfo.getProjId();
    String[] docList = docIds.split(",");
    for (int i = 0; i < docList.length; i++) {
      if (DataTypeUtil.validate(swbs_type_id)) {
        sql = " INSERT INTO CM_DOCLINK(WID,DOC_ID,BASE_MASTER_KEY,BASE_ITEM_TYPE,SWBS_TYPE_ID,DATE_LINKED)  VALUES ('" + Guid.getGuid() + "','" + docList[i] + "','" + base_master_key + "','" + base_item_type + "','" + swbs_type_id + "',now())";
        this.baseJdbcDao.insert(sql);
      } else {
        sql = " INSERT INTO CM_DOCLINK(WID,PROJ_ID,DOC_ID,BASE_MASTER_KEY,BASE_ITEM_TYPE,DATE_LINKED)  VALUES ('" + Guid.getGuid() + "','" + projId + "','" + docList[i] + "','" + base_master_key + "','" + base_item_type + "',now())";
        this.baseJdbcDao.insert(sql);
      }
    }
    return "success";
  }

  public String delDocLink(String wids)
  {
    String sql = "delete from CM_DOCLINK where wid in ('" + wids.replaceAll(",", "','") + "')";
    this.baseJdbcDao.delete(sql);
    return "success";
  }

  public String PlanStructTable(String swbsId)
  {
    return null;
  }

  public String AddSwbs(SmSwbs smSwbs)
  {
    if ((DataTypeUtil.validate(smSwbs.getSwbsShortName())) && (DataTypeUtil.validate(smSwbs.getSwbsName()))) {
      String sql = "";
      if (smSwbs.getNodeType().equals("SWBS")) {
        String swbsId = Guid.getGuid();
        sql = " INSERT INTO SM_SWBS(WID,SWBS_ID,SWBS_SHORT_NAME,SWBS_NAME,SWBS_TYPE_ID,PARENT_SWBS_ID,SEQ_NUM,NODE_TYPE,INFO,DOC_ID) VALUES ('" + Guid.getGuid() + "','" + swbsId + "','" + smSwbs.getSwbsShortName() + "','" + smSwbs.getSwbsName() + "','" + smSwbs.getSwbsTypeId() + "','" + smSwbs.getParentSwbsId() + "'," + smSwbs.getSeqNum() + ",'" + smSwbs.getNodeType() + "','" + smSwbs.getRemark() + "','" + swbsId + "')";
      } else {
        String swbsId = Guid.getGuid();
        sql = " INSERT INTO SM_SWBS(WID,SWBS_ID,SWBS_SHORT_NAME,SWBS_NAME,SWBS_TYPE_ID,PARENT_SWBS_ID,SEQ_NUM,NODE_TYPE,REMARK,DOC_ID) VALUES ('" + Guid.getGuid() + "','" + swbsId + "','" + smSwbs.getSwbsShortName() + "','" + smSwbs.getSwbsName() + "','" + smSwbs.getSwbsTypeId() + "','" + smSwbs.getParentSwbsId() + "'," + smSwbs.getSeqNum() + ",'" + smSwbs.getNodeType() + "','" + smSwbs.getRemark() + "','" + swbsId + "')";
      }

      this.baseJdbcDao.insert(sql);
      return "success";
    }
    return "代码名称任一不能为空！";
  }

  public String delSwbs(String swbsId)
  {
    String sql = "Delete from sm_swbs where SWBS_ID = '" + swbsId + "'";
    this.baseJdbcDao.delete(sql);
    return "success";
  }

  public String moduleSave(String tableName, String moduleName, String remark, String swbsTypeName, String pro_id)
  {
    if (DataTypeUtil.validate(swbsTypeName)) {
      String sql = "SELECT COUNT(WID) AS COUNT FROM SM_SWBSTYPE WHERE SWBS_TYPE_NAME='" + swbsTypeName + "' AND MODLENAME='" + moduleName + "'";
      if (this.baseJdbcDao.getCount(sql) == 0)
      {
        String swbs_type_id = Guid.getGuid();
        String str = swbs_type_id.substring(0, 5);
        sql = "INSERT INTO SM_SWBSTYPE(WID,SWBS_TYPE_ID,SWBS_TYPE_NAME,DESCRIPTION,MODLENAME) VALUES ('" + Guid.getGuid() + "','" + swbs_type_id + "','" + swbsTypeName + "','" + remark + "','" + moduleName + "')";
        this.baseJdbcDao.insert(sql);
        String sqltask = "SELECT WID,PLAN_ID,PROJ_ID,PARENT_PLAN_ID,DOC_SWBS_ID FROM " + tableName + " WHERE PROJ_ID='" + pro_id + "' AND NODE_TYPE='TASK'";
        List<Map<String, Object>> listmap = this.baseJdbcDao.queryListMap(sqltask);
        String doc_id1 = "";
        if (DataTypeUtil.validate(listmap)) {
          for (Map<String, Object> map : listmap) {
            doc_id1 = doc_id1 + ",'" + DataTypeUtil.formatDbColumn(map.get("DOC_SWBS_ID")) + "','" + DataTypeUtil.formatDbColumn(map.get("PLAN_ID")) + "'";
          }
        }
        String doc_id2 = "''";
        if (DataTypeUtil.validate(doc_id1)) {
          doc_id2 = doc_id1.substring(1);
        }
        String selectSQL = "";
        selectSQL = "SELECT DOC_ID,BASE_MASTER_KEY FROM CM_DOCLINK WHERE BASE_MASTER_KEY IN (" + doc_id2 + ") AND PROJ_ID='" + pro_id + "'";
        String docid = "";
        String base_master_key = "";
        String wid = "";
        listmap = this.baseJdbcDao.queryListMap(selectSQL);
        if (DataTypeUtil.validate(listmap)) {
          for (Map map : listmap) {
            docid = DataTypeUtil.formatDbColumn(map.get("DOC_ID"));
            base_master_key = DataTypeUtil.formatDbColumn(map.get("BASE_MASTER_KEY"));
            String SQL = "SELECT WID FROM " + tableName + " WHERE PROJ_ID = '" + pro_id + "' AND NODE_TYPE = 'TASK' AND (DOC_SWBS_ID = '" + base_master_key + "' OR PLAN_ID = '" + base_master_key + "')";
            wid = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(SQL));
            if ((wid != null) && (wid.length() > 0))
            {
              String insertSQL = "INSERT INTO CM_DOCLINK(WID,DOC_ID,BASE_MASTER_KEY,BASE_ITEM_TYPE,SWBS_TYPE_ID,DATE_LINKED) VALUES('" + Guid.getGuid() + "','" + docid + "','" + wid + "','SM_PLAN_DATA','" + swbs_type_id + "',now())";
              this.baseJdbcDao.insert(insertSQL);
            }
          }
        }
        sql = "SELECT PLAN_ID,PLAN_SHORT_NAME,PLAN_NAME,NODE_TYPE,REMARK,DOC_SWBS_ID FROM " + tableName + " WHERE PARENT_PLAN_ID=(SELECT PLAN_ID FROM " + tableName + " WHERE PROJ_ID='" + pro_id + "' AND PARENT_PLAN_ID ='0') ORDER BY SEQ_NUM";
        listmap = this.baseJdbcDao.queryListMap(sql);
        if (DataTypeUtil.validate(listmap))
        {
          for (Map ins : listmap)
          {
            String intChlSql = "INSERT INTO SM_SWBS (WID,SWBS_ID,SWBS_TYPE_ID,PARENT_SWBS_ID,SWBS_SHORT_NAME,SWBS_NAME,NODE_TYPE,SEQ_NUM,REMARK,INFO,DOC_ID,CODE_ID) (SELECT UUID(),CONCAT('" + str + "',PLAN_ID),'" + swbs_type_id + "', CASE PLAN_ID WHEN '" + DataTypeUtil.formatDbColumn(ins.get("PLAN_ID")) + "' THEN '0' ELSE  CONCAT('" + str + "', PARENT_PLAN_ID) END AS A,PLAN_SHORT_NAME, PLAN_NAME, NODE_TYPE,SEQ_NUM,REMARK,CASE NODE_TYPE WHEN 'TASK' THEN ASS_GUIDES ELSE REMARK END AS ASS,WID,CODE_ID FROM " + tableName + " WHERE INSTR(PLAN_ID_PATH,'" + DataTypeUtil.formatDbColumn(ins.get("PLAN_ID")) + "')>0 AND PROJ_ID = '" + pro_id + "' )";
            this.baseJdbcDao.insert(intChlSql);
          }
        }
        return "success";
      }
      return "模板名称已存在！";
    }

    return "模板名称能为空！";
  }

  public String CopySwbs(String parentId, String orginId, String swbsTypeId)
  {
    if ((DataTypeUtil.validate(parentId)) && (DataTypeUtil.validate(orginId))) {
      CopyInfo(parentId, orginId, swbsTypeId);
    }
    return "success";
  }

  public void CopyInfo(String parentId, String orginId, String swbsTypeId) {
    String newId = Guid.getGuid();
    String sql = "INSERT INTO SM_SWBS (WID,SWBS_ID,SWBS_TYPE_ID,PARENT_SWBS_ID,SWBS_SHORT_NAME,SWBS_NAME,NODE_TYPE,SEQ_NUM,REMARK,INFO,DOC_ID,SERVICE_ID,CODE_ID) select '" + 
      Guid.getGuid() + "','" + newId + "',SWBS_TYPE_ID,'" + parentId + "',SWBS_SHORT_NAME,SWBS_NAME,NODE_TYPE,SEQ_NUM,REMARK,INFO,DOC_ID,SERVICE_ID,CODE_ID FROM SM_SWBS WHERE SWBS_ID='" + orginId + "' AND SWBS_TYPE_ID ='" + swbsTypeId + "' ";
    this.baseJdbcDao.insert(sql);
    sql = "INSERT INTO CM_DOCLINK(wid, proj_id, doc_id, base_master_key, base_item_type, date_linked, created_by, swbs_type_id)SELECT uuid(),proj_id,doc_id,'" + 
      newId + "',base_item_type, date_linked,created_by, swbs_type_id from cm_doclink where base_master_key = '" + orginId + "' ";
    this.baseJdbcDao.insert(sql);
    sql = "insert into struct_officetpl_swbs(wid,link_id,officetpl_flag,swbs_id)select  uuid(), uuid(),officetpl_flag,'" + 
      newId + "' from  struct_officetpl_swbs where swbs_id = '" + orginId + "'";
    this.baseJdbcDao.insert(sql);
    sql = "SELECT SWBS_ID FROM  SM_SWBS WHERE PARENT_SWBS_ID = '" + orginId + "' AND SWBS_TYPE_ID ='" + swbsTypeId + "' ";
    List<Map<String, Object>> listMap = this.baseJdbcDao.queryListMap(sql);
    if (DataTypeUtil.validate(listMap))
      for (Map<String, Object> map : listMap)
        CopyInfo(newId, map.get("SWBS_ID").toString(), swbsTypeId);
  }

  public String MoveSwbs(String parentId, String orginId, String swbsTypeId)
  {
    if ((DataTypeUtil.validate(parentId)) && (DataTypeUtil.validate(orginId)))
    {
      String sql = "SELECT PARENT_SWBS_ID FROM SM_SWBS WHERE SWBS_ID='" + orginId + "' AND SWBS_TYPE_ID ='" + swbsTypeId + "' ";
      String orgin_parentId = DataTypeUtil.formatDbColumn(this.baseJdbcDao.getFieldValue(sql));
      if (parentId.equals(orgin_parentId)) {
        return "success";
      }
      sql = "UPDATE SM_SWBS SET PARENT_SWBS_ID = '" + parentId + "' where SWBS_ID='" + orginId + "' AND SWBS_TYPE_ID ='" + swbsTypeId + "'";
      this.baseJdbcDao.update(sql);
    }
    return "success";
  }
}