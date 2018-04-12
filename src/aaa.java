import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
import net.sf.json.JSONObject;

public class aaa {
	
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    
    public static String url ="http://www.hankemall.com:8081/php/api/refund/upload";
    public static final String APPKEY ="e727ba9ba993a4521fcf620148a5edb0";
 
    public static String getRequest1(String phone,String code,String orderNo,String desc,int amount){
    	String result = null;
        Map params = new HashMap();
            params.put("phone",phone);
            params.put("code",code);
            params.put("chargeOrderId",orderNo);
            params.put("desc",desc);
            params.put("amount",amount);
            System.out.println(params);
        try {
            result = net(url, params, "POST");
            	JSONObject object = JSONObject.fromObject(result);
            	System.out.println(object.toString());
//	            Map<String, Object> map = Phone.readXml(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
 
 
 
    public static void main(String[] args) {
//    	getRequest1("");
    }
 
    /**
     *
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("POST")){
                strUrl = strUrl+"?"+urlencode(params);
                strUrl = strUrl.substring(0, strUrl.length() - 1);
                System.out.println(strUrl);
            }
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
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (Exception e) {
           // e.printStackTrace();
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
			map = parseMap1(map, list);
			
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
//				map1 = Phone.parseMap1(map1, list1);
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
//    			map1 = Phone.parseMap2(map1, list1);
    			map.put(et.getName(), map1);
    		}else{
    			map.put(et.getName(), et.getText());
    		}
    	}
    	return map;
    }

}