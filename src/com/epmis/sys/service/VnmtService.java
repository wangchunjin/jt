package com.epmis.sys.service;

import com.epmis.ds.vo.DsCmVnmtSupp;
import com.epmis.sys.vo.CmVndt;
import com.epmis.sys.vo.CmVnmt;
import java.util.List;
import java.util.Map;

public abstract interface VnmtService
{
  public abstract List SysVnmtTable(String paramString1, String paramString2);

  public abstract Map<String, Object> GetVnmtInfoById(String paramString);

  public abstract String AddVnmt(CmVnmt paramCmVnmt, DsCmVnmtSupp paramDsCmVnmtSupp);

  public abstract String DelVnmt(String paramString);

  public abstract String UpdateVnmt(CmVnmt paramCmVnmt, DsCmVnmtSupp paramDsCmVnmtSupp);

  public abstract List SysVnmtTree(String paramString1, String paramString2);

  public abstract String UpdateVnmtOther(CmVnmt paramCmVnmt);

  public abstract List VndtTable(String paramString);

  public abstract String AddVndt(CmVndt paramCmVndt);

  public abstract Map<String, Object> GetVndtInfoById(String paramString);

  public abstract String UpdateVndt(CmVndt paramCmVndt);

  public abstract String DelVndt(String paramString);
}