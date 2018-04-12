package com.epmis.sys.util;

import java.util.HashMap;
import java.util.UUID;

public class Guid
{
  public static String getGuid()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    UUID localUUID = UUID.randomUUID();
    localStringBuffer.append(localUUID.toString().replaceAll("-", "").toUpperCase());
    return localStringBuffer.toString();
  }

  public static String getGuid(HashMap<String, String> paramHashMap)
  {
    return getGuid();
  }
}