<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<title>SWBS</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
</head>
	
<frameset name="MainFrame" id="MainFrame"  noresize="noresize" scrollTopHeight="0" stTrId="" rows="28,*" cols="*" frameborder="0" border="0" bordercolor="lightgreen">
	<frame name="OneFrame" id="OneFrame"   noresize="noresize" src="${ctx}/web/JT/CONNECT/RECORD/top.jsp?id=${param.id}"         frameborder="0" scrolling="no">
	<frame name="LeftFrame" id="LeftFrame" noresize="noresize" src="${ctx}/web/JT/CONNECT/RECORD/selCodeType.jsp?id=${param.id}" frameborder="0" scrolling="no">
	<frame name="Toogle" id="Toogle" scrolling="no" noresize="noresize" marginwidth="0" marginheight="0" src="${ctx}/common/lable.bar.v.jsp">	
</frameset>
</HTML>
