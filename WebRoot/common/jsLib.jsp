<%@ page language="java" pageEncoding="UTF-8"%>
<!-- Common JS Lib & CSS Lib -->

<!-- jquery & easyui js -->
<script type="text/javascript" src="${ctx}/webResources/js/easyui/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctx}/webResources/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/webResources/js/easyui/locale/easyui-lang-zh_CN.js"></script>

<!-- easyui css -->
<link rel="stylesheet" type="text/css" href="${ctx}/webResources/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/webResources/js/easyui/themes/icon.css">

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
