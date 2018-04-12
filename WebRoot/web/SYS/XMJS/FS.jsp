<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<title>EPS</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<frameset name="MainFrame" id="MainFrame" noresize="noresize" scrollTopHeight="0" stTrId="" rows="28,330,1,26,*" cols="*" frameborder="0" border="0" bordercolor="lightgreen">
	<frame name="OneFrame" id="OneFrame" noresize="noresize" src="${ctx}/web/SYS/XMJS/top.jsp" frameborder="0" scrolling="no">
	<frame name="TwoFrame" id="TwoFrame"  src="${ctx}/xmjs/proj/search.action" frameborder="0" scrolling="auto">
	<frame name="Toogle" id="Toogle" scrolling="no" noresizes="noresize" marginwidth="0" marginheight="0" src="${ctx}/common/lable.bar.v.jsp">
	<frame name="ThreeFrame" id="ThreeFrame" noresize="noresize" src="${ctx}/web/SYS/XMJS/label.jsp"  frameborder="0" scrolling="no">
	<frame name="FourFrame" id="FourFrame" noresize="noresize" src=""  frameborder="0" scrolling="no">
</frameset>

</HTML>
