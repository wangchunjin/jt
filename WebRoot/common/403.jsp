<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>403 - 缺少权限</title>
	<link rel="stylesheet" type="text/css"
			href='<c:url value="/webResources/js/easyui/themes/icon.css"/>' />
	<link rel="stylesheet" type="text/css" href='<c:url value="/webResources/js/easyui/themes/${curr_user.cssId}/easyui.css"/>'/>
		<script type="text/javascript"
			src='<c:url value="/webResources/js/easyui/jquery-1.4.4.min.js"/>'></script>
		<script type="text/javascript"
			src='<c:url value="/webResources/js/easyui/jquery.easyui.min.js"/>'></script>
		<script type="text/javascript" src="${ctx}/webResources/js/common.js"></script>
</head>

<body>
<div>
	<div><h1>你没有访问该页面的权限.</h1></div>
	<a href="###" class="easyui-linkbutton" onclick="parent.close_tab()" iconCls="icon-back">关闭当前页面</a>
	<a href="###" class="easyui-linkbutton" onclick="parent.parent.parent.location='${ctx}/login1.jsp'" iconCls="icon-reload">退出重新登录</a>
	
</div>
</body>
</html>