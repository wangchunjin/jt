package com.epmis.sys.util;

import com.epmis.sys.util.security.XSSHttpRequestWrapper;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class JspFilter
  implements Filter
{
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    throws IOException, ServletException
  {
    HttpServletRequest request = (HttpServletRequest)req;
    String url = request.getRequestURI();
    String content_type = DataTypeUtil.formatDbColumn(request.getContentType());
    if ((!url.contains("OpSelectBySql.action")) && (!url.contains("OpBySql.action")) && (content_type.indexOf("multipart/form-data") == -1)) {
      XSSHttpRequestWrapper xssRequest = new XSSHttpRequestWrapper(request);
      chain.doFilter(xssRequest, res);
    } else {
      chain.doFilter(req, res);
    }
  }

  public void destroy()
  {
  }

  public void init(FilterConfig arg0)
    throws ServletException
  {
  }
}