package com.jiguang;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import com.xiaheng.util.BASE64Encoder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
  
import cn.jpush.api.JPushClient;  
import cn.jpush.api.common.resp.APIConnectionException;  
import cn.jpush.api.common.resp.APIRequestException;  
import cn.jpush.api.push.PushResult;  
import cn.jpush.api.push.model.Message;  
import cn.jpush.api.push.model.Options;  
import cn.jpush.api.push.model.Platform;  
import cn.jpush.api.push.model.PushPayload;  
import cn.jpush.api.push.model.audience.Audience;  
import cn.jpush.api.push.model.notification.AndroidNotification;  
import cn.jpush.api.push.model.notification.IosNotification;  
import cn.jpush.api.push.model.notification.Notification;  

@SuppressWarnings({ "deprecation", "restriction" })
public class jiguangAll {
    private static String masterSecret = "870613ba7b26b64e3851a91e";
    private static String appKey = "ec0454d60d2e714e303de6fd";
    private static String pushUrl = "https://api.jpush.cn/v3/push";
    private static boolean apns_production = false;
    private static int time_to_live = 86400;
    private static final String ALERT = "推送信息"; 
    //private static final boolean content_available = true; 
    /**
     * 极光推送
     */
    public static void jiguangAll(String alias,String alert,String title,String type){
        try{
            String result = push(title,pushUrl,alias,alert,appKey,masterSecret,apns_production,time_to_live,type);
            JSONObject resData = JSONObject.fromObject(result);
                if(resData.containsKey("error")){
                    System.out.println("针对别名为" + alias + "的信息推送失败！");
                    JSONObject error = JSONObject.fromObject(resData.get("error"));
                    System.out.println("错误信息为：" + error.get("message").toString());
                }
            System.out.println("针对别名为" + alias + "的信息推送成功！");
        }catch(Exception e){
            System.out.println("针对别名为" + alias + "的信息推送失败！"+e);
        }
    }
    
    
    /**
     * 组装极光推送专用json串
     * @param alias
     * @param alert
     * @return json
     */
    public static JSONObject allPush(String title,String alias,String alert,boolean apns_production,int time_to_live,String type){
        JSONObject json = new JSONObject();
        JSONArray platform = new JSONArray();//平台
        platform.add("android");
        platform.add("ios");
        
        JSONObject audience = new JSONObject();//推送目标
        JSONArray alias1 = new JSONArray();
        alias1.add(alias);
        audience.put("tag", alias1);

        
        JSONObject notification = new JSONObject();//通知内容
        JSONObject android = new JSONObject();//android通知内容
        android.put("alert", alert);
        android.put("builder_id", 1);
        JSONObject android_extras = new JSONObject();//android额外参数
        android_extras.put("clickid", title);
        android_extras.put("type", type);
        android.put("extras", android_extras);
        
        JSONObject ios = new JSONObject();//ios通知内容
        ios.put("alert", alert);
        ios.put("sound", "default");
        ios.put("badge", "+1");
        ios.put("content-available", true); 
        JSONObject ios_extras = new JSONObject();//ios额外参数
        ios_extras.put("clickid", title);
        ios_extras.put("type", type);
        ios.put("extras", ios_extras);
        notification.put("android", android);
        notification.put("ios", ios);
        
        JSONObject options = new JSONObject();//设置参数
        options.put("time_to_live", Integer.valueOf(time_to_live));
        options.put("apns_production", apns_production);
        
        json.put("platform", "all");
        json.put("audience", "all");
        //json.put("message", message);
        json.put("notification", notification);
        json.put("options", options);
        System.out.println(json);
        return json;
        
    }
    
    
    /**
     * 组装极光推送专用json串
     * @param alias
     * @param alert
     * @return json
     */
    public static JSONObject generateJson(String title,String alias,String alert,boolean apns_production,int time_to_live,String type){
        JSONObject json = new JSONObject();
        JSONArray platform = new JSONArray();//平台
        platform.add("android");
        platform.add("ios");
        
        JSONObject audience = new JSONObject();//推送目标
        JSONArray alias1 = new JSONArray();
        alias1.add(alias);
        audience.put("tag", alias1);
        
//        JSONObject message = new JSONObject();//推送目标
//        message.put("msg_content","23423423424");
//        message.put("content_type","text");
//        message.put("title","D1503041156814553,3");
//       // message.put("title","D1503041156814553,3");
//        JSONObject messageKey = new JSONObject();//推送目标
//        messageKey.put("clickid", "D1503041156814553,3");
//        message.put("extras",messageKey);
        
        JSONObject notification = new JSONObject();//通知内容
        JSONObject android = new JSONObject();//android通知内容
        android.put("alert", alert);
        android.put("builder_id", 1);
        JSONObject android_extras = new JSONObject();//android额外参数
        android_extras.put("clickid", title);
        android_extras.put("type", type);
        android.put("extras", android_extras);
        
        JSONObject ios = new JSONObject();//ios通知内容
        ios.put("alert", alert);
        ios.put("sound", "default");
        ios.put("badge", "+1");
        ios.put("content-available", false); 
        JSONObject ios_extras = new JSONObject();//ios额外参数
        ios_extras.put("clickid", title);
        ios_extras.put("type", type);
        ios.put("extras", ios_extras);
        notification.put("android", android);
        notification.put("ios", ios);
        
        JSONObject options = new JSONObject();//设置参数
        options.put("time_to_live", Integer.valueOf(time_to_live));
        options.put("apns_production", apns_production);
        System.out.println(alias+"=========");
        
       // if(!alias.equals("") && alias !=null){
        json.put("platform", "all");
        json.put("audience", "all");
       
        //json.put("message", message);
        json.put("notification", notification);
        json.put("options", options);
        System.out.println(json);
        return json;
        
    }
    
    /**
     * 推送方法-调用极光API
     * @param reqUrl
     * @param alias
     * @param alert
     * @return result
     */
    public static String push(String title,String reqUrl,String alias,String alert,String appKey,String masterSecret,boolean apns_production,int time_to_live,String type){
        String base64_auth_string = encryptBASE64(appKey + ":" + masterSecret);
        String authorization = "Basic " + base64_auth_string;
        return sendPostRequest(reqUrl,generateJson(title,alias,alert,apns_production,time_to_live,type).toString(),"UTF-8",authorization);
    }
    
    /**
     * 发送Post请求（json格式）
     * @param reqURL
     * @param data
     * @param encodeCharset
     * @param authorization
     * @return result
     */
    @SuppressWarnings({ "resource" })
    public static String sendPostRequest(String reqURL, String data, String encodeCharset,String authorization){
        HttpPost httpPost = new HttpPost(reqURL);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        String result = "";
        try {
             StringEntity entity = new StringEntity(data, encodeCharset);
             entity.setContentType("application/json");
             httpPost.setEntity(entity);
             httpPost.setHeader("Authorization",authorization.trim());
             response = client.execute(httpPost);
             result = EntityUtils.toString(response.getEntity(), encodeCharset);
        } catch (Exception e) {
            System.out.println("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下"+e);  
        }finally{
            client.getConnectionManager().shutdown();
        }
        return result;
    }
     /** 
　　　　* BASE64加密工具
　　　　*/
     public static String encryptBASE64(String str) {
         byte[] key = str.getBytes();
       BASE64Encoder base64Encoder = new BASE64Encoder();
       String strs = base64Encoder.encodeBuffer(key);
         return strs;
     }
     
     
     
     public static void sendAllsetNotification(String message)  
     {  
         JPushClient jpushClient = new JPushClient("870613ba7b26b64e3851a91e","ec0454d60d2e714e303de6fd");  
         //JPushClient jpushClient = new JPushClient(masterSecret, appKey);//第一个参数是masterSecret 第二个是appKey  
         Map<String, String> extras = new HashMap<String, String>();  
         // 添加附加信息  
         extras.put("extMessage", "我是额外的通知");  
         PushPayload payload = buildPushObject_all_alias_alert(message, extras);  
         try  
         {  
             PushResult result = jpushClient.sendPush(payload);  
             System.out.println(result);  
         }  
         catch (APIConnectionException e)  
         {  
             System.out.println(e);  
         }  
         catch (APIRequestException e)  
         {  
             System.out.println(e);  
             System.out.println("Error response from JPush server. Should review and fix it. " + e);  
             System.out.println("HTTP Status: " + e.getStatus());  
             System.out.println("Error Code: " + e.getErrorCode());  
             System.out.println("Error Message: " + e.getErrorMessage());  
             System.out.println("Msg ID: " + e.getMsgId());  
         }  
     }  
     /** 
      * 给所有平台的所有用户发消息 
      *  
      * @param message 
      * @author WangMeng 
      * @date 2017年1月13日 
      */  
     public static void sendAllMessage(String message)  
     {  
    	 JPushClient jpushClient = new JPushClient("870613ba7b26b64e3851a91e","ec0454d60d2e714e303de6fd");  
         
         Map<String, String> extras = new HashMap<String, String>();  
         // 添加附加信息  
         extras.put("extMessage", "我是额外透传的消息");  
         PushPayload payload = buildPushObject_all_alias_Message(message, extras);  
         try  
         {  
             PushResult result = jpushClient.sendPush(payload);  
             System.out.println(result+"----------");  
         }  
         catch (APIConnectionException e)  
         {  
             System.out.println(e);  
         }  
         catch (APIRequestException e)  
         {  
             System.out.println(e);  
             System.out.println("Error response from JPush server. Should review and fix it. " + e);  
             System.out.println("HTTP Status: " + e.getStatus());  
             System.out.println("Error Code: " + e.getErrorCode());  
             System.out.println("Error Message: " + e.getErrorMessage());  
             System.out.println("Msg ID: " + e.getMsgId());  
         }  
     }  
     /** 
      * 发送通知 
      *  
      * @param message 
      * @param extras 
      * @return 
      * @author WangMeng 
      * @date 2017年1月13日 
      */  
     private static PushPayload buildPushObject_all_alias_alert(String message,  
             Map<String, String> extras)  
     {  
         return PushPayload.newBuilder()  
                 .setPlatform(Platform.all())  
                 // 设置平台  
                 .setAudience(Audience.all())  
                 // 按什么发送 tag alia  
                 .setNotification(  
                         Notification  
                                 .newBuilder()  
                                 .setAlert(message)  
                                 .addPlatformNotification(  
                                         AndroidNotification.newBuilder().addExtras(extras).build())  
                                 .addPlatformNotification(  
                                         IosNotification.newBuilder().addExtras(extras).build())  
                                 .build())  
                 // 发送消息  
                 .setOptions(Options.newBuilder().setApnsProduction(false).build()).build();  
                 //设置ios平台环境  True 表示推送生产环境，False 表示要推送开发环境   默认是开发    
     }  
     /** 
      * 发送透传消息 
      *  
      * @param message 
      * @param extras 
      * @return 
      * @author WangMeng 
      * @date 2017年1月13日 
      */  
     private static PushPayload buildPushObject_all_alias_Message(String message,  
             Map<String, String> extras)  
     {  
         return PushPayload.newBuilder().setPlatform(Platform.all())  
         // 设置平台  
         .setAudience(Audience.all())  
             // 按什么发送 tag alia  
             .setMessage(Message.newBuilder().setMsgContent(message).addExtras(extras).build())  
             // 发送通知  
             .setOptions(Options.newBuilder().setApnsProduction(false).build()).build();  
         //设置ios平台环境  True 表示推送生产环境，False 表示要推送开发环境   默认是开发    
     }  
   
     /** 
      * 客户端 给所有平台的一个或者一组用户发送信息 
      */  
     public static void sendAlias(String message, List<String> aliasList)  
     {  
    	 JPushClient jpushClient = new JPushClient("870613ba7b26b64e3851a91e","ec0454d60d2e714e303de6fd");  
          
         Map<String, String> extras = new HashMap<String, String>();  
         // 添加附加信息  
         extras.put("extMessage", "我是额外的消息--sendAlias");  
   
         PushPayload payload = allPlatformAndAlias(message, extras, aliasList);  
         try  
         {  
             PushResult result = jpushClient.sendPush(payload);  
             System.out.println(result);  
         }  
         catch (APIConnectionException e)  
         {  
             System.out.println(e);  
         }  
         catch (APIRequestException e)  
         {  
             System.out.println(e);  
             System.out.println("Error response from JPush server. Should review and fix it. " + e);  
             System.out.println("HTTP Status: " + e.getStatus());  
             System.out.println("Error Code: " + e.getErrorCode());  
             System.out.println("Error Message: " + e.getErrorMessage());  
             System.out.println("Msg ID: " + e.getMsgId());  
         }  
     }  
   
     /** 
      * 极光推送：生成向一个或者一组用户发送的消息。 
      */  
     private static PushPayload allPlatformAndAlias(String alert, Map<String, String> extras,  
             List<String> aliasList)  
     {  
   
         return PushPayload  
                 .newBuilder()  
                 .setPlatform(Platform.all())  
                 .setAudience(Audience.alias(aliasList))  
                 .setNotification(  
                         Notification  
                                 .newBuilder()  
                                 .setAlert(alert)  
                                 .addPlatformNotification(  
                                         AndroidNotification.newBuilder().addExtras(extras).build())  
                                 .addPlatformNotification(  
                                         IosNotification.newBuilder().addExtras(extras).build())  
                                 .build())  
                 .setOptions(Options.newBuilder().setApnsProduction(false).build()).build();  
     }  
     /** 
      * 客户端 给平台的一个或者一组标签发送消息。 
      */  
     public static void sendTag(String message, String messageId, String type, List<String> tagsList)  
     {  
    	 JPushClient jpushClient = new JPushClient("870613ba7b26b64e3851a91e","ec0454d60d2e714e303de6fd");  
          
         // 附加字段  
         Map<String, String> extras = new HashMap<String, String>();  
         extras.put("messageId", messageId);  
         extras.put("typeId", type);  
   
         PushPayload payload = allPlatformAndTag(message, extras, tagsList);  
         try  
         {  
             PushResult result = jpushClient.sendPush(payload);  
             System.out.println(result);  
         }  
         catch (APIConnectionException e)  
         {  
             System.out.println(e);  
         }  
         catch (APIRequestException e)  
         {  
             System.out.println(e);  
             System.out.println("Error response from JPush server. Should review and fix it. " + e);  
             System.out.println("HTTP Status: " + e.getStatus());  
             System.out.println("Error Code: " + e.getErrorCode());  
             System.out.println("Error Message: " + e.getErrorMessage());  
             System.out.println("Msg ID: " + e.getMsgId());  
         }  
     }  
   
     /** 
      * 极光推送：生成向一组标签进行推送的消息。 
      */  
     private static PushPayload allPlatformAndTag(String alert, Map<String, String> extras,  
             List<String> tagsList)  
     {  
   
         return PushPayload  
                 .newBuilder()  
                 .setPlatform(Platform.android_ios())  
                 .setAudience(Audience.tag(tagsList))  
                 .setNotification(  
                         Notification  
                                 .newBuilder()  
                                 .setAlert(alert)  
                                 .addPlatformNotification(  
                                         AndroidNotification.newBuilder().addExtras(extras).build())  
                                 .addPlatformNotification(  
                                         IosNotification.newBuilder().addExtras(extras).build())  
                                 .build())  
                 .setOptions(Options.newBuilder().setApnsProduction(false).build()).build();  
     }  
     public static void main(String[] args){
    	 
    	 
    	 String alias = "15";
    	 String alert = "alert111111";//推送的标题
    	 String title="title222222";//点击事件ID oid
    	 jiguangAll("",alert,title,"2");
    	 
//    	 new JiguangPush().sendAllMessage("这是后台发送的透传消息");
    	 
 
     }
}
