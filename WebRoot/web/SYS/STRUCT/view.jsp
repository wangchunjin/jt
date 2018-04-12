<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<title>查看文档</title>
</head>
<script type="text/javascript">
var pw="epmis"
//打开文件
function objinit(doc_url)
{
  TANGER_OCX_OBJ = document.getElementById("TANGER_OCX");
  //alert(TANGER_OCX_OBJ)
  //window.setTimeout(function(){TANGER_OCX_OBJ.OpenFromUrl(doc_url)}, 2000);
 	  TANGER_OCX_OBJ.Activate(true);
  // TANGER_OCX_OBJ.OpenFromUrl(doc_url); 
   TANGER_OCX_OBJ.BeginOpenFromURL(doc_url+"?random="+Math.round(Math.random()*100)); 
 }
 
</script>
<style>
.ocxobjdiv{
position: absolute;
bottom: 10px;
left: 0px;
right: 0px;
top:40px;
}

</style>
<body onload="objinit('${param.url}');" >
   <table width="100%" height="30" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
	<tr height="30">
	   <td align="right">&nbsp;</td>
		<td width="180"> 
			<epmis:button id="return"   imageCss="icon-cancel" value="关闭" action="parent.close_win('viewDocWindow')" />&nbsp;&nbsp;&nbsp;&nbsp;
	    </td>
	</tr>
   </table>
 <div id="ocxobject" class="ocxobjdiv">
	<script src="${ctx}/webResources/ntko/ntkoofficecontrol.min.js"></script>
</div>
   <input id="doc_grade" type="hidden" value="${param.DOC_GRADE}"/>
</body>
</html>