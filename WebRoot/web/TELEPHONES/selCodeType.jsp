<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
  function searchByKey(){ 	 
		 var isdel = $("#isdel").val();
		 var type=$("#type").val(); 
	  
	  TwoFrame.location.href= "${ctx}/web/TELEPHONES/content.jsp?isdel="+isdel+"&type="+type;
  }
</script>
</head>
<body class="NewWinClass" >
<div style="width: 100%;height: 30px;">

<table width="100%" border="0">
   <tr height="25" >
<!--        <td width="75" height="25" align="right">&nbsp;国别名称：&nbsp;</td> -->
<!--       	 <td width="100"><input type="" class="form_text" style="background-color: white;" name="title" id="title" > -->
<!--        </td> -->
      <td width="75" height="25" align="right">&nbsp;是否删除：&nbsp;</td>
      <td width="100">
       	<select id="isdel" class="form_text">
       		<option value="">--请选择--</option>
       		<option value="0">未删除</option>
       		<option value="1">删除</option>
       	</select>
      </td>
      <td width="75" height="25" align="right">&nbsp;服务类别：&nbsp;</td>
      <td width="100">
       	<select id="type" class="form_text">
       		<option value="">--请选择--</option>
       		<option value="1">客服电话</option>
       		<option value="2">合作电话</option>
       		<option value="3">售后电话</option>
       	</select>
      </td>      
      <input type="hidden" id="status" value=""/>     
      </td>
      <td>&nbsp;&nbsp;
          <epmis:button id="NewRecord" imageCss="icon-search" value="搜索" action="searchByKey()"  ></epmis:button>
         &nbsp;<epmis:button id="reload" imageCss="icon-back" value="重置" action="parent.location.reload()"></epmis:button>
         </td>
    </tr>
</table> 
</div>
<div style="position: absolute;top: 30px;bottom: 0;left: 0;right: 0;">
	<iframe name="TwoFrame" width="100%" height="100%" id="TwoFrame" noresize="noresize" src="${ctx}/web/TELEPHONES/content.jsp" frameborder="0" scrolling="no"></iframe>
</div>
</body>
</html>