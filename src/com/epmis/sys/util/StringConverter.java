package com.epmis.sys.util;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
import java.util.Map;

public class StringConverter extends DefaultTypeConverter
{
  public Object convertValue(Map context, Object value, Class toType)
  {
    String dateString = null;
    if ((toType == String.class) && (value != null)) {
      String[] params = (String[])value;
      dateString = params[0];
      dateString = dateString.replaceAll("\\\\", "\\\\\\\\").replaceAll("'", "\\\\'");
    }

    return dateString;
  }
}