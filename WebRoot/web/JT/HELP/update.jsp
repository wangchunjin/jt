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
	        var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数
	        var orderflag=$("#orderflag").val();
	        var param=$("#title").val();
	        if(param==null || trim(param)==""){
	        	$.messager.alert("错误","帮助标题不能为空！");
	        	return;
	        }
	        if(r1.test(orderflag)==false){
	        	$.messager.alert("错误","排序只能是整数或小数！");
	        	return;
	        }
	        var params=$("#content").val();
	        if(params==null || trim(params)==""){
	        	$.messager.alert("错误","帮助内容不能为空！");
	        	return;
	        }
	        
	        var res = $('#addForm').SubmitForm("${ctx}/help/thelp/update.action");
	        
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
   <div style="height:500px;overflow: auto;" >
		<table style="margin-top: 10px;width: 96%;" >
		<tr>
				<th width="10%;" align="right"><span style="color: red;">*</span>帮助标题：</th>
				<td >
				<input  type="hidden" class="form_text"  name="help.id" id="id"  value="${help[0].id }" >
				<input  type="" class="form_text"  name="help.title" id="title"  value="${help[0].title }" >
				</td>
		</tr>
		<tr>
				<th width="10%;" align="right"><span style="color: red;">*</span>排序数字：</th>
				<td  >
				<input  type="" class="form_text"  name="help.orderflag" id="orderflag"  value="${help[0].orderflag }" >
				</td>
			</tr>
			<tr>
				<th width="10%;" align="right"><span style="color: red;">*</span>帮助内容：</th>
				<td >
					<textarea rows="20" cols="" name="help.content" id="content">${help[0].content }</textarea>
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
