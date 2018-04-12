package com.epmis.sys.listen;

import com.epmis.webService.web.LicenseWeb;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.TimerTask;
import javax.servlet.ServletContext;

public class Task extends TimerTask
{
  private static boolean isRunning = false;
  private ServletContext context = null;

  public Task(ServletContext context) {
    this.context = context;
  }

  public void run()
  {
    if (!isRunning)
    {
      isRunning = true;
      this.context.log("开始执行指定任务");
      try {
        LicenseWeb.downLicense(getMACAddress());
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      isRunning = false;
      this.context.log("指定任务执行结束");
    }
  }

  public static String getMACAddress() throws Exception { InetAddress ia = InetAddress.getLocalHost();
    byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < mac.length; i++) {
      if (i != 0) {
        sb.append("-");
      }

      String s = Integer.toHexString(mac[i] & 0xFF);
      sb.append(s.length() == 1 ? 0 + s : s);
    }

    return sb.toString().toUpperCase();
  }
}