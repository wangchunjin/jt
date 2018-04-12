<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<html:base />
<head>
<title>数据词典</title>
<style>
</style> 
<script language=javascript>
</script>
</head>
<body>
	<div width="100%" style="font-size: 20px;text-align:center;font-weight: bold;margin-top:20px;margin-bottom: 20px;">安吉通表结构说明</div>
	
	<c:if test="${not empty tableData }">
 		<table width="50%" border="1" style="" align="center" cellspacing="0" cellpadding="0">
		 	<c:forEach items="${tableData }" var="tableData">
		 		<tr style="background: #B8B8B8;">
		 			<td width="10%">表名</td>
		 			<td width="90%" colspan="3">${tableData.TABLE_NAME }</td>
		 		</tr>
		 		
	 			<c:if test="${not empty  tableData.tableColumns}">
	 				<c:forEach items="${tableData.tableColumns }" var="columns">
		 				<tr>
		 					<td>字段名</td>
		 					<td>${columns.COLUMN_NAME }</td>
		 					<td>注释</td>
		 					<td>${columns.COLUMN_COMMENT }</td>
		 				</tr>
	 				</c:forEach>
	 			</c:if>
		 	</c:forEach>
 		</table>
	 </c:if>
	
 
</body>
</html:html>