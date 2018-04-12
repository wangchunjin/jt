package com.epmis.sys.util;

import java.io.IOException;

public class Logger
{
  public static void error(Exception paramException)
  {
    errorFile(paramException, "error");
  }

  public static void info(String paramString) {
    errorFile(paramString, "error");
  }

  public static void errorFile(Exception paramException, String paramString)
  {
    try {
      if (!FileUtil.validate(AppSetting.LOG_PATH, DateUtil.getTodaydate() + ".log"))
        FileUtil.createFile(AppSetting.LOG_PATH, DateUtil.getTodaydate() + ".log");
      StringBuffer localStringBuffer = new StringBuffer();
      if ("info".equalsIgnoreCase(paramString))
        localStringBuffer.append(DateUtil.getTodaytime()).append(" -i ").append(getFunctionCallSource());
      else if ("error".equalsIgnoreCase(paramString))
        localStringBuffer.append(DateUtil.getTodaytime()).append(" -e ").append(getFunctionCallSource(true));
      else if ("debug".equalsIgnoreCase(paramString))
        localStringBuffer.append(DateUtil.getTodaytime()).append(" -d ").append(getFunctionCallSource(true));
      else if ("warn".equalsIgnoreCase(paramString))
        localStringBuffer.append(DateUtil.getTodaytime()).append(" -w ").append(getFunctionCallSource(true));
      localStringBuffer.append(paramException.getClass().getName() + " " + paramException.getMessage() + "\r\n");
      for (int i = 0; i < paramException.getStackTrace().length; i++)
        localStringBuffer.append("\tat ").append(paramException.getStackTrace()[i]).append("\r\n");
      localStringBuffer.append("\r\n");
      FileUtil.appendFile(AppSetting.LOG_PATH + "\\" + DateUtil.getTodaydate() + ".log", localStringBuffer.toString());
    }
    catch (IOException localIOException)
    {
    }
  }

  public static void errorFile(String paramString1, String paramString2)
  {
    try
    {
      if (!FileUtil.validate(AppSetting.LOG_PATH, DateUtil.getTodaydate() + ".log"))
        FileUtil.createFile(AppSetting.LOG_PATH, DateUtil.getTodaydate() + ".log");
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("\r\n");
      if ("info".equalsIgnoreCase(paramString2))
        localStringBuffer.append(DateUtil.getTodaytime()).append(" -i ").append(getFunctionCallSource());
      else if ("error".equalsIgnoreCase(paramString2))
        localStringBuffer.append(DateUtil.getTodaytime()).append(" -e ").append(getFunctionCallSource(true));
      else if ("debug".equalsIgnoreCase(paramString2))
        localStringBuffer.append(DateUtil.getTodaytime()).append(" -d ").append(getFunctionCallSource(true));
      else if ("warn".equalsIgnoreCase(paramString2))
        localStringBuffer.append(DateUtil.getTodaytime()).append(" -w ").append(getFunctionCallSource(true));
      localStringBuffer.append(paramString1).append("\r\n");
      FileUtil.appendFile(AppSetting.LOG_PATH + "\\" + DateUtil.getTodaydate() + ".log", localStringBuffer.toString());
    }
    catch (IOException localIOException)
    {
    }
  }

  public static String getFunctionCallSource(boolean paramBoolean) {
    StackTraceElement[] arrayOfStackTraceElement = new Exception().getStackTrace();
    if (arrayOfStackTraceElement.length > 4)
    {
      if (paramBoolean)
      {
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i = arrayOfStackTraceElement.length - 1; i > 1; i--)
        {
          localStringBuffer.append(arrayOfStackTraceElement[i].toString());
          if (i > 2)
            localStringBuffer.append(" --> \r\n");
          else
            localStringBuffer.append(": ");
        }
        return localStringBuffer.toString() + " \r\n ";
      }
      return arrayOfStackTraceElement[4].toString() + ": " + " \r\n ";
    }
    return "";
  }

  public static String getFunctionCallSource()
  {
    return getFunctionCallSource(false);
  }
}