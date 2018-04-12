package com.epmis.app.listen;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;//定时器类
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
 
public class TaskListener implements ServletContextListener
{
  private Timer timer = null;
  private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;
  public void contextInitialized(ServletContextEvent event)
  {//在这里初始化监听器，在tomcat启动的时候监听器启动，可以在这里实现定时器功能
	  Calendar calendar = Calendar.getInstance();  
	  calendar.set(Calendar.HOUR_OF_DAY,9);
	  calendar.set(Calendar.MINUTE,0);
	  calendar.set(Calendar.SECOND,0);
	  Date date=calendar.getTime(); //第一次执行定时任务的时间
	  if (date.before(new Date())) {
	      date = this.addDay(date, 1);
	  } 
      //timer = new Timer(true);
      //event.getServletContext().log("推送定时器已启动");//添加日志，可在tomcat日志中查看到
      //timer.schedule(new Task(event.getServletContext()),date,PERIOD_DAY);//调用exportHistoryBean，0表示任务无延迟，5*1000表示每隔5秒执行任务，60*60*1000表示一个小时。
      //event.getServletContext().log("已经添加任务");
  }
  public void contextDestroyed(ServletContextEvent event)
  {//在这里关闭监听器，所以在这里销毁定时器。
      //timer.cancel();
      //event.getServletContext().log("定时器销毁");
  }
	 public Date addDay(Date date,int num) {
		  Calendar startDT = Calendar.getInstance();
		  startDT.setTime(date);
		  startDT.add(Calendar.DAY_OF_MONTH, num);
		  return startDT.getTime();
		 }
}