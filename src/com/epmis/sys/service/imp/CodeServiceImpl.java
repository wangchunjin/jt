package com.epmis.sys.service.imp;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.CodeService;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.vo.CmCode;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("codeService")
public class CodeServiceImpl
  implements CodeService
{

  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;

  public List SysCodeTable(String codeType, String belongto)
  {
    String where = "";
    if (DataTypeUtil.validate(belongto)) {
      where = " and A.CODE_BELONGTO = '" + belongto + "' ";
    }
    String sql = "SELECT A.WID,A.SEQ_NUM,A.CODE_SHORT_NAME,A.CODE_NAME,C.MODULE_NAME FROM CM_CODE A,CM_CODE_TYPE B, (SELECT MODULE_CODE,MODULE_NAME FROM CM_MODULE UNION SELECT  MODULE_CODE,MODULE_NAME FROM CM_MODULE_ENT)  C WHERE A.CODE_TYPE=B.CODE_TYPE AND B.SUBSYS_CODE=C.MODULE_CODE  AND A.CODE_TYPE='" + 
      codeType + "' " + where + "  ORDER BY A.SEQ_NUM,A.CODE_SHORT_NAME";

    return this.baseJdbcDao.queryListMap(sql);
  }

  public Map<String, Object> GetCodeInfoByWid(String wid)
  {
    return this.baseJdbcDao.queryMap("select * from cm_code where wid ='" + wid + "'");
  }

  public String AddCode(CmCode cmCode)
  {
    String sql = "insert into cm_code(wid,id,code_short_name,code_name,code_Type,code_Belongto,remark,seq_num)values('" + 
      Guid.getGuid() + "','" + Guid.getGuid() + "','" + cmCode.getCodeShortName() + "','" + cmCode.getCodeName() + "','" + cmCode.getCodeType() + "','" + cmCode.getCodeBelongto() + "','" + cmCode.getRemark() + "'," + cmCode.getSeqNum() + ")";
    this.baseJdbcDao.insert(sql);
    return "success";
  }

  public String DelCode(String wids)
  {
    String sql = "delete from cm_code where wid in ('" + wids.replaceAll(",", "','") + "')";
    this.baseJdbcDao.delete(sql);
    return "success";
  }

  public String UpdateCode(CmCode cmCode)
  {
    String sql = "update cm_code set code_short_name='" + cmCode.getCodeShortName() + "', code_name ='" + cmCode.getCodeName() + "',remark='" + cmCode.getRemark() + "', seq_num=" + cmCode.getSeqNum() + " where  wid ='" + cmCode.getWid() + "'";
    this.baseJdbcDao.update(sql);
    return "success";
  }

  public List SysCodeTable(String codeType, String belongto, String value, String test, String showValue, String showTest)
  {
    String where = "";
    String colume = test;
    if (DataTypeUtil.validate(belongto)) {
      where = " AND A.CODE_BELONGTO = '" + belongto + "' ";
    }
    if (test.split(",").length > 1) {
      colume = "";
      String[] temp = test.split(",");
      for (int i = 0; i < temp.length; i++) {
        colume = colume + ",'--'," + temp[i];
      }
      colume = "concat(" + colume + ")";
    }
    String sql = "SELECT " + value + " " + showValue + "," + colume + " " + showTest + ",CODE_TYPE" + 
      " FROM CM_CODE A WHERE CODE_TYPE IN ('" + codeType.replaceAll(",", "','") + "') " + where + "  ORDER BY CODE_TYPE,A.SEQ_NUM,A.CODE_SHORT_NAME";

    return this.baseJdbcDao.queryListMap(sql);
  }
}