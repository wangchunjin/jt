<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
function initModeType(){
     SetSelect("SELECT CODE_NAME ID,CODE_NAME FROM CM_CODE WHERE CODE_TYPE ='PROJ_STAGE'  ORDER BY CODE_SHORT_NAME","proj_type","",new Array("=--请选择项目类别--"));
 }
function selProjTypeName(obj)
{
 	var src="${ctx}/web/SYS/COLUMN/content.jsp?proj_stage="+obj.value;
	parent.document.all('TwoFrame').src=src;
 
}

</script>
</head>
<body onload="initModeType()">
 
<table width="100%" border="0">
   <tr height="25" >
      <td width="99" height="25">&nbsp;项目类别：&nbsp;</td>
      <td width="300">
  	  <select   name="proj_type" id="proj_type"   onChange="selProjTypeName(this)" style="width:170px" />
 
      </td>
    
      <td style="color: red;"> 双击可修改‘字段名称’,‘排序’列对应的值，鼠标离开自动保存</td>
    </tr>
</table>
</body>
</html>