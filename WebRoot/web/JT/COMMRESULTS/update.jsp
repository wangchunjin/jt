<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
    
 <script type="text/javascript">
	     function UpdateRecord(){
	        var currFrameId = "${param.currFrameId}";
	      
	        var comment=$("#name").val();
	        if(comment==null || trim(comment)==""){
	        	$.messager.alert("错误","沟通结果名称不能为空！");
	        	return;
	        }
	        
	        var res = $('#addForm').SubmitForm("${ctx}/commresults/tcommresults/update.action");
	        
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('eidtWindow');
			}
 			else{
 				parent.parent.$.messager.alert("输入符号已存在!",res[0].result);
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
  <style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
   <div style="height:300px;overflow: auto;" >
		<table style="margin-top: 50px;width: 84%;" >
		<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>沟通结果名称：</th>
				<td colspan="3" width="30%">
				<input  type="hidden" class="form_text"  name="commresults.id" id="id"  value="${commresults[0].id }" >
				<input  type="" class="form_text"  name="commresults.name" id="name"  value="${commresults[0].name }" >
				</td>
		</tr>
			
       </table>
       </div>
</form>

		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
