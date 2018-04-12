package com.xiaheng.util;

import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class CommonCode
{
  public static final String BIGZM = "ABCDEFGHIJKLMNOPQRSTYWXYZ";
  public static final String SMALLZM = "abcdefghijklmnopqrstywxyz";
  public static int jia = 1;
  public static int jian = 2;
  public static int chen = 3;
  public static int chu = 4;
  public static int zhengchu = 5;
  private static int quyu = 6;

  public static int CALYEAR = 1;
  public static int CALMONTH = 2;
  public static int CALWEEK = 8;
  public static int CALDAY = 5;
  public static final long DAY = 86400000L;
  private static final long MONTY = -1702967296L;
  private static final long YEAR = 1039228928L;
  public static final long HOUR = 3600000L;
  public static final long MINUTES = 60000L;
  public static final long SECOND = 1000L;
  public static final long MILLISECOND = 1L;

  public static String getRemoteAddr(HttpServletRequest request)
  {
    return request.getRemoteAddr();
  }

  public static String operatingByNumber(String a, String b, int YsType, String decFormat)
  {
    DecimalFormat df = new DecimalFormat(decFormat);
    try
    {
      if (YsType == jia) {
        return df.format(Float.valueOf(a).floatValue() + Float.valueOf(b).floatValue());
      }
      if (YsType == jian) {
        return df.format(Float.valueOf(a).floatValue() - Float.valueOf(b).floatValue());
      }
      if (YsType == chen) {
        return df.format(Float.valueOf(a).floatValue() * Float.valueOf(b).floatValue());
      }
      if (YsType == chu) {
        float cs = Float.valueOf(a).floatValue();
        float bcs = Float.valueOf(b).floatValue();
        if (bcs == 0.0F) {
          return "0";
        }
        float zs = cs / bcs;
        return df.format(zs);
      }
      if (YsType == zhengchu) {
        float cs = Float.valueOf(a).floatValue();
        float bcs = Float.valueOf(b).floatValue();
        if (bcs == 0.0F) {
          return "0";
        }
        float zs = cs / bcs;

        DecimalFormat decf = new DecimalFormat("#");
        String resu = decf.format(zs);

        return resu;
      }
      if (YsType == quyu) {
        float cs = Float.valueOf(a).floatValue();
        float bcs = Float.valueOf(b).floatValue();
        if (bcs == 0.0F) {
          return "0";
        }
        String zs = (cs / bcs)+"";
        return "";
      }
      return "0"; } catch (Exception e) {
    }
    return "0";
  }

  public static String operatingByNumber(String a, String b, int YsType)
  {
    return operatingByNumber(a, b, YsType, "#.00");
  }

  public static String judStrOrInt(String str)
  {
    if (str.trim().equals("")) {
      return "0";
    }
    return str;
  }

  public static Map<String, Integer> getPageNum(int pageSize, String pagenum)
  {
    if ((pagenum == null) && ("".equals(pagenum))) {
      pagenum = "1";
    }
    try
    {
      Integer.valueOf(pagenum);
    } catch (Exception e) {
      pagenum = "1";
    }

    int minpage = (Integer.valueOf(pagenum).intValue() - 1) * pageSize;
    int maxpage = Integer.valueOf(pagenum).intValue() * pageSize;

    Map map = new HashMap();
    map.put("minpage", Integer.valueOf(minpage));
    map.put("maxpage", Integer.valueOf(maxpage));

    return map;
  }

  public static String getPwdByMd5(String pwd, String UUID)
  {
    String result = pwd;
    result = MD5.md5Hex(MD5.md5Hex(new StringBuilder(String.valueOf(pwd)).append(UUID).toString()) + MD5.md5Hex(UUID));

    return result;
  }

  public static String compareNumber(String a, String b)
  {
    try
    {
      float anumber = Float.valueOf(a).floatValue();
      float bnumber = Float.valueOf(b).floatValue();

      if (anumber > bnumber)
        return ">";
      if (anumber < bnumber) {
        return "<";
      }
      return "=";
    } catch (Exception e) {
    }
    return "NaN";
  }

  public static String getRandomUnique()
  {
    String region = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
    return getRandomUnique(10, region);
  }

  public static String getRandomUnique(int num) {
    String region = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
    return getRandomUnique(10, region);
  }

  public static String getRandomUnique(String region) {
    return getRandomUnique(10, region);
  }

  public static String getRandomUnique(int num, String region) {
    String result = "";
    Random r1 = new Random();
    int len = 0;
    while (len++ < num) {
      result = result + region.toCharArray()[r1.nextInt(region.length())];
    }
    return result;
  }

  public static List<Object> changeArysToList(Object[] arys)
  {
    return Arrays.asList(arys);
  }

  public static boolean judeStrInArys(String str, String[] ary)
  {
    if ((ary != null) && (ary.length > 0)) {
      for (int i = 0; i < ary.length; i++) {
        if (ary[i].equals(str)) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean regexTest(String regexString, String str)
  {
    Pattern pat = Pattern.compile(regexString);
    Matcher mat = pat.matcher(str);
    return mat.find();
  }

  public static String getTimeStr()
  {
    return getTimeStr(null);
  }

  public static String getTimeStr(String format) {
    if ((format == null) || ("".equals(format))) {
      format = "yyyy-MM-dd HH:mm:ss";
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Date now = new Date();
    return sdf.format(now);
  }

  public static String FormatDate(String format1, String format2, String source)
  {
    try
    {
      SimpleDateFormat sdf = new SimpleDateFormat(format1);
      Date birdate = sdf.parse(source);
      SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
      String result = sdf2.format(Long.valueOf(birdate.getTime()));
      return result;
    } catch (Exception e) {
      e.printStackTrace();
    }return "error";
  }

  public static String DataOperation(Date date, int time)
  {
    return DataOperation(date, "yyyy-MM-dd HH:mm:ss", CALYEAR, time);
  }

  public static String DataOperation(Date date, int type, int time)
  {
    return DataOperation(date, "yyyy-MM-dd HH:mm:ss", type, time);
  }

  public static String DataOperation(Date date, String format, int type, int time)
  {
    SimpleDateFormat sdf = null;
    if ((format != null) && (!format.equals("")))
      sdf = new SimpleDateFormat(format);
    else {
      sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(type, time);
    return sdf.format(cal.getTime());
  }

  public static String DataOperation(String date, String format, int type, int time)
    throws ParseException
  {
    SimpleDateFormat sdf = null;
    if ((format != null) && (!format.equals("")))
      sdf = new SimpleDateFormat(format);
    else {
      sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    Date date1 = sdf.parse(date);
    return DataOperation(date1, format, type, time);
  }

  public static String operatingDate(String date1, String date2, String format, long unit)
    throws ParseException
  {
    if ((format == null) || ("".equals(format))) {
      format = "yyyy-MM-dd";
    }

    SimpleDateFormat myFormatter = new SimpleDateFormat(format);

    Date date = myFormatter.parse(date2);

    Date mydate = myFormatter.parse(date1);

    long day = (date.getTime() - mydate.getTime()) / unit;

    return day+"";
  }

  public static String replaceHtmlWidthToPers(String html)
  {
    return replaceHtmlWidthToPers(html, "100", "%");
  }

  public static String replaceHtmlWidthToPers(String html, String pers) {
    return replaceHtmlWidthToPers(html, pers, "%");
  }

  public static String replaceHtmlWidthToPers(String html, String pers, String unit)
  {
    String result = html;
    String regex = "((<td.*width:\\d+px))";
    String[] patterns = { "<.*(width:\\d+px)", "<.*width=\"\\d+\"", 
      "<.*width=\"\\d+px\"" };
    for (int i = 0; i < patterns.length; i++) {
      Matcher matcher = Pattern.compile(patterns[i]).matcher(result);
      while (matcher.find()) {
        if (regexTest(regex, matcher.group())) {
          continue;
        }
        result = result.replace(matcher.group(), matcher.group()
          .replaceAll("(width:\\d+px)", "width:" + pers + unit));
        result = result.replace(
          matcher.group(), 
          matcher.group().replaceAll(
          "(width=\"\\d+\")|(width=\"\\d+px\")", 
          "width=\"" + pers + unit + "\""));

        result = handleImgNoWidth(result);
      }
    }
    return result;
  }

  public static String handleImgNoWidth(String html) {
    String result = "";
    int i = 0;
    while (true) {
      int index = html.indexOf("<img");

      if (index != -1) {
        String str_tmp = html.substring(0, index);
        String next_tmp = html.substring(str_tmp.length(), 
          html.length());
        String curr_img_str = next_tmp.substring(0, 
          next_tmp.indexOf(">") + 1);
        String other_tmp = next_tmp.substring(curr_img_str.length(), 
          next_tmp.length());

        boolean hasWidth = regexTest("width", curr_img_str);

        if (!hasWidth)
        {
          String headImgStr = curr_img_str.substring(0, 
            curr_img_str.lastIndexOf(" "));
          String footImgStr = curr_img_str.substring(
            curr_img_str.lastIndexOf(" "), 
            curr_img_str.length());

          result = result + str_tmp + " " + headImgStr + " width=\"100%\" " + 
            footImgStr + " ";
        } else {
          result = result + str_tmp + " " + curr_img_str + " ";
        }
        html = other_tmp;
      }
      else {
        result = result + html;
        break;
      }
      i++;
    }
    return result;
  }

  public static String picbzh(String result) {
    result = result.replaceAll("\\.jpg_\\.webp", "\\.jpg");
    return result;
  }

  private static String resolveURL(String url)
  {
    URL aURL = null;
    try {
      aURL = new URL(
        "http://java.sun.com:80/docs/books/tutorial/index.html?#name=networking#DOWNLOADING");
    }
    catch (MalformedURLException e) {
      e.printStackTrace();
    }

    //System.out.println("protocol = " + aURL.getProtocol());

    //System.out.println("authority = " + aURL.getAuthority());
    //System.out.println("host = " + aURL.getHost());

    //System.out.println("port = " + aURL.getPort());

    //System.out.println("path = " + aURL.getPath());

    //System.out.println("query = " + aURL.getQuery());

    //System.out.println("filename = " + aURL.getFile());

    //System.out.println("ref = " + aURL.getRef());

    return "";
  }

  public static int getAgeByBirthday(Date birthday)
  {
    Calendar cal = Calendar.getInstance();

    if (cal.before(birthday)) {
      throw new IllegalArgumentException(
        "The birthDay is before Now.It's unbelievable!");
    }

    int yearNow = cal.get(1);
    int monthNow = cal.get(2) + 1;
    int dayOfMonthNow = cal.get(5);

    cal.setTime(birthday);
    int yearBirth = cal.get(1);
    int monthBirth = cal.get(2) + 1;
    int dayOfMonthBirth = cal.get(5);

    int age = yearNow - yearBirth;

    if (monthNow <= monthBirth) {
      if (monthNow == monthBirth)
      {
        if (dayOfMonthNow < dayOfMonthBirth) {
          age--;
        }
      }
      else {
        age--;
      }
    }
    return age;
  }

  public static String replace(String str, char str1, char str2)
  {
    if (str == null) {
      return null;
    }
    char[] array = str.toCharArray();
    int i = 0; for (int length = array.length; i < length; i++) {
      if (array[i] == str1) {
        array[i] = str2;
      }
    }
    return new String(array);
  }
  
	public static void main(String[] args) {
		String payStr = "merchantAcctId="+1+"&orderId="+2+"&orderAmount="+3;//主要参数拼接成的字符串
		String unique = com.xiaheng.util.CommonCode.getRandomUnique(8);
		String lelangSign = com.xiaheng.util.CommonCode.getPwdByMd5(payStr,unique);

	}
  
}