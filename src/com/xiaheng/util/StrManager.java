/***********************************************************
 * Title:			公共模板
 * Description:		这是一个关于字符串有关操作的类
 * Copyright:		Copyright (c) 2002
 * Company:			
 * @author			2002-4-15创建
 * @version 1.0
 ***********************************************************/
package com.xiaheng.util;

public class StrManager {
	public String[] Split(String strSplit, String splitFlag) {
		/********************************************************************
		 *字符串分割函数
		 * 
		 * @param1是要被分割的字符串
		 *@param2是要分割的标识 函数返回一个分割后的字符串数组
		 *                如要将字符串"aa,aac,ccs,d"用","分割，则返回的数组是:{"aa"
		 *                ,"aac","ccs","d"}
		 **********************************************************************/
		if (strSplit == null || splitFlag == null) {
			String[] tmpSplit = new String[1];
			tmpSplit[0] = strSplit;
			return tmpSplit;
		}

		java.util.StringTokenizer st = new java.util.StringTokenizer(strSplit, splitFlag);

		int size = st.countTokens();
		String[] tmpSplit = new String[size];

		for (int i = 0; i < size; i++) {
			tmpSplit[i] = st.nextToken();
		}
		return tmpSplit;
	}

	public static String getFileExt(String fileName) {
		/************************************************
		 *传入文件名 返回文件的扩展名
		 ************************************************/
		String value = new String();
		int start = 0;
		int end = 0;
		if (fileName == null)
			return null;
		start = fileName.lastIndexOf(46) + 1;
		// 46是"."的ASCII码值，取到最后一个"."的位置
		end = fileName.length();
		value = fileName.substring(start, end);
		if (fileName.lastIndexOf(46) > 0)
			return value;
		else
			return "";
	}

	public boolean isNumeric(String strData, boolean dotFlag) {
		/**************************************************************
		 *判断字符串strData是否为数值型 dotFlag标识是否为整数，为true则函数判断strData是否为整数，为flase是否数值
		 * 返回true、false,如果是true则表示是数值，false则不是数值 如:strData =
		 * "123",不论dotFlag=true还是false函数返回true, strData = "123.1",dotFlag =
		 * true则返回false,若dotFlag=false则返回true
		 ***************************************************************/
		if (strData == null) {
			return false;
		}
		char[] numbers = strData.toCharArray();
		for (int i = 0; i < numbers.length; i++) {
			if (dotFlag) {
				if (!Character.isDigit(numbers[i]))
					return false;
			} else {
				if (!Character.isDigit(numbers[i]) && numbers[i] != '.')
					return false;
			}
		}
		if (strData.lastIndexOf(46) != strData.indexOf(46))
			return false;
		return true;
	}

	public static String changeChinese(String chnString) {
		/**********************************************************
		 *中文显示转换函数 str是要转化的字符串
		 ***********************************************************/
		String strChinese = null;
		byte[] temp;
		if (chnString == null || chnString == "") {
			return new String("");
		}
		try {
			temp = chnString.getBytes("ISO-8859-1");
			strChinese = new String(temp);
		} catch (java.io.UnsupportedEncodingException e) {
			System.out.println(e);
		}
		return strChinese;
	}

	public static String changeSybase(String chnString) {
		/**********************************************************
		 *中文显示转换函数 str是要转化的字符串
		 ***********************************************************/
		String strChinese = null;
		byte[] temp;
		if (chnString == null || chnString == "") {
			return new String("");
		}
		try {
			temp = chnString.getBytes("gb2312");
			strChinese = new String(temp);
		} catch (java.io.UnsupportedEncodingException e) {
			System.out.println(e);
		}
		return strChinese;
	}

	public static String Replace(String source, String oldString, String newString) {
		/******************* 字符串替换 ***************************/
		StringBuffer output = new StringBuffer();

		int lengthOfSource = source.length(); // 源字符串长度
		int lengthOfOld = oldString.length(); // 被替换字符串长度

		int posStart = 0; // 开始搜索位置
		int pos; // 搜索到老字符串的位置

		while ((pos = source.indexOf(oldString, posStart)) >= 0) {
			output.append(source.substring(posStart, pos));

			output.append(newString);
			posStart = pos + lengthOfOld;
		}
		if (posStart < lengthOfSource) {
			output.append(source.substring(posStart));
		}
		return output.toString();
	}

	public static String Left(String sourceString, int nLength) {
		/**********************************************************
		 *取字符串sourceString左边长为nLength子串的函数
		 **********************************************************/
		if (sourceString == null || sourceString == "" || sourceString.length() <= nLength) {
			return sourceString;
		}
		return sourceString.substring(0, nLength);
	}

	public static String Reverse(String strReverse) {
		/***************************************************
		 *将strReverse倒转，输入"abcd"返回"dcba"
		 ***************************************************/
		if (strReverse == null) {
			return strReverse;
		} else {
			StringBuffer tmpString = new StringBuffer(strReverse);

			tmpString = tmpString.reverse();

			return tmpString.toString();
		}
	}

	public static String Right(String sourceString, int nLength) {
		/**********************************************************
		 *取字符串sourceString右边长为nLength子串的函数
		 **********************************************************/
		if (sourceString == null || sourceString == "" || sourceString.length() <= nLength) {
			return sourceString;
		}
		return sourceString.substring(sourceString.length() - nLength, sourceString.length());
	}

	public static String Mid(String sourceString, int nStart, int nLength) {
		/**********************************************************
		 *取字符串sourceString从nStart开始长为nLength子串的函数 如Mid("abcd",1,3)则返回"bcd",如果
		 **********************************************************/
		try {
			if (sourceString == null || sourceString == "") {
				return sourceString;
			}
			int Length = sourceString.length();
			if (nStart > Length || nStart < 0) {
				return null;
			}
			if ((nStart + nLength) > Length)
				return sourceString.substring(nStart, Length);
			return sourceString.substring(nStart, nStart + nLength);
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public static String toSql(String str) {
		/*********** 将普通字符串格式化成数据库认可的字符串格式 **********/
		String sql = new String(str);
		return Replace(sql, "'", "''");
	}

	public static String toHtmlInput(String str) {
		/*
		 * 将字符串格式化成 HTML 代码输出 只转换特殊字符，适合于 HTML 中的表单区域
		 */
		if (str == null)
			return null;

		String html = new String(str);

		html = Replace(html, "&", "&amp;");
		html = Replace(html, "<", "&lt;");
		html = Replace(html, ">", "&gt;");

		return html;
	}

	public static String toHtml(String str) {
		/************************************************
		 * 将字符串格式化成 HTML 代码输出 除普通特殊字符外，还对空格、制表符和换行进行转换， 以将内容格式化输出， 适合于 HTML
		 * 中的显示输出
		 **********************************************/
		if (str == null) {
			return null;
		}

		String html = new String(str);

		html = toHtmlInput(html);
		html = Replace(html, "\r\n", "\n");
		html = Replace(html, "\n", "<br>");
		html = Replace(html, "\t", "    ");
		html = Replace(html, " ", " &nbsp;");

		return html;
	}

	public String notEmpty(String value) {
		/*** 使null值转换成"" */
		if (value == null) {
			value = "";
		}
		return value;
	}

	public String isSelected(String value, String selectedValue) {
		/***********************************************************
		 * 该函数用来设置列表的默认选择项, 第一个参数代表列表中列出的项的Value值, 第二个参数代表应当选择的项的Value值
		 * 如果不是要选择的项,则返回"" 如果是要选择的项,应返回" selected" 如果selectValue为"",则应默认选择第一项
		 ***********************************************************/
		if (selectedValue.trim().equals("")) {
			selectedValue = value;
		}

		if (selectedValue.trim().equals(value.trim())) {
			// 如果是应选择项
			return "selected";
		} else {
			return "";
		}
	}

	public String isChecked(String radioValue, String value) {
		/************************************************************
		 * 该函数用来设置单选按钮的选中与否,对应于radio 第一个参数代表当前单选按钮的value值,
		 * 第二个参数代表应当选中的单选按钮的Value值 如果不是要选择的项,则返回"" 如果是要选择的项,应返回" checked"
		 ***********************************************************/
		if (radioValue.trim().equals(value.trim())) {
			return " checked";
		} else {
			return "";
		}
	}

	public String isChecked(String checkboxValue, String value, String flag) {
		/*************************************************************
		 * 该函数用来设置复选按钮的选中与否,,对应于checkBox 第一个参数代表当前复选按钮的value值,
		 * 第二个参数代表以flag(第三个参数)分隔的所有应当选中的复选按钮的Value值 第三个参数代表分隔符,默认为","
		 * 如果不是要选择的项,则返回"" 如果是要选择的项,应返回" checked"
		 *************************************************************/
		if (flag.length() == 0) {
			flag = ",";
		}

		java.util.StringTokenizer st = new java.util.StringTokenizer(value.trim(), flag);
		int size = st.countTokens();
		boolean checked = false;

		for (int i = 0; i < size; i++) {
			String temp = st.nextToken();
			if (checkboxValue.trim().equals(temp.trim())) {
				return " checked";
			}
		}
		return "";
	}

	public static void main(String[] args) {
		StrManager str = new StrManager();
		System.out.println(str.notEmpty(null));
	}
}