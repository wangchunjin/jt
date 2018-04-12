<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
  function searchByKey(){
  		 var uid = '${param.uid}';
  		 var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数
	 	 
		 var isdel = $("#isdel").val();
  		 var status=$("#status").val();
  		 var name=$("#name").val();
		
	 	 
	  
	  TwoFrame.location.href= "${ctx}/web/CUSER/FOOTS/GROUPLISTF/content.jsp?isdel="+isdel+"&uid="+uid+"&status="+status+"&name="+name;
  }
  

  function check(){
	  
	  
	  
  }
  
  
  
</script>
</head>
<body class="NewWinClass" >
<div style="width: 100%;height: 30px;">

<table width="100%" border="0">
   <tr height="25" >
      
      	<input type="hidden" class="form_text" style="background-color: white;" name="title" id="title" >
      
      <td width="75" height="25" align="right">&nbsp;是否删除：&nbsp;</td>
      <td width="100">
       	<select id="isdel" class="form_text">
       		<option value="">--请选择--</option>
       		<option value="0">未删除</option>
       		<option value="1">删除</option>
       	</select>
      </td>
      <td width="75" height="25" align="right">&nbsp;足迹类别：&nbsp;</td>
      <td width="100">
       	<select id="status" class="form_text" onchange="check();">
       		<option value="">--请选择--</option>
       		<option value="1">楼盘</option>
       		<option value="2">问答问题</option>
       		<option value="3">资讯</option>
       		<option value="4">户型</option>
       			
       	</select>
      </td>
      
       <td width="75" height="25" align="right">&nbsp;类别名称：&nbsp;</td>
      <td width="100">
       	<input id="name" name="name" value="" class="form_text"/>
      </td>
      
      
      <input type="hidden" id="status" value="" class="form_text"/>
    
      
      </td>
      <td>&nbsp;&nbsp;
          <epmis:button id="NewRecord" imageCss="icon-search" value="搜索" action="searchByKey()"  ></epmis:button>
         &nbsp;<epmis:button id="reload" imageCss="icon-back" value="重置" action="parent.location.reload()"></epmis:button>
         </td>
 
    </tr>
</table>
</div>
<div style="position: absolute;top: 30px;bottom: 0;left: 0;right: 0;">
	<iframe name="TwoFrame" width="100%" height="100%" id="TwoFrame" noresize="noresize" src="${ctx}/web/CUSER/FOOTS/GROUPLISTF/content.jsp?uid=${param.uid}" frameborder="0" scrolling="no"></iframe>
</div>

</body>
</html>