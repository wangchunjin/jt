package com.epmis.sys.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware{
	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException{
		SpringContextHolder.applicationContext = applicationContext;
	}
	public static ApplicationContext getApplicationContext()
	{
		checkApplicationContext();
		return applicationContext;
	}
	private static void checkApplicationContext()
	{
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}
	public static <T> T getBean(String beanName)
	{
		checkApplicationContext();
		return (T) applicationContext.getBean(beanName);
	}
	public static <T> T getBean(Class<T> clazz)
	{
		checkApplicationContext();
		return (T) applicationContext.getBeansOfType(clazz);
	}
	public static Map<String, Object> getAllBeans()
	{
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		Map beanMap = new HashMap();
		String[] arrayOfString1 = beanNames;int j = beanNames.length;
		for (int i = 0; i < j; i++)
		{
			String beanName = arrayOfString1[i];
			if (beanName.indexOf("ProxyTemplate") == -1)
			{
				Object bean = applicationContext.getBean(beanName);
				beanMap.put(beanName, bean);
			}
		}
		return beanMap;
	}
}
