package com.jiguang;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;







import com.xiaheng.util.BASE64Encoder;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@SuppressWarnings({ "deprecation", "restriction" })
public class JiguangPush {
	private static String masterSecret ;
	private static String appKey ;
    private static String pushUrl = "https://api.jpush.cn/v3/push"; 
    private static boolean apns_production = false;    
    private static int time_to_live = 86400;
    private static final String ALERT = "推送信息"; 
    
    /**
     * 极光推送
     */
    public static void jiguangPush(String alias,String alert,String title,int tag,int status){
       		  masterSecret = "b62406e212409b035a03586e";
       		  appKey = "1b2520bdf9d67a96b96b9a99";
        
        try{
            String result = push(title,pushUrl,alias,alert,tag,status,appKey,masterSecret,apns_production,time_to_live);
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
    public static JSONObject generateJson(String title,String alias,String alert,int tag,int status,boolean apns_production,int time_to_live){
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
        JSONObject android_extras = new JSONObject();//android可加多个额外参数,
        android_extras.put("clickid", title);
        android_extras.put("type", tag);
        android_extras.put("status", status);
        android.put("extras", android_extras);
        
        JSONObject ios = new JSONObject();//ios通知内容
        ios.put("alert", alert);
        ios.put("sound", "default");
        ios.put("badge", "+1");
        JSONObject ios_extras = new JSONObject();//ios可加多个额外参数
        ios_extras.put("clickid", title);
        ios_extras.put("type", tag);
        ios_extras.put("status", status);
        ios.put("extras", ios_extras);
        notification.put("android", android);
        notification.put("ios", ios);
        
        JSONObject options = new JSONObject();//设置参数
        options.put("time_to_live", Integer.valueOf(time_to_live));
        options.put("apns_production", apns_production);
        
        json.put("platform", platform);
        json.put("audience", audience);
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
    public static String push(String title,String reqUrl,String alias,String alert,int tag,int status,String appKey,String masterSecret,boolean apns_production,int time_to_live){
        String base64_auth_string = encryptBASE64(appKey + ":" + masterSecret);
        String authorization = "Basic " + base64_auth_string;
        return sendPostRequest(reqUrl,generateJson(title,alias,alert,tag,status,apns_production,time_to_live).toString(),"UTF-8",authorization);
    }
    
    
    
    /** 
     * 客户端 给所有平台的一个或者一组用户发送信息 
     */  
    public static void sendAlias(String message, List<String> aliasList)  
    {  
   	 JPushClient jpushClient = new JPushClient("b62406e212409b035a03586e","1b2520bdf9d67a96b96b9a99");  
         
        Map<String, String> extras = new HashMap<String, String>();  
        // 添加附加信息  
        extras.put("extMessage", "我是额外的消息--sendAlias");  
        System.out.println(aliasList+"++++++++");
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
                .setAudience(Audience.tag(aliasList))  
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
       String strs = base64Encoder.encode(key);
         return strs;
     }
     
     public static void main(String[] args){
    	/* String alias = "18888888888";
    	 String alert = "预约行程已开始";
    	 String title=",7";//事件的主键id
    	 int tag=2;//推送类型 1、异地登录的   2、系统消息（1、日报2、投诉反馈3、我的行程） 3、用户订阅的 （1、今日观察2、某个楼盘动态3、楼价走势）4、问答回复我的

    	 int status=3;// tag  下的子类型  如 tag=2  status=3  表示系统消息下的  我的行程消息，再根据 title 里的 主键 id 跳列表或者详情
    	 jiguangPush(alias,alert,title,tag,status);
    	 //举个例子  jiguangPush(“18792139123”,“预约开始服务了”,“”,2,3);   如果没有 字段就给空
*/     
    	 List<String> aliasList = new ArrayList<String>();
    	 aliasList.add("15358116015");
    	 aliasList.add( "19999999999");
    	  aliasList.add("17300000000");
    	   aliasList.add("15189841110");
    	 sendAlias("针对部分人测试", aliasList); 
     
     
     
     
     }
}