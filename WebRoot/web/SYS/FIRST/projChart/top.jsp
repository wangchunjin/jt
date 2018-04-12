<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
  </head>
  <script type="text/javascript">
  		function openw4(){
  			parent.TwoFrame.openw4();
  		}
  </script>
  <body>
  <table width="100%" style="background: #ffffff">
       <tr>
       	  <td id="showProj">
       	  	&nbsp;
	 		<span id="isShow">
		 		<input id="showundo1" type="checkbox" onclick="openw4();" value="未完工" checked/>未完工项目
				<input id="showdo1" type="checkbox" onclick="openw4();" value="已完工" />已完工项目
			</span>
	      </td>
          <td width="*" align="right">
	         <epmis:button id="cancelPublish" imageCss="icon-reload" value="刷新" action="parent.window.location.reload();" datactrSql=""  datactrCode="" ></epmis:button>
          </td>
       </tr>
  </table>
  </body>
</html>
