<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<title>SWBS</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<frameset name="MainFrame" id="MainFrame" noresize="noresize" scrollTopHeight="0" stTrId="" rows="28,25,*,1,26,200" cols="*" frameborder="0" border="0" bordercolor="lightgreen">
	<frame name="OneFrame" id="OneFrame" noresize="noresize" src="${ctx}/web/SYS/CATVAL/top.jsp" frameborder="0" scrolling="no">
	<frame name="LeftFrame" id="LeftFrame" noresize="noresize" src="${ctx}/web/SYS/CATVAL/selCatvalType.jsp?WHICH_CATVAL_TYPE=${param.WHICH_CATVAL_TYPE}" frameborder="0" scrolling="no">
	<frame name="TwoFrame" id="TwoFrame" noresize="noresize" src="${ctx}/web/SYS/CATVAL/content.jsp" frameborder="0" scrolling="no">
	<frame name="Toogle" id="Toogle" scrolling="no" noresize="noresize" marginwidth="0" marginheight="0" src="${ctx}/common/lable.bar.v.jsp">
	<frame name="ThreeFrame" id="ThreeFrame" noresize="noresize" src="${ctx}/common/label.jsp?Module=SYS_CATVAL"  frameborder="0" scrolling="no">
	<frame name="FourFrame" id="FourFrame" noresize="noresize" src=""  frameborder="0" scrolling="no">
</frameset>

</HTML>
