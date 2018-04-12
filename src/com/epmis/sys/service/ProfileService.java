package com.epmis.sys.service;

import com.epmis.sys.vo.CmProfile;
import java.util.List;
import java.util.Map;

public abstract interface ProfileService
{
  public abstract List SysProfileTable(String paramString);

  public abstract Map<String, Object> GetProfileInfoById(String paramString);

  public abstract String AddProfile(CmProfile paramCmProfile);

  public abstract String DelProfile(String paramString1, String paramString2);

  public abstract String UpdateProfile(CmProfile paramCmProfile);

  public abstract List DatactrListTable(String paramString1, String paramString2);

  public abstract String DeleteProfctr(String paramString);

  public abstract String InsertProfctr(String paramString1, String paramString2);

  public abstract List ProfileUserTable(String paramString);

  public abstract List AddUserTable(String paramString);

  public abstract String AddProfileUser(String paramString1, String paramString2);

  public abstract String GetUserNameByProfileId(String paramString);

  public abstract String DelProfileUser(String paramString);

  public abstract String CopyProfile(String paramString1, String paramString2);
}