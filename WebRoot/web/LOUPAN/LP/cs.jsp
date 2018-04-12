<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'cs.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <div>
    		<table border="1" bordercolor="red">
    			<tr>
    				<td>开盘时间</td>
    				<td>售卖楼栋</td>
    				<td>交房时间</td>
    				<td>交房时间</td>
    			</tr>
    			<tr>
    				<td>2017年07月21日</td>
    				<td>8、2、9栋</td>
    				<td>2017年07月21日</td>
    				<td>第二期</td>
    			</tr>
    			
    		
    		</table>
    </div>
    		<table>
    			
    			<tr>
    				<td>2017年07月21日</td>
    				<td>8、2、9栋</td>
    				<td>2017年07月21日</td>
    				<td>第二期</td>
    			</tr>
    			
    		
    		</table>
  </body>
</html>
