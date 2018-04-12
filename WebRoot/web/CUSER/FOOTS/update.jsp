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
	        var title=$("#title").val();
	        if(title==null||title==""){
	        	alert("汽车参数类别名不能为空！");
	        	return;
	        }
	        
	        var res = $('#addForm').SubmitForm("${ctx}/param/tparam/update.action");
	        
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
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
			
			
			<tr>
				<th width="20%"><span style="color: red;">*</span>汽车参数类别名：</th>
				<td width="20%">
				<input class="form_text" type="hidden"  name="param.id" id="id"  value="${params[0].id }">
				<input class="form_text" type="hidden"  name="param.isdel" id="isdel"  value="${params[0].isdel }">
				<input class="form_text" type="hidden"  name="param.cid" id="cid"  value="${params[0].cid }">
				<input class="form_text" type="hidden"  name="param.type" id="type"  value="${params[0].type }">
				<input class="form_text" type="hidden"  name="param.sid" id="sid"  value="${params[0].sid }">
				<input class="form_text" type=""  name="param.title" id="title"  value="${params[0].title }">
				
				</td>
			</tr>
       </table>
</form>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="UpdateRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('eidtWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
