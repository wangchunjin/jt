<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
  function searchByKey(){
  		 
  		 
	  var start_time =$("#start").val();
	  var end_time =$("#end").val();
	 
	  
	  TwoFrame.location.href= "${ctx}/web/JT/CUISHOU/CYRANKINGS/SHOW/content.jsp?start_time="+start_time+"&end_time="+end_time;
	  
  }
</script>
</head>
<body class="NewWinClass" >
<div style="width: 100%;height: 30px;">

<table width="100%" border="0">
   <tr height="25" >

      <td width="100" height="25" align="right">开始时间：</td>
      <td width="100">       	
       		<input class="form_text" onchange="findbycoi()" id="start" type="text" readonly="readonly" onclick="WdatePicker({el:'start'})" />
       		
       
      </td>
	  <td width="100" height="25" align="right">结束时间：</td>
      <td width="100">
       <input class="form_text" onchange="findbycoi()" id="end" type="text" readonly="readonly" onclick="WdatePicker({el:'end'})" />
      </td>
      
    
      
      </td>
      <td>&nbsp;&nbsp;
          <epmis:button id="NewRecord" imageCss="icon-search" value="搜索" action="searchByKey()"  ></epmis:button>
         &nbsp;<epmis:button id="reload" imageCss="icon-back" value="重置" action="parent.location.reload()"></epmis:button>
         </td> 
    </tr>
</table> 
</div>
<div style="position: absolute;top: 30px;bottom: 0;left: 0;right: 0;">
	<iframe name="TwoFrame" width="100%" height="100%" id="TwoFrame" noresize="noresize" src="${ctx}/web/JT/CUISHOU/CYRANKINGS/SHOW/content.jsp" frameborder="0" scrolling="no"></iframe>
</div>
</body>
</html>