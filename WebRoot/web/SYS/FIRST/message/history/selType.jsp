<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>词典定义</title>
<script type="text/javascript">
function searchByDate(){
	TwoFrame.location.reload();
}
</script>
</head>
<body>
 <div style="width: 100%;height: 30px;">
<table width="100%" border="0">
   <tr height="25" >
     <td width="150px" align="center"><input type="radio" name="msgType" value="0"   checked    >已发送&nbsp;&nbsp;<input type="radio" name="msgType" value="1"  > 已接收</td>
      <td width="60" height="25" align="right">&nbsp;时间：&nbsp;</td>
      <td width="100">
             <input  class="form_text"    name="begin" id="begin"
                     onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'end\')}'})">
       </td>
       <td width="18" height="25" align="right">至</td>
        <td width="100">
          	 <input  class="form_text"    name="end" id="end"  
		             onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'begin\')}'})">     
        </td>
     <td>&nbsp;</td>
       <td>&nbsp;&nbsp;
          <epmis:button id="NewRecord" imageCss="icon-search" value="搜索" action="searchByDate()"  ></epmis:button>
         </td>
    </tr>
</table>
 </div>
 <div style="position: absolute;top: 30px;bottom: 0;left: 0;right: 0;">
	<iframe name="TwoFrame" width="100%" height="100%" id="TwoFrame" noresize="noresize" src="${ctx}/web/SYS/FIRST/message/history/content.jsp" frameborder="0" scrolling="no"></iframe>

</div>
</body>
</html>