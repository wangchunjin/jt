<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html:html>
<html:base />
<head>
<title>文件上传</title>
<style>
</style> 
<script language=javascript>
</script>
</head>
<body>
	<form action="/jcy/api/fileUpload/upload.do" method="post" action="" id="labelForm" name="labelForm" enctype="multipart/form-data">
	 	<input type="file" name="aaa" id="aaa">
	 	<input type="submit">
	</form>
</body>
</html:html>