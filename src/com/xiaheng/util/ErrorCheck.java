/**
 * Title:			公共模板
 * Description:
 * Copyright:		Copyright (c) 2002
 * Company:      
 * @author	xhm		
 * ErrorCheck v 1.0
 */
package com.xiaheng.util;


public class ErrorCheck {

	/* public: the javascript string */
	String errorCheckStr;
	/* public: the form name you used */
	public String formName;

	public void setFormName(String formName) {
		this.formName = formName;
	}

	/***************************************************************************
	 * \ public: constructor functions 构造函数 \
	 ***************************************************************************/
	public ErrorCheck() {
		this.errorCheckStr = "<script ID=clientEventHandlersJS language=javascript>" + "\n" + "<!--" + "\n";
		this.neededFunction(); // load the needed functions
		this.errorCheckStr += "function errorCheck() {" + "\n";
	}

	/***************************************************************************
	 * \ public: export javascript script 输出 JAVASCRIPT 脚本 \
	 ***************************************************************************/
	public String ErrorCheckScript() {
		this.errorCheckStr += "}" + "\n" + "-->" + "\n" + "</script>" + "\n";
		return this.errorCheckStr;
	}

	/***************************************************************************
	 * \ 判断是否保存 \
	 ***************************************************************************/
	public String isSubmit() {
		this.errorCheckStr += "submit = window.confirm(\"确认要修改吗？\");" + "\n" + "if (submit == true){" + "\n" + "}" + "\n" + "else{   " + "\n" + "event.returnValue = false;" + "   return false;" + "\n" + "}" + "\n";
		return this.errorCheckStr;
	}

	/***************************************************************************
	 * \ public: check the numeric 检查录入框值是否是数字(包括为空) \
	 ***************************************************************************/
	public void numericCheck(String inputName, String errorMsg) {
		this.errorCheckStr += "  if(fucCheckNUM(document." + formName + "." + inputName + ".value) == 0) {" + "\n" + "    alert(\"" + errorMsg + ".\");" + "\n" + "    document." + formName + "." + inputName + ".focus();" + "\n" + "event.returnValue = false;" + "    return(false);" + "\n" + "  }" + "\n\n";
	}

	/***************************************************************************
	 * \ public: check the numeric 检查录入框是否为空 \
	 ***************************************************************************/
	public void isValue(String inputName, String errorMsg) {
		this.errorCheckStr += "  if(isValue_temp(document." + formName + "." + inputName + ".value) == 0) {" + "\n" + "    alert(\"" + errorMsg + ".\");" + "\n" + "    document." + formName + "." + inputName + ".focus();" + "\n" + "event.returnValue = false;" + "    return(false);" + "\n" + "  }" + "\n\n";
	}

	/***************************************************************************
	 * \ public: check the numeric 检查录入框值是否是数字(不包括为空) \
	 ***************************************************************************/
	public void isNumeric(String inputName, String errorMsg) {
		this.errorCheckStr += "  if(checkNum(document." + formName + "." + inputName + ".value) == 0) {" + "\n" + "    alert(\"" + errorMsg + ".\");" + "\n" + "    document." + formName + "." + inputName + ".focus();" + "\n" + "event.returnValue = false;" + "    return(false);" + "\n" + "  }" + "\n\n";
	}

	/***************************************************************************
	 * \ public: check the length 检查录入框值的长度 \
	 ***************************************************************************/
	public void lengthCheck(String inputName, String errorMsg, int MinLength, int MaxLength) {
		this.errorCheckStr += "  if(fucCheckLength(document." + formName + "." + inputName + ".value)<" + MinLength + "||" + "\n" + "    fucCheckLength(document." + formName + "." + inputName + ".value)>" + MaxLength + ") {" + "\n" + "    alert(\"" + errorMsg + ".\");" + "\n" + "    document." + formName + "." + inputName + ".focus();" + "\n" + "event.returnValue = false;" + "    return(false);"
				+ "\n" + "  }" + "\n\n";
	}

	/***************************************************************************
	 * \ public: check the email 检查录入框值是否是正确的EMAIL格式 \
	 ***************************************************************************/
	public void emailCheck(String inputName, String errorMsg, boolean isEmpty) {
		if (isEmpty == true) {// 允许为空
			this.errorCheckStr += "  if(chkemail_true(document." + formName + "." + inputName + ".value) == 0) {" + "\n" + "    alert(\"" + errorMsg + ".\");" + "\n" + "    document." + formName + "." + inputName + ".focus();" + "\n" + "event.returnValue = false;" + "    return(false);" + "\n" + "  }" + "\n\n";
		} else {
			this.errorCheckStr += "  if(chkemail_false(document." + formName + "." + inputName + ".value) == 0) {" + "\n" + "    alert(\"" + errorMsg + ".\");" + "\n" + "    document." + formName + "." + inputName + ".focus();" + "\n" + "event.returnValue = false;" + "    return(false);" + "\n" + "  }" + "\n\n";
		}
	}

	/***************************************************************************
	 * \ public: check the telephone number 检查录入框值是否是电话号码 \
	 ***************************************************************************/
	public void telCheck(String inputName, String errorMsg, boolean isEmpty) {
		if (isEmpty == true) {// 允许为空
			this.errorCheckStr += "  if(document." + formName + "." + inputName + ".value == \"\"){return true};\n";
		} else {
			this.errorCheckStr += "  if(document." + formName + "." + inputName + ".value == \"\"){return false};\n";
		}

		this.errorCheckStr += "  if(fucCheckTEL(document." + formName + "." + inputName + ".value) == 0) {" + "\n" + "    alert(\"" + errorMsg + ".\");" + "\n" + "    document." + formName + "." + inputName + ".focus();" + "\n" + "event.returnValue = false;" + "    return(false);" + "\n" + "  }" + "\n\n";
	}

	/***************************************************************************
	 * \ public: check if the input value contian the prefered string
	 * 检查录入框值是否是包含给定字串 \
	 ***************************************************************************/
	public void stringCheck(String inputName, String errorMsg, String string) {
		this.errorCheckStr += "  if(document." + formName + "." + inputName + ".value.indexOf(\"" + string + "\") != 0) {" + "\n" + "    alert(\"" + errorMsg + ".\");" + "\n" + "    document." + formName + "." + inputName + ".focus();" + "\n" + "event.returnValue = false;" + "    return(false);" + "\n" + "  }" + "\n\n";
	}

	/***************************************************************************
	 * \ public: check if the input value contain the denyed string
	 * 检查录入框值是否是包含给禁止的字串 \
	 ***************************************************************************/
	public void denyStrCheck(String inputName, String errorMsg, String string) {
		this.errorCheckStr += "  if (document." + formName + "." + inputName + ".value.length == 0 ||" + "\n" + "    document." + formName + "." + inputName + ".value.indexOf(\"" + string + "\") != -1) {" + "\n" + "    alert(\"" + errorMsg + ".\");" + "\n" + "    document." + formName + "." + inputName + ".focus();" + "\n" + "event.returnValue = false;" + "    return(false);" + "\n" + "  }"
				+ "\n\n";
	}

	/***************************************************************************
	 * \ public: check the YYYY-MM-DD format date 检查录入框值是否是YYYY-MM-DD的日期格式 \
	 ***************************************************************************/
	public void dateCheck(String inputName, String errorMsg, boolean isEmpty) {
		int empty = 0;
		if (isEmpty)
			empty = 1;// 表示可以为空
		this.errorCheckStr += "  if(chkdate(document." + formName + "." + inputName + ".value, " + empty + ") == 0) {" + "\n" + "    alert(\"" + errorMsg + ".\");" + "\n" + "    document." + formName + "." + inputName + ".focus();" + "\n" + "event.returnValue = false;" + "    return(false);" + "\n" + "  }" + "\n\n";
	}

	public void neededFunction() {
		this.errorCheckStr += "//函数名：fucCheckNUM" + "\n" + "//功能介绍：检查是否为数字" + "\n" + "//参数说明：要检查的数字" + "\n" + "//返回值：1为是数字，0为不是数字" + "\n" + "function fucCheckNUM(NUM) {" + "\n" + "  var i,j,strTemp;" + "\n" + "  strTemp=\"0123456789\";" + "\n" + "  if ( NUM.length == 0) return 0;" + "\n" + "  for (i=0;i<NUM.length;i++)    {" + "\n" + "    j = strTemp.indexOf(NUM.charAt(i));" + "\n"
				+ "    if (j==-1) {" + "\n" + "      //说明有字符不是数字" + "\n" + "      return 0;" + "\n" + "    }" + "\n" + "  }" + "\n" + "  //说明是数字" + "\n" + "  return 1;" + "\n" + "}" + "\n\n" +

				"//函数名：isValue_temp" + "\n" + "//功能介绍：检查是否为空" + "\n" + "//参数说明：要检查的数字" + "\n" + "//返回值：1为不为空，0为空" + "\n" + "function isValue_temp(NUM) {" + "\n" + "  if (NUM.length==0) return 0;" + "\n" + "  if(NUM.length!=0) return 1;" + "\n" + "}" + "\n\n" +

				"//函数名(不包括为空)：checkNUM" + "\n" + "//功能介绍：检查是否为数字" + "\n" + "//参数说明：要检查的数字" + "\n" + "//返回值：1为是数字，0为不是数字" + "\n" + "function checkNum(NUM) {" + "\n" + "  var i,j,strTemp;" + "\n" + "  strTemp=\"0123456789\";" + "\n" + "  if(NUM.length==0) return 1;" + "\n" + "  else{" + "\n" + "  for (i=0;i<NUM.length;i++)    {" + "\n" + "    j = strTemp.indexOf(NUM.charAt(i));" + "\n"
				+ "    if (j==-1) {" + "\n" + "      //说明有字符不是数字" + "\n" + "      return 0;" + "\n" + "    }" + "\n" + "  }" + "\n" + "  //说明是数字" + "\n" + "  return 1;}" + "\n" + "}" + "\n\n" +

				"//函数名：fucCheckLength" + "\n" + "//功能介绍：检查字符串的长度" + "\n" + "//参数说明：要检查的字符串" + "\n" + "//返回值：长度值" + "\n" + "function fucCheckLength(strTemp) {" + "\n" + "  var i,sum;" + "\n" + "  sum=0;" + "\n" + "  for(i=0;i<strTemp.length;i++) {" + "\n" + "    if ((strTemp.charCodeAt(i)>=0)&&(strTemp.charCodeAt(i)<=255))" + "\n" + "      sum=sum+1;" + "\n" + "    else" + "\n"
				+ "      sum=sum+2;" + "\n" + "  }" + "\n" + "  return sum;" + "\n" + "}" + "\n\n" +

				"//函数名：chkemail_false" + "\n" + "//功能介绍：检查是否为Email Address" + "\n" + "//参数说明：要检查的字符串" + "\n" + "//返回值：0：不是  1：是" + "\n" + "function chkemail_false(a)    {" + "\n" + "  var i=a.length;" + "\n" + "  var temp = a.indexOf('@');" + "\n" + "  var tempd = a.indexOf('.');" + "\n" + "  if (temp>1) {" + "\n" + "    if ((i-temp)>3) {" + "\n" + "      if (tempd!=-1) {" + "\n"
				+ "        return 1;" + "\n" + "      }" + "\n" + "    }" + "\n" + "  }" + "\n" + "  return 0;" + "\n" + "}" + "\n\n" +

				"//函数名：chkemail_true" + "\n" + "//功能介绍：检查是否为Email Address" + "\n" + "//参数说明：要检查的字符串" + "\n" + "//返回值：0：不是  1：是" + "\n" + "function chkemail_true(a)    {" + "\n" + "  var i=a.length;" + "\n" + "  var temp = a.indexOf('@');" + "\n" + "  var tempd = a.indexOf('.');" + "\n" + "  if(i==0){ return 1;}" + "  if (temp>1) {" + "\n" + "    if ((i-temp)>3) {" + "\n" + "      if (tempd=-1) {"
				+ "\n" + "        return 1;" + "\n" + "      }" + "\n" + "    }" + "\n" + "  }" + "\n" + "  return 0;" + "\n" + "}" + "\n\n" +

				"//函数名：fucCheckTEL" + "\n" + "//功能介绍：检查是否为电话号码" + "\n" + "//参数说明：要检查的字符串" + "\n" + "//返回值：1为是合法，0为不合法" + "\n" + "function fucCheckTEL(TEL) {" + "\n" + "  var i,j,strTemp;" + "\n" + "  strTemp=\"0123456789-()#\";" + "\n" + "  if (TEL.length == 0) return 0;" + "\n" + "  for (i=0;i<TEL.length;i++) {" + "\n" + "    j=strTemp.indexOf(TEL.charAt(i));" + "\n" + "    if (j==-1) {" + "\n"
				+ "      //说明有字符不合法" + "\n" + "      return 0;" + "\n" + "    }" + "\n" + "  }" + "\n" + "  //说明合法" + "\n" + "  return 1;" + "\n" + "}" + "\n\n" +

				"//函数名：chkdate    (YYYY-MM-DD)" + "\n" + "//功能介绍：检查是否为日期" + "\n" + "//参数说明：要检查的字符串" + "\n" + "//返回值：0：不是日期  1：是日期" + "\n" + "function chkdate(datestr, intEmpty) {" + "\n" + "  var lthdatestr" + "\n" + "  if (datestr != \"\")" + "\n" + "    lthdatestr= datestr.length;" + "\n" + "  else" + "\n" + "    lthdatestr=0;" + "\n" + "  if (intEmpty == 0 && lthdatestr == 0)\n"
				+ "    return 0;\n" + "  else if (intEmpty == 1 && lthdatestr == 0)\n" + "    return 1;\n" + "  var tmpy=\"\";" + "\n" + "  var tmpm=\"\";" + "\n" + "  var tmpd=\"\";" + "\n" + "  //var datestr;" + "\n" + "  var status;" + "\n" + "  status=0;" + "\n" + "  for (i=0;i<lthdatestr;i++) {" + "\n" + "    if (datestr.charAt(i)== '-') {" + "\n" + "      status++;" + "\n" + "    }" + "\n"
				+ "    if (status>2) {" + "\n" + "      return 0;" + "\n" + "    }" + "\n" + "    if ((status==0)&&(datestr.charAt(i)!='-')) {" + "\n" + "      tmpy=tmpy+datestr.charAt(i)" + "\n" + "    }" + "\n" + "    if ((status==1)&&(datestr.charAt(i)!='-')) {" + "\n" + "      tmpm=tmpm+datestr.charAt(i)" + "\n" + "    }" + "\n" + "    if ((status==2)&&(datestr.charAt(i)!='-')) {" + "\n"
				+ "      tmpd=tmpd+datestr.charAt(i)" + "\n" + "    }" + "\n" + "  }" + "\n" + "  if (status == 0){" + "     tmpy = \"\";\n" + "     tmpm = \"\";\n" + "     tmpd = \"\";\n" + "     for (i=0;i<lthdatestr;i++) {" + "\n" + "         if (datestr.charAt(i)== '/') {" + "\n" + "             status++;" + "\n" + "         }" + "\n" + "         if (status>2) {" + "\n"
				+ "             return 0;" + "\n" + "         }" + "\n" + "         if ((status==0)&&(datestr.charAt(i)!='/')) {" + "\n" + "             tmpy=tmpy+datestr.charAt(i)" + "\n" + "         }" + "\n" + "         if ((status==1)&&(datestr.charAt(i)!='/')) {" + "\n" + "             tmpm=tmpm+datestr.charAt(i)" + "\n" + "         }" + "\n"
				+ "         if ((status==2)&&(datestr.charAt(i)!='/')) {" + "\n" + "             tmpd=tmpd+datestr.charAt(i)" + "\n" + "         }" + "\n" + "     }" + "\n" + "  }" + "\n" + "  year=new String (tmpy);" + "\n" + "  month=new String (tmpm);" + "\n" + "  day=new String (tmpd)" + "\n" + "  if ((tmpy.length!=4) || (tmpm.length>2) || (tmpd.length>2)) {" + "\n" + "    return 0;" + "\n"
				+ "  }" + "\n" + "  if (!((1900 < year) && (10000 > year)&&(1<=month)&&(12>=month)&&(31>=day)&&(1<=day)) ) {" + "\n" + "    return 0;" + "\n" + "  }" + "\n" + "  if (!((year % 4)==0)&&(month==2)&&(day==29)) {" + "\n" + "    return 0;" + "\n" + "  }" + "\n" + "  if ((month<=7)&&((month % 2)==0)&&(day>=31)) {" + "\n" + "    return 0;" + "\n" + "  }" + "\n"
				+ "  if ((month>=8)&&((month % 2)==1)&&(day>=31)) {" + "\n" + "    return 0;" + "\n" + "  }" + "\n" + "  if ((month==2)&&(day==30)) {" + "\n" + "    return 0;" + "\n" + "  }" + "\n" + "  return 1;" + "\n" + "}" + "\n\n";
	}
}
