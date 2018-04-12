package com.epmis.sys.util;

public class SqlUtil
{
  public static String InSql(String ids, String sql)
  {
    String[] idlist = ids.split(",");
    String sqlin = "";
    if (idlist.length > 100) {
      int length = idlist.length;
      String temp = "";
      int count = idlist.length / 100;
      int time = 0;

      for (int i = 0; i <= count; i++) {
        if (i == count)
          time = length - 100 * count;
        else {
          time = 100;
        }
        for (int j = 0; j < time; j++)
        {
          if ((temp == "") || (temp == null))
            temp = idlist[(100 * i)];
          else {
            temp = temp + "," + idlist[(100 * i + j)];
          }
        }

        sqlin = sqlin + " or  " + sql + " (" + temp + ")";
        temp = "";
      }
    }
    else {
      sqlin = " or " + sql + " (" + ids + ")";
    }
    return sqlin;
  }
}