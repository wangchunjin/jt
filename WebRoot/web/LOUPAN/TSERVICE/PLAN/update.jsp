<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script type="text/javascript" src="js/imagePreview.js"></script>
<script type="text/javascript">
	function MCEInit() {
		$("#CONTENTT_ifr").contents().find("#tinymce").html();
	};
	function UpdateRecord() {
// 		var currFrameId = "${param.currFrameId}";
		var content = tinyMCE.get('CONTENTT').getContent().replace(/"/g, "\'");
		$("#content").val(content);
		
		
	
	        
	        
	     
	       
		var res = $('#addForm').SubmitForm("${ctx}/serviceplan/tserviceplan/update.action");
		if (res[0].result == "success") {
			location.reload();
			parent.close_win('eidtWindow');
		} else {
			$.messager.alert("输入符号已存在!", res[0].result);
		}

	}
	 $(function(){
		 $("#CONTENTT").html($("#content").val());

	  });
	  
	 function getObjectURL(file) {
         var url = null;
         if (window.createObjcectURL != undefined) {
             url = window.createOjcectURL(file);
         } else if (window.URL != undefined) {
             url = window.URL.createObjectURL(file);
         } else if (window.webkitURL != undefined) {
             url = window.webkitURL.createObjectURL(file);
         }
         return url;
     }

     
	
	function getUrl(node){
		   
	    var file = null;  
	    if(node.files && node.files[0] ){  
	        file = node.files[0]; 
	    }else if(node.files && node.files.item(0)) {                                  
	        file = node.files.item(0);     
	    }
	     document.getElementById("imghead").src = getObjectURL(file);
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
		<table style="margin-top: 15px;width: 96%;" >
		
				<input type="hidden" class="form_text"  name="serviceplan.id" id="id"  value="${serviceplan[0].id }">
				
			
			<tr>
				<th width="5%" align="right"><span style="color: red;">*</span>服务图片：</th>	
				<td width="30%">
				
				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="${ctx}${serviceplan[0].pic}" width="NAN" height="100" /></a></div><br>
				
				</td>		
			</tr>
			<tr>
			<th width="5%"><span style="color: red;"></th>
				<td width="30%"><input name="file" type="file" id="productimg"  class="offset10 lf" onchange="getUrl(this)" /></td>
			</tr>
			
			
			<tr id="hhh">
			<th width="5%" align="right"><span style="color: red;">*</span>服务计划书：</th>	
				<td>
					<textarea id="CONTENTT" name="CONTENTT" class="label_textarea"  style="height: 350px;width: 1000px;" >${serviceplan[0].content }</textarea> 					   	  
				</td>
			</tr>
       </table>
       <input class="form_text" type="hidden"  name="serviceplan.content" id="content"  value="${serviceplan[0].content }">	
</form>
</div>
	<div class="BottomDiv">
		<epmis:button id="NewRecord" imageCss="icon-edit" value="确定" action="UpdateRecord()" datactrCode="servicetype_plan.edit" ></epmis:button> 
<!-- 		<a -->
<!-- 			href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a> -->
	</div>
</body>
</html>