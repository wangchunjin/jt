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
	      
			
			
	 	        var title = $("#title").val();
	 	        if(title==null||title==""){
	 	        	parent.$.messager.alert("错误","使用帮助主问题不能为空！");
	 	        	return;
	 	        }
	 	       
	 	        

	        var res = $('#addForm').SubmitForm("${ctx}/help/thelp/update.action");
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('eidtWindow');
			}
 			else{
 				parent.$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	
	     }
	     
		  
 </script>
 <style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
  <div style="height:330px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 50px;width: 96%;" >
			<tr>
				<th width="20%"><span style="color: red;">*</span>帮助主问题标题：</th>
				<td width="20%">
				<input class="form_text" type="hidden"  name="help.id" id="id"  value="${help[0].id }">
				<input class="form_text" type="hidden"  name="help.isdel" id="isdel"  value="${help[0].isdel }">
				<input class="form_text" type="hidden"  name="help.sid" id="sid"  value="${help[0].sid }">
				<input class="form_text" type="hidden"  name="help.type" id="type"  value="${help[0].type }">
				<input class="form_text"  name="help.title" id="title"  value="${help[0].title }"></td>
			</tr>
       </table>
<!--       <input class="form_text" type="hidden"  name="supp.content" id="content"  value="${supp[0].content }">	 -->
</form>
</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
