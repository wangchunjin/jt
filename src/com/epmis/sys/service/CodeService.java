package com.epmis.sys.service;

import com.epmis.sys.vo.CmCode;
import java.util.List;
import java.util.Map;

public abstract interface CodeService
{
  public abstract List SysCodeTable(String paramString1, String paramString2);

  public abstract Map<String, Object> GetCodeInfoByWid(String paramString);

  public abstract String AddCode(CmCode paramCmCode);

  public abstract String DelCode(String paramString);

  public abstract String UpdateCode(CmCode paramCmCode);

  public abstract List SysCodeTable(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);
}