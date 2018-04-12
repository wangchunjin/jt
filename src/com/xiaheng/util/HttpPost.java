package com.xiaheng.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


public class HttpPost {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
    	
    	String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    	
    	//把参数转为二维码
    	String appid="wxdf914be6a2732eba";							//公众账号ID
    	String mch_id="1361088102";									//商户号
    	String nonce_str="5K8264ILTKCH16CQ2502SI8ZNMTM67VS";		//随机字符串(不长于32位)
    	String sign="308B8F00A9AEB20CE77F5E79E0687783";				//签名
    	String body="oppoR9plass";									//商品描述
    	String out_trade_no="5551563";								//商户订单号
    	String attach="您有2%的几率中奖。";											//附加数据
    	String totalFee="1";										//总金额
    	String spbill_create_ip="192.168.0.41";			//终端IP    自己获取
    	String notify_url="http://192.168.0.37:8080/zhongkexiche/xd_1.jsp";										//通知地址
    	String trade_type="NATIVE";										//交易类型SAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
    	String device_info="WEB";									//终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
    	//Strign stringA="appid="+appid+"&body="+body+"&device_info="+device_info+"&mch_id="+mch_id+"&nonce_str="+nonce_str;
    	//String stringSignTemp=stringA+"&key=192006250b4c09247ec02edce69f6a2d";//key的值?
//    	sign=MD5(stringSignTemp).toUpperCase();
    	String xml = "<xml>" + "<appid>" + appid + "</appid>"  
    	        + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
    	        + "</nonce_str>" + "<sign>" + sign + "</sign>"  
    	        + "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>"  
    	        + out_trade_no + "</out_trade_no>" + "<attach>" + attach  
    	        + "</attach>" + "<total_fee>" + totalFee + "</total_fee>"  
    	        + "<spbill_create_ip>" + spbill_create_ip  
    	        + "</spbill_create_ip>" + "<notify_url>" + notify_url  
    	        + "</notify_url>" + "<trade_type>" + trade_type  
    	        + "</trade_type>" + "</xml>";  
    	
    	System.out.println(xml);
    	
    	String param = "";
    	
    	String ret = com.xiaheng.util.HttpPost.sendPost(url,xml);
    	
    	System.out.println("ret:"+ret);
	}
    
}
