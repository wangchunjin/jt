package com.epmis.sys.util;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
import java.util.Map;

public class IntegerConverter extends DefaultTypeConverter
{
  public Object convertValue(Map context, Object value, Class toType)
  {
    int res = 0;
    if (toType == Integer.class) {
      String dateString = null;
      String[] params = (String[])value;
      dateString = params[0];
      if (DataTypeUtil.validate(dateString)) {
        res = Integer.valueOf(dateString).intValue();
      }
    }
    return Integer.valueOf(res);
  }
}