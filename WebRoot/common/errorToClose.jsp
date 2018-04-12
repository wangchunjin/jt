<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%
		String successUrl = request.getParameter("successUrl");
	%>
	<head>
		<title>Success</title>
		<%@include file="/common/jsLib.jsp"%>
		<script language="JavaScript">
       function returnPage(successUrl) {
       		if(opener==null){
	            currTab = top.getCurrTab();
	        }else
	        	currTab = opener.top.getCurrTab();
       		target = currTab.find("iframe");
			 if(successUrl.indexOf("?")!=-1)
			 	target[0].src="${ctx}/"+successUrl+"&refresh=1";
             else
             	target[0].src="${ctx}/"+successUrl+"?refresh=1";
            window.close("_self");
		}
	</script>
	<c:set var="curr_path" value="操作结果"></c:set>
	</head>
	<body>
		<div class="caption">
		</div>
		<div class="submitdata">
			<table width="200" align="center" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td class="set_c">
						<c:if test="${not empty message}">${message}</c:if>
						<c:if test="${empty message}">操作失败！</c:if>
					</td>
				</tr>
				<tr>
					<td class="set_c">
						<input type="button" class="input_button" value="返 回"onclick="returnPage('<%=successUrl%>');" />
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
