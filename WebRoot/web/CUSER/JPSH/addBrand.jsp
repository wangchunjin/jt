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
	        var r4=/^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;  //判断是否是手机号码
	        var telephone=$("#telephone").val();
	        
	        
	        if(telephone==null || trim(telephone)==""){
	        	$.messager.alert("错误","手机号码不能为空！");
	        	return;
	        }
	        if(r4.test(telephone)==false){
	        	$.messager.alert("错误","输入手机号格式不正确！");
	        	return;
	        }
	        var result = GetXmlBySql("SELECT count(id)num FROM T_user WHERE telephone='"+telephone+"'");
	    	 if(result[0].num==0){
	    		 
	    	 }else{
	    		 $.messager.alert("错误","手机号码已被注册，请重新填写！");
		        	return;
	    	 }
	    	 
	    		
	    	 return;

	        var res = $('#addForm').SubmitForm("${ctx}/user/tuser/save.action");
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
				<th width="20%"><span style="color: red;">*</span>真实姓名：</th>
				<td width="20%"><input class="form_text"  name="user.realName" id="realName"  value=""></td>
			</tr>			
			<tr>
				<th width="20%"><span style="color: red;">*</span>性别：</th>
				<td width="20%">
					<select id="sex" class="form_text" name="user.sex">
						<option value="男">男</option>
						<option value="女">女</option>
					</select>				
				</td>
			</tr>	
			<tr>
				<th width="20%"><span style="color: red;">*</span>生日：</th>
				<td width="20%">				
					<input class="form_text"  name="user.birthdate" id="user.birthdate" readonly="readonly"  value="" onfocus="DataEdit('yyyy-MM-dd','','')">		
				</td>
			</tr>		
			<tr>
				<th width="20%"><span style="color: red;">*</span>手机号：</th>
				<td width="30%">
					<input class="form_text"  name="user.telephone" id="telephone"  value="">
				</td>			
			</tr>
			
			<tr>
				<th width="20%"><span style="color: red;">*</span>金牌成员类别：</th>
				<td width="20%">
					<select id="neibu" class="form_text" name="user.neibu">
						<option value="0">外部金牌顾问</option>
						<option value="1">内部金牌顾问</option>
					</select>				
				</td>
			</tr>
			<tr>
				<th width="20%"><span style="color: red;">*</span>身份证号：</th>
				<td width="20%"><input class="form_text"  name="user.IdNo" id="IdNo"  value=""></td>
			</tr>	
			
       </table>
</form>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>