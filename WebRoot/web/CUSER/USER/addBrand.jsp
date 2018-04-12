<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
 <script type="text/javascript">
	     function AddRecord(){
	        var currFrameId = "${param.currFrameId}";
 	        var title = $("#title").val();
 	        if(title==null||title==""){
 	        	$.messager.alert("错误","级别不能为空！");
 	        	return;
 	        }
 	        var type = $("#type").val();
 	        if(type==null||type==""){
 	        	$.messager.alert("错误","预留字段不能为空！");
 	        	return;
 	        }

	        var res = $('#addForm').SubmitForm("${ctx}/level/tlevel/save.action");
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('addWindow');
			}
 			else{
 				$.messager.alert("输入符号已存在!",res[0].result);
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
				<th width="20%"><span style="color: red;">*</span>级别：</th>
				<td width="20%"><input class="form_text"  name="level.title" id="title"  value=""></td>
				</tr>
			<tr>
				<th width="20%"><span style="color: red;">*</span>小图标：</th>
				<td width="30%">
				<!--  <input class="form_text"  name="level.ico" id="ico"  value=""></td>-->
				<div class="lf salebd"><a href="#"  id="preview"><img id="imghead"  src="../../../images/ic_pic_loading.png" width="50" height="40" /></a></div><br>
				</td>
			</tr>
			<tr>
			<th width="20%"><span style="color: red;"></th>
				<td width="30%"><input name="file" type="file" id="productimg"  class="offset10 lf" onchange="previewImage(this)" /></td>
			</tr>
			<tr>
				<th width="20%"><span style="color: red;">*</span>预留字段</th>
				<td width="20%">
				<input class="form_text" type="hidden"  name="level.isdel" id="isdel"  value="0">
				<input class="form_text"  name="level.type" id="type"  value=""></td>
				</tr>
			
			<tr>
				<th width="20%"><span style="color: red;">*</span>级别状态：</th>
				<td width="30%">
					<select name="level.status" id="status">						
						<option value="0">正常</option>
						<option value="1">推荐首页</option>
						
					</select>
				</td>
				
				
			</tr>
			
       </table>
</form>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>
