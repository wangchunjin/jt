package com.epmis.sys.service;

import com.epmis.sys.util.UserInfo;
import com.epmis.sys.vo.CmUsers;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface BaseJdbcService
{
  public abstract String UserLogin(CmUsers paramCmUsers);

  public abstract String InitLogin(HttpServletRequest paramHttpServletRequest);

  public abstract List initLabel(String paramString);

  public abstract List<Map<String, Object>> OpBySql(String paramString);

  public abstract String OpSelectBySql(String paramString1, String paramString2);

  public abstract String GetChildIdsByParentId(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract String getStringByLicense(String paramString);

  public abstract String Register(File paramFile);

  public abstract Map<String, Object> ChangeUserInfo(UserInfo paramUserInfo);

  public abstract String GetPassword(String paramString);

  public abstract String saveLoginPic(File paramFile1, File paramFile2, String paramString);

  public abstract String login();

  public abstract String GetRegister(String paramString1, String paramString2, String paramString3);

  public abstract void InsertLog(String paramString);

  public abstract List<Map<String, Object>> sysLog(int paramInt1, int paramInt2);

  public abstract int getSysLogCount();

  public abstract String delLog(String paramString);

  public abstract String checkRegisterFile(HttpServletRequest paramHttpServletRequest);
}