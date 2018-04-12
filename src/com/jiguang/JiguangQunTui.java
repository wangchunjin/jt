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
@SuppressWarnings({ "deprecation", "restriction" })
public class JiguangQunTui{

    private static String masterSecret ;
    private static String appKey ;

    private static String pushUrl = "https://api.jpush.cn/v3/push"; 
    private static boolean apns_production = false;    
    private static int time_to_live = 86400;
    private static final String ALERT = "推送信息"; 
    
    
    /**
     * 极光推送
     */
    public static void jiguangQunTui(String alias,String alert,String title ,int tag,int status){
    		masterSecret = "bd7f2df66c9fedd554b062c2";
    		appKey = "61f490bd77f16c113e277e6e";
        try{
            String result = push(title,pushUrl,alias,alert,tag,status,appKey,masterSecret,apns_production,time_to_live);
            JSONObject resData = JSONObject.fromObject(result);
                if(resData.containsKey("error")){
                    System.out.println("针对标签为" + alias + "的信息推送失败！");
                    JSONObject error = JSONObject.fromObject(resData.get("error"));
                    System.out.println("错误信息为：" + error.get("message").toString());
                }
            System.out.println("针对标签为" + alias + "的信息推送成功！");
        }catch(Exception e){
            System.out.println("针对标签为" + alias + "的信息推送失败！"+e);
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
        System.out.println(audience);
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
        
        json.put("platform", "all");
        if(alias.equals("3")){
        	json.put("audience", "all");
        }else{
        	json.put("audience", audience);
        }
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
    	 String alias = "3";
    	 String alert = "测试";
    	 String title="1";
    	 int  tag=7;
    	 int  status=7;
    	 jiguangQunTui(alias,alert,title,tag,status);
     }
}