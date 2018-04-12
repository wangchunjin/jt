package com.epmis.sys.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract interface BaseJdbcDao
{
  public abstract Object getFieldValue(String paramString);

  public abstract Map<String, Object> queryMap(String paramString);

  public abstract List<Map<String, Object>> queryListMap(String paramString);

  public abstract int getCount(String paramString);

  public abstract boolean delete(String paramString);

  public abstract boolean update(String paramString);


  public abstract boolean insert(String paramString);

  public abstract List<Map<String, Object>> OpBySql(String paramString);

  public abstract boolean insertMapInfo(Map<String, Object> paramMap, String paramString)
    throws SQLException;

  public abstract boolean insertListMapInfo(List<Map<String, Object>> paramList, String paramString)
    throws Exception;
}