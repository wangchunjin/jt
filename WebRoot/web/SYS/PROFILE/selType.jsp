<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
function selectType(obj)
{
		type="0";
		parent.TwoFrame.location.href="${ctx}/web/SYS/PROFILE/content.jsp";
}
</script>
</head>
<body onload="initModeType()">
 
<table width="100%" border="0">
   <tr height="25" >
      <td width="50" height="25">&nbsp; 类型:  &nbsp;</td>
      <td> 
   	<input type="radio" name="type" id="type1"  onclick="selectType(this)">全局安全配置&nbsp;&nbsp;
    	<input type="radio" name="type" id="type2" checked="checked" onclick="selectType(this)">项目安全配置

      </td>
      <td>&nbsp;</td>
    </tr>
</table>
</body>
</html>