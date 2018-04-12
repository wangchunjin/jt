package com.epmis.sys.service.imp;

import com.epmis.ds.vo.DsCmVnmtSupp;
import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.VnmtService;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.vo.CmVndt;
import com.epmis.sys.vo.CmVnmt;

import java.awt.ItemSelectable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("vnmtService")
public class VnmtServiceImpl
  implements VnmtService
{

  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;

  public List SysVnmtTable(String key, String projId)
  {
    String where = "";
    if (DataTypeUtil.validate(projId)) {
      where = " WHERE VNMT_ID NOT IN (SELECT VNMT_ID FROM CM_PROJ_JOIN WHERE PROJ_ID = '" + projId + "')";
    }
    if (DataTypeUtil.validate(key)) {
      where = where + " AND (COMPANY_NAME LIKE '%" + key + "%' OR COMPANY_ABBREV LIKE '%" + key + "%') ";
    }
    String sql = "SELECT WID,VNMT_ID,COMPANY_ABBREV,COMPANY_NAME,ROLE_ID,(SELECT NAME FROM CM_VNDT WHERE VNDT_ID=DEFAULT_LINKMAN) as DEFAULT_LINKMAN FROM CM_VNMT " + where + " ORDER BY COMPANY_NAME";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public List SysVnmtTree(String parentId, String catgTypeId)
  {
    List<Map<String, Object>> items = findCattyeTreeBySql(parentId, catgTypeId);
    
    for (Map<String, Object> item : items)
    {
      String type = DataTypeUtil.formatDbColumn(item.get("TYPE"));
      if ((type.equals("CAT")) && 
        (findCattyeTreeBySql(item.get("VNMT_ID").toString(), catgTypeId).size() > 0)) {
        item.put("state", "closed");
      }

      if (type.equals("CAT"))
        item.put("iconCls", "icon-cat");
      else {
        item.put("iconCls", "icon-company");
      }
    }

    return items;
  }

  public List<Map<String, Object>> findCattyeTreeBySql(String parentId, String catg_type_id) {
    List result = null;

    String sql = "";
    if (parentId.equals("0")) {
      sql = " SELECT CATG_ID VNMT_ID,CATG_SHORT_NAME COMPANY_ABBREV,CATG_NAME COMPANY_NAME ,SEQ_NUM,'CAT' TYPE FROM CM_CATVAL WHERE PARENT_CATG_ID='0' AND CATG_TYPE_ID='" + catg_type_id + "' UNION SELECT 'NOT' ,'δ����','δ����',100000 ,'CAT' TYPE  ORDER BY SEQ_NUM";
      result = this.baseJdbcDao.queryListMap(sql);
      return result;
    }
    String where = "";
    if (parentId.equals("NOT"))
      where = " AND A.VNMT_ID NOT IN (SELECT BASE_MASTER_KEY FROM CM_CATBASE WHERE CATG_TYPE_ID='" + catg_type_id + "') ";
    else {
      where = " AND A.VNMT_ID IN (SELECT BASE_MASTER_KEY FROM CM_CATBASE WHERE CATG_TYPE_ID ='" + catg_type_id + "' AND CATG_ID='" + parentId + "') ";
    }

    sql = " SELECT VNMT_ID,COMPANY_ABBREV,COMPANY_NAME,ROLE_ID, (SELECT NAME FROM CM_VNDT WHERE VNDT_ID=DEFAULT_LINKMAN) AS DEFAULT_LINKMAN ,'' TYPE  FROM CM_VNMT A WHERE 1=1 " + 
      where + 
      " ORDER BY A.COMPANY_NAME";
    result = this.baseJdbcDao.queryListMap(sql);
    sql = "SELECT CATG_ID VNMT_ID,CATG_SHORT_NAME COMPANY_ABBREV,CATG_NAME COMPANY_NAME ,'','','CAT' TYPE FROM CM_CATVAL WHERE PARENT_CATG_ID='" + parentId + "' AND CATG_TYPE_ID='" + catg_type_id + "' ORDER BY SEQ_NUM ";
    List catList = this.baseJdbcDao.queryListMap(sql);
    if (DataTypeUtil.validate(catList)) {
      catList.addAll(result);
      return catList;
    }
    return result;
  }

  public Map<String, Object> GetVnmtInfoById(String vnmtId)
  {
    return this.baseJdbcDao.queryMap("select * from cm_vnmt where vnmt_id ='" + vnmtId + "'");
  }

  public String AddVnmt(CmVnmt cmVnmt, DsCmVnmtSupp dsCmVnmtSupp)
  {
    String sql = " INSERT INTO CM_VNMT(VNMT_ID,WID,COMPANY_NAME,COMPANY_ABBREV,ROLE_ID,INTG_GRADE,TRADE,ESTM_GRADE,OFFICE_PHONE,DEFAULT_LOCATION,FAX,REMARK,TAXPAYER_NUMBER,TAXPAYER_NAME,BANK_ACCOUNT,BANK_NUMBER) VALUES ('" + 
      Guid.getGuid() + "','" + Guid.getGuid() + "','" + cmVnmt.getCompanyName() + "','" + cmVnmt.getCompanyAbbrev() + "','" + cmVnmt.getRoleId() + "','" + cmVnmt.getIntgGrade() + "','" + cmVnmt.getTrade() + "','" + cmVnmt.getEstmGrade() + "','" + cmVnmt.getOfficePhone() + "','" + cmVnmt.getDefaultLocation() + "','" + cmVnmt.getFax() + "','" + cmVnmt.getRemark() + "','" + dsCmVnmtSupp.getTaxpayerNumber() + "'," + 
      "'" + dsCmVnmtSupp.getTaxpayerName() + "'," + 
      "'" + dsCmVnmtSupp.getBankAccount() + "','" + dsCmVnmtSupp.getBankNumber() + "')";
    this.baseJdbcDao.insert(sql);
    return "success";
  }

  public String DelVnmt(String vnmtId)
  {
    String sql = "delete from cm_vndt where vnmt_id  in ('" + vnmtId.replaceAll(",", "','") + "')";
    this.baseJdbcDao.delete(sql);
    sql = "delete from cm_vnmt where vnmt_id  in ('" + vnmtId.replaceAll(",", "','") + "')";
    this.baseJdbcDao.delete(sql);
    return "success";
  }

  public String UpdateVnmt(CmVnmt cmVnmt, DsCmVnmtSupp dsCmVnmtSupp)
  {
    if ((DataTypeUtil.validate(cmVnmt.getIsdefault())) && (cmVnmt.getIsdefault().equals("1"))) {
      this.baseJdbcDao.update("update cm_vnmt set ISDEFAULT='0'");
    }
    String sql = "update cm_vnmt set COMPANY_NAME='" + cmVnmt.getCompanyName() + "', ROLE_ID ='" + cmVnmt.getRoleId() + "',COMPANY_ABBREV='" + cmVnmt.getCompanyAbbrev() + "', DEFAULT_LINKMAN='" + cmVnmt.getDefaultLinkman() + "'," + 
      " OTHER_VNMT_ID ='" + cmVnmt.getOtherVnmtId() + "' ,ISDEFAULT ='" + (DataTypeUtil.validate(cmVnmt.getIsdefault()) ? cmVnmt.getIsdefault() : "0") + "',REMARK='" + cmVnmt.getRemark() + "' where  vnmt_id ='" + cmVnmt.getVnmtId() + "'";
    this.baseJdbcDao.update(sql);
    this.baseJdbcDao.update("UPDATE CM_VNMT SET TAXPAYER_NUMBER='" + dsCmVnmtSupp.getTaxpayerNumber() + "'," + 
      "TAXPAYER_NAME='" + dsCmVnmtSupp.getTaxpayerName() + "',BANK_ACCOUNT='" + dsCmVnmtSupp.getBankAccount() + "'," + 
      "BANK_NUMBER='" + dsCmVnmtSupp.getBankNumber() + "' WHERE VNMT_ID ='" + cmVnmt.getVnmtId() + "' ");
    return "success";
  }

  public String UpdateVnmtOther(CmVnmt cmVnmt) {
    String sql = "update cm_vnmt set REGISTER_FUND='" + cmVnmt.getRegisterFund() + "', TRADE ='" + cmVnmt.getTrade() + "',INTG_GRADE='" + cmVnmt.getIntgGrade() + "', ESTM_GRADE='" + cmVnmt.getEstmGrade() + "'," + 
      " OFFICE_PHONE ='" + cmVnmt.getOfficePhone() + "' ,FAX ='" + cmVnmt.getFax() + "',DEFAULT_LOCATION='" + cmVnmt.getDefaultLocation() + "' where  vnmt_id ='" + cmVnmt.getVnmtId() + "'";
    this.baseJdbcDao.update(sql);
    return "success";
  }

  public List VndtTable(String vnmtId)
  {
    String sql = " SELECT VNDT_ID,NAME,OFFICE_PHONE,MOBILE_PHONE,MAIL_ADDRESS FROM CM_VNDT WHERE VNMT_ID='" + vnmtId + "' order by NAME ASC ";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public String AddVndt(CmVndt cmVndt)
  {
    String sql = " INSERT INTO CM_VNDT(VNDT_ID,WID,NAME,GENDER,INITIALS,OFFICE_PHONE,LOCATION,MOBILE_PHONE,COUNTRY,MAIL_ADDRESS,STATE,CITY,VNMT_ID,DESCRIPTION) VALUES ('" + 
      Guid.getGuid() + "','" + Guid.getGuid() + "','" + cmVndt.getName() + "','" + cmVndt.getGender() + "','" + cmVndt.getInitials() + "','" + cmVndt.getOfficePhone() + "','" + cmVndt.getLocation() + "','" + cmVndt.getMobilePhone() + "','" + cmVndt.getCountry() + "','" + cmVndt.getMailAddress() + "','" + cmVndt.getState() + "','" + cmVndt.getCity() + "','" + cmVndt.getVnmtId() + "','" + cmVndt.getDescription() + "')";
    this.baseJdbcDao.insert(sql);
    return "success";
  }

  public Map<String, Object> GetVndtInfoById(String vndtId)
  {
    String sql = " SELECT * FROM CM_VNDT WHERE VNDT_ID='" + vndtId + "' ";
    return this.baseJdbcDao.queryMap(sql);
  }

  public String UpdateVndt(CmVndt cmVndt)
  {
    String sql = "update CM_VNDT set NAME='" + cmVndt.getName() + "',GENDER='" + cmVndt.getGender() + "',INITIALS='" + cmVndt.getInitials() + "',OFFICE_PHONE='" + cmVndt.getOfficePhone() + "',LOCATION='" + cmVndt.getLocation() + "',MOBILE_PHONE='" + cmVndt.getMobilePhone() + "',COUNTRY='" + cmVndt.getCountry() + "',MAIL_ADDRESS='" + cmVndt.getMailAddress() + "',STATE='" + cmVndt.getState() + "',CITY='" + cmVndt.getCity() + "',DESCRIPTION='" + cmVndt.getDescription() + "' WHERE VNDT_ID='" + cmVndt.getVndtId() + "' ";
    this.baseJdbcDao.update(sql);
    return "success";
  }

  public String DelVndt(String vndtIds)
  {
    String sql = "delete from CM_VNDT WHERE VNDT_ID in ('" + vndtIds.replaceAll(",", "','") + "')";
    this.baseJdbcDao.delete(sql);
    return "success";
  }
}