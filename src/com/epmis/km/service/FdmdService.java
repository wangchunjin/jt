package com.epmis.km.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.epmis.km.vo.KmFdmd;
import com.epmis.sys.util.UserInfo;
import com.epmis.sys.vo.SmSwbs;

public abstract interface FdmdService
{
  public abstract List<Map<String, Object>>  ShowFdmdTree(String ParentId,String Fdmd_type_id);
  public abstract Map<String, Object> GetFdmd(String FdmdId);
  public abstract String UpdateFdmd(KmFdmd kmFdmd);
  public abstract List ShowFdmdTypeTable();
  public abstract String DelFdmdType(String FdmdTypeId); 
  public abstract String AddFdmd(KmFdmd kmFdmd);
  public abstract String delFdmd(String FdmdId);

}