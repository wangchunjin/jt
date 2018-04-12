package com.epmis.sys.util;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WriterJsonArray
{
  public static void writerJSONArray(List<Map<String, Object>> items, HttpServletResponse response)
  {
    try
    {
      JSONArray json = JSONArray.fromObject(items);
      response.setContentType("application/json;charset=UTF-8");
      response.setCharacterEncoding("utf-8");
      response.setHeader("Charset", "utf-8");
      response.setHeader("Cache-Control", "no-cache");
      response.getWriter().println(json.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void writerJSONArray(Map<String, Object> items, HttpServletResponse response)
  {
    try {
      JSONArray json = JSONArray.fromObject(items);
      response.setContentType("application/json;charset=UTF-8");
      response.setCharacterEncoding("utf-8");
      response.setHeader("Charset", "utf-8");
      response.setHeader("Cache-Control", "no-cache");
      response.getWriter().println(json.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void writerJSONArray(String result, HttpServletResponse response)
  {
    try {
      Map root = new HashMap();
      root.put("result", result);
      JSONArray json = JSONArray.fromObject(root);
      response.setContentType("application/json;charset=UTF-8");
      response.setCharacterEncoding("utf-8");
      response.setHeader("Charset", "utf-8");
      response.setHeader("Cache-Control", "no-cache");
      response.getWriter().println(json.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void AppWriterJSONArray(Map<String, Object> items, HttpServletResponse response)
  {
    try
    {
      JSONObject json = JSONObject.fromObject(items);
      response.setContentType("application/json;charset=UTF-8");
      response.setCharacterEncoding("utf-8");
      response.setHeader("Charset", "utf-8");
      response.setHeader("Cache-Control", "no-cache");
      response.addHeader("Access-Control-Allow-Origin", "*");
      response.addHeader("Access-Control-Allow-Methods", "POST,GET");
      response.addHeader("Access-Control-Allow-Credentials", "true");
      response.getWriter().println(json.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}