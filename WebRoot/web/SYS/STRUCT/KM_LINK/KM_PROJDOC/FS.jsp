<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
<title>KM_FOLDER维护</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<frameset name="MainFrame" id="MainFrame" noresize="noresize" scrollTopHeight="0" stTrId="" rows="28,*" cols="*" frameborder="0" border="0" bordercolor="lightgreen">
 	<frame name="OneFrame" id="OneFrame" noresize="noresize" src="${ctx}/web/SYS/STRUCT/KM_LINK/KM_PROJDOC/top.jsp" frameborder="0" scrolling="no">
 	<frameset name="RightFrame" id="RightFrame" noresize="noresize" scrollTopHeight="0" stTrId="" rows="*" cols="40%,*" frameborder="0" border="0" bordercolor="lightgreen">
		<frame name="TreeFrame" id="TreeFrame" noresize="noresize" src="${ctx}/web/SYS/STRUCT/KM_LINK/KM_PROJDOC/content.jsp?DOC_GRADE=0" frameborder="0" scrolling="no">		
		<frameset name="LeftFrame" id="LeftFrame" noresize="noresize" scrollTopHeight="0" stTrId="" rows="*,40" cols="*" frameborder="0" border="0" bordercolor="lightgreen">
				<frame name="TwoFrame" id ="TwoFrame" noresize="noresize" src="${ctx}/web/SYS/STRUCT/KM_LINK/KM_PROJDOC/PUBcontent.jsp" frameborder="0" scrolling="no">
				<frame name="bottomBar" id="bottomBar" scrolling="no" noresize="noresize" marginwidth="0"  marginheight="0" src="${ctx}/web/SYS/STRUCT/KM_LINK/KM_PROJDOC/centerTop.jsp">
		</frameset>
	</frameset>
</frameset>
</HTML>
