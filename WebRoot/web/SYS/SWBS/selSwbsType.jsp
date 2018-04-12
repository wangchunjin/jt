<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>安全管理</title>
<script type="text/javascript">
function initModeType(){
   
   var MODULE_NAME = $(parent.frameElement).attr("MODULE_NAME");
     SetSelect("SELECT SWBS_TYPE_ID ID,SWBS_TYPE_NAME NAME FROM SM_SWBSTYPE WHERE MODLENAME='"+MODULE_NAME+"' ORDER BY SWBS_TYPE_NAME","swbsTypeName","",new Array("=--请选择模板类型--"))
   
}
function selModule(obj)
{
	swbs_type_id=obj.value;
	var src="${ctx}/web/SYS/SWBS/content.jsp?swbs_type_id="+swbs_type_id;
	parent.document.all('TwoFrame').src=src;
}
function UpdateSwbsType(){
	var module_name = $(parent.frameElement).attr("MODULE_NAME");
	var currFrameId =  parent.frameElement.id; 
	 createSimpleWindow("addEpsWindow","WBS模板类型管理","${ctx}/web/SYS/SWBS/SwbsTypeInfo.jsp?module_name="+encodeURI(module_name)+"&currFrameId="+currFrameId, 800, 380);
}
</script>
</head>
<body onload="initModeType()">
 
<table width="100%" border="0">
   <tr height="25" >
      <td width="99" height="25">&nbsp;模板类型：&nbsp;</td>
      <td width="100">
  	  <select   name="swbsTypeName" id="swbsTypeName"   onChange="selModule(this)" style="width:170px" />
 
      </td>
      <td>   
&nbsp;&nbsp;&nbsp;&nbsp;
             <epmis:button id="btn1" imageCss="icon-edit" value="修改" action="UpdateSwbsType()"></epmis:button>
 
         </td>
 
    </tr>
</table>
 
</body>
<input type="hidden" name="show" value="${param.show}">
</html>