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
  		
	 	 
		 var approve_status = $("#approve_status").val();	 	
		 var transfer_status = $("#transfer_status").val();	 	
		 var mobile=$("#mobile").val();
	  
	  TwoFrame.location.href= "${ctx}/web/JT/ORDER/FLAT/content.jsp?approve_status="+approve_status+"&transfer_status="+transfer_status+"&mobile="+mobile;
  }
</script>
</head>
<body class="NewWinClass" >
<div style="width: 100%;height: 30px;">

<table width="100%" border="0">
   <tr height="25" >
      
      	<input type="hidden" class="form_text" style="background-color: white;" name="title" id="title" >
      
      <td style="display: none;" width="75" height="25" align="right">&nbsp;审核状态：&nbsp;</td>
      <td width="100" style="display: none;">
       	<select id="approve_status" class="form_text">
       		<option value="">--请选择--</option>
       		<option value="0">待审核</option>
       		<option value="1">通过</option>
       		<option value="2">失败</option>
       	</select>
      </td>
       <td width="75" height="25" align="right">&nbsp;打款状态：&nbsp;</td>
      <td width="100">
       	<select id="transfer_status" class="form_text">
       		
       		
       		<option value="1">还款中</option>
       		<option value="2">正常还款</option>
       		<option value="3">逾期</option>
       		<option value="4">逾期还款</option>
       		
       		
       	</select>
      </td>
      <td width="55" height="25" align="right">手机号：</td>
      <td width="100">       	
       		<input class="form_text" name="mobile" id="mobile"/>
       		
       
      </td>

      
      
    

      <td>&nbsp;&nbsp;
          <epmis:button id="NewRecord" imageCss="icon-search" value="搜索" action="searchByKey()"  ></epmis:button>
         &nbsp;<epmis:button id="reload" imageCss="icon-back" value="重置" action="parent.location.reload()"></epmis:button>
         </td>
 
    </tr>
</table> 
</div>
<div style="position: absolute;top: 30px;bottom: 0;left: 0;right: 0;">
	<iframe name="TwoFrame" width="100%" height="100%" id="TwoFrame" noresize="noresize" src="${ctx}/web/JT/ORDER/FLAT/content.jsp?transfer_status=1" frameborder="0" scrolling="no"></iframe>
</div>

</body>
</html>