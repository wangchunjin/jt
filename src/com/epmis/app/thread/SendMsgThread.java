package com.epmis.app.thread;

import com.epmis.app.service.SendMsgService;
import com.epmis.app.vo.SysAppMsg;
import com.epmis.sys.util.SpringContextHolder;

public class SendMsgThread extends Thread {
    static SendMsgService sendMsgService =((SendMsgService)SpringContextHolder.getBean("sendMsgService"));
    private SysAppMsg sysAppMsg ; 
    public SendMsgThread(SysAppMsg sysAppMsg) 
    { 
    this.sysAppMsg = sysAppMsg; 
    } 

	public void run() {  
		sendMsgService.SendMsg(sysAppMsg);
	}  
}
