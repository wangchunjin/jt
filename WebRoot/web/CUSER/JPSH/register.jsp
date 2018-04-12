<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    
 <script type="text/javascript">
	    
 </script>
 
 </head>
  <body class="NewWinClass">
 <div  >
   <%  
java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");  
  
java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
  
String str_date1 = formatter.format(currentTime); //将日期时间格式化  
String str_date2 = currentTime.toString(); //将Date型日期时间转换成字符串形式  
%> 

		<table style="margin-top: 15px;width: 96%;" >
		<tr>
				<td width="100%" align="center">
					<%=str_date1 %>
				</td>
				</tr>
			<tr>
				<td width="100%" align="center" style="font-size: 20px;">	
				已有${counts }人注册，
				今天共有${count }人注册
					
				</td>				
			</tr> 			
			
			
       </table>
       
</div>

<!-- 		<div class="BottomDiv" style="height: 10%;"> -->
			
<!-- 			<a href="###" class="btn_01" onclick="parent.close_win('CountWindow')">关闭<b></b></a>				 -->
<!-- 		</div> -->
  </body>
</html>
