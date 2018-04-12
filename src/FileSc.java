
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import net.sf.json.JSONObject;

public class FileSc {
	
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    
    public static String url ="http://www.hankemall.com:8081/php/api/upload";
    public static final String APPKEY ="e727ba9ba993a4521fcf620148a5edb0";
  
    public static HttpServletRequest request;
    public static HttpServletResponse response = ServletActionContext.getResponse();
    public static File file;
    public static String fileFileName;
    public static String FileContentType;

 


	public static String getRequest1(String img) throws IOException{
    	String result = null;
        Map params = new HashMap();
        request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;chaset=UTF-8");       
		String root = ServletActionContext.getServletContext().getRealPath("/");
		String dirName ="upload";
		String dirPath = root+dirName;
		System.out.println(dirPath);
		//拼接文件夹
   	 	File dirFile = new File(dirPath);
			if(!dirFile.exists()){
				dirFile.mkdir();	//创建文件夹
			}
        if(file!=null&&!"".equals(file)){
	        InputStream is = new FileInputStream(file);
	        //中文乱码处理
			String uu = UUID.randomUUID().toString().replace("-", "");
			String lastName = fileFileName.substring(fileFileName.lastIndexOf("."));
			fileFileName = uu+""+lastName;
	    	String filePath = dirPath+"/"+fileFileName;
	 	    OutputStream os = new FileOutputStream(filePath);
	        String src="/"+dirName+"/"+fileFileName;
	        System.out.println(src);
	        System.out.println(filePath);
	        byte[] buffer = new byte[500];
	        int length = 0;
        
        while(-1 != (length = is.read(buffer, 0, buffer.length)))
	        {
	            os.write(buffer);
	        }
		        os.close();
		        is.close();
		        return src ;
        	}
      
            params.put("img",file);

            System.out.println(params);
        try {
            result = net(url, params, "POST");
            	JSONObject object = JSONObject.fromObject(result);
            	System.out.println(object.toString());
	            Map<String, Object> map = FileSc.readXml(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
 
 
 
    public static void main(String[] args) {
//    	getRequest1();
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
				map1 = FileSc.parseMap1(map1, list1);
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
    			map1 = FileSc.parseMap2(map1, list1);
    			map.put(et.getName(), map1);
    		}else{
    			map.put(et.getName(), et.getText());
    		}
    	}
    	return map;
    }

}