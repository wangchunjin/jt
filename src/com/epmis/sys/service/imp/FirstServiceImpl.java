package com.epmis.sys.service.imp;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.FirstService;
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.UserInfo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("firstService")
public class FirstServiceImpl
  implements FirstService
{

  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;

  public List MajorTable(UserInfo userInfo, String startdate, String enddate)
  {
    String where = "";
    if (DataTypeUtil.validate(startdate)) {
      where = " and  ( date_format(created_date,'%Y-%m-%d')  > '" + startdate + "' or   date_format(created_date,'%Y-%m-%d')  = '" + startdate + "')  ";
    }
    if (DataTypeUtil.validate(enddate)) {
      where = where + " and  ( date_format(created_date,'%Y-%m-%d')  < '" + enddate + "' or   date_format(created_date,'%Y-%m-%d')  = '" + enddate + "')  ";
    }
    String proj_id = userInfo.getProjId();
    String sql = " SELECT PLAN_ID,PLAN_SHORT_NAME,PLAN_NAME,MODULE_CODE,(SELECT COUNT(WID) FROM KM_DOC WHERE BASE_MASTER_KEY = TT.PLAN_ID AND DELETED_FLAG = '0' AND PROJ_ID = '" + proj_id + "' " + where + " )  DOC_COUNT FROM (" + 
      " SELECT D.PLAN_ID,D.PLAN_SHORT_NAME,D.PLAN_NAME,'DS_PLAN' MODULE_CODE FROM DS_PLAN D LEFT JOIN SM_SWBS W ON D.SWBS_ID = W.SWBS_ID WHERE ( D.IS_MAJOR = '1'  OR  W.IS_MAJOR = '1' )AND  D.PROJ_ID = '" + proj_id + "' " + 
      " UNION" + 
      " SELECT S.PLAN_ID,S.PLAN_SHORT_NAME,S.PLAN_NAME,'SM_PLAN' FROM SM_PLAN S LEFT JOIN SM_SWBS W ON S.SWBS_ID = W.SWBS_ID WHERE ( S.IS_MAJOR = '1'  OR  W.IS_MAJOR = '1' )  AND S.PROJ_ID = '" + proj_id + "'" + 
      " UNION" + 
      " SELECT T.PLAN_ID,T.PLAN_SHORT_NAME,T.PLAN_NAME,'SM_TEST' FROM SM_TEST T LEFT JOIN SM_SWBS W ON T.SWBS_ID = W.SWBS_ID WHERE ( T.IS_MAJOR = '1'  OR  W.IS_MAJOR = '1' ) AND T.PROJ_ID = '" + proj_id + "'" + 
      " UNION" + 
      " SELECT Y.PLAN_ID,Y.PLAN_SHORT_NAME,Y.PLAN_NAME,'SYS_PLAN' FROM SYS_PLAN Y LEFT JOIN SM_SWBS W ON Y.SWBS_ID = W.SWBS_ID WHERE ( Y.IS_MAJOR = '1'  OR  W.IS_MAJOR = '1' ) AND Y.PROJ_ID = '" + proj_id + "') TT ORDER BY  PLAN_SHORT_NAME";
    return this.baseJdbcDao.queryListMap(sql);
  }

  public List ProjectPic(String projId)
  {
    String sql = "SELECT IMG_ID,PROJ_ID,IMG_NAME,UPLOAD_PERSON,DATE_FORMAT(UPLOAD_TIME,'%Y-%m-%d') UPLOAD_TIME,DEPART,PATH,CONTENT,PATH_THUMBNAIL FROM PIC_TABLE WHERE  PROJ_ID='" + projId + "' ORDER BY PIC_TABLE.IMG_TIME DESC,PIC_TABLE.UPLOAD_TIME DESC limit  5 ";
    List<Map<String, Object>> PhotoList = this.baseJdbcDao.queryListMap(sql);
    if (DataTypeUtil.validate(PhotoList)) {
      for (Map<String,Object> map : PhotoList) {
        map.put("PATH", AppSetting.PROJECT_NAME + "/KM/DOC" + map.get("PATH"));
        map.put("PATH_THUMBNAIL", AppSetting.PROJECT_NAME + "/KM/DOC" + map.get("PATH_THUMBNAIL"));
      }
    }
    return PhotoList;
  }
}