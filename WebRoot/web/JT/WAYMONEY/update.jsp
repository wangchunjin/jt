<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script type="text/javascript" src="js/imagePreview.js"></script>
<script type="text/javascript">
	
	function UpdateRecord() {
// 		var currFrameId = "${param.currFrameId}";
		
		
	
		
		
	
	        
	        

	       
		var res = $('#addForm').SubmitForm("${ctx}/waymoney/twaymoney/update.action");
		if (res[0].result == "success") {
			location.reload();
			parent.close_win('eidtWindow');
		} else {
			$.messager.alert("输入符号已存在!", res[0].result);
		}

	}
	 
</script>
<style type="text/css">
#preview {
	width: 100px;
	height: 130px;
	overflow: hidden;
}

#imghead {
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);
}
</style>
</head>
<body class="NewWinClass">
	 <div style="height:500px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 150px;width: 96%;" align="center" >

				
			
			
			<tr id="hhh">
			<th width="50%" align="right"><span style="color: red;">*</span>打款方式：</th>
			
				<td width="300px">
				<input type="hidden" class="form_text"  name="waymoney.id" id="id"  value="${waymoney[0].id }">
				
					<select name="waymoney.type" id="type"  class="form_text" style="width: 150px" >
						<option value="0" <c:if test="${waymoney[0].type==0 }">selected="selected"</c:if>>代发</option>
						<option value="1" <c:if test="${waymoney[0].type==1 }">selected="selected"</c:if>>线下打款</option>
						
					
					</select>
				</td>
			</tr>
       </table>
       	
</form>
</div>
	<div class="BottomDiv">
		<epmis:button id="NewRecord" imageCss="icon-edit" value="确定" action="UpdateRecord()" datactrCode="wayy_update.edit" ></epmis:button> 
<!-- 		<a -->
<!-- 			href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a> -->
	</div>
</body>
</html>
