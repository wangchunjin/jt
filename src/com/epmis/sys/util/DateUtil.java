package com.epmis.sys.util;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class DateUtil
{
  public static void main(String[] paramArrayOfString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar localCalendar = Calendar.getInstance();
    System.out.println(localSimpleDateFormat.format(localCalendar.getTime()));
  }

  public static String getTodaydate()
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar localCalendar = Calendar.getInstance();
    return localSimpleDateFormat.format(localCalendar.getTime());
  }

  public static String getTodaytime()
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar localCalendar = Calendar.getInstance();
    return localSimpleDateFormat.format(localCalendar.getTime());
  }

  public static String getTodayTimeInMillis()
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar localCalendar = Calendar.getInstance();
    String str = localSimpleDateFormat.format(localCalendar.getTime()) + ":" + localCalendar.get(14);
    return str;
  }

  public static int getDaysOfTowDiffDate(String paramString1, String paramString2)
  {
    Date localDate1 = toUtilDateFromStrDateByFormat(paramString1, "yyyy-MM-dd");
    Date localDate2 = toUtilDateFromStrDateByFormat(paramString2, "yyyy-MM-dd");
    double d1 = getMillisOfDate(localDate1);
    double d2 = getMillisOfDate(localDate2);
    int i = (int)((d2 - d1) / 86400000.0D);
    return i;
  }

  public static double getMillisOfDate(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    return localCalendar.getTimeInMillis();
  }

  public static Date toUtilDateFromStrDateByFormat(String paramString1, String paramString2)
  {
    Date localDate = null;
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2);
    if ((paramString1 != null) && (!"".equals(paramString1)) && (paramString2 != null) && (!"".equals(paramString2)))
      try
      {
        localDate = localSimpleDateFormat.parse(paramString1);
      }
      catch (ParseException localParseException)
      {
        Logger.error(localParseException);
      }
    return localDate;
  }

  public static int getDaysByMonthAndYear(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    int i = arrayOfInt[(paramInt1 - 1)];
    if ((paramInt1 == 2) && (paramInt2 % 4 == 0))
      i++;
    return i;
  }

  public static int getYearByDate(String paramString)
  {
    if (DataTypeUtil.validate(paramString))
      return Integer.valueOf(paramString.split("-")[0]).intValue();
    return 0;
  }

  public static int getMonthByDate(String paramString)
  {
    if (DataTypeUtil.validate(paramString))
      return Integer.valueOf(paramString.split("-")[1]).intValue();
    return 0;
  }

  public static int getAllyeardays(int paramInt)
  {
    if ((paramInt % 400 == 0) || ((paramInt % 4 == 0) && (paramInt % 100 != 0)))
      return 366;
    return 365;
  }

  public static int getYearOfDate(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    return localCalendar.get(1);
  }

  public static int getMonthOfDate(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    return localCalendar.get(2) + 1;
  }

  public static int getDayOfDate(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    return localCalendar.get(5);
  }

  public static String toStrDateFromUtilDateByFormat(Date paramDate, String paramString)
  {
    String str = "";
    if (paramDate != null)
    {
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
      str = localSimpleDateFormat.format(paramDate);
    }
    return str;
  }

  public static String addDate(Date paramDate, int paramInt1, int paramInt2, String paramString)
  {
    int i = getYearOfDate(paramDate);
    int j = getMonthOfDate(paramDate) - 1;
    int k = getDayOfDate(paramDate);
    int m = getHourOfDate(paramDate);
    int n = getMinuteOfDate(paramDate);
    int i1 = getSecondOfDate(paramDate);
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(i, j, k, m, n, i1);
    localGregorianCalendar.add(paramInt2, paramInt1);
    return toStrDateFromUtilDateByFormat(localGregorianCalendar.getTime(), paramString);
  }

  public static int getHourOfDate(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    return localCalendar.get(11);
  }

  public static int getMinuteOfDate(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    return localCalendar.get(12);
  }

  public static int getSecondOfDate(Date paramDate)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    return localCalendar.get(13);
  }

  public static Date parseData(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try
    {
      return localSimpleDateFormat.parse(paramString);
    }
    catch (ParseException localParseException)
    {
      Logger.error(localParseException);
    }
    return null;
  }

  public static Date parseTime(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try
    {
      return localSimpleDateFormat.parse(paramString);
    }
    catch (ParseException localParseException)
    {
      Logger.error(localParseException);
    }
    return null;
  }

  public static boolean validate(String paramString)
  {
    try
    {
      if ((DataTypeUtil.validate(paramString)) && (paramString.length() == 10))
      {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        localSimpleDateFormat.setLenient(false);
        localSimpleDateFormat.parse(paramString);
        return true;
      }
    }
    catch (ParseException localParseException)
    {
      Logger.error(localParseException);
    }
    return false;
  }

  public static boolean validateTime(String paramString)
  {
    try
    {
      if ((DataTypeUtil.validate(paramString)) && (paramString.length() == 19))
      {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localSimpleDateFormat.setLenient(false);
        localSimpleDateFormat.parse(paramString);
        return true;
      }
    }
    catch (ParseException localParseException)
    {
      Logger.error(localParseException);
    }
    return false;
  }

  public static Map<String, Object> getDateInfo(long paramLong)
  {
    if (paramLong > 0L)
    {
      HashMap localHashMap = new HashMap();
      Date localDate = new Date(paramLong);
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTime(localDate);
      int i = localCalendar.get(1);
      int j = localCalendar.get(2);
      int k = localCalendar.get(5);
      int m = localCalendar.get(11);
      int n = localCalendar.get(12);
      int i1 = localCalendar.get(13);
      int i2 = localCalendar.get(7);
      int i3 = 0;
      if ((j == 0) || (j == 1) || (j == 2))
        i3 = 1;
      else if ((j == 3) || (j == 4) || (j == 5))
        i3 = 2;
      else if ((j == 6) || (j == 7) || (j == 8))
        i3 = 3;
      else if ((j == 9) || (j == 10) || (j == 11))
        i3 = 4;
      localHashMap.put("YEAR", Integer.valueOf(i));
      localHashMap.put("MONTH", Integer.valueOf(j + 1));
      localHashMap.put("DAY", Integer.valueOf(k));
      localHashMap.put("HOUR", Integer.valueOf(m));
      localHashMap.put("MINUTE", Integer.valueOf(n));
      localHashMap.put("SECOND", Integer.valueOf(i1));
      localHashMap.put("QUARTER", Integer.valueOf(i3));
      localHashMap.put("WEEK", Integer.valueOf(i2 + 1));
      return localHashMap;
    }
    return null;
  }

  public static String getMonday(String paramString)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(parseData(paramString));
    localCalendar.set(7, 2);
    return new SimpleDateFormat("yyyy-MM-dd").format(localCalendar.getTime());
  }

  public static String getFormatDate(Date date, String format)
  {
    String renDate = "";
    if (format.equalsIgnoreCase("yyyy"))
    {
      renDate = new SimpleDateFormat("yyyy").format(date);
    } else if (format.equalsIgnoreCase("yyyy-MM"))
    {
      renDate = new SimpleDateFormat("yyyy-MM").format(date);
    } else if (format.equalsIgnoreCase("yyyy-MM-dd"))
    {
      renDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
    } else if (format.equalsIgnoreCase("yyyyMM"))
    {
      renDate = new SimpleDateFormat("yyyyMM").format(date);
    } else if (format.equalsIgnoreCase("yyyyMMdd"))
    {
      renDate = new SimpleDateFormat("yyyyMMdd").format(date);
    } else if (format.equalsIgnoreCase("MM"))
    {
      renDate = new SimpleDateFormat("MM").format(date);
    } else if (format.equalsIgnoreCase("dd"))
    {
      renDate = new SimpleDateFormat("dd").format(date);
    } else if (format.equalsIgnoreCase("MM-dd"))
    {
      renDate = new SimpleDateFormat("MM-dd").format(date);
    } else if (format.equalsIgnoreCase("MMdd"))
    {
      renDate = new SimpleDateFormat("MMdd").format(date);
    } else if (format.equalsIgnoreCase("yyyy-MM-dd HH:mm:ss"))
    {
      renDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    } else if (format.equalsIgnoreCase("yyyyMMddHHmmss"))
    {
      renDate = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    } else if (format.equalsIgnoreCase("yyyyMMddHHmm"))
    {
      renDate = new SimpleDateFormat("yyyyMMddHHmm").format(date);
    } else if (format.equalsIgnoreCase("yyyyMMddHH"))
    {
      renDate = new SimpleDateFormat("yyyyMMddHH").format(date);
    } else if (format.equalsIgnoreCase("HH:mm:ss"))
    {
      renDate = new SimpleDateFormat("HH:mm:ss").format(date);
    } else if (format.equalsIgnoreCase("HHmmss"))
    {
      renDate = new SimpleDateFormat("HHmmss").format(date);
    } else if (format.equalsIgnoreCase("HHmm"))
    {
      renDate = new SimpleDateFormat("HHmm").format(date);
    }
    return renDate;
  }

  public static Date parseDate(String dateStr)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try
    {
      return sdf.parse(dateStr);
    }
    catch (ParseException e) {
      Logger.error(e);
    }
    return null;
  }

  public static int daysBetween(String startDate, String endDate)
  {
    int betWeenDay = 0;
    if ((DataTypeUtil.validate(startDate)) && (DataTypeUtil.validate(endDate)))
    {
      if (startDate.equalsIgnoreCase(endDate)) {
        betWeenDay = 1;
      }
      else {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parseDate(startDate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(parseDate(endDate));
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / 86400000L;
        betWeenDay = Integer.parseInt(String.valueOf(betweenDays));
      }
    }
    return betWeenDay;
  }

  public static Date addDays(Date addDate, int addDays)
  {
    if (addDays != 0)
    {
      Calendar cal = Calendar.getInstance();
      boolean isAdd = true;
      if (addDays < 0)
      {
        addDays = -addDays;
        isAdd = false;
      }
      for (int i = 0; i < addDays; i++)
      {
        cal.setTime(addDate);
        if (isAdd)
          cal.add(5, 1);
        else
          cal.add(5, -1);
        addDate = cal.getTime();
      }
    }
    return addDate;
  }

  public static boolean isNotWorkDay(String calenderId, Date date)
  {
    boolean flag = false;

    return flag;
  }

  public static boolean isWeekDay(Date date)
  {
    boolean flag = false;

    return flag;
  }

  public static Date getAddDurationDate(Date date, int duration, String calenderId)
  {
    if (duration > 0)
    {
      int ljDuration = 0;
      while (duration != ljDuration)
      {
        date = addDays(date, 1);
        if ((isNotWorkDay(calenderId, date)) || (isWeekDay(date)))
          date = addDays(date, 1);
        else
          ljDuration++;
      }
    }
    else {
      int ljDuration = 0;
      while (duration != -ljDuration)
      {
        date = addDays(date, -1);
        if ((isNotWorkDay(calenderId, date)) || (isWeekDay(date)))
          date = addDays(date, -1);
        else
          ljDuration++;
      }
    }
    return date;
  }
}