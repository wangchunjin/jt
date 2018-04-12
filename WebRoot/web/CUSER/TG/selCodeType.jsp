<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
  function searchByKey(){
	  var title= $("#title").val();
	  var isdel = $("#isdel").val();
	  var status = $("#status").val();
	  var uid=$("#uid").val();
	  TwoFrame.location.href= "${ctx}/web/CUSER/USER/content.jsp?title="+encodeURI(title)+"&isdel="+isdel+"&status="+status+"&uid="+uid;
  }
</script>
</head>
<body class="NewWinClass" >
<div style="width: 100%;height: 30px;">

<table width="100%" border="0">
   <tr height="25" >
      <td width="75" height="25" align="right">&nbsp;手机号：&nbsp;</td>
      <td width="100">
      	<input class="form_text" style="background-color: white;" name="title" id="title" >
      </td>
      <td width="75" height="25" align="right">&nbsp;用户编号：&nbsp;</td>
      <td width="100">
      	<input class="form_text" style="background-color: white;" name="uid" id="uid" >
      </td>
      <td width="75" height="25" align="right">&nbsp;是否删除：&nbsp;</td>
      <td width="100">
       	<select id="isdel" class="form_text">
       		<option value="">--请选择--</option>
       		<option value="0">未删除</option>
       		<option value="1">删除</option>
       	</select>
      </td>
	
      
      </td>
      <td>&nbsp;&nbsp;
          <epmis:button id="NewRecord" imageCss="icon-search" value="搜索" action="searchByKey()"  ></epmis:button>
         &nbsp;<epmis:button id="reload" imageCss="icon-back" value="重置" action="parent.location.reload()"></epmis:button>
         </td>
 
    </tr>
</table> 
</div>
<div style="position: absolute;top: 0px;bottom: 0;left: 0;right: 0;">
	<iframe name="TwoFrame" width="100%" height="100%" id="TwoFrame" noresize="noresize" src="${ctx}/web/CUSER/TG/content.jsp?id=${param.id}" frameborder="0" scrolling="no"></iframe>
</div>
</body>
</html>