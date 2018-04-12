<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
function initModeType(){
     SetSelect(" SELECT CODE_type,CODE_TYPE_NAME FROM CM_CODE_TYPE WHERE ENABLED ='0'  ORDER BY CODE_TYPE_NAME","CodeTypeName","",new Array("=--选择数据词典类型--"));
    SetSelect(" SELECT CODE_type,CODE_TYPE_NAME FROM CM_CODE_TYPE WHERE ENABLED ='0'  ORDER BY CODE_TYPE_NAME","BelongtoCodeTypeName","",new Array("=--所属词典类型--"));
}
function selCodeTypeName(obj)
{
	var res = GetXmlBySql(" SELECT READONLY FROM CM_CODE_TYPE WHERE ENABLED ='0' AND CODE_TYPE='"+obj.value+"'  ORDER BY CODE_TYPE_NAME");
	if(isStringNull(res[0].READONLY)&&res[0].READONLY=='Y'){
		parent.OneFrame.document.getElementById("NewRecord").style.display = "none";
		parent.OneFrame.document.getElementById("DeleteRecord").style.display = "none";
	}else{
			parent.OneFrame.document.getElementById("NewRecord").style.display = "";
			parent.OneFrame.document.getElementById("DeleteRecord").style.display = "";
	}
		document.getElementById("BelongtoCodeTypeName").value="";
        document.getElementById("BelongtoCodeName").value="";
		var src="${ctx}/web/SYS/CODE/content.jsp?CodeType="+obj.value;
		parent.document.all('TwoFrame').src=src;
}

function selBelongtoCodeTypeName(obj){
	 SetSelect(" SELECT ID,CODE_NAME FROM CM_CODE WHERE CODE_TYPE='"+obj.value+"'  ORDER BY CODE_TYPE","BelongtoCodeName","",new Array("=--选择所属编码类型--"));	
}
function selBelongtoCodeName(obj){
    	var CodeType  = $("#CodeTypeName").val();
		 var src="${ctx}/web/SYS/CODE/content.jsp?CodeType="+CodeType+"&Belongto="+obj.value;
		 parent.document.all('TwoFrame').src=src;
}
</script>
</head>
<body onload="initModeType()">
 
<table width="100%" border="0">
   <tr height="25" >
      <td width="99" height="25">&nbsp;数据词典类型：&nbsp;</td>
      <td width="300">
  	  <select   name="CodeTypeName" id="CodeTypeName"   onChange="selCodeTypeName(this)" style="width:170px" />
 
      </td>
      <td width="99" height="25">&nbsp;所属词典类型：&nbsp;</td>
      <td width="100">
  	  <select   name="BelongtoCodeTypeName" id="BelongtoCodeTypeName"   onChange="selBelongtoCodeTypeName(this)" style="width:170px" />
      </td>
      <td width="100">
  	  <select   name="BelongtoCodeName" id="BelongtoCodeName"   onChange="selBelongtoCodeName(this)" style="width:170px" />
 
      </td>
      <td>&nbsp;</td>
    </tr>
</table>
 
</body>
<input type="hidden" name="show" value="${param.show}">
</html>