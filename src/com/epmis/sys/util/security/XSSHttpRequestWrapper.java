package com.epmis.sys.util.security;

import com.epmis.sys.util.DataTypeUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSHttpRequestWrapper extends HttpServletRequestWrapper
{
  public XSSHttpRequestWrapper(HttpServletRequest request)
  {
    super(request);
  }

  public String getParameter(String name)
  {
    String value = super.getParameter(name);

    if (DataTypeUtil.validate(value))
    {
      value = value.replaceAll("'", "\\\\'");
    }
    return value;
  }
}