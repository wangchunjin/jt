<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>标签修改</title>
  </head>
  <script type="text/javascript">
  function SaveRecord(){
      
    var areaEn = $("#region\\.areaEn").val();
	if(areaEn.replace(/\s/g,'') == ""){
		$.messager.alert("提示","英文名/拼音不能为空!");
		return false;
	}
	
	var areaName = $("#region\\.areaName").val();
	if(areaName.replace(/\s/g,'') == ""){
		$.messager.alert("提示","名称不能为空!");
		return false;
	}
	
	var seqNum = $("#region\\.seqNum").val();
	if(seqNum.replace(/\s/g,'') == ""){
		$.messager.alert("提示","排序号不能为空!");
		return false;
	}
    var res=  $("#labelForm").SubmitForm("${ctx}/sys/sys_region/update.action");
    if(res[0].result=="success"){
		window.location.reload();
     }else{
  	   $.messager.alert("错误",res[0].result)
     }
  }
  </script>
  <body class="LabelWinClass">
     <div class="label_div">
       <form method="post" action="" id="labelForm" name="labelForm">
       		<input type="hidden" name="region.wid" value="${region.WID }">
     		<table style="margin-top: 15px;width: 96%;" >
				<tr class="">
					<th width="12%">英文名/拼音</th>
					<td width="38%" class=""  ><input class="label_text" name="region.areaEn" id="region.areaEn" value="${region.AREA_EN }"></td>
					<th width="12%">名称</th>
					<td width="38%" class="" colspan=""><input class="label_text" name="region.areaName" id="region.areaName" value="${region.AREA_NAME }"></td>				
				</tr>
				<tr class="">
					<th width="12%">排序</th>
					<td width="38%" class=""  ><input class="label_text" name="region.seqNum" id="region.seqNum" value="${region.SEQ_NUM }" style="text-align:right;" onblur="value=value.replace(/[^\- \d]/g,'')"></td>
				</tr>
       		</table>
       </form>
     </div> 						
  </body>
</html>
