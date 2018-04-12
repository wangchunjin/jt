package com.xiaheng.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class sql_inserj {
	

	public boolean sql_inserj(String str) {//判断 从str的 哪个位置有inj_str 里设置的 字符。
		String inj_str = "and|exec|insert|select|delete|update|count|<script|<img|javascript|alert|chr|mid|master|truncate|char|declare|or|http|herf|www|com|net|cn";
		if (str != null && !str.equals("")) {
			String[] inj_stra = inj_str.split("\\|");
			for (int i = 0; i < inj_stra.length; i++) {
				if (str.toUpperCase().indexOf(inj_stra[i].toUpperCase()) >= 0) {
					System.out.println("发现注入参数值：" + inj_stra[i]);				
					return true;
				}
			}
		}
		return false;//还要先判断是否为空
	}

	// url参数防注入验证,返回false验证通过
	public boolean urlParametersInserj(HttpServletRequest request, int maxParametersLength) {
		String parameters = request.getQueryString();// 获取URL?后的参数
		if (parameters != null && !parameters.equals("")) {
			// 参数长度验证
			if (parameters.length() > maxParametersLength) {
				System.out.println("URL参数及参数值太长：" + parameters.length());
				return true;
			}
			// 参数值验证
			Map<String, String> parametersMap = getParametersMap(parameters);// 参数转成键值对
			if (parametersMap != null && parametersMap.size() > 0) {
				sql_inserj inj = new sql_inserj();
				for (String key : parametersMap.keySet()) {
					String mapValueString = parametersMap.get(key);
					if (mapValueString != null && !mapValueString.equals("")) {
						if (inj.sql_inserj(mapValueString) == true) {
							System.out.println("发现注入参数：参数名：" + key + "，参数值：" + mapValueString);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 解析出url参数中的键值对 如 "Action=del&id=123"，解析出Action:del,id:123存入map中
	 */
	public Map<String, String> getParametersMap(String parameters) {
		Map<String, String> mapRequest = new HashMap<String, String>();
		String[] arrSplit = parameters.split("&");// 每个键值为一组
		for (int i = 0; i < arrSplit.length; i++) {
			String paraStr2 = arrSplit[i];
			if (paraStr2 != null && !paraStr2.equals("") && paraStr2.length() > 0) {
				// 解析出键值,第一 "=" 号前、后分别位键、值
				String mapName = null;
				String mapValue = "";
				if (paraStr2.indexOf("=") >= 0) {
					mapName = paraStr2.substring(0, paraStr2.indexOf("="));
					mapValue = paraStr2.substring(paraStr2.indexOf("=") + 1, paraStr2.length());
				} else {
					mapName = paraStr2;
				}
				if (mapName != null && !mapName.equals("")) {
					mapRequest.put(mapName, mapValue);
				}
			}
		}
		return mapRequest;
	}

	// 获取网站basePath访问路径http://192.168.0.111:80/zhongkexiche/
	public String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		return basePath;
	}

	// 正则验证正整数,返回false验证通过
	public boolean checkPositiveInteger(String... str) {
		String regex = "^\\+?[1-9][0-9]*$";
		if (str == null || str.length <= 0) {
			return true;
		}
		for (int i = 0; i < str.length; i++) {
			if (str[i] == null || "".equals(str[i])) {
				return true;
			}
			boolean flag = match(regex, str[i]);
			if (!flag) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public static void main(String[] args) {
		sql_inserj inj = new sql_inserj();
		boolean a = inj.checkPositiveInteger("465", "", "1");
		System.out.println(a);
	}
}
