package com.xiaheng.util;

public class Str {
	// 手机号码模糊显示(只显示前三、后四)
	public static String getPhoneStr(String phone) {
		String phoneStr = "";
		if (phone.length() == 11) {
			String phone3 = phone.substring(0, 3);
			String phone4 = phone.substring(7, 11);
			phoneStr = phone3 + "****" + phone4;
		}
		return phoneStr;
	}

	// 姓名模糊显示(隐藏姓)
	public static String getNameStr(String temp) {
		String nameStr = "";
		if (temp.length() > 1) {
			String nameMing = temp.substring(1, temp.length());
			nameStr = "*" + nameMing;
		}
		return nameStr;
	}

	public static void main(String[] args) {
		Str str = new Str();
		System.out.println(562770553%2128);
	}
}
