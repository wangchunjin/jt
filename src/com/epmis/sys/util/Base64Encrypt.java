package com.epmis.sys.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Base64Encrypt
{
  public static final int RANGE = 255;
  private static final char[] Base64ByteToStr = { 
    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
    'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
    'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
    'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
    'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
    '8', '9', '+', '/' };

  private static byte[] StrToBase64Byte = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 50, 51, -1, -1, -1, -1, -1 };

  public static void generateDecoder() throws Exception {
    for (int i = 0; i <= StrToBase64Byte.length - 1; i++) {
      StrToBase64Byte[i] = -1;
    }
    for (int i = 0; i <= Base64ByteToStr.length - 1; i++)
      StrToBase64Byte[Base64ByteToStr[i]] = ((byte)i);
  }

  private static String Base64Encode(byte[] bytes)
    throws Exception
  {
    StringBuilder res = new StringBuilder();

    for (int i = 0; i <= bytes.length - 1; i += 3) {
      byte[] enBytes = new byte[4];
      byte tmp = 0;

      for (int k = 0; k <= 2; k++) {
        if (i + k <= bytes.length - 1) {
          enBytes[k] = ((byte)((bytes[(i + k)] & 0xFF) >>> 2 + 2 * k | tmp));
          tmp = (byte)(((bytes[(i + k)] & 0xFF) << 2 + 2 * (2 - k) & 0xFF) >>> 2);
        } else {
          enBytes[k] = tmp;
          tmp = 64;
        }
      }
      enBytes[3] = tmp;

      for (int k = 0; k <= 3; k++) {
        if (enBytes[k] <= 63)
          res.append(Base64ByteToStr[enBytes[k]]);
        else {
          res.append('=');
        }
      }
    }
    return res.toString();
  }

  private static byte[] Base64Decode(String val) throws Exception {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    byte[] srcBytes = val.getBytes();
    byte[] base64bytes = new byte[srcBytes.length];

    for (int i = 0; i <= srcBytes.length - 1; i++) {
      int ind = srcBytes[i];
      base64bytes[i] = StrToBase64Byte[ind];
    }

    for (int i = 0; i <= base64bytes.length - 1; i += 4) {
      byte[] deBytes = new byte[3];
      int delen = 0;

      for (int k = 0; k <= 2; k++) {
        if ((i + k + 1 <= base64bytes.length - 1) && (base64bytes[(i + k + 1)] >= 0)) {
          byte tmp = (byte)((base64bytes[(i + k + 1)] & 0xFF) >>> 2 + 2 * (2 - (k + 1)));
          deBytes[k] = ((byte)((base64bytes[(i + k)] & 0xFF) << 2 + 2 * k & 0xFF | tmp));
          delen++;
        }
      }
      for (int k = 0; k <= delen - 1; k++) {
        bos.write(deBytes[k]);
      }
    }
    return bos.toByteArray();
  }

  public static String encode(String str)
  {
    if ((str == null) || ("".equals(str)))
      return "";
    try
    {
      byte[] bs = str.getBytes("UTF-8");
      return Base64Encode(bs);
    }
    catch (Exception localException)
    {
    }

    return null;
  }

  public static String decode(String str)
  {
    try
    {
      byte[] bs = Base64Decode(str);
      return new String(bs, "UTF-8");
    }
    catch (Exception e)
    {
    }
    return null;
  }

  public static void main(String[] args)
    throws Exception
  {
    System.out.println(0);
  }
}