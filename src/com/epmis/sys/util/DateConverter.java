package com.epmis.sys.util;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class DateConverter extends DefaultTypeConverter
{
  private static final SimpleDateFormat[] ACCEPT_DATE_FORMATS = { 
    new SimpleDateFormat("yyyy-MM-dd") };

  public Object convertValue(Map context, Object value, Class toType)
  {
    if (toType == java.sql.Date.class)
    {
      String dateString = null;
      String[] params = (String[])value;
      dateString = params[0];
      if (dateString.length() > 19)
        dateString = dateString.substring(0, 19);
      SimpleDateFormat[] arrayOfSimpleDateFormat;
      int j = (arrayOfSimpleDateFormat = ACCEPT_DATE_FORMATS).length;
      for (int i = 0; i < j; ) {
        SimpleDateFormat format = arrayOfSimpleDateFormat[i];
        try {
          if (DataTypeUtil.validate(format.parse(dateString))) {
            return new java.sql.Date(format.parse(dateString).getTime());
          }

        }
        catch (Exception localException)
        {
          i++;
        }

      }

      return null;
    }if (toType == Timestamp.class) {
      String dateString = null;
      String[] params = (String[])value;
      dateString = params[0];
      if (DataTypeUtil.validate(dateString)) {
        if (dateString.length() == 10) {
          dateString = dateString + " 00:00:00";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
          if (DataTypeUtil.validate(format.parse(dateString)))
            return new Timestamp(format.parse(dateString).getTime());
        }
        catch (ParseException e)
        {
          e.printStackTrace();
        }
      }
    }
    if (toType == String.class) {
      java.sql.Date date = (java.sql.Date)value;
      return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    return null;
  }
}