<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.epmis.sys.util.UserInfo" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/epmis-tags.tld" prefix="epmis"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	request.setAttribute("basePath",basePath);
    UserInfo user = (UserInfo) request.getSession().getAttribute("UserInfo");
    UserInfo user_ent = (UserInfo) request.getSession().getAttribute("UserInfo_Ent");
    String url = request.getRequestURI();
    if(url != null && !url.equals(request.getContextPath()+"/") && !url.endsWith("welcone_ent.jsp")&&!url.endsWith("ent")&& !url.endsWith("welcome.jsp")&&!url.endsWith("register.jsp")) {  
    	if(user == null&&user_ent == null){
           response.sendRedirect(request.getContextPath());  
    	}
    } 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<!-- jquery & easyui js -->
<script type="text/javascript" src="${ctx}/webResources/js/easyui1.4.4/jquery.min.js"></script >
<script type="text/javascript" src="${ctx}/webResources/js/easyui1.4.4/jquery.easyui.min.1.4.4.js"></script>	
<script type="text/javascript" src="${ctx}/webResources/js/easyui/jquery-form.js"></script>
<script type="text/javascript" src="${ctx}/webResources/js/easyui1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
</script>
<!-- easyui css -->
<link rel="stylesheet" type="text/css" href="${ctx}/webResources/js/easyui1.4.4/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/webResources/js/easyui1.4.4/themes/icon.css"> 

<!-- datePicker -->
<script src="${ctx}/webResources/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

<!-- validform -->
<script type="text/javascript" src="${ctx}/webResources/js/Validform/Validform_v5.3.2.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/webResources/js/Validform/Validform.css"/>


<script type="text/javascript">
    window.autoDatagridHeight= <%=(String) session.getAttribute("autoDatagridHeight")%>;
</script>
<!-- platform -->
<script type="text/javascript" src="${ctx}/webResources/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/webResources/style/css/default/base.css">
<link rel="stylesheet" type="text/css" href="${ctx}/webResources/style/css/default/animation1.css">
<link rel="stylesheet" type="text/css" href="${ctx}/webResources/style/css/default/form.css">


<!-- 原有的表单验证工具，为了兼容旧的项目 -->
<script src="${ctx}/webResources/js/validator.js"></script>
<script type="text/javascript" src="${ctx}/webResources/tinymce/tinymce.min.js"></script>
<script type="text/javascript" src="${ctx}/webResources/tinymce/tinyMCE.js"></script>

<script type="text/javascript">

	function SetDatactr(datactrValue){
		var datactrs =  '${UserInfo.getDatactrCode()}'+'${UserInfo_Ent.getDatactrCode()}';
		if(datactrs.indexOf(datactrValue) ==-1){
			SetReadOnly(true);
		}else{
			SetReadOnly(false);
		}
	}
	function SetDatactrs(datactrValue1,datactrValue2){
		var datactrs =  '${UserInfo.getDatactrCode()}'+'${UserInfo_Ent.getDatactrCode()}';
		if(datactrs.indexOf(datactrValue1) ==-1&&datactrs.indexOf(datactrValue2) ==-1){
			SetReadOnly(true);
		}else{
			SetReadOnly(false);
		}
	}
	
	function SetReadOnly(isTrue){
		if(isTrue){
			$("form input").removeAttr("onfocus");
			$("form input").attr("readonly","readonly");
			$("form select").attr("readonly","readonly");
			$("form textarea").attr("readonly","readonly");
			$("form input").css("background-color", "#eeeeee");
			$("form select").css("background-color", "#eeeeee");
			$("form textarea").css("background-color", "#eeeeee");
		}else{
			$("form input").removeAttr("readonly");
			$("form select").removeAttr("readonly");
			$("form textarea").removeAttr("readonly");
			$("form input").css("background-color", "#ffffff");
			$("form select").css("background-color", "#ffffff");
			$("form textarea").css("background-color", "#ffffff");
		}
	}
	$(document).ready(function(){
		var notFileType = "sql";
	$('input[type="file"]').change(function(e){
		var   accept = '\\.' + notFileType
        .replace( /,/g, '$|\\.' )
         .replace( /\*/g, '.*' ) + '$';
		accept = new RegExp( accept, 'i' );
	    rExt = /\.\w+$/; 
	    
	      var reg = /[^\\\/]*[\\\/]+/g; //匹配文件的名称和后缀的正则表达式
	      var fileName = $(this).val().replace(reg, '');
	     
	   
	     if (rExt.exec( fileName ) &&  accept.test( fileName )) {
			   this.value = "";
			   $.messager.alert("错误","文件格式不允许上传！");
		 }  
	});
	});
	
</script>