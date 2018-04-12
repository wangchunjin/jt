package com.epmis.sys.util;

import com.sun.crypto.provider.SunJCE;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServlet;

public class DESEncrypt extends HttpServlet
{
  private static String _$1 = "DES";

  static
  {
    Security.addProvider(new SunJCE());
  }

  public static byte[] getKey()
  {
    KeyGenerator localKeyGenerator = null;
    try
    {
      localKeyGenerator = KeyGenerator.getInstance(_$1);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
    SecretKey localSecretKey = localKeyGenerator.generateKey();
    return localSecretKey.getEncoded();
  }

  public static byte[] encode(byte[] paramArrayOfByte)
    throws Exception
  {
    SecretKeySpec localSecretKeySpec = new SecretKeySpec(getKey(), _$1);
    Cipher localCipher = Cipher.getInstance(_$1);
    localCipher.init(1, localSecretKeySpec);
    byte[] arrayOfByte = localCipher.doFinal(paramArrayOfByte);
    return arrayOfByte;
  }

  public static byte[] encode(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte1, _$1);
    Cipher localCipher = Cipher.getInstance(_$1);
    localCipher.init(1, localSecretKeySpec);
    byte[] arrayOfByte = localCipher.doFinal(paramArrayOfByte2);
    return arrayOfByte;
  }

  public static String encode(byte[] paramArrayOfByte, String paramString)
    throws Exception
  {
    return new String(encode(paramArrayOfByte, paramString.getBytes()));
  }

  public static byte[] decode(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte1, _$1);
    Cipher localCipher = Cipher.getInstance(_$1);
    localCipher.init(2, localSecretKeySpec);
    byte[] arrayOfByte = localCipher.doFinal(paramArrayOfByte2);
    return arrayOfByte;
  }

  private static String _$1(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramArrayOfByte.length; i++)
      localStringBuffer.append(Integer.toHexString(256 + (paramArrayOfByte[i] & 0xFF)).substring(1).toUpperCase());
    return localStringBuffer.toString();
  }

  public static byte[] hex2byte(String paramString)
  {
    byte[] arrayOfByte = new byte[paramString.length() / 2];
    for (int i = 0; i < arrayOfByte.length; i++)
      arrayOfByte[i] = ((byte)Integer.parseInt(paramString.substring(2 * i, 2 * i + 2), 16));
    return arrayOfByte;
  }

  public static String desEncode(String paramString)
  {
    byte[] arrayOfByte1 = hex2byte(AppSetting.DESENCRYPT_KEY);
    byte[] arrayOfByte2 = null;
    try
    {
      arrayOfByte2 = encode(arrayOfByte1, paramString.getBytes());
    }
    catch (Exception localException)
    {
      Logger.error(localException);
    }
    return _$1(arrayOfByte2);
  }

  public static String desDecode(String paramString)
  {
    byte[] arrayOfByte1 = hex2byte(AppSetting.DESENCRYPT_KEY);
    byte[] arrayOfByte2 = hex2byte(paramString);
    byte[] arrayOfByte3 = null;
    try
    {
      arrayOfByte3 = decode(arrayOfByte1, arrayOfByte2);
    }
    catch (Exception localException)
    {
      Logger.error(localException);
    }
    return new String(arrayOfByte3);
  }

  public static void main(String[] args)
    throws Exception
  {
    AppSetting.DESENCRYPT_KEY = "850B1032922CDCDC";
    System.out.print(desEncode("123456"));
  }
}