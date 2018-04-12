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
	        var param=$("#param").val();
	        if(param==null || trim(param)==""){
	        	$.messager.alert("错误","param不能为空！");
	        	return;
	        }
	        var value=$("#value").val();
	        if(value==null || trim(value)==""){
	        	$.messager.alert("错误","value不能为空！");
	        	return;
	        }
	        var comment=$("#comment").val();
	        if(comment==null || trim(comment)==""){
	        	$.messager.alert("错误","comment不能为空！");
	        	return;
	        }
	        
	        var res = $('#addForm').SubmitForm("${ctx}/sysconf/tsysconf/update.action");
	        
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
				<th width="20%;" align="right"><span style="color: red;">*</span>param：</th>
				<td colspan="3" width="30%">
				<input  type="hidden" class="form_text"  name="sysconf.id" id="id"  value="${sysconf[0].id }" >
				<input  type="" class="form_text"  name="sysconf.param" id="param"  value="${sysconf[0].param }" >
				</td>
			</tr>
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>value：</th>
				<td colspan="3" width="30%">
				<input  type="" class="form_text"  name="sysconf.value" id="value"  value="${sysconf[0].value }" >
				</td>
			</tr>
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>comment：</th>
				<td colspan="3" width="30%">
				<input  type="" class="form_text"  name="sysconf.comment" id="comment"  value="${sysconf[0].comment }" >
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
