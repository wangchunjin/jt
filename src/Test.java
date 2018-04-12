import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

//import com.epmis.tkgl.web.Phone;

import net.sf.json.JSONObject;

 /**
  * 银行接口
  * @author MyBot
  *
  */
public class Test {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    public static String url ="http://118.89.140.184:8082/";//请求接口地址
 
    public static String getRequest1(){
        String result =null;
        Map params = new HashMap();
        
    try {
        result = net(url,params,"POST");
//        	JSONObject object = JSONObject.fromObject(result);
//        	System.out.println(object.toString());
//            Map<String, Object> map = Phone.readXml(result);
    } catch (Exception e) {
        e.printStackTrace();
    }
        return result;
    }
 

// public static String getXML(Map params){
//	String a = "<?xml version='1.0' encoding = 'utf-8'?>"+
//		"<CMBSDKPGK>"+
//			"<INFO>"+
//				"<FUNNAM>GetBbkInfo</FUNNAM>"+
//				"<DATTYP>2</DATTYP>"+
//				"<LGNNAM>银企直连测试用户67</LGNNAM>"+
//			"</INFO>"+
//			"<NTACCBBKY>"+
//				"<ACCNBR></ACCNBR>"+
//			"</NTACCBBKY>"+
//		"</CMBSDKPGK>";
//	 return a;
// }
 
 public static String getXML(Map params){
	 String a = "<?xml version='1.0' encoding = 'utf-8'?>"+
	 "<CMBSDKPGK>"+
	 	"<INFO>"+
	 		"<FUNNAM>GetPaymentInfo</FUNNAM>"+
	 		"<DATTYP>2</DATTYP>"+
	 		"<LGNNAM>银企直连测试用户67</LGNNAM>"+
	 	"</INFO>"+
	 	"<SDKPAYQYX>"+
	 		"<BUSCOD></BUSCOD>"+
	 		"<BGNDAT>20170713</BGNDAT>"+
	 		"<ENDDAT>20170726</ENDDAT>"+
	 		"<YURREF></YURREF>"+
	 	"</SDKPAYQYX>"+
	 "</CMBSDKPGK>";
	 return a;
 }
 
 
 
    public static void main(String[] args) {
    	String str = getRequest1();
    	System.out.println(str);
    }
 
    public static String net(String strUrl,Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
	        OutputStream os = conn.getOutputStream();
	        os.write(Test.getXML(params).getBytes());
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,"utf-8");
            reader = new BufferedReader(isr);
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
//            while (isr.ready()) {   
//            	sb.append((char)isr.read());  
//            }  
            rs = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
    public static String urlencode(Map<String,String> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    public static Map<String,Object> readXml(String xmlDoc){
    	Map<String, Object> map = null;
    	StringReader reader = new StringReader(xmlDoc);
    
    	InputSource inputSource = new InputSource(reader);
    	SAXBuilder sb = new SAXBuilder();
    	try {
			Document doc = sb.build(inputSource);
			Element root = doc.getRootElement();
			List list = root.getChildren();
			Namespace ns = root.getNamespace();
			map = new HashMap<String, Object>();
			map = parseMap2(map, list);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return map;
    }
    public static Map<String,Object> parseMap1(Map map,List list){
    	int i = 1;
    	for(Object l : list){
			Element et = (Element) l;
			if (et.getChildren().hashCode() != 1) {
				List list1 = et.getChildren();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1 = Test.parseMap1(map1, list1);
				map.put(et.getName()+"-"+i, map1);
			}else{
				map.put(et.getName()+"-"+i, et.getText());
			}
			i++;
		}
    	return map;
    }

    public static Map<String,Object> parseMap2(Map map,List list){
    	for(Object l : list){
    		Element et = (Element) l;
    		if (et.getChildren().hashCode() != 1) {
    			List list1 = et.getChildren();
    			Map<String, Object> map1 = new HashMap<String, Object>();
    			map1 = Test.parseMap2(map1, list1);
    			map.put(et.getName(), map1);
    		}else{
    			map.put(et.getName(), et.getText());
    		}
    	}
    	return map;
    }
}