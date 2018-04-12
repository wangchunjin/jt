<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
  function searchByKey(){
//   		 var rid = '${param.rid}';
  		
	 	 var client_id = $("#client_id").val();	
	 	var order_number = $("#order_number").val();	
		 var createtime = $("#createtime").val();	 	 
	  
	  TwoFrame.location.href= "${ctx}/web/JT/CONTRACT/content.jsp?client_id="+client_id+"&order_number="+order_number+"&createtime="+createtime;
  }
</script>
</head>
<body class="NewWinClass" >
<div style="width: 100%;height: 30px;">

<table width="100%" border="0">
   <tr height="25" >
      
      	<input type="hidden" class="form_text" style="background-color: white;" name="title" id="title" >
      
      <td width="75" height="25" align="right">&nbsp;用户编号：&nbsp;</td>

      <td width="100">
       <input type="" class="form_text" style="background-color: white;" name="client_id" id="client_id" >
      </td>
      
       <td width="75" height="25" align="right">&nbsp;订单编号：&nbsp;</td>

      <td width="100">
       <input type="" class="form_text" style="background-color: white;" name="order_number" id="order_number" >
      </td>
     <td width="75" height="25" align="right">&nbsp;创建时间：&nbsp;</td>

      <td width="100">
       <input class="form_text" onchange="findbycoi()" id="createtime" type="text" readonly="readonly" onclick="WdatePicker({el:'createtime'})" />
       		
      </td>
      
      
      <td>&nbsp;&nbsp;
          <epmis:button id="NewRecord" imageCss="icon-search" value="搜索" action="searchByKey()"  ></epmis:button>
         &nbsp;<epmis:button id="reload" imageCss="icon-back" value="重置" action="parent.location.reload()"></epmis:button>
         </td>
 
    </tr>
</table> 
</div>
<div style="position: absolute;top: 30px;bottom: 0;left: 0;right: 0;">
	<iframe name="TwoFrame" width="100%" height="100%" id="TwoFrame" noresize="noresize" src="${ctx}/web/JT/CONTRACT/content.jsp" frameborder="0" scrolling="no"></iframe>
</div>

</body>
</html>