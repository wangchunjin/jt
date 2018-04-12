<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript" src="js/imagePreview.js"></script>
    
 <script type="text/javascript">
	     function UpdateRecord(){
	    	 var r1=/^[0-9]+([.][0-9]+){0,1}$/;//判断是否是整数或小数
	        var currFrameId = "${param.currFrameId}";
	        var param=$("#name").val();
	        if(param==null || trim(param)==""){
	        	$.messager.alert("错误","出借人姓名不能为空！");
	        	return;
	        }
	        var params=$("#idcard").val();
	        if(params==null || trim(params)==""){
	        	$.messager.alert("错误","身份证号不能为空！");
	        	return;
	        }
	        var paramss=$("#mobile").val();
	        if(paramss==null || trim(paramss)==""){
	        	$.messager.alert("错误","手机号不能为空！");
	        	return;
	        }
	        var money=$("#money").val();
	        if(money==null || trim(money)==""){
	        	$.messager.alert("错误","出借人额度不能为空！");
	        	return;
	        }
	        if(r1.test(money)==false){
	        	$.messager.alert("错误","出借人额度只能为数字或小数！");
	        	return;
	        }
	        var res = $('#addForm').SubmitForm("${ctx}/lender/tlender/update.action");
	        
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
				<th width="20%;" align="right"><span style="color: red;">*</span>姓名：</th>
				<td colspan="3" width="30%">
				<input  type="hidden" class="form_text"  name="lender.lender_id" id="lender_id"  value="${lender[0].lender_id }" >
				<input  type="" class="form_text"  name="lender.name" id="name"  value="${lender[0].name }" >
				</td>
			</tr>
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>身份证号：</th>
				<td colspan="3" width="30%">
					<input  type="" class="form_text"  name="lender.idcard" id="idcard" value="${lender[0].idcard }"  >
				</td>
			</tr>
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>手机号：</th>
				<td colspan="3" width="30%">
					<input  type="" class="form_text"  name="lender.mobile" id="mobile" value="${lender[0].mobile }"  >
				</td>
			</tr>
			<tr>
				<th width="20%;" align="right"><span style="color: red;">*</span>额度：</th>
				<td colspan="3" width="30%">
					<input  type="" class="form_text"  name="lender.money" id="money" value="${lender[0].money }"  >
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
