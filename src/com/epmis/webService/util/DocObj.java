package com.epmis.webService.util;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
 


import com.epmis.webService.service.IDocUpdateService;
import com.epmis.webService.service.ILicenseService;
 
public class DocObj 
{
	private static final String namespace="http://OpDoc.xfire.webservice/WebService";
	private static IDocUpdateService service = null;
	public synchronized static IDocUpdateService getServiceInstance()
	{
        
    	Service serviceModel = new ObjectServiceFactory().create(IDocUpdateService.class,null,namespace,null);  
		XFireProxyFactory facotry = new XFireProxyFactory(XFireFactory.newInstance().getXFire());  
		ResourceBundle rb=ResourceBundle.getBundle("system");  
		String address = rb.getString("CLOUD-SERVICES");   
		 String endpoint = address+"/services/OpDoc"; 
		// String endpoint = "http://localhost:8080/WebService/services/OpDoc"; 
		if(service == null)
		{
			try 
			{
				service = (IDocUpdateService) facotry.create(serviceModel, endpoint);
			} catch (MalformedURLException e) 
			{
				e.printStackTrace();
			}
		}
		return service;
	}

	public static void main(String[] args){
		String a = "";
		IDocUpdateService service = DocObj.getServiceInstance();//获得服务器代理对象 
	//	String flag = service.ConnectTest(mac);
	//	System.out.print(flag+"**");
	}
}
