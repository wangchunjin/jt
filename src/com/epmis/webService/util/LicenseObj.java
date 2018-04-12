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
 

import com.epmis.webService.service.ILicenseService;
 
public class LicenseObj 
{
	private static final String namespace="http://OpLicense.xfire.webservice/WebService";
	private static ILicenseService service = null;
	public synchronized static ILicenseService getServiceInstance()
	{
        
    	Service serviceModel = new ObjectServiceFactory().create(ILicenseService.class,null,namespace,null);   
		XFireProxyFactory facotry = new XFireProxyFactory(XFireFactory.newInstance().getXFire());
	//	 String endpoint = "http://localhost:8080/WebService/services/OpLicense"; 
		ResourceBundle rb=ResourceBundle.getBundle("system");  
		String address = rb.getString("CLOUD-SERVICES");
	 	String endpoint = address+"/services/OpLicense"; 
		if(service == null)
		{
			try 
			{
				service = (ILicenseService) facotry.create(serviceModel, endpoint);
			} catch (MalformedURLException e) 
			{
				e.printStackTrace();
			}
		}
		return service;
	}
	public static void main(String[] args){ 
		ILicenseService service = LicenseObj.getServiceInstance();//获得服务器代理对象 
	//	String flag = service.ConnectTest();
	//	System.out.print(flag);
	}
}
