<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<title>SWBS</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
</head>
	
<frameset name="MainFrame" id="MainFrame"  noresize="noresize" scrollTopHeight="0" stTrId="" rows="*" cols="*" frameborder="0" border="0" bordercolor="lightgreen">
<!-- 	<frame name="OneFrame" id="OneFrame"   noresize="noresize" src="${ctx}/web/JT/CUISHOU/YG/SHOW/top.jsp"         frameborder="0" scrolling="no"> -->
	<frame name="LeftFrame" id="LeftFrame" noresize="noresize" src="${ctx}/web/JT/CUISHOU/YG/SHOW/selCodeType.jsp?id=${param.id}&cid=${param.cid}&currFrameId=${param.currFrameId}" frameborder="0" scrolling="no">

	<frame name="FourFrame" id="FourFrame" noresize="noresize" src=""  frameborder="0" scrolling="no">
</frameset>
</HTML>
