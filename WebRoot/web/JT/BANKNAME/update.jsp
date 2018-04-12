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
	        var param=$("#name").val();
	        if(param==null || trim(param)==""){
	        	$.messager.alert("错误","银行名称不能为空！");
	        	return;
	        }
	        var params=$("#code").val();
	        if(params==null || trim(params)==""){
	        	$.messager.alert("错误","code不能为空！");
	        	return;
	        }
	        var paramss=$("#code_zfb").val();
	        if(paramss==null || trim(paramss)==""){
	        	$.messager.alert("错误","支付宝code不能为空！");
	        	return;
	        }
	       
	        var res = $('#addForm').SubmitForm("${ctx}/bankname/tbankname/update.action");
	        
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
		<table style="margin-top: 20px;width: 84%;" >
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>银行名称：</th>
				<td colspan="3" width="30%">
				<input  type="hidden" class="form_text"  name="bankname.id" id="id"  value="${bankname[0].id }" >
				<input  type="" class="form_text"  name="bankname.name" id="name"  value="${bankname[0].name }" >
				</td>
			</tr>
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>code：</th>
				<td colspan="3" width="30%">
					<input  type="" class="form_text"  name="bankname.code" id="code" value="${bankname[0].code }"  >
				</td>
			</tr>
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>支付宝code：</th>
				<td colspan="3" width="30%">
					<input  type="" class="form_text"  name="bankname.code_zfb" id="code_zfb" value="${bankname[0].code_zfb }"  >
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
