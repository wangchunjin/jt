package com.xiaheng.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ZhuanHuan {
	
	public static DecimalFormat DF = new DecimalFormat("#.00");
	
	// String转double
	public static double get_double(String tmp) {
		double D = 0;
		if (tmp != null && !tmp.equals("")) {
			D = Double.parseDouble(tmp);
		}
		return D;
	}

	// String转float
	public static float getFLOAT(String tmp) {
		float F = 0;
		if (tmp != null && !tmp.equals("")) {
			F = Float.parseFloat(tmp);
		}

		BigDecimal b = new BigDecimal(F);
		F = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(); // 保留2位

		return F;
	}

	// String转int
	public static int getINT(String tmp) {
		int I = 0;
		if (tmp != null && !tmp.equals("")) {
			I = Integer.parseInt(tmp);
		}
		return I;
	}

	// String转long
	public static long getLong(String tmp) {
		long L = 0;
		if (tmp != null && !tmp.equals("")) {
			L = Long.parseLong(tmp);
		}
		return L;
	}

	// float保留两位有效数字
	public static float getFloatDecimal2(float tmp) {
		float F = tmp;
		BigDecimal b = new BigDecimal(F);
		F = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(); // 保留2位
		return F;
	}

	// float保留两位有效数字
	public static String getFloatDecimal2String(float tmp) {
		String s = "0.00";
		java.text.DecimalFormat df = new java.text.DecimalFormat("##0.00");
		return df.format(tmp);
	}

	// String转date
	public static Date getDate(String tmp) {
		Date date = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(tmp);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return date;
	}

	// 两个date日期天数之差
	public static Integer getTwoDateDay(Date tmp_big, Date tmp_small) {
		int day = 0;
		long time_now = tmp_big.getTime();
		long time_old = tmp_small.getTime();
		day = (int) (((time_now - time_old) / 1000) / 86400);
		return day;
	}

	// 当前日期 + - 多少天
	public static String getAddOrJianDay(int day) {
		java.util.Date now = new java.util.Date();
		java.util.Calendar cld = java.util.Calendar.getInstance();
		cld.setTime(now);
		cld.add(java.util.Calendar.DATE, day);// 日期加 day 天
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String NEWDATEStr = sdf.format(cld.getTime());
		return NEWDATEStr;
	}

	// 获取0.1-3的随机数
	public static float getRandom3() {
		double dd = Math.random() * 3 + 0.1;
		float F = Float.parseFloat(dd + "");
		BigDecimal b = new BigDecimal(F);
		F = b.setScale(1, BigDecimal.ROUND_DOWN).floatValue(); // 保留1位
		return F;
	}

	public static void main(String[] args) throws ParseException {
		System.out.println();
	}

}
