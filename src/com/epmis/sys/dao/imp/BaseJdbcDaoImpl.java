package com.epmis.sys.dao.imp;

import com.epmis.sys.dao.BaseJdbcDao;
import com.epmis.sys.util.DataTypeUtil;

import java.io.PrintStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Component;

@Component("baseJdbcDao")
public class BaseJdbcDaoImpl
  implements BaseJdbcDao
{

  @Autowired
  @Qualifier("jdbcTemplate")
  private JdbcTemplate jdbcTemplate;

  public Object getFieldValue(String sql)
  {
    System.out.println(sql);
    Object result = "";
    List rows = this.jdbcTemplate.queryForList(sql);
    if (rows.size() > 0) {
      Map map = (Map)rows.get(0);
      Iterator keys = map.keySet().iterator();
      if (keys.hasNext()) {
        String key = (String)keys.next();
        result = map.get(key);
      }
    }

    return result;
  }

  public Map<String, Object> queryMap(String sql) {
    System.out.println(sql);
    Map result = null;
    List rows = this.jdbcTemplate.queryForList(sql);
    if (rows.size() > 0) {
      result = (Map)rows.get(0);
    }
    return result;
  }

  public List<Map<String, Object>> queryListMap(String sql)
  {
    System.out.println(sql);
    List<Map<String, Object>> result = this.jdbcTemplate.queryForList(sql);
    

    return result;
  }
  public int getCount(String sql) {
    System.out.println(sql);
    int result = this.jdbcTemplate.queryForInt(sql);
    return result;
  }
  public boolean delete(String sql) {
    System.out.println(sql);
    int result = this.jdbcTemplate.update(sql);
    if (result > 0) {
      return true;
    }
    return false;
  }

  public boolean insert(String sql) {
    System.out.println(sql);
    int result = this.jdbcTemplate.update(sql);
    if (result > 0) {
      return true;
    }
    return false;
  }

  public boolean update(String sql)
  {
    System.out.println(sql);
    int result = this.jdbcTemplate.update(sql);
    if (result > 0) {
      return true;
    }
    return false;
  }

  public List<Map<String, Object>> OpBySql(String sql)
  {
    System.out.println(sql);
    List ListMap = new ArrayList();
    Map map = new HashMap();
    int result = this.jdbcTemplate.update(sql);
    if (result > 0)
      map.put("result", "success");
    else {
      map.put("result", "error");
    }
    ListMap.add(map);
    return ListMap;
  }

  public boolean insertMapInfo(Map<String, Object> paramMap, String paramString) throws SQLException
  {
    Map localMap = new HashMap();
    try {
      localMap = initColumnType(paramString);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return InsertColumn(paramMap, paramString, localMap);
  }

  public boolean insertListMapInfo(List<Map<String, Object>> paramList, String paramString) throws Exception
  {
    Map localMap = new HashMap();
    localMap = initColumnType(paramString);
    if (DataTypeUtil.validate(paramList))
      for (int i = 0; i < paramList.size(); i++)
        InsertColumn((Map)paramList.get(i), paramString, localMap);
    return true;
  }

  public Map<String, String> initColumnType(String paramString)
    throws SQLException
  {
    HashMap localHashMap = new HashMap();

    String str1 = "SELECT * FROM " + paramString + " WHERE 1=2";
    SqlRowSet sqlRowSet = this.jdbcTemplate.queryForRowSet(str1);
    SqlRowSetMetaData sqlRsmd = sqlRowSet.getMetaData();
    int columnCount = sqlRsmd.getColumnCount();
    for (int i = 1; i <= columnCount; i++) {
      String str3 = sqlRsmd.getColumnClassName(i);
      String[] arrayOfString = str3.replace(".", ";").split(";");

      localHashMap.put(sqlRsmd.getColumnName(i), arrayOfString[(arrayOfString.length - 1)]);
    }
    return localHashMap;
  }

  public boolean InsertColumn(Map<String, Object> paramMap, String paramString, Map<String, String> paramMap1) throws SQLException {
    Object localObject1 = null;
    if ((DataTypeUtil.validate(paramMap)) && (DataTypeUtil.validate(paramString)))
    {
      String str2 = "";
      String str3 = "";
      ArrayList localArrayList = new ArrayList();
      Set localSet = paramMap.entrySet();
      Object localObject2 = localSet.iterator();

      while (((Iterator)localObject2).hasNext())
      {
        Object localObject3 = (Map.Entry)((Iterator)localObject2).next();
        String str4 = ((Map.Entry)localObject3).getKey().toString();
        String str5 = (String)paramMap1.get(str4.toUpperCase());
        if (str5 != null)
        {
          str2 = str2 + (DataTypeUtil.validate(str2) ? "," + str4 : str4);
          String str6 = "";
          str6 = initColumnType(str5, ((Map.Entry)localObject3).getValue());
          if ((str6.equals("''")) && ((str5.equalsIgnoreCase("Date")) || (str5.equalsIgnoreCase("Timestamp"))))
            str3 = str3 + (DataTypeUtil.validate(str3) ? ",null" : "null");
          else {
            str3 = str3 + (DataTypeUtil.validate(str3) ? "," + str6 : str6);
          }
        }
        else if ((!str4.endsWith("_FDate")) && (!str4.endsWith("_FTime")))
        {
          throw new SQLException("Error : Table " + paramString + " Not Find Field " + str4 + " !");
        }
      }
      localObject2 = "INSERT INTO " + paramString + "(" + str2 + ") VALUES(" + str3 + ")";
      System.out.println((String)localObject2);
      this.jdbcTemplate.update((String)localObject2);
    }
    return false;
  }

  public String initColumnType(String paramString, Object paramObject) throws SQLException
  {
    String str = "''";
    if ((paramString != null) && (paramObject != null))
      if (("BigDecimal".equalsIgnoreCase(paramString)) || ("Int".equalsIgnoreCase(paramString)) || ("Integer".equalsIgnoreCase(paramString)) || ("Float".equalsIgnoreCase(paramString)) || ("Double".equalsIgnoreCase(paramString)))
      {
        str = paramObject.toString();
      }
      else if (("String".equalsIgnoreCase(paramString)) || ("Clob".equalsIgnoreCase(paramString)))
      {
        str = "'" + paramObject.toString() + "'";
      }
      else if (("Timestamp".equalsIgnoreCase(paramString)) || ("Date".equalsIgnoreCase(paramString)))
      {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if ("NOW()".equalsIgnoreCase(paramObject.toString()))
          str = "NOW()";
        else if ("CURDATE()".equalsIgnoreCase(paramObject.toString()))
          str = "CURDATE()";
        else if ("String".equalsIgnoreCase(paramObject.getClass().getSimpleName()))
          str = "'" + paramObject.toString() + "'";
        else if ("GregorianCalendar".equalsIgnoreCase(paramObject.getClass().getSimpleName()))
          str = "'" + localSimpleDateFormat.format(((Calendar)paramObject).getTime()) + "'";
        else if ("java.util.Date".equalsIgnoreCase(paramObject.getClass().getName()))
          str = "'" + localSimpleDateFormat.format(Long.valueOf(((java.util.Date)paramObject).getTime())) + "'";
        else if ("java.sql.Date".equalsIgnoreCase(paramObject.getClass().getName()))
          str = "'" + localSimpleDateFormat.format(Long.valueOf(((java.sql.Date)paramObject).getTime())) + "'";
      }
    return str;
  }
}