package com.xiaheng.util;

import java.util.Random;

public class MD5PassWdUtil {

	/**
	 * 传入用户输入的密码和随机唯一码进行特殊算法，算出加密后的密码
	 * 
	 * @param pwd
	 * @param UUID
	 * @return
	 */
	public static String getPwdByMd5(String pwd, String UUID) {
		String result = pwd;
		String pp = MD5.md5Hex(pwd);
		String salt = UUID;
		String ppsalt = pp + salt + pp;
		result = MD5.md5Hex(ppsalt);
		// System.out.println(result);
		return result;
	}

	/**
	 * 获取密码加密的随机唯一编码
	 * 
	 * @return 随机编码
	 */
	public static String getRandomUnique() {
		return getRandomUnique(10);
	}

	/**
	 * 获取密码加密的随机唯一编码
	 * 
	 * @return 随机编码
	 */
	public static String getRandomUnique(int num) {
		String region = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";

		return getRandomUnique(num, region);
	}

	public static String getRandomUnique(int num, String region) {

		String result = "";
		Random r1 = new Random();
		int len = 0;
		while (len++ < num) {
			result += region.toCharArray()[r1.nextInt(region.length())];
		}
		// System.out.println(result);
		return result;
	}

	public static void main(String[] args) {
		MD5PassWdUtil md5Pass = new MD5PassWdUtil();
		String RANDOM = md5Pass.getRandomUnique();// 10位随机串，RANDOM
		String TOKEN = md5Pass.getPwdByMd5("18777712220", RANDOM);// 账号 加密后的字符串，TOKEN
		System.out.println(RANDOM);
		System.out.println(TOKEN);
	}
}
