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
 
   
   
		<table style="margin-top: 50px;width: 96%;" >
			<tr align="center" style="margin-top: 25px;">
				<td width="100%" align="center" style="font-size: 20px;">
					<c:if test="${sign[0].signin!=null }">该用户签到${sign[0].signin }天</c:if>
					<c:if test="${sign[0].signin==null }">该用户还未签到</c:if>
				</td>				
			</tr>
       </table>
       


<!-- 		<div class="BottomDiv" style="height: 10%;"> -->
			
<!-- 			<a href="###" class="btn_01" onclick="parent.close_win('CountWindow')">关闭<b></b></a>				 -->
<!-- 		</div> -->
  </body>
</html>
