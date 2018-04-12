<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
 function SetView(obj){
	var catg_type_id=  obj.value
	 parent.document.all.TwoFrame.src = "${ctx}/web/SYS/CATVAL/content.jsp?catg_type_id="+catg_type_id;
 }
 function UpdateCatvalType(){
	var WHICH_CATVAL_TYPE = '${param.WHICH_CATVAL_TYPE}';
	var currFrameId =  parent.frameElement.id; 
	 createSimpleWindow("addWindow","分类码模板类型管理","${ctx}/web/SYS/CATVAL/CatvalTypeInfo.jsp?WHICH_CATVAL_TYPE="+WHICH_CATVAL_TYPE+"&currFrameId="+currFrameId, 800, 380);
}
</script>
</head>
<body>
<table width="100%" border="0">
   <tr height="25" >
 	  <td width="100px" align="center">分类码类型：</td>
      <td width="170px">
                <epmis:select id="catg_type_id" onChange="SetView(this)" define="SELECT CATG_TYPE_ID,CATG_TYPE FROM CM_CATTYPE WHERE  WHICH_CATVAL_TYPE='${param.WHICH_CATVAL_TYPE}' ORDER BY CATG_TYPE_ID DESC"></epmis:select>
      </td>
            <td>   
&nbsp;&nbsp;&nbsp;&nbsp;
             <epmis:button id="btn1" imageCss="icon-edit" value="修改" action="UpdateCatvalType()"></epmis:button>
 
         </td>
      <td>&nbsp;</td>
    </tr>
</table>
 
</body>
<input type="hidden" name="show" value="${param.show}">
</html>