package com.epmis.sys.service.imp;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.service.BaseJdbcService;
import com.epmis.sys.service.UserService;
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.DESEncrypt;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.DateUtil;
import com.epmis.sys.util.FileUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.XMLUtil;
import com.epmis.sys.vo.CmUsers;
import com.epmis.webService.service.ILicenseService;
import com.epmis.webService.util.LicenseObj;
import com.epmis.webService.web.LicenseWeb;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("baseJdbcService")
public class BaseJdbcServiceImpl
  implements BaseJdbcService
{

  @Autowired
  @Qualifier("baseJdbcDao")
  private BaseJdbcDao baseJdbcDao;

  @Autowired
  @Qualifier("userService")
  private UserService userService;

  public String UserLogin(CmUsers cmUsers)
  {
    String result = "";
    String UserName = cmUsers.getUserName();
    String PassWord = cmUsers.getPassword();
    HttpServletRequest request = ServletActionContext.getRequest();

//    String res = checkRegisterFile(request);//
//    if (DataTypeUtil.validate(res)) {//
//      request.getSession().setAttribute("_error", res);//
//      request.getSession().setAttribute("_info", "");//
//      return "wzc";//
//    }//
    
    if (DataTypeUtil.validate(UserName)) {
    	//<hxl  
    	int ran = (int) Math.floor(Math.random() * 1000);    	
		String fileName = "";
			fileName = DateUtil.getTodaytime().replace("-", "").replace(" ", "").replace(":", "") + ran;
			System.out.println("  "+fileName);
    	//>hxl
    	
      String sql = "SELECT A.USER_ID,A.USER_NAME,A.ACTUAL_NAME,A.DEF_PROJ FROM CM_USERS A WHERE A.USER_NAME='" + UserName + "' AND DISABLE_FLAG = '0' ";
      if (DataTypeUtil.validate(PassWord))
        sql = sql + " AND A.PASSWORD='" + DESEncrypt.desEncode(PassWord) + "'";
      else {
        sql = sql + "AND (A.PASSWORD='' OR A.PASSWORD IS NULL)";
      }
      Map userMap = this.baseJdbcDao.queryMap(sql);
      if (DataTypeUtil.validate(userMap)) {
        UserInfo userInfo = new UserInfo();
        String UserId = userMap.get("USER_ID").toString();
        String ActualName = userMap.get("ACTUAL_NAME").toString();
        userInfo.setUserId(UserId);
        userInfo.setActualName(ActualName);
        userInfo.setUserName(UserName);
        String sqlss = "select MOBIL_NO from cm_users where USER_ID = '"+UserId+"'";
        Map<String,Object> sss = this.baseJdbcDao.queryMap(sqlss);
        request.getSession().setAttribute("sssss", sss);
        request.getSession().setAttribute("UserInfo", userInfo);
        request.getSession().setAttribute("GROUP_ID", Guid.getGuid());
        //<hxl
        request.getSession().setAttribute("dlogin",fileName);
        String sqllogon="update cm_users set logon_value='"+fileName+"' where USER_ID = '"+UserId+"'";
        this.baseJdbcDao.update(sqllogon);
        //>hxl
        sql = "SELECT DATACTR_CODE FROM CM_PROFCTR A JOIN CM_PROFILE B ON A.PROFILE_ID = B.PROFILE_ID  WHERE (A.PROFILE_ID = (SELECT PROFILE_ID FROM CM_USERS WHERE USER_ID='" + UserId + "') AND B.PROF_TYPE='0')";
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

        result = "success";
      } else {
        result = "input";
      }
    }
    else {
      result = "input";
    }
    request.getSession().setAttribute("result", result);
    return result;
  }
  public String InitLogin(HttpServletRequest request) {
    UserInfo userInfo = (UserInfo)request.getSession().getAttribute("UserInfo");
    String UserId = userInfo.getUserId();
    
    request.getSession().setAttribute("items", getChildrenModule("0", 1, UserId));
    return "success";
  }
  public List<Map<String, Object>> getChildrenModule(String modulecode, int level, String UserId) {
    String sql = "";
    if (level == 2)
      sql = "SELECT * FROM CM_MODULE A  WHERE PARENT_MODULE_CODE='" + modulecode + "' AND ENABLED='1' and module_code in(select PARENT_MODULE_CODE from cm_usermdl where user_id='" + UserId + "')    UNION  SELECT * FROM CM_MODULE A  WHERE PARENT_MODULE_CODE='" + modulecode + "' AND ENABLED='1' and module_code in(select PARENT_MODULE_CODE from CM_USERMDL where role_id=(SELECT C.ROLE_ID FROM CM_RLUSER C WHERE C.USER_ID='" + UserId + "'))   ORDER BY SEQ_NUM";
    else 
      sql = "SELECT A.*,(SELECT COUNT(C.WID) FROM CM_USERLINK C WHERE C.MODULE_CODE = A.MODULE_CODE) AS LINK FROM CM_MODULE A  WHERE PARENT_MODULE_CODE='" + modulecode + "' AND ENABLED='1' AND A.MODULE_CODE IN(SELECT B.MODULE_CODE FROM  CM_USERMDL B WHERE B.USER_ID='" + UserId + "' OR B.ROLE_ID IN (SELECT C.ROLE_ID FROM CM_RLUSER C WHERE C.USER_ID='" + UserId + "')) ORDER BY SEQ_NUM";
    
    List<Map<String,Object>> moduleList = this.baseJdbcDao.queryListMap(sql);
    List items = new ArrayList();
//    String ModuleCode = getStringByLicense("ModuleCode");  //
//    String[] ModuleCodeList = ModuleCode.split(",");  //
//    Arrays.sort(ModuleCodeList);   //
    for (Map<String,Object> m : moduleList) {
//      if ((modulecode.equals("0")) &&  // 
//        (Arrays.binarySearch(ModuleCodeList, m.get("MODULE_CODE").toString()) < 0))
//      {
//        continue;
//      }//
      Map item = new HashMap();
      item.put("id", m.get("MODULE_CODE").toString());
      item.put("text", m.get("MODULE_NAME").toString());
      item.put("wid", m.get("WID").toString());
      if (level == 3) {
        if (m.get("LINK").toString().equals("0")) {
          item.put("className", "shortlinkDel");
          item.put("title", "收藏");
        } else {
          item.put("className", "shortlinkAdd");
          item.put("title", "取消收藏");
        }
      }

      String countSql = "";
      if (level == 1)    	 
        countSql = "SELECT COUNT(*) FROM CM_MODULE A WHERE PARENT_MODULE_CODE='" + m.get("MODULE_CODE").toString() + "' AND ENABLED='1'";
      else 
        countSql = "SELECT COUNT(*) FROM CM_MODULE A WHERE PARENT_MODULE_CODE='" + m.get("MODULE_CODE").toString() + "' AND ENABLED='1'  AND A.MODULE_CODE IN(SELECT B.MODULE_CODE FROM  CM_USERMDL B WHERE B.USER_ID='" + UserId + "' OR B.ROLE_ID IN (SELECT C.ROLE_ID FROM CM_RLUSER C WHERE C.USER_ID='" + UserId + "'))";
      
      if (this.baseJdbcDao.getCount(countSql) > 0) {
    	item.put("children", getChildrenModule(m.get("MODULE_CODE").toString(), level + 1, UserId));
        items.add(item);
      } else {
    	  		item.put("attributes", m.get("URLTO").toString());
        if (level != 2) {
          items.add(item);
        }
      }
    }
    return items;
  }

  public List<Map<String, Object>>  initLabel(String ModuleName) {
    String sql = "SELECT WID,MODULE,LABEL,LABELURL,ISDISPLAY,ISDEFAULT,FILTER,CURRENTUSER,ORDERNUM FROM CM_LABLE WHERE MODULE='" + ModuleName + "' AND ISDISPLAY='1' ORDER BY ORDERNUM ASC";
    List<Map<String, Object>>  moduleList = this.baseJdbcDao.queryListMap(sql);
    return moduleList;
  }

  public List<Map<String, Object>> OpBySql(String sql) {
    List ResultList = null;
    if (sql.toUpperCase().trim().startsWith("SELECT"))
      ResultList = this.baseJdbcDao.queryListMap(sql);
    else {
      ResultList = this.baseJdbcDao.OpBySql(sql);
    }
    return ResultList;
  }

  public String OpSelectBySql(String sql, String defaultId) {
    String optionStr = "";
    List<Map<String,Object>> listMap = this.baseJdbcDao.queryListMap(sql);
    if (DataTypeUtil.validate(listMap)) {
      for (Map<String,Object> map : listMap) {
        String id = "";
        String value = "";
        String selected = "";
        Iterator keys = map.keySet().iterator();
        int i = 0;
        while (keys.hasNext()) {
          if (i == 0) {
            String key = (String)keys.next();
            id = DataTypeUtil.formatDbColumn(map.get(key));
          } else if (i == 1) {
            String key = (String)keys.next();
            value = DataTypeUtil.formatDbColumn(map.get(key));
          }
          i++;
        }
        if ((DataTypeUtil.validate(defaultId)) && (defaultId.equals(id))) {
          selected = "selected";
        }
        optionStr = optionStr + "<option value='" + id + "' " + selected + " >" + value + "</option>";
      }
    }
    return optionStr;
  }

  public String GetChildIdsByParentId(String tableName, String parentId, String chindId, String parentValue)
  {
    String childValues = "";
    String sql = "select " + chindId + " from " + tableName + " where " + parentId + " = '" + parentValue + "'";
    List<Map<String,Object>> listMap = this.baseJdbcDao.queryListMap(sql);
    if (DataTypeUtil.validate(listMap)) {
      for (Map<String,Object> map : listMap) {
        String childValue = DataTypeUtil.formatDbColumn(map.get(chindId.toUpperCase()));
        childValues = childValues + "," + childValue + GetChildIdsByParentId(tableName, parentId, chindId, childValue);
      }
    }

    return childValues;
  }

  public String checkRegisterFile(HttpServletRequest request)
  {
    try
    {
      File file = new File(AppSetting.PROJECT_PATH + "/license.txt");

      if (!file.exists()) {
        LicenseWeb.downLicense(getMACAddress());
        if (!file.exists()) {
          return "注册文件不存在!";
        }
      }
      String str1 = getLicenseFile(AppSetting.PROJECT_PATH + "/license.txt");
      Document localDocument = XMLUtil.parse(str1);
      String str2 = XMLUtil.getElementByDataXml(localDocument, "NetworkCardAddress");

      String str3 = XMLUtil.getElementByDataXml(localDocument, "StartDate");

      String str4 = XMLUtil.getElementByDataXml(localDocument, "EndDate");
      String str5 = XMLUtil.getElementByDataXml(localDocument, "AppNum");
      if ((DataTypeUtil.validate(str2)) && (DataTypeUtil.validateDate(str3)) && (DataTypeUtil.validateDate(str4)))
      {
        String mac = getMACAddress();
        if (!str2.equals(mac)) {
          return "mac地址不匹配!";
        }
        Date localDate1 = DateUtil.parseDate(DateUtil.getTodaydate());

        Date localDate2 = DateUtil.parseDate(str3);
        Date localDate3 = DateUtil.parseDate(str4);
        request.getSession().setAttribute("StartDate", str3);
        request.getSession().setAttribute("EndDate", str4);
        if ((localDate1.after(localDate3)) || (localDate1.before(localDate2)))
        {
          return "不在有效期内!(开始时间=" + localDate2 + ";结束时间=" + localDate3 + "),";
        }
      }
      else
      {
        return "注册文件损坏!";
      }
      if (DataTypeUtil.validate(str5)) {
        String sql = "SELECT COUNT(WID) NUM FROM CM_USERS WHERE  ENSEMBLE ='Y'";
        int AppNum = this.baseJdbcDao.getCount(sql);
        if ((DataTypeUtil.validate(str5)) && (Integer.valueOf(str5).intValue() < AppNum))
          return "超出许可允许的客户端个数(共" + str5 + "个)";
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      System.out.print(e.getMessage());
    }

    return "";
  }

  public static String getMACAddress() throws Exception {
    InetAddress ia = InetAddress.getLocalHost();
    byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < mac.length; i++) {
      if (i != 0) {
        sb.append("-");
      }

      String s = Integer.toHexString(mac[i] & 0xFF);
      sb.append(s.length() == 1 ? 0 + s : s);
    }

    return sb.toString().toUpperCase();
  }

  private static String getIpAddress() throws Exception {
    ILicenseService service = LicenseObj.getServiceInstance();
    return service.getIpAddress();
  }

  public static String getLicenseFile(String paramString) throws Exception
  {
    if (DataTypeUtil.validate(paramString))
    {
      byte[] paramArrayOfByte = FileUtil.readFileBinary(paramString);
      if (DataTypeUtil.validate(paramArrayOfByte))
      {
        byte[] arrayOfByte1 = new byte[8];
        byte[] arrayOfByte2 = new byte[paramArrayOfByte.length - 8];
        for (int i = 0; i < paramArrayOfByte.length; i++)
          if (i < 8)
            arrayOfByte1[i] = paramArrayOfByte[i];
          else
            arrayOfByte2[(i - 8)] = paramArrayOfByte[i];
        try
        {
          byte[] arrayOfByte3 = DESEncrypt.decode(arrayOfByte1, arrayOfByte2);
          if (!arrayOfByte3.equals(""))
            return new String(arrayOfByte3);
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
      return null;
    }
    return null;
  }

  public String GetRegister(String name, String contactWay, String version)
  {
    String str2 = AppSetting.PROJECT_PATH + "/register.txt";
    try {
      String mac = getMACAddress();
      String ip = getIpAddress();
      Document newdocument = DocumentHelper.createDocument();
      Element document = newdocument.addElement("document");
      Element record = document.addElement("record");
      Element NetworkCardAddress = record.addElement("NetworkCardAddress");
      NetworkCardAddress.setText(mac);
      Element IpAddress = record.addElement("IpAddress");
      IpAddress.setText(ip);
      Element CompanyName = record.addElement("CompanyName");
      CompanyName.setText(name);
      Element ProjName = record.addElement("ProjName");
      ProjName.setText(AppSetting.PROJECT_NAME.substring(1));
      Element ContactWay = record.addElement("ContactWay");
      ContactWay.setText(contactWay);
      Element Version = record.addElement("Version");
      Version.setText(version);

      System.out.print(newdocument.asXML());
      if (DataTypeUtil.validate(newdocument.asXML()))
        createRegisterFile(str2, newdocument.asXML());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return AppSetting.PROJECT_NAME + "/register.txt";
  }

  public static void createRegisterFile(String paramString1, String paramString2) throws Exception
  {
    byte[] arrayOfByte1 = DESEncrypt.getKey();
    if ((DataTypeUtil.validate(arrayOfByte1)) && (DataTypeUtil.validate(paramString2)))
    {
      FileUtil.writerFileBinary(paramString1, arrayOfByte1, false);
      byte[] arrayOfByte2 = DESEncrypt.encode(arrayOfByte1, paramString2.getBytes());
      FileUtil.writerFileBinary(paramString1, arrayOfByte2, true);
    }
  }

  public String Register(File uploadfile) {
    HttpServletRequest request = ServletActionContext.getRequest();
    String path = AppSetting.PROJECT_PATH + "/license.txt";
   FileUtil.uploadFile(uploadfile, path);
   String res = checkRegisterFile(request);
    if (DataTypeUtil.validate(res)) {
      request.getSession().setAttribute("_info", "");
      request.getSession().setAttribute("_error", res);
      return "wzc";
    }
    request.getSession().setAttribute("_info", "注册成功");
    request.getSession().setAttribute("_error", "");
    return "wzc";
   
  }

  public Map<String, Object> ChangeUserInfo(UserInfo userInfo)
  {
    Map userinfo = this.userService.GetUserInfoById(userInfo.getUserId());
    return userinfo;
  }

  public String GetPassword(String password)
  {
    try {
      if (DataTypeUtil.validate(password))
        password = DESEncrypt.desEncode(password);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return password;
  }

  public String saveLoginPic(File headpic, File pic, String title) {
    String path = AppSetting.PROJECT_PATH + "/KM/LOGIN";
    File file = new File(path);
    if (!file.exists())
      file.mkdirs();
    try
    {
      if (DataTypeUtil.validate(headpic)) {
        FileUtil.uploadFile(headpic, path + "/head.png");
      }
      if (DataTypeUtil.validate(pic)) {
        FileUtil.uploadFile(pic, path + "/login.png");
      }
      if (DataTypeUtil.validate(title))
        FileUtil.writerFile(path + "/login.txt", title, false);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return "success";
  }

  public String login() {
    String title = "";
    String pic = "";
    HttpServletRequest request = ServletActionContext.getRequest();
    String path = request.getRealPath("/") + "KM/LOGIN";
    try
    {
      File file = new File(path + "/login.txt");
      if (file.exists())
        title = FileUtil.readerFile(path + "/login.txt");
      else {
        title = "在'系统管理'->'基础信息'->'系统配置'中，自定义您的登陆页标题、图片及首页logo";
      }
      File picFile = new File(path + "/login.png");
      if (picFile.exists())
        pic = request.getContextPath() + "/KM/LOGIN/login.png";
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    request.setAttribute("title", title);
    request.setAttribute("pic", pic);

    return "success";
  }

  public String getStringByLicense(String nodeName) {
    File file = new File(AppSetting.PROJECT_PATH + "/license.txt");
    if (!file.exists()) {
      return "注册文件不存在!";
    }
    String str1 = "";
    try {
      str1 = getLicenseFile(AppSetting.PROJECT_PATH + "/license.txt");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    Document localDocument = XMLUtil.parse(str1);
    String str2 = XMLUtil.getElementByDataXml(localDocument, nodeName);
    return str2;
  }

  public void InsertLog(String msg) {
    HttpServletRequest request = ServletActionContext.getRequest();
    UserInfo user = (UserInfo)request.getSession().getAttribute("UserInfo");
    String ip = getClientIp(request);
    String actual_name = user.getActualName();
    String user_name = user.getUserName();
    String port = String.valueOf(request.getServerPort());
    String sql = "insert into sys_log(wid, loginlog_id, actual_name, user_name, login_time, ip, machine_name, state, port)values('" + 
      Guid.getGuid() + "','" + Guid.getGuid() + "','" + actual_name + "','" + user_name + "',now(),'" + ip + "','','" + msg + "','" + port + "')";
    this.baseJdbcDao.insert(sql);
  }
  public static String getClientIp(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if ((StringUtils.isNotEmpty(ip)) && (!"unKnown".equalsIgnoreCase(ip)))
    {
      int index = ip.indexOf(",");
      if (index != -1) {
        return ip.substring(0, index);
      }
      return ip;
    }

    ip = request.getHeader("X-Real-IP");
    if ((StringUtils.isNotEmpty(ip)) && (!"unKnown".equalsIgnoreCase(ip))) {
      return ip;
    }
    return request.getRemoteAddr();
  }

  public List<Map<String, Object>> sysLog(int start, int number)
  {
    return this.baseJdbcDao.queryListMap(" SELECT WID, USER_NAME,ACTUAL_NAME,IP,STATE, DATE_FORMAT(LOGIN_TIME,'%y-%m-%d %H:%i:%s') LOGIN_TIME FROM  SYS_LOG ORDER BY  LOGIN_TIME DESC LIMIT " + start + " ," + number);
  }

  public int getSysLogCount()
  {
    return this.baseJdbcDao.getCount("SELECT COUNT(WID) FROM  SYS_LOG");
  }

  public String delLog(String wids) {
    this.baseJdbcDao.delete("DELETE FROM SYS_LOG WHERE WID IN ('" + wids.replace(",", "','") + "')");
    return "success";
  }
}