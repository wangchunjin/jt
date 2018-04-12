package com.epmis.app.service;

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.epmis.app.vo.SysAppMsg;

public abstract interface BaiduPushService {
	public abstract void IosPushMsg(String title,String channel,String key1,String key2,String sound) throws PushClientException, PushServerException;
	public abstract void AndroidPushMsg(String title, String content, String[] channel, String key1, String key2) throws PushClientException,PushServerException;

}
