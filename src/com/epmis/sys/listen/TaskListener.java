package com.epmis.sys.listen;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TaskListener
  implements ServletContextListener
{
  private Timer timer = null;
  private static final long PERIOD_DAY = 259200000L;

  public void contextInitialized(ServletContextEvent event)
  {
    Calendar calendar = Calendar.getInstance();
    calendar.set(11, 2);
    calendar.set(12, 0);
    calendar.set(13, 0);
    Date date = calendar.getTime();
    if (date.before(new Date())) {
      date = addDay(date, 1);
    }
    //this.timer = new Timer(true);
    //event.getServletContext().log("定时器已启动");
   // this.timer.schedule(new Task(event.getServletContext()), 5000L, 259200000L);
   // event.getServletContext().log("已经添加任务");
  }

  public void contextDestroyed(ServletContextEvent event) {
    //this.timer.cancel();
    //event.getServletContext().log("定时器销毁");
  }
  public Date addDay(Date date, int num) {
    Calendar startDT = Calendar.getInstance();
    startDT.setTime(date);
    startDT.add(5, num);
    return startDT.getTime();
  }

  public static String getMACAddress() throws Exception {
    InetAddress ia = InetAddress.getLocalHost();
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