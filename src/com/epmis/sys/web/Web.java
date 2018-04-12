package com.epmis.sys.web;

import com.epmis.sys.service.BaseJdbcService;
import com.epmis.sys.util.SpringContextHolder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Web
{
  static BaseJdbcService baseJdbcService = (BaseJdbcService)SpringContextHolder.getBean("baseJdbcService");

  public static void initLabel(HttpServletRequest request, HttpServletResponse response)
  {
    String MuduleName = request.getParameter("Module");
    System.out.println("MuduleName="+MuduleName);
    List lable = baseJdbcService.initLabel(MuduleName);
    request.setAttribute("LabelEntityList", lable);
  }
}