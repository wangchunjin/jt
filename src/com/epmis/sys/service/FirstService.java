package com.epmis.sys.service;

import com.epmis.sys.util.UserInfo;
import java.util.List;

public abstract interface FirstService
{
  public abstract List MajorTable(UserInfo paramUserInfo, String paramString1, String paramString2);

  public abstract List ProjectPic(String paramString);
}