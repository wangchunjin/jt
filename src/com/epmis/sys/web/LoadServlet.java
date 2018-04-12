package com.epmis.sys.web;

import java.io.PrintStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
@SuppressWarnings("serial")
public class LoadServlet extends HttpServlet
{
  

public void init()
    throws ServletException
  {
    System.out.println("项目初始化");
    com.epmis.sys.util.AppSetting.PROJECT_PATH = getServletContext().getRealPath("");
    com.epmis.sys.util.AppSetting.PROJECT_NAME = getServletContext().getContextPath();
    System.out.println("初始化完成.");
  }
}