<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<title>用户增加页面</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<frameset name="MainFrame" id="MainFrame" noresize="noresize" scrollTopHeight="0" stTrId="" rows="*" cols="25%,*" frameborder="0" border="0" bordercolor="lightgreen">
	<frame name="OneFrame" id="OneFrame" noresize="noresize" src="${ctx}/web/SYS/PROJ/ADD/Navigation.jsp?currFrameId=${param.currFrameId}" frameborder="0" style="border-right: 1px solid #D3D3D3;" scrolling="no">
	<frame name="TwoFrame" id="TwoFrame" noresize="noresize" src="${ctx}/web/SYS/PROJ/ADD/AddProj.jsp?ParentProjId=${param.ParentProjId}" frameborder="0" scrolling="yes">
</frameset>
</HTML>
