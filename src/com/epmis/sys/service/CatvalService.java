package com.epmis.sys.service;

import com.epmis.sys.vo.CmCatval;
import java.util.List;
import java.util.Map;

public abstract interface CatvalService
{
  public abstract List CatvalTableOrderByKey(String paramString);

  public abstract List CatvalTreeByKey(String paramString1, String paramString2);

  public abstract String AddCatval(String paramString1, String paramString2, String paramString3);

  public abstract String DelCatval(String paramString);

  public abstract List AddCatvalAllTree(String paramString1, String paramString2, String paramString3);

  public abstract List ShowCatvalTypeTable(String paramString);

  public abstract String DelCatvalType(String paramString);

  public abstract String AddCatvalCode(CmCatval paramCmCatval);

  public abstract String DelCatvalCode(String paramString);

  public abstract Map<String, Object> GetCatvalInfo(String paramString);

  public abstract String UpdateCatvalCode(CmCatval paramCmCatval);
}