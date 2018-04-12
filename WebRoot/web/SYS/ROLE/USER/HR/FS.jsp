<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<title>KM_FOLDER维护</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<frameset name="MainFrame" id="MainFrame" noresize="noresize" scrollTopHeight="0" stTrId="" rows="*" cols="40%,*" frameborder="0" border="0" bordercolor="lightgreen">
   	<frame name="TreeFrame" id="TreeFrame" noresize="noresize" src="${ctx }/web/SYS/ROLE/USER/HR//deptTree.jsp" frameborder="0" scrolling="no">		
	<frame name="TwoFrame" id ="TwoFrame" noresize="noresize" src="${ctx }/web/SYS/ROLE/USER/HR//content.jsp?roleId=${param.roleId}&currFrameId=${param.currFrameId}" frameborder="0" scrolling="no">
</frameset>
</HTML>
