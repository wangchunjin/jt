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
		 var isdel ="";
		 var shen=$("#shen").val();
		 var shi=$("#shi").val();
	  
	  TwoFrame.location.href= "${ctx}/web/NAIRES/content.jsp?isdel="+isdel+"&shen="+shen+"&shi="+shi;
  }
  
  function changeValue1(){
   	var obj=$("#shen").val();
   		SetSelect("SELECT cityId,cityName FROM T_CITY WHERE provinceId="+obj,"shi","",new Array("=--请选择--")); 
   		
	}
</script>
</head>
<body class="NewWinClass" >
<div style="width: 100%;height: 30px;">

<table width="100%" border="0">
   <tr height="25" >
      
      	<input type="hidden" class="form_text" style="background-color: white;" name="title" id="title" >
      
<!--       <td width="75" height="25" align="right">&nbsp;是否删除：&nbsp;</td> -->
<!--       <td width="100"> -->
<!--        	<select id="isdel" class="form_text"> -->
<!--        		<option value="">--请选择--</option> -->
<!--        		<option value="0">未删除</option> -->
<!--        		<option value="1">删除</option> -->
<!--        	</select> -->
<!--       </td> -->

      <td width="35" height="25" align="right">省：</td>
      <td width="100" align="left">
       <epmis:select id="shen" define="SELECT provinceId,provinceName FROM T_province" attr="style='width: 100%;'" onChange="changeValue1()" ></epmis:select>
      </td>      
      <td width="35" height="25" align="right">市：</td>
      <td width="100" align="left">
       <epmis:select id="shi" define="" attr="style='width: 100%;'" onChange="changeValue2()"></epmis:select>
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
	<iframe name="TwoFrame" width="100%" height="100%" id="TwoFrame" noresize="noresize" src="${ctx}/web/NAIRES/content.jsp" frameborder="0" scrolling="no"></iframe>
</div>

</body>
</html>