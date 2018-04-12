package com.epmis.sys.util;

import java.text.SimpleDateFormat;
import java.util.List;

public class DataTypeUtil
{
  public static boolean validate(int paramInt)
  {
    return (paramInt != 0) && (!"null".equalsIgnoreCase(String.valueOf(paramInt)));
  }

  public static boolean validate(String paramString) {
    return (paramString != null) && (paramString != "") && (!paramString.equalsIgnoreCase("null")) && (paramString.trim().length() >= 1);
  }

  public static boolean validate(Object paramObject) {
    return paramObject != null;
  }

  public static boolean validate(List paramObject) {
    return (paramObject != null) && (paramObject.size() > 0);
  }

  public static String formatDbColumn(int paramInt) {
    return String.valueOf(paramInt);
  }

  public static boolean validate(java.sql.Date paramObject) {
    return paramObject != null;
  }

  public static String formatDbColumn(Object paramObject) {
    return validate(paramObject) ? String.valueOf(paramObject).trim() : "";
  }

  public static String formatDbColumn(String paramString)
  {
    return paramString != null ? paramString.trim() : "";
  }

  public static String formatLengthStr(String paramString1, int paramInt, String paramString2, boolean paramBoolean) {
    String str = paramString1 == null ? "" : paramString1.trim();
    while (str.length() < paramInt)
    {
      if (paramBoolean)
      {
        str = paramString2 + str;
      }
      else
        str = str + paramString2;
    }
    if (str.length() > paramInt)
      if (paramBoolean)
        str = str.substring(str.length() - paramInt);
      else
        str = str.substring(0, paramInt);
    return str;
  }

  public static boolean validateDate(String paramString)
  {
    try {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      localSimpleDateFormat.setLenient(false);
      java.util.Date localDate = localSimpleDateFormat.parse(paramString);
      localSimpleDateFormat.format(localDate);
    }
    catch (Exception localException)
    {
      return false;
    }
    return true;
  }
}