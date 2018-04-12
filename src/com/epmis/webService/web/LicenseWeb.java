package com.epmis.webService.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;

import org.codehaus.xfire.transport.http.HttpTransport;
import org.codehaus.xfire.util.Base64;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import com.epmis.sys.util.AppSetting;
import com.epmis.sys.util.WriterJsonArray;
import com.epmis.webService.service.ILicenseService;
import com.epmis.webService.util.LicenseObj;

public class LicenseWeb {

	/**
	 * @param args
	 */

	public static void main(String[] args) { 
		downLicense("F0-76-1C-04-54-AE");
	 	 
	}
	public static String TestConnect(String mac)
	{
		String flag = "N";
		try{
		ILicenseService service = LicenseObj.getServiceInstance();//获得服务器代理对象 
		flag = service.ConnectTest(mac,AppSetting.PROJECT_NAME.substring(1));
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	public static String  downLicense(String mac){
		FileOutputStream  data_fos= null;
		String flag = "N";
    	try {  
    		ILicenseService service = LicenseObj.getServiceInstance();//获得服务器代理对象 
    		if(TestConnect(mac).equals("Y")){    		
    		  if(service.createLicense(mac,AppSetting.PROJECT_NAME.substring(1))){
	              Client client = Client.getInstance(service);
	              client.setProperty("mtom-enabled", "true");
	              client.setProperty(HttpTransport.CHUNKING_ENABLED, "true");
	             
	              String data = service.downLicense();
	             
	             byte[] bytes = Base64.decode(data);    
	              data_fos = new FileOutputStream(AppSetting.PROJECT_PATH+"/license.txt"); 
	             data_fos.write(bytes); 
	             data_fos.flush(); 
    		  }
    		  flag = "Y";
    		}
          } catch (Exception e) {           
              e.printStackTrace();
          }finally{
        	  if(data_fos!=null){
        		  try {
					data_fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	  }
          }
		return flag;
    
    }
    	
}
