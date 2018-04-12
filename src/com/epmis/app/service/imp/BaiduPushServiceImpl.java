package com.epmis.app.service.imp;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushBatchUniMsgRequest;
import com.baidu.yun.push.model.PushBatchUniMsgResponse;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.epmis.app.service.BaiduPushService;

import net.sf.json.JSONObject;


@Transactional
@Service("baiduPushService")
public class BaiduPushServiceImpl implements BaiduPushService{
	 private static String iosApiKey = "miez2y9TP7o14OMZLkPRIfBE";
	 private static String iosSecretKey = "cZ3CLHX1NzOnVKhQweNCREQ1BpLohtgi";

	 private static String androidApiKey = "T1tPqZMgTh56v6EyWKuydWMV";
	 private static String androidSecretKey = "fzQfIkZh3QSRE6F8nOY5L0fcYEP1rVcf";
  
	/**
	 * 推送单个设备
	 * @param userId
	 * @param content
	 * @param channel
	 * @param pushId
	 * @throws PushClientException
	 * @throws PushServerException
	 */
	 @Override
	public  void  IosPushMsg(String title,String channel,String key1,String key2,String sound) throws PushClientException, PushServerException{
		// 1. get apiKey and secretKey from developer console
		PushKeyPair pair = new PushKeyPair(iosApiKey, iosSecretKey);

		//2. build a BaidupushClient object to access released interfaces
		BaiduPushClient pushClient = new BaiduPushClient(pair,
				BaiduPushConstants.CHANNEL_REST_URL);

		// 3. register a YunLogHandler to get detail interacting information
		// in this request.
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});

		try {
			// 4. specify request arguments
			// make IOS Notification
			JSONObject notification = new JSONObject();
			JSONObject jsonAPS = new JSONObject();
			jsonAPS.put("alert", title);
			jsonAPS.put("sound", sound); // 设置通知铃声样式，例如"ttt"，用户自定义。
			notification.put("aps", jsonAPS);
			notification.put("key1", key1);
			notification.put("key2", key2);
			
			PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
			.addChannelId(channel)
			.addMsgExpires(new Integer(3600)). // 设置message的有效时间
			addMessageType(1).// 1：通知,0:透传消息.默认为0 注：IOS只有通知.
			addMessage(notification.toString()).
			//addDeployStatus(1). // IOS,
												// DeployStatus
												// => 1: Developer
												// 2: Production.
			addDeviceType(4);// deviceType => 1:android, 2:ios
			// 5. http request
			PushMsgToSingleDeviceResponse response = pushClient
					.pushMsgToSingleDevice(request);
			// Http请求结果解析打印
			System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
						+ response.getSendTime());
		} catch (PushClientException e) {
			/*
			* ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,'true' 表示抛出, 'false' 表示捕获。
			*/
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else
			{
				e.printStackTrace();
			}
		} catch (PushServerException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				System.out.println(String.format(
						"requestId: %d, errorCode: %d, errorMessage: %s",
						e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
			}
		}
	}
	 @Override
	public  void AndroidPushMsg(String title,String content,
			String[] channel,String key1,String key2) throws PushClientException,
			PushServerException {
		PushKeyPair pair = new PushKeyPair(androidApiKey, androidSecretKey);

		// 2. build a BaidupushClient object to access released interfaces
		BaiduPushClient pushClient = new BaiduPushClient(pair,
				BaiduPushConstants.CHANNEL_REST_URL);

		// 3. register a YunLogHandler to get detail interacting information
		// in this request.
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});

		try {
			// 4. specify request arguments
			// 创建 Android的通知
			JSONObject notification = new JSONObject();
			notification.put("title", title);
			notification.put("description", content);
			notification.put("notification_builder_id", 0);
			notification.put("notification_basic_style", 7);
			notification.put("open_type", 2);
		//	notification.put("url", "http://push.baidu.com");
			JSONObject jsonCustormCont = new JSONObject();
			jsonCustormCont.put("key1", key1); // 自定义内容，key-value
			jsonCustormCont.put("key2", key2); // 自定义内容，key-value
			notification.put("custom_content", jsonCustormCont);

	//		PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
			PushBatchUniMsgRequest request =  new PushBatchUniMsgRequest()
					.addChannelIds(channel).addMsgExpires(new Integer(3600)). // message有效时间
					addMessageType(1).// 1：通知,0:透传消息. 默认为0 注：IOS只有通知.
					addMessage(notification.toString())
					.addDeviceType(3);//  // 3:android,
																			// 4:ios
			// 5. http request
		//	PushMsgToSingleDeviceResponse response = pushClient
				//	.pushMsgToSingleDevice(request);
			PushBatchUniMsgResponse response = pushClient.pushBatchUniMsg(request);
			// Http请求结果解析打印
			System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
					+ response.getSendTime());
		} catch (PushClientException e) {
			/*
			 * ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,'true' 表示抛出, 'false' 表示捕获。
			 */
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else 
			{
				e.printStackTrace();
			}
		} catch (PushServerException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				System.out.println(String.format(
						"requestId: %d, errorCode: %d, errorMessage: %s",
						e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
			}
		}

	}

public static void main(String[] args) throws PushClientException, PushServerException{
 		String key1="";
 		String key2="";
 		
 		String content="";
    //新闻;
 	  key1 = "2";
 	  key2 ="D8CA8BDFF6DE4188B480F32E0254DF2A";	
	  content = "测试新闻推送2（公司2015新闻)";
 	
 	
	//公告
 	  key1 = "3";
 	  key2 ="049709B1D57E45BF82A8933AE6C535FE";	
	  content = "测试公告推送1";
		
		
		
 	//String[] channels = {"3965368899916131778"};
	  String aa = "4396619258449725228";
 //	String[] channels = {"4088458546218175966"};//左
	  String[] channels = aa.split(",");
 	//String channel = "4952382131136616864";//
    String channel ="5643485974243985180"; //左
    String sound = "default";
   //	new BaiduPushServiceImpl().IosPushMsg(content,channel, key1,key2,sound); //发给指定用户
      new BaiduPushServiceImpl().AndroidPushMsg("XX1", "XXX", channels, "1", "2");
 	//pushIosSys(content);//发给ios所有用户 	
 }
	
}
