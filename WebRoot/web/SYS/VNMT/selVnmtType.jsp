<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
 function SetView(obj){
	var catgTypeId = obj.value;
	var src;
	if(catgTypeId=="Full")
	{
		src = "${ctx}/web/SYS/VNMT/content.jsp";
		parent.OneFrame.document.getElementById("NewRecord").style.display ="";
		parent.OneFrame.document.getElementById("DeleteRecord").style.display ="";
	}else
	{
		src = "${ctx}/web/SYS/VNMT/content_catval.jsp?CATG_TYPE_ID=" + catgTypeId;
		parent.OneFrame.document.getElementById("NewRecord").style.display ="none";
		parent.OneFrame.document.getElementById("DeleteRecord").style.display ="none";
	}
	parent.document.all('TwoFrame').src = src;
 }
function initModeType(){
   	var options = new Array();
   	options[0] = "Full=默认视图";
     SetSelect(" SELECT CATG_TYPE_ID,CONCAT(CATG_TYPE,'(分类码)') AS NAME FROM CM_CATTYPE WHERE  WHICH_CATVAL_TYPE='SYS_DWFLM'  ORDER BY CATG_TYPE","type","",options)   
}
</script>
</head>
<body onload="initModeType()">
<table width="100%" border="0">
   <tr height="25" >
 	  <td width="80px" align="center">视图：</td>
      <td width="170px">
		<select id="type" class="select" name="type" onchange="SetView(this)">
		</select> 
      </td>
      <td>&nbsp;</td>
    </tr>
</table>
 
</body>
<input type="hidden" name="show" value="${param.show}">
</html>