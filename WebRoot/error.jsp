<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>找不到页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


<style>
.info_01 {
	border-right: 0px solid;
	border-top: 0px solid;
	background-image: url(webResources/images/info_01.gif);
	border-left: 0px solid;
	width: 5px;
	border-bottom: 0px solid;
	background-repeat: no-repeat;
	height: 95px;
	background-color: transparent;
}

.info_03 {
	border-right: 0px solid;
	border-top: 0px solid;
	background-image: url(webResources/images/info_03.png);
	border-left: 0px solid;
	background-repeat: no-repeat;
	width: 3px;
	border-bottom: 0px solid;
	height: 95px;
	background-color: transparent;
}

.info_02 {
	border-right: 0px solid;
	border-top: 0px solid;
	background-image: url(webResources/images/info_02.png);
	border-left: 0px solid;
	border-bottom: 0px solid;
	height: 95px;
	background-color: transparent;
}
</style>
</head>

<body>
<table width="400px" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td class="info_01"></td>
		<td class="info_02" valign="middle" align="center">
		<table>
		<tr>
			<%--<td><img src="webResources/images/error.gif" border="0"></td>
			--%><td><font color=red><b>对不起，您访问的页面有错，请联系管理员！</b></font></td>
		</tr>
		</table>
		</td>
		<td class="info_03"></td>
	</tr>
</table>
</body>
</html>