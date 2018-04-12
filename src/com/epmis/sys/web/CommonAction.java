package com.epmis.sys.web;

import com.epmis.ds.service.DsLdzlService;
import com.epmis.sys.service.BaseJdbcService;
import com.epmis.sys.service.UserService;
import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.DataTypeUtil;
import com.epmis.sys.util.FileUtil;
import com.epmis.sys.util.Guid;
import com.epmis.sys.util.SpringContextHolder;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.sys.vo.CmUsers;
import com.opensymphony.xwork2.ActionSupport;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
@SuppressWarnings("serial")
public class CommonAction extends ActionSupport
  implements ServletRequestAware
{
 

BaseJdbcService baseJdbcService = (BaseJdbcService)SpringContextHolder.getBean("baseJdbcService");

  @Autowired
  private DsLdzlService dsLdzlService;
  private CmUsers cmUsers;
  private List<Map<String, Object>> items;
  HttpServletRequest request;
  HttpServletResponse response = ServletActionContext.getResponse();
  private String name;
  private String contact_way;
  private String version;
  private File uploadfile;

  @Autowired
  private UserService userService;
  private String title;
  private File pic;
  private File headpic;
  private String rows;
  private String page;

  public CmUsers getCmUsers() { return this.cmUsers; }

  public void setCmUsers(CmUsers cmUsers)
  {
    this.cmUsers = cmUsers;
  }

  public List<Map<String, Object>> getItems()
  {
    return this.items;
  }

  public void setItems(List<Map<String, Object>> items) {
    this.items = items;
  }

  public String login() throws IOException {
    String result = this.baseJdbcService.login();
    return result;
  }
  public String userLogin() throws IOException {
    String result = "";
    try {
      result = this.baseJdbcService.UserLogin(this.cmUsers);
      if (result.equals("success")) {
//    	  System.out.println("这里变动");
        this.baseJdbcService.InsertLog("项目平台-登录成功");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return result;
  }
  public String UserLogout() throws IOException {
    try {
      UserInfo userInfo = (UserInfo)this.request.getSession().getAttribute("UserInfo");
      String UserId = userInfo.getUserId();
      if (DataTypeUtil.validate(UserId)) {
        File file = new File(AppSetting.PROJECT_PATH + "/KM/temp/" + UserId);
        if (file.exists()) {
          FileUtil.delFolder(AppSetting.PROJECT_PATH + "/KM/temp/" + UserId);
        }
      }
      this.baseJdbcService.InsertLog("项目平台-注销成功");
      this.request.getSession().removeAttribute("UserInfo");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "success";
  }
  public String index() {
    String result = "";
    try {
      result = this.baseJdbcService.InitLogin(this.request);
      UserInfo userInfo = (UserInfo)this.request.getSession().getAttribute("UserInfo");
      List ldzlListMap = this.dsLdzlService.LdzlModuleByUserId(userInfo.getUserId());
      List zjlrListMap = this.dsLdzlService.ZjlrModuleByUserId(userInfo.getUserId());
      this.request.getSession().setAttribute("ldzlListMap", ldzlListMap);
      this.request.getSession().setAttribute("zjlrListMap", zjlrListMap);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "success";
  }
  public String ChangeUserInfo() {
    try {
      String type = this.request.getParameter("type");
      UserInfo userInfo = (UserInfo)this.request.getSession().getAttribute(type);
      Map userinfo = this.baseJdbcService.ChangeUserInfo(userInfo);
      this.request.setAttribute("CmUserInfo", userinfo);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "success";
  }

  public void OpBySql() throws IOException {
    List result = null;
    String sql = this.request.getParameter("sqlStr");
    try {
      sql = URLDecoder.decode(sql, "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      result = this.baseJdbcService.OpBySql(sql);
    } catch (Exception e) {
      e.printStackTrace();
    }
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void OpSelectBySql() throws IOException {
    String result = null;
    String sql = this.request.getParameter("sqlStr");
    String defaultId = this.request.getParameter("defaultId");
    try {
      result = this.baseJdbcService.OpSelectBySql(sql, defaultId);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Map map = new HashMap();
    map.put("OpStr", result);
    WriterJsonArray.writerJSONArray(map, this.response);
  }

  public void setServletRequest(HttpServletRequest request)
  {
    this.request = request;
  }

  public void GetGuid() throws IOException {
    Map result = new HashMap();
    try
    {
      result.put("guid", Guid.getGuid());
    } catch (Exception e) {
      e.printStackTrace();
    }
    WriterJsonArray.writerJSONArray(result, this.response);
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContact_way() {
    return this.contact_way;
  }

  public void setContact_way(String contactWay) {
    this.contact_way = contactWay;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public void GetRegister() {
    String result = this.baseJdbcService.GetRegister(this.name, this.contact_way, this.version);
    WriterJsonArray.writerJSONArray(result, this.response);
  }

  public File getUploadfile()
  {
    return this.uploadfile;
  }

  public void setUploadfile(File uploadfile) {
    this.uploadfile = uploadfile;
  }

  public void Register() {
    String result = this.baseJdbcService.Register(this.uploadfile);
    WriterJsonArray.writerJSONArray(result, this.response);
  }
  public void GetPassword() {
    String password = this.request.getParameter("password");
    String result = this.baseJdbcService.GetPassword(password);
    Map root = new HashMap();
    root.put("result", "success");
    root.put("password", result);
    WriterJsonArray.writerJSONArray(root, this.response);
  }

  public void SaveUserInfo()
  {
    String result = this.userService.UpdateUser(this.cmUsers);
    result = this.userService.UpdateUserOther(this.cmUsers);
    WriterJsonArray.writerJSONArray(result, this.response);
  }

  public String getTitle()
  {
    return this.title;
  }

  public File getHeadpic() {
    return this.headpic;
  }

  public void setHeadpic(File headpic) {
    this.headpic = headpic;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public File getPic() {
    return this.pic;
  }

  public void setPic(File pic) {
    this.pic = pic;
  }

  public void saveLoginPic() {
    String result = this.baseJdbcService.saveLoginPic(this.headpic, this.pic, this.title);
    WriterJsonArray.writerJSONArray(result, this.response);
  }

  public String getRows()
  {
    return this.rows;
  }

  public void setRows(String rows) {
    this.rows = rows;
  }

  public String getPage() {
    return this.page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public void log() {
    int intPage = Integer.parseInt((this.page == null) || (this.page == "0") ? "1" : this.page);

    int number = Integer.parseInt((this.rows == null) || (this.rows == "0") ? "20" : this.rows);

    int start = (intPage - 1) * number;
    List result = this.baseJdbcService.sysLog(start, number);
    Map jsonMap = new HashMap();
    jsonMap.put("total", Integer.valueOf(this.baseJdbcService.getSysLogCount()));
    jsonMap.put("rows", result);

    JSONObject json = JSONObject.fromObject(jsonMap);
    this.response.setContentType("application/json;charset=UTF-8");
    this.response.setCharacterEncoding("utf-8");
    this.response.setHeader("Charset", "utf-8");
    this.response.setHeader("Cache-Control", "no-cache");
    try {
      this.response.getWriter().println(json.toString());
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void DelLog() { String wids = this.request.getParameter("wids");
    String result = this.baseJdbcService.delLog(wids);
    WriterJsonArray.writerJSONArray(result, this.response); }

  public void ReadSystemPro()
  {
    String KEY = this.request.getParameter("KEY");
    String value = "";
    ResourceBundle rb = ResourceBundle.getBundle("system");
    try {
      value = DataTypeUtil.formatDbColumn(rb.getString(KEY));
    } catch (Exception e) {
      e.getMessage();
    }
    WriterJsonArray.writerJSONArray(value, this.response);
  }
}