<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<title>SWBS</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<frameset name="MainFrame" id="MainFrame" noresize="noresize" scrollTopHeight="0" stTrId="" rows="28,*" cols="*" frameborder="0" border="0" bordercolor="lightgreen">
	<frame name="OneFrame" id="OneFrame" noresize="noresize" src="${ctx}/web/SYS/USER/QXJS/top.jsp?USER_ID=${param.USER_ID}&PROFILE_ID=${param.PROFILE_ID}" frameborder="0" scrolling="no">
<!-- 	<frame name="TwoFrame" id="TwoFrame" noresize="noresize" src="${ctx}/web/SYS/USER/PROJFILE/content.jsp?USER_ID=${param.USER_ID}&PROFILE_ID=${param.PROFILE_ID}" frameborder="0" scrolling="no"> -->
</frameset>

</HTML>
