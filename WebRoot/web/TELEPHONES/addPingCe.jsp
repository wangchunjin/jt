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

	        
	        
	        var res = $('#addForm').SubmitForm("${ctx}/telephones/ttelephones/save.action");
	       
	        
	        
	        
	 	  	if(res[0].result=="success"){
	 		GetIndexFrame(currFrameId).LeftFrame.TwoFrame.location.reload();
	 		parent.close_win('addWindow');
			}
 			else{
 				$.messager.alert("输入符号已存在!",res[0].result);
 		 	}
	
	     };
	    
	     
	     
	     
	     
	     
 </script>
<style type="text/css">
			#preview{width:100px;height:130px;overflow:hidden;}
			#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
	</style>
 </head>
  <body class="NewWinClass">
    <div style="height:500px;overflow: auto;">
   <form method="post" action="" id="addForm" name="addForm">
		<table style="margin-top: 15px;width: 96%;" >
			
	
			<tr id="www"  >
				<th width="20%" align="right"><span style="color: red;">*</span>服务类别：</th>
				<td width="20%">
					<select id="type" class="form_text"  name="telephones.type">
						<option value="1">客服电话</option>
						<option value="2">合作电话</option>
						<option value="3">售后电话</option>
						
					</select>
				
				</td>
			</tr>
			<tr id="ccc"  >
				<th width="20%" align="right"><span style="color: red;">*</span>服务电话：</th>
				<td width="20%"><input class="form_text"  name="telephones.telephone" id="telephone"  value=""></td>
			</tr>
			
			
			
			
       </table>
      
</form>
</div>
		<div class="BottomDiv">
			<a href="###" class="btn_01" onclick="AddRecord();">确定<b></b></a>
			<a href="###" class="btn_01" onclick="parent.close_win('addWindow')">关闭<b></b></a>				
		</div>
  </body>
</html>