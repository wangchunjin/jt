
<%@page import="com.opensymphony.xwork2.util.ValueStack;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" type="text/css"
			href='<c:url value="/webResources/themes/${curr_user.cssId}/main.css" />' />
<style type="text/css">
.error {
	color: fuchsia;
	font: 20px "微软雅黑", "宋体";
}
</style>

<script type="text/javascript">


    function hideMessages() {
        if (document.getElementById("errorMessages") != null) {
            document.getElementById("errorMessages").style.display = 'none';
        }
    }

    function timeOutMessage() {
        window.setTimeout("hideMessages()", 20000);
    }

    function clearMessages() {
        document.getElementById("myValidateMessages").innerHTML = '';
        document.getElementById("myValidateImg").style.display = "none";
    }
</script>

<%-- Error Messages (from session on JSPs, not through Struts --%>
<c:if test="${not empty errorsMessages}">
	<div class="error" id="errorMessages" >
		<c:forEach var="error" items="${errorsMessages}">
			<c:if test="${error.errorId eq 'EDP001' }">
				<img src='<c:url value="/webResources/images/error.gif"/>' /> 数据库访问异常：
			</c:if>
			<c:if test="${error.errorId eq 'EDP002' }">
				<img src='<c:url value="/webResources/images/error.PNG"/>' /> 业务逻辑异常：
			</c:if>
			<c:if test="${error.errorId eq 'EDP003' }">
				<img src='<c:url value="/webResources/images/user.gif"/>' /> 系统信息：
			</c:if>
			<c:if test="${error.errorType eq 'info'}"><span style="background-color: green;" ><c:out value="${error.message}" /> </span></c:if>
			<c:if test="${error.errorType eq 'error'}"><span style="background-color: red;" ><c:out value="${error.message}" /> </span></c:if>
			<c:if test="${error.errorType eq 'warn'}"><span style="background-color: yellow;" ><c:out value="${error.message}" /> </span></c:if>
			<br />
		</c:forEach>
		<script type="text/javascript">
            timeOutMessage();
        </script>
	</div>
	<c:remove var="errorsMessages" scope="session" />
</c:if>

